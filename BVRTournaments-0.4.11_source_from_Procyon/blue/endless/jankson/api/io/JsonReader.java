// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.io;

import blue.endless.jankson.api.document.DocumentElement;
import java.util.function.Consumer;
import blue.endless.jankson.api.document.JanksonDocument;
import java.util.ArrayDeque;
import blue.endless.jankson.api.document.ObjectElement;
import blue.endless.jankson.impl.context.ParserContext;
import java.util.Deque;
import java.io.Reader;

public class JsonReader implements DocumentReader
{
    private final Reader source;
    private final DeserializerOptions options;
    private Deque<ParserContext<?>> contextStack;
    private ObjectElement documentRoot;
    
    public JsonReader(final Reader source) {
        this(source, new DeserializerOptions(new DeserializerOptions.Hint[0]));
    }
    
    public JsonReader(final Reader source, final DeserializerOptions options) {
        this.contextStack = new ArrayDeque<ParserContext<?>>();
        this.documentRoot = new ObjectElement();
        this.source = source;
        this.options = options;
    }
    
    @Override
    public JanksonDocument readDocument() {
        return null;
    }
    
    private void pushContext(final ParserContext<?> ctx, final Consumer<DocumentElement> consumer) {
    }
}
