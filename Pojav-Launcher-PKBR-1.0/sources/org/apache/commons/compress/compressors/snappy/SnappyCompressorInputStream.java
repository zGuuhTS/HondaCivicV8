package org.apache.commons.compress.compressors.snappy;

import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import net.kdt.pojavlaunch.AWTInputEvent;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class SnappyCompressorInputStream extends CompressorInputStream {
    public static final int DEFAULT_BLOCK_SIZE = 32768;
    private static final int TAG_MASK = 3;
    private final int blockSize;
    private final byte[] decompressBuf;
    private boolean endReached;

    /* renamed from: in */
    private final InputStream f165in;
    private final byte[] oneByte;
    private int readIndex;
    private final int size;
    private int uncompressedBytesRemaining;
    private int writeIndex;

    public SnappyCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, 32768);
    }

    public SnappyCompressorInputStream(InputStream inputStream, int i) throws IOException {
        this.oneByte = new byte[1];
        this.endReached = false;
        this.f165in = inputStream;
        this.blockSize = i;
        this.decompressBuf = new byte[(i * 3)];
        this.readIndex = 0;
        this.writeIndex = 0;
        int readSize = (int) readSize();
        this.size = readSize;
        this.uncompressedBytesRemaining = readSize;
    }

    private boolean expandCopy(long j, int i) throws IOException {
        if (j <= ((long) this.blockSize)) {
            int i2 = (int) j;
            if (i2 == 1) {
                byte b = this.decompressBuf[this.writeIndex - 1];
                for (int i3 = 0; i3 < i; i3++) {
                    byte[] bArr = this.decompressBuf;
                    int i4 = this.writeIndex;
                    this.writeIndex = i4 + 1;
                    bArr[i4] = b;
                }
            } else {
                if (i < i2) {
                    byte[] bArr2 = this.decompressBuf;
                    int i5 = this.writeIndex;
                    System.arraycopy(bArr2, i5 - i2, bArr2, i5, i);
                } else {
                    int i6 = i / i2;
                    i -= i2 * i6;
                    while (i6 != 0) {
                        byte[] bArr3 = this.decompressBuf;
                        int i7 = this.writeIndex;
                        System.arraycopy(bArr3, i7 - i2, bArr3, i7, i2);
                        this.writeIndex += i2;
                        i6--;
                    }
                    if (i > 0) {
                        byte[] bArr4 = this.decompressBuf;
                        int i8 = this.writeIndex;
                        System.arraycopy(bArr4, i8 - i2, bArr4, i8, i);
                    }
                }
                this.writeIndex += i;
            }
            return this.writeIndex >= this.blockSize * 2;
        }
        throw new IOException("Offset is larger than block size");
    }

    private boolean expandLiteral(int i) throws IOException {
        int readFully = IOUtils.readFully(this.f165in, this.decompressBuf, this.writeIndex, i);
        count(readFully);
        if (i == readFully) {
            int i2 = this.writeIndex + i;
            this.writeIndex = i2;
            return i2 >= this.blockSize * 2;
        }
        throw new IOException("Premature end of stream");
    }

    private void fill(int i) throws IOException {
        int i2 = this.uncompressedBytesRemaining;
        if (i2 == 0) {
            this.endReached = true;
        }
        int min = Math.min(i, i2);
        while (min > 0) {
            int readOneByte = readOneByte();
            int i3 = 0;
            switch (readOneByte & 3) {
                case 0:
                    i3 = readLiteralLength(readOneByte);
                    if (!expandLiteral(i3)) {
                        break;
                    } else {
                        return;
                    }
                case 1:
                    i3 = ((readOneByte >> 2) & 7) + 4;
                    if (!expandCopy(((long) ((readOneByte & AWTInputEvent.VK_KP_UP) << 3)) | ((long) readOneByte()), i3)) {
                        break;
                    } else {
                        return;
                    }
                case 2:
                    i3 = (readOneByte >> 2) + 1;
                    if (!expandCopy(((long) readOneByte()) | ((long) (readOneByte() << 8)), i3)) {
                        break;
                    } else {
                        return;
                    }
                case 3:
                    i3 = (readOneByte >> 2) + 1;
                    if (!expandCopy(((long) readOneByte()) | ((long) (readOneByte() << 8)) | ((long) (readOneByte() << 16)) | (((long) readOneByte()) << 24), i3)) {
                        break;
                    } else {
                        return;
                    }
            }
            min -= i3;
            this.uncompressedBytesRemaining -= i3;
        }
    }

    private int readLiteralLength(int i) throws IOException {
        int i2;
        int i3;
        int i4 = i >> 2;
        switch (i4) {
            case 60:
                i4 = readOneByte();
                break;
            case 61:
                i2 = readOneByte();
                i3 = readOneByte() << 8;
                break;
            case 62:
                i2 = readOneByte() | (readOneByte() << 8);
                i3 = readOneByte() << 16;
                break;
            case 63:
                i4 = (int) (((long) (readOneByte() | (readOneByte() << 8) | (readOneByte() << 16))) | (((long) readOneByte()) << 24));
                break;
        }
        i4 = i2 | i3;
        return i4 + 1;
    }

    private int readOneByte() throws IOException {
        int read = this.f165in.read();
        if (read != -1) {
            count(1);
            return read & 255;
        }
        throw new IOException("Premature end of stream");
    }

    private long readSize() throws IOException {
        int i = 0;
        long j = 0;
        while (true) {
            int readOneByte = readOneByte();
            j |= (long) ((readOneByte & 127) << (i * 7));
            if ((readOneByte & 128) == 0) {
                return j;
            }
            i++;
        }
    }

    private void slideBuffer() {
        byte[] bArr = this.decompressBuf;
        int i = this.blockSize;
        System.arraycopy(bArr, i, bArr, 0, i * 2);
        int i2 = this.writeIndex;
        int i3 = this.blockSize;
        this.writeIndex = i2 - i3;
        this.readIndex -= i3;
    }

    public int available() {
        return this.writeIndex - this.readIndex;
    }

    public void close() throws IOException {
        this.f165in.close();
    }

    public int getSize() {
        return this.size;
    }

    public int read() throws IOException {
        if (read(this.oneByte, 0, 1) == -1) {
            return -1;
        }
        return this.oneByte[0] & UByte.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.endReached) {
            return -1;
        }
        int available = available();
        if (i2 > available) {
            fill(i2 - available);
        }
        int min = Math.min(i2, available());
        if (min == 0 && i2 > 0) {
            return -1;
        }
        System.arraycopy(this.decompressBuf, this.readIndex, bArr, i, min);
        int i3 = this.readIndex + min;
        this.readIndex = i3;
        if (i3 > this.blockSize) {
            slideBuffer();
        }
        return min;
    }
}
