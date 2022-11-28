// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element;

import java.io.IOException;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.JsonGrammar;
import java.io.Writer;

public class JsonNull extends JsonElement
{
    public static final JsonNull INSTANCE;
    
    private JsonNull() {
    }
    
    @Override
    public String toString() {
        return "null";
    }
    
    @Override
    public boolean equals(final Object other) {
        return other == JsonNull.INSTANCE;
    }
    
    @Override
    public int hashCode() {
        return 0;
    }
    
    @Override
    public String toJson(final boolean comments, final boolean newlines, final int depth) {
        return "null";
    }
    
    @Override
    public void toJson(final Writer writer, final JsonGrammar grammar, final int depth) throws IOException {
        writer.write("null");
    }
    
    @Override
    public JsonNull clone() {
        return this;
    }
    
    static {
        INSTANCE = new JsonNull();
    }
}
