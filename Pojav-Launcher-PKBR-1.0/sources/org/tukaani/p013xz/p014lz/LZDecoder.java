package org.tukaani.p013xz.p014lz;

import java.io.DataInputStream;
import java.io.IOException;
import kotlin.UByte;
import org.tukaani.p013xz.ArrayCache;
import org.tukaani.p013xz.CorruptedInputException;

/* renamed from: org.tukaani.xz.lz.LZDecoder */
public final class LZDecoder {
    private final byte[] buf;
    private final int bufSize;
    private int full = 0;
    private int limit = 0;
    private int pendingDist = 0;
    private int pendingLen = 0;
    private int pos = 0;
    private int start = 0;

    public LZDecoder(int i, byte[] bArr, ArrayCache arrayCache) {
        this.bufSize = i;
        byte[] byteArray = arrayCache.getByteArray(i, false);
        this.buf = byteArray;
        if (bArr != null) {
            int min = Math.min(bArr.length, i);
            this.pos = min;
            this.full = min;
            this.start = min;
            System.arraycopy(bArr, bArr.length - min, byteArray, 0, min);
        }
    }

    public void copyUncompressed(DataInputStream dataInputStream, int i) throws IOException {
        int min = Math.min(this.bufSize - this.pos, i);
        dataInputStream.readFully(this.buf, this.pos, min);
        int i2 = this.pos + min;
        this.pos = i2;
        if (this.full < i2) {
            this.full = i2;
        }
    }

    public int flush(byte[] bArr, int i) {
        int i2 = this.pos;
        int i3 = this.start;
        int i4 = i2 - i3;
        if (i2 == this.bufSize) {
            this.pos = 0;
        }
        System.arraycopy(this.buf, i3, bArr, i, i4);
        this.start = this.pos;
        return i4;
    }

    public int getByte(int i) {
        int i2 = this.pos;
        int i3 = (i2 - i) - 1;
        if (i >= i2) {
            i3 += this.bufSize;
        }
        return this.buf[i3] & UByte.MAX_VALUE;
    }

    public int getPos() {
        return this.pos;
    }

    public boolean hasPending() {
        return this.pendingLen > 0;
    }

    public boolean hasSpace() {
        return this.pos < this.limit;
    }

    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.buf);
    }

    public void putByte(byte b) {
        byte[] bArr = this.buf;
        int i = this.pos;
        int i2 = i + 1;
        this.pos = i2;
        bArr[i] = b;
        if (this.full < i2) {
            this.full = i2;
        }
    }

    public void repeat(int i, int i2) throws IOException {
        int i3;
        int i4;
        if (i < 0 || i >= this.full) {
            throw new CorruptedInputException();
        }
        int min = Math.min(this.limit - this.pos, i2);
        this.pendingLen = i2 - min;
        this.pendingDist = i;
        int i5 = this.pos;
        int i6 = (i5 - i) - 1;
        if (i >= i5) {
            i6 += this.bufSize;
        }
        do {
            byte[] bArr = this.buf;
            int i7 = this.pos;
            i4 = i7 + 1;
            this.pos = i4;
            int i8 = i3 + 1;
            bArr[i7] = bArr[i3];
            i3 = i8 == this.bufSize ? 0 : i8;
            min--;
        } while (min > 0);
        if (this.full < i4) {
            this.full = i4;
        }
    }

    public void repeatPending() throws IOException {
        int i = this.pendingLen;
        if (i > 0) {
            repeat(this.pendingDist, i);
        }
    }

    public void reset() {
        this.start = 0;
        this.pos = 0;
        this.full = 0;
        this.limit = 0;
        this.buf[this.bufSize - 1] = 0;
    }

    public void setLimit(int i) {
        int i2 = this.bufSize;
        int i3 = this.pos;
        if (i2 - i3 <= i) {
            this.limit = i2;
        } else {
            this.limit = i3 + i;
        }
    }
}
