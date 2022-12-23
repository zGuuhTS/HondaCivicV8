package net.kdt.pojavlaunch.tasks;

import android.util.Log;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import net.kdt.pojavlaunch.JMinecraftVersionList;
import net.kdt.pojavlaunch.PXBRApplication;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.tasks.AsyncVersionList;
import net.kdt.pojavlaunch.utils.DownloadUtils;

public class AsyncVersionList {

    public interface VersionDoneListener {
        void onVersionDone(JMinecraftVersionList jMinecraftVersionList);
    }

    public void getVersionList(VersionDoneListener listener) {
        PXBRApplication.sExecutorService.execute(new Runnable(listener) {
            public final /* synthetic */ AsyncVersionList.VersionDoneListener f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                AsyncVersionList.this.lambda$getVersionList$0$AsyncVersionList(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$getVersionList$0$AsyncVersionList(VersionDoneListener listener) {
        File versionFile = new File(Tools.DIR_DATA + "/version_list.json");
        JMinecraftVersionList versionList = null;
        try {
            if (!versionFile.exists() || System.currentTimeMillis() > versionFile.lastModified() + 86400000) {
                versionList = downloadVersionList(LauncherPreferences.PREF_VERSION_REPOS);
            }
        } catch (Exception e) {
            Log.e("AsyncVersionList", "Refreshing version list failed :" + e);
            e.printStackTrace();
        }
        if (versionList == null) {
            try {
                versionList = (JMinecraftVersionList) Tools.GLOBAL_GSON.fromJson(new JsonReader(new FileReader(versionFile)), (Type) JMinecraftVersionList.class);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        if (listener != null) {
            listener.onVersionDone(versionList);
        }
    }

    private JMinecraftVersionList downloadVersionList(String mirror) {
        JMinecraftVersionList list = null;
        try {
            Log.i("ExtVL", "Syncing to external: " + mirror);
            String jsonString = DownloadUtils.downloadString(mirror);
            list = (JMinecraftVersionList) Tools.GLOBAL_GSON.fromJson(jsonString, JMinecraftVersionList.class);
            Log.i("ExtVL", "Downloaded the version list, len=" + list.versions.length);
            FileOutputStream fos = new FileOutputStream(Tools.DIR_DATA + "/version_list.json");
            fos.write(jsonString.getBytes());
            fos.close();
            return list;
        } catch (IOException e) {
            Log.e("AsyncVersionList", e.toString());
            return list;
        }
    }
}
