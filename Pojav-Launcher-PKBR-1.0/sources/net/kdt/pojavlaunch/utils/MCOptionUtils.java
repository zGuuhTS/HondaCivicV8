package net.kdt.pojavlaunch.utils;

import android.os.Build;
import android.os.FileObserver;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.kdt.pojavlaunch.AWTInputEvent;
import net.kdt.pojavlaunch.Tools;
import org.lwjgl.glfw.CallbackBridge;
import top.defaults.checkerboarddrawable.BuildConfig;

public class MCOptionUtils {
    private static FileObserver sFileObserver;
    private static String sOptionFolderPath = null;
    private static final ArrayList<WeakReference<MCOptionListener>> sOptionListeners = new ArrayList<>();
    private static final HashMap<String, String> sParameterMap = new HashMap<>();

    public interface MCOptionListener {
        void onOptionChanged();
    }

    public static void load() {
        String str = sOptionFolderPath;
        if (str == null) {
            str = Tools.DIR_GAME_NEW;
        }
        load(str);
    }

    public static void load(String folderPath) {
        File optionFile = new File(folderPath + "/options.txt");
        if (!optionFile.exists()) {
            try {
                optionFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (sFileObserver == null || !Objects.equals(sOptionFolderPath, folderPath)) {
            sOptionFolderPath = folderPath;
            setupFileObserver();
        }
        sOptionFolderPath = folderPath;
        sParameterMap.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(optionFile));
            while (true) {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine != null) {
                    int firstColonIndex = line.indexOf(58);
                    if (firstColonIndex < 0) {
                        Log.w(Tools.APP_NAME, "No colon on line \"" + line + "\", skipping");
                    } else {
                        sParameterMap.put(line.substring(0, firstColonIndex), line.substring(firstColonIndex + 1));
                    }
                } else {
                    reader.close();
                    return;
                }
            }
        } catch (IOException e2) {
            Log.w(Tools.APP_NAME, "Could not load options.txt", e2);
        }
    }

    public static void set(String key, String value) {
        sParameterMap.put(key, value);
    }

    public static void set(String key, List<String> values) {
        sParameterMap.put(key, values.toString());
    }

    public static String get(String key) {
        return sParameterMap.get(key);
    }

    public static List<String> getAsList(String key) {
        String value = get(key);
        if (value == null) {
            return new ArrayList();
        }
        String value2 = value.replace("[", BuildConfig.FLAVOR).replace("]", BuildConfig.FLAVOR);
        if (value2.isEmpty()) {
            return new ArrayList();
        }
        return Arrays.asList(value2.split(","));
    }

    public static void save() {
        StringBuilder result = new StringBuilder();
        for (String key : sParameterMap.keySet()) {
            result.append(key).append(':').append(sParameterMap.get(key)).append(10);
        }
        try {
            sFileObserver.stopWatching();
            Tools.write(sOptionFolderPath + "/options.txt", result.toString());
            sFileObserver.startWatching();
        } catch (IOException e) {
            Log.w(Tools.APP_NAME, "Could not save options.txt", e);
        }
    }

    public static int getMcScale() {
        String str = get("guiScale");
        int guiScale = str == null ? 0 : Integer.parseInt(str);
        int scale = Math.max(Math.min(CallbackBridge.windowWidth / 320, CallbackBridge.windowHeight / AWTInputEvent.VK_ALPHANUMERIC), 1);
        if (scale < guiScale || guiScale == 0) {
            return scale;
        }
        return guiScale;
    }

    private static void setupFileObserver() {
        if (Build.VERSION.SDK_INT >= 29) {
            sFileObserver = new FileObserver(new File(sOptionFolderPath + "/options.txt"), 2) {
                public void onEvent(int i, String s) {
                    MCOptionUtils.load();
                    MCOptionUtils.notifyListeners();
                }
            };
        } else {
            sFileObserver = new FileObserver(sOptionFolderPath + "/options.txt", 2) {
                public void onEvent(int i, String s) {
                    MCOptionUtils.load();
                    MCOptionUtils.notifyListeners();
                }
            };
        }
        sFileObserver.startWatching();
    }

    public static void notifyListeners() {
        Iterator<WeakReference<MCOptionListener>> it = sOptionListeners.iterator();
        while (it.hasNext()) {
            MCOptionListener optionListener = (MCOptionListener) it.next().get();
            if (optionListener != null) {
                optionListener.onOptionChanged();
            }
        }
    }

    public static void addMCOptionListener(MCOptionListener listener) {
        sOptionListeners.add(new WeakReference(listener));
    }

    public static void removeMCOptionListener(MCOptionListener listener) {
        Iterator<WeakReference<MCOptionListener>> it = sOptionListeners.iterator();
        while (it.hasNext()) {
            WeakReference<MCOptionListener> weakReference = it.next();
            MCOptionListener optionListener = (MCOptionListener) weakReference.get();
            if (optionListener != null && optionListener == listener) {
                sOptionListeners.remove(weakReference);
                return;
            }
        }
    }
}
