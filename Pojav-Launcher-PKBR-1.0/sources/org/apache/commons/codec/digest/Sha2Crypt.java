package org.apache.commons.codec.digest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.UByte;

public class Sha2Crypt {
    private static final int ROUNDS_DEFAULT = 5000;
    private static final int ROUNDS_MAX = 999999999;
    private static final int ROUNDS_MIN = 1000;
    private static final String ROUNDS_PREFIX = "rounds=";
    private static final Pattern SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");
    private static final int SHA256_BLOCKSIZE = 32;
    static final String SHA256_PREFIX = "$5$";
    private static final int SHA512_BLOCKSIZE = 64;
    static final String SHA512_PREFIX = "$6$";

    public static String sha256Crypt(byte[] keyBytes) {
        return sha256Crypt(keyBytes, (String) null);
    }

    public static String sha256Crypt(byte[] keyBytes, String salt) {
        if (salt == null) {
            salt = SHA256_PREFIX + B64.getRandomSalt(8);
        }
        return sha2Crypt(keyBytes, salt, SHA256_PREFIX, 32, MessageDigestAlgorithms.SHA_256);
    }

    public static String sha256Crypt(byte[] keyBytes, String salt, Random random) {
        if (salt == null) {
            salt = SHA256_PREFIX + B64.getRandomSalt(8, random);
        }
        return sha2Crypt(keyBytes, salt, SHA256_PREFIX, 32, MessageDigestAlgorithms.SHA_256);
    }

