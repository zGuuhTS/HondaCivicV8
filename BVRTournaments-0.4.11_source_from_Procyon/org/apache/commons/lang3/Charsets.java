// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3;

import java.nio.charset.Charset;

class Charsets
{
    static Charset toCharset(final Charset charset) {
        return (charset == null) ? Charset.defaultCharset() : charset;
    }
    
    static Charset toCharset(final String charsetName) {
        return (charsetName == null) ? Charset.defaultCharset() : Charset.forName(charsetName);
    }
    
    static String toCharsetName(final String charsetName) {
        return (charsetName == null) ? Charset.defaultCharset().name() : charsetName;
    }
}
