// 
// Decompiled by Procyon v0.5.36
// 

package net.objecthunter.exp4j.tokenizer;

public class VariableToken extends Token
{
    private final String name;
    
    public String getName() {
        return this.name;
    }
    
    public VariableToken(final String name) {
        super(6);
        this.name = name;
    }
}
