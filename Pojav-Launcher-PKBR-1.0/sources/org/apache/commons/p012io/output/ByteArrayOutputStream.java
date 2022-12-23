package org.apache.commons.p012io.output;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.p012io.input.ClosedInputStream;

/* renamed from: org.apache.commons.io.output.ByteArrayOutputStream */
public class ByteArrayOutputStream extends OutputStream {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private final List<byte[]> buffers;
    private int count;
    private byte[] currentBuffer;
    private int currentBufferIndex;
    private int filledBufferSum;

    public ByteArrayOutputStream() {
        this(1024);
    }

    public ByteArrayOutputStream(int i) {
        this.buffers = new ArrayList();
        if (i >= 0) {
            synchronized (this) {
                needNewBuffer(i);
            }
            return;
        }
        throw new IllegalArgumentException("Negative initial size: " + i);
    }

    private void needNewBuffer(int i) {
        int i2;
        if (this.currentBufferIndex < this.buffers.size() - 1) {
            this.filledBufferSum += this.currentBuffer.length;
            int i3 = this.currentBufferIndex + 1;
            this.currentBufferIndex = i3;
            this.currentBuffer = this.buffers.get(i3);
            return;
        }
        byte[] bArr = this.currentBuffer;
        if (bArr == null) {
            i2 = 0;
        } else {
            i = Math.max(bArr.length << 1, i - this.filledBufferSum);
            i2 = this.filledBufferSum + this.currentBuffer.length;
        }
        this.filledBufferSum = i2;
        this.currentBufferIndex++;
        byte[] bArr2 = new byte[i];
        this.currentBuffer = bArr2;
        this.buffers.add(bArr2);
    }

    private InputStream toBufferedInputStream() {
        int i = this.count;
        if (i == 0) {
            return new ClosedInputStream();
        }
        ArrayList arrayList = new ArrayList(this.buffers.size());
        for (byte[] next : this.buffers) {
            int min = Math.min(next.length, i);
            arrayList.add(new ByteArrayInputStream(next, 0, min));
            i -= min;
            if (i == 0) {
                break;
            }
        }
        return new SequenceInputStream(Collections.enumeration(arrayList));
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(inputStream);
        return byteArrayOutputStream.toBufferedInputStream();
    }

    public void close() throws IOException {
    }

    public void reset() {
        synchronized (this) {
            this.count = 0;
            this.filledBufferSum = 0;
            this.currentBufferIndex = 0;
            this.currentBuffer = this.buffers.get(0);
        }
    }

    public int size() {
        int i;
        synchronized (this) {
            i = this.count;
        }
        return i;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002c, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] toByteArray() {
        /*
            r7 = this;
            monitor-enter(r7)
            int r0 = r7.count     // Catch:{ all -> 0x002d }
            if (r0 != 0) goto L_0x0009
            byte[] r0 = EMPTY_BYTE_ARRAY     // Catch:{ all -> 0x002d }
            monitor-exit(r7)
            return r0
        L_0x0009:
            byte[] r1 = new byte[r0]     // Catch:{ all -> 0x002d }
            java.util.List<byte[]> r2 = r7.buffers     // Catch:{ all -> 0x002d }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x002d }
            r3 = 0
            r4 = r3
        L_0x0013:
            boolean r5 = r2.hasNext()     // Catch:{ all -> 0x002d }
            if (r5 == 0) goto L_0x002b
            java.lang.Object r5 = r2.next()     // Catch:{ all -> 0x002d }
            byte[] r5 = (byte[]) r5     // Catch:{ all -> 0x002d }
            int r6 = r5.length     // Catch:{ all -> 0x002d }
            int r6 = java.lang.Math.min(r6, r0)     // Catch:{ all -> 0x002d }
            java.lang.System.arraycopy(r5, r3, r1, r4, r6)     // Catch:{ all -> 0x002d }
            int r4 = r4 + r6
            int r0 = r0 - r6
            if (r0 != 0) goto L_0x0013
        L_0x002b:
            monitor-exit(r7)
            return r1
        L_0x002d:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p012io.output.ByteArrayOutputStream.toByteArray():byte[]");
    }

    public String toString() {
        return new String(toByteArray());
    }

    public String toString(String str) throws UnsupportedEncodingException {
        return new String(toByteArray(), str);
    }

    public int write(InputStream inputStream) throws IOException {
        int i;
        synchronized (this) {
            int i2 = this.count - this.filledBufferSum;
            byte[] bArr = this.currentBuffer;
            int read = inputStream.read(bArr, i2, bArr.length - i2);
            i = 0;
            while (read != -1) {
                i += read;
                i2 += read;
                this.count += read;
                byte[] bArr2 = this.currentBuffer;
                if (i2 == bArr2.length) {
                    needNewBuffer(bArr2.length);
                    i2 = 0;
                }
                byte[] bArr3 = this.currentBuffer;
                read = inputStream.read(bArr3, i2, bArr3.length - i2);
            }
        }
        return i;
    }

    public void write(int i) {
        synchronized (this) {
            int i2 = this.count;
            int i3 = i2 - this.filledBufferSum;
            if (i3 == this.currentBuffer.length) {
                needNewBuffer(i2 + 1);
                i3 = 0;
            }
            this.currentBuffer[i3] = (byte) i;
            this.count++;
        }
    }

    public void write(byte[] bArr, int i, int i2) {
        int i3;
        if (i < 0 || i > bArr.length || i2 < 0 || (i3 = i + i2) > bArr.length || i3 < 0) {
            throw new IndexOutOfBoundsException();
        } else if (i2 != 0) {
            synchronized (this) {
                int i4 = this.count;
                int i5 = i4 + i2;
                int i6 = i4 - this.filledBufferSum;
                while (i2 > 0) {
                    int min = Math.min(i2, this.currentBuffer.length - i6);
                    System.arraycopy(bArr, i3 - i2, this.currentBuffer, i6, min);
                    i2 -= min;
                    if (i2 > 0) {
                        needNewBuffer(i5);
                        i6 = 0;
                    }
                }
                this.count = i5;
            }
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        synchronized (this) {
            int i = this.count;
            for (byte[] next : this.buffers) {
                int min = Math.min(next.length, i);
                outputStream.write(next, 0, min);
                i -= min;
                if (i == 0) {
                    break;
                }
            }
        }
    }
}
