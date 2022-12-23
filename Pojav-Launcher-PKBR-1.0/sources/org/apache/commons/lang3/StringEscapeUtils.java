package org.apache.commons.lang3;

import net.kdt.pojavlaunch.AWTInputEvent;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityEscaper;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.apache.commons.lang3.text.translate.UnicodeUnpairedSurrogateRemover;
import top.defaults.checkerboarddrawable.BuildConfig;

public class StringEscapeUtils {
    public static final CharSequenceTranslator ESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()));
    public static final CharSequenceTranslator ESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE()));
    public static final CharSequenceTranslator ESCAPE_XML10 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.APOS_ESCAPE()), new LookupTranslator(new String[]{"\u0000", BuildConfig.FLAVOR}, new String[]{"\u0001", BuildConfig.FLAVOR}, new String[]{"\u0002", BuildConfig.FLAVOR}, new String[]{"\u0003", BuildConfig.FLAVOR}, new String[]{"\u0004", BuildConfig.FLAVOR}, new String[]{"\u0005", BuildConfig.FLAVOR}, new String[]{"\u0006", BuildConfig.FLAVOR}, new String[]{"\u0007", BuildConfig.FLAVOR}, new String[]{"\b", BuildConfig.FLAVOR}, new String[]{"\u000b", BuildConfig.FLAVOR}, new String[]{"\f", BuildConfig.FLAVOR}, new String[]{"\u000e", BuildConfig.FLAVOR}, new String[]{"\u000f", BuildConfig.FLAVOR}, new String[]{"\u0010", BuildConfig.FLAVOR}, new String[]{"\u0011", BuildConfig.FLAVOR}, new String[]{"\u0012", BuildConfig.FLAVOR}, new String[]{"\u0013", BuildConfig.FLAVOR}, new String[]{"\u0014", BuildConfig.FLAVOR}, new String[]{"\u0015", BuildConfig.FLAVOR}, new String[]{"\u0016", BuildConfig.FLAVOR}, new String[]{"\u0017", BuildConfig.FLAVOR}, new String[]{"\u0018", BuildConfig.FLAVOR}, new String[]{"\u0019", BuildConfig.FLAVOR}, new String[]{"\u001a", BuildConfig.FLAVOR}, new String[]{"\u001b", BuildConfig.FLAVOR}, new String[]{"\u001c", BuildConfig.FLAVOR}, new String[]{"\u001d", BuildConfig.FLAVOR}, new String[]{"\u001e", BuildConfig.FLAVOR}, new String[]{"\u001f", BuildConfig.FLAVOR}, new String[]{"￾", BuildConfig.FLAVOR}, new String[]{"￿", BuildConfig.FLAVOR}), NumericEntityEscaper.between(127, AWTInputEvent.VK_DEAD_MACRON), NumericEntityEscaper.between(AWTInputEvent.VK_DEAD_ABOVEDOT, 159), new UnicodeUnpairedSurrogateRemover());
    public static final CharSequenceTranslator ESCAPE_XML11 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.APOS_ESCAPE()), new LookupTranslator(new String[]{"\u0000", BuildConfig.FLAVOR}, new String[]{"\u000b", "&#11;"}, new String[]{"\f", "&#12;"}, new String[]{"￾", BuildConfig.FLAVOR}, new String[]{"￿", BuildConfig.FLAVOR}), NumericEntityEscaper.between(1, 8), NumericEntityEscaper.between(14, 31), NumericEntityEscaper.between(127, AWTInputEvent.VK_DEAD_MACRON), NumericEntityEscaper.between(AWTInputEvent.VK_DEAD_ABOVEDOT, 159), new UnicodeUnpairedSurrogateRemover());
    public static final CharSequenceTranslator UNESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
    public static final CharSequenceTranslator UNESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));
    public static final CharSequenceTranslator UNESCAPE_XML = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.APOS_UNESCAPE()), new NumericEntityUnescaper(new NumericEntityUnescaper.OPTION[0]));

    public static String escapeHtml3(String str) {
        return str == null ? BuildConfig.FLAVOR : ESCAPE_HTML3.translate(str);
    }

    public static String escapeHtml4(String str) {
        return str == null ? BuildConfig.FLAVOR : ESCAPE_HTML4.translate(str);
    }

    public static String escapeXml10(String str) {
        return ESCAPE_XML10.translate(str);
    }

    public static String escapeXml11(String str) {
        return ESCAPE_XML11.translate(str);
    }

    public static String unescapeHtml3(String str) {
        return UNESCAPE_HTML3.translate(str);
    }

    public static String unescapeHtml4(String str) {
        return UNESCAPE_HTML4.translate(str);
    }

    public static String unescapeXml(String str) {
        return UNESCAPE_XML.translate(str);
    }
}
