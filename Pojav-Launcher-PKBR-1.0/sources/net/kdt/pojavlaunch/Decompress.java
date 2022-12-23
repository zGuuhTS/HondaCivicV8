package net.kdt.pojavlaunch;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.kdt.mcgui.ProgressLayout;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import top.defaults.checkerboarddrawable.BuildConfig;

public class Decompress {
    private static final int BUFFER_SIZE = 10240;
    private static final String TAG = "Decompress";

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
        if (r3.length() == 0) goto L_0x0008;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void unzipFromAssets(android.content.Context r1, java.lang.String r2, java.lang.String r3, boolean r4) {
        /*
            if (r3 == 0) goto L_0x0008
            int r0 = r3.length()     // Catch:{ IOException -> 0x001d }
            if (r0 != 0) goto L_0x0011
        L_0x0008:
            java.io.File r0 = r1.getFilesDir()     // Catch:{ IOException -> 0x001d }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ IOException -> 0x001d }
            r3 = r0
        L_0x0011:
            android.content.res.AssetManager r0 = r1.getAssets()     // Catch:{ IOException -> 0x001d }
            java.io.InputStream r0 = r0.open(r2)     // Catch:{ IOException -> 0x001d }
            unzip(r1, r0, r3, r4)     // Catch:{ IOException -> 0x001d }
            goto L_0x0021
        L_0x001d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.Decompress.unzipFromAssets(android.content.Context, java.lang.String, java.lang.String, boolean):void");
    }

    public static void unzip(Context ctx, InputStream stream, String destination, boolean overwrite) {
        String str = destination;
        char c = 0;
        SharedPreferences firstLaunchPrefs = ctx.getSharedPreferences("pojav_extract", 0);
        if (!firstLaunchPrefs.getBoolean("filesDecompress", false)) {
            dirChecker(str, BuildConfig.FLAVOR);
            byte[] buffer = new byte[10240];
            try {
                try {
                    ZipInputStream zin = new ZipInputStream(stream);
                    while (true) {
                        ZipEntry nextEntry = zin.getNextEntry();
                        ZipEntry ze = nextEntry;
                        if (nextEntry != null) {
                            Object[] objArr = new Object[1];
                            objArr[c] = ze.getName();
                            ProgressLayout.setProgress(ProgressLayout.UNPACK_RUNTIME, 100, R.string.global_unpacking, objArr);
                            if (ze.isDirectory()) {
                                dirChecker(str, ze.getName());
                            } else {
                                File f = new File(str, ze.getName());
                                if (!f.exists()) {
                                    if (!f.createNewFile()) {
                                        Log.w(TAG, "Failed to create file " + f.getName());
                                    } else {
                                        FileOutputStream fout = new FileOutputStream(f);
                                        while (true) {
                                            int read = zin.read(buffer);
                                            int count = read;
                                            if (read == -1) {
                                                break;
                                            }
                                            fout.write(buffer, 0, count);
                                        }
                                        c = 0;
                                        zin.closeEntry();
                                        fout.close();
                                    }
                                }
                            }
                        } else {
                            firstLaunchPrefs.edit().putBoolean("filesDecompress", true).apply();
                            ProgressLayout.clearProgress(ProgressLayout.UNPACK_RUNTIME);
                            zin.close();
                            return;
                        }
                    }
                } catch (Exception e) {
                    e = e;
                    ProgressLayout.clearProgress(ProgressLayout.UNPACK_RUNTIME);
                    Log.e(TAG, "unzip", e);
                }
            } catch (Exception e2) {
                e = e2;
                InputStream inputStream = stream;
                ProgressLayout.clearProgress(ProgressLayout.UNPACK_RUNTIME);
                Log.e(TAG, "unzip", e);
            }
        } else {
            InputStream inputStream2 = stream;
        }
    }

    private static void dirChecker(String destination, String dir) {
        File f = new File(destination, dir);
        if (!f.isDirectory() && !f.mkdirs()) {
            Log.w(TAG, "Failed to create folder " + f.getName());
        }
    }
}
