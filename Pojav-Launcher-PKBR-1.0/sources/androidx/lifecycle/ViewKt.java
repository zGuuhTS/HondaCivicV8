package androidx.lifecycle;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, mo11815d2 = {"findViewTreeLifecycleOwner", "Landroidx/lifecycle/LifecycleOwner;", "Landroid/view/View;", "lifecycle-runtime-ktx_release"}, mo11816k = 2, mo11817mv = {1, 4, 1})
/* compiled from: View.kt */
public final class ViewKt {
    public static final LifecycleOwner findViewTreeLifecycleOwner(View $this$findViewTreeLifecycleOwner) {
        Intrinsics.checkNotNullParameter($this$findViewTreeLifecycleOwner, "$this$findViewTreeLifecycleOwner");
        return ViewTreeLifecycleOwner.get($this$findViewTreeLifecycleOwner);
    }
}
