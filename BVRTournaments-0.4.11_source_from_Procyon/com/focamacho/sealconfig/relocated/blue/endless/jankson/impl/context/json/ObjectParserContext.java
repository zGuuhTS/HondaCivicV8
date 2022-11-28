// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.json;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.AnnotatedElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonPrimitive;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.SyntaxError;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Jankson;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonObject;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.ParserContext;

public class ObjectParserContext implements ParserContext<JsonObject>
{
    private JsonObject result;
    private final boolean assumeOpen;
    private String comment;
    private boolean openBraceFound;
    private boolean noOpenBrace;
    private String key;
    private boolean colonFound;
    private boolean closeBraceFound;
    private boolean eof;
    
    public ObjectParserContext(final boolean assumeOpen) {
        this.result = new JsonObject();
        this.openBraceFound = false;
        this.noOpenBrace = false;
        this.colonFound = false;
        this.closeBraceFound = false;
        this.eof = false;
        this.assumeOpen = assumeOpen;
    }
    
    @Override
    public boolean consume(final int codePoint, final Jankson loader) throws SyntaxError {
        this.result.setMarshaller(loader.getMarshaller());
        if (!this.openBraceFound) {
            if (Character.isWhitespace(codePoint)) {
                return true;
            }
            if (codePoint == 47 || codePoint == 35) {
                loader.push(new CommentParserContext(codePoint), it -> {
                    if (this.comment == null) {
                        this.comment = it;
                    }
                    else {
                        this.comment = this.comment + "\n" + it;
                    }
                    return;
                });
                return true;
            }
            if (codePoint == 123) {
                return this.openBraceFound = true;
            }
            if (!this.assumeOpen) {
                throw new SyntaxError("Found character '" + (char)codePoint + "' instead of '{' while looking for the start of an object");
            }
            this.openBraceFound = true;
            this.noOpenBrace = true;
        }
        if (this.closeBraceFound) {
            return false;
        }
        if (this.key == null) {
            if (Character.isWhitespace(codePoint) || codePoint == 44) {
                return true;
            }
            switch (codePoint) {
                case 125: {
                    if (this.noOpenBrace) {
                        throw new SyntaxError("Found spurious '}' while parsing an object with no open brace.");
                    }
                    return this.closeBraceFound = true;
                }
                case 44: {
                    return true;
                }
                case 34:
                case 39: {
                    loader.push(new StringParserContext(codePoint), it -> this.key = it.asString());
                    return true;
                }
                case 35:
                case 47: {
                    loader.push(new CommentParserContext(codePoint), it -> {
                        if (this.comment == null) {
                            this.comment = it;
                        }
                        else {
                            this.comment = this.comment + "\n" + it;
                        }
                        return;
                    });
                    return true;
                }
                case 123: {
                    loader.throwDelayed(new SyntaxError("Found spurious '{' while parsing an object."));
                    return true;
                }
                default: {
                    loader.push(new TokenParserContext(codePoint), it -> this.key = it.asString());
                    return true;
                }
            }
        }
        else {
            if (this.colonFound) {
                final String elemKey = this.key;
                String resolvedComment;
                final String key;
                loader.push((ParserContext<Object>)new ElementParserContext(), it -> {
                    resolvedComment = "";
                    if (this.comment != null) {
                        resolvedComment += this.comment;
                    }
                    if (this.comment != null && it.getComment() != null) {
                        resolvedComment += '\n';
                    }
                    if (it.getComment() != null) {
                        resolvedComment += it.getComment();
                    }
                    this.result.put(key, it.getElement(), resolvedComment);
                    this.key = null;
                    this.colonFound = false;
                    this.comment = null;
                    return;
                });
                return false;
            }
            if (Character.isWhitespace(codePoint)) {
                return true;
            }
            if (codePoint == 58) {
                return this.colonFound = true;
            }
            throw new SyntaxError("Found unexpected character '" + (char)codePoint + "' while looking for the colon (':') between a key and a value in an object");
        }
    }
    
    @Override
    public boolean isComplete() {
        return this.closeBraceFound || (this.assumeOpen && this.noOpenBrace && this.eof);
    }
    
    @Override
    public JsonObject getResult() {
        return this.result;
    }
    
    @Override
    public void eof() throws SyntaxError {
        this.eof = true;
        if (this.assumeOpen && this.noOpenBrace) {
            return;
        }
        if (this.closeBraceFound) {
            return;
        }
        throw new SyntaxError("Expected to find '}' to end an object, found EOF instead.");
    }
}
