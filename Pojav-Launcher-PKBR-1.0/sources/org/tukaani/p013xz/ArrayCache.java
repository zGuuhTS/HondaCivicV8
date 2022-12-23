package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.ArrayCache */
public class ArrayCache {
    private static volatile ArrayCache defaultCache;
    private static final ArrayCache dummyCache;

    static {
        ArrayCache arrayCache = new ArrayCache();
        dummyCache = arrayCache;
        defaultCache = arrayCache;
    }

    public static ArrayCache getDefaultCache() {
        return defaultCache;
    }

    public static ArrayCache getDummyCache() {
        return dummyCache;
    }

    public static void setDefaultCache(ArrayCache arrayCache) {
        if (arrayCache != null) {
            defaultCache = arrayCache;
            return;
        }
        throw new NullPointerException();
    }

    public byte[] getByteArray(int i, boolean z) {
        return new byte[i];
    }

    public int[] getIntArray(int i, boolean z) {
        return new int[i];
    }

    public void putArray(byte[] bArr) {
    }

    public void putArray(int[] iArr) {
    }
}
