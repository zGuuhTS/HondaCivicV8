package net.objecthunter.exp4j;

import java.util.List;

public class ValidationResult {
    public static final ValidationResult SUCCESS = new ValidationResult(true, (List<String>) null);
    private final List<String> errors;
    private final boolean valid;

    public ValidationResult(boolean valid2, List<String> errors2) {
        this.valid = valid2;
        this.errors = errors2;
    }

    public boolean isValid() {
        return this.valid;
    }

    public List<String> getErrors() {
        return this.errors;
    }
}
