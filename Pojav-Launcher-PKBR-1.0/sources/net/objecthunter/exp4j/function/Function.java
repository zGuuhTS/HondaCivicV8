package net.objecthunter.exp4j.function;

public abstract class Function {
    private final String name;
    protected final int numArguments;

    public abstract double apply(double... dArr);

    public Function(String name2, int numArguments2) {
        if (numArguments2 < 0) {
            throw new IllegalArgumentException("The number of function arguments can not be less than 0 for '" + name2 + "'");
        } else if (isValidFunctionName(name2)) {
            this.name = name2;
            this.numArguments = numArguments2;
        } else {
            throw new IllegalArgumentException("The function name '" + name2 + "' is invalid");
        }
    }

    public Function(String name2) {
        this(name2, 1);
    }

    public String getName() {
        return this.name;
    }

    public int getNumArguments() {
        return this.numArguments;
    }

    public static char[] getAllowedFunctionCharacters() {
        char[] chars = new char[53];
        int count = 0;
        int i = 65;
        while (i < 91) {
            chars[count] = (char) i;
            i++;
            count++;
        }
        int i2 = 97;
        while (i2 < 123) {
            chars[count] = (char) i2;
            i2++;
            count++;
        }
        chars[count] = '_';
        return chars;
    }

    public static boolean isValidFunctionName(String name2) {
        int size;
        if (name2 == null || (size = name2.length()) == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            char c = name2.charAt(i);
            if (!Character.isLetter(c) && c != '_' && (!Character.isDigit(c) || i <= 0)) {
                return false;
            }
        }
        return true;
    }
}
