package org.tukaani.p013xz.index;

import org.tukaani.p013xz.XZIOException;
import org.tukaani.p013xz.common.Util;

/* renamed from: org.tukaani.xz.index.IndexBase */
abstract class IndexBase {
    long blocksSum = 0;
    long indexListSize = 0;
    private final XZIOException invalidIndexException;
    long recordCount = 0;
    long uncompressedSum = 0;

    IndexBase(XZIOException xZIOException) {
        this.invalidIndexException = xZIOException;
    }

    private long getUnpaddedIndexSize() {
        return ((long) (Util.getVLISize(this.recordCount) + 1)) + this.indexListSize + 4;
    }

    /* access modifiers changed from: package-private */
    public void add(long j, long j2) throws XZIOException {
        this.blocksSum += (3 + j) & -4;
        this.uncompressedSum += j2;
        this.indexListSize += (long) (Util.getVLISize(j) + Util.getVLISize(j2));
        this.recordCount++;
        if (this.blocksSum < 0 || this.uncompressedSum < 0 || getIndexSize() > Util.BACKWARD_SIZE_MAX || getStreamSize() < 0) {
            throw this.invalidIndexException;
        }
    }

    /* access modifiers changed from: package-private */
    public int getIndexPaddingSize() {
        return (int) (3 & (4 - getUnpaddedIndexSize()));
    }

    public long getIndexSize() {
        return (getUnpaddedIndexSize() + 3) & -4;
    }

    public long getStreamSize() {
        return this.blocksSum + 12 + getIndexSize() + 12;
    }
}
