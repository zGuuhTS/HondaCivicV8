package org.apache.commons.compress.compressors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMAUtils;
import org.apache.commons.compress.compressors.p010xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.p010xz.XZCompressorOutputStream;
import org.apache.commons.compress.compressors.p010xz.XZUtils;
import org.apache.commons.compress.compressors.p011z.ZCompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200CompressorOutputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class CompressorStreamFactory {
    public static final String BZIP2 = "bzip2";
    public static final String DEFLATE = "deflate";
    public static final String GZIP = "gz";
    public static final String LZMA = "lzma";
    public static final String PACK200 = "pack200";
    public static final String SNAPPY_FRAMED = "snappy-framed";
    public static final String SNAPPY_RAW = "snappy-raw";

    /* renamed from: XZ */
    public static final String f154XZ = "xz";

    /* renamed from: Z */
    public static final String f155Z = "z";
    private volatile boolean decompressConcatenated;
    private final Boolean decompressUntilEOF;

    public CompressorStreamFactory() {
        this.decompressConcatenated = false;
        this.decompressUntilEOF = null;
    }

    public CompressorStreamFactory(boolean z) {
        this.decompressConcatenated = false;
        this.decompressUntilEOF = Boolean.valueOf(z);
        this.decompressConcatenated = z;
    }

    public CompressorInputStream createCompressorInputStream(InputStream inputStream) throws CompressorException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Stream must not be null.");
        } else if (inputStream.markSupported()) {
            byte[] bArr = new byte[12];
            inputStream.mark(12);
            try {
                int readFully = IOUtils.readFully(inputStream, bArr);
                inputStream.reset();
                if (BZip2CompressorInputStream.matches(bArr, readFully)) {
                    return new BZip2CompressorInputStream(inputStream, this.decompressConcatenated);
                }
                if (GzipCompressorInputStream.matches(bArr, readFully)) {
                    return new GzipCompressorInputStream(inputStream, this.decompressConcatenated);
                }
                if (Pack200CompressorInputStream.matches(bArr, readFully)) {
                    return new Pack200CompressorInputStream(inputStream);
                }
                if (FramedSnappyCompressorInputStream.matches(bArr, readFully)) {
                    return new FramedSnappyCompressorInputStream(inputStream);
                }
                if (ZCompressorInputStream.matches(bArr, readFully)) {
                    return new ZCompressorInputStream(inputStream);
                }
                if (DeflateCompressorInputStream.matches(bArr, readFully)) {
                    return new DeflateCompressorInputStream(inputStream);
                }
                if (XZUtils.matches(bArr, readFully) && XZUtils.isXZCompressionAvailable()) {
                    return new XZCompressorInputStream(inputStream, this.decompressConcatenated);
                }
                if (LZMAUtils.matches(bArr, readFully) && LZMAUtils.isLZMACompressionAvailable()) {
                    return new LZMACompressorInputStream(inputStream);
                }
                throw new CompressorException("No Compressor found for the stream signature.");
            } catch (IOException e) {
                throw new CompressorException("Failed to detect Compressor from InputStream.", e);
            }
        } else {
            throw new IllegalArgumentException("Mark is not supported.");
        }
    }

    public CompressorInputStream createCompressorInputStream(String str, InputStream inputStream) throws CompressorException {
        if (str == null || inputStream == null) {
            throw new IllegalArgumentException("Compressor name and stream must not be null.");
        }
        try {
            if (GZIP.equalsIgnoreCase(str)) {
                return new GzipCompressorInputStream(inputStream, this.decompressConcatenated);
            }
            if (BZIP2.equalsIgnoreCase(str)) {
                return new BZip2CompressorInputStream(inputStream, this.decompressConcatenated);
            }
            if (f154XZ.equalsIgnoreCase(str)) {
                return new XZCompressorInputStream(inputStream, this.decompressConcatenated);
            }
            if (LZMA.equalsIgnoreCase(str)) {
                return new LZMACompressorInputStream(inputStream);
            }
            if (PACK200.equalsIgnoreCase(str)) {
                return new Pack200CompressorInputStream(inputStream);
            }
            if (SNAPPY_RAW.equalsIgnoreCase(str)) {
                return new SnappyCompressorInputStream(inputStream);
            }
            if (SNAPPY_FRAMED.equalsIgnoreCase(str)) {
                return new FramedSnappyCompressorInputStream(inputStream);
            }
            if (f155Z.equalsIgnoreCase(str)) {
                return new ZCompressorInputStream(inputStream);
            }
            if (DEFLATE.equalsIgnoreCase(str)) {
                return new DeflateCompressorInputStream(inputStream);
            }
            throw new CompressorException("Compressor: " + str + " not found.");
        } catch (IOException e) {
            throw new CompressorException("Could not create CompressorInputStream.", e);
        }
    }

    public CompressorOutputStream createCompressorOutputStream(String str, OutputStream outputStream) throws CompressorException {
        if (str == null || outputStream == null) {
            throw new IllegalArgumentException("Compressor name and stream must not be null.");
        }
        try {
            if (GZIP.equalsIgnoreCase(str)) {
                return new GzipCompressorOutputStream(outputStream);
            }
            if (BZIP2.equalsIgnoreCase(str)) {
                return new BZip2CompressorOutputStream(outputStream);
            }
            if (f154XZ.equalsIgnoreCase(str)) {
                return new XZCompressorOutputStream(outputStream);
            }
            if (PACK200.equalsIgnoreCase(str)) {
                return new Pack200CompressorOutputStream(outputStream);
            }
            if (DEFLATE.equalsIgnoreCase(str)) {
                return new DeflateCompressorOutputStream(outputStream);
            }
            throw new CompressorException("Compressor: " + str + " not found.");
        } catch (IOException e) {
            throw new CompressorException("Could not create CompressorOutputStream", e);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean getDecompressConcatenated() {
        return this.decompressConcatenated;
    }

    @Deprecated
    public void setDecompressConcatenated(boolean z) {
        if (this.decompressUntilEOF == null) {
            this.decompressConcatenated = z;
            return;
        }
        throw new IllegalStateException("Cannot override the setting defined by the constructor");
    }
}
