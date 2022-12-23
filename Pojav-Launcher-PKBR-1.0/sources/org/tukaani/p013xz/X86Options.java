package org.tukaani.p013xz;

import java.io.InputStream;
import org.tukaani.p013xz.simple.X86;

/* renamed from: org.tukaani.xz.X86Options */
public class X86Options extends BCJOptions {
    private static final int ALIGNMENT = 1;

    public X86Options() {
        super(1);
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
        return new BCJEncoder(this, 4);
    }

    public InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) {
        return new SimpleInputStream(inputStream, new X86(false, this.startOffset));
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream, ArrayCache arrayCache) {
        return new SimpleOutputStream(finishableOutputStream, new X86(true, this.startOffset));
    }

    public /* bridge */ /* synthetic */ int getStartOffset() {
        return super.getStartOffset();
    }

    public /* bridge */ /* synthetic */ void setStartOffset(int i) throws UnsupportedOptionsException {
        super.setStartOffset(i);
    }
}
