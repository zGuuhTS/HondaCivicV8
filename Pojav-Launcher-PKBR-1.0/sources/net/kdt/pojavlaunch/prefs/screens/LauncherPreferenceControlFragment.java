package net.kdt.pojavlaunch.prefs.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.Preference;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.CustomControlsActivity;
import net.kdt.pojavlaunch.prefs.CustomSeekBarPreference;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class LauncherPreferenceControlFragment extends LauncherPreferenceFragment {
    public void onCreatePreferences(Bundle b, String str) {
        int longPressTrigger = LauncherPreferences.PREF_LONGPRESS_TRIGGER;
        int prefButtonSize = (int) LauncherPreferences.PREF_BUTTONSIZE;
        int mouseScale = (int) LauncherPreferences.PREF_MOUSESCALE;
        float mouseSpeed = LauncherPreferences.PREF_MOUSESPEED;
        addPreferencesFromResource(R.xml.pref_control);
        CustomSeekBarPreference seek2 = (CustomSeekBarPreference) findPreference("timeLongPressTrigger");
        seek2.setRange(100, 1000);
        seek2.setValue(longPressTrigger);
        seek2.setSuffix(" ms");
        Preference button = getPreferenceManager().findPreference("custom_control_button");
        if (button != null) {
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public final boolean onPreferenceClick(Preference preference) {
                    return LauncherPreferenceControlFragment.this.lambda$onCreatePreferences$0$LauncherPreferenceControlFragment(preference);
                }
            });
        }
        CustomSeekBarPreference seek3 = (CustomSeekBarPreference) findPreference("buttonscale");
        seek3.setRange(80, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        seek3.setValue(prefButtonSize);
        seek3.setSuffix(" %");
        CustomSeekBarPreference seek4 = (CustomSeekBarPreference) findPreference("mousescale");
        seek4.setRange(25, 300);
        seek4.setValue(mouseScale);
        seek4.setSuffix(" %");
        CustomSeekBarPreference seek6 = (CustomSeekBarPreference) findPreference("mousespeed");
        seek6.setRange(25, 300);
        seek6.setValue((int) (100.0f * mouseSpeed));
        seek6.setSuffix(" %");
        computeVisibility();
    }

    public /* synthetic */ boolean lambda$onCreatePreferences$0$LauncherPreferenceControlFragment(Preference arg0) {
        startActivity(new Intent(requireContext(), CustomControlsActivity.class));
        return true;
    }

    public void onSharedPreferenceChanged(SharedPreferences p, String s) {
        super.onSharedPreferenceChanged(p, s);
        computeVisibility();
    }

    private void computeVisibility() {
        ((CustomSeekBarPreference) findPreference("timeLongPressTrigger")).setVisible(!LauncherPreferences.PREF_DISABLE_GESTURES);
    }
}
