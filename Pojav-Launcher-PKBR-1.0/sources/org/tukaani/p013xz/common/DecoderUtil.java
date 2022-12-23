package org.tukaani.p013xz.common;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import kotlin.UByte;
import org.tukaani.p013xz.C0927XZ;
import org.tukaani.p013xz.CorruptedInputException;
import org.tukaani.p013xz.UnsupportedOptionsException;
import org.tukaani.p013xz.XZFormatException;

/* renamed from: org.tukaani.xz.common.DecoderUtil */
public class DecoderUtil extends Util {
    public static boolean areStreamFlagsEqual(StreamFlags streamFlags, StreamFlags streamFlags2) {
        return streamFlags.checkType == streamFlags2.checkType;
    }

    private static StreamFlags decodeStreamFlags(byte[] bArr, int i) throws UnsupportedOptionsException {
        if (bArr[i] == 0) {
            int i2 = i + 1;
            if ((bArr[i2] & UByte.MAX_VALUE) < 16) {
                StreamFlags streamFlags = new StreamFlags();
                streamFlags.checkType = bArr[i2];
                return streamFlags;
            }
        }
        throw new UnsupportedOptionsException();
    }

    public static StreamFlags decodeStreamFooter(byte[] bArr) throws IOException {
        if (bArr[10] != C0927XZ.FOOTER_MAGIC[0] || bArr[11] != C0927XZ.FOOTER_MAGIC[1]) {
            throw new CorruptedInputException("XZ Stream Footer is corrupt");
        } else if (isCRC32Valid(bArr, 4, 6, 0)) {
            try {
                StreamFlags decodeStreamFlags = decodeStreamFlags(bArr, 8);
                decodeStreamFlags.backwardSize = 0;
                for (int i = 0; i < 4; i++) {
                    decodeStreamFlags.backwardSize |= (long) ((bArr[i + 4] & UByte.MAX_VALUE) << (i * 8));
                }
                decodeStreamFlags.backwardSize = (decodeStreamFlags.backwardSize + 1) * 4;
                return decodeStreamFlags;
            } catch (UnsupportedOptionsException e) {
                throw new UnsupportedOptionsException("Unsupported options in XZ Stream Footer");
            }
        } else {
            throw new CorruptedInputException("XZ Stream Footer is corrupt");
        }
    }

    public static StreamFlags decodeStreamHeader(byte[] bArr) throws IOException {
        int i = 0;
        while (i < C0927XZ.HEADER_MAGIC.length) {
            if (bArr[i] == C0927XZ.HEADER_MAGIC[i]) {
                i++;
            } else {
                throw new XZFormatException();
            }
        }
        if (isCRC32Valid(bArr, C0927XZ.HEADER_MAGIC.length, 2, C0927XZ.HEADER_MAGIC.length + 2)) {
            try {
                return decodeStreamFlags(bArr, C0927XZ.HEADER_MAGIC.length);
            } catch (UnsupportedOptionsException e) {
                throw new UnsupportedOptionsException("Unsupported options in XZ Stream Header");
            }
        } else {
            throw new CorruptedInputException("XZ Stream Header is corrupt");
        }
    }

    public static long decodeVLI(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            long j = (long) (read & 127);
            int i = 0;
            while ((read & 128) != 0) {
                i++;
                if (i < 9) {
                    read = inputStream.read();
                    if (read == -1) {
                        throw new EOFException();
                    } else if (read != 0) {
                        j |= ((long) (read & 127)) << (i * 7);
                    } else {
                        throw new CorruptedInputException();
                    }
                } else {
                    throw new CorruptedInputException();
                }
            }
            return j;
        }
        throw new EOFException();
    }

    public static boolean isCRC32Valid(byte[] bArr, int i, int i2, int i3) {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr, i, i2);
        long value = crc32.getValue();
        for (int i4 = 0; i4 < 4; i4++) {
            if (((byte) ((int) (value >>> (i4 * 8)))) != bArr[i3 + i4]) {
                return false;
            }
        }
        return true;
    }
}
