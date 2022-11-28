// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.builder;

@FunctionalInterface
public interface Diffable<T>
{
    DiffResult<T> diff(final T p0);
}
