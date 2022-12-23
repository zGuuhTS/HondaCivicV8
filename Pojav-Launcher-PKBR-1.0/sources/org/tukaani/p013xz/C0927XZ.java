package org.tukaani.p013xz;

import org.apache.commons.compress.archivers.tar.TarConstants;

/* renamed from: org.tukaani.xz.XZ */
public class C0927XZ {
    public static final int CHECK_CRC32 = 1;
    public static final int CHECK_CRC64 = 4;
    public static final int CHECK_NONE = 0;
    public static final int CHECK_SHA256 = 10;
    public static final byte[] FOOTER_MAGIC = {89, 90};
    public static final byte[] HEADER_MAGIC = {-3, TarConstants.LF_CONTIG, 122, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 90, 0};

    private C0927XZ() {
    }
}
