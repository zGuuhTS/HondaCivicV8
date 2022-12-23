package net.kdt.pojavlaunch;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.kdt.pojavlaunch.JMinecraftVersionList;
import net.kdt.pojavlaunch.multirt.MultiRTUtils;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.utils.DownloadUtils;
import net.kdt.pojavlaunch.utils.JREUtils;
import net.kdt.pojavlaunch.utils.JSONUtils;
import net.kdt.pojavlaunch.utils.OldVersionsUtils;
import net.kdt.pojavlaunch.value.DependentLibrary;
import net.kdt.pojavlaunch.value.MinecraftAccount;
import net.kdt.pojavlaunch.value.launcherprofiles.LauncherProfiles;
import net.kdt.pojavlaunch.value.launcherprofiles.MinecraftProfile;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.p012io.FileUtils;
import org.apache.commons.p012io.IOUtils;
import org.lwjgl.glfw.CallbackBridge;
import top.defaults.checkerboarddrawable.BuildConfig;

public final class Tools {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static String APP_NAME = "null";
    public static String ASSETS_PATH = null;
    public static String CTRLDEF_FILE = null;
    public static String CTRLMAP_PATH = null;
    public static int DEVICE_ARCHITECTURE = 0;
    public static String DIRNAME_HOME_JRE = "lib";
    public static String DIR_ACCOUNT_NEW = null;
    public static String DIR_ACCOUNT_OLD = null;
    public static String DIR_DATA = null;
    public static String DIR_GAME_HOME = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/PojavLauncher");
    public static String DIR_GAME_NEW = null;
    public static String DIR_GAME_OLD = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/.minecraft");
    public static String DIR_HOME_CRASH = null;
    public static String DIR_HOME_JRE = null;
    public static String DIR_HOME_LIBRARY = null;
    public static String DIR_HOME_VERSION = null;
    public static final boolean ENABLE_DEV_FEATURES = BuildConfig.DEBUG;
    public static final Gson GLOBAL_GSON = new GsonBuilder().setPrettyPrinting().create();
    public static String LAUNCHERPROFILES_RTPREFIX = "pojav://";
    public static final String LIBNAME_OPTIFINE = "optifine:OptiFine";
    public static String LOCAL_RENDERER = null;
    public static String MULTIRT_HOME = null;
    public static String NATIVE_LIB_DIR = null;
    public static String OBSOLETE_RESOURCES_PATH = null;
    public static final int RUN_MOD_INSTALLER = 2050;
    public static final String URL_HOME = "https://pojavlauncherteam.github.io/PojavLauncher";
    public static Boolean canChange = false;
    public static Boolean checked = false;
    public static DisplayMetrics currentDisplayMetrics;
    public static Boolean downloaded = false;
    private static boolean isClientFirst = false;
    /* access modifiers changed from: private */
    public static Handler mHandler = new Handler();
    public static Boolean startedCounter = false;
    public static Boolean stopDownload = false;

    public interface DownloaderFeedback {
        void updateProgress(int i, int i2);
    }

    public static void changeBackground() {
        startedCounter = true;
        new Runnable() {
            public void run() {
                Tools.canChange = true;
                Tools.mHandler.postDelayed(this, 5000);
            }
        }.run();
    }

    public static void initContextConstants(Context ctx) {
        DIR_DATA = ctx.getFilesDir().getParent();
        MULTIRT_HOME = DIR_DATA + "/runtimes";
        if (Build.VERSION.SDK_INT >= 29) {
            DIR_GAME_HOME = ctx.getExternalFilesDir((String) null).getAbsolutePath();
        } else {
            DIR_GAME_HOME = new File(Environment.getExternalStorageDirectory(), "games/PojavLauncher").getAbsolutePath();
        }
        DIR_GAME_NEW = DIR_GAME_HOME + "/.minecraft";
        DIR_HOME_VERSION = DIR_GAME_NEW + "/versions";
        DIR_HOME_LIBRARY = DIR_GAME_NEW + "/libraries";
        DIR_HOME_CRASH = DIR_GAME_NEW + "/crash-reports";
        ASSETS_PATH = DIR_GAME_NEW + "/assets";
        OBSOLETE_RESOURCES_PATH = DIR_GAME_NEW + "/resources";
        CTRLMAP_PATH = DIR_GAME_HOME + "/controlmap";
        CTRLDEF_FILE = DIR_GAME_HOME + "/controlmap/default.json";
        NATIVE_LIB_DIR = ctx.getApplicationInfo().nativeLibraryDir;
    }

