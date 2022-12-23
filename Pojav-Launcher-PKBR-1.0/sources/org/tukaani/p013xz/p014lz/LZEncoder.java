package org.tukaani.p013xz.p014lz;

import java.io.IOException;
import java.io.OutputStream;
import kotlin.UByte;
import org.tukaani.p013xz.ArrayCache;

/* renamed from: org.tukaani.xz.lz.LZEncoder */
public abstract class LZEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int MF_BT4 = 20;
    public static final int MF_HC4 = 4;
    final byte[] buf;
    final int bufSize;
    private boolean finishing = false;
    private final int keepSizeAfter;
    private final int keepSizeBefore;
    final int matchLenMax;
    final int niceLen;
    private int pendingSize = 0;
    private int readLimit = -1;
    int readPos = -1;
    private int writePos = 0;

    LZEncoder(int i, int i2, int i3, int i4, int i5, ArrayCache arrayCache) {
        int bufSize2 = getBufSize(i, i2, i3, i5);
        this.bufSize = bufSize2;
        this.buf = arrayCache.getByteArray(bufSize2, false);
        this.keepSizeBefore = i2 + i;
        this.keepSizeAfter = i3 + i5;
        this.matchLenMax = i5;
        this.niceLen = i4;
    }

    private static int getBufSize(int i, int i2, int i3, int i4) {
        return i2 + i + i3 + i4 + Math.min((i / 2) + 262144, 536870912);
    }

    public static LZEncoder getInstance(int i, int i2, int i3, int i4, int i5, int i6, int i7, ArrayCache arrayCache) {
        switch (i6) {
            case 4:
                return new HC4(i, i2, i3, i4, i5, i7, arrayCache);
            case 20:
                return new BT4(i, i2, i3, i4, i5, i7, arrayCache);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static int getMemoryUsage(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int bufSize2 = (getBufSize(i, i2, i3, i4) / 1024) + 10;
        switch (i5) {
            case 4:
                i6 = HC4.getMemoryUsage(i);
                break;
            case 20:
                i6 = BT4.getMemoryUsage(i);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return bufSize2 + i6;
    }

    private void moveWindow() {
        int i = ((this.readPos + 1) - this.keepSizeBefore) & -16;
        byte[] bArr = this.buf;
        System.arraycopy(bArr, i, bArr, 0, this.writePos - i);
        this.readPos -= i;
        this.readLimit -= i;
        this.writePos -= i;
    }

    static void normalize(int[] iArr, int i, int i2) {
        for (int i3 = 0; i3 < i; i3++) {
            if (iArr[i3] <= i2) {
                iArr[i3] = 0;
            } else {
                iArr[i3] = iArr[i3] - i2;
            }
        }
    }

    private void processPendingBytes() {
        int i;
        int i2 = this.pendingSize;
        if (i2 > 0 && (i = this.readPos) < this.readLimit) {
            this.readPos = i - i2;
            this.pendingSize = 0;
            skip(i2);
            if (this.pendingSize >= i2) {
                throw new AssertionError();
            }
        }
    }

    public void copyUncompressed(OutputStream outputStream, int i, int i2) throws IOException {
        outputStream.write(this.buf, (this.readPos + 1) - i, i2);
    }

    public int fillWindow(byte[] bArr, int i, int i2) {
        if (!this.finishing) {
            if (this.readPos >= this.bufSize - this.keepSizeAfter) {
                moveWindow();
            }
            int i3 = this.bufSize;
            int i4 = this.writePos;
            if (i2 > i3 - i4) {
                i2 = i3 - i4;
            }
            System.arraycopy(bArr, i, this.buf, i4, i2);
            int i5 = this.writePos + i2;
            this.writePos = i5;
            int i6 = this.keepSizeAfter;
            if (i5 >= i6) {
                this.readLimit = i5 - i6;
            }
            processPendingBytes();
            return i2;
        }
        throw new AssertionError();
    }

    public int getAvail() {
        if (isStarted()) {
            return this.writePos - this.readPos;
        }
        throw new AssertionError();
    }

    public int getByte(int i) {
        return this.buf[this.readPos - i] & UByte.MAX_VALUE;
    }

    public int getByte(int i, int i2) {
        return this.buf[(this.readPos + i) - i2] & UByte.MAX_VALUE;
    }

    public int getMatchLen(int i, int i2) {
        int i3 = (this.readPos - i) - 1;
        int i4 = 0;
        while (i4 < i2) {
            byte[] bArr = this.buf;
            if (bArr[this.readPos + i4] != bArr[i3 + i4]) {
                break;
            }
            i4++;
        }
        return i4;
    }

    public int getMatchLen(int i, int i2, int i3) {
        int i4 = this.readPos + i;
        int i5 = (i4 - i2) - 1;
        int i6 = 0;
        while (i6 < i3) {
            byte[] bArr = this.buf;
            if (bArr[i4 + i6] != bArr[i5 + i6]) {
                break;
            }
            i6++;
        }
        return i6;
    }

    public abstract Matches getMatches();

    public int getPos() {
        return this.readPos;
    }

    public boolean hasEnoughData(int i) {
        return this.readPos - i < this.readLimit;
    }

    public boolean isStarted() {
        return this.readPos != -1;
    }

    /* access modifiers changed from: package-private */
    public int movePos(int i, int i2) {
        if (i >= i2) {
            int i3 = this.readPos + 1;
            this.readPos = i3;
            int i4 = this.writePos - i3;
            if (i4 >= i) {
                return i4;
            }
            if (i4 >= i2 && this.finishing) {
                return i4;
            }
            this.pendingSize++;
            return 0;
        }
        throw new AssertionError();
    }

    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.buf);
    }

    public void setFinishing() {
        this.readLimit = this.writePos - 1;
        this.finishing = true;
        processPendingBytes();
    }

    public void setFlushing() {
        this.readLimit = this.writePos - 1;
        processPendingBytes();
    }

    public void setPresetDict(int i, byte[] bArr) {
        if (isStarted()) {
            throw new AssertionError();
        } else if (this.writePos != 0) {
            throw new AssertionError();
        } else if (bArr != null) {
            int min = Math.min(bArr.length, i);
            System.arraycopy(bArr, bArr.length - min, this.buf, 0, min);
            this.writePos += min;
            skip(min);
        }
    }

    public abstract void skip(int i);

    public boolean verifyMatches(Matches matches) {
        int min = Math.min(getAvail(), this.matchLenMax);
        for (int i = 0; i < matches.count; i++) {
            if (getMatchLen(matches.dist[i], min) != matches.len[i]) {
                return false;
            }
        }
        return true;
    }
}
