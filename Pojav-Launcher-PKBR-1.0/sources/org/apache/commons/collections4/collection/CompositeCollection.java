package org.apache.commons.collections4.collection;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections4.iterators.EmptyIterator;
import org.apache.commons.collections4.iterators.IteratorChain;

public class CompositeCollection<E> implements Collection<E>, Serializable {
    private static final long serialVersionUID = 8417515734108306801L;
    private final List<Collection<E>> all = new ArrayList();
    private CollectionMutator<E> mutator;

    public interface CollectionMutator<E> extends Serializable {
        boolean add(CompositeCollection<E> compositeCollection, List<Collection<E>> list, E e);

        boolean addAll(CompositeCollection<E> compositeCollection, List<Collection<E>> list, Collection<? extends E> collection);

        boolean remove(CompositeCollection<E> compositeCollection, List<Collection<E>> list, Object obj);
    }

    public CompositeCollection() {
    }

    public CompositeCollection(Collection<E> collection) {
        addComposited(collection);
    }

    public CompositeCollection(Collection<E> collection, Collection<E> collection2) {
        addComposited(collection, collection2);
    }

    public CompositeCollection(Collection<E>... collectionArr) {
        addComposited(collectionArr);
    }

    /* renamed from: of */
    public static <E> Collection<E> m42of(Collection<E>... collectionArr) {
        return new CompositeCollection(collectionArr);
    }

    public boolean add(E e) {
        CollectionMutator<E> collectionMutator = this.mutator;
        if (collectionMutator != null) {
            return collectionMutator.add(this, this.all, e);
        }
        throw new UnsupportedOperationException("add() is not supported on CompositeCollection without a CollectionMutator strategy");
    }

    public boolean addAll(Collection<? extends E> collection) {
        CollectionMutator<E> collectionMutator = this.mutator;
        if (collectionMutator != null) {
            return collectionMutator.addAll(this, this.all, collection);
        }
        throw new UnsupportedOperationException("addAll() is not supported on CompositeCollection without a CollectionMutator strategy");
    }

    public void addComposited(Collection<E> collection) {
        this.all.add(collection);
    }

    public void addComposited(Collection<E> collection, Collection<E> collection2) {
        this.all.add(collection);
        this.all.add(collection2);
    }

    public void addComposited(Collection<E>... collectionArr) {
        this.all.addAll(Arrays.asList(collectionArr));
    }

    public void clear() {
        for (Collection<E> clear : this.all) {
            clear.clear();
        }
    }

    public boolean contains(Object obj) {
        for (Collection<E> contains : this.all) {
            if (contains.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(Collection<?> collection) {
        for (Object contains : collection) {
            if (!contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public List<Collection<E>> getCollections() {
        return Collections.unmodifiableList(this.all);
    }

    /* access modifiers changed from: protected */
    public CollectionMutator<E> getMutator() {
        return this.mutator;
    }

    public boolean isEmpty() {
        for (Collection<E> isEmpty : this.all) {
            if (!isEmpty.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Iterator<E> iterator() {
        if (this.all.isEmpty()) {
            return EmptyIterator.emptyIterator();
        }
        IteratorChain iteratorChain = new IteratorChain();
        for (Collection<E> it : this.all) {
            iteratorChain.addIterator(it.iterator());
        }
        return iteratorChain;
    }

    public boolean remove(Object obj) {
        CollectionMutator<E> collectionMutator = this.mutator;
        if (collectionMutator != null) {
            return collectionMutator.remove(this, this.all, obj);
        }
        throw new UnsupportedOperationException("remove() is not supported on CompositeCollection without a CollectionMutator strategy");
    }

    public boolean removeAll(Collection<?> collection) {
        boolean z = false;
        if (collection.size() == 0) {
            return false;
        }
        for (Collection<E> removeAll : this.all) {
            z |= removeAll.removeAll(collection);
        }
        return z;
    }

    public void removeComposited(Collection<E> collection) {
        this.all.remove(collection);
    }

    public boolean retainAll(Collection<?> collection) {
        boolean z = false;
        for (Collection<E> retainAll : this.all) {
            z |= retainAll.retainAll(collection);
        }
        return z;
    }

    public void setMutator(CollectionMutator<E> collectionMutator) {
        this.mutator = collectionMutator;
    }

    public int size() {
        int i = 0;
        for (Collection<E> size : this.all) {
            i += size.size();
        }
        return i;
    }

    public Object[] toArray() {
        Object[] objArr = new Object[size()];
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
        return objArr;
    }

    public <T> T[] toArray(T[] tArr) {
        int size = size();
        if (tArr.length < size) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
        }
        int i = 0;
        for (Collection<E> it : this.all) {
            for (E e : it) {
                tArr[i] = e;
                i++;
            }
        }
        if (tArr.length > size) {
            tArr[size] = null;
        }
        return (Object[]) tArr;
    }

    public Collection<E> toCollection() {
        return new ArrayList(this);
    }
}
