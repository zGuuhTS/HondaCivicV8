package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.io.InputStream;

class ExplodingInputStream extends InputStream {
    private BitStream bits;
    private final CircularBuffer buffer = new CircularBuffer(32768);
    private final int dictionarySize;
    private BinaryTree distanceTree;

    /* renamed from: in */
    private final InputStream f143in;
    private BinaryTree lengthTree;
    private BinaryTree literalTree;
    private final int minimumMatchLength;
    private final int numberOfTrees;

    public ExplodingInputStream(int i, int i2, InputStream inputStream) {
        if (i != 4096 && i != 8192) {
            throw new IllegalArgumentException("The dictionary size must be 4096 or 8192");
        } else if (i2 == 2 || i2 == 3) {
            this.dictionarySize = i;
            this.numberOfTrees = i2;
            this.minimumMatchLength = i2;
            this.f143in = inputStream;
        } else {
            throw new IllegalArgumentException("The number of trees must be 2 or 3");
        }
    }

    private void fillBuffer() throws IOException {
        init();
        int nextBit = this.bits.nextBit();
        if (nextBit == 1) {
            BinaryTree binaryTree = this.literalTree;
            int read = binaryTree != null ? binaryTree.read(this.bits) : this.bits.nextByte();
            if (read != -1) {
                this.buffer.put(read);
            }
        } else if (nextBit == 0) {
            int i = this.dictionarySize == 4096 ? 6 : 7;
            int nextBits = (int) this.bits.nextBits(i);
            int read2 = this.distanceTree.read(this.bits);
            if (read2 != -1 || nextBits > 0) {
                int read3 = this.lengthTree.read(this.bits);
                if (read3 == 63) {
                    read3 = (int) (((long) read3) + this.bits.nextBits(8));
                }
                this.buffer.copy(((read2 << i) | nextBits) + 1, read3 + this.minimumMatchLength);
            }
        }
    }

    private void init() throws IOException {
        if (this.bits == null) {
            if (this.numberOfTrees == 3) {
                this.literalTree = BinaryTree.decode(this.f143in, 256);
            }
            this.lengthTree = BinaryTree.decode(this.f143in, 64);
            this.distanceTree = BinaryTree.decode(this.f143in, 64);
            this.bits = new BitStream(this.f143in);
        }
    }

    public int read() throws IOException {
        if (!this.buffer.available()) {
            fillBuffer();
        }
        return this.buffer.get();
    }
}
