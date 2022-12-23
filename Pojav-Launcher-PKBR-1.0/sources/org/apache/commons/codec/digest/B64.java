package org.apache.commons.codec.digest;

import java.security.SecureRandom;
import java.util.Random;
import kotlin.UByte;

class B64 {
    static final char[] B64T_ARRAY = B64T_STRING.toCharArray();
    static final String B64T_STRING = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    B64() {
    }

    static void b64from24bit(byte b2, byte b1, byte b0, int outLen, StringBuilder buffer) {
        int w = ((b2 << 16) & UByte.MAX_VALUE) | ((b1 << 8) & UByte.MAX_VALUE) | (b0 & UByte.MAX_VALUE);
        int n = outLen;
        while (true) {
            int n2 = n - 1;
            if (n > 0) {
                buffer.append(B64T_ARRAY[w & 63]);
                w >>= 6;
                n = n2;
            } else {
                return;
            }
        }
    }

    static String getRandomSalt(int num) {
        return getRandomSalt(num, new SecureRandom());
    }

    static String getRandomSalt(int num, Random random) {
        StringBuilder saltString = new StringBuilder(num);
        for (int i = 1; i <= num; i++) {
            saltString.append(B64T_STRING.charAt(random.nextInt(B64T_STRING.length())));
        }
        return saltString.toString();
    }
}
