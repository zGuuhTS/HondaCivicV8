package org.tukaani.p013xz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.p013xz.check.Check;
import org.tukaani.p013xz.common.EncoderUtil;

/* renamed from: org.tukaani.xz.BlockOutputStream */
class BlockOutputStream extends FinishableOutputStream {
    private final Check check;
    private final long compressedSizeLimit;
    private FinishableOutputStream filterChain;
    private final int headerSize;
    private final OutputStream out;
    private final CountingOutputStream outCounted;
    private final byte[] tempBuf = new byte[1];
    private long uncompressedSize = 0;

    public BlockOutputStream(OutputStream outputStream, FilterEncoder[] filterEncoderArr, Check check2, ArrayCache arrayCache) throws IOException {
        this.out = outputStream;
        this.check = check2;
        CountingOutputStream countingOutputStream = new CountingOutputStream(outputStream);
        this.outCounted = countingOutputStream;
        this.filterChain = countingOutputStream;
        for (int length = filterEncoderArr.length - 1; length >= 0; length--) {
            this.filterChain = filterEncoderArr[length].getOutputStream(this.filterChain, arrayCache);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(filterEncoderArr.length - 1);
        for (int i = 0; i < filterEncoderArr.length; i++) {
            EncoderUtil.encodeVLI(byteArrayOutputStream, filterEncoderArr[i].getFilterID());
            byte[] filterProps = filterEncoderArr[i].getFilterProps();
            EncoderUtil.encodeVLI(byteArrayOutputStream, (long) filterProps.length);
            byteArrayOutputStream.write(filterProps);
        }
        while ((byteArrayOutputStream.size() & 3) != 0) {
            byteArrayOutputStream.write(0);
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int length2 = byteArray.length + 4;
        this.headerSize = length2;
        if (length2 <= 1024) {
            byteArray[0] = (byte) (byteArray.length / 4);
            outputStream.write(byteArray);
            EncoderUtil.writeCRC32(outputStream, byteArray);
            this.compressedSizeLimit = (9223372036854775804L - ((long) length2)) - ((long) check2.getSize());
            return;
        }
        throw new UnsupportedOptionsException();
    }

    private void validate() throws IOException {
        long size = this.outCounted.getSize();
        if (size < 0 || size > this.compressedSizeLimit || this.uncompressedSize < 0) {
            throw new XZIOException("XZ Stream has grown too big");
        }
    }

    public void finish() throws IOException {
        this.filterChain.finish();
        validate();
        for (long size = this.outCounted.getSize(); (3 & size) != 0; size++) {
            this.out.write(0);
        }
        this.out.write(this.check.finish());
    }

    public void flush() throws IOException {
        this.filterChain.flush();
        validate();
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getUnpaddedSize() {
        return ((long) this.headerSize) + this.outCounted.getSize() + ((long) this.check.getSize());
    }

    public void write(int i) throws IOException {
        byte[] bArr = this.tempBuf;
        bArr[0] = (byte) i;
        write(bArr, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.filterChain.write(bArr, i, i2);
        this.check.update(bArr, i, i2);
        this.uncompressedSize += (long) i2;
        validate();
    }
}
