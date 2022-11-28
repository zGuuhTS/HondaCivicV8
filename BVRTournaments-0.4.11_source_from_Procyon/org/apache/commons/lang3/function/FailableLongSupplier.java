// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

@FunctionalInterface
public interface FailableLongSupplier<E extends Throwable>
{
    long getAsLong() throws E, Throwable;
}
