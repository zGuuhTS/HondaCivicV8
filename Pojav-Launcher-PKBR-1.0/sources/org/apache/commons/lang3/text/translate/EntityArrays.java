package org.apache.commons.lang3.text.translate;

import java.lang.reflect.Array;
import net.kdt.pojavlaunch.AWTInputEvent;
import org.apache.commons.p012io.IOUtils;

public class EntityArrays {
    private static final String[][] APOS_ESCAPE;
    private static final String[][] APOS_UNESCAPE;
    private static final String[][] BASIC_ESCAPE;
    private static final String[][] BASIC_UNESCAPE;
    private static final String[][] HTML40_EXTENDED_ESCAPE;
    private static final String[][] HTML40_EXTENDED_UNESCAPE;
    private static final String[][] ISO8859_1_ESCAPE;
    private static final String[][] ISO8859_1_UNESCAPE;
    private static final String[][] JAVA_CTRL_CHARS_ESCAPE;
    private static final String[][] JAVA_CTRL_CHARS_UNESCAPE;

    static {
        String[] strArr = {"Æ", "&AElig;"};
        String[][] strArr2 = {new String[]{" ", "&nbsp;"}, new String[]{"¡", "&iexcl;"}, new String[]{"¢", "&cent;"}, new String[]{"£", "&pound;"}, new String[]{"¤", "&curren;"}, new String[]{"¥", "&yen;"}, new String[]{"¦", "&brvbar;"}, new String[]{"§", "&sect;"}, new String[]{"¨", "&uml;"}, new String[]{"©", "&copy;"}, new String[]{"ª", "&ordf;"}, new String[]{"«", "&laquo;"}, new String[]{"¬", "&not;"}, new String[]{"­", "&shy;"}, new String[]{"®", "&reg;"}, new String[]{"¯", "&macr;"}, new String[]{"°", "&deg;"}, new String[]{"±", "&plusmn;"}, new String[]{"²", "&sup2;"}, new String[]{"³", "&sup3;"}, new String[]{"´", "&acute;"}, new String[]{"µ", "&micro;"}, new String[]{"¶", "&para;"}, new String[]{"·", "&middot;"}, new String[]{"¸", "&cedil;"}, new String[]{"¹", "&sup1;"}, new String[]{"º", "&ordm;"}, new String[]{"»", "&raquo;"}, new String[]{"¼", "&frac14;"}, new String[]{"½", "&frac12;"}, new String[]{"¾", "&frac34;"}, new String[]{"¿", "&iquest;"}, new String[]{"À", "&Agrave;"}, new String[]{"Á", "&Aacute;"}, new String[]{"Â", "&Acirc;"}, new String[]{"Ã", "&Atilde;"}, new String[]{"Ä", "&Auml;"}, new String[]{"Å", "&Aring;"}, strArr, new String[]{"Ç", "&Ccedil;"}, new String[]{"È", "&Egrave;"}, new String[]{"É", "&Eacute;"}, new String[]{"Ê", "&Ecirc;"}, new String[]{"Ë", "&Euml;"}, new String[]{"Ì", "&Igrave;"}, new String[]{"Í", "&Iacute;"}, new String[]{"Î", "&Icirc;"}, new String[]{"Ï", "&Iuml;"}, new String[]{"Ð", "&ETH;"}, new String[]{"Ñ", "&Ntilde;"}, new String[]{"Ò", "&Ograve;"}, new String[]{"Ó", "&Oacute;"}, new String[]{"Ô", "&Ocirc;"}, new String[]{"Õ", "&Otilde;"}, new String[]{"Ö", "&Ouml;"}, new String[]{"×", "&times;"}, new String[]{"Ø", "&Oslash;"}, new String[]{"Ù", "&Ugrave;"}, new String[]{"Ú", "&Uacute;"}, new String[]{"Û", "&Ucirc;"}, new String[]{"Ü", "&Uuml;"}, new String[]{"Ý", "&Yacute;"}, new String[]{"Þ", "&THORN;"}, new String[]{"ß", "&szlig;"}, new String[]{"à", "&agrave;"}, new String[]{"á", "&aacute;"}, new String[]{"â", "&acirc;"}, new String[]{"ã", "&atilde;"}, new String[]{"ä", "&auml;"}, new String[]{"å", "&aring;"}, new String[]{"æ", "&aelig;"}, new String[]{"ç", "&ccedil;"}, new String[]{"è", "&egrave;"}, new String[]{"é", "&eacute;"}, new String[]{"ê", "&ecirc;"}, new String[]{"ë", "&euml;"}, new String[]{"ì", "&igrave;"}, new String[]{"í", "&iacute;"}, new String[]{"î", "&icirc;"}, new String[]{"ï", "&iuml;"}, new String[]{"ð", "&eth;"}, new String[]{"ñ", "&ntilde;"}, new String[]{"ò", "&ograve;"}, new String[]{"ó", "&oacute;"}, new String[]{"ô", "&ocirc;"}, new String[]{"õ", "&otilde;"}, new String[]{"ö", "&ouml;"}, new String[]{"÷", "&divide;"}, new String[]{"ø", "&oslash;"}, new String[]{"ù", "&ugrave;"}, new String[]{"ú", "&uacute;"}, new String[]{"û", "&ucirc;"}, new String[]{"ü", "&uuml;"}, new String[]{"ý", "&yacute;"}, new String[]{"þ", "&thorn;"}, new String[]{"ÿ", "&yuml;"}};
        ISO8859_1_ESCAPE = strArr2;
        ISO8859_1_UNESCAPE = invert(strArr2);
        String[][] strArr3 = new String[AWTInputEvent.VK_ASTERISK][];
        strArr3[0] = new String[]{"ƒ", "&fnof;"};
        strArr3[1] = new String[]{"Α", "&Alpha;"};
        strArr3[2] = new String[]{"Β", "&Beta;"};
        strArr3[3] = new String[]{"Γ", "&Gamma;"};
        strArr3[4] = new String[]{"Δ", "&Delta;"};
        strArr3[5] = new String[]{"Ε", "&Epsilon;"};
        strArr3[6] = new String[]{"Ζ", "&Zeta;"};
        strArr3[7] = new String[]{"Η", "&Eta;"};
        strArr3[8] = new String[]{"Θ", "&Theta;"};
        strArr3[9] = new String[]{"Ι", "&Iota;"};
        strArr3[10] = new String[]{"Κ", "&Kappa;"};
        strArr3[11] = new String[]{"Λ", "&Lambda;"};
        strArr3[12] = new String[]{"Μ", "&Mu;"};
        strArr3[13] = new String[]{"Ν", "&Nu;"};
        strArr3[14] = new String[]{"Ξ", "&Xi;"};
        strArr3[15] = new String[]{"Ο", "&Omicron;"};
        strArr3[16] = new String[]{"Π", "&Pi;"};
        strArr3[17] = new String[]{"Ρ", "&Rho;"};
        strArr3[18] = new String[]{"Σ", "&Sigma;"};
        strArr3[19] = new String[]{"Τ", "&Tau;"};
        strArr3[20] = new String[]{"Υ", "&Upsilon;"};
        strArr3[21] = new String[]{"Φ", "&Phi;"};
        strArr3[22] = new String[]{"Χ", "&Chi;"};
        strArr3[23] = new String[]{"Ψ", "&Psi;"};
        strArr3[24] = new String[]{"Ω", "&Omega;"};
        strArr3[25] = new String[]{"α", "&alpha;"};
        strArr3[26] = new String[]{"β", "&beta;"};
        strArr3[27] = new String[]{"γ", "&gamma;"};
        strArr3[28] = new String[]{"δ", "&delta;"};
        strArr3[29] = new String[]{"ε", "&epsilon;"};
        strArr3[30] = new String[]{"ζ", "&zeta;"};
        strArr3[31] = new String[]{"η", "&eta;"};
        strArr3[32] = new String[]{"θ", "&theta;"};
        strArr3[33] = new String[]{"ι", "&iota;"};
        strArr3[34] = new String[]{"κ", "&kappa;"};
        strArr3[35] = new String[]{"λ", "&lambda;"};
        strArr3[36] = new String[]{"μ", "&mu;"};
        strArr3[37] = new String[]{"ν", "&nu;"};
        strArr3[38] = new String[]{"ξ", "&xi;"};
        strArr3[39] = new String[]{"ο", "&omicron;"};
        strArr3[40] = new String[]{"π", "&pi;"};
        strArr3[41] = new String[]{"ρ", "&rho;"};
        strArr3[42] = new String[]{"ς", "&sigmaf;"};
        strArr3[43] = new String[]{"σ", "&sigma;"};
        strArr3[44] = new String[]{"τ", "&tau;"};
        strArr3[45] = new String[]{"υ", "&upsilon;"};
        strArr3[46] = new String[]{"φ", "&phi;"};
        strArr3[47] = new String[]{"χ", "&chi;"};
        strArr3[48] = new String[]{"ψ", "&psi;"};
        strArr3[49] = new String[]{"ω", "&omega;"};
        strArr3[50] = new String[]{"ϑ", "&thetasym;"};
        strArr3[51] = new String[]{"ϒ", "&upsih;"};
        strArr3[52] = new String[]{"ϖ", "&piv;"};
        strArr3[53] = new String[]{"•", "&bull;"};
        strArr3[54] = new String[]{"…", "&hellip;"};
        strArr3[55] = new String[]{"′", "&prime;"};
        strArr3[56] = new String[]{"″", "&Prime;"};
        strArr3[57] = new String[]{"‾", "&oline;"};
        strArr3[58] = new String[]{"⁄", "&frasl;"};
        strArr3[59] = new String[]{"℘", "&weierp;"};
        strArr3[60] = new String[]{"ℑ", "&image;"};
        strArr3[61] = new String[]{"ℜ", "&real;"};
        strArr3[62] = new String[]{"™", "&trade;"};
        strArr3[63] = new String[]{"ℵ", "&alefsym;"};
        strArr3[64] = new String[]{"←", "&larr;"};
        strArr3[65] = new String[]{"↑", "&uarr;"};
        strArr3[66] = new String[]{"→", "&rarr;"};
        strArr3[67] = new String[]{"↓", "&darr;"};
        strArr3[68] = new String[]{"↔", "&harr;"};
        strArr3[69] = new String[]{"↵", "&crarr;"};
        strArr3[70] = new String[]{"⇐", "&lArr;"};
        strArr3[71] = new String[]{"⇑", "&uArr;"};
        strArr3[72] = new String[]{"⇒", "&rArr;"};
        strArr3[73] = new String[]{"⇓", "&dArr;"};
        strArr3[74] = new String[]{"⇔", "&hArr;"};
        strArr3[75] = new String[]{"∀", "&forall;"};
        strArr3[76] = new String[]{"∂", "&part;"};
        strArr3[77] = new String[]{"∃", "&exist;"};
        strArr3[78] = new String[]{"∅", "&empty;"};
        strArr3[79] = new String[]{"∇", "&nabla;"};
        strArr3[80] = new String[]{"∈", "&isin;"};
        strArr3[81] = new String[]{"∉", "&notin;"};
        strArr3[82] = new String[]{"∋", "&ni;"};
        strArr3[83] = new String[]{"∏", "&prod;"};
        strArr3[84] = new String[]{"∑", "&sum;"};
        strArr3[85] = new String[]{"−", "&minus;"};
        strArr3[86] = new String[]{"∗", "&lowast;"};
        strArr3[87] = new String[]{"√", "&radic;"};
        strArr3[88] = new String[]{"∝", "&prop;"};
        strArr3[89] = new String[]{"∞", "&infin;"};
        strArr3[90] = new String[]{"∠", "&ang;"};
        strArr3[91] = new String[]{"∧", "&and;"};
        strArr3[92] = new String[]{"∨", "&or;"};
        strArr3[93] = new String[]{"∩", "&cap;"};
        strArr3[94] = new String[]{"∪", "&cup;"};
        strArr3[95] = new String[]{"∫", "&int;"};
        strArr3[96] = new String[]{"∴", "&there4;"};
        strArr3[97] = new String[]{"∼", "&sim;"};
        strArr3[98] = new String[]{"≅", "&cong;"};
        strArr3[99] = new String[]{"≈", "&asymp;"};
        strArr3[100] = new String[]{"≠", "&ne;"};
        strArr3[101] = new String[]{"≡", "&equiv;"};
        strArr3[102] = new String[]{"≤", "&le;"};
        strArr3[103] = new String[]{"≥", "&ge;"};
        strArr3[104] = new String[]{"⊂", "&sub;"};
        strArr3[105] = new String[]{"⊃", "&sup;"};
        strArr3[106] = new String[]{"⊆", "&sube;"};
        strArr3[107] = new String[]{"⊇", "&supe;"};
        strArr3[108] = new String[]{"⊕", "&oplus;"};
        strArr3[109] = new String[]{"⊗", "&otimes;"};
        strArr3[110] = new String[]{"⊥", "&perp;"};
        strArr3[111] = new String[]{"⋅", "&sdot;"};
        strArr3[112] = new String[]{"⌈", "&lceil;"};
        strArr3[113] = new String[]{"⌉", "&rceil;"};
        strArr3[114] = new String[]{"⌊", "&lfloor;"};
        strArr3[115] = new String[]{"⌋", "&rfloor;"};
        strArr3[116] = new String[]{"〈", "&lang;"};
        strArr3[117] = new String[]{"〉", "&rang;"};
        strArr3[118] = new String[]{"◊", "&loz;"};
        strArr3[119] = new String[]{"♠", "&spades;"};
        strArr3[120] = new String[]{"♣", "&clubs;"};
        strArr3[121] = new String[]{"♥", "&hearts;"};
        strArr3[122] = new String[]{"♦", "&diams;"};
        strArr3[123] = new String[]{"Œ", "&OElig;"};
        strArr3[124] = new String[]{"œ", "&oelig;"};
        strArr3[125] = new String[]{"Š", "&Scaron;"};
        strArr3[126] = new String[]{"š", "&scaron;"};
        strArr3[127] = new String[]{"Ÿ", "&Yuml;"};
        strArr3[128] = new String[]{"ˆ", "&circ;"};
        strArr3[129] = new String[]{"˜", "&tilde;"};
        strArr3[130] = new String[]{" ", "&ensp;"};
        strArr3[131] = new String[]{" ", "&emsp;"};
        strArr3[132] = new String[]{" ", "&thinsp;"};
        strArr3[133] = new String[]{"‌", "&zwnj;"};
        strArr3[134] = new String[]{"‍", "&zwj;"};
        strArr3[135] = new String[]{"‎", "&lrm;"};
        strArr3[136] = new String[]{"‏", "&rlm;"};
        strArr3[137] = new String[]{"–", "&ndash;"};
        strArr3[138] = new String[]{"—", "&mdash;"};
        strArr3[139] = new String[]{"‘", "&lsquo;"};
        strArr3[140] = new String[]{"’", "&rsquo;"};
        strArr3[141] = new String[]{"‚", "&sbquo;"};
        strArr3[142] = new String[]{"“", "&ldquo;"};
        strArr3[143] = new String[]{"”", "&rdquo;"};
        strArr3[144] = new String[]{"„", "&bdquo;"};
        strArr3[145] = new String[]{"†", "&dagger;"};
        strArr3[146] = new String[]{"‡", "&Dagger;"};
        strArr3[147] = new String[]{"‰", "&permil;"};
        strArr3[148] = new String[]{"‹", "&lsaquo;"};
        strArr3[149] = new String[]{"›", "&rsaquo;"};
        strArr3[150] = new String[]{"€", "&euro;"};
        HTML40_EXTENDED_ESCAPE = strArr3;
        HTML40_EXTENDED_UNESCAPE = invert(strArr3);
        String[][] strArr4 = {new String[]{"\"", "&quot;"}, new String[]{"&", "&amp;"}, new String[]{"<", "&lt;"}, new String[]{">", "&gt;"}};
        BASIC_ESCAPE = strArr4;
        BASIC_UNESCAPE = invert(strArr4);
        String[][] strArr5 = {new String[]{"'", "&apos;"}};
        APOS_ESCAPE = strArr5;
        APOS_UNESCAPE = invert(strArr5);
        String[][] strArr6 = {new String[]{"\b", "\\b"}, new String[]{IOUtils.LINE_SEPARATOR_UNIX, "\\n"}, new String[]{"\t", "\\t"}, new String[]{"\f", "\\f"}, new String[]{"\r", "\\r"}};
        JAVA_CTRL_CHARS_ESCAPE = strArr6;
        JAVA_CTRL_CHARS_UNESCAPE = invert(strArr6);
    }

