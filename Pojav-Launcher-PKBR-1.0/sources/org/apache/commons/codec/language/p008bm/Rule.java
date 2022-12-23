package org.apache.commons.codec.language.p008bm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.apache.commons.codec.Resources;
import org.apache.commons.codec.language.p008bm.Languages;
import top.defaults.checkerboarddrawable.BuildConfig;

/* renamed from: org.apache.commons.codec.language.bm.Rule */
public class Rule {
    public static final String ALL = "ALL";
    public static final RPattern ALL_STRINGS_RMATCHER = new RPattern() {
        public boolean isMatch(CharSequence input) {
            return true;
        }
    };
    private static final String DOUBLE_QUOTE = "\"";
    private static final String HASH_INCLUDE = "#include";
    private static final Map<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>> RULES = new EnumMap(NameType.class);
    private final RPattern lContext;
    private final String pattern;
    private final PhonemeExpr phoneme;
    private final RPattern rContext;

    /* renamed from: org.apache.commons.codec.language.bm.Rule$PhonemeExpr */
    public interface PhonemeExpr {
        Iterable<Phoneme> getPhonemes();
    }

    /* renamed from: org.apache.commons.codec.language.bm.Rule$RPattern */
    public interface RPattern {
        boolean isMatch(CharSequence charSequence);
    }

    /* renamed from: org.apache.commons.codec.language.bm.Rule$Phoneme */
    public static final class Phoneme implements PhonemeExpr {
        public static final Comparator<Phoneme> COMPARATOR = new Comparator<Phoneme>() {
            public int compare(Phoneme o1, Phoneme o2) {
                for (int i = 0; i < o1.phonemeText.length(); i++) {
                    if (i >= o2.phonemeText.length()) {
                        return 1;
                    }
                    int c = o1.phonemeText.charAt(i) - o2.phonemeText.charAt(i);
                    if (c != 0) {
                        return c;
                    }
                }
                if (o1.phonemeText.length() < o2.phonemeText.length()) {
                    return -1;
                }
                return 0;
            }
        };
        private final Languages.LanguageSet languages;
        /* access modifiers changed from: private */
        public final StringBuilder phonemeText;

        public Phoneme(CharSequence phonemeText2, Languages.LanguageSet languages2) {
            this.phonemeText = new StringBuilder(phonemeText2);
            this.languages = languages2;
        }

        public Phoneme(Phoneme phonemeLeft, Phoneme phonemeRight) {
            this((CharSequence) phonemeLeft.phonemeText, phonemeLeft.languages);
            this.phonemeText.append(phonemeRight.phonemeText);
        }

        public Phoneme(Phoneme phonemeLeft, Phoneme phonemeRight, Languages.LanguageSet languages2) {
            this((CharSequence) phonemeLeft.phonemeText, languages2);
            this.phonemeText.append(phonemeRight.phonemeText);
        }

        public Phoneme append(CharSequence str) {
            this.phonemeText.append(str);
            return this;
        }

        public Languages.LanguageSet getLanguages() {
            return this.languages;
        }

        public Iterable<Phoneme> getPhonemes() {
            return Collections.singleton(this);
        }

        public CharSequence getPhonemeText() {
            return this.phonemeText;
        }

        @Deprecated
        public Phoneme join(Phoneme right) {
            return new Phoneme((CharSequence) this.phonemeText.toString() + right.phonemeText.toString(), this.languages.restrictTo(right.languages));
        }

        public Phoneme mergeWithLanguage(Languages.LanguageSet lang) {
            return new Phoneme((CharSequence) this.phonemeText.toString(), this.languages.merge(lang));
        }

        public String toString() {
            return this.phonemeText.toString() + "[" + this.languages + "]";
        }
    }

    /* renamed from: org.apache.commons.codec.language.bm.Rule$PhonemeList */
    public static final class PhonemeList implements PhonemeExpr {
        private final List<Phoneme> phonemes;

        public PhonemeList(List<Phoneme> phonemes2) {
            this.phonemes = phonemes2;
        }

