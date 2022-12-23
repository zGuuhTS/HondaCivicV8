package org.apache.commons.compress.compressors.bzip2;

import java.util.BitSet;
import kotlin.UByte;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

class BlockSort {
    private static final int CLEARMASK = -2097153;
    private static final int DEPTH_THRESH = 10;
    private static final int FALLBACK_QSORT_SMALL_THRESH = 10;
    private static final int FALLBACK_QSORT_STACK_SIZE = 100;
    private static final int[] INCS = {1, 4, 13, 40, 121, 364, 1093, 3280, 9841, 29524, 88573, 265720, 797161, 2391484};
    private static final int QSORT_STACK_SIZE = 1000;
    private static final int SETMASK = 2097152;
    private static final int SMALL_THRESH = 20;
    private static final int STACK_SIZE = 1000;
    private static final int WORK_FACTOR = 30;
    private int[] eclass;
    private boolean firstAttempt;
    private final int[] ftab = new int[65537];
    private final boolean[] mainSort_bigDone = new boolean[256];
    private final int[] mainSort_copy = new int[256];
    private final int[] mainSort_runningOrder = new int[256];
    private final char[] quadrant;
    private final int[] stack_dd = new int[1000];
    private final int[] stack_hh = new int[1000];
    private final int[] stack_ll = new int[1000];
    private int workDone;
    private int workLimit;

    BlockSort(BZip2CompressorOutputStream.Data data) {
        this.quadrant = data.sfmap;
    }

    private void fallbackQSort3(int[] iArr, int[] iArr2, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int[] iArr3 = iArr;
        int[] iArr4 = iArr2;
        char c = 0;
        fpush(0, i, i2);
        long j = 0;
        int i6 = 1;
        long j2 = 0;
        int i7 = 1;
        while (i7 > 0) {
            i7--;
            int[] fpop = fpop(i7);
            int i8 = fpop[c];
            int i9 = fpop[i6];
            if (i9 - i8 < 10) {
                fallbackSimpleSort(iArr3, iArr4, i8, i9);
            } else {
                j2 = ((j2 * 7621) + 1) % 32768;
                long j3 = j2 % 3;
                long j4 = (long) (j3 == j ? iArr4[iArr3[i8]] : j3 == 1 ? iArr4[iArr3[(i8 + i9) >>> i6]] : iArr4[iArr3[i9]]);
                int i10 = i9;
                int i11 = i10;
                int i12 = i8;
                int i13 = i12;
                while (true) {
                    if (i13 <= i10) {
                        int i14 = iArr4[iArr3[i13]] - ((int) j4);
                        if (i14 == 0) {
                            fswap(iArr3, i13, i12);
                            i12++;
                            i13++;
                            i3 = i6;
                        } else if (i14 <= 0) {
                            i3 = i6;
                            i13++;
                        }
                        i6 = i3;
                    }
                    i4 = i11;
                    while (i13 <= i10) {
                        int i15 = iArr4[iArr3[i10]] - ((int) j4);
                        if (i15 == 0) {
                            fswap(iArr3, i10, i4);
                            i4--;
                            i10--;
                        } else if (i15 < 0) {
                            break;
                        } else {
                            i10--;
                        }
                    }
                    if (i13 > i10) {
                        break;
                    }
                    i3 = 1;
                    fswap(iArr3, i13, i10);
                    i13++;
                    i10--;
                    i11 = i4;
                    i6 = i3;
                }
                if (i4 < i12) {
                    c = 0;
                    j = 0;
                    i6 = 1;
                } else {
                    int fmin = fmin(i12 - i8, i13 - i12);
                    fvswap(iArr3, i8, i13 - fmin, fmin);
                    int i16 = i4 - i10;
                    int fmin2 = fmin(i9 - i4, i16);
                    fvswap(iArr3, i10 + 1, (i9 - fmin2) + 1, fmin2);
                    int i17 = ((i13 + i8) - i12) - 1;
                    int i18 = (i9 - i16) + 1;
                    if (i17 - i8 > i9 - i18) {
                        i5 = i7 + 1;
                        fpush(i7, i8, i17);
                        fpush(i5, i18, i9);
                    } else {
                        i5 = i7 + 1;
                        fpush(i7, i18, i9);
                        fpush(i5, i8, i17);
                    }
                    i7 = i5 + 1;
                    i6 = 1;
                    c = 0;
                    j = 0;
                }
            }
        }
    }

