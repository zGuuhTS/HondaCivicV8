package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.compress.archivers.tar.TarConstants;

public class Base32 extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 5;
    private static final int BYTES_PER_ENCODED_BLOCK = 8;
    private static final int BYTES_PER_UNENCODED_BLOCK = 5;
    private static final byte[] CHUNK_SEPARATOR = {13, 10};
    private static final byte[] DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    private static final byte[] ENCODE_TABLE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG};
    private static final byte[] HEX_DECODE_TABLE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    private static final byte[] HEX_ENCODE_TABLE = {TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86};
    private static final long MASK_1BITS = 1;
    private static final long MASK_2BITS = 3;
    private static final long MASK_3BITS = 7;
    private static final long MASK_4BITS = 15;
    private static final int MASK_5BITS = 31;
    private static final long MASK_6BITS = 63;
    private static final long MASK_7BITS = 127;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    public Base32() {
        this(false);
    }

    public Base32(byte pad) {
        this(false, pad);
    }

    public Base32(boolean useHex) {
        this(0, (byte[]) null, useHex, (byte) 61);
    }

    public Base32(boolean useHex, byte pad) {
        this(0, (byte[]) null, useHex, pad);
    }

    public Base32(int lineLength) {
        this(lineLength, CHUNK_SEPARATOR);
    }

    public Base32(int lineLength, byte[] lineSeparator2) {
        this(lineLength, lineSeparator2, false, (byte) 61);
    }

    public Base32(int lineLength, byte[] lineSeparator2, boolean useHex) {
        this(lineLength, lineSeparator2, useHex, (byte) 61);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Base32(int lineLength, byte[] lineSeparator2, boolean useHex, byte pad) {
        super(5, 8, lineLength, lineSeparator2 == null ? 0 : lineSeparator2.length, pad);
        if (useHex) {
            this.encodeTable = HEX_ENCODE_TABLE;
            this.decodeTable = HEX_DECODE_TABLE;
        } else {
            this.encodeTable = ENCODE_TABLE;
            this.decodeTable = DECODE_TABLE;
        }
        if (lineLength <= 0) {
            this.encodeSize = 8;
            this.lineSeparator = null;
        } else if (lineSeparator2 == null) {
            throw new IllegalArgumentException("lineLength " + lineLength + " > 0, but lineSeparator is null");
        } else if (!containsAlphabetOrPad(lineSeparator2)) {
            this.encodeSize = lineSeparator2.length + 8;
            byte[] bArr = new byte[lineSeparator2.length];
            this.lineSeparator = bArr;
            System.arraycopy(lineSeparator2, 0, bArr, 0, lineSeparator2.length);
        } else {
            throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + StringUtils.newStringUtf8(lineSeparator2) + "]");
        }
        this.decodeSize = this.encodeSize - 1;
        if (isInAlphabet(pad) || isWhiteSpace(pad)) {
            throw new IllegalArgumentException("pad must not be in alphabet or whitespace");
        }
    }

    /* access modifiers changed from: package-private */
    public void decode(byte[] input, int inPos, int inAvail, BaseNCodec.Context context) {
        int i = inAvail;
        BaseNCodec.Context context2 = context;
        if (!context2.eof) {
            boolean z = true;
            if (i < 0) {
                context2.eof = true;
            }
            int i2 = 0;
            int inPos2 = inPos;
            while (true) {
                if (i2 >= i) {
                    break;
                }
                int inPos3 = inPos2 + 1;
                byte b = input[inPos2];
                if (b == this.pad) {
                    context2.eof = z;
                    int i3 = inPos3;
                    break;
                }
                byte[] buffer = ensureBufferSize(this.decodeSize, context2);
                if (b >= 0) {
                    byte[] bArr = this.decodeTable;
                    if (b < bArr.length) {
                        byte result = bArr[b];
                        if (result >= 0) {
                            context2.modulus = (context2.modulus + (z ? 1 : 0)) % 8;
                            byte b2 = b;
                            context2.lbitWorkArea = (context2.lbitWorkArea << 5) + ((long) result);
                            if (context2.modulus == 0) {
                                int i4 = context2.pos;
                                context2.pos = i4 + 1;
                                buffer[i4] = (byte) ((int) ((context2.lbitWorkArea >> 32) & 255));
                                int i5 = context2.pos;
                                context2.pos = i5 + 1;
                                buffer[i5] = (byte) ((int) ((context2.lbitWorkArea >> 24) & 255));
                                int i6 = context2.pos;
                                context2.pos = i6 + 1;
                                buffer[i6] = (byte) ((int) ((context2.lbitWorkArea >> 16) & 255));
                                int i7 = context2.pos;
                                context2.pos = i7 + 1;
                                buffer[i7] = (byte) ((int) ((context2.lbitWorkArea >> 8) & 255));
                                int i8 = context2.pos;
                                context2.pos = i8 + 1;
                                buffer[i8] = (byte) ((int) (context2.lbitWorkArea & 255));
                            }
                        }
                        i2++;
                        inPos2 = inPos3;
                        z = true;
                    }
                }
                i2++;
                inPos2 = inPos3;
                z = true;
            }
            if (context2.eof && context2.modulus >= 2) {
                byte[] buffer2 = ensureBufferSize(this.decodeSize, context2);
                switch (context2.modulus) {
                    case 2:
                        validateCharacter(3, context2);
                        int i9 = context2.pos;
                        context2.pos = i9 + 1;
                        buffer2[i9] = (byte) ((int) ((context2.lbitWorkArea >> 2) & 255));
                        return;
                    case 3:
                        validateCharacter(MASK_7BITS, context2);
                        int i10 = context2.pos;
                        context2.pos = i10 + 1;
                        buffer2[i10] = (byte) ((int) ((context2.lbitWorkArea >> 7) & 255));
                        return;
                    case 4:
                        validateCharacter(MASK_4BITS, context2);
                        context2.lbitWorkArea >>= 4;
                        int i11 = context2.pos;
                        context2.pos = i11 + 1;
                        buffer2[i11] = (byte) ((int) ((context2.lbitWorkArea >> 8) & 255));
                        int i12 = context2.pos;
                        context2.pos = i12 + 1;
                        buffer2[i12] = (byte) ((int) (context2.lbitWorkArea & 255));
                        return;
                    case 5:
                        validateCharacter(MASK_1BITS, context2);
                        context2.lbitWorkArea >>= MASK_1BITS;
                        int i13 = context2.pos;
                        context2.pos = i13 + 1;
                        buffer2[i13] = (byte) ((int) ((context2.lbitWorkArea >> 16) & 255));
                        int i14 = context2.pos;
                        context2.pos = i14 + 1;
                        buffer2[i14] = (byte) ((int) ((context2.lbitWorkArea >> 8) & 255));
                        int i15 = context2.pos;
                        context2.pos = i15 + 1;
                        buffer2[i15] = (byte) ((int) (context2.lbitWorkArea & 255));
                        return;
                    case 6:
                        validateCharacter(MASK_6BITS, context2);
                        context2.lbitWorkArea >>= 6;
                        int i16 = context2.pos;
                        context2.pos = i16 + 1;
                        buffer2[i16] = (byte) ((int) ((context2.lbitWorkArea >> 16) & 255));
                        int i17 = context2.pos;
                        context2.pos = i17 + 1;
                        buffer2[i17] = (byte) ((int) ((context2.lbitWorkArea >> 8) & 255));
                        int i18 = context2.pos;
                        context2.pos = i18 + 1;
                        buffer2[i18] = (byte) ((int) (context2.lbitWorkArea & 255));
                        return;
                    case 7:
                        validateCharacter(7, context2);
                        context2.lbitWorkArea >>= 3;
                        int i19 = context2.pos;
                        context2.pos = i19 + 1;
                        buffer2[i19] = (byte) ((int) ((context2.lbitWorkArea >> 24) & 255));
                        int i20 = context2.pos;
                        context2.pos = i20 + 1;
                        buffer2[i20] = (byte) ((int) ((context2.lbitWorkArea >> 16) & 255));
                        int i21 = context2.pos;
                        context2.pos = i21 + 1;
                        buffer2[i21] = (byte) ((int) ((context2.lbitWorkArea >> 8) & 255));
                        int i22 = context2.pos;
                        context2.pos = i22 + 1;
                        buffer2[i22] = (byte) ((int) (context2.lbitWorkArea & 255));
                        return;
                    default:
                        throw new IllegalStateException("Impossible modulus " + context2.modulus);
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: byte} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(byte[] r17, int r18, int r19, org.apache.commons.codec.binary.BaseNCodec.Context r20) {
        /*
            r16 = this;
            r0 = r16
            r1 = r19
            r2 = r20
            boolean r3 = r2.eof
            if (r3 == 0) goto L_0x000b
            return
        L_0x000b:
            r3 = 0
            r4 = 1
            if (r1 >= 0) goto L_0x0258
            r2.eof = r4
            int r5 = r2.modulus
            if (r5 != 0) goto L_0x001a
            int r5 = r0.lineLength
            if (r5 != 0) goto L_0x001a
            return
        L_0x001a:
            int r5 = r0.encodeSize
            byte[] r5 = r0.ensureBufferSize(r5, r2)
            int r6 = r2.pos
            int r7 = r2.modulus
            r8 = 4
            r9 = 2
            r10 = 3
            switch(r7) {
                case 0: goto L_0x0231;
                case 1: goto L_0x01ce;
                case 2: goto L_0x0159;
                case 3: goto L_0x00d9;
                case 4: goto L_0x0045;
                default: goto L_0x002a;
            }
        L_0x002a:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r7 = "Impossible modulus "
            java.lang.StringBuilder r4 = r4.append(r7)
            int r7 = r2.modulus
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0045:
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r11 = r2.lbitWorkArea
            r8 = 27
            long r11 = r11 >> r8
            int r8 = (int) r11
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r11 = r2.lbitWorkArea
            r8 = 22
            long r11 = r11 >> r8
            int r8 = (int) r11
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r11 = r2.lbitWorkArea
            r8 = 17
            long r11 = r11 >> r8
            int r8 = (int) r11
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r11 = r2.lbitWorkArea
            r8 = 12
            long r11 = r11 >> r8
            int r8 = (int) r11
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r11 = r2.lbitWorkArea
            r8 = 7
            long r11 = r11 >> r8
            int r8 = (int) r11
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r11 = r2.lbitWorkArea
            long r8 = r11 >> r9
            int r8 = (int) r8
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r8 = r2.lbitWorkArea
            long r8 = r8 << r10
            int r8 = (int) r8
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            goto L_0x0232
        L_0x00d9:
            int r7 = r2.pos
            int r9 = r7 + 1
            r2.pos = r9
            byte[] r9 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            r12 = 19
            long r10 = r10 >> r12
            int r10 = (int) r10
            r10 = r10 & 31
            byte r9 = r9[r10]
            r5[r7] = r9
            int r7 = r2.pos
            int r9 = r7 + 1
            r2.pos = r9
            byte[] r9 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            r12 = 14
            long r10 = r10 >> r12
            int r10 = (int) r10
            r10 = r10 & 31
            byte r9 = r9[r10]
            r5[r7] = r9
            int r7 = r2.pos
            int r9 = r7 + 1
            r2.pos = r9
            byte[] r9 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            r12 = 9
            long r10 = r10 >> r12
            int r10 = (int) r10
            r10 = r10 & 31
            byte r9 = r9[r10]
            r5[r7] = r9
            int r7 = r2.pos
            int r9 = r7 + 1
            r2.pos = r9
            byte[] r9 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            long r10 = r10 >> r8
            int r8 = (int) r10
            r8 = r8 & 31
            byte r8 = r9[r8]
            r5[r7] = r8
            int r7 = r2.pos
            int r8 = r7 + 1
            r2.pos = r8
            byte[] r8 = r0.encodeTable
            long r9 = r2.lbitWorkArea
            long r9 = r9 << r4
            int r4 = (int) r9
            r4 = r4 & 31
            byte r4 = r8[r4]
            r5[r7] = r4
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            goto L_0x0232
        L_0x0159:
            int r7 = r2.pos
            int r9 = r7 + 1
            r2.pos = r9
            byte[] r9 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            r12 = 11
            long r10 = r10 >> r12
            int r10 = (int) r10
            r10 = r10 & 31
            byte r9 = r9[r10]
            r5[r7] = r9
            int r7 = r2.pos
            int r9 = r7 + 1
            r2.pos = r9
            byte[] r9 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            r12 = 6
            long r10 = r10 >> r12
            int r10 = (int) r10
            r10 = r10 & 31
            byte r9 = r9[r10]
            r5[r7] = r9
            int r7 = r2.pos
            int r9 = r7 + 1
            r2.pos = r9
            byte[] r9 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            long r10 = r10 >> r4
            int r4 = (int) r10
            r4 = r4 & 31
            byte r4 = r9[r4]
            r5[r7] = r4
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r9 = r2.lbitWorkArea
            long r8 = r9 << r8
            int r8 = (int) r8
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            goto L_0x0232
        L_0x01ce:
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r11 = r2.lbitWorkArea
            long r10 = r11 >> r10
            int r8 = (int) r10
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte[] r7 = r0.encodeTable
            long r10 = r2.lbitWorkArea
            long r8 = r10 << r9
            int r8 = (int) r8
            r8 = r8 & 31
            byte r7 = r7[r8]
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            int r4 = r2.pos
            int r7 = r4 + 1
            r2.pos = r7
            byte r7 = r0.pad
            r5[r4] = r7
            goto L_0x0232
        L_0x0231:
        L_0x0232:
            int r4 = r2.currentLinePos
            int r7 = r2.pos
            int r7 = r7 - r6
            int r4 = r4 + r7
            r2.currentLinePos = r4
            int r4 = r0.lineLength
            if (r4 <= 0) goto L_0x0254
            int r4 = r2.currentLinePos
            if (r4 <= 0) goto L_0x0254
            byte[] r4 = r0.lineSeparator
            int r7 = r2.pos
            byte[] r8 = r0.lineSeparator
            int r8 = r8.length
            java.lang.System.arraycopy(r4, r3, r5, r7, r8)
            int r3 = r2.pos
            byte[] r4 = r0.lineSeparator
            int r4 = r4.length
            int r3 = r3 + r4
            r2.pos = r3
        L_0x0254:
            r5 = r18
            goto L_0x0343
        L_0x0258:
            r5 = 0
            r6 = r5
            r5 = r18
        L_0x025c:
            if (r6 >= r1) goto L_0x0343
            int r7 = r0.encodeSize
            byte[] r7 = r0.ensureBufferSize(r7, r2)
            int r8 = r2.modulus
            int r8 = r8 + r4
            r9 = 5
            int r8 = r8 % r9
            r2.modulus = r8
            int r8 = r5 + 1
            byte r5 = r17[r5]
            if (r5 >= 0) goto L_0x0273
            int r5 = r5 + 256
        L_0x0273:
            long r10 = r2.lbitWorkArea
            r12 = 8
            long r10 = r10 << r12
            long r13 = (long) r5
            long r10 = r10 + r13
            r2.lbitWorkArea = r10
            int r10 = r2.modulus
            if (r10 != 0) goto L_0x033e
            int r10 = r2.pos
            int r11 = r10 + 1
            r2.pos = r11
            byte[] r11 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            r15 = 35
            long r13 = r13 >> r15
            int r13 = (int) r13
            r13 = r13 & 31
            byte r11 = r11[r13]
            r7[r10] = r11
            int r10 = r2.pos
            int r11 = r10 + 1
            r2.pos = r11
            byte[] r11 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            r15 = 30
            long r13 = r13 >> r15
            int r13 = (int) r13
            r13 = r13 & 31
            byte r11 = r11[r13]
            r7[r10] = r11
            int r10 = r2.pos
            int r11 = r10 + 1
            r2.pos = r11
            byte[] r11 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            r15 = 25
            long r13 = r13 >> r15
            int r13 = (int) r13
            r13 = r13 & 31
            byte r11 = r11[r13]
            r7[r10] = r11
            int r10 = r2.pos
            int r11 = r10 + 1
            r2.pos = r11
            byte[] r11 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            r15 = 20
            long r13 = r13 >> r15
            int r13 = (int) r13
            r13 = r13 & 31
            byte r11 = r11[r13]
            r7[r10] = r11
            int r10 = r2.pos
            int r11 = r10 + 1
            r2.pos = r11
            byte[] r11 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            r15 = 15
            long r13 = r13 >> r15
            int r13 = (int) r13
            r13 = r13 & 31
            byte r11 = r11[r13]
            r7[r10] = r11
            int r10 = r2.pos
            int r11 = r10 + 1
            r2.pos = r11
            byte[] r11 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            r15 = 10
            long r13 = r13 >> r15
            int r13 = (int) r13
            r13 = r13 & 31
            byte r11 = r11[r13]
            r7[r10] = r11
            int r10 = r2.pos
            int r11 = r10 + 1
            r2.pos = r11
            byte[] r11 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            long r13 = r13 >> r9
            int r9 = (int) r13
            r9 = r9 & 31
            byte r9 = r11[r9]
            r7[r10] = r9
            int r9 = r2.pos
            int r10 = r9 + 1
            r2.pos = r10
            byte[] r10 = r0.encodeTable
            long r13 = r2.lbitWorkArea
            int r11 = (int) r13
            r11 = r11 & 31
            byte r10 = r10[r11]
            r7[r9] = r10
            int r9 = r2.currentLinePos
            int r9 = r9 + r12
            r2.currentLinePos = r9
            int r9 = r0.lineLength
            if (r9 <= 0) goto L_0x033e
            int r9 = r0.lineLength
            int r10 = r2.currentLinePos
            if (r9 > r10) goto L_0x033e
            byte[] r9 = r0.lineSeparator
            int r10 = r2.pos
            byte[] r11 = r0.lineSeparator
            int r11 = r11.length
            java.lang.System.arraycopy(r9, r3, r7, r10, r11)
            int r9 = r2.pos
            byte[] r10 = r0.lineSeparator
            int r10 = r10.length
            int r9 = r9 + r10
            r2.pos = r9
            r2.currentLinePos = r3
        L_0x033e:
            int r6 = r6 + 1
            r5 = r8
            goto L_0x025c
        L_0x0343:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.binary.Base32.encode(byte[], int, int, org.apache.commons.codec.binary.BaseNCodec$Context):void");
    }

    public boolean isInAlphabet(byte octet) {
        if (octet >= 0) {
            byte[] bArr = this.decodeTable;
            return octet < bArr.length && bArr[octet] != -1;
        }
    }

    private static void validateCharacter(long emptyBitsMask, BaseNCodec.Context context) {
        if ((context.lbitWorkArea & emptyBitsMask) != 0) {
            throw new IllegalArgumentException("Last encoded character (before the paddings if any) is a valid base 32 alphabet but not a possible value. Expected the discarded bits to be zero.");
        }
    }
}
