// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.document;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ObjectElement implements ValueElement
{
    protected CommentElement commentBefore;
    protected CommentElement commentAfter;
    protected List<DocumentElement> entries;
    
    public ObjectElement() {
        this.commentBefore = null;
        this.commentAfter = null;
        this.entries = new ArrayList<DocumentElement>();
    }
    
    @Nullable
    public ValueElement get(final String key) {
        for (final DocumentElement entry : this.entries) {
            if (entry instanceof KeyValuePairElement && ((KeyValuePairElement)entry).getKey().equals(key)) {
                return ((KeyValuePairElement)entry).getValue();
            }
        }
        return null;
    }
    
    public DocumentElement put(final String key, final ValueElement value) {
        if (value instanceof KeyValuePairElement || value instanceof CommentElement) {
            throw new IllegalArgumentException();
        }
        for (final DocumentElement entry : this.entries) {
            if (entry instanceof KeyValuePairElement) {
                final KeyValuePairElement pair = (KeyValuePairElement)entry;
                if (pair.getKey().equals(key)) {
                    return pair.setValue(value);
                }
                continue;
            }
        }
        this.entries.add(new KeyValuePairElement(key, value));
        return null;
    }
    
    public CommentElement commentBefore() {
        return this.commentBefore;
    }
    
    public CommentElement commentAfter() {
        return this.commentAfter;
    }
    
    public void setCommentBefore(final String comment) {
        this.commentBefore = new CommentElement(comment);
    }
    
    public void setCommentBefore(final CommentElement comment) {
        this.commentBefore = comment;
    }
    
    public void setCommentAfter(final String comment) {
        (this.commentAfter = new CommentElement(comment)).setLineEnd(true);
    }
    
    public void setCommentAfter(final CommentElement comment) {
        this.commentAfter = comment;
    }
    
    @Override
    public boolean isComment() {
        return false;
    }
    
    @Override
    public CommentElement asComment() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isValueEntry() {
        return true;
    }
    
    @Override
    public ValueElement asValueEntry() {
        return this;
    }
}
