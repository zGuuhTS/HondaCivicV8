// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.api;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.io.DeserializationException;
import java.lang.reflect.Type;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonElement;

public interface Marshaller
{
    JsonElement serialize(final Object p0);
    
     <E> E marshall(final Class<E> p0, final JsonElement p1);
    
     <E> E marshall(final Type p0, final JsonElement p1);
    
     <E> E marshallCarefully(final Class<E> p0, final JsonElement p1) throws DeserializationException;
}
