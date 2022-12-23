package org.apache.commons.codec.language.p008bm;

/* renamed from: org.apache.commons.codec.language.bm.RuleType */
public enum RuleType {
    APPROX("approx"),
    EXACT("exact"),
    RULES("rules");
    
    private final String name;

    private RuleType(String name2) {
        this.name = name2;
    }

    public String getName() {
        return this.name;
    }
}
