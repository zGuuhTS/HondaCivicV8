package org.tukaani.p013xz.lzma;

import org.apache.commons.compress.archivers.zip.UnixStat;
import org.tukaani.p013xz.ArrayCache;
import org.tukaani.p013xz.LZMA2Options;
import org.tukaani.p013xz.p014lz.LZEncoder;
import org.tukaani.p013xz.p014lz.Matches;
import org.tukaani.p013xz.rangecoder.RangeEncoder;

/* renamed from: org.tukaani.xz.lzma.LZMAEncoderNormal */
final class LZMAEncoderNormal extends LZMAEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int EXTRA_SIZE_AFTER = 4096;
    private static final int EXTRA_SIZE_BEFORE = 4096;
    private static final int OPTS = 4096;
    private Matches matches;
    private final State nextState;
    private int optCur;
    private int optEnd;
    private final Optimum[] opts = new Optimum[4096];
    private final int[] repLens;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    LZMAEncoderNormal(RangeEncoder rangeEncoder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, ArrayCache arrayCache) {
        super(rangeEncoder, LZEncoder.getInstance(i4, Math.max(i5, 4096), 4096, i6, LZMA2Options.NICE_LEN_MAX, i7, i8, arrayCache), i, i2, i3, i4, i6);
        this.optCur = 0;
        this.optEnd = 0;
        this.repLens = new int[4];
        this.nextState = new State();
        for (int i9 = 0; i9 < 4096; i9++) {
            this.opts[i9] = new Optimum();
        }
    }

    private void calc1BytePrices(int i, int i2, int i3, int i4) {
        boolean z;
        int matchLen;
        int i5;
        int shortRepPrice;
        int i6 = this.f198lz.getByte(0);
        int i7 = this.f198lz.getByte(this.opts[this.optCur].reps[0] + 1);
        int price = this.opts[this.optCur].price + this.literalEncoder.getPrice(i6, i7, this.f198lz.getByte(1), i, this.opts[this.optCur].state);
        if (price < this.opts[this.optCur + 1].price) {
            Optimum[] optimumArr = this.opts;
            int i8 = this.optCur;
            optimumArr[i8 + 1].set1(price, i8, -1);
            z = true;
        } else {
            z = false;
        }
        if (i7 == i6 && ((this.opts[this.optCur + 1].optPrev == (i5 = this.optCur) || this.opts[i5 + 1].backPrev != 0) && (shortRepPrice = getShortRepPrice(i4, this.opts[this.optCur].state, i2)) <= this.opts[this.optCur + 1].price)) {
            Optimum[] optimumArr2 = this.opts;
            int i9 = this.optCur;
            optimumArr2[i9 + 1].set1(shortRepPrice, i9, 0);
            z = true;
        }
        if (!z && i7 != i6 && i3 > 2 && (matchLen = this.f198lz.getMatchLen(1, this.opts[this.optCur].reps[0], Math.min(this.niceLen, i3 - 1))) >= 2) {
            this.nextState.set(this.opts[this.optCur].state);
            this.nextState.updateLiteral();
            int longRepAndLenPrice = price + getLongRepAndLenPrice(0, matchLen, this.nextState, (i + 1) & this.posMask);
            int i10 = this.optCur + 1 + matchLen;
            while (true) {
                int i11 = this.optEnd;
                if (i11 >= i10) {
                    break;
                }
                Optimum[] optimumArr3 = this.opts;
                int i12 = i11 + 1;
                this.optEnd = i12;
                optimumArr3[i12].reset();
            }
            if (longRepAndLenPrice < this.opts[i10].price) {
                this.opts[i10].set2(longRepAndLenPrice, this.optCur, 0);
            }
        }
    }

    private int calcLongRepPrices(int i, int i2, int i3, int i4) {
        int i5;
        int i6 = i2;
        int i7 = i3;
        int min = Math.min(i7, this.niceLen);
        int i8 = 2;
        for (int i9 = 0; i9 < 4; i9++) {
            int matchLen = this.f198lz.getMatchLen(this.opts[this.optCur].reps[i9], min);
            if (matchLen < 2) {
                int i10 = i4;
            } else {
                while (true) {
                    int i11 = this.optEnd;
                    i5 = this.optCur;
                    if (i11 >= i5 + matchLen) {
                        break;
                    }
                    Optimum[] optimumArr = this.opts;
                    int i12 = i11 + 1;
                    this.optEnd = i12;
                    optimumArr[i12].reset();
                }
                int longRepPrice = getLongRepPrice(i4, i9, this.opts[i5].state, i6);
                for (int i13 = matchLen; i13 >= 2; i13--) {
                    int price = this.repLenEncoder.getPrice(i13, i6) + longRepPrice;
                    if (price < this.opts[this.optCur + i13].price) {
                        Optimum[] optimumArr2 = this.opts;
                        int i14 = this.optCur;
                        optimumArr2[i14 + i13].set1(price, i14, i9);
                    }
                }
                if (i9 == 0) {
                    i8 = matchLen + 1;
                }
                int i15 = i8;
                int matchLen2 = this.f198lz.getMatchLen(matchLen + 1, this.opts[this.optCur].reps[i9], Math.min(this.niceLen, (i7 - matchLen) - 1));
                if (matchLen2 >= 2) {
                    int price2 = longRepPrice + this.repLenEncoder.getPrice(matchLen, i6);
                    this.nextState.set(this.opts[this.optCur].state);
                    this.nextState.updateLongRep();
                    int i16 = i + matchLen;
                    int price3 = price2 + this.literalEncoder.getPrice(this.f198lz.getByte(matchLen, 0), this.f198lz.getByte(0), this.f198lz.getByte(matchLen, 1), i16, this.nextState);
                    this.nextState.updateLiteral();
                    int longRepAndLenPrice = price3 + getLongRepAndLenPrice(0, matchLen2, this.nextState, (i16 + 1) & this.posMask);
                    int i17 = this.optCur + matchLen + 1 + matchLen2;
                    while (true) {
                        int i18 = this.optEnd;
                        if (i18 >= i17) {
                            break;
                        }
                        Optimum[] optimumArr3 = this.opts;
                        int i19 = i18 + 1;
                        this.optEnd = i19;
                        optimumArr3[i19].reset();
                    }
                    if (longRepAndLenPrice < this.opts[i17].price) {
                        this.opts[i17].set3(longRepAndLenPrice, this.optCur, i9, matchLen, 0);
                    }
                }
                i8 = i15;
            }
        }
        return i8;
    }

    private void calcNormalMatchPrices(int i, int i2, int i3, int i4, int i5) {
        int i6 = i3;
        int i7 = i5;
        if (this.matches.len[this.matches.count - 1] > i6) {
            this.matches.count = 0;
            while (this.matches.len[this.matches.count] < i6) {
                this.matches.count++;
            }
            int[] iArr = this.matches.len;
            Matches matches2 = this.matches;
            int i8 = matches2.count;
            matches2.count = i8 + 1;
            iArr[i8] = i6;
        }
        if (this.matches.len[this.matches.count - 1] >= i7) {
            while (this.optEnd < this.optCur + this.matches.len[this.matches.count - 1]) {
                Optimum[] optimumArr = this.opts;
                int i9 = this.optEnd + 1;
                this.optEnd = i9;
                optimumArr[i9].reset();
            }
            int normalMatchPrice = getNormalMatchPrice(i4, this.opts[this.optCur].state);
            int i10 = 0;
            while (i7 > this.matches.len[i10]) {
                i10++;
            }
            while (true) {
                int i11 = this.matches.dist[i10];
                int matchAndLenPrice = getMatchAndLenPrice(normalMatchPrice, i11, i7, i2);
                if (matchAndLenPrice < this.opts[this.optCur + i7].price) {
                    Optimum[] optimumArr2 = this.opts;
                    int i12 = this.optCur;
                    optimumArr2[i12 + i7].set1(matchAndLenPrice, i12, i11 + 4);
                }
                if (i7 == this.matches.len[i10]) {
                    int matchLen = this.f198lz.getMatchLen(i7 + 1, i11, Math.min(this.niceLen, (i6 - i7) - 1));
                    if (matchLen >= 2) {
                        this.nextState.set(this.opts[this.optCur].state);
                        this.nextState.updateMatch();
                        int i13 = i + i7;
                        int price = matchAndLenPrice + this.literalEncoder.getPrice(this.f198lz.getByte(i7, 0), this.f198lz.getByte(0), this.f198lz.getByte(i7, 1), i13, this.nextState);
                        this.nextState.updateLiteral();
                        int longRepAndLenPrice = price + getLongRepAndLenPrice(0, matchLen, this.nextState, (i13 + 1) & this.posMask);
                        int i14 = this.optCur + i7 + 1 + matchLen;
                        while (true) {
                            int i15 = this.optEnd;
                            if (i15 >= i14) {
                                break;
                            }
                            Optimum[] optimumArr3 = this.opts;
                            int i16 = i15 + 1;
                            this.optEnd = i16;
                            optimumArr3[i16].reset();
                        }
                        if (longRepAndLenPrice < this.opts[i14].price) {
                            this.opts[i14].set3(longRepAndLenPrice, this.optCur, i11 + 4, i7, 0);
                        }
                    }
                    i10++;
                    if (i10 == this.matches.count) {
                        return;
                    }
                }
                i7++;
            }
        }
    }

    private int convertOpts() {
        int i = this.optCur;
        this.optEnd = i;
        int i2 = this.opts[i].optPrev;
        while (true) {
            Optimum optimum = this.opts[this.optCur];
            if (optimum.prev1IsLiteral) {
                this.opts[i2].optPrev = this.optCur;
                this.opts[i2].backPrev = -1;
                int i3 = i2 - 1;
                this.optCur = i2;
                if (optimum.hasPrev2) {
                    this.opts[i3].optPrev = i3 + 1;
                    this.opts[i3].backPrev = optimum.backPrev2;
                    this.optCur = i3;
                    i2 = optimum.optPrev2;
                } else {
                    i2 = i3;
                }
            }
            int i4 = this.opts[i2].optPrev;
            this.opts[i2].optPrev = this.optCur;
            this.optCur = i2;
            if (i2 <= 0) {
                int i5 = this.opts[0].optPrev;
                this.optCur = i5;
                this.back = this.opts[i5].backPrev;
                return this.optCur;
            }
            i2 = i4;
        }
    }

    static int getMemoryUsage(int i, int i2, int i3) {
        return LZEncoder.getMemoryUsage(i, Math.max(i2, 4096), 4096, LZMA2Options.NICE_LEN_MAX, i3) + 256;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0168  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateOptStateAndReps() {
        /*
            r7 = this;
            org.tukaani.xz.lzma.Optimum[] r0 = r7.opts
            int r1 = r7.optCur
            r0 = r0[r1]
            int r0 = r0.optPrev
            int r1 = r7.optCur
            if (r0 >= r1) goto L_0x0182
            org.tukaani.xz.lzma.Optimum[] r2 = r7.opts
            r1 = r2[r1]
            boolean r1 = r1.prev1IsLiteral
            r2 = 4
            if (r1 == 0) goto L_0x0077
            int r0 = r0 + -1
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            boolean r1 = r1.hasPrev2
            if (r1 == 0) goto L_0x005a
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            org.tukaani.xz.lzma.Optimum[] r3 = r7.opts
            int r4 = r7.optCur
            r4 = r3[r4]
            int r4 = r4.optPrev2
            r3 = r3[r4]
            org.tukaani.xz.lzma.State r3 = r3.state
            r1.set(r3)
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            int r1 = r1.backPrev2
            if (r1 >= r2) goto L_0x004e
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            r1.updateLongRep()
            goto L_0x006b
        L_0x004e:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            r1.updateMatch()
            goto L_0x006b
        L_0x005a:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            org.tukaani.xz.lzma.Optimum[] r3 = r7.opts
            r3 = r3[r0]
            org.tukaani.xz.lzma.State r3 = r3.state
            r1.set(r3)
        L_0x006b:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            r1.updateLiteral()
            goto L_0x0088
        L_0x0077:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            org.tukaani.xz.lzma.Optimum[] r3 = r7.opts
            r3 = r3[r0]
            org.tukaani.xz.lzma.State r3 = r3.state
            r1.set(r3)
        L_0x0088:
            int r1 = r7.optCur
            int r3 = r1 + -1
            r4 = 0
            if (r0 != r3) goto L_0x00dd
            org.tukaani.xz.lzma.Optimum[] r3 = r7.opts
            r1 = r3[r1]
            int r1 = r1.backPrev
            if (r1 == 0) goto L_0x00a9
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            int r1 = r1.backPrev
            r3 = -1
            if (r1 != r3) goto L_0x00a3
            goto L_0x00a9
        L_0x00a3:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x00a9:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            int r1 = r1.backPrev
            if (r1 != 0) goto L_0x00bf
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            r1.updateShortRep()
            goto L_0x00ca
        L_0x00bf:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            org.tukaani.xz.lzma.State r1 = r1.state
            r1.updateLiteral()
        L_0x00ca:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            r0 = r1[r0]
            int[] r0 = r0.reps
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            int[] r1 = r1.reps
            java.lang.System.arraycopy(r0, r4, r1, r4, r2)
            goto L_0x0181
        L_0x00dd:
            org.tukaani.xz.lzma.Optimum[] r3 = r7.opts
            r1 = r3[r1]
            boolean r1 = r1.prev1IsLiteral
            if (r1 == 0) goto L_0x010b
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            boolean r1 = r1.hasPrev2
            if (r1 == 0) goto L_0x010b
            org.tukaani.xz.lzma.Optimum[] r0 = r7.opts
            int r1 = r7.optCur
            r0 = r0[r1]
            int r0 = r0.optPrev2
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            int r1 = r1.backPrev2
            org.tukaani.xz.lzma.Optimum[] r3 = r7.opts
            int r5 = r7.optCur
            r3 = r3[r5]
        L_0x0105:
            org.tukaani.xz.lzma.State r3 = r3.state
            r3.updateLongRep()
            goto L_0x0123
        L_0x010b:
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r3 = r7.optCur
            r1 = r1[r3]
            int r1 = r1.backPrev
            org.tukaani.xz.lzma.Optimum[] r3 = r7.opts
            int r5 = r7.optCur
            if (r1 >= r2) goto L_0x011c
            r3 = r3[r5]
            goto L_0x0105
        L_0x011c:
            r3 = r3[r5]
            org.tukaani.xz.lzma.State r3 = r3.state
            r3.updateMatch()
        L_0x0123:
            r3 = 1
            org.tukaani.xz.lzma.Optimum[] r5 = r7.opts
            int r6 = r7.optCur
            if (r1 >= r2) goto L_0x0168
            r5 = r5[r6]
            int[] r5 = r5.reps
            org.tukaani.xz.lzma.Optimum[] r6 = r7.opts
            r6 = r6[r0]
            int[] r6 = r6.reps
            r6 = r6[r1]
            r5[r4] = r6
        L_0x0138:
            if (r3 > r1) goto L_0x0151
            org.tukaani.xz.lzma.Optimum[] r4 = r7.opts
            int r5 = r7.optCur
            r4 = r4[r5]
            int[] r4 = r4.reps
            org.tukaani.xz.lzma.Optimum[] r5 = r7.opts
            r5 = r5[r0]
            int[] r5 = r5.reps
            int r6 = r3 + -1
            r5 = r5[r6]
            r4[r3] = r5
            int r3 = r3 + 1
            goto L_0x0138
        L_0x0151:
            if (r3 >= r2) goto L_0x0181
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r4 = r7.optCur
            r1 = r1[r4]
            int[] r1 = r1.reps
            org.tukaani.xz.lzma.Optimum[] r4 = r7.opts
            r4 = r4[r0]
            int[] r4 = r4.reps
            r4 = r4[r3]
            r1[r3] = r4
            int r3 = r3 + 1
            goto L_0x0151
        L_0x0168:
            r5 = r5[r6]
            int[] r5 = r5.reps
            int r1 = r1 - r2
            r5[r4] = r1
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            r0 = r1[r0]
            int[] r0 = r0.reps
            org.tukaani.xz.lzma.Optimum[] r1 = r7.opts
            int r2 = r7.optCur
            r1 = r1[r2]
            int[] r1 = r1.reps
            r2 = 3
            java.lang.System.arraycopy(r0, r4, r1, r3, r2)
        L_0x0181:
            return
        L_0x0182:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.lzma.LZMAEncoderNormal.updateOptStateAndReps():void");
    }

    /* access modifiers changed from: package-private */
    public int getNextSymbol() {
        int i;
        int shortRepPrice;
        int i2 = this.optCur;
        int i3 = this.optEnd;
        if (i2 < i3) {
            int i4 = this.opts[i2].optPrev;
            int i5 = this.optCur;
            int i6 = i4 - i5;
            int i7 = this.opts[i5].optPrev;
            this.optCur = i7;
            this.back = this.opts[i7].backPrev;
            return i6;
        } else if (i2 == i3) {
            this.optCur = 0;
            this.optEnd = 0;
            this.back = -1;
            if (this.readAhead == -1) {
                this.matches = getMatches();
            }
            int min = Math.min(this.f198lz.getAvail(), LZMA2Options.NICE_LEN_MAX);
            if (min < 2) {
                return 1;
            }
            int i8 = 0;
            for (int i9 = 0; i9 < 4; i9++) {
                this.repLens[i9] = this.f198lz.getMatchLen(this.reps[i9], min);
                int[] iArr = this.repLens;
                if (iArr[i9] < 2) {
                    iArr[i9] = 0;
                } else if (iArr[i9] > iArr[i8]) {
                    i8 = i9;
                }
            }
            if (this.repLens[i8] >= this.niceLen) {
                this.back = i8;
                skip(this.repLens[i8] - 1);
                return this.repLens[i8];
            }
            if (this.matches.count > 0) {
                i = this.matches.len[this.matches.count - 1];
                int i10 = this.matches.dist[this.matches.count - 1];
                if (i >= this.niceLen) {
                    this.back = i10 + 4;
                    skip(i - 1);
                    return i;
                }
            } else {
                i = 0;
            }
            int i11 = this.f198lz.getByte(0);
            int i12 = this.f198lz.getByte(this.reps[0] + 1);
            if (i < 2 && i11 != i12 && this.repLens[i8] < 2) {
                return 1;
            }
            int pos = this.f198lz.getPos();
            int i13 = pos & this.posMask;
            int i14 = i13;
            this.opts[1].set1(this.literalEncoder.getPrice(i11, i12, this.f198lz.getByte(1), pos, this.state), 0, -1);
            int anyMatchPrice = getAnyMatchPrice(this.state, i14);
            int anyRepPrice = getAnyRepPrice(anyMatchPrice, this.state);
            if (i12 == i11 && (shortRepPrice = getShortRepPrice(anyRepPrice, this.state, i14)) < this.opts[1].price) {
                this.opts[1].set1(shortRepPrice, 0, 0);
            }
            int max = Math.max(i, this.repLens[i8]);
            this.optEnd = max;
            if (max >= 2) {
                updatePrices();
                this.opts[0].state.set(this.state);
                System.arraycopy(this.reps, 0, this.opts[0].reps, 0, 4);
                for (int i15 = this.optEnd; i15 >= 2; i15--) {
                    this.opts[i15].reset();
                }
                for (int i16 = 0; i16 < 4; i16++) {
                    int i17 = this.repLens[i16];
                    if (i17 >= 2) {
                        int longRepPrice = getLongRepPrice(anyRepPrice, i16, this.state, i14);
                        do {
                            int price = this.repLenEncoder.getPrice(i17, i14) + longRepPrice;
                            if (price < this.opts[i17].price) {
                                this.opts[i17].set1(price, 0, i16);
                            }
                            i17--;
                        } while (i17 >= 2);
                    }
                }
                int max2 = Math.max(this.repLens[0] + 1, 2);
                if (max2 <= i) {
                    int normalMatchPrice = getNormalMatchPrice(anyMatchPrice, this.state);
                    int i18 = 0;
                    while (max2 > this.matches.len[i18]) {
                        i18++;
                    }
                    while (true) {
                        int i19 = this.matches.dist[i18];
                        int matchAndLenPrice = getMatchAndLenPrice(normalMatchPrice, i19, max2, i14);
                        if (matchAndLenPrice < this.opts[max2].price) {
                            this.opts[max2].set1(matchAndLenPrice, 0, i19 + 4);
                        }
                        if (max2 == this.matches.len[i18] && (i18 = i18 + 1) == this.matches.count) {
                            break;
                        }
                        max2++;
                    }
                }
                int min2 = Math.min(this.f198lz.getAvail(), UnixStat.PERM_MASK);
                while (true) {
                    int i20 = this.optCur + 1;
                    this.optCur = i20;
                    if (i20 >= this.optEnd) {
                        break;
                    }
                    Matches matches2 = getMatches();
                    this.matches = matches2;
                    if (matches2.count > 0 && this.matches.len[this.matches.count - 1] >= this.niceLen) {
                        break;
                    }
                    int i21 = min2 - 1;
                    int i22 = pos + 1;
                    int i23 = i22 & this.posMask;
                    updateOptStateAndReps();
                    int anyMatchPrice2 = this.opts[this.optCur].price + getAnyMatchPrice(this.opts[this.optCur].state, i23);
                    int anyRepPrice2 = getAnyRepPrice(anyMatchPrice2, this.opts[this.optCur].state);
                    calc1BytePrices(i22, i23, i21, anyRepPrice2);
                    if (i21 >= 2) {
                        int calcLongRepPrices = calcLongRepPrices(i22, i23, i21, anyRepPrice2);
                        if (this.matches.count > 0) {
                            calcNormalMatchPrices(i22, i23, i21, anyMatchPrice2, calcLongRepPrices);
                        }
                    }
                    min2 = i21;
                    pos = i22;
                }
                return convertOpts();
            } else if (max == 0) {
                this.back = this.opts[1].backPrev;
                return 1;
            } else {
                throw new AssertionError(this.optEnd);
            }
        } else {
            throw new AssertionError();
        }
    }

    public void reset() {
        this.optCur = 0;
        this.optEnd = 0;
        super.reset();
    }
}
