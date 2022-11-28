// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.api;

import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Consumer;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.io.DeserializationException;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonNull;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.json.ElementParserContext;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.ParserContext;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.json.ObjectParserContext;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import java.io.File;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.MarshallerImpl;
import java.util.ArrayDeque;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.AnnotatedElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonObject;
import java.util.Deque;

public class Jankson
{
    private Deque<ParserFrame<?>> contextStack;
    private JsonObject root;
    private int line;
    private int column;
    private int withheldCodePoint;
    private Marshaller marshaller;
    private boolean allowBareRootObject;
    private int retries;
    private SyntaxError delayedError;
    private AnnotatedElement rootElement;
    
    private Jankson(final Builder builder) {
        this.contextStack = new ArrayDeque<ParserFrame<?>>();
        this.line = 0;
        this.column = 0;
        this.withheldCodePoint = -1;
        this.marshaller = MarshallerImpl.getFallback();
        this.allowBareRootObject = false;
        this.retries = 0;
        this.delayedError = null;
    }
    
    @Nonnull
    public JsonObject load(final String s) throws SyntaxError {
        final ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes(Charset.forName("UTF-8")));
        try {
            return this.load(in);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Nonnull
    public JsonObject load(final File f) throws IOException, SyntaxError {
        try (final InputStream in = new FileInputStream(f)) {
            return this.load(in);
        }
    }
    
    @Nonnull
    public JsonObject load(final InputStream in) throws IOException, SyntaxError {
        final InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        this.withheldCodePoint = -1;
        this.root = null;
        this.push(new ObjectParserContext(this.allowBareRootObject), it -> this.root = it);
        while (this.root == null) {
            if (this.delayedError != null) {
                throw this.delayedError;
            }
            if (this.withheldCodePoint != -1) {
                ++this.retries;
                if (this.retries > 25) {
                    throw new IOException("Parser got stuck near line " + this.line + " column " + this.column);
                }
                this.processCodePoint(this.withheldCodePoint);
            }
            else {
                final int inByte = reader.read();
                if (inByte == -1) {
                    while (!this.contextStack.isEmpty()) {
                        final ParserFrame<?> frame = this.contextStack.pop();
                        try {
                            ((ParserFrame<Object>)frame).context.eof();
                            if (!((ParserFrame<Object>)frame).context.isComplete()) {
                                continue;
                            }
                            frame.supply();
                        }
                        catch (SyntaxError error) {
                            error.setStartParsing(((ParserFrame<Object>)frame).startLine, ((ParserFrame<Object>)frame).startCol);
                            error.setEndParsing(this.line, this.column);
                            throw error;
                        }
                    }
                    if (this.root == null) {
                        (this.root = new JsonObject()).setMarshaller(this.marshaller);
                    }
                    return this.root;
                }
                this.processCodePoint(inByte);
            }
        }
        return this.root;
    }
    
    @Nonnull
    public JsonElement loadElement(final String s) throws SyntaxError {
        final ByteArrayInputStream in = new ByteArrayInputStream(s.getBytes(Charset.forName("UTF-8")));
        try {
            return this.loadElement(in);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @Nonnull
    public JsonElement loadElement(final File f) throws IOException, SyntaxError {
        try (final InputStream in = new FileInputStream(f)) {
            return this.loadElement(in);
        }
    }
    
    @Nonnull
    public JsonElement loadElement(final InputStream in) throws IOException, SyntaxError {
        final InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        this.withheldCodePoint = -1;
        this.rootElement = null;
        this.push(new ElementParserContext(), it -> this.rootElement = it);
        while (this.rootElement == null) {
            if (this.delayedError != null) {
                throw this.delayedError;
            }
            if (this.withheldCodePoint != -1) {
                ++this.retries;
                if (this.retries > 25) {
                    throw new IOException("Parser got stuck near line " + this.line + " column " + this.column);
                }
                this.processCodePoint(this.withheldCodePoint);
            }
            else {
                final int inByte = reader.read();
                if (inByte == -1) {
                    while (!this.contextStack.isEmpty()) {
                        final ParserFrame<?> frame = this.contextStack.pop();
                        try {
                            ((ParserFrame<Object>)frame).context.eof();
                            if (!((ParserFrame<Object>)frame).context.isComplete()) {
                                continue;
                            }
                            frame.supply();
                        }
                        catch (SyntaxError error) {
                            error.setStartParsing(((ParserFrame<Object>)frame).startLine, ((ParserFrame<Object>)frame).startCol);
                            error.setEndParsing(this.line, this.column);
                            throw error;
                        }
                    }
                    if (this.rootElement == null) {
                        return JsonNull.INSTANCE;
                    }
                    return this.rootElement.getElement();
                }
                else {
                    this.processCodePoint(inByte);
                }
            }
        }
        return this.rootElement.getElement();
    }
    
    public <T> T fromJson(final JsonObject obj, final Class<T> clazz) {
        return this.marshaller.marshall(clazz, obj);
    }
    
    public <T> T fromJson(final String json, final Class<T> clazz) throws SyntaxError {
        final JsonObject obj = this.load(json);
        return this.fromJson(obj, clazz);
    }
    
    public <T> T fromJsonCarefully(final String json, final Class<T> clazz) throws SyntaxError, DeserializationException {
        final JsonObject obj = this.load(json);
        return this.fromJsonCarefully(obj, clazz);
    }
    
    public <T> T fromJsonCarefully(final JsonObject obj, final Class<T> clazz) throws DeserializationException {
        return this.marshaller.marshallCarefully(clazz, obj);
    }
    
    public <T> JsonElement toJson(final T t) {
        return this.marshaller.serialize(t);
    }
    
    public <T> JsonElement toJson(final T t, final Marshaller alternateMarshaller) {
        return alternateMarshaller.serialize(t);
    }
    
    private void processCodePoint(final int codePoint) throws SyntaxError {
        ParserFrame<?> frame = this.contextStack.peek();
        if (frame == null) {
            throw new IllegalStateException("Parser problem! The ParserContext stack underflowed! (line " + this.line + ", col " + this.column + ")");
        }
        try {
            if (frame.context().isComplete()) {
                this.contextStack.pop();
                frame.supply();
                frame = this.contextStack.peek();
            }
        }
        catch (SyntaxError error) {
            error.setStartParsing(((ParserFrame<Object>)frame).startLine, ((ParserFrame<Object>)frame).startCol);
            error.setEndParsing(this.line, this.column);
            throw error;
        }
        try {
            final boolean consumed = ((ParserFrame<Object>)frame).context.consume(codePoint, this);
            if (((ParserFrame<Object>)frame).context.isComplete()) {
                this.contextStack.pop();
                frame.supply();
            }
            if (consumed) {
                this.withheldCodePoint = -1;
                this.retries = 0;
            }
            else {
                this.withheldCodePoint = codePoint;
            }
        }
        catch (SyntaxError error) {
            error.setStartParsing(((ParserFrame<Object>)frame).startLine, ((ParserFrame<Object>)frame).startCol);
            error.setEndParsing(this.line, this.column);
            throw error;
        }
        ++this.column;
        if (codePoint == 10) {
            ++this.line;
            this.column = 0;
        }
    }
    
    public <T> void push(final ParserContext<T> t, final Consumer<T> consumer) {
        final ParserFrame<T> frame = new ParserFrame<T>(t, consumer);
        ((ParserFrame<Object>)frame).startLine = this.line;
        ((ParserFrame<Object>)frame).startCol = this.column;
        this.contextStack.push(frame);
    }
    
    public Marshaller getMarshaller() {
        return this.marshaller;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public void throwDelayed(final SyntaxError syntaxError) {
        syntaxError.setEndParsing(this.line, this.column);
        this.delayedError = syntaxError;
    }
    
    public static class Builder
    {
        MarshallerImpl marshaller;
        boolean allowBareRootObject;
        
        public Builder() {
            this.marshaller = new MarshallerImpl();
            this.allowBareRootObject = false;
        }
        
        @Deprecated
        public <T> Builder registerTypeAdapter(final Class<T> clazz, final Function<JsonObject, T> adapter) {
            this.marshaller.registerTypeAdapter(clazz, adapter);
            return this;
        }
        
        @Deprecated
        public <T> Builder registerPrimitiveTypeAdapter(final Class<T> clazz, final Function<Object, T> adapter) {
            this.marshaller.register(clazz, adapter);
            return this;
        }
        
        public <T> Builder registerSerializer(final Class<T> clazz, final BiFunction<T, Marshaller, JsonElement> serializer) {
            this.marshaller.registerSerializer(clazz, serializer);
            return this;
        }
        
        public <A, B> Builder registerDeserializer(final Class<A> sourceClass, final Class<B> targetClass, final DeserializerFunction<A, B> function) {
            this.marshaller.registerDeserializer(sourceClass, targetClass, function);
            return this;
        }
        
        public <T> Builder registerTypeFactory(final Class<T> clazz, final Supplier<T> factory) {
            this.marshaller.registerTypeFactory(clazz, factory);
            return this;
        }
        
        public Builder allowBareRootObject() {
            this.allowBareRootObject = true;
            return this;
        }
        
        public Jankson build() {
            final Jankson result = new Jankson(this, null);
            result.marshaller = this.marshaller;
            result.allowBareRootObject = this.allowBareRootObject;
            return result;
        }
    }
    
    private static class ParserFrame<T>
    {
        private ParserContext<T> context;
        private Consumer<T> consumer;
        private int startLine;
        private int startCol;
        
        public ParserFrame(final ParserContext<T> context, final Consumer<T> consumer) {
            this.startLine = 0;
            this.startCol = 0;
            this.context = context;
            this.consumer = consumer;
        }
        
        public ParserContext<T> context() {
            return this.context;
        }
        
        public void supply() throws SyntaxError {
            this.consumer.accept(this.context.getResult());
        }
    }
}
