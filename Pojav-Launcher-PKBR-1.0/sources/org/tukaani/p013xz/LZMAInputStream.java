package org.tukaani.p013xz;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;
import org.tukaani.p013xz.lzma.LZMADecoder;
import org.tukaani.p013xz.p014lz.LZDecoder;
import org.tukaani.p013xz.rangecoder.RangeDecoderFromStream;

/* renamed from: org.tukaani.xz.LZMAInputStream */
public class LZMAInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int DICT_SIZE_MAX = 2147483632;
    private ArrayCache arrayCache;
    private boolean endReached;
    private IOException exception;

    /* renamed from: in */
    private InputStream f186in;

    /* renamed from: lz */
    private LZDecoder f187lz;
    private LZMADecoder lzma;

    /* renamed from: rc */
    private RangeDecoderFromStream f188rc;
    private long remainingSize;
    private final byte[] tempBuf;

    public LZMAInputStream(InputStream inputStream) throws IOException {
        this(inputStream, -1);
    }

    public LZMAInputStream(InputStream inputStream, int i) throws IOException {
        this(inputStream, i, ArrayCache.getDefaultCache());
    }

    public LZMAInputStream(InputStream inputStream, int i, ArrayCache arrayCache2) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        byte readByte = dataInputStream.readByte();
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            i2 |= dataInputStream.readUnsignedByte() << (i3 * 8);
        }
        long j = 0;
        for (int i4 = 0; i4 < 8; i4++) {
            j |= ((long) dataInputStream.readUnsignedByte()) << (i4 * 8);
        }
        int memoryUsage = getMemoryUsage(i2, readByte);
        if (i == -1 || memoryUsage <= i) {
            initialize(inputStream, j, readByte, i2, (byte[]) null, arrayCache2);
            return;
        }
        throw new MemoryLimitException(memoryUsage, i);
    }

    public LZMAInputStream(InputStream inputStream, long j, byte b, int i) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, b, i, (byte[]) null, ArrayCache.getDefaultCache());
    }

    public LZMAInputStream(InputStream inputStream, long j, byte b, int i, byte[] bArr) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, b, i, bArr, ArrayCache.getDefaultCache());
    }

    public LZMAInputStream(InputStream inputStream, long j, byte b, int i, byte[] bArr, ArrayCache arrayCache2) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, b, i, bArr, arrayCache2);
    }

    public LZMAInputStream(InputStream inputStream, long j, int i, int i2, int i3, int i4, byte[] bArr) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, i, i2, i3, i4, bArr, ArrayCache.getDefaultCache());
    }

    public LZMAInputStream(InputStream inputStream, long j, int i, int i2, int i3, int i4, byte[] bArr, ArrayCache arrayCache2) throws IOException {
        this.endReached = false;
        this.tempBuf = new byte[1];
        this.exception = null;
        initialize(inputStream, j, i, i2, i3, i4, bArr, arrayCache2);
    }

    public LZMAInputStream(InputStream inputStream, ArrayCache arrayCache2) throws IOException {
        this(inputStream, -1, arrayCache2);
    }

    private static int getDictSize(int i) {
        if (i < 0 || i > 2147483632) {
            throw new IllegalArgumentException("LZMA dictionary is too big for this implementation");
        }
        if (i < 4096) {
            i = 4096;
        }
        return (i + 15) & -16;
    }

    public static int getMemoryUsage(int i, byte b) throws UnsupportedOptionsException, CorruptedInputException {
        if (i < 0 || i > 2147483632) {
            throw new UnsupportedOptionsException("LZMA dictionary is too big for this implementation");
        }
        byte b2 = b & UByte.MAX_VALUE;
        if (b2 <= 224) {
            int i2 = b2 % 45;
            int i3 = i2 / 9;
            return getMemoryUsage(i, i2 - (i3 * 9), i3);
        }
        throw new CorruptedInputException("Invalid LZMA properties byte");
    }

    public static int getMemoryUsage(int i, int i2, int i3) {
        if (i2 >= 0 && i2 <= 8 && i3 >= 0 && i3 <= 4) {
            return (getDictSize(i) / 1024) + 10 + ((1536 << (i2 + i3)) / 1024);
        }
        throw new IllegalArgumentException("Invalid lc or lp");
    }

    private void initialize(InputStream inputStream, long j, byte b, int i, byte[] bArr, ArrayCache arrayCache2) throws IOException {
        int i2 = i;
        if (j >= -1) {
            byte b2 = b & UByte.MAX_VALUE;
            if (b2 <= 224) {
                int i3 = b2 / 45;
                int i4 = b2 - ((i3 * 9) * 5);
                int i5 = i4 / 9;
                int i6 = i4 - (i5 * 9);
                if (i2 < 0 || i2 > 2147483632) {
                    throw new UnsupportedOptionsException("LZMA dictionary is too big for this implementation");
                }
                initialize(inputStream, j, i6, i5, i3, i, bArr, arrayCache2);
                return;
            }
            throw new CorruptedInputException("Invalid LZMA properties byte");
        }
        throw new UnsupportedOptionsException("Uncompressed size is too big");
    }

    private void initialize(InputStream inputStream, long j, int i, int i2, int i3, int i4, byte[] bArr, ArrayCache arrayCache2) throws IOException {
        if (j < -1 || i < 0 || i > 8 || i2 < 0 || i2 > 4 || i3 < 0 || i3 > 4) {
            throw new IllegalArgumentException();
        }
        this.f186in = inputStream;
        this.arrayCache = arrayCache2;
        int dictSize = getDictSize(i4);
        if (j >= 0 && ((long) dictSize) > j) {
            dictSize = getDictSize((int) j);
        }
        this.f187lz = new LZDecoder(getDictSize(dictSize), bArr, arrayCache2);
        this.f188rc = new RangeDecoderFromStream(inputStream);
        this.lzma = new LZMADecoder(this.f187lz, this.f188rc, i, i2, i3);
        this.remainingSize = j;
    }

    private void putArraysToCache() {
        LZDecoder lZDecoder = this.f187lz;
        if (lZDecoder != null) {
            lZDecoder.putArraysToCache(this.arrayCache);
            this.f187lz = null;
        }
    }

    public void close() throws IOException {
        if (this.f186in != null) {
            putArraysToCache();
            try {
                this.f186in.close();
            } finally {
                this.f186in = null;
            }
        }
    }

    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & UByte.MAX_VALUE;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(byte[] r12, int r13, int r14) throws java.io.IOException {
        /*
            r11 = this;
            if (r13 < 0) goto L_0x00a8
            if (r14 < 0) goto L_0x00a8
            int r0 = r13 + r14
            if (r0 < 0) goto L_0x00a8
            int r1 = r12.length
            if (r0 > r1) goto L_0x00a8
            r0 = 0
            if (r14 != 0) goto L_0x000f
            return r0
        L_0x000f:
            java.io.InputStream r1 = r11.f186in
            if (r1 == 0) goto L_0x00a0
            java.io.IOException r1 = r11.exception
            if (r1 != 0) goto L_0x009f
            boolean r1 = r11.endReached
            r2 = -1
            if (r1 == 0) goto L_0x001d
            return r2
        L_0x001d:
            if (r14 <= 0) goto L_0x009e
            long r3 = r11.remainingSize     // Catch:{ IOException -> 0x009a }
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 < 0) goto L_0x002e
            long r7 = (long) r14     // Catch:{ IOException -> 0x009a }
            int r1 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r1 >= 0) goto L_0x002e
            int r1 = (int) r3     // Catch:{ IOException -> 0x009a }
            goto L_0x002f
        L_0x002e:
            r1 = r14
        L_0x002f:
            org.tukaani.xz.lz.LZDecoder r3 = r11.f187lz     // Catch:{ IOException -> 0x009a }
            r3.setLimit(r1)     // Catch:{ IOException -> 0x009a }
            r1 = 1
            org.tukaani.xz.lzma.LZMADecoder r3 = r11.lzma     // Catch:{ CorruptedInputException -> 0x003b }
            r3.decode()     // Catch:{ CorruptedInputException -> 0x003b }
            goto L_0x0053
        L_0x003b:
            r3 = move-exception
            long r7 = r11.remainingSize     // Catch:{ IOException -> 0x009a }
            r9 = -1
            int r4 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r4 != 0) goto L_0x0099
            org.tukaani.xz.lzma.LZMADecoder r4 = r11.lzma     // Catch:{ IOException -> 0x009a }
            boolean r4 = r4.endMarkerDetected()     // Catch:{ IOException -> 0x009a }
            if (r4 == 0) goto L_0x0099
            r11.endReached = r1     // Catch:{ IOException -> 0x009a }
            org.tukaani.xz.rangecoder.RangeDecoderFromStream r3 = r11.f188rc     // Catch:{ IOException -> 0x009a }
            r3.normalize()     // Catch:{ IOException -> 0x009a }
        L_0x0053:
            org.tukaani.xz.lz.LZDecoder r3 = r11.f187lz     // Catch:{ IOException -> 0x009a }
            int r3 = r3.flush(r12, r13)     // Catch:{ IOException -> 0x009a }
            int r13 = r13 + r3
            int r14 = r14 - r3
            int r0 = r0 + r3
            long r7 = r11.remainingSize     // Catch:{ IOException -> 0x009a }
            int r4 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r4 < 0) goto L_0x0077
            long r3 = (long) r3     // Catch:{ IOException -> 0x009a }
            long r7 = r7 - r3
            r11.remainingSize = r7     // Catch:{ IOException -> 0x009a }
            int r3 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r3 < 0) goto L_0x0071
            int r3 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0077
            r11.endReached = r1     // Catch:{ IOException -> 0x009a }
            goto L_0x0077
        L_0x0071:
            java.lang.AssertionError r12 = new java.lang.AssertionError     // Catch:{ IOException -> 0x009a }
            r12.<init>()     // Catch:{ IOException -> 0x009a }
            throw r12     // Catch:{ IOException -> 0x009a }
        L_0x0077:
            boolean r1 = r11.endReached     // Catch:{ IOException -> 0x009a }
            if (r1 == 0) goto L_0x001d
            org.tukaani.xz.rangecoder.RangeDecoderFromStream r12 = r11.f188rc     // Catch:{ IOException -> 0x009a }
            boolean r12 = r12.isFinished()     // Catch:{ IOException -> 0x009a }
            if (r12 == 0) goto L_0x0093
            org.tukaani.xz.lz.LZDecoder r12 = r11.f187lz     // Catch:{ IOException -> 0x009a }
            boolean r12 = r12.hasPending()     // Catch:{ IOException -> 0x009a }
            if (r12 != 0) goto L_0x0093
            r11.putArraysToCache()     // Catch:{ IOException -> 0x009a }
            if (r0 != 0) goto L_0x0091
            goto L_0x0092
        L_0x0091:
            r2 = r0
        L_0x0092:
            return r2
        L_0x0093:
            org.tukaani.xz.CorruptedInputException r12 = new org.tukaani.xz.CorruptedInputException     // Catch:{ IOException -> 0x009a }
            r12.<init>()     // Catch:{ IOException -> 0x009a }
            throw r12     // Catch:{ IOException -> 0x009a }
        L_0x0099:
            throw r3     // Catch:{ IOException -> 0x009a }
        L_0x009a:
            r12 = move-exception
            r11.exception = r12
            throw r12
        L_0x009e:
            return r0
        L_0x009f:
            throw r1
        L_0x00a0:
            org.tukaani.xz.XZIOException r12 = new org.tukaani.xz.XZIOException
            java.lang.String r13 = "Stream closed"
            r12.<init>(r13)
            throw r12
        L_0x00a8:
            java.lang.IndexOutOfBoundsException r12 = new java.lang.IndexOutOfBoundsException
            r12.<init>()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.LZMAInputStream.read(byte[], int, int):int");
    }
}
