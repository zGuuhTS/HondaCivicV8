package org.tukaani.p013xz;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.tukaani.p013xz.check.Check;
import org.tukaani.p013xz.common.DecoderUtil;

/* renamed from: org.tukaani.xz.BlockInputStream */
class BlockInputStream extends InputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Check check;
    private long compressedSizeInHeader = -1;
    private long compressedSizeLimit;
    private boolean endReached = false;
    private InputStream filterChain;
    private final int headerSize;
    private final CountingInputStream inCounted;
    private final DataInputStream inData;
    private final byte[] tempBuf = new byte[1];
    private long uncompressedSize = 0;
    private long uncompressedSizeInHeader = -1;
    private final boolean verifyCheck;

    public BlockInputStream(InputStream inputStream, Check check2, boolean z, int i, long j, long j2, ArrayCache arrayCache) throws IOException, IndexIndicatorException {
        String str;
        InputStream inputStream2 = inputStream;
        int i2 = i;
        long j3 = j2;
        this.check = check2;
        this.verifyCheck = z;
        DataInputStream dataInputStream = new DataInputStream(inputStream2);
        this.inData = dataInputStream;
        byte[] bArr = new byte[1024];
        dataInputStream.readFully(bArr, 0, 1);
        if (bArr[0] != 0) {
            int i3 = ((bArr[0] & UByte.MAX_VALUE) + 1) * 4;
            this.headerSize = i3;
            dataInputStream.readFully(bArr, 1, i3 - 1);
            if (!DecoderUtil.isCRC32Valid(bArr, 0, i3 - 4, i3 - 4)) {
                throw new CorruptedInputException("XZ Block Header is corrupt");
            } else if ((bArr[1] & 60) == 0) {
                int i4 = (bArr[1] & 3) + 1;
                long[] jArr = new long[i4];
                byte[][] bArr2 = new byte[i4][];
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr, 2, i3 - 6);
                try {
                    this.compressedSizeLimit = (9223372036854775804L - ((long) i3)) - ((long) check2.getSize());
                    if ((bArr[1] & 64) != 0) {
                        long decodeVLI = DecoderUtil.decodeVLI(byteArrayInputStream);
                        this.compressedSizeInHeader = decodeVLI;
                        str = "XZ Block Header is corrupt";
                        if (decodeVLI != 0) {
                            try {
                                if (decodeVLI <= this.compressedSizeLimit) {
                                    this.compressedSizeLimit = decodeVLI;
                                }
                            } catch (IOException e) {
                                throw new CorruptedInputException(str);
                            }
                        }
                        throw new CorruptedInputException();
                    }
                    Object obj = "XZ Block Header is corrupt";
                    if ((bArr[1] & ByteCompanionObject.MIN_VALUE) != 0) {
                        this.uncompressedSizeInHeader = DecoderUtil.decodeVLI(byteArrayInputStream);
                    }
                    int i5 = 0;
                    while (i5 < i4) {
                        jArr[i5] = DecoderUtil.decodeVLI(byteArrayInputStream);
                        long decodeVLI2 = DecoderUtil.decodeVLI(byteArrayInputStream);
                        if (decodeVLI2 <= ((long) byteArrayInputStream.available())) {
                            bArr2[i5] = new byte[((int) decodeVLI2)];
                            byteArrayInputStream.read(bArr2[i5]);
                            i5++;
                        } else {
                            throw new CorruptedInputException();
                        }
                    }
                    int available = byteArrayInputStream.available();
                    while (available > 0) {
                        if (byteArrayInputStream.read() == 0) {
                            available--;
                        } else {
                            throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
                        }
                    }
                    if (j != -1) {
                        long size = (long) (this.headerSize + check2.getSize());
                        if (size < j) {
                            long j4 = j - size;
                            if (j4 <= this.compressedSizeLimit) {
                                long j5 = this.compressedSizeInHeader;
                                if (j5 == -1 || j5 == j4) {
                                    long j6 = this.uncompressedSizeInHeader;
                                    if (j6 == -1 || j6 == j3) {
                                        this.compressedSizeLimit = j4;
                                        this.compressedSizeInHeader = j4;
                                        this.uncompressedSizeInHeader = j3;
                                    } else {
                                        throw new CorruptedInputException("XZ Index does not match a Block Header");
                                    }
                                }
                            }
                            throw new CorruptedInputException("XZ Index does not match a Block Header");
                        }
                        throw new CorruptedInputException("XZ Index does not match a Block Header");
                    }
                    FilterDecoder[] filterDecoderArr = new FilterDecoder[i4];
                    for (int i6 = 0; i6 < i4; i6++) {
                        if (jArr[i6] == 33) {
                            filterDecoderArr[i6] = new LZMA2Decoder(bArr2[i6]);
                        } else if (jArr[i6] == 3) {
                            filterDecoderArr[i6] = new DeltaDecoder(bArr2[i6]);
                        } else if (BCJDecoder.isBCJFilterID(jArr[i6])) {
                            filterDecoderArr[i6] = new BCJDecoder(jArr[i6], bArr2[i6]);
                        } else {
                            throw new UnsupportedOptionsException("Unknown Filter ID " + jArr[i6]);
                        }
                    }
                    RawCoder.validate(filterDecoderArr);
                    if (i2 >= 0) {
                        int i7 = 0;
                        for (int i8 = 0; i8 < i4; i8++) {
                            i7 += filterDecoderArr[i8].getMemoryUsage();
                        }
                        if (i7 > i2) {
                            throw new MemoryLimitException(i7, i2);
                        }
                    }
                    CountingInputStream countingInputStream = new CountingInputStream(inputStream2);
                    this.inCounted = countingInputStream;
                    this.filterChain = countingInputStream;
                    for (int i9 = i4 - 1; i9 >= 0; i9--) {
                        this.filterChain = filterDecoderArr[i9].getInputStream(this.filterChain, arrayCache);
                    }
                } catch (IOException e2) {
                    str = "XZ Block Header is corrupt";
                    throw new CorruptedInputException(str);
                }
            } else {
                throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
            }
        } else {
            throw new IndexIndicatorException();
        }
    }

    private void validate() throws IOException {
        long size = this.inCounted.getSize();
        long j = this.compressedSizeInHeader;
        if (j == -1 || j == size) {
            long j2 = this.uncompressedSizeInHeader;
            if (j2 == -1 || j2 == this.uncompressedSize) {
                while (true) {
                    long j3 = 1 + size;
                    if ((size & 3) == 0) {
                        byte[] bArr = new byte[this.check.getSize()];
                        this.inData.readFully(bArr);
                        if (this.verifyCheck && !Arrays.equals(this.check.finish(), bArr)) {
                            throw new CorruptedInputException("Integrity check (" + this.check.getName() + ") does not match");
                        }
                        return;
                    } else if (this.inData.readUnsignedByte() == 0) {
                        size = j3;
                    } else {
                        throw new CorruptedInputException();
                    }
                }
            }
        }
        throw new CorruptedInputException();
    }

    public int available() throws IOException {
        return this.filterChain.available();
    }

    public void close() {
        try {
            this.filterChain.close();
            this.filterChain = null;
        } catch (IOException e) {
            throw new AssertionError();
        }
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getUnpaddedSize() {
        return ((long) this.headerSize) + this.inCounted.getSize() + ((long) this.check.getSize());
    }

    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & UByte.MAX_VALUE;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005d, code lost:
        if (r0 == -1) goto L_0x005f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(byte[] r8, int r9, int r10) throws java.io.IOException {
        /*
            r7 = this;
            boolean r0 = r7.endReached
            r1 = -1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            java.io.InputStream r0 = r7.filterChain
            int r0 = r0.read(r8, r9, r10)
            r2 = 1
            if (r0 <= 0) goto L_0x005d
            boolean r3 = r7.verifyCheck
            if (r3 == 0) goto L_0x0018
            org.tukaani.xz.check.Check r3 = r7.check
            r3.update(r8, r9, r0)
        L_0x0018:
            long r8 = r7.uncompressedSize
            long r3 = (long) r0
            long r8 = r8 + r3
            r7.uncompressedSize = r8
            org.tukaani.xz.CountingInputStream r8 = r7.inCounted
            long r8 = r8.getSize()
            r3 = 0
            int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0057
            long r5 = r7.compressedSizeLimit
            int r8 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r8 > 0) goto L_0x0057
            long r8 = r7.uncompressedSize
            int r3 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r3 < 0) goto L_0x0057
            long r3 = r7.uncompressedSizeInHeader
            r5 = -1
            int r5 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r5 == 0) goto L_0x0042
            int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x0057
        L_0x0042:
            if (r0 < r10) goto L_0x0048
            int r8 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r8 != 0) goto L_0x0064
        L_0x0048:
            java.io.InputStream r8 = r7.filterChain
            int r8 = r8.read()
            if (r8 != r1) goto L_0x0051
            goto L_0x005f
        L_0x0051:
            org.tukaani.xz.CorruptedInputException r8 = new org.tukaani.xz.CorruptedInputException
            r8.<init>()
            throw r8
        L_0x0057:
            org.tukaani.xz.CorruptedInputException r8 = new org.tukaani.xz.CorruptedInputException
            r8.<init>()
            throw r8
        L_0x005d:
            if (r0 != r1) goto L_0x0064
        L_0x005f:
            r7.validate()
            r7.endReached = r2
        L_0x0064:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.BlockInputStream.read(byte[], int, int):int");
    }
}
