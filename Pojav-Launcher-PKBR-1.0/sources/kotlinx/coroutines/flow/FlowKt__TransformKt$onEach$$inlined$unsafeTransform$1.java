package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;

@Metadata(mo11814d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, mo11815d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 implements Flow<T> {
    final /* synthetic */ Function2 $action$inlined;
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$action$inlined = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d1 = collector;
        Continuation continuation = $completion;
        final Function2 function2 = this.$action$inlined;
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<T>() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0033  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x003f  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0075 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0076  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                /*
                    r9 = this;
                    boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C08092.C08101
                    if (r0 == 0) goto L_0x0014
                    r0 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C08092.C08101) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r11 = r0.label
                    int r11 = r11 - r2
                    r0.label = r11
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r9, r11)
                L_0x0019:
                    r11 = r0
                    java.lang.Object r0 = r11.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r11.label
                    switch(r2) {
                        case 0: goto L_0x003f;
                        case 1: goto L_0x0033;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r11)
                    throw r10
                L_0x002d:
                    r10 = 0
                    r1 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0077
                L_0x0033:
                    r10 = 0
                    r2 = 0
                    java.lang.Object r3 = r11.L$1
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    java.lang.Object r4 = r11.L$0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0067
                L_0x003f:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r9
                    r3 = 0
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r11
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r7
                    r11.L$0 = r10
                    r11.L$1 = r4
                    r7 = 1
                    r11.label = r7
                    r7 = 6
                    kotlin.jvm.internal.InlineMarker.mark((int) r7)
                    java.lang.Object r6 = r6.invoke(r10, r11)
                    r7 = 7
                    kotlin.jvm.internal.InlineMarker.mark((int) r7)
                    if (r6 != r1) goto L_0x0062
                    return r1
                L_0x0062:
                    r2 = r5
                    r8 = r4
                    r4 = r10
                    r10 = r3
                    r3 = r8
                L_0x0067:
                    r5 = 0
                    r11.L$0 = r5
                    r11.L$1 = r5
                    r5 = 2
                    r11.label = r5
                    java.lang.Object r3 = r3.emit(r4, r11)
                    if (r3 != r1) goto L_0x0076
                    return r1
                L_0x0076:
                    r1 = r2
                L_0x0077:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1.C08092.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }
        }, $completion);
        if (collect == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return collect;
        }
        return Unit.INSTANCE;
    }
}