    public static void launchMinecraft(Activity activity, MinecraftAccount minecraftAccount, MinecraftProfile minecraftProfile) throws Throwable {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ((ActivityManager) activity.getSystemService("activity")).getMemoryInfo(mi);
        if (((long) LauncherPreferences.PREF_RAM_ALLOCATION) > mi.availMem / FileUtils.ONE_MB) {
            Object memoryErrorLock = new Object();
            activity.runOnUiThread(new Runnable(activity, mi, memoryErrorLock) {
                public final /* synthetic */ Activity f$0;
                public final /* synthetic */ ActivityManager.MemoryInfo f$1;
                public final /* synthetic */ Object f$2;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void run() {
                    new AlertDialog.Builder(this.f$0).setMessage((CharSequence) this.f$0.getString(R.string.memory_warning_msg, new Object[]{Long.valueOf(this.f$1.availMem / FileUtils.ONE_MB), Integer.valueOf(LauncherPreferences.PREF_RAM_ALLOCATION)})).setPositiveButton(17039370, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener(this.f$2) {
                        public final /* synthetic */ Object f$0;

                        {
                            this.f$0 = r1;
                        }

                        public final void onClick(DialogInterface dialogInterface, int i) {
                            Tools.lambda$null$0(this.f$0, dialogInterface, i);
                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener(this.f$2) {
                        public final /* synthetic */ Object f$0;

                        {
                            this.f$0 = r1;
                        }

                        public final void onCancel(DialogInterface dialogInterface) {
                            Tools.lambda$null$1(this.f$0, dialogInterface);
                        }
                    }).show();
                }
            });
            synchronized (memoryErrorLock) {
                memoryErrorLock.wait();
            }
        }
        JMinecraftVersionList.Version versionInfo = getVersionInfo(minecraftProfile.lastVersionId);
        LauncherProfiles.update();
        String gamedirPath = getGameDirPath(minecraftProfile);
        if (minecraftProfile.javaArgs != null && !minecraftProfile.javaArgs.isEmpty()) {
            LauncherPreferences.PREF_CUSTOM_JAVA_ARGS = minecraftProfile.javaArgs;
        }
        disableSplash(gamedirPath);
        String[] launchArgs = getMinecraftClientArgs(minecraftAccount, versionInfo, gamedirPath);
        OldVersionsUtils.selectOpenGlVersion(versionInfo);
        String launchClassPath = generateLaunchClassPath(versionInfo, minecraftProfile.lastVersionId);
        List<String> javaArgList = new ArrayList<>();
        getCacioJavaArgs(javaArgList, JREUtils.jreReleaseList.get("JAVA_VERSION").equals("1.8.0"));
        if (versionInfo.logging != null) {
            String configFile = DIR_DATA + "/security/" + versionInfo.logging.client.file.f17id.replace("client", "log4j-rce-patch");
            if (!new File(configFile).exists()) {
                configFile = DIR_GAME_NEW + "/" + versionInfo.logging.client.file.f17id;
            }
            javaArgList.add("-Dlog4j.configurationFile=" + configFile);
        }
        javaArgList.addAll(Arrays.asList(getMinecraftJVMArgs(minecraftProfile.lastVersionId, gamedirPath)));
        javaArgList.add("-cp");
        javaArgList.add(getLWJGL3ClassPath() + ":" + launchClassPath);
        javaArgList.add(versionInfo.mainClass);
        javaArgList.addAll(Arrays.asList(launchArgs));
        JREUtils.launchJavaVM(activity, javaArgList);
    }

    static /* synthetic */ void lambda$null$0(Object memoryErrorLock, DialogInterface dialogInterface, int i) {
        synchronized (memoryErrorLock) {
            memoryErrorLock.notifyAll();
        }
    }

    static /* synthetic */ void lambda$null$1(Object memoryErrorLock, DialogInterface i) {
        synchronized (memoryErrorLock) {
            memoryErrorLock.notifyAll();
        }
    }

    public static String getGameDirPath(MinecraftProfile minecraftProfile) {
        if (minecraftProfile.gameDir == null) {
            return DIR_GAME_NEW;
        }
        if (minecraftProfile.gameDir.startsWith(LAUNCHERPROFILES_RTPREFIX)) {
            return minecraftProfile.gameDir.replace(LAUNCHERPROFILES_RTPREFIX, DIR_GAME_HOME + "/");
        }
        return DIR_GAME_HOME + minecraftProfile.gameDir;
    }

    public static void buildNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManagerCompat.from(context).createNotificationChannel(new NotificationChannel(context.getString(R.string.notif_channel_id), context.getString(R.string.notif_channel_name), 3));
        }
    }

    private static boolean mkdirs(String path) {
        return new File(path).mkdirs();
    }

    public static void disableSplash(String dir) {
        mkdirs(dir + "/config");
        File forgeSplashFile = new File(dir, "config/splash.properties");
        String forgeSplashContent = "enabled=true";
        try {
            if (forgeSplashFile.exists()) {
                forgeSplashContent = read(forgeSplashFile.getAbsolutePath());
            }
            if (forgeSplashContent.contains("enabled=true")) {
                write(forgeSplashFile.getAbsolutePath(), forgeSplashContent.replace("enabled=true", "enabled=false"));
            }
        } catch (IOException e) {
            Log.w(APP_NAME, "Could not disable Forge 1.12.2 and below splash screen!", e);
        }
    }

