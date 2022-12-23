package org.tukaani.p013xz.p014lz;

import org.tukaani.p013xz.ArrayCache;

/* renamed from: org.tukaani.xz.lz.HC4 */
final class HC4 extends LZEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int[] chain;
    private int cyclicPos = -1;
    private final int cyclicSize;
    private final int depthLimit;
    private final Hash234 hash;
    private int lzPos;
    private final Matches matches;

    HC4(int i, int i2, int i3, int i4, int i5, int i6, ArrayCache arrayCache) {
        super(i, i2, i3, i4, i5, arrayCache);
        this.hash = new Hash234(i, arrayCache);
        int i7 = i + 1;
        this.cyclicSize = i7;
        this.chain = arrayCache.getIntArray(i7, false);
        this.lzPos = i7;
        this.matches = new Matches(i4 - 1);
        this.depthLimit = i6 <= 0 ? (i4 / 4) + 4 : i6;
    }

    static int getMemoryUsage(int i) {
        return Hash234.getMemoryUsage(i) + (i / 256) + 10;
    }

    private int movePos() {
        int movePos = movePos(4, 4);
        if (movePos != 0) {
            int i = this.lzPos + 1;
            this.lzPos = i;
            if (i == Integer.MAX_VALUE) {
                int i2 = Integer.MAX_VALUE - this.cyclicSize;
                this.hash.normalize(i2);
                normalize(this.chain, this.cyclicSize, i2);
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

    /* JADX WARNING: Removed duplicated region for block: B:51:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x013d A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.tukaani.p013xz.p014lz.Matches getMatches() {
        /*
            r13 = this;
            org.tukaani.xz.lz.Matches r0 = r13.matches
            r1 = 0
            r0.count = r1
            int r0 = r13.matchLenMax
            int r2 = r13.niceLen
            int r3 = r13.movePos()
            if (r3 >= r0) goto L_0x0018
            if (r3 != 0) goto L_0x0014
            org.tukaani.xz.lz.Matches r0 = r13.matches
            return r0
        L_0x0014:
            r0 = r3
            if (r2 <= r3) goto L_0x0018
            r2 = r0
        L_0x0018:
            org.tukaani.xz.lz.Hash234 r3 = r13.hash
            byte[] r4 = r13.buf
            int r5 = r13.readPos
            r3.calcHashes(r4, r5)
            int r3 = r13.lzPos
            org.tukaani.xz.lz.Hash234 r4 = r13.hash
            int r4 = r4.getHash2Pos()
            int r3 = r3 - r4
            int r4 = r13.lzPos
            org.tukaani.xz.lz.Hash234 r5 = r13.hash
            int r5 = r5.getHash3Pos()
            int r4 = r4 - r5
            org.tukaani.xz.lz.Hash234 r5 = r13.hash
            int r5 = r5.getHash4Pos()
            org.tukaani.xz.lz.Hash234 r6 = r13.hash
            int r7 = r13.lzPos
            r6.updateTables(r7)
            int[] r6 = r13.chain
            int r7 = r13.cyclicPos
            r6[r7] = r5
            int r6 = r13.cyclicSize
            r7 = 2
            r8 = 1
            if (r3 >= r6) goto L_0x006e
            byte[] r6 = r13.buf
            int r9 = r13.readPos
            int r9 = r9 - r3
            byte r6 = r6[r9]
            byte[] r9 = r13.buf
            int r10 = r13.readPos
            byte r9 = r9[r10]
            if (r6 != r9) goto L_0x006e
            org.tukaani.xz.lz.Matches r6 = r13.matches
            int[] r6 = r6.len
            r6[r1] = r7
            org.tukaani.xz.lz.Matches r6 = r13.matches
            int[] r6 = r6.dist
            int r9 = r3 + -1
            r6[r1] = r9
            org.tukaani.xz.lz.Matches r6 = r13.matches
            r6.count = r8
            goto L_0x006f
        L_0x006e:
            r7 = r1
        L_0x006f:
            r6 = 3
            if (r3 == r4) goto L_0x0097
            int r9 = r13.cyclicSize
            if (r4 >= r9) goto L_0x0097
            byte[] r9 = r13.buf
            int r10 = r13.readPos
            int r10 = r10 - r4
            byte r9 = r9[r10]
            byte[] r10 = r13.buf
            int r11 = r13.readPos
            byte r10 = r10[r11]
            if (r9 != r10) goto L_0x0097
            org.tukaani.xz.lz.Matches r3 = r13.matches
            int[] r3 = r3.dist
            org.tukaani.xz.lz.Matches r7 = r13.matches
            int r9 = r7.count
            int r10 = r9 + 1
            r7.count = r10
            int r7 = r4 + -1
            r3[r9] = r7
            r3 = r4
            r7 = r6
        L_0x0097:
            org.tukaani.xz.lz.Matches r4 = r13.matches
            int r4 = r4.count
            if (r4 <= 0) goto L_0x00c3
        L_0x009d:
            if (r7 >= r0) goto L_0x00b3
            byte[] r4 = r13.buf
            int r9 = r13.readPos
            int r9 = r9 + r7
            int r9 = r9 - r3
            byte r4 = r4[r9]
            byte[] r9 = r13.buf
            int r10 = r13.readPos
            int r10 = r10 + r7
            byte r9 = r9[r10]
            if (r4 != r9) goto L_0x00b3
            int r7 = r7 + 1
            goto L_0x009d
        L_0x00b3:
            org.tukaani.xz.lz.Matches r3 = r13.matches
            int[] r3 = r3.len
            org.tukaani.xz.lz.Matches r4 = r13.matches
            int r4 = r4.count
            int r4 = r4 - r8
            r3[r4] = r7
            if (r7 < r2) goto L_0x00c3
            org.tukaani.xz.lz.Matches r0 = r13.matches
            return r0
        L_0x00c3:
            if (r7 >= r6) goto L_0x00c6
            goto L_0x00c7
        L_0x00c6:
            r6 = r7
        L_0x00c7:
            int r3 = r13.depthLimit
        L_0x00c9:
            int r4 = r13.lzPos
            int r4 = r4 - r5
            int r5 = r3 + -1
            if (r3 == 0) goto L_0x0141
            int r3 = r13.cyclicSize
            if (r4 < r3) goto L_0x00d6
            goto L_0x0141
        L_0x00d6:
            int[] r7 = r13.chain
            int r9 = r13.cyclicPos
            int r10 = r9 - r4
            if (r4 <= r9) goto L_0x00df
            goto L_0x00e0
        L_0x00df:
            r3 = r1
        L_0x00e0:
            int r10 = r10 + r3
            r3 = r7[r10]
            byte[] r7 = r13.buf
            int r9 = r13.readPos
            int r9 = r9 + r6
            int r9 = r9 - r4
            byte r7 = r7[r9]
            byte[] r9 = r13.buf
            int r10 = r13.readPos
            int r10 = r10 + r6
            byte r9 = r9[r10]
            if (r7 != r9) goto L_0x013d
            byte[] r7 = r13.buf
            int r9 = r13.readPos
            int r9 = r9 - r4
            byte r7 = r7[r9]
            byte[] r9 = r13.buf
            int r10 = r13.readPos
            byte r9 = r9[r10]
            if (r7 != r9) goto L_0x013d
            r7 = r1
        L_0x0104:
            int r7 = r7 + r8
            if (r7 >= r0) goto L_0x0118
            byte[] r9 = r13.buf
            int r10 = r13.readPos
            int r10 = r10 + r7
            int r10 = r10 - r4
            byte r9 = r9[r10]
            byte[] r10 = r13.buf
            int r11 = r13.readPos
            int r11 = r11 + r7
            byte r10 = r10[r11]
            if (r9 == r10) goto L_0x0104
        L_0x0118:
            if (r7 <= r6) goto L_0x013d
            org.tukaani.xz.lz.Matches r6 = r13.matches
            int[] r6 = r6.len
            org.tukaani.xz.lz.Matches r9 = r13.matches
            int r9 = r9.count
            r6[r9] = r7
            org.tukaani.xz.lz.Matches r6 = r13.matches
            int[] r6 = r6.dist
            org.tukaani.xz.lz.Matches r9 = r13.matches
            int r9 = r9.count
            int r4 = r4 + -1
            r6[r9] = r4
            org.tukaani.xz.lz.Matches r4 = r13.matches
            int r6 = r4.count
            int r6 = r6 + r8
            r4.count = r6
            if (r7 < r2) goto L_0x013c
            org.tukaani.xz.lz.Matches r0 = r13.matches
            return r0
        L_0x013c:
            r6 = r7
        L_0x013d:
            r12 = r5
            r5 = r3
            r3 = r12
            goto L_0x00c9
        L_0x0141:
            org.tukaani.xz.lz.Matches r0 = r13.matches
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.p014lz.HC4.getMatches():org.tukaani.xz.lz.Matches");
    }

    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.chain);
        this.hash.putArraysToCache(arrayCache);
        super.putArraysToCache(arrayCache);
    }

    public void skip(int i) {
        if (i >= 0) {
            while (true) {
                int i2 = i - 1;
                if (i > 0) {
                    if (movePos() != 0) {
                        this.hash.calcHashes(this.buf, this.readPos);
                        this.chain[this.cyclicPos] = this.hash.getHash4Pos();
                        this.hash.updateTables(this.lzPos);
                    }
                    i = i2;
                } else {
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }
}
