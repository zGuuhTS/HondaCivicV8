package org.tukaani.p013xz.simple;

import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import kotlin.UByte;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

/* renamed from: org.tukaani.xz.simple.SPARC */
public final class SPARC implements SimpleFilter {
    private final boolean isEncoder;
    private int pos;

    public SPARC(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i;
    }

    public int code(byte[] bArr, int i, int i2) {
        int i3 = (i2 + i) - 4;
        int i4 = i;
        while (i4 <= i3) {
            if ((bArr[i4] == 64 && (bArr[i4 + 1] & 192) == 0) || (bArr[i4] == Byte.MAX_VALUE && (bArr[i4 + 1] & 192) == 192)) {
                int i5 = i4 + 1;
                int i6 = i4 + 2;
                int i7 = i4 + 3;
                int i8 = (((((bArr[i4] & UByte.MAX_VALUE) << 24) | ((bArr[i5] & UByte.MAX_VALUE) << 16)) | ((bArr[i6] & UByte.MAX_VALUE) << 8)) | (bArr[i7] & UByte.MAX_VALUE)) << 2;
                int i9 = (this.isEncoder ? i8 + ((this.pos + i4) - i) : i8 - ((this.pos + i4) - i)) >>> 2;
                int i10 = (i9 & 4194303) | (((0 - ((i9 >>> 22) & 1)) << 22) & LockFreeTaskQueueCore.MAX_CAPACITY_MASK) | BasicMeasure.EXACTLY;
                bArr[i4] = (byte) (i10 >>> 24);
                bArr[i5] = (byte) (i10 >>> 16);
                bArr[i6] = (byte) (i10 >>> 8);
                bArr[i7] = (byte) i10;
            }
            i4 += 4;
        }
        int i11 = i4 - i;
        this.pos += i11;
        return i11;
    }
}
