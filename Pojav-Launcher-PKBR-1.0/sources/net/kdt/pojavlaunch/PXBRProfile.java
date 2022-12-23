package net.kdt.pojavlaunch;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.IOException;
import net.kdt.pojavlaunch.value.MinecraftAccount;
import top.defaults.checkerboarddrawable.BuildConfig;

public class PXBRProfile {
    private static final String PROFILE_PREF = "pojav_profile";
    private static final String PROFILE_PREF_FILE = "file";
    public static String PROFILE_PREF_TEMP_CONTENT = "tempContent";

    public static SharedPreferences getPrefs(Context ctx) {
        return ctx.getSharedPreferences(PROFILE_PREF, 0);
    }

    public static MinecraftAccount getCurrentProfileContent(Context ctx, String profileName) throws JsonSyntaxException {
        return MinecraftAccount.load(profileName == null ? getCurrentProfileName(ctx) : profileName);
    }

    public static MinecraftAccount getTempProfileContent() {
        try {
            MinecraftAccount account = MinecraftAccount.parse(Tools.read(Tools.DIR_DATA + "/cache/tempacc.json"));
            if (account.accessToken == null) {
                account.accessToken = "0";
            }
            if (account.clientToken == null) {
                account.clientToken = "0";
            }
            if (account.profileId == null) {
                account.profileId = "00000000-0000-0000-0000-000000000000";
            }
            if (account.username == null) {
                account.username = "0";
            }
            if (account.selectedVersion == null) {
                account.selectedVersion = "1.7.10";
            }
            if (account.msaRefreshToken == null) {
                account.msaRefreshToken = "0";
            }
            return account;
        } catch (IOException e) {
            Log.e(MinecraftAccount.class.getName(), "Caught an exception while loading the temporary profile", e);
            return null;
        }
    }

    public static String getCurrentProfileName(Context ctx) {
        String name = getPrefs(ctx).getString(PROFILE_PREF_FILE, BuildConfig.FLAVOR);
        if (name.isEmpty() || !name.startsWith(Tools.DIR_ACCOUNT_NEW) || !name.endsWith(".json")) {
            return name;
        }
        String name2 = name.substring(0, name.length() - 5).replace(Tools.DIR_ACCOUNT_NEW, BuildConfig.FLAVOR).replace(".json", BuildConfig.FLAVOR);
        setCurrentProfile(ctx, name2);
        return name2;
    }

    public static boolean setCurrentProfile(Context ctx, Object obj) {
        SharedPreferences.Editor pref = getPrefs(ctx).edit();
        try {
            if (obj instanceof String) {
                pref.putString(PROFILE_PREF_FILE, (String) obj);
                return pref.commit();
            } else if (obj == null) {
                pref.putString(PROFILE_PREF_FILE, BuildConfig.FLAVOR);
                return pref.commit();
            } else {
                throw new IllegalArgumentException("Profile must be String.class or null");
            }
        } catch (Throwable th) {
        }
    }

    public static boolean isFileType(Context ctx) {
        return new File(Tools.DIR_ACCOUNT_NEW + "/" + getCurrentProfileName(ctx) + ".json").exists();
    }
}
