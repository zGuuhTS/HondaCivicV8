package org.tukaani.p013xz.lzma;

import java.lang.reflect.Array;
import org.tukaani.p013xz.rangecoder.RangeCoder;

/* renamed from: org.tukaani.xz.lzma.LZMACoder */
abstract class LZMACoder {
    static final int ALIGN_BITS = 4;
    static final int ALIGN_MASK = 15;
    static final int ALIGN_SIZE = 16;
    static final int DIST_MODEL_END = 14;
    static final int DIST_MODEL_START = 4;
    static final int DIST_SLOTS = 64;
    static final int DIST_STATES = 4;
    static final int FULL_DISTANCES = 128;
    static final int MATCH_LEN_MAX = 273;
    static final int MATCH_LEN_MIN = 2;
    static final int POS_STATES_MAX = 16;
    static final int REPS = 4;
    final short[] distAlign;
    final short[][] distSlots;
    final short[][] distSpecial;
    final short[][] isMatch;
    final short[] isRep;
    final short[] isRep0;
    final short[][] isRep0Long;
    final short[] isRep1;
    final short[] isRep2;
    final int posMask;
    final int[] reps = new int[4];
    final State state = new State();

    /* renamed from: org.tukaani.xz.lzma.LZMACoder$LengthCoder */
    abstract class LengthCoder {
        static final int HIGH_SYMBOLS = 256;
        static final int LOW_SYMBOLS = 8;
        static final int MID_SYMBOLS = 8;
        final short[] choice = new short[2];
        final short[] high;
        final short[][] low;
        final short[][] mid;
        final /* synthetic */ LZMACoder this$0;

        LengthCoder(LZMACoder lZMACoder) {
            Class<short> cls = short.class;
            this.this$0 = lZMACoder;
            this.low = (short[][]) Array.newInstance(cls, new int[]{16, 8});
            this.mid = (short[][]) Array.newInstance(cls, new int[]{16, 8});
            this.high = new short[256];
        }

        /* access modifiers changed from: package-private */
        public void reset() {
            RangeCoder.initProbs(this.choice);
            int i = 0;
            while (true) {
                short[][] sArr = this.low;
                if (i >= sArr.length) {
                    break;
                }
                RangeCoder.initProbs(sArr[i]);
                i++;
            }
            for (int i2 = 0; i2 < this.low.length; i2++) {
                RangeCoder.initProbs(this.mid[i2]);
            }
            RangeCoder.initProbs(this.high);
        }
    }

    /* renamed from: org.tukaani.xz.lzma.LZMACoder$LiteralCoder */
    abstract class LiteralCoder {

        /* renamed from: lc */
        private final int f195lc;
        private final int literalPosMask;

        /* renamed from: org.tukaani.xz.lzma.LZMACoder$LiteralCoder$LiteralSubcoder */
        abstract class LiteralSubcoder {
            final short[] probs = new short[768];

            LiteralSubcoder() {
            }

            /* access modifiers changed from: package-private */
            public void reset() {
                RangeCoder.initProbs(this.probs);
            }
        }

        LiteralCoder(int i, int i2) {
            this.f195lc = i;
            this.literalPosMask = (1 << i2) - 1;
        }

        /* access modifiers changed from: package-private */
        public final int getSubcoderIndex(int i, int i2) {
            int i3 = this.f195lc;
            return (i >> (8 - i3)) + ((i2 & this.literalPosMask) << i3);
        }
    }

    LZMACoder(int i) {
        Class<short> cls = short.class;
        this.isMatch = (short[][]) Array.newInstance(cls, new int[]{12, 16});
        this.isRep = new short[12];
        this.isRep0 = new short[12];
        this.isRep1 = new short[12];
        this.isRep2 = new short[12];
        this.isRep0Long = (short[][]) Array.newInstance(cls, new int[]{12, 16});
        this.distSlots = (short[][]) Array.newInstance(cls, new int[]{4, 64});
        this.distSpecial = new short[][]{new short[2], new short[2], new short[4], new short[4], new short[8], new short[8], new short[16], new short[16], new short[32], new short[32]};
        this.distAlign = new short[16];
        this.posMask = (1 << i) - 1;
    }

    static final int getDistState(int i) {
        if (i < 6) {
            return i - 2;
        }
        return 3;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        int[] iArr = this.reps;
        int i = 0;
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
        this.state.reset();
        int i2 = 0;
        while (true) {
            short[][] sArr = this.isMatch;
            if (i2 >= sArr.length) {
                break;
            }
            RangeCoder.initProbs(sArr[i2]);
            i2++;
        }
        RangeCoder.initProbs(this.isRep);
        RangeCoder.initProbs(this.isRep0);
        RangeCoder.initProbs(this.isRep1);
        RangeCoder.initProbs(this.isRep2);
        int i3 = 0;
        while (true) {
            short[][] sArr2 = this.isRep0Long;
            if (i3 >= sArr2.length) {
                break;
            }
            RangeCoder.initProbs(sArr2[i3]);
            i3++;
        }
        int i4 = 0;
        while (true) {
            short[][] sArr3 = this.distSlots;
            if (i4 >= sArr3.length) {
                break;
            }
            RangeCoder.initProbs(sArr3[i4]);
            i4++;
        }
        while (true) {
            short[][] sArr4 = this.distSpecial;
            if (i < sArr4.length) {
                RangeCoder.initProbs(sArr4[i]);
                i++;
            } else {
                RangeCoder.initProbs(this.distAlign);
                return;
            }
        }
    }
}
