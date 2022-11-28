// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

@FunctionalInterface
public interface FailableDoubleBinaryOperator<E extends Throwable>
{
    double applyAsDouble(final double p0, final double p1) throws E, Throwable;
}
