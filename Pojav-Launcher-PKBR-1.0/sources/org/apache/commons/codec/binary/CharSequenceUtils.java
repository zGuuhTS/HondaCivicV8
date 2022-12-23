package org.apache.commons.codec.binary;

public class CharSequenceUtils {
    static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
        if (!(cs instanceof String) || !(substring instanceof String)) {
            int index1 = thisStart;
            int index2 = start;
            int index12 = length;
            while (true) {
                int tmpLen = index12 - 1;
                if (index12 <= 0) {
                    return true;
                }
                int index13 = index1 + 1;
                char c1 = cs.charAt(index1);
                int index22 = index2 + 1;
                char c2 = substring.charAt(index2);
                if (c1 != c2) {
                    if (!ignoreCase) {
                        return false;
                    }
                    if (!(Character.toUpperCase(c1) == Character.toUpperCase(c2) || Character.toLowerCase(c1) == Character.toLowerCase(c2))) {
                        return false;
                    }
                }
                index1 = index13;
                index12 = tmpLen;
                index2 = index22;
            }
        } else {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
    }
}
