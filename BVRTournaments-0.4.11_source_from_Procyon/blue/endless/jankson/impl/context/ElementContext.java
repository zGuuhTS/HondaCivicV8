// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.context;

import blue.endless.jankson.api.SyntaxError;
import blue.endless.jankson.api.io.DeserializerOptions;

public interface ElementContext<T>
{
    boolean consume(final char p0, final int p1, final int p2, final DeserializerOptions p3) throws SyntaxError;
    
    void eof() throws SyntaxError;
    
    boolean isComplete();
    
    T getResult() throws SyntaxError;
}
