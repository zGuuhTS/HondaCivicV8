package org.apache.commons.compress.compressors.p010xz;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.tukaani.p013xz.C0927XZ;
import org.tukaani.p013xz.SingleXZInputStream;
import org.tukaani.p013xz.XZInputStream;

/* renamed from: org.apache.commons.compress.compressors.xz.XZCompressorInputStream */
public class XZCompressorInputStream extends CompressorInputStream {

    /* renamed from: in */
    private final InputStream f166in;

    public XZCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public XZCompressorInputStream(InputStream inputStream, boolean z) throws IOException {
        this.f166in = z ? new XZInputStream(inputStream) : new SingleXZInputStream(inputStream);
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < C0927XZ.HEADER_MAGIC.length) {
            return false;
        }
        for (int i2 = 0; i2 < C0927XZ.HEADER_MAGIC.length; i2++) {
            if (bArr[i2] != C0927XZ.HEADER_MAGIC[i2]) {
                return false;
            }
        }
        return true;
    }

    public int available() throws IOException {
        return this.f166in.available();
    }

    public void close() throws IOException {
        this.f166in.close();
    }

    public int read() throws IOException {
        int read = this.f166in.read();
        int i = -1;
        if (read != -1) {
            i = 1;
        }
        count(i);
        return read;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.f166in.read(bArr, i, i2);
        count(read);
        return read;
    }

    public long skip(long j) throws IOException {
        return this.f166in.skip(j);
    }
}
