package org.geometerplus.android.fbreader;

import android.content.ActivityNotFoundException;
import android.content.Intent;

import org.geometerplus.android.fbreader.api.FBReaderIntents;
import org.geometerplus.android.fbreader.library.FTSSearchActivity;
import org.geometerplus.android.util.PackageUtil;

import org.geometerplus.fbreader.fbreader.FBReaderApp;

/**
 * Created by gordon on 11/22/15.
 */
public class FullTextSearchAction extends FBAndroidAction {
    private String myProfileName;

    FullTextSearchAction(FBReader baseActivity, FBReaderApp fbreader) {
        super(baseActivity, fbreader);
    }

    @Override
    protected void run(Object ... params) {
        final Intent externalIntent =
                new Intent(FBReaderIntents.Action.EXTERNAL_LIBRARY);
        final Intent internalIntent =
                new Intent(BaseActivity.getApplicationContext(),// LibraryActivity.class);
                        FTSSearchActivity.class);
        if (PackageUtil.canBeStarted(BaseActivity, externalIntent, true)) {
            try {
                startFTSActivity(externalIntent);
            } catch (ActivityNotFoundException e) {
                startFTSActivity(internalIntent);
            }
        } else {
            startFTSActivity(internalIntent);
        }
    }

    private void startFTSActivity(Intent intent) {
        if (Reader.Model != null) {
            FBReaderIntents.putBookExtra(intent, Reader.getCurrentBook());
        }
        OrientationUtil.startActivity(BaseActivity, intent);
    }

}
