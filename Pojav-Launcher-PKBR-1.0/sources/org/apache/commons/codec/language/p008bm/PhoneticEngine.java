package org.apache.commons.codec.language.p008bm;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.codec.language.p008bm.Languages;
import org.apache.commons.codec.language.p008bm.Rule;
import top.defaults.checkerboarddrawable.BuildConfig;

/* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine */
public class PhoneticEngine {
    private static final int DEFAULT_MAX_PHONEMES = 20;
    private static final Map<NameType, Set<String>> NAME_PREFIXES;
    private final boolean concat;
    private final Lang lang;
    private final int maxPhonemes;
    private final NameType nameType;
    private final RuleType ruleType;

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine$PhonemeBuilder */
    static final class PhonemeBuilder {
        private final Set<Rule.Phoneme> phonemes;

        /* synthetic */ PhonemeBuilder(Set x0, C08891 x1) {
            this((Set<Rule.Phoneme>) x0);
        }

        public static PhonemeBuilder empty(Languages.LanguageSet languages) {
            return new PhonemeBuilder(new Rule.Phoneme((CharSequence) BuildConfig.FLAVOR, languages));
        }

        private PhonemeBuilder(Rule.Phoneme phoneme) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            this.phonemes = linkedHashSet;
            linkedHashSet.add(phoneme);
        }

        private PhonemeBuilder(Set<Rule.Phoneme> phonemes2) {
            this.phonemes = phonemes2;
        }

        public void append(CharSequence str) {
            for (Rule.Phoneme ph : this.phonemes) {
                ph.append(str);
            }
        }

