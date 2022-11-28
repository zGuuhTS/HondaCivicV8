// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

@FunctionalInterface
public interface BiStringLookup<U> extends StringLookup
{
    default String lookup(final String key, final U object) {
        return this.lookup(key);
    }
}
