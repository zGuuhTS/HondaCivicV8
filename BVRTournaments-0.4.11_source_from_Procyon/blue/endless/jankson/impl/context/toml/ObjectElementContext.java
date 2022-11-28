// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.context.toml;

import blue.endless.jankson.api.SyntaxError;
import blue.endless.jankson.api.io.DeserializerOptions;
import blue.endless.jankson.api.element.JsonObject;
import blue.endless.jankson.impl.context.ElementContext;

public class ObjectElementContext implements ElementContext<JsonObject>
{
    @Override
    public boolean consume(final char character, final int lineNum, final int charNum, final DeserializerOptions options) throws SyntaxError {
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
    public JsonObject getResult() throws SyntaxError {
        return null;
    }
}
