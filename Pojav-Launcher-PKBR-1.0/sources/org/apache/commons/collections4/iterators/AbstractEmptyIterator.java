package org.apache.commons.collections4.iterators;

import java.util.NoSuchElementException;

abstract class AbstractEmptyIterator<E> {
    protected AbstractEmptyIterator() {
    }

    public void add(E e) {
        throw new UnsupportedOperationException("add() not supported for empty Iterator");
    }

    public boolean hasNext() {
        return false;
    }

    public boolean hasPrevious() {
        return false;
    }

    public E next() {
        throw new NoSuchElementException("Iterator contains no elements");
    }

    public int nextIndex() {
        return 0;
    }

    public E previous() {
        throw new NoSuchElementException("Iterator contains no elements");
    }

    public int previousIndex() {
        return -1;
    }

    public void remove() {
        throw new IllegalStateException("Iterator contains no elements");
    }

    public void reset() {
    }

    public void set(E e) {
        throw new IllegalStateException("Iterator contains no elements");
    }
}
