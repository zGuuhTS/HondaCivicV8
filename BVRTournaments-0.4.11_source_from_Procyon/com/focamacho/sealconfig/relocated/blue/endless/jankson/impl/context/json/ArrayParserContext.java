// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.json;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.SyntaxError;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.AnnotatedElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Jankson;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonArray;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.context.ParserContext;

public class ArrayParserContext implements ParserContext<JsonArray>
{
    private JsonArray result;
    private boolean foundClosingBrace;
    
    public ArrayParserContext() {
        this.result = new JsonArray();
        this.foundClosingBrace = false;
    }
    
    @Override
    public boolean consume(final int codePoint, final Jankson loader) throws SyntaxError {
        this.result.setMarshaller(loader.getMarshaller());
        if (this.foundClosingBrace) {
            return false;
        }
        if (Character.isWhitespace(codePoint) || codePoint == 44) {
            return true;
        }
        if (codePoint == 93) {
            return this.foundClosingBrace = true;
        }
        String existing;
        String combined;
        loader.push(new ElementParserContext(), it -> {
            if (it.getElement() != null) {
                this.result.add(it.getElement(), it.getComment());
            }
            else {
                existing = this.result.getComment(this.result.size() - 1);
                if (existing == null) {
                    existing = "";
                }
                combined = existing + "\n" + it.getComment();
                this.result.setComment(this.result.size() - 1, combined);
            }
            return;
        });
        return false;
    }
    
    @Override
    public void eof() throws SyntaxError {
        if (this.foundClosingBrace) {
            return;
        }
        throw new SyntaxError("Unexpected end-of-file in the middle of a list! Are you missing a ']'?");
    }
    
    @Override
    public boolean isComplete() {
        return this.foundClosingBrace;
    }
    
    @Override
    public JsonArray getResult() throws SyntaxError {
        return this.result;
    }
}
