package net.kdt.pojavlaunch.prefs.screens;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.preference.PreferenceFragmentCompat;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class LauncherPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setBackgroundColor(Color.parseColor("#232323"));
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreatePreferences(Bundle b, String str) {
        addPreferencesFromResource(R.xml.pref_main);
    }

    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    public void onSharedPreferenceChanged(SharedPreferences p, String s) {
        LauncherPreferences.loadPreferences(getContext());
    }
}
