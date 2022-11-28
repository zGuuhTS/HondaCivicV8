// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

@FunctionalInterface
public interface FailableDoubleSupplier<E extends Throwable>
{
    double getAsDouble() throws E, Throwable;
}
