package net.objecthunter.exp4j.tokenizer;

import java.util.Map;
import java.util.Set;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.operator.Operator;
import net.objecthunter.exp4j.operator.Operators;

public class Tokenizer {
    private final char[] expression;
    private final int expressionLength;
    private final boolean implicitMultiplication;
    private Token lastToken;
    private int pos = 0;
    private final Map<String, Function> userFunctions;
    private final Map<String, Operator> userOperators;
    private final Set<String> variableNames;

    public Tokenizer(String expression2, Map<String, Function> userFunctions2, Map<String, Operator> userOperators2, Set<String> variableNames2, boolean implicitMultiplication2) {
        char[] charArray = expression2.trim().toCharArray();
        this.expression = charArray;
        this.expressionLength = charArray.length;
        this.userFunctions = userFunctions2;
        this.userOperators = userOperators2;
        this.variableNames = variableNames2;
        this.implicitMultiplication = implicitMultiplication2;
    }

    public Tokenizer(String expression2, Map<String, Function> userFunctions2, Map<String, Operator> userOperators2, Set<String> variableNames2) {
        char[] charArray = expression2.trim().toCharArray();
        this.expression = charArray;
        this.expressionLength = charArray.length;
        this.userFunctions = userFunctions2;
        this.userOperators = userOperators2;
        this.variableNames = variableNames2;
        this.implicitMultiplication = true;
    }

    public boolean hasNext() {
        return this.expression.length > this.pos;
    }

    public Token nextToken() {
        char ch = this.expression[this.pos];
        while (Character.isWhitespace(ch)) {
            char[] cArr = this.expression;
            int i = this.pos + 1;
            this.pos = i;
            ch = cArr[i];
        }
        if (Character.isDigit(ch) || ch == '.') {
            Token token = this.lastToken;
            if (token != null) {
                if (token.getType() == 1) {
                    throw new IllegalArgumentException("Unable to parse char '" + ch + "' (Code:" + ch + ") at [" + this.pos + "]");
                } else if (!(!this.implicitMultiplication || this.lastToken.getType() == 2 || this.lastToken.getType() == 4 || this.lastToken.getType() == 3 || this.lastToken.getType() == 7)) {
                    OperatorToken operatorToken = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                    this.lastToken = operatorToken;
                    return operatorToken;
                }
            }
            return parseNumberToken(ch);
        } else if (isArgumentSeparator(ch)) {
            return parseArgumentSeparatorToken();
        } else {
            if (isOpenParentheses(ch)) {
                Token token2 = this.lastToken;
                if (token2 == null || !this.implicitMultiplication || token2.getType() == 2 || this.lastToken.getType() == 4 || this.lastToken.getType() == 3 || this.lastToken.getType() == 7) {
                    return parseParentheses(true);
                }
                OperatorToken operatorToken2 = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                this.lastToken = operatorToken2;
                return operatorToken2;
            } else if (isCloseParentheses(ch)) {
                return parseParentheses(false);
            } else {
                if (Operator.isAllowedOperatorChar(ch)) {
                    return parseOperatorToken(ch);
                }
                if (isAlphabetic(ch) || ch == '_') {
                    Token token3 = this.lastToken;
                    if (token3 == null || !this.implicitMultiplication || token3.getType() == 2 || this.lastToken.getType() == 4 || this.lastToken.getType() == 3 || this.lastToken.getType() == 7) {
                        return parseFunctionOrVariable();
                    }
                    OperatorToken operatorToken3 = new OperatorToken(Operators.getBuiltinOperator('*', 2));
                    this.lastToken = operatorToken3;
                    return operatorToken3;
                }
                throw new IllegalArgumentException("Unable to parse char '" + ch + "' (Code:" + ch + ") at [" + this.pos + "]");
            }
        }
    }

    private Token parseArgumentSeparatorToken() {
        this.pos++;
        ArgumentSeparatorToken argumentSeparatorToken = new ArgumentSeparatorToken();
        this.lastToken = argumentSeparatorToken;
        return argumentSeparatorToken;
    }

    private boolean isArgumentSeparator(char ch) {
        return ch == ',';
    }

    private Token parseParentheses(boolean open) {
        if (open) {
            this.lastToken = new OpenParenthesesToken();
        } else {
            this.lastToken = new CloseParenthesesToken();
        }
        this.pos++;
        return this.lastToken;
    }

    private boolean isOpenParentheses(char ch) {
        return ch == '(' || ch == '{' || ch == '[';
    }

    private boolean isCloseParentheses(char ch) {
        return ch == ')' || ch == '}' || ch == ']';
    }

