package org.tukaani.p013xz;

import java.io.InputStream;

/* renamed from: org.tukaani.xz.DeltaOptions */
public class DeltaOptions extends FilterOptions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DISTANCE_MAX = 256;
    public static final int DISTANCE_MIN = 1;
    private int distance = 1;

    public DeltaOptions() {
    }

    public DeltaOptions(int i) throws UnsupportedOptionsException {
        setDistance(i);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public int getDecoderMemoryUsage() {
        return 1;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getEncoderMemoryUsage() {
        return DeltaOutputStream.getMemoryUsage();
    }

    /* access modifiers changed from: package-private */
    public FilterEncoder getFilterEncoder() {
        return new DeltaEncoder(this);
    }

    public InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) {
        return new DeltaInputStream(inputStream, this.distance);
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream, ArrayCache arrayCache) {
        return new DeltaOutputStream(finishableOutputStream, this);
    }

    public void setDistance(int i) throws UnsupportedOptionsException {
        if (i < 1 || i > 256) {
            throw new UnsupportedOptionsException("Delta distance must be in the range [1, 256]: " + i);
        }
        this.distance = i;
    }
}
