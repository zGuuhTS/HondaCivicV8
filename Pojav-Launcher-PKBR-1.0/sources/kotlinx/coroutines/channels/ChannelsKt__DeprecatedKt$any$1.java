package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", mo12530f = "Deprecated.kt", mo12531i = {0}, mo12532l = {404}, mo12533m = "any", mo12534n = {"$this$consume$iv"}, mo12535s = {"L$0"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$any$1<E> extends ContinuationImpl {
    Object L$0;
    int label;
    /* synthetic */ Object result;

    ChannelsKt__DeprecatedKt$any$1(Continuation<? super ChannelsKt__DeprecatedKt$any$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ChannelsKt__DeprecatedKt.any((ReceiveChannel) null, this);
    }
}
