package org.apache.commons.compress.compressors.deflate;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;

public class DeflateCompressorInputStream extends CompressorInputStream {
    private static final int MAGIC_1 = 120;
    private static final int MAGIC_2a = 1;
    private static final int MAGIC_2b = 94;
    private static final int MAGIC_2c = 156;
    private static final int MAGIC_2d = 218;

    /* renamed from: in */
    private final InputStream f158in;
    private final Inflater inflater;

    public DeflateCompressorInputStream(InputStream inputStream) {
        this(inputStream, new DeflateParameters());
    }

    public DeflateCompressorInputStream(InputStream inputStream, DeflateParameters deflateParameters) {
        Inflater inflater2 = new Inflater(!deflateParameters.withZlibHeader());
        this.inflater = inflater2;
        this.f158in = new InflaterInputStream(inputStream, inflater2);
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i <= 3 || bArr[0] != 120) {
            return false;
        }
        return bArr[1] == 1 || bArr[1] == 94 || bArr[1] == -100 || bArr[1] == -38;
    }

    public int available() throws IOException {
        return this.f158in.available();
    }

    public void close() throws IOException {
        try {
            this.f158in.close();
        } finally {
            this.inflater.end();
        }
    }

    public int read() throws IOException {
        int read = this.f158in.read();
        count(read == -1 ? 0 : 1);
        return read;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.f158in.read(bArr, i, i2);
        count(read);
        return read;
    }

    public long skip(long j) throws IOException {
        return this.f158in.skip(j);
    }
}
