// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element;

import java.util.ListIterator;
import javax.annotation.Nullable;
import java.util.Objects;
import javax.annotation.Nonnull;
import java.io.IOException;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.serializer.CommentSerializer;
import java.io.Writer;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.JsonGrammar;
import java.util.Iterator;
import java.util.Collection;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.MarshallerImpl;
import java.util.ArrayList;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Marshaller;
import java.util.List;

public class JsonArray extends JsonElement implements List<JsonElement>, Iterable<JsonElement>
{
    private List<Entry> entries;
    protected Marshaller marshaller;
    
    public JsonArray() {
        this.entries = new ArrayList<Entry>();
        this.marshaller = MarshallerImpl.getFallback();
    }
    
    public <T> JsonArray(final T[] ts, final Marshaller marshaller) {
        this.entries = new ArrayList<Entry>();
        this.marshaller = MarshallerImpl.getFallback();
        this.marshaller = marshaller;
        for (final T t : ts) {
            this.add(marshaller.serialize(t));
        }
    }
    
    public JsonArray(final Collection<?> ts, final Marshaller marshaller) {
        this.entries = new ArrayList<Entry>();
        this.marshaller = MarshallerImpl.getFallback();
        this.marshaller = marshaller;
        for (final Object t : ts) {
            this.add(marshaller.serialize(t));
        }
    }
    
    @Override
    public JsonElement get(final int i) {
        return this.entries.get(i).value;
    }
    