    private static String sha2Crypt(byte[] keyBytes, String salt, String saltPrefix, int blocksize, String algorithm) {
        MessageDigest ctx;
        byte[] altResult;
        byte b;
        int i;
        byte[] bArr = keyBytes;
        String str = salt;
        int i2 = blocksize;
        int keyLen = bArr.length;
        int rounds = 5000;
        boolean roundsCustom = false;
        if (str != null) {
            Matcher m = SALT_PATTERN.matcher(str);
            if (m.find()) {
                if (m.group(3) != null) {
                    rounds = Math.max(1000, Math.min(ROUNDS_MAX, Integer.parseInt(m.group(3))));
                    roundsCustom = true;
                }
                String saltString = m.group(4);
                byte[] saltBytes = saltString.getBytes(StandardCharsets.UTF_8);
                int saltLen = saltBytes.length;
                MessageDigest ctx2 = DigestUtils.getDigest(algorithm);
                ctx2.update(bArr);
                ctx2.update(saltBytes);
                MessageDigest altCtx = DigestUtils.getDigest(algorithm);
                altCtx.update(bArr);
                altCtx.update(saltBytes);
                altCtx.update(bArr);
                byte[] altResult2 = altCtx.digest();
                int cnt = bArr.length;
                while (cnt > i2) {
                    ctx2.update(altResult2, 0, i2);
                    cnt -= i2;
                }
                ctx2.update(altResult2, 0, cnt);
                int cnt2 = bArr.length;
                while (cnt2 > 0) {
                    if ((cnt2 & 1) != 0) {
                        ctx2.update(altResult2, 0, i2);
                    } else {
                        ctx2.update(bArr);
                    }
                    cnt2 >>= 1;
                }
                byte[] altResult3 = ctx2.digest();
                MessageDigest altCtx2 = DigestUtils.getDigest(algorithm);
                for (int i3 = 1; i3 <= keyLen; i3++) {
                    altCtx2.update(bArr);
                }
                byte[] tempResult = altCtx2.digest();
                byte[] pBytes = new byte[keyLen];
                Matcher matcher = m;
                int cp = 0;
                while (true) {
                    ctx = ctx2;
                    if (cp >= keyLen - i2) {
                        break;
                    }
                    System.arraycopy(tempResult, 0, pBytes, cp, i2);
                    cp += i2;
                    ctx2 = ctx;
                }
                char c = 0;
                MessageDigest messageDigest = altCtx2;
                System.arraycopy(tempResult, 0, pBytes, cp, keyLen - cp);
                MessageDigest altCtx3 = DigestUtils.getDigest(algorithm);
                int i4 = cp;
                int i5 = 1;
                while (true) {
                    byte[] tempResult2 = tempResult;
                    if (i5 > (altResult3[c] & UByte.MAX_VALUE) + 16) {
                        break;
                    }
                    altCtx3.update(saltBytes);
                    i5++;
                    tempResult = tempResult2;
                    c = 0;
                }
                byte[] tempResult3 = altCtx3.digest();
                byte[] sBytes = new byte[saltLen];
                int cp2 = 0;
                while (true) {
                    altResult = altResult3;
                    if (cp2 >= saltLen - i2) {
                        break;
                    }
                    System.arraycopy(tempResult3, 0, sBytes, cp2, i2);
                    cp2 += i2;
                    altResult3 = altResult;
                }
                int i6 = cnt2;
                System.arraycopy(tempResult3, 0, sBytes, cp2, saltLen - cp2);
                int i7 = 0;
                byte[] altResult4 = altResult;
                while (true) {
                    int cp3 = cp2;
                    if (i7 > rounds - 1) {
                        break;
                    }
                    MessageDigest ctx3 = DigestUtils.getDigest(algorithm);
                    if ((i7 & 1) != 0) {
                        i = 0;
                        ctx3.update(pBytes, 0, keyLen);
                    } else {
                        i = 0;
                        ctx3.update(altResult4, 0, i2);
                    }
                    if (i7 % 3 != 0) {
                        ctx3.update(sBytes, i, saltLen);
                    }
                    if (i7 % 7 != 0) {
                        ctx3.update(pBytes, i, keyLen);
                    }
                    if ((i7 & 1) != 0) {
                        ctx3.update(altResult4, i, i2);
                    } else {
                        ctx3.update(pBytes, i, keyLen);
                    }
                    altResult4 = ctx3.digest();
                    i7++;
                    String str2 = salt;
                    ctx = ctx3;
                    cp2 = cp3;
                }
                StringBuilder buffer = new StringBuilder(saltPrefix);
                if (roundsCustom) {
                    int i8 = keyLen;
                    buffer.append(ROUNDS_PREFIX);
                    buffer.append(rounds);
                    buffer.append("$");
                }
                buffer.append(saltString);
                buffer.append("$");
                if (i2 == 32) {
                    int i9 = rounds;
                    B64.b64from24bit(altResult4[0], altResult4[10], altResult4[20], 4, buffer);
                    B64.b64from24bit(altResult4[21], altResult4[1], altResult4[11], 4, buffer);
                    B64.b64from24bit(altResult4[12], altResult4[22], altResult4[2], 4, buffer);
                    B64.b64from24bit(altResult4[3], altResult4[13], altResult4[23], 4, buffer);
                    B64.b64from24bit(altResult4[24], altResult4[4], altResult4[14], 4, buffer);
                    B64.b64from24bit(altResult4[15], altResult4[25], altResult4[5], 4, buffer);
                    B64.b64from24bit(altResult4[6], altResult4[16], altResult4[26], 4, buffer);
                    B64.b64from24bit(altResult4[27], altResult4[7], altResult4[17], 4, buffer);
                    B64.b64from24bit(altResult4[18], altResult4[28], altResult4[8], 4, buffer);
                    B64.b64from24bit(altResult4[9], altResult4[19], altResult4[29], 4, buffer);
                    B64.b64from24bit((byte) 0, altResult4[31], altResult4[30], 3, buffer);
                    b = 0;
                } else {
                    B64.b64from24bit(altResult4[0], altResult4[21], altResult4[42], 4, buffer);
                    B64.b64from24bit(altResult4[22], altResult4[43], altResult4[1], 4, buffer);
                    B64.b64from24bit(altResult4[44], altResult4[2], altResult4[23], 4, buffer);
                    B64.b64from24bit(altResult4[3], altResult4[24], altResult4[45], 4, buffer);
                    B64.b64from24bit(altResult4[25], altResult4[46], altResult4[4], 4, buffer);
                    B64.b64from24bit(altResult4[47], altResult4[5], altResult4[26], 4, buffer);
                    B64.b64from24bit(altResult4[6], altResult4[27], altResult4[48], 4, buffer);
                    B64.b64from24bit(altResult4[28], altResult4[49], altResult4[7], 4, buffer);
                    B64.b64from24bit(altResult4[50], altResult4[8], altResult4[29], 4, buffer);
                    B64.b64from24bit(altResult4[9], altResult4[30], altResult4[51], 4, buffer);
                    B64.b64from24bit(altResult4[31], altResult4[52], altResult4[10], 4, buffer);
                    B64.b64from24bit(altResult4[53], altResult4[11], altResult4[32], 4, buffer);
                    B64.b64from24bit(altResult4[12], altResult4[33], altResult4[54], 4, buffer);
                    B64.b64from24bit(altResult4[34], altResult4[55], altResult4[13], 4, buffer);
                    B64.b64from24bit(altResult4[56], altResult4[14], altResult4[35], 4, buffer);
                    B64.b64from24bit(altResult4[15], altResult4[36], altResult4[57], 4, buffer);
                    B64.b64from24bit(altResult4[37], altResult4[58], altResult4[16], 4, buffer);
                    B64.b64from24bit(altResult4[59], altResult4[17], altResult4[38], 4, buffer);
                    B64.b64from24bit(altResult4[18], altResult4[39], altResult4[60], 4, buffer);
                    B64.b64from24bit(altResult4[40], altResult4[61], altResult4[19], 4, buffer);
                    B64.b64from24bit(altResult4[62], altResult4[20], altResult4[41], 4, buffer);
                    b = 0;
                    B64.b64from24bit((byte) 0, (byte) 0, altResult4[63], 2, buffer);
                }
                Arrays.fill(tempResult3, b);
                Arrays.fill(pBytes, b);
                Arrays.fill(sBytes, b);
                ctx.reset();
                altCtx3.reset();
                Arrays.fill(bArr, b);
                Arrays.fill(saltBytes, b);
                return buffer.toString();
            }
            throw new IllegalArgumentException("Invalid salt value: " + salt);
        }
        String str3 = str;
        throw new IllegalArgumentException("Salt must not be null");
    }

    public static String sha512Crypt(byte[] keyBytes) {
        return sha512Crypt(keyBytes, (String) null);
    }

    public static String sha512Crypt(byte[] keyBytes, String salt) {
        if (salt == null) {
            salt = SHA512_PREFIX + B64.getRandomSalt(8);
        }
        return sha2Crypt(keyBytes, salt, SHA512_PREFIX, 64, MessageDigestAlgorithms.SHA_512);
    }

    public static String sha512Crypt(byte[] keyBytes, String salt, Random random) {
        if (salt == null) {
            salt = SHA512_PREFIX + B64.getRandomSalt(8, random);
        }
        return sha2Crypt(keyBytes, salt, SHA512_PREFIX, 64, MessageDigestAlgorithms.SHA_512);
    }
}
