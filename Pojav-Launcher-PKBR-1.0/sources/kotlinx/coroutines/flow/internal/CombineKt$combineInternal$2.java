package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo11814d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u00020\u0004H@"}, mo11815d2 = {"<anonymous>", "", "R", "T", "Lkotlinx/coroutines/CoroutineScope;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2", mo12530f = "Combine.kt", mo12531i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2}, mo12532l = {57, 79, 82}, mo12533m = "invokeSuspend", mo12534n = {"latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch", "latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch", "latestValues", "resultChannel", "lastReceivedEpoch", "remainingAbsentValues", "currentEpoch"}, mo12535s = {"L$0", "L$1", "L$2", "I$0", "I$1", "L$0", "L$1", "L$2", "I$0", "I$1", "L$0", "L$1", "L$2", "I$0", "I$1"})
/* compiled from: Combine.kt */
final class CombineKt$combineInternal$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function0<T[]> $arrayFactory;
    final /* synthetic */ Flow<T>[] $flows;
    final /* synthetic */ FlowCollector<R> $this_combineInternal;
    final /* synthetic */ Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> $transform;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    CombineKt$combineInternal$2(Flow<? extends T>[] flowArr, Function0<T[]> function0, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> function3, FlowCollector<? super R> flowCollector, Continuation<? super CombineKt$combineInternal$2> continuation) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$arrayFactory = function0;
        this.$transform = function3;
        this.$this_combineInternal = flowCollector;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CombineKt$combineInternal$2 combineKt$combineInternal$2 = new CombineKt$combineInternal$2(this.$flows, this.$arrayFactory, this.$transform, this.$this_combineInternal, continuation);
        combineKt$combineInternal$2.L$0 = obj;
        return combineKt$combineInternal$2;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((CombineKt$combineInternal$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v10, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v22, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v14, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00f6 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0180  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r24) {
        /*
            r23 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r23
            int r2 = r1.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0082;
                case 1: goto L_0x0058;
                case 2: goto L_0x0035;
                case 3: goto L_0x0015;
                default: goto L_0x000d;
            }
        L_0x000d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0015:
            r2 = r23
            r4 = r24
            int r5 = r2.I$1
            int r6 = r2.I$0
            java.lang.Object r7 = r2.L$2
            byte[] r7 = (byte[]) r7
            java.lang.Object r8 = r2.L$1
            kotlinx.coroutines.channels.Channel r8 = (kotlinx.coroutines.channels.Channel) r8
            java.lang.Object r9 = r2.L$0
            java.lang.Object[] r9 = (java.lang.Object[]) r9
            kotlin.ResultKt.throwOnFailure(r4)
            r14 = r9
            r9 = r3
            r3 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r8
            goto L_0x017e
        L_0x0035:
            r2 = r23
            r5 = r24
            int r6 = r2.I$1
            int r7 = r2.I$0
            java.lang.Object r8 = r2.L$2
            byte[] r8 = (byte[]) r8
            java.lang.Object r9 = r2.L$1
            kotlinx.coroutines.channels.Channel r9 = (kotlinx.coroutines.channels.Channel) r9
            java.lang.Object r10 = r2.L$0
            java.lang.Object[] r10 = (java.lang.Object[]) r10
            kotlin.ResultKt.throwOnFailure(r5)
            r14 = r10
            r22 = r9
            r9 = r3
            r3 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r22
            goto L_0x0154
        L_0x0058:
            r2 = r23
            r4 = r24
            int r5 = r2.I$1
            int r6 = r2.I$0
            java.lang.Object r7 = r2.L$2
            byte[] r7 = (byte[]) r7
            java.lang.Object r8 = r2.L$1
            kotlinx.coroutines.channels.Channel r8 = (kotlinx.coroutines.channels.Channel) r8
            java.lang.Object r9 = r2.L$0
            java.lang.Object[] r9 = (java.lang.Object[]) r9
            kotlin.ResultKt.throwOnFailure(r4)
            r10 = r4
            kotlinx.coroutines.channels.ChannelResult r10 = (kotlinx.coroutines.channels.ChannelResult) r10
            java.lang.Object r10 = r10.m1574unboximpl()
            r22 = r9
            r9 = r3
            r3 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r8
            r8 = r22
            goto L_0x00f8
        L_0x0082:
            kotlin.ResultKt.throwOnFailure(r24)
            r2 = r23
            r5 = r24
            java.lang.Object r6 = r2.L$0
            kotlinx.coroutines.CoroutineScope r6 = (kotlinx.coroutines.CoroutineScope) r6
            kotlinx.coroutines.flow.Flow<T>[] r7 = r2.$flows
            int r13 = r7.length
            if (r13 != 0) goto L_0x0095
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0095:
            java.lang.Object[] r14 = new java.lang.Object[r13]
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.flow.internal.NullSurrogateKt.UNINITIALIZED
            r9 = 0
            r10 = 0
            r11 = 6
            r12 = 0
            r7 = r14
            kotlin.collections.ArraysKt.fill$default((java.lang.Object[]) r7, (java.lang.Object) r8, (int) r9, (int) r10, (int) r11, (java.lang.Object) r12)
            r7 = 6
            kotlinx.coroutines.channels.Channel r4 = kotlinx.coroutines.channels.ChannelKt.Channel$default(r13, r4, r4, r7, r4)
            java.util.concurrent.atomic.AtomicInteger r7 = new java.util.concurrent.atomic.AtomicInteger
            r7.<init>(r13)
            r18 = r7
            r21 = r13
            r7 = 0
            if (r13 <= 0) goto L_0x00d7
        L_0x00b2:
            r17 = r7
            int r12 = r7 + 1
            r8 = 0
            r9 = 0
            kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1 r7 = new kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$1
            kotlinx.coroutines.flow.Flow<T>[] r10 = r2.$flows
            r20 = 0
            r15 = r7
            r16 = r10
            r19 = r4
            r15.<init>(r16, r17, r18, r19, r20)
            r10 = r7
            kotlin.jvm.functions.Function2 r10 = (kotlin.jvm.functions.Function2) r10
            r11 = 3
            r15 = 0
            r7 = r6
            r3 = r12
            r12 = r15
            kotlinx.coroutines.Job unused = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r7, r8, r9, r10, r11, r12)
            if (r3 < r13) goto L_0x00d4
            goto L_0x00d7
        L_0x00d4:
            r7 = r3
            r3 = 1
            goto L_0x00b2
        L_0x00d7:
            byte[] r3 = new byte[r13]
            r6 = 0
            r7 = r21
        L_0x00dc:
            int r8 = r6 + 1
            byte r6 = (byte) r8
            r8 = r2
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r2.L$0 = r14
            r2.L$1 = r4
            r2.L$2 = r3
            r2.I$0 = r7
            r2.I$1 = r6
            r9 = 1
            r2.label = r9
            java.lang.Object r10 = r4.m1581receiveCatchingJP2dKIU(r8)
            if (r10 != r0) goto L_0x00f7
            return r0
        L_0x00f7:
            r8 = r14
        L_0x00f8:
            java.lang.Object r10 = kotlinx.coroutines.channels.ChannelResult.m1567getOrNullimpl(r10)
            kotlin.collections.IndexedValue r10 = (kotlin.collections.IndexedValue) r10
            if (r10 != 0) goto L_0x0103
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0103:
            int r11 = r10.getIndex()
            r12 = r8[r11]
            java.lang.Object r13 = r10.getValue()
            r8[r11] = r13
            kotlinx.coroutines.internal.Symbol r10 = kotlinx.coroutines.flow.internal.NullSurrogateKt.UNINITIALIZED
            if (r12 != r10) goto L_0x0116
            int r7 = r7 + -1
        L_0x0116:
            byte r10 = r3[r11]
            if (r10 != r6) goto L_0x011b
            goto L_0x012a
        L_0x011b:
            byte r10 = (byte) r6
            r3[r11] = r10
            java.lang.Object r10 = r4.m1582tryReceivePtdJZtk()
            java.lang.Object r10 = kotlinx.coroutines.channels.ChannelResult.m1567getOrNullimpl(r10)
            kotlin.collections.IndexedValue r10 = (kotlin.collections.IndexedValue) r10
            if (r10 != 0) goto L_0x0183
        L_0x012a:
            if (r7 != 0) goto L_0x0180
            kotlin.jvm.functions.Function0<T[]> r10 = r2.$arrayFactory
            java.lang.Object r10 = r10.invoke()
            java.lang.Object[] r10 = (java.lang.Object[]) r10
            if (r10 != 0) goto L_0x0159
            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T[], kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r11 = r2.$transform
            kotlinx.coroutines.flow.FlowCollector<R> r12 = r2.$this_combineInternal
            r2.L$0 = r8
            r2.L$1 = r4
            r2.L$2 = r3
            r2.I$0 = r7
            r2.I$1 = r6
            r13 = 2
            r2.label = r13
            java.lang.Object r11 = r11.invoke(r12, r8, r2)
            if (r11 != r0) goto L_0x014e
            return r0
        L_0x014e:
            r14 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r10
        L_0x0154:
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x00dc
        L_0x0159:
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 14
            r17 = 0
            r11 = r8
            r12 = r10
            kotlin.collections.ArraysKt.copyInto$default((java.lang.Object[]) r11, (java.lang.Object[]) r12, (int) r13, (int) r14, (int) r15, (int) r16, (java.lang.Object) r17)
            kotlin.jvm.functions.Function3<kotlinx.coroutines.flow.FlowCollector<? super R>, T[], kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r11 = r2.$transform
            kotlinx.coroutines.flow.FlowCollector<R> r12 = r2.$this_combineInternal
            r2.L$0 = r8
            r2.L$1 = r4
            r2.L$2 = r3
            r2.I$0 = r7
            r2.I$1 = r6
            r13 = 3
            r2.label = r13
            java.lang.Object r10 = r11.invoke(r12, r10, r2)
            if (r10 != r0) goto L_0x017d
            return r0
        L_0x017d:
            r14 = r8
        L_0x017e:
            goto L_0x00dc
        L_0x0180:
            r14 = r8
            goto L_0x00dc
        L_0x0183:
            goto L_0x0103
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