    public String getString(final int index, final String defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asString();
        }
        return defaultValue;
    }
    
    public boolean getBoolean(final int index, final boolean defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asBoolean(defaultValue);
        }
        return defaultValue;
    }
    
    public byte getByte(final int index, final byte defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asByte(defaultValue);
        }
        return defaultValue;
    }
    
    public char getChar(final int index, final char defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asChar(defaultValue);
        }
        return defaultValue;
    }
    
    public short getShort(final int index, final short defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asShort(defaultValue);
        }
        return defaultValue;
    }
    
    public int getInt(final int index, final int defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asInt(defaultValue);
        }
        return defaultValue;
    }
    
    public long getLong(final int index, final long defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asLong(defaultValue);
        }
        return defaultValue;
    }
    
    public float getFloat(final int index, final float defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asFloat(defaultValue);
        }
        return defaultValue;
    }
    
    public double getDouble(final int index, final double defaultValue) {
        final JsonElement elem = this.get(index);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asDouble(defaultValue);
        }
        return defaultValue;
    }
    
    public String getComment(final int i) {
        return this.entries.get(i).getComment();
    }
    
    public void setComment(final int i, final String comment) {
        this.entries.get(i).setComment(comment);
    }
    
    @Override
    public String toJson(final boolean comments, final boolean newlines, final int depth) {
        final JsonGrammar grammar = JsonGrammar.builder().withComments(comments).printWhitespace(newlines).build();
        return this.toJson(grammar, depth);
    }
    
    @Override
    public void toJson(final Writer writer, final JsonGrammar grammar, final int depth) throws IOException {
        final int effectiveDepth = grammar.isBareRootObject() ? (depth - 1) : depth;
        writer.append((CharSequence)"[");
        if (this.entries.size() > 0) {
            if (grammar.shouldOutputWhitespace()) {
                writer.append('\n');
            }
            else {
                writer.append(' ');
            }
        }
        for (int i = 0; i < this.entries.size(); ++i) {
            final Entry entry = this.entries.get(i);
            if (grammar.shouldOutputWhitespace()) {
                for (int j = 0; j < effectiveDepth + 1; ++j) {
                    writer.append((CharSequence)"\t");
                }
            }
            CommentSerializer.print(writer, entry.getComment(), effectiveDepth, grammar);
            writer.append((CharSequence)entry.value.toJson(grammar, depth + 1));
            if (grammar.shouldPrintCommas()) {
                if (i < this.entries.size() - 1 || grammar.isTrailingCommas()) {
                    writer.append((CharSequence)",");
                    if (i < this.entries.size() - 1 && !grammar.shouldOutputWhitespace()) {
                        writer.append(' ');
                    }
                }
            }
            else {
                writer.append((CharSequence)" ");
            }
            if (grammar.shouldOutputWhitespace()) {
                writer.append('\n');
            }
        }
        if (this.entries.size() > 0 && grammar.shouldOutputWhitespace() && depth > 0) {
            for (int k = 0; k < effectiveDepth; ++k) {
                writer.append((CharSequence)"\t");
            }
        }
        if (this.entries.size() > 0 && !grammar.shouldOutputWhitespace()) {
            writer.append(' ');
        }
        writer.append(']');
    }
    
    @Override
    public String toString() {
        return this.toJson(true, false, 0);
    }
    
    public boolean add(@Nonnull final JsonElement e, final String comment) {
        final Entry entry = new Entry();
        entry.value = e;
        entry.setComment(comment);
        this.entries.add(entry);
        return true;
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof JsonArray)) {
            return false;
        }
        final List<Entry> a = this.entries;
        final List<Entry> b = ((JsonArray)other).entries;
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); ++i) {
            final Entry ae = a.get(i);
            final Entry be = b.get(i);
            if (!ae.value.equals(be.value)) {
                return false;
            }
            if (!Objects.equals(ae.getComment(), be.getComment())) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return this.entries.hashCode();
    }
    
    @Nullable
    public <E> E get(@Nonnull final Class<E> clazz, final int index) {
        final JsonElement elem = this.get(index);
        return this.marshaller.marshall(clazz, elem);
    }
    
    public void setMarshaller(final Marshaller marshaller) {
        this.marshaller = marshaller;
    }
    
    public Marshaller getMarshaller() {
        return this.marshaller;
    }
    
    @Override
    public JsonArray clone() {
        final JsonArray result = new JsonArray();
        result.marshaller = this.marshaller;
        for (final Entry entry : this.entries) {
            result.add(entry.value.clone(), entry.getComment());
        }
        return result;
    }
    
    @Override
    public int size() {
        return this.entries.size();
    }
    
    @Override
    public boolean add(@Nonnull final JsonElement e) {
        final Entry entry = new Entry();
        entry.value = e;
        this.entries.add(entry);
        return true;
    }
    
    @Override
    public boolean addAll(final Collection<? extends JsonElement> c) {
        boolean result = false;
        for (final JsonElement elem : c) {
            result |= this.add(elem);
        }
        return result;
    }
    
    @Override
    public void clear() {
        this.entries.clear();
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null || !(o instanceof JsonElement)) {
            return false;
        }
        for (final Entry entry : this.entries) {
            if (entry.value.equals(o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }
    
    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < this.entries.size(); ++i) {
            final Entry cur = this.entries.get(i);
            if (cur.value.equals(o)) {
                this.entries.remove(i);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException("removeAll not supported");
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException("retainAll not supported");
    }
    
    @Override
    public JsonElement[] toArray() {
        final JsonElement[] result = new JsonElement[this.entries.size()];
        for (int i = 0; i < this.entries.size(); ++i) {
            result[i] = this.entries.get(i).value;
        }
        return result;
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.entries.size()) {
            a = (T[])new Object[this.entries.size()];
        }
        for (int i = 0; i < this.entries.size(); ++i) {
            a[i] = (T)this.entries.get(i).value;
        }
        if (a.length > this.entries.size()) {
            a[this.entries.size()] = null;
        }
        return a;
    }
    
    @Override
    public Iterator<JsonElement> iterator() {
        return new EntryIterator(this.entries);
    }
    
    @Override
    public void add(final int index, final JsonElement element) {
        this.entries.add(index, new Entry(element));
    }
    
    @Override
    public boolean addAll(final int index, final Collection<? extends JsonElement> elements) {
        if (elements.isEmpty()) {
            return false;
        }
        int i = index;
        for (final JsonElement element : elements) {
            this.entries.add(i, new Entry(element));
            ++i;
        }
        return true;
    }
    
    @Override
    public int indexOf(final Object obj) {
        if (obj == null) {
            return -1;
        }
        for (int i = 0; i < this.entries.size(); ++i) {
            final JsonElement val = this.entries.get(i).value;
            if (val != null && val.equals(obj)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final Object obj) {
        if (obj == null) {
            return -1;
        }
        for (int i = this.entries.size() - 1; i >= 0; --i) {
            final JsonElement val = this.entries.get(i).value;
            if (val != null && val.equals(obj)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public ListIterator<JsonElement> listIterator() {
        return new EntryIterator(this.entries);
    }
    
    @Override
    public ListIterator<JsonElement> listIterator(final int index) {
        return new EntryIterator(this.entries, index);
    }
    
    @Override
    public JsonElement remove(final int index) {
        return this.entries.remove(index).value;
    }
    
    @Override
    public JsonElement set(final int index, final JsonElement element) {
        final Entry cur = new Entry(element);
        final Entry old = this.entries.get(index);
        if (old != null) {
            cur.setComment(old.getComment());
        }
        this.entries.set(index, cur);
        return (old == null) ? null : old.value;
    }
    
    @Override
    public List<JsonElement> subList(final int arg0, final int arg1) {
        throw new UnsupportedOperationException();
    }
    
    private static class EntryIterator implements ListIterator<JsonElement>
    {
        private final ListIterator<Entry> delegate;
        
        public EntryIterator(final List<Entry> list) {
            this.delegate = list.listIterator();
        }
        
        public EntryIterator(final List<Entry> list, final int index) {
            this.delegate = list.listIterator(index);
        }
        
        @Override
        public boolean hasNext() {
            return this.delegate.hasNext();
        }
        
        @Override
        public JsonElement next() {
            return this.delegate.next().value;
        }
        
        @Override
        public void remove() {
            this.delegate.remove();
        }
        
        @Override
        public void add(final JsonElement elem) {
            this.delegate.add(new Entry(elem));
        }
        
        @Override
        public boolean hasPrevious() {
            return this.delegate.hasPrevious();
        }
        
        @Override
        public int nextIndex() {
            return this.delegate.nextIndex();
        }
        
        @Override
        public JsonElement previous() {
            return this.delegate.previous().value;
        }
        
        @Override
        public int previousIndex() {
            return this.delegate.previousIndex();
        }
        
        @Override
        public void set(final JsonElement obj) {
            this.delegate.set(new Entry(obj));
        }
    }
    
    private static class Entry
    {
        String comment;
        JsonElement value;
        
        public Entry() {
        }
        
        public Entry(final JsonElement value) {
            this.value = value;
        }
        
        @Override
        public boolean equals(final Object other) {
            if (!(other instanceof Entry)) {
                return false;
            }
            final Entry o = (Entry)other;
            return Objects.equals(this.comment, o.comment) && Objects.equals(this.value, o.value);
        }
        
        public String getComment() {
            return this.comment;
        }
        
        public void setComment(final String comment) {
            if (comment != null && !comment.trim().isEmpty()) {
                this.comment = comment;
            }
            else {
                this.comment = null;
            }
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.comment, this.value);
        }
    }
}
