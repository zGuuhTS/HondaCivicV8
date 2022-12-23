package net.kdt.pojavlaunch.prefs.screens;

import android.os.Bundle;
import com.p000br.pixelmonbrasil.debug.R;

public class LauncherPreferenceMiscellaneousFragment extends LauncherPreferenceFragment {
    public void onCreatePreferences(Bundle b, String str) {
        addPreferencesFromResource(R.xml.pref_misc);
    }
}
