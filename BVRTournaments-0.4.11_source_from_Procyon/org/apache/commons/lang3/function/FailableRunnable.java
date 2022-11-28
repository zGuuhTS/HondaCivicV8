// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.function;

@FunctionalInterface
public interface FailableRunnable<E extends Throwable>
{
    void run() throws E, Throwable;
}
