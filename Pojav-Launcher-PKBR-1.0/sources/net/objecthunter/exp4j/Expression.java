package net.objecthunter.exp4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.function.Functions;
import net.objecthunter.exp4j.tokenizer.FunctionToken;
import net.objecthunter.exp4j.tokenizer.NumberToken;
import net.objecthunter.exp4j.tokenizer.OperatorToken;
import net.objecthunter.exp4j.tokenizer.Token;
import net.objecthunter.exp4j.tokenizer.VariableToken;

public class Expression {
    private final Token[] tokens;
    private final Set<String> userFunctionNames;
    private final Map<String, Double> variables;

    private static Map<String, Double> createDefaultVariables() {
        Map<String, Double> vars = new HashMap<>(4);
        Double valueOf = Double.valueOf(3.141592653589793d);
        vars.put("pi", valueOf);
        vars.put("π", valueOf);
        vars.put("φ", Double.valueOf(1.61803398874d));
        vars.put("e", Double.valueOf(2.718281828459045d));
        return vars;
    }

    public Expression(Expression existing) {
        Token[] tokenArr = existing.tokens;
        this.tokens = (Token[]) Arrays.copyOf(tokenArr, tokenArr.length);
        HashMap hashMap = new HashMap();
        this.variables = hashMap;
        hashMap.putAll(existing.variables);
        this.userFunctionNames = new HashSet(existing.userFunctionNames);
    }

    Expression(Token[] tokens2) {
        this.tokens = tokens2;
        this.variables = createDefaultVariables();
        this.userFunctionNames = Collections.emptySet();
    }

    Expression(Token[] tokens2, Set<String> userFunctionNames2) {
        this.tokens = tokens2;
        this.variables = createDefaultVariables();
        this.userFunctionNames = userFunctionNames2;
    }

    public Expression setVariable(String name, double value) {
        checkVariableName(name);
        this.variables.put(name, Double.valueOf(value));
        return this;
    }

    private void checkVariableName(String name) {
        if (this.userFunctionNames.contains(name) || Functions.getBuiltinFunction(name) != null) {
            throw new IllegalArgumentException("The variable name '" + name + "' is invalid. Since there exists a function with the same name");
        }
    }

    public Expression setVariables(Map<String, Double> variables2) {
        for (Map.Entry<String, Double> v : variables2.entrySet()) {
            setVariable(v.getKey(), v.getValue().doubleValue());
        }
        return this;
    }

    public Expression clearVariables() {
        this.variables.clear();
        return this;
    }

    public Set<String> getVariableNames() {
        Set<String> variables2 = new HashSet<>();
        for (Token t : this.tokens) {
            if (t.getType() == 6) {
                variables2.add(((VariableToken) t).getName());
            }
        }
        return variables2;
    }

    public ValidationResult validate(boolean checkVariablesSet) {
        List<String> errors = new ArrayList<>(0);
        if (checkVariablesSet) {
            for (Token t : this.tokens) {
                if (t.getType() == 6) {
                    String var = ((VariableToken) t).getName();
                    if (!this.variables.containsKey(var)) {
                        errors.add("The setVariable '" + var + "' has not been set");
                    }
                }
            }
        }
        int count = 0;
        for (Token tok : this.tokens) {
            switch (tok.getType()) {
                case 1:
                case 6:
                    count++;
                    break;
                case 2:
                    if (((OperatorToken) tok).getOperator().getNumOperands() == 2) {
                        count--;
                        break;
                    }
                    break;
                case 3:
                    Function func = ((FunctionToken) tok).getFunction();
                    int argsNum = func.getNumArguments();
                    if (argsNum > count) {
                        errors.add("Not enough arguments for '" + func.getName() + "'");
                    }
                    if (argsNum <= 1) {
                        if (argsNum == 0) {
                            count++;
                            break;
                        }
                    } else {
                        count -= argsNum - 1;
                        break;
                    }
                    break;
            }
            if (count < 1) {
                errors.add("Too many operators");
                return new ValidationResult(false, errors);
            }
        }
        if (count > 1) {
            errors.add("Too many operands");
        }
        return errors.size() == 0 ? ValidationResult.SUCCESS : new ValidationResult(false, errors);
    }

    public ValidationResult validate() {
        return validate(true);
    }

    public Future<Double> evaluateAsync(ExecutorService executor) {
        return executor.submit(new Callable() {
            public final Object call() {
                return Double.valueOf(Expression.this.evaluate());
            }
        });
    }

    public double evaluate() {
        ArrayStack output = new ArrayStack();
        for (Token t : this.tokens) {
            if (t.getType() == 1) {
                output.push(((NumberToken) t).getValue());
            } else if (t.getType() == 6) {
                String name = ((VariableToken) t).getName();
                Double value = this.variables.get(name);
                if (value != null) {
                    output.push(value.doubleValue());
                } else {
                    throw new IllegalArgumentException("No value has been set for the setVariable '" + name + "'.");
                }
            } else if (t.getType() == 2) {
                OperatorToken op = (OperatorToken) t;
                if (output.size() < op.getOperator().getNumOperands()) {
                    throw new IllegalArgumentException("Invalid number of operands available for '" + op.getOperator().getSymbol() + "' operator");
                } else if (op.getOperator().getNumOperands() == 2) {
                    output.push(op.getOperator().apply(output.pop(), output.pop()));
                } else if (op.getOperator().getNumOperands() == 1) {
                    output.push(op.getOperator().apply(output.pop()));
                }
            } else if (t.getType() == 3) {
                FunctionToken func = (FunctionToken) t;
                int numArguments = func.getFunction().getNumArguments();
                if (output.size() >= numArguments) {
                    double[] args = new double[numArguments];
                    for (int j = numArguments - 1; j >= 0; j--) {
                        args[j] = output.pop();
                    }
                    output.push(func.getFunction().apply(args));
                } else {
                    throw new IllegalArgumentException("Invalid number of arguments available for '" + func.getFunction().getName() + "' function");
                }
            } else {
                continue;
            }
        }
        if (output.size() <= 1) {
            return output.pop();
        }
        throw new IllegalArgumentException("Invalid number of items on the output queue. Might be caused by an invalid number of arguments for a function.");
    }
}
