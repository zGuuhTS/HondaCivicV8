package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo11814d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u00042\u0006\u0010\u0005\u001a\u00020\u0001H@"}, mo11815d2 = {"<anonymous>", "", "T1", "T2", "R", "it"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2$1$1", mo12530f = "Combine.kt", mo12531i = {}, mo12532l = {132, 135, 135}, mo12533m = "invokeSuspend", mo12534n = {}, mo12535s = {})
/* compiled from: Combine.kt */
final class CombineKt$zipImpl$1$1$2$1$1 extends SuspendLambda implements Function2<Unit, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<Object> $second;
    final /* synthetic */ FlowCollector<R> $this_unsafeFlow;
    final /* synthetic */ Function3<T1, T2, Continuation<? super R>, Object> $transform;
    final /* synthetic */ T1 $value;
    Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$zipImpl$1$1$2$1$1(ReceiveChannel<? extends Object> receiveChannel, FlowCollector<? super R> flowCollector, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> function3, T1 t1, Continuation<? super CombineKt$zipImpl$1$1$2$1$1> continuation) {
        super(2, continuation);
        this.$second = receiveChannel;
        this.$this_unsafeFlow = flowCollector;
        this.$transform = function3;
        this.$value = t1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CombineKt$zipImpl$1$1$2$1$1(this.$second, this.$this_unsafeFlow, this.$transform, this.$value, continuation);
    }

    public final Object invoke(Unit unit, Continuation<? super Unit> continuation) {
        return ((CombineKt$zipImpl$1$1$2$1$1) create(unit, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0043, code lost:
        r4 = r1.$this_unsafeFlow;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0049, code lost:
        if ((r3 instanceof kotlinx.coroutines.channels.ChannelResult.Failed) == false) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004b, code lost:
        r0 = kotlinx.coroutines.channels.ChannelResult.m1566exceptionOrNullimpl(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0051, code lost:
        if (r0 != null) goto L_0x005a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0053, code lost:
        r0 = new kotlinx.coroutines.flow.internal.AbortFlowException(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005a, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005b, code lost:
        r5 = r1.$transform;
        r6 = r1.$value;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0064, code lost:
        if (r3 != kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0066, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0068, code lost:
        r1.L$0 = r4;
        r1.label = 2;
        r3 = r5.invoke(r6, r3, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0071, code lost:
        if (r3 != r0) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0073, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0074, code lost:
        r9 = r1;
        r1 = r11;
        r11 = r3;
        r3 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0078, code lost:
        r3.L$0 = null;
        r3.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0084, code lost:
        if (r4.emit(r11, r3) != r0) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0086, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0087, code lost:
        r11 = r1;
        r0 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008b, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 0
            switch(r1) {
                case 0: goto L_0x0030;
                case 1: goto L_0x0024;
                case 2: goto L_0x0018;
                case 3: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0012:
            r0 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0089
        L_0x0018:
            r1 = r10
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r11)
            r4 = r3
            r3 = r1
            r1 = r11
            goto L_0x0078
        L_0x0024:
            r1 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            r3 = r11
            kotlinx.coroutines.channels.ChannelResult r3 = (kotlinx.coroutines.channels.ChannelResult) r3
            java.lang.Object r3 = r3.m1574unboximpl()
            goto L_0x0043
        L_0x0030:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            kotlinx.coroutines.channels.ReceiveChannel<java.lang.Object> r3 = r1.$second
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r5 = 1
            r1.label = r5
            java.lang.Object r3 = r3.m1581receiveCatchingJP2dKIU(r4)
            if (r3 != r0) goto L_0x0043
            return r0
        L_0x0043:
            kotlinx.coroutines.flow.FlowCollector<R> r4 = r1.$this_unsafeFlow
            r5 = 0
            boolean r6 = r3 instanceof kotlinx.coroutines.channels.ChannelResult.Failed
            if (r6 == 0) goto L_0x005b
            java.lang.Throwable r0 = kotlinx.coroutines.channels.ChannelResult.m1566exceptionOrNullimpl(r3)
            r2 = 0
            if (r0 != 0) goto L_0x005a
            kotlinx.coroutines.flow.internal.AbortFlowException r0 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r0.<init>(r4)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
        L_0x005a:
            throw r0
        L_0x005b:
            kotlin.jvm.functions.Function3<T1, T2, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r5 = r1.$transform
            T1 r6 = r1.$value
            kotlinx.coroutines.internal.Symbol r7 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            r8 = 0
            if (r3 != r7) goto L_0x0068
            r3 = r2
        L_0x0068:
            r1.L$0 = r4
            r7 = 2
            r1.label = r7
            java.lang.Object r3 = r5.invoke(r6, r3, r1)
            if (r3 != r0) goto L_0x0074
            return r0
        L_0x0074:
            r9 = r1
            r1 = r11
            r11 = r3
            r3 = r9
        L_0x0078:
            r5 = r3
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r3.L$0 = r2
            r2 = 3
            r3.label = r2
            java.lang.Object r11 = r4.emit(r11, r5)
            if (r11 != r0) goto L_0x0087
            return r0
        L_0x0087:
            r11 = r1
            r0 = r3
        L_0x0089:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$zipImpl$1$1$2$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
