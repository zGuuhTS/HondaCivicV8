package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.sync.Semaphore;

@Metadata(mo11814d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/FlowKt__CollectKt$collect$3", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Collect.kt */
public final class ChannelFlowMerge$collectTo$$inlined$collect$1 implements FlowCollector<Flow<? extends T>> {
    final /* synthetic */ SendingCollector $collector$inlined;
    final /* synthetic */ Job $job$inlined;
    final /* synthetic */ ProducerScope $scope$inlined;
    final /* synthetic */ Semaphore $semaphore$inlined;

    public ChannelFlowMerge$collectTo$$inlined$collect$1(Job job, Semaphore semaphore, ProducerScope producerScope, SendingCollector sendingCollector) {
        this.$job$inlined = job;
        this.$semaphore$inlined = semaphore;
        this.$scope$inlined = producerScope;
        this.$collector$inlined = sendingCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object emit(kotlinx.coroutines.flow.Flow<? extends T> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1.C08361
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1.C08361) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1$1 r0 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1$1
            r0.<init>(r10, r12)
        L_0x0019:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r12.label
            switch(r2) {
                case 0: goto L_0x003a;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = 0
            java.lang.Object r1 = r12.L$1
            kotlinx.coroutines.flow.Flow r1 = (kotlinx.coroutines.flow.Flow) r1
            java.lang.Object r2 = r12.L$0
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1 r2 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1) r2
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x005e
        L_0x003a:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r10
            r3 = r12
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            kotlinx.coroutines.flow.Flow r11 = (kotlinx.coroutines.flow.Flow) r11
            r3 = 0
            kotlinx.coroutines.Job r4 = r2.$job$inlined
            if (r4 != 0) goto L_0x0049
            goto L_0x004c
        L_0x0049:
            kotlinx.coroutines.JobKt.ensureActive((kotlinx.coroutines.Job) r4)
        L_0x004c:
            kotlinx.coroutines.sync.Semaphore r4 = r2.$semaphore$inlined
            r12.L$0 = r2
            r12.L$1 = r11
            r5 = 1
            r12.label = r5
            java.lang.Object r4 = r4.acquire(r12)
            if (r4 != r1) goto L_0x005c
            return r1
        L_0x005c:
            r1 = r11
            r11 = r3
        L_0x005e:
            kotlinx.coroutines.channels.ProducerScope r3 = r2.$scope$inlined
            r4 = r3
            kotlinx.coroutines.CoroutineScope r4 = (kotlinx.coroutines.CoroutineScope) r4
            r5 = 0
            r6 = 0
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1 r3 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1
            kotlinx.coroutines.flow.internal.SendingCollector r7 = r2.$collector$inlined
            kotlinx.coroutines.sync.Semaphore r8 = r2.$semaphore$inlined
            r9 = 0
            r3.<init>(r1, r7, r8, r9)
            r7 = r3
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            r8 = 3
            kotlinx.coroutines.Job unused = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r4, r5, r6, r7, r8, r9)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$$inlined$collect$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
