// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.context.json;

import java.util.Locale;
import blue.endless.jankson.api.SyntaxError;
import blue.endless.jankson.api.Jankson;
import blue.endless.jankson.api.element.JsonPrimitive;
import blue.endless.jankson.impl.context.ParserContext;

public class NumberParserContext implements ParserContext<JsonPrimitive>
{
    private String numberString;
    private boolean complete;
    private String acceptedChars;
    
    public NumberParserContext(final int firstCodePoint) {
        this.numberString = "";
        this.complete = false;
        this.acceptedChars = "0123456789.+-eExabcdefInityNn";
        this.numberString += (char)firstCodePoint;
    }
    
    @Override
    public boolean consume(final int codePoint, final Jankson loader) throws SyntaxError {
        if (this.complete) {
            return false;
        }
        if (this.acceptedChars.indexOf(codePoint) != -1) {
            this.numberString += (char)codePoint;
            return true;
        }
        this.complete = true;
        return false;
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
        final String lc = this.numberString.toLowerCase(Locale.ROOT);
        if (lc.equals("infinity") || lc.equals("+infinity")) {
            return JsonPrimitive.of(Double.POSITIVE_INFINITY);
        }
        if (lc.equals("-infinity")) {
            return JsonPrimitive.of(Double.NEGATIVE_INFINITY);
        }
        if (lc.equals("nan")) {
            return JsonPrimitive.of(Double.NaN);
        }
        if (this.numberString.startsWith(".")) {
            this.numberString = '0' + this.numberString;
        }
        if (this.numberString.endsWith(".")) {
            this.numberString += '0';
        }
        if (this.numberString.startsWith("0x")) {
            this.numberString = this.numberString.substring(2);
            try {
                final Long l = Long.parseUnsignedLong(this.numberString, 16);
                return JsonPrimitive.of(l);
            }
            catch (NumberFormatException nfe) {
                throw new SyntaxError("Tried to parse '" + this.numberString + "' as a hexadecimal number, but it appears to be invalid.");
            }
        }
        if (this.numberString.startsWith("-0x")) {
            this.numberString = this.numberString.substring(3);
            try {
                final Long l = -Long.parseUnsignedLong(this.numberString, 16);
                return JsonPrimitive.of(l);
            }
            catch (NumberFormatException nfe) {
                throw new SyntaxError("Tried to parse '" + this.numberString + "' as a hexadecimal number, but it appears to be invalid.");
            }
        }
        if (this.numberString.indexOf(46) != -1) {
            try {
                final Double d = Double.valueOf(this.numberString);
                return JsonPrimitive.of(d);
            }
            catch (NumberFormatException ex) {
                throw new SyntaxError("Tried to parse '" + this.numberString + "' as a floating-point number, but it appears to be invalid.");
            }
        }
        try {
            final Long l = Long.valueOf(this.numberString);
            return JsonPrimitive.of(l);
        }
        catch (NumberFormatException ex) {
            throw new SyntaxError("Tried to parse '" + this.numberString + "' as an integer, but it appears to be invalid.");
        }
    }
}
