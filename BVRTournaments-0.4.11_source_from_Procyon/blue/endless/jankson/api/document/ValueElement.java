// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.document;

public interface ValueElement extends DocumentElement
{
    default boolean isValueEntry() {
        return true;
    }
}
