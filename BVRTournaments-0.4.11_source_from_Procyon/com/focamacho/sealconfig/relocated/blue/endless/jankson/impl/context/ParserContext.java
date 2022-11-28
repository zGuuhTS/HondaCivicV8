// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.SyntaxError;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Jankson;

public interface ParserContext<T>
{
    boolean consume(final int p0, final Jankson p1) throws SyntaxError;
    
    void eof() throws SyntaxError;
    
    boolean isComplete();
    
    T getResult() throws SyntaxError;
}
