package org.tukaani.p013xz;

import java.io.DataOutputStream;
import java.io.IOException;
import net.kdt.pojavlaunch.AWTInputEvent;
import org.tukaani.p013xz.lzma.LZMAEncoder;
import org.tukaani.p013xz.p014lz.LZEncoder;
import org.tukaani.p013xz.rangecoder.RangeEncoderToBuffer;

/* renamed from: org.tukaani.xz.LZMA2OutputStream */
class LZMA2OutputStream extends FinishableOutputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int COMPRESSED_SIZE_MAX = 65536;
    private final ArrayCache arrayCache;
    private boolean dictResetNeeded = true;
    private IOException exception = null;
    private boolean finished = false;

    /* renamed from: lz */
    private LZEncoder f184lz;
    private LZMAEncoder lzma;
    private FinishableOutputStream out;
    private final DataOutputStream outData;
    private int pendingSize = 0;
    private final int props;
    private boolean propsNeeded = true;

    /* renamed from: rc */
    private RangeEncoderToBuffer f185rc;
    private boolean stateResetNeeded = true;
    private final byte[] tempBuf = new byte[1];

    LZMA2OutputStream(FinishableOutputStream finishableOutputStream, LZMA2Options lZMA2Options, ArrayCache arrayCache2) {
        FinishableOutputStream finishableOutputStream2 = finishableOutputStream;
        ArrayCache arrayCache3 = arrayCache2;
        if (finishableOutputStream2 != null) {
            this.arrayCache = arrayCache3;
            this.out = finishableOutputStream2;
            this.outData = new DataOutputStream(finishableOutputStream);
            this.f185rc = new RangeEncoderToBuffer(65536, arrayCache3);
            int dictSize = lZMA2Options.getDictSize();
            int i = dictSize;
            LZMAEncoder instance = LZMAEncoder.getInstance(this.f185rc, lZMA2Options.getLc(), lZMA2Options.getLp(), lZMA2Options.getPb(), lZMA2Options.getMode(), i, getExtraSizeBefore(dictSize), lZMA2Options.getNiceLen(), lZMA2Options.getMatchFinder(), lZMA2Options.getDepthLimit(), arrayCache2);
            this.lzma = instance;
            this.f184lz = instance.getLZEncoder();
            byte[] presetDict = lZMA2Options.getPresetDict();
            if (presetDict != null && presetDict.length > 0) {
                this.f184lz.setPresetDict(dictSize, presetDict);
                this.dictResetNeeded = false;
            }
            this.props = (((lZMA2Options.getPb() * 5) + lZMA2Options.getLp()) * 9) + lZMA2Options.getLc();
            return;
        }
        throw new NullPointerException();
    }

    private static int getExtraSizeBefore(int i) {
        if (65536 > i) {
            return 65536 - i;
        }
        return 0;
    }

    static int getMemoryUsage(LZMA2Options lZMA2Options) {
        int dictSize = lZMA2Options.getDictSize();
        return LZMAEncoder.getMemoryUsage(lZMA2Options.getMode(), dictSize, getExtraSizeBefore(dictSize), lZMA2Options.getMatchFinder()) + 70;
    }

    private void writeChunk() throws IOException {
        int finish = this.f185rc.finish();
        int uncompressedSize = this.lzma.getUncompressedSize();
        if (finish <= 0) {
            throw new AssertionError(finish);
        } else if (uncompressedSize > 0) {
            if (finish + 2 < uncompressedSize) {
                writeLZMA(uncompressedSize, finish);
            } else {
                this.lzma.reset();
                uncompressedSize = this.lzma.getUncompressedSize();
                if (uncompressedSize > 0) {
                    writeUncompressed(uncompressedSize);
                } else {
                    throw new AssertionError(uncompressedSize);
                }
            }
            this.pendingSize -= uncompressedSize;
            this.lzma.resetUncompressedSize();
            this.f185rc.reset();
        } else {
            throw new AssertionError(uncompressedSize);
        }
    }

    private void writeEndMarker() throws IOException {
        if (!this.finished) {
            IOException iOException = this.exception;
            if (iOException == null) {
                this.f184lz.setFinishing();
                while (this.pendingSize > 0) {
                    try {
                        this.lzma.encodeForLZMA2();
                        writeChunk();
                    } catch (IOException e) {
                        this.exception = e;
                        throw e;
                    }
                }
                this.out.write(0);
                this.finished = true;
                this.lzma.putArraysToCache(this.arrayCache);
                this.lzma = null;
                this.f184lz = null;
                this.f185rc.putArraysToCache(this.arrayCache);
                this.f185rc = null;
                return;
            }
            throw iOException;
        }
        throw new AssertionError();
    }

    private void writeLZMA(int i, int i2) throws IOException {
        int i3 = i - 1;
        this.outData.writeByte((this.propsNeeded ? this.dictResetNeeded ? AWTInputEvent.VK_KP_UP : AWTInputEvent.VK_BACK_QUOTE : this.stateResetNeeded ? AWTInputEvent.VK_GREATER : 128) | (i3 >>> 16));
        this.outData.writeShort(i3);
        this.outData.writeShort(i2 - 1);
        if (this.propsNeeded) {
            this.outData.writeByte(this.props);
        }
        this.f185rc.write(this.out);
        this.propsNeeded = false;
        this.stateResetNeeded = false;
        this.dictResetNeeded = false;
    }

    private void writeUncompressed(int i) throws IOException {
        while (true) {
            int i2 = 1;
            if (i > 0) {
                int min = Math.min(i, 65536);
                DataOutputStream dataOutputStream = this.outData;
                if (!this.dictResetNeeded) {
                    i2 = 2;
                }
                dataOutputStream.writeByte(i2);
                this.outData.writeShort(min - 1);
                this.f184lz.copyUncompressed(this.out, i, min);
                i -= min;
                this.dictResetNeeded = false;
            } else {
                this.stateResetNeeded = true;
                return;
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            if (!this.finished) {
                try {
                    writeEndMarker();
                } catch (IOException e) {
                }
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
            writeEndMarker();
            try {
                this.out.finish();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        } else if (!this.finished) {
            try {
                this.f184lz.setFlushing();
                while (this.pendingSize > 0) {
                    this.lzma.encodeForLZMA2();
                    writeChunk();
                }
                this.out.flush();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        } else {
            throw new XZIOException("Stream finished or closed");
        }
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
            while (i2 > 0) {
                try {
                    int fillWindow = this.f184lz.fillWindow(bArr, i, i2);
                    i += fillWindow;
                    i2 -= fillWindow;
                    this.pendingSize += fillWindow;
                    if (this.lzma.encodeForLZMA2()) {
                        writeChunk();
                    }
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            }
        } else {
            throw new XZIOException("Stream finished or closed");
        }
    }
}
