package androidx.core.graphics.drawable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0003H\b¨\u0006\u0004"}, mo11815d2 = {"toDrawable", "Landroid/graphics/drawable/ColorDrawable;", "Landroid/graphics/Color;", "", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: ColorDrawable.kt */
public final class ColorDrawableKt {
    public static final ColorDrawable toDrawable(int $this$toDrawable) {
        return new ColorDrawable($this$toDrawable);
    }

    public static final ColorDrawable toDrawable(Color $this$toDrawable) {
        Intrinsics.checkParameterIsNotNull($this$toDrawable, "$this$toDrawable");
        return new ColorDrawable($this$toDrawable.toArgb());
    }
}