    private Token parseFunctionOrVariable() {
        int offset = this.pos;
        int lastValidLen = 1;
        Token lastValidToken = null;
        int len = 1;
        if (isEndOfExpression(offset)) {
            this.pos++;
        }
        int testPos = (offset + 1) - 1;
        while (!isEndOfExpression(testPos) && isVariableOrFunctionCharacter(this.expression[testPos])) {
            String name = new String(this.expression, offset, len);
            Set<String> set = this.variableNames;
            if (set == null || !set.contains(name)) {
                Function f = getFunction(name);
                if (f != null) {
                    lastValidLen = len;
                    lastValidToken = new FunctionToken(f);
                }
            } else {
                lastValidLen = len;
                lastValidToken = new VariableToken(name);
            }
            len++;
            testPos = (offset + len) - 1;
        }
        if (lastValidToken != null) {
            this.pos += lastValidLen;
            this.lastToken = lastValidToken;
            return lastValidToken;
        }
        throw new UnknownFunctionOrVariableException(new String(this.expression), this.pos, len);
    }

    private Function getFunction(String name) {
        Function f = null;
        Map<String, Function> map = this.userFunctions;
        if (map != null) {
            f = map.get(name);
        }
        if (f == null) {
            return Functions.getBuiltinFunction(name);
        }
        return f;
    }

    private Token parseOperatorToken(char firstChar) {
        int offset = this.pos;
        int len = 1;
        StringBuilder symbol = new StringBuilder();
        Operator lastValid = null;
        symbol.append(firstChar);
        while (!isEndOfExpression(offset + len) && Operator.isAllowedOperatorChar(this.expression[offset + len])) {
            symbol.append(this.expression[len + offset]);
            len++;
        }
        while (true) {
            if (symbol.length() > 0) {
                Operator op = getOperator(symbol.toString());
                if (op != null) {
                    lastValid = op;
                    break;
                }
                symbol.setLength(symbol.length() - 1);
            } else {
                break;
            }
        }
        this.pos += symbol.length();
        OperatorToken operatorToken = new OperatorToken(lastValid);
        this.lastToken = operatorToken;
        return operatorToken;
    }

    private Operator getOperator(String symbol) {
        Operator op = null;
        Map<String, Operator> map = this.userOperators;
        if (map != null) {
            op = map.get(symbol);
        }
        if (op != null || symbol.length() != 1) {
            return op;
        }
        int argc = 2;
        Token token = this.lastToken;
        if (token == null) {
            argc = 1;
        } else {
            int lastTokenType = token.getType();
            if (lastTokenType == 4 || lastTokenType == 7) {
                argc = 1;
            } else if (lastTokenType == 2) {
                Operator lastOp = ((OperatorToken) this.lastToken).getOperator();
                if (lastOp.getNumOperands() == 2 || (lastOp.getNumOperands() == 1 && !lastOp.isLeftAssociative())) {
                    argc = 1;
                }
            }
        }
        return Operators.getBuiltinOperator(symbol.charAt(0), argc);
    }

    private Token parseNumberToken(char firstChar) {
        int offset = this.pos;
        int len = 1;
        this.pos++;
        if (isEndOfExpression(offset + 1)) {
            NumberToken numberToken = new NumberToken(Double.parseDouble(String.valueOf(firstChar)));
            this.lastToken = numberToken;
            return numberToken;
        }
        while (!isEndOfExpression(offset + len)) {
            char[] cArr = this.expression;
            if (!isNumeric(cArr[offset + len], cArr[(offset + len) - 1] == 'e' || cArr[(offset + len) - 1] == 'E')) {
                break;
            }
            len++;
            this.pos++;
        }
        char[] cArr2 = this.expression;
        if (cArr2[(offset + len) - 1] == 'e' || cArr2[(offset + len) - 1] == 'E') {
            len--;
            this.pos--;
        }
        NumberToken numberToken2 = new NumberToken(this.expression, offset, len);
        this.lastToken = numberToken2;
        return numberToken2;
    }

    private static boolean isNumeric(char ch, boolean lastCharE) {
        return Character.isDigit(ch) || ch == '.' || ch == 'e' || ch == 'E' || (lastCharE && (ch == '-' || ch == '+'));
    }

    public static boolean isAlphabetic(int codePoint) {
        return Character.isLetter(codePoint);
    }

    public static boolean isVariableOrFunctionCharacter(int codePoint) {
        return isAlphabetic(codePoint) || Character.isDigit(codePoint) || codePoint == 95 || codePoint == 46;
    }

    private boolean isEndOfExpression(int offset) {
        return this.expressionLength <= offset;
    }
}
