package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.LZMA2Coder */
abstract class LZMA2Coder implements FilterCoder {
    public static final long FILTER_ID = 33;

    LZMA2Coder() {
    }

    public boolean changesSize() {
        return true;
    }

    public boolean lastOK() {
        return true;
    }

    public boolean nonLastOK() {
        return false;
    }
}
