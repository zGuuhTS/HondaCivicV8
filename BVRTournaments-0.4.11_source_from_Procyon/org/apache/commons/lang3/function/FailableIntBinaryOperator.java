// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

@FunctionalInterface
public interface FailableIntBinaryOperator<E extends Throwable>
{
    int applyAsInt(final int p0, final int p1) throws E, Throwable;
}
