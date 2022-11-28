// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

final class UrlDecoderStringLookup extends AbstractStringLookup
{
    static final UrlDecoderStringLookup INSTANCE;
    
    String decode(final String key, final String enc) throws UnsupportedEncodingException {
        return URLDecoder.decode(key, enc);
    }
    
    @Override
    public String lookup(final String key) {
        if (key == null) {
            return null;
        }
        final String enc = StandardCharsets.UTF_8.name();
        try {
            return this.decode(key, enc);
        }
        catch (UnsupportedEncodingException e) {
            throw IllegalArgumentExceptions.format(e, "%s: source=%s, encoding=%s", e, key, enc);
        }
    }
    
    static {
        INSTANCE = new UrlDecoderStringLookup();
    }
}
