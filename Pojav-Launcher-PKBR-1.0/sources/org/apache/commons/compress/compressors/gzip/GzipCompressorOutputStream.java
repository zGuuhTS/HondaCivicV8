package org.apache.commons.compress.compressors.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import org.apache.commons.compress.compressors.CompressorOutputStream;

public class GzipCompressorOutputStream extends CompressorOutputStream {
    private static final int FCOMMENT = 16;
    private static final int FNAME = 8;
    private boolean closed;
    private final CRC32 crc;
    private final byte[] deflateBuffer;
    private final Deflater deflater;
    private final OutputStream out;

    public GzipCompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, new GzipParameters());
    }

    public GzipCompressorOutputStream(OutputStream outputStream, GzipParameters gzipParameters) throws IOException {
        this.deflateBuffer = new byte[512];
        this.crc = new CRC32();
        this.out = outputStream;
        this.deflater = new Deflater(gzipParameters.getCompressionLevel(), true);
        writeHeader(gzipParameters);
    }

    private void deflate() throws IOException {
        Deflater deflater2 = this.deflater;
        byte[] bArr = this.deflateBuffer;
        int deflate = deflater2.deflate(bArr, 0, bArr.length);
        if (deflate > 0) {
            this.out.write(this.deflateBuffer, 0, deflate);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void writeHeader(org.apache.commons.compress.compressors.gzip.GzipParameters r10) throws java.io.IOException {
        /*
            r9 = this;
            java.lang.String r0 = r10.getFilename()
            java.lang.String r1 = r10.getComment()
            r2 = 10
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocate(r2)
            java.nio.ByteOrder r3 = java.nio.ByteOrder.LITTLE_ENDIAN
            r2.order(r3)
            r3 = -29921(0xffffffffffff8b1f, float:NaN)
            r2.putShort(r3)
            r3 = 8
            r2.put(r3)
            r4 = 0
            if (r0 == 0) goto L_0x0021
            goto L_0x0022
        L_0x0021:
            r3 = r4
        L_0x0022:
            if (r1 == 0) goto L_0x0027
            r5 = 16
            goto L_0x0028
        L_0x0027:
            r5 = r4
        L_0x0028:
            r3 = r3 | r5
            byte r3 = (byte) r3
            r2.put(r3)
            long r5 = r10.getModificationTime()
            r7 = 1000(0x3e8, double:4.94E-321)
            long r5 = r5 / r7
            int r3 = (int) r5
            r2.putInt(r3)
            int r3 = r10.getCompressionLevel()
            r5 = 9
            if (r3 != r5) goto L_0x0045
            r3 = 2
        L_0x0041:
            r2.put(r3)
            goto L_0x004d
        L_0x0045:
            r5 = 1
            if (r3 != r5) goto L_0x004a
            r3 = 4
            goto L_0x0041
        L_0x004a:
            r2.put(r4)
        L_0x004d:
            int r10 = r10.getOperatingSystem()
            byte r10 = (byte) r10
            r2.put(r10)
            java.io.OutputStream r10 = r9.out
            byte[] r2 = r2.array()
            r10.write(r2)
            java.lang.String r10 = "ISO-8859-1"
            if (r0 == 0) goto L_0x0070
            java.io.OutputStream r2 = r9.out
            byte[] r0 = r0.getBytes(r10)
            r2.write(r0)
            java.io.OutputStream r0 = r9.out
            r0.write(r4)
        L_0x0070:
            if (r1 == 0) goto L_0x0080
            java.io.OutputStream r0 = r9.out
            byte[] r10 = r1.getBytes(r10)
            r0.write(r10)
            java.io.OutputStream r10 = r9.out
            r10.write(r4)
        L_0x0080:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream.writeHeader(org.apache.commons.compress.compressors.gzip.GzipParameters):void");
    }

    private void writeTrailer() throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putInt((int) this.crc.getValue());
        allocate.putInt(this.deflater.getTotalIn());
        this.out.write(allocate.array());
    }

    public void close() throws IOException {
        if (!this.closed) {
            finish();
            this.deflater.end();
            this.out.close();
            this.closed = true;
        }
    }

    public void finish() throws IOException {
        if (!this.deflater.finished()) {
            this.deflater.finish();
            while (!this.deflater.finished()) {
                deflate();
            }
            writeTrailer();
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void write(int i) throws IOException {
        write(new byte[]{(byte) (i & 255)}, 0, 1);
    }

    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        if (this.deflater.finished()) {
            throw new IOException("Cannot write more data, the end of the compressed data stream has been reached");
        } else if (i2 > 0) {
            this.deflater.setInput(bArr, i, i2);
            while (!this.deflater.needsInput()) {
                deflate();
            }
            this.crc.update(bArr, i, i2);
        }
    }
}
