package net.kdt.pojavlaunch.utils;

import android.content.Context;
import androidx.preference.PreferenceManager;
import java.util.Locale;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class LocaleUtils {
    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static Context setLocale(Context context) {
        if (LauncherPreferences.DEFAULT_PREF == null) {
            LauncherPreferences.DEFAULT_PREF = PreferenceManager.getDefaultSharedPreferences(context);
            LauncherPreferences.loadPreferences(context);
        }
        return context;
    }
}
