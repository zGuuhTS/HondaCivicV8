package net.kdt.pojavlaunch.value;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import net.kdt.pojavlaunch.Tools;

public class PerVersionConfig {
    public static HashMap<String, VersionConfig> configMap;
    static File pvcFile;

    public static class VersionConfig {
        public String gamePath;
        public String jvmArgs;
        public String renderer;
        public String selectedRuntime;
    }

    public static void update() throws IOException {
        if (configMap == null) {
            File file = new File(Tools.DIR_GAME_HOME, "per-version-config.json");
            pvcFile = file;
            if (file.exists()) {
                try {
                    configMap = (HashMap) Tools.GLOBAL_GSON.fromJson(Tools.read(pvcFile.getAbsolutePath()), new TypeToken<HashMap<String, VersionConfig>>() {
                    }.getType());
                } catch (JsonSyntaxException ex) {
                    ex.printStackTrace();
                    configMap = new HashMap<>();
                }
            } else {
                configMap = new HashMap<>();
            }
        } else {
            Tools.write(pvcFile.getAbsolutePath(), Tools.GLOBAL_GSON.toJson((Object) configMap));
        }
    }

    public static boolean erase() {
        return new File(Tools.DIR_GAME_HOME, "per-version-config.json").delete();
    }

    public static boolean exists() {
        return new File(Tools.DIR_GAME_HOME, "per-version-config.json").exists();
    }
}
