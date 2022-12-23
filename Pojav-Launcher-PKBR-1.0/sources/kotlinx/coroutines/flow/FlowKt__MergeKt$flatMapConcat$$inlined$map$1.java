package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;

@Metadata(mo11814d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\t"}, mo11815d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__MergeKt$flatMapConcat$$inlined$map$1 implements Flow<Flow<? extends R>> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;
    final /* synthetic */ Function2 $transform$inlined$1;

    public FlowKt__MergeKt$flatMapConcat$$inlined$map$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$transform$inlined$1 = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d1 = collector;
        Continuation continuation = $completion;
        final Function2 function2 = this.$transform$inlined$1;
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<T>() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0033  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0040  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0063 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0064  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1.C07812.C07821
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1.C07812.C07821) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r10 = r0.label
                    int r10 = r10 - r2
                    r0.label = r10
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0040;
                        case 1: goto L_0x0033;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r10)
                    throw r9
                L_0x002d:
                    r9 = 0
                    r1 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0066
                L_0x0033:
                    r9 = 0
                    r2 = 0
                    java.lang.Object r3 = r10.L$0
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r3
                    r3 = r9
                    r9 = r0
                    goto L_0x0057
                L_0x0040:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r8
                    r3 = 0
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r7
                    r10.L$0 = r4
                    r7 = 1
                    r10.label = r7
                    java.lang.Object r9 = r6.invoke(r9, r10)
                    if (r9 != r1) goto L_0x0056
                    return r1
                L_0x0056:
                    r2 = r5
                L_0x0057:
                    r5 = 0
                    r10.L$0 = r5
                    r5 = 2
                    r10.label = r5
                    java.lang.Object r9 = r4.emit(r9, r10)
                    if (r9 != r1) goto L_0x0064
                    return r1
                L_0x0064:
                    r1 = r2
                    r9 = r3
                L_0x0066:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1.C07812.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, $completion);
        if (collect == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
