package org.apache.commons.compress.compressors.lzma;

import java.util.HashMap;
import org.apache.commons.compress.compressors.FileNameUtil;
import top.defaults.checkerboarddrawable.BuildConfig;

public class LZMAUtils {
    private static final byte[] HEADER_MAGIC = {93, 0, 0};
    private static volatile CachedAvailability cachedLZMAAvailability = CachedAvailability.DONT_CACHE;
    private static final FileNameUtil fileNameUtil;

    enum CachedAvailability {
        DONT_CACHE,
        CACHED_AVAILABLE,
        CACHED_UNAVAILABLE
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(".lzma", BuildConfig.FLAVOR);
        hashMap.put("-lzma", BuildConfig.FLAVOR);
        fileNameUtil = new FileNameUtil(hashMap, ".lzma");
        try {
            Class.forName("org.osgi.framework.BundleEvent");
        } catch (Exception e) {
            setCacheLZMAAvailablity(true);
        }
    }

    private LZMAUtils() {
    }

    static CachedAvailability getCachedLZMAAvailability() {
        return cachedLZMAAvailability;
    }

    public static String getCompressedFilename(String str) {
        return fileNameUtil.getCompressedFilename(str);
    }

    public static String getUncompressedFilename(String str) {
        return fileNameUtil.getUncompressedFilename(str);
    }

    private static boolean internalIsLZMACompressionAvailable() {
        try {
            LZMACompressorInputStream.matches((byte[]) null, 0);
            return true;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    public static boolean isCompressedFilename(String str) {
        return fileNameUtil.isCompressedFilename(str);
    }

    public static boolean isLZMACompressionAvailable() {
        CachedAvailability cachedAvailability = cachedLZMAAvailability;
        return cachedAvailability != CachedAvailability.DONT_CACHE ? cachedAvailability == CachedAvailability.CACHED_AVAILABLE : internalIsLZMACompressionAvailable();
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < HEADER_MAGIC.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr2 = HEADER_MAGIC;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    public static void setCacheLZMAAvailablity(boolean z) {
        CachedAvailability cachedAvailability;
        if (!z) {
            cachedAvailability = CachedAvailability.DONT_CACHE;
        } else if (cachedLZMAAvailability == CachedAvailability.DONT_CACHE) {
            cachedAvailability = internalIsLZMACompressionAvailable() ? CachedAvailability.CACHED_AVAILABLE : CachedAvailability.CACHED_UNAVAILABLE;
        } else {
            return;
        }
        cachedLZMAAvailability = cachedAvailability;
    }
}
