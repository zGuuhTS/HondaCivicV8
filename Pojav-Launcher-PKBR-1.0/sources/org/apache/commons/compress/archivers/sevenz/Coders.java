package org.apache.commons.compress.archivers.sevenz;

import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.tukaani.p013xz.ARMOptions;
import org.tukaani.p013xz.ARMThumbOptions;
import org.tukaani.p013xz.FilterOptions;
import org.tukaani.p013xz.FinishableWrapperOutputStream;
import org.tukaani.p013xz.IA64Options;
import org.tukaani.p013xz.LZMAInputStream;
import org.tukaani.p013xz.PowerPCOptions;
import org.tukaani.p013xz.SPARCOptions;
import org.tukaani.p013xz.X86Options;

class Coders {
    private static final Map<SevenZMethod, CoderBase> CODER_MAP = new HashMap<SevenZMethod, CoderBase>() {
        private static final long serialVersionUID = 1664829131806520867L;

        {
            put(SevenZMethod.COPY, new CopyDecoder());
            put(SevenZMethod.LZMA, new LZMADecoder());
            put(SevenZMethod.LZMA2, new LZMA2Decoder());
            put(SevenZMethod.DEFLATE, new DeflateDecoder());
            put(SevenZMethod.BZIP2, new BZIP2Decoder());
            put(SevenZMethod.AES256SHA256, new AES256SHA256Decoder());
            put(SevenZMethod.BCJ_X86_FILTER, new BCJDecoder(new X86Options()));
            put(SevenZMethod.BCJ_PPC_FILTER, new BCJDecoder(new PowerPCOptions()));
            put(SevenZMethod.BCJ_IA64_FILTER, new BCJDecoder(new IA64Options()));
            put(SevenZMethod.BCJ_ARM_FILTER, new BCJDecoder(new ARMOptions()));
            put(SevenZMethod.BCJ_ARM_THUMB_FILTER, new BCJDecoder(new ARMThumbOptions()));
            put(SevenZMethod.BCJ_SPARC_FILTER, new BCJDecoder(new SPARCOptions()));
            put(SevenZMethod.DELTA_FILTER, new DeltaDecoder());
        }
    };

    static class BCJDecoder extends CoderBase {
        private final FilterOptions opts;

        BCJDecoder(FilterOptions filterOptions) {
            super(new Class[0]);
            this.opts = filterOptions;
        }

        /* access modifiers changed from: package-private */
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            try {
                return this.opts.getInputStream(inputStream);
            } catch (AssertionError e) {
                throw new IOException("BCJ filter used in " + str + " needs XZ for Java > 1.4 - see " + "http://commons.apache.org/proper/commons-compress/limitations.html#7Z", e);
            }
        }

        /* access modifiers changed from: package-private */
        public OutputStream encode(OutputStream outputStream, Object obj) {
            return new FilterOutputStream(this.opts.getOutputStream(new FinishableWrapperOutputStream(outputStream))) {
                public void flush() {
                }
            };
        }
    }

    static class BZIP2Decoder extends CoderBase {
        BZIP2Decoder() {
            super(Number.class);
        }

        /* access modifiers changed from: package-private */
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            return new BZip2CompressorInputStream(inputStream);
        }

        /* access modifiers changed from: package-private */
        public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
            return new BZip2CompressorOutputStream(outputStream, numberOptionOrDefault(obj, 9));
        }
    }

    static class CopyDecoder extends CoderBase {
        CopyDecoder() {
            super(new Class[0]);
        }

        /* access modifiers changed from: package-private */
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            return inputStream;
        }

        /* access modifiers changed from: package-private */
        public OutputStream encode(OutputStream outputStream, Object obj) {
            return outputStream;
        }
    }

    static class DeflateDecoder extends CoderBase {
        DeflateDecoder() {
            super(Number.class);
        }

        /* access modifiers changed from: package-private */
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            final Inflater inflater = new Inflater(true);
            final InflaterInputStream inflaterInputStream = new InflaterInputStream(new DummyByteAddingInputStream(inputStream), inflater);
            return new InputStream() {
                public void close() throws IOException {
                    try {
                        inflaterInputStream.close();
                    } finally {
                        inflater.end();
                    }
                }

                public int read() throws IOException {
                    return inflaterInputStream.read();
                }

                public int read(byte[] bArr) throws IOException {
                    return inflaterInputStream.read(bArr);
                }

                public int read(byte[] bArr, int i, int i2) throws IOException {
                    return inflaterInputStream.read(bArr, i, i2);
                }
            };
        }

        /* access modifiers changed from: package-private */
        public OutputStream encode(OutputStream outputStream, Object obj) {
            final Deflater deflater = new Deflater(numberOptionOrDefault(obj, 9), true);
            final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(outputStream, deflater);
            return new OutputStream() {
                public void close() throws IOException {
                    try {
                        deflaterOutputStream.close();
                    } finally {
                        deflater.end();
                    }
                }

                public void write(int i) throws IOException {
                    deflaterOutputStream.write(i);
                }

                public void write(byte[] bArr) throws IOException {
                    deflaterOutputStream.write(bArr);
                }

                public void write(byte[] bArr, int i, int i2) throws IOException {
                    deflaterOutputStream.write(bArr, i, i2);
                }
            };
        }
    }

    private static class DummyByteAddingInputStream extends FilterInputStream {
        private boolean addDummyByte;

        private DummyByteAddingInputStream(InputStream inputStream) {
            super(inputStream);
            this.addDummyByte = true;
        }

        public int read() throws IOException {
            int read = super.read();
            if (read != -1 || !this.addDummyByte) {
                return read;
            }
            this.addDummyByte = false;
            return 0;
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = super.read(bArr, i, i2);
            if (read != -1 || !this.addDummyByte) {
                return read;
            }
            this.addDummyByte = false;
            bArr[i] = 0;
            return 1;
        }
    }

    static class LZMADecoder extends CoderBase {
        LZMADecoder() {
            super(new Class[0]);
        }

        /* access modifiers changed from: package-private */
        public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
            Coder coder2 = coder;
            byte b = coder2.properties[0];
            int i = 1;
            long j2 = (long) coder2.properties[1];
            while (i < 4) {
                int i2 = i + 1;
                j2 |= (((long) coder2.properties[i2]) & 255) << (i * 8);
                i = i2;
            }
            if (j2 <= 2147483632) {
                return new LZMAInputStream(inputStream, j, b, (int) j2);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Dictionary larger than 4GiB maximum size used in ");
            String str2 = str;
            sb.append(str);
            throw new IOException(sb.toString());
        }
    }

    Coders() {
    }

    static InputStream addDecoder(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
        CoderBase findByMethod = findByMethod(SevenZMethod.byId(coder.decompressionMethodId));
        if (findByMethod != null) {
            return findByMethod.decode(str, inputStream, j, coder, bArr);
        }
        throw new IOException("Unsupported compression method " + Arrays.toString(coder.decompressionMethodId) + " used in " + str);
    }

    static OutputStream addEncoder(OutputStream outputStream, SevenZMethod sevenZMethod, Object obj) throws IOException {
        CoderBase findByMethod = findByMethod(sevenZMethod);
        if (findByMethod != null) {
            return findByMethod.encode(outputStream, obj);
        }
        throw new IOException("Unsupported compression method " + sevenZMethod);
    }

    static CoderBase findByMethod(SevenZMethod sevenZMethod) {
        return CODER_MAP.get(sevenZMethod);
    }
}
