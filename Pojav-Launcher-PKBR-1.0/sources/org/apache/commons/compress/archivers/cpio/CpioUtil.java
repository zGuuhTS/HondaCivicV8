package org.apache.commons.compress.archivers.cpio;

import kotlin.UByte;

class CpioUtil {
    CpioUtil() {
    }

    static long byteArray2long(byte[] bArr, boolean z) {
        if (bArr.length % 2 == 0) {
            int length = bArr.length;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            if (!z) {
                int i = 0;
                while (i < length) {
                    byte b = bArr2[i];
                    int i2 = i + 1;
                    bArr2[i] = bArr2[i2];
                    bArr2[i2] = b;
                    i = i2 + 1;
                }
            }
            long j = (long) (bArr2[0] & UByte.MAX_VALUE);
            for (int i3 = 1; i3 < length; i3++) {
                j = (j << 8) | ((long) (bArr2[i3] & UByte.MAX_VALUE));
            }
            return j;
        }
        throw new UnsupportedOperationException();
    }

    static long fileType(long j) {
        return j & 61440;
    }

    static byte[] long2byteArray(long j, int i, boolean z) {
        byte[] bArr = new byte[i];
        if (i % 2 != 0 || i < 2) {
            throw new UnsupportedOperationException();
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            bArr[i2] = (byte) ((int) (255 & j));
            j >>= 8;
        }
        if (!z) {
            int i3 = 0;
            while (i3 < i) {
                byte b = bArr[i3];
                int i4 = i3 + 1;
                bArr[i3] = bArr[i4];
                bArr[i4] = b;
                i3 = i4 + 1;
            }
        }
        return bArr;
    }
}
