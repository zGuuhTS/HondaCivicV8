package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.CallbackFlowBuilder", mo12530f = "Builders.kt", mo12531i = {0}, mo12532l = {355}, mo12533m = "collectTo", mo12534n = {"scope"}, mo12535s = {"L$0"})
/* compiled from: Builders.kt */
final class CallbackFlowBuilder$collectTo$1 extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ CallbackFlowBuilder<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CallbackFlowBuilder$collectTo$1(CallbackFlowBuilder<T> callbackFlowBuilder, Continuation<? super CallbackFlowBuilder$collectTo$1> continuation) {
        super(continuation);
        this.this$0 = callbackFlowBuilder;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.collectTo((ProducerScope) null, this);
    }
}
