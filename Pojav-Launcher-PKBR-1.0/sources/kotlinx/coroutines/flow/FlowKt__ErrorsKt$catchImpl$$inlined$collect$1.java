package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.internal.Ref;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Collect.kt */
public final class FlowKt__ErrorsKt$catchImpl$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ FlowCollector $collector$inlined;
    final /* synthetic */ Ref.ObjectRef $fromDownstream$inlined;

    public FlowKt__ErrorsKt$catchImpl$$inlined$collect$1(FlowCollector flowCollector, Ref.ObjectRef objectRef) {
        this.$collector$inlined = flowCollector;
        this.$fromDownstream$inlined = objectRef;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0054, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(T r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1.C07731
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1.C07731) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1$1
            r0.<init>(r6, r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002d:
            r7 = 0
            java.lang.Object r1 = r8.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1 r1 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1) r1
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0036 }
            goto L_0x0051
        L_0x0036:
            r2 = move-exception
            goto L_0x0059
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            r3 = r8
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r3 = 0
            kotlinx.coroutines.flow.FlowCollector r4 = r2.$collector$inlined     // Catch:{ all -> 0x0055 }
            r8.L$0 = r2     // Catch:{ all -> 0x0055 }
            r5 = 1
            r8.label = r5     // Catch:{ all -> 0x0055 }
            java.lang.Object r4 = r4.emit(r7, r8)     // Catch:{ all -> 0x0055 }
            if (r4 != r1) goto L_0x004f
            return r1
        L_0x004f:
            r1 = r2
            r7 = r3
        L_0x0051:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        L_0x0055:
            r7 = move-exception
            r1 = r2
            r2 = r7
            r7 = r3
        L_0x0059:
            kotlin.jvm.internal.Ref$ObjectRef r3 = r1.$fromDownstream$inlined
            r3.element = r2
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt$catchImpl$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
