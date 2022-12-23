package org.tukaani.p013xz;

import top.defaults.checkerboarddrawable.BuildConfig;

/* renamed from: org.tukaani.xz.MemoryLimitException */
public class MemoryLimitException extends XZIOException {
    private static final long serialVersionUID = 3;
    private final int memoryLimit;
    private final int memoryNeeded;

    public MemoryLimitException(int i, int i2) {
        super(BuildConfig.FLAVOR + i + " KiB of memory would be needed; limit was " + i2 + " KiB");
        this.memoryNeeded = i;
        this.memoryLimit = i2;
    }

    public int getMemoryLimit() {
        return this.memoryLimit;
    }

    public int getMemoryNeeded() {
        return this.memoryNeeded;
    }
}
