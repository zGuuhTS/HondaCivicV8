// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.document;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ArrayElement implements ValueElement
{
    protected CommentElement commentBefore;
    protected CommentElement commentAfter;
    protected List<DocumentElement> entries;
    
    public ArrayElement() {
        this.entries = new ArrayList<DocumentElement>();
    }
    
    public DocumentElement get(final int index) {
        int cur = 0;
        for (final DocumentElement entry : this.entries) {
            if (entry.isValueEntry()) {
                if (index == cur) {
                    return entry;
                }
                ++cur;
            }
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + cur);
    }
    
    public int size() {
        int result = 0;
        for (final DocumentElement entry : this.entries) {
            if (entry.isValueEntry()) {
                ++result;
            }
        }
        return result;
    }
    
    public void add(final DocumentElement entry) {
        this.entries.add(entry);
    }
    
    public int entrySize() {
        return this.entries.size();
    }
    
    public DocumentElement getEntry(final int i) {
        return this.entries.get(i);
    }
    
    public Iterator<DocumentElement> entryIterator() {
        return this.entries.iterator();
    }
    
    public int elementCount() {
        int counter = 0;
        for (final DocumentElement entry : this.entries) {
            if (!(entry instanceof CommentElement)) {
                ++counter;
            }
        }
        return counter;
    }
    
    @Override
    public ValueElement asValueEntry() {
        return this;
    }
}
