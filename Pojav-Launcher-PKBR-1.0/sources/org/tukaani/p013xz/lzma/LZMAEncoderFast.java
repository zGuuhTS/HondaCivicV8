package org.tukaani.p013xz.lzma;

import org.tukaani.p013xz.ArrayCache;
import org.tukaani.p013xz.LZMA2Options;
import org.tukaani.p013xz.p014lz.LZEncoder;
import org.tukaani.p013xz.p014lz.Matches;
import org.tukaani.p013xz.rangecoder.RangeEncoder;

/* renamed from: org.tukaani.xz.lzma.LZMAEncoderFast */
final class LZMAEncoderFast extends LZMAEncoder {
    private static final int EXTRA_SIZE_AFTER = 272;
    private static final int EXTRA_SIZE_BEFORE = 1;
    private Matches matches = null;

    LZMAEncoderFast(RangeEncoder rangeEncoder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ArrayCache arrayCache) {
        super(rangeEncoder, LZEncoder.getInstance(i4, Math.max(i5, 1), EXTRA_SIZE_AFTER, i6, LZMA2Options.NICE_LEN_MAX, i7, i8, arrayCache), i, i2, i3, i4, i6);
    }

    private boolean changePair(int i, int i2) {
        return i < (i2 >>> 7);
    }

