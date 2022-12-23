package org.apache.commons.compress.archivers.zip;

import androidx.core.view.InputDeviceCompat;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.CRC32;
import org.apache.commons.compress.archivers.zip.UnsupportedZipFeatureException;

public abstract class ZipUtil {
    private static final byte[] DOS_TIME_MIN = ZipLong.getBytes(8448);

    public static long adjustToLong(int i) {
        return i < 0 ? ((long) i) + 4294967296L : (long) i;
    }

    static long bigToLong(BigInteger bigInteger) {
        if (bigInteger.bitLength() <= 63) {
            return bigInteger.longValue();
        }
        throw new NumberFormatException("The BigInteger cannot fit inside a 64 bit java long: [" + bigInteger + "]");
    }

    static boolean canHandleEntryData(ZipArchiveEntry zipArchiveEntry) {
        return supportsEncryptionOf(zipArchiveEntry) && supportsMethodOf(zipArchiveEntry);
    }

    static void checkRequestedFeatures(ZipArchiveEntry zipArchiveEntry) throws UnsupportedZipFeatureException {
        if (!supportsEncryptionOf(zipArchiveEntry)) {
            throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.ENCRYPTION, zipArchiveEntry);
        } else if (!supportsMethodOf(zipArchiveEntry)) {
            ZipMethod methodByCode = ZipMethod.getMethodByCode(zipArchiveEntry.getMethod());
            if (methodByCode == null) {
                throw new UnsupportedZipFeatureException(UnsupportedZipFeatureException.Feature.METHOD, zipArchiveEntry);
            }
            throw new UnsupportedZipFeatureException(methodByCode, zipArchiveEntry);
        }
    }

    static void copy(byte[] bArr, byte[] bArr2, int i) {
        if (bArr != null) {
            System.arraycopy(bArr, 0, bArr2, i, bArr.length);
        }
    }

    static byte[] copy(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    public static long dosToJavaTime(long j) {
        Calendar instance = Calendar.getInstance();
        instance.set(1, ((int) ((j >> 25) & 127)) + 1980);
        instance.set(2, ((int) ((j >> 21) & 15)) - 1);
        instance.set(5, ((int) (j >> 16)) & 31);
        instance.set(11, ((int) (j >> 11)) & 31);
        instance.set(12, ((int) (j >> 5)) & 63);
        instance.set(13, ((int) (j << 1)) & 62);
        instance.set(14, 0);
        return instance.getTime().getTime();
    }

    public static Date fromDosTime(ZipLong zipLong) {
        return new Date(dosToJavaTime(zipLong.getValue()));
    }

    private static String getUnicodeStringIfOriginalMatches(AbstractUnicodeExtraField abstractUnicodeExtraField, byte[] bArr) {
        if (abstractUnicodeExtraField != null) {
            CRC32 crc32 = new CRC32();
            crc32.update(bArr);
            if (crc32.getValue() == abstractUnicodeExtraField.getNameCRC32()) {
                try {
                    return ZipEncodingHelper.UTF8_ZIP_ENCODING.decode(abstractUnicodeExtraField.getUnicodeName());
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    static BigInteger longToBig(long j) {
        int i = (j > -2147483648L ? 1 : (j == -2147483648L ? 0 : -1));
        if (i >= 0) {
            if (j < 0 && i >= 0) {
                j = adjustToLong((int) j);
            }
            return BigInteger.valueOf(j);
        }
        throw new IllegalArgumentException("Negative longs < -2^31 not permitted: [" + j + "]");
    }

    public static byte[] reverse(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < bArr.length / 2; i++) {
            byte b = bArr[i];
            int i2 = (length - 1) - i;
            bArr[i] = bArr[i2];
            bArr[i2] = b;
        }
        return bArr;
    }

    static void setNameAndCommentFromExtraFields(ZipArchiveEntry zipArchiveEntry, byte[] bArr, byte[] bArr2) {
        String unicodeStringIfOriginalMatches;
        String name = zipArchiveEntry.getName();
        String unicodeStringIfOriginalMatches2 = getUnicodeStringIfOriginalMatches((UnicodePathExtraField) zipArchiveEntry.getExtraField(UnicodePathExtraField.UPATH_ID), bArr);
        if (unicodeStringIfOriginalMatches2 != null && !name.equals(unicodeStringIfOriginalMatches2)) {
            zipArchiveEntry.setName(unicodeStringIfOriginalMatches2);
        }
        if (bArr2 != null && bArr2.length > 0 && (unicodeStringIfOriginalMatches = getUnicodeStringIfOriginalMatches((UnicodeCommentExtraField) zipArchiveEntry.getExtraField(UnicodeCommentExtraField.UCOM_ID), bArr2)) != null) {
            zipArchiveEntry.setComment(unicodeStringIfOriginalMatches);
        }
    }

    public static int signedByteToUnsignedInt(byte b) {
        return b >= 0 ? b : b + 256;
    }

    private static boolean supportsEncryptionOf(ZipArchiveEntry zipArchiveEntry) {
        return !zipArchiveEntry.getGeneralPurposeBit().usesEncryption();
    }

    private static boolean supportsMethodOf(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getMethod() == 0 || zipArchiveEntry.getMethod() == ZipMethod.UNSHRINKING.getCode() || zipArchiveEntry.getMethod() == ZipMethod.IMPLODING.getCode() || zipArchiveEntry.getMethod() == 8 || zipArchiveEntry.getMethod() == ZipMethod.BZIP2.getCode();
    }

    public static ZipLong toDosTime(Date date) {
        return new ZipLong(toDosTime(date.getTime()));
    }

    public static void toDosTime(long j, byte[] bArr, int i) {
        toDosTime(Calendar.getInstance(), j, bArr, i);
    }

    static void toDosTime(Calendar calendar, long j, byte[] bArr, int i) {
        calendar.setTimeInMillis(j);
        int i2 = calendar.get(1);
        if (i2 < 1980) {
            byte[] bArr2 = DOS_TIME_MIN;
            System.arraycopy(bArr2, 0, bArr, i, bArr2.length);
            return;
        }
        int i3 = calendar.get(2);
        int i4 = (i3 + 1) << 21;
        ZipLong.putLong((long) ((calendar.get(13) >> 1) | i4 | ((i2 - 1980) << 25) | (calendar.get(5) << 16) | (calendar.get(11) << 11) | (calendar.get(12) << 5)), bArr, i);
    }

    public static byte[] toDosTime(long j) {
        byte[] bArr = new byte[4];
        toDosTime(j, bArr, 0);
        return bArr;
    }

    public static byte unsignedIntToSignedByte(int i) {
        if (i <= 255 && i >= 0) {
            return i < 128 ? (byte) i : (byte) (i + InputDeviceCompat.SOURCE_ANY);
        }
        throw new IllegalArgumentException("Can only convert non-negative integers between [0,255] to byte: [" + i + "]");
    }
}
