package kotlinx.coroutines.flow;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.BroadcastKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowKt;

@Metadata(mo11814d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a0\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n\u001a/\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a9\u0010\u0010\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H@ø\u0001\u0000¢\u0006\u0004\b\u0013\u0010\u0014\u001a&\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\n\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, mo11815d2 = {"asFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "Lkotlinx/coroutines/channels/BroadcastChannel;", "broadcastIn", "scope", "Lkotlinx/coroutines/CoroutineScope;", "start", "Lkotlinx/coroutines/CoroutineStart;", "consumeAsFlow", "Lkotlinx/coroutines/channels/ReceiveChannel;", "emitAll", "", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitAllImpl", "consume", "", "emitAllImpl$FlowKt__ChannelsKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "produceIn", "receiveAsFlow", "kotlinx-coroutines-core"}, mo11816k = 5, mo11817mv = {1, 5, 1}, mo11819xi = 48, mo11820xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Channels.kt */
final /* synthetic */ class FlowKt__ChannelsKt {

    @Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
    /* compiled from: Channels.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 2;
            iArr[BufferOverflow.DROP_LATEST.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final <T> Object emitAll(FlowCollector<? super T> $this$emitAll, ReceiveChannel<? extends T> channel, Continuation<? super Unit> $completion) {
        Object emitAllImpl$FlowKt__ChannelsKt = emitAllImpl$FlowKt__ChannelsKt($this$emitAll, channel, true, $completion);
        return emitAllImpl$FlowKt__ChannelsKt == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.flow.FlowCollector<? super T>, code=kotlinx.coroutines.flow.FlowCollector, for r8v0, types: [kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.flow.FlowCollector<? super T>] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x008a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector r8, kotlinx.coroutines.channels.ReceiveChannel<? extends T> r9, boolean r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = (kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = new kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            r0.<init>(r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x006b;
                case 1: goto L_0x0047;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            boolean r8 = r11.Z$0
            r9 = 0
            java.lang.Object r10 = r11.L$1
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r2 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0044 }
            r6 = r10
            r10 = r8
            r8 = r2
            r2 = r1
            r1 = r0
            r0 = r11
            r11 = r6
            goto L_0x00c4
        L_0x0044:
            r1 = move-exception
            goto L_0x00d6
        L_0x0047:
            r8 = 0
            boolean r9 = r11.Z$0
            r10 = 0
            java.lang.Object r2 = r11.L$1
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r3 = r11.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0063 }
            r4 = r0
            kotlinx.coroutines.channels.ChannelResult r4 = (kotlinx.coroutines.channels.ChannelResult) r4     // Catch:{ all -> 0x0063 }
            java.lang.Object r4 = r4.m1574unboximpl()     // Catch:{ all -> 0x0063 }
            r6 = r0
            r0 = r11
            r11 = r2
            r2 = r1
            r1 = r6
            goto L_0x0091
        L_0x0063:
            r1 = move-exception
            r8 = r10
            r10 = r2
            r6 = r9
            r9 = r8
            r8 = r6
            goto L_0x00d6
        L_0x006b:
            kotlin.ResultKt.throwOnFailure(r0)
            kotlinx.coroutines.flow.FlowKt.ensureActive(r8)
            r2 = 0
            r6 = r11
            r11 = r9
            r9 = r2
            r2 = r1
            r1 = r0
            r0 = r6
        L_0x0078:
            r3 = 0
            r0.L$0 = r8     // Catch:{ all -> 0x00cf }
            r0.L$1 = r11     // Catch:{ all -> 0x00cf }
            r0.Z$0 = r10     // Catch:{ all -> 0x00cf }
            r4 = 1
            r0.label = r4     // Catch:{ all -> 0x00cf }
            java.lang.Object r4 = r11.m1581receiveCatchingJP2dKIU(r0)     // Catch:{ all -> 0x00cf }
            if (r4 != r2) goto L_0x008b
            return r2
        L_0x008b:
            r6 = r3
            r3 = r8
            r8 = r6
            r7 = r10
            r10 = r9
            r9 = r7
        L_0x0091:
            r8 = r10
            r10 = r4
            boolean r4 = kotlinx.coroutines.channels.ChannelResult.m1570isClosedimpl(r10)     // Catch:{ all -> 0x00c5 }
            if (r4 == 0) goto L_0x00ad
            java.lang.Throwable r2 = kotlinx.coroutines.channels.ChannelResult.m1566exceptionOrNullimpl(r10)     // Catch:{ all -> 0x00c5 }
            if (r2 != 0) goto L_0x00aa
            if (r9 == 0) goto L_0x00a6
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r8)
        L_0x00a6:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L_0x00aa:
            r4 = 0
            throw r2     // Catch:{ all -> 0x00c5 }
        L_0x00ad:
            java.lang.Object r4 = kotlinx.coroutines.channels.ChannelResult.m1568getOrThrowimpl(r10)     // Catch:{ all -> 0x00c5 }
            r0.L$0 = r3     // Catch:{ all -> 0x00c5 }
            r0.L$1 = r11     // Catch:{ all -> 0x00c5 }
            r0.Z$0 = r9     // Catch:{ all -> 0x00c5 }
            r5 = 2
            r0.label = r5     // Catch:{ all -> 0x00c5 }
            java.lang.Object r4 = r3.emit(r4, r0)     // Catch:{ all -> 0x00c5 }
            if (r4 != r2) goto L_0x00c1
            return r2
        L_0x00c1:
            r10 = r9
            r9 = r8
            r8 = r3
        L_0x00c4:
            goto L_0x0078
        L_0x00c5:
            r10 = move-exception
            r6 = r9
            r9 = r8
            r8 = r6
            r7 = r1
            r1 = r10
            r10 = r11
            r11 = r0
            r0 = r7
            goto L_0x00d6
        L_0x00cf:
            r8 = move-exception
            r6 = r1
            r1 = r8
            r8 = r10
            r10 = r11
            r11 = r0
            r0 = r6
        L_0x00d6:
            r9 = r1
            throw r1     // Catch:{ all -> 0x00d9 }
        L_0x00d9:
            r1 = move-exception
            if (r8 == 0) goto L_0x00df
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r9)
        L_0x00df:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.channels.ReceiveChannel, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Flow<T> receiveAsFlow(ReceiveChannel<? extends T> $this$receiveAsFlow) {
        return new ChannelAsFlow<>($this$receiveAsFlow, false, (CoroutineContext) null, 0, (BufferOverflow) null, 28, (DefaultConstructorMarker) null);
    }

    public static final <T> Flow<T> consumeAsFlow(ReceiveChannel<? extends T> $this$consumeAsFlow) {
        return new ChannelAsFlow<>($this$consumeAsFlow, true, (CoroutineContext) null, 0, (BufferOverflow) null, 28, (DefaultConstructorMarker) null);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "'BroadcastChannel' is obsolete and all corresponding operators are deprecated in the favour of StateFlow and SharedFlow")
    public static final <T> Flow<T> asFlow(BroadcastChannel<T> $this$asFlow) {
        return new FlowKt__ChannelsKt$asFlow$$inlined$unsafeFlow$1($this$asFlow);
    }

    public static /* synthetic */ BroadcastChannel broadcastIn$default(Flow flow, CoroutineScope coroutineScope, CoroutineStart coroutineStart, int i, Object obj) {
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.LAZY;
        }
        return FlowKt.broadcastIn(flow, coroutineScope, coroutineStart);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use shareIn operator and the resulting SharedFlow as a replacement for BroadcastChannel", replaceWith = @ReplaceWith(expression = "this.shareIn(scope, SharingStarted.Lazily, 0)", imports = {}))
    public static final <T> BroadcastChannel<T> broadcastIn(Flow<? extends T> $this$broadcastIn, CoroutineScope scope, CoroutineStart start) {
        int capacity;
        ChannelFlow channelFlow = ChannelFlowKt.asChannelFlow($this$broadcastIn);
        switch (WhenMappings.$EnumSwitchMapping$0[channelFlow.onBufferOverflow.ordinal()]) {
            case 1:
                capacity = channelFlow.getProduceCapacity$kotlinx_coroutines_core();
                break;
            case 2:
                capacity = -1;
                break;
            case 3:
                throw new IllegalArgumentException("Broadcast channel does not support BufferOverflow.DROP_LATEST");
            default:
                throw new NoWhenBranchMatchedException();
        }
        return BroadcastKt.broadcast$default(scope, channelFlow.context, capacity, start, (Function1) null, new FlowKt__ChannelsKt$broadcastIn$1($this$broadcastIn, (Continuation<? super FlowKt__ChannelsKt$broadcastIn$1>) null), 8, (Object) null);
    }

    public static final <T> ReceiveChannel<T> produceIn(Flow<? extends T> $this$produceIn, CoroutineScope scope) {
        return ChannelFlowKt.asChannelFlow($this$produceIn).produceImpl(scope);
    }
}
