package org.tukaani.p013xz;

import java.io.DataOutputStream;
import java.io.IOException;

/* renamed from: org.tukaani.xz.UncompressedLZMA2OutputStream */
class UncompressedLZMA2OutputStream extends FinishableOutputStream {
    private final ArrayCache arrayCache;
    private boolean dictResetNeeded = true;
    private IOException exception = null;
    private boolean finished = false;
    private FinishableOutputStream out;
    private final DataOutputStream outData;
    private final byte[] tempBuf = new byte[1];
    private final byte[] uncompBuf;
    private int uncompPos = 0;

    UncompressedLZMA2OutputStream(FinishableOutputStream finishableOutputStream, ArrayCache arrayCache2) {
        if (finishableOutputStream != null) {
            this.out = finishableOutputStream;
            this.outData = new DataOutputStream(finishableOutputStream);
            this.arrayCache = arrayCache2;
            this.uncompBuf = arrayCache2.getByteArray(65536, false);
            return;
        }
        throw new NullPointerException();
    }

    static int getMemoryUsage() {
        return 70;
    }

    private void writeChunk() throws IOException {
        this.outData.writeByte(this.dictResetNeeded ? 1 : 2);
        this.outData.writeShort(this.uncompPos - 1);
        this.outData.write(this.uncompBuf, 0, this.uncompPos);
        this.uncompPos = 0;
        this.dictResetNeeded = false;
    }

    private void writeEndMarker() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        } else if (!this.finished) {
            try {
                if (this.uncompPos > 0) {
                    writeChunk();
                }
                this.out.write(0);
                this.finished = true;
                this.arrayCache.putArray(this.uncompBuf);
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        } else {
            throw new XZIOException("Stream finished or closed");
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
                if (this.uncompPos > 0) {
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
                    int min = Math.min(65536 - this.uncompPos, i2);
                    System.arraycopy(bArr, i, this.uncompBuf, this.uncompPos, min);
                    i2 -= min;
                    int i4 = this.uncompPos + min;
                    this.uncompPos = i4;
                    if (i4 == 65536) {
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
