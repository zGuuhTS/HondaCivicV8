package org.tukaani.p013xz.p014lz;

import kotlin.UByte;
import org.tukaani.p013xz.ArrayCache;

/* renamed from: org.tukaani.xz.lz.Hash234 */
final class Hash234 extends CRC32Hash {
    private static final int HASH_2_MASK = 1023;
    private static final int HASH_2_SIZE = 1024;
    private static final int HASH_3_MASK = 65535;
    private static final int HASH_3_SIZE = 65536;
    private final int[] hash2Table;
    private int hash2Value = 0;
    private final int[] hash3Table;
    private int hash3Value = 0;
    private final int hash4Mask;
    private final int hash4Size;
    private final int[] hash4Table;
    private int hash4Value = 0;

    Hash234(int i, ArrayCache arrayCache) {
        this.hash2Table = arrayCache.getIntArray(1024, true);
        this.hash3Table = arrayCache.getIntArray(65536, true);
        int hash4Size2 = getHash4Size(i);
        this.hash4Size = hash4Size2;
        this.hash4Table = arrayCache.getIntArray(hash4Size2, true);
        this.hash4Mask = hash4Size2 - 1;
    }

    static int getHash4Size(int i) {
        int i2 = i - 1;
        int i3 = i2 | (i2 >>> 1);
        int i4 = i3 | (i3 >>> 2);
        int i5 = i4 | (i4 >>> 4);
        int i6 = ((i5 | (i5 >>> 8)) >>> 1) | 65535;
        if (i6 > 16777216) {
            i6 >>>= 1;
        }
        return i6 + 1;
    }

    static int getMemoryUsage(int i) {
        return ((getHash4Size(i) + 66560) / 256) + 4;
    }

    /* access modifiers changed from: package-private */
    public void calcHashes(byte[] bArr, int i) {
        byte b = crcTable[bArr[i] & UByte.MAX_VALUE] ^ (bArr[i + 1] & UByte.MAX_VALUE);
        this.hash2Value = b & UByte.MAX_VALUE;
        byte b2 = b ^ ((bArr[i + 2] & UByte.MAX_VALUE) << 8);
        this.hash3Value = 65535 & b2;
        this.hash4Value = ((crcTable[bArr[i + 3] & UByte.MAX_VALUE] << 5) ^ b2) & this.hash4Mask;
    }

    /* access modifiers changed from: package-private */
    public int getHash2Pos() {
        return this.hash2Table[this.hash2Value];
    }

    /* access modifiers changed from: package-private */
    public int getHash3Pos() {
        return this.hash3Table[this.hash3Value];
    }

    /* access modifiers changed from: package-private */
    public int getHash4Pos() {
        return this.hash4Table[this.hash4Value];
    }

    /* access modifiers changed from: package-private */
    public void normalize(int i) {
        LZEncoder.normalize(this.hash2Table, 1024, i);
        LZEncoder.normalize(this.hash3Table, 65536, i);
        LZEncoder.normalize(this.hash4Table, this.hash4Size, i);
    }

    /* access modifiers changed from: package-private */
    public void putArraysToCache(ArrayCache arrayCache) {
        arrayCache.putArray(this.hash4Table);
        arrayCache.putArray(this.hash3Table);
        arrayCache.putArray(this.hash2Table);
    }

    /* access modifiers changed from: package-private */
    public void updateTables(int i) {
        this.hash2Table[this.hash2Value] = i;
        this.hash3Table[this.hash3Value] = i;
        this.hash4Table[this.hash4Value] = i;
    }
}
