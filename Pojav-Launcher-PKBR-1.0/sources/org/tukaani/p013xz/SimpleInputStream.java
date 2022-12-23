package org.tukaani.p013xz;

import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import org.tukaani.p013xz.simple.SimpleFilter;

/* renamed from: org.tukaani.xz.SimpleInputStream */
class SimpleInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FILTER_BUF_SIZE = 4096;
    private boolean endReached = false;
    private IOException exception = null;
    private final byte[] filterBuf = new byte[4096];
    private int filtered = 0;

    /* renamed from: in */
    private InputStream f192in;
    private int pos = 0;
    private final SimpleFilter simpleFilter;
    private final byte[] tempBuf = new byte[1];
    private int unfiltered = 0;

    SimpleInputStream(InputStream inputStream, SimpleFilter simpleFilter2) {
        if (inputStream == null) {
            throw new NullPointerException();
        } else if (simpleFilter2 != null) {
            this.f192in = inputStream;
            this.simpleFilter = simpleFilter2;
        } else {
            throw new AssertionError();
        }
    }

    static int getMemoryUsage() {
        return 5;
    }

    public int available() throws IOException {
        if (this.f192in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                return this.filtered;
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    public void close() throws IOException {
        InputStream inputStream = this.f192in;
        if (inputStream != null) {
            try {
                inputStream.close();
            } finally {
                this.f192in = null;
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
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        } else if (i2 == 0) {
            return 0;
        } else {
            if (this.f192in != null) {
                IOException iOException = this.exception;
                if (iOException == null) {
                    int i4 = 0;
                    while (true) {
                        try {
                            int min = Math.min(this.filtered, i2);
                            System.arraycopy(this.filterBuf, this.pos, bArr, i, min);
                            int i5 = this.pos + min;
                            this.pos = i5;
                            int i6 = this.filtered - min;
                            this.filtered = i6;
                            i += min;
                            i2 -= min;
                            i4 += min;
                            int i7 = this.unfiltered;
                            if (i5 + i6 + i7 == 4096) {
                                byte[] bArr2 = this.filterBuf;
                                System.arraycopy(bArr2, i5, bArr2, 0, i6 + i7);
                                this.pos = 0;
                            }
                            if (i2 == 0) {
                                break;
                            } else if (this.endReached) {
                                break;
                            } else {
                                int i8 = this.filtered;
                                if (i8 == 0) {
                                    int i9 = this.pos;
                                    int i10 = this.unfiltered;
                                    int read = this.f192in.read(this.filterBuf, i9 + i8 + i10, 4096 - ((i9 + i8) + i10));
                                    if (read == -1) {
                                        this.endReached = true;
                                        this.filtered = this.unfiltered;
                                        this.unfiltered = 0;
                                    } else {
                                        int i11 = this.unfiltered + read;
                                        this.unfiltered = i11;
                                        int code = this.simpleFilter.code(this.filterBuf, this.pos, i11);
                                        this.filtered = code;
                                        int i12 = this.unfiltered;
                                        if (code <= i12) {
                                            this.unfiltered = i12 - code;
                                        } else {
                                            throw new AssertionError();
                                        }
                                    }
                                } else {
                                    throw new AssertionError();
                                }
                            }
                        } catch (IOException e) {
                            this.exception = e;
                            throw e;
                        }
                    }
                    if (i4 > 0) {
                        return i4;
                    }
                    return -1;
                }
                throw iOException;
            }
            throw new XZIOException("Stream closed");
        }
    }
}
