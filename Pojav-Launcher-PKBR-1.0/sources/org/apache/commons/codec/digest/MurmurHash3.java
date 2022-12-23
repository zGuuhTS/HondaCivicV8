package org.apache.commons.codec.digest;

import kotlin.UByte;
import org.apache.commons.codec.binary.StringUtils;

public final class MurmurHash3 {

    /* renamed from: C1 */
    private static final long f123C1 = -8663945395140668459L;
    private static final int C1_32 = -862048943;

    /* renamed from: C2 */
    private static final long f124C2 = 5545529020109919103L;
    private static final int C2_32 = 461845907;
    public static final int DEFAULT_SEED = 104729;
    static final int INTEGER_BYTES = 4;
    static final int LONG_BYTES = 8;

    /* renamed from: M */
    private static final int f125M = 5;
    private static final int M_32 = 5;

    /* renamed from: N1 */
    private static final int f126N1 = 1390208809;

    /* renamed from: N2 */
    private static final int f127N2 = 944331445;
    @Deprecated
    public static final long NULL_HASHCODE = 2862933555777941757L;
    private static final int N_32 = -430675100;

    /* renamed from: R1 */
    private static final int f128R1 = 31;
    private static final int R1_32 = 15;

    /* renamed from: R2 */
    private static final int f129R2 = 27;
    private static final int R2_32 = 13;

    /* renamed from: R3 */
    private static final int f130R3 = 33;
    static final int SHORT_BYTES = 2;

    private MurmurHash3() {
    }

    public static int hash32(long data1, long data2) {
        return hash32(data1, data2, (int) DEFAULT_SEED);
    }

    public static int hash32(long data1, long data2, int seed) {
        long r0 = Long.reverseBytes(data1);
        long r1 = Long.reverseBytes(data2);
        int i = (int) (r0 >>> 32);
        return fmix32(mix32((int) (r1 >>> 32), mix32((int) r1, mix32(i, mix32((int) r0, seed)))) ^ 16);
    }

    public static int hash32(long data) {
        return hash32(data, (int) DEFAULT_SEED);
    }

    public static int hash32(long data, int seed) {
        long r0 = Long.reverseBytes(data);
        return fmix32(mix32((int) (r0 >>> 32), mix32((int) r0, seed)) ^ 8);
    }

    @Deprecated
    public static int hash32(byte[] data) {
        return hash32(data, 0, data.length, DEFAULT_SEED);
    }

    @Deprecated
    public static int hash32(String data) {
        byte[] bytes = StringUtils.getBytesUtf8(data);
        return hash32(bytes, 0, bytes.length, DEFAULT_SEED);
    }

    @Deprecated
    public static int hash32(byte[] data, int length) {
        return hash32(data, length, (int) DEFAULT_SEED);
    }

