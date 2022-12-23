package org.apache.commons.compress.compressors.bzip2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import kotlin.UByte;
import org.apache.commons.compress.compressors.CompressorInputStream;

public class BZip2CompressorInputStream extends CompressorInputStream implements BZip2Constants {
    private static final int EOF = 0;
    private static final int NO_RAND_PART_A_STATE = 5;
    private static final int NO_RAND_PART_B_STATE = 6;
    private static final int NO_RAND_PART_C_STATE = 7;
    private static final int RAND_PART_A_STATE = 2;
    private static final int RAND_PART_B_STATE = 3;
    private static final int RAND_PART_C_STATE = 4;
    private static final int START_BLOCK_STATE = 1;
    private boolean blockRandomised;
    private int blockSize100k;
    private int bsBuff;
    private int bsLive;
    private int computedBlockCRC;
    private int computedCombinedCRC;
    private final CRC crc;
    private int currentState;
    private Data data;
    private final boolean decompressConcatenated;

    /* renamed from: in */
    private InputStream f156in;
    private int last;
    private int nInUse;
    private int origPtr;
    private int storedBlockCRC;
    private int storedCombinedCRC;
    private int su_ch2;
    private int su_chPrev;
    private int su_count;
    private int su_i2;
    private int su_j2;
    private int su_rNToGo;
    private int su_rTPos;
    private int su_tPos;
    private char su_z;

