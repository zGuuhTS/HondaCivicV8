package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.channels.SendChannel;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo11814d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u0019\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\n"}, mo11815d2 = {"Lkotlinx/coroutines/flow/internal/SendingCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(Lkotlinx/coroutines/channels/SendChannel;)V", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: SendingCollector.kt */
public final class SendingCollector<T> implements FlowCollector<T> {
    private final SendChannel<T> channel;

    public SendingCollector(SendChannel<? super T> channel2) {
        this.channel = channel2;
    }

    public Object emit(T value, Continuation<? super Unit> $completion) {
        Object send = this.channel.send(value, $completion);
        return send == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? send : Unit.INSTANCE;
    }
}
