// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

final class UrlEncoderStringLookup extends AbstractStringLookup
{
    static final UrlEncoderStringLookup INSTANCE;
    
    String encode(final String key, final String enc) throws UnsupportedEncodingException {
        return URLEncoder.encode(key, enc);
    }
    
    @Override
    public String lookup(final String key) {
        if (key == null) {
            return null;
        }
        final String enc = StandardCharsets.UTF_8.name();
        try {
            return this.encode(key, enc);
        }
        catch (UnsupportedEncodingException e) {
            throw IllegalArgumentExceptions.format(e, "%s: source=%s, encoding=%s", e, key, enc);
        }
    }
    
    static {
        INSTANCE = new UrlEncoderStringLookup();
    }
}
