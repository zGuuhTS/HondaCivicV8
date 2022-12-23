package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* renamed from: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1 */
/* compiled from: Collect.kt */
public final class C0837xfa64eeb5 implements FlowCollector<T> {
    final /* synthetic */ CoroutineScope $$this$flowScope$inlined;
    final /* synthetic */ FlowCollector $collector$inlined;
    final /* synthetic */ Ref.ObjectRef $previousFlow$inlined;
    final /* synthetic */ ChannelFlowTransformLatest this$0;

    public C0837xfa64eeb5(Ref.ObjectRef objectRef, CoroutineScope coroutineScope, ChannelFlowTransformLatest channelFlowTransformLatest, FlowCollector flowCollector) {
        this.$previousFlow$inlined = objectRef;
        this.$$this$flowScope$inlined = coroutineScope;
        this.this$0 = channelFlowTransformLatest;
        this.$collector$inlined = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(T r12, kotlin.coroutines.Continuation<? super kotlin.Unit> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof kotlinx.coroutines.flow.internal.C0837xfa64eeb5.C08381
            if (r0 == 0) goto L_0x0014
            r0 = r13
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.internal.C0837xfa64eeb5.C08381) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1$1
            r0.<init>(r11, r13)
        L_0x0019:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x003d;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002d:
            r12 = 0
            r1 = 0
            java.lang.Object r2 = r13.L$2
            kotlinx.coroutines.Job r2 = (kotlinx.coroutines.Job) r2
            java.lang.Object r2 = r13.L$1
            java.lang.Object r3 = r13.L$0
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$invokeSuspend$$inlined$collect$1 r3 = (kotlinx.coroutines.flow.internal.C0837xfa64eeb5) r3
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x006c
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r0)
            r3 = r11
            r2 = r13
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r2 = r12
            r12 = 0
            kotlin.jvm.internal.Ref$ObjectRef r4 = r3.$previousFlow$inlined
            T r4 = r4.element
            kotlinx.coroutines.Job r4 = (kotlinx.coroutines.Job) r4
            if (r4 != 0) goto L_0x004f
            goto L_0x006e
        L_0x004f:
            r5 = r4
            r6 = 0
            kotlinx.coroutines.flow.internal.ChildCancelledException r7 = new kotlinx.coroutines.flow.internal.ChildCancelledException
            r7.<init>()
            java.util.concurrent.CancellationException r7 = (java.util.concurrent.CancellationException) r7
            r5.cancel((java.util.concurrent.CancellationException) r7)
            r13.L$0 = r3
            r13.L$1 = r2
            r13.L$2 = r4
            r4 = 1
            r13.label = r4
            java.lang.Object r4 = r5.join(r13)
            if (r4 != r1) goto L_0x006b
            return r1
        L_0x006b:
            r1 = r6
        L_0x006c:
        L_0x006e:
            kotlin.jvm.internal.Ref$ObjectRef r1 = r3.$previousFlow$inlined
            kotlinx.coroutines.CoroutineScope r4 = r3.$$this$flowScope$inlined
            r5 = 0
            kotlinx.coroutines.CoroutineStart r6 = kotlinx.coroutines.CoroutineStart.UNDISPATCHED
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2 r7 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r8 = r3.this$0
            kotlinx.coroutines.flow.FlowCollector r9 = r3.$collector$inlined
            r10 = 0
            r7.<init>(r8, r9, r2, r10)
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            r8 = 1
            r9 = 0
            kotlinx.coroutines.Job r4 = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r4, r5, r6, r7, r8, r9)
            r1.element = r4
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.C0837xfa64eeb5.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
