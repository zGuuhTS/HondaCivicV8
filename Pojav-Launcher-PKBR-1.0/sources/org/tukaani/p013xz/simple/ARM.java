package org.tukaani.p013xz.simple;

import kotlin.UByte;

/* renamed from: org.tukaani.xz.simple.ARM */
public final class ARM implements SimpleFilter {
    private final boolean isEncoder;
    private int pos;

    public ARM(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i + 8;
    }

    public int code(byte[] bArr, int i, int i2) {
        int i3 = (i2 + i) - 4;
        int i4 = i;
        while (i4 <= i3) {
            if ((bArr[i4 + 3] & UByte.MAX_VALUE) == 235) {
                int i5 = i4 + 2;
                int i6 = i4 + 1;
                int i7 = ((((bArr[i5] & UByte.MAX_VALUE) << 16) | ((bArr[i6] & UByte.MAX_VALUE) << 8)) | (bArr[i4] & UByte.MAX_VALUE)) << 2;
                int i8 = (this.isEncoder ? i7 + ((this.pos + i4) - i) : i7 - ((this.pos + i4) - i)) >>> 2;
                bArr[i5] = (byte) (i8 >>> 16);
                bArr[i6] = (byte) (i8 >>> 8);
                bArr[i4] = (byte) i8;
            }
            i4 += 4;
        }
        int i9 = i4 - i;
        this.pos += i9;
        return i9;
    }
}
