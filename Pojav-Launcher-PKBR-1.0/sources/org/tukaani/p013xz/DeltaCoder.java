package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.DeltaCoder */
abstract class DeltaCoder implements FilterCoder {
    public static final long FILTER_ID = 3;

    DeltaCoder() {
    }

    public boolean changesSize() {
        return false;
    }

    public boolean lastOK() {
        return false;
    }

    public boolean nonLastOK() {
        return true;
    }
}
