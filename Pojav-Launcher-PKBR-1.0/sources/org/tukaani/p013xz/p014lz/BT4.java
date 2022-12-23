package org.tukaani.p013xz.p014lz;

import kotlin.UByte;
import org.tukaani.p013xz.ArrayCache;

/* renamed from: org.tukaani.xz.lz.BT4 */
final class BT4 extends LZEncoder {
    private int cyclicPos = -1;
    private final int cyclicSize;
    private final int depthLimit;
    private final Hash234 hash;
    private int lzPos;
    private final Matches matches;
    private final int[] tree;

    BT4(int i, int i2, int i3, int i4, int i5, int i6, ArrayCache arrayCache) {
        super(i, i2, i3, i4, i5, arrayCache);
        int i7 = i + 1;
        this.cyclicSize = i7;
        this.lzPos = i7;
        this.hash = new Hash234(i, arrayCache);
        this.tree = arrayCache.getIntArray(i7 * 2, false);
        this.matches = new Matches(i4 - 1);
        this.depthLimit = i6 <= 0 ? (i4 / 2) + 16 : i6;
    }

    static int getMemoryUsage(int i) {
        return Hash234.getMemoryUsage(i) + (i / 128) + 10;
    }

    private int movePos() {
        int movePos = movePos(this.niceLen, 4);
        if (movePos != 0) {
            int i = this.lzPos + 1;
            this.lzPos = i;
            if (i == Integer.MAX_VALUE) {
                int i2 = Integer.MAX_VALUE - this.cyclicSize;
                this.hash.normalize(i2);
                normalize(this.tree, this.cyclicSize * 2, i2);
                this.lzPos -= i2;
            }
            int i3 = this.cyclicPos + 1;
            this.cyclicPos = i3;
            if (i3 == this.cyclicSize) {
                this.cyclicPos = 0;
            }
        }
        return movePos;
    }

