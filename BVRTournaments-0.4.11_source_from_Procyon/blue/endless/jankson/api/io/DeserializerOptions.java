// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.io;

import blue.endless.jankson.impl.MarshallerImpl;
import blue.endless.jankson.api.Marshaller;
import java.util.EnumSet;

public class DeserializerOptions
{
    private final EnumSet<Hint> hints;
    private final Marshaller marshaller;
    
    public DeserializerOptions(final Hint... hints) {
        this.hints = EnumSet.noneOf(Hint.class);
        this.marshaller = MarshallerImpl.getFallback();
    }
    
    public DeserializerOptions(final Marshaller marshaller, final Hint... hints) {
        this.hints = EnumSet.noneOf(Hint.class);
        for (final Hint hint : hints) {
            this.hints.add(hint);
        }
        this.marshaller = marshaller;
    }
    
    public boolean hasHint(final Hint hint) {
        return this.hints.contains(hint);
    }
    
    public Marshaller getMarshaller() {
        return this.marshaller;
    }
    
    public enum Hint
    {
        ALLOW_BARE_ROOTS, 
        ALLOW_UNQUOTED_KEYS, 
        MERGE_DUPLICATE_OBJECTS, 
        ALLOW_KEY_EQUALS_VALUE;
    }
}
