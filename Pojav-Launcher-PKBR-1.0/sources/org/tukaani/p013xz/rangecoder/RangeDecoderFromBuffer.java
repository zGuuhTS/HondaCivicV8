package org.tukaani.p013xz.rangecoder;

import androidx.core.view.ViewCompat;
import java.io.DataInputStream;
import java.io.IOException;
import kotlin.UByte;
import org.tukaani.p013xz.ArrayCache;
import org.tukaani.p013xz.CorruptedInputException;

/* renamed from: org.tukaani.xz.rangecoder.RangeDecoderFromBuffer */
public final class RangeDecoderFromBuffer extends RangeDecoder {
    private static final int INIT_SIZE = 5;
    private final byte[] buf;
    private int pos;

    public RangeDecoderFromBuffer(int i, ArrayCache arrayCache) {
        byte[] byteArray = arrayCache.getByteArray(i - 5, false);
        this.buf = byteArray;
        this.pos = byteArray.length;
    }

    public boolean isFinished() {
        return this.pos == this.buf.length && this.code == 0;
    }

    public void normalize() throws IOException {
        if ((this.range & ViewCompat.MEASURED_STATE_MASK) == 0) {
            try {
                byte[] bArr = this.buf;
                int i = this.pos;
                this.pos = i + 1;
                this.code = (this.code << 8) | (bArr[i] & UByte.MAX_VALUE);
                this.range <<= 8;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new CorruptedInputException();
            }
        }
    }

    public void prepareInputBuffer(DataInputStream dataInputStream, int i) throws IOException {
        if (i < 5) {
            throw new CorruptedInputException();
        } else if (dataInputStream.readUnsignedByte() == 0) {
            this.code = dataInputStream.readInt();
            this.range = -1;
            int i2 = i - 5;
            byte[] bArr = this.buf;
            int length = bArr.length - i2;
            this.pos = length;
            dataInputStream.readFully(bArr, length, i2);
        } else {
            throw new CorruptedInputException();
        }
    }

    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.buf);
    }
}
