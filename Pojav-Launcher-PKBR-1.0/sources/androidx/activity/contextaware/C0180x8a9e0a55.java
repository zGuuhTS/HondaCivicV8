package androidx.activity.contextaware;

import android.content.Context;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"androidx/activity/contextaware/ContextAwareKt$withContextAvailable$2$listener$1", "Landroidx/activity/contextaware/OnContextAvailableListener;", "onContextAvailable", "", "context", "Landroid/content/Context;", "activity-ktx_release"}, mo11816k = 1, mo11817mv = {1, 4, 1})
/* renamed from: androidx.activity.contextaware.ContextAwareKt$withContextAvailable$$inlined$suspendCancellableCoroutine$lambda$1 */
/* compiled from: ContextAware.kt */
public final class C0180x8a9e0a55 implements OnContextAvailableListener {
    final /* synthetic */ CancellableContinuation $co;
    final /* synthetic */ Function1 $onContextAvailable$inlined;
    final /* synthetic */ ContextAware $this_withContextAvailable$inlined;

    public C0180x8a9e0a55(CancellableContinuation $captured_local_variable$1, ContextAware contextAware, Function1 function1) {
        this.$co = $captured_local_variable$1;
        this.$this_withContextAvailable$inlined = contextAware;
        this.$onContextAvailable$inlined = function1;
    }

    public void onContextAvailable(Context context) {
        Object obj;
        Intrinsics.checkNotNullParameter(context, "context");
        CancellableContinuation cancellableContinuation = this.$co;
        try {
            Result.Companion companion = Result.Companion;
            C0180x8a9e0a55 contextAwareKt$withContextAvailable$$inlined$suspendCancellableCoroutine$lambda$1 = this;
            obj = Result.m64constructorimpl(this.$onContextAvailable$inlined.invoke(context));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m64constructorimpl(ResultKt.createFailure(th));
        }
        cancellableContinuation.resumeWith(obj);
    }
}
