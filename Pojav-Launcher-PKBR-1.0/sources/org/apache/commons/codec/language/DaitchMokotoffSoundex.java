package org.apache.commons.codec.language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class DaitchMokotoffSoundex implements StringEncoder {
    private static final String COMMENT = "//";
    private static final String DOUBLE_QUOTE = "\"";
    private static final Map<Character, Character> FOLDINGS;
    private static final int MAX_LENGTH = 6;
    private static final String MULTILINE_COMMENT_END = "*/";
    private static final String MULTILINE_COMMENT_START = "/*";
    private static final String RESOURCE_FILE = "org/apache/commons/codec/language/dmrules.txt";
    private static final Map<Character, List<Rule>> RULES;
    private final boolean folding;

    private static final class Branch {
        private final StringBuilder builder;
        private String cachedString;
        private String lastReplacement;

        private Branch() {
            this.builder = new StringBuilder();
            this.lastReplacement = null;
            this.cachedString = null;
        }

        public Branch createBranch() {
            Branch branch = new Branch();
            branch.builder.append(toString());
            branch.lastReplacement = this.lastReplacement;
            return branch;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Branch)) {
                return false;
            }
            return toString().equals(((Branch) other).toString());
        }

        public void finish() {
            while (this.builder.length() < 6) {
                this.builder.append('0');
                this.cachedString = null;
            }
        }

        public int hashCode() {
            return toString().hashCode();
        }

        public void processNextReplacement(String replacement, boolean forceAppend) {
            String str = this.lastReplacement;
            if ((str == null || !str.endsWith(replacement) || forceAppend) && this.builder.length() < 6) {
                this.builder.append(replacement);
                if (this.builder.length() > 6) {
                    StringBuilder sb = this.builder;
                    sb.delete(6, sb.length());
                }
                this.cachedString = null;
            }
            this.lastReplacement = replacement;
        }

        public String toString() {
            if (this.cachedString == null) {
                this.cachedString = this.builder.toString();
            }
            return this.cachedString;
        }
    }

    private static final class Rule {
        /* access modifiers changed from: private */
        public final String pattern;
        private final String[] replacementAtStart;
        private final String[] replacementBeforeVowel;
        private final String[] replacementDefault;

        protected Rule(String pattern2, String replacementAtStart2, String replacementBeforeVowel2, String replacementDefault2) {
            this.pattern = pattern2;
            this.replacementAtStart = replacementAtStart2.split("\\|");
            this.replacementBeforeVowel = replacementBeforeVowel2.split("\\|");
            this.replacementDefault = replacementDefault2.split("\\|");
        }

        public int getPatternLength() {
            return this.pattern.length();
        }

        public String[] getReplacements(String context, boolean atStart) {
            if (atStart) {
                return this.replacementAtStart;
            }
            int nextIndex = getPatternLength();
            if (nextIndex < context.length() ? isVowel(context.charAt(nextIndex)) : false) {
                return this.replacementBeforeVowel;
            }
            return this.replacementDefault;
        }

        private boolean isVowel(char ch) {
            return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
        }

        public boolean matches(String context) {
            return context.startsWith(this.pattern);
        }

        public String toString() {
            return String.format("%s=(%s,%s,%s)", new Object[]{this.pattern, Arrays.asList(this.replacementAtStart), Arrays.asList(this.replacementBeforeVowel), Arrays.asList(this.replacementDefault)});
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0047, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0048, code lost:
        if (r0 != null) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004e, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004f, code lost:
        r0.addSuppressed(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0053, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0056, code lost:
        throw r1;
     */
    static {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            RULES = r0
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            FOLDINGS = r1
            java.util.Scanner r2 = new java.util.Scanner
            java.lang.String r3 = "org/apache/commons/codec/language/dmrules.txt"
            java.io.InputStream r4 = org.apache.commons.codec.Resources.getInputStream(r3)
            java.lang.String r5 = "UTF-8"
            r2.<init>(r4, r5)
            parseRules(r2, r3, r0, r1)     // Catch:{ all -> 0x0045 }
            r2.close()
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0029:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0044
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getValue()
            java.util.List r2 = (java.util.List) r2
            org.apache.commons.codec.language.DaitchMokotoffSoundex$1 r3 = new org.apache.commons.codec.language.DaitchMokotoffSoundex$1
            r3.<init>()
            java.util.Collections.sort(r2, r3)
            goto L_0x0029
        L_0x0044:
            return
        L_0x0045:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0047 }
        L_0x0047:
            r1 = move-exception
            if (r0 == 0) goto L_0x0053
            r2.close()     // Catch:{ all -> 0x004e }
            goto L_0x0056
        L_0x004e:
            r3 = move-exception
            r0.addSuppressed(r3)
            goto L_0x0056
        L_0x0053:
            r2.close()
        L_0x0056:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DaitchMokotoffSoundex.<clinit>():void");
    }

    private static void parseRules(Scanner scanner, String location, Map<Character, List<Rule>> ruleMapping, Map<Character, Character> asciiFoldings) {
        String str = location;
        Map<Character, List<Rule>> map = ruleMapping;
        int currentLine = 0;
        boolean inMultilineComment = false;
        while (scanner.hasNextLine()) {
            int currentLine2 = currentLine + 1;
            String rawLine = scanner.nextLine();
            String line = rawLine;
            if (!inMultilineComment) {
                if (line.startsWith(MULTILINE_COMMENT_START)) {
                    inMultilineComment = true;
                    Map<Character, Character> map2 = asciiFoldings;
                } else {
                    int cmtI = line.indexOf(COMMENT);
                    if (cmtI >= 0) {
                        line = line.substring(0, cmtI);
                    }
                    String line2 = line.trim();
                    if (line2.length() != 0) {
                        if (line2.contains("=")) {
                            String[] parts = line2.split("=");
                            if (parts.length == 2) {
                                String leftCharacter = parts[0];
                                String rightCharacter = parts[1];
                                if (leftCharacter.length() == 1 && rightCharacter.length() == 1) {
                                    asciiFoldings.put(Character.valueOf(leftCharacter.charAt(0)), Character.valueOf(rightCharacter.charAt(0)));
                                } else {
                                    Map<Character, Character> map3 = asciiFoldings;
                                    throw new IllegalArgumentException("Malformed folding statement - patterns are not single characters: " + rawLine + " in " + str);
                                }
                            } else {
                                Map<Character, Character> map4 = asciiFoldings;
                                throw new IllegalArgumentException("Malformed folding statement split into " + parts.length + " parts: " + rawLine + " in " + str);
                            }
                        } else {
                            Map<Character, Character> map5 = asciiFoldings;
                            String[] parts2 = line2.split("\\s+");
                            if (parts2.length == 4) {
                                try {
                                    String pattern = stripQuotes(parts2[0]);
                                    Rule r = new Rule(pattern, stripQuotes(parts2[1]), stripQuotes(parts2[2]), stripQuotes(parts2[3]));
                                    char patternKey = r.pattern.charAt(0);
                                    List<Rule> rules = map.get(Character.valueOf(patternKey));
                                    if (rules == null) {
                                        rules = new ArrayList<>();
                                        String str2 = pattern;
                                        map.put(Character.valueOf(patternKey), rules);
                                    }
                                    rules.add(r);
                                } catch (IllegalArgumentException e) {
                                    throw new IllegalStateException("Problem parsing line '" + currentLine2 + "' in " + str, e);
                                }
                            } else {
                                throw new IllegalArgumentException("Malformed rule statement split into " + parts2.length + " parts: " + rawLine + " in " + str);
                            }
                        }
                    }
                }
                currentLine = currentLine2;
            } else if (line.endsWith(MULTILINE_COMMENT_END)) {
                inMultilineComment = false;
                currentLine = currentLine2;
            }
            currentLine = currentLine2;
        }
        Map<Character, Character> map6 = asciiFoldings;
    }

    private static String stripQuotes(String str) {
        if (str.startsWith(DOUBLE_QUOTE)) {
            str = str.substring(1);
        }
        if (str.endsWith(DOUBLE_QUOTE)) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    public DaitchMokotoffSoundex() {
        this(true);
    }

    public DaitchMokotoffSoundex(boolean folding2) {
        this.folding = folding2;
    }

    private String cleanup(String input) {
        StringBuilder sb = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (!Character.isWhitespace(ch)) {
                char ch2 = Character.toLowerCase(ch);
                if (this.folding) {
                    Map<Character, Character> map = FOLDINGS;
                    if (map.containsKey(Character.valueOf(ch2))) {
                        ch2 = map.get(Character.valueOf(ch2)).charValue();
                    }
                }
                sb.append(ch2);
            }
        }
        return sb.toString();
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Parameter supplied to DaitchMokotoffSoundex encode is not of type java.lang.String");
    }

    public String encode(String source) {
        if (source == null) {
            return null;
        }
        return soundex(source, false)[0];
    }

    public String soundex(String source) {
        String[] branches = soundex(source, true);
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (String branch : branches) {
            sb.append(branch);
            index++;
            if (index < branches.length) {
                sb.append('|');
            }
        }
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a1, code lost:
        if (r4 != 'n') goto L_0x00a6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00b8 A[LOOP:3: B:33:0x008a->B:50:0x00b8, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00c7 A[EDGE_INSN: B:73:0x00c7->B:52:0x00c7 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String[] soundex(java.lang.String r20, boolean r21) {
        /*
            r19 = this;
            r0 = 0
            if (r20 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = r19.cleanup(r20)
            java.util.LinkedHashSet r2 = new java.util.LinkedHashSet
            r2.<init>()
            org.apache.commons.codec.language.DaitchMokotoffSoundex$Branch r3 = new org.apache.commons.codec.language.DaitchMokotoffSoundex$Branch
            r3.<init>()
            r2.add(r3)
            r0 = 0
            r3 = 0
        L_0x0017:
            int r4 = r1.length()
            if (r3 >= r4) goto L_0x00f3
            char r4 = r1.charAt(r3)
            boolean r5 = java.lang.Character.isWhitespace(r4)
            r6 = 1
            if (r5 == 0) goto L_0x0029
            goto L_0x003c
        L_0x0029:
            java.lang.String r5 = r1.substring(r3)
            java.util.Map<java.lang.Character, java.util.List<org.apache.commons.codec.language.DaitchMokotoffSoundex$Rule>> r7 = RULES
            java.lang.Character r8 = java.lang.Character.valueOf(r4)
            java.lang.Object r7 = r7.get(r8)
            java.util.List r7 = (java.util.List) r7
            if (r7 != 0) goto L_0x0040
        L_0x003c:
            r16 = r1
            goto L_0x00ed
        L_0x0040:
            if (r21 == 0) goto L_0x0048
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            goto L_0x004c
        L_0x0048:
            java.util.List r8 = java.util.Collections.emptyList()
        L_0x004c:
            java.util.Iterator r9 = r7.iterator()
        L_0x0050:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00e8
            java.lang.Object r10 = r9.next()
            org.apache.commons.codec.language.DaitchMokotoffSoundex$Rule r10 = (org.apache.commons.codec.language.DaitchMokotoffSoundex.Rule) r10
            boolean r11 = r10.matches(r5)
            if (r11 == 0) goto L_0x00e1
            if (r21 == 0) goto L_0x0067
            r8.clear()
        L_0x0067:
            if (r0 != 0) goto L_0x006b
            r11 = r6
            goto L_0x006c
        L_0x006b:
            r11 = 0
        L_0x006c:
            java.lang.String[] r11 = r10.getReplacements(r5, r11)
            int r12 = r11.length
            if (r12 <= r6) goto L_0x0077
            if (r21 == 0) goto L_0x0077
            r12 = r6
            goto L_0x0078
        L_0x0077:
            r12 = 0
        L_0x0078:
            java.util.Iterator r13 = r2.iterator()
        L_0x007c:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x00cd
            java.lang.Object r14 = r13.next()
            org.apache.commons.codec.language.DaitchMokotoffSoundex$Branch r14 = (org.apache.commons.codec.language.DaitchMokotoffSoundex.Branch) r14
            int r15 = r11.length
            r9 = 0
        L_0x008a:
            if (r9 >= r15) goto L_0x00c3
            r6 = r11[r9]
            if (r12 == 0) goto L_0x0095
            org.apache.commons.codec.language.DaitchMokotoffSoundex$Branch r16 = r14.createBranch()
            goto L_0x0097
        L_0x0095:
            r16 = r14
        L_0x0097:
            r17 = r16
            r16 = r1
            r1 = 109(0x6d, float:1.53E-43)
            if (r0 != r1) goto L_0x00a4
            r1 = 110(0x6e, float:1.54E-43)
            if (r4 == r1) goto L_0x00ac
            goto L_0x00a6
        L_0x00a4:
            r1 = 110(0x6e, float:1.54E-43)
        L_0x00a6:
            if (r0 != r1) goto L_0x00ae
            r1 = 109(0x6d, float:1.53E-43)
            if (r4 != r1) goto L_0x00ae
        L_0x00ac:
            r1 = 1
            goto L_0x00af
        L_0x00ae:
            r1 = 0
        L_0x00af:
            r18 = r0
            r0 = r17
            r0.processNextReplacement(r6, r1)
            if (r21 == 0) goto L_0x00c7
            r8.add(r0)
            int r9 = r9 + 1
            r1 = r16
            r0 = r18
            r6 = 1
            goto L_0x008a
        L_0x00c3:
            r18 = r0
            r16 = r1
        L_0x00c7:
            r1 = r16
            r0 = r18
            r6 = 1
            goto L_0x007c
        L_0x00cd:
            r18 = r0
            r16 = r1
            if (r21 == 0) goto L_0x00d9
            r2.clear()
            r2.addAll(r8)
        L_0x00d9:
            int r0 = r10.getPatternLength()
            r1 = 1
            int r0 = r0 - r1
            int r3 = r3 + r0
            goto L_0x00ec
        L_0x00e1:
            r18 = r0
            r16 = r1
            r6 = 1
            goto L_0x0050
        L_0x00e8:
            r18 = r0
            r16 = r1
        L_0x00ec:
            r0 = r4
        L_0x00ed:
            r1 = 1
            int r3 = r3 + r1
            r1 = r16
            goto L_0x0017
        L_0x00f3:
            r18 = r0
            r16 = r1
            int r0 = r2.size()
            java.lang.String[] r0 = new java.lang.String[r0]
            r1 = 0
            java.util.Iterator r3 = r2.iterator()
        L_0x0102:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x011b
            java.lang.Object r4 = r3.next()
            org.apache.commons.codec.language.DaitchMokotoffSoundex$Branch r4 = (org.apache.commons.codec.language.DaitchMokotoffSoundex.Branch) r4
            r4.finish()
            int r5 = r1 + 1
            java.lang.String r6 = r4.toString()
            r0[r1] = r6
            r1 = r5
            goto L_0x0102
        L_0x011b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.DaitchMokotoffSoundex.soundex(java.lang.String, boolean):java.lang.String[]");
    }
}
