package net.objecthunter.exp4j.tokenizer;

import net.objecthunter.exp4j.function.Function;

public class FunctionToken extends Token {
    private final Function function;

    public FunctionToken(Function function2) {
        super(3);
        this.function = function2;
    }

    public Function getFunction() {
        return this.function;
    }
}
