package org.tukaani.p013xz.simple;

import kotlin.UByte;

/* renamed from: org.tukaani.xz.simple.PowerPC */
public final class PowerPC implements SimpleFilter {
    private final boolean isEncoder;
    private int pos;

    public PowerPC(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i;
    }

    public int code(byte[] bArr, int i, int i2) {
        int i3 = (i2 + i) - 4;
        int i4 = i;
        while (i4 <= i3) {
            if ((bArr[i4] & 252) == 72) {
                int i5 = i4 + 3;
                if ((bArr[i5] & 3) == 1) {
                    int i6 = i4 + 1;
                    int i7 = i4 + 2;
                    byte b = ((bArr[i4] & 3) << 24) | ((bArr[i6] & UByte.MAX_VALUE) << 16) | ((bArr[i7] & UByte.MAX_VALUE) << 8) | (bArr[i5] & 252);
                    int i8 = this.isEncoder ? b + ((this.pos + i4) - i) : b - ((this.pos + i4) - i);
                    bArr[i4] = (byte) (72 | ((i8 >>> 24) & 3));
                    bArr[i6] = (byte) (i8 >>> 16);
                    bArr[i7] = (byte) (i8 >>> 8);
                    bArr[i5] = (byte) ((bArr[i5] & 3) | i8);
                }
            }
            i4 += 4;
        }
        int i9 = i4 - i;
        this.pos += i9;
        return i9;
    }
}
