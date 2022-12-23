package org.tukaani.p013xz;

import java.io.IOException;
import org.tukaani.p013xz.simple.SimpleFilter;

/* renamed from: org.tukaani.xz.SimpleOutputStream */
class SimpleOutputStream extends FinishableOutputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FILTER_BUF_SIZE = 4096;
    private IOException exception = null;
    private final byte[] filterBuf = new byte[4096];
    private boolean finished = false;
    private FinishableOutputStream out;
    private int pos = 0;
    private final SimpleFilter simpleFilter;
    private final byte[] tempBuf = new byte[1];
    private int unfiltered = 0;

    SimpleOutputStream(FinishableOutputStream finishableOutputStream, SimpleFilter simpleFilter2) {
        if (finishableOutputStream != null) {
            this.out = finishableOutputStream;
            this.simpleFilter = simpleFilter2;
            return;
        }
        throw new NullPointerException();
    }

    static int getMemoryUsage() {
        return 5;
    }

    private void writePending() throws IOException {
        if (!this.finished) {
            IOException iOException = this.exception;
            if (iOException == null) {
                try {
                    this.out.write(this.filterBuf, this.pos, this.unfiltered);
                    this.finished = true;
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            } else {
                throw iOException;
            }
        } else {
            throw new AssertionError();
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            if (!this.finished) {
                try {
                    writePending();
                } catch (IOException e) {
                }
            }
            try {
                this.out.close();
            } catch (IOException e2) {
                if (this.exception == null) {
                    this.exception = e2;
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
            writePending();
            try {
                this.out.finish();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws IOException {
        throw new UnsupportedOptionsException("Flushing is not supported");
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
            while (i2 > 0) {
                int min = Math.min(i2, 4096 - (this.pos + this.unfiltered));
                System.arraycopy(bArr, i, this.filterBuf, this.pos + this.unfiltered, min);
                i += min;
                i2 -= min;
                int i4 = this.unfiltered + min;
                this.unfiltered = i4;
                int code = this.simpleFilter.code(this.filterBuf, this.pos, i4);
                int i5 = this.unfiltered;
                if (code <= i5) {
                    this.unfiltered = i5 - code;
                    try {
                        this.out.write(this.filterBuf, this.pos, code);
                        int i6 = this.pos + code;
                        this.pos = i6;
                        int i7 = this.unfiltered;
                        if (i6 + i7 == 4096) {
                            byte[] bArr2 = this.filterBuf;
                            System.arraycopy(bArr2, i6, bArr2, 0, i7);
                            this.pos = 0;
                        }
                    } catch (IOException e) {
                        this.exception = e;
                        throw e;
                    }
                } else {
                    throw new AssertionError();
                }
            }
        } else {
            throw new XZIOException("Stream finished or closed");
        }
    }
}
