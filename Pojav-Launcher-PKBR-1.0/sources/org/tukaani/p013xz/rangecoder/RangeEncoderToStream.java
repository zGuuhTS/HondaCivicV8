package org.tukaani.p013xz.rangecoder;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.tukaani.xz.rangecoder.RangeEncoderToStream */
public final class RangeEncoderToStream extends RangeEncoder {
    private final OutputStream out;

    public RangeEncoderToStream(OutputStream outputStream) {
        this.out = outputStream;
        reset();
    }

    /* access modifiers changed from: package-private */
    public void writeByte(int i) throws IOException {
        this.out.write(i);
    }
}
