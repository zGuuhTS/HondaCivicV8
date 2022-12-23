package org.tukaani.p013xz;

import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import org.tukaani.p013xz.delta.DeltaDecoder;

/* renamed from: org.tukaani.xz.DeltaInputStream */
public class DeltaInputStream extends InputStream {
    public static final int DISTANCE_MAX = 256;
    public static final int DISTANCE_MIN = 1;
    private final DeltaDecoder delta;
    private IOException exception = null;

    /* renamed from: in */
    private InputStream f176in;
    private final byte[] tempBuf = new byte[1];

    public DeltaInputStream(InputStream inputStream, int i) {
        if (inputStream != null) {
            this.f176in = inputStream;
            this.delta = new DeltaDecoder(i);
            return;
        }
        throw new NullPointerException();
    }

    public int available() throws IOException {
        InputStream inputStream = this.f176in;
        if (inputStream != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                return inputStream.available();
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    public void close() throws IOException {
        InputStream inputStream = this.f176in;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                this.f176in = null;
            }
        }
    }

    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & UByte.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        InputStream inputStream = this.f176in;
        if (inputStream != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                try {
                    int read = inputStream.read(bArr, i, i2);
                    if (read == -1) {
                        return -1;
                    }
                    this.delta.decode(bArr, i, read);
                    return read;
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            } else {
                throw iOException;
            }
        } else {
            throw new XZIOException("Stream closed");
        }
    }
}
