package org.apache.commons.compress.archivers.dump;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.p012io.FileUtils;

class TapeInputStream extends FilterInputStream {
    private static final int recordSize = 1024;
    private byte[] blockBuffer = new byte[1024];
    private int blockSize = 1024;
    private long bytesRead = 0;
    private int currBlkIdx = -1;
    private boolean isCompressed = false;
    private int readOffset = 1024;

    /* renamed from: org.apache.commons.compress.archivers.dump.TapeInputStream$1 */
    static /* synthetic */ class C09021 {

        /* renamed from: $SwitchMap$org$apache$commons$compress$archivers$dump$DumpArchiveConstants$COMPRESSION_TYPE */
        static final /* synthetic */ int[] f140x24f7e417;

        static {
            int[] iArr = new int[DumpArchiveConstants.COMPRESSION_TYPE.values().length];
            f140x24f7e417 = iArr;
            try {
                iArr[DumpArchiveConstants.COMPRESSION_TYPE.ZLIB.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f140x24f7e417[DumpArchiveConstants.COMPRESSION_TYPE.BZLIB.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f140x24f7e417[DumpArchiveConstants.COMPRESSION_TYPE.LZO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public TapeInputStream(InputStream inputStream) {
        super(inputStream);
    }

    private boolean readBlock(boolean z) throws IOException {
        boolean z2;
        if (this.in != null) {
            if (!this.isCompressed || this.currBlkIdx == -1) {
                z2 = readFully(this.blockBuffer, 0, this.blockSize);
            } else if (!readFully(this.blockBuffer, 0, 4)) {
                return false;
            } else {
                this.bytesRead += 4;
                int convert32 = DumpArchiveUtil.convert32(this.blockBuffer, 0);
                if (!((convert32 & 1) == 1)) {
                    z2 = readFully(this.blockBuffer, 0, this.blockSize);
                } else {
                    int i = (convert32 >> 4) & 268435455;
                    byte[] bArr = new byte[i];
                    boolean readFully = readFully(bArr, 0, i);
                    this.bytesRead += (long) i;
                    if (!z) {
                        Arrays.fill(this.blockBuffer, (byte) 0);
                    } else {
                        switch (C09021.f140x24f7e417[DumpArchiveConstants.COMPRESSION_TYPE.find((convert32 >> 1) & 7 & 3).ordinal()]) {
                            case 1:
                                Inflater inflater = new Inflater();
                                try {
                                    inflater.setInput(bArr, 0, i);
                                    if (inflater.inflate(this.blockBuffer) == this.blockSize) {
                                        inflater.end();
                                        break;
                                    } else {
                                        throw new ShortFileException();
                                    }
                                } catch (DataFormatException e) {
                                    throw new DumpArchiveException("bad data", e);
                                } catch (Throwable th) {
                                    inflater.end();
                                    throw th;
                                }
                            case 2:
                                throw new UnsupportedCompressionAlgorithmException("BZLIB2");
                            case 3:
                                throw new UnsupportedCompressionAlgorithmException("LZO");
                            default:
                                throw new UnsupportedCompressionAlgorithmException();
                        }
                    }
                    z2 = readFully;
                    this.currBlkIdx++;
                    this.readOffset = 0;
                    return z2;
                }
            }
            this.bytesRead += (long) this.blockSize;
            this.currBlkIdx++;
            this.readOffset = 0;
            return z2;
        }
        throw new IOException("input buffer is closed");
    }

    private boolean readFully(byte[] bArr, int i, int i2) throws IOException {
        if (IOUtils.readFully(this.in, bArr, i, i2) >= i2) {
            return true;
        }
        throw new ShortFileException();
    }

    public int available() throws IOException {
        int i = this.readOffset;
        int i2 = this.blockSize;
        return i < i2 ? i2 - i : this.in.available();
    }

    public void close() throws IOException {
        if (this.in != null && this.in != System.in) {
            this.in.close();
        }
    }

    public long getBytesRead() {
        return this.bytesRead;
    }

    public byte[] peek() throws IOException {
        if (this.readOffset == this.blockSize && !readBlock(true)) {
            return null;
        }
        byte[] bArr = new byte[1024];
        System.arraycopy(this.blockBuffer, this.readOffset, bArr, 0, 1024);
        return bArr;
    }

    public int read() throws IOException {
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 % 1024 == 0) {
            int i3 = 0;
            while (i3 < i2) {
                if (this.readOffset == this.blockSize && !readBlock(true)) {
                    return -1;
                }
                int i4 = this.readOffset;
                int i5 = i2 - i3;
                int i6 = i4 + i5;
                int i7 = this.blockSize;
                if (i6 > i7) {
                    i5 = i7 - i4;
                }
                System.arraycopy(this.blockBuffer, i4, bArr, i, i5);
                this.readOffset += i5;
                i3 += i5;
                i += i5;
            }
            return i3;
        }
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }

    public byte[] readRecord() throws IOException {
        byte[] bArr = new byte[1024];
        if (-1 != read(bArr, 0, 1024)) {
            return bArr;
        }
        throw new ShortFileException();
    }

    public void resetBlockSize(int i, boolean z) throws IOException {
        this.isCompressed = z;
        int i2 = i * 1024;
        this.blockSize = i2;
        byte[] bArr = this.blockBuffer;
        byte[] bArr2 = new byte[i2];
        this.blockBuffer = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, 1024);
        readFully(this.blockBuffer, 1024, this.blockSize - 1024);
        this.currBlkIdx = 0;
        this.readOffset = 1024;
    }

    public long skip(long j) throws IOException {
        long j2 = 0;
        if (j % FileUtils.ONE_KB == 0) {
            while (j2 < j) {
                int i = this.readOffset;
                int i2 = this.blockSize;
                if (i == i2) {
                    if (!readBlock(j - j2 < ((long) i2))) {
                        return -1;
                    }
                }
                int i3 = this.readOffset;
                long j3 = j - j2;
                int i4 = this.blockSize;
                if (((long) i3) + j3 > ((long) i4)) {
                    j3 = (long) (i4 - i3);
                }
                this.readOffset = (int) (((long) i3) + j3);
                j2 += j3;
            }
            return j2;
        }
        throw new IllegalArgumentException("all reads must be multiple of record size (1024 bytes.");
    }
}
