// 
// Decompiled by Procyon v0.5.36
// 

package net.objecthunter.exp4j;

import java.util.List;

public class ValidationResult
{
    private final boolean valid;
    private final List<String> errors;
    public static final ValidationResult SUCCESS;
    
    public ValidationResult(final boolean valid, final List<String> errors) {
        this.valid = valid;
        this.errors = errors;
    }
    
    public boolean isValid() {
        return this.valid;
    }
    
    public List<String> getErrors() {
        return this.errors;
    }
    
    static {
        SUCCESS = new ValidationResult(true, null);
    }
}
