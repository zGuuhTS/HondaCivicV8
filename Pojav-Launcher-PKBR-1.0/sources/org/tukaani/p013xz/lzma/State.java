package org.tukaani.p013xz.lzma;

/* renamed from: org.tukaani.xz.lzma.State */
final class State {
    private static final int LIT_LIT = 0;
    private static final int LIT_LONGREP = 8;
    private static final int LIT_MATCH = 7;
    private static final int LIT_SHORTREP = 9;
    private static final int LIT_STATES = 7;
    private static final int MATCH_LIT = 4;
    private static final int MATCH_LIT_LIT = 1;
    private static final int NONLIT_MATCH = 10;
    private static final int NONLIT_REP = 11;
    private static final int REP_LIT = 5;
    private static final int REP_LIT_LIT = 2;
    private static final int SHORTREP_LIT = 6;
    private static final int SHORTREP_LIT_LIT = 3;
    static final int STATES = 12;
    private int state;

    State() {
    }

    State(State state2) {
        this.state = state2.state;
    }

    /* access modifiers changed from: package-private */
    public int get() {
        return this.state;
    }

    /* access modifiers changed from: package-private */
    public boolean isLiteral() {
        return this.state < 7;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.state = 0;
    }

    /* access modifiers changed from: package-private */
    public void set(State state2) {
        this.state = state2.state;
    }

    /* access modifiers changed from: package-private */
    public void updateLiteral() {
        int i = this.state;
        this.state = i <= 3 ? 0 : i <= 9 ? i - 3 : i - 6;
    }

    /* access modifiers changed from: package-private */
    public void updateLongRep() {
        this.state = this.state < 7 ? 8 : 11;
    }

    /* access modifiers changed from: package-private */
    public void updateMatch() {
        int i = 7;
        if (this.state >= 7) {
            i = 10;
        }
        this.state = i;
    }

    /* access modifiers changed from: package-private */
    public void updateShortRep() {
        this.state = this.state < 7 ? 9 : 11;
    }
}
