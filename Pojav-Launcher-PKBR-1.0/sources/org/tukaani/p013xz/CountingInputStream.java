package org.tukaani.p013xz;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.tukaani.xz.CountingInputStream */
class CountingInputStream extends CloseIgnoringInputStream {
    private long size = 0;

    public CountingInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public long getSize() {
        return this.size;
    }

    public int read() throws IOException {
        int read = this.in.read();
        if (read != -1) {
            long j = this.size;
            if (j >= 0) {
                this.size = j + 1;
            }
        }
        return read;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.in.read(bArr, i, i2);
        if (read > 0) {
            long j = this.size;
            if (j >= 0) {
                this.size = j + ((long) read);
            }
        }
        return read;
    }
}
