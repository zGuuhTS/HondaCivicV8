// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.json;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.SyntaxError;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Jankson;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonPrimitive;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.ParserContext;

public class TokenParserContext implements ParserContext<JsonPrimitive>
{
    private String token;
    private boolean complete;
    
    public TokenParserContext(final int firstCodePoint) {
        this.token = "";
        this.complete = false;
        this.token += (char)firstCodePoint;
    }
    
    @Override
    public boolean consume(final int codePoint, final Jankson loader) throws SyntaxError {
        if (this.complete) {
            return false;
        }
        if (codePoint != 126 && !Character.isUnicodeIdentifierPart(codePoint)) {
            this.complete = true;
            return false;
        }
        if (codePoint < 65535) {
            this.token += (char)codePoint;
            return true;
        }
        final int temp = codePoint - 65536;
        final int highSurrogate = (temp >>> 10) + 55296;
        final int lowSurrogate = (temp & 0x3FF) + 56320;
        this.token += (char)highSurrogate;
        this.token += (char)lowSurrogate;
        return true;
    }
    
    @Override
    public void eof() throws SyntaxError {
        this.complete = true;
    }
    
    @Override
    public boolean isComplete() {
        return this.complete;
    }
    
    @Override
    public JsonPrimitive getResult() throws SyntaxError {
        return JsonPrimitive.of(this.token);
    }
}