        public void apply(Rule.PhonemeExpr phonemeExpr, int maxPhonemes) {
            Set<Rule.Phoneme> newPhonemes = new LinkedHashSet<>(maxPhonemes);
            loop0:
            for (Rule.Phoneme left : this.phonemes) {
                Iterator<Rule.Phoneme> it = phonemeExpr.getPhonemes().iterator();
                while (true) {
                    if (it.hasNext()) {
                        Rule.Phoneme right = it.next();
                        Languages.LanguageSet languages = left.getLanguages().restrictTo(right.getLanguages());
                        if (!languages.isEmpty()) {
                            Rule.Phoneme join = new Rule.Phoneme(left, right, languages);
                            if (newPhonemes.size() < maxPhonemes) {
                                newPhonemes.add(join);
                                if (newPhonemes.size() >= maxPhonemes) {
                                    break loop0;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
            this.phonemes.clear();
            this.phonemes.addAll(newPhonemes);
        }

        public Set<Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public String makeString() {
            StringBuilder sb = new StringBuilder();
            for (Rule.Phoneme ph : this.phonemes) {
                if (sb.length() > 0) {
                    sb.append("|");
                }
                sb.append(ph.getPhonemeText());
            }
            return sb.toString();
        }
    }

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine$RulesApplication */
    private static final class RulesApplication {
        private final Map<String, List<Rule>> finalRules;
        private boolean found;

        /* renamed from: i */
        private int f134i;
        private final CharSequence input;
        private final int maxPhonemes;
        private final PhonemeBuilder phonemeBuilder;

        public RulesApplication(Map<String, List<Rule>> finalRules2, CharSequence input2, PhonemeBuilder phonemeBuilder2, int i, int maxPhonemes2) {
            Objects.requireNonNull(finalRules2, "finalRules");
            this.finalRules = finalRules2;
            this.phonemeBuilder = phonemeBuilder2;
            this.input = input2;
            this.f134i = i;
            this.maxPhonemes = maxPhonemes2;
        }

        public int getI() {
            return this.f134i;
        }

        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }

        public RulesApplication invoke() {
            this.found = false;
            int patternLength = 1;
            Map<String, List<Rule>> map = this.finalRules;
            CharSequence charSequence = this.input;
            int i = this.f134i;
            List<Rule> rules = map.get(charSequence.subSequence(i, i + 1));
            if (rules != null) {
                Iterator<Rule> it = rules.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Rule rule = it.next();
                    patternLength = rule.getPattern().length();
                    if (rule.patternAndContextMatches(this.input, this.f134i)) {
                        this.phonemeBuilder.apply(rule.getPhoneme(), this.maxPhonemes);
                        this.found = true;
                        break;
                    }
                }
            }
            if (!this.found) {
                patternLength = 1;
            }
            this.f134i += patternLength;
            return this;
        }

        public boolean isFound() {
            return this.found;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(NameType.class);
        NAME_PREFIXES = enumMap;
        enumMap.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"bar", "ben", "da", "de", "van", "von"}))));
        enumMap.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"}))));
        enumMap.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"}))));
    }

    private static String join(Iterable<String> strings, String sep) {
        StringBuilder sb = new StringBuilder();
        Iterator<String> si = strings.iterator();
        if (si.hasNext()) {
            sb.append(si.next());
        }
        while (si.hasNext()) {
            sb.append(sep).append(si.next());
        }
        return sb.toString();
    }

    public PhoneticEngine(NameType nameType2, RuleType ruleType2, boolean concat2) {
        this(nameType2, ruleType2, concat2, 20);
    }

    public PhoneticEngine(NameType nameType2, RuleType ruleType2, boolean concat2, int maxPhonemes2) {
        if (ruleType2 != RuleType.RULES) {
            this.nameType = nameType2;
            this.ruleType = ruleType2;
            this.concat = concat2;
            this.lang = Lang.instance(nameType2);
            this.maxPhonemes = maxPhonemes2;
            return;
        }
        throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
    }

    private PhonemeBuilder applyFinalRules(PhonemeBuilder phonemeBuilder, Map<String, List<Rule>> finalRules) {
        Objects.requireNonNull(finalRules, "finalRules");
        if (finalRules.isEmpty()) {
            return phonemeBuilder;
        }
        Map<Rule.Phoneme, Rule.Phoneme> phonemes = new TreeMap<>(Rule.Phoneme.COMPARATOR);
        for (Rule.Phoneme phoneme : phonemeBuilder.getPhonemes()) {
            PhonemeBuilder subBuilder = PhonemeBuilder.empty(phoneme.getLanguages());
            String phonemeText = phoneme.getPhonemeText().toString();
            int i = 0;
            while (i < phonemeText.length()) {
                RulesApplication rulesApplication = new RulesApplication(finalRules, phonemeText, subBuilder, i, this.maxPhonemes).invoke();
                boolean found = rulesApplication.isFound();
                subBuilder = rulesApplication.getPhonemeBuilder();
                if (!found) {
                    subBuilder.append(phonemeText.subSequence(i, i + 1));
                }
                i = rulesApplication.getI();
            }
            for (Rule.Phoneme newPhoneme : subBuilder.getPhonemes()) {
                if (phonemes.containsKey(newPhoneme)) {
                    Rule.Phoneme mergedPhoneme = phonemes.remove(newPhoneme).mergeWithLanguage(newPhoneme.getLanguages());
                    phonemes.put(mergedPhoneme, mergedPhoneme);
                } else {
                    phonemes.put(newPhoneme, newPhoneme);
                }
            }
        }
        return new PhonemeBuilder(phonemes.keySet(), (C08891) null);
    }

    public String encode(String input) {
        return encode(input, this.lang.guessLanguages(input));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String encode(java.lang.String r18, org.apache.commons.codec.language.p008bm.Languages.LanguageSet r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            org.apache.commons.codec.language.bm.NameType r2 = r0.nameType
            org.apache.commons.codec.language.bm.RuleType r3 = org.apache.commons.codec.language.p008bm.RuleType.RULES
            java.util.Map r2 = org.apache.commons.codec.language.p008bm.Rule.getInstanceMap((org.apache.commons.codec.language.p008bm.NameType) r2, (org.apache.commons.codec.language.p008bm.RuleType) r3, (org.apache.commons.codec.language.p008bm.Languages.LanguageSet) r1)
            org.apache.commons.codec.language.bm.NameType r3 = r0.nameType
            org.apache.commons.codec.language.bm.RuleType r4 = r0.ruleType
            java.lang.String r5 = "common"
            java.util.Map r3 = org.apache.commons.codec.language.p008bm.Rule.getInstanceMap((org.apache.commons.codec.language.p008bm.NameType) r3, (org.apache.commons.codec.language.p008bm.RuleType) r4, (java.lang.String) r5)
            org.apache.commons.codec.language.bm.NameType r4 = r0.nameType
            org.apache.commons.codec.language.bm.RuleType r5 = r0.ruleType
            java.util.Map r10 = org.apache.commons.codec.language.p008bm.Rule.getInstanceMap((org.apache.commons.codec.language.p008bm.NameType) r4, (org.apache.commons.codec.language.p008bm.RuleType) r5, (org.apache.commons.codec.language.p008bm.Languages.LanguageSet) r1)
            java.util.Locale r4 = java.util.Locale.ENGLISH
            r5 = r18
            java.lang.String r4 = r5.toLowerCase(r4)
            r6 = 45
            r7 = 32
            java.lang.String r4 = r4.replace(r6, r7)
            java.lang.String r4 = r4.trim()
            org.apache.commons.codec.language.bm.NameType r5 = r0.nameType
            org.apache.commons.codec.language.bm.NameType r6 = org.apache.commons.codec.language.p008bm.NameType.GENERIC
            java.lang.String r7 = " "
            r8 = 1
            if (r5 != r6) goto L_0x0104
            int r5 = r4.length()
            java.lang.String r6 = ")"
            java.lang.String r9 = ")-("
            java.lang.String r11 = "("
            r12 = 2
            if (r5 < r12) goto L_0x0092
            r5 = 0
            java.lang.String r5 = r4.substring(r5, r12)
            java.lang.String r13 = "d'"
            boolean r5 = r5.equals(r13)
            if (r5 == 0) goto L_0x0092
            java.lang.String r5 = r4.substring(r12)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "d"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r5)
            java.lang.String r7 = r7.toString()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.String r11 = r0.encode(r5)
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r0.encode(r7)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r6 = r8.append(r6)
            java.lang.String r6 = r6.toString()
            return r6
        L_0x0092:
            java.util.Map<org.apache.commons.codec.language.bm.NameType, java.util.Set<java.lang.String>> r5 = NAME_PREFIXES
            org.apache.commons.codec.language.bm.NameType r12 = r0.nameType
            java.lang.Object r5 = r5.get(r12)
            java.util.Set r5 = (java.util.Set) r5
            java.util.Iterator r5 = r5.iterator()
        L_0x00a0:
            boolean r12 = r5.hasNext()
            if (r12 == 0) goto L_0x0104
            java.lang.Object r12 = r5.next()
            java.lang.String r12 = (java.lang.String) r12
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.StringBuilder r13 = r13.append(r12)
            java.lang.StringBuilder r13 = r13.append(r7)
            java.lang.String r13 = r13.toString()
            boolean r13 = r4.startsWith(r13)
            if (r13 == 0) goto L_0x0103
            int r5 = r12.length()
            int r5 = r5 + r8
            java.lang.String r5 = r4.substring(r5)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r12)
            java.lang.StringBuilder r7 = r7.append(r5)
            java.lang.String r7 = r7.toString()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.String r11 = r0.encode(r5)
            java.lang.StringBuilder r8 = r8.append(r11)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r0.encode(r7)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r6 = r8.append(r6)
            java.lang.String r6 = r6.toString()
            return r6
        L_0x0103:
            goto L_0x00a0
        L_0x0104:
            java.lang.String r5 = "\\s+"
            java.lang.String[] r5 = r4.split(r5)
            java.util.List r11 = java.util.Arrays.asList(r5)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r12 = r5
            int[] r5 = org.apache.commons.codec.language.p008bm.PhoneticEngine.C08891.$SwitchMap$org$apache$commons$codec$language$bm$NameType
            org.apache.commons.codec.language.bm.NameType r6 = r0.nameType
            int r6 = r6.ordinal()
            r5 = r5[r6]
            switch(r5) {
                case 1: goto L_0x0151;
                case 2: goto L_0x0140;
                case 3: goto L_0x013c;
                default: goto L_0x0121;
            }
        L_0x0121:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Unreachable case: "
            java.lang.StringBuilder r6 = r6.append(r7)
            org.apache.commons.codec.language.bm.NameType r7 = r0.nameType
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L_0x013c:
            r12.addAll(r11)
            goto L_0x017d
        L_0x0140:
            r12.addAll(r11)
            java.util.Map<org.apache.commons.codec.language.bm.NameType, java.util.Set<java.lang.String>> r5 = NAME_PREFIXES
            org.apache.commons.codec.language.bm.NameType r6 = r0.nameType
            java.lang.Object r5 = r5.get(r6)
            java.util.Collection r5 = (java.util.Collection) r5
            r12.removeAll(r5)
            goto L_0x017d
        L_0x0151:
            java.util.Iterator r5 = r11.iterator()
        L_0x0155:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x016f
            java.lang.Object r6 = r5.next()
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String r9 = "'"
            java.lang.String[] r9 = r6.split(r9)
            int r13 = r9.length
            int r13 = r13 - r8
            r13 = r9[r13]
            r12.add(r13)
            goto L_0x0155
        L_0x016f:
            java.util.Map<org.apache.commons.codec.language.bm.NameType, java.util.Set<java.lang.String>> r5 = NAME_PREFIXES
            org.apache.commons.codec.language.bm.NameType r6 = r0.nameType
            java.lang.Object r5 = r5.get(r6)
            java.util.Collection r5 = (java.util.Collection) r5
            r12.removeAll(r5)
        L_0x017d:
            boolean r5 = r0.concat
            if (r5 == 0) goto L_0x0187
            java.lang.String r4 = join(r12, r7)
            r13 = r4
            goto L_0x0199
        L_0x0187:
            int r5 = r12.size()
            if (r5 != r8) goto L_0x01cd
            java.util.Iterator r5 = r11.iterator()
            java.lang.Object r5 = r5.next()
            r4 = r5
            java.lang.String r4 = (java.lang.String) r4
            r13 = r4
        L_0x0199:
            org.apache.commons.codec.language.bm.PhoneticEngine$PhonemeBuilder r4 = org.apache.commons.codec.language.p008bm.PhoneticEngine.PhonemeBuilder.empty(r19)
            r5 = 0
            r14 = r4
            r15 = r5
        L_0x01a0:
            int r4 = r13.length()
            if (r15 >= r4) goto L_0x01c0
            org.apache.commons.codec.language.bm.PhoneticEngine$RulesApplication r16 = new org.apache.commons.codec.language.bm.PhoneticEngine$RulesApplication
            int r9 = r0.maxPhonemes
            r4 = r16
            r5 = r2
            r6 = r13
            r7 = r14
            r8 = r15
            r4.<init>(r5, r6, r7, r8, r9)
            org.apache.commons.codec.language.bm.PhoneticEngine$RulesApplication r4 = r16.invoke()
            int r15 = r4.getI()
            org.apache.commons.codec.language.bm.PhoneticEngine$PhonemeBuilder r14 = r4.getPhonemeBuilder()
            goto L_0x01a0
        L_0x01c0:
            org.apache.commons.codec.language.bm.PhoneticEngine$PhonemeBuilder r4 = r0.applyFinalRules(r14, r3)
            org.apache.commons.codec.language.bm.PhoneticEngine$PhonemeBuilder r4 = r0.applyFinalRules(r4, r10)
            java.lang.String r5 = r4.makeString()
            return r5
        L_0x01cd:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.util.Iterator r6 = r12.iterator()
        L_0x01d6:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x01f0
            java.lang.Object r7 = r6.next()
            java.lang.String r7 = (java.lang.String) r7
            java.lang.String r9 = "-"
            java.lang.StringBuilder r9 = r5.append(r9)
            java.lang.String r13 = r0.encode(r7)
            r9.append(r13)
            goto L_0x01d6
        L_0x01f0:
            java.lang.String r6 = r5.substring(r8)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.p008bm.PhoneticEngine.encode(java.lang.String, org.apache.commons.codec.language.bm.Languages$LanguageSet):java.lang.String");
    }

    /* renamed from: org.apache.commons.codec.language.bm.PhoneticEngine$1 */
    static /* synthetic */ class C08891 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$codec$language$bm$NameType;

        static {
            int[] iArr = new int[NameType.values().length];
            $SwitchMap$org$apache$commons$codec$language$bm$NameType = iArr;
            try {
                iArr[NameType.SEPHARDIC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$codec$language$bm$NameType[NameType.ASHKENAZI.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$apache$commons$codec$language$bm$NameType[NameType.GENERIC.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public Lang getLang() {
        return this.lang;
    }

    public NameType getNameType() {
        return this.nameType;
    }

    public RuleType getRuleType() {
        return this.ruleType;
    }

    public boolean isConcat() {
        return this.concat;
    }

    public int getMaxPhonemes() {
        return this.maxPhonemes;
    }
}
