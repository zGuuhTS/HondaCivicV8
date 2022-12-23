package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Collect.kt */
public final class FlowKt__CountKt$count$$inlined$collect$2 implements FlowCollector<T> {
    final /* synthetic */ Ref.IntRef $i$inlined;
    final /* synthetic */ Function2 $predicate$inlined;

    public FlowKt__CountKt$count$$inlined$collect$2(Function2 function2, Ref.IntRef intRef) {
        this.$predicate$inlined = function2;
        this.$i$inlined = intRef;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2.C07631
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2.C07631) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2$1
            r0.<init>(r7, r9)
        L_0x0019:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r9.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002e:
            r8 = 0
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2 r1 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2) r1
            kotlin.ResultKt.throwOnFailure(r0)
            r5 = r0
            goto L_0x0057
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r7
            r4 = r9
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r4 = 0
            kotlin.jvm.functions.Function2 r5 = r2.$predicate$inlined
            r9.L$0 = r2
            r9.label = r3
            r6 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r6)
            java.lang.Object r5 = r5.invoke(r8, r9)
            r6 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r6)
            if (r5 != r1) goto L_0x0055
            return r1
        L_0x0055:
            r1 = r2
            r8 = r4
        L_0x0057:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r2 = r5.booleanValue()
            if (r2 == 0) goto L_0x006a
            kotlin.jvm.internal.Ref$IntRef r2 = r1.$i$inlined
            int r4 = r2.element
            int r4 = r4 + r3
            r2.element = r4
            kotlin.jvm.internal.Ref$IntRef r2 = r1.$i$inlined
            int r2 = r2.element
        L_0x006a:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__CountKt$count$$inlined$collect$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
