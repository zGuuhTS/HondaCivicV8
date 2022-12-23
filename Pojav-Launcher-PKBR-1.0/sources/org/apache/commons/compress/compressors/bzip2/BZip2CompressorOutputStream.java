package org.apache.commons.compress.compressors.bzip2;

import androidx.core.view.InputDeviceCompat;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import kotlin.UByte;
import net.kdt.pojavlaunch.AWTInputEvent;
import org.apache.commons.compress.compressors.CompressorOutputStream;

public class BZip2CompressorOutputStream extends CompressorOutputStream implements BZip2Constants {
    private static final int GREATER_ICOST = 15;
    private static final int LESSER_ICOST = 0;
    public static final int MAX_BLOCKSIZE = 9;
    public static final int MIN_BLOCKSIZE = 1;
    private final int allowableBlockSize;
    private int blockCRC;
    private final int blockSize100k;
    private BlockSort blockSorter;
    private int bsBuff;
    private int bsLive;
    private volatile boolean closed;
    private int combinedCRC;
    private final CRC crc;
    private int currentChar;
    private Data data;
    private int last;
    private int nInUse;
    private int nMTF;
    private OutputStream out;
    private int runLength;

    static final class Data {
        final byte[] block;
        final int[] fmap;
        final byte[] generateMTFValues_yy = new byte[256];
        final int[] heap = new int[AWTInputEvent.VK_JAPANESE_HIRAGANA];
        final boolean[] inUse = new boolean[256];
        final int[] mtfFreq = new int[258];
        int origPtr;
        final int[] parent = new int[AWTInputEvent.VK_EURO_SIGN];
        final byte[] selector = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] selectorMtf = new byte[BZip2Constants.MAX_SELECTORS];
        final byte[] sendMTFValues2_pos = new byte[6];
        final int[][] sendMTFValues_code = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 258}));
        final short[] sendMTFValues_cost = new short[6];
        final int[] sendMTFValues_fave = new int[6];
        final byte[][] sendMTFValues_len = ((byte[][]) Array.newInstance(Byte.TYPE, new int[]{6, 258}));
        final int[][] sendMTFValues_rfreq = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{6, 258}));
        final boolean[] sentMTFValues4_inUse16 = new boolean[16];
        final char[] sfmap;
        final byte[] unseqToSeq = new byte[256];
        final int[] weight = new int[AWTInputEvent.VK_EURO_SIGN];

        Data(int i) {
            int i2 = i * BZip2Constants.BASEBLOCKSIZE;
            this.block = new byte[(i2 + 1 + 20)];
            this.fmap = new int[i2];
            this.sfmap = new char[(i2 * 2)];
        }
    }

    public BZip2CompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, 9);
    }

    public BZip2CompressorOutputStream(OutputStream outputStream, int i) throws IOException {
        this.crc = new CRC();
        this.currentChar = -1;
        this.runLength = 0;
        if (i < 1) {
            throw new IllegalArgumentException("blockSize(" + i + ") < 1");
        } else if (i <= 9) {
            this.blockSize100k = i;
            this.out = outputStream;
            this.allowableBlockSize = (i * BZip2Constants.BASEBLOCKSIZE) - 20;
            init();
        } else {
            throw new IllegalArgumentException("blockSize(" + i + ") > 9");
        }
    }

    private void blockSort() {
        this.blockSorter.blockSort(this.data, this.last);
    }

    private void bsFinishedWithStream() throws IOException {
        while (this.bsLive > 0) {
            this.out.write(this.bsBuff >> 24);
            this.bsBuff <<= 8;
            this.bsLive -= 8;
        }
    }

    private void bsPutInt(int i) throws IOException {
        bsW(8, (i >> 24) & 255);
        bsW(8, (i >> 16) & 255);
        bsW(8, (i >> 8) & 255);
        bsW(8, i & 255);
    }

    private void bsPutUByte(int i) throws IOException {
        bsW(8, i);
    }

    private void bsW(int i, int i2) throws IOException {
        OutputStream outputStream = this.out;
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        while (i3 >= 8) {
            outputStream.write(i4 >> 24);
            i4 <<= 8;
            i3 -= 8;
        }
        this.bsBuff = (i2 << ((32 - i3) - i)) | i4;
        this.bsLive = i3 + i;
    }

    public static int chooseBlockSize(long j) {
        if (j > 0) {
            return (int) Math.min((j / 132000) + 1, 9);
        }
        return 9;
    }

    private void endBlock() throws IOException {
        int finalCRC = this.crc.getFinalCRC();
        this.blockCRC = finalCRC;
        int i = this.combinedCRC;
        int i2 = (i >>> 31) | (i << 1);
        this.combinedCRC = i2;
        this.combinedCRC = finalCRC ^ i2;
        if (this.last != -1) {
            blockSort();
            bsPutUByte(49);
            bsPutUByte(65);
            bsPutUByte(89);
            bsPutUByte(38);
            bsPutUByte(83);
            bsPutUByte(89);
            bsPutInt(this.blockCRC);
            bsW(1, 0);
            moveToFrontCodeAndSend();
        }
    }

    private void endCompression() throws IOException {
        bsPutUByte(23);
        bsPutUByte(114);
        bsPutUByte(69);
        bsPutUByte(56);
        bsPutUByte(80);
        bsPutUByte(AWTInputEvent.VK_NUM_LOCK);
        bsPutInt(this.combinedCRC);
        bsFinishedWithStream();
    }

    private void generateMTFValues() {
        int i;
        int i2 = this.last;
        Data data2 = this.data;
        boolean[] zArr = data2.inUse;
        byte[] bArr = data2.block;
        int[] iArr = data2.fmap;
        char[] cArr = data2.sfmap;
        int[] iArr2 = data2.mtfFreq;
        byte[] bArr2 = data2.unseqToSeq;
        byte[] bArr3 = data2.generateMTFValues_yy;
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            if (zArr[i4]) {
                bArr2[i4] = (byte) i3;
                i3++;
            }
        }
        this.nInUse = i3;
        int i5 = i3 + 1;
        for (int i6 = i5; i6 >= 0; i6--) {
            iArr2[i6] = 0;
        }
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            bArr3[i3] = (byte) i3;
        }
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 <= i2; i9++) {
            byte b = bArr2[bArr[iArr[i9]] & UByte.MAX_VALUE];
            byte b2 = bArr3[0];
            int i10 = 0;
            while (b != b2) {
                i10++;
                byte b3 = bArr3[i10];
                bArr3[i10] = b2;
                b2 = b3;
            }
            bArr3[0] = b2;
            if (i10 == 0) {
                i8++;
            } else {
                if (i8 > 0) {
                    int i11 = i8 - 1;
                    while (true) {
                        if ((i11 & 1) == 0) {
                            cArr[i7] = 0;
                            i7++;
                            iArr2[0] = iArr2[0] + 1;
                        } else {
                            cArr[i7] = 1;
                            i7++;
                            iArr2[1] = iArr2[1] + 1;
                        }
                        if (i11 < 2) {
                            break;
                        }
                        i11 = (i11 - 2) >> 1;
                    }
                    i8 = 0;
                }
                int i12 = i10 + 1;
                cArr[i7] = (char) i12;
                i7++;
                iArr2[i12] = iArr2[i12] + 1;
            }
        }
        if (i8 > 0) {
            int i13 = i8 - 1;
            while (true) {
                if ((i13 & 1) == 0) {
                    cArr[i7] = 0;
                    i = i7 + 1;
                    iArr2[0] = iArr2[0] + 1;
                } else {
                    cArr[i7] = 1;
                    i = i7 + 1;
                    iArr2[1] = iArr2[1] + 1;
                }
                if (i13 < 2) {
                    break;
                }
                i13 = (i13 - 2) >> 1;
            }
        }
        cArr[i7] = (char) i5;
        iArr2[i5] = iArr2[i5] + 1;
        this.nMTF = i7 + 1;
    }

    private static void hbAssignCodes(int[] iArr, byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i <= i2) {
            for (int i5 = 0; i5 < i3; i5++) {
                if ((bArr[i5] & UByte.MAX_VALUE) == i) {
                    iArr[i5] = i4;
                    i4++;
                }
            }
            i4 <<= 1;
            i++;
        }
    }

    private static void hbMakeCodeLengths(byte[] bArr, int[] iArr, Data data2, int i, int i2) {
        Data data3 = data2;
        int i3 = i;
        int[] iArr2 = data3.heap;
        int[] iArr3 = data3.weight;
        int[] iArr4 = data3.parent;
        int i4 = i3;
        while (true) {
            int i5 = 1;
            i4--;
            if (i4 < 0) {
                break;
            }
            if (iArr[i4] != 0) {
                i5 = iArr[i4];
            }
            iArr3[i4 + 1] = i5 << 8;
        }
        boolean z = true;
        while (z) {
            iArr2[0] = 0;
            iArr3[0] = 0;
            iArr4[0] = -2;
            int i6 = 0;
            for (int i7 = 1; i7 <= i3; i7++) {
                iArr4[i7] = -1;
                i6++;
                iArr2[i6] = i7;
                int i8 = iArr2[i6];
                int i9 = i6;
                while (true) {
                    int i10 = i9 >> 1;
                    if (iArr3[i8] >= iArr3[iArr2[i10]]) {
                        break;
                    }
                    iArr2[i9] = iArr2[i10];
                    i9 = i10;
                }
                iArr2[i9] = i8;
            }
            int i11 = i3;
            while (i6 > 1) {
                int i12 = iArr2[1];
                iArr2[1] = iArr2[i6];
                int i13 = i6 - 1;
                int i14 = iArr2[1];
                int i15 = 1;
                while (true) {
                    int i16 = i15 << 1;
                    if (i16 > i13) {
                        break;
                    }
                    if (i16 < i13) {
                        int i17 = i16 + 1;
                        if (iArr3[iArr2[i17]] < iArr3[iArr2[i16]]) {
                            i16 = i17;
                        }
                    }
                    if (iArr3[i14] < iArr3[iArr2[i16]]) {
                        break;
                    }
                    iArr2[i15] = iArr2[i16];
                    i15 = i16;
                }
                iArr2[i15] = i14;
                int i18 = iArr2[1];
                iArr2[1] = iArr2[i13];
                int i19 = i13 - 1;
                int i20 = iArr2[1];
                int i21 = 1;
                while (true) {
                    int i22 = i21 << 1;
                    if (i22 > i19) {
                        break;
                    }
                    if (i22 < i19) {
                        int i23 = i22 + 1;
                        if (iArr3[iArr2[i23]] < iArr3[iArr2[i22]]) {
                            i22 = i23;
                        }
                    }
                    if (iArr3[i20] < iArr3[iArr2[i22]]) {
                        break;
                    }
                    iArr2[i21] = iArr2[i22];
                    i21 = i22;
                }
                iArr2[i21] = i20;
                i11++;
                iArr4[i18] = i11;
                iArr4[i12] = i11;
                int i24 = iArr3[i12];
                int i25 = iArr3[i18];
                int i26 = i24 & 255;
                int i27 = i25 & 255;
                if (i26 <= i27) {
                    i26 = i27;
                }
                iArr3[i11] = ((i24 & InputDeviceCompat.SOURCE_ANY) + (i25 & InputDeviceCompat.SOURCE_ANY)) | (i26 + 1);
                iArr4[i11] = -1;
                i6 = i19 + 1;
                iArr2[i6] = i11;
                int i28 = iArr2[i6];
                int i29 = iArr3[i28];
                int i30 = i6;
                while (true) {
                    int i31 = i30 >> 1;
                    if (i29 >= iArr3[iArr2[i31]]) {
                        break;
                    }
                    iArr2[i30] = iArr2[i31];
                    i30 = i31;
                }
                iArr2[i30] = i28;
            }
            z = false;
            for (int i32 = 1; i32 <= i3; i32++) {
                int i33 = i32;
                int i34 = 0;
                while (true) {
                    i33 = iArr4[i33];
                    if (i33 < 0) {
                        break;
                    }
                    i34++;
                }
                bArr[i32 - 1] = (byte) i34;
                if (i34 > i2) {
                    z = true;
                }
            }
            int i35 = i2;
            if (z) {
                for (int i36 = 1; i36 < i3; i36++) {
                    iArr3[i36] = (((iArr3[i36] >> 8) >> 1) + 1) << 8;
                }
            }
        }
    }

    private void init() throws IOException {
        bsPutUByte(66);
        bsPutUByte(90);
        this.data = new Data(this.blockSize100k);
        this.blockSorter = new BlockSort(this.data);
        bsPutUByte(104);
        bsPutUByte(this.blockSize100k + 48);
        this.combinedCRC = 0;
        initBlock();
    }

    private void initBlock() {
        this.crc.initialiseCRC();
        this.last = -1;
        boolean[] zArr = this.data.inUse;
        int i = 256;
        while (true) {
            i--;
            if (i >= 0) {
                zArr[i] = false;
            } else {
                return;
            }
        }
    }

    private void moveToFrontCodeAndSend() throws IOException {
        bsW(24, this.data.origPtr);
        generateMTFValues();
        sendMTFValues();
    }

    private void sendMTFValues() throws IOException {
        byte[][] bArr = this.data.sendMTFValues_len;
        int i = 2;
        int i2 = this.nInUse + 2;
        int i3 = 6;
        while (true) {
            i3--;
            if (i3 < 0) {
                break;
            }
            byte[] bArr2 = bArr[i3];
            int i4 = i2;
            while (true) {
                i4--;
                if (i4 >= 0) {
                    bArr2[i4] = 15;
                }
            }
        }
        int i5 = this.nMTF;
        if (i5 >= 200) {
            i = i5 < 600 ? 3 : i5 < 1200 ? 4 : i5 < 2400 ? 5 : 6;
        }
        sendMTFValues0(i, i2);
        int sendMTFValues1 = sendMTFValues1(i, i2);
        sendMTFValues2(i, sendMTFValues1);
        sendMTFValues3(i, i2);
        sendMTFValues4();
        sendMTFValues5(i, sendMTFValues1);
        sendMTFValues6(i, i2);
        sendMTFValues7();
    }

    private void sendMTFValues0(int i, int i2) {
        byte[][] bArr = this.data.sendMTFValues_len;
        int[] iArr = this.data.mtfFreq;
        int i3 = this.nMTF;
        int i4 = 0;
        for (int i5 = i; i5 > 0; i5--) {
            int i6 = i3 / i5;
            int i7 = i4 - 1;
            int i8 = 0;
            while (i8 < i6 && i7 < i2 - 1) {
                i7++;
                i8 += iArr[i7];
            }
            if (!(i7 <= i4 || i5 == i || i5 == 1 || (1 & (i - i5)) == 0)) {
                i8 -= iArr[i7];
                i7--;
            }
            byte[] bArr2 = bArr[i5 - 1];
            int i9 = i2;
            while (true) {
                i9--;
                if (i9 < 0) {
                    break;
                } else if (i9 < i4 || i9 > i7) {
                    bArr2[i9] = 15;
                } else {
                    bArr2[i9] = 0;
                }
            }
            i4 = i7 + 1;
            i3 -= i8;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: int[][]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r19v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v3, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v0, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v13, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v25, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v29, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v33, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v37, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v41, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int sendMTFValues1(int r35, int r36) {
        /*
            r34 = this;
            r0 = r34
            r1 = r35
            org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data r2 = r0.data
            int[][] r3 = r2.sendMTFValues_rfreq
            int[] r4 = r2.sendMTFValues_fave
            short[] r5 = r2.sendMTFValues_cost
            char[] r6 = r2.sfmap
            byte[] r7 = r2.selector
            byte[][] r2 = r2.sendMTFValues_len
            r8 = 0
            r9 = r2[r8]
            r10 = 1
            r11 = r2[r10]
            r12 = 2
            r13 = r2[r12]
            r14 = 3
            r15 = r2[r14]
            r14 = 4
            r17 = r2[r14]
            r18 = 5
            r19 = r2[r18]
            int r12 = r0.nMTF
            r21 = r8
        L_0x0029:
            if (r8 >= r14) goto L_0x0158
            r21 = r1
        L_0x002d:
            int r21 = r21 + -1
            if (r21 < 0) goto L_0x0042
            r20 = 0
            r4[r21] = r20
            r22 = r3[r21]
            r23 = r36
        L_0x0039:
            int r23 = r23 + -1
            if (r23 < 0) goto L_0x002d
            r22[r23] = r20
            r20 = 0
            goto L_0x0039
        L_0x0042:
            r14 = 0
            r21 = 0
        L_0x0045:
            int r10 = r0.nMTF
            if (r14 >= r10) goto L_0x012a
            int r10 = r14 + 50
            r23 = 1
            int r10 = r10 + -1
            r24 = r14
            int r14 = r12 + -1
            int r10 = java.lang.Math.min(r10, r14)
            r14 = 6
            r25 = -1
            if (r1 != r14) goto L_0x00be
            r31 = r12
            r12 = r24
            r14 = 0
            r26 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            r30 = 0
        L_0x006b:
            if (r12 > r10) goto L_0x00a9
            char r32 = r6[r12]
            r33 = r8
            r8 = r9[r32]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r14 = r14 + r8
            short r14 = (short) r14
            r8 = r11[r32]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r26 + r8
            short r8 = (short) r8
            r26 = r8
            r8 = r13[r32]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r27 + r8
            short r8 = (short) r8
            r27 = r8
            r8 = r15[r32]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r28 + r8
            short r8 = (short) r8
            r28 = r8
            r8 = r17[r32]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r29 + r8
            short r8 = (short) r8
            r29 = r8
            r8 = r19[r32]
            r8 = r8 & 255(0xff, float:3.57E-43)
            int r8 = r30 + r8
            short r8 = (short) r8
            int r12 = r12 + 1
            r30 = r8
            r8 = r33
            goto L_0x006b
        L_0x00a9:
            r33 = r8
            r20 = 0
            r5[r20] = r14
            r8 = 1
            r5[r8] = r26
            r8 = 2
            r5[r8] = r27
            r12 = 3
            r5[r12] = r28
            r14 = 4
            r5[r14] = r29
            r5[r18] = r30
            goto L_0x00ef
        L_0x00be:
            r33 = r8
            r31 = r12
            r8 = 2
            r12 = 3
            r14 = 4
            r20 = 0
            r16 = r1
        L_0x00c9:
            int r16 = r16 + -1
            if (r16 < 0) goto L_0x00d0
            r5[r16] = r20
            goto L_0x00c9
        L_0x00d0:
            r8 = r24
        L_0x00d2:
            if (r8 > r10) goto L_0x00ef
            char r16 = r6[r8]
            r22 = r1
        L_0x00d8:
            int r22 = r22 + -1
            if (r22 < 0) goto L_0x00eb
            short r26 = r5[r22]
            r27 = r2[r22]
            r12 = r27[r16]
            r12 = r12 & 255(0xff, float:3.57E-43)
            int r12 = r26 + r12
            short r12 = (short) r12
            r5[r22] = r12
            r12 = 3
            goto L_0x00d8
        L_0x00eb:
            int r8 = r8 + 1
            r12 = 3
            goto L_0x00d2
        L_0x00ef:
            r8 = 999999999(0x3b9ac9ff, float:0.004723787)
            r12 = r1
            r14 = r25
        L_0x00f5:
            int r12 = r12 + -1
            r16 = r9
            if (r12 < 0) goto L_0x0104
            short r9 = r5[r12]
            if (r9 >= r8) goto L_0x0101
            r8 = r9
            r14 = r12
        L_0x0101:
            r9 = r16
            goto L_0x00f5
        L_0x0104:
            r8 = r4[r14]
            r9 = 1
            int r8 = r8 + r9
            r4[r14] = r8
            byte r8 = (byte) r14
            r7[r21] = r8
            int r21 = r21 + 1
            r8 = r3[r14]
            r14 = r24
        L_0x0113:
            if (r14 > r10) goto L_0x0120
            char r12 = r6[r14]
            r23 = r8[r12]
            int r23 = r23 + 1
            r8[r12] = r23
            int r14 = r14 + 1
            goto L_0x0113
        L_0x0120:
            int r14 = r10 + 1
            r9 = r16
            r12 = r31
            r8 = r33
            goto L_0x0045
        L_0x012a:
            r33 = r8
            r16 = r9
            r31 = r12
            r9 = 1
            r20 = 0
            r8 = r20
        L_0x0135:
            if (r8 >= r1) goto L_0x014a
            r10 = r2[r8]
            r12 = r3[r8]
            org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data r14 = r0.data
            r9 = 20
            r0 = r36
            hbMakeCodeLengths(r10, r12, r14, r0, r9)
            int r8 = r8 + 1
            r9 = 1
            r0 = r34
            goto L_0x0135
        L_0x014a:
            r0 = r36
            int r8 = r33 + 1
            r9 = r16
            r12 = r31
            r10 = 1
            r14 = 4
            r0 = r34
            goto L_0x0029
        L_0x0158:
            return r21
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.sendMTFValues1(int, int):int");
    }

    private void sendMTFValues2(int i, int i2) {
        Data data2 = this.data;
        byte[] bArr = data2.sendMTFValues2_pos;
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            bArr[i] = (byte) i;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            byte b = data2.selector[i3];
            byte b2 = bArr[0];
            int i4 = 0;
            while (b != b2) {
                i4++;
                byte b3 = bArr[i4];
                bArr[i4] = b2;
                b2 = b3;
            }
            bArr[0] = b2;
            data2.selectorMtf[i3] = (byte) i4;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: int[][]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: byte[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v0, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendMTFValues3(int r10, int r11) {
        /*
            r9 = this;
            org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data r0 = r9.data
            int[][] r0 = r0.sendMTFValues_code
            org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data r1 = r9.data
            byte[][] r1 = r1.sendMTFValues_len
            r2 = 0
            r3 = r2
        L_0x000a:
            if (r3 >= r10) goto L_0x002b
            r4 = r1[r3]
            r5 = 32
            r6 = r11
            r7 = r2
        L_0x0012:
            int r6 = r6 + -1
            if (r6 < 0) goto L_0x0021
            r8 = r4[r6]
            r8 = r8 & 255(0xff, float:3.57E-43)
            if (r8 <= r7) goto L_0x001d
            r7 = r8
        L_0x001d:
            if (r8 >= r5) goto L_0x0012
            r5 = r8
            goto L_0x0012
        L_0x0021:
            r4 = r0[r3]
            r6 = r1[r3]
            hbAssignCodes(r4, r6, r5, r7, r11)
            int r3 = r3 + 1
            goto L_0x000a
        L_0x002b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.sendMTFValues3(int, int):void");
    }

    private void sendMTFValues4() throws IOException {
        throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:553)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:701)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:813)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:161)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:433)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:129)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:528)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:425)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:441)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:123)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:271)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:107)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
    }

    private void sendMTFValues5(int i, int i2) throws IOException {
        bsW(3, i);
        bsW(15, i2);
        OutputStream outputStream = this.out;
        byte[] bArr = this.data.selectorMtf;
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        for (int i5 = 0; i5 < i2; i5++) {
            byte b = bArr[i5];
            for (int i6 = 0; i6 < (b & UByte.MAX_VALUE); i6++) {
                while (i3 >= 8) {
                    outputStream.write(i4 >> 24);
                    i4 <<= 8;
                    i3 -= 8;
                }
                i4 |= 1 << ((32 - i3) - 1);
                i3++;
            }
            while (i3 >= 8) {
                outputStream.write(i4 >> 24);
                i4 <<= 8;
                i3 -= 8;
            }
            i3++;
        }
        this.bsBuff = i4;
        this.bsLive = i3;
    }

    private void sendMTFValues6(int i, int i2) throws IOException {
        int[][] iArr = this.data.sendMTFValues_len;
        OutputStream outputStream = this.out;
        int i3 = this.bsLive;
        int i4 = this.bsBuff;
        int i5 = i;
        for (int i6 = 0; i6 < i5; i6++) {
            int[] iArr2 = iArr[i6];
            int i7 = iArr2[0] & 255;
            while (i3 >= 8) {
                outputStream.write(i4 >> 24);
                i4 <<= 8;
                i3 -= 8;
            }
            i4 = (i7 << ((32 - i3) - 5)) | i4;
            i3 += 5;
            int i8 = i2;
            for (int i9 = 0; i9 < i8; i9++) {
                int i10 = iArr2[i9] & 255;
                while (i7 < i10) {
                    while (i3 >= 8) {
                        outputStream.write(i4 >> 24);
                        i4 <<= 8;
                        i3 -= 8;
                    }
                    i4 |= 2 << ((32 - i3) - 2);
                    i3 += 2;
                    i7++;
                }
                while (i7 > i10) {
                    while (i3 >= 8) {
                        outputStream.write(i4 >> 24);
                        i4 <<= 8;
                        i3 -= 8;
                    }
                    i4 |= 3 << ((32 - i3) - 2);
                    i3 += 2;
                    i7--;
                }
                while (i3 >= 8) {
                    outputStream.write(i4 >> 24);
                    i4 <<= 8;
                    i3 -= 8;
                }
                i3++;
            }
        }
        this.bsBuff = i4;
        this.bsLive = i3;
    }

    private void sendMTFValues7() throws IOException {
        Data data2 = this.data;
        int[][] iArr = data2.sendMTFValues_len;
        int[][] iArr2 = data2.sendMTFValues_code;
        OutputStream outputStream = this.out;
        byte[] bArr = data2.selector;
        char[] cArr = data2.sfmap;
        int i = this.nMTF;
        int i2 = this.bsLive;
        int i3 = this.bsBuff;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i) {
            int min = Math.min((i4 + 50) - 1, i - 1);
            byte b = bArr[i5] & UByte.MAX_VALUE;
            int[] iArr3 = iArr2[b];
            int[] iArr4 = iArr[b];
            while (i4 <= min) {
                char c = cArr[i4];
                while (i2 >= 8) {
                    outputStream.write(i3 >> 24);
                    i3 <<= 8;
                    i2 -= 8;
                }
                int i6 = iArr4[c] & 255;
                i3 |= iArr3[c] << ((32 - i2) - i6);
                i2 += i6;
                i4++;
            }
            i4 = min + 1;
            i5++;
        }
        this.bsBuff = i3;
        this.bsLive = i2;
    }

    private void write0(int i) throws IOException {
        int i2;
        int i3 = this.currentChar;
        int i4 = i & 255;
        if (i3 == -1) {
            this.currentChar = i4;
            i2 = this.runLength + 1;
        } else if (i3 == i4) {
            int i5 = this.runLength + 1;
            this.runLength = i5;
            if (i5 > 254) {
                writeRun();
                this.currentChar = -1;
                i2 = 0;
            } else {
                return;
            }
        } else {
            writeRun();
            this.runLength = 1;
            this.currentChar = i4;
            return;
        }
        this.runLength = i2;
    }

    private void writeRun() throws IOException {
        int i;
        int i2 = this.last;
        if (i2 < this.allowableBlockSize) {
            int i3 = this.currentChar;
            Data data2 = this.data;
            data2.inUse[i3] = true;
            byte b = (byte) i3;
            int i4 = this.runLength;
            this.crc.updateCRC(i3, i4);
            switch (i4) {
                case 1:
                    data2.block[i2 + 2] = b;
                    this.last = i2 + 1;
                    return;
                case 2:
                    int i5 = i2 + 2;
                    data2.block[i5] = b;
                    data2.block[i2 + 3] = b;
                    this.last = i5;
                    return;
                case 3:
                    byte[] bArr = data2.block;
                    bArr[i2 + 2] = b;
                    i = i2 + 3;
                    bArr[i] = b;
                    bArr[i2 + 4] = b;
                    break;
                default:
                    int i6 = i4 - 4;
                    data2.inUse[i6] = true;
                    byte[] bArr2 = data2.block;
                    bArr2[i2 + 2] = b;
                    bArr2[i2 + 3] = b;
                    bArr2[i2 + 4] = b;
                    i = i2 + 5;
                    bArr2[i] = b;
                    bArr2[i2 + 6] = (byte) i6;
                    break;
            }
            this.last = i;
            return;
        }
        endBlock();
        initBlock();
        writeRun();
    }

    public void close() throws IOException {
        if (!this.closed) {
            OutputStream outputStream = this.out;
            finish();
            outputStream.close();
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (!this.closed) {
            System.err.println("Unclosed BZip2CompressorOutputStream detected, will *not* close it");
        }
        super.finalize();
    }

    public void finish() throws IOException {
        if (!this.closed) {
            this.closed = true;
            try {
                if (this.runLength > 0) {
                    writeRun();
                }
                this.currentChar = -1;
                endBlock();
                endCompression();
            } finally {
                this.out = null;
                this.blockSorter = null;
                this.data = null;
            }
        }
    }

    public void flush() throws IOException {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    public final int getBlockSize() {
        return this.blockSize100k;
    }

    public void write(int i) throws IOException {
        if (!this.closed) {
            write0(i);
            return;
        }
        throw new IOException("closed");
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (i < 0) {
            throw new IndexOutOfBoundsException("offs(" + i + ") < 0.");
        } else if (i2 >= 0) {
            int i3 = i + i2;
            if (i3 > bArr.length) {
                throw new IndexOutOfBoundsException("offs(" + i + ") + len(" + i2 + ") > buf.length(" + bArr.length + ").");
            } else if (!this.closed) {
                while (i < i3) {
                    write0(bArr[i]);
                    i++;
                }
            } else {
                throw new IOException("stream closed");
            }
        } else {
            throw new IndexOutOfBoundsException("len(" + i2 + ") < 0.");
        }
    }
}
