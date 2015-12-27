package org.geometerplus.fbreader.fulltextsearch;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.text.Html;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.Paths;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.book.BookEvent;
import org.geometerplus.fbreader.book.IBookCollection;
import org.geometerplus.fbreader.bookmodel.BookModel;
import org.geometerplus.fbreader.bookmodel.BookReadingException;
import org.geometerplus.fbreader.formats.BuiltinFormatPlugin;
import org.geometerplus.fbreader.formats.FormatPlugin;
import org.geometerplus.fbreader.library.LibraryTree;
import org.geometerplus.fbreader.library.RootTree;
import org.geometerplus.fbreader.tree.FBTree;
import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.text.model.ZLTextModel;
import org.geometerplus.zlibrary.text.model.ZLTextParagraph;


/**
 * Created by gordon on 10/21/15.
 */
@TargetApi(16)
public class FTSService extends IntentService implements IBookCollection.Listener {

    private static final String TAG = "FTSService";

    private static final String COL_LOCATION = "LOCATION";
    private static final String COL_TEXT = "TEXT";
    private static final String COL_DOCID = "docid";
    private static final String COL_BOOKTITLE = "TITLE";


    private static final String COL_BOOKID = "ID";
    private static final String COL_FILEPATH = "PATH";
    private static final String COL_FILESIZE = "SIZE";
    private static final String COL_MODIFIED_TIME = "MODIFIED";
    private static final String COL_INDEXPOS = "INDEXPOS";

    private static final String COL_ROWID = "rowid";

    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final String FTS_CONTENT_TABLE = "CONTENT_TABLE";
    private static final String BOOK_STATUS_TABLE = "FILE_STATUS";

