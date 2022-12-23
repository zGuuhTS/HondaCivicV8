package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Ref;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$take$lambda-7$$inlined$collect$1  reason: invalid class name */
/* compiled from: Collect.kt */
public final class FlowKt__LimitKt$take$lambda7$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ Ref.IntRef $consumed$inlined;
    final /* synthetic */ int $count$inlined;
    final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

    public FlowKt__LimitKt$take$lambda7$$inlined$collect$1(Ref.IntRef intRef, int i, FlowCollector flowCollector) {
        this.$consumed$inlined = intRef;
        this.$count$inlined = i;
        this.$this_unsafeFlow$inlined = flowCollector;
    }

    public Object emit(T value, Continuation<? super Unit> $completion) {
        Object value2 = value;
        Continuation<? super Unit> continuation = $completion;
        this.$consumed$inlined.element++;
        if (this.$consumed$inlined.element < this.$count$inlined) {
            Object emit = this.$this_unsafeFlow$inlined.emit(value2, $completion);
            if (emit == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return emit;
            }
        } else {
            Object access$emitAbort$FlowKt__LimitKt = FlowKt__LimitKt.emitAbort$FlowKt__LimitKt(this.$this_unsafeFlow$inlined, value2, $completion);
            if (access$emitAbort$FlowKt__LimitKt == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return access$emitAbort$FlowKt__LimitKt;
            }
        }
        return Unit.INSTANCE;
    }
}
