package org.geometerplus.fbreader.fulltextsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gordon on 12/28/15.
 */
public class SearchHighlighter {

    private int mPara;
    private List<String> phases;

    public SearchHighlighter (String searchTxt, int para) {
        mPara = para;

        phases = new ArrayList<String>();

        TextUtil.getSearchPhases(searchTxt, phases);
    }

    public SearchHighlighter (List<String> highlightWords, int para) {
        mPara = para;

        phases = highlightWords;
    }


    public int getParaNum () {return mPara;}

    public List<String> getPhases() {return phases;}

}
