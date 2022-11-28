// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.serializer;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.io.DeserializationException;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Marshaller;

@FunctionalInterface
public interface InternalDeserializerFunction<B>
{
    B deserialize(final Object p0, final Marshaller p1) throws DeserializationException;
}
