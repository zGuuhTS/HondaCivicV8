package org.apache.commons.codec.digest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Md5Crypt {
    static final String APR1_PREFIX = "$apr1$";
    private static final int BLOCKSIZE = 16;
    static final String MD5_PREFIX = "$1$";
    private static final int ROUNDS = 1000;

    public static String apr1Crypt(byte[] keyBytes) {
        return apr1Crypt(keyBytes, APR1_PREFIX + B64.getRandomSalt(8));
    }

    public static String apr1Crypt(byte[] keyBytes, Random random) {
        return apr1Crypt(keyBytes, APR1_PREFIX + B64.getRandomSalt(8, random));
    }

    public static String apr1Crypt(byte[] keyBytes, String salt) {
        if (salt != null && !salt.startsWith(APR1_PREFIX)) {
            salt = APR1_PREFIX + salt;
        }
        return md5Crypt(keyBytes, salt, APR1_PREFIX);
    }

    public static String apr1Crypt(String keyBytes) {
        return apr1Crypt(keyBytes.getBytes(StandardCharsets.UTF_8));
    }

    public static String apr1Crypt(String keyBytes, String salt) {
        return apr1Crypt(keyBytes.getBytes(StandardCharsets.UTF_8), salt);
    }

    public static String md5Crypt(byte[] keyBytes) {
        return md5Crypt(keyBytes, MD5_PREFIX + B64.getRandomSalt(8));
    }

    public static String md5Crypt(byte[] keyBytes, Random random) {
        return md5Crypt(keyBytes, MD5_PREFIX + B64.getRandomSalt(8, random));
    }

    public static String md5Crypt(byte[] keyBytes, String salt) {
        return md5Crypt(keyBytes, salt, MD5_PREFIX);
    }

    public static String md5Crypt(byte[] keyBytes, String salt, String prefix) {
        return md5Crypt(keyBytes, salt, prefix, new SecureRandom());
    }

    public static String md5Crypt(byte[] keyBytes, String salt, String prefix, Random random) {
        String saltString;
        byte[] bArr = keyBytes;
        String str = salt;
        String str2 = prefix;
        int keyLen = bArr.length;
        if (str == null) {
            saltString = B64.getRandomSalt(8, random);
        } else {
            Random random2 = random;
            Matcher m = Pattern.compile("^" + str2.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*").matcher(str);
            if (m.find()) {
                saltString = m.group(1);
            } else {
                throw new IllegalArgumentException("Invalid salt value: " + str);
            }
        }
        byte[] saltBytes = saltString.getBytes(StandardCharsets.UTF_8);
        MessageDigest ctx = DigestUtils.getMd5Digest();
        ctx.update(bArr);
        ctx.update(str2.getBytes(StandardCharsets.UTF_8));
        ctx.update(saltBytes);
        MessageDigest ctx1 = DigestUtils.getMd5Digest();
        ctx1.update(bArr);
        ctx1.update(saltBytes);
        ctx1.update(bArr);
        byte[] finalb = ctx1.digest();
        int ii = keyLen;
        while (true) {
            int i = 16;
            if (ii <= 0) {
                break;
            }
            if (ii <= 16) {
                i = ii;
            }
            ctx.update(finalb, 0, i);
            ii -= 16;
        }
        Arrays.fill(finalb, (byte) 0);
        for (int ii2 = keyLen; ii2 > 0; ii2 >>= 1) {
            if ((ii2 & 1) == 1) {
                ctx.update(finalb[0]);
            } else {
                ctx.update(bArr[0]);
            }
        }
        StringBuilder passwd = new StringBuilder(str2 + saltString + "$");
        byte[] finalb2 = ctx.digest();
        for (int i2 = 0; i2 < 1000; i2++) {
            ctx1 = DigestUtils.getMd5Digest();
            if ((i2 & 1) != 0) {
                ctx1.update(bArr);
            } else {
                ctx1.update(finalb2, 0, 16);
            }
            if (i2 % 3 != 0) {
                ctx1.update(saltBytes);
            }
            if (i2 % 7 != 0) {
                ctx1.update(bArr);
            }
            if ((i2 & 1) != 0) {
                ctx1.update(finalb2, 0, 16);
            } else {
                ctx1.update(bArr);
            }
            finalb2 = ctx1.digest();
        }
        B64.b64from24bit(finalb2[0], finalb2[6], finalb2[12], 4, passwd);
        B64.b64from24bit(finalb2[1], finalb2[7], finalb2[13], 4, passwd);
        B64.b64from24bit(finalb2[2], finalb2[8], finalb2[14], 4, passwd);
        B64.b64from24bit(finalb2[3], finalb2[9], finalb2[15], 4, passwd);
        B64.b64from24bit(finalb2[4], finalb2[10], finalb2[5], 4, passwd);
        B64.b64from24bit((byte) 0, (byte) 0, finalb2[11], 2, passwd);
        ctx.reset();
        ctx1.reset();
        Arrays.fill(bArr, (byte) 0);
        Arrays.fill(saltBytes, (byte) 0);
        Arrays.fill(finalb2, (byte) 0);
        return passwd.toString();
    }
}