    private void skip(int i, int i2) {
        int i3;
        int i4 = this.depthLimit;
        int i5 = this.cyclicPos;
        int i6 = (i5 << 1) + 1;
        int i7 = i5 << 1;
        int i8 = 0;
        int i9 = 0;
        while (true) {
            int i10 = this.lzPos - i2;
            int i11 = i4 - 1;
            if (i4 == 0 || i10 >= (i3 = this.cyclicSize)) {
                int[] iArr = this.tree;
                iArr[i6] = 0;
                iArr[i7] = 0;
            } else {
                int i12 = this.cyclicPos;
                int i13 = i12 - i10;
                if (i10 <= i12) {
                    i3 = 0;
                }
                int i14 = (i13 + i3) << 1;
                int min = Math.min(i8, i9);
                if (this.buf[(this.readPos + min) - i10] == this.buf[this.readPos + min]) {
                    do {
                        min++;
                        if (min == i) {
                            int[] iArr2 = this.tree;
                            iArr2[i7] = iArr2[i14];
                            iArr2[i6] = iArr2[i14 + 1];
                            return;
                        }
                    } while (this.buf[(this.readPos + min) - i10] == this.buf[this.readPos + min]);
                }
                if ((this.buf[(this.readPos + min) - i10] & UByte.MAX_VALUE) < (this.buf[this.readPos + min] & UByte.MAX_VALUE)) {
                    int[] iArr3 = this.tree;
                    iArr3[i7] = i2;
                    int i15 = i14 + 1;
                    i2 = iArr3[i15];
                    i7 = i15;
                    i9 = min;
                } else {
                    int[] iArr4 = this.tree;
                    iArr4[i6] = i2;
                    i2 = iArr4[i14];
                    i6 = i14;
                    i8 = min;
                }
                i4 = i11;
            }
        }
        int[] iArr5 = this.tree;
        iArr5[i6] = 0;
        iArr5[i7] = 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x0114  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0159  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0165  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.tukaani.p013xz.p014lz.Matches getMatches() {
        /*
            r16 = this;
            r0 = r16
            org.tukaani.xz.lz.Matches r1 = r0.matches
            r2 = 0
            r1.count = r2
            int r1 = r0.matchLenMax
            int r3 = r0.niceLen
            int r4 = r16.movePos()
            if (r4 >= r1) goto L_0x001a
            if (r4 != 0) goto L_0x0016
            org.tukaani.xz.lz.Matches r1 = r0.matches
            return r1
        L_0x0016:
            r1 = r4
            if (r3 <= r4) goto L_0x001a
            r3 = r1
        L_0x001a:
            org.tukaani.xz.lz.Hash234 r4 = r0.hash
            byte[] r5 = r0.buf
            int r6 = r0.readPos
            r4.calcHashes(r5, r6)
            int r4 = r0.lzPos
            org.tukaani.xz.lz.Hash234 r5 = r0.hash
            int r5 = r5.getHash2Pos()
            int r4 = r4 - r5
            int r5 = r0.lzPos
            org.tukaani.xz.lz.Hash234 r6 = r0.hash
            int r6 = r6.getHash3Pos()
            int r5 = r5 - r6
            org.tukaani.xz.lz.Hash234 r6 = r0.hash
            int r6 = r6.getHash4Pos()
            org.tukaani.xz.lz.Hash234 r7 = r0.hash
            int r8 = r0.lzPos
            r7.updateTables(r8)
            int r7 = r0.cyclicSize
            r8 = 2
            r9 = 1
            if (r4 >= r7) goto L_0x006a
            byte[] r7 = r0.buf
            int r10 = r0.readPos
            int r10 = r10 - r4
            byte r7 = r7[r10]
            byte[] r10 = r0.buf
            int r11 = r0.readPos
            byte r10 = r10[r11]
            if (r7 != r10) goto L_0x006a
            org.tukaani.xz.lz.Matches r7 = r0.matches
            int[] r7 = r7.len
            r7[r2] = r8
            org.tukaani.xz.lz.Matches r7 = r0.matches
            int[] r7 = r7.dist
            int r10 = r4 + -1
            r7[r2] = r10
            org.tukaani.xz.lz.Matches r7 = r0.matches
            r7.count = r9
            goto L_0x006b
        L_0x006a:
            r8 = r2
        L_0x006b:
            r7 = 3
            if (r4 == r5) goto L_0x0093
            int r10 = r0.cyclicSize
            if (r5 >= r10) goto L_0x0093
            byte[] r10 = r0.buf
            int r11 = r0.readPos
            int r11 = r11 - r5
            byte r10 = r10[r11]
            byte[] r11 = r0.buf
            int r12 = r0.readPos
            byte r11 = r11[r12]
            if (r10 != r11) goto L_0x0093
            org.tukaani.xz.lz.Matches r4 = r0.matches
            int[] r4 = r4.dist
            org.tukaani.xz.lz.Matches r8 = r0.matches
            int r10 = r8.count
            int r11 = r10 + 1
            r8.count = r11
            int r8 = r5 + -1
            r4[r10] = r8
            r4 = r5
            r8 = r7
        L_0x0093:
            org.tukaani.xz.lz.Matches r5 = r0.matches
            int r5 = r5.count
            if (r5 <= 0) goto L_0x00c2
        L_0x0099:
            if (r8 >= r1) goto L_0x00af
            byte[] r5 = r0.buf
            int r10 = r0.readPos
            int r10 = r10 + r8
            int r10 = r10 - r4
            byte r5 = r5[r10]
            byte[] r10 = r0.buf
            int r11 = r0.readPos
            int r11 = r11 + r8
            byte r10 = r10[r11]
            if (r5 != r10) goto L_0x00af
            int r8 = r8 + 1
            goto L_0x0099
        L_0x00af:
            org.tukaani.xz.lz.Matches r4 = r0.matches
            int[] r4 = r4.len
            org.tukaani.xz.lz.Matches r5 = r0.matches
            int r5 = r5.count
            int r5 = r5 - r9
            r4[r5] = r8
            if (r8 < r3) goto L_0x00c2
            r0.skip(r3, r6)
        L_0x00bf:
            org.tukaani.xz.lz.Matches r1 = r0.matches
            return r1
        L_0x00c2:
            if (r8 >= r7) goto L_0x00c5
            goto L_0x00c6
        L_0x00c5:
            r7 = r8
        L_0x00c6:
            int r4 = r0.depthLimit
            int r5 = r0.cyclicPos
            int r8 = r5 << 1
            int r8 = r8 + r9
            int r5 = r5 << r9
            r10 = r2
            r11 = r10
        L_0x00d0:
            int r12 = r0.lzPos
            int r12 = r12 - r6
            int r13 = r4 + -1
            if (r4 == 0) goto L_0x0172
            int r4 = r0.cyclicSize
            if (r12 < r4) goto L_0x00dd
            goto L_0x0172
        L_0x00dd:
            int r14 = r0.cyclicPos
            int r15 = r14 - r12
            if (r12 <= r14) goto L_0x00e4
            goto L_0x00e5
        L_0x00e4:
            r4 = r2
        L_0x00e5:
            int r15 = r15 + r4
            int r4 = r15 << 1
            int r14 = java.lang.Math.min(r10, r11)
            byte[] r15 = r0.buf
            int r2 = r0.readPos
            int r2 = r2 + r14
            int r2 = r2 - r12
            byte r2 = r15[r2]
            byte[] r15 = r0.buf
            int r9 = r0.readPos
            int r9 = r9 + r14
            byte r9 = r15[r9]
            if (r2 != r9) goto L_0x0143
        L_0x00fd:
            r2 = 1
            int r14 = r14 + r2
            if (r14 >= r1) goto L_0x0112
            byte[] r2 = r0.buf
            int r9 = r0.readPos
            int r9 = r9 + r14
            int r9 = r9 - r12
            byte r2 = r2[r9]
            byte[] r9 = r0.buf
            int r15 = r0.readPos
            int r15 = r15 + r14
            byte r9 = r9[r15]
            if (r2 == r9) goto L_0x00fd
        L_0x0112:
            if (r14 <= r7) goto L_0x0143
            org.tukaani.xz.lz.Matches r2 = r0.matches
            int[] r2 = r2.len
            org.tukaani.xz.lz.Matches r7 = r0.matches
            int r7 = r7.count
            r2[r7] = r14
            org.tukaani.xz.lz.Matches r2 = r0.matches
            int[] r2 = r2.dist
            org.tukaani.xz.lz.Matches r7 = r0.matches
            int r7 = r7.count
            int r9 = r12 + -1
            r2[r7] = r9
            org.tukaani.xz.lz.Matches r2 = r0.matches
            int r7 = r2.count
            r9 = 1
            int r7 = r7 + r9
            r2.count = r7
            if (r14 < r3) goto L_0x0141
            int[] r1 = r0.tree
            r2 = r1[r4]
            r1[r5] = r2
            int r4 = r4 + r9
            r2 = r1[r4]
            r1[r8] = r2
            goto L_0x00bf
        L_0x0141:
            r7 = r14
            goto L_0x0144
        L_0x0143:
            r9 = 1
        L_0x0144:
            byte[] r2 = r0.buf
            int r15 = r0.readPos
            int r15 = r15 + r14
            int r15 = r15 - r12
            byte r2 = r2[r15]
            r2 = r2 & 255(0xff, float:3.57E-43)
            byte[] r12 = r0.buf
            int r15 = r0.readPos
            int r15 = r15 + r14
            byte r12 = r12[r15]
            r12 = r12 & 255(0xff, float:3.57E-43)
            if (r2 >= r12) goto L_0x0165
            int[] r2 = r0.tree
            r2[r5] = r6
            int r4 = r4 + 1
            r2 = r2[r4]
            r6 = r2
            r5 = r4
            r11 = r14
            goto L_0x016e
        L_0x0165:
            int[] r2 = r0.tree
            r2[r8] = r6
            r2 = r2[r4]
            r6 = r2
            r8 = r4
            r10 = r14
        L_0x016e:
            r4 = r13
            r2 = 0
            goto L_0x00d0
        L_0x0172:
            int[] r1 = r0.tree
            r2 = 0
            r1[r8] = r2
            r1[r5] = r2
            goto L_0x00bf
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.p014lz.BT4.getMatches():org.tukaani.xz.lz.Matches");
    }

    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.tree);
        this.hash.putArraysToCache(arrayCache);
        super.putArraysToCache(arrayCache);
    }

    public void skip(int i) {
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                int i3 = this.niceLen;
                int movePos = movePos();
                if (movePos < i3) {
                    if (movePos == 0) {
                        i = i2;
                    } else {
                        i3 = movePos;
                    }
                }
                this.hash.calcHashes(this.buf, this.readPos);
                int hash4Pos = this.hash.getHash4Pos();
                this.hash.updateTables(this.lzPos);
                skip(i3, hash4Pos);
                i = i2;
            } else {
                return;
            }
        }
    }
}
