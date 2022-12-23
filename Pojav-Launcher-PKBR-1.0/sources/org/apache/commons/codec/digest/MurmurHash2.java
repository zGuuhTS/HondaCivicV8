package org.apache.commons.codec.digest;

import kotlin.UByte;
import org.apache.commons.codec.binary.StringUtils;

public final class MurmurHash2 {
    private static final int M32 = 1540483477;
    private static final long M64 = -4132994306676758123L;
    private static final int R32 = 24;
    private static final int R64 = 47;

    private MurmurHash2() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0041, code lost:
        return r0 ^ (r0 >>> 15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002b, code lost:
        r0 = r0 ^ ((r7[r2 + 1] & kotlin.UByte.MAX_VALUE) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0034, code lost:
        r0 = (r0 ^ (r7[r2] & 255)) * M32;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003a, code lost:
        r0 = (r0 ^ (r0 >>> 13)) * M32;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int hash32(byte[] r7, int r8, int r9) {
        /*
            r0 = r9 ^ r8
            int r1 = r8 >> 2
            r2 = 0
        L_0x0005:
            r3 = 1540483477(0x5bd1e995, float:1.18170193E17)
            if (r2 >= r1) goto L_0x001a
            int r4 = r2 << 2
            int r5 = getLittleEndianInt(r7, r4)
            int r5 = r5 * r3
            int r6 = r5 >>> 24
            r5 = r5 ^ r6
            int r5 = r5 * r3
            int r0 = r0 * r3
            r0 = r0 ^ r5
            int r2 = r2 + 1
            goto L_0x0005
        L_0x001a:
            int r2 = r1 << 2
            int r4 = r8 - r2
            switch(r4) {
                case 1: goto L_0x0034;
                case 2: goto L_0x002b;
                case 3: goto L_0x0022;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x003a
        L_0x0022:
            int r4 = r2 + 2
            byte r4 = r7[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 16
            r0 = r0 ^ r4
        L_0x002b:
            int r4 = r2 + 1
            byte r4 = r7[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 8
            r0 = r0 ^ r4
        L_0x0034:
            byte r4 = r7[r2]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r0 = r0 ^ r4
            int r0 = r0 * r3
        L_0x003a:
            int r4 = r0 >>> 13
            r0 = r0 ^ r4
            int r0 = r0 * r3
            int r3 = r0 >>> 15
            r0 = r0 ^ r3
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash2.hash32(byte[], int, int):int");
    }

    public static int hash32(byte[] data, int length) {
        return hash32(data, length, -1756908916);
    }

    public static int hash32(String text) {
        byte[] bytes = StringUtils.getBytesUtf8(text);
        return hash32(bytes, bytes.length);
    }

    public static int hash32(String text, int from, int length) {
        return hash32(text.substring(from, from + length));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0058, code lost:
        r0 = r0 ^ ((((long) r12[r3 + 2]) & 255) << 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0062, code lost:
        r0 = r0 ^ ((((long) r12[r3 + 1]) & 255) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x006c, code lost:
        r0 = (r0 ^ (((long) r12[r3]) & 255)) * M64;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0073, code lost:
        r0 = (r0 ^ (r0 >>> 47)) * M64;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x007a, code lost:
        return r0 ^ (r0 >>> 47);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003a, code lost:
        r0 = r0 ^ ((((long) r12[r3 + 5]) & 255) << 40);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0044, code lost:
        r0 = r0 ^ ((((long) r12[r3 + 4]) & 255) << 32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x004e, code lost:
        r0 = r0 ^ ((((long) r12[r3 + 3]) & 255) << 24);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long hash64(byte[] r12, int r13, int r14) {
        /*
            long r0 = (long) r14
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = r0 & r2
            long r2 = (long) r13
            r4 = -4132994306676758123(0xc6a4a7935bd1e995, double:-2.0946245025644615E32)
            long r2 = r2 * r4
            long r0 = r0 ^ r2
            int r2 = r13 >> 3
            r3 = 0
        L_0x0012:
            r6 = 47
            if (r3 >= r2) goto L_0x0026
            int r7 = r3 << 3
            long r8 = getLittleEndianLong(r12, r7)
            long r8 = r8 * r4
            long r10 = r8 >>> r6
            long r8 = r8 ^ r10
            long r8 = r8 * r4
            long r0 = r0 ^ r8
            long r0 = r0 * r4
            int r3 = r3 + 1
            goto L_0x0012
        L_0x0026:
            int r3 = r2 << 3
            int r7 = r13 - r3
            r8 = 255(0xff, double:1.26E-321)
            switch(r7) {
                case 1: goto L_0x006c;
                case 2: goto L_0x0062;
                case 3: goto L_0x0058;
                case 4: goto L_0x004e;
                case 5: goto L_0x0044;
                case 6: goto L_0x003a;
                case 7: goto L_0x0030;
                default: goto L_0x002f;
            }
        L_0x002f:
            goto L_0x0073
        L_0x0030:
            int r7 = r3 + 6
            byte r7 = r12[r7]
            long r10 = (long) r7
            long r10 = r10 & r8
            r7 = 48
            long r10 = r10 << r7
            long r0 = r0 ^ r10
        L_0x003a:
            int r7 = r3 + 5
            byte r7 = r12[r7]
            long r10 = (long) r7
            long r10 = r10 & r8
            r7 = 40
            long r10 = r10 << r7
            long r0 = r0 ^ r10
        L_0x0044:
            int r7 = r3 + 4
            byte r7 = r12[r7]
            long r10 = (long) r7
            long r10 = r10 & r8
            r7 = 32
            long r10 = r10 << r7
            long r0 = r0 ^ r10
        L_0x004e:
            int r7 = r3 + 3
            byte r7 = r12[r7]
            long r10 = (long) r7
            long r10 = r10 & r8
            r7 = 24
            long r10 = r10 << r7
            long r0 = r0 ^ r10
        L_0x0058:
            int r7 = r3 + 2
            byte r7 = r12[r7]
            long r10 = (long) r7
            long r10 = r10 & r8
            r7 = 16
            long r10 = r10 << r7
            long r0 = r0 ^ r10
        L_0x0062:
            int r7 = r3 + 1
            byte r7 = r12[r7]
            long r10 = (long) r7
            long r10 = r10 & r8
            r7 = 8
            long r10 = r10 << r7
            long r0 = r0 ^ r10
        L_0x006c:
            byte r7 = r12[r3]
            long r10 = (long) r7
            long r7 = r10 & r8
            long r0 = r0 ^ r7
            long r0 = r0 * r4
        L_0x0073:
            long r7 = r0 >>> r6
            long r0 = r0 ^ r7
            long r0 = r0 * r4
            long r4 = r0 >>> r6
            long r0 = r0 ^ r4
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash2.hash64(byte[], int, int):long");
    }

    public static long hash64(byte[] data, int length) {
        return hash64(data, length, -512093083);
    }

    public static long hash64(String text) {
        byte[] bytes = StringUtils.getBytesUtf8(text);
        return hash64(bytes, bytes.length);
    }

    public static long hash64(String text, int from, int length) {
        return hash64(text.substring(from, from + length));
    }

    private static int getLittleEndianInt(byte[] data, int index) {
        return (data[index] & UByte.MAX_VALUE) | ((data[index + 1] & UByte.MAX_VALUE) << 8) | ((data[index + 2] & UByte.MAX_VALUE) << 16) | ((data[index + 3] & UByte.MAX_VALUE) << 24);
    }

    private static long getLittleEndianLong(byte[] data, int index) {
        return (((long) data[index]) & 255) | ((((long) data[index + 1]) & 255) << 8) | ((((long) data[index + 2]) & 255) << 16) | ((((long) data[index + 3]) & 255) << 24) | ((((long) data[index + 4]) & 255) << 32) | ((((long) data[index + 5]) & 255) << 40) | ((((long) data[index + 6]) & 255) << 48) | ((255 & ((long) data[index + 7])) << 56);
    }
}
