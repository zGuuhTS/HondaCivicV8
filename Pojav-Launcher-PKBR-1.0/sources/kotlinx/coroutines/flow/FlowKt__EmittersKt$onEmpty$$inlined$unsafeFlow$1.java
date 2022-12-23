package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(mo11814d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function2 $action$inlined;
    final /* synthetic */ Flow $this_onEmpty$inlined;

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r12, kotlin.coroutines.Continuation<? super kotlin.Unit> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.C07671
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.C07671) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1
            r0.<init>(r11, r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x004c;
                case 1: goto L_0x003a;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002d:
            r12 = 0
            java.lang.Object r1 = r13.L$0
            kotlinx.coroutines.flow.internal.SafeCollector r1 = (kotlinx.coroutines.flow.internal.SafeCollector) r1
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0037 }
            goto L_0x00a5
        L_0x0037:
            r2 = move-exception
            goto L_0x00ad
        L_0x003a:
            r12 = 0
            r2 = 0
            java.lang.Object r3 = r13.L$2
            kotlin.jvm.internal.Ref$BooleanRef r3 = (kotlin.jvm.internal.Ref.BooleanRef) r3
            java.lang.Object r4 = r13.L$1
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            java.lang.Object r5 = r13.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1 r5 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0079
        L_0x004c:
            kotlin.ResultKt.throwOnFailure(r0)
            r5 = r11
            r2 = r13
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r4 = r12
            r12 = 0
            kotlin.jvm.internal.Ref$BooleanRef r2 = new kotlin.jvm.internal.Ref$BooleanRef
            r2.<init>()
            r3 = r2
            r2 = 1
            r3.element = r2
            kotlinx.coroutines.flow.Flow r6 = r5.$this_onEmpty$inlined
            r7 = r13
            r8 = 0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$lambda-5$$inlined$collect$1 r9 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$lambda-5$$inlined$collect$1
            r9.<init>(r3, r4)
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            r13.L$0 = r5
            r13.L$1 = r4
            r13.L$2 = r3
            r13.label = r2
            java.lang.Object r2 = r6.collect(r9, r7)
            if (r2 != r1) goto L_0x0078
            return r1
        L_0x0078:
            r2 = r8
        L_0x0079:
            boolean r2 = r3.element
            if (r2 == 0) goto L_0x00b1
            kotlinx.coroutines.flow.internal.SafeCollector r2 = new kotlinx.coroutines.flow.internal.SafeCollector
            r3 = 0
            kotlin.coroutines.CoroutineContext r6 = r13.getContext()
            r2.<init>(r4, r6)
            kotlin.jvm.functions.Function2 r3 = r5.$action$inlined     // Catch:{ all -> 0x00a9 }
            r13.L$0 = r2     // Catch:{ all -> 0x00a9 }
            r4 = 0
            r13.L$1 = r4     // Catch:{ all -> 0x00a9 }
            r13.L$2 = r4     // Catch:{ all -> 0x00a9 }
            r4 = 2
            r13.label = r4     // Catch:{ all -> 0x00a9 }
            r4 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r4)     // Catch:{ all -> 0x00a9 }
            java.lang.Object r3 = r3.invoke(r2, r13)     // Catch:{ all -> 0x00a9 }
            r4 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r4)     // Catch:{ all -> 0x00a9 }
            if (r3 != r1) goto L_0x00a4
            return r1
        L_0x00a4:
            r1 = r2
        L_0x00a5:
            r1.releaseIntercepted()
            goto L_0x00b1
        L_0x00a9:
            r1 = move-exception
            r10 = r2
            r2 = r1
            r1 = r10
        L_0x00ad:
            r1.releaseIntercepted()
            throw r2
        L_0x00b1:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1(Flow flow, Function2 function2) {
        this.$this_onEmpty$inlined = flow;
        this.$action$inlined = function2;
    }
}
