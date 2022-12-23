package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.internal.Ref;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Collect.kt */
public final class StartedLazily$command$1$invokeSuspend$$inlined$collect$1 implements FlowCollector<Integer> {
    final /* synthetic */ FlowCollector $$this$flow$inlined;
    final /* synthetic */ Ref.BooleanRef $started$inlined;

    public StartedLazily$command$1$invokeSuspend$$inlined$collect$1(Ref.BooleanRef booleanRef, FlowCollector flowCollector) {
        this.$started$inlined = booleanRef;
        this.$$this$flow$inlined = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(java.lang.Integer r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1.C08351
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1.C08351) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1$1
            r0.<init>(r6, r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0032;
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
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x005c
        L_0x0032:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r6
            r3 = r8
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r3 = r7
            java.lang.Number r3 = (java.lang.Number) r3
            int r7 = r3.intValue()
            r3 = 0
            if (r7 <= 0) goto L_0x005c
            kotlin.jvm.internal.Ref$BooleanRef r4 = r2.$started$inlined
            boolean r4 = r4.element
            if (r4 != 0) goto L_0x005c
            kotlin.jvm.internal.Ref$BooleanRef r7 = r2.$started$inlined
            r4 = 1
            r7.element = r4
            kotlinx.coroutines.flow.FlowCollector r7 = r2.$$this$flow$inlined
            kotlinx.coroutines.flow.SharingCommand r5 = kotlinx.coroutines.flow.SharingCommand.START
            r8.label = r4
            java.lang.Object r7 = r7.emit(r5, r8)
            if (r7 != r1) goto L_0x005b
            return r1
        L_0x005b:
            r7 = r3
        L_0x005c:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StartedLazily$command$1$invokeSuspend$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
