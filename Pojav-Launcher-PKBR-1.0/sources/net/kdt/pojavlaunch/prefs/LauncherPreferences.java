package net.kdt.pojavlaunch.prefs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import java.util.Iterator;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.multirt.MultiRTUtils;
import net.kdt.pojavlaunch.utils.JREUtils;
import top.defaults.checkerboarddrawable.BuildConfig;

public class LauncherPreferences {
    public static SharedPreferences DEFAULT_PREF = null;
    public static boolean PREF_ARC_CAPES = false;
    public static float PREF_BUTTONSIZE = 100.0f;
    public static boolean PREF_CHECK_LIBRARY_SHA = true;
    public static int PREF_CONTROL_BOTTOM_OFFSET = 0;
    public static int PREF_CONTROL_LEFT_OFFSET = 0;
    public static int PREF_CONTROL_RIGHT_OFFSET = 0;
    public static int PREF_CONTROL_TOP_OFFSET = 0;
    public static String PREF_CUSTOM_JAVA_ARGS = null;
    public static String PREF_DEFAULTCTRL_PATH = Tools.CTRLDEF_FILE;
    public static String PREF_DEFAULT_RUNTIME = null;
    public static boolean PREF_DISABLE_GESTURES = false;
    public static boolean PREF_FORCE_ENGLISH = false;
    public static boolean PREF_HIDE_SIDEBAR = false;
    public static boolean PREF_IGNORE_NOTCH = false;
    public static final String PREF_KEY_CURRENT_PROFILE = "currentProfile";
    public static int PREF_LONGPRESS_TRIGGER = 300;
    public static float PREF_MOUSESCALE = 100.0f;
    public static float PREF_MOUSESPEED = 1.0f;
    public static int PREF_NOTCH_SIZE = 0;
    public static int PREF_RAM_ALLOCATION;
    public static String PREF_RENDERER = "opengles2";
    public static int PREF_SCALE_FACTOR = 100;
    public static boolean PREF_SUSTAINED_PERFORMANCE = false;
    public static boolean PREF_USE_ALTERNATE_SURFACE = true;
    public static String PREF_VERSION_REPOS = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";
    public static boolean PREF_VERTYPE_OLDALPHA = false;
    public static boolean PREF_VERTYPE_OLDBETA = false;
    public static boolean PREF_VERTYPE_RELEASE = true;
    public static boolean PREF_VERTYPE_SNAPSHOT = false;
    public static boolean PREF_VIRTUAL_MOUSE_START = false;

