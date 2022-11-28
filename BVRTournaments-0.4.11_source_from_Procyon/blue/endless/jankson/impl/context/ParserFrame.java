// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.context;

import blue.endless.jankson.api.SyntaxError;
import java.util.function.Consumer;

public class ParserFrame<T>
{
    private ElementContext<T> context;
    private Consumer<T> consumer;
    
    public ParserFrame(final ElementContext<T> context, final Consumer<T> consumer) {
        this.context = context;
        this.consumer = consumer;
    }
    
    public ElementContext<T> context() {
        return this.context;
    }
    
    public Consumer<T> consumer() {
        return this.consumer;
    }
    
    public void supply() throws SyntaxError {
        this.consumer.accept(this.context.getResult());
    }
}
