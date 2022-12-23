package kotlinx.coroutines.channels;

import java.util.Map;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", mo12530f = "Deprecated.kt", mo12531i = {0, 0}, mo12532l = {487}, mo12533m = "toMap", mo12534n = {"destination", "$this$consume$iv$iv"}, mo12535s = {"L$0", "L$1"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$toMap$2<K, V, M extends Map<? super K, ? super V>> extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;

    ChannelsKt__DeprecatedKt$toMap$2(Continuation<? super ChannelsKt__DeprecatedKt$toMap$2> continuation) {
        super(continuation);
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ChannelsKt.toMap((ReceiveChannel) null, null, this);
    }
}
