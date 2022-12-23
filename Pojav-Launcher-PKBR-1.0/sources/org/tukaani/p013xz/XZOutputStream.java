package org.tukaani.p013xz;

import java.io.IOException;
import java.io.OutputStream;
import org.tukaani.p013xz.check.Check;
import org.tukaani.p013xz.common.EncoderUtil;
import org.tukaani.p013xz.common.StreamFlags;
import org.tukaani.p013xz.index.IndexEncoder;

/* renamed from: org.tukaani.xz.XZOutputStream */
public class XZOutputStream extends FinishableOutputStream {
    private final ArrayCache arrayCache;
    private BlockOutputStream blockEncoder;
    private final Check check;
    private IOException exception;
    private FilterEncoder[] filters;
    private boolean filtersSupportFlushing;
    private boolean finished;
    private final IndexEncoder index;
    private OutputStream out;
    private final StreamFlags streamFlags;
    private final byte[] tempBuf;

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions) throws IOException {
        this(outputStream, filterOptions, 4);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions, int i) throws IOException {
        this(outputStream, new FilterOptions[]{filterOptions}, i);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions, int i, ArrayCache arrayCache2) throws IOException {
        this(outputStream, new FilterOptions[]{filterOptions}, i, arrayCache2);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions filterOptions, ArrayCache arrayCache2) throws IOException {
        this(outputStream, filterOptions, 4, arrayCache2);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr) throws IOException {
        this(outputStream, filterOptionsArr, 4);
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr, int i) throws IOException {
        this(outputStream, filterOptionsArr, i, ArrayCache.getDefaultCache());
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr, int i, ArrayCache arrayCache2) throws IOException {
        StreamFlags streamFlags2 = new StreamFlags();
        this.streamFlags = streamFlags2;
        this.index = new IndexEncoder();
        this.blockEncoder = null;
        this.exception = null;
        this.finished = false;
        this.tempBuf = new byte[1];
        this.arrayCache = arrayCache2;
        this.out = outputStream;
        updateFilters(filterOptionsArr);
        streamFlags2.checkType = i;
        this.check = Check.getInstance(i);
        encodeStreamHeader();
    }

    public XZOutputStream(OutputStream outputStream, FilterOptions[] filterOptionsArr, ArrayCache arrayCache2) throws IOException {
        this(outputStream, filterOptionsArr, 4, arrayCache2);
    }

    private void encodeStreamFlags(byte[] bArr, int i) {
        bArr[i] = 0;
        bArr[i + 1] = (byte) this.streamFlags.checkType;
    }

    private void encodeStreamFooter() throws IOException {
        byte[] bArr = new byte[6];
        long indexSize = (this.index.getIndexSize() / 4) - 1;
        for (int i = 0; i < 4; i++) {
            bArr[i] = (byte) ((int) (indexSize >>> (i * 8)));
        }
        encodeStreamFlags(bArr, 4);
        EncoderUtil.writeCRC32(this.out, bArr);
        this.out.write(bArr);
        this.out.write(C0927XZ.FOOTER_MAGIC);
    }

    private void encodeStreamHeader() throws IOException {
        this.out.write(C0927XZ.HEADER_MAGIC);
        byte[] bArr = new byte[2];
        encodeStreamFlags(bArr, 0);
        this.out.write(bArr);
        EncoderUtil.writeCRC32(this.out, bArr);
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

    public void endBlock() throws IOException {
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        } else if (!this.finished) {
            BlockOutputStream blockOutputStream = this.blockEncoder;
            if (blockOutputStream != null) {
                try {
                    blockOutputStream.finish();
                    this.index.add(this.blockEncoder.getUnpaddedSize(), this.blockEncoder.getUncompressedSize());
                    this.blockEncoder = null;
                } catch (IOException e) {
                    this.exception = e;
                    throw e;
                }
            }
        } else {
            throw new XZIOException("Stream finished or closed");
        }
    }

    public void finish() throws IOException {
        if (!this.finished) {
            endBlock();
            try {
                this.index.encode(this.out);
                encodeStreamFooter();
                this.finished = true;
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        }
    }

    public void flush() throws IOException {
        OutputStream outputStream;
        IOException iOException = this.exception;
        if (iOException != null) {
            throw iOException;
        } else if (!this.finished) {
            try {
                BlockOutputStream blockOutputStream = this.blockEncoder;
                if (blockOutputStream == null) {
                    outputStream = this.out;
                } else if (this.filtersSupportFlushing) {
                    blockOutputStream.flush();
                    return;
                } else {
                    endBlock();
                    outputStream = this.out;
                }
                outputStream.flush();
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        } else {
            throw new XZIOException("Stream finished or closed");
        }
    }

    public void updateFilters(FilterOptions filterOptions) throws XZIOException {
        updateFilters(new FilterOptions[]{filterOptions});
    }

    public void updateFilters(FilterOptions[] filterOptionsArr) throws XZIOException {
        if (this.blockEncoder != null) {
            throw new UnsupportedOptionsException("Changing filter options in the middle of a XZ Block not implemented");
        } else if (filterOptionsArr.length < 1 || filterOptionsArr.length > 4) {
            throw new UnsupportedOptionsException("XZ filter chain must be 1-4 filters");
        } else {
            this.filtersSupportFlushing = true;
            FilterEncoder[] filterEncoderArr = new FilterEncoder[filterOptionsArr.length];
            for (int i = 0; i < filterOptionsArr.length; i++) {
                filterEncoderArr[i] = filterOptionsArr[i].getFilterEncoder();
                this.filtersSupportFlushing &= filterEncoderArr[i].supportsFlushing();
            }
            RawCoder.validate(filterEncoderArr);
            this.filters = filterEncoderArr;
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
            try {
                if (this.blockEncoder == null) {
                    this.blockEncoder = new BlockOutputStream(this.out, this.filters, this.check, this.arrayCache);
                }
                this.blockEncoder.write(bArr, i, i2);
            } catch (IOException e) {
                this.exception = e;
                throw e;
            }
        } else {
            throw new XZIOException("Stream finished or closed");
        }
    }
}
