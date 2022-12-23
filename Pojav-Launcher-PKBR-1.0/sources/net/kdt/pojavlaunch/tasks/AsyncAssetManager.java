package net.kdt.pojavlaunch.tasks;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.kdt.pojavlaunch.Architecture;
import net.kdt.pojavlaunch.Decompress;
import net.kdt.pojavlaunch.PXBRApplication;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.multirt.MultiRTUtils;
import org.apache.commons.p012io.FileUtils;

public class AsyncAssetManager {
    private AsyncAssetManager() {
    }

    public static boolean unpackRuntime(Context ctx, AssetManager am, boolean otherRuntimesAvailable) {
        String rt_version = null;
        String current_rt_version = MultiRTUtils.__internal__readBinpackVersion("Internal");
        try {
            rt_version = Tools.read(am.open("components/jre/version"));
        } catch (IOException e) {
            Log.e("JREAuto", "JRE was not included on this APK.", e);
        }
        if (current_rt_version == null && MultiRTUtils.getExactJreName(8) != null) {
            return true;
        }
        if (rt_version == null) {
            return otherRuntimesAvailable;
        }
        if (rt_version.equals(current_rt_version)) {
            return true;
        }
        PXBRApplication.sExecutorService.execute(new Runnable(ctx, am, rt_version) {
            public final /* synthetic */ Context f$0;
            public final /* synthetic */ AssetManager f$1;
            public final /* synthetic */ String f$2;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void run() {
                AsyncAssetManager.lambda$unpackRuntime$0(this.f$0, this.f$1, this.f$2);
            }
        });
        return true;
    }

    static /* synthetic */ void lambda$unpackRuntime$0(Context ctx, AssetManager am, String finalRt_version) {
        try {
            MultiRTUtils.installRuntimeNamedBinpack(ctx, am.open("components/jre/universal.tar.xz"), am.open("components/jre/bin-" + Architecture.archAsString(Tools.DEVICE_ARCHITECTURE) + ".tar.xz"), "Internal", finalRt_version);
            MultiRTUtils.postPrepare(ctx, "Internal");
        } catch (IOException e) {
            Log.e("JREAuto", "Internal JRE unpack failed", e);
        }
    }

    public static void unpackSingleFiles(Context ctx) {
        PXBRApplication.sExecutorService.execute(new Runnable(ctx) {
            public final /* synthetic */ Context f$0;

            {
                this.f$0 = r1;
            }

            public final void run() {
                AsyncAssetManager.lambda$unpackSingleFiles$1(this.f$0);
            }
        });
    }

    static /* synthetic */ void lambda$unpackSingleFiles$1(Context ctx) {
        try {
            Tools.copyAssetFile(ctx, "options.txt", Tools.DIR_GAME_NEW, false);
            Tools.copyAssetFile(ctx, "optionsof.txt", Tools.DIR_GAME_NEW, false);
            Tools.copyAssetFile(ctx, "default.json", Tools.CTRLMAP_PATH, false);
            Decompress.unzipFromAssets(ctx, "data.zip", Tools.DIR_GAME_NEW, false);
            Tools.copyAssetFile(ctx, "launcher_profiles.json", Tools.DIR_GAME_NEW, false);
            Tools.copyAssetFile(ctx, "resolv.conf", Tools.DIR_DATA, false);
            Tools.copyAssetFile(ctx, "arc_dns_injector.jar", Tools.DIR_DATA, false);
        } catch (IOException e) {
            Log.e("AsyncAssetManager", "Failed to unpack critical components !");
        }
    }

    public static void unpackComponents(Context ctx) {
        PXBRApplication.sExecutorService.execute(new Runnable(ctx) {
            public final /* synthetic */ Context f$0;

            {
                this.f$0 = r1;
            }

            public final void run() {
                AsyncAssetManager.lambda$unpackComponents$2(this.f$0);
            }
        });
    }

    static /* synthetic */ void lambda$unpackComponents$2(Context ctx) {
        try {
            unpackComponent(ctx, "caciocavallo", false);
            unpackComponent(ctx, "caciocavallo17", false);
            unpackComponent(ctx, "lwjgl3", false);
            unpackComponent(ctx, "security", true);
        } catch (IOException e) {
            Log.e("AsyncAssetManager", "Failed o unpack components !", e);
        }
    }

    private static boolean unpackComponent(Context ctx, String component, boolean privateDirectory) throws IOException {
        Context context = ctx;
        String str = component;
        AssetManager am = ctx.getAssets();
        String rootDir = privateDirectory ? Tools.DIR_DATA : Tools.DIR_GAME_HOME;
        File versionFile = new File(rootDir + "/" + str + "/version");
        InputStream is = am.open("components/" + str + "/version");
        int i = 0;
        boolean z = true;
        if (!versionFile.exists()) {
            if (versionFile.getParentFile().exists() && versionFile.getParentFile().isDirectory()) {
                FileUtils.deleteDirectory(versionFile.getParentFile());
            }
            versionFile.getParentFile().mkdir();
            Log.i("UnpackPrep", str + ": Pack was installed manually, or does not exist, unpacking new...");
            String[] fileList = am.list("components/" + str);
            int length = fileList.length;
            while (i < length) {
                Tools.copyAssetFile(context, "components/" + str + "/" + fileList[i], rootDir + "/" + str, true);
                i++;
            }
            AssetManager assetManager = am;
            String str2 = rootDir;
            return true;
        }
        if (!Tools.read(is).equals(Tools.read((InputStream) new FileInputStream(versionFile)))) {
            if (versionFile.getParentFile().exists() && versionFile.getParentFile().isDirectory()) {
                FileUtils.deleteDirectory(versionFile.getParentFile());
            }
            versionFile.getParentFile().mkdir();
            String[] fileList2 = am.list("components/" + str);
            int length2 = fileList2.length;
            while (i < length2) {
                Tools.copyAssetFile(context, "components/" + str + "/" + fileList2[i], rootDir + "/" + str, true);
                i++;
                z = true;
                am = am;
                rootDir = rootDir;
            }
            String str3 = rootDir;
            return z;
        }
        String str4 = rootDir;
        Log.i("UnpackPrep", str + ": Pack is up-to-date with the launcher, continuing...");
        return false;
    }
}
