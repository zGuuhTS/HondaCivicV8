package org.tukaani.p013xz;

import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.p013xz.lzma.LZMAEncoder;
import org.tukaani.p013xz.p014lz.LZEncoder;
import org.tukaani.p013xz.rangecoder.RangeEncoderToStream;

/* renamed from: org.tukaani.xz.LZMAOutputStream */
public class LZMAOutputStream extends FinishableOutputStream {
    private final ArrayCache arrayCache;
    private long currentUncompressedSize;
    private IOException exception;
    private final long expectedUncompressedSize;
    private boolean finished;

    /* renamed from: lz */
    private LZEncoder f189lz;
    private LZMAEncoder lzma;
    private OutputStream out;
    private final int props;

    /* renamed from: rc */
    private final RangeEncoderToStream f190rc;
    private final byte[] tempBuf;
    private final boolean useEndMarker;

    public LZMAOutputStream(OutputStream outputStream, LZMA2Options lZMA2Options, long j) throws IOException {
        this(outputStream, lZMA2Options, j, ArrayCache.getDefaultCache());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LZMAOutputStream(OutputStream outputStream, LZMA2Options lZMA2Options, long j, ArrayCache arrayCache2) throws IOException {
        this(outputStream, lZMA2Options, true, j == -1, j, arrayCache2);
    }

    public LZMAOutputStream(OutputStream outputStream, LZMA2Options lZMA2Options, boolean z) throws IOException {
        this(outputStream, lZMA2Options, z, ArrayCache.getDefaultCache());
    }

    public LZMAOutputStream(OutputStream outputStream, LZMA2Options lZMA2Options, boolean z, ArrayCache arrayCache2) throws IOException {
        this(outputStream, lZMA2Options, false, z, -1, arrayCache2);
    }

    private LZMAOutputStream(OutputStream outputStream, LZMA2Options lZMA2Options, boolean z, boolean z2, long j, ArrayCache arrayCache2) throws IOException {
        OutputStream outputStream2 = outputStream;
        long j2 = j;
        this.currentUncompressedSize = 0;
        this.finished = false;
        this.exception = null;
        this.tempBuf = new byte[1];
        if (outputStream2 == null) {
            throw new NullPointerException();
        } else if (j2 >= -1) {
            this.useEndMarker = z2;
            this.expectedUncompressedSize = j2;
            ArrayCache arrayCache3 = arrayCache2;
            this.arrayCache = arrayCache3;
            this.out = outputStream2;
            RangeEncoderToStream rangeEncoderToStream = new RangeEncoderToStream(outputStream2);
            this.f190rc = rangeEncoderToStream;
            int dictSize = lZMA2Options.getDictSize();
            int i = dictSize;
            LZMAEncoder instance = LZMAEncoder.getInstance(rangeEncoderToStream, lZMA2Options.getLc(), lZMA2Options.getLp(), lZMA2Options.getPb(), lZMA2Options.getMode(), dictSize, 0, lZMA2Options.getNiceLen(), lZMA2Options.getMatchFinder(), lZMA2Options.getDepthLimit(), arrayCache3);
            this.lzma = instance;
            this.f189lz = instance.getLZEncoder();
            byte[] presetDict = lZMA2Options.getPresetDict();
            if (presetDict != null && presetDict.length > 0) {
                if (!z) {
                    this.f189lz.setPresetDict(i, presetDict);
                } else {
                    throw new UnsupportedOptionsException("Preset dictionary cannot be used in .lzma files (try a raw LZMA stream instead)");
                }
            }
            int pb = (((lZMA2Options.getPb() * 5) + lZMA2Options.getLp()) * 9) + lZMA2Options.getLc();
            this.props = pb;
            if (z) {
                outputStream2.write(pb);
                int i2 = i;
                for (int i3 = 0; i3 < 4; i3++) {
                    outputStream2.write(i2 & 255);
                    i2 >>>= 8;
                }
                for (int i4 = 0; i4 < 8; i4++) {
                    outputStream2.write(((int) (j2 >>> (i4 * 8))) & 255);
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid expected input size (less than -1)");
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            try {
                finish();
            } catch (IOException e) {
            }
            try {
                this.out.close();
            } catch (IOException e2) {
                if (this.exception == null) {
                    this.exception = e2;
                }
            }
            this.out = null;
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            IOException iOException = this.exception;
            if (iOException == null) {
                try {
                    long j = this.expectedUncompressedSize;
                    if (j != -1) {
                        if (j != this.currentUncompressedSize) {
                            throw new XZIOException("Expected uncompressed size (" + this.expectedUncompressedSize + ") doesn't equal " + "the number of bytes written to the stream (" + this.currentUncompressedSize + ")");
                        }
                    }
                    this.f189lz.setFinishing();
                    this.lzma.encodeForLZMA1();
                    if (this.useEndMarker) {
                        this.lzma.encodeLZMA1EndMarker();
                    }
                    this.f190rc.finish();
                    this.finished = true;
                    this.lzma.putArraysToCache(this.arrayCache);
                    this.lzma = null;
                    this.f189lz = null;
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            } else {
                throw iOException;
            }
        }
    }

    public void flush() throws IOException {
        throw new XZIOException("LZMAOutputStream does not support flushing");
    }

    public int getProps() {
        return this.props;
    }

    public long getUncompressedSize() {
        return this.currentUncompressedSize;
    }

    public void write(int i) throws IOException {
        byte[] bArr = this.tempBuf;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        } else if (!this.finished) {
            long j = this.expectedUncompressedSize;
            if (j == -1 || j - this.currentUncompressedSize >= ((long) i2)) {
                this.currentUncompressedSize += (long) i2;
                while (i2 > 0) {
                    try {
                        int fillWindow = this.f189lz.fillWindow(bArr, i, i2);
                        i += fillWindow;
                        i2 -= fillWindow;
                        this.lzma.encodeForLZMA1();
                    } catch (IOException e) {
                        this.exception = e;
                        throw e;
                    }
                }
                return;
            }
            throw new XZIOException("Expected uncompressed input size (" + this.expectedUncompressedSize + " bytes) was exceeded");
        } else {
            throw new XZIOException("Stream finished or closed");
        }
    }
}
