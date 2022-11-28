// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.api;

public class JsonGrammar
{
    public static final JsonGrammar JANKSON;
    public static final JsonGrammar JSON5;
    public static final JsonGrammar STRICT;
    public static final JsonGrammar COMPACT;
    protected boolean comments;
    protected boolean printWhitespace;
    protected boolean printCommas;
    protected boolean printTrailingCommas;
    protected boolean bareSpecialNumerics;
    protected boolean bareRootObject;
    protected boolean printUnquotedKeys;
    protected int newLinesAmount;
    
    public JsonGrammar() {
        this.comments = true;
        this.printWhitespace = true;
        this.printCommas = true;
        this.printTrailingCommas = false;
        this.bareSpecialNumerics = false;
        this.bareRootObject = false;
        this.printUnquotedKeys = false;
        this.newLinesAmount = 0;
    }
    
    public boolean hasComments() {
        return this.comments;
    }
    
    public boolean shouldOutputWhitespace() {
        return this.printWhitespace;
    }
    
    public boolean isBareRootObject() {
        return this.bareRootObject;
    }
    
    public boolean shouldPrintCommas() {
        return this.printCommas;
    }
    
    public boolean isTrailingCommas() {
        return this.printTrailingCommas;
    }
    
    public boolean isBareSpecialNumerics() {
        return this.bareSpecialNumerics;
    }
    
    public boolean shouldUnquoteKeys() {
        return this.printUnquotedKeys;
    }
    
    public int getNewLinesAmount() {
        return this.newLinesAmount;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    static {
        JANKSON = builder().bareSpecialNumerics(true).build();
        JSON5 = builder().withComments(true).printTrailingCommas(true).bareSpecialNumerics(true).build();
        STRICT = builder().withComments(false).build();
        COMPACT = builder().withComments(false).printWhitespace(false).bareSpecialNumerics(true).build();
    }
    
    public static class Builder
    {
        private JsonGrammar grammar;
        
        public Builder() {
            this.grammar = new JsonGrammar();
        }
        
        public Builder withComments(final boolean comments) {
            this.grammar.comments = comments;
            return this;
        }
        
        public Builder printWhitespace(final boolean whitespace) {
            this.grammar.printWhitespace = whitespace;
            return this;
        }
        
        public Builder printWhitespace(final boolean whitespace, final int amount) {
            this.grammar.printWhitespace = whitespace;
            this.grammar.newLinesAmount = amount;
            return this;
        }
        
        public Builder printCommas(final boolean commas) {
            this.grammar.printCommas = commas;
            return this;
        }
        
        public Builder printTrailingCommas(final boolean trailing) {
            this.grammar.printTrailingCommas = trailing;
            return this;
        }
        
        public Builder bareSpecialNumerics(final boolean bare) {
            this.grammar.bareSpecialNumerics = bare;
            return this;
        }
        
        public Builder bareRootObject(final boolean bare) {
            this.grammar.bareRootObject = bare;
            return this;
        }
        
        public Builder printUnquotedKeys(final boolean unquoted) {
            this.grammar.printUnquotedKeys = unquoted;
            return this;
        }
        
        public JsonGrammar build() {
            return this.grammar;
        }
    }
}
