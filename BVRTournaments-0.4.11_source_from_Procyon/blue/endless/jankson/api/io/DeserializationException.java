// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.io;

public class DeserializationException extends Exception
{
    private static final long serialVersionUID = 8425560848572561283L;
    
    public DeserializationException() {
    }
    
    public DeserializationException(final String message) {
        super(message);
    }
    
    public DeserializationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public DeserializationException(final Throwable cause) {
        super(cause);
    }
}
