package androidx.lifecycle;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b¸\u0006\u0000"}, mo11815d2 = {"androidx/lifecycle/WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1", "Landroidx/lifecycle/LifecycleEventObserver;", "onStateChanged", "", "source", "Landroidx/lifecycle/LifecycleOwner;", "event", "Landroidx/lifecycle/Lifecycle$Event;", "lifecycle-runtime-ktx_release"}, mo11816k = 1, mo11817mv = {1, 4, 1})
/* renamed from: androidx.lifecycle.WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$$inlined$suspendCancellableCoroutine$lambda$1 */
/* compiled from: WithLifecycleState.kt */
public final class C0482x3029b39e implements LifecycleEventObserver {
    final /* synthetic */ Function0 $block$inlined;
    final /* synthetic */ CancellableContinuation $co;
    final /* synthetic */ boolean $dispatchNeeded$inlined;
    final /* synthetic */ CoroutineDispatcher $lifecycleDispatcher$inlined;
    final /* synthetic */ Lifecycle.State $state$inlined;
    final /* synthetic */ Lifecycle $this_suspendWithStateAtLeastUnchecked$inlined;

    C0482x3029b39e(CancellableContinuation $captured_local_variable$1, Lifecycle lifecycle, Lifecycle.State state, Function0 function0, boolean z, CoroutineDispatcher coroutineDispatcher) {
        this.$co = $captured_local_variable$1;
        this.$this_suspendWithStateAtLeastUnchecked$inlined = lifecycle;
        this.$state$inlined = state;
        this.$block$inlined = function0;
        this.$dispatchNeeded$inlined = z;
        this.$lifecycleDispatcher$inlined = coroutineDispatcher;
    }

    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        Object obj;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(event, NotificationCompat.CATEGORY_EVENT);
        if (event == Lifecycle.Event.upTo(this.$state$inlined)) {
            this.$this_suspendWithStateAtLeastUnchecked$inlined.removeObserver(this);
            CancellableContinuation cancellableContinuation = this.$co;
            Function0 function0 = this.$block$inlined;
            try {
                Result.Companion companion = Result.Companion;
                obj = Result.m64constructorimpl(function0.invoke());
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                obj = Result.m64constructorimpl(ResultKt.createFailure(th));
            }
            cancellableContinuation.resumeWith(obj);
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            this.$this_suspendWithStateAtLeastUnchecked$inlined.removeObserver(this);
            Result.Companion companion3 = Result.Companion;
            this.$co.resumeWith(Result.m64constructorimpl(ResultKt.createFailure(new LifecycleDestroyedException())));
        }
    }
}
