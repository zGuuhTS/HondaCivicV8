package net.kdt.pojavlaunch.prefs.screens;

import android.os.Bundle;
import androidx.preference.EditTextPreference;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.Architecture;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.prefs.CustomSeekBarPreference;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class LauncherPreferenceJavaFragment extends LauncherPreferenceFragment {
    public void onCreatePreferences(Bundle b, String str) {
        int maxRAM;
        int ramAllocation = LauncherPreferences.PREF_RAM_ALLOCATION;
        addPreferencesFromResource(R.xml.pref_java);
        int deviceRam = Tools.getTotalDeviceMemory(getContext());
        CustomSeekBarPreference seek7 = (CustomSeekBarPreference) findPreference("allocation");
        seek7.setMin(256);
        if (Architecture.is32BitsDevice()) {
            maxRAM = Math.min(1100, deviceRam);
        } else {
            maxRAM = deviceRam - (deviceRam < 3064 ? 800 : 1024);
        }
        seek7.setMax(maxRAM);
        seek7.setValue(ramAllocation);
        seek7.setSuffix(" MB");
        EditTextPreference editJVMArgs = (EditTextPreference) findPreference("javaArgs");
        if (editJVMArgs != null) {
            editJVMArgs.setOnBindEditTextListener(C0010xdd2e9ec1.INSTANCE);
        }
    }
}