    public static void getCacioJavaArgs(List<String> javaArgList, boolean isJava8) {
        javaArgList.add("-Djava.awt.headless=false");
        javaArgList.add("-Dcacio.managed.screensize=720x600");
        javaArgList.add("-Dcacio.font.fontmanager=sun.awt.X11FontManager");
        javaArgList.add("-Dcacio.font.fontscaler=sun.font.FreetypeFontScaler");
        javaArgList.add("-Dswing.defaultlaf=javax.swing.plaf.metal.MetalLookAndFeel");
        if (isJava8) {
            javaArgList.add("-Dawt.toolkit=net.java.openjdk.cacio.ctc.CTCToolkit");
            javaArgList.add("-Djava.awt.graphicsenv=net.java.openjdk.cacio.ctc.CTCGraphicsEnvironment");
        } else {
            javaArgList.add("-Dawt.toolkit=com.github.caciocavallosilano.cacio.ctc.CTCToolkit");
            javaArgList.add("-Djava.awt.graphicsenv=com.github.caciocavallosilano.cacio.ctc.CTCGraphicsEnvironment");
            javaArgList.add("-Djava.system.class.loader=com.github.caciocavallosilano.cacio.ctc.CTCPreloadClassLoader");
            javaArgList.add("--add-exports=java.desktop/java.awt=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/java.awt.peer=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/sun.awt.image=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/sun.java2d=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/java.awt.dnd.peer=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/sun.awt=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/sun.awt.event=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/sun.awt.datatransfer=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.desktop/sun.font=ALL-UNNAMED");
            javaArgList.add("--add-exports=java.base/sun.security.action=ALL-UNNAMED");
            javaArgList.add("--add-opens=java.base/java.util=ALL-UNNAMED");
            javaArgList.add("--add-opens=java.desktop/java.awt=ALL-UNNAMED");
            javaArgList.add("--add-opens=java.desktop/sun.font=ALL-UNNAMED");
            javaArgList.add("--add-opens=java.desktop/sun.java2d=ALL-UNNAMED");
            javaArgList.add("--add-opens=java.base/java.lang.reflect=ALL-UNNAMED");
            javaArgList.add("--add-opens=java.base/java.net=ALL-UNNAMED");
        }
        StringBuilder cacioClasspath = new StringBuilder();
        cacioClasspath.append("-Xbootclasspath/" + (isJava8 ? "p" : "a"));
        File cacioDir = new File(DIR_GAME_HOME + "/caciocavallo" + (isJava8 ? BuildConfig.FLAVOR : "17"));
        if (cacioDir.exists() && cacioDir.isDirectory()) {
            for (File file : cacioDir.listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    cacioClasspath.append(":" + file.getAbsolutePath());
                }
            }
        }
        javaArgList.add(cacioClasspath.toString());
    }

    public static String[] getMinecraftJVMArgs(String versionName, String strGameDir) {
        JMinecraftVersionList.Version versionInfo = getVersionInfo(versionName, true);
        if (versionInfo.inheritsFrom == null || versionInfo.arguments == null || versionInfo.arguments.jvm == null) {
            return new String[0];
        }
        Map<String, String> varArgMap = new ArrayMap<>();
        varArgMap.put("classpath_separator", ":");
        varArgMap.put("library_directory", strGameDir + "/libraries");
        varArgMap.put("version_name", versionInfo.f17id);
        List<String> minecraftArgs = new ArrayList<>();
        if (versionInfo.arguments != null) {
            for (Object arg : versionInfo.arguments.jvm) {
                if (arg instanceof String) {
                    minecraftArgs.add((String) arg);
                }
            }
        }
        return JSONUtils.insertJSONValueList((String[]) minecraftArgs.toArray(new String[0]), varArgMap);
    }

    public static String[] getMinecraftClientArgs(MinecraftAccount profile, JMinecraftVersionList.Version versionInfo, String strGameDir) {
        String versionName = versionInfo.f17id;
        if (versionInfo.inheritsFrom != null) {
            versionName = versionInfo.inheritsFrom;
        }
        File gameDir = new File(strGameDir);
        gameDir.mkdirs();
        Map<String, String> varArgMap = new ArrayMap<>();
        varArgMap.put("auth_session", profile.accessToken);
        varArgMap.put("auth_access_token", profile.accessToken);
        varArgMap.put("auth_player_name", profile.username);
        varArgMap.put("auth_uuid", profile.profileId.replace("-", BuildConfig.FLAVOR));
        varArgMap.put("auth_xuid", profile.xuid);
        varArgMap.put("assets_root", ASSETS_PATH);
        varArgMap.put("assets_index_name", versionInfo.assets);
        varArgMap.put("game_assets", ASSETS_PATH);
        varArgMap.put("game_directory", gameDir.getAbsolutePath());
        varArgMap.put("user_properties", "{}");
        varArgMap.put("user_type", "mojang");
        varArgMap.put("version_name", versionName);
        varArgMap.put("version_type", versionInfo.type);
        List<String> minecraftArgs = new ArrayList<>();
        if (versionInfo.arguments != null) {
            for (Object arg : versionInfo.arguments.game) {
                if (arg instanceof String) {
                    minecraftArgs.add((String) arg);
                }
            }
        }
        return JSONUtils.insertJSONValueList(splitAndFilterEmpty(versionInfo.minecraftArguments == null ? fromStringArray((String[]) minecraftArgs.toArray(new String[0])) : versionInfo.minecraftArguments), varArgMap);
    }

    public static String fromStringArray(String[] strArr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                builder.append(" ");
            }
            builder.append(strArr[i]);
        }
        return builder.toString();
    }

    private static String[] splitAndFilterEmpty(String argStr) {
        List<String> strList = new ArrayList<>();
        for (String arg : argStr.split(" ")) {
            if (!arg.isEmpty()) {
                strList.add(arg);
            }
        }
        return (String[]) strList.toArray(new String[0]);
    }

    public static String artifactToPath(String name) {
        int idx = name.indexOf(":");
        if (idx != -1) {
            int idx2 = name.indexOf(":", idx + 1);
            if (idx2 != -1) {
                String group = name.substring(0, idx);
                String artifact = name.substring(idx + 1, idx2);
                String version = name.substring(idx2 + 1).replace(':', Soundex.SILENT_MARKER);
                return group.replaceAll("\\.", "/") + "/" + artifact + "/" + version + "/" + artifact + "-" + version + ".jar";
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static String getPatchedFile(String version) {
        return DIR_HOME_VERSION + "/" + version + "/" + version + ".jar";
    }

    private static String getLWJGL3ClassPath() {
        StringBuilder libStr = new StringBuilder();
        File lwjgl3Folder = new File(DIR_GAME_HOME, "lwjgl3");
        if (lwjgl3Folder.exists()) {
            for (File file : lwjgl3Folder.listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    libStr.append(file.getAbsolutePath() + ":");
                }
            }
        }
        libStr.setLength(libStr.length() - 1);
        return libStr.toString();
    }

    public static String generateLaunchClassPath(JMinecraftVersionList.Version info, String actualname) {
        StringBuilder libStr = new StringBuilder();
        String[] classpath = generateLibClasspath(info);
        if (isClientFirst) {
            libStr.append(getPatchedFile(actualname));
        }
        for (String perJar : classpath) {
            if (!new File(perJar).exists()) {
                Log.d(APP_NAME, "Ignored non-exists file: " + perJar);
            } else {
                String str = ":";
                StringBuilder append = new StringBuilder().append(isClientFirst ? str : BuildConfig.FLAVOR).append(perJar);
                if (isClientFirst) {
                    str = BuildConfig.FLAVOR;
                }
                libStr.append(append.append(str).toString());
            }
        }
        if (!isClientFirst) {
            libStr.append(getPatchedFile(actualname));
        }
        return libStr.toString();
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT < 24 || (!activity.isInMultiWindowMode() && !activity.isInPictureInPictureMode())) {
            if (Build.VERSION.SDK_INT >= 30) {
                activity.getDisplay().getRealMetrics(displayMetrics);
            } else if (Build.VERSION.SDK_INT >= 28) {
                activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
            } else {
                activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            }
            if (!LauncherPreferences.PREF_IGNORE_NOTCH) {
                displayMetrics.widthPixels -= LauncherPreferences.PREF_NOTCH_SIZE;
            }
        } else {
            displayMetrics = activity.getResources().getDisplayMetrics();
        }
        currentDisplayMetrics = displayMetrics;
        return displayMetrics;
    }

    public static void setFullscreen(Activity activity, boolean fullscreen) {
        View decorView = activity.getWindow().getDecorView();
        View.OnSystemUiVisibilityChangeListener visibilityChangeListener = new View.OnSystemUiVisibilityChangeListener(fullscreen, decorView) {
            public final /* synthetic */ boolean f$0;
            public final /* synthetic */ View f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void onSystemUiVisibilityChange(int i) {
                Tools.lambda$setFullscreen$3(this.f$0, this.f$1, i);
            }
        };
        decorView.setOnSystemUiVisibilityChangeListener(visibilityChangeListener);
        visibilityChangeListener.onSystemUiVisibilityChange(decorView.getSystemUiVisibility());
    }

    static /* synthetic */ void lambda$setFullscreen$3(boolean fullscreen, View decorView, int visibility) {
        if (!fullscreen) {
            decorView.setSystemUiVisibility(0);
        } else if ((visibility & 4) == 0) {
            decorView.setSystemUiVisibility(5894);
        }
    }

    public static void updateWindowSize(Activity activity) {
        DisplayMetrics displayMetrics = getDisplayMetrics(activity);
        currentDisplayMetrics = displayMetrics;
        CallbackBridge.physicalWidth = displayMetrics.widthPixels;
        CallbackBridge.physicalHeight = currentDisplayMetrics.heightPixels;
    }

    public static float dpToPx(float dp) {
        return currentDisplayMetrics.density * dp;
    }

    public static float pxToDp(float px) {
        return px / currentDisplayMetrics.density;
    }

    public static void copyAssetFile(Context ctx, String fileName, String output, boolean overwrite) throws IOException {
        copyAssetFile(ctx, fileName, output, new File(fileName).getName(), overwrite);
    }

    public static void copyAssetFile(Context ctx, String fileName, String output, String outputName, boolean overwrite) throws IOException {
        File parentFolder = new File(output);
        if (!parentFolder.exists()) {
            parentFolder.mkdirs();
        }
        File destinationFile = new File(output, outputName);
        if (!destinationFile.exists() || overwrite) {
            IOUtils.copy(ctx.getAssets().open(fileName), (OutputStream) new FileOutputStream(destinationFile));
        }
    }

    public static void showError(Context ctx, Throwable e) {
        showError(ctx, e, false);
    }

    public static void showError(Context ctx, Throwable e, boolean exitIfOk) {
        showError(ctx, R.string.global_error, e, exitIfOk, false);
    }

    public static void showError(Context ctx, int titleId, Throwable e, boolean exitIfOk) {
        showError(ctx, titleId, e, exitIfOk, false);
    }

    /* access modifiers changed from: private */
    public static void showError(Context ctx, int titleId, Throwable e, boolean exitIfOk, boolean showMore) {
        e.printStackTrace();
        Runnable runnable = new Runnable(showMore, e, ctx, titleId, exitIfOk) {
            public final /* synthetic */ boolean f$0;
            public final /* synthetic */ Throwable f$1;
            public final /* synthetic */ Context f$2;
            public final /* synthetic */ int f$3;
            public final /* synthetic */ boolean f$4;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
            }

            public final void run() {
                Tools.lambda$showError$7(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4);
            }
        };
        if (ctx instanceof Activity) {
            ((Activity) ctx).runOnUiThread(runnable);
        } else {
            runnable.run();
        }
    }

    static /* synthetic */ void lambda$showError$7(boolean showMore, Throwable e, Context ctx, int titleId, boolean exitIfOk) {
        try {
            new AlertDialog.Builder(ctx).setTitle(titleId).setMessage(showMore ? Log.getStackTraceString(e) : e.getMessage()).setPositiveButton(17039370, new DialogInterface.OnClickListener(exitIfOk, ctx) {
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ Context f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void onClick(DialogInterface dialogInterface, int i) {
                    Tools.lambda$null$4(this.f$0, this.f$1, dialogInterface, i);
                }
            }).setNegativeButton(showMore ? R.string.error_show_less : R.string.error_show_more, new DialogInterface.OnClickListener(ctx, titleId, e, exitIfOk, showMore) {
                public final /* synthetic */ Context f$0;
                public final /* synthetic */ int f$1;
                public final /* synthetic */ Throwable f$2;
                public final /* synthetic */ boolean f$3;
                public final /* synthetic */ boolean f$4;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                    this.f$4 = r5;
                }

                public final void onClick(DialogInterface dialogInterface, int i) {
                    Tools.showError(this.f$0, this.f$1, this.f$2, this.f$3, !this.f$4);
                }
            }).setNeutralButton(17039361, new DialogInterface.OnClickListener(ctx, e, exitIfOk) {
                public final /* synthetic */ Context f$0;
                public final /* synthetic */ Throwable f$1;
                public final /* synthetic */ boolean f$2;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                    this.f$2 = r3;
                }

                public final void onClick(DialogInterface dialogInterface, int i) {
                    Tools.lambda$null$6(this.f$0, this.f$1, this.f$2, dialogInterface, i);
                }
            }).setCancelable(!exitIfOk).show();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    static /* synthetic */ void lambda$null$4(boolean exitIfOk, Context ctx, DialogInterface p1, int p2) {
        if (!exitIfOk) {
            return;
        }
        if (ctx instanceof MainActivity) {
            MainActivity.fullyExit();
        } else if (ctx instanceof Activity) {
            ((Activity) ctx).finish();
        }
    }

    static /* synthetic */ void lambda$null$6(Context ctx, Throwable e, boolean exitIfOk, DialogInterface p1, int p2) {
        ((ClipboardManager) ctx.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("error", Log.getStackTraceString(e)));
        if (!exitIfOk) {
            return;
        }
        if (ctx instanceof MainActivity) {
            MainActivity.fullyExit();
        } else {
            ((Activity) ctx).finish();
        }
    }

    public static void dialogOnUiThread(Activity activity, CharSequence title, CharSequence message) {
        activity.runOnUiThread(new Runnable(activity, title, message) {
            public final /* synthetic */ Activity f$0;
            public final /* synthetic */ CharSequence f$1;
            public final /* synthetic */ CharSequence f$2;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                new AlertDialog.Builder(this.f$0).setTitle(this.f$1).setMessage(this.f$2).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).show();
            }
        });
    }

    public static void moveRecursive(String from, String to) {
        moveRecursive(new File(from), new File(to));
    }

    public static void moveRecursive(File from, File to) {
        File toFrom = new File(to, from.getName());
        try {
            if (from.isDirectory()) {
                for (File child : from.listFiles()) {
                    moveRecursive(child, toFrom);
                }
            }
        } finally {
            from.getParentFile().mkdirs();
            from.renameTo(toFrom);
        }
    }

    public static void openURL(Activity act, String url) {
        act.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    private static boolean checkRules(JMinecraftVersionList.Arguments.ArgValue.ArgRules[] rules) {
        if (rules == null) {
            return true;
        }
        for (JMinecraftVersionList.Arguments.ArgValue.ArgRules rule : rules) {
            if (rule.action.equals("allow") && rule.f16os != null && rule.f16os.name.equals("osx")) {
                return false;
            }
        }
        return true;
    }

    public static String[] generateLibClasspath(JMinecraftVersionList.Version info) {
        List<String> libDir = new ArrayList<>();
        for (DependentLibrary libItem : info.libraries) {
            if (checkRules(libItem.rules)) {
                libDir.add(DIR_HOME_LIBRARY + "/" + artifactToPath(libItem.name));
            }
        }
        return (String[]) libDir.toArray(new String[0]);
    }

    public static JMinecraftVersionList.Version getVersionInfo(String versionName) {
        return getVersionInfo(versionName, false);
    }

    public static JMinecraftVersionList.Version getVersionInfo(String versionName, boolean skipInheriting) {
        JMinecraftVersionList.Version customVer;
        JMinecraftVersionList.Version inheritsVer;
        List<DependentLibrary> libList;
        DependentLibrary[] dependentLibraryArr;
        String str = versionName;
        try {
            customVer = (JMinecraftVersionList.Version) GLOBAL_GSON.fromJson(read(DIR_HOME_VERSION + "/" + str + "/" + str + ".json"), JMinecraftVersionList.Version.class);
            int i = 0;
            for (DependentLibrary lib : customVer.libraries) {
                if (lib.name.startsWith(LIBNAME_OPTIFINE)) {
                    customVer.optifineLib = lib;
                }
            }
            if (skipInheriting || customVer.inheritsFrom == null || customVer.inheritsFrom.equals(customVer.f17id)) {
                return customVer;
            }
            inheritsVer = (JMinecraftVersionList.Version) GLOBAL_GSON.fromJson(read(DIR_HOME_VERSION + "/" + customVer.inheritsFrom + "/" + customVer.inheritsFrom + ".json"), JMinecraftVersionList.Version.class);
            insertSafety(inheritsVer, customVer, "assetIndex", "assets", "id", "mainClass", "minecraftArguments", "optifineLib", "releaseTime", "time", "type");
            libList = new ArrayList<>(Arrays.asList(inheritsVer.libraries));
            DependentLibrary[] dependentLibraryArr2 = customVer.libraries;
            int length = dependentLibraryArr2.length;
            int i2 = 0;
            while (i2 < length) {
                DependentLibrary lib2 = dependentLibraryArr2[i2];
                String libName = lib2.name.substring(i, lib2.name.lastIndexOf(":"));
                int i3 = 0;
                while (true) {
                    if (i3 >= libList.size()) {
                        dependentLibraryArr = dependentLibraryArr2;
                        libList.add(lib2);
                        break;
                    }
                    DependentLibrary libAdded = libList.get(i3);
                    String libAddedName = libAdded.name.substring(i, libAdded.name.lastIndexOf(":"));
                    if (libAddedName.equals(libName)) {
                        dependentLibraryArr = dependentLibraryArr2;
                        Log.d(APP_NAME, "Library " + libName + ": Replaced version " + libName.substring(libName.lastIndexOf(":") + 1) + " with " + libAddedName.substring(libAddedName.lastIndexOf(":") + 1));
                        libList.set(i3, lib2);
                        break;
                    }
                    DependentLibrary[] dependentLibraryArr3 = dependentLibraryArr2;
                    i3++;
                    i = 0;
                }
                i2++;
                dependentLibraryArr2 = dependentLibraryArr;
                i = 0;
            }
            inheritsVer.libraries = (DependentLibrary[]) libList.toArray(new DependentLibrary[0]);
            if (!(inheritsVer.arguments == null || customVer.arguments == null)) {
                List totalArgList = new ArrayList();
                totalArgList.addAll(Arrays.asList(inheritsVer.arguments.game));
                int nskip = 0;
                for (int i4 = 0; i4 < customVer.arguments.game.length; i4++) {
                    if (nskip > 0) {
                        nskip--;
                    } else {
                        Object perCustomArg = customVer.arguments.game[i4];
                        if (perCustomArg instanceof String) {
                            String perCustomArgStr = (String) perCustomArg;
                            if (!perCustomArgStr.startsWith("--") || !totalArgList.contains(perCustomArgStr)) {
                                totalArgList.add(perCustomArgStr);
                            } else {
                                Object perCustomArg2 = customVer.arguments.game[i4 + 1];
                                if ((perCustomArg2 instanceof String) && !((String) perCustomArg2).startsWith("--")) {
                                    nskip++;
                                }
                            }
                        } else if (!totalArgList.contains(perCustomArg)) {
                            totalArgList.add(perCustomArg);
                        }
                    }
                }
                inheritsVer.arguments.game = totalArgList.toArray(new Object[0]);
            }
            return inheritsVer;
        } catch (IOException e) {
            throw new RuntimeException("Can't find the source version for " + str + " (req version=" + customVer.inheritsFrom + ")");
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        } catch (Throwable th) {
            inheritsVer.libraries = (DependentLibrary[]) libList.toArray(new DependentLibrary[0]);
            throw th;
        }
    }

    private static void insertSafety(JMinecraftVersionList.Version targetVer, JMinecraftVersionList.Version fromVer, String... keyArr) {
        int length = keyArr.length;
        int i = 0;
        while (i < length) {
            String key = keyArr[i];
            try {
                Object value = fromVer.getClass().getDeclaredField(key).get(fromVer);
                if ((!(value instanceof String) || ((String) value).isEmpty()) && value == null) {
                    i++;
                } else {
                    targetVer.getClass().getDeclaredField(key).set(targetVer, value);
                    i++;
                }
            } catch (Throwable th) {
                Log.w(APP_NAME, "Unable to insert " + key + "=" + null, th);
            }
        }
    }

    public static String convertStream(InputStream inputStream) throws IOException {
        return convertStream(inputStream, Charset.forName("UTF-8"));
    }

    public static String convertStream(InputStream inputStream, Charset charset) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] buf = new byte[512];
        while (true) {
            int read = inputStream.read(buf);
            int len = read;
            if (read == -1) {
                return out.toString();
            }
            out.append(new String(buf, 0, len, charset));
        }
    }

    public static File lastFileModified(String dir) {
        File[] files = new File(dir).listFiles($$Lambda$Tools$cjLmXmfGtL0mNLn_N_w3rQTUYMI.INSTANCE);
        if (files == null) {
            return null;
        }
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }

    public static String read(InputStream is) throws IOException {
        StringBuilder out = new StringBuilder();
        byte[] buf = new byte[512];
        while (true) {
            int read = is.read(buf);
            int len = read;
            if (read == -1) {
                return out.toString();
            }
            out.append(new String(buf, 0, len));
        }
    }

    public static String read(String path) throws IOException {
        return read((InputStream) new FileInputStream(path));
    }

    public static void write(String path, byte[] content) throws IOException {
        File outPath = new File(path);
        outPath.getParentFile().mkdirs();
        outPath.createNewFile();
        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(path));
        fos.write(content, 0, content.length);
        fos.close();
    }

    public static void write(String path, String content) throws IOException {
        write(path, content.getBytes());
    }

    public static byte[] loadFromAssetToByte(Context ctx, String inFile) {
        byte[] buffer = null;
        try {
            InputStream stream = ctx.getAssets().open(inFile);
            buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return buffer;
        }
    }

    public static void downloadFile(String urlInput, String nameOutput) throws IOException {
        DownloadUtils.downloadFile(urlInput, new File(nameOutput));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0021, code lost:
        if (r2 != null) goto L_0x0023;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002c, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002f, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean compareSHA1(java.io.File r5, java.lang.String r6) {
        /*
            r0 = 1
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0030 }
            r1.<init>(r5)     // Catch:{ IOException -> 0x0030 }
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x001e }
            byte[] r3 = org.apache.commons.codec.digest.DigestUtils.sha1((java.io.InputStream) r1)     // Catch:{ all -> 0x001e }
            char[] r3 = org.apache.commons.codec.binary.Hex.encodeHex((byte[]) r3)     // Catch:{ all -> 0x001e }
            r2.<init>(r3)     // Catch:{ all -> 0x001e }
            r1.close()     // Catch:{ IOException -> 0x0030 }
            if (r6 == 0) goto L_0x001d
            boolean r0 = r2.equalsIgnoreCase(r6)     // Catch:{ IOException -> 0x0030 }
            return r0
        L_0x001d:
            return r0
        L_0x001e:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0020 }
        L_0x0020:
            r3 = move-exception
            if (r2 == 0) goto L_0x002c
            r1.close()     // Catch:{ all -> 0x0027 }
            goto L_0x002f
        L_0x0027:
            r4 = move-exception
            r2.addSuppressed(r4)     // Catch:{ IOException -> 0x0030 }
            goto L_0x002f
        L_0x002c:
            r1.close()     // Catch:{ IOException -> 0x0030 }
        L_0x002f:
            throw r3     // Catch:{ IOException -> 0x0030 }
        L_0x0030:
            r1 = move-exception
            java.lang.String r2 = "SHA1"
            java.lang.String r3 = "Fake-matching a hash due to a read error"
            android.util.Log.i(r2, r3, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.Tools.compareSHA1(java.io.File, java.lang.String):boolean");
    }

    public static void ignoreNotch(boolean shouldIgnore, Activity ctx) {
        if (Build.VERSION.SDK_INT >= 28) {
            if (shouldIgnore) {
                ctx.getWindow().getAttributes().layoutInDisplayCutoutMode = 1;
            } else {
                ctx.getWindow().getAttributes().layoutInDisplayCutoutMode = 2;
            }
            ctx.getWindow().setFlags(256, 256);
            updateWindowSize(ctx);
        }
    }

    public static int getTotalDeviceMemory(Context ctx) {
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) ctx.getSystemService("activity")).getMemoryInfo(memInfo);
        return (int) (memInfo.totalMem / FileUtils.ONE_MB);
    }

    public static int getFreeDeviceMemory(Context ctx) {
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        ((ActivityManager) ctx.getSystemService("activity")).getMemoryInfo(memInfo);
        return (int) (memInfo.availMem / FileUtils.ONE_MB);
    }

    public static int getDisplayFriendlyRes(int displaySideRes, float scaling) {
        int displaySideRes2 = (int) (((float) displaySideRes) * scaling);
        if (displaySideRes2 % 2 != 0) {
            return displaySideRes2 + 1;
        }
        return displaySideRes2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0030, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0031, code lost:
        if (r1 != null) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
        if (r2 != null) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0041, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getFileName(android.content.Context r8, android.net.Uri r9) {
        /*
            r0 = 0
            java.lang.String r1 = r9.getScheme()
            java.lang.String r2 = "content"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0047
            android.content.ContentResolver r2 = r8.getContentResolver()
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r3 = r9
            android.database.Cursor r1 = r2.query(r3, r4, r5, r6, r7)
            if (r1 == 0) goto L_0x0042
            boolean r2 = r1.moveToFirst()     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x0042
            java.lang.String r2 = "_display_name"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ all -> 0x002e }
            java.lang.String r2 = r1.getString(r2)     // Catch:{ all -> 0x002e }
            r0 = r2
            goto L_0x0042
        L_0x002e:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0030 }
        L_0x0030:
            r3 = move-exception
            if (r1 == 0) goto L_0x0041
            if (r2 == 0) goto L_0x003e
            r1.close()     // Catch:{ all -> 0x0039 }
            goto L_0x0041
        L_0x0039:
            r4 = move-exception
            r2.addSuppressed(r4)
            goto L_0x0041
        L_0x003e:
            r1.close()
        L_0x0041:
            throw r3
        L_0x0042:
            if (r1 == 0) goto L_0x0047
            r1.close()
        L_0x0047:
            if (r0 != 0) goto L_0x005c
            java.lang.String r0 = r9.getPath()
            r1 = 47
            int r1 = r0.lastIndexOf(r1)
            r2 = -1
            if (r1 == r2) goto L_0x005c
            int r2 = r1 + 1
            java.lang.String r0 = r0.substring(r2)
        L_0x005c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.Tools.getFileName(android.content.Context, android.net.Uri):java.lang.String");
    }

    public static void swapFragment(FragmentActivity fragmentActivity, Class<? extends Fragment> fragmentClass, String fragmentTag, boolean addCurrentToBackstack, Bundle bundle) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.container_fragment, fragmentClass, bundle, fragmentTag);
        if (addCurrentToBackstack) {
            transaction.addToBackStack((String) null);
        }
        transaction.commit();
    }

    public static void removeCurrentFragment(FragmentActivity fragmentActivity) {
        fragmentActivity.getSupportFragmentManager().popBackStackImmediate();
    }

    private static ProgressDialog getWaitingDialog(Context ctx) {
        ProgressDialog barrier = new ProgressDialog(ctx);
        barrier.setMessage(ctx.getString(R.string.global_waiting));
        barrier.setProgressStyle(0);
        barrier.setCancelable(false);
        barrier.show();
        return barrier;
    }

    public static void installRuntimeFromUri(Activity activity, Uri uri) {
        PXBRApplication.sExecutorService.execute(new Runnable(activity, uri) {
            public final /* synthetic */ Activity f$0;
            public final /* synthetic */ Uri f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void run() {
                Tools.lambda$installRuntimeFromUri$9(this.f$0, this.f$1);
            }
        });
    }

    static /* synthetic */ void lambda$installRuntimeFromUri$9(Activity activity, Uri uri) {
        try {
            String name = getFileName(activity, uri);
            MultiRTUtils.installRuntimeNamed(activity.getApplicationContext(), activity.getContentResolver().openInputStream(uri), name);
            MultiRTUtils.postPrepare(activity.getApplicationContext(), name);
        } catch (IOException e) {
            showError(activity, e);
        }
    }
}
