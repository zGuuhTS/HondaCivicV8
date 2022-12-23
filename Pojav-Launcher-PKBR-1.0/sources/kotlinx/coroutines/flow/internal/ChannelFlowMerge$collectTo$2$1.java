package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.sync.Semaphore;

@Metadata(mo11814d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, mo11815d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1", mo12530f = "Merge.kt", mo12531i = {}, mo12532l = {69}, mo12533m = "invokeSuspend", mo12534n = {}, mo12535s = {})
/* compiled from: Merge.kt */
final class ChannelFlowMerge$collectTo$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ SendingCollector<T> $collector;
    final /* synthetic */ Flow<T> $inner;
    final /* synthetic */ Semaphore $semaphore;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelFlowMerge$collectTo$2$1(Flow<? extends T> flow, SendingCollector<T> sendingCollector, Semaphore semaphore, Continuation<? super ChannelFlowMerge$collectTo$2$1> continuation) {
        super(2, continuation);
        this.$inner = flow;
        this.$collector = sendingCollector;
        this.$semaphore = semaphore;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ChannelFlowMerge$collectTo$2$1(this.$inner, this.$collector, this.$semaphore, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ChannelFlowMerge$collectTo$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0031, code lost:
        r0.$semaphore.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0039, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            switch(r1) {
                case 0: goto L_0x0018;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x0011:
            r0 = r7
            kotlin.ResultKt.throwOnFailure(r8)     // Catch:{ all -> 0x0016 }
            goto L_0x0031
        L_0x0016:
            r1 = move-exception
            goto L_0x003e
        L_0x0018:
            kotlin.ResultKt.throwOnFailure(r8)
            r1 = r7
            kotlinx.coroutines.flow.Flow<T> r2 = r1.$inner     // Catch:{ all -> 0x003a }
            kotlinx.coroutines.flow.internal.SendingCollector<T> r3 = r1.$collector     // Catch:{ all -> 0x003a }
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3     // Catch:{ all -> 0x003a }
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4     // Catch:{ all -> 0x003a }
            r5 = 1
            r1.label = r5     // Catch:{ all -> 0x003a }
            java.lang.Object r2 = r2.collect(r3, r4)     // Catch:{ all -> 0x003a }
            if (r2 != r0) goto L_0x0030
            return r0
        L_0x0030:
            r0 = r1
        L_0x0031:
            kotlinx.coroutines.sync.Semaphore r1 = r0.$semaphore
            r1.release()
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x003a:
            r0 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x003e:
            kotlinx.coroutines.sync.Semaphore r2 = r0.$semaphore
            r2.release()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
