package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import top.defaults.checkerboarddrawable.BuildConfig;

public class Metaphone implements StringEncoder {
    private static final String FRONTV = "EIY";
    private static final String VARSON = "CSPTG";
    private static final String VOWELS = "AEIOU";
    private int maxCodeLen = 4;

    public String metaphone(String txt) {
        boolean z;
        char c;
        boolean hard;
        String str = txt;
        if (str == null) {
            return BuildConfig.FLAVOR;
        }
        int length = txt.length();
        int txtLength = length;
        if (length == 0) {
            return BuildConfig.FLAVOR;
        }
        boolean z2 = true;
        if (txtLength == 1) {
            return str.toUpperCase(Locale.ENGLISH);
        }
        char[] inwd = str.toUpperCase(Locale.ENGLISH).toCharArray();
        StringBuilder local = new StringBuilder(40);
        StringBuilder code = new StringBuilder(10);
        char c2 = 'H';
        switch (inwd[0]) {
            case 'A':
                if (inwd[1] != 'E') {
                    local.append(inwd);
                    break;
                } else {
                    local.append(inwd, 1, inwd.length - 1);
                    break;
                }
            case 'G':
            case 'K':
            case 'P':
                if (inwd[1] != 'N') {
                    local.append(inwd);
                    break;
                } else {
                    local.append(inwd, 1, inwd.length - 1);
                    break;
                }
            case 'W':
                if (inwd[1] != 'R') {
                    if (inwd[1] != 'H') {
                        local.append(inwd);
                        break;
                    } else {
                        local.append(inwd, 1, inwd.length - 1);
                        local.setCharAt(0, 'W');
                        break;
                    }
                } else {
                    local.append(inwd, 1, inwd.length - 1);
                    break;
                }
            case 'X':
                inwd[0] = 'S';
                local.append(inwd);
                break;
            default:
                local.append(inwd);
                break;
        }
        int wdsz = local.length();
        int n = 0;
        while (code.length() < getMaxCodeLen() && n < wdsz) {
            char symb = local.charAt(n);
            if (symb == 'C' || !isPreviousChar(local, n, symb)) {
                switch (symb) {
                    case 'A':
                    case 'E':
                    case 'I':
                    case 'O':
                    case 'U':
                        c = 'H';
                        if (n == 0) {
                            code.append(symb);
                            break;
                        }
                        break;
                    case 'B':
                        c = 'H';
                        if (!isPreviousChar(local, n, 'M') || !isLastChar(wdsz, n)) {
                            code.append(symb);
                            break;
                        }
                    case 'C':
                        if (!isPreviousChar(local, n, 'S') || isLastChar(wdsz, n) || FRONTV.indexOf(local.charAt(n + 1)) < 0) {
                            if (!regionMatch(local, n, "CIA")) {
                                if (!isLastChar(wdsz, n) && FRONTV.indexOf(local.charAt(n + 1)) >= 0) {
                                    code.append('S');
                                    c = 'H';
                                    break;
                                } else {
                                    if (isPreviousChar(local, n, 'S')) {
                                        c = 'H';
                                        if (isNextChar(local, n, 'H')) {
                                            code.append('K');
                                            break;
                                        }
                                    } else {
                                        c = 'H';
                                    }
                                    if (isNextChar(local, n, c)) {
                                        if (n == 0 && wdsz >= 3 && isVowel(local, 2)) {
                                            code.append('K');
                                            break;
                                        } else {
                                            code.append('X');
                                            break;
                                        }
                                    } else {
                                        code.append('K');
                                        break;
                                    }
                                }
                            } else {
                                code.append('X');
                                c = 'H';
                                break;
                            }
                        } else {
                            c = 'H';
                            break;
                        }
                        break;
                    case 'D':
                        if (!isLastChar(wdsz, n + 1) && isNextChar(local, n, 'G') && FRONTV.indexOf(local.charAt(n + 2)) >= 0) {
                            code.append('J');
                            n += 2;
                            c = 'H';
                            break;
                        } else {
                            code.append('T');
                            c = 'H';
                            break;
                        }
                        break;
                    case 'F':
                    case 'J':
                    case 'L':
                    case 'M':
                    case 'N':
                    case 'R':
                        code.append(symb);
                        c = 'H';
                        break;
                    case 'G':
                        if (!isLastChar(wdsz, n + 1) || !isNextChar(local, n, 'H')) {
                            if (!isLastChar(wdsz, n + 1) && isNextChar(local, n, 'H') && !isVowel(local, n + 2)) {
                                c = 'H';
                                break;
                            } else {
                                if (n > 0) {
                                    if (!regionMatch(local, n, "GN")) {
                                        if (regionMatch(local, n, "GNED")) {
                                            c = 'H';
                                            break;
                                        }
                                    } else {
                                        c = 'H';
                                        break;
                                    }
                                }
                                if (isPreviousChar(local, n, 'G')) {
                                    hard = true;
                                } else {
                                    hard = false;
                                }
                                if (isLastChar(wdsz, n) || FRONTV.indexOf(local.charAt(n + 1)) < 0 || hard) {
                                    code.append('K');
                                } else {
                                    code.append('J');
                                }
                                c = 'H';
                                break;
                            }
                        } else {
                            c = 'H';
                            break;
                        }
                        break;
                    case 'H':
                        if (!isLastChar(wdsz, n)) {
                            if (n <= 0 || VARSON.indexOf(local.charAt(n - 1)) < 0) {
                                if (!isVowel(local, n + 1)) {
                                    c = 'H';
                                    break;
                                } else {
                                    c = 'H';
                                    code.append('H');
                                    break;
                                }
                            } else {
                                c = 'H';
                                break;
                            }
                        } else {
                            c = 'H';
                            break;
                        }
                        break;
                    case 'K':
                        if (n > 0) {
                            if (isPreviousChar(local, n, 'C')) {
                                c = 'H';
                                break;
                            } else {
                                code.append(symb);
                                c = 'H';
                                break;
                            }
                        } else {
                            code.append(symb);
                            c = 'H';
                            break;
                        }
                    case 'P':
                        if (!isNextChar(local, n, 'H')) {
                            code.append(symb);
                            c = 'H';
                            break;
                        } else {
                            code.append('F');
                            c = 'H';
                            break;
                        }
                    case 'Q':
                        code.append('K');
                        c = 'H';
                        break;
                    case 'S':
                        if (!regionMatch(local, n, "SH") && !regionMatch(local, n, "SIO") && !regionMatch(local, n, "SIA")) {
                            code.append('S');
                            c = 'H';
                            break;
                        } else {
                            code.append('X');
                            c = 'H';
                            break;
                        }
                        break;
                    case 'T':
                        if (!regionMatch(local, n, "TIA") && !regionMatch(local, n, "TIO")) {
                            if (!regionMatch(local, n, "TCH")) {
                                if (!regionMatch(local, n, "TH")) {
                                    code.append('T');
                                    c = 'H';
                                    break;
                                } else {
                                    code.append('0');
                                    c = 'H';
                                    break;
                                }
                            } else {
                                c = 'H';
                                break;
                            }
                        } else {
                            code.append('X');
                            c = 'H';
                            break;
                        }
                        break;
                    case 'V':
                        code.append('F');
                        c = 'H';
                        break;
                    case 'W':
                    case 'Y':
                        if (!isLastChar(wdsz, n)) {
                            if (!isVowel(local, n + 1)) {
                                c = 'H';
                                break;
                            } else {
                                code.append(symb);
                                c = 'H';
                                break;
                            }
                        } else {
                            c = 'H';
                            break;
                        }
                    case 'X':
                        code.append('K');
                        code.append('S');
                        c = 'H';
                        break;
                    case 'Z':
                        code.append('S');
                        c = 'H';
                        break;
                    default:
                        c = 'H';
                        break;
                }
                z = true;
                n++;
            } else {
                n++;
                char c3 = c2;
                z = z2;
                c = c3;
            }
            if (code.length() > getMaxCodeLen()) {
                code.setLength(getMaxCodeLen());
            }
            boolean z3 = z;
            c2 = c;
            z2 = z3;
        }
        return code.toString();
    }

