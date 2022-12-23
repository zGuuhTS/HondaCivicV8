package org.apache.commons.p012io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import top.defaults.checkerboarddrawable.BuildConfig;

/* renamed from: org.apache.commons.io.FilenameUtils */
public class FilenameUtils {
    public static final char EXTENSION_SEPARATOR = '.';
    public static final String EXTENSION_SEPARATOR_STR = Character.toString(EXTENSION_SEPARATOR);
    private static final char OTHER_SEPARATOR = (isSystemWindows() ? '/' : '\\');
    private static final char SYSTEM_SEPARATOR = File.separatorChar;
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';

    public static String concat(String str, String str2) {
        StringBuilder sb;
        int prefixLength = getPrefixLength(str2);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength > 0) {
            return normalize(str2);
        }
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return normalize(str2);
        }
        if (isSeparator(str.charAt(length - 1))) {
            sb = new StringBuilder();
            sb.append(str);
        } else {
            sb = new StringBuilder();
            sb.append(str);
            sb.append('/');
        }
        sb.append(str2);
        return normalize(sb.toString());
    }

    public static boolean directoryContains(String str, String str2) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("Directory must not be null");
        } else if (str2 != null && !IOCase.SYSTEM.checkEquals(str, str2)) {
            return IOCase.SYSTEM.checkStartsWith(str2, str);
        } else {
            return false;
        }
    }

    private static String doGetFullPath(String str, boolean z) {
        throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:161)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:433)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:129)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:528)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:425)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:441)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:123)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:271)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:107)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
    }

    private static String doGetPath(String str, int i) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        int indexOfLastSeparator = indexOfLastSeparator(str);
        int i2 = i + indexOfLastSeparator;
        return (prefixLength >= str.length() || indexOfLastSeparator < 0 || prefixLength >= i2) ? BuildConfig.FLAVOR : str.substring(prefixLength, i2);
    }

    private static String doNormalize(String str, char c, boolean z) {
        boolean z2;
        String str2 = str;
        char c2 = c;
        if (str2 == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return str2;
        }
        int prefixLength = getPrefixLength(str);
        if (prefixLength < 0) {
            return null;
        }
        int i = length + 2;
        char[] cArr = new char[i];
        str2.getChars(0, str.length(), cArr, 0);
        char c3 = SYSTEM_SEPARATOR;
        if (c2 == c3) {
            c3 = OTHER_SEPARATOR;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (cArr[i2] == c3) {
                cArr[i2] = c2;
            }
        }
        if (cArr[length - 1] != c2) {
            cArr[length] = c2;
            length++;
            z2 = false;
        } else {
            z2 = true;
        }
        int i3 = prefixLength + 1;
        int i4 = i3;
        while (i4 < length) {
            if (cArr[i4] == c2) {
                int i5 = i4 - 1;
                if (cArr[i5] == c2) {
                    System.arraycopy(cArr, i4, cArr, i5, length - i4);
                    length--;
                    i4 = i5;
                }
            }
            i4++;
        }
        int i6 = i3;
        while (i6 < length) {
            if (cArr[i6] == c2) {
                int i7 = i6 - 1;
                if (cArr[i7] == '.' && (i6 == i3 || cArr[i6 - 2] == c2)) {
                    if (i6 == length - 1) {
                        z2 = true;
                    }
                    System.arraycopy(cArr, i6 + 1, cArr, i7, length - i6);
                    length -= 2;
                    i6 = i7;
                }
            }
            i6++;
        }
        int i8 = prefixLength + 2;
        int i9 = i8;
        while (i9 < length) {
            if (cArr[i9] == c2 && cArr[i9 - 1] == '.' && cArr[i9 - 2] == '.' && (i9 == i8 || cArr[i9 - 3] == c2)) {
                if (i9 != i8) {
                    if (i9 == length - 1) {
                        z2 = true;
                    }
                    int i10 = i9 - 4;
                    while (true) {
                        if (i10 < prefixLength) {
                            int i11 = i9 + 1;
                            System.arraycopy(cArr, i11, cArr, prefixLength, length - i9);
                            length -= i11 - prefixLength;
                            i9 = i3;
                            break;
                        } else if (cArr[i10] == c2) {
                            int i12 = i10 + 1;
                            System.arraycopy(cArr, i9 + 1, cArr, i12, length - i9);
                            length -= i9 - i10;
                            i9 = i12;
                            break;
                        } else {
                            i10--;
                        }
                    }
                } else {
                    return null;
                }
            }
            i9++;
        }
        return length <= 0 ? BuildConfig.FLAVOR : length <= prefixLength ? new String(cArr, 0, length) : (!z2 || !z) ? new String(cArr, 0, length - 1) : new String(cArr, 0, length);
    }

    public static boolean equals(String str, String str2) {
        return equals(str, str2, false, IOCase.SENSITIVE);
    }

    public static boolean equals(String str, String str2, boolean z, IOCase iOCase) {
        if (str == null || str2 == null) {
            return str == null && str2 == null;
        }
        if (z) {
            str = normalize(str);
            str2 = normalize(str2);
            if (str == null || str2 == null) {
                throw new NullPointerException("Error normalizing one or both of the file names");
            }
        }
        if (iOCase == null) {
            iOCase = IOCase.SENSITIVE;
        }
        return iOCase.checkEquals(str, str2);
    }

    public static boolean equalsNormalized(String str, String str2) {
        return equals(str, str2, true, IOCase.SENSITIVE);
    }

    public static boolean equalsNormalizedOnSystem(String str, String str2) {
        return equals(str, str2, true, IOCase.SYSTEM);
    }

    public static boolean equalsOnSystem(String str, String str2) {
        return equals(str, str2, false, IOCase.SYSTEM);
    }

    public static String getBaseName(String str) {
        return removeExtension(getName(str));
    }

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        }
        int indexOfExtension = indexOfExtension(str);
        return indexOfExtension == -1 ? BuildConfig.FLAVOR : str.substring(indexOfExtension + 1);
    }

    public static String getFullPath(String str) {
        return doGetFullPath(str, true);
    }

    public static String getFullPathNoEndSeparator(String str) {
        return doGetFullPath(str, false);
    }

    public static String getName(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(indexOfLastSeparator(str) + 1);
    }

    public static String getPath(String str) {
        return doGetPath(str, 1);
    }

    public static String getPathNoEndSeparator(String str) {
        return doGetPath(str, 0);
    }

    public static String getPrefix(String str) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        if (prefixLength <= str.length()) {
            return str.substring(0, prefixLength);
        }
        return str + '/';
    }

    public static int getPrefixLength(String str) {
        throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:662)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:813)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:161)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:433)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:129)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:528)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:425)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:441)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:123)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:271)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:107)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
    }

    public static int indexOfExtension(String str) {
        int lastIndexOf;
        if (str != null && indexOfLastSeparator(str) <= (lastIndexOf = str.lastIndexOf(46))) {
            return lastIndexOf;
        }
        return -1;
    }

    public static int indexOfLastSeparator(String str) {
        if (str == null) {
            return -1;
        }
        return Math.max(str.lastIndexOf(47), str.lastIndexOf(92));
    }

    public static boolean isExtension(String str, String str2) {
        if (str == null) {
            return false;
        }
        return (str2 == null || str2.length() == 0) ? indexOfExtension(str) == -1 : getExtension(str).equals(str2);
    }

    public static boolean isExtension(String str, Collection<String> collection) {
        if (str == null) {
            return false;
        }
        if (collection == null || collection.isEmpty()) {
            return indexOfExtension(str) == -1;
        }
        String extension = getExtension(str);
        for (String equals : collection) {
            if (extension.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExtension(String str, String[] strArr) {
        if (str == null) {
            return false;
        }
        if (strArr == null || strArr.length == 0) {
            return indexOfExtension(str) == -1;
        }
        String extension = getExtension(str);
        for (String equals : strArr) {
            if (extension.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSeparator(char c) {
        return c == '/' || c == '\\';
    }

    static boolean isSystemWindows() {
        return SYSTEM_SEPARATOR == '\\';
    }

    public static String normalize(String str) {
        return doNormalize(str, SYSTEM_SEPARATOR, true);
    }

    public static String normalize(String str, boolean z) {
        return doNormalize(str, z ? '/' : '\\', true);
    }

    public static String normalizeNoEndSeparator(String str) {
        return doNormalize(str, SYSTEM_SEPARATOR, false);
    }

    public static String normalizeNoEndSeparator(String str, boolean z) {
        return doNormalize(str, z ? '/' : '\\', false);
    }

    public static String removeExtension(String str) {
        if (str == null) {
            return null;
        }
        int indexOfExtension = indexOfExtension(str);
        return indexOfExtension == -1 ? str : str.substring(0, indexOfExtension);
    }

    public static String separatorsToSystem(String str) {
        if (str == null) {
            return null;
        }
        return isSystemWindows() ? separatorsToWindows(str) : separatorsToUnix(str);
    }

    public static String separatorsToUnix(String str) {
        return (str == null || str.indexOf(92) == -1) ? str : str.replace('\\', '/');
    }

    public static String separatorsToWindows(String str) {
        return (str == null || str.indexOf(47) == -1) ? str : str.replace('/', '\\');
    }

    static String[] splitOnTokens(String str) {
        if (str.indexOf(63) == -1 && str.indexOf(42) == -1) {
            return new String[]{str};
        }
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '?' || charArray[i] == '*') {
                if (sb.length() != 0) {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                }
                if (charArray[i] == '?') {
                    arrayList.add("?");
                } else if (arrayList.isEmpty() || (i > 0 && !((String) arrayList.get(arrayList.size() - 1)).equals("*"))) {
                    arrayList.add("*");
                }
            } else {
                sb.append(charArray[i]);
            }
        }
        if (sb.length() != 0) {
            arrayList.add(sb.toString());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static boolean wildcardMatch(String str, String str2) {
        return wildcardMatch(str, str2, IOCase.SENSITIVE);
    }

    public static boolean wildcardMatch(String str, String str2, IOCase iOCase) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        if (iOCase == null) {
            iOCase = IOCase.SENSITIVE;
        }
        String[] splitOnTokens = splitOnTokens(str2);
        Stack stack = new Stack();
        int i = 0;
        boolean z = false;
        int i2 = 0;
        do {
            if (stack.size() > 0) {
                int[] iArr = (int[]) stack.pop();
                i2 = iArr[0];
                i = iArr[1];
                z = true;
            }
            while (i2 < splitOnTokens.length) {
                if (splitOnTokens[i2].equals("?")) {
                    i++;
                    if (i > str.length()) {
                        break;
                    }
                } else if (splitOnTokens[i2].equals("*")) {
                    if (i2 == splitOnTokens.length - 1) {
                        i = str.length();
                    }
                    z = true;
                    i2++;
                } else {
                    if (!z) {
                        if (!iOCase.checkRegionMatches(str, i, splitOnTokens[i2])) {
                            break;
                        }
                    } else {
                        i = iOCase.checkIndexOf(str, i, splitOnTokens[i2]);
                        if (i == -1) {
                            break;
                        }
                        int checkIndexOf = iOCase.checkIndexOf(str, i + 1, splitOnTokens[i2]);
                        if (checkIndexOf >= 0) {
                            stack.push(new int[]{i2, checkIndexOf});
                        }
                    }
                    i += splitOnTokens[i2].length();
                }
                z = false;
                i2++;
            }
            if (i2 == splitOnTokens.length && i == str.length()) {
                return true;
            }
        } while (stack.size() > 0);
        return false;
    }

    public static boolean wildcardMatchOnSystem(String str, String str2) {
        return wildcardMatch(str, str2, IOCase.SYSTEM);
    }
}
