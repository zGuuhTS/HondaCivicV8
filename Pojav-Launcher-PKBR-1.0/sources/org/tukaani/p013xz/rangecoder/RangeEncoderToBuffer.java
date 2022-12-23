package org.tukaani.p013xz.rangecoder;

import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.p013xz.ArrayCache;

/* renamed from: org.tukaani.xz.rangecoder.RangeEncoderToBuffer */
public final class RangeEncoderToBuffer extends RangeEncoder {
    private final byte[] buf;
    private int bufPos;

    public RangeEncoderToBuffer(int i, ArrayCache arrayCache) {
        this.buf = arrayCache.getByteArray(i, false);
        reset();
    }

    public int finish() {
        try {
            super.finish();
            return this.bufPos;
        } catch (IOException e) {
            throw new Error();
        }
    }

    public int getPendingSize() {
        return ((this.bufPos + ((int) this.cacheSize)) + 5) - 1;
    }

    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.buf);
    }

    public void reset() {
        super.reset();
        this.bufPos = 0;
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(this.buf, 0, this.bufPos);
    }

    /* access modifiers changed from: package-private */
    public void writeByte(int i) {
        byte[] bArr = this.buf;
        int i2 = this.bufPos;
        this.bufPos = i2 + 1;
        bArr[i2] = (byte) i;
    }
}
