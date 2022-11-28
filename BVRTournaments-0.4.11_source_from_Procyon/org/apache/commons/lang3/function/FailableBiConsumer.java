// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

import java.util.Objects;

@FunctionalInterface
public interface FailableBiConsumer<T, U, E extends Throwable>
{
    public static final FailableBiConsumer NOP = (t, u) -> {};
    
    default <T, U, E extends Throwable> FailableBiConsumer<T, U, E> nop() {
        return (FailableBiConsumer<T, U, E>)FailableBiConsumer.NOP;
    }
    
    void accept(final T p0, final U p1) throws E, Throwable;
    
    default FailableBiConsumer<T, U, E> andThen(final FailableBiConsumer<? super T, ? super U, E> after) {
        Objects.requireNonNull(after);
        return (t, u) -> {
            this.accept(t, u);
            after.accept((Object)t, (Object)u);
        };
    }
}
