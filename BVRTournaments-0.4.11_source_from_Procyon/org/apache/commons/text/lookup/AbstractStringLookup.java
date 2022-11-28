// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import org.apache.commons.lang3.StringUtils;

abstract class AbstractStringLookup implements StringLookup
{
    protected static final char SPLIT_CH = ':';
    protected static final String SPLIT_STR;
    
    static String toLookupKey(final String left, final String right) {
        return toLookupKey(left, AbstractStringLookup.SPLIT_STR, right);
    }
    
    static String toLookupKey(final String left, final String separator, final String right) {
        return left + separator + right;
    }
    
    @Deprecated
    protected String substringAfter(final String value, final char ch) {
        return StringUtils.substringAfter(value, ch);
    }
    
    @Deprecated
    protected String substringAfter(final String value, final String str) {
        return StringUtils.substringAfter(value, str);
    }
    
    @Deprecated
    protected String substringAfterLast(final String value, final char ch) {
        return StringUtils.substringAfterLast(value, ch);
    }
    
    static {
        SPLIT_STR = String.valueOf(':');
    }
}
