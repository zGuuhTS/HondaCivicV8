package net.kdt.pojavlaunch.multirt;

import android.content.Context;
import android.system.Os;
import android.util.Log;
import com.kdt.mcgui.ProgressLayout;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.utils.JREUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.p010xz.XZCompressorInputStream;
import org.apache.commons.p012io.FileUtils;
import org.apache.commons.p012io.IOUtils;
import top.defaults.checkerboarddrawable.BuildConfig;

public class MultiRTUtils {
    private static final String JAVA_VERSION_STR = "JAVA_VERSION=\"";
    private static final String OS_ARCH_STR = "OS_ARCH=\"";
    private static final File RUNTIME_FOLDER = new File(Tools.MULTIRT_HOME);
    private static final HashMap<String, Runtime> sCache = new HashMap<>();

    public static List<Runtime> getRuntimes() {
        File file = RUNTIME_FOLDER;
        if (!file.exists()) {
            file.mkdirs();
        }
        ArrayList<Runtime> runtimes = new ArrayList<>();
        System.out.println("Fetch runtime list");
        for (File f : file.listFiles()) {
            runtimes.add(read(f.getName()));
        }
        return runtimes;
    }

    public static String getExactJreName(int majorVersion) {
        for (Runtime r : getRuntimes()) {
            if (r.javaVersion == majorVersion) {
                return r.name;
            }
        }
        return null;
    }

    public static String getNearestJreName(int majorVersion) {
        int currentFactor;
        int diff_factor = Integer.MAX_VALUE;
        String result = null;
        for (Runtime r : getRuntimes()) {
            if (r.javaVersion >= majorVersion && diff_factor > (currentFactor = r.javaVersion - majorVersion)) {
                result = r.name;
                diff_factor = currentFactor;
            }
        }
        return result;
    }

    public static void installRuntimeNamed(Context ctx, InputStream runtimeInputStream, String name) throws IOException {
        File file = RUNTIME_FOLDER;
        File dest = new File(file, "/" + name);
        if (dest.exists()) {
            FileUtils.deleteDirectory(dest);
        }
        dest.mkdirs();
        uncompressTarXZ(runtimeInputStream, dest);
        runtimeInputStream.close();
        unpack200(ctx, file + "/" + name);
        ProgressLayout.clearProgress(ProgressLayout.UNPACK_RUNTIME);
        read(name);
    }

    public static void postPrepare(Context ctx, String name) throws IOException {
        File dest = new File(RUNTIME_FOLDER, "/" + name);
        if (dest.exists()) {
            Runtime runtime = read(name);
            String libFolder = "lib";
            if (new File(dest, libFolder + "/" + runtime.arch).exists()) {
                libFolder = libFolder + "/" + runtime.arch;
            }
            File ftIn = new File(dest, libFolder + "/libfreetype.so.6");
            File ftOut = new File(dest, libFolder + "/libfreetype.so");
            if (ftIn.exists() && (!ftOut.exists() || ftIn.length() != ftOut.length())) {
                ftIn.renameTo(ftOut);
            }
            copyDummyNativeLib(ctx, "libawt_xawt.so", dest, libFolder);
        }
    }

    public static Runtime installRuntimeNamedBinpack(Context ctx, InputStream universalFileInputStream, InputStream platformBinsInputStream, String name, String binpackVersion) throws IOException {
        File file = RUNTIME_FOLDER;
        File dest = new File(file, "/" + name);
        if (dest.exists()) {
            FileUtils.deleteDirectory(dest);
        }
        dest.mkdirs();
        installRuntimeNamedNoRemove(universalFileInputStream, dest);
        installRuntimeNamedNoRemove(platformBinsInputStream, dest);
        unpack200(ctx, file + "/" + name);
        FileOutputStream fos = new FileOutputStream(new File(file, "/" + name + "/pojav_version"));
        fos.write(binpackVersion.getBytes());
        fos.close();
        ProgressLayout.clearProgress(ProgressLayout.UNPACK_RUNTIME);
        sCache.remove(name);
        return read(name);
    }

