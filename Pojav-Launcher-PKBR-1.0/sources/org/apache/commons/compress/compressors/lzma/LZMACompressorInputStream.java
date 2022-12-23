package org.apache.commons.compress.compressors.lzma;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.tukaani.p013xz.LZMAInputStream;

public class LZMACompressorInputStream extends CompressorInputStream {

    /* renamed from: in */
    private final InputStream f160in;

    public LZMACompressorInputStream(InputStream inputStream) throws IOException {
        this.f160in = new LZMAInputStream(inputStream);
    }

    public static boolean matches(byte[] bArr, int i) {
        return bArr != null && i >= 3 && bArr[0] == 93 && bArr[1] == 0 && bArr[2] == 0;
    }

    public int available() throws IOException {
        return this.f160in.available();
    }

    public void close() throws IOException {
        this.f160in.close();
    }

    public int read() throws IOException {
        int read = this.f160in.read();
        count(read == -1 ? 0 : 1);
        return read;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.f160in.read(bArr, i, i2);
        count(read);
        return read;
    }

    public long skip(long j) throws IOException {
        return this.f160in.skip(j);
    }
}
