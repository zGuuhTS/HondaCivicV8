// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.stream;

import org.apache.commons.lang3.function.FailableFunction;
import java.util.function.Consumer;
import org.apache.commons.lang3.function.FailableConsumer;
import java.util.function.Predicate;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.function.FailablePredicate;
import java.util.Collections;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.lang.reflect.Array;
import java.util.function.Function;
import java.util.function.BinaryOperator;
import java.util.function.BiConsumer;
import java.util.Set;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.Collection;

public class Streams
{
    public static <O> FailableStream<O> stream(final Collection<O> stream) {
        return stream(stream.stream());
    }
    
    public static <O> FailableStream<O> stream(final Stream<O> stream) {
        return new FailableStream<O>(stream);
    }
    
    public static <O> Collector<O, ?, O[]> toArray(final Class<O> pElementType) {
        return new ArrayCollector<O>(pElementType);
    }
    
    public static class ArrayCollector<O> implements Collector<O, List<O>, O[]>
    {
        private static final Set<Characteristics> characteristics;
        private final Class<O> elementType;
        
        public ArrayCollector(final Class<O> elementType) {
            this.elementType = elementType;
        }
        
        @Override
        public BiConsumer<List<O>, O> accumulator() {
            return List::add;
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return ArrayCollector.characteristics;
        }
        
        @Override
        public BinaryOperator<List<O>> combiner() {
            return (BinaryOperator<List<O>>)((left, right) -> {
                left.addAll(right);
                return left;
            });
        }
        
        @Override
        public Function<List<O>, O[]> finisher() {
            final O[] array;
            return (Function<List<O>, O[]>)(list -> {
                array = (O[])Array.newInstance(this.elementType, list.size());
                return list.toArray(array);
            });
        }
        
        @Override
        public Supplier<List<O>> supplier() {
            return (Supplier<List<O>>)ArrayList::new;
        }
        
        static {
            characteristics = Collections.emptySet();
        }
    }
    
    public static class FailableStream<O>
    {
        private Stream<O> stream;
        private boolean terminated;
        
        public FailableStream(final Stream<O> stream) {
            this.stream = stream;
        }
        
        public boolean allMatch(final FailablePredicate<O, ?> predicate) {
            this.assertNotTerminated();
            return this.stream().allMatch(Failable.asPredicate(predicate));
        }
        
        public boolean anyMatch(final FailablePredicate<O, ?> predicate) {
            this.assertNotTerminated();
            return this.stream().anyMatch(Failable.asPredicate(predicate));
        }
        
        protected void assertNotTerminated() {
            if (this.terminated) {
                throw new IllegalStateException("This stream is already terminated.");
            }
        }
        
        public <A, R> R collect(final Collector<? super O, A, R> collector) {
            this.makeTerminated();
            return this.stream().collect(collector);
        }
        
        public <A, R> R collect(final Supplier<R> pupplier, final BiConsumer<R, ? super O> accumulator, final BiConsumer<R, R> combiner) {
            this.makeTerminated();
            return this.stream().collect(pupplier, accumulator, combiner);
        }
        
        public FailableStream<O> filter(final FailablePredicate<O, ?> predicate) {
            this.assertNotTerminated();
            this.stream = this.stream.filter(Failable.asPredicate(predicate));
            return this;
        }
        
        public void forEach(final FailableConsumer<O, ?> action) {
            this.makeTerminated();
            this.stream().forEach(Failable.asConsumer(action));
        }
        
        protected void makeTerminated() {
            this.assertNotTerminated();
            this.terminated = true;
        }
        
        public <R> FailableStream<R> map(final FailableFunction<O, R, ?> mapper) {
            this.assertNotTerminated();
            return new FailableStream<R>(this.stream.map((Function<? super O, ? extends R>)Failable.asFunction((FailableFunction<O, ? extends R, ?>)mapper)));
        }
        
        public O reduce(final O identity, final BinaryOperator<O> accumulator) {
            this.makeTerminated();
            return this.stream().reduce(identity, accumulator);
        }
        
        public Stream<O> stream() {
            return this.stream;
        }
    }
}
