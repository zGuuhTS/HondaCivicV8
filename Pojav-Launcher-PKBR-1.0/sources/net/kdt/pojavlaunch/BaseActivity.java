package net.kdt.pojavlaunch;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.utils.LocaleUtils;

public abstract class BaseActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleUtils.setLocale(this);
        Tools.setFullscreen(this, setFullscreen());
        Tools.updateWindowSize(this);
    }

    public boolean setFullscreen() {
        return true;
    }

    public void startActivity(Intent i) {
        super.startActivity(i);
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        Tools.ignoreNotch(LauncherPreferences.PREF_IGNORE_NOTCH, this);
    }
}