    private static final String FTS_TABLE_CREATE =
            "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                    " USING fts4 (" +
                    "content=" + FTS_CONTENT_TABLE + ", " +
                    COL_BOOKID + ", " +
                    COL_LOCATION + ", " +
                    COL_TEXT +
                    //", notindexed=" + COL_BOOKID +
                    //", notindexed=" + COL_LOCATION +
                    //", tokenize=icu zh_CN)";
                    ")";

    private static final String FTS_CONTENT_TABLE_CREATE = // doc location
            "CREATE TABLE " + FTS_CONTENT_TABLE
                    + " ( "
                    //+ COL_DOCID +  " INTEGER PRIMARY KEY, "
                    + COL_BOOKID + " ,  "
//                    + COL_FILEPATH + ", "
                    + COL_LOCATION + ""
                    + COL_TEXT
                    +")";


    private static final String BOOK_STATUS_TABLE_CREATE =
            "CREATE TABLE " + BOOK_STATUS_TABLE
                    + " ( "
                    + COL_BOOKID + ","
                    + COL_FILEPATH + ","
                    + COL_FILESIZE + ","
                    + COL_MODIFIED_TIME + ","
                    + COL_INDEXPOS + " ) ";

    private static final int DATABASE_VERSION = 1;

    private final DatabaseOpenHelper mDatabaseOpenHelper;

    private static final String FORMAT_HIGHTLIGHT_START = "<font color='red'>";
    private static final String FORMAT_HIGHTLIGHT_END = "</font>";
    private static final String DATABASE_PATH = Paths.TempDirectoryOption.getValue();
    private static final String DATABASE_FILE = "index.db";

    private BreakIterator mBreakIter;// = BreakIterator.getWordInstance(Locale.CHINA);
   /*
        mBreakIter = BreakIterator.getWordInstance(Locale.CHINESE);
    } */

    private boolean mIndexCreated = false;
    private long mLastDocID=0;

    private SQLiteDatabase mDatabaseIndexing;

    private String mDir;

    boolean flagRescanBookStatus = false;
    private boolean mMarkIndexTaskCanceled = false;

    @Override
    public void onBookEvent(BookEvent event, Book book) {

    }

    @Override
    public void onBuildEvent(IBookCollection.Status status) {

    }

    private synchronized void deleteRootTree() {
        if (myRootTree != null) {
            myCollection.removeListener(this);
            myCollection.unbind();
            myRootTree = null;
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mDatabaseIndexing = mDatabaseOpenHelper.getWritableDatabase();

        // TODO: temporoty
        addAllBooksToIndex(myRootTree);

        loadAndCreateIndex();
    }

    private class BookIndexStatus {
        Book book;
        String path;
        long size;
        long modifiedTime;
        long indexPos;
    }

    private Map <Long, BookIndexStatus> booksIndexed =
            new TreeMap <Long,BookIndexStatus> ();

    private List <BookIndexStatus> orderedListBookIndex = new LinkedList<BookIndexStatus>();

    private final BookCollectionShadow myCollection = new BookCollectionShadow();

    private volatile RootTree myRootTree;



    public FTSService() {

        super("FTSService");

        mBreakIter = BreakIterator.getWordInstance();

        mDatabaseOpenHelper = new DatabaseOpenHelper(null, 1);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        deleteRootTree();

        myCollection.bindToService(this, new Runnable() {
            public void run() {
                myRootTree = new RootTree(myCollection);
                myCollection.addListener(FTSService.this);
            }
        });
    }

    private final String [] COLUMNS_BOOK_STATUS_TABLE = {
            COL_BOOKID, COL_FILEPATH, COL_FILESIZE, COL_MODIFIED_TIME, COL_INDEXPOS
    };


    /* Read from the database and update the
       index status table
     */
    public void loadBookIndexStatus () {
        booksIndexed.clear();
        orderedListBookIndex.clear();


        //private final String [] COLUMNS_BOOK_STATUS_TABLE = {
        //        COL_BOOKID, COL_FILEPATH, COL_FILESIZE, COL_MODIFIED_TIME, COL_INDEXPOS
        //};

        Cursor c = null;
        synchronized (this) {
            c = mDatabaseIndexing.query(BOOK_STATUS_TABLE, COLUMNS_BOOK_STATUS_TABLE,
                    null, null, null, null, null);
        }

        c.moveToFirst();
        while (!c.isAfterLast()) {

            BookIndexStatus bkStatus = new BookIndexStatus();

            long id = c.getLong (0);
            //Book book = BookCollection.Status.

            Book book = myCollection.getBookById(id);
            bkStatus.book = book;
            ZLFile file = book.File;
            bkStatus.path = file.getPath();
            bkStatus.size = file.size();
            bkStatus.modifiedTime = file.lastModified();

            //check the status in database, whether it is inline with the actual file
            if (!bkStatus.path.equals(c.getString(1)) ||
                 bkStatus.size != c.getLong(2) ||
                 bkStatus.modifiedTime != c.getLong(3) ) {
                // TODO
                // this is a modified file, not the original one
                // add the record to the database as well
            }

            bkStatus.indexPos = c.getLong(4);

            booksIndexed.put (book.getId(), bkStatus);

            orderedListBookIndex.add(0, bkStatus);

            c.moveToNext();
        }

    }

    // add all books in the collection to the index book list
    private void addAllBooksToIndex (LibraryTree tree) {
        for (int i=1; i<=7; i++) {
            Book bk = myCollection.getBookById(i);
            if (bk!=null)
                addBookIndex(bk);
        }
    }

    private void addBookStatusInDatabase (BookIndexStatus bkStatus) {

        //private final String [] COLUMNS_BOOK_STATUS_TABLE = {
        //        COL_BOOKID, COL_FILEPATH, COL_FILESIZE, COL_MODIFIED_TIME, COL_INDEXPOS
        //};

        ContentValues values = new ContentValues();
        values.put (COL_BOOKID, bkStatus.book.getId());
        values.put (COL_FILEPATH, bkStatus.path);
        values.put (COL_FILESIZE, bkStatus.size);
        values.put (COL_MODIFIED_TIME, bkStatus.modifiedTime);
        values.put(COL_INDEXPOS, bkStatus.indexPos);

        mDatabaseIndexing.insert(BOOK_STATUS_TABLE, null, values);

    }

    /* Add a book into the index pool (set to highest priority)
       and restart the indexing process if it was running
     */
    public void addBookIndex(Book book) {

        if (booksIndexed.get(book.getId())!=null)
            return;

        BookIndexStatus bkStatus = new BookIndexStatus();

        bkStatus.book = book;
        ZLFile file = book.File;
        bkStatus.path = file.getPath();
        bkStatus.size = file.size();
        bkStatus.modifiedTime = file.lastModified();

        bkStatus.indexPos = 0; // TODO: get index pos from database

        booksIndexed.put(book.getId(), bkStatus);

        orderedListBookIndex.add(0, bkStatus);

        addBookStatusInDatabase (bkStatus);

    }

    /* Delete a book from the index pool
       and delete all indexing tuples from database
     */
    public void deleteBookIndex(Book book) {
        BookIndexStatus bkStatus = booksIndexed.get(book.getId());

        if (bkStatus!=null) {
            orderedListBookIndex.remove(bkStatus);

            booksIndexed.remove(book.getId());
        }

        // TODO: remove book from database
        // TODO: remove index data from database

    }

    /* Reset the book indexing tuples for the book
       (delete, re-add and set to highest priority,
       restart the indexing process if it was running
     */
    public void resetBookIndex(Book book) {

    }

    /* Get the list of books being indexed
     */
    public List<Book> getBooksIndexed () {
        return null;
    }

    /* Set the sub-set of books for next Full Text Search
     */
    public boolean setBooksForFTS ( List<Book> listBooks) {
        return false;
    }

    /* Get the overall index progress

     */
    public float indexProgress() {
        return 0;
    }

    /* Get the index progress for the subset of books

     */
    public float indexProgress(List<Book> listBooks) {
        return 0;
    }

    /* Get the index progress for the specific book

     */
    public float indexProgress(Book book) {
        BookIndexStatus bkStatus = booksIndexed.get(book.getId());

        return (float)bkStatus.indexPos / bkStatus.size;
    }

    /* Return whether the background index task is running

     */
    public boolean isBackgroundIndexTaskRunning () {

        return false;
    }

    /* Pause the background index building task
       Preserve the list of index books, the order and the status
     */
    public void pauseBackgroundIndexTask () {
    }

    /* Resume the background index building task
       from where it was paused
     */
    public void resumeBackgroundIndexTask() {



    }

    /* Rescan the index book list and status, and restart the background index task

     */
    public void restartBackgroundIndexTask() {


    }

    /* Return whether all the index has been created

     */
    public boolean isAllIndexCreated () {

        return indexProgress() >= 1.0;
    }

    private synchronized String getTokenizedText (String txt, List<String> words) {
        mBreakIter.setText(txt);
        int start = mBreakIter.first();
        int end = mBreakIter.next();

        StringBuffer buf = new StringBuffer();
        if (words!=null) words.clear();

        while (end != BreakIterator.DONE) {
            String word = txt.substring(start,end).trim();
            if (!word.isEmpty()) {
                if (words!=null) words.add(word);
                buf.append(txt.substring(start, end));
                buf.append(" ");
            }
            start = end;
            end = mBreakIter.next();
        }

        buf.deleteCharAt(buf.length()-1);

        return buf.toString();
    }

    private static String getHighlightedText (String txt, List<String> words) {
        StringBuffer buf = new StringBuffer(txt);

        for (String word: words) {
            int i = 0;
            while ((i = buf.indexOf(word, i)) >= 0) {
                String formated_word = FORMAT_HIGHTLIGHT_START + word + FORMAT_HIGHTLIGHT_END;
                buf.replace(i, i + word.length(), formated_word);
                i = i + formated_word.length();
            }
        }

        return new String (buf);
    }


    private class DatabaseOpenHelper extends SDCardSQLiteOpenHelper {

        //private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        public DatabaseOpenHelper (SQLiteDatabase.CursorFactory factory, int version) {
            super(DATABASE_PATH, DATABASE_FILE, factory, version);
        }
       /* DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            //mHelperContext = context;
        }*/

        private void deleteTables () {
            mDatabase.execSQL("DROP TABLE IF EXISTS " + BOOK_STATUS_TABLE);
            mDatabase.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            mDatabase.execSQL("DROP TABLE IF EXISTS " + FTS_CONTENT_TABLE);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL (FTS_CONTENT_TABLE_CREATE);
            mDatabase.execSQL (FTS_TABLE_CREATE);
            mDatabase.execSQL (BOOK_STATUS_TABLE_CREATE);

            //loadAndCreateIndex();
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            mDatabase = db;

            //TODO: temprory
            deleteTables();

            //loadAndCreateIndex();

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }

    }

    private boolean insertLineIndex (long bookId, long loc, String line) {

        synchronized (this) {
            if (!mDatabaseIndexing.isOpen())
                return false;

            ContentValues initialValues = new ContentValues();

            // initialValues.put(COL_DOCID, r1);//mLastDocID);
            initialValues.put(COL_BOOKID, bookId);
            //initialValues.put(COL_FILEPATH, fpath);
            //initialValues.put(COL_FILEPATH, (String)null);
            initialValues.put(COL_LOCATION, loc);
            initialValues.put(COL_TEXT, (String)null);

            long r1 = mDatabaseIndexing.insert(FTS_CONTENT_TABLE, null, initialValues);

            //initialValues.put(COL_DOCID, mLastDocID);
            //initialValues.put(COL_DOCID, "adfb/dftet.txt-" + String.valueOf(mLastDocID));
            initialValues.clear();

            initialValues.put(COL_DOCID, r1);//mLastDocID);
//                initialValues.put(COL_FILEID, 0);
//                initialValues.put(COL_FILEPATH, (String)null);
            initialValues.put(COL_BOOKID, 0);
            initialValues.put(COL_LOCATION, 0);

            String txt = getTokenizedText(line, null);
            initialValues.put(COL_TEXT, txt);

            long r2 = 0;

            mDatabaseIndexing.insert(FTS_VIRTUAL_TABLE, null, initialValues);

        }

        return true;
    }

    // start or resume the index creation
    private void loadAndCreateIndex() {

        loadBookIndexStatus();

        System.out.println ("Now loading index for " + orderedListBookIndex.size() + " books.");

        // for each new or updated file, build the index
        for (BookIndexStatus bkStatus : orderedListBookIndex) {
            if (mMarkIndexTaskCanceled) break;

            loadAndCreateIndex(bkStatus);
        }

        // load and create index for each new or update file

    }

    // load and create index in file path, starting from location pos
    private void loadAndCreateIndex(BookIndexStatus bkStatus) {

        //mLastDocID = 0;


        try {

            long size = bkStatus.size;
            float progress = 0f;

            ContentValues newPosValues = new ContentValues();

            String line;
            mIndexCreated = true;

            System.out.println("indexed:" + bkStatus.indexPos + "; total:" + size +
                    "; progress:" + String.valueOf(progress));


            Book book = bkStatus.book;

            BookModel model = null;
            try {
                FormatPlugin plugin = book.getPlugin();

                if (plugin instanceof BuiltinFormatPlugin) {
                    model = BookModel.createModel(book);
                    Log.v("model class", model.getClass().toString());
                }
            } catch (BookReadingException e) {
                e.printStackTrace();
                return;
            }

            if (model == null) return;

            ZLTextModel modelText = model.getTextModel();

            Log.v("text model class", modelText.getClass().toString());


            int n = modelText.getParagraphsNumber();
            Log.v("PARA NUM", String.valueOf(n));

            long bookId = book.getId();

            for (int i = 0; i < n; i++) {
                ZLTextParagraph p = modelText.getParagraph(i);

                ZLTextParagraph.EntryIterator iter = p.iterator();

                while (iter.next()) {

                    char c[] = iter.getTextData();
                    int start = iter.getTextOffset();
                    int len = iter.getTextLength();

                    if (c != null) {
                        String txt = new String(c, start, len);
                        Log.v("Text" + i, txt);

                        insertLineIndex(bookId, i, txt);

                        progress = (float) i / n;

                        System.out.println("indexed:" + i + "; total:" + n + "; progress:" + progress);

                        newPosValues.clear();
                        newPosValues.put(COL_INDEXPOS, i);
                        synchronized (this) {
                            mDatabaseIndexing.update(BOOK_STATUS_TABLE,
                                    newPosValues,
                                    COL_BOOKID + " = ?",
                                    new String[]{String.valueOf(bookId)}
                            );
                        }

                        break;
                    }

                    if (mMarkIndexTaskCanceled) break;
                }
                //Log.v("Para " + i, String.valueOf(start) + "-" + String.valueOf(len)); //new String (c));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private static final String[] COLUMNS =
            new String [] {
                    BaseColumns._ID,
                    COL_BOOKTITLE,
                    COL_TEXT};


    private static final String SELECTION = COL_TEXT + " MATCH ?";

    private static final String[] PROJECTION = new String[] {
            //           COL_DOCID + " AS " + BaseColumns._ID ,
            //           COL_FILEID,
            COL_FILEPATH,
            COL_LOCATION
            // COL_DOCID + " AS " + COL_LOCATION
    };

    private static final String RAW_SEARCH_SQL =
            "SELECT F." + COL_FILEPATH + ", L." + COL_LOCATION
                    + " FROM " + FTS_VIRTUAL_TABLE + " AS FTS"
                    + " INNER JOIN " + FTS_CONTENT_TABLE + " AS L"
                    + " INNER JOIN " + BOOK_STATUS_TABLE + " AS F"
                    + " ON FTS." + COL_DOCID + "=" + "L." + COL_ROWID
                    + " AND L." + COL_BOOKID + "=" + "F." + COL_ROWID
                    + " WHERE " + COL_TEXT + " MATCH ?";


    private Cursor searchText (String query) {

        if (query == null) return null;

        ArrayList<String> words = new ArrayList<String> ();

        String[] selectionArgs = new String[]{// query };
                getTokenizedText(query,words)}; //todo deal with "", AND, OR etc

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = null;

        synchronized (this) {
            SQLiteDatabase database = mDatabaseOpenHelper.getReadableDatabase();

            if (database.isOpen()) {
//                cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
//                      PROJECTION, SELECTION, selectionArgs, null, null, null);
                cursor = database.rawQuery(
                        RAW_SEARCH_SQL, selectionArgs);

                database.close();
            }
        }

        if (cursor == null) {
            return null;
        }  else {
            // Build a cursor, adding the real Text
            MatrixCursor cursor_new = new MatrixCursor(COLUMNS);

            cursor.moveToFirst();

            Object[] row = new Object[3];

            int resID=0;
            while (!cursor.isAfterLast()) {
                row[0] = ++resID;//cursor.getLong(0);  // ID
                String path = cursor.getString(0);
                long pos = cursor.getLong(1);  // Location

                int i = path.lastIndexOf('/');
                int j = path.lastIndexOf('.');

                if (i>0 & j>i)
                    row[1] = path.substring(i+1,j);
                else if (i>0 && j<0)
                    row[1] = path.substring(i+1);
                else
                    row[1] = path;

                String para = null; //getTextFromFile(path, pos);

                if (para!=null) {
                    String formatedTxt = getHighlightedText(para, words);

                    row[2] = formatedTxt;

                    cursor_new.addRow(row);
                }

                cursor.moveToNext();
            }

            return cursor_new;
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

    public static class HTMLTextCursorAdapter extends SimpleCursorAdapter {
        HTMLTextCursorAdapter(Context context, int layout, Cursor c,
                              String[] from, int[] to, int flags) {
            super (context, layout, c, from, to, flags);
        }

        @Override
        public void setViewText (TextView v, String text) {
            v.setText(Html.fromHtml(text));
        }
    }

    public static class TextQueryLoader extends CursorLoader {
        private String mTxt;
        FTSService mTable;

        TextQueryLoader (Context context, FTSService table, String txt) {
            super(context);
            mTable = table;
            mTxt = txt;
        }

        @Override
        public Cursor loadInBackground () {
            return mTable.searchText(mTxt);
        }
    }

    public void deleteDatabase() {
        // TODO
        synchronized (this) {
            mDatabaseOpenHelper.close();
        }

        File f = new File (mDir + DATABASE_FILE);

        f.delete();
    }

    public void closeDatabase() {

        synchronized (this) {
            mDatabaseOpenHelper.close();
        }
    }

}
