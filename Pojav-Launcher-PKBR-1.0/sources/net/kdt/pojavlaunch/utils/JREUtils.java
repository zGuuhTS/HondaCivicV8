package net.kdt.pojavlaunch.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.system.ErrnoException;
import android.system.Os;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;
import com.oracle.dalvik.VMLauncher;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import net.kdt.pojavlaunch.Architecture;
import net.kdt.pojavlaunch.Logger;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import org.lwjgl.glfw.CallbackBridge;
import top.defaults.checkerboarddrawable.BuildConfig;

public class JREUtils {
    private static final int EGL_OPENGL_ES2_BIT = 4;
    private static final int EGL_OPENGL_ES3_BIT_KHR = 64;
    private static final int EGL_OPENGL_ES_BIT = 1;
    public static String LD_LIBRARY_PATH;
    public static Map<String, String> jreReleaseList;
    public static String jvmLibraryPath;

    public static native int chdir(String str);

    public static native boolean dlopen(String str);

    public static native void logToLogger(Logger logger);

    public static native void releaseBridgeWindow();

    public static native int[] renderAWTScreenFrame();

    public static native void setLdLibraryPath(String str);

    public static native void setupBridgeWindow(Object obj);

    public static native void setupExitTrap(Context context);

    private JREUtils() {
    }

    public static String findInLdLibPath(String libName) {
        if (Os.getenv("LD_LIBRARY_PATH") == null) {
            try {
                String str = LD_LIBRARY_PATH;
                if (str != null) {
                    Os.setenv("LD_LIBRARY_PATH", str, true);
                }
            } catch (ErrnoException e) {
                e.printStackTrace();
            }
            return libName;
        }
        for (String libPath : Os.getenv("LD_LIBRARY_PATH").split(":")) {
            File f = new File(libPath, libName);
            if (f.exists() && f.isFile()) {
                return f.getAbsolutePath();
            }
        }
        return libName;
    }