    @Deprecated
    public static int hash32(byte[] data, int length, int seed) {
        return hash32(data, 0, length, seed);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0026, code lost:
        r3 = r3 ^ (r5[r2 + 1] << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002d, code lost:
        r0 = r0 ^ (java.lang.Integer.rotateLeft((r3 ^ r5[r2]) * C1_32, 15) * C2_32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0044, code lost:
        return fmix32(r0 ^ r7);
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int hash32(byte[] r5, int r6, int r7, int r8) {
        /*
            r0 = r8
            int r1 = r7 >> 2
            r2 = 0
        L_0x0004:
            if (r2 >= r1) goto L_0x0014
            int r3 = r2 << 2
            int r3 = r3 + r6
            int r4 = getLittleEndianInt(r5, r3)
            int r0 = mix32(r4, r0)
            int r2 = r2 + 1
            goto L_0x0004
        L_0x0014:
            int r2 = r1 << 2
            int r2 = r2 + r6
            r3 = 0
            int r4 = r6 + r7
            int r4 = r4 - r2
            switch(r4) {
                case 1: goto L_0x002d;
                case 2: goto L_0x0026;
                case 3: goto L_0x001f;
                default: goto L_0x001e;
            }
        L_0x001e:
            goto L_0x003f
        L_0x001f:
            int r4 = r2 + 2
            byte r4 = r5[r4]
            int r4 = r4 << 16
            r3 = r3 ^ r4
        L_0x0026:
            int r4 = r2 + 1
            byte r4 = r5[r4]
            int r4 = r4 << 8
            r3 = r3 ^ r4
        L_0x002d:
            byte r4 = r5[r2]
            r3 = r3 ^ r4
            r4 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r3 = r3 * r4
            r4 = 15
            int r3 = java.lang.Integer.rotateLeft(r3, r4)
            r4 = 461845907(0x1b873593, float:2.2368498E-22)
            int r3 = r3 * r4
            r0 = r0 ^ r3
        L_0x003f:
            r0 = r0 ^ r7
            int r4 = fmix32(r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash3.hash32(byte[], int, int, int):int");
    }

    public static int hash32x86(byte[] data) {
        return hash32x86(data, 0, data.length, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0028, code lost:
        r3 = r3 ^ ((r5[r2 + 1] & kotlin.UByte.MAX_VALUE) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0031, code lost:
        r0 = r0 ^ (java.lang.Integer.rotateLeft((r3 ^ (r5[r2] & 255)) * C1_32, 15) * C2_32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x004a, code lost:
        return fmix32(r0 ^ r7);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int hash32x86(byte[] r5, int r6, int r7, int r8) {
        /*
            r0 = r8
            int r1 = r7 >> 2
            r2 = 0
        L_0x0004:
            if (r2 >= r1) goto L_0x0014
            int r3 = r2 << 2
            int r3 = r3 + r6
            int r4 = getLittleEndianInt(r5, r3)
            int r0 = mix32(r4, r0)
            int r2 = r2 + 1
            goto L_0x0004
        L_0x0014:
            int r2 = r1 << 2
            int r2 = r2 + r6
            r3 = 0
            int r4 = r6 + r7
            int r4 = r4 - r2
            switch(r4) {
                case 1: goto L_0x0031;
                case 2: goto L_0x0028;
                case 3: goto L_0x001f;
                default: goto L_0x001e;
            }
        L_0x001e:
            goto L_0x0045
        L_0x001f:
            int r4 = r2 + 2
            byte r4 = r5[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 16
            r3 = r3 ^ r4
        L_0x0028:
            int r4 = r2 + 1
            byte r4 = r5[r4]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 8
            r3 = r3 ^ r4
        L_0x0031:
            byte r4 = r5[r2]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r3 = r3 ^ r4
            r4 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r3 = r3 * r4
            r4 = 15
            int r3 = java.lang.Integer.rotateLeft(r3, r4)
            r4 = 461845907(0x1b873593, float:2.2368498E-22)
            int r3 = r3 * r4
            r0 = r0 ^ r3
        L_0x0045:
            r0 = r0 ^ r7
            int r4 = fmix32(r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash3.hash32x86(byte[], int, int, int):int");
    }

    @Deprecated
    public static long hash64(long data) {
        return fmix64(8 ^ ((Long.rotateLeft(104729 ^ (Long.rotateLeft(Long.reverseBytes(data) * f123C1, 31) * f124C2), 27) * 5) + 1390208809));
    }

    @Deprecated
    public static long hash64(int data) {
        return fmix64((104729 ^ (Long.rotateLeft((((long) Integer.reverseBytes(data)) & 4294967295L) * f123C1, 31) * f124C2)) ^ 4);
    }

    @Deprecated
    public static long hash64(short data) {
        return fmix64((104729 ^ (Long.rotateLeft(((0 ^ ((((long) data) & 255) << 8)) ^ (((long) ((65280 & data) >> 8)) & 255)) * f123C1, 31) * f124C2)) ^ 2);
    }

    @Deprecated
    public static long hash64(byte[] data) {
        return hash64(data, 0, data.length, DEFAULT_SEED);
    }

    @Deprecated
    public static long hash64(byte[] data, int offset, int length) {
        return hash64(data, offset, length, DEFAULT_SEED);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x006f, code lost:
        r12 = r12 ^ ((((long) r0[r6 + 2]) & 255) << 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0079, code lost:
        r12 = r12 ^ ((((long) r0[r6 + 1]) & 255) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0083, code lost:
        r3 = r3 ^ (java.lang.Long.rotateLeft(((((long) r0[r6]) & 255) ^ r12) * f123C1, 31) * f124C2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x009b, code lost:
        return fmix64(r3 ^ ((long) r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0051, code lost:
        r12 = r12 ^ ((((long) r0[r6 + 5]) & 255) << 40);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x005b, code lost:
        r12 = r12 ^ ((((long) r0[r6 + 4]) & 255) << 32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0065, code lost:
        r12 = r12 ^ ((((long) r0[r6 + 3]) & 255) << 24);
     */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long hash64(byte[] r17, int r18, int r19, int r20) {
        /*
            r0 = r17
            r1 = r19
            r2 = r20
            long r3 = (long) r2
            int r5 = r1 >> 3
            r6 = 0
        L_0x000a:
            r7 = 5545529020109919103(0x4cf5ad432745937f, double:5.573325460219186E62)
            r9 = 31
            r10 = -8663945395140668459(0x87c37b91114253d5, double:-2.8811287363897357E-271)
            if (r6 >= r5) goto L_0x0038
            int r12 = r6 << 3
            int r12 = r18 + r12
            long r13 = getLittleEndianLong(r0, r12)
            long r13 = r13 * r10
            long r9 = java.lang.Long.rotateLeft(r13, r9)
            long r9 = r9 * r7
            long r3 = r3 ^ r9
            r7 = 27
            long r7 = java.lang.Long.rotateLeft(r3, r7)
            r13 = 5
            long r7 = r7 * r13
            r13 = 1390208809(0x52dce729, double:6.86854413E-315)
            long r3 = r7 + r13
            int r6 = r6 + 1
            goto L_0x000a
        L_0x0038:
            r12 = 0
            int r6 = r5 << 3
            int r6 = r18 + r6
            int r14 = r18 + r1
            int r14 = r14 - r6
            r15 = 255(0xff, double:1.26E-321)
            switch(r14) {
                case 1: goto L_0x0083;
                case 2: goto L_0x0079;
                case 3: goto L_0x006f;
                case 4: goto L_0x0065;
                case 5: goto L_0x005b;
                case 6: goto L_0x0051;
                case 7: goto L_0x0047;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x0095
        L_0x0047:
            int r14 = r6 + 6
            byte r14 = r0[r14]
            long r7 = (long) r14
            long r7 = r7 & r15
            r14 = 48
            long r7 = r7 << r14
            long r12 = r12 ^ r7
        L_0x0051:
            int r7 = r6 + 5
            byte r7 = r0[r7]
            long r7 = (long) r7
            long r7 = r7 & r15
            r14 = 40
            long r7 = r7 << r14
            long r12 = r12 ^ r7
        L_0x005b:
            int r7 = r6 + 4
            byte r7 = r0[r7]
            long r7 = (long) r7
            long r7 = r7 & r15
            r14 = 32
            long r7 = r7 << r14
            long r12 = r12 ^ r7
        L_0x0065:
            int r7 = r6 + 3
            byte r7 = r0[r7]
            long r7 = (long) r7
            long r7 = r7 & r15
            r14 = 24
            long r7 = r7 << r14
            long r12 = r12 ^ r7
        L_0x006f:
            int r7 = r6 + 2
            byte r7 = r0[r7]
            long r7 = (long) r7
            long r7 = r7 & r15
            r14 = 16
            long r7 = r7 << r14
            long r12 = r12 ^ r7
        L_0x0079:
            int r7 = r6 + 1
            byte r7 = r0[r7]
            long r7 = (long) r7
            long r7 = r7 & r15
            r14 = 8
            long r7 = r7 << r14
            long r12 = r12 ^ r7
        L_0x0083:
            byte r7 = r0[r6]
            long r7 = (long) r7
            long r7 = r7 & r15
            long r7 = r7 ^ r12
            long r7 = r7 * r10
            long r7 = java.lang.Long.rotateLeft(r7, r9)
            r9 = 5545529020109919103(0x4cf5ad432745937f, double:5.573325460219186E62)
            long r12 = r7 * r9
            long r3 = r3 ^ r12
        L_0x0095:
            long r7 = (long) r1
            long r3 = r3 ^ r7
            long r3 = fmix64(r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash3.hash64(byte[], int, int, int):long");
    }

    public static long[] hash128(byte[] data) {
        return hash128(data, 0, data.length, DEFAULT_SEED);
    }

    public static long[] hash128x64(byte[] data) {
        return hash128x64(data, 0, data.length, 0);
    }

    @Deprecated
    public static long[] hash128(String data) {
        byte[] bytes = StringUtils.getBytesUtf8(data);
        return hash128(bytes, 0, bytes.length, DEFAULT_SEED);
    }

    @Deprecated
    public static long[] hash128(byte[] data, int offset, int length, int seed) {
        return hash128x64(data, offset, length, seed);
    }

    public static long[] hash128x64(byte[] data, int offset, int length, int seed) {
        return hash128x64(data, offset, length, ((long) seed) & 4294967295L);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x009c, code lost:
        r14 = r14 ^ ((((long) r0[r7 + 12]) & 255) << 32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00a9, code lost:
        r14 = r14 ^ ((((long) r0[r7 + 11]) & 255) << 24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00b6, code lost:
        r14 = r14 ^ ((((long) r0[r7 + 10]) & 255) << 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00c3, code lost:
        r14 = r14 ^ ((((long) r0[r7 + 9]) & 255) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00d0, code lost:
        r4 = r4 ^ (java.lang.Long.rotateLeft((((long) (r0[r7 + 8] & kotlin.UByte.MAX_VALUE)) ^ r14) * f124C2, 33) * f123C1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ea, code lost:
        r9 = r26 ^ ((((long) r0[r7 + 7]) & 255) << 56);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00f9, code lost:
        r9 = r9 ^ ((((long) r0[r7 + 6]) & 255) << 48);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0106, code lost:
        r9 = r9 ^ ((((long) r0[r7 + 5]) & 255) << 40);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0113, code lost:
        r9 = r9 ^ ((((long) r0[r7 + 4]) & 255) << 32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0120, code lost:
        r9 = r9 ^ ((((long) r0[r7 + 3]) & 255) << 24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x012d, code lost:
        r9 = r9 ^ ((((long) r0[r7 + 2]) & 255) << 16);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x013a, code lost:
        r9 = r9 ^ ((((long) r0[r7 + 1]) & 255) << 8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0147, code lost:
        r2 = r2 ^ (java.lang.Long.rotateLeft((r9 ^ ((long) (r0[r7] & kotlin.UByte.MAX_VALUE))) * f123C1, 31) * f124C2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0162, code lost:
        r4 = r4 ^ ((long) r1);
        r2 = (r2 ^ ((long) r1)) + r4;
        r4 = r4 + r2;
        r2 = fmix64(r2);
        r4 = fmix64(r4);
        r2 = r2 + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x017b, code lost:
        return new long[]{r2, r4 + r2};
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x008f, code lost:
        r14 = r14 ^ ((((long) r0[r7 + 13]) & 255) << 40);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long[] hash128x64(byte[] r28, int r29, int r30, long r31) {
        /*
            r0 = r28
            r1 = r30
            r2 = r31
            r4 = r31
            int r6 = r1 >> 4
            r7 = 0
        L_0x000b:
            r9 = 31
            r12 = -8663945395140668459(0x87c37b91114253d5, double:-2.8811287363897357E-271)
            if (r7 >= r6) goto L_0x0060
            int r14 = r7 << 4
            int r14 = r29 + r14
            long r15 = getLittleEndianLong(r0, r14)
            int r8 = r14 + 8
            long r17 = getLittleEndianLong(r0, r8)
            long r10 = r15 * r12
            long r10 = java.lang.Long.rotateLeft(r10, r9)
            r15 = 5545529020109919103(0x4cf5ad432745937f, double:5.573325460219186E62)
            long r10 = r10 * r15
            long r2 = r2 ^ r10
            r8 = 27
            long r2 = java.lang.Long.rotateLeft(r2, r8)
            long r2 = r2 + r4
            r15 = 5
            long r21 = r2 * r15
            r23 = 1390208809(0x52dce729, double:6.86854413E-315)
            long r2 = r21 + r23
            r21 = r10
            r19 = 5545529020109919103(0x4cf5ad432745937f, double:5.573325460219186E62)
            long r9 = r17 * r19
            r11 = 33
            long r9 = java.lang.Long.rotateLeft(r9, r11)
            long r9 = r9 * r12
            long r4 = r4 ^ r9
            r8 = 31
            long r4 = java.lang.Long.rotateLeft(r4, r8)
            long r4 = r4 + r2
            long r15 = r15 * r4
            r11 = 944331445(0x38495ab5, double:4.665617253E-315)
            long r4 = r15 + r11
            int r7 = r7 + 1
            goto L_0x000b
        L_0x0060:
            r9 = 0
            r14 = 0
            int r7 = r6 << 4
            int r7 = r29 + r7
            int r11 = r29 + r1
            int r11 = r11 - r7
            r16 = 48
            r17 = 40
            r18 = 32
            r21 = 24
            r22 = 16
            r23 = 8
            r24 = 255(0xff, double:1.26E-321)
            switch(r11) {
                case 1: goto L_0x0145;
                case 2: goto L_0x0138;
                case 3: goto L_0x012b;
                case 4: goto L_0x011e;
                case 5: goto L_0x0111;
                case 6: goto L_0x0104;
                case 7: goto L_0x00f7;
                case 8: goto L_0x00e8;
                case 9: goto L_0x00ce;
                case 10: goto L_0x00c1;
                case 11: goto L_0x00b4;
                case 12: goto L_0x00a7;
                case 13: goto L_0x009a;
                case 14: goto L_0x008d;
                case 15: goto L_0x0080;
                default: goto L_0x007c;
            }
        L_0x007c:
            r26 = r9
            goto L_0x0162
        L_0x0080:
            int r11 = r7 + 14
            byte r11 = r0[r11]
            r26 = r9
            long r8 = (long) r11
            long r8 = r8 & r24
            long r8 = r8 << r16
            long r14 = r14 ^ r8
            goto L_0x008f
        L_0x008d:
            r26 = r9
        L_0x008f:
            int r8 = r7 + 13
            byte r8 = r0[r8]
            long r8 = (long) r8
            long r8 = r8 & r24
            long r8 = r8 << r17
            long r14 = r14 ^ r8
            goto L_0x009c
        L_0x009a:
            r26 = r9
        L_0x009c:
            int r8 = r7 + 12
            byte r8 = r0[r8]
            long r8 = (long) r8
            long r8 = r8 & r24
            long r8 = r8 << r18
            long r14 = r14 ^ r8
            goto L_0x00a9
        L_0x00a7:
            r26 = r9
        L_0x00a9:
            int r8 = r7 + 11
            byte r8 = r0[r8]
            long r8 = (long) r8
            long r8 = r8 & r24
            long r8 = r8 << r21
            long r14 = r14 ^ r8
            goto L_0x00b6
        L_0x00b4:
            r26 = r9
        L_0x00b6:
            int r8 = r7 + 10
            byte r8 = r0[r8]
            long r8 = (long) r8
            long r8 = r8 & r24
            long r8 = r8 << r22
            long r14 = r14 ^ r8
            goto L_0x00c3
        L_0x00c1:
            r26 = r9
        L_0x00c3:
            int r8 = r7 + 9
            byte r8 = r0[r8]
            long r8 = (long) r8
            long r8 = r8 & r24
            long r8 = r8 << r23
            long r14 = r14 ^ r8
            goto L_0x00d0
        L_0x00ce:
            r26 = r9
        L_0x00d0:
            int r8 = r7 + 8
            byte r8 = r0[r8]
            r8 = r8 & 255(0xff, float:3.57E-43)
            long r8 = (long) r8
            long r8 = r8 ^ r14
            r10 = 5545529020109919103(0x4cf5ad432745937f, double:5.573325460219186E62)
            long r8 = r8 * r10
            r10 = 33
            long r8 = java.lang.Long.rotateLeft(r8, r10)
            long r14 = r8 * r12
            long r4 = r4 ^ r14
            goto L_0x00ea
        L_0x00e8:
            r26 = r9
        L_0x00ea:
            int r8 = r7 + 7
            byte r8 = r0[r8]
            long r8 = (long) r8
            long r8 = r8 & r24
            r10 = 56
            long r8 = r8 << r10
            long r9 = r26 ^ r8
            goto L_0x00f9
        L_0x00f7:
            r26 = r9
        L_0x00f9:
            int r8 = r7 + 6
            byte r8 = r0[r8]
            long r12 = (long) r8
            long r11 = r12 & r24
            long r11 = r11 << r16
            long r9 = r9 ^ r11
            goto L_0x0106
        L_0x0104:
            r26 = r9
        L_0x0106:
            int r8 = r7 + 5
            byte r8 = r0[r8]
            long r11 = (long) r8
            long r11 = r11 & r24
            long r11 = r11 << r17
            long r9 = r9 ^ r11
            goto L_0x0113
        L_0x0111:
            r26 = r9
        L_0x0113:
            int r8 = r7 + 4
            byte r8 = r0[r8]
            long r11 = (long) r8
            long r11 = r11 & r24
            long r11 = r11 << r18
            long r9 = r9 ^ r11
            goto L_0x0120
        L_0x011e:
            r26 = r9
        L_0x0120:
            int r8 = r7 + 3
            byte r8 = r0[r8]
            long r11 = (long) r8
            long r11 = r11 & r24
            long r11 = r11 << r21
            long r9 = r9 ^ r11
            goto L_0x012d
        L_0x012b:
            r26 = r9
        L_0x012d:
            int r8 = r7 + 2
            byte r8 = r0[r8]
            long r11 = (long) r8
            long r11 = r11 & r24
            long r11 = r11 << r22
            long r9 = r9 ^ r11
            goto L_0x013a
        L_0x0138:
            r26 = r9
        L_0x013a:
            int r8 = r7 + 1
            byte r8 = r0[r8]
            long r11 = (long) r8
            long r11 = r11 & r24
            long r11 = r11 << r23
            long r9 = r9 ^ r11
            goto L_0x0147
        L_0x0145:
            r26 = r9
        L_0x0147:
            byte r8 = r0[r7]
            r8 = r8 & 255(0xff, float:3.57E-43)
            long r11 = (long) r8
            long r8 = r9 ^ r11
            r10 = -8663945395140668459(0x87c37b91114253d5, double:-2.8811287363897357E-271)
            long r8 = r8 * r10
            r10 = 31
            long r8 = java.lang.Long.rotateLeft(r8, r10)
            r10 = 5545529020109919103(0x4cf5ad432745937f, double:5.573325460219186E62)
            long r9 = r8 * r10
            long r2 = r2 ^ r9
        L_0x0162:
            long r11 = (long) r1
            long r2 = r2 ^ r11
            long r11 = (long) r1
            long r4 = r4 ^ r11
            long r2 = r2 + r4
            long r4 = r4 + r2
            long r2 = fmix64(r2)
            long r4 = fmix64(r4)
            long r2 = r2 + r4
            long r4 = r4 + r2
            r8 = 2
            long[] r8 = new long[r8]
            r11 = 0
            r8[r11] = r2
            r11 = 1
            r8[r11] = r4
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash3.hash128x64(byte[], int, int, long):long[]");
    }

    private static long getLittleEndianLong(byte[] data, int index) {
        return (((long) data[index]) & 255) | ((((long) data[index + 1]) & 255) << 8) | ((((long) data[index + 2]) & 255) << 16) | ((((long) data[index + 3]) & 255) << 24) | ((((long) data[index + 4]) & 255) << 32) | ((((long) data[index + 5]) & 255) << 40) | ((((long) data[index + 6]) & 255) << 48) | ((255 & ((long) data[index + 7])) << 56);
    }

    /* access modifiers changed from: private */
    public static int getLittleEndianInt(byte[] data, int index) {
        return (data[index] & UByte.MAX_VALUE) | ((data[index + 1] & UByte.MAX_VALUE) << 8) | ((data[index + 2] & UByte.MAX_VALUE) << 16) | ((data[index + 3] & UByte.MAX_VALUE) << 24);
    }

    /* access modifiers changed from: private */
    public static int mix32(int k, int hash) {
        return (Integer.rotateLeft(hash ^ (Integer.rotateLeft(k * C1_32, 15) * C2_32), 13) * 5) + N_32;
    }

    /* access modifiers changed from: private */
    public static int fmix32(int hash) {
        int hash2 = (hash ^ (hash >>> 16)) * -2048144789;
        int hash3 = (hash2 ^ (hash2 >>> 13)) * -1028477387;
        return hash3 ^ (hash3 >>> 16);
    }

    private static long fmix64(long hash) {
        long hash2 = (hash ^ (hash >>> 33)) * -49064778989728563L;
        long hash3 = (hash2 ^ (hash2 >>> 33)) * -4265267296055464877L;
        return hash3 ^ (hash3 >>> 33);
    }

    public static class IncrementalHash32x86 {
        private static final int BLOCK_SIZE = 4;
        private int hash;
        private int totalLen;
        private final byte[] unprocessed = new byte[3];
        private int unprocessedLength;

        public final void start(int seed) {
            this.totalLen = 0;
            this.unprocessedLength = 0;
            this.hash = seed;
        }

        public final void add(byte[] data, int offset, int length) {
            int newOffset;
            int k;
            int k2;
            if (length > 0) {
                this.totalLen += length;
                int i = this.unprocessedLength;
                if ((i + length) - 4 < 0) {
                    System.arraycopy(data, offset, this.unprocessed, i, length);
                    this.unprocessedLength += length;
                    return;
                }
                if (i > 0) {
                    switch (i) {
                        case 1:
                            k2 = orBytes(this.unprocessed[0], data[offset], data[offset + 1], data[offset + 2]);
                            break;
                        case 2:
                            byte[] bArr = this.unprocessed;
                            k2 = orBytes(bArr[0], bArr[1], data[offset], data[offset + 1]);
                            break;
                        case 3:
                            byte[] bArr2 = this.unprocessed;
                            k2 = orBytes(bArr2[0], bArr2[1], bArr2[2], data[offset]);
                            break;
                        default:
                            throw new IllegalStateException("Unprocessed length should be 1, 2, or 3: " + this.unprocessedLength);
                    }
                    this.hash = MurmurHash3.mix32(k2, this.hash);
                    int consumed = 4 - this.unprocessedLength;
                    newOffset = offset + consumed;
                    k = length - consumed;
                } else {
                    newOffset = offset;
                    k = length;
                }
                int nblocks = k >> 2;
                for (int i2 = 0; i2 < nblocks; i2++) {
                    this.hash = MurmurHash3.mix32(MurmurHash3.getLittleEndianInt(data, (i2 << 2) + newOffset), this.hash);
                }
                int i3 = nblocks << 2;
                int i4 = k - i3;
                this.unprocessedLength = i4;
                if (i4 != 0) {
                    System.arraycopy(data, newOffset + i3, this.unprocessed, 0, i4);
                }
            }
        }

        public final int end() {
            return finalise(this.hash, this.unprocessedLength, this.unprocessed, this.totalLen);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:3:0x000e, code lost:
            r1 = r1 ^ ((r6[1] & kotlin.UByte.MAX_VALUE) << 8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:4:0x0016, code lost:
            r0 = r0 ^ (java.lang.Integer.rotateLeft((r1 ^ (r6[0] & 255)) * org.apache.commons.codec.digest.MurmurHash3.C1_32, 15) * org.apache.commons.codec.digest.MurmurHash3.C2_32);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x0030, code lost:
            return org.apache.commons.codec.digest.MurmurHash3.access$200(r0 ^ r7);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int finalise(int r4, int r5, byte[] r6, int r7) {
            /*
                r3 = this;
                r0 = r4
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x0016;
                    case 2: goto L_0x000e;
                    case 3: goto L_0x0006;
                    default: goto L_0x0005;
                }
            L_0x0005:
                goto L_0x002b
            L_0x0006:
                r2 = 2
                byte r2 = r6[r2]
                r2 = r2 & 255(0xff, float:3.57E-43)
                int r2 = r2 << 16
                r1 = r1 ^ r2
            L_0x000e:
                r2 = 1
                byte r2 = r6[r2]
                r2 = r2 & 255(0xff, float:3.57E-43)
                int r2 = r2 << 8
                r1 = r1 ^ r2
            L_0x0016:
                r2 = 0
                byte r2 = r6[r2]
                r2 = r2 & 255(0xff, float:3.57E-43)
                r1 = r1 ^ r2
                r2 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
                int r1 = r1 * r2
                r2 = 15
                int r1 = java.lang.Integer.rotateLeft(r1, r2)
                r2 = 461845907(0x1b873593, float:2.2368498E-22)
                int r1 = r1 * r2
                r0 = r0 ^ r1
            L_0x002b:
                r0 = r0 ^ r7
                int r2 = org.apache.commons.codec.digest.MurmurHash3.fmix32(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash3.IncrementalHash32x86.finalise(int, int, byte[], int):int");
        }

        private static int orBytes(byte b1, byte b2, byte b3, byte b4) {
            return (b1 & UByte.MAX_VALUE) | ((b2 & UByte.MAX_VALUE) << 8) | ((b3 & UByte.MAX_VALUE) << 16) | ((b4 & UByte.MAX_VALUE) << 24);
        }
    }

    @Deprecated
    public static class IncrementalHash32 extends IncrementalHash32x86 {
        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:3:0x000c, code lost:
            r1 = r1 ^ (r6[1] << 8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:4:0x0012, code lost:
            r0 = r0 ^ (java.lang.Integer.rotateLeft((r1 ^ r6[0]) * org.apache.commons.codec.digest.MurmurHash3.C1_32, 15) * org.apache.commons.codec.digest.MurmurHash3.C2_32);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:6:0x002a, code lost:
            return org.apache.commons.codec.digest.MurmurHash3.access$200(r0 ^ r7);
         */
        @java.lang.Deprecated
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int finalise(int r4, int r5, byte[] r6, int r7) {
            /*
                r3 = this;
                r0 = r4
                r1 = 0
                switch(r5) {
                    case 1: goto L_0x0012;
                    case 2: goto L_0x000c;
                    case 3: goto L_0x0006;
                    default: goto L_0x0005;
                }
            L_0x0005:
                goto L_0x0025
            L_0x0006:
                r2 = 2
                byte r2 = r6[r2]
                int r2 = r2 << 16
                r1 = r1 ^ r2
            L_0x000c:
                r2 = 1
                byte r2 = r6[r2]
                int r2 = r2 << 8
                r1 = r1 ^ r2
            L_0x0012:
                r2 = 0
                byte r2 = r6[r2]
                r1 = r1 ^ r2
                r2 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
                int r1 = r1 * r2
                r2 = 15
                int r1 = java.lang.Integer.rotateLeft(r1, r2)
                r2 = 461845907(0x1b873593, float:2.2368498E-22)
                int r1 = r1 * r2
                r0 = r0 ^ r1
            L_0x0025:
                r0 = r0 ^ r7
                int r2 = org.apache.commons.codec.digest.MurmurHash3.fmix32(r0)
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.digest.MurmurHash3.IncrementalHash32.finalise(int, int, byte[], int):int");
        }
    }
}