    public static String __internal__readBinpackVersion(String name) {
        File binpack_verfile = new File(RUNTIME_FOLDER, "/" + name + "/pojav_version");
        try {
            if (binpack_verfile.exists()) {
                return Tools.read(binpack_verfile.getAbsolutePath());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeRuntimeNamed(String name) throws IOException {
        File dest = new File(RUNTIME_FOLDER, "/" + name);
        if (dest.exists()) {
            FileUtils.deleteDirectory(dest);
            sCache.remove(name);
        }
    }

    public static void setRuntimeNamed(String name) throws IOException {
        File dest = new File(RUNTIME_FOLDER, "/" + name);
        if (!dest.exists() || forceReread(name).versionString == null) {
            throw new RuntimeException("Selected runtime is broken!");
        }
        Tools.DIR_HOME_JRE = dest.getAbsolutePath();
        JREUtils.relocateLibPath();
    }

    public static Runtime forceReread(String name) {
        sCache.remove(name);
        return read(name);
    }

    public static Runtime read(String name) {
        Runtime returnRuntime;
        int javaVersionInt;
        HashMap<String, Runtime> hashMap = sCache;
        if (hashMap.containsKey(name)) {
            return hashMap.get(name);
        }
        File release = new File(RUNTIME_FOLDER, "/" + name + "/release");
        if (!release.exists()) {
            return new Runtime(name);
        }
        try {
            String content = Tools.read(release.getAbsolutePath());
            int javaVersionIndex = content.indexOf(JAVA_VERSION_STR);
            int osArchIndex = content.indexOf(OS_ARCH_STR);
            if (javaVersionIndex == -1 || osArchIndex == -1) {
                returnRuntime = new Runtime(name);
                sCache.put(name, returnRuntime);
                return returnRuntime;
            }
            int javaVersionIndex2 = javaVersionIndex + JAVA_VERSION_STR.length();
            int osArchIndex2 = osArchIndex + OS_ARCH_STR.length();
            String javaVersion = content.substring(javaVersionIndex2, content.indexOf(34, javaVersionIndex2));
            String[] javaVersionSplit = javaVersion.split("\\.");
            if (javaVersionSplit[0].equals("1")) {
                javaVersionInt = Integer.parseInt(javaVersionSplit[1]);
            } else {
                javaVersionInt = Integer.parseInt(javaVersionSplit[0]);
            }
            Runtime runtime = new Runtime(name);
            runtime.arch = content.substring(osArchIndex2, content.indexOf(34, osArchIndex2));
            runtime.javaVersion = javaVersionInt;
            runtime.versionString = javaVersion;
            returnRuntime = runtime;
            sCache.put(name, returnRuntime);
            return returnRuntime;
        } catch (IOException e) {
            returnRuntime = new Runtime(name);
        }
    }

    private static void unpack200(Context ctx, String runtimePath) {
        Collection<File> files = FileUtils.listFiles(new File(runtimePath), new String[]{"pack"}, true);
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]).directory(new File(ctx.getApplicationInfo().nativeLibraryDir));
        for (File jarFile : files) {
            try {
                processBuilder.command(new String[]{"./libunpack200.so", "-r", jarFile.getAbsolutePath(), jarFile.getAbsolutePath().replace(".pack", BuildConfig.FLAVOR)}).start().waitFor();
            } catch (IOException | InterruptedException e) {
                Log.e("MULTIRT", "Failed to unpack the runtime !");
            }
        }
    }

    private static void copyDummyNativeLib(Context ctx, String name, File dest, String libFolder) throws IOException {
        File fileLib = new File(dest, "/" + libFolder + "/" + name);
        fileLib.delete();
        FileUtils.copyFile(new File(ctx.getApplicationInfo().nativeLibraryDir, name), fileLib);
    }

    public static void installRuntimeNamedNoRemove(InputStream runtimeInputStream, File dest) throws IOException {
        uncompressTarXZ(runtimeInputStream, dest);
        runtimeInputStream.close();
    }

    private static void uncompressTarXZ(InputStream tarFileInputStream, File dest) throws IOException {
        dest.mkdirs();
        byte[] buffer = new byte[8192];
        TarArchiveInputStream tarIn = new TarArchiveInputStream(new XZCompressorInputStream(tarFileInputStream));
        for (TarArchiveEntry tarEntry = tarIn.getNextTarEntry(); tarEntry != null; tarEntry = tarIn.getNextTarEntry()) {
            ProgressLayout.setProgress(ProgressLayout.UNPACK_RUNTIME, 100, R.string.global_unpacking, tarEntry.getName());
            File destPath = new File(dest, tarEntry.getName());
            if (tarEntry.isSymbolicLink()) {
                destPath.getParentFile().mkdirs();
                try {
                    Os.symlink(tarEntry.getName(), tarEntry.getLinkName());
                } catch (Throwable e) {
                    Log.e("MultiRT", e.toString());
                }
            } else if (tarEntry.isDirectory()) {
                destPath.mkdirs();
                destPath.setExecutable(true);
            } else if (!destPath.exists() || destPath.length() != tarEntry.getSize()) {
                destPath.getParentFile().mkdirs();
                destPath.createNewFile();
                FileOutputStream os = new FileOutputStream(destPath);
                IOUtils.copyLarge((InputStream) tarIn, (OutputStream) os, buffer);
                os.close();
            }
        }
        tarIn.close();
    }
}
