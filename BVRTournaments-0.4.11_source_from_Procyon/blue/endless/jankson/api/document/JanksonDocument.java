// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.document;

public class JanksonDocument
{
    protected DocumentElement root;
    protected String indent;
    
    public JanksonDocument() {
        this.indent = "\t";
    }
    
    public DocumentElement getRoot() {
        return this.root;
    }
}
