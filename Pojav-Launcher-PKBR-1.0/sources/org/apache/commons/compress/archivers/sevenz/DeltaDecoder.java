package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.UByte;
import org.tukaani.p013xz.DeltaOptions;
import org.tukaani.p013xz.FinishableWrapperOutputStream;
import org.tukaani.p013xz.UnsupportedOptionsException;

class DeltaDecoder extends CoderBase {
    DeltaDecoder() {
        super(Number.class);
    }

    private int getOptionsFromCoder(Coder coder) {
        if (coder.properties == null || coder.properties.length == 0) {
            return 1;
        }
        return (coder.properties[0] & UByte.MAX_VALUE) + 1;
    }

    /* access modifiers changed from: package-private */
    public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
        return new DeltaOptions(getOptionsFromCoder(coder)).getInputStream(inputStream);
    }

    /* access modifiers changed from: package-private */
    public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
        try {
            return new DeltaOptions(numberOptionOrDefault(obj, 1)).getOutputStream(new FinishableWrapperOutputStream(outputStream));
        } catch (UnsupportedOptionsException e) {
            throw new IOException(e.getMessage());
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] getOptionsAsProperties(Object obj) {
        return new byte[]{(byte) (numberOptionOrDefault(obj, 1) - 1)};
    }

    /* access modifiers changed from: package-private */
    public Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return Integer.valueOf(getOptionsFromCoder(coder));
    }
}
