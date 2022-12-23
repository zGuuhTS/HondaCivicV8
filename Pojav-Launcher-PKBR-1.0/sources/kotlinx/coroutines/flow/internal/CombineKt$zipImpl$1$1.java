package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo11814d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\u00020\u0005H@"}, mo11815d2 = {"<anonymous>", "", "T1", "T2", "R", "Lkotlinx/coroutines/CoroutineScope;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1", mo12530f = "Combine.kt", mo12531i = {0}, mo12532l = {129}, mo12533m = "invokeSuspend", mo12534n = {"second"}, mo12535s = {"L$0"})
/* compiled from: Combine.kt */
final class CombineKt$zipImpl$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T1> $flow;
    final /* synthetic */ Flow<T2> $flow2;
    final /* synthetic */ FlowCollector<R> $this_unsafeFlow;
    final /* synthetic */ Function3<T1, T2, Continuation<? super R>, Object> $transform;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$zipImpl$1$1(FlowCollector<? super R> flowCollector, Flow<? extends T2> flow, Flow<? extends T1> flow2, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super CombineKt$zipImpl$1$1> continuation) {
        super(2, continuation);
        this.$this_unsafeFlow = flowCollector;
        this.$flow2 = flow;
        this.$flow = flow2;
        this.$transform = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CombineKt$zipImpl$1$1 combineKt$zipImpl$1$1 = new CombineKt$zipImpl$1$1(this.$this_unsafeFlow, this.$flow2, this.$flow, this.$transform, continuation);
        combineKt$zipImpl$1$1.L$0 = obj;
        return combineKt$zipImpl$1$1;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CombineKt$zipImpl$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v9, resolved type: kotlinx.coroutines.channels.ReceiveChannel} */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ac, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ad, code lost:
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00af, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b0, code lost:
        r6 = r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00af A[ExcHandler: AbortFlowException (e kotlinx.coroutines.flow.internal.AbortFlowException), Splitter:B:10:0x005b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r25) {
        /*
            r24 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r24
            int r2 = r1.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0029;
                case 1: goto L_0x0015;
                default: goto L_0x000d;
            }
        L_0x000d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0015:
            r2 = r24
            r5 = r25
            java.lang.Object r0 = r2.L$0
            r6 = r0
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            kotlin.ResultKt.throwOnFailure(r5)     // Catch:{ AbortFlowException -> 0x0026, all -> 0x0023 }
            goto L_0x00a3
        L_0x0023:
            r0 = move-exception
            goto L_0x00bc
        L_0x0026:
            r0 = move-exception
            goto L_0x00b1
        L_0x0029:
            kotlin.ResultKt.throwOnFailure(r25)
            r2 = r24
            r5 = r25
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.CoroutineScope r6 = (kotlinx.coroutines.CoroutineScope) r6
            r8 = 0
            r9 = 0
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1 r7 = new kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$second$1
            kotlinx.coroutines.flow.Flow<T2> r10 = r2.$flow2
            r7.<init>(r10, r4)
            r10 = r7
            kotlin.jvm.functions.Function2 r10 = (kotlin.jvm.functions.Function2) r10
            r11 = 3
            r12 = 0
            r7 = r6
            kotlinx.coroutines.channels.ReceiveChannel r7 = kotlinx.coroutines.channels.ProduceKt.produce$default(r7, r8, r9, r10, r11, r12)
            kotlinx.coroutines.CompletableJob r8 = kotlinx.coroutines.JobKt.Job$default((kotlinx.coroutines.Job) r4, (int) r3, (java.lang.Object) r4)
            r9 = r7
            kotlinx.coroutines.channels.SendChannel r9 = (kotlinx.coroutines.channels.SendChannel) r9
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$1 r10 = new kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$1
            kotlinx.coroutines.flow.FlowCollector<R> r11 = r2.$this_unsafeFlow
            r10.<init>(r8, r11)
            kotlin.jvm.functions.Function1 r10 = (kotlin.jvm.functions.Function1) r10
            r9.invokeOnClose(r10)
            kotlin.coroutines.CoroutineContext r15 = r6.getCoroutineContext()     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            java.lang.Object r16 = kotlinx.coroutines.internal.ThreadContextKt.threadContextElements(r15)     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            kotlin.coroutines.CoroutineContext r9 = r6.getCoroutineContext()     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            r10 = r8
            kotlin.coroutines.CoroutineContext r10 = (kotlin.coroutines.CoroutineContext) r10     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            kotlin.coroutines.CoroutineContext r9 = r9.plus(r10)     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            r11 = 0
            kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2 r12 = new kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            kotlinx.coroutines.flow.Flow<T1> r14 = r2.$flow     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            kotlinx.coroutines.flow.FlowCollector<R> r13 = r2.$this_unsafeFlow     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00ac }
            kotlin.jvm.functions.Function3<T1, T2, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r4 = r2.$transform     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00a8 }
            r20 = 0
            r18 = r13
            r13 = r12
            r17 = r7
            r19 = r4
            r13.<init>(r14, r15, r16, r17, r18, r19, r20)     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00a8 }
            r20 = r12
            kotlin.jvm.functions.Function2 r20 = (kotlin.jvm.functions.Function2) r20     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00a8 }
            r21 = r2
            kotlin.coroutines.Continuation r21 = (kotlin.coroutines.Continuation) r21     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00a8 }
            r22 = 4
            r23 = 0
            r2.L$0 = r7     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00a8 }
            r2.label = r3     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00a8 }
            r17 = r9
            r18 = r10
            r19 = r11
            java.lang.Object r4 = kotlinx.coroutines.flow.internal.ChannelFlowKt.withContextUndispatched$default(r17, r18, r19, r20, r21, r22, r23)     // Catch:{ AbortFlowException -> 0x00af, all -> 0x00a8 }
            if (r4 != r0) goto L_0x00a2
            return r0
        L_0x00a2:
            r6 = r7
        L_0x00a3:
            r4 = 0
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r6, (java.util.concurrent.CancellationException) r4, (int) r3, (java.lang.Object) r4)
            goto L_0x00b7
        L_0x00a8:
            r0 = move-exception
            r6 = r7
            r4 = 0
            goto L_0x00bc
        L_0x00ac:
            r0 = move-exception
            r6 = r7
            goto L_0x00bc
        L_0x00af:
            r0 = move-exception
            r6 = r7
        L_0x00b1:
            kotlinx.coroutines.flow.FlowCollector<R> r4 = r2.$this_unsafeFlow     // Catch:{ all -> 0x00ba }
            kotlinx.coroutines.flow.internal.FlowExceptions_commonKt.checkOwnership(r0, r4)     // Catch:{ all -> 0x00ba }
            goto L_0x00a3
        L_0x00b7:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x00ba:
            r0 = move-exception
            r4 = 0
        L_0x00bc:
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r6, (java.util.concurrent.CancellationException) r4, (int) r3, (java.lang.Object) r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
