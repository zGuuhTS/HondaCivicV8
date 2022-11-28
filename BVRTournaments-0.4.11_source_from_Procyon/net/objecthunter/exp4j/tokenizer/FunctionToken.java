// 
// Decompiled by Procyon v0.5.36
// 

package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.function.Function;

public class FunctionToken extends Token
{
    private final Function function;
    
    public FunctionToken(final Function function) {
        super(3);
        this.function = function;
    }
    
    public Function getFunction() {
        return this.function;
    }
}
