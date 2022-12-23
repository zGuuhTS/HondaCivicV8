package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@Metadata(mo11814d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0005H@"}, mo11815d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;", "count", ""}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.StartedWhileSubscribed$command$1", mo12530f = "SharingStarted.kt", mo12531i = {1, 2, 3}, mo12532l = {179, 181, 183, 184, 186}, mo12533m = "invokeSuspend", mo12534n = {"$this$transformLatest", "$this$transformLatest", "$this$transformLatest"}, mo12535s = {"L$0", "L$0", "L$0"})
/* compiled from: SharingStarted.kt */
final class StartedWhileSubscribed$command$1 extends SuspendLambda implements Function3<FlowCollector<? super SharingCommand>, Integer, Continuation<? super Unit>, Object> {
    /* synthetic */ int I$0;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StartedWhileSubscribed this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StartedWhileSubscribed$command$1(StartedWhileSubscribed startedWhileSubscribed, Continuation<? super StartedWhileSubscribed$command$1> continuation) {
        super(3, continuation);
        this.this$0 = startedWhileSubscribed;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector<? super SharingCommand>) (FlowCollector) obj, ((Number) obj2).intValue(), (Continuation<? super Unit>) (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super SharingCommand> flowCollector, int i, Continuation<? super Unit> continuation) {
        StartedWhileSubscribed$command$1 startedWhileSubscribed$command$1 = new StartedWhileSubscribed$command$1(this.this$0, continuation);
        startedWhileSubscribed$command$1.L$0 = flowCollector;
        startedWhileSubscribed$command$1.I$0 = i;
        return startedWhileSubscribed$command$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006b, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r1.this$0.stopTimeout, r1) == r0) goto L_0x006d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006d, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0078, code lost:
        if (r1.this$0.replayExpiration <= 0) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007a, code lost:
        r1.L$0 = r3;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0088, code lost:
        if (r3.emit(kotlinx.coroutines.flow.SharingCommand.STOP, r1) != r0) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008b, code lost:
        r1.L$0 = r3;
        r1.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009d, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r1.this$0.replayExpiration, r1) != r0) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a0, code lost:
        r1.L$0 = null;
        r1.label = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ae, code lost:
        if (r3.emit(kotlinx.coroutines.flow.SharingCommand.STOP_AND_RESET_REPLAY_CACHE, r1) != r0) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b0, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b1, code lost:
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b5, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 0
            switch(r1) {
                case 0: goto L_0x003a;
                case 1: goto L_0x0034;
                case 2: goto L_0x002b;
                case 3: goto L_0x0022;
                case 4: goto L_0x0018;
                case 5: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0012:
            r0 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x00b2
        L_0x0018:
            r1 = r8
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x00a0
        L_0x0022:
            r1 = r8
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x008b
        L_0x002b:
            r1 = r8
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x006e
        L_0x0034:
            r0 = r8
            r1 = r2
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x0058
        L_0x003a:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            int r4 = r1.I$0
            if (r4 <= 0) goto L_0x0059
            kotlinx.coroutines.flow.SharingCommand r2 = kotlinx.coroutines.flow.SharingCommand.START
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 1
            r1.label = r5
            java.lang.Object r2 = r3.emit(r2, r4)
            if (r2 != r0) goto L_0x0056
            return r0
        L_0x0056:
            r0 = r1
            r1 = r3
        L_0x0058:
            goto L_0x00b3
        L_0x0059:
            kotlinx.coroutines.flow.StartedWhileSubscribed r4 = r1.this$0
            long r4 = r4.stopTimeout
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r1.L$0 = r3
            r7 = 2
            r1.label = r7
            java.lang.Object r4 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r4 != r0) goto L_0x006e
        L_0x006d:
            return r0
        L_0x006e:
            kotlinx.coroutines.flow.StartedWhileSubscribed r4 = r1.this$0
            long r4 = r4.replayExpiration
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x00a0
            kotlinx.coroutines.flow.SharingCommand r4 = kotlinx.coroutines.flow.SharingCommand.STOP
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r3
            r6 = 3
            r1.label = r6
            java.lang.Object r4 = r3.emit(r4, r5)
            if (r4 != r0) goto L_0x008b
            goto L_0x006d
        L_0x008b:
            kotlinx.coroutines.flow.StartedWhileSubscribed r4 = r1.this$0
            long r4 = r4.replayExpiration
            r6 = r1
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r1.L$0 = r3
            r7 = 4
            r1.label = r7
            java.lang.Object r4 = kotlinx.coroutines.DelayKt.delay(r4, r6)
            if (r4 != r0) goto L_0x00a0
            goto L_0x006d
        L_0x00a0:
            kotlinx.coroutines.flow.SharingCommand r4 = kotlinx.coroutines.flow.SharingCommand.STOP_AND_RESET_REPLAY_CACHE
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r2
            r2 = 5
            r1.label = r2
            java.lang.Object r2 = r3.emit(r4, r5)
            if (r2 != r0) goto L_0x00b1
            return r0
        L_0x00b1:
            r0 = r1
        L_0x00b2:
        L_0x00b3:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StartedWhileSubscribed$command$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
