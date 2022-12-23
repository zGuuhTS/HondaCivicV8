package org.apache.commons.codec.language.p008bm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.p008bm.Languages;

/* renamed from: org.apache.commons.codec.language.bm.Lang */
public class Lang {
    private static final String LANGUAGE_RULES_RN = "org/apache/commons/codec/language/bm/%s_lang.txt";
    private static final Map<NameType, Lang> Langs = new EnumMap(NameType.class);
    private final Languages languages;
    private final List<LangRule> rules;

    /* renamed from: org.apache.commons.codec.language.bm.Lang$LangRule */
    private static final class LangRule {
        /* access modifiers changed from: private */
        public final boolean acceptOnMatch;
        /* access modifiers changed from: private */
        public final Set<String> languages;
        private final Pattern pattern;

        private LangRule(Pattern pattern2, Set<String> languages2, boolean acceptOnMatch2) {
            this.pattern = pattern2;
            this.languages = languages2;
            this.acceptOnMatch = acceptOnMatch2;
        }

        public boolean matches(String txt) {
            return this.pattern.matcher(txt).find();
        }
    }

    static {
        for (NameType s : NameType.values()) {
            Langs.put(s, loadFromResource(String.format(LANGUAGE_RULES_RN, new Object[]{s.getName()}), Languages.getInstance(s)));
        }
    }

    public static Lang instance(NameType nameType) {
        return Langs.get(nameType);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b5, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b6, code lost:
        if (r2 != null) goto L_0x00b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bc, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bd, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c1, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c4, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.apache.commons.codec.language.p008bm.Lang loadFromResource(java.lang.String r13, org.apache.commons.codec.language.p008bm.Languages r14) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.Scanner r1 = new java.util.Scanner
            java.io.InputStream r2 = org.apache.commons.codec.Resources.getInputStream(r13)
            java.lang.String r3 = "UTF-8"
            r1.<init>(r2, r3)
            r2 = 0
        L_0x0011:
            boolean r3 = r1.hasNextLine()     // Catch:{ all -> 0x00b3 }
            if (r3 == 0) goto L_0x00aa
            java.lang.String r3 = r1.nextLine()     // Catch:{ all -> 0x00b3 }
            r4 = r3
            if (r2 == 0) goto L_0x0028
            java.lang.String r5 = "*/"
            boolean r5 = r4.endsWith(r5)     // Catch:{ all -> 0x00b3 }
            if (r5 == 0) goto L_0x0080
            r2 = 0
            goto L_0x0080
        L_0x0028:
            java.lang.String r5 = "/*"
            boolean r5 = r4.startsWith(r5)     // Catch:{ all -> 0x00b3 }
            if (r5 == 0) goto L_0x0032
            r2 = 1
            goto L_0x0080
        L_0x0032:
            java.lang.String r5 = "//"
            int r5 = r4.indexOf(r5)     // Catch:{ all -> 0x00b3 }
            r6 = 0
            if (r5 < 0) goto L_0x0040
            java.lang.String r7 = r4.substring(r6, r5)     // Catch:{ all -> 0x00b3 }
            r4 = r7
        L_0x0040:
            java.lang.String r7 = r4.trim()     // Catch:{ all -> 0x00b3 }
            r4 = r7
            int r7 = r4.length()     // Catch:{ all -> 0x00b3 }
            if (r7 != 0) goto L_0x004c
            goto L_0x0011
        L_0x004c:
            java.lang.String r7 = "\\s+"
            java.lang.String[] r7 = r4.split(r7)     // Catch:{ all -> 0x00b3 }
            int r8 = r7.length     // Catch:{ all -> 0x00b3 }
            r9 = 3
            if (r8 != r9) goto L_0x0081
            r6 = r7[r6]     // Catch:{ all -> 0x00b3 }
            java.util.regex.Pattern r6 = java.util.regex.Pattern.compile(r6)     // Catch:{ all -> 0x00b3 }
            r8 = 1
            r8 = r7[r8]     // Catch:{ all -> 0x00b3 }
            java.lang.String r9 = "\\+"
            java.lang.String[] r8 = r8.split(r9)     // Catch:{ all -> 0x00b3 }
            r9 = 2
            r9 = r7[r9]     // Catch:{ all -> 0x00b3 }
            java.lang.String r10 = "true"
            boolean r9 = r9.equals(r10)     // Catch:{ all -> 0x00b3 }
            org.apache.commons.codec.language.bm.Lang$LangRule r10 = new org.apache.commons.codec.language.bm.Lang$LangRule     // Catch:{ all -> 0x00b3 }
            java.util.HashSet r11 = new java.util.HashSet     // Catch:{ all -> 0x00b3 }
            java.util.List r12 = java.util.Arrays.asList(r8)     // Catch:{ all -> 0x00b3 }
            r11.<init>(r12)     // Catch:{ all -> 0x00b3 }
            r12 = 0
            r10.<init>(r6, r11, r9)     // Catch:{ all -> 0x00b3 }
            r0.add(r10)     // Catch:{ all -> 0x00b3 }
        L_0x0080:
            goto L_0x0011
        L_0x0081:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00b3 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b3 }
            r8.<init>()     // Catch:{ all -> 0x00b3 }
            java.lang.String r9 = "Malformed line '"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00b3 }
            java.lang.StringBuilder r8 = r8.append(r3)     // Catch:{ all -> 0x00b3 }
            java.lang.String r9 = "' in language resource '"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00b3 }
            java.lang.StringBuilder r8 = r8.append(r13)     // Catch:{ all -> 0x00b3 }
            java.lang.String r9 = "'"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x00b3 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00b3 }
            r6.<init>(r8)     // Catch:{ all -> 0x00b3 }
            throw r6     // Catch:{ all -> 0x00b3 }
        L_0x00aa:
            r1.close()
            org.apache.commons.codec.language.bm.Lang r1 = new org.apache.commons.codec.language.bm.Lang
            r1.<init>(r0, r14)
            return r1
        L_0x00b3:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x00b5 }
        L_0x00b5:
            r3 = move-exception
            if (r2 == 0) goto L_0x00c1
            r1.close()     // Catch:{ all -> 0x00bc }
            goto L_0x00c4
        L_0x00bc:
            r4 = move-exception
            r2.addSuppressed(r4)
            goto L_0x00c4
        L_0x00c1:
            r1.close()
        L_0x00c4:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.p008bm.Lang.loadFromResource(java.lang.String, org.apache.commons.codec.language.bm.Languages):org.apache.commons.codec.language.bm.Lang");
    }

    private Lang(List<LangRule> rules2, Languages languages2) {
        this.rules = Collections.unmodifiableList(rules2);
        this.languages = languages2;
    }

    public String guessLanguage(String text) {
        Languages.LanguageSet ls = guessLanguages(text);
        return ls.isSingleton() ? ls.getAny() : Languages.ANY;
    }

    public Languages.LanguageSet guessLanguages(String input) {
        String text = input.toLowerCase(Locale.ENGLISH);
        Set<String> langs = new HashSet<>(this.languages.getLanguages());
        for (LangRule rule : this.rules) {
            if (rule.matches(text)) {
                if (rule.acceptOnMatch) {
                    langs.retainAll(rule.languages);
                } else {
                    langs.removeAll(rule.languages);
                }
            }
        }
        Languages.LanguageSet ls = Languages.LanguageSet.from(langs);
        return ls.equals(Languages.NO_LANGUAGES) ? Languages.ANY_LANGUAGE : ls;
    }
}
