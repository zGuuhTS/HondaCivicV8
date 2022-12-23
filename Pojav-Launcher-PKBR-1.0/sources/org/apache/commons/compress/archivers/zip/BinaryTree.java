package org.apache.commons.compress.archivers.zip;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

class BinaryTree {
    private static final int NODE = -2;
    private static final int UNDEFINED = -1;
    private final int[] tree;

    public BinaryTree(int i) {
        int[] iArr = new int[((1 << (i + 1)) - 1)];
        this.tree = iArr;
        Arrays.fill(iArr, -1);
    }

    static BinaryTree decode(InputStream inputStream, int i) throws IOException {
        int read = inputStream.read() + 1;
        if (read != 0) {
            byte[] bArr = new byte[read];
            new DataInputStream(inputStream).readFully(bArr);
            int[] iArr = new int[i];
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < read; i4++) {
                byte b = bArr[i4];
                int i5 = (b & 15) + 1;
                int i6 = 0;
                while (i6 < ((b & 240) >> 4) + 1) {
                    iArr[i2] = i5;
                    i6++;
                    i2++;
                }
                i3 = Math.max(i3, i5);
            }
            int[] iArr2 = new int[i];
            for (int i7 = 0; i7 < i; i7++) {
                iArr2[i7] = i7;
            }
            int[] iArr3 = new int[i];
            int i8 = 0;
            for (int i9 = 0; i9 < i; i9++) {
                for (int i10 = 0; i10 < i; i10++) {
                    if (iArr[i10] == i9) {
                        iArr3[i8] = i9;
                        iArr2[i8] = i10;
                        i8++;
                    }
                }
            }
            int[] iArr4 = new int[i];
            int i11 = 0;
            int i12 = 0;
            int i13 = 0;
            for (int i14 = i - 1; i14 >= 0; i14--) {
                i11 += i12;
                if (iArr3[i14] != i13) {
                    i13 = iArr3[i14];
                    i12 = 1 << (16 - i13);
                }
                iArr4[iArr2[i14]] = i11;
            }
            BinaryTree binaryTree = new BinaryTree(i3);
            for (int i15 = 0; i15 < i; i15++) {
                int i16 = iArr[i15];
                if (i16 > 0) {
                    binaryTree.addLeaf(0, Integer.reverse(iArr4[i15] << 16), i16, i15);
                }
            }
            return binaryTree;
        }
        throw new IOException("Cannot read the size of the encoded tree, unexpected end of stream");
    }

    public void addLeaf(int i, int i2, int i3, int i4) {
        if (i3 == 0) {
            int[] iArr = this.tree;
            if (iArr[i] == -1) {
                iArr[i] = i4;
                return;
            }
            throw new IllegalArgumentException("Tree value at index " + i + " has already been assigned (" + this.tree[i] + ")");
        }
        this.tree[i] = -2;
        addLeaf((i * 2) + 1 + (i2 & 1), i2 >>> 1, i3 - 1, i4);
    }

    public int read(BitStream bitStream) throws IOException {
        int i = 0;
        while (true) {
            int nextBit = bitStream.nextBit();
            if (nextBit == -1) {
                return -1;
            }
            int i2 = (i * 2) + 1 + nextBit;
            int i3 = this.tree[i2];
            if (i3 == -2) {
                i = i2;
            } else if (i3 != -1) {
                return i3;
            } else {
                throw new IOException("The child " + nextBit + " of node at index " + i + " is not defined");
            }
        }
    }
}
