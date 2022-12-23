package kotlinx.coroutines.flow;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;
import kotlinx.coroutines.flow.internal.FusibleFlow;

@Metadata(mo11814d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b\u0004\u001a(\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a0\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b\u001a\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0006\u001a\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0006\u001a$\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0006\u0010\u0002\u001a\u00020\u0003\u001a[\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0006\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0006\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\t2#\u0010\u0013\u001a\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00100\u00060\u0014¢\u0006\u0002\b\u0015H\u0007¨\u0006\u0016"}, mo11815d2 = {"checkFlowContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "checkFlowContext$FlowKt__ContextKt", "buffer", "Lkotlinx/coroutines/flow/Flow;", "T", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "cancellable", "conflate", "flowOn", "flowWith", "R", "flowContext", "bufferSize", "builder", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "kotlinx-coroutines-core"}, mo11816k = 5, mo11817mv = {1, 5, 1}, mo11819xi = 48, mo11820xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Context.kt */
final /* synthetic */ class FlowKt__ContextKt {
    public static /* synthetic */ Flow buffer$default(Flow flow, int i, BufferOverflow bufferOverflow, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        if ((i2 & 2) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        return FlowKt.buffer(flow, i, bufferOverflow);
    }

    public static final <T> Flow<T> buffer(Flow<? extends T> $this$buffer, int capacity, BufferOverflow onBufferOverflow) {
        boolean z = false;
        if (capacity >= 0 || capacity == -2 || capacity == -1) {
            if (capacity != -1 || onBufferOverflow == BufferOverflow.SUSPEND) {
                z = true;
            }
            if (z) {
                int capacity2 = capacity;
                BufferOverflow onBufferOverflow2 = onBufferOverflow;
                if (capacity2 == -1) {
                    capacity2 = 0;
                    onBufferOverflow2 = BufferOverflow.DROP_OLDEST;
                }
                if ($this$buffer instanceof FusibleFlow) {
                    return FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) $this$buffer, (CoroutineContext) null, capacity2, onBufferOverflow2, 1, (Object) null);
                }
                return new ChannelFlowOperatorImpl<>($this$buffer, (CoroutineContext) null, capacity2, onBufferOverflow2, 2, (DefaultConstructorMarker) null);
            }
            throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
        }
        throw new IllegalArgumentException(Intrinsics.stringPlus("Buffer size should be non-negative, BUFFERED, or CONFLATED, but was ", Integer.valueOf(capacity)).toString());
    }

    public static /* synthetic */ Flow buffer$default(Flow flow, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        return buffer$default(flow, i, (BufferOverflow) null, 2, (Object) null);
    }

    public static final <T> Flow<T> conflate(Flow<? extends T> $this$conflate) {
        return buffer$default($this$conflate, -1, (BufferOverflow) null, 2, (Object) null);
    }

    public static final <T> Flow<T> flowOn(Flow<? extends T> $this$flowOn, CoroutineContext context) {
        checkFlowContext$FlowKt__ContextKt(context);
        if (Intrinsics.areEqual((Object) context, (Object) EmptyCoroutineContext.INSTANCE)) {
            return $this$flowOn;
        }
        if ($this$flowOn instanceof FusibleFlow) {
            return FusibleFlow.DefaultImpls.fuse$default((FusibleFlow) $this$flowOn, context, 0, (BufferOverflow) null, 6, (Object) null);
        }
        return new ChannelFlowOperatorImpl<>($this$flowOn, context, 0, (BufferOverflow) null, 12, (DefaultConstructorMarker) null);
    }

    public static final <T> Flow<T> cancellable(Flow<? extends T> $this$cancellable) {
        if ($this$cancellable instanceof CancellableFlow) {
            return $this$cancellable;
        }
        return new CancellableFlowImpl<>($this$cancellable);
    }

    public static /* synthetic */ Flow flowWith$default(Flow flow, CoroutineContext coroutineContext, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -2;
        }
        return FlowKt.flowWith(flow, coroutineContext, i, function1);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "flowWith is deprecated without replacement, please refer to its KDoc for an explanation")
    public static final <T, R> Flow<R> flowWith(Flow<? extends T> $this$flowWith, CoroutineContext flowContext, int bufferSize, Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> builder) {
        checkFlowContext$FlowKt__ContextKt(flowContext);
        return new FlowKt__ContextKt$flowWith$$inlined$unsafeFlow$1($this$flowWith, bufferSize, builder, flowContext);
    }

    private static final void checkFlowContext$FlowKt__ContextKt(CoroutineContext context) {
        if (!(context.get(Job.Key) == null)) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("Flow context cannot contain job in it. Had ", context).toString());
        }
    }
}
