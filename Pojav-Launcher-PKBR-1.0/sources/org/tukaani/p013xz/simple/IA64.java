package org.tukaani.p013xz.simple;

import org.apache.commons.p012io.FileUtils;

/* renamed from: org.tukaani.xz.simple.IA64 */
public final class IA64 implements SimpleFilter {
    private static final int[] BRANCH_TABLE = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 6, 6, 0, 0, 7, 7, 4, 4, 0, 0, 4, 4, 0, 0};
    private final boolean isEncoder;
    private int pos;

    public IA64(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i;
    }

    public int code(byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        char c;
        char c2 = 16;
        int i5 = (i + i2) - 16;
        int i6 = i;
        while (i6 <= i5) {
            int i7 = BRANCH_TABLE[bArr[i6] & 31];
            int i8 = 5;
            int i9 = 0;
            while (i9 < 3) {
                if (((i7 >>> i9) & 1) == 0) {
                    c = c2;
                    i4 = i6;
                    i3 = i9;
                } else {
                    int i10 = i8 >>> 3;
                    int i11 = i8 & 7;
                    long j = 0;
                    int i12 = 0;
                    while (i12 < 6) {
                        j |= (((long) bArr[(i6 + i10) + i12]) & 255) << (i12 * 8);
                        i12++;
                        i6 = i6;
                    }
                    i4 = i6;
                    long j2 = j >>> i11;
                    if (((j2 >>> 37) & 15) == 5 && ((j2 >>> 9) & 7) == 0) {
                        i3 = i9;
                        int i13 = (((((int) (j2 >>> 36)) & 1) << 20) | ((int) ((j2 >>> 13) & 1048575))) << 4;
                        long j3 = (long) ((this.isEncoder ? i13 + ((this.pos + i4) - i) : i13 - ((this.pos + i4) - i)) >>> 4);
                        c = 16;
                        long j4 = ((((j2 & -77309403137L) | ((j3 & 1048575) << 13)) | ((j3 & FileUtils.ONE_MB) << 16)) << i11) | (((long) ((1 << i11) - 1)) & j);
                        for (int i14 = 0; i14 < 6; i14++) {
                            bArr[i4 + i10 + i14] = (byte) ((int) (j4 >>> (i14 * 8)));
                        }
                    } else {
                        i3 = i9;
                        c = 16;
                    }
                }
                i9 = i3 + 1;
                i8 += 41;
                c2 = c;
                i6 = i4;
            }
            char c3 = c2;
            i6 += 16;
        }
        int i15 = i6 - i;
        this.pos += i15;
        return i15;
    }
}
