package org.tukaani.p013xz.lzma;

import java.io.IOException;
import org.tukaani.p013xz.lzma.LZMACoder;
import org.tukaani.p013xz.p014lz.LZDecoder;
import org.tukaani.p013xz.rangecoder.RangeDecoder;

/* renamed from: org.tukaani.xz.lzma.LZMADecoder */
public final class LZMADecoder extends LZMACoder {
    private final LiteralDecoder literalDecoder;
    /* access modifiers changed from: private */

    /* renamed from: lz */
    public final LZDecoder f196lz;
    private final LengthDecoder matchLenDecoder = new LengthDecoder();
    /* access modifiers changed from: private */

    /* renamed from: rc */
    public final RangeDecoder f197rc;
    private final LengthDecoder repLenDecoder = new LengthDecoder();

    /* renamed from: org.tukaani.xz.lzma.LZMADecoder$LengthDecoder */
    private class LengthDecoder extends LZMACoder.LengthCoder {
        private LengthDecoder() {
            super(LZMADecoder.this);
        }

        /* access modifiers changed from: package-private */
        public int decode(int i) throws IOException {
            return LZMADecoder.this.f197rc.decodeBit(this.choice, 0) == 0 ? LZMADecoder.this.f197rc.decodeBitTree(this.low[i]) + 2 : LZMADecoder.this.f197rc.decodeBit(this.choice, 1) == 0 ? LZMADecoder.this.f197rc.decodeBitTree(this.mid[i]) + 2 + 8 : LZMADecoder.this.f197rc.decodeBitTree(this.high) + 2 + 8 + 8;
        }
    }

    /* renamed from: org.tukaani.xz.lzma.LZMADecoder$LiteralDecoder */
    private class LiteralDecoder extends LZMACoder.LiteralCoder {
        private final LiteralSubdecoder[] subdecoders;

        /* renamed from: org.tukaani.xz.lzma.LZMADecoder$LiteralDecoder$LiteralSubdecoder */
        private class LiteralSubdecoder extends LZMACoder.LiteralCoder.LiteralSubcoder {
            private LiteralSubdecoder() {
                super();
            }

            /* access modifiers changed from: package-private */
            public void decode() throws IOException {
                int i = 1;
                if (LZMADecoder.this.state.isLiteral()) {
                    do {
                        i = LZMADecoder.this.f197rc.decodeBit(this.probs, i) | (i << 1);
                    } while (i < 256);
                } else {
                    int i2 = LZMADecoder.this.f196lz.getByte(LZMADecoder.this.reps[0]);
                    int i3 = 256;
                    int i4 = 1;
                    do {
                        i2 <<= 1;
                        int i5 = i2 & i3;
                        int decodeBit = LZMADecoder.this.f197rc.decodeBit(this.probs, i3 + i5 + i4);
                        i4 = (i4 << 1) | decodeBit;
                        i3 &= (~i5) ^ (0 - decodeBit);
                    } while (i4 < 256);
                    i = i4;
                }
                LZMADecoder.this.f196lz.putByte((byte) i);
                LZMADecoder.this.state.updateLiteral();
            }
        }

        LiteralDecoder(int i, int i2) {
            super(i, i2);
            this.subdecoders = new LiteralSubdecoder[(1 << (i + i2))];
            int i3 = 0;
            while (true) {
                LiteralSubdecoder[] literalSubdecoderArr = this.subdecoders;
                if (i3 < literalSubdecoderArr.length) {
                    literalSubdecoderArr[i3] = new LiteralSubdecoder();
                    i3++;
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void decode() throws IOException {
            this.subdecoders[getSubcoderIndex(LZMADecoder.this.f196lz.getByte(0), LZMADecoder.this.f196lz.getPos())].decode();
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            int i = 0;
            while (true) {
                LiteralSubdecoder[] literalSubdecoderArr = this.subdecoders;
                if (i < literalSubdecoderArr.length) {
                    literalSubdecoderArr[i].reset();
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public LZMADecoder(LZDecoder lZDecoder, RangeDecoder rangeDecoder, int i, int i2, int i3) {
        super(i3);
        this.f196lz = lZDecoder;
        this.f197rc = rangeDecoder;
        this.literalDecoder = new LiteralDecoder(i, i2);
        reset();
    }

    private int decodeMatch(int i) throws IOException {
        this.state.updateMatch();
        this.reps[3] = this.reps[2];
        this.reps[2] = this.reps[1];
        this.reps[1] = this.reps[0];
        int decode = this.matchLenDecoder.decode(i);
        int decodeBitTree = this.f197rc.decodeBitTree(this.distSlots[getDistState(decode)]);
        if (decodeBitTree < 4) {
            this.reps[0] = decodeBitTree;
        } else {
            int i2 = (decodeBitTree >> 1) - 1;
            this.reps[0] = (2 | (decodeBitTree & 1)) << i2;
            if (decodeBitTree < 14) {
                int[] iArr = this.reps;
                iArr[0] = this.f197rc.decodeReverseBitTree(this.distSpecial[decodeBitTree - 4]) | iArr[0];
            } else {
                int[] iArr2 = this.reps;
                iArr2[0] = (this.f197rc.decodeDirectBits(i2 - 4) << 4) | iArr2[0];
                int[] iArr3 = this.reps;
                iArr3[0] = iArr3[0] | this.f197rc.decodeReverseBitTree(this.distAlign);
            }
        }
        return decode;
    }

    private int decodeRepMatch(int i) throws IOException {
        int i2;
        if (this.f197rc.decodeBit(this.isRep0, this.state.get()) != 0) {
            if (this.f197rc.decodeBit(this.isRep1, this.state.get()) == 0) {
                i2 = this.reps[1];
            } else {
                if (this.f197rc.decodeBit(this.isRep2, this.state.get()) == 0) {
                    i2 = this.reps[2];
                } else {
                    i2 = this.reps[3];
                    this.reps[3] = this.reps[2];
                }
                this.reps[2] = this.reps[1];
            }
            this.reps[1] = this.reps[0];
            this.reps[0] = i2;
        } else if (this.f197rc.decodeBit(this.isRep0Long[this.state.get()], i) == 0) {
            this.state.updateShortRep();
            return 1;
        }
        this.state.updateLongRep();
        return this.repLenDecoder.decode(i);
    }

    public void decode() throws IOException {
        this.f196lz.repeatPending();
        while (this.f196lz.hasSpace()) {
            int pos = this.f196lz.getPos() & this.posMask;
            if (this.f197rc.decodeBit(this.isMatch[this.state.get()], pos) == 0) {
                this.literalDecoder.decode();
            } else {
                this.f196lz.repeat(this.reps[0], this.f197rc.decodeBit(this.isRep, this.state.get()) == 0 ? decodeMatch(pos) : decodeRepMatch(pos));
            }
        }
        this.f197rc.normalize();
    }

    public boolean endMarkerDetected() {
        return this.reps[0] == -1;
    }

    public void reset() {
        super.reset();
        this.literalDecoder.reset();
        this.matchLenDecoder.reset();
        this.repLenDecoder.reset();
    }
}
