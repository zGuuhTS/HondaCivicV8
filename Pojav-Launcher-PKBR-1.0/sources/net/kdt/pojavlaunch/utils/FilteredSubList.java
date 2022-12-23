package net.kdt.pojavlaunch.utils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FilteredSubList<E> extends AbstractList<E> implements List<E> {
    private final ArrayList<E> mArrayList = new ArrayList<>();

    public interface BasicPredicate<E> {
        boolean test(E e);
    }

    public FilteredSubList(Iterable<E> motherList, BasicPredicate<E> filter) {
        refresh(motherList, filter);
    }

    public FilteredSubList(E[] motherList, BasicPredicate<E> filter) {
        refresh(motherList, filter);
    }

    public void refresh(Iterable<E> motherList, BasicPredicate<E> filter) {
        if (!this.mArrayList.isEmpty()) {
            this.mArrayList.clear();
        }
        for (E item : motherList) {
            if (filter.test(item)) {
                this.mArrayList.add(item);
            }
        }
        this.mArrayList.trimToSize();
    }

    public void refresh(E[] motherArray, BasicPredicate<E> filter) {
        if (!this.mArrayList.isEmpty()) {
            this.mArrayList.clear();
        }
        for (E item : motherArray) {
            if (filter.test(item)) {
                this.mArrayList.add(item);
            }
        }
        this.mArrayList.trimToSize();
    }

    public int size() {
        return this.mArrayList.size();
    }

    public Iterator<E> iterator() {
        return this.mArrayList.iterator();
    }

    public boolean remove(Object o) {
        return this.mArrayList.remove(o);
    }

    public boolean removeAll(Collection<?> c) {
        return this.mArrayList.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return this.mArrayList.retainAll(c);
    }

    public void clear() {
        this.mArrayList.clear();
    }

    public E get(int index) {
        return this.mArrayList.get(index);
    }

    public E remove(int index) {
        return this.mArrayList.remove(index);
    }

    public ListIterator<E> listIterator() {
        return this.mArrayList.listIterator();
    }

    public ListIterator<E> listIterator(int index) {
        return this.mArrayList.listIterator(index);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        return this.mArrayList.subList(fromIndex, toIndex);
    }
}
