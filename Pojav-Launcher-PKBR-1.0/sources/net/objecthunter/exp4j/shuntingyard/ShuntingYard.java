package net.objecthunter.exp4j.shuntingyard;

public class ShuntingYard {
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004d, code lost:
        if (r0.empty() != false) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
        if (r0.peek().getType() != 4) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0064, code lost:
        throw new java.lang.IllegalArgumentException("Misplaced function separator ',' or mismatched parentheses");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0079, code lost:
        r0.pop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0080, code lost:
        if (r0.isEmpty() != false) goto L_0x0015;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008d, code lost:
        if (r0.peek().getType() != 3) goto L_0x0015;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008f, code lost:
        r1.add(r0.pop());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x010a, code lost:
        r0.push(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static net.objecthunter.exp4j.tokenizer.Token[] convertToRPN(java.lang.String r9, java.util.Map<java.lang.String, net.objecthunter.exp4j.function.Function> r10, java.util.Map<java.lang.String, net.objecthunter.exp4j.operator.Operator> r11, java.util.Set<java.lang.String> r12, boolean r13) {
        /*
            java.util.Stack r0 = new java.util.Stack
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            net.objecthunter.exp4j.tokenizer.Tokenizer r8 = new net.objecthunter.exp4j.tokenizer.Tokenizer
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r2.<init>(r3, r4, r5, r6, r7)
        L_0x0015:
            boolean r3 = r2.hasNext()
            r4 = 4
            if (r3 == 0) goto L_0x0114
            net.objecthunter.exp4j.tokenizer.Token r3 = r2.nextToken()
            int r5 = r3.getType()
            switch(r5) {
                case 1: goto L_0x010e;
                case 2: goto L_0x00a2;
                case 3: goto L_0x009d;
                case 4: goto L_0x0098;
                case 5: goto L_0x0065;
                case 6: goto L_0x010e;
                case 7: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Unknown Token type encountered. This should not happen"
            r4.<init>(r5)
            throw r4
        L_0x002f:
            boolean r5 = r0.empty()
            if (r5 != 0) goto L_0x0049
            java.lang.Object r5 = r0.peek()
            net.objecthunter.exp4j.tokenizer.Token r5 = (net.objecthunter.exp4j.tokenizer.Token) r5
            int r5 = r5.getType()
            if (r5 == r4) goto L_0x0049
            java.lang.Object r5 = r0.pop()
            r1.add(r5)
            goto L_0x002f
        L_0x0049:
            boolean r5 = r0.empty()
            if (r5 != 0) goto L_0x005d
            java.lang.Object r5 = r0.peek()
            net.objecthunter.exp4j.tokenizer.Token r5 = (net.objecthunter.exp4j.tokenizer.Token) r5
            int r5 = r5.getType()
            if (r5 != r4) goto L_0x005d
            goto L_0x0112
        L_0x005d:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Misplaced function separator ',' or mismatched parentheses"
            r4.<init>(r5)
            throw r4
        L_0x0065:
            java.lang.Object r5 = r0.peek()
            net.objecthunter.exp4j.tokenizer.Token r5 = (net.objecthunter.exp4j.tokenizer.Token) r5
            int r5 = r5.getType()
            if (r5 == r4) goto L_0x0079
            java.lang.Object r5 = r0.pop()
            r1.add(r5)
            goto L_0x0065
        L_0x0079:
            r0.pop()
            boolean r4 = r0.isEmpty()
            if (r4 != 0) goto L_0x0112
            java.lang.Object r4 = r0.peek()
            net.objecthunter.exp4j.tokenizer.Token r4 = (net.objecthunter.exp4j.tokenizer.Token) r4
            int r4 = r4.getType()
            r5 = 3
            if (r4 != r5) goto L_0x0112
            java.lang.Object r4 = r0.pop()
            r1.add(r4)
            goto L_0x0112
        L_0x0098:
            r0.push(r3)
            goto L_0x0112
        L_0x009d:
            r0.add(r3)
            goto L_0x0112
        L_0x00a2:
            boolean r4 = r0.empty()
            if (r4 != 0) goto L_0x010a
            java.lang.Object r4 = r0.peek()
            net.objecthunter.exp4j.tokenizer.Token r4 = (net.objecthunter.exp4j.tokenizer.Token) r4
            int r4 = r4.getType()
            r5 = 2
            if (r4 != r5) goto L_0x010a
            r4 = r3
            net.objecthunter.exp4j.tokenizer.OperatorToken r4 = (net.objecthunter.exp4j.tokenizer.OperatorToken) r4
            java.lang.Object r6 = r0.peek()
            net.objecthunter.exp4j.tokenizer.OperatorToken r6 = (net.objecthunter.exp4j.tokenizer.OperatorToken) r6
            net.objecthunter.exp4j.operator.Operator r7 = r4.getOperator()
            int r7 = r7.getNumOperands()
            r8 = 1
            if (r7 != r8) goto L_0x00d4
            net.objecthunter.exp4j.operator.Operator r7 = r6.getOperator()
            int r7 = r7.getNumOperands()
            if (r7 != r5) goto L_0x00d4
            goto L_0x010a
        L_0x00d4:
            net.objecthunter.exp4j.operator.Operator r5 = r4.getOperator()
            boolean r5 = r5.isLeftAssociative()
            if (r5 == 0) goto L_0x00f0
            net.objecthunter.exp4j.operator.Operator r5 = r4.getOperator()
            int r5 = r5.getPrecedence()
            net.objecthunter.exp4j.operator.Operator r7 = r6.getOperator()
            int r7 = r7.getPrecedence()
            if (r5 <= r7) goto L_0x0102
        L_0x00f0:
            net.objecthunter.exp4j.operator.Operator r5 = r4.getOperator()
            int r5 = r5.getPrecedence()
            net.objecthunter.exp4j.operator.Operator r7 = r6.getOperator()
            int r7 = r7.getPrecedence()
            if (r5 >= r7) goto L_0x010a
        L_0x0102:
            java.lang.Object r5 = r0.pop()
            r1.add(r5)
            goto L_0x00a2
        L_0x010a:
            r0.push(r3)
            goto L_0x0112
        L_0x010e:
            r1.add(r3)
        L_0x0112:
            goto L_0x0015
        L_0x0114:
            boolean r3 = r0.empty()
            if (r3 != 0) goto L_0x0139
            java.lang.Object r3 = r0.pop()
            net.objecthunter.exp4j.tokenizer.Token r3 = (net.objecthunter.exp4j.tokenizer.Token) r3
            int r5 = r3.getType()
            r6 = 5
            if (r5 == r6) goto L_0x0131
            int r5 = r3.getType()
            if (r5 == r4) goto L_0x0131
            r1.add(r3)
            goto L_0x0114
        L_0x0131:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r5 = "Mismatched parentheses detected. Please check the expression"
            r4.<init>(r5)
            throw r4
        L_0x0139:
            r3 = 0
            net.objecthunter.exp4j.tokenizer.Token[] r3 = new net.objecthunter.exp4j.tokenizer.Token[r3]
            java.lang.Object[] r3 = r1.toArray(r3)
            net.objecthunter.exp4j.tokenizer.Token[] r3 = (net.objecthunter.exp4j.tokenizer.Token[]) r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: net.objecthunter.exp4j.shuntingyard.ShuntingYard.convertToRPN(java.lang.String, java.util.Map, java.util.Map, java.util.Set, boolean):net.objecthunter.exp4j.tokenizer.Token[]");
    }
}
