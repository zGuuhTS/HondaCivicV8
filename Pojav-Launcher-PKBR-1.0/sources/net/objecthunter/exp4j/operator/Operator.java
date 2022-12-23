package net.objecthunter.exp4j.operator;

import kotlin.text.Typography;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.p012io.IOUtils;

public abstract class Operator {
    public static final char[] ALLOWED_OPERATOR_CHARS = {'+', Soundex.SILENT_MARKER, '*', IOUtils.DIR_SEPARATOR_UNIX, '%', '^', '!', '#', Typography.section, Typography.dollar, Typography.amp, ';', ':', '~', Typography.less, Typography.greater, '|', '=', 247, 8730, 8731, 8968, 8970};
    public static final int PRECEDENCE_ADDITION = 500;
    public static final int PRECEDENCE_DIVISION = 1000;
    public static final int PRECEDENCE_MODULO = 1000;
    public static final int PRECEDENCE_MULTIPLICATION = 1000;
    public static final int PRECEDENCE_POWER = 10000;
    public static final int PRECEDENCE_SUBTRACTION = 500;
    public static final int PRECEDENCE_UNARY_MINUS = 5000;
    public static final int PRECEDENCE_UNARY_PLUS = 5000;
    private final boolean leftAssociative;
    private final int numOperands;
    private final int precedence;
    private final String symbol;

    public abstract double apply(double... dArr);

    public Operator(String symbol2, int numberOfOperands, boolean leftAssociative2, int precedence2) {
        this.numOperands = numberOfOperands;
        this.leftAssociative = leftAssociative2;
        this.symbol = symbol2;
        this.precedence = precedence2;
    }

    public static boolean isAllowedOperatorChar(char ch) {
        for (char allowed : ALLOWED_OPERATOR_CHARS) {
            if (ch == allowed) {
                return true;
            }
        }
        return false;
    }

    public boolean isLeftAssociative() {
        return this.leftAssociative;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getNumOperands() {
        return this.numOperands;
    }
}
