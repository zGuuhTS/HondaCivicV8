// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element;

import java.io.IOException;
import java.io.Writer;
import java.io.StringWriter;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.JsonGrammar;

public abstract class JsonElement implements Cloneable
{
    public abstract JsonElement clone();
    
    public String toJson() {
        return this.toJson(false, false, 0);
    }
    
    public String toJson(final boolean comments, final boolean newlines) {
        return this.toJson(comments, newlines, 0);
    }
    
    @Deprecated
    public abstract String toJson(final boolean p0, final boolean p1, final int p2);
    
    public String toJson(final JsonGrammar grammar, final int depth) {
        final StringWriter w = new StringWriter();
        try {
            this.toJson(w, grammar, depth);
            w.flush();
            return w.toString();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public String toJson(final JsonGrammar grammar) {
        return this.toJson(grammar, 0);
    }
    
    public abstract void toJson(final Writer p0, final JsonGrammar p1, final int p2) throws IOException;
}
