package org.geometerplus.android.fbreader.library;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.text.Html;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.book.BookEvent;
import org.geometerplus.fbreader.book.IBookCollection;
import org.geometerplus.fbreader.bookmodel.BookModel;
import org.geometerplus.fbreader.bookmodel.BookReadingException;
import org.geometerplus.fbreader.formats.BuiltinFormatPlugin;
import org.geometerplus.fbreader.formats.FormatPlugin;
import org.geometerplus.fbreader.fulltextsearch.SDCardSQLiteOpenHelper;
import org.geometerplus.fbreader.library.RootTree;
import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.text.model.ZLTextModel;
import org.geometerplus.zlibrary.text.model.ZLTextParagraph;

import java.io.File;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gordon on 12/27/15.
 */
public class FTSIndexDatabase {

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
                    COL_BOOKID + ", " +   // book ID in FBReader library
                    COL_LOCATION + ", " + // location in book
                    COL_TEXT +            // text of paragraph
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
                    + COL_LOCATION + ", "
                    + COL_TEXT
                    +")";


    private static final String BOOK_STATUS_TABLE_CREATE =
            "CREATE TABLE " + BOOK_STATUS_TABLE
                    + " ( "
                    + COL_BOOKID + ","         // book ID in FBReader library
                    + COL_FILEPATH + ","       // path of the file
                    + COL_FILESIZE + ","       // size of file
                    + COL_MODIFIED_TIME + ","  // book modified time
                    + COL_INDEXPOS + " ) ";    // last index build position

    private static final int DATABASE_VERSION = 1;

    // Database helper for the index database
    private DatabaseOpenHelper mDatabaseOpenHelper;
    ;

    private static final String FORMAT_HIGHTLIGHT_START = "<font color='red'>";
    private static final String FORMAT_HIGHTLIGHT_END = "</font>";
    private static final String DATABASE_FILE = "index.db";

    // An iterator to break the text
    private BreakIterator mBreakIter  = BreakIterator.getWordInstance();// = BreakIterator.getWordInstance(Locale.CHINA);
   /*
        mBreakIter = BreakIterator.getWordInstance(Locale.CHINESE);
    } */

    private boolean mIndexCreated = false;
    private long mLastDocID=0;

    private SQLiteDatabase mDatabaseIndexing;

    private String mDir;




    /**
     * Wrapper class of book indexing status
     */
    public static class BookIndexStatus {
        public long bookId;
        public String path;
        public int size;
        public long modifiedTime;
        public int indexPos;



        public boolean sameFileAs (BookIndexStatus other) {
            return ( bookId == other.bookId &&
                     path.equals(other.path) &&
                     size == other.size &&
                     modifiedTime == other.modifiedTime );
        }
    }

    private FTSIndexDatabase () {}

    private FTSIndexDatabase(String path, boolean writtable) {

        File folder;

        mDatabaseOpenHelper = new DatabaseOpenHelper(path, 1);

        if (path != null && (folder = new File(path)) != null) {
            mDatabaseIndexing = mDatabaseOpenHelper.getWritableDatabase();
        }
    }

    public static FTSIndexDatabase getWrittableIndexDatabase (String path) {
        return new FTSIndexDatabase(path, true);
    }

    public static FTSIndexDatabase getReadOnlyIndexDatabase (String path) {
        return new FTSIndexDatabase(path, true);
    }


    // add an entry in the indexing data base for a book status
    public void addBookStatus (BookIndexStatus bkStatus) {

        //private final String [] COLUMNS_BOOK_STATUS_TABLE = {
        //        COL_BOOKID, COL_FILEPATH, COL_FILESIZE, COL_MODIFIED_TIME, COL_INDEXPOS
        //};

        ContentValues values = new ContentValues();
        values.put (COL_BOOKID, bkStatus.bookId);
        values.put (COL_FILEPATH, bkStatus.path);
        values.put (COL_FILESIZE, bkStatus.size);
        values.put (COL_MODIFIED_TIME, bkStatus.modifiedTime);
        values.put(COL_INDEXPOS, bkStatus.indexPos);

        mDatabaseIndexing.insert(BOOK_STATUS_TABLE, null, values);

    }

