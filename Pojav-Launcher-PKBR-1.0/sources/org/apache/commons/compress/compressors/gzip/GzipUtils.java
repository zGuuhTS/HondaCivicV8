package org.apache.commons.compress.compressors.gzip;

import java.util.LinkedHashMap;
import org.apache.commons.compress.compressors.FileNameUtil;
import top.defaults.checkerboarddrawable.BuildConfig;

public class GzipUtils {
    private static final FileNameUtil fileNameUtil;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(".tgz", ".tar");
        linkedHashMap.put(".taz", ".tar");
        linkedHashMap.put(".svgz", ".svg");
        linkedHashMap.put(".cpgz", ".cpio");
        linkedHashMap.put(".wmz", ".wmf");
        linkedHashMap.put(".emz", ".emf");
        linkedHashMap.put(".gz", BuildConfig.FLAVOR);
        linkedHashMap.put(".z", BuildConfig.FLAVOR);
        linkedHashMap.put("-gz", BuildConfig.FLAVOR);
        linkedHashMap.put("-z", BuildConfig.FLAVOR);
        linkedHashMap.put("_z", BuildConfig.FLAVOR);
        fileNameUtil = new FileNameUtil(linkedHashMap, ".gz");
    }

    private GzipUtils() {
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
