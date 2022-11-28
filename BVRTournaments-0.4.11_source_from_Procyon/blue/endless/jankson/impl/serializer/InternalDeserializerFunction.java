// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.serializer;

import blue.endless.jankson.api.io.DeserializationException;
import blue.endless.jankson.api.Marshaller;

@FunctionalInterface
public interface InternalDeserializerFunction<B>
{
    B deserialize(final Object p0, final Marshaller p1) throws DeserializationException;
}
