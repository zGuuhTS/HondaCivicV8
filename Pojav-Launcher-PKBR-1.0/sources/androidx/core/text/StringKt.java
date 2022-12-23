package androidx.core.text;

import android.text.TextUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\b¨\u0006\u0002"}, mo11815d2 = {"htmlEncode", "", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: String.kt */
public final class StringKt {
    public static final String htmlEncode(String $this$htmlEncode) {
        Intrinsics.checkParameterIsNotNull($this$htmlEncode, "$this$htmlEncode");
        String htmlEncode = TextUtils.htmlEncode($this$htmlEncode);
        Intrinsics.checkExpressionValueIsNotNull(htmlEncode, "TextUtils.htmlEncode(this)");
        return htmlEncode;
    }
}
