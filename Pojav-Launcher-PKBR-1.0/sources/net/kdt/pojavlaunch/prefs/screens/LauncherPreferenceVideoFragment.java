package net.kdt.pojavlaunch.prefs.screens;

import android.os.Build;
import android.os.Bundle;
import androidx.preference.SwitchPreference;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.prefs.CustomSeekBarPreference;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class LauncherPreferenceVideoFragment extends LauncherPreferenceFragment {
    public void onCreatePreferences(Bundle b, String str) {
        addPreferencesFromResource(R.xml.pref_video);
        boolean z = true;
        findPreference("ignoreNotch").setVisible(Build.VERSION.SDK_INT >= 28 && LauncherPreferences.PREF_NOTCH_SIZE > 0);
        CustomSeekBarPreference seek5 = (CustomSeekBarPreference) findPreference("resolutionRatio");
        seek5.setMin(25);
        seek5.setSuffix(" %");
        if (seek5.getValue() < 25) {
            seek5.setValue(100);
        }
        SwitchPreference sustainedPerfSwitch = (SwitchPreference) findPreference("sustainedPerformance");
        if (Build.VERSION.SDK_INT < 24) {
            z = false;
        }
        sustainedPerfSwitch.setVisible(z);
    }
}
