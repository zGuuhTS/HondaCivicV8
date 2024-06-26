package org.apache.commons.codec.binary;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.codec.binary.BaseNCodec;

public class BaseNCodecInputStream extends FilterInputStream {
    private final BaseNCodec baseNCodec;
    private final BaseNCodec.Context context = new BaseNCodec.Context();
    private final boolean doEncode;
    private final byte[] singleByte = new byte[1];

    protected BaseNCodecInputStream(InputStream input, BaseNCodec baseNCodec2, boolean doEncode2) {
        super(input);
        this.doEncode = doEncode2;
        this.baseNCodec = baseNCodec2;
    }

    public int available() throws IOException {
        return this.context.eof ^ true ? 1 : 0;
    }

    public synchronized void mark(int readLimit) {
    }

    public boolean markSupported() {
        return false;
    }

    public int read() throws IOException {
        int r = read(this.singleByte, 0, 1);
        while (r == 0) {
            r = read(this.singleByte, 0, 1);
        }
        if (r <= 0) {
            return -1;
        }
        byte b = this.singleByte[0];
        return b < 0 ? b + 256 : b;
    }

    public int read(byte[] array, int offset, int len) throws IOException {
        Objects.requireNonNull(array, "array");
        if (offset < 0 || len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (offset > array.length || offset + len > array.length) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        } else {
            int readLen = 0;
            while (readLen == 0) {
                if (!this.baseNCodec.hasData(this.context)) {
                    byte[] buf = new byte[(this.doEncode ? 4096 : 8192)];
                    int c = this.in.read(buf);
                    if (this.doEncode) {
                        this.baseNCodec.encode(buf, 0, c, this.context);
                    } else {
                        this.baseNCodec.decode(buf, 0, c, this.context);
                    }
                }
                readLen = this.baseNCodec.readResults(array, offset, len, this.context);
            }
            return readLen;
        }
    }

    public synchronized void reset() throws IOException {
        throw new IOException("mark/reset not supported");
    }

    public long skip(long n) throws IOException {
        int len;
        if (n >= 0) {
            byte[] b = new byte[512];
            long todo = n;
            while (todo > 0 && (len = read(b, 0, (int) Math.min((long) b.length, todo))) != -1) {
                todo -= (long) len;
            }
            return n - todo;
        }
        throw new IllegalArgumentException("Negative skip length: " + n);
    }
}
