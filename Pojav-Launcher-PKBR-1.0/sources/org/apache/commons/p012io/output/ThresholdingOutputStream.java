package org.apache.commons.p012io.output;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.ThresholdingOutputStream */
public abstract class ThresholdingOutputStream extends OutputStream {
    private final int threshold;
    private boolean thresholdExceeded;
    private long written;

    public ThresholdingOutputStream(int i) {
        this.threshold = i;
    }

    /* access modifiers changed from: protected */
    public void checkThreshold(int i) throws IOException {
        if (!this.thresholdExceeded && this.written + ((long) i) > ((long) this.threshold)) {
            this.thresholdExceeded = true;
            thresholdReached();
        }
    }

    public void close() throws IOException {
        try {
            flush();
        } catch (IOException e) {
        }
        getStream().close();
    }

    public void flush() throws IOException {
        getStream().flush();
    }

    public long getByteCount() {
        return this.written;
    }

    /* access modifiers changed from: protected */
    public abstract OutputStream getStream() throws IOException;

    public int getThreshold() {
        return this.threshold;
    }

    public boolean isThresholdExceeded() {
        return this.written > ((long) this.threshold);
    }

    /* access modifiers changed from: protected */
    public void resetByteCount() {
        this.thresholdExceeded = false;
        this.written = 0;
    }

    /* access modifiers changed from: protected */
    public abstract void thresholdReached() throws IOException;

    public void write(int i) throws IOException {
        checkThreshold(1);
        getStream().write(i);
        this.written++;
    }

    public void write(byte[] bArr) throws IOException {
        checkThreshold(bArr.length);
        getStream().write(bArr);
        this.written += (long) bArr.length;
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        checkThreshold(i2);
        getStream().write(bArr, i, i2);
        this.written += (long) i2;
    }
}
