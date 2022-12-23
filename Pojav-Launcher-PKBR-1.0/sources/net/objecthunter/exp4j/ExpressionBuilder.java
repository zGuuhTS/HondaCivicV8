package net.objecthunter.exp4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class ExpressionBuilder {
    private String expression;
    private boolean implicitMultiplication = true;
    private final Map<String, Function> userFunctions;
    private final Map<String, Operator> userOperators;
    private final Set<String> variableNames;

    public ExpressionBuilder(String expression2) {
        if (expression2 == null || expression2.trim().length() == 0) {
            throw new IllegalArgumentException("Expression can not be empty");
        }
        this.expression = expression2;
        this.userOperators = new HashMap(4);
        this.userFunctions = new HashMap(4);
        this.variableNames = new HashSet(4);
    }

    public ExpressionBuilder expression(String expression2) {
        this.expression = expression2;
        return this;
    }

    public ExpressionBuilder function(Function function) {
        this.userFunctions.put(function.getName(), function);
        return this;
    }

    public ExpressionBuilder functions(Function... functions) {
        for (Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    public ExpressionBuilder functions(List<Function> functions) {
        for (Function f : functions) {
            this.userFunctions.put(f.getName(), f);
        }
        return this;
    }

    public ExpressionBuilder variables(Set<String> variableNames2) {
        this.variableNames.addAll(variableNames2);
        return this;
    }

    public ExpressionBuilder variables(String... variableNames2) {
        Collections.addAll(this.variableNames, variableNames2);
        return this;
    }

    public ExpressionBuilder variable(String variableName) {
        this.variableNames.add(variableName);
        return this;
    }

    public ExpressionBuilder implicitMultiplication(boolean enabled) {
        this.implicitMultiplication = enabled;
        return this;
    }

    public ExpressionBuilder operator(Operator operator) {
        checkOperatorSymbol(operator);
        this.userOperators.put(operator.getSymbol(), operator);
        return this;
    }

    private void checkOperatorSymbol(Operator op) {
        String name = op.getSymbol();
        char[] charArray = name.toCharArray();
        int length = charArray.length;
        int i = 0;
        while (i < length) {
            if (Operator.isAllowedOperatorChar(charArray[i])) {
                i++;
            } else {
                throw new IllegalArgumentException("The operator symbol '" + name + "' is invalid");
            }
        }
    }

    public ExpressionBuilder operator(Operator... operators) {
        for (Operator o : operators) {
            operator(o);
        }
        return this;
    }

    public ExpressionBuilder operator(List<Operator> operators) {
        for (Operator o : operators) {
            operator(o);
        }
        return this;
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public net.objecthunter.exp4j.Expression build() {
        /*
            r6 = this;
            java.lang.String r0 = r6.expression
            int r0 = r0.length()
            if (r0 == 0) goto L_0x007e
            java.util.Set<java.lang.String> r0 = r6.variableNames
            java.lang.String r1 = "pi"
            r0.add(r1)
            java.util.Set<java.lang.String> r0 = r6.variableNames
            java.lang.String r1 = "π"
            r0.add(r1)
            java.util.Set<java.lang.String> r0 = r6.variableNames
            java.lang.String r1 = "e"
            r0.add(r1)
            java.util.Set<java.lang.String> r0 = r6.variableNames
            java.lang.String r1 = "φ"
            r0.add(r1)
            java.util.Set<java.lang.String> r0 = r6.variableNames
            java.util.Iterator r0 = r0.iterator()
        L_0x002a:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0064
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            net.objecthunter.exp4j.function.Function r2 = net.objecthunter.exp4j.function.Functions.getBuiltinFunction(r1)
            if (r2 != 0) goto L_0x0045
            java.util.Map<java.lang.String, net.objecthunter.exp4j.function.Function> r2 = r6.userFunctions
            boolean r2 = r2.containsKey(r1)
            if (r2 != 0) goto L_0x0045
            goto L_0x002a
        L_0x0045:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "A variable can not have the same name as a function ["
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r3 = "]"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x0064:
            net.objecthunter.exp4j.Expression r0 = new net.objecthunter.exp4j.Expression
            java.lang.String r1 = r6.expression
            java.util.Map<java.lang.String, net.objecthunter.exp4j.function.Function> r2 = r6.userFunctions
            java.util.Map<java.lang.String, net.objecthunter.exp4j.operator.Operator> r3 = r6.userOperators
            java.util.Set<java.lang.String> r4 = r6.variableNames
            boolean r5 = r6.implicitMultiplication
            net.objecthunter.exp4j.tokenizer.Token[] r1 = net.objecthunter.exp4j.shuntingyard.ShuntingYard.convertToRPN(r1, r2, r3, r4, r5)
            java.util.Map<java.lang.String, net.objecthunter.exp4j.function.Function> r2 = r6.userFunctions
            java.util.Set r2 = r2.keySet()
            r0.<init>(r1, r2)
            return r0
        L_0x007e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "The expression can not be empty"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.objecthunter.exp4j.ExpressionBuilder.build():net.objecthunter.exp4j.Expression");
    }
}
