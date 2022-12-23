package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda-4$$inlined$collect$1  reason: invalid class name */
/* compiled from: Collect.kt */
public final class FlowKt__LimitKt$dropWhile$lambda4$$inlined$collect$1 implements FlowCollector<T> {
    final /* synthetic */ Ref.BooleanRef $matched$inlined;
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

    public FlowKt__LimitKt$dropWhile$lambda4$$inlined$collect$1(Ref.BooleanRef booleanRef, FlowCollector flowCollector, Function2 function2) {
        this.$matched$inlined = booleanRef;
        this.$this_unsafeFlow$inlined = flowCollector;
        this.$predicate$inlined = function2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0086  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda4$$inlined$collect$1.C07751
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda-4$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda4$$inlined$collect$1.C07751) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda-4$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda-4$$inlined$collect$1$1
            r0.<init>(r8, r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0048;
                case 1: goto L_0x0041;
                case 2: goto L_0x0035;
                case 3: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002f:
            r9 = 0
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x009a
        L_0x0035:
            r9 = 0
            java.lang.Object r2 = r10.L$1
            java.lang.Object r5 = r10.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda-4$$inlined$collect$1 r5 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda4$$inlined$collect$1) r5
            kotlin.ResultKt.throwOnFailure(r0)
            r6 = r0
            goto L_0x007e
        L_0x0041:
            r9 = r8
            r1 = r4
            r2 = 0
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x0065
        L_0x0048:
            kotlin.ResultKt.throwOnFailure(r0)
            r5 = r8
            r2 = r10
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r2 = r9
            r9 = 0
            kotlin.jvm.internal.Ref$BooleanRef r6 = r5.$matched$inlined
            boolean r6 = r6.element
            if (r6 == 0) goto L_0x0066
            kotlinx.coroutines.flow.FlowCollector r4 = r5.$this_unsafeFlow$inlined
            r10.label = r3
            java.lang.Object r3 = r4.emit(r2, r10)
            if (r3 != r1) goto L_0x0062
            return r1
        L_0x0062:
            r1 = r2
            r2 = r9
            r9 = r5
        L_0x0065:
            goto L_0x009a
        L_0x0066:
            kotlin.jvm.functions.Function2 r6 = r5.$predicate$inlined
            r10.L$0 = r5
            r10.L$1 = r2
            r7 = 2
            r10.label = r7
            r7 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r7)
            java.lang.Object r6 = r6.invoke(r2, r10)
            r7 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r7)
            if (r6 != r1) goto L_0x007e
            return r1
        L_0x007e:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            if (r6 != 0) goto L_0x009a
            kotlin.jvm.internal.Ref$BooleanRef r6 = r5.$matched$inlined
            r6.element = r3
            kotlinx.coroutines.flow.FlowCollector r3 = r5.$this_unsafeFlow$inlined
            r10.L$0 = r4
            r10.L$1 = r4
            r4 = 3
            r10.label = r4
            java.lang.Object r2 = r3.emit(r2, r10)
            if (r2 != r1) goto L_0x009a
            return r1
        L_0x009a:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$lambda4$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
