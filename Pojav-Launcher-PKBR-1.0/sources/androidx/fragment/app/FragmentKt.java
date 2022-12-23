package androidx.fragment.app;

import android.os.Bundle;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001a\u0010\u0006\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b\u001aJ\u0010\t\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u000426\u0010\n\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00010\u000b¨\u0006\u000f"}, mo11815d2 = {"clearFragmentResult", "", "Landroidx/fragment/app/Fragment;", "requestKey", "", "clearFragmentResultListener", "setFragmentResult", "result", "Landroid/os/Bundle;", "setFragmentResultListener", "listener", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "bundle", "fragment-ktx_release"}, mo11816k = 2, mo11817mv = {1, 4, 1})
/* compiled from: Fragment.kt */
public final class FragmentKt {
    public static final void setFragmentResult(Fragment $this$setFragmentResult, String requestKey, Bundle result) {
        Intrinsics.checkNotNullParameter($this$setFragmentResult, "$this$setFragmentResult");
        Intrinsics.checkNotNullParameter(requestKey, "requestKey");
        Intrinsics.checkNotNullParameter(result, "result");
        $this$setFragmentResult.getParentFragmentManager().setFragmentResult(requestKey, result);
    }

    public static final void clearFragmentResult(Fragment $this$clearFragmentResult, String requestKey) {
        Intrinsics.checkNotNullParameter($this$clearFragmentResult, "$this$clearFragmentResult");
        Intrinsics.checkNotNullParameter(requestKey, "requestKey");
        $this$clearFragmentResult.getParentFragmentManager().clearFragmentResult(requestKey);
    }

    public static final void setFragmentResultListener(Fragment $this$setFragmentResultListener, String requestKey, Function2<? super String, ? super Bundle, Unit> listener) {
        Intrinsics.checkNotNullParameter($this$setFragmentResultListener, "$this$setFragmentResultListener");
        Intrinsics.checkNotNullParameter(requestKey, "requestKey");
        Intrinsics.checkNotNullParameter(listener, "listener");
        $this$setFragmentResultListener.getParentFragmentManager().setFragmentResultListener(requestKey, $this$setFragmentResultListener, new FragmentKt$sam$androidx_fragment_app_FragmentResultListener$0(listener));
    }

    public static final void clearFragmentResultListener(Fragment $this$clearFragmentResultListener, String requestKey) {
        Intrinsics.checkNotNullParameter($this$clearFragmentResultListener, "$this$clearFragmentResultListener");
        Intrinsics.checkNotNullParameter(requestKey, "requestKey");
        $this$clearFragmentResultListener.getParentFragmentManager().clearFragmentResultListener(requestKey);
    }
}
