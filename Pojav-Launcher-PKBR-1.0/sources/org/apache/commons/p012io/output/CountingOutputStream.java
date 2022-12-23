package org.apache.commons.p012io.output;

import java.io.OutputStream;

/* renamed from: org.apache.commons.io.output.CountingOutputStream */
public class CountingOutputStream extends ProxyOutputStream {
    private long count = 0;

    public CountingOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    /* access modifiers changed from: protected */
    public void beforeWrite(int i) {
        synchronized (this) {
            this.count += (long) i;
        }
    }

    public long getByteCount() {
        long j;
        synchronized (this) {
            j = this.count;
        }
        return j;
    }

    public int getCount() {
        long byteCount = getByteCount();
        if (byteCount <= 2147483647L) {
            return (int) byteCount;
        }
        throw new ArithmeticException("The byte count " + byteCount + " is too large to be converted to an int");
    }

    public long resetByteCount() {
        long j;
        synchronized (this) {
            j = this.count;
            this.count = 0;
        }
        return j;
    }

    public int resetCount() {
        long resetByteCount = resetByteCount();
        if (resetByteCount <= 2147483647L) {
            return (int) resetByteCount;
        }
        throw new ArithmeticException("The byte count " + resetByteCount + " is too large to be converted to an int");
    }
}