    public static void loadPreferences(Context ctx) {
        Tools.initContextConstants(ctx);
        PREF_RENDERER = DEFAULT_PREF.getString("renderer", "opengles2");
        PREF_BUTTONSIZE = (float) DEFAULT_PREF.getInt("buttonscale", 100);
        PREF_MOUSESCALE = (float) DEFAULT_PREF.getInt("mousescale", 100);
        PREF_MOUSESPEED = ((float) DEFAULT_PREF.getInt("mousespeed", 100)) / 100.0f;
        PREF_HIDE_SIDEBAR = DEFAULT_PREF.getBoolean("hideSidebar", false);
        PREF_IGNORE_NOTCH = DEFAULT_PREF.getBoolean("ignoreNotch", false);
        PREF_VERTYPE_RELEASE = DEFAULT_PREF.getBoolean("vertype_release", true);
        PREF_VERTYPE_SNAPSHOT = DEFAULT_PREF.getBoolean("vertype_snapshot", false);
        PREF_VERTYPE_OLDALPHA = DEFAULT_PREF.getBoolean("vertype_oldalpha", false);
        PREF_VERTYPE_OLDBETA = DEFAULT_PREF.getBoolean("vertype_oldbeta", false);
        PREF_LONGPRESS_TRIGGER = DEFAULT_PREF.getInt("timeLongPressTrigger", 300);
        PREF_DEFAULTCTRL_PATH = DEFAULT_PREF.getString("defaultCtrl", Tools.CTRLDEF_FILE);
        PREF_FORCE_ENGLISH = DEFAULT_PREF.getBoolean("force_english", false);
        PREF_CHECK_LIBRARY_SHA = DEFAULT_PREF.getBoolean("checkLibraries", true);
        PREF_DISABLE_GESTURES = DEFAULT_PREF.getBoolean("disableGestures", false);
        PREF_RAM_ALLOCATION = DEFAULT_PREF.getInt("allocation", findBestRAMAllocation(ctx));
        PREF_CUSTOM_JAVA_ARGS = DEFAULT_PREF.getString("javaArgs", BuildConfig.FLAVOR);
        PREF_CONTROL_TOP_OFFSET = DEFAULT_PREF.getInt("controlTopOffset", 0);
        PREF_CONTROL_RIGHT_OFFSET = DEFAULT_PREF.getInt("controlRightOffset", 0);
        PREF_CONTROL_BOTTOM_OFFSET = DEFAULT_PREF.getInt("controlBottomOffset", 0);
        PREF_CONTROL_LEFT_OFFSET = DEFAULT_PREF.getInt("controlLeftOffset", 0);
        PREF_SUSTAINED_PERFORMANCE = DEFAULT_PREF.getBoolean("sustainedPerformance", false);
        PREF_VIRTUAL_MOUSE_START = DEFAULT_PREF.getBoolean("mouse_start", false);
        PREF_ARC_CAPES = DEFAULT_PREF.getBoolean("arc_capes", false);
        PREF_USE_ALTERNATE_SURFACE = DEFAULT_PREF.getBoolean("alternate_surface", false);
        PREF_SCALE_FACTOR = DEFAULT_PREF.getInt("resolutionRatio", 100);
        Iterator<String> it = JREUtils.parseJavaArguments(PREF_CUSTOM_JAVA_ARGS).iterator();
        while (it.hasNext()) {
            String arg = it.next();
            if (arg.startsWith("-Dorg.lwjgl.opengl.libname=")) {
                DEFAULT_PREF.edit().putString("javaArgs", PREF_CUSTOM_JAVA_ARGS.replace(arg, BuildConfig.FLAVOR)).commit();
            }
        }
        if (DEFAULT_PREF.contains("defaultRuntime")) {
            PREF_DEFAULT_RUNTIME = DEFAULT_PREF.getString("defaultRuntime", BuildConfig.FLAVOR);
        } else if (MultiRTUtils.getRuntimes().size() < 1) {
            PREF_DEFAULT_RUNTIME = BuildConfig.FLAVOR;
        } else {
            PREF_DEFAULT_RUNTIME = MultiRTUtils.getRuntimes().get(0).name;
            DEFAULT_PREF.edit().putString("defaultRuntime", PREF_DEFAULT_RUNTIME).apply();
        }
    }

    private static int findBestRAMAllocation(Context ctx) {
        int deviceRam = Tools.getTotalDeviceMemory(ctx);
        if (deviceRam < 1024) {
            return 300;
        }
        if (deviceRam < 1536) {
            return 450;
        }
        if (deviceRam < 2048) {
            return 600;
        }
        if (deviceRam < 3064) {
            return 936;
        }
        if (deviceRam < 4096) {
            return 1148;
        }
        if (deviceRam < 6144) {
            return 1536;
        }
        return 2048;
    }

    public static void computeNotchSize(Activity activity) {
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                if (Build.VERSION.SDK_INT >= 31) {
                    Rect notchRect = activity.getWindowManager().getCurrentWindowMetrics().getWindowInsets().getDisplayCutout().getBoundingRects().get(0);
                    PREF_NOTCH_SIZE = Math.min(notchRect.width(), notchRect.height());
                    Tools.updateWindowSize(activity);
                    return;
                }
                Rect notchRect2 = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout().getBoundingRects().get(0);
                PREF_NOTCH_SIZE = Math.min(notchRect2.width(), notchRect2.height());
                Tools.updateWindowSize(activity);
            } catch (Exception e) {
                Log.i("NOTCH DETECTION", "No notch detected, or the device if in split screen mode");
                PREF_NOTCH_SIZE = -1;
            }
        }
    }
}
