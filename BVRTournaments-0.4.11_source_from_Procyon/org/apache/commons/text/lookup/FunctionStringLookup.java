// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.util.Objects;
import java.util.Map;
import java.util.function.Function;

final class FunctionStringLookup<V> extends AbstractStringLookup
{
    private final Function<String, V> function;
    
    static <R> FunctionStringLookup<R> on(final Function<String, R> function) {
        return new FunctionStringLookup<R>(function);
    }
    
    static <V> FunctionStringLookup<V> on(final Map<String, V> map) {
        return on(map::get);
    }
    
    private FunctionStringLookup(final Function<String, V> function) {
        this.function = function;
    }
    
    @Override
    public String lookup(final String key) {
        if (this.function == null) {
            return null;
        }
        V obj;
        try {
            obj = this.function.apply(key);
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
        return super.toString() + " [function=" + this.function + "]";
    }
}
