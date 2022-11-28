// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.tuple;

import java.util.Objects;
import org.apache.commons.lang3.builder.CompareToBuilder;
import java.io.Serializable;
import java.util.Map;

public abstract class Pair<L, R> implements Map.Entry<L, R>, Comparable<Pair<L, R>>, Serializable
{
    private static final long serialVersionUID = 4954918890077093841L;
    public static final Pair<?, ?>[] EMPTY_ARRAY;
    
    public static <L, R> Pair<L, R>[] emptyArray() {
        return (Pair<L, R>[])Pair.EMPTY_ARRAY;
    }
    
    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return ImmutablePair.of(left, right);
    }
    
    public static <L, R> Pair<L, R> of(final Map.Entry<L, R> pair) {
        return ImmutablePair.of(pair);
    }
    
    @Override
    public int compareTo(final Pair<L, R> other) {
        return new CompareToBuilder().append(this.getLeft(), other.getLeft()).append(this.getRight(), other.getRight()).toComparison();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Map.Entry) {
            final Map.Entry<?, ?> other = (Map.Entry<?, ?>)obj;
            return Objects.equals(this.getKey(), other.getKey()) && Objects.equals(this.getValue(), other.getValue());
        }
        return false;
    }
    
    @Override
    public final L getKey() {
        return this.getLeft();
    }
    
    public abstract L getLeft();
    
    public abstract R getRight();
    
    @Override
    public R getValue() {
        return this.getRight();
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getKey()) ^ Objects.hashCode(this.getValue());
    }
    
    @Override
    public String toString() {
        return "(" + this.getLeft() + ',' + this.getRight() + ')';
    }
    
    public String toString(final String format) {
        return String.format(format, this.getLeft(), this.getRight());
    }
    
    static {
        EMPTY_ARRAY = new PairAdapter[0];
    }
    
    private static final class PairAdapter<L, R> extends Pair<L, R>
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public L getLeft() {
            return null;
        }
        
        @Override
        public R getRight() {
            return null;
        }
        
        @Override
        public R setValue(final R value) {
            return null;
        }
    }
}
