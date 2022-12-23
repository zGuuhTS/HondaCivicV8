package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo11814d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, mo11815d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.FlowKt__ChannelsKt$broadcastIn$1", mo12530f = "Channels.kt", mo12531i = {}, mo12532l = {233}, mo12533m = "invokeSuspend", mo12534n = {}, mo12535s = {})
/* compiled from: Channels.kt */
final class FlowKt__ChannelsKt$broadcastIn$1 extends SuspendLambda implements Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T> $this_broadcastIn;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__ChannelsKt$broadcastIn$1(Flow<? extends T> flow, Continuation<? super FlowKt__ChannelsKt$broadcastIn$1> continuation) {
        super(2, continuation);
        this.$this_broadcastIn = flow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__ChannelsKt$broadcastIn$1 flowKt__ChannelsKt$broadcastIn$1 = new FlowKt__ChannelsKt$broadcastIn$1(this.$this_broadcastIn, continuation);
        flowKt__ChannelsKt$broadcastIn$1.L$0 = obj;
        return flowKt__ChannelsKt$broadcastIn$1;
    }

    public final Object invoke(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation) {
        return ((FlowKt__ChannelsKt$broadcastIn$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                if (this.$this_broadcastIn.collect(new C0762x739755bf((ProducerScope) this.L$0), this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
