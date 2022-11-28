// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.context.json;

import blue.endless.jankson.api.SyntaxError;
import java.util.Arrays;
import blue.endless.jankson.api.io.DeserializerOptions;
import blue.endless.jankson.api.document.ObjectElement;
import blue.endless.jankson.impl.context.ElementContext;

public class ObjectElementContext implements ElementContext<ObjectElement>
{
    private static final char[] CHARS_WHITESPACE;
    private static final char[] CHARS_JOINER;
    private ObjectElement result;
    private boolean openBraced;
    private boolean closeBraced;
    
    public ObjectElementContext() {
        this.result = new ObjectElement();
    }
    
    @Override
    public boolean consume(final char codePoint, final int line, final int column, final DeserializerOptions options) throws SyntaxError {
        if (!this.openBraced & !options.hasHint(DeserializerOptions.Hint.ALLOW_BARE_ROOTS)) {
            if (codePoint == '{') {
                return this.openBraced = true;
            }
            if (Arrays.binarySearch(ObjectElementContext.CHARS_WHITESPACE, codePoint) >= 0) {
                return true;
            }
            if (Arrays.binarySearch(ObjectElementContext.CHARS_JOINER, codePoint) >= 0) {
                return true;
            }
        }
        else if (!this.closeBraced && !this.openBraced && codePoint == '=') {
            throw new SyntaxError("Found close brace for a bare object.", line, column);
        }
        return false;
    }
    
    @Override
    public void eof() throws SyntaxError {
    }
    
    @Override
    public boolean isComplete() {
        return false;
    }
    
    @Override
    public ObjectElement getResult() throws SyntaxError {
        return this.result;
    }
    
    static {
        CHARS_WHITESPACE = new char[] { '\t', '\n', '\u000b', '\f', '\r', ' ', '\u0085', ' ', '\u1680', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2007', '\u2008', '\u2009', '\u200a', '\u2028', '\u2029', '\u202f', '\u205f', '\u3000' };
        CHARS_JOINER = new char[] { '\u180e', '\u200b', '\u200c', '\u200d', '\u2060', '\ufeff' };
    }
}
