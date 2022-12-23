package org.tukaani.p013xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import org.tukaani.p013xz.check.Check;
import org.tukaani.p013xz.common.DecoderUtil;
import org.tukaani.p013xz.common.StreamFlags;
import org.tukaani.p013xz.index.IndexHash;

/* renamed from: org.tukaani.xz.SingleXZInputStream */
public class SingleXZInputStream extends InputStream {
    private final ArrayCache arrayCache;
    private BlockInputStream blockDecoder;
    private final Check check;
    private boolean endReached;
    private IOException exception;

    /* renamed from: in */
    private InputStream f193in;
    private final IndexHash indexHash;
    private final int memoryLimit;
    private final StreamFlags streamHeaderFlags;
    private final byte[] tempBuf;
    private final boolean verifyCheck;

    public SingleXZInputStream(InputStream inputStream) throws IOException {
        this(inputStream, -1);
    }

    public SingleXZInputStream(InputStream inputStream, int i) throws IOException {
        this(inputStream, i, true);
    }

    public SingleXZInputStream(InputStream inputStream, int i, ArrayCache arrayCache2) throws IOException {
        this(inputStream, i, true, arrayCache2);
    }

    public SingleXZInputStream(InputStream inputStream, int i, boolean z) throws IOException {
        this(inputStream, i, z, ArrayCache.getDefaultCache());
    }

    public SingleXZInputStream(InputStream inputStream, int i, boolean z, ArrayCache arrayCache2) throws IOException {
        this(inputStream, i, z, readStreamHeader(inputStream), arrayCache2);
    }

    SingleXZInputStream(InputStream inputStream, int i, boolean z, byte[] bArr, ArrayCache arrayCache2) throws IOException {
        this.blockDecoder = null;
        this.indexHash = new IndexHash();
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        this.arrayCache = arrayCache2;
        this.f193in = inputStream;
        this.memoryLimit = i;
        this.verifyCheck = z;
        StreamFlags decodeStreamHeader = DecoderUtil.decodeStreamHeader(bArr);
        this.streamHeaderFlags = decodeStreamHeader;
        this.check = Check.getInstance(decodeStreamHeader.checkType);
    }

    public SingleXZInputStream(InputStream inputStream, ArrayCache arrayCache2) throws IOException {
        this(inputStream, -1, arrayCache2);
    }

    private static byte[] readStreamHeader(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[12];
        new DataInputStream(inputStream).readFully(bArr);
        return bArr;
    }

    private void validateStreamFooter() throws IOException {
        byte[] bArr = new byte[12];
        new DataInputStream(this.f193in).readFully(bArr);
        StreamFlags decodeStreamFooter = DecoderUtil.decodeStreamFooter(bArr);
        if (!DecoderUtil.areStreamFlagsEqual(this.streamHeaderFlags, decodeStreamFooter) || this.indexHash.getIndexSize() != decodeStreamFooter.backwardSize) {
            throw new CorruptedInputException("XZ Stream Footer does not match Stream Header");
        }
    }

    public int available() throws IOException {
        if (this.f193in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                BlockInputStream blockInputStream = this.blockDecoder;
                if (blockInputStream == null) {
                    return 0;
                }
                return blockInputStream.available();
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    public void close() throws IOException {
        close(true);
    }

    public void close(boolean z) throws IOException {
        if (this.f193in != null) {
            BlockInputStream blockInputStream = this.blockDecoder;
            if (blockInputStream != null) {
                blockInputStream.close();
                this.blockDecoder = null;
            }
            if (z) {
                try {
                    this.f193in.close();
                } catch (Throwable th) {
                    this.f193in = null;
                    throw th;
                }
            }
            this.f193in = null;
        }
    }

    public String getCheckName() {
        return this.check.getName();
    }

    public int getCheckType() {
        return this.streamHeaderFlags.checkType;
    }

    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & UByte.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        byte[] bArr2 = bArr;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr2.length) {
            throw new IndexOutOfBoundsException();
        } else if (i2 == 0) {
            return 0;
        } else {
            if (this.f193in != null) {
                IOException iOException = this.exception;
                if (iOException != null) {
                    throw iOException;
                } else if (this.endReached) {
                    return -1;
                } else {
                    int i4 = i2;
                    int i5 = 0;
                    int i6 = i;
                    while (i4 > 0) {
                        try {
                            if (this.blockDecoder == null) {
                                try {
                                    this.blockDecoder = new BlockInputStream(this.f193in, this.check, this.verifyCheck, this.memoryLimit, -1, -1, this.arrayCache);
                                } catch (IndexIndicatorException e) {
                                    this.indexHash.validate(this.f193in);
                                    validateStreamFooter();
                                    this.endReached = true;
                                    if (i5 > 0) {
                                        return i5;
                                    }
                                    return -1;
                                }
                            }
                            int read = this.blockDecoder.read(bArr2, i6, i4);
                            if (read > 0) {
                                i5 += read;
                                i6 += read;
                                i4 -= read;
                            } else if (read == -1) {
                                this.indexHash.add(this.blockDecoder.getUnpaddedSize(), this.blockDecoder.getUncompressedSize());
                                this.blockDecoder = null;
                            }
                        } catch (IOException e2) {
                            this.exception = e2;
                            if (i5 == 0) {
                                throw e2;
                            }
                        }
                    }
                    return i5;
                }
            } else {
                throw new XZIOException("Stream closed");
            }
        }
    }
}
