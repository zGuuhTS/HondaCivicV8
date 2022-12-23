package org.tukaani.p013xz;

import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;

/* renamed from: org.tukaani.xz.XZInputStream */
public class XZInputStream extends InputStream {
    private final ArrayCache arrayCache;
    private boolean endReached;
    private IOException exception;

    /* renamed from: in */
    private InputStream f194in;
    private final int memoryLimit;
    private final byte[] tempBuf;
    private final boolean verifyCheck;
    private SingleXZInputStream xzIn;

    public XZInputStream(InputStream inputStream) throws IOException {
        this(inputStream, -1);
    }

    public XZInputStream(InputStream inputStream, int i) throws IOException {
        this(inputStream, i, true);
    }

    public XZInputStream(InputStream inputStream, int i, ArrayCache arrayCache2) throws IOException {
        this(inputStream, i, true, arrayCache2);
    }

    public XZInputStream(InputStream inputStream, int i, boolean z) throws IOException {
        this(inputStream, i, z, ArrayCache.getDefaultCache());
    }

    public XZInputStream(InputStream inputStream, int i, boolean z, ArrayCache arrayCache2) throws IOException {
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        this.arrayCache = arrayCache2;
        this.f194in = inputStream;
        this.memoryLimit = i;
        this.verifyCheck = z;
        this.xzIn = new SingleXZInputStream(inputStream, i, z, arrayCache2);
    }

    public XZInputStream(InputStream inputStream, ArrayCache arrayCache2) throws IOException {
        this(inputStream, -1, arrayCache2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void prepareNextStream() throws java.io.IOException {
        /*
            r8 = this;
            java.io.DataInputStream r0 = new java.io.DataInputStream
            java.io.InputStream r1 = r8.f194in
            r0.<init>(r1)
            r1 = 12
            byte[] r6 = new byte[r1]
        L_0x000b:
            r1 = 0
            r2 = 1
            int r3 = r0.read(r6, r1, r2)
            r4 = -1
            if (r3 != r4) goto L_0x0017
            r8.endReached = r2
            return
        L_0x0017:
            r3 = 3
            r0.readFully(r6, r2, r3)
            byte r1 = r6[r1]
            if (r1 != 0) goto L_0x002c
            byte r1 = r6[r2]
            if (r1 != 0) goto L_0x002c
            r1 = 2
            byte r1 = r6[r1]
            if (r1 != 0) goto L_0x002c
            byte r1 = r6[r3]
            if (r1 == 0) goto L_0x000b
        L_0x002c:
            r1 = 4
            r2 = 8
            r0.readFully(r6, r1, r2)
            org.tukaani.xz.SingleXZInputStream r0 = new org.tukaani.xz.SingleXZInputStream     // Catch:{ XZFormatException -> 0x0043 }
            java.io.InputStream r3 = r8.f194in     // Catch:{ XZFormatException -> 0x0043 }
            int r4 = r8.memoryLimit     // Catch:{ XZFormatException -> 0x0043 }
            boolean r5 = r8.verifyCheck     // Catch:{ XZFormatException -> 0x0043 }
            org.tukaani.xz.ArrayCache r7 = r8.arrayCache     // Catch:{ XZFormatException -> 0x0043 }
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ XZFormatException -> 0x0043 }
            r8.xzIn = r0     // Catch:{ XZFormatException -> 0x0043 }
            return
        L_0x0043:
            r0 = move-exception
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException
            java.lang.String r1 = "Garbage after a valid XZ Stream"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.XZInputStream.prepareNextStream():void");
    }

    public int available() throws IOException {
        if (this.f194in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                SingleXZInputStream singleXZInputStream = this.xzIn;
                if (singleXZInputStream == null) {
                    return 0;
                }
                return singleXZInputStream.available();
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    public void close() throws IOException {
        close(true);
    }

    public void close(boolean z) throws IOException {
        if (this.f194in != null) {
            SingleXZInputStream singleXZInputStream = this.xzIn;
            if (singleXZInputStream != null) {
                singleXZInputStream.close(false);
                this.xzIn = null;
            }
            if (z) {
                try {
                    this.f194in.close();
                } catch (Throwable th) {
                    this.f194in = null;
                    throw th;
                }
            }
            this.f194in = null;
        }
    }

    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & UByte.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        int i4 = 0;
        if (i2 == 0) {
            return 0;
        }
        if (this.f194in != null) {
            IOException iOException = this.exception;
            if (iOException != null) {
                throw iOException;
            } else if (this.endReached) {
                return -1;
            } else {
                while (i2 > 0) {
                    try {
                        if (this.xzIn == null) {
                            prepareNextStream();
                            if (this.endReached) {
                                if (i4 == 0) {
                                    return -1;
                                }
                                return i4;
                            }
                        }
                        int read = this.xzIn.read(bArr, i, i2);
                        if (read > 0) {
                            i4 += read;
                            i += read;
                            i2 -= read;
                        } else if (read == -1) {
                            this.xzIn = null;
                        }
                    } catch (IOException e) {
                        this.exception = e;
                        if (i4 == 0) {
                            throw e;
                        }
                    }
                }
                return i4;
            }
        } else {
            throw new XZIOException("Stream closed");
        }
    }
}
