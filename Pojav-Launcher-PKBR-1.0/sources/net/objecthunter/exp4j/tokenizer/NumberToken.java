package net.objecthunter.exp4j.tokenizer;

public final class NumberToken extends Token {
    private final double value;

    public NumberToken(double value2) {
        super(1);
        this.value = value2;
    }

    NumberToken(char[] expression, int offset, int len) {
        this(Double.parseDouble(String.valueOf(expression, offset, len)));
    }

    public double getValue() {
        return this.value;
    }
}
