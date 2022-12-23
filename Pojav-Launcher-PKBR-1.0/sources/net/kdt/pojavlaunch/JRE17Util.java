package net.kdt.pojavlaunch;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.IOException;
import net.kdt.pojavlaunch.JMinecraftVersionList;
import net.kdt.pojavlaunch.multirt.MultiRTUtils;
import net.kdt.pojavlaunch.multirt.Runtime;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.value.launcherprofiles.LauncherProfiles;
import net.kdt.pojavlaunch.value.launcherprofiles.MinecraftProfile;
import top.defaults.checkerboarddrawable.BuildConfig;

public class JRE17Util {
    public static final String NEW_JRE_NAME = "Internal-17";

    public static boolean checkInternalNewJre(Context ctx, AssetManager assetManager) {
        try {
            String read = Tools.read(assetManager.open("components/jre-new/version"));
            return true;
        } catch (IOException e) {
            if (MultiRTUtils.__internal__readBinpackVersion(NEW_JRE_NAME) != null) {
                return true;
            }
            return false;
        }
    }

    public static boolean isInternalNewJRE(String s_runtime) {
        Runtime runtime = MultiRTUtils.read(s_runtime);
        if (runtime == null) {
            return false;
        }
        return NEW_JRE_NAME.equals(runtime.name);
    }

    public static boolean installNewJreIfNeeded(Activity activity, JMinecraftVersionList.Version versionInfo) {
        if (versionInfo.javaVersion == null || versionInfo.javaVersion.component.equalsIgnoreCase("jre-legacy")) {
            return true;
        }
        LauncherProfiles.update();
        MinecraftProfile minecraftProfile = LauncherProfiles.mainProfileJson.profiles.get(LauncherPreferences.DEFAULT_PREF.getString(LauncherPreferences.PREF_KEY_CURRENT_PROFILE, BuildConfig.FLAVOR));
        String selectedRuntime = null;
        if (minecraftProfile.javaDir != null && minecraftProfile.javaDir.startsWith(Tools.LAUNCHERPROFILES_RTPREFIX)) {
            selectedRuntime = minecraftProfile.javaDir.substring(Tools.LAUNCHERPROFILES_RTPREFIX.length());
        }
        if ((selectedRuntime != null ? MultiRTUtils.read(selectedRuntime) : MultiRTUtils.read(LauncherPreferences.PREF_DEFAULT_RUNTIME)).javaVersion >= versionInfo.javaVersion.majorVersion) {
            return true;
        }
        String appropriateRuntime = MultiRTUtils.getNearestJreName(versionInfo.javaVersion.majorVersion);
        if (appropriateRuntime != null) {
            if (isInternalNewJRE(appropriateRuntime)) {
                checkInternalNewJre(activity.getApplicationContext(), activity.getAssets());
            }
            minecraftProfile.javaDir = Tools.LAUNCHERPROFILES_RTPREFIX + appropriateRuntime;
            LauncherProfiles.update();
        } else if (versionInfo.javaVersion.majorVersion > 17) {
            showRuntimeFail(activity, versionInfo);
            return false;
        } else if (!checkInternalNewJre(activity.getApplicationContext(), activity.getAssets())) {
            showRuntimeFail(activity, versionInfo);
            return false;
        } else {
            minecraftProfile.javaDir = Tools.LAUNCHERPROFILES_RTPREFIX + NEW_JRE_NAME;
            LauncherProfiles.update();
        }
        return true;
    }

    private static void showRuntimeFail(Activity activity, JMinecraftVersionList.Version verInfo) {
        Tools.dialogOnUiThread(activity, activity.getString(R.string.global_error), activity.getString(R.string.multirt_nocompartiblert, new Object[]{Integer.valueOf(verInfo.javaVersion.majorVersion)}));
    }
}
