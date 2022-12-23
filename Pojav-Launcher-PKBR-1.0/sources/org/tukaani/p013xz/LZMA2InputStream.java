package org.tukaani.p013xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import org.tukaani.p013xz.lzma.LZMADecoder;
import org.tukaani.p013xz.p014lz.LZDecoder;
import org.tukaani.p013xz.rangecoder.RangeDecoderFromBuffer;

/* renamed from: org.tukaani.xz.LZMA2InputStream */
public class LZMA2InputStream extends InputStream {
    private static final int COMPRESSED_SIZE_MAX = 65536;
    public static final int DICT_SIZE_MAX = 2147483632;
    public static final int DICT_SIZE_MIN = 4096;
    private final ArrayCache arrayCache;
    private boolean endReached;
    private IOException exception;

    /* renamed from: in */
    private DataInputStream f177in;
    private boolean isLZMAChunk;

    /* renamed from: lz */
    private LZDecoder f178lz;
    private LZMADecoder lzma;
    private boolean needDictReset;
    private boolean needProps;

    /* renamed from: rc */
    private RangeDecoderFromBuffer f179rc;
    private final byte[] tempBuf;
    private int uncompressedSize;

    public LZMA2InputStream(InputStream inputStream, int i) {
        this(inputStream, i, (byte[]) null);
    }

    public LZMA2InputStream(InputStream inputStream, int i, byte[] bArr) {
        this(inputStream, i, bArr, ArrayCache.getDefaultCache());
    }

    LZMA2InputStream(InputStream inputStream, int i, byte[] bArr, ArrayCache arrayCache2) {
        this.uncompressedSize = 0;
        this.isLZMAChunk = false;
        this.needDictReset = true;
        this.needProps = true;
        this.endReached = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        if (inputStream != null) {
            this.arrayCache = arrayCache2;
            this.f177in = new DataInputStream(inputStream);
            this.f179rc = new RangeDecoderFromBuffer(65536, arrayCache2);
            this.f178lz = new LZDecoder(getDictSize(i), bArr, arrayCache2);
            if (bArr != null && bArr.length > 0) {
                this.needDictReset = false;
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private void decodeChunkHeader() throws IOException {
        int readUnsignedByte = this.f177in.readUnsignedByte();
        if (readUnsignedByte == 0) {
            this.endReached = true;
            putArraysToCache();
            return;
        }
        if (readUnsignedByte >= 224 || readUnsignedByte == 1) {
            this.needProps = true;
            this.needDictReset = false;
            this.f178lz.reset();
        } else if (this.needDictReset) {
            throw new CorruptedInputException();
        }
        if (readUnsignedByte >= 128) {
            this.isLZMAChunk = true;
            int i = (readUnsignedByte & 31) << 16;
            this.uncompressedSize = i;
            this.uncompressedSize = i + this.f177in.readUnsignedShort() + 1;
            int readUnsignedShort = this.f177in.readUnsignedShort() + 1;
            if (readUnsignedByte >= 192) {
                this.needProps = false;
                decodeProps();
            } else if (this.needProps) {
                throw new CorruptedInputException();
            } else if (readUnsignedByte >= 160) {
                this.lzma.reset();
            }
            this.f179rc.prepareInputBuffer(this.f177in, readUnsignedShort);
        } else if (readUnsignedByte <= 2) {
            this.isLZMAChunk = false;
            this.uncompressedSize = this.f177in.readUnsignedShort() + 1;
        } else {
            throw new CorruptedInputException();
        }
    }

    private void decodeProps() throws IOException {
        int readUnsignedByte = this.f177in.readUnsignedByte();
        if (readUnsignedByte <= 224) {
            int i = readUnsignedByte / 45;
            int i2 = readUnsignedByte - ((i * 9) * 5);
            int i3 = i2 / 9;
            int i4 = i2 - (i3 * 9);
            if (i4 + i3 <= 4) {
                this.lzma = new LZMADecoder(this.f178lz, this.f179rc, i4, i3, i);
                return;
            }
            throw new CorruptedInputException();
        }
        throw new CorruptedInputException();
    }

    private static int getDictSize(int i) {
        if (i >= 4096 && i <= 2147483632) {
            return (i + 15) & -16;
        }
        throw new IllegalArgumentException("Unsupported dictionary size " + i);
    }

    public static int getMemoryUsage(int i) {
        return (getDictSize(i) / 1024) + 104;
    }

    private void putArraysToCache() {
        LZDecoder lZDecoder = this.f178lz;
        if (lZDecoder != null) {
            lZDecoder.putArraysToCache(this.arrayCache);
            this.f178lz = null;
            this.f179rc.putArraysToCache(this.arrayCache);
            this.f179rc = null;
        }
    }

    public int available() throws IOException {
        DataInputStream dataInputStream = this.f177in;
        if (dataInputStream != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                return this.isLZMAChunk ? this.uncompressedSize : Math.min(this.uncompressedSize, dataInputStream.available());
            }
            throw iOException;
        }
        throw new XZIOException("Stream closed");
    }

    public void close() throws IOException {
        if (this.f177in != null) {
            putArraysToCache();
            try {
                this.f177in.close();
            } finally {
                this.f177in = null;
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
        }
        int i4 = 0;
        if (i2 == 0) {
            return 0;
        }
        if (this.f177in != null) {
            IOException iOException = this.exception;
            if (iOException != null) {
                throw iOException;
            } else if (this.endReached) {
                return -1;
            } else {
                while (i2 > 0) {
                    try {
                        if (this.uncompressedSize == 0) {
                            decodeChunkHeader();
                            if (this.endReached) {
                                if (i4 == 0) {
                                    return -1;
                                }
                                return i4;
                            }
                        }
                        int min = Math.min(this.uncompressedSize, i2);
                        if (!this.isLZMAChunk) {
                            this.f178lz.copyUncompressed(this.f177in, min);
                        } else {
                            this.f178lz.setLimit(min);
                            this.lzma.decode();
                        }
                        int flush = this.f178lz.flush(bArr, i);
                        i += flush;
                        i2 -= flush;
                        i4 += flush;
                        int i5 = this.uncompressedSize - flush;
                        this.uncompressedSize = i5;
                        if (i5 == 0) {
                            if (!this.f179rc.isFinished() || this.f178lz.hasPending()) {
                                throw new CorruptedInputException();
                            }
                        }
                    } catch (IOException e) {
                        this.exception = e;
                        throw e;
                    }
                }
                return i4;
            }
        } else {
            throw new XZIOException("Stream closed");
        }
    }
}