    public static String[][] APOS_ESCAPE() {
        return (String[][]) APOS_ESCAPE.clone();
    }

    public static String[][] APOS_UNESCAPE() {
        return (String[][]) APOS_UNESCAPE.clone();
    }

    public static String[][] BASIC_ESCAPE() {
        return (String[][]) BASIC_ESCAPE.clone();
    }

    public static String[][] BASIC_UNESCAPE() {
        return (String[][]) BASIC_UNESCAPE.clone();
    }

    public static String[][] HTML40_EXTENDED_ESCAPE() {
        return (String[][]) HTML40_EXTENDED_ESCAPE.clone();
    }

    public static String[][] HTML40_EXTENDED_UNESCAPE() {
        return (String[][]) HTML40_EXTENDED_UNESCAPE.clone();
    }

    public static String[][] ISO8859_1_ESCAPE() {
        return (String[][]) ISO8859_1_ESCAPE.clone();
    }

    public static String[][] ISO8859_1_UNESCAPE() {
        return (String[][]) ISO8859_1_UNESCAPE.clone();
    }

    public static String[][] JAVA_CTRL_CHARS_ESCAPE() {
        return (String[][]) JAVA_CTRL_CHARS_ESCAPE.clone();
    }

    public static String[][] JAVA_CTRL_CHARS_UNESCAPE() {
        return (String[][]) JAVA_CTRL_CHARS_UNESCAPE.clone();
    }

    public static String[][] invert(String[][] strArr) {
        String[][] strArr2 = (String[][]) Array.newInstance(String.class, new int[]{strArr.length, 2});
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i][0] = strArr[i][1];
            strArr2[i][1] = strArr[i][0];
        }
        return strArr2;
    }
}
