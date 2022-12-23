package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", mo12530f = "Deprecated.kt", mo12531i = {0, 0, 0}, mo12532l = {487}, mo12533m = "indexOf", mo12534n = {"element", "index", "$this$consume$iv$iv"}, mo12535s = {"L$0", "L$1", "L$2"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$indexOf$1<E> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;

    ChannelsKt__DeprecatedKt$indexOf$1(Continuation<? super ChannelsKt__DeprecatedKt$indexOf$1> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ChannelsKt__DeprecatedKt.indexOf((ReceiveChannel) null, (Object) null, this);
    }
}
