package org.tukaani.p013xz;

import java.io.IOException;
import org.tukaani.p013xz.delta.DeltaEncoder;

/* renamed from: org.tukaani.xz.DeltaOutputStream */
class DeltaOutputStream extends FinishableOutputStream {
    private static final int FILTER_BUF_SIZE = 4096;
    private final DeltaEncoder delta;
    private IOException exception = null;
    private final byte[] filterBuf = new byte[4096];
    private boolean finished = false;
    private FinishableOutputStream out;
    private final byte[] tempBuf = new byte[1];

    DeltaOutputStream(FinishableOutputStream finishableOutputStream, DeltaOptions deltaOptions) {
        this.out = finishableOutputStream;
        this.delta = new DeltaEncoder(deltaOptions.getDistance());
    }

    static int getMemoryUsage() {
        return 5;
    }

    public void close() throws IOException {
        FinishableOutputStream finishableOutputStream = this.out;
        if (finishableOutputStream != null) {
            try {
                finishableOutputStream.close();
            } catch (IOException e) {
                if (this.exception == null) {
                    this.exception = e;
                }
            }
            this.out = null;
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            IOException iOException = this.exception;
            if (iOException == null) {
                try {
                    this.out.finish();
                    this.finished = true;
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            } else {
                throw iOException;
            }
        }
    }

    public void flush() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        } else if (!this.finished) {
            try {
                this.out.flush();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        } else {
            throw new XZIOException("Stream finished or closed");
        }
    }

    public void write(int i) throws IOException {
        byte[] bArr = this.tempBuf;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        } else if (!this.finished) {
            while (i2 > 4096) {
                try {
                    this.delta.encode(bArr, i, 4096, this.filterBuf);
                    this.out.write(this.filterBuf);
                    i += 4096;
                    i2 -= 4096;
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            }
            this.delta.encode(bArr, i, i2, this.filterBuf);
            this.out.write(this.filterBuf, 0, i2);
        } else {
            throw new XZIOException("Stream finished");
        }
    }
}
