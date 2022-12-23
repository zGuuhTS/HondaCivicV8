package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* renamed from: kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda-12$$inlined$collect$1  reason: invalid class name */
/* compiled from: Collect.kt */
public final class FlowKt__TransformKt$runningReduce$lambda12$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ Ref.ObjectRef $accumulator$inlined;
    final /* synthetic */ Function3 $operation$inlined;
    final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

    public FlowKt__TransformKt$runningReduce$lambda12$$inlined$collect$1(Ref.ObjectRef objectRef, Function3 function3, FlowCollector flowCollector) {
        this.$accumulator$inlined = objectRef;
        this.$operation$inlined = function3;
        this.$this_unsafeFlow$inlined = flowCollector;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=T, code=java.lang.Object, for r9v0, types: [T, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0086 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda12$$inlined$collect$1.C08131
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda-12$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda12$$inlined$collect$1.C08131) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda-12$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda-12$$inlined$collect$1$1
            r0.<init>(r8, r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x0042;
                case 1: goto L_0x0032;
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
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0088
        L_0x0032:
            r9 = 0
            java.lang.Object r2 = r10.L$1
            kotlin.jvm.internal.Ref$ObjectRef r2 = (kotlin.jvm.internal.Ref.ObjectRef) r2
            java.lang.Object r3 = r10.L$0
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda-12$$inlined$collect$1 r3 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda12$$inlined$collect$1) r3
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r2
            r2 = r9
            r9 = r0
            goto L_0x0070
        L_0x0042:
            kotlin.ResultKt.throwOnFailure(r0)
            r3 = r8
            r2 = r10
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r2 = 0
            kotlin.jvm.internal.Ref$ObjectRef r4 = r3.$accumulator$inlined
            T r5 = r4.element
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            if (r5 != r6) goto L_0x0053
            goto L_0x0070
        L_0x0053:
            kotlin.jvm.functions.Function3 r5 = r3.$operation$inlined
            kotlin.jvm.internal.Ref$ObjectRef r6 = r3.$accumulator$inlined
            T r6 = r6.element
            r10.L$0 = r3
            r10.L$1 = r4
            r7 = 1
            r10.label = r7
            r7 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r7)
            java.lang.Object r5 = r5.invoke(r6, r9, r10)
            r6 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r6)
            if (r5 != r1) goto L_0x006f
            return r1
        L_0x006f:
            r9 = r5
        L_0x0070:
            r4.element = r9
            kotlinx.coroutines.flow.FlowCollector r9 = r3.$this_unsafeFlow$inlined
            kotlin.jvm.internal.Ref$ObjectRef r4 = r3.$accumulator$inlined
            T r4 = r4.element
            r5 = 0
            r10.L$0 = r5
            r10.L$1 = r5
            r5 = 2
            r10.label = r5
            java.lang.Object r9 = r9.emit(r4, r10)
            if (r9 != r1) goto L_0x0087
            return r1
        L_0x0087:
            r9 = r2
        L_0x0088:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$lambda12$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