    static int getMemoryUsage(int i, int i2, int i3) {
        return LZEncoder.getMemoryUsage(i, Math.max(i2, 1), EXTRA_SIZE_AFTER, LZMA2Options.NICE_LEN_MAX, i3);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00db  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getNextSymbol() {
        /*
            r11 = this;
            int r0 = r11.readAhead
            r1 = -1
            if (r0 != r1) goto L_0x000b
            org.tukaani.xz.lz.Matches r0 = r11.getMatches()
            r11.matches = r0
        L_0x000b:
            r11.back = r1
            org.tukaani.xz.lz.LZEncoder r0 = r11.f198lz
            int r0 = r0.getAvail()
            r1 = 273(0x111, float:3.83E-43)
            int r0 = java.lang.Math.min(r0, r1)
            r1 = 2
            r2 = 1
            if (r0 >= r1) goto L_0x001e
            return r2
        L_0x001e:
            r3 = 0
            r4 = r3
            r5 = r4
            r6 = r5
        L_0x0022:
            r7 = 4
            if (r4 >= r7) goto L_0x0045
            org.tukaani.xz.lz.LZEncoder r7 = r11.f198lz
            int[] r8 = r11.reps
            r8 = r8[r4]
            int r7 = r7.getMatchLen(r8, r0)
            if (r7 >= r1) goto L_0x0032
            goto L_0x0042
        L_0x0032:
            int r8 = r11.niceLen
            if (r7 < r8) goto L_0x003e
            r11.back = r4
            int r0 = r7 + -1
            r11.skip(r0)
            return r7
        L_0x003e:
            if (r7 <= r5) goto L_0x0042
            r6 = r4
            r5 = r7
        L_0x0042:
            int r4 = r4 + 1
            goto L_0x0022
        L_0x0045:
            org.tukaani.xz.lz.Matches r4 = r11.matches
            int r4 = r4.count
            if (r4 <= 0) goto L_0x00ba
            org.tukaani.xz.lz.Matches r4 = r11.matches
            int[] r4 = r4.len
            org.tukaani.xz.lz.Matches r8 = r11.matches
            int r8 = r8.count
            int r8 = r8 - r2
            r4 = r4[r8]
            org.tukaani.xz.lz.Matches r8 = r11.matches
            int[] r8 = r8.dist
            org.tukaani.xz.lz.Matches r9 = r11.matches
            int r9 = r9.count
            int r9 = r9 - r2
            r8 = r8[r9]
            int r9 = r11.niceLen
            if (r4 < r9) goto L_0x006e
            int r8 = r8 + r7
            r11.back = r8
            int r0 = r4 + -1
        L_0x006a:
            r11.skip(r0)
            return r4
        L_0x006e:
            org.tukaani.xz.lz.Matches r9 = r11.matches
            int r9 = r9.count
            if (r9 <= r2) goto L_0x00b2
            org.tukaani.xz.lz.Matches r9 = r11.matches
            int[] r9 = r9.len
            org.tukaani.xz.lz.Matches r10 = r11.matches
            int r10 = r10.count
            int r10 = r10 - r1
            r9 = r9[r10]
            int r9 = r9 + r2
            if (r4 != r9) goto L_0x00b2
            org.tukaani.xz.lz.Matches r9 = r11.matches
            int[] r9 = r9.dist
            org.tukaani.xz.lz.Matches r10 = r11.matches
            int r10 = r10.count
            int r10 = r10 - r1
            r9 = r9[r10]
            boolean r9 = r11.changePair(r9, r8)
            if (r9 != 0) goto L_0x0094
            goto L_0x00b2
        L_0x0094:
            org.tukaani.xz.lz.Matches r4 = r11.matches
            int r8 = r4.count
            int r8 = r8 - r2
            r4.count = r8
            org.tukaani.xz.lz.Matches r4 = r11.matches
            int[] r4 = r4.len
            org.tukaani.xz.lz.Matches r8 = r11.matches
            int r8 = r8.count
            int r8 = r8 - r2
            r4 = r4[r8]
            org.tukaani.xz.lz.Matches r8 = r11.matches
            int[] r8 = r8.dist
            org.tukaani.xz.lz.Matches r9 = r11.matches
            int r9 = r9.count
            int r9 = r9 - r2
            r8 = r8[r9]
            goto L_0x006e
        L_0x00b2:
            if (r4 != r1) goto L_0x00bc
            r9 = 128(0x80, float:1.794E-43)
            if (r8 < r9) goto L_0x00bc
            r4 = r2
            goto L_0x00bc
        L_0x00ba:
            r4 = r3
            r8 = r4
        L_0x00bc:
            if (r5 < r1) goto L_0x00db
            int r9 = r5 + 1
            if (r9 >= r4) goto L_0x00d3
            int r9 = r5 + 2
            if (r9 < r4) goto L_0x00ca
            r9 = 512(0x200, float:7.175E-43)
            if (r8 >= r9) goto L_0x00d3
        L_0x00ca:
            int r9 = r5 + 3
            if (r9 < r4) goto L_0x00db
            r9 = 32768(0x8000, float:4.5918E-41)
            if (r8 < r9) goto L_0x00db
        L_0x00d3:
            r11.back = r6
            int r0 = r5 + -1
            r11.skip(r0)
            return r5
        L_0x00db:
            if (r4 < r1) goto L_0x013c
            if (r0 > r1) goto L_0x00e0
            goto L_0x013c
        L_0x00e0:
            org.tukaani.xz.lz.Matches r0 = r11.getMatches()
            r11.matches = r0
            int r0 = r0.count
            if (r0 <= 0) goto L_0x011d
            org.tukaani.xz.lz.Matches r0 = r11.matches
            int[] r0 = r0.len
            org.tukaani.xz.lz.Matches r5 = r11.matches
            int r5 = r5.count
            int r5 = r5 - r2
            r0 = r0[r5]
            org.tukaani.xz.lz.Matches r5 = r11.matches
            int[] r5 = r5.dist
            org.tukaani.xz.lz.Matches r6 = r11.matches
            int r6 = r6.count
            int r6 = r6 - r2
            r5 = r5[r6]
            if (r0 < r4) goto L_0x0104
            if (r5 < r8) goto L_0x011c
        L_0x0104:
            int r6 = r4 + 1
            if (r0 != r6) goto L_0x010e
            boolean r9 = r11.changePair(r8, r5)
            if (r9 == 0) goto L_0x011c
        L_0x010e:
            if (r0 > r6) goto L_0x011c
            int r0 = r0 + r2
            if (r0 < r4) goto L_0x011d
            r0 = 3
            if (r4 < r0) goto L_0x011d
            boolean r0 = r11.changePair(r5, r8)
            if (r0 == 0) goto L_0x011d
        L_0x011c:
            return r2
        L_0x011d:
            int r0 = r4 + -1
            int r0 = java.lang.Math.max(r0, r1)
        L_0x0123:
            if (r3 >= r7) goto L_0x0135
            org.tukaani.xz.lz.LZEncoder r1 = r11.f198lz
            int[] r5 = r11.reps
            r5 = r5[r3]
            int r1 = r1.getMatchLen(r5, r0)
            if (r1 != r0) goto L_0x0132
            return r2
        L_0x0132:
            int r3 = r3 + 1
            goto L_0x0123
        L_0x0135:
            int r8 = r8 + r7
            r11.back = r8
            int r0 = r4 + -2
            goto L_0x006a
        L_0x013c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.lzma.LZMAEncoderFast.getNextSymbol():int");
    }
}
