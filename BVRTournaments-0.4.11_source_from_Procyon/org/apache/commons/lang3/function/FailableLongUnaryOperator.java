// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

import java.util.Objects;

public interface FailableLongUnaryOperator<E extends Throwable>
{
    public static final FailableLongUnaryOperator NOP = t -> 0L;
    
    default <E extends Throwable> FailableLongUnaryOperator<E> identity() {
        return t -> t;
    }
    
    default <E extends Throwable> FailableLongUnaryOperator<E> nop() {
        return (FailableLongUnaryOperator<E>)FailableLongUnaryOperator.NOP;
    }
    
    default FailableLongUnaryOperator<E> andThen(final FailableLongUnaryOperator<E> after) {
        Objects.requireNonNull(after);
        return t -> after.applyAsLong(this.applyAsLong(t));
    }
    
    long applyAsLong(final long p0) throws E, Throwable;
    
    default FailableLongUnaryOperator<E> compose(final FailableLongUnaryOperator<E> before) {
        Objects.requireNonNull(before);
        return v -> this.applyAsLong(before.applyAsLong(v));
    }
}
