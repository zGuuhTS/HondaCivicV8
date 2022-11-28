// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl;

import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonElement;

public class AnnotatedElement
{
    protected String comment;
    protected JsonElement elem;
    
    public AnnotatedElement(@Nonnull final JsonElement elem, @Nullable final String comment) {
        this.comment = comment;
        this.elem = elem;
    }
    
    @Nullable
    public String getComment() {
        return this.comment;
    }
    
    @Nonnull
    public JsonElement getElement() {
        return this.elem;
    }
}
