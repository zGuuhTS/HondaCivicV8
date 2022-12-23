package net.objecthunter.exp4j.tokenizer;

class UnknownFunctionOrVariableException extends IllegalArgumentException {
    private static final long serialVersionUID = 1;
    private final String expression;
    private final String message;
    private final int position;
    private final String token;

    public UnknownFunctionOrVariableException(String expression2, int position2, int length) {
        this.expression = expression2;
        String str = token(expression2, position2, length);
        this.token = str;
        this.position = position2;
        this.message = "Unknown function or variable '" + str + "' at pos " + position2 + " in expression '" + expression2 + "'";
    }

    private static String token(String expression2, int position2, int length) {
        int len = expression2.length();
        int end = (position2 + length) - 1;
        if (len < end) {
            end = len;
        }
        return expression2.substring(position2, end);
    }

    public String getMessage() {
        return this.message;
    }

    public String getExpression() {
        return this.expression;
    }

    public String getToken() {
        return this.token;
    }

    public int getPosition() {
        return this.position;
    }
}