    public static ArrayList<File> locateLibs(File path) {
        ArrayList<File> returnValue = new ArrayList<>();
        File[] list = path.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isFile() && f.getName().endsWith(".so")) {
                    returnValue.add(f);
                } else if (f.isDirectory()) {
                    returnValue.addAll(locateLibs(f));
                }
            }
        }
        return returnValue;
    }

    public static void initJavaRuntime() {
        dlopen(findInLdLibPath("libjli.so"));
        if (!dlopen("libjvm.so")) {
            Log.w("DynamicLoader", "Failed to load with no path, trying with full path");
            dlopen(jvmLibraryPath + "/libjvm.so");
        }
        dlopen(findInLdLibPath("libverify.so"));
        dlopen(findInLdLibPath("libjava.so"));
        dlopen(findInLdLibPath("libnet.so"));
        dlopen(findInLdLibPath("libnio.so"));
        dlopen(findInLdLibPath("libawt.so"));
        dlopen(findInLdLibPath("libawt_headless.so"));
        dlopen(findInLdLibPath("libfreetype.so"));
        dlopen(findInLdLibPath("libfontmanager.so"));
        Iterator<File> it = locateLibs(new File(Tools.DIR_HOME_JRE + "/" + Tools.DIRNAME_HOME_JRE)).iterator();
        while (it.hasNext()) {
            dlopen(it.next().getAbsolutePath());
        }
        dlopen(Tools.NATIVE_LIB_DIR + "/libopenal.so");
    }

    public static Map<String, String> readJREReleaseProperties() throws IOException {
        return readJREReleaseProperties(Tools.DIR_HOME_JRE);
    }

    public static Map<String, String> readJREReleaseProperties(String name) throws IOException {
        Map<String, String> jreReleaseMap = new ArrayMap<>();
        if (!name.contains("/")) {
            name = Tools.MULTIRT_HOME + "/" + name;
        }
        BufferedReader jreReleaseReader = new BufferedReader(new FileReader(name + "/release"));
        while (true) {
            String readLine = jreReleaseReader.readLine();
            String currLine = readLine;
            if (readLine == null) {
                jreReleaseReader.close();
                return jreReleaseMap;
            } else if (!currLine.isEmpty() || currLine.contains("=")) {
                String[] keyValue = currLine.split("=");
                jreReleaseMap.put(keyValue[0], keyValue[1].replace("\"", BuildConfig.FLAVOR));
            }
        }
    }

    public static void redirectAndPrintJRELog() {
        Log.v("jrelog", "Log starts here");
        logToLogger(Logger.getInstance());
        new Thread(new Runnable() {
            int failTime = 0;
            ProcessBuilder logcatPb;

            public void run() {
                try {
                    if (this.logcatPb == null) {
                        this.logcatPb = new ProcessBuilder(new String[0]).command(new String[]{"logcat", "-v", "brief", "-s", "jrelog:I", "LIBGL:I"}).redirectErrorStream(true);
                    }
                    Log.i("jrelog-logcat", "Clearing logcat");
                    new ProcessBuilder(new String[0]).command(new String[]{"logcat", "-c"}).redirectErrorStream(true).start();
                    Log.i("jrelog-logcat", "Starting logcat");
                    Process p = this.logcatPb.start();
                    byte[] buf = new byte[1024];
                    while (true) {
                        int read = p.getInputStream().read(buf);
                        int len = read;
                        if (read == -1) {
                            break;
                        }
                        Logger.getInstance().appendToLog(new String(buf, 0, len));
                    }
                    if (p.waitFor() != 0) {
                        Log.e("jrelog-logcat", "Logcat exited with code " + p.exitValue());
                        this.failTime++;
                        Log.i("jrelog-logcat", (this.failTime <= 10 ? "Restarting logcat" : "Too many restart fails") + " (attempt " + this.failTime + "/10");
                        if (this.failTime <= 10) {
                            run();
                        } else {
                            Logger.getInstance().appendToLog("ERROR: Unable to get more log.");
                        }
                    }
                } catch (Throwable e) {
                    Log.e("jrelog-logcat", "Exception on logging thread", e);
                    Logger.getInstance().appendToLog("Exception on logging thread:\n" + Log.getStackTraceString(e));
                }
            }
        }).start();
        Log.i("jrelog-logcat", "Logcat thread started");
    }

    public static void relocateLibPath() throws IOException {
        String JRE_ARCHITECTURE = readJREReleaseProperties().get("OS_ARCH");
        if (Architecture.archAsInt(JRE_ARCHITECTURE) == Architecture.ARCH_X86) {
            JRE_ARCHITECTURE = "i386/i486/i586";
        }
        for (String arch : JRE_ARCHITECTURE.split("/")) {
            File f = new File(Tools.DIR_HOME_JRE, "lib/" + arch);
            if (f.exists() && f.isDirectory()) {
                Tools.DIRNAME_HOME_JRE = "lib/" + arch;
            }
        }
        String libName = Architecture.is64BitsDevice() ? "lib64" : "lib";
        StringBuilder ldLibraryPath = new StringBuilder();
        ldLibraryPath.append(Tools.DIR_HOME_JRE + "/" + Tools.DIRNAME_HOME_JRE + "/jli:" + Tools.DIR_HOME_JRE + "/" + Tools.DIRNAME_HOME_JRE + ":");
        ldLibraryPath.append("/system/" + libName + ":/vendor/" + libName + ":/vendor/" + libName + "/hw:" + Tools.NATIVE_LIB_DIR);
        LD_LIBRARY_PATH = ldLibraryPath.toString();
    }

    public static void setJavaEnvironment(Activity activity) throws Throwable {
        Map<String, String> envMap = new ArrayMap<>();
        envMap.put("POJAV_NATIVEDIR", Tools.NATIVE_LIB_DIR);
        envMap.put("JAVA_HOME", Tools.DIR_HOME_JRE);
        envMap.put("HOME", Tools.DIR_GAME_NEW);
        envMap.put("TMPDIR", activity.getCacheDir().getAbsolutePath());
        envMap.put("LIBGL_MIPMAP", "3");
        envMap.put("LIBGL_NOINTOVLHACK", "1");
        envMap.put("LIBGL_NORMALIZE", "1");
        envMap.put("LIBGL_ES", (String) ExtraCore.getValue(ExtraConstants.OPEN_GL_VERSION));
        envMap.put("MESA_GLSL_CACHE_DIR", activity.getCacheDir().getAbsolutePath());
        if (Tools.LOCAL_RENDERER != null) {
            envMap.put("MESA_GL_VERSION_OVERRIDE", Tools.LOCAL_RENDERER.equals("opengles3_virgl") ? "4.3" : "4.6");
            envMap.put("MESA_GLSL_VERSION_OVERRIDE", Tools.LOCAL_RENDERER.equals("opengles3_virgl") ? "430" : "460");
        }
        envMap.put("force_glsl_extensions_warn", "true");
        envMap.put("allow_higher_compat_version", "true");
        envMap.put("allow_glsl_extension_directive_midshader", "true");
        envMap.put("MESA_LOADER_DRIVER_OVERRIDE", "zink");
        envMap.put("VTEST_SOCKET_NAME", activity.getCacheDir().getAbsolutePath() + "/.virgl_test");
        envMap.put("LD_LIBRARY_PATH", LD_LIBRARY_PATH);
        envMap.put("PATH", Tools.DIR_HOME_JRE + "/bin:" + Os.getenv("PATH"));
        envMap.put("REGAL_GL_VENDOR", "Android");
        envMap.put("REGAL_GL_RENDERER", "Regal");
        envMap.put("REGAL_GL_VERSION", "4.5");
        if (Tools.LOCAL_RENDERER != null) {
            envMap.put("POJAV_RENDERER", Tools.LOCAL_RENDERER);
            if (Tools.LOCAL_RENDERER.equals("opengles3_desktopgl_angle_vulkan")) {
                envMap.put("LIBGL_ES", "3");
                envMap.put("POJAVEXEC_EGL", "libEGL_angle.so");
            }
        }
        envMap.put("AWTSTUB_WIDTH", Integer.toString(CallbackBridge.windowWidth > 0 ? CallbackBridge.windowWidth : CallbackBridge.physicalWidth));
        envMap.put("AWTSTUB_HEIGHT", Integer.toString(CallbackBridge.windowHeight > 0 ? CallbackBridge.windowHeight : CallbackBridge.physicalHeight));
        File customEnvFile = new File(Tools.DIR_GAME_HOME, "custom_env.txt");
        if (customEnvFile.exists() && customEnvFile.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(customEnvFile));
            while (true) {
                String readLine = reader.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                int index = line.indexOf("=");
                envMap.put(line.substring(0, index), line.substring(index + 1));
            }
            reader.close();
        }
        if (!envMap.containsKey("LIBGL_ES") && Tools.LOCAL_RENDERER != null) {
            int glesMajor = getDetectedVersion();
            Log.i("glesDetect", "GLES version detected: " + glesMajor);
            if (glesMajor < 3) {
                envMap.put("LIBGL_ES", "2");
            } else if (Tools.LOCAL_RENDERER.startsWith("opengles")) {
                envMap.put("LIBGL_ES", Tools.LOCAL_RENDERER.replace("opengles", BuildConfig.FLAVOR).replace("_5", BuildConfig.FLAVOR));
            } else {
                envMap.put("LIBGL_ES", "3");
            }
        }
        for (Map.Entry<String, String> env : envMap.entrySet()) {
            Logger.getInstance().appendToLog("Added custom env: " + env.getKey() + "=" + env.getValue());
            try {
                Os.setenv(env.getKey(), env.getValue(), true);
            } catch (NullPointerException exception) {
                Log.e("JREUtils", exception.toString());
            }
        }
        jvmLibraryPath = Tools.DIR_HOME_JRE + "/" + Tools.DIRNAME_HOME_JRE + "/" + (new File(new StringBuilder().append(Tools.DIR_HOME_JRE).append("/").append(Tools.DIRNAME_HOME_JRE).append("/server/libjvm.so").toString()).exists() ? "server" : "client");
        Log.d("DynamicLoader", "Base LD_LIBRARY_PATH: " + LD_LIBRARY_PATH);
        Log.d("DynamicLoader", "Internal LD_LIBRARY_PATH: " + jvmLibraryPath + ":" + LD_LIBRARY_PATH);
        setLdLibraryPath(jvmLibraryPath + ":" + LD_LIBRARY_PATH);
    }

    public static int launchJavaVM(Activity activity, List<String> JVMArgs) throws Throwable {
        relocateLibPath();
        setJavaEnvironment(activity);
        String graphicsLib = loadGraphicsLibrary();
        List<String> userArgs = getJavaArgs(activity);
        purgeArg(userArgs, "-Xms");
        purgeArg(userArgs, "-Xmx");
        purgeArg(userArgs, "-d32");
        purgeArg(userArgs, "-d64");
        purgeArg(userArgs, "-Xint");
        purgeArg(userArgs, "-Dorg.lwjgl.opengl.libname");
        userArgs.add("-Xms" + LauncherPreferences.PREF_RAM_ALLOCATION + "M");
        userArgs.add("-Xmx" + LauncherPreferences.PREF_RAM_ALLOCATION + "M");
        if (Tools.LOCAL_RENDERER != null) {
            userArgs.add("-Dorg.lwjgl.opengl.libname=" + graphicsLib);
        }
        userArgs.addAll(JVMArgs);
        activity.runOnUiThread(new Runnable(activity) {
            public final /* synthetic */ Activity f$0;

            {
                this.f$0 = r1;
            }

            public final void run() {
                Toast.makeText(this.f$0, this.f$0.getString(R.string.autoram_info_msg, new Object[]{Integer.valueOf(LauncherPreferences.PREF_RAM_ALLOCATION)}), 0).show();
            }
        });
        System.out.println(JVMArgs);
        initJavaRuntime();
        setupExitTrap(activity.getApplication());
        chdir(Tools.DIR_GAME_NEW);
        userArgs.add(0, "java");
        int exitCode = VMLauncher.launchJVM((String[]) userArgs.toArray(new String[0]));
        Logger.getInstance().appendToLog("Java Exit code: " + exitCode);
        if (exitCode != 0) {
            activity.runOnUiThread(new Runnable(activity, exitCode) {
                public final /* synthetic */ Activity f$0;
                public final /* synthetic */ int f$1;

                {
                    this.f$0 = r1;
                    this.f$1 = r2;
                }

                public final void run() {
                    JREUtils.lambda$launchJavaVM$2(this.f$0, this.f$1);
                }
            });
        }
        return exitCode;
    }

    static /* synthetic */ void lambda$launchJavaVM$2(Activity activity, int exitCode) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setMessage(activity.getString(R.string.mcn_exit_title, new Object[]{Integer.valueOf(exitCode)}));
        dialog.setPositiveButton(17039370, $$Lambda$JREUtils$pecwo3YHhgBhiw7P0__PcrBRv7E.INSTANCE);
        dialog.show();
    }

    public static List<String> getJavaArgs(Context ctx) {
        List<String> userArguments = parseJavaArguments(LauncherPreferences.PREF_CUSTOM_JAVA_ARGS);
        ArrayList<String> overridableArguments = new ArrayList<>(Arrays.asList(new String[]{"-Djava.home=" + Tools.DIR_HOME_JRE, "-Djava.io.tmpdir=" + ctx.getCacheDir().getAbsolutePath(), "-Duser.home=" + new File(Tools.DIR_GAME_NEW).getParent(), "-Duser.language=" + System.getProperty("user.language"), "-Dos.name=Linux", "-Dos.version=Android-" + Build.VERSION.RELEASE, "-Dpojav.path.minecraft=" + Tools.DIR_GAME_NEW, "-Dpojav.path.private.account=" + Tools.DIR_ACCOUNT_NEW, "-Dglfwstub.windowWidth=" + Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.widthPixels, ((float) LauncherPreferences.PREF_SCALE_FACTOR) / 100.0f), "-Dglfwstub.windowHeight=" + Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.heightPixels, ((float) LauncherPreferences.PREF_SCALE_FACTOR) / 100.0f), "-Dglfwstub.initEgl=false", "-Dext.net.resolvPath=" + new File(Tools.DIR_DATA, "resolv.conf").getAbsolutePath(), "-Dlog4j2.formatMsgNoLookups=true", "-Dnet.minecraft.clientmodname=" + Tools.APP_NAME, "-Dfml.earlyprogresswindow=false"}));
        if (LauncherPreferences.PREF_ARC_CAPES) {
            overridableArguments.add("-javaagent:" + new File(Tools.DIR_DATA, "arc_dns_injector.jar").getAbsolutePath() + "=23.95.137.176");
        }
        List<String> additionalArguments = new ArrayList<>();
        Iterator<String> it = overridableArguments.iterator();
        while (it.hasNext()) {
            String arg = it.next();
            String strippedArg = arg.substring(0, arg.indexOf(61));
            boolean add = true;
            Iterator<String> it2 = userArguments.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (it2.next().startsWith(strippedArg)) {
                        add = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (add) {
                additionalArguments.add(arg);
            } else {
                Log.i("ArgProcessor", "Arg skipped: " + arg);
            }
        }
        userArguments.addAll(additionalArguments);
        return userArguments;
    }

    public static ArrayList<String> parseJavaArguments(String args) {
        ArrayList<String> parsedArguments = new ArrayList<>(0);
        String args2 = args.trim().replace(" ", BuildConfig.FLAVOR);
        String[] strArr = {"-XX:-", "-XX:+", "-XX:", "--", "-"};
        for (int i = 0; i < 5; i++) {
            String prefix = strArr[i];
            while (true) {
                int start = args2.indexOf(prefix);
                if (start == -1) {
                    break;
                }
                int end = args2.indexOf("-", prefix.length() + start);
                if (end == -1) {
                    end = args2.length();
                }
                String parsedSubString = args2.substring(start, end);
                args2 = args2.replace(parsedSubString, BuildConfig.FLAVOR);
                if (parsedSubString.indexOf(61) == parsedSubString.lastIndexOf(61)) {
                    int arraySize = parsedArguments.size();
                    if (arraySize > 0) {
                        String lastString = parsedArguments.get(arraySize - 1);
                        if (lastString.charAt(lastString.length() - 1) == ',' || parsedSubString.contains(",")) {
                            parsedArguments.set(arraySize - 1, lastString + parsedSubString);
                        }
                    }
                    parsedArguments.add(parsedSubString);
                } else {
                    Log.w("JAVA ARGS PARSER", "Removed improper arguments: " + parsedSubString);
                }
            }
        }
        return parsedArguments;
    }

    public static String loadGraphicsLibrary() {
        String renderLibrary;
        if (Tools.LOCAL_RENDERER == null) {
            return null;
        }
        String str = Tools.LOCAL_RENDERER;
        char c = 65535;
        switch (str.hashCode()) {
            case -2113734149:
                if (str.equals("opengles3_virgl")) {
                    c = 3;
                    break;
                }
                break;
            case -2105757762:
                if (str.equals("opengles3_desktopgl_angle_vulkan")) {
                    c = 5;
                    break;
                }
                break;
            case -1749180245:
                if (str.equals("opengles2_5")) {
                    c = 1;
                    break;
                }
                break;
            case 190643136:
                if (str.equals("vulkan_zink")) {
                    c = 4;
                    break;
                }
                break;
            case 1553485365:
                if (str.equals("opengles2")) {
                    c = 0;
                    break;
                }
                break;
            case 1553485366:
                if (str.equals("opengles3")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                renderLibrary = "libgl4es_114.so";
                break;
            case 3:
            case 4:
                renderLibrary = "libOSMesa_8.so";
                break;
            case 5:
                renderLibrary = "libtinywrapper.so";
                break;
            default:
                Log.w("RENDER_LIBRARY", "No renderer selected, defaulting to opengles2");
                renderLibrary = "libgl4es_114.so";
                break;
        }
        if (dlopen(renderLibrary) || dlopen(findInLdLibPath(renderLibrary))) {
            return renderLibrary;
        }
        Log.e("RENDER_LIBRARY", "Failed to load renderer " + renderLibrary + ". Falling back to GL4ES 1.1.4");
        Tools.LOCAL_RENDERER = "opengles2";
        dlopen(Tools.NATIVE_LIB_DIR + "/libgl4es_114.so");
        return "libgl4es_114.so";
    }

    private static void purgeArg(List<String> argList, String argStart) {
        for (int i = 0; i < argList.size(); i++) {
            if (argList.get(i).startsWith(argStart)) {
                argList.remove(i);
            }
        }
    }

    private static boolean hasExtension(String extensions, String name) {
        int start = extensions.indexOf(name);
        while (start >= 0) {
            int end = name.length() + start;
            if (end == extensions.length() || extensions.charAt(end) == ' ') {
                return true;
            }
            start = extensions.indexOf(name, end);
        }
        return false;
    }

    public static int getDetectedVersion() {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        int[] numConfigs = new int[1];
        if (egl.eglInitialize(display, (int[]) null)) {
            try {
                boolean checkES3 = hasExtension(egl.eglQueryString(display, 12373), "EGL_KHR_create_context");
                if (egl.eglGetConfigs(display, (EGLConfig[]) null, 0, numConfigs)) {
                    EGLConfig[] configs = new EGLConfig[numConfigs[0]];
                    if (egl.eglGetConfigs(display, configs, numConfigs[0], numConfigs)) {
                        int highestEsVersion = 0;
                        int[] value = new int[1];
                        for (int i = 0; i < numConfigs[0]; i++) {
                            if (!egl.eglGetConfigAttrib(display, configs[i], 12352, value)) {
                                Log.w("glesDetect", "Getting config attribute with EGL10#eglGetConfigAttrib failed (" + i + "/" + numConfigs[0] + "): " + egl.eglGetError());
                            } else if (!checkES3 || (value[0] & 64) != 64) {
                                if ((value[0] & 4) == 4) {
                                    if (highestEsVersion < 2) {
                                        highestEsVersion = 2;
                                    }
                                } else if ((value[0] & 1) == 1 && highestEsVersion < 1) {
                                    highestEsVersion = 1;
                                }
                            } else if (highestEsVersion < 3) {
                                highestEsVersion = 3;
                            }
                        }
                        return highestEsVersion;
                    }
                    Log.e("glesDetect", "Getting configs with EGL10#eglGetConfigs failed: " + egl.eglGetError());
                    egl.eglTerminate(display);
                    return -1;
                }
                Log.e("glesDetect", "Getting number of configs with EGL10#eglGetConfigs failed: " + egl.eglGetError());
                egl.eglTerminate(display);
                return -2;
            } finally {
                egl.eglTerminate(display);
            }
        } else {
            Log.e("glesDetect", "Couldn't initialize EGL.");
            return -3;
        }
    }

    static {
        System.loadLibrary("pojavexec");
        System.loadLibrary("pojavexec_awt");
        dlopen("libxhook.so");
        System.loadLibrary("istdio");
    }
}
