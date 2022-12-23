package org.tukaani.p013xz;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.tukaani.xz.CountingOutputStream */
class CountingOutputStream extends FinishableOutputStream {
    private final OutputStream out;
    private long size = 0;

    public CountingOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void close() throws IOException {
        this.out.close();
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public long getSize() {
        return this.size;
    }

    public void write(int i) throws IOException {
        this.out.write(i);
        long j = this.size;
        if (j >= 0) {
            this.size = j + 1;
        }
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        long j = this.size;
        if (j >= 0) {
            this.size = j + ((long) i2);
        }
    }
}
