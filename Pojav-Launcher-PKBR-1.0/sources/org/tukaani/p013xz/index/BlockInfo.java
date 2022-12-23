package org.tukaani.p013xz.index;

/* renamed from: org.tukaani.xz.index.BlockInfo */
public class BlockInfo {
    public int blockNumber = -1;
    public long compressedOffset = -1;
    IndexDecoder index;
    public long uncompressedOffset = -1;
    public long uncompressedSize = -1;
    public long unpaddedSize = -1;

    public BlockInfo(IndexDecoder indexDecoder) {
        this.index = indexDecoder;
    }

    public int getCheckType() {
        return this.index.getStreamFlags().checkType;
    }

    public boolean hasNext() {
        return this.index.hasRecord(this.blockNumber + 1);
    }

    public void setNext() {
        this.index.setBlockInfo(this, this.blockNumber + 1);
    }
}
