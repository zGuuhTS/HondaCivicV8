package org.apache.commons.collections4.iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class IteratorChain<E> implements Iterator<E> {
    private Iterator<? extends E> currentIterator;
    private boolean isLocked;
    private final Queue<Iterator<? extends E>> iteratorChain;
    private Iterator<? extends E> lastUsedIterator;

    public IteratorChain() {
        this.iteratorChain = new LinkedList();
        this.currentIterator = null;
        this.lastUsedIterator = null;
        this.isLocked = false;
    }

    public IteratorChain(Collection<Iterator<? extends E>> collection) {
        this.iteratorChain = new LinkedList();
        this.currentIterator = null;
        this.lastUsedIterator = null;
        this.isLocked = false;
        for (Iterator<? extends E> addIterator : collection) {
            addIterator(addIterator);
        }
    }

    public IteratorChain(Iterator<? extends E> it) {
        this.iteratorChain = new LinkedList();
        this.currentIterator = null;
        this.lastUsedIterator = null;
        this.isLocked = false;
        addIterator(it);
    }

    public IteratorChain(Iterator<? extends E> it, Iterator<? extends E> it2) {
        this.iteratorChain = new LinkedList();
        this.currentIterator = null;
        this.lastUsedIterator = null;
        this.isLocked = false;
        addIterator(it);
        addIterator(it2);
    }

    public IteratorChain(Iterator<? extends E>... itArr) {
        this.iteratorChain = new LinkedList();
        this.currentIterator = null;
        this.lastUsedIterator = null;
        this.isLocked = false;
        for (Iterator<? extends E> addIterator : itArr) {
            addIterator(addIterator);
        }
    }

    private void checkLocked() {
        if (this.isLocked) {
            throw new UnsupportedOperationException("IteratorChain cannot be changed after the first use of a method from the Iterator interface");
        }
    }

    private void lockChain() {
        if (!this.isLocked) {
            this.isLocked = true;
        }
    }

    public void addIterator(Iterator<? extends E> it) {
        checkLocked();
        if (it != null) {
            this.iteratorChain.add(it);
            return;
        }
        throw new NullPointerException("Iterator must not be null");
    }

    public boolean hasNext() {
        lockChain();
        updateCurrentIterator();
        Iterator<? extends E> it = this.currentIterator;
        this.lastUsedIterator = it;
        return it.hasNext();
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public E next() {
        lockChain();
        updateCurrentIterator();
        Iterator<? extends E> it = this.currentIterator;
        this.lastUsedIterator = it;
        return it.next();
    }

    public void remove() {
        lockChain();
        if (this.currentIterator == null) {
            updateCurrentIterator();
        }
        this.lastUsedIterator.remove();
    }

    public int size() {
        return this.iteratorChain.size();
    }

    /* access modifiers changed from: protected */
    public void updateCurrentIterator() {
        if (this.currentIterator == null) {
            this.currentIterator = this.iteratorChain.isEmpty() ? EmptyIterator.emptyIterator() : this.iteratorChain.remove();
            this.lastUsedIterator = this.currentIterator;
        }
        while (!this.currentIterator.hasNext() && !this.iteratorChain.isEmpty()) {
            this.currentIterator = this.iteratorChain.remove();
        }
    }
}
