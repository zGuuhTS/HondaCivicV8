package org.apache.commons.p012io.filefilter;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.p012io.IOUtils;

/* renamed from: org.apache.commons.io.filefilter.MagicNumberFileFilter */
public class MagicNumberFileFilter extends AbstractFileFilter implements Serializable {
    private static final long serialVersionUID = -547733176983104172L;
    private final long byteOffset;
    private final byte[] magicNumbers;

    public MagicNumberFileFilter(String str) {
        this(str, 0);
    }

    public MagicNumberFileFilter(String str, long j) {
        if (str == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        } else if (str.length() == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        } else if (j >= 0) {
            this.magicNumbers = str.getBytes();
            this.byteOffset = j;
        } else {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
    }

    public MagicNumberFileFilter(byte[] bArr) {
        this(bArr, 0);
    }

    public MagicNumberFileFilter(byte[] bArr, long j) {
        if (bArr == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        } else if (bArr.length == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        } else if (j >= 0) {
            byte[] bArr2 = new byte[bArr.length];
            this.magicNumbers = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.byteOffset = j;
        } else {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
    }

    public boolean accept(File file) {
        if (file != null && file.isFile() && file.canRead()) {
            RandomAccessFile randomAccessFile = null;
            try {
                byte[] bArr = new byte[this.magicNumbers.length];
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                try {
                    randomAccessFile2.seek(this.byteOffset);
                    int read = randomAccessFile2.read(bArr);
                    byte[] bArr2 = this.magicNumbers;
                    if (read != bArr2.length) {
                        IOUtils.closeQuietly((Closeable) randomAccessFile2);
                        return false;
                    }
                    boolean equals = Arrays.equals(bArr2, bArr);
                    IOUtils.closeQuietly((Closeable) randomAccessFile2);
                    return equals;
                } catch (IOException e) {
                    randomAccessFile = randomAccessFile2;
                    IOUtils.closeQuietly((Closeable) randomAccessFile);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    randomAccessFile = randomAccessFile2;
                    IOUtils.closeQuietly((Closeable) randomAccessFile);
                    throw th;
                }
            } catch (IOException e2) {
                IOUtils.closeQuietly((Closeable) randomAccessFile);
                return false;
            } catch (Throwable th2) {
                th = th2;
                IOUtils.closeQuietly((Closeable) randomAccessFile);
                throw th;
            }
        }
        return false;
    }

    public String toString() {
        return super.toString() + "(" + new String(this.magicNumbers) + "," + this.byteOffset + ")";
    }
}
