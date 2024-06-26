package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;

public class AggregateTranslator extends CharSequenceTranslator {
    private final CharSequenceTranslator[] translators;

    public AggregateTranslator(CharSequenceTranslator... charSequenceTranslatorArr) {
        this.translators = charSequenceTranslatorArr != null ? (CharSequenceTranslator[]) charSequenceTranslatorArr.clone() : null;
    }

    public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        for (CharSequenceTranslator translate : this.translators) {
            int translate2 = translate.translate(charSequence, i, writer);
            if (translate2 != 0) {
                return translate2;
            }
        }
        return 0;
    }
}
