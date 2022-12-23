package org.tukaani.p013xz;

import java.io.InputStream;
import org.tukaani.p013xz.simple.IA64;

/* renamed from: org.tukaani.xz.IA64Options */
public class IA64Options extends BCJOptions {
    private static final int ALIGNMENT = 16;

    public IA64Options() {
        super(16);
    }

    public /* bridge */ /* synthetic */ Object clone() {
        return super.clone();
    }

    public /* bridge */ /* synthetic */ int getDecoderMemoryUsage() {
        return super.getDecoderMemoryUsage();
    }

    public /* bridge */ /* synthetic */ int getEncoderMemoryUsage() {
        return super.getEncoderMemoryUsage();
    }

    /* access modifiers changed from: package-private */
    public FilterEncoder getFilterEncoder() {
        return new BCJEncoder(this, 6);
    }

    public InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) {
        return new SimpleInputStream(inputStream, new IA64(false, this.startOffset));
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream, ArrayCache arrayCache) {
        return new SimpleOutputStream(finishableOutputStream, new IA64(true, this.startOffset));
    }

    public /* bridge */ /* synthetic */ int getStartOffset() {
        return super.getStartOffset();
    }

    public /* bridge */ /* synthetic */ void setStartOffset(int i) throws UnsupportedOptionsException {
        super.setStartOffset(i);
    }
}
