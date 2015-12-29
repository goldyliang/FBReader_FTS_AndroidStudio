package org.geometerplus.fbreader.fulltextsearch;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.text.BreakIterator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import org.geometerplus.fbreader.fulltextsearch.FTSIndexDatabase.BookIndexStatus;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.book.BookEvent;
import org.geometerplus.fbreader.book.BookUtil;
import org.geometerplus.fbreader.book.IBookCollection;
import org.geometerplus.fbreader.library.RootTree;
import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.text.model.ZLTextModel;


/**
 * Created by gordon on 10/21/15.
 */
@TargetApi(16)
public class FTSService extends IntentService implements IBookCollection.Listener {

    public static final String FTS_BOOKS_FOLDER = "folder";


    private boolean mMarkIndexTaskCanceled = false; // flag indicating the request of cancellation

    FTSIndexDatabase mIndexDatabase;

    public static final String PROGRESS_REPORT = "com.goldyliang.FTSProgress";


    @Override
    public void onBookEvent(BookEvent event, Book book) {

    }

    @Override
    public void onBuildEvent(IBookCollection.Status status) {

    }

    /**
     * Clear the root tree of book library
     */
    private synchronized void deleteRootTree() {
        if (myRootTree != null) {
            myCollection.removeListener(this);
            myCollection.unbind();
            myRootTree = null;
        }
    }

    // Add all book files under folder to the list of books to index
    private void addBookFilesToIndex (File folder) {

        Log.v("FTS", "Check path:" + folder.getPath());

        for (File sub : folder.listFiles()) {
            Log.v("FTS", "Check path:" + sub.getPath());
            if (sub.isFile()) {
                ZLFile file = ZLFile.createFileByPath(sub.getPath());
                Book book;
                if (file!=null && (book = myCollection.getBookByFile(file))!=null) {
                    addBookIndex(book);
                }
            } else if (sub.isDirectory()) {
                addBookFilesToIndex (sub);
            }
        }
    }




    /**
     *  The list of books to be indexed in order
     */
    private List <BookIndexStatus> orderedListBookIndex = new LinkedList<BookIndexStatus>();

    private int totalParagraphs;
    private int doneParagraphs;
    private int cumulatedIndexedCount;
    private static final int UPDATE_PROGRESS_PER_PARAGRAPH = 100;

    /**
     * Book collection service provided by FBReader
     */
    private final BookCollectionShadow myCollection = new BookCollectionShadow();

    /**
     * The root tree from which to access the FBReader libraries trees and books
     */
    private volatile RootTree myRootTree;


    public FTSService() {

        super("FTSService");

    }

    private void waitForBound() {

        synchronized (myCollection) {
            while (myRootTree == null) {
                try {
                    myCollection.wait();
                } catch (InterruptedException e) {
                }
            }
        }

        Log.v("FTS", "Waited bound");
    }

    private synchronized void getRootTreeAndNotify () {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        deleteRootTree();

        /* Bind to library service, and once bound, initialize local fields */
        myCollection.bindToService(this, new Runnable() {
            public void run() {
                myCollection.addListener(FTSService.this);

                synchronized (myCollection) {
                    myRootTree = new RootTree(myCollection);
                    myCollection.notifyAll();
                }

                Log.v("FTS", "Collection bound");

            }
        });
    }

    /**
     * Handle intent of building book index
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        String path = intent.getStringExtra(FTS_BOOKS_FOLDER);
        String pathIndexDB = path;

        Log.v("Handle Intent:", path + pathIndexDB);

        File folder = new File (path);

        this.orderedListBookIndex.clear();

        waitForBound();

        try {
            mIndexDatabase = FTSIndexDatabase.getWrittableIndexDatabase(this, path);

            if (mIndexDatabase != null) {

                totalParagraphs = 0;

                // add all books file to orderedListBookIndex
                addBookFilesToIndex(folder);

                Log.v("FTS", "Total paragraphs:" + totalParagraphs);

                // create index for each file in orderedListBooksIndex
                loadAndCreateIndex();

            }
        } finally {
            mIndexDatabase.closeDatabase();
        }


    }

    @Override
    public void onDestroy() {
        deleteRootTree();

        mIndexDatabase.closeDatabase();

        super.onDestroy();
    }


    /* Combine information of the index-list and the status table
       If a book in the index-list already exists in status table,
              update the last index position from the status table
       If not, insert an entry for the book
     */
    private void loadBookIndexStatus () {

        doneParagraphs = 0;

        Iterator<BookIndexStatus> iter = orderedListBookIndex.iterator();

        while (iter.hasNext()) {
            BookIndexStatus bkStatus = iter.next();

            BookIndexStatus statusDatabase = mIndexDatabase.getBookStatus(bkStatus.bookId);

            if (statusDatabase != null) {
                // The status of this book already in database
                // Verify information

                //check the status in database, whether it is inline with the actual file
                if ( ! bkStatus.sameFileAs (statusDatabase) ) {
                    // this is a modified file, not the original one
                    // Ignore it. When the library is refreshed, there will be a new book ID
                    // and the scan will be redo towards the new book ID
                    // SO now just remove this book status
                    iter.remove();
                } else {
                    // update the last index position
                    bkStatus.indexPos = statusDatabase.indexPos;

                    doneParagraphs += bkStatus.indexPos;

                    if (bkStatus.indexPos >= bkStatus.size)
                        // already finished, ignore
                        iter.remove();;
                }
            } else {
                // THe status of this book not yet in database
                // Create an entry

                mIndexDatabase.addBookStatus(bkStatus);
            }
        }

    }

