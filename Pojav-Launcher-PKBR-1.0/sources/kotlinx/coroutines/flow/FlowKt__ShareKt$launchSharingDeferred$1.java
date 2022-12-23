package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo11814d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, mo11815d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1", mo12530f = "Share.kt", mo12531i = {}, mo12532l = {418}, mo12533m = "invokeSuspend", mo12534n = {}, mo12535s = {})
/* compiled from: Share.kt */
final class FlowKt__ShareKt$launchSharingDeferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ CompletableDeferred<StateFlow<T>> $result;
    final /* synthetic */ Flow<T> $upstream;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__ShareKt$launchSharingDeferred$1(Flow<? extends T> flow, CompletableDeferred<StateFlow<T>> completableDeferred, Continuation<? super FlowKt__ShareKt$launchSharingDeferred$1> continuation) {
        super(2, continuation);
        this.$upstream = flow;
        this.$result = completableDeferred;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlowKt__ShareKt$launchSharingDeferred$1 flowKt__ShareKt$launchSharingDeferred$1 = new FlowKt__ShareKt$launchSharingDeferred$1(this.$upstream, this.$result, continuation);
        flowKt__ShareKt$launchSharingDeferred$1.L$0 = obj;
        return flowKt__ShareKt$launchSharingDeferred$1;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((FlowKt__ShareKt$launchSharingDeferred$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0045, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L_0x0019;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0011:
            r0 = r10
            r1 = 0
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0017 }
            goto L_0x0042
        L_0x0017:
            r1 = move-exception
            goto L_0x004a
        L_0x0019:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlin.jvm.internal.Ref$ObjectRef r3 = new kotlin.jvm.internal.Ref$ObjectRef     // Catch:{ all -> 0x0046 }
            r3.<init>()     // Catch:{ all -> 0x0046 }
            kotlinx.coroutines.flow.Flow<T> r4 = r1.$upstream     // Catch:{ all -> 0x0046 }
            kotlinx.coroutines.CompletableDeferred<kotlinx.coroutines.flow.StateFlow<T>> r5 = r1.$result     // Catch:{ all -> 0x0046 }
            r6 = 0
            kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$invokeSuspend$$inlined$collect$1 r7 = new kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1$invokeSuspend$$inlined$collect$1     // Catch:{ all -> 0x0046 }
            r7.<init>(r3, r2, r5)     // Catch:{ all -> 0x0046 }
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7     // Catch:{ all -> 0x0046 }
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5     // Catch:{ all -> 0x0046 }
            r8 = 1
            r1.label = r8     // Catch:{ all -> 0x0046 }
            java.lang.Object r5 = r4.collect(r7, r5)     // Catch:{ all -> 0x0046 }
            if (r5 != r0) goto L_0x0040
            return r0
        L_0x0040:
            r0 = r1
            r1 = r6
        L_0x0042:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x0046:
            r0 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
        L_0x004a:
            kotlinx.coroutines.CompletableDeferred<kotlinx.coroutines.flow.StateFlow<T>> r2 = r0.$result
            r2.completeExceptionally(r1)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ShareKt$launchSharingDeferred$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
