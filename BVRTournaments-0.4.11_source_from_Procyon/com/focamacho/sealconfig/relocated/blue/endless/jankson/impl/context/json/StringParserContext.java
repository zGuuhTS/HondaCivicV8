// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.json;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.SyntaxError;
import java.util.Locale;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Jankson;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonPrimitive;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.ParserContext;

public class StringParserContext implements ParserContext<JsonPrimitive>
{
    private static final String HEX_DIGITS = "0123456789abcdefABCDEF";
    private int quote;
    private boolean escape;
    private int unicodeUs;
    private StringBuilder builder;
    private boolean complete;
    private String unicodeSequence;
    
    public StringParserContext(final int quote) {
        this.escape = false;
        this.unicodeUs = 0;
        this.builder = new StringBuilder();
        this.complete = false;
        this.unicodeSequence = "";
        this.quote = quote;
    }
    
    @Override
    public boolean consume(final int codePoint, final Jankson loader) {
        if (this.escape) {
            if (this.unicodeUs > 0) {
                if (codePoint == 117 || codePoint == 85) {
                    ++this.unicodeUs;
                    return true;
                }
                if ("0123456789abcdefABCDEF".indexOf(codePoint) != -1) {
                    this.unicodeSequence += (char)codePoint;
                    if (this.unicodeSequence.length() == 4) {
                        this.emitUnicodeSequence(loader);
                        this.escape = false;
                    }
                    return true;
                }
                this.emitUnicodeSequence(loader);
                return this.escape = false;
            }
            else {
                this.escape = false;
                switch (codePoint) {
                    case 98: {
                        this.builder.append('\b');
                        return true;
                    }
                    case 102: {
                        this.builder.append('\f');
                        return true;
                    }
                    case 110: {
                        this.builder.append('\n');
                        return true;
                    }
                    case 10: {
                        return true;
                    }
                    case 114: {
                        this.builder.append('\r');
                        return true;
                    }
                    case 116: {
                        this.builder.append('\t');
                        return true;
                    }
                    case 34: {
                        this.builder.append('\"');
                        return true;
                    }
                    case 39: {
                        this.builder.append('\'');
                        return true;
                    }
                    case 92: {
                        this.builder.append('\\');
                        return true;
                    }
                    case 85:
                    case 117: {
                        this.escape = true;
                        this.unicodeUs = 1;
                        return true;
                    }
                    default: {
                        this.builder.append((char)codePoint);
                        return true;
                    }
                }
            }
        }
        else {
            if (codePoint == this.quote) {
                return this.complete = true;
            }
            if (codePoint == 92) {
                return this.escape = true;
            }
            if (codePoint == 10) {
                this.complete = true;
                return false;
            }
            if (codePoint < 65535) {
                this.builder.append((char)codePoint);
                return true;
            }
            final int temp = codePoint - 65536;
            final int highSurrogate = (temp >>> 10) + 55296;
            final int lowSurrogate = (temp & 0x3FF) + 56320;
            this.builder.append((char)highSurrogate);
            this.builder.append((char)lowSurrogate);
            return true;
        }
    }
    
    private void emitUnicodeSequence(final Jankson loader) {
        if (this.unicodeUs > 1) {
            --this.unicodeUs;
            this.builder.append("\\");
            for (int i = 0; i < this.unicodeUs; ++i) {
                this.builder.append('u');
            }
            while (this.unicodeSequence.length() < 4) {
                this.unicodeSequence = "0" + this.unicodeSequence;
            }
            this.builder.append(this.unicodeSequence.toLowerCase(Locale.ROOT));
        }
        else {
            final int sequence = (int)Long.parseLong(this.unicodeSequence, 16);
            final char[] chars2;
            final char[] chars = chars2 = Character.toChars(sequence);
            for (final char ch : chars2) {
                this.builder.append(ch);
            }
        }
        this.unicodeUs = 0;
        this.unicodeSequence = "";
        this.escape = false;
    }
    
    @Override
    public boolean isComplete() {
        return this.complete;
    }
    
    @Override
    public JsonPrimitive getResult() {
        return JsonPrimitive.of(this.builder.toString());
    }
    
    @Override
    public void eof() throws SyntaxError {
        throw new SyntaxError("Expected to find '" + (char)this.quote + "' to end a String, found EOF instead.");
    }
}
