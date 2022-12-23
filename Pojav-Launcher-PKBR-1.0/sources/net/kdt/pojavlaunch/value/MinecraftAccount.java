package net.kdt.pojavlaunch.value;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.kdt.pojavlaunch.Tools;
import org.apache.commons.p012io.IOUtils;

public class MinecraftAccount {
    public String accessToken = "0";
    public String clientToken = "0";
    public long expiresAt;
    public boolean isMicrosoft = false;
    public String msaRefreshToken = "0";
    public String profileId = "00000000-0000-0000-0000-000000000000";
    public String selectedVersion = "1.7.10";
    public String skinFaceBase64;
    public String username = "Steve";
    public String xuid;

    /* access modifiers changed from: package-private */
    public void updateSkinFace(String uuid) {
        try {
            File skinFile = File.createTempFile("skin", ".png", new File(Tools.DIR_DATA, "cache"));
            Tools.downloadFile("https://mc-heads.net/head/" + uuid + "/100", skinFile.getAbsolutePath());
            this.skinFaceBase64 = Base64.encodeToString(IOUtils.toByteArray((InputStream) new FileInputStream(skinFile)), 0);
            Log.i("SkinLoader", "Update skin face success");
        } catch (IOException e) {
            Log.w("SkinLoader", "Could not update skin face", e);
        }
    }

    public boolean isLocal() {
        return this.accessToken.equals("0");
    }

    public void updateSkinFace() {
        updateSkinFace(this.profileId);
    }

    public String save(String outPath) throws IOException {
        Tools.write(outPath, Tools.GLOBAL_GSON.toJson((Object) this));
        return this.username;
    }

    public String save() throws IOException {
        return save(Tools.DIR_ACCOUNT_NEW + "/" + this.username + ".json");
    }

    public static MinecraftAccount parse(String content) throws JsonSyntaxException {
        return (MinecraftAccount) Tools.GLOBAL_GSON.fromJson(content, MinecraftAccount.class);
    }

    public static MinecraftAccount load(String name) throws JsonSyntaxException {
        if (!accountExists(name)) {
            return null;
        }
        try {
            MinecraftAccount acc = parse(Tools.read(Tools.DIR_ACCOUNT_NEW + "/" + name + ".json"));
            if (acc.accessToken == null) {
                acc.accessToken = "0";
            }
            if (acc.clientToken == null) {
                acc.clientToken = "0";
            }
            if (acc.profileId == null) {
                acc.profileId = "00000000-0000-0000-0000-000000000000";
            }
            if (acc.username == null) {
                acc.username = "0";
            }
            if (acc.selectedVersion == null) {
                acc.selectedVersion = "1.7.10";
            }
            if (acc.msaRefreshToken == null) {
                acc.msaRefreshToken = "0";
            }
            String str = acc.skinFaceBase64;
            return acc;
        } catch (IOException e) {
            Log.e(MinecraftAccount.class.getName(), "Caught an exception while loading the profile", e);
            return null;
        }
    }

    public Bitmap getSkinFace() {
        String str = this.skinFaceBase64;
        if (str == null) {
            return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        }
        byte[] faceIconBytes = Base64.decode(str, 0);
        return BitmapFactory.decodeByteArray(faceIconBytes, 0, faceIconBytes.length);
    }

    private static boolean accountExists(String username2) {
        return new File(Tools.DIR_ACCOUNT_NEW + "/" + username2 + ".json").exists();
    }
}