    private boolean isVowel(StringBuilder string, int index) {
        return VOWELS.indexOf(string.charAt(index)) >= 0;
    }

    private boolean isPreviousChar(StringBuilder string, int index, char c) {
        if (index <= 0 || index >= string.length()) {
            return false;
        }
        return string.charAt(index + -1) == c;
    }

    private boolean isNextChar(StringBuilder string, int index, char c) {
        if (index < 0) {
            return false;
        }
        boolean matches = true;
        if (index >= string.length() - 1) {
            return false;
        }
        if (string.charAt(index + 1) != c) {
            matches = false;
        }
        return matches;
    }

    private boolean regionMatch(StringBuilder string, int index, String test) {
        if (index < 0 || (test.length() + index) - 1 >= string.length()) {
            return false;
        }
        return string.substring(index, test.length() + index).equals(test);
    }

    private boolean isLastChar(int wdsz, int n) {
        return n + 1 == wdsz;
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return metaphone((String) obj);
        }
        throw new EncoderException("Parameter supplied to Metaphone encode is not of type java.lang.String");
    }

    public String encode(String str) {
        return metaphone(str);
    }

    public boolean isMetaphoneEqual(String str1, String str2) {
        return metaphone(str1).equals(metaphone(str2));
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public void setMaxCodeLen(int maxCodeLen2) {
        this.maxCodeLen = maxCodeLen2;
    }
}
