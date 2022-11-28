// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.document;

public class CommentElement implements FormattingElement
{
    protected String value;
    protected boolean lineEnd;
    
    public CommentElement(final String comment) {
        this.value = comment;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String setValue(final String value) {
        final String result = this.value;
        this.value = value;
        return result;
    }
    
    public boolean isLineEnd() {
        return this.lineEnd;
    }
    
    public void setLineEnd(final boolean lineEnd) {
        this.lineEnd = lineEnd;
    }
    
    @Override
    public boolean isComment() {
        return true;
    }
    
    @Override
    public CommentElement asComment() {
        return this;
    }
}