    public void updateBookIdxPosition (long bookId, int indexpos) {
        ContentValues newPosValues = new ContentValues();

        newPosValues.clear();
        newPosValues.put(COL_INDEXPOS, indexpos); // the next position which not indexed yet
        synchronized (this) {
            int updated = mDatabaseIndexing.update(BOOK_STATUS_TABLE,
                    newPosValues,
                    COL_BOOKID + " = " + bookId, null);
        }
    }

    // Get
    public BookIndexStatus getBookStatus (long id) {

        final String [] COLUMNS_BOOK_STATUS_TABLE = {
                COL_BOOKID, COL_FILEPATH, COL_FILESIZE, COL_MODIFIED_TIME, COL_INDEXPOS
        };

        Cursor c;

        String selection = COL_BOOKID + " = " + id;
        synchronized (this) {
            c = mDatabaseIndexing.query(BOOK_STATUS_TABLE, COLUMNS_BOOK_STATUS_TABLE,
                    selection, null, null, null, null);
        }

        c.moveToFirst();

        if (!c.isAfterLast())  {

            BookIndexStatus status = new BookIndexStatus();
            status.bookId = id;
            status.path = c.getString(1);
            status.size = c.getInt(2);
            status.modifiedTime = c.getLong(3);
            status.indexPos = c.getInt(4);

            return status;
        } else {
            return null;
        }

    }

    /* Retrieve all words from a text */
    private synchronized String getWordsFromText(String txt, List<String> words) {
        mBreakIter.setText(txt);
        int start = mBreakIter.first();
        int end = mBreakIter.next();

        StringBuffer buf = new StringBuffer();
        if (words!=null) words.clear();

        while (end != BreakIterator.DONE) {
            String word = txt.substring(start,end).trim();
            if (word.length()!=0) {
                if (words!=null) words.add(word);
                buf.append(txt.substring(start, end));
                buf.append(" ");
            }
            start = end;
            end = mBreakIter.next();
        }

        if (buf.length() > 0)
            buf.deleteCharAt(buf.length()-1);

        return buf.toString();
    }

    /* Retrieve an HTML annotated text, highlighting all words from the list of words
     */
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


    /* Database helper to create/open and manager the indexing database
     */
    private class DatabaseOpenHelper extends SDCardSQLiteOpenHelper {

        //private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        public DatabaseOpenHelper (String path, int version) {
            super(path, DATABASE_FILE, null , version);
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
            mDatabase.execSQL (FTS_CONTENT_TABLE_CREATE + ";");
            mDatabase.execSQL (FTS_TABLE_CREATE+ ";");
            mDatabase.execSQL (BOOK_STATUS_TABLE_CREATE+ ";");

            //loadAndCreateIndex();
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            mDatabase = db;

            //TODO: temprory
            //deleteTables();
            //onCreate(db);

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

    // Insert a line for indexing
    public boolean insertLineIndex (long bookId, long loc, String line) {

        if (line == null || line.length()==0) return true;

        String txtToAdd = getWordsFromText(line, null);

        if (txtToAdd == null || txtToAdd.length()==0) return true;

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

            initialValues.put(COL_TEXT, txtToAdd);

            long r2 = 0;

            mDatabaseIndexing.insert(FTS_VIRTUAL_TABLE, null, initialValues);

        }

        return true;
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


    /* Performe a full-text search and return the cursor of results
     */
    private Cursor searchText (String query) {

        if (query == null) return null;

        ArrayList<String> words = new ArrayList<String> ();

        String[] selectionArgs = new String[]{// query };
                getWordsFromText(query, words)}; //todo deal with "", AND, OR etc

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
