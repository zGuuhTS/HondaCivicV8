package net.objecthunter.exp4j.function;

public class Functions {
    private static final Function[] BUILT_IN_FUNCTIONS;
    private static final int INDEX_ABS = 17;
    private static final int INDEX_ACOS = 13;
    private static final int INDEX_ASIN = 12;
    private static final int INDEX_ATAN = 14;
    private static final int INDEX_CBRT = 16;
    private static final int INDEX_CEIL = 18;
    private static final int INDEX_COS = 1;
    private static final int INDEX_COSH = 7;
    private static final int INDEX_COT = 5;
    private static final int INDEX_COTH = 11;
    private static final int INDEX_CSC = 3;
    private static final int INDEX_CSCH = 9;
    private static final int INDEX_EXP = 21;
    private static final int INDEX_EXPM1 = 22;
    private static final int INDEX_FLOOR = 19;
    private static final int INDEX_LOG = 25;
    private static final int INDEX_LOG10 = 23;
    private static final int INDEX_LOG1P = 26;
    private static final int INDEX_LOG2 = 24;
    private static final int INDEX_LOGB = 27;
    private static final int INDEX_POW = 20;
    private static final int INDEX_SEC = 4;
    private static final int INDEX_SECH = 10;
    private static final int INDEX_SGN = 28;
    private static final int INDEX_SIN = 0;
    private static final int INDEX_SINH = 6;
    private static final int INDEX_SQRT = 15;
    private static final int INDEX_TAN = 2;
    private static final int INDEX_TANH = 8;
    private static final int INDEX_TO_DEGREE = 30;
    private static final int INDEX_TO_RADIAN = 29;

