package androidx.activity.result;

import android.content.Intent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\u000f\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u0002H\u0002¨\u0006\u0005"}, mo11815d2 = {"component1", "", "Landroidx/activity/result/ActivityResult;", "component2", "Landroid/content/Intent;", "activity-ktx_release"}, mo11816k = 2, mo11817mv = {1, 4, 1})
/* compiled from: ActivityResult.kt */
public final class ActivityResultKt {
    public static final int component1(ActivityResult $this$component1) {
        Intrinsics.checkNotNullParameter($this$component1, "$this$component1");
        return $this$component1.getResultCode();
    }

    public static final Intent component2(ActivityResult $this$component2) {
        Intrinsics.checkNotNullParameter($this$component2, "$this$component2");
        return $this$component2.getData();
    }
}
