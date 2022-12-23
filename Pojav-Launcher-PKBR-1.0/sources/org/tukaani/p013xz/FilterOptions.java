package org.tukaani.p013xz;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.tukaani.xz.FilterOptions */
public abstract class FilterOptions implements Cloneable {
    FilterOptions() {
    }

    public static int getDecoderMemoryUsage(FilterOptions[] filterOptionsArr) {
        int i = 0;
        for (FilterOptions decoderMemoryUsage : filterOptionsArr) {
            i += decoderMemoryUsage.getDecoderMemoryUsage();
        }
        return i;
    }

    public static int getEncoderMemoryUsage(FilterOptions[] filterOptionsArr) {
        int i = 0;
        for (FilterOptions encoderMemoryUsage : filterOptionsArr) {
            i += encoderMemoryUsage.getEncoderMemoryUsage();
        }
        return i;
    }

    public abstract int getDecoderMemoryUsage();

    public abstract int getEncoderMemoryUsage();

    /* access modifiers changed from: package-private */
    public abstract FilterEncoder getFilterEncoder();

    public InputStream getInputStream(InputStream inputStream) throws IOException {
        return getInputStream(inputStream, ArrayCache.getDefaultCache());
    }

    public abstract InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) throws IOException;

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream) {
        return getOutputStream(finishableOutputStream, ArrayCache.getDefaultCache());
    }

    public abstract FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream, ArrayCache arrayCache);
}