    /* Add a book into the indexing book list orderedListBookIndex (set to highest priority)
     */
    public void addBookIndex(Book book) {

        Log.v("Add book" , book.getTitle());

        BookIndexStatus bkStatus = new BookIndexStatus();

        bkStatus.bookId = book.getId();
        ZLFile file = book.File;
        bkStatus.path = file.getPath();
        bkStatus.size = BookUtil.getBookParagraphNum(book);

        totalParagraphs += bkStatus.size;

        bkStatus.modifiedTime = file.lastModified();

        bkStatus.indexPos = 0; // Initial value, will be overwritten with the value in database

        //booksIndexed.put(book.getId(), bkStatus);

        orderedListBookIndex.add(0, bkStatus);

        //addBookStatusInDatabase (bkStatus);

    }

    // start or resume the index creation
    private void loadAndCreateIndex() {

        loadBookIndexStatus();

        updateProgress(true);

        Log.v ("FTS", "Now loading index for " + orderedListBookIndex.size() + " books.");

        // for each new or updated file, build the index
        for (BookIndexStatus bkStatus : orderedListBookIndex) {
            if (mMarkIndexTaskCanceled) break;

            loadAndCreateIndex(bkStatus);
        }

        // load and create index for each new or update file

    }


    private void updateProgress (boolean forceReport) {
        boolean report = (forceReport ||
                cumulatedIndexedCount >= UPDATE_PROGRESS_PER_PARAGRAPH);

        if (report) {
            cumulatedIndexedCount = 0;

            Intent msgIntent = new Intent(PROGRESS_REPORT);
            msgIntent.putExtra("progress", (totalParagraphs==0 ? 0:(doneParagraphs * 100) / totalParagraphs));
            LocalBroadcastManager.getInstance(this).sendBroadcast(msgIntent);

            Log.v("FTS", "Progress:" + doneParagraphs + " out of " + totalParagraphs);
        } else
            cumulatedIndexedCount ++;

    }

    // load the book and create index for the book provided in bkStatus
    // Start from last location indicated in bkStatus
    private void loadAndCreateIndex(BookIndexStatus bkStatus) {

        //mLastDocID = 0;

        updateProgress(true);

        try {

            long size = bkStatus.size;
            float progress = 0f;

            ContentValues newPosValues = new ContentValues();

            String line;

            System.out.println("indexed:" + bkStatus.indexPos + "; total:" + size +
                    "; progress:" + String.valueOf(progress));


            Book book = myCollection.getBookById(bkStatus.bookId);

            ZLTextModel modelText = BookUtil.getModelText(book);

            if (modelText == null)
                return;

            int n = modelText.getParagraphsNumber();

            Log.v("PARA NUM", String.valueOf(n));

            long bookId = book.getId();

            for (int i = bkStatus.indexPos; i < n; i++) {

                String txt = BookUtil.getParagraph(modelText, i);

                if (txt != null && txt.length() > 0) {
                    //Log.v("PARA-", String.valueOf(i));

                    mIndexDatabase.insertLineIndex(bookId, i, txt);

                    updateProgress(false);

                    //System.out.println("indexed:" + i + "; total:" + n + "; progress:" + progress);
                }

                if (mMarkIndexTaskCanceled) break;

                mIndexDatabase.updateBookIdxPosition(bkStatus.bookId, i+1);

                bkStatus.indexPos = i + 1;

                doneParagraphs ++;
                //Log.v("Para " + i, String.valueOf(start) + "-" + String.valueOf(len)); //new String (c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


 /*   public Cursor getWordMatches(String query, String[] columns) {
        if (query!=null) {
            String selection = COL_WORD + " MATCH ?";
            String[] selectionArgs = new String[]{query};

            return query(selection, selectionArgs, columns);
        }
            return query (null, null, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }*/


}
