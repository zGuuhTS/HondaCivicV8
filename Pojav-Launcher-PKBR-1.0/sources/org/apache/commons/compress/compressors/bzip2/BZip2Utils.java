package org.apache.commons.compress.compressors.bzip2;

import java.util.LinkedHashMap;
import org.apache.commons.compress.compressors.FileNameUtil;
import top.defaults.checkerboarddrawable.BuildConfig;

public abstract class BZip2Utils {
    private static final FileNameUtil fileNameUtil;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(".tar.bz2", ".tar");
        linkedHashMap.put(".tbz2", ".tar");
        linkedHashMap.put(".tbz", ".tar");
        linkedHashMap.put(".bz2", BuildConfig.FLAVOR);
        linkedHashMap.put(".bz", BuildConfig.FLAVOR);
        fileNameUtil = new FileNameUtil(linkedHashMap, ".bz2");
    }

    private BZip2Utils() {
    }

    public static String getCompressedFilename(String str) {
        return fileNameUtil.getCompressedFilename(str);
    }

    public static String getUncompressedFilename(String str) {
        return fileNameUtil.getUncompressedFilename(str);
    }

    public static boolean isCompressedFilename(String str) {
        return fileNameUtil.isCompressedFilename(str);
    }
}
