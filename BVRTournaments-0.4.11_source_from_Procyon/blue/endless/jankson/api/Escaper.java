// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Escaper
{
    private static final Set<Character.UnicodeBlock> DEFAULT_BLOCKS;
    
    private Escaper() {
    }
    
    public static String escapeString(final String s) {
        return escapeString(s, '\"', Escaper.DEFAULT_BLOCKS);
    }
    
    public static String escapeString(final String s, final char quoteChar, final Set<Character.UnicodeBlock> unquotedBlocks) {
        final StringBuilder result = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); ++i) {
            final char ch = s.charAt(i);
            switch (ch) {
                case '\\': {
                    result.append("\\\\");
                    break;
                }
                case '\r': {
                    result.append("\\r");
                    break;
                }
                case '\n': {
                    result.append("\\n");
                    break;
                }
                case '\b': {
                    result.append("\\b");
                    break;
                }
                case '\f': {
                    result.append("\\f");
                    break;
                }
                case '\t': {
                    result.append("\\t");
                    break;
                }
                case '\"': {
                    if (quoteChar == ch) {
                        result.append("\\\"");
                        break;
                    }
                    result.append(ch);
                    break;
                }
                case '\'': {
                    if (quoteChar == ch) {
                        result.append("\\'");
                        break;
                    }
                    result.append(ch);
                    break;
                }
                default: {
                    if (Character.isBmpCodePoint(ch)) {
                        final Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
                        if (ch != '\uffff' && !Character.isISOControl(ch) && block != null && unquotedBlocks.contains(block)) {
                            result.append(ch);
                        }
                        else {
                            result.append(unicodeEscape(ch));
                        }
                        break;
                    }
                    ++i;
                    final char upper = s.charAt(i);
                    final int codePoint = Character.toCodePoint(ch, upper);
                    result.append(unicodeEscape(codePoint));
                    break;
                }
            }
        }
        return result.toString();
    }
    
    private static String unicodeEscape(final int codePoint) {
        String codeString;
        for (codeString = "" + Integer.toHexString(codePoint); codeString.length() < 4; codeString = "0" + codeString) {}
        return "\\u" + codeString;
    }
    
    static {
        final HashSet<Character.UnicodeBlock> tmp = new HashSet<Character.UnicodeBlock>();
        tmp.add(Character.UnicodeBlock.BASIC_LATIN);
        DEFAULT_BLOCKS = Collections.unmodifiableSet((Set<? extends Character.UnicodeBlock>)tmp);
    }
}
