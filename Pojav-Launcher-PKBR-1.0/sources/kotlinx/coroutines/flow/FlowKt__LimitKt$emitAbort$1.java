package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.FlowKt__LimitKt", mo12530f = "Limit.kt", mo12531i = {0}, mo12532l = {73}, mo12533m = "emitAbort$FlowKt__LimitKt", mo12534n = {"$this$emitAbort"}, mo12535s = {"L$0"})
/* compiled from: Limit.kt */
final class FlowKt__LimitKt$emitAbort$1<T> extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    FlowKt__LimitKt$emitAbort$1(Continuation<? super FlowKt__LimitKt$emitAbort$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return FlowKt__LimitKt.emitAbort$FlowKt__LimitKt((FlowCollector) null, null, this);
    }
}
