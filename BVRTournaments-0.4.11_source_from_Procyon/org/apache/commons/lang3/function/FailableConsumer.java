// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

import java.util.Objects;

@FunctionalInterface
public interface FailableConsumer<T, E extends Throwable>
{
    public static final FailableConsumer NOP = t -> {};
    
    default <T, E extends Throwable> FailableConsumer<T, E> nop() {
        return (FailableConsumer<T, E>)FailableConsumer.NOP;
    }
    
    void accept(final T p0) throws E, Throwable;
    
    default FailableConsumer<T, E> andThen(final FailableConsumer<? super T, E> after) {
        Objects.requireNonNull(after);
        return t -> {
            this.accept(t);
            after.accept((Object)t);
        };
    }
}