    private static final class Data {
        final int[][] base = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 258}));
        final int[] cftab = new int[257];
        final char[] getAndMoveToFrontDecode_yy = new char[256];
        final boolean[] inUse = new boolean[256];
        final int[][] limit = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 258}));
        byte[] ll8;
        final int[] minLens = new int[6];
        final int[][] perm = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 258}));
        final byte[] recvDecodingTables_pos = new byte[6];
        final byte[] selector = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] selectorMtf = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] seqToUnseq = new byte[256];
        final char[][] temp_charArray2d = ((char[][]) Array.newInstance(Character.TYPE, new int[]{6, 258}));

        /* renamed from: tt */
        int[] f157tt;
        final int[] unzftab = new int[256];

        Data(int i) {
            this.ll8 = new byte[(i * BZip2Constants.BASEBLOCKSIZE)];
        }

        /* access modifiers changed from: package-private */
        public int[] initTT(int i) {
            int[] iArr = this.f157tt;
            if (iArr != null && iArr.length >= i) {
                return iArr;
            }
            int[] iArr2 = new int[i];
            this.f157tt = iArr2;
            return iArr2;
        }
    }

    public BZip2CompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public BZip2CompressorInputStream(InputStream inputStream, boolean z) throws IOException {
        this.crc = new CRC();
        this.currentState = 1;
        this.f156in = inputStream;
        this.decompressConcatenated = z;
        init(true);
        initBlock();
    }

    private boolean bsGetBit() throws IOException {
        return bsR(1) != 0;
    }

    private int bsGetInt() throws IOException {
        int bsR = bsR(8);
        int bsR2 = bsR(8);
        return (((((bsR << 8) | bsR2) << 8) | bsR(8)) << 8) | bsR(8);
    }

    private char bsGetUByte() throws IOException {
        return (char) bsR(8);
    }

    private int bsR(int i) throws IOException {
        int i2 = this.bsLive;
        int i3 = this.bsBuff;
        if (i2 < i) {
            InputStream inputStream = this.f156in;
            do {
                int read = inputStream.read();
                if (read >= 0) {
                    i3 = (i3 << 8) | read;
                    i2 += 8;
                } else {
                    throw new IOException("unexpected end of stream");
                }
            } while (i2 < i);
            this.bsBuff = i3;
        }
        int i4 = i2 - i;
        this.bsLive = i4;
        return ((1 << i) - 1) & (i3 >> i4);
    }

    private boolean complete() throws IOException {
        int bsGetInt = bsGetInt();
        this.storedCombinedCRC = bsGetInt;
        this.currentState = 0;
        this.data = null;
        if (bsGetInt == this.computedCombinedCRC) {
            return !this.decompressConcatenated || !init(false);
        }
        throw new IOException("BZip2 CRC error");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: int[][]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v0, resolved type: char[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v1, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void createHuffmanDecodingTables(int r19, int r20) {
        /*
            r18 = this;
            r0 = r18
            org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream$Data r1 = r0.data
            char[][] r2 = r1.temp_charArray2d
            int[] r3 = r1.minLens
            int[][] r4 = r1.limit
            int[][] r5 = r1.base
            int[][] r1 = r1.perm
            r6 = 0
            r7 = r20
            r8 = r6
        L_0x0012:
            if (r8 >= r7) goto L_0x003e
            r9 = r2[r8]
            r10 = 32
            r11 = r19
            r15 = r6
        L_0x001b:
            int r11 = r11 + -1
            if (r11 < 0) goto L_0x0028
            r12 = r9[r11]
            if (r12 <= r15) goto L_0x0024
            r15 = r12
        L_0x0024:
            if (r12 >= r10) goto L_0x001b
            r10 = r12
            goto L_0x001b
        L_0x0028:
            r11 = r4[r8]
            r12 = r5[r8]
            r13 = r1[r8]
            r14 = r2[r8]
            r9 = r15
            r15 = r10
            r16 = r9
            r17 = r19
            hbCreateDecodeTables(r11, r12, r13, r14, r15, r16, r17)
            r3[r8] = r10
            int r8 = r8 + 1
            goto L_0x0012
        L_0x003e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream.createHuffmanDecodingTables(int, int):void");
    }

    private void endBlock() throws IOException {
        int finalCRC = this.crc.getFinalCRC();
        this.computedBlockCRC = finalCRC;
        int i = this.storedBlockCRC;
        if (i == finalCRC) {
            int i2 = this.computedCombinedCRC;
            int i3 = (i2 >>> 31) | (i2 << 1);
            this.computedCombinedCRC = i3;
            this.computedCombinedCRC = finalCRC ^ i3;
            return;
        }
        int i4 = this.storedCombinedCRC;
        int i5 = (i4 >>> 31) | (i4 << 1);
        this.computedCombinedCRC = i5;
        this.computedCombinedCRC = i5 ^ i;
        throw new IOException("BZip2 CRC error");
    }

    private void getAndMoveToFrontDecode() throws IOException {
        int i;
        int i2;
        int i3;
        char c;
        int i4;
        BZip2CompressorInputStream bZip2CompressorInputStream = this;
        bZip2CompressorInputStream.origPtr = bZip2CompressorInputStream.bsR(24);
        recvDecodingTables();
        InputStream inputStream = bZip2CompressorInputStream.f156in;
        Data data2 = bZip2CompressorInputStream.data;
        byte[] bArr = data2.ll8;
        int[] iArr = data2.unzftab;
        byte[] bArr2 = data2.selector;
        byte[] bArr3 = data2.seqToUnseq;
        char[] cArr = data2.getAndMoveToFrontDecode_yy;
        int[] iArr2 = data2.minLens;
        int[][] iArr3 = data2.limit;
        int[][] iArr4 = data2.base;
        int[][] iArr5 = data2.perm;
        int i5 = bZip2CompressorInputStream.blockSize100k * BZip2Constants.BASEBLOCKSIZE;
        int i6 = 256;
        while (true) {
            i6--;
            if (i6 < 0) {
                break;
            }
            cArr[i6] = (char) i6;
            iArr[i6] = 0;
        }
        int i7 = bZip2CompressorInputStream.nInUse + 1;
        int andMoveToFrontDecode0 = bZip2CompressorInputStream.getAndMoveToFrontDecode0(0);
        int i8 = bZip2CompressorInputStream.bsBuff;
        int i9 = bZip2CompressorInputStream.bsLive;
        int i10 = i8;
        byte b = bArr2[0] & UByte.MAX_VALUE;
        int[] iArr6 = iArr4[b];
        int[] iArr7 = iArr3[b];
        int[] iArr8 = iArr5[b];
        int i11 = 0;
        int i12 = i9;
        int i13 = andMoveToFrontDecode0;
        int i14 = 49;
        int i15 = -1;
        int i16 = i10;
        int i17 = iArr2[b];
        int i18 = i16;
        while (i13 != i7) {
            int i19 = i7;
            int i20 = i18;
            if (i13 == 0 || i13 == 1) {
                int i21 = 1;
                int i22 = -1;
                while (true) {
                    if (i13 == 0) {
                        i22 += i21;
                        i = i15;
                    } else {
                        i = i15;
                        if (i13 == 1) {
                            i22 += i21 << 1;
                        } else {
                            int[][] iArr9 = iArr5;
                            byte[] bArr4 = bArr2;
                            byte b2 = bArr3[cArr[0]];
                            byte b3 = b2 & UByte.MAX_VALUE;
                            iArr[b3] = iArr[b3] + i22 + 1;
                            i15 = i;
                            while (i22 >= 0) {
                                i15++;
                                bArr[i15] = b2;
                                i22--;
                            }
                            if (i15 < i5) {
                                bZip2CompressorInputStream = this;
                                i7 = i19;
                                i18 = i20;
                                iArr5 = iArr9;
                                bArr2 = bArr4;
                            } else {
                                throw new IOException("block overrun");
                            }
                        }
                    }
                    if (i14 == 0) {
                        i11++;
                        byte b4 = bArr2[i11] & UByte.MAX_VALUE;
                        iArr6 = iArr4[b4];
                        iArr7 = iArr3[b4];
                        iArr8 = iArr5[b4];
                        i2 = iArr2[b4];
                        i3 = 49;
                    } else {
                        i3 = i14 - 1;
                        i2 = i17;
                    }
                    int i23 = i12;
                    while (i23 < i2) {
                        int read = inputStream.read();
                        if (read >= 0) {
                            i20 = (i20 << 8) | read;
                            i23 += 8;
                        } else {
                            throw new IOException("unexpected end of stream");
                        }
                    }
                    int i24 = i23 - i2;
                    int[][] iArr10 = iArr5;
                    int i25 = i2;
                    int i26 = i24;
                    int i27 = (i20 >> i24) & ((1 << i2) - 1);
                    while (true) {
                        byte[] bArr5 = bArr2;
                        if (i27 <= iArr7[i25]) {
                            break;
                        }
                        int i28 = i12;
                        while (i28 < 1) {
                            int read2 = inputStream.read();
                            if (read2 >= 0) {
                                i20 = (i20 << 8) | read2;
                                i28 += 8;
                            } else {
                                throw new IOException("unexpected end of stream");
                            }
                        }
                        i26 = i28 - 1;
                        i27 = (i27 << 1) | ((i20 >> i26) & 1);
                        i25++;
                        bArr2 = bArr5;
                    }
                    int i29 = iArr8[i27 - iArr6[i25]];
                    i21 <<= 1;
                    i17 = i2;
                    i15 = i;
                    i13 = i29;
                    iArr5 = iArr10;
                }
            } else {
                i15++;
                if (i15 < i5) {
                    int i30 = i13 - 1;
                    char c2 = cArr[i30];
                    byte b5 = bArr3[c2] & UByte.MAX_VALUE;
                    iArr[b5] = iArr[b5] + 1;
                    bArr[i15] = bArr3[c2];
                    if (i13 <= 16) {
                        while (i30 > 0) {
                            int i31 = i30 - 1;
                            cArr[i30] = cArr[i31];
                            i30 = i31;
                        }
                        c = 0;
                    } else {
                        c = 0;
                        System.arraycopy(cArr, 0, cArr, 1, i30);
                    }
                    cArr[c] = c2;
                    if (i14 == 0) {
                        i11++;
                        byte b6 = bArr2[i11] & UByte.MAX_VALUE;
                        int[] iArr11 = iArr4[b6];
                        int[] iArr12 = iArr3[b6];
                        int[] iArr13 = iArr5[b6];
                        i4 = iArr2[b6];
                        iArr6 = iArr11;
                        iArr7 = iArr12;
                        iArr8 = iArr13;
                        i14 = 49;
                    } else {
                        i14--;
                        i4 = i17;
                    }
                    int i32 = i12;
                    while (i32 < i4) {
                        int read3 = inputStream.read();
                        if (read3 >= 0) {
                            i20 = (i20 << 8) | read3;
                            i32 += 8;
                        } else {
                            throw new IOException("unexpected end of stream");
                        }
                    }
                    int i33 = i32 - i4;
                    int i34 = (i20 >> i33) & ((1 << i4) - 1);
                    i12 = i33;
                    int i35 = i4;
                    while (i34 > iArr7[i35]) {
                        i35++;
                        int i36 = i4;
                        int i37 = i12;
                        while (i37 < 1) {
                            int read4 = inputStream.read();
                            if (read4 >= 0) {
                                i20 = (i20 << 8) | read4;
                                i37 += 8;
                            } else {
                                throw new IOException("unexpected end of stream");
                            }
                        }
                        i12 = i37 - 1;
                        i34 = (i34 << 1) | ((i20 >> i12) & 1);
                        i4 = i36;
                    }
                    int i38 = i4;
                    i13 = iArr8[i34 - iArr6[i35]];
                    bZip2CompressorInputStream = this;
                    i7 = i19;
                    i18 = i20;
                    i17 = i38;
                } else {
                    throw new IOException("block overrun");
                }
            }
        }
        bZip2CompressorInputStream.last = i15;
        bZip2CompressorInputStream.bsLive = i12;
        bZip2CompressorInputStream.bsBuff = i18;
    }

    private int getAndMoveToFrontDecode0(int i) throws IOException {
        InputStream inputStream = this.f156in;
        Data data2 = this.data;
        byte b = data2.selector[i] & UByte.MAX_VALUE;
        int[] iArr = data2.limit[b];
        int i2 = data2.minLens[b];
        int bsR = bsR(i2);
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        while (bsR > iArr[i2]) {
            i2++;
            while (i3 < 1) {
                int read = inputStream.read();
                if (read >= 0) {
                    i4 = (i4 << 8) | read;
                    i3 += 8;
                } else {
                    throw new IOException("unexpected end of stream");
                }
            }
            i3--;
            bsR = (bsR << 1) | (1 & (i4 >> i3));
        }
        this.bsLive = i3;
        this.bsBuff = i4;
        return data2.perm[b][bsR - data2.base[b][i2]];
    }

    private static void hbCreateDecodeTables(int[] iArr, int[] iArr2, int[] iArr3, char[] cArr, int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        for (int i6 = i; i6 <= i2; i6++) {
            for (int i7 = 0; i7 < i3; i7++) {
                if (cArr[i7] == i6) {
                    iArr3[i5] = i7;
                    i5++;
                }
            }
        }
        int i8 = 23;
        while (true) {
            i8--;
            if (i8 <= 0) {
                break;
            }
            iArr2[i8] = 0;
            iArr[i8] = 0;
        }
        for (int i9 = 0; i9 < i3; i9++) {
            int i10 = cArr[i9] + 1;
            iArr2[i10] = iArr2[i10] + 1;
        }
        int i11 = iArr2[0];
        for (int i12 = 1; i12 < 23; i12++) {
            i11 += iArr2[i12];
            iArr2[i12] = i11;
        }
        int i13 = iArr2[i];
        int i14 = i;
        while (i14 <= i2) {
            int i15 = i14 + 1;
            int i16 = iArr2[i15];
            int i17 = i4 + (i16 - i13);
            iArr[i14] = i17 - 1;
            i4 = i17 << 1;
            i14 = i15;
            i13 = i16;
        }
        for (int i18 = i + 1; i18 <= i2; i18++) {
            iArr2[i18] = ((iArr[i18 - 1] + 1) << 1) - iArr2[i18];
        }
    }

    private boolean init(boolean z) throws IOException {
        InputStream inputStream = this.f156in;
        if (inputStream != null) {
            int read = inputStream.read();
            if (read == -1 && !z) {
                return false;
            }
            int read2 = this.f156in.read();
            int read3 = this.f156in.read();
            if (read == 66 && read2 == 90 && read3 == 104) {
                int read4 = this.f156in.read();
                if (read4 < 49 || read4 > 57) {
                    throw new IOException("BZip2 block size is invalid");
                }
                this.blockSize100k = read4 - 48;
                this.bsLive = 0;
                this.computedCombinedCRC = 0;
                return true;
            }
            throw new IOException(z ? "Stream is not in the BZip2 format" : "Garbage after a valid BZip2 stream");
        }
        throw new IOException("No InputStream");
    }

    private void initBlock() throws IOException {
        do {
            char bsGetUByte = bsGetUByte();
            char bsGetUByte2 = bsGetUByte();
            char bsGetUByte3 = bsGetUByte();
            char bsGetUByte4 = bsGetUByte();
            char bsGetUByte5 = bsGetUByte();
            char bsGetUByte6 = bsGetUByte();
            if (bsGetUByte != 23 || bsGetUByte2 != 'r' || bsGetUByte3 != 'E' || bsGetUByte4 != '8' || bsGetUByte5 != 'P' || bsGetUByte6 != 144) {
                boolean z = false;
                if (bsGetUByte == '1' && bsGetUByte2 == 'A' && bsGetUByte3 == 'Y' && bsGetUByte4 == '&' && bsGetUByte5 == 'S' && bsGetUByte6 == 'Y') {
                    this.storedBlockCRC = bsGetInt();
                    if (bsR(1) == 1) {
                        z = true;
                    }
                    this.blockRandomised = z;
                    if (this.data == null) {
                        this.data = new Data(this.blockSize100k);
                    }
                    getAndMoveToFrontDecode();
                    this.crc.initialiseCRC();
                    this.currentState = 1;
                    return;
                }
                this.currentState = 0;
                throw new IOException("bad block header");
            }
        } while (!complete());
    }

    private void makeMaps() {
        boolean[] zArr = this.data.inUse;
        byte[] bArr = this.data.seqToUnseq;
        int i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            if (zArr[i2]) {
                bArr[i] = (byte) i2;
                i++;
            }
        }
        this.nInUse = i;
    }

    public static boolean matches(byte[] bArr, int i) {
        return i >= 3 && bArr[0] == 66 && bArr[1] == 90 && bArr[2] == 104;
    }

    private int read0() throws IOException {
        switch (this.currentState) {
            case 0:
                return -1;
            case 1:
                return setupBlock();
            case 2:
                throw new IllegalStateException();
            case 3:
                return setupRandPartB();
            case 4:
                return setupRandPartC();
            case 5:
                throw new IllegalStateException();
            case 6:
                return setupNoRandPartB();
            case 7:
                return setupNoRandPartC();
            default:
                throw new IllegalStateException();
        }
    }

    private void recvDecodingTables() throws IOException {
        Data data2 = this.data;
        boolean[] zArr = data2.inUse;
        byte[] bArr = data2.recvDecodingTables_pos;
        byte[] bArr2 = data2.selector;
        byte[] bArr3 = data2.selectorMtf;
        int i = 0;
        for (int i2 = 0; i2 < 16; i2++) {
            if (bsGetBit()) {
                i |= 1 << i2;
            }
        }
        int i3 = 256;
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            zArr[i3] = false;
        }
        for (int i4 = 0; i4 < 16; i4++) {
            if (((1 << i4) & i) != 0) {
                for (int i5 = 0; i5 < 16; i5++) {
                    if (bsGetBit()) {
                        zArr[(i4 << 4) + i5] = true;
                    }
                }
            }
        }
        makeMaps();
        int i6 = this.nInUse + 2;
        int bsR = bsR(3);
        int bsR2 = bsR(15);
        for (int i7 = 0; i7 < bsR2; i7++) {
            int i8 = 0;
            while (bsGetBit()) {
                i8++;
            }
            bArr3[i7] = (byte) i8;
        }
        int i9 = bsR;
        while (true) {
            i9--;
            if (i9 < 0) {
                break;
            }
            bArr[i9] = (byte) i9;
        }
        for (int i10 = 0; i10 < bsR2; i10++) {
            int i11 = bArr3[i10] & UByte.MAX_VALUE;
            byte b = bArr[i11];
            while (i11 > 0) {
                bArr[i11] = bArr[i11 - 1];
                i11--;
            }
            bArr[0] = b;
            bArr2[i10] = b;
        }
        char[][] cArr = data2.temp_charArray2d;
        for (int i12 = 0; i12 < bsR; i12++) {
            int bsR3 = bsR(5);
            char[] cArr2 = cArr[i12];
            for (int i13 = 0; i13 < i6; i13++) {
                while (bsGetBit()) {
                    bsR3 += bsGetBit() ? -1 : 1;
                }
                cArr2[i13] = (char) bsR3;
            }
        }
        createHuffmanDecodingTables(i6, bsR);
    }

    private int setupBlock() throws IOException {
        Data data2;
        if (this.currentState == 0 || (data2 = this.data) == null) {
            return -1;
        }
        int[] iArr = data2.cftab;
        int[] initTT = this.data.initTT(this.last + 1);
        byte[] bArr = this.data.ll8;
        iArr[0] = 0;
        System.arraycopy(this.data.unzftab, 0, iArr, 1, 256);
        int i = iArr[0];
        for (int i2 = 1; i2 <= 256; i2++) {
            i += iArr[i2];
            iArr[i2] = i;
        }
        int i3 = this.last;
        for (int i4 = 0; i4 <= i3; i4++) {
            byte b = bArr[i4] & UByte.MAX_VALUE;
            int i5 = iArr[b];
            iArr[b] = i5 + 1;
            initTT[i5] = i4;
        }
        int i6 = this.origPtr;
        if (i6 < 0 || i6 >= initTT.length) {
            throw new IOException("stream corrupted");
        }
        this.su_tPos = initTT[i6];
        this.su_count = 0;
        this.su_i2 = 0;
        this.su_ch2 = 256;
        if (!this.blockRandomised) {
            return setupNoRandPartA();
        }
        this.su_rNToGo = 0;
        this.su_rTPos = 0;
        return setupRandPartA();
    }

    private int setupNoRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            byte b = this.data.ll8[this.su_tPos] & UByte.MAX_VALUE;
            this.su_ch2 = b;
            this.su_tPos = this.data.f157tt[this.su_tPos];
            this.su_i2++;
            this.currentState = 6;
            this.crc.updateCRC(b);
            return b;
        }
        this.currentState = 5;
        endBlock();
        initBlock();
        return setupBlock();
    }

    private int setupNoRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.su_count = 1;
            return setupNoRandPartA();
        }
        int i = this.su_count + 1;
        this.su_count = i;
        if (i < 4) {
            return setupNoRandPartA();
        }
        this.su_z = (char) (this.data.ll8[this.su_tPos] & UByte.MAX_VALUE);
        this.su_tPos = this.data.f157tt[this.su_tPos];
        this.su_j2 = 0;
        return setupNoRandPartC();
    }

    private int setupNoRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            int i = this.su_ch2;
            this.crc.updateCRC(i);
            this.su_j2++;
            this.currentState = 7;
            return i;
        }
        this.su_i2++;
        this.su_count = 0;
        return setupNoRandPartA();
    }

    private int setupRandPartA() throws IOException {
        if (this.su_i2 <= this.last) {
            this.su_chPrev = this.su_ch2;
            byte b = this.data.ll8[this.su_tPos];
            this.su_tPos = this.data.f157tt[this.su_tPos];
            int i = this.su_rNToGo;
            byte b2 = 0;
            if (i == 0) {
                this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                int i2 = this.su_rTPos + 1;
                this.su_rTPos = i2;
                if (i2 == 512) {
                    this.su_rTPos = 0;
                }
            } else {
                this.su_rNToGo = i - 1;
            }
            if (this.su_rNToGo == 1) {
                b2 = 1;
            }
            byte b3 = (b & UByte.MAX_VALUE) ^ b2;
            this.su_ch2 = b3;
            this.su_i2++;
            this.currentState = 3;
            this.crc.updateCRC(b3);
            return b3;
        }
        endBlock();
        initBlock();
        return setupBlock();
    }

    private int setupRandPartB() throws IOException {
        if (this.su_ch2 != this.su_chPrev) {
            this.currentState = 2;
            this.su_count = 1;
        } else {
            int i = this.su_count + 1;
            this.su_count = i;
            if (i >= 4) {
                this.su_z = (char) (this.data.ll8[this.su_tPos] & UByte.MAX_VALUE);
                this.su_tPos = this.data.f157tt[this.su_tPos];
                int i2 = this.su_rNToGo;
                if (i2 == 0) {
                    this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
                    int i3 = this.su_rTPos + 1;
                    this.su_rTPos = i3;
                    if (i3 == 512) {
                        this.su_rTPos = 0;
                    }
                } else {
                    this.su_rNToGo = i2 - 1;
                }
                this.su_j2 = 0;
                this.currentState = 4;
                if (this.su_rNToGo == 1) {
                    this.su_z = (char) (this.su_z ^ 1);
                }
                return setupRandPartC();
            }
            this.currentState = 2;
        }
        return setupRandPartA();
    }

    private int setupRandPartC() throws IOException {
        if (this.su_j2 < this.su_z) {
            this.crc.updateCRC(this.su_ch2);
            this.su_j2++;
            return this.su_ch2;
        }
        this.currentState = 2;
        this.su_i2++;
        this.su_count = 0;
        return setupRandPartA();
    }

    public void close() throws IOException {
        InputStream inputStream = this.f156in;
        if (inputStream != null) {
            try {
                if (inputStream != System.in) {
                    inputStream.close();
                }
            } finally {
                this.data = null;
                this.f156in = null;
            }
        }
    }

    public int read() throws IOException {
        if (this.f156in != null) {
            int read0 = read0();
            count(read0 < 0 ? -1 : 1);
            return read0;
        }
        throw new IOException("stream closed");
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i < 0) {
            throw new IndexOutOfBoundsException("offs(" + i + ") < 0.");
        } else if (i2 >= 0) {
            int i3 = i + i2;
            if (i3 > bArr.length) {
                throw new IndexOutOfBoundsException("offs(" + i + ") + len(" + i2 + ") > dest.length(" + bArr.length + ").");
            } else if (this.f156in == null) {
                throw new IOException("stream closed");
            } else if (i2 == 0) {
                return 0;
            } else {
                int i4 = i;
                while (i4 < i3) {
                    int read0 = read0();
                    if (read0 < 0) {
                        break;
                    }
                    bArr[i4] = (byte) read0;
                    count(1);
                    i4++;
                }
                if (i4 == i) {
                    return -1;
                }
                return i4 - i;
            }
        } else {
            throw new IndexOutOfBoundsException("len(" + i2 + ") < 0.");
        }
    }
}