    private void fallbackSimpleSort(int[] iArr, int[] iArr2, int i, int i2) {
        if (i != i2) {
            if (i2 - i > 3) {
                for (int i3 = i2 - 4; i3 >= i; i3--) {
                    int i4 = iArr[i3];
                    int i5 = iArr2[i4];
                    int i6 = i3 + 4;
                    while (i6 <= i2 && i5 > iArr2[iArr[i6]]) {
                        iArr[i6 - 4] = iArr[i6];
                        i6 += 4;
                    }
                    iArr[i6 - 4] = i4;
                }
            }
            for (int i7 = i2 - 1; i7 >= i; i7--) {
                int i8 = iArr[i7];
                int i9 = iArr2[i8];
                int i10 = i7 + 1;
                while (i10 <= i2 && i9 > iArr2[iArr[i10]]) {
                    iArr[i10 - 1] = iArr[i10];
                    i10++;
                }
                iArr[i10 - 1] = i8;
            }
        }
    }

    private int fmin(int i, int i2) {
        return i < i2 ? i : i2;
    }

    private int[] fpop(int i) {
        return new int[]{this.stack_ll[i], this.stack_hh[i]};
    }

    private void fpush(int i, int i2, int i3) {
        this.stack_ll[i] = i2;
        this.stack_hh[i] = i3;
    }

