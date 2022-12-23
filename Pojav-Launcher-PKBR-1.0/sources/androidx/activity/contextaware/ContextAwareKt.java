package androidx.activity.contextaware;

import android.content.Context;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00010\u0004HHø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007"}, mo11815d2 = {"withContextAvailable", "R", "Landroidx/activity/contextaware/ContextAware;", "onContextAvailable", "Lkotlin/Function1;", "Landroid/content/Context;", "(Landroidx/activity/contextaware/ContextAware;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "activity-ktx_release"}, mo11816k = 2, mo11817mv = {1, 4, 1})
/* compiled from: ContextAware.kt */
public final class ContextAwareKt {
    public static final <R> Object withContextAvailable(ContextAware $this$withContextAvailable, Function1<? super Context, ? extends R> onContextAvailable, Continuation<? super R> $completion) {
        Context availableContext = $this$withContextAvailable.peekAvailableContext();
        if (availableContext != null) {
            return onContextAvailable.invoke(availableContext);
        }
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation co = cancellable$iv;
        C0180x8a9e0a55 listener = new C0180x8a9e0a55(co, $this$withContextAvailable, onContextAvailable);
        $this$withContextAvailable.addOnContextAvailableListener(listener);
        co.invokeOnCancellation(new C0181x8a9e0a56(listener, $this$withContextAvailable, onContextAvailable));
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    private static final Object withContextAvailable$$forInline(ContextAware $this$withContextAvailable, Function1 onContextAvailable, Continuation uCont$iv) {
        Context availableContext = $this$withContextAvailable.peekAvailableContext();
        if (availableContext != null) {
            return onContextAvailable.invoke(availableContext);
        }
        InlineMarker.mark(0);
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted(uCont$iv), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation co = cancellable$iv;
        C0180x8a9e0a55 listener = new C0180x8a9e0a55(co, $this$withContextAvailable, onContextAvailable);
        $this$withContextAvailable.addOnContextAvailableListener(listener);
        co.invokeOnCancellation(new C0181x8a9e0a56(listener, $this$withContextAvailable, onContextAvailable));
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(uCont$iv);
        }
        InlineMarker.mark(1);
        return result;
    }
}
