package org.tukaani.p013xz;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/* renamed from: org.tukaani.xz.BasicArrayCache */
public class BasicArrayCache extends ArrayCache {
    private static final int CACHEABLE_SIZE_MIN = 32768;
    private static final int ELEMENTS_PER_STACK = 512;
    private static final int STACKS_MAX = 32;
    private final CacheMap<byte[]> byteArrayCache = new CacheMap<>();
    private final CacheMap<int[]> intArrayCache = new CacheMap<>();

    /* renamed from: org.tukaani.xz.BasicArrayCache$CacheMap */
    private static class CacheMap<T> extends LinkedHashMap<Integer, CyclicStack<Reference<T>>> {
        private static final long serialVersionUID = 1;

        public CacheMap() {
            super(64, 0.75f, true);
        }

        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Map.Entry<Integer, CyclicStack<Reference<T>>> entry) {
            return size() > 32;
        }
    }

    /* renamed from: org.tukaani.xz.BasicArrayCache$CyclicStack */
    private static class CyclicStack<T> {
        private final T[] elements;
        private int pos;

        private CyclicStack() {
            this.elements = (Object[]) new Object[512];
            this.pos = 0;
        }

        public synchronized T pop() {
            T t;
            T[] tArr = this.elements;
            int i = this.pos;
            t = tArr[i];
            tArr[i] = null;
            this.pos = (i - 1) & 511;
            return t;
        }

        public synchronized void push(T t) {
            int i = (this.pos + 1) & 511;
            this.pos = i;
            this.elements[i] = t;
        }
    }

    /* renamed from: org.tukaani.xz.BasicArrayCache$LazyHolder */
    private static final class LazyHolder {
        static final BasicArrayCache INSTANCE = new BasicArrayCache();

        private LazyHolder() {
        }
    }

    private static <T> T getArray(CacheMap<T> cacheMap, int i) {
        CyclicStack cyclicStack;
        T t;
        if (i < 32768) {
            return null;
        }
        synchronized (cacheMap) {
            cyclicStack = (CyclicStack) cacheMap.get(Integer.valueOf(i));
        }
        if (cyclicStack == null) {
            return null;
        }
        do {
            Reference reference = (Reference) cyclicStack.pop();
            if (reference == null) {
                return null;
            }
            t = reference.get();
        } while (t == null);
        return t;
    }

    public static BasicArrayCache getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static <T> void putArray(CacheMap<T> cacheMap, T t, int i) {
        CyclicStack cyclicStack;
        if (i >= 32768) {
            synchronized (cacheMap) {
                cyclicStack = (CyclicStack) cacheMap.get(Integer.valueOf(i));
                if (cyclicStack == null) {
                    cyclicStack = new CyclicStack();
                    cacheMap.put(Integer.valueOf(i), cyclicStack);
                }
            }
            cyclicStack.push(new SoftReference(t));
        }
    }

    public byte[] getByteArray(int i, boolean z) {
        byte[] bArr = (byte[]) getArray(this.byteArrayCache, i);
        if (bArr == null) {
            return new byte[i];
        }
        if (!z) {
            return bArr;
        }
        Arrays.fill(bArr, (byte) 0);
        return bArr;
    }

    public int[] getIntArray(int i, boolean z) {
        int[] iArr = (int[]) getArray(this.intArrayCache, i);
        if (iArr == null) {
            return new int[i];
        }
        if (!z) {
            return iArr;
        }
        Arrays.fill(iArr, 0);
        return iArr;
    }

    public void putArray(byte[] bArr) {
        putArray(this.byteArrayCache, bArr, bArr.length);
    }

    public void putArray(int[] iArr) {
        putArray(this.intArrayCache, iArr, iArr.length);
    }
}
