package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.BCJOptions */
abstract class BCJOptions extends FilterOptions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int alignment;
    int startOffset = 0;

    BCJOptions(int i) {
        this.alignment = i;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public int getDecoderMemoryUsage() {
        return SimpleInputStream.getMemoryUsage();
    }

    public int getEncoderMemoryUsage() {
        return SimpleOutputStream.getMemoryUsage();
    }

    public int getStartOffset() {
        return this.startOffset;
    }

    public void setStartOffset(int i) throws UnsupportedOptionsException {
        if (((this.alignment - 1) & i) == 0) {
            this.startOffset = i;
            return;
        }
        throw new UnsupportedOptionsException("Start offset must be a multiple of " + this.alignment);
    }
}