        public List<Phoneme> getPhonemes() {
            return this.phonemes;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0061, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0062, code lost:
        if (r14 != null) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0064, code lost:
        if (r0 != null) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r14.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006f, code lost:
        r14.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0072, code lost:
        throw r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b2, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b3, code lost:
        if (r13 != null) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b5, code lost:
        if (r0 != null) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00bb, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00bc, code lost:
        r0.addSuppressed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c0, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00c3, code lost:
        throw r1;
     */
    static {
        /*
            org.apache.commons.codec.language.bm.Rule$1 r0 = new org.apache.commons.codec.language.bm.Rule$1
            r0.<init>()
            ALL_STRINGS_RMATCHER = r0
            java.util.EnumMap r0 = new java.util.EnumMap
            java.lang.Class<org.apache.commons.codec.language.bm.NameType> r1 = org.apache.commons.codec.language.p008bm.NameType.class
            r0.<init>(r1)
            RULES = r0
            org.apache.commons.codec.language.bm.NameType[] r0 = org.apache.commons.codec.language.p008bm.NameType.values()
            int r1 = r0.length
            r2 = 0
            r3 = r2
        L_0x0017:
            if (r3 >= r1) goto L_0x00dc
            r4 = r0[r3]
            java.util.EnumMap r5 = new java.util.EnumMap
            java.lang.Class<org.apache.commons.codec.language.bm.RuleType> r6 = org.apache.commons.codec.language.p008bm.RuleType.class
            r5.<init>(r6)
            org.apache.commons.codec.language.bm.RuleType[] r6 = org.apache.commons.codec.language.p008bm.RuleType.values()
            int r7 = r6.length
            r8 = r2
        L_0x0028:
            if (r8 >= r7) goto L_0x00cf
            r9 = r6[r8]
            java.util.HashMap r10 = new java.util.HashMap
            r10.<init>()
            org.apache.commons.codec.language.bm.Languages r11 = org.apache.commons.codec.language.p008bm.Languages.getInstance((org.apache.commons.codec.language.p008bm.NameType) r4)
            java.util.Set r12 = r11.getLanguages()
            java.util.Iterator r12 = r12.iterator()
        L_0x003d:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0091
            java.lang.Object r13 = r12.next()
            java.lang.String r13 = (java.lang.String) r13
            java.util.Scanner r14 = createScanner(r4, r9, r13)     // Catch:{ IllegalStateException -> 0x0073 }
            java.lang.String r15 = createResourceName(r4, r9, r13)     // Catch:{ all -> 0x005f }
            java.util.Map r15 = parseRules(r14, r15)     // Catch:{ all -> 0x005f }
            r10.put(r13, r15)     // Catch:{ all -> 0x005f }
            if (r14 == 0) goto L_0x005d
            r14.close()     // Catch:{ IllegalStateException -> 0x0073 }
        L_0x005d:
            goto L_0x003d
        L_0x005f:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0061 }
        L_0x0061:
            r1 = move-exception
            if (r14 == 0) goto L_0x0072
            if (r0 == 0) goto L_0x006f
            r14.close()     // Catch:{ all -> 0x006a }
            goto L_0x0072
        L_0x006a:
            r2 = move-exception
            r0.addSuppressed(r2)     // Catch:{ IllegalStateException -> 0x0073 }
            goto L_0x0072
        L_0x006f:
            r14.close()     // Catch:{ IllegalStateException -> 0x0073 }
        L_0x0072:
            throw r1     // Catch:{ IllegalStateException -> 0x0073 }
        L_0x0073:
            r0 = move-exception
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Problem processing "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = createResourceName(r4, r9, r13)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2, r0)
            throw r1
        L_0x0091:
            org.apache.commons.codec.language.bm.RuleType r12 = org.apache.commons.codec.language.p008bm.RuleType.RULES
            boolean r12 = r9.equals(r12)
            if (r12 != 0) goto L_0x00c4
            java.lang.String r12 = "common"
            java.util.Scanner r13 = createScanner(r4, r9, r12)
            java.lang.String r14 = createResourceName(r4, r9, r12)     // Catch:{ all -> 0x00b0 }
            java.util.Map r14 = parseRules(r13, r14)     // Catch:{ all -> 0x00b0 }
            r10.put(r12, r14)     // Catch:{ all -> 0x00b0 }
            if (r13 == 0) goto L_0x00c4
            r13.close()
            goto L_0x00c4
        L_0x00b0:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x00b2 }
        L_0x00b2:
            r1 = move-exception
            if (r13 == 0) goto L_0x00c3
            if (r0 == 0) goto L_0x00c0
            r13.close()     // Catch:{ all -> 0x00bb }
            goto L_0x00c3
        L_0x00bb:
            r2 = move-exception
            r0.addSuppressed(r2)
            goto L_0x00c3
        L_0x00c0:
            r13.close()
        L_0x00c3:
            throw r1
        L_0x00c4:
            java.util.Map r12 = java.util.Collections.unmodifiableMap(r10)
            r5.put(r9, r12)
            int r8 = r8 + 1
            goto L_0x0028
        L_0x00cf:
            java.util.Map<org.apache.commons.codec.language.bm.NameType, java.util.Map<org.apache.commons.codec.language.bm.RuleType, java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<org.apache.commons.codec.language.bm.Rule>>>>> r6 = RULES
            java.util.Map r7 = java.util.Collections.unmodifiableMap(r5)
            r6.put(r4, r7)
            int r3 = r3 + 1
            goto L_0x0017
        L_0x00dc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.p008bm.Rule.<clinit>():void");
    }

    /* access modifiers changed from: private */
    public static boolean contains(CharSequence chars, char input) {
        for (int i = 0; i < chars.length(); i++) {
            if (chars.charAt(i) == input) {
                return true;
            }
        }
        return false;
    }

    private static String createResourceName(NameType nameType, RuleType rt, String lang) {
        return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", new Object[]{nameType.getName(), rt.getName(), lang});
    }

    private static Scanner createScanner(NameType nameType, RuleType rt, String lang) {
        return new Scanner(Resources.getInputStream(createResourceName(nameType, rt, lang)), "UTF-8");
    }

    private static Scanner createScanner(String lang) {
        return new Scanner(Resources.getInputStream(String.format("org/apache/commons/codec/language/bm/%s.txt", new Object[]{lang})), "UTF-8");
    }

    /* access modifiers changed from: private */
    public static boolean endsWith(CharSequence input, CharSequence suffix) {
        if (suffix.length() > input.length()) {
            return false;
        }
        int i = input.length() - 1;
        for (int j = suffix.length() - 1; j >= 0; j--) {
            if (input.charAt(i) != suffix.charAt(j)) {
                return false;
            }
            i--;
        }
        return true;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType rt, Languages.LanguageSet langs) {
        Map<String, List<Rule>> ruleMap = getInstanceMap(nameType, rt, langs);
        List<Rule> allRules = new ArrayList<>();
        for (List<Rule> rules : ruleMap.values()) {
            allRules.addAll(rules);
        }
        return allRules;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType rt, String lang) {
        return getInstance(nameType, rt, Languages.LanguageSet.from(new HashSet(Arrays.asList(new String[]{lang}))));
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType rt, Languages.LanguageSet langs) {
        if (langs.isSingleton()) {
            return getInstanceMap(nameType, rt, langs.getAny());
        }
        return getInstanceMap(nameType, rt, Languages.ANY);
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType rt, String lang) {
        Map<String, List<Rule>> rules = (Map) ((Map) RULES.get(nameType).get(rt)).get(lang);
        if (rules != null) {
            return rules;
        }
        throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", new Object[]{nameType.getName(), rt.getName(), lang}));
    }

    private static Phoneme parsePhoneme(String ph) {
        int open = ph.indexOf("[");
        if (open < 0) {
            return new Phoneme((CharSequence) ph, Languages.ANY_LANGUAGE);
        }
        if (ph.endsWith("]")) {
            return new Phoneme((CharSequence) ph.substring(0, open), Languages.LanguageSet.from(new HashSet<>(Arrays.asList(ph.substring(open + 1, ph.length() - 1).split("[+]")))));
        }
        throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
    }

    private static PhonemeExpr parsePhonemeExpr(String ph) {
        if (!ph.startsWith("(")) {
            return parsePhoneme(ph);
        }
        if (ph.endsWith(")")) {
            List<Phoneme> phs = new ArrayList<>();
            String body = ph.substring(1, ph.length() - 1);
            for (String part : body.split("[|]")) {
                phs.add(parsePhoneme(part));
            }
            if (body.startsWith("|") || body.endsWith("|")) {
                phs.add(new Phoneme((CharSequence) BuildConfig.FLAVOR, Languages.ANY_LANGUAGE));
            }
            return new PhonemeList(phs);
        }
        throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
    }

    private static Map<String, List<Rule>> parseRules(Scanner scanner, String location) {
        boolean inMultilineComment;
        String rawLine;
        Throwable th;
        String str = location;
        Map<String, List<Rule>> lines = new HashMap<>();
        int currentLine = 0;
        boolean inMultilineComment2 = false;
        while (scanner.hasNextLine()) {
            int currentLine2 = currentLine + 1;
            String rawLine2 = scanner.nextLine();
            String line = rawLine2;
            if (inMultilineComment2) {
                if (line.endsWith("*/")) {
                    inMultilineComment2 = false;
                    currentLine = currentLine2;
                } else {
                    inMultilineComment = inMultilineComment2;
                }
            } else if (line.startsWith("/*")) {
                inMultilineComment2 = true;
                currentLine = currentLine2;
            } else {
                int cmtI = line.indexOf("//");
                if (cmtI >= 0) {
                    line = line.substring(0, cmtI);
                }
                String line2 = line.trim();
                if (line2.length() == 0) {
                    currentLine = currentLine2;
                } else if (line2.startsWith(HASH_INCLUDE)) {
                    String incl = line2.substring(HASH_INCLUDE.length()).trim();
                    if (!incl.contains(" ")) {
                        Scanner hashIncludeScanner = createScanner(incl);
                        try {
                            lines.putAll(parseRules(hashIncludeScanner, str + "->" + incl));
                            if (hashIncludeScanner != null) {
                                hashIncludeScanner.close();
                            }
                            inMultilineComment = inMultilineComment2;
                        } catch (Throwable th2) {
                            Throwable th3 = th2;
                            if (hashIncludeScanner != null) {
                                if (th != null) {
                                    try {
                                        hashIncludeScanner.close();
                                    } catch (Throwable th4) {
                                        th.addSuppressed(th4);
                                    }
                                } else {
                                    hashIncludeScanner.close();
                                }
                            }
                            throw th3;
                        }
                    } else {
                        throw new IllegalArgumentException("Malformed import statement '" + rawLine2 + "' in " + str);
                    }
                } else {
                    String[] parts = line2.split("\\s+");
                    if (parts.length == 4) {
                        try {
                            String pat = stripQuotes(parts[0]);
                            String lCon = stripQuotes(parts[1]);
                            String rCon = stripQuotes(parts[2]);
                            PhonemeExpr ph = parsePhonemeExpr(stripQuotes(parts[3]));
                            inMultilineComment = inMultilineComment2;
                            String[] strArr = parts;
                            String str2 = rawLine2;
                            rawLine = "' in ";
                            String str3 = line2;
                            int i = cmtI;
                            try {
                                C08922 r1 = new Rule(pat, lCon, rCon, ph, currentLine2, location, pat, lCon, rCon) {
                                    private final String loc;
                                    private final int myLine;
                                    final /* synthetic */ int val$cLine;
                                    final /* synthetic */ String val$lCon;
                                    final /* synthetic */ String val$location;
                                    final /* synthetic */ String val$pat;
                                    final /* synthetic */ String val$rCon;

                                    {
                                        this.val$cLine = r5;
                                        this.val$location = r6;
                                        this.val$pat = r7;
                                        this.val$lCon = r8;
                                        this.val$rCon = r9;
                                        this.myLine = r5;
                                        this.loc = r6;
                                    }

                                    public String toString() {
                                        StringBuilder sb = new StringBuilder();
                                        sb.append("Rule");
                                        sb.append("{line=").append(this.myLine);
                                        sb.append(", loc='").append(this.loc).append('\'');
                                        sb.append(", pat='").append(this.val$pat).append('\'');
                                        sb.append(", lcon='").append(this.val$lCon).append('\'');
                                        sb.append(", rcon='").append(this.val$rCon).append('\'');
                                        sb.append('}');
                                        return sb.toString();
                                    }
                                };
                                Rule r = r1;
                                String patternKey = r.pattern.substring(0, 1);
                                List<Rule> rules = lines.get(patternKey);
                                if (rules == null) {
                                    rules = new ArrayList<>();
                                    lines.put(patternKey, rules);
                                }
                                rules.add(r);
                            } catch (IllegalArgumentException e) {
                                e = e;
                                throw new IllegalStateException("Problem parsing line '" + currentLine2 + rawLine + str, e);
                            }
                        } catch (IllegalArgumentException e2) {
                            e = e2;
                            String str4 = line2;
                            int i2 = cmtI;
                            boolean z = inMultilineComment2;
                            String str5 = rawLine2;
                            String[] strArr2 = parts;
                            rawLine = "' in ";
                            throw new IllegalStateException("Problem parsing line '" + currentLine2 + rawLine + str, e);
                        }
                    } else {
                        throw new IllegalArgumentException("Malformed rule statement split into " + parts.length + " parts: " + rawLine2 + " in " + str);
                    }
                }
            }
            inMultilineComment2 = inMultilineComment;
            currentLine = currentLine2;
        }
        return lines;
    }

    private static RPattern pattern(String regex) {
        boolean startsWith = regex.startsWith("^");
        boolean endsWith = regex.endsWith("$");
        int length = regex.length();
        if (endsWith) {
            length--;
        }
        final String content = regex.substring(startsWith, length);
        if (content.contains("[")) {
            boolean startsWithBox = content.startsWith("[");
            boolean endsWithBox = content.endsWith("]");
            if (startsWithBox && endsWithBox) {
                String boxContent = content.substring(1, content.length() - 1);
                if (!boxContent.contains("[")) {
                    boolean negate = boxContent.startsWith("^");
                    if (negate) {
                        boxContent = boxContent.substring(1);
                    }
                    final String bContent = boxContent;
                    final boolean shouldMatch = !negate;
                    if (startsWith && endsWith) {
                        return new RPattern() {
                            public boolean isMatch(CharSequence input) {
                                return input.length() == 1 && Rule.contains(bContent, input.charAt(0)) == shouldMatch;
                            }
                        };
                    }
                    if (startsWith) {
                        return new RPattern() {
                            public boolean isMatch(CharSequence input) {
                                return input.length() > 0 && Rule.contains(bContent, input.charAt(0)) == shouldMatch;
                            }
                        };
                    }
                    if (endsWith) {
                        return new RPattern() {
                            public boolean isMatch(CharSequence input) {
                                if (input.length() <= 0 || Rule.contains(bContent, input.charAt(input.length() - 1)) != shouldMatch) {
                                    return false;
                                }
                                return true;
                            }
                        };
                    }
                }
            }
        } else if (!startsWith || !endsWith) {
            if ((startsWith || endsWith) && content.length() == 0) {
                return ALL_STRINGS_RMATCHER;
            }
            if (startsWith) {
                return new RPattern() {
                    public boolean isMatch(CharSequence input) {
                        return Rule.startsWith(input, content);
                    }
                };
            }
            if (endsWith) {
                return new RPattern() {
                    public boolean isMatch(CharSequence input) {
                        return Rule.endsWith(input, content);
                    }
                };
            }
        } else if (content.length() == 0) {
            return new RPattern() {
                public boolean isMatch(CharSequence input) {
                    return input.length() == 0;
                }
            };
        } else {
            return new RPattern() {
                public boolean isMatch(CharSequence input) {
                    return input.equals(content);
                }
            };
        }
        return new RPattern(regex) {
            Pattern pattern;
            final /* synthetic */ String val$regex;

            {
                this.val$regex = r1;
                this.pattern = Pattern.compile(r1);
            }

            public boolean isMatch(CharSequence input) {
                return this.pattern.matcher(input).find();
            }
        };
    }

    /* access modifiers changed from: private */
    public static boolean startsWith(CharSequence input, CharSequence prefix) {
        if (prefix.length() > input.length()) {
            return false;
        }
        for (int i = 0; i < prefix.length(); i++) {
            if (input.charAt(i) != prefix.charAt(i)) {
                return false;
            }
        }
        return true;
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

    public Rule(String pattern2, String lContext2, String rContext2, PhonemeExpr phoneme2) {
        this.pattern = pattern2;
        this.lContext = pattern(lContext2 + "$");
        this.rContext = pattern("^" + rContext2);
        this.phoneme = phoneme2;
    }

    public RPattern getLContext() {
        return this.lContext;
    }

    public String getPattern() {
        return this.pattern;
    }

    public PhonemeExpr getPhoneme() {
        return this.phoneme;
    }

    public RPattern getRContext() {
        return this.rContext;
    }

    public boolean patternAndContextMatches(CharSequence input, int i) {
        if (i >= 0) {
            int ipl = i + this.pattern.length();
            if (ipl <= input.length() && input.subSequence(i, ipl).equals(this.pattern) && this.rContext.isMatch(input.subSequence(ipl, input.length()))) {
                return this.lContext.isMatch(input.subSequence(0, i));
            }
            return false;
        }
        throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
    }
}
