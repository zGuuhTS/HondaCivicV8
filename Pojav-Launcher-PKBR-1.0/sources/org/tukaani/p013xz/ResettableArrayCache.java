package org.tukaani.p013xz;

import java.util.ArrayList;
import java.util.List;

/* renamed from: org.tukaani.xz.ResettableArrayCache */
public class ResettableArrayCache extends ArrayCache {
    private final ArrayCache arrayCache;
    private final List<byte[]> byteArrays;
    private final List<int[]> intArrays;

    public ResettableArrayCache(ArrayCache arrayCache2) {
        ArrayList arrayList;
        this.arrayCache = arrayCache2;
        if (arrayCache2 == ArrayCache.getDummyCache()) {
            arrayList = null;
            this.byteArrays = null;
        } else {
            this.byteArrays = new ArrayList();
            arrayList = new ArrayList();
        }
        this.intArrays = arrayList;
    }

    public byte[] getByteArray(int i, boolean z) {
        byte[] byteArray = this.arrayCache.getByteArray(i, z);
        List<byte[]> list = this.byteArrays;
        if (list != null) {
            synchronized (list) {
                this.byteArrays.add(byteArray);
            }
        }
        return byteArray;
    }

    public int[] getIntArray(int i, boolean z) {
        int[] intArray = this.arrayCache.getIntArray(i, z);
        List<int[]> list = this.intArrays;
        if (list != null) {
            synchronized (list) {
                this.intArrays.add(intArray);
            }
        }
        return intArray;
    }

    public void putArray(byte[] bArr) {
        List<byte[]> list = this.byteArrays;
        if (list != null) {
            synchronized (list) {
                int lastIndexOf = this.byteArrays.lastIndexOf(bArr);
                if (lastIndexOf != -1) {
                    this.byteArrays.remove(lastIndexOf);
                }
            }
            this.arrayCache.putArray(bArr);
        }
    }

    public void putArray(int[] iArr) {
        List<int[]> list = this.intArrays;
        if (list != null) {
            synchronized (list) {
                int lastIndexOf = this.intArrays.lastIndexOf(iArr);
                if (lastIndexOf != -1) {
                    this.intArrays.remove(lastIndexOf);
                }
            }
            this.arrayCache.putArray(iArr);
        }
    }

    public void reset() {
        List<byte[]> list = this.byteArrays;
        if (list != null) {
            synchronized (list) {
                for (int size = this.byteArrays.size() - 1; size >= 0; size--) {
                    this.arrayCache.putArray(this.byteArrays.get(size));
                }
                this.byteArrays.clear();
            }
            synchronized (this.intArrays) {
                for (int size2 = this.intArrays.size() - 1; size2 >= 0; size2--) {
                    this.arrayCache.putArray(this.intArrays.get(size2));
                }
                this.intArrays.clear();
            }
        }
    }
}
