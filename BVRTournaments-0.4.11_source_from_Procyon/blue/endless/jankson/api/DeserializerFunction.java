// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api;

import blue.endless.jankson.api.io.DeserializationException;
import blue.endless.jankson.impl.serializer.InternalDeserializerFunction;

@FunctionalInterface
public interface DeserializerFunction<A, B> extends InternalDeserializerFunction<B>
{
    B apply(final A p0, final Marshaller p1) throws DeserializationException;
    
    default B deserialize(final Object a, final Marshaller m) throws DeserializationException {
        try {
            return this.apply(a, m);
        }
        catch (ClassCastException ex) {
            throw new DeserializationException(ex);
        }
    }
}
