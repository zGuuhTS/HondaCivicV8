package org.apache.commons.compress.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

public class BitInputStream implements Closeable {
    private static final long[] MASKS = new long[64];
    private static final int MAXIMUM_CACHE_SIZE = 63;
    private long bitsCached = 0;
    private int bitsCachedSize = 0;
    private final ByteOrder byteOrder;

    /* renamed from: in */
    private final InputStream f168in;

    static {
        for (int i = 1; i <= 63; i++) {
            long[] jArr = MASKS;
            jArr[i] = (jArr[i - 1] << 1) + 1;
        }
    }

    public BitInputStream(InputStream inputStream, ByteOrder byteOrder2) {
        this.f168in = inputStream;
        this.byteOrder = byteOrder2;
    }

    public void clearBitCache() {
        this.bitsCached = 0;
        this.bitsCachedSize = 0;
    }

    public void close() throws IOException {
        this.f168in.close();
    }

    public long readBits(int i) throws IOException {
        long j;
        long j2;
        if (i < 0 || i > 63) {
            throw new IllegalArgumentException("count must not be negative or greater than 63");
        }
        while (this.bitsCachedSize < i) {
            long read = (long) this.f168in.read();
            if (read < 0) {
                return read;
            }
            if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
                j2 = this.bitsCached;
                read <<= this.bitsCachedSize;
            } else {
                j2 = this.bitsCached << 8;
                this.bitsCached = j2;
            }
            this.bitsCached = read | j2;
            this.bitsCachedSize += 8;
        }
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            long j3 = this.bitsCached;
            j = j3 & MASKS[i];
            this.bitsCached = j3 >>> i;
        } else {
            j = (this.bitsCached >> (this.bitsCachedSize - i)) & MASKS[i];
        }
        this.bitsCachedSize -= i;
        return j;
    }
}