    private void fswap(int[] iArr, int i, int i2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    private void fvswap(int[] iArr, int i, int i2, int i3) {
        while (i3 > 0) {
            fswap(iArr, i, i2);
            i++;
            i2++;
            i3--;
        }
    }

    private int[] getEclass() {
        int[] iArr = this.eclass;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[(this.quadrant.length / 2)];
        this.eclass = iArr2;
        return iArr2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0066, code lost:
        if (r6 < 0) goto L_0x0063;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void mainQSort3(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.Data r19, int r20, int r21, int r22, int r23) {
        /*
            r18 = this;
            r6 = r18
            r7 = r19
            int[] r8 = r6.stack_ll
            int[] r9 = r6.stack_hh
            int[] r10 = r6.stack_dd
            int[] r11 = r7.fmap
            byte[] r12 = r7.block
            r0 = 0
            r8[r0] = r20
            r9[r0] = r21
            r10[r0] = r22
            r13 = 1
            r0 = r13
        L_0x0017:
            int r14 = r0 + -1
            if (r14 < 0) goto L_0x00ff
            r2 = r8[r14]
            r3 = r9[r14]
            r4 = r10[r14]
            int r0 = r3 - r2
            r1 = 20
            if (r0 < r1) goto L_0x00e9
            r0 = 10
            if (r4 <= r0) goto L_0x002d
            goto L_0x00e9
        L_0x002d:
            int r0 = r4 + 1
            r1 = r11[r2]
            int r1 = r1 + r0
            byte r1 = r12[r1]
            r5 = r11[r3]
            int r5 = r5 + r0
            byte r5 = r12[r5]
            int r15 = r2 + r3
            int r15 = r15 >>> r13
            r15 = r11[r15]
            int r15 = r15 + r0
            byte r15 = r12[r15]
            byte r1 = med3(r1, r5, r15)
            r1 = r1 & 255(0xff, float:3.57E-43)
            r5 = r2
            r13 = r5
            r15 = r3
            r16 = r15
        L_0x004c:
            if (r5 > r15) goto L_0x006c
            r17 = r11[r5]
            int r17 = r17 + r0
            byte r6 = r12[r17]
            r6 = r6 & 255(0xff, float:3.57E-43)
            int r6 = r6 - r1
            if (r6 != 0) goto L_0x0066
            r6 = r11[r5]
            r17 = r11[r13]
            r11[r5] = r17
            r11[r13] = r6
            int r13 = r13 + 1
        L_0x0063:
            int r5 = r5 + 1
            goto L_0x0069
        L_0x0066:
            if (r6 >= 0) goto L_0x006c
            goto L_0x0063
        L_0x0069:
            r6 = r18
            goto L_0x004c
        L_0x006c:
            r6 = r16
        L_0x006e:
            if (r5 > r15) goto L_0x008e
            r16 = r11[r15]
            int r16 = r16 + r0
            byte r7 = r12[r16]
            r7 = r7 & 255(0xff, float:3.57E-43)
            int r7 = r7 - r1
            if (r7 != 0) goto L_0x0088
            r7 = r11[r15]
            r16 = r11[r6]
            r11[r15] = r16
            r11[r6] = r7
            int r6 = r6 + -1
        L_0x0085:
            int r15 = r15 + -1
            goto L_0x008b
        L_0x0088:
            if (r7 <= 0) goto L_0x008e
            goto L_0x0085
        L_0x008b:
            r7 = r19
            goto L_0x006e
        L_0x008e:
            if (r5 > r15) goto L_0x00a1
            r7 = r11[r5]
            r16 = r11[r15]
            r11[r5] = r16
            r11[r15] = r7
            int r15 = r15 + -1
            int r5 = r5 + 1
            r7 = r19
            r16 = r6
            goto L_0x0069
        L_0x00a1:
            if (r6 >= r13) goto L_0x00ae
            r8[r14] = r2
            r9[r14] = r3
            r10[r14] = r0
            int r14 = r14 + 1
            r0 = r14
            r15 = 1
            goto L_0x00f8
        L_0x00ae:
            int r1 = r13 - r2
            int r7 = r5 - r13
            if (r1 >= r7) goto L_0x00b5
            goto L_0x00b6
        L_0x00b5:
            r1 = r7
        L_0x00b6:
            int r7 = r5 - r1
            vswap(r11, r2, r7, r1)
            int r1 = r3 - r6
            int r6 = r6 - r15
            if (r1 >= r6) goto L_0x00c1
            goto L_0x00c2
        L_0x00c1:
            r1 = r6
        L_0x00c2:
            int r7 = r3 - r1
            r15 = 1
            int r7 = r7 + r15
            vswap(r11, r5, r7, r1)
            int r5 = r5 + r2
            int r5 = r5 - r13
            int r5 = r5 - r15
            int r1 = r3 - r6
            int r1 = r1 + r15
            r8[r14] = r2
            r9[r14] = r5
            r10[r14] = r4
            int r14 = r14 + 1
            int r5 = r5 + r15
            r8[r14] = r5
            int r2 = r1 + -1
            r9[r14] = r2
            r10[r14] = r0
            int r14 = r14 + r15
            r8[r14] = r1
            r9[r14] = r3
            r10[r14] = r4
            int r14 = r14 + r15
            goto L_0x00f7
        L_0x00e9:
            r15 = r13
            r0 = r18
            r1 = r19
            r5 = r23
            boolean r0 = r0.mainSimpleSort(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x00f7
            return
        L_0x00f7:
            r0 = r14
        L_0x00f8:
            r6 = r18
            r7 = r19
            r13 = r15
            goto L_0x0017
        L_0x00ff:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BlockSort.mainQSort3(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data, int, int, int, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0169, code lost:
        r4 = r19;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean mainSimpleSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.Data r30, int r31, int r32, int r33, int r34) {
        /*
            r29 = this;
            r0 = r29
            r1 = r30
            r2 = r32
            int r3 = r2 - r31
            r4 = 1
            int r3 = r3 + r4
            r6 = 2
            if (r3 >= r6) goto L_0x001a
            boolean r1 = r0.firstAttempt
            if (r1 == 0) goto L_0x0018
            int r1 = r0.workDone
            int r2 = r0.workLimit
            if (r1 <= r2) goto L_0x0018
            goto L_0x0019
        L_0x0018:
            r4 = 0
        L_0x0019:
            return r4
        L_0x001a:
            r6 = 0
        L_0x001b:
            int[] r7 = INCS
            r7 = r7[r6]
            if (r7 >= r3) goto L_0x0024
            int r6 = r6 + 1
            goto L_0x001b
        L_0x0024:
            int[] r3 = r1.fmap
            char[] r7 = r0.quadrant
            byte[] r1 = r1.block
            int r8 = r34 + 1
            boolean r9 = r0.firstAttempt
            int r10 = r0.workLimit
            int r11 = r0.workDone
        L_0x0032:
            int r6 = r6 - r4
            if (r6 < 0) goto L_0x021c
            int[] r12 = INCS
            r12 = r12[r6]
            int r13 = r31 + r12
            int r14 = r13 + -1
        L_0x003d:
            if (r13 > r2) goto L_0x0216
            r15 = 3
        L_0x0040:
            if (r13 > r2) goto L_0x01fe
            int r15 = r15 - r4
            if (r15 < 0) goto L_0x01fe
            r16 = r3[r13]
            int r17 = r16 + r33
            r19 = r13
            r18 = 0
            r20 = 0
        L_0x004f:
            if (r18 == 0) goto L_0x0064
            r3[r19] = r20
            int r4 = r19 - r12
            if (r4 > r14) goto L_0x0061
            r24 = r6
            r26 = r12
            r27 = r14
            r28 = r15
            goto L_0x01ed
        L_0x0061:
            r19 = r4
            goto L_0x0066
        L_0x0064:
            r18 = 1
        L_0x0066:
            int r4 = r19 - r12
            r4 = r3[r4]
            int r21 = r4 + r33
            int r22 = r21 + 1
            byte r5 = r1[r22]
            int r23 = r17 + 1
            r30 = r4
            byte r4 = r1[r23]
            if (r5 != r4) goto L_0x01c4
            int r4 = r21 + 2
            byte r5 = r1[r4]
            int r22 = r17 + 2
            r24 = r6
            byte r6 = r1[r22]
            if (r5 != r6) goto L_0x01b1
            int r4 = r21 + 3
            byte r5 = r1[r4]
            int r6 = r17 + 3
            r25 = r11
            byte r11 = r1[r6]
            if (r5 != r11) goto L_0x01a0
            int r4 = r21 + 4
            byte r5 = r1[r4]
            int r6 = r17 + 4
            byte r11 = r1[r6]
            if (r5 != r11) goto L_0x018f
            int r4 = r21 + 5
            byte r5 = r1[r4]
            int r6 = r17 + 5
            byte r11 = r1[r6]
            if (r5 != r11) goto L_0x017e
            int r21 = r21 + 6
            byte r4 = r1[r21]
            int r5 = r17 + 6
            byte r6 = r1[r5]
            if (r4 != r6) goto L_0x016d
            r4 = r34
            r11 = r25
        L_0x00b2:
            if (r4 <= 0) goto L_0x0163
            int r6 = r21 + 1
            r26 = r12
            byte r12 = r1[r6]
            int r22 = r5 + 1
            r27 = r14
            byte r14 = r1[r22]
            if (r12 != r14) goto L_0x0155
            char r12 = r7[r21]
            char r14 = r7[r5]
            if (r12 != r14) goto L_0x014b
            int r12 = r21 + 2
            byte r14 = r1[r12]
            int r23 = r5 + 2
            r28 = r15
            byte r15 = r1[r23]
            if (r14 != r15) goto L_0x013f
            char r14 = r7[r6]
            char r15 = r7[r22]
            if (r14 != r15) goto L_0x0137
            int r6 = r21 + 3
            byte r14 = r1[r6]
            int r15 = r5 + 3
            byte r0 = r1[r15]
            if (r14 != r0) goto L_0x012b
            char r0 = r7[r12]
            char r14 = r7[r23]
            if (r0 != r14) goto L_0x0123
            int r0 = r21 + 4
            byte r12 = r1[r0]
            int r5 = r5 + 4
            byte r14 = r1[r5]
            if (r12 != r14) goto L_0x0117
            char r12 = r7[r6]
            char r14 = r7[r15]
            if (r12 != r14) goto L_0x010f
            if (r0 < r8) goto L_0x00fd
            int r0 = r0 - r8
        L_0x00fd:
            r21 = r0
            if (r5 < r8) goto L_0x0102
            int r5 = r5 - r8
        L_0x0102:
            int r11 = r11 + 1
            int r4 = r4 + -4
            r0 = r29
            r12 = r26
            r14 = r27
            r15 = r28
            goto L_0x00b2
        L_0x010f:
            char r0 = r7[r6]
            char r4 = r7[r15]
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x0117:
            byte r0 = r1[r0]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r5]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x0123:
            char r0 = r7[r12]
            char r4 = r7[r23]
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x012b:
            byte r0 = r1[r6]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r15]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x0137:
            char r0 = r7[r6]
            char r4 = r7[r22]
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x013f:
            byte r0 = r1[r12]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r23]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x014b:
            r28 = r15
            char r0 = r7[r21]
            char r4 = r7[r5]
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x0155:
            r28 = r15
            byte r0 = r1[r6]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r22]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x0169
            goto L_0x01da
        L_0x0163:
            r26 = r12
            r27 = r14
            r28 = r15
        L_0x0169:
            r4 = r19
            goto L_0x01ed
        L_0x016d:
            r26 = r12
            r27 = r14
            r28 = r15
            byte r0 = r1[r21]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r5]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x01e9
            goto L_0x01d8
        L_0x017e:
            r26 = r12
            r27 = r14
            r28 = r15
            byte r0 = r1[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r6]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x01e9
            goto L_0x01d8
        L_0x018f:
            r26 = r12
            r27 = r14
            r28 = r15
            byte r0 = r1[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r6]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x01e9
            goto L_0x01d8
        L_0x01a0:
            r26 = r12
            r27 = r14
            r28 = r15
            byte r0 = r1[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r6]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x01e9
            goto L_0x01d8
        L_0x01b1:
            r25 = r11
            r26 = r12
            r27 = r14
            r28 = r15
            byte r0 = r1[r4]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r22]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x01e9
            goto L_0x01d8
        L_0x01c4:
            r24 = r6
            r25 = r11
            r26 = r12
            r27 = r14
            r28 = r15
            byte r0 = r1[r22]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r4 = r1[r23]
            r4 = r4 & 255(0xff, float:3.57E-43)
            if (r0 <= r4) goto L_0x01e9
        L_0x01d8:
            r11 = r25
        L_0x01da:
            r0 = r29
            r20 = r30
            r6 = r24
            r12 = r26
            r14 = r27
            r15 = r28
            r4 = 1
            goto L_0x004f
        L_0x01e9:
            r4 = r19
            r11 = r25
        L_0x01ed:
            r3[r4] = r16
            int r13 = r13 + 1
            r0 = r29
            r6 = r24
            r12 = r26
            r14 = r27
            r15 = r28
            r4 = 1
            goto L_0x0040
        L_0x01fe:
            r24 = r6
            r26 = r12
            r27 = r14
            if (r9 == 0) goto L_0x020b
            if (r13 > r2) goto L_0x020b
            if (r11 <= r10) goto L_0x020b
            goto L_0x021c
        L_0x020b:
            r0 = r29
            r6 = r24
            r12 = r26
            r14 = r27
            r4 = 1
            goto L_0x003d
        L_0x0216:
            r24 = r6
            r0 = r29
            goto L_0x0032
        L_0x021c:
            r0 = r29
            r0.workDone = r11
            if (r9 == 0) goto L_0x0226
            if (r11 <= r10) goto L_0x0226
            r4 = 1
            goto L_0x0227
        L_0x0226:
            r4 = 0
        L_0x0227:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BlockSort.mainSimpleSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data, int, int, int, int):boolean");
    }

    private static byte med3(byte b, byte b2, byte b3) {
        if (b < b2) {
            if (b2 >= b3) {
                if (b >= b3) {
                    return b;
                }
                return b3;
            }
        } else if (b2 <= b3) {
            if (b <= b3) {
                return b;
            }
            return b3;
        }
        return b2;
    }

    private static void vswap(int[] iArr, int i, int i2, int i3) {
        for (int i4 = i; i4 < i3 + i; i4++) {
            int i5 = iArr[i4];
            iArr[i4] = iArr[i2];
            iArr[i2] = i5;
            i2++;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001f, code lost:
        if (r3.workDone > r3.workLimit) goto L_0x0010;
     */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void blockSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream.Data r4, int r5) {
        /*
            r3 = this;
            int r0 = r5 * 30
            r3.workLimit = r0
            r0 = 0
            r3.workDone = r0
            r1 = 1
            r3.firstAttempt = r1
            int r1 = r5 + 1
            r2 = 10000(0x2710, float:1.4013E-41)
            if (r1 >= r2) goto L_0x0014
        L_0x0010:
            r3.fallbackSort(r4, r5)
            goto L_0x0022
        L_0x0014:
            r3.mainSort(r4, r5)
            boolean r1 = r3.firstAttempt
            if (r1 == 0) goto L_0x0022
            int r1 = r3.workDone
            int r2 = r3.workLimit
            if (r1 <= r2) goto L_0x0022
            goto L_0x0010
        L_0x0022:
            int[] r1 = r4.fmap
            r2 = -1
            r4.origPtr = r2
        L_0x0027:
            if (r0 > r5) goto L_0x0033
            r2 = r1[r0]
            if (r2 != 0) goto L_0x0030
            r4.origPtr = r0
            goto L_0x0033
        L_0x0030:
            int r0 = r0 + 1
            goto L_0x0027
        L_0x0033:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.bzip2.BlockSort.blockSort(org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream$Data, int):void");
    }

    /* access modifiers changed from: package-private */
    public final void fallbackSort(BZip2CompressorOutputStream.Data data, int i) {
        int i2 = i + 1;
        data.block[0] = data.block[i2];
        fallbackSort(data.fmap, data.block, i2);
        for (int i3 = 0; i3 < i2; i3++) {
            int[] iArr = data.fmap;
            iArr[i3] = iArr[i3] - 1;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            if (data.fmap[i4] == -1) {
                data.fmap[i4] = i;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void fallbackSort(int[] iArr, byte[] bArr, int i) {
        int i2;
        int[] iArr2 = new int[257];
        int[] eclass2 = getEclass();
        for (int i3 = 0; i3 < i; i3++) {
            eclass2[i3] = 0;
        }
        for (int i4 = 0; i4 < i; i4++) {
            byte b = bArr[i4] & UByte.MAX_VALUE;
            iArr2[b] = iArr2[b] + 1;
        }
        for (int i5 = 1; i5 < 257; i5++) {
            iArr2[i5] = iArr2[i5] + iArr2[i5 - 1];
        }
        for (int i6 = 0; i6 < i; i6++) {
            byte b2 = bArr[i6] & UByte.MAX_VALUE;
            int i7 = iArr2[b2] - 1;
            iArr2[b2] = i7;
            iArr[i7] = i6;
        }
        BitSet bitSet = new BitSet(i + 64);
        for (int i8 = 0; i8 < 256; i8++) {
            bitSet.set(iArr2[i8]);
        }
        for (int i9 = 0; i9 < 32; i9++) {
            int i10 = (i9 * 2) + i;
            bitSet.set(i10);
            bitSet.clear(i10 + 1);
        }
        int i11 = 1;
        do {
            int i12 = 0;
            for (int i13 = 0; i13 < i; i13++) {
                if (bitSet.get(i13)) {
                    i12 = i13;
                }
                int i14 = iArr[i13] - i11;
                if (i14 < 0) {
                    i14 += i;
                }
                eclass2[i14] = i12;
            }
            int i15 = -1;
            i2 = 0;
            while (true) {
                int nextClearBit = bitSet.nextClearBit(i15 + 1);
                int i16 = nextClearBit - 1;
                if (i16 < i && (i15 = bitSet.nextSetBit(nextClearBit + 1) - 1) < i) {
                    if (i15 > i16) {
                        i2 += (i15 - i16) + 1;
                        fallbackQSort3(iArr, eclass2, i16, i15);
                        int i17 = -1;
                        while (i16 <= i15) {
                            int i18 = eclass2[iArr[i16]];
                            if (i17 != i18) {
                                bitSet.set(i16);
                                i17 = i18;
                            }
                            i16++;
                        }
                    }
                }
            }
            i11 *= 2;
            if (i11 > i) {
                return;
            }
        } while (i2 != 0);
    }

    /* access modifiers changed from: package-private */
    public final void mainSort(BZip2CompressorOutputStream.Data data, int i) {
        int[] iArr;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        BZip2CompressorOutputStream.Data data2 = data;
        int i7 = i;
        int[] iArr2 = this.mainSort_runningOrder;
        int[] iArr3 = this.mainSort_copy;
        boolean[] zArr = this.mainSort_bigDone;
        int[] iArr4 = this.ftab;
        byte[] bArr = data2.block;
        int[] iArr5 = data2.fmap;
        char[] cArr = this.quadrant;
        int i8 = this.workLimit;
        boolean z = this.firstAttempt;
        int i9 = 65537;
        while (true) {
            i9--;
            if (i9 < 0) {
                break;
            }
            iArr4[i9] = 0;
        }
        for (int i10 = 0; i10 < 20; i10++) {
            bArr[i7 + i10 + 2] = bArr[(i10 % (i7 + 1)) + 1];
        }
        int i11 = i7 + 20 + 1;
        while (true) {
            i11--;
            if (i11 < 0) {
                break;
            }
            cArr[i11] = 0;
        }
        int i12 = i7 + 1;
        bArr[0] = bArr[i12];
        boolean z2 = z;
        int i13 = 255;
        byte b = bArr[0] & UByte.MAX_VALUE;
        int i14 = 0;
        while (i14 <= i7) {
            i14++;
            byte b2 = bArr[i14] & UByte.MAX_VALUE;
            int i15 = (b << 8) + b2;
            iArr4[i15] = iArr4[i15] + 1;
            b = b2;
        }
        for (int i16 = 1; i16 <= 65536; i16++) {
            iArr4[i16] = iArr4[i16] + iArr4[i16 - 1];
        }
        boolean z3 = true;
        byte b3 = bArr[1] & UByte.MAX_VALUE;
        int i17 = 0;
        while (i17 < i7) {
            byte b4 = bArr[i17 + 2] & UByte.MAX_VALUE;
            int i18 = (b3 << 8) + b4;
            int i19 = iArr4[i18] - 1;
            iArr4[i18] = i19;
            iArr5[i19] = i17;
            i17++;
            b3 = b4;
            z3 = true;
        }
        int i20 = ((bArr[i12] & UByte.MAX_VALUE) << 8) + (bArr[z3] & UByte.MAX_VALUE);
        int i21 = iArr4[i20] - 1;
        iArr4[i20] = i21;
        iArr5[i21] = i7;
        int i22 = 256;
        while (true) {
            i22--;
            if (i22 < 0) {
                break;
            }
            zArr[i22] = false;
            iArr2[i22] = i22;
        }
        int i23 = 364;
        while (i23 != 1) {
            i23 /= 3;
            int i24 = i23;
            while (i24 <= i13) {
                int i25 = iArr2[i24];
                int i26 = iArr4[(i25 + 1) << 8];
                int i27 = iArr4[i25 << 8];
                int i28 = iArr2[i24 - i23];
                int i29 = i24;
                while (true) {
                    i6 = i8;
                    if (iArr4[(i28 + 1) << 8] - iArr4[i28 << 8] <= i26 - i27) {
                        break;
                    }
                    iArr2[i29] = i28;
                    int i30 = i29 - i23;
                    if (i30 <= i23 - 1) {
                        i29 = i30;
                        break;
                    }
                    i28 = iArr2[i30 - i23];
                    i29 = i30;
                    i8 = i6;
                }
                iArr2[i29] = i25;
                i24++;
                i8 = i6;
                i13 = 255;
            }
        }
        int i31 = i8;
        int i32 = 0;
        while (i32 <= i13) {
            int i33 = iArr2[i32];
            int i34 = 0;
            while (i34 <= i13) {
                int i35 = (i33 << 8) + i34;
                int i36 = iArr4[i35];
                if ((i36 & 2097152) != 2097152) {
                    int i37 = i36 & CLEARMASK;
                    int i38 = (iArr4[i35 + 1] & CLEARMASK) - 1;
                    if (i38 > i37) {
                        i5 = 2097152;
                        i2 = i34;
                        int i39 = i13;
                        i4 = i31;
                        iArr = iArr2;
                        i3 = i32;
                        mainQSort3(data, i37, i38, 2, i);
                        if (z2 && this.workDone > i4) {
                            return;
                        }
                    } else {
                        i2 = i34;
                        i4 = i31;
                        i5 = 2097152;
                        iArr = iArr2;
                        i3 = i32;
                    }
                    iArr4[i35] = i36 | i5;
                } else {
                    i2 = i34;
                    i4 = i31;
                    iArr = iArr2;
                    i3 = i32;
                }
                i34 = i2 + 1;
                i32 = i3;
                iArr2 = iArr;
                i13 = 255;
                i31 = i4;
                BZip2CompressorOutputStream.Data data3 = data;
            }
            int i40 = i31;
            int[] iArr6 = iArr2;
            int i41 = i32;
            int i42 = 0;
            for (int i43 = i13; i42 <= i43; i43 = 255) {
                iArr3[i42] = iArr4[(i42 << 8) + i33] & CLEARMASK;
                i42++;
            }
            int i44 = i33 << 8;
            int i45 = iArr4[i44] & CLEARMASK;
            int i46 = (i33 + 1) << 8;
            int i47 = iArr4[i46] & CLEARMASK;
            while (i45 < i47) {
                int i48 = iArr5[i45];
                int i49 = i47;
                byte b5 = bArr[i48] & UByte.MAX_VALUE;
                if (!zArr[b5]) {
                    iArr5[iArr3[b5]] = i48 == 0 ? i7 : i48 - 1;
                    iArr3[b5] = iArr3[b5] + 1;
                }
                i45++;
                i47 = i49;
            }
            int i50 = 256;
            while (true) {
                i50--;
                if (i50 < 0) {
                    break;
                }
                int i51 = (i50 << 8) + i33;
                iArr4[i51] = iArr4[i51] | 2097152;
            }
            zArr[i33] = true;
            if (i41 < 255) {
                int i52 = iArr4[i44] & CLEARMASK;
                int i53 = (CLEARMASK & iArr4[i46]) - i52;
                int i54 = 0;
                while ((i53 >> i54) > 65534) {
                    i54++;
                }
                int i55 = 0;
                while (i55 < i53) {
                    int i56 = iArr5[i52 + i55];
                    char c = (char) (i55 >> i54);
                    cArr[i56] = c;
                    int i57 = i52;
                    if (i56 < 20) {
                        cArr[i56 + i7 + 1] = c;
                    }
                    i55++;
                    i52 = i57;
                }
            }
            i32 = i41 + 1;
            iArr2 = iArr6;
            i13 = 255;
            i31 = i40;
            BZip2CompressorOutputStream.Data data4 = data;
        }
    }
}
