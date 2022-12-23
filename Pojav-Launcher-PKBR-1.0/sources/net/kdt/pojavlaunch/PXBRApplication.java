package net.kdt.pojavlaunch;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import java.io.File;
import java.io.PrintStream;
import java.lang.Thread;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.kdt.pojavlaunch.tasks.AsyncAssetManager;
import net.kdt.pojavlaunch.utils.LocaleUtils;
import org.apache.commons.p012io.IOUtils;

public class PXBRApplication extends Application {
    public static String CRASH_REPORT_TAG = "PojavCrashReport";
    public static ExecutorService sExecutorService = new ThreadPoolExecutor(0, 4, 500, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
    private SharedPreferences firstLaunchPrefs;

    public void onCreate() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public final void uncaughtException(Thread thread, Throwable th) {
                PXBRApplication.this.lambda$onCreate$0$PXBRApplication(thread, th);
            }
        });
        try {
            super.onCreate();
            Tools.APP_NAME = "Pixelmon Brasil";
            Tools.DIR_DATA = getDir("files", 0).getParent();
            Tools.DIR_ACCOUNT_OLD = Tools.DIR_DATA + "/Users";
            Tools.DIR_ACCOUNT_NEW = Tools.DIR_DATA + "/accounts";
            Tools.DEVICE_ARCHITECTURE = Architecture.getDeviceArchitecture();
            if (Architecture.isx86Device() && Architecture.is32BitsDevice()) {
                String originalJNIDirectory = getApplicationInfo().nativeLibraryDir;
                getApplicationInfo().nativeLibraryDir = originalJNIDirectory.substring(0, originalJNIDirectory.lastIndexOf("/")).concat("/x86");
            }
            AsyncAssetManager.unpackRuntime(this, getAssets(), false);
            AsyncAssetManager.unpackComponents(this);
            AsyncAssetManager.unpackSingleFiles(this);
        } catch (Throwable throwable) {
            Intent ferrorIntent = new Intent(this, FatalErrorActivity.class);
            ferrorIntent.putExtra("throwable", throwable);
            startActivity(ferrorIntent);
        }
    }

    public /* synthetic */ void lambda$onCreate$0$PXBRApplication(Thread thread, Throwable th) {
        boolean storagePermAllowed = Build.VERSION.SDK_INT < 23 || Build.VERSION.SDK_INT >= 29 || ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        File crashFile = new File(storagePermAllowed ? Tools.DIR_GAME_HOME : Tools.DIR_DATA, "latestcrash.txt");
        try {
            crashFile.getParentFile().mkdirs();
            crashFile.createNewFile();
            PrintStream crashStream = new PrintStream(crashFile);
            crashStream.append("PojavLauncher crash report\n");
            crashStream.append(" - Time: " + DateFormat.getDateTimeInstance().format(new Date()) + IOUtils.LINE_SEPARATOR_UNIX);
            crashStream.append(" - Device: " + Build.PRODUCT + " " + Build.MODEL + IOUtils.LINE_SEPARATOR_UNIX);
            crashStream.append(" - Android version: " + Build.VERSION.RELEASE + IOUtils.LINE_SEPARATOR_UNIX);
            crashStream.append(" - Crash stack trace:\n");
            crashStream.append(" - Launcher version: 1.0\n");
            crashStream.append(Log.getStackTraceString(th));
            crashStream.close();
        } catch (Throwable throwable) {
            Log.e(CRASH_REPORT_TAG, " - Exception attempt saving crash stack trace:", throwable);
            Log.e(CRASH_REPORT_TAG, " - The crash stack trace was:", th);
        }
        FatalErrorActivity.showError(this, crashFile.getAbsolutePath(), storagePermAllowed, th);
        MainActivity.fullyExit();
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleUtils.setLocale(base));
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.setLocale(this);
    }
}
