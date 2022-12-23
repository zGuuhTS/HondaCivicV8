package org.tukaani.p013xz.index;

import org.tukaani.p013xz.common.StreamFlags;

/* renamed from: org.tukaani.xz.index.IndexDecoder */
public class IndexDecoder extends IndexBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long compressedOffset = 0;
    private long largestBlockSize = 0;
    private final int memoryUsage;
    private int recordOffset = 0;
    private final StreamFlags streamFlags;
    private final long streamPadding;
    private final long[] uncompressed;
    private long uncompressedOffset = 0;
    private final long[] unpadded;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: org.tukaani.xz.common.StreamFlags} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v3, resolved type: org.tukaani.xz.common.StreamFlags} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v4, resolved type: org.tukaani.xz.common.StreamFlags} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.String} */
    /* JADX WARNING: Illegal instructions before constructor call */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public IndexDecoder(org.tukaani.p013xz.SeekableInputStream r17, org.tukaani.p013xz.common.StreamFlags r18, long r19, int r21) throws java.io.IOException {
        /*
            r16 = this;
            r1 = r16
            r0 = r18
            r2 = r21
            org.tukaani.xz.CorruptedInputException r3 = new org.tukaani.xz.CorruptedInputException
            java.lang.String r4 = "XZ Index is corrupt"
            r3.<init>(r4)
            r1.<init>(r3)
            r5 = 0
            r1.largestBlockSize = r5
            r3 = 0
            r1.recordOffset = r3
            r1.compressedOffset = r5
            r1.uncompressedOffset = r5
            r1.streamFlags = r0
            r5 = r19
            r1.streamPadding = r5
            long r5 = r17.position()
            long r7 = r0.backwardSize
            long r5 = r5 + r7
            r7 = 4
            long r5 = r5 - r7
            java.util.zip.CRC32 r7 = new java.util.zip.CRC32
            r7.<init>()
            java.util.zip.CheckedInputStream r8 = new java.util.zip.CheckedInputStream
            r9 = r17
            r8.<init>(r9, r7)
            int r10 = r8.read()
            if (r10 != 0) goto L_0x012c
            long r10 = org.tukaani.p013xz.common.DecoderUtil.decodeVLI(r8)     // Catch:{ EOFException -> 0x0124 }
            long r12 = r0.backwardSize     // Catch:{ EOFException -> 0x0124 }
            r14 = 2
            long r12 = r12 / r14
            int r0 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r0 >= 0) goto L_0x011b
            r12 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r0 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r0 > 0) goto L_0x0112
            r12 = 16
            long r12 = r12 * r10
            r14 = 1023(0x3ff, double:5.054E-321)
            long r12 = r12 + r14
            r14 = 1024(0x400, double:5.06E-321)
            long r12 = r12 / r14
            int r0 = (int) r12     // Catch:{ EOFException -> 0x0124 }
            int r0 = r0 + 1
            r1.memoryUsage = r0     // Catch:{ EOFException -> 0x0124 }
            if (r2 < 0) goto L_0x006a
            if (r0 > r2) goto L_0x0064
            goto L_0x006a
        L_0x0064:
            org.tukaani.xz.MemoryLimitException r3 = new org.tukaani.xz.MemoryLimitException     // Catch:{ EOFException -> 0x0124 }
            r3.<init>(r0, r2)     // Catch:{ EOFException -> 0x0124 }
            throw r3     // Catch:{ EOFException -> 0x0124 }
        L_0x006a:
            int r0 = (int) r10     // Catch:{ EOFException -> 0x0124 }
            long[] r2 = new long[r0]     // Catch:{ EOFException -> 0x0124 }
            r1.unpadded = r2     // Catch:{ EOFException -> 0x0124 }
            long[] r2 = new long[r0]     // Catch:{ EOFException -> 0x0124 }
            r1.uncompressed = r2     // Catch:{ EOFException -> 0x0124 }
            r2 = r3
        L_0x0074:
            if (r0 <= 0) goto L_0x00c9
            long r10 = org.tukaani.p013xz.common.DecoderUtil.decodeVLI(r8)     // Catch:{ EOFException -> 0x0124 }
            long r12 = org.tukaani.p013xz.common.DecoderUtil.decodeVLI(r8)     // Catch:{ EOFException -> 0x0124 }
            long r14 = r17.position()     // Catch:{ EOFException -> 0x0124 }
            int r14 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r14 > 0) goto L_0x00bb
            long[] r14 = r1.unpadded     // Catch:{ EOFException -> 0x0124 }
            r15 = r4
            long r3 = r1.blocksSum     // Catch:{ EOFException -> 0x00b7 }
            long r3 = r3 + r10
            r14[r2] = r3     // Catch:{ EOFException -> 0x00b7 }
            long[] r3 = r1.uncompressed     // Catch:{ EOFException -> 0x00b7 }
            r18 = r15
            long r14 = r1.uncompressedSum     // Catch:{ EOFException -> 0x00c5 }
            long r14 = r14 + r12
            r3[r2] = r14     // Catch:{ EOFException -> 0x00c5 }
            int r2 = r2 + 1
            super.add(r10, r12)     // Catch:{ EOFException -> 0x00c5 }
            long r3 = (long) r2     // Catch:{ EOFException -> 0x00c5 }
            long r10 = r1.recordCount     // Catch:{ EOFException -> 0x00c5 }
            int r3 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r3 != 0) goto L_0x00b1
            long r3 = r1.largestBlockSize     // Catch:{ EOFException -> 0x00c5 }
            int r3 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r3 >= 0) goto L_0x00ab
            r1.largestBlockSize = r12     // Catch:{ EOFException -> 0x00c5 }
        L_0x00ab:
            int r0 = r0 + -1
            r4 = r18
            r3 = 0
            goto L_0x0074
        L_0x00b1:
            java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch:{ EOFException -> 0x00c5 }
            r0.<init>()     // Catch:{ EOFException -> 0x00c5 }
            throw r0     // Catch:{ EOFException -> 0x00c5 }
        L_0x00b7:
            r0 = move-exception
            r2 = r15
            goto L_0x0126
        L_0x00bb:
            r18 = r4
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException     // Catch:{ EOFException -> 0x00c5 }
            r2 = r18
            r0.<init>(r2)     // Catch:{ EOFException -> 0x0122 }
            throw r0     // Catch:{ EOFException -> 0x0122 }
        L_0x00c5:
            r0 = move-exception
            r2 = r18
            goto L_0x0126
        L_0x00c9:
            r2 = r4
            int r0 = r16.getIndexPaddingSize()
            long r3 = r17.position()
            long r10 = (long) r0
            long r3 = r3 + r10
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x010c
        L_0x00d8:
            int r3 = r0 + -1
            if (r0 <= 0) goto L_0x00ea
            int r0 = r8.read()
            if (r0 != 0) goto L_0x00e4
            r0 = r3
            goto L_0x00d8
        L_0x00e4:
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException
            r0.<init>(r2)
            throw r0
        L_0x00ea:
            long r3 = r7.getValue()
            r0 = 0
        L_0x00ef:
            r5 = 4
            if (r0 >= r5) goto L_0x010b
            int r5 = r0 * 8
            long r5 = r3 >>> r5
            r7 = 255(0xff, double:1.26E-321)
            long r5 = r5 & r7
            int r7 = r17.read()
            long r7 = (long) r7
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 != 0) goto L_0x0105
            int r0 = r0 + 1
            goto L_0x00ef
        L_0x0105:
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException
            r0.<init>(r2)
            throw r0
        L_0x010b:
            return
        L_0x010c:
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException
            r0.<init>(r2)
            throw r0
        L_0x0112:
            r2 = r4
            org.tukaani.xz.UnsupportedOptionsException r0 = new org.tukaani.xz.UnsupportedOptionsException     // Catch:{ EOFException -> 0x0122 }
            java.lang.String r3 = "XZ Index has over 2147483647 Records"
            r0.<init>(r3)     // Catch:{ EOFException -> 0x0122 }
            throw r0     // Catch:{ EOFException -> 0x0122 }
        L_0x011b:
            r2 = r4
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException     // Catch:{ EOFException -> 0x0122 }
            r0.<init>(r2)     // Catch:{ EOFException -> 0x0122 }
            throw r0     // Catch:{ EOFException -> 0x0122 }
        L_0x0122:
            r0 = move-exception
            goto L_0x0126
        L_0x0124:
            r0 = move-exception
            r2 = r4
        L_0x0126:
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException
            r0.<init>(r2)
            throw r0
        L_0x012c:
            r2 = r4
            org.tukaani.xz.CorruptedInputException r0 = new org.tukaani.xz.CorruptedInputException
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.p013xz.index.IndexDecoder.<init>(org.tukaani.xz.SeekableInputStream, org.tukaani.xz.common.StreamFlags, long, int):void");
    }

    public /* bridge */ /* synthetic */ long getIndexSize() {
        return super.getIndexSize();
    }

    public long getLargestBlockSize() {
        return this.largestBlockSize;
    }

    public int getMemoryUsage() {
        return this.memoryUsage;
    }

    public int getRecordCount() {
        return (int) this.recordCount;
    }

    public StreamFlags getStreamFlags() {
        return this.streamFlags;
    }

    public /* bridge */ /* synthetic */ long getStreamSize() {
        return super.getStreamSize();
    }

    public long getUncompressedSize() {
        return this.uncompressedSum;
    }

    public boolean hasRecord(int i) {
        int i2 = this.recordOffset;
        return i >= i2 && ((long) i) < ((long) i2) + this.recordCount;
    }

    public boolean hasUncompressedOffset(long j) {
        long j2 = this.uncompressedOffset;
        return j >= j2 && j < j2 + this.uncompressedSum;
    }

    public void locateBlock(BlockInfo blockInfo, long j) {
        long j2 = this.uncompressedOffset;
        if (j >= j2) {
            long j3 = j - j2;
            if (j3 < this.uncompressedSum) {
                int i = 0;
                int length = this.unpadded.length - 1;
                while (i < length) {
                    int i2 = ((length - i) / 2) + i;
                    if (this.uncompressed[i2] <= j3) {
                        i = i2 + 1;
                    } else {
                        length = i2;
                    }
                }
                setBlockInfo(blockInfo, this.recordOffset + i);
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public void setBlockInfo(BlockInfo blockInfo, int i) {
        int i2 = this.recordOffset;
        if (i < i2) {
            throw new AssertionError();
        } else if (((long) (i - i2)) < this.recordCount) {
            blockInfo.index = this;
            blockInfo.blockNumber = i;
            int i3 = i - this.recordOffset;
            if (i3 == 0) {
                blockInfo.compressedOffset = 0;
                blockInfo.uncompressedOffset = 0;
            } else {
                int i4 = i3 - 1;
                blockInfo.compressedOffset = (this.unpadded[i4] + 3) & -4;
                blockInfo.uncompressedOffset = this.uncompressed[i4];
            }
            blockInfo.unpaddedSize = this.unpadded[i3] - blockInfo.compressedOffset;
            blockInfo.uncompressedSize = this.uncompressed[i3] - blockInfo.uncompressedOffset;
            blockInfo.compressedOffset += this.compressedOffset + 12;
            blockInfo.uncompressedOffset += this.uncompressedOffset;
        } else {
            throw new AssertionError();
        }
    }

    public void setOffsets(IndexDecoder indexDecoder) {
        this.recordOffset = indexDecoder.recordOffset + ((int) indexDecoder.recordCount);
        long streamSize = indexDecoder.compressedOffset + indexDecoder.getStreamSize() + indexDecoder.streamPadding;
        this.compressedOffset = streamSize;
        if ((streamSize & 3) == 0) {
            this.uncompressedOffset = indexDecoder.uncompressedOffset + indexDecoder.uncompressedSum;
            return;
        }
        throw new AssertionError();
    }
}
