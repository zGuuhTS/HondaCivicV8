package org.apache.commons.codec.language.p008bm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* renamed from: org.apache.commons.codec.language.bm.Languages */
public class Languages {
    public static final String ANY = "any";
    public static final LanguageSet ANY_LANGUAGE = new LanguageSet() {
        public boolean contains(String language) {
            return true;
        }

        public String getAny() {
            throw new NoSuchElementException("Can't fetch any language from the any language set.");
        }

        public boolean isEmpty() {
            return false;
        }

        public boolean isSingleton() {
            return false;
        }

        public LanguageSet restrictTo(LanguageSet other) {
            return other;
        }

        public LanguageSet merge(LanguageSet other) {
            return other;
        }

        public String toString() {
            return "ANY_LANGUAGE";
        }
    };
    private static final Map<NameType, Languages> LANGUAGES = new EnumMap(NameType.class);
    public static final LanguageSet NO_LANGUAGES = new LanguageSet() {
        public boolean contains(String language) {
            return false;
        }

        public String getAny() {
            throw new NoSuchElementException("Can't fetch any language from the empty language set.");
        }

        public boolean isEmpty() {
            return true;
        }

        public boolean isSingleton() {
            return false;
        }

        public LanguageSet restrictTo(LanguageSet other) {
            return this;
        }

        public LanguageSet merge(LanguageSet other) {
            return other;
        }

        public String toString() {
            return "NO_LANGUAGES";
        }
    };
    private final Set<String> languages;

    /* renamed from: org.apache.commons.codec.language.bm.Languages$LanguageSet */
    public static abstract class LanguageSet {
        public abstract boolean contains(String str);

        public abstract String getAny();

        public abstract boolean isEmpty();

        public abstract boolean isSingleton();

        /* access modifiers changed from: package-private */
        public abstract LanguageSet merge(LanguageSet languageSet);

        public abstract LanguageSet restrictTo(LanguageSet languageSet);

        public static LanguageSet from(Set<String> langs) {
            return langs.isEmpty() ? Languages.NO_LANGUAGES : new SomeLanguages(langs);
        }
    }

    /* renamed from: org.apache.commons.codec.language.bm.Languages$SomeLanguages */
    public static final class SomeLanguages extends LanguageSet {
        private final Set<String> languages;

        private SomeLanguages(Set<String> languages2) {
            this.languages = Collections.unmodifiableSet(languages2);
        }

        public boolean contains(String language) {
            return this.languages.contains(language);
        }

        public String getAny() {
            return this.languages.iterator().next();
        }

        public Set<String> getLanguages() {
            return this.languages;
        }

        public boolean isEmpty() {
            return this.languages.isEmpty();
        }

        public boolean isSingleton() {
            return this.languages.size() == 1;
        }

        public LanguageSet restrictTo(LanguageSet other) {
            if (other == Languages.NO_LANGUAGES) {
                return other;
            }
            if (other == Languages.ANY_LANGUAGE) {
                return this;
            }
            SomeLanguages sl = (SomeLanguages) other;
            Set<String> ls = new HashSet<>(Math.min(this.languages.size(), sl.languages.size()));
            for (String lang : this.languages) {
                if (sl.languages.contains(lang)) {
                    ls.add(lang);
                }
            }
            return from(ls);
        }

        public LanguageSet merge(LanguageSet other) {
            if (other == Languages.NO_LANGUAGES) {
                return this;
            }
            if (other == Languages.ANY_LANGUAGE) {
                return other;
            }
            Set<String> ls = new HashSet<>(this.languages);
            for (String lang : ((SomeLanguages) other).languages) {
                ls.add(lang);
            }
            return from(ls);
        }

        public String toString() {
            return "Languages(" + this.languages.toString() + ")";
        }
    }

    static {
        for (NameType s : NameType.values()) {
            LANGUAGES.put(s, getInstance(langResourceName(s)));
        }
    }

    public static Languages getInstance(NameType nameType) {
        return LANGUAGES.get(nameType);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004e, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004f, code lost:
        if (r2 != null) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        r2.addSuppressed(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005a, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005d, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.apache.commons.codec.language.p008bm.Languages getInstance(java.lang.String r5) {
        /*
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Scanner r1 = new java.util.Scanner
            java.io.InputStream r2 = org.apache.commons.codec.Resources.getInputStream(r5)
            java.lang.String r3 = "UTF-8"
            r1.<init>(r2, r3)
            r2 = 0
        L_0x0011:
            boolean r3 = r1.hasNextLine()     // Catch:{ all -> 0x004c }
            if (r3 == 0) goto L_0x003f
            java.lang.String r3 = r1.nextLine()     // Catch:{ all -> 0x004c }
            java.lang.String r3 = r3.trim()     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x002b
            java.lang.String r4 = "*/"
            boolean r4 = r3.endsWith(r4)     // Catch:{ all -> 0x004c }
            if (r4 == 0) goto L_0x003e
            r2 = 0
            goto L_0x003e
        L_0x002b:
            java.lang.String r4 = "/*"
            boolean r4 = r3.startsWith(r4)     // Catch:{ all -> 0x004c }
            if (r4 == 0) goto L_0x0035
            r2 = 1
            goto L_0x003e
        L_0x0035:
            int r4 = r3.length()     // Catch:{ all -> 0x004c }
            if (r4 <= 0) goto L_0x003e
            r0.add(r3)     // Catch:{ all -> 0x004c }
        L_0x003e:
            goto L_0x0011
        L_0x003f:
            org.apache.commons.codec.language.bm.Languages r3 = new org.apache.commons.codec.language.bm.Languages     // Catch:{ all -> 0x004c }
            java.util.Set r4 = java.util.Collections.unmodifiableSet(r0)     // Catch:{ all -> 0x004c }
            r3.<init>(r4)     // Catch:{ all -> 0x004c }
            r1.close()
            return r3
        L_0x004c:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x004e }
        L_0x004e:
            r3 = move-exception
            if (r2 == 0) goto L_0x005a
            r1.close()     // Catch:{ all -> 0x0055 }
            goto L_0x005d
        L_0x0055:
            r4 = move-exception
            r2.addSuppressed(r4)
            goto L_0x005d
        L_0x005a:
            r1.close()
        L_0x005d:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.codec.language.p008bm.Languages.getInstance(java.lang.String):org.apache.commons.codec.language.bm.Languages");
    }

    private static String langResourceName(NameType nameType) {
        return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", new Object[]{nameType.getName()});
    }

    private Languages(Set<String> languages2) {
        this.languages = languages2;
    }

    public Set<String> getLanguages() {
        return this.languages;
    }
}
