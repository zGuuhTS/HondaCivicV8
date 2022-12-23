package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo11814d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H@"}, mo11815d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.FlowKt__DelayKt$fixedPeriodTicker$3", mo12530f = "Delay.kt", mo12531i = {0, 1, 2}, mo12532l = {316, 318, 319}, mo12533m = "invokeSuspend", mo12534n = {"$this$produce", "$this$produce", "$this$produce"}, mo12535s = {"L$0", "L$0", "L$0"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$fixedPeriodTicker$3 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $delayMillis;
    final /* synthetic */ long $initialDelayMillis;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$fixedPeriodTicker$3(long j, long j2, Continuation<? super FlowKt__DelayKt$fixedPeriodTicker$3> continuation) {
        super(2, continuation);
        this.$initialDelayMillis = j;
        this.$delayMillis = j2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__DelayKt$fixedPeriodTicker$3 flowKt__DelayKt$fixedPeriodTicker$3 = new FlowKt__DelayKt$fixedPeriodTicker$3(this.$initialDelayMillis, this.$delayMillis, continuation);
        flowKt__DelayKt$fixedPeriodTicker$3.L$0 = obj;
        return flowKt__DelayKt$fixedPeriodTicker$3;
    }

    public final Object invoke(ProducerScope<? super Unit> producerScope, Continuation<? super Unit> continuation) {
        return ((FlowKt__DelayKt$fixedPeriodTicker$3) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0058, code lost:
        if (r2.getChannel().send(kotlin.Unit.INSTANCE, r1) == r0) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005b, code lost:
        r1.L$0 = r2;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0069, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r1.$delayMillis, r1) != r0) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0042, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r1.$initialDelayMillis, r1) == r0) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0044, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            switch(r1) {
                case 0: goto L_0x002c;
                case 1: goto L_0x0023;
                case 2: goto L_0x001a;
                case 3: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x0011:
            r1 = r7
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x006c
        L_0x001a:
            r1 = r7
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x005b
        L_0x0023:
            r1 = r7
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x0045
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r8)
            r1 = r7
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            long r3 = r1.$initialDelayMillis
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r6 = 1
            r1.label = r6
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r3 != r0) goto L_0x0045
        L_0x0044:
            return r0
        L_0x0045:
        L_0x0046:
            kotlinx.coroutines.channels.SendChannel r3 = r2.getChannel()
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r6 = 2
            r1.label = r6
            java.lang.Object r3 = r3.send(r4, r5)
            if (r3 != r0) goto L_0x005b
            goto L_0x0044
        L_0x005b:
            long r3 = r1.$delayMillis
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r6 = 3
            r1.label = r6
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r3, r5)
            if (r3 != r0) goto L_0x006c
            goto L_0x0044
        L_0x006c:
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$fixedPeriodTicker$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
