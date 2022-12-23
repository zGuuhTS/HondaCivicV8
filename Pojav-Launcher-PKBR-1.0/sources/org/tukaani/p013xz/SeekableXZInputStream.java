package org.tukaani.p013xz;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import kotlin.UByte;
import org.tukaani.p013xz.check.Check;
import org.tukaani.p013xz.common.DecoderUtil;
import org.tukaani.p013xz.common.StreamFlags;
import org.tukaani.p013xz.index.BlockInfo;
import org.tukaani.p013xz.index.IndexDecoder;

/* renamed from: org.tukaani.xz.SeekableXZInputStream */
public class SeekableXZInputStream extends SeekableInputStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ArrayCache arrayCache;
    private int blockCount;
    private BlockInputStream blockDecoder;
    private Check check;
    private int checkTypes;
    private final BlockInfo curBlockInfo;
    private long curPos;
    private boolean endReached;
    private IOException exception;

    /* renamed from: in */
    private SeekableInputStream f191in;
    private int indexMemoryUsage;
    private long largestBlockSize;
    private final int memoryLimit;
    private final BlockInfo queriedBlockInfo;
    private boolean seekNeeded;
    private long seekPos;
    private final ArrayList<IndexDecoder> streams;
    private final byte[] tempBuf;
    private long uncompressedSize;
    private final boolean verifyCheck;

    public SeekableXZInputStream(SeekableInputStream seekableInputStream) throws IOException {
        this(seekableInputStream, -1);
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream, int i) throws IOException {
        this(seekableInputStream, i, true);
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream, int i, ArrayCache arrayCache2) throws IOException {
        this(seekableInputStream, i, true, arrayCache2);
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream, int i, boolean z) throws IOException {
        this(seekableInputStream, i, z, ArrayCache.getDefaultCache());
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream, int i, boolean z, ArrayCache arrayCache2) throws IOException {
        long j;
        SeekableInputStream seekableInputStream2 = seekableInputStream;
        this.indexMemoryUsage = 0;
        this.streams = new ArrayList<>();
        this.checkTypes = 0;
        long j2 = 0;
        this.uncompressedSize = 0;
        this.largestBlockSize = 0;
        this.blockCount = 0;
        this.blockDecoder = null;
        this.curPos = 0;
        this.seekNeeded = false;
        this.endReached = false;
        this.exception = null;
        int i2 = 1;
        this.tempBuf = new byte[1];
        this.arrayCache = arrayCache2;
        this.verifyCheck = z;
        this.f191in = seekableInputStream2;
        DataInputStream dataInputStream = new DataInputStream(seekableInputStream2);
        seekableInputStream2.seek(0);
        byte[] bArr = new byte[C0927XZ.HEADER_MAGIC.length];
        dataInputStream.readFully(bArr);
        if (Arrays.equals(bArr, C0927XZ.HEADER_MAGIC)) {
            long length = seekableInputStream.length();
            if ((3 & length) == 0) {
                byte[] bArr2 = new byte[12];
                int i3 = i;
                while (true) {
                    long j3 = j2;
                    while (true) {
                        int i4 = (length > j2 ? 1 : (length == j2 ? 0 : -1));
                        if (i4 > 0) {
                            if (length >= 12) {
                                j = length - 12;
                                seekableInputStream2.seek(j);
                                dataInputStream.readFully(bArr2);
                                if (bArr2[8] == 0 && bArr2[9] == 0 && bArr2[10] == 0 && bArr2[11] == 0) {
                                    j3 += 4;
                                    length -= 4;
                                    j2 = 0;
                                } else {
                                    StreamFlags decodeStreamFooter = DecoderUtil.decodeStreamFooter(bArr2);
                                }
                            } else {
                                throw new CorruptedInputException();
                            }
                        } else if (i4 == 0) {
                            this.memoryLimit = i3;
                            ArrayList<IndexDecoder> arrayList = this.streams;
                            IndexDecoder indexDecoder = arrayList.get(arrayList.size() - 1);
                            int size = this.streams.size() - 2;
                            while (size >= 0) {
                                IndexDecoder indexDecoder2 = this.streams.get(size);
                                indexDecoder2.setOffsets(indexDecoder);
                                size--;
                                indexDecoder = indexDecoder2;
                            }
                            ArrayList<IndexDecoder> arrayList2 = this.streams;
                            IndexDecoder indexDecoder3 = arrayList2.get(arrayList2.size() - 1);
                            this.curBlockInfo = new BlockInfo(indexDecoder3);
                            this.queriedBlockInfo = new BlockInfo(indexDecoder3);
                            return;
                        } else {
                            throw new AssertionError();
                        }
                    }
                    StreamFlags decodeStreamFooter2 = DecoderUtil.decodeStreamFooter(bArr2);
                    if (decodeStreamFooter2.backwardSize < j) {
                        this.check = Check.getInstance(decodeStreamFooter2.checkType);
                        this.checkTypes |= i2 << decodeStreamFooter2.checkType;
                        seekableInputStream2.seek(j - decodeStreamFooter2.backwardSize);
                        try {
                            IndexDecoder indexDecoder4 = r2;
                            StreamFlags streamFlags = decodeStreamFooter2;
                            IndexDecoder indexDecoder5 = new IndexDecoder(seekableInputStream, decodeStreamFooter2, j3, i3);
                            this.indexMemoryUsage += indexDecoder4.getMemoryUsage();
                            if (i3 < 0 || (i3 = i3 - indexDecoder4.getMemoryUsage()) >= 0) {
                                if (this.largestBlockSize < indexDecoder4.getLargestBlockSize()) {
                                    this.largestBlockSize = indexDecoder4.getLargestBlockSize();
                                }
                                long streamSize = indexDecoder4.getStreamSize() - 12;
                                if (j >= streamSize) {
                                    length = j - streamSize;
                                    seekableInputStream2.seek(length);
                                    dataInputStream.readFully(bArr2);
                                    if (DecoderUtil.areStreamFlagsEqual(DecoderUtil.decodeStreamHeader(bArr2), streamFlags)) {
                                        long uncompressedSize2 = this.uncompressedSize + indexDecoder4.getUncompressedSize();
                                        this.uncompressedSize = uncompressedSize2;
                                        if (uncompressedSize2 >= 0) {
                                            int recordCount = this.blockCount + indexDecoder4.getRecordCount();
                                            this.blockCount = recordCount;
                                            if (recordCount >= 0) {
                                                this.streams.add(indexDecoder4);
                                                j2 = 0;
                                                i2 = 1;
                                            } else {
                                                throw new UnsupportedOptionsException("XZ file has over 2147483647 Blocks");
                                            }
                                        } else {
                                            throw new UnsupportedOptionsException("XZ file is too big");
                                        }
                                    } else {
                                        throw new CorruptedInputException("XZ Stream Footer does not match Stream Header");
                                    }
                                } else {
                                    throw new CorruptedInputException("XZ Index indicates too big compressed size for the XZ Stream");
                                }
                            } else {
                                throw new AssertionError();
                            }
                        } catch (MemoryLimitException e) {
                            if (i3 < 0) {
                                throw new AssertionError();
                            }
                            int memoryNeeded = e.getMemoryNeeded();
                            int i5 = this.indexMemoryUsage;
                            throw new MemoryLimitException(memoryNeeded + i5, i3 + i5);
                        }
                    } else {
                        throw new CorruptedInputException("Backward Size in XZ Stream Footer is too big");
                    }
                }
            } else {
                throw new CorruptedInputException("XZ file size is not a multiple of 4 bytes");
            }
        } else {
            throw new XZFormatException();
        }
    }

    public SeekableXZInputStream(SeekableInputStream seekableInputStream, ArrayCache arrayCache2) throws IOException {
        this(seekableInputStream, -1, arrayCache2);
    }

    private void initBlockDecoder() throws IOException {
        try {
            BlockInputStream blockInputStream = this.blockDecoder;
            if (blockInputStream != null) {
                blockInputStream.close();
                this.blockDecoder = null;
            }
            this.blockDecoder = new BlockInputStream(this.f191in, this.check, this.verifyCheck, this.memoryLimit, this.curBlockInfo.unpaddedSize, this.curBlockInfo.uncompressedSize, this.arrayCache);
        } catch (MemoryLimitException e) {
            if (this.memoryLimit < 0) {
                throw new AssertionError();
            }
            int memoryNeeded = e.getMemoryNeeded();
            int i = this.indexMemoryUsage;
            throw new MemoryLimitException(memoryNeeded + i, this.memoryLimit + i);
        } catch (IndexIndicatorException e2) {
            throw new CorruptedInputException();
        }
    }

    private void locateBlockByNumber(BlockInfo blockInfo, int i) {
        if (i < 0 || i >= this.blockCount) {
            throw new IndexOutOfBoundsException("Invalid XZ Block number: " + i);
        } else if (blockInfo.blockNumber != i) {
            int i2 = 0;
            while (true) {
                IndexDecoder indexDecoder = this.streams.get(i2);
                if (indexDecoder.hasRecord(i)) {
                    indexDecoder.setBlockInfo(blockInfo, i);
                    return;
                }
                i2++;
            }
        }
    }

    private void locateBlockByPos(BlockInfo blockInfo, long j) {
        IndexDecoder indexDecoder;
        if (j < 0 || j >= this.uncompressedSize) {
            throw new IndexOutOfBoundsException("Invalid uncompressed position: " + j);
        }
        int i = 0;
        while (true) {
            indexDecoder = this.streams.get(i);
            if (indexDecoder.hasUncompressedOffset(j)) {
                break;
            }
            i++;
        }
        indexDecoder.locateBlock(blockInfo, j);
        if ((blockInfo.compressedOffset & 3) != 0) {
            throw new AssertionError();
        } else if (blockInfo.uncompressedSize <= 0) {
            throw new AssertionError();
        } else if (j < blockInfo.uncompressedOffset) {
            throw new AssertionError();
        } else if (j >= blockInfo.uncompressedOffset + blockInfo.uncompressedSize) {
            throw new AssertionError();
        }
    }

    private void seek() throws IOException {
        if (!this.seekNeeded) {
            if (this.curBlockInfo.hasNext()) {
                this.curBlockInfo.setNext();
                initBlockDecoder();
                return;
            }
            this.seekPos = this.curPos;
        }
        this.seekNeeded = false;
        long j = this.seekPos;
        if (j >= this.uncompressedSize) {
            this.curPos = j;
            BlockInputStream blockInputStream = this.blockDecoder;
            if (blockInputStream != null) {
                blockInputStream.close();
                this.blockDecoder = null;
            }
            this.endReached = true;
            return;
        }
        this.endReached = false;
        locateBlockByPos(this.curBlockInfo, j);
        if (this.curPos <= this.curBlockInfo.uncompressedOffset || this.curPos > this.seekPos) {
            this.f191in.seek(this.curBlockInfo.compressedOffset);
            this.check = Check.getInstance(this.curBlockInfo.getCheckType());
            initBlockDecoder();
            this.curPos = this.curBlockInfo.uncompressedOffset;
        }
        long j2 = this.seekPos;
        long j3 = this.curPos;
        if (j2 > j3) {
            long j4 = j2 - j3;
            if (this.blockDecoder.skip(j4) == j4) {
                this.curPos = this.seekPos;
                return;
            }
            throw new CorruptedInputException();
        }
    }

    public int available() throws IOException {
        BlockInputStream blockInputStream;
        if (this.f191in != null) {
            IOException iOException = this.exception;
            if (iOException != null) {
                throw iOException;
            } else if (this.endReached || this.seekNeeded || (blockInputStream = this.blockDecoder) == null) {
                return 0;
            } else {
                return blockInputStream.available();
            }
        } else {
            throw new XZIOException("Stream closed");
        }
    }

    public void close() throws IOException {
        close(true);
    }

    public void close(boolean z) throws IOException {
        if (this.f191in != null) {
            BlockInputStream blockInputStream = this.blockDecoder;
            if (blockInputStream != null) {
                blockInputStream.close();
                this.blockDecoder = null;
            }
            if (z) {
                try {
                    this.f191in.close();
                } catch (Throwable th) {
                    this.f191in = null;
                    throw th;
                }
            }
            this.f191in = null;
        }
    }

    public int getBlockCheckType(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.getCheckType();
    }

    public long getBlockCompPos(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.compressedOffset;
    }

    public long getBlockCompSize(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return (this.queriedBlockInfo.unpaddedSize + 3) & -4;
    }

    public int getBlockCount() {
        return this.blockCount;
    }

    public int getBlockNumber(long j) {
        locateBlockByPos(this.queriedBlockInfo, j);
        return this.queriedBlockInfo.blockNumber;
    }

    public long getBlockPos(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.uncompressedOffset;
    }

    public long getBlockSize(int i) {
        locateBlockByNumber(this.queriedBlockInfo, i);
        return this.queriedBlockInfo.uncompressedSize;
    }

    public int getCheckTypes() {
        return this.checkTypes;
    }

    public int getIndexMemoryUsage() {
        return this.indexMemoryUsage;
    }

    public long getLargestBlockSize() {
        return this.largestBlockSize;
    }

    public int getStreamCount() {
        return this.streams.size();
    }

    public long length() {
        return this.uncompressedSize;
    }

    public long position() throws IOException {
        if (this.f191in != null) {
            return this.seekNeeded ? this.seekPos : this.curPos;
        }
        throw new XZIOException("Stream closed");
    }

    public int read() throws IOException {
        if (read(this.tempBuf, 0, 1) == -1) {
            return -1;
        }
        return this.tempBuf[0] & UByte.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        if (i < 0 || i2 < 0 || (i3 = i + i2) < 0 || i3 > bArr.length) {
            throw new IndexOutOfBoundsException();
        }
        int i4 = 0;
        if (i2 == 0) {
            return 0;
        }
        if (this.f191in != null) {
            IOException iOException = this.exception;
            if (iOException == null) {
                try {
                    if (this.seekNeeded) {
                        seek();
                    }
                    if (this.endReached) {
                        return -1;
                    }
                    while (true) {
                        if (i2 <= 0) {
                            break;
                        }
                        if (this.blockDecoder == null) {
                            seek();
                            if (this.endReached) {
                                break;
                            }
                        }
                        int read = this.blockDecoder.read(bArr, i, i2);
                        if (read > 0) {
                            this.curPos += (long) read;
                            i4 += read;
                            i += read;
                            i2 -= read;
                        } else if (read == -1) {
                            this.blockDecoder = null;
                        }
                    }
                    return i4;
                } catch (IOException e) {
                    e = e;
                    if (e instanceof EOFException) {
                        e = new CorruptedInputException();
                    }
                    this.exception = e;
                    if (0 == 0) {
                        throw e;
                    }
                }
            } else {
                throw iOException;
            }
        } else {
            throw new XZIOException("Stream closed");
        }
    }

    public void seek(long j) throws IOException {
        if (this.f191in == null) {
            throw new XZIOException("Stream closed");
        } else if (j >= 0) {
            this.seekPos = j;
            this.seekNeeded = true;
        } else {
            throw new XZIOException("Negative seek position: " + j);
        }
    }

    public void seekToBlock(int i) throws IOException {
        if (this.f191in == null) {
            throw new XZIOException("Stream closed");
        } else if (i < 0 || i >= this.blockCount) {
            throw new XZIOException("Invalid XZ Block number: " + i);
        } else {
            this.seekPos = getBlockPos(i);
            this.seekNeeded = true;
        }
    }
}
