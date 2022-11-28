// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.document;

import java.util.ArrayList;
import java.util.List;

public class KeyValuePairElement implements DocumentElement
{
    protected CommentElement commentBefore;
    protected CommentElement commentAfter;
    protected List<DocumentElement> entries;
    protected String key;
    protected ValueElement valueEntry;
    
    public KeyValuePairElement(final String key, final ValueElement value) {
        this.commentBefore = null;
        this.commentAfter = null;
        this.entries = new ArrayList<DocumentElement>();
        this.key = key;
        this.entries.add(value);
        this.valueEntry = value;
    }
    
    public KeyValuePairElement(final String key, final DocumentElement value, final String comment) {
        this.commentBefore = null;
        this.commentAfter = null;
        this.entries = new ArrayList<DocumentElement>();
        this.commentBefore = new CommentElement(comment);
    }
    
    public String getKey() {
        return this.key;
    }
    
    public ValueElement getValue() {
        return this.valueEntry;
    }
    
    public DocumentElement setValue(final ValueElement value) {
        final ValueElement result = this.valueEntry;
        for (int i = 0; i < this.entries.size(); ++i) {
            if (this.valueEntry == this.entries.get(i)) {
                this.entries.set(i, value);
                this.valueEntry = value;
                return result;
            }
        }
        this.entries.add(value);
        this.valueEntry = value;
        return null;
    }
}
