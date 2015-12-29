/*
 * Copyright (C) 2010-2014 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.android.fbreader.library;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.fbreader.Paths;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.fulltextsearch.FTSIndexDatabase;

import org.geometerplus.fbreader.fulltextsearch.SearchHighlighter;
import org.geometerplus.fbreader.library.ExternalViewTree;
import org.geometerplus.fbreader.library.LibraryTree;
import org.geometerplus.zlibrary.ui.android.R;

public class FTSSearchActivity extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {


    // Adapter for a search result
    // Adapter to convert the text view to parse the HTML annoations
    public static class HTMLTextCursorAdapter extends SimpleCursorAdapter {

        final static String[] FROM_COLUMNS =
                {FTSIndexDatabase.FTS_RESULT_COL_TITLE,
                        FTSIndexDatabase.FTS_RESULT_COL_FORMATTED_TEXT};// {BaseColumns._ID};
        final static int[] TO_VIEWS = {R.id.searchedTextTitle, R.id.searchedTextParagraph};

        HTMLTextCursorAdapter (Context context) {
            super ( context,
                    R.layout.fts_result_item, null, FROM_COLUMNS, TO_VIEWS, 0);
        }

        @Override
        public void setViewText (TextView v, String text) {
            v.setText(Html.fromHtml(text));
        }
    }

    EditText mSearchText;
    ListView mSearchResults;
    ProgressBar mProgressBar;
    Button mButtonFTS;

    String searchedText;

    HTMLTextCursorAdapter mAdapter;

    FTSIndexDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fts_search);

        mProgressBar = (ProgressBar) findViewById (R.id.progressBarIndex);
        mProgressBar.setMax(100);

        mButtonFTS = (Button) findViewById (R.id.buttonFTS);

        mButtonFTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });

        mSearchResults = this.getListView();



        mAdapter = new HTMLTextCursorAdapter(this);

        this.setListAdapter(mAdapter);



        mSearchText = (EditText)findViewById(R.id.searchText);

        database = FTSIndexDatabase.getReadOnlyIndexDatabase(this, Paths.mainBookDirectory());

       // getLoaderManager().initLoader(0, null, this);

    }



    @Override
    protected void onDestroy() {

        if (database!=null)
            database.closeDatabase();

        super.onDestroy();
    }

    public void doSearch() {

        String text = mSearchText.getText().toString();

//        Cursor c = db.searchText( text );

        Bundle args = new Bundle();
        args.putString("query", text);

        searchedText = text;

        getLoaderManager().restartLoader(0, args, this);

    }

    public static class TextQueryLoader extends CursorLoader {
        private String mTxt;
        FTSIndexDatabase mDatabase;

        TextQueryLoader (Context context, FTSIndexDatabase database, String txt) {
            super (context);
            mTxt = txt;
            this.mDatabase = database;
        }

        @Override
        public Cursor loadInBackground () {

            Log.v ("Search", mTxt);
            return mDatabase.searchText(mTxt);

        }

    }

    // Called when a new Loader needs to be created    @Override
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        Log.v ("onCreateLoader", "id");

        return new TextQueryLoader(this, database, args.getString("query"));
    }

    // Called when a previously created loader has finished loading
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
    }

    // Called when a previously created loader is reset, making the data unavailable
    //public void onLoaderReset (Loader<D> loader)

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long rowId) {

        HTMLTextCursorAdapter adapter = (HTMLTextCursorAdapter)getListAdapter();
        Cursor cursor = adapter.getCursor();
        cursor.moveToPosition(position);

        long bookId = cursor.getLong(cursor.getColumnIndex(FTSIndexDatabase.FTS_RESULT_COL_BOOKID));
        Book book = database.getBookById(bookId);

        int para = cursor.getInt (cursor.getColumnIndex(FTSIndexDatabase.FTS_RESULT_COL_LOCATION));

        SearchHighlighter highlighter = new SearchHighlighter(searchedText, para);

        if (book != null) {
            FBReader.openBookActivityWithSearchHighlight(this, book, null, highlighter);
        }
    }

}