    static {
        Function[] functionArr = new Function[31];
        BUILT_IN_FUNCTIONS = functionArr;
        functionArr[0] = new Function("sin") {
            public double apply(double... args) {
                return Math.sin(args[0]);
            }
        };
        functionArr[1] = new Function("cos") {
            public double apply(double... args) {
                return Math.cos(args[0]);
            }
        };
        functionArr[2] = new Function("tan") {
            public double apply(double... args) {
                return Math.tan(args[0]);
            }
        };
        functionArr[5] = new Function("cot") {
            public double apply(double... args) {
                double tan = Math.tan(args[0]);
                if (tan != 0.0d) {
                    return 1.0d / tan;
                }
                throw new ArithmeticException("Division by zero in cotangent!");
            }
        };
        functionArr[25] = new Function("log") {
            public double apply(double... args) {
                return Math.log(args[0]);
            }
        };
        functionArr[24] = new Function("log2") {
            public double apply(double... args) {
                return Math.log(args[0]) / Math.log(2.0d);
            }
        };
        functionArr[23] = new Function("log10") {
            public double apply(double... args) {
                return Math.log10(args[0]);
            }
        };
        functionArr[26] = new Function("log1p") {
            public double apply(double... args) {
                return Math.log1p(args[0]);
            }
        };
        functionArr[17] = new Function("abs") {
            public double apply(double... args) {
                return Math.abs(args[0]);
            }
        };
        functionArr[13] = new Function("acos") {
            public double apply(double... args) {
                return Math.acos(args[0]);
            }
        };
        functionArr[12] = new Function("asin") {
            public double apply(double... args) {
                return Math.asin(args[0]);
            }
        };
        functionArr[14] = new Function("atan") {
            public double apply(double... args) {
                return Math.atan(args[0]);
            }
        };
        functionArr[16] = new Function("cbrt") {
            public double apply(double... args) {
                return Math.cbrt(args[0]);
            }
        };
        functionArr[19] = new Function("floor") {
            public double apply(double... args) {
                return Math.floor(args[0]);
            }
        };
        functionArr[6] = new Function("sinh") {
            public double apply(double... args) {
                return Math.sinh(args[0]);
            }
        };
        functionArr[15] = new Function("sqrt") {
            public double apply(double... args) {
                return Math.sqrt(args[0]);
            }
        };
        functionArr[8] = new Function("tanh") {
            public double apply(double... args) {
                return Math.tanh(args[0]);
            }
        };
        functionArr[7] = new Function("cosh") {
            public double apply(double... args) {
                return Math.cosh(args[0]);
            }
        };
        functionArr[18] = new Function("ceil") {
            public double apply(double... args) {
                return Math.ceil(args[0]);
            }
        };
        functionArr[20] = new Function("pow", 2) {
            public double apply(double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        functionArr[21] = new Function("exp", 1) {
            public double apply(double... args) {
                return Math.exp(args[0]);
            }
        };
        functionArr[22] = new Function("expm1", 1) {
            public double apply(double... args) {
                return Math.expm1(args[0]);
            }
        };
        functionArr[28] = new Function("signum", 1) {
            public double apply(double... args) {
                if (args[0] > 0.0d) {
                    return 1.0d;
                }
                if (args[0] < 0.0d) {
                    return -1.0d;
                }
                return 0.0d;
            }
        };
        functionArr[3] = new Function("csc") {
            public double apply(double... args) {
                double sin = Math.sin(args[0]);
                if (sin != 0.0d) {
                    return 1.0d / sin;
                }
                throw new ArithmeticException("Division by zero in cosecant!");
            }
        };
        functionArr[4] = new Function("sec") {
            public double apply(double... args) {
                double cos = Math.cos(args[0]);
                if (cos != 0.0d) {
                    return 1.0d / cos;
                }
                throw new ArithmeticException("Division by zero in secant!");
            }
        };
        functionArr[9] = new Function("csch") {
            public double apply(double... args) {
                if (args[0] == 0.0d) {
                    return 0.0d;
                }
                return 1.0d / Math.sinh(args[0]);
            }
        };
        functionArr[10] = new Function("sech") {
            public double apply(double... args) {
                return 1.0d / Math.cosh(args[0]);
            }
        };
        functionArr[11] = new Function("coth") {
            public double apply(double... args) {
                return Math.cosh(args[0]) / Math.sinh(args[0]);
            }
        };
        functionArr[27] = new Function("logb", 2) {
            public double apply(double... args) {
                return Math.log(args[1]) / Math.log(args[0]);
            }
        };
        functionArr[29] = new Function("toradian") {
            public double apply(double... args) {
                return Math.toRadians(args[0]);
            }
        };
        functionArr[30] = new Function("todegree") {
            public double apply(double... args) {
                return Math.toDegrees(args[0]);
            }
        };
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static net.objecthunter.exp4j.function.Function getBuiltinFunction(java.lang.String r24) {
        /*
            r0 = r24
            int r1 = r24.hashCode()
            r2 = 22
            r3 = 21
            r4 = 20
            r5 = 16
            r6 = 15
            r7 = 19
            r8 = 18
            r9 = 26
            r10 = 24
            r11 = 23
            r12 = 25
            r13 = 17
            r14 = 8
            r15 = 7
            r16 = 6
            r17 = 14
            r18 = 13
            r19 = 12
            r20 = 5
            r21 = 2
            r22 = 1
            r23 = 0
            switch(r1) {
                case -1304398585: goto L_0x017a;
                case -907382692: goto L_0x016f;
                case -902467307: goto L_0x0165;
                case 96370: goto L_0x015a;
                case 98695: goto L_0x014f;
                case 98696: goto L_0x0145;
                case 98803: goto L_0x013b;
                case 100893: goto L_0x0131;
                case 107332: goto L_0x0126;
                case 111192: goto L_0x011b;
                case 113745: goto L_0x0110;
                case 113880: goto L_0x0104;
                case 114593: goto L_0x00f8;
                case 2988422: goto L_0x00ec;
                case 3003607: goto L_0x00e1;
                case 3004320: goto L_0x00d5;
                case 3047137: goto L_0x00ca;
                case 3049733: goto L_0x00bf;
                case 3059649: goto L_0x00b4;
                case 3059680: goto L_0x00a8;
                case 3062997: goto L_0x009d;
                case 3327342: goto L_0x0091;
                case 3526199: goto L_0x0086;
                case 3530384: goto L_0x007b;
                case 3538208: goto L_0x0070;
                case 3552487: goto L_0x0064;
                case 96961601: goto L_0x0059;
                case 97526796: goto L_0x004e;
                case 103147619: goto L_0x0042;
                case 103147683: goto L_0x0036;
                default: goto L_0x0034;
            }
        L_0x0034:
            goto L_0x0185
        L_0x0036:
            java.lang.String r1 = "log1p"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r17
            goto L_0x0186
        L_0x0042:
            java.lang.String r1 = "log10"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r19
            goto L_0x0186
        L_0x004e:
            java.lang.String r1 = "floor"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r5
            goto L_0x0186
        L_0x0059:
            java.lang.String r1 = "expm1"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r3
            goto L_0x0186
        L_0x0064:
            java.lang.String r1 = "tanh"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 9
            goto L_0x0186
        L_0x0070:
            java.lang.String r1 = "sqrt"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r13
            goto L_0x0186
        L_0x007b:
            java.lang.String r1 = "sinh"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r15
            goto L_0x0186
        L_0x0086:
            java.lang.String r1 = "sech"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r9
            goto L_0x0186
        L_0x0091:
            java.lang.String r1 = "log2"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r18
            goto L_0x0186
        L_0x009d:
            java.lang.String r1 = "csch"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r12
            goto L_0x0186
        L_0x00a8:
            java.lang.String r1 = "coth"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 27
            goto L_0x0186
        L_0x00b4:
            java.lang.String r1 = "cosh"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r14
            goto L_0x0186
        L_0x00bf:
            java.lang.String r1 = "ceil"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r6
            goto L_0x0186
        L_0x00ca:
            java.lang.String r1 = "cbrt"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r8
            goto L_0x0186
        L_0x00d5:
            java.lang.String r1 = "atan"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r16
            goto L_0x0186
        L_0x00e1:
            java.lang.String r1 = "asin"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 4
            goto L_0x0186
        L_0x00ec:
            java.lang.String r1 = "acos"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r20
            goto L_0x0186
        L_0x00f8:
            java.lang.String r1 = "tan"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r21
            goto L_0x0186
        L_0x0104:
            java.lang.String r1 = "sin"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r23
            goto L_0x0186
        L_0x0110:
            java.lang.String r1 = "sec"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r10
            goto L_0x0186
        L_0x011b:
            java.lang.String r1 = "pow"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r7
            goto L_0x0186
        L_0x0126:
            java.lang.String r1 = "log"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 11
            goto L_0x0186
        L_0x0131:
            java.lang.String r1 = "exp"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r4
            goto L_0x0186
        L_0x013b:
            java.lang.String r1 = "csc"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r11
            goto L_0x0186
        L_0x0145:
            java.lang.String r1 = "cot"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 3
            goto L_0x0186
        L_0x014f:
            java.lang.String r1 = "cos"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r22
            goto L_0x0186
        L_0x015a:
            java.lang.String r1 = "abs"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 10
            goto L_0x0186
        L_0x0165:
            java.lang.String r1 = "signum"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = r2
            goto L_0x0186
        L_0x016f:
            java.lang.String r1 = "toradian"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 28
            goto L_0x0186
        L_0x017a:
            java.lang.String r1 = "todegree"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0034
            r1 = 29
            goto L_0x0186
        L_0x0185:
            r1 = -1
        L_0x0186:
            switch(r1) {
                case 0: goto L_0x022a;
                case 1: goto L_0x0225;
                case 2: goto L_0x0220;
                case 3: goto L_0x021b;
                case 4: goto L_0x0216;
                case 5: goto L_0x0211;
                case 6: goto L_0x020c;
                case 7: goto L_0x0207;
                case 8: goto L_0x0202;
                case 9: goto L_0x01fd;
                case 10: goto L_0x01f8;
                case 11: goto L_0x01f3;
                case 12: goto L_0x01ee;
                case 13: goto L_0x01e9;
                case 14: goto L_0x01e4;
                case 15: goto L_0x01df;
                case 16: goto L_0x01da;
                case 17: goto L_0x01d5;
                case 18: goto L_0x01d0;
                case 19: goto L_0x01cb;
                case 20: goto L_0x01c6;
                case 21: goto L_0x01c1;
                case 22: goto L_0x01ba;
                case 23: goto L_0x01b4;
                case 24: goto L_0x01ae;
                case 25: goto L_0x01a7;
                case 26: goto L_0x01a0;
                case 27: goto L_0x0199;
                case 28: goto L_0x0192;
                case 29: goto L_0x018b;
                default: goto L_0x0189;
            }
        L_0x0189:
            r1 = 0
            return r1
        L_0x018b:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 30
            r1 = r1[r2]
            return r1
        L_0x0192:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 29
            r1 = r1[r2]
            return r1
        L_0x0199:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 11
            r1 = r1[r2]
            return r1
        L_0x01a0:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 10
            r1 = r1[r2]
            return r1
        L_0x01a7:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 9
            r1 = r1[r2]
            return r1
        L_0x01ae:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 4
            r1 = r1[r2]
            return r1
        L_0x01b4:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 3
            r1 = r1[r2]
            return r1
        L_0x01ba:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r2 = 28
            r1 = r1[r2]
            return r1
        L_0x01c1:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r2]
            return r1
        L_0x01c6:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r3]
            return r1
        L_0x01cb:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r4]
            return r1
        L_0x01d0:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r5]
            return r1
        L_0x01d5:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r6]
            return r1
        L_0x01da:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r7]
            return r1
        L_0x01df:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r8]
            return r1
        L_0x01e4:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r9]
            return r1
        L_0x01e9:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r10]
            return r1
        L_0x01ee:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r11]
            return r1
        L_0x01f3:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r12]
            return r1
        L_0x01f8:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r13]
            return r1
        L_0x01fd:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r14]
            return r1
        L_0x0202:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r15]
            return r1
        L_0x0207:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r16]
            return r1
        L_0x020c:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r17]
            return r1
        L_0x0211:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r18]
            return r1
        L_0x0216:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r19]
            return r1
        L_0x021b:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r20]
            return r1
        L_0x0220:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r21]
            return r1
        L_0x0225:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r22]
            return r1
        L_0x022a:
            net.objecthunter.exp4j.function.Function[] r1 = BUILT_IN_FUNCTIONS
            r1 = r1[r23]
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.objecthunter.exp4j.function.Functions.getBuiltinFunction(java.lang.String):net.objecthunter.exp4j.function.Function");
    }
}
