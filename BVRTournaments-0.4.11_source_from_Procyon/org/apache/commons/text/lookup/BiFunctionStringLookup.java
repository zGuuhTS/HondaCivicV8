// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.util.Objects;
import java.util.Map;
import java.util.function.BiFunction;

final class BiFunctionStringLookup<P, R> implements BiStringLookup<P>
{
    private final BiFunction<String, P, R> biFunction;
    
    static <U, T> BiFunctionStringLookup<U, T> on(final BiFunction<String, U, T> biFunction) {
        return new BiFunctionStringLookup<U, T>(biFunction);
    }
    
    static <U, T> BiFunctionStringLookup<U, T> on(final Map<String, T> map) {
        return on((key, u) -> map.get(key));
    }
    
    private BiFunctionStringLookup(final BiFunction<String, P, R> biFunction) {
        this.biFunction = biFunction;
    }
    
    @Override
    public String lookup(final String key) {
        return this.lookup(key, null);
    }
    
    @Override
    public String lookup(final String key, final P object) {
        if (this.biFunction == null) {
            return null;
        }
        R obj;
        try {
            obj = this.biFunction.apply(key, object);
        }
        catch (SecurityException | NullPointerException | IllegalArgumentException ex2) {
            final RuntimeException ex;
            final RuntimeException e = ex;
            return null;
        }
        return Objects.toString(obj, null);
    }
    
    @Override
    public String toString() {
        return super.toString() + " [function=" + this.biFunction + "]";
    }
}
