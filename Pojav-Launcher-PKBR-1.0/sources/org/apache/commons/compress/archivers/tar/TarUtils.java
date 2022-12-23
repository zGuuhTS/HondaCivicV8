package org.apache.commons.compress.archivers.tar;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import top.defaults.checkerboarddrawable.BuildConfig;

public class TarUtils {
    private static final int BYTE_MASK = 255;
    static final ZipEncoding DEFAULT_ENCODING = ZipEncodingHelper.getZipEncoding((String) null);
    static final ZipEncoding FALLBACK_ENCODING = new ZipEncoding() {
        public boolean canEncode(String str) {
            return true;
        }

        public String decode(byte[] bArr) {
            StringBuilder sb = new StringBuilder(bArr.length);
            for (byte b : bArr) {
                if (b == 0) {
                    break;
                }
                sb.append((char) (b & UByte.MAX_VALUE));
            }
            return sb.toString();
        }

        public ByteBuffer encode(String str) {
            int length = str.length();
            byte[] bArr = new byte[length];
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) str.charAt(i);
            }
            return ByteBuffer.wrap(bArr);
        }
    };

    private TarUtils() {
    }

    public static long computeCheckSum(byte[] bArr) {
        long j = 0;
        for (byte b : bArr) {
            j += (long) (b & UByte.MAX_VALUE);
        }
        return j;
    }

    private static String exceptionMessage(byte[] bArr, int i, int i2, int i3, byte b) {
        String replaceAll = new String(bArr, i, i2).replaceAll("\u0000", "{NUL}");
        return "Invalid byte " + b + " at offset " + (i3 - i) + " in '" + replaceAll + "' len=" + i2;
    }

    private static void formatBigIntegerBinary(long j, byte[] bArr, int i, int i2, boolean z) {
        byte[] byteArray = BigInteger.valueOf(j).toByteArray();
        int length = byteArray.length;
        int i3 = (i2 + i) - length;
        int i4 = 0;
        System.arraycopy(byteArray, 0, bArr, i3, length);
        if (z) {
            i4 = 255;
        }
        byte b = (byte) i4;
        while (true) {
            i++;
            if (i < i3) {
                bArr[i] = b;
            } else {
                return;
            }
        }
    }

    public static int formatCheckSumOctalBytes(long j, byte[] bArr, int i, int i2) {
        int i3 = i2 - 2;
        formatUnsignedOctalString(j, bArr, i, i3);
        bArr[i3 + i] = 0;
        bArr[i3 + 1 + i] = 32;
        return i + i2;
    }

    private static void formatLongBinary(long j, byte[] bArr, int i, int i2, boolean z) {
        int i3 = (i2 - 1) * 8;
        long j2 = 1 << i3;
        long abs = Math.abs(j);
        if (abs < j2) {
            if (z) {
                abs = ((abs ^ (j2 - 1)) | ((long) (255 << i3))) + 1;
            }
            for (int i4 = (i2 + i) - 1; i4 >= i; i4--) {
                bArr[i4] = (byte) ((int) abs);
                abs >>= 8;
            }
            return;
        }
        throw new IllegalArgumentException("Value " + j + " is too large for " + i2 + " byte field.");
    }

    public static int formatLongOctalBytes(long j, byte[] bArr, int i, int i2) {
        int i3 = i2 - 1;
        formatUnsignedOctalString(j, bArr, i, i3);
        bArr[i3 + i] = 32;
        return i + i2;
    }

    public static int formatLongOctalOrBinaryBytes(long j, byte[] bArr, int i, int i2) {
        long j2 = i2 == 8 ? TarConstants.MAXID : TarConstants.MAXSIZE;
        boolean z = j < 0;
        if (!z && j <= j2) {
            return formatLongOctalBytes(j, bArr, i, i2);
        }
        if (i2 < 9) {
            formatLongBinary(j, bArr, i, i2, z);
        }
        formatBigIntegerBinary(j, bArr, i, i2, z);
        bArr[i] = (byte) (z ? 255 : 128);
        return i + i2;
    }

    public static int formatNameBytes(String str, byte[] bArr, int i, int i2) {
        try {
            return formatNameBytes(str, bArr, i, i2, DEFAULT_ENCODING);
        } catch (IOException e) {
            try {
                return formatNameBytes(str, bArr, i, i2, FALLBACK_ENCODING);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public static int formatNameBytes(String str, byte[] bArr, int i, int i2, ZipEncoding zipEncoding) throws IOException {
        int length = str.length();
        ByteBuffer encode = zipEncoding.encode(str);
        while (encode.limit() > i2 && length > 0) {
            length--;
            encode = zipEncoding.encode(str.substring(0, length));
        }
        int limit = encode.limit() - encode.position();
        System.arraycopy(encode.array(), encode.arrayOffset(), bArr, i, limit);
        while (limit < i2) {
            bArr[i + limit] = 0;
            limit++;
        }
        return i + i2;
    }

    public static int formatOctalBytes(long j, byte[] bArr, int i, int i2) {
        int i3 = i2 - 2;
        formatUnsignedOctalString(j, bArr, i, i3);
        bArr[i3 + i] = 32;
        bArr[i3 + 1 + i] = 0;
        return i + i2;
    }

    public static void formatUnsignedOctalString(long j, byte[] bArr, int i, int i2) {
        int i3;
        int i4 = i2 - 1;
        if (j == 0) {
            i3 = i4 - 1;
            bArr[i4 + i] = TarConstants.LF_NORMAL;
        } else {
            long j2 = j;
            while (i4 >= 0 && j2 != 0) {
                bArr[i + i4] = (byte) (((byte) ((int) (7 & j2))) + TarConstants.LF_NORMAL);
                j2 >>>= 3;
                i4--;
            }
            if (j2 == 0) {
                i3 = i4;
            } else {
                throw new IllegalArgumentException(j + "=" + Long.toOctalString(j) + " will not fit in octal number buffer of length " + i2);
            }
        }
        while (i3 >= 0) {
            bArr[i + i3] = TarConstants.LF_NORMAL;
            i3--;
        }
    }

    private static long parseBinaryBigInteger(byte[] bArr, int i, int i2, boolean z) {
        int i3 = i2 - 1;
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i + 1, bArr2, 0, i3);
        BigInteger bigInteger = new BigInteger(bArr2);
        if (z) {
            bigInteger = bigInteger.add(BigInteger.valueOf(-1)).not();
        }
        if (bigInteger.bitLength() <= 63) {
            long longValue = bigInteger.longValue();
            return z ? -longValue : longValue;
        }
        throw new IllegalArgumentException("At offset " + i + ", " + i2 + " byte binary number" + " exceeds maximum signed long" + " value");
    }

    private static long parseBinaryLong(byte[] bArr, int i, int i2, boolean z) {
        if (i2 < 9) {
            long j = 0;
            for (int i3 = 1; i3 < i2; i3++) {
                j = (j << 8) + ((long) (bArr[i + i3] & UByte.MAX_VALUE));
            }
            if (z) {
                j = (j - 1) ^ (((long) Math.pow(2.0d, (double) ((i2 - 1) * 8))) - 1);
            }
            return z ? -j : j;
        }
        throw new IllegalArgumentException("At offset " + i + ", " + i2 + " byte binary number" + " exceeds maximum signed long" + " value");
    }

    public static boolean parseBoolean(byte[] bArr, int i) {
        return bArr[i] == 1;
    }

    public static String parseName(byte[] bArr, int i, int i2) {
        try {
            return parseName(bArr, i, i2, DEFAULT_ENCODING);
        } catch (IOException e) {
            try {
                return parseName(bArr, i, i2, FALLBACK_ENCODING);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public static String parseName(byte[] bArr, int i, int i2, ZipEncoding zipEncoding) throws IOException {
        while (i2 > 0 && bArr[(i + i2) - 1] == 0) {
            i2--;
        }
        if (i2 <= 0) {
            return BuildConfig.FLAVOR;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return zipEncoding.decode(bArr2);
    }

    public static long parseOctal(byte[] bArr, int i, int i2) {
        int i3 = i + i2;
        if (i2 >= 2) {
            long j = 0;
            if (bArr[i] == 0) {
                return 0;
            }
            int i4 = i;
            while (i4 < i3 && bArr[i4] == 32) {
                i4++;
            }
            byte b = bArr[i3 - 1];
            while (i4 < i3 && (b == 0 || b == 32)) {
                i3--;
                b = bArr[i3 - 1];
            }
            while (i4 < i3) {
                byte b2 = bArr[i4];
                if (b2 < 48 || b2 > 55) {
                    throw new IllegalArgumentException(exceptionMessage(bArr, i, i2, i4, b2));
                }
                j = (j << 3) + ((long) (b2 - 48));
                i4++;
            }
            return j;
        }
        throw new IllegalArgumentException("Length " + i2 + " must be at least 2");
    }

    public static long parseOctalOrBinary(byte[] bArr, int i, int i2) {
        if ((bArr[i] & ByteCompanionObject.MIN_VALUE) == 0) {
            return parseOctal(bArr, i, i2);
        }
        boolean z = bArr[i] == -1;
        return i2 < 9 ? parseBinaryLong(bArr, i, i2, z) : parseBinaryBigInteger(bArr, i, i2, z);
    }

    public static boolean verifyCheckSum(byte[] bArr) {
        long parseOctal = parseOctal(bArr, TarConstants.CHKSUM_OFFSET, 8);
        long j = 0;
        long j2 = 0;
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            if (148 <= i && i < 156) {
                b = 32;
            }
            j += (long) (b & UByte.MAX_VALUE);
            j2 += (long) b;
        }
        return parseOctal == j || parseOctal == j2;
    }
}
