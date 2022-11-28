// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.document;

public class PrimitiveValueElement implements ValueElement
{
    Object value;
    
    public PrimitiveValueElement(final String s) {
        this.value = s;
    }
    
    public PrimitiveValueElement(final long l) {
        this.value = l;
    }
    
    public PrimitiveValueElement(final double d) {
        this.value = d;
    }
    
    @Override
    public ValueElement asValueEntry() {
        return null;
    }
    
    public String asString() {
        if (this.value == null) {
            return "null";
        }
        return this.value.toString();
    }
}
