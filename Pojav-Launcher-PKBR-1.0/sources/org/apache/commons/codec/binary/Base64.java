package org.apache.commons.codec.binary;

import java.math.BigInteger;
import java.util.Objects;
import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.compress.archivers.tar.TarConstants;

public class Base64 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    static final byte[] CHUNK_SEPARATOR = {13, 10};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR};
    private static final int MASK_2BITS = 3;
    private static final int MASK_4BITS = 15;
    private static final int MASK_6BITS = 63;
    private static final byte[] STANDARD_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 43, 47};
    private static final byte[] URL_SAFE_ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 45, 95};
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    public Base64() {
        this(0);
    }

    public Base64(boolean urlSafe) {
        this(76, CHUNK_SEPARATOR, urlSafe);
    }

    public Base64(int lineLength) {
        this(lineLength, CHUNK_SEPARATOR);
    }

    public Base64(int lineLength, byte[] lineSeparator2) {
        this(lineLength, lineSeparator2, false);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Base64(int lineLength, byte[] lineSeparator2, boolean urlSafe) {
        super(3, 4, lineLength, lineSeparator2 == null ? 0 : lineSeparator2.length);
        this.decodeTable = DECODE_TABLE;
        if (lineSeparator2 == null) {
            this.encodeSize = 4;
            this.lineSeparator = null;
        } else if (containsAlphabetOrPad(lineSeparator2)) {
            throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + StringUtils.newStringUtf8(lineSeparator2) + "]");
        } else if (lineLength > 0) {
            this.encodeSize = lineSeparator2.length + 4;
            byte[] bArr = new byte[lineSeparator2.length];
            this.lineSeparator = bArr;
            System.arraycopy(lineSeparator2, 0, bArr, 0, lineSeparator2.length);
        } else {
            this.encodeSize = 4;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    }

    public boolean isUrlSafe() {
        return this.encodeTable == URL_SAFE_ENCODE_TABLE;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v6, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v7, resolved type: byte} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(byte[] r9, int r10, int r11, org.apache.commons.codec.binary.BaseNCodec.Context r12) {
        /*
            r8 = this;
            boolean r0 = r12.eof
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r1 = 1
            if (r11 >= 0) goto L_0x00e7
            r12.eof = r1
            int r1 = r12.modulus
            if (r1 != 0) goto L_0x0014
            int r1 = r8.lineLength
            if (r1 != 0) goto L_0x0014
            return
        L_0x0014:
            int r1 = r8.encodeSize
            byte[] r1 = r8.ensureBufferSize(r1, r12)
            int r2 = r12.pos
            int r3 = r12.modulus
            switch(r3) {
                case 0: goto L_0x00c2;
                case 1: goto L_0x0083;
                case 2: goto L_0x003c;
                default: goto L_0x0021;
            }
        L_0x0021:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Impossible modulus "
            java.lang.StringBuilder r3 = r3.append(r4)
            int r4 = r12.modulus
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        L_0x003c:
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte[] r4 = r8.encodeTable
            int r5 = r12.ibitWorkArea
            int r5 = r5 >> 10
            r5 = r5 & 63
            byte r4 = r4[r5]
            r1[r3] = r4
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte[] r4 = r8.encodeTable
            int r5 = r12.ibitWorkArea
            int r5 = r5 >> 4
            r5 = r5 & 63
            byte r4 = r4[r5]
            r1[r3] = r4
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte[] r4 = r8.encodeTable
            int r5 = r12.ibitWorkArea
            int r5 = r5 << 2
            r5 = r5 & 63
            byte r4 = r4[r5]
            r1[r3] = r4
            byte[] r3 = r8.encodeTable
            byte[] r4 = STANDARD_ENCODE_TABLE
            if (r3 != r4) goto L_0x00c3
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte r4 = r8.pad
            r1[r3] = r4
            goto L_0x00c3
        L_0x0083:
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte[] r4 = r8.encodeTable
            int r5 = r12.ibitWorkArea
            int r5 = r5 >> 2
            r5 = r5 & 63
            byte r4 = r4[r5]
            r1[r3] = r4
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte[] r4 = r8.encodeTable
            int r5 = r12.ibitWorkArea
            int r5 = r5 << 4
            r5 = r5 & 63
            byte r4 = r4[r5]
            r1[r3] = r4
            byte[] r3 = r8.encodeTable
            byte[] r4 = STANDARD_ENCODE_TABLE
            if (r3 != r4) goto L_0x00c3
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte r4 = r8.pad
            r1[r3] = r4
            int r3 = r12.pos
            int r4 = r3 + 1
            r12.pos = r4
            byte r4 = r8.pad
            r1[r3] = r4
            goto L_0x00c3
        L_0x00c2:
        L_0x00c3:
            int r3 = r12.currentLinePos
            int r4 = r12.pos
            int r4 = r4 - r2
            int r3 = r3 + r4
            r12.currentLinePos = r3
            int r3 = r8.lineLength
            if (r3 <= 0) goto L_0x00e5
            int r3 = r12.currentLinePos
            if (r3 <= 0) goto L_0x00e5
            byte[] r3 = r8.lineSeparator
            int r4 = r12.pos
            byte[] r5 = r8.lineSeparator
            int r5 = r5.length
            java.lang.System.arraycopy(r3, r0, r1, r4, r5)
            int r0 = r12.pos
            byte[] r3 = r8.lineSeparator
            int r3 = r3.length
            int r0 = r0 + r3
            r12.pos = r0
        L_0x00e5:
            goto L_0x0179
        L_0x00e7:
            r2 = 0
        L_0x00e8:
            if (r2 >= r11) goto L_0x0179
            int r3 = r8.encodeSize
            byte[] r3 = r8.ensureBufferSize(r3, r12)
            int r4 = r12.modulus
            int r4 = r4 + r1
            int r4 = r4 % 3
            r12.modulus = r4
            int r4 = r10 + 1
            byte r10 = r9[r10]
            if (r10 >= 0) goto L_0x00ff
            int r10 = r10 + 256
        L_0x00ff:
            int r5 = r12.ibitWorkArea
            int r5 = r5 << 8
            int r5 = r5 + r10
            r12.ibitWorkArea = r5
            int r5 = r12.modulus
            if (r5 != 0) goto L_0x0174
            int r5 = r12.pos
            int r6 = r5 + 1
            r12.pos = r6
            byte[] r6 = r8.encodeTable
            int r7 = r12.ibitWorkArea
            int r7 = r7 >> 18
            r7 = r7 & 63
            byte r6 = r6[r7]
            r3[r5] = r6
            int r5 = r12.pos
            int r6 = r5 + 1
            r12.pos = r6
            byte[] r6 = r8.encodeTable
            int r7 = r12.ibitWorkArea
            int r7 = r7 >> 12
            r7 = r7 & 63
            byte r6 = r6[r7]
            r3[r5] = r6
            int r5 = r12.pos
            int r6 = r5 + 1
            r12.pos = r6
            byte[] r6 = r8.encodeTable
            int r7 = r12.ibitWorkArea
            int r7 = r7 >> 6
            r7 = r7 & 63
            byte r6 = r6[r7]
            r3[r5] = r6
            int r5 = r12.pos
            int r6 = r5 + 1
            r12.pos = r6
            byte[] r6 = r8.encodeTable
            int r7 = r12.ibitWorkArea
            r7 = r7 & 63
            byte r6 = r6[r7]
            r3[r5] = r6
            int r5 = r12.currentLinePos
            int r5 = r5 + 4
            r12.currentLinePos = r5
            int r5 = r8.lineLength
            if (r5 <= 0) goto L_0x0174
            int r5 = r8.lineLength
            int r6 = r12.currentLinePos
            if (r5 > r6) goto L_0x0174
            byte[] r5 = r8.lineSeparator
            int r6 = r12.pos
            byte[] r7 = r8.lineSeparator
            int r7 = r7.length
            java.lang.System.arraycopy(r5, r0, r3, r6, r7)
            int r5 = r12.pos
            byte[] r6 = r8.lineSeparator
            int r6 = r6.length
            int r5 = r5 + r6
            r12.pos = r5
            r12.currentLinePos = r0
        L_0x0174:
            int r2 = r2 + 1
            r10 = r4
            goto L_0x00e8
        L_0x0179:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.binary.Base64.encode(byte[], int, int, org.apache.commons.codec.binary.BaseNCodec$Context):void");
    }

    /* access modifiers changed from: package-private */
    public void decode(byte[] in, int inPos, int inAvail, BaseNCodec.Context context) {
        byte result;
        if (!context.eof) {
            if (inAvail < 0) {
                context.eof = true;
            }
            int i = 0;
            while (true) {
                if (i >= inAvail) {
                    break;
                }
                byte[] buffer = ensureBufferSize(this.decodeSize, context);
                int inPos2 = inPos + 1;
                byte b = in[inPos];
                if (b == this.pad) {
                    context.eof = true;
                    int i2 = inPos2;
                    break;
                }
                if (b >= 0) {
                    byte[] bArr = DECODE_TABLE;
                    if (b < bArr.length && (result = bArr[b]) >= 0) {
                        context.modulus = (context.modulus + 1) % 4;
                        context.ibitWorkArea = (context.ibitWorkArea << 6) + result;
                        if (context.modulus == 0) {
                            int i3 = context.pos;
                            context.pos = i3 + 1;
                            buffer[i3] = (byte) ((context.ibitWorkArea >> 16) & 255);
                            int i4 = context.pos;
                            context.pos = i4 + 1;
                            buffer[i4] = (byte) ((context.ibitWorkArea >> 8) & 255);
                            int i5 = context.pos;
                            context.pos = i5 + 1;
                            buffer[i5] = (byte) (context.ibitWorkArea & 255);
                        }
                    }
                }
                i++;
                inPos = inPos2;
            }
            if (context.eof && context.modulus != 0) {
                byte[] buffer2 = ensureBufferSize(this.decodeSize, context);
                switch (context.modulus) {
                    case 1:
                        return;
                    case 2:
                        validateCharacter(15, context);
                        context.ibitWorkArea >>= 4;
                        int i6 = context.pos;
                        context.pos = i6 + 1;
                        buffer2[i6] = (byte) (context.ibitWorkArea & 255);
                        return;
                    case 3:
                        validateCharacter(3, context);
                        context.ibitWorkArea >>= 2;
                        int i7 = context.pos;
                        context.pos = i7 + 1;
                        buffer2[i7] = (byte) ((context.ibitWorkArea >> 8) & 255);
                        int i8 = context.pos;
                        context.pos = i8 + 1;
                        buffer2[i8] = (byte) (context.ibitWorkArea & 255);
                        return;
                    default:
                        throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
        }
    }

    @Deprecated
    public static boolean isArrayByteBase64(byte[] arrayOctet) {
        return isBase64(arrayOctet);
    }

    public static boolean isBase64(byte octet) {
        if (octet != 61) {
            if (octet >= 0) {
                byte[] bArr = DECODE_TABLE;
                if (octet >= bArr.length || bArr[octet] == -1) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    public static boolean isBase64(String base64) {
        return isBase64(StringUtils.getBytesUtf8(base64));
    }

    public static boolean isBase64(byte[] arrayOctet) {
        for (int i = 0; i < arrayOctet.length; i++) {
            if (!isBase64(arrayOctet[i]) && !isWhiteSpace(arrayOctet[i])) {
                return false;
            }
        }
        return true;
    }

    public static byte[] encodeBase64(byte[] binaryData) {
        return encodeBase64(binaryData, false);
    }

    public static String encodeBase64String(byte[] binaryData) {
        return StringUtils.newStringUsAscii(encodeBase64(binaryData, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] binaryData) {
        return encodeBase64(binaryData, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] binaryData) {
        return StringUtils.newStringUsAscii(encodeBase64(binaryData, false, true));
    }

    public static byte[] encodeBase64Chunked(byte[] binaryData) {
        return encodeBase64(binaryData, true);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked) {
        return encodeBase64(binaryData, isChunked, false);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe) {
        return encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
    }

    public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize) {
        Base64 b64;
        if (binaryData == null || binaryData.length == 0) {
            return binaryData;
        }
        if (!isChunked) {
            b64 = new Base64(0, CHUNK_SEPARATOR, urlSafe);
        }
        long len = b64.getEncodedLength(binaryData);
        if (len <= ((long) maxResultSize)) {
            return b64.encode(binaryData);
        }
        throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + len + ") than the specified maximum size of " + maxResultSize);
    }

    public static byte[] decodeBase64(String base64String) {
        return new Base64().decode(base64String);
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        return new Base64().decode(base64Data);
    }

    public static BigInteger decodeInteger(byte[] pArray) {
        return new BigInteger(1, decodeBase64(pArray));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        Objects.requireNonNull(bigInteger, "bigInteger");
        return encodeBase64(toIntegerBytes(bigInteger), false);
    }

    static byte[] toIntegerBytes(BigInteger bigInt) {
        int bitlen = ((bigInt.bitLength() + 7) >> 3) << 3;
        byte[] bigBytes = bigInt.toByteArray();
        if (bigInt.bitLength() % 8 != 0 && (bigInt.bitLength() / 8) + 1 == bitlen / 8) {
            return bigBytes;
        }
        int startSrc = 0;
        int len = bigBytes.length;
        if (bigInt.bitLength() % 8 == 0) {
            startSrc = 1;
            len--;
        }
        byte[] resizedBytes = new byte[(bitlen / 8)];
        System.arraycopy(bigBytes, startSrc, resizedBytes, (bitlen / 8) - len, len);
        return resizedBytes;
    }

    /* access modifiers changed from: protected */
    public boolean isInAlphabet(byte octet) {
        if (octet >= 0) {
            byte[] bArr = this.decodeTable;
            return octet < bArr.length && bArr[octet] != -1;
        }
    }

    private static void validateCharacter(int emptyBitsMask, BaseNCodec.Context context) {
        if ((context.ibitWorkArea & emptyBitsMask) != 0) {
            throw new IllegalArgumentException("Last encoded character (before the paddings if any) is a valid base 64 alphabet but not a possible value. Expected the discarded bits to be zero.");
        }
    }
}
