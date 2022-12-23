package org.tukaani.p013xz.rangecoder;

import java.io.IOException;
import kotlin.UByte;

/* renamed from: org.tukaani.xz.rangecoder.RangeEncoder */
public abstract class RangeEncoder extends RangeCoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BIT_PRICE_SHIFT_BITS = 4;
    private static final int MOVE_REDUCING_BITS = 4;
    private static final int[] prices = new int[128];
    private byte cache;
    long cacheSize;
    private long low;
    private int range;

    static {
        for (int i = 8; i < 2048; i += 16) {
            int i2 = i;
            int i3 = 0;
            for (int i4 = 0; i4 < 4; i4++) {
                i2 *= i2;
                i3 <<= 1;
                while ((-65536 & i2) != 0) {
                    i2 >>>= 1;
                    i3++;
                }
            }
            prices[i >> 4] = 161 - i3;
        }
    }

    public static int getBitPrice(int i, int i2) {
        if (i2 == 0 || i2 == 1) {
            return prices[(i ^ ((-i2) & 2047)) >>> 4];
        }
        throw new AssertionError();
    }

    public static int getBitTreePrice(short[] sArr, int i) {
        int length = i | sArr.length;
        int i2 = 0;
        do {
            int i3 = length & 1;
            length >>>= 1;
            i2 += getBitPrice(sArr[length], i3);
        } while (length != 1);
        return i2;
    }

    public static int getDirectBitsPrice(int i) {
        return i << 4;
    }

    public static int getReverseBitTreePrice(short[] sArr, int i) {
        int length = i | sArr.length;
        int i2 = 0;
        int i3 = 1;
        do {
            int i4 = length & 1;
            length >>>= 1;
            i2 += getBitPrice(sArr[i3], i4);
            i3 = (i3 << 1) | i4;
        } while (length != 1);
        return i2;
    }

    private void shiftLow() throws IOException {
        long j;
        long j2 = this.low;
        int i = (int) (j2 >>> 32);
        if (i != 0 || j2 < 4278190080L) {
            byte b = this.cache;
            do {
                writeByte(b + i);
                b = UByte.MAX_VALUE;
                j = this.cacheSize - 1;
                this.cacheSize = j;
            } while (j != 0);
            this.cache = (byte) ((int) (this.low >>> 24));
        }
        this.cacheSize++;
        this.low = (this.low & 16777215) << 8;
    }

    public void encodeBit(short[] sArr, int i, int i2) throws IOException {
        short s = sArr[i];
        int i3 = this.range;
        int i4 = (i3 >>> 11) * s;
        if (i2 == 0) {
            this.range = i4;
            sArr[i] = (short) (s + ((2048 - s) >>> 5));
        } else {
            this.low += ((long) i4) & 4294967295L;
            this.range = i3 - i4;
            sArr[i] = (short) (s - (s >>> 5));
        }
        int i5 = this.range;
        if ((-16777216 & i5) == 0) {
            this.range = i5 << 8;
            shiftLow();
        }
    }

    public void encodeBitTree(short[] sArr, int i) throws IOException {
        int length = sArr.length;
        int i2 = 1;
        do {
            length >>>= 1;
            int i3 = i & length;
            encodeBit(sArr, i2, i3);
            i2 <<= 1;
            if (i3 != 0) {
                i2 |= 1;
                continue;
            }
        } while (length != 1);
    }

    public void encodeDirectBits(int i, int i2) throws IOException {
        do {
            int i3 = this.range >>> 1;
            this.range = i3;
            i2--;
            this.low += (long) ((0 - ((i >>> i2) & 1)) & i3);
            if ((-16777216 & i3) == 0) {
                this.range = i3 << 8;
                shiftLow();
                continue;
            }
        } while (i2 != 0);
    }

    public void encodeReverseBitTree(short[] sArr, int i) throws IOException {
        int length = i | sArr.length;
        int i2 = 1;
        do {
            int i3 = length & 1;
            length >>>= 1;
            encodeBit(sArr, i2, i3);
            i2 = (i2 << 1) | i3;
        } while (length != 1);
    }

    public int finish() throws IOException {
        for (int i = 0; i < 5; i++) {
            shiftLow();
        }
        return -1;
    }

    public int getPendingSize() {
        throw new Error();
    }

    public void reset() {
        this.low = 0;
        this.range = -1;
        this.cache = 0;
        this.cacheSize = 1;
    }

    /* access modifiers changed from: package-private */
    public abstract void writeByte(int i) throws IOException;
}
