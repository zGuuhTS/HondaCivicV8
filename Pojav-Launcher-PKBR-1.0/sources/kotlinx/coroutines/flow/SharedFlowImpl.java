package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlow;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.flow.internal.FusibleFlow;

@Metadata(mo11814d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0012\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\b\u0012\u0004\u0012\u0002H\u00010\u0006:\u0001bB\u001d\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0019\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u0003H@ø\u0001\u0000¢\u0006\u0002\u0010)J\u0010\u0010*\u001a\u00020'2\u0006\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020'H\u0002J\u001f\u0010.\u001a\u00020'2\f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u000000H@ø\u0001\u0000¢\u0006\u0002\u00101J\u0010\u00102\u001a\u00020'2\u0006\u00103\u001a\u00020\u0012H\u0002J\b\u00104\u001a\u00020\u0003H\u0014J\u001d\u00105\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u000e2\u0006\u00106\u001a\u00020\bH\u0014¢\u0006\u0002\u00107J\b\u00108\u001a\u00020'H\u0002J\u0019\u00109\u001a\u00020'2\u0006\u0010:\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010;J\u0019\u0010<\u001a\u00020'2\u0006\u0010:\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u0010;J\u0012\u0010=\u001a\u00020'2\b\u0010>\u001a\u0004\u0018\u00010\u000fH\u0002J1\u0010?\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020'\u0018\u00010@0\u000e2\u0014\u0010A\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020'\u0018\u00010@0\u000eH\u0002¢\u0006\u0002\u0010BJ&\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00000D2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010H\u001a\u0004\u0018\u00010\u000f2\u0006\u0010I\u001a\u00020\u0012H\u0002J7\u0010J\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\u0010\u0010K\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000e2\u0006\u0010L\u001a\u00020\b2\u0006\u0010M\u001a\u00020\bH\u0002¢\u0006\u0002\u0010NJ\b\u0010O\u001a\u00020'H\u0016J\u0015\u0010P\u001a\u00020Q2\u0006\u0010:\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010RJ\u0015\u0010S\u001a\u00020Q2\u0006\u0010:\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010RJ\u0015\u0010T\u001a\u00020Q2\u0006\u0010:\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010RJ\u0010\u0010U\u001a\u00020\u00122\u0006\u0010(\u001a\u00020\u0003H\u0002J\u0012\u0010V\u001a\u0004\u0018\u00010\u000f2\u0006\u0010(\u001a\u00020\u0003H\u0002J(\u0010W\u001a\u00020'2\u0006\u0010X\u001a\u00020\u00122\u0006\u0010Y\u001a\u00020\u00122\u0006\u0010Z\u001a\u00020\u00122\u0006\u0010[\u001a\u00020\u0012H\u0002J%\u0010\\\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020'\u0018\u00010@0\u000e2\u0006\u0010]\u001a\u00020\u0012H\u0000¢\u0006\u0004\b^\u0010_J\r\u0010`\u001a\u00020\u0012H\u0000¢\u0006\u0002\baR\u001a\u0010\r\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u000eX\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\t\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0014R\u000e\u0010\u0018\u001a\u00020\u0012X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u00128BX\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0014R\u000e\u0010\u001b\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001d8VX\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020\u0012X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010!\u001a\u00020\b8BX\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020\b8BX\u0004¢\u0006\u0006\u001a\u0004\b%\u0010#\u0002\u0004\n\u0002\b\u0019¨\u0006c"}, mo11815d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl;", "T", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlow;", "Lkotlinx/coroutines/flow/SharedFlowSlot;", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lkotlinx/coroutines/flow/CancellableFlow;", "Lkotlinx/coroutines/flow/internal/FusibleFlow;", "replay", "", "bufferCapacity", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "(IILkotlinx/coroutines/channels/BufferOverflow;)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferEndIndex", "", "getBufferEndIndex", "()J", "bufferSize", "head", "getHead", "minCollectorIndex", "queueEndIndex", "getQueueEndIndex", "queueSize", "replayCache", "", "getReplayCache", "()Ljava/util/List;", "replayIndex", "replaySize", "getReplaySize", "()I", "totalSize", "getTotalSize", "awaitValue", "", "slot", "(Lkotlinx/coroutines/flow/SharedFlowSlot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelEmitter", "emitter", "Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "cleanupTailLocked", "collect", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "correctCollectorIndexesOnDropOldest", "newHead", "createSlot", "createSlotArray", "size", "(I)[Lkotlinx/coroutines/flow/SharedFlowSlot;", "dropOldestLocked", "emit", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitSuspend", "enqueueLocked", "item", "findSlotsToResumeLocked", "Lkotlin/coroutines/Continuation;", "resumesIn", "([Lkotlin/coroutines/Continuation;)[Lkotlin/coroutines/Continuation;", "fuse", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "getPeekedValueLockedAt", "index", "growBuffer", "curBuffer", "curSize", "newSize", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "resetReplayCache", "tryEmit", "", "(Ljava/lang/Object;)Z", "tryEmitLocked", "tryEmitNoCollectorsLocked", "tryPeekLocked", "tryTakeValue", "updateBufferLocked", "newReplayIndex", "newMinCollectorIndex", "newBufferEndIndex", "newQueueEndIndex", "updateCollectorIndexLocked", "oldIndex", "updateCollectorIndexLocked$kotlinx_coroutines_core", "(J)[Lkotlin/coroutines/Continuation;", "updateNewCollectorIndexLocked", "updateNewCollectorIndexLocked$kotlinx_coroutines_core", "Emitter", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: SharedFlow.kt */
final class SharedFlowImpl<T> extends AbstractSharedFlow<SharedFlowSlot> implements MutableSharedFlow<T>, CancellableFlow<T>, FusibleFlow<T> {
    private Object[] buffer;
    /* access modifiers changed from: private */
    public final int bufferCapacity;
    private int bufferSize;
    private long minCollectorIndex;
    private final BufferOverflow onBufferOverflow;
    /* access modifiers changed from: private */
    public int queueSize;
    private final int replay;
    private long replayIndex;

    @Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
    /* compiled from: SharedFlow.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BufferOverflow.values().length];
            iArr[BufferOverflow.SUSPEND.ordinal()] = 1;
            iArr[BufferOverflow.DROP_LATEST.ordinal()] = 2;
            iArr[BufferOverflow.DROP_OLDEST.ordinal()] = 3;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public SharedFlowImpl(int replay2, int bufferCapacity2, BufferOverflow onBufferOverflow2) {
        this.replay = replay2;
        this.bufferCapacity = bufferCapacity2;
        this.onBufferOverflow = onBufferOverflow2;
    }

    /* access modifiers changed from: private */
    public final long getHead() {
        return Math.min(this.minCollectorIndex, this.replayIndex);
    }

    private final int getReplaySize() {
        return (int) ((getHead() + ((long) this.bufferSize)) - this.replayIndex);
    }

    /* access modifiers changed from: private */
    public final int getTotalSize() {
        return this.bufferSize + this.queueSize;
    }

    private final long getBufferEndIndex() {
        return getHead() + ((long) this.bufferSize);
    }

    private final long getQueueEndIndex() {
        return getHead() + ((long) this.bufferSize) + ((long) this.queueSize);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0035, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<T> getReplayCache() {
        /*
            r12 = this;
            r0 = 0
            monitor-enter(r12)
            r1 = 0
            int r2 = r12.getReplaySize()     // Catch:{ all -> 0x0036 }
            if (r2 != 0) goto L_0x000f
            java.util.List r3 = kotlin.collections.CollectionsKt.emptyList()     // Catch:{ all -> 0x0036 }
            monitor-exit(r12)
            return r3
        L_0x000f:
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ all -> 0x0036 }
            r3.<init>(r2)     // Catch:{ all -> 0x0036 }
            java.lang.Object[] r4 = r12.buffer     // Catch:{ all -> 0x0036 }
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)     // Catch:{ all -> 0x0036 }
            r5 = 0
            if (r2 <= 0) goto L_0x002f
        L_0x001c:
            r6 = r5
            int r5 = r5 + 1
            r7 = r3
            java.util.Collection r7 = (java.util.Collection) r7     // Catch:{ all -> 0x0036 }
            long r8 = r12.replayIndex     // Catch:{ all -> 0x0036 }
            long r10 = (long) r6     // Catch:{ all -> 0x0036 }
            long r8 = r8 + r10
            java.lang.Object r8 = kotlinx.coroutines.flow.SharedFlowKt.getBufferAt(r4, r8)     // Catch:{ all -> 0x0036 }
            r7.add(r8)     // Catch:{ all -> 0x0036 }
            if (r5 < r2) goto L_0x001c
        L_0x002f:
            monitor-exit(r12)
            r0 = r3
            java.util.List r0 = (java.util.List) r0
            return r0
        L_0x0036:
            r1 = move-exception
            monitor-exit(r12)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.getReplayCache():java.util.List");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: kotlinx.coroutines.flow.SharedFlowSlot} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: kotlinx.coroutines.flow.SharedFlowImpl} */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008b, code lost:
        if (((kotlinx.coroutines.flow.SubscribedFlowCollector) r9).onSubscription(r10) == r1) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008d, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008e, code lost:
        r3 = (kotlinx.coroutines.Job) r10.getContext().get(kotlinx.coroutines.Job.Key);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x009d, code lost:
        r7 = r3;
        r3 = r9;
        r9 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a0, code lost:
        r5 = r4.tryTakeValue(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a7, code lost:
        if (r5 == kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a9, code lost:
        if (r9 != null) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ac, code lost:
        kotlinx.coroutines.JobKt.ensureActive(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00af, code lost:
        r10.L$0 = r4;
        r10.L$1 = r3;
        r10.L$2 = r2;
        r10.L$3 = r9;
        r10.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00be, code lost:
        if (r3.emit(r5, r10) != r1) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c1, code lost:
        r7 = r3;
        r3 = r9;
        r9 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c5, code lost:
        r10.L$0 = r4;
        r10.L$1 = r3;
        r10.L$2 = r2;
        r10.L$3 = r9;
        r10.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00d4, code lost:
        if (r4.awaitValue(r2, r10) != r1) goto L_0x00a0;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.flow.FlowCollector<? super T>, code=kotlinx.coroutines.flow.FlowCollector, for r9v0, types: [java.lang.Object, kotlinx.coroutines.flow.FlowCollector<? super T>] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof kotlinx.coroutines.flow.SharedFlowImpl$collect$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.flow.SharedFlowImpl$collect$1 r0 = (kotlinx.coroutines.flow.SharedFlowImpl$collect$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.SharedFlowImpl$collect$1 r0 = new kotlinx.coroutines.flow.SharedFlowImpl$collect$1
            r0.<init>(r8, r10)
        L_0x0019:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x006c;
                case 1: goto L_0x005a;
                case 2: goto L_0x0045;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002d:
            java.lang.Object r9 = r10.L$3
            kotlinx.coroutines.Job r9 = (kotlinx.coroutines.Job) r9
            java.lang.Object r2 = r10.L$2
            kotlinx.coroutines.flow.SharedFlowSlot r2 = (kotlinx.coroutines.flow.SharedFlowSlot) r2
            java.lang.Object r3 = r10.L$1
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            java.lang.Object r4 = r10.L$0
            kotlinx.coroutines.flow.SharedFlowImpl r4 = (kotlinx.coroutines.flow.SharedFlowImpl) r4
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x00d8 }
            r7 = r3
            r3 = r9
            r9 = r7
            goto L_0x00c4
        L_0x0045:
            java.lang.Object r9 = r10.L$3
            kotlinx.coroutines.Job r9 = (kotlinx.coroutines.Job) r9
            java.lang.Object r2 = r10.L$2
            kotlinx.coroutines.flow.SharedFlowSlot r2 = (kotlinx.coroutines.flow.SharedFlowSlot) r2
            java.lang.Object r3 = r10.L$1
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            java.lang.Object r4 = r10.L$0
            kotlinx.coroutines.flow.SharedFlowImpl r4 = (kotlinx.coroutines.flow.SharedFlowImpl) r4
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x00d8 }
            goto L_0x00d7
        L_0x005a:
            java.lang.Object r9 = r10.L$2
            r2 = r9
            kotlinx.coroutines.flow.SharedFlowSlot r2 = (kotlinx.coroutines.flow.SharedFlowSlot) r2
            java.lang.Object r9 = r10.L$1
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            java.lang.Object r3 = r10.L$0
            r4 = r3
            kotlinx.coroutines.flow.SharedFlowImpl r4 = (kotlinx.coroutines.flow.SharedFlowImpl) r4
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x00d8 }
            goto L_0x008e
        L_0x006c:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r8
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot r2 = r4.allocateSlot()
            kotlinx.coroutines.flow.SharedFlowSlot r2 = (kotlinx.coroutines.flow.SharedFlowSlot) r2
            boolean r3 = r9 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector     // Catch:{ all -> 0x00d8 }
            if (r3 == 0) goto L_0x008e
            r3 = r9
            kotlinx.coroutines.flow.SubscribedFlowCollector r3 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r3     // Catch:{ all -> 0x00d8 }
            r10.L$0 = r4     // Catch:{ all -> 0x00d8 }
            r10.L$1 = r9     // Catch:{ all -> 0x00d8 }
            r10.L$2 = r2     // Catch:{ all -> 0x00d8 }
            r5 = 1
            r10.label = r5     // Catch:{ all -> 0x00d8 }
            java.lang.Object r3 = r3.onSubscription(r10)     // Catch:{ all -> 0x00d8 }
            if (r3 != r1) goto L_0x008e
        L_0x008d:
            return r1
        L_0x008e:
            r3 = 0
            kotlin.coroutines.CoroutineContext r5 = r10.getContext()     // Catch:{ all -> 0x00d8 }
            kotlinx.coroutines.Job$Key r3 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x00d8 }
            kotlin.coroutines.CoroutineContext$Key r3 = (kotlin.coroutines.CoroutineContext.Key) r3     // Catch:{ all -> 0x00d8 }
            kotlin.coroutines.CoroutineContext$Element r3 = r5.get(r3)     // Catch:{ all -> 0x00d8 }
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3     // Catch:{ all -> 0x00d8 }
        L_0x009d:
            r7 = r3
            r3 = r9
            r9 = r7
        L_0x00a0:
            java.lang.Object r5 = r4.tryTakeValue(r2)     // Catch:{ all -> 0x00d8 }
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE     // Catch:{ all -> 0x00d8 }
            if (r5 == r6) goto L_0x00c5
            if (r9 != 0) goto L_0x00ac
            goto L_0x00af
        L_0x00ac:
            kotlinx.coroutines.JobKt.ensureActive((kotlinx.coroutines.Job) r9)     // Catch:{ all -> 0x00d8 }
        L_0x00af:
            r10.L$0 = r4     // Catch:{ all -> 0x00d8 }
            r10.L$1 = r3     // Catch:{ all -> 0x00d8 }
            r10.L$2 = r2     // Catch:{ all -> 0x00d8 }
            r10.L$3 = r9     // Catch:{ all -> 0x00d8 }
            r6 = 3
            r10.label = r6     // Catch:{ all -> 0x00d8 }
            java.lang.Object r6 = r3.emit(r5, r10)     // Catch:{ all -> 0x00d8 }
            if (r6 != r1) goto L_0x00c1
            goto L_0x008d
        L_0x00c1:
            r7 = r3
            r3 = r9
            r9 = r7
        L_0x00c4:
            goto L_0x009d
        L_0x00c5:
            r10.L$0 = r4     // Catch:{ all -> 0x00d8 }
            r10.L$1 = r3     // Catch:{ all -> 0x00d8 }
            r10.L$2 = r2     // Catch:{ all -> 0x00d8 }
            r10.L$3 = r9     // Catch:{ all -> 0x00d8 }
            r5 = 2
            r10.label = r5     // Catch:{ all -> 0x00d8 }
            java.lang.Object r5 = r4.awaitValue(r2, r10)     // Catch:{ all -> 0x00d8 }
            if (r5 != r1) goto L_0x00d7
            goto L_0x008d
        L_0x00d7:
            goto L_0x00a0
        L_0x00d8:
            r9 = move-exception
            r1 = r2
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot r1 = (kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot) r1
            r4.freeSlot(r1)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public boolean tryEmit(T value) {
        int i;
        boolean z;
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            i = 0;
            if (tryEmitLocked(value)) {
                continuationArr = findSlotsToResumeLocked(continuationArr);
                z = true;
            } else {
                z = false;
            }
        }
        boolean emitted = z;
        int length = continuationArr.length;
        while (i < length) {
            Continuation cont = continuationArr[i];
            i++;
            if (cont != null) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m64constructorimpl(unit));
            }
        }
        return emitted;
    }

    public Object emit(T value, Continuation<? super Unit> $completion) {
        if (tryEmit(value)) {
            return Unit.INSTANCE;
        }
        Object emitSuspend = emitSuspend(value, $completion);
        return emitSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitSuspend : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final boolean tryEmitLocked(T value) {
        if (getNCollectors() == 0) {
            return tryEmitNoCollectorsLocked(value);
        }
        if (this.bufferSize >= this.bufferCapacity && this.minCollectorIndex <= this.replayIndex) {
            switch (WhenMappings.$EnumSwitchMapping$0[this.onBufferOverflow.ordinal()]) {
                case 1:
                    return false;
                case 2:
                    return true;
            }
        }
        enqueueLocked(value);
        int i = this.bufferSize + 1;
        this.bufferSize = i;
        if (i > this.bufferCapacity) {
            dropOldestLocked();
        }
        if (getReplaySize() > this.replay) {
            updateBufferLocked(this.replayIndex + 1, this.minCollectorIndex, getBufferEndIndex(), getQueueEndIndex());
        }
        return true;
    }

    private final boolean tryEmitNoCollectorsLocked(T value) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getNCollectors() == 0)) {
                throw new AssertionError();
            }
        }
        if (this.replay == 0) {
            return true;
        }
        enqueueLocked(value);
        int i = this.bufferSize + 1;
        this.bufferSize = i;
        if (i > this.replay) {
            dropOldestLocked();
        }
        this.minCollectorIndex = getHead() + ((long) this.bufferSize);
        return true;
    }

    private final void dropOldestLocked() {
        Object[] objArr = this.buffer;
        Intrinsics.checkNotNull(objArr);
        SharedFlowKt.setBufferAt(objArr, getHead(), (Object) null);
        this.bufferSize--;
        long newHead = getHead() + 1;
        if (this.replayIndex < newHead) {
            this.replayIndex = newHead;
        }
        if (this.minCollectorIndex < newHead) {
            correctCollectorIndexesOnDropOldest(newHead);
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getHead() == newHead)) {
                throw new AssertionError();
            }
        }
    }

    private final void correctCollectorIndexesOnDropOldest(long newHead) {
        AbstractSharedFlowSlot[] $this$forEach$iv$iv;
        long j = newHead;
        AbstractSharedFlow this_$iv = this;
        if (!(this_$iv.nCollectors == 0 || ($this$forEach$iv$iv = this_$iv.slots) == null)) {
            for (AbstractSharedFlowSlot slot$iv : $this$forEach$iv$iv) {
                if (slot$iv != null) {
                    SharedFlowSlot slot = (SharedFlowSlot) slot$iv;
                    if (slot.index >= 0 && slot.index < j) {
                        slot.index = j;
                    }
                }
            }
        }
        this.minCollectorIndex = j;
    }

    /* access modifiers changed from: private */
    public final void enqueueLocked(Object item) {
        int curSize = getTotalSize();
        Object[] curBuffer = this.buffer;
        if (curBuffer == null) {
            curBuffer = growBuffer((Object[]) null, 0, 2);
        } else if (curSize >= curBuffer.length) {
            curBuffer = growBuffer(curBuffer, curSize, curBuffer.length * 2);
        }
        SharedFlowKt.setBufferAt(curBuffer, getHead() + ((long) curSize), item);
    }

    private final Object[] growBuffer(Object[] curBuffer, int curSize, int newSize) {
        int i = 0;
        if (newSize > 0) {
            Object[] newBuffer = new Object[newSize];
            this.buffer = newBuffer;
            if (curBuffer == null) {
                return newBuffer;
            }
            long head = getHead();
            if (curSize > 0) {
                do {
                    int i2 = i;
                    i++;
                    SharedFlowKt.setBufferAt(newBuffer, ((long) i2) + head, SharedFlowKt.getBufferAt(curBuffer, ((long) i2) + head));
                } while (i < curSize);
            }
            return newBuffer;
        }
        throw new IllegalStateException("Buffer size overflow".toString());
    }

    /* access modifiers changed from: private */
    public final Object emitSuspend(T value, Continuation<? super Unit> $completion) {
        Emitter emitter;
        Continuation[] continuationArr;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        Continuation[] continuationArr2 = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            if (tryEmitLocked(value)) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m64constructorimpl(unit));
                continuationArr = findSlotsToResumeLocked(continuationArr2);
                emitter = null;
            } else {
                Emitter it = new Emitter(this, ((long) getTotalSize()) + getHead(), value, cont);
                enqueueLocked(it);
                this.queueSize = this.queueSize + 1;
                if (this.bufferCapacity == 0) {
                    continuationArr2 = findSlotsToResumeLocked(continuationArr2);
                }
                continuationArr = continuationArr2;
                emitter = it;
            }
        }
        Emitter emitter2 = emitter;
        if (emitter2 != null) {
            CancellableContinuationKt.disposeOnCancellation(cont, emitter2);
        }
        int i = 0;
        int length = continuationArr.length;
        while (i < length) {
            Continuation r = continuationArr[i];
            i++;
            if (r != null) {
                Unit unit2 = Unit.INSTANCE;
                Result.Companion companion2 = Result.Companion;
                r.resumeWith(Result.m64constructorimpl(unit2));
            }
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final void cancelEmitter(Emitter emitter) {
        synchronized (this) {
            if (emitter.index >= getHead()) {
                Object[] buffer2 = this.buffer;
                Intrinsics.checkNotNull(buffer2);
                if (SharedFlowKt.getBufferAt(buffer2, emitter.index) == emitter) {
                    SharedFlowKt.setBufferAt(buffer2, emitter.index, SharedFlowKt.NO_VALUE);
                    cleanupTailLocked();
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    }

    public final long updateNewCollectorIndexLocked$kotlinx_coroutines_core() {
        long index = this.replayIndex;
        if (index < this.minCollectorIndex) {
            this.minCollectorIndex = index;
        }
        return index;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0173  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x017d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.Continuation<kotlin.Unit>[] updateCollectorIndexLocked$kotlinx_coroutines_core(long r27) {
        /*
            r26 = this;
            r9 = r26
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x001b
            r0 = 0
            long r1 = r9.minCollectorIndex
            int r1 = (r27 > r1 ? 1 : (r27 == r1 ? 0 : -1))
            if (r1 < 0) goto L_0x0011
            r0 = 1
            goto L_0x0012
        L_0x0011:
            r0 = 0
        L_0x0012:
            if (r0 == 0) goto L_0x0015
            goto L_0x001b
        L_0x0015:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001b:
            long r0 = r9.minCollectorIndex
            int r0 = (r27 > r0 ? 1 : (r27 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x0024
            kotlin.coroutines.Continuation<kotlin.Unit>[] r0 = kotlinx.coroutines.flow.internal.AbstractSharedFlowKt.EMPTY_RESUMES
            return r0
        L_0x0024:
            long r12 = r26.getHead()
            r0 = 0
            int r2 = r9.bufferSize
            long r2 = (long) r2
            long r2 = r2 + r12
            int r0 = r9.bufferCapacity
            r4 = 1
            if (r0 != 0) goto L_0x0039
            int r0 = r9.queueSize
            if (r0 <= 0) goto L_0x0039
            long r2 = r2 + r4
        L_0x0039:
            r0 = r9
            kotlinx.coroutines.flow.internal.AbstractSharedFlow r0 = (kotlinx.coroutines.flow.internal.AbstractSharedFlow) r0
            r1 = 0
            int r6 = r0.nCollectors
            if (r6 != 0) goto L_0x0046
            r20 = r12
            goto L_0x0080
        L_0x0046:
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot[] r6 = r0.slots
            if (r6 != 0) goto L_0x004f
            r20 = r12
            goto L_0x007f
        L_0x004f:
            r7 = 0
            int r8 = r6.length
            r14 = 0
        L_0x0052:
            if (r14 >= r8) goto L_0x007d
            r15 = r6[r14]
            r16 = r15
            r17 = 0
            if (r16 == 0) goto L_0x0075
            r10 = r16
            kotlinx.coroutines.flow.SharedFlowSlot r10 = (kotlinx.coroutines.flow.SharedFlowSlot) r10
            r19 = 0
            r20 = r12
            long r11 = r10.index
            r22 = 0
            int r11 = (r11 > r22 ? 1 : (r11 == r22 ? 0 : -1))
            if (r11 < 0) goto L_0x0074
            long r11 = r10.index
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 >= 0) goto L_0x0074
            long r2 = r10.index
        L_0x0074:
            goto L_0x0077
        L_0x0075:
            r20 = r12
        L_0x0077:
            int r14 = r14 + 1
            r12 = r20
            goto L_0x0052
        L_0x007d:
            r20 = r12
        L_0x007f:
        L_0x0080:
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x0099
            r0 = 0
            long r6 = r9.minCollectorIndex
            int r1 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r1 < 0) goto L_0x008f
            r0 = 1
            goto L_0x0090
        L_0x008f:
            r0 = 0
        L_0x0090:
            if (r0 == 0) goto L_0x0093
            goto L_0x0099
        L_0x0093:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x0099:
            long r0 = r9.minCollectorIndex
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x00a2
            kotlin.coroutines.Continuation<kotlin.Unit>[] r0 = kotlinx.coroutines.flow.internal.AbstractSharedFlowKt.EMPTY_RESUMES
            return r0
        L_0x00a2:
            long r0 = r26.getBufferEndIndex()
            int r6 = r26.getNCollectors()
            if (r6 <= 0) goto L_0x00b9
            long r6 = r0 - r2
            int r6 = (int) r6
            int r7 = r9.queueSize
            int r8 = r9.bufferCapacity
            int r8 = r8 - r6
            int r6 = java.lang.Math.min(r7, r8)
            goto L_0x00bb
        L_0x00b9:
            int r6 = r9.queueSize
        L_0x00bb:
            r10 = r6
            kotlin.coroutines.Continuation<kotlin.Unit>[] r6 = kotlinx.coroutines.flow.internal.AbstractSharedFlowKt.EMPTY_RESUMES
            int r7 = r9.queueSize
            long r7 = (long) r7
            long r11 = r0 + r7
            if (r10 <= 0) goto L_0x011d
            kotlin.coroutines.Continuation[] r6 = new kotlin.coroutines.Continuation[r10]
            r7 = 0
            java.lang.Object[] r8 = r9.buffer
            kotlin.jvm.internal.Intrinsics.checkNotNull(r8)
            int r13 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r13 >= 0) goto L_0x0119
            r13 = r0
        L_0x00d2:
            r15 = r0
            long r0 = r0 + r4
            r4 = r15
            java.lang.Object r15 = kotlinx.coroutines.flow.SharedFlowKt.getBufferAt(r8, r4)
            r16 = r2
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE
            if (r15 == r2) goto L_0x010d
            if (r15 == 0) goto L_0x0105
            r2 = r15
            kotlinx.coroutines.flow.SharedFlowImpl$Emitter r2 = (kotlinx.coroutines.flow.SharedFlowImpl.Emitter) r2
            int r2 = r7 + 1
            r3 = r15
            kotlinx.coroutines.flow.SharedFlowImpl$Emitter r3 = (kotlinx.coroutines.flow.SharedFlowImpl.Emitter) r3
            kotlin.coroutines.Continuation<kotlin.Unit> r3 = r3.cont
            r6[r7] = r3
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE
            kotlinx.coroutines.flow.SharedFlowKt.setBufferAt(r8, r4, r3)
            r3 = r15
            kotlinx.coroutines.flow.SharedFlowImpl$Emitter r3 = (kotlinx.coroutines.flow.SharedFlowImpl.Emitter) r3
            java.lang.Object r3 = r3.value
            kotlinx.coroutines.flow.SharedFlowKt.setBufferAt(r8, r13, r3)
            r22 = 1
            long r13 = r13 + r22
            if (r2 < r10) goto L_0x0103
            r0 = r13
            r13 = r6
            goto L_0x0120
        L_0x0103:
            r7 = r2
            goto L_0x010d
        L_0x0105:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r1 = "null cannot be cast to non-null type kotlinx.coroutines.flow.SharedFlowImpl.Emitter"
            r0.<init>(r1)
            throw r0
        L_0x010d:
            int r2 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r2 < 0) goto L_0x0114
            r0 = r13
            r13 = r6
            goto L_0x0120
        L_0x0114:
            r2 = r16
            r4 = 1
            goto L_0x00d2
        L_0x0119:
            r16 = r2
            r13 = r6
            goto L_0x0120
        L_0x011d:
            r16 = r2
            r13 = r6
        L_0x0120:
            long r2 = r0 - r20
            int r14 = (int) r2
            int r2 = r26.getNCollectors()
            if (r2 != 0) goto L_0x012c
            r2 = r0
            r16 = r2
        L_0x012c:
            long r2 = r9.replayIndex
            int r4 = r9.replay
            int r4 = java.lang.Math.min(r4, r14)
            long r4 = (long) r4
            long r4 = r0 - r4
            long r2 = java.lang.Math.max(r2, r4)
            int r4 = r9.bufferCapacity
            if (r4 != 0) goto L_0x015d
            int r4 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r4 >= 0) goto L_0x015d
            java.lang.Object[] r4 = r9.buffer
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            java.lang.Object r4 = kotlinx.coroutines.flow.SharedFlowKt.getBufferAt(r4, r2)
            kotlinx.coroutines.internal.Symbol r5 = kotlinx.coroutines.flow.SharedFlowKt.NO_VALUE
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r4, (java.lang.Object) r5)
            if (r4 == 0) goto L_0x015d
            r4 = 1
            long r0 = r0 + r4
            long r2 = r2 + r4
            r22 = r0
            r24 = r2
            goto L_0x0161
        L_0x015d:
            r22 = r0
            r24 = r2
        L_0x0161:
            r0 = r26
            r1 = r24
            r3 = r16
            r5 = r22
            r7 = r11
            r0.updateBufferLocked(r1, r3, r5, r7)
            r26.cleanupTailLocked()
            int r0 = r13.length
            if (r0 != 0) goto L_0x0176
            r18 = 1
            goto L_0x0178
        L_0x0176:
            r18 = 0
        L_0x0178:
            r0 = 1
            r0 = r18 ^ 1
            if (r0 == 0) goto L_0x0181
            kotlin.coroutines.Continuation[] r13 = r9.findSlotsToResumeLocked(r13)
        L_0x0181:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.updateCollectorIndexLocked$kotlinx_coroutines_core(long):kotlin.coroutines.Continuation[]");
    }

    private final void updateBufferLocked(long newReplayIndex, long newMinCollectorIndex, long newBufferEndIndex, long newQueueEndIndex) {
        long j = newReplayIndex;
        long j2 = newMinCollectorIndex;
        long newHead = Math.min(j2, j);
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((newHead >= getHead() ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        long head = getHead();
        if (head < newHead) {
            do {
                long index = head;
                head++;
                Object[] objArr = this.buffer;
                Intrinsics.checkNotNull(objArr);
                SharedFlowKt.setBufferAt(objArr, index, (Object) null);
            } while (head < newHead);
        }
        this.replayIndex = j;
        this.minCollectorIndex = j2;
        this.bufferSize = (int) (newBufferEndIndex - newHead);
        this.queueSize = (int) (newQueueEndIndex - newBufferEndIndex);
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((this.bufferSize >= 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((this.queueSize >= 0 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (this.replayIndex > getHead() + ((long) this.bufferSize)) {
                z = false;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
    }

    private final void cleanupTailLocked() {
        if (this.bufferCapacity != 0 || this.queueSize > 1) {
            Object[] buffer2 = this.buffer;
            Intrinsics.checkNotNull(buffer2);
            while (this.queueSize > 0 && SharedFlowKt.getBufferAt(buffer2, (getHead() + ((long) getTotalSize())) - 1) == SharedFlowKt.NO_VALUE) {
                this.queueSize--;
                SharedFlowKt.setBufferAt(buffer2, getHead() + ((long) getTotalSize()), (Object) null);
            }
        }
    }

    private final Object tryTakeValue(SharedFlowSlot slot) {
        Object obj;
        Continuation[] continuationArr = AbstractSharedFlowKt.EMPTY_RESUMES;
        synchronized (this) {
            long index = tryPeekLocked(slot);
            if (index < 0) {
                obj = SharedFlowKt.NO_VALUE;
            } else {
                long oldIndex = slot.index;
                Object newValue = getPeekedValueLockedAt(index);
                slot.index = 1 + index;
                continuationArr = updateCollectorIndexLocked$kotlinx_coroutines_core(oldIndex);
                obj = newValue;
            }
        }
        Object value = obj;
        int i = 0;
        int length = continuationArr.length;
        while (i < length) {
            Continuation resume = continuationArr[i];
            i++;
            if (resume != null) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                resume.resumeWith(Result.m64constructorimpl(unit));
            }
        }
        return value;
    }

    /* access modifiers changed from: private */
    public final long tryPeekLocked(SharedFlowSlot slot) {
        long index = slot.index;
        if (index < getBufferEndIndex()) {
            return index;
        }
        if (this.bufferCapacity <= 0 && index <= getHead() && this.queueSize != 0) {
            return index;
        }
        return -1;
    }

    private final Object getPeekedValueLockedAt(long index) {
        Object[] objArr = this.buffer;
        Intrinsics.checkNotNull(objArr);
        Object item = SharedFlowKt.getBufferAt(objArr, index);
        if (item instanceof Emitter) {
            return ((Emitter) item).value;
        }
        return item;
    }

    /* access modifiers changed from: private */
    public final Object awaitValue(SharedFlowSlot slot, Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        synchronized (this) {
            if (tryPeekLocked(slot) < 0) {
                slot.cont = cont;
                slot.cont = cont;
            } else {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m64constructorimpl(unit));
            }
            Unit unit2 = Unit.INSTANCE;
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* JADX WARNING: type inference failed for: r0v13, types: [java.lang.Object[], java.lang.Object] */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.coroutines.Continuation<kotlin.Unit>[] findSlotsToResumeLocked(kotlin.coroutines.Continuation<kotlin.Unit>[] r21) {
        /*
            r20 = this;
            r0 = r20
            r1 = 0
            r1 = r21
            r2 = 0
            r3 = r21
            int r2 = r3.length
            r4 = r0
            kotlinx.coroutines.flow.internal.AbstractSharedFlow r4 = (kotlinx.coroutines.flow.internal.AbstractSharedFlow) r4
            r5 = 0
            int r6 = r4.nCollectors
            if (r6 != 0) goto L_0x0015
            goto L_0x007d
        L_0x0015:
            kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot[] r6 = r4.slots
            if (r6 != 0) goto L_0x001d
            goto L_0x007c
        L_0x001d:
            r7 = 0
            int r8 = r6.length
            r9 = 0
        L_0x0020:
            if (r9 >= r8) goto L_0x007a
            r10 = r6[r9]
            r11 = r10
            r12 = 0
            if (r11 == 0) goto L_0x006e
            r13 = r11
            kotlinx.coroutines.flow.SharedFlowSlot r13 = (kotlinx.coroutines.flow.SharedFlowSlot) r13
            r14 = 0
            kotlin.coroutines.Continuation<? super kotlin.Unit> r15 = r13.cont
            if (r15 != 0) goto L_0x0033
            r16 = r1
            goto L_0x0070
        L_0x0033:
            long r16 = r0.tryPeekLocked(r13)
            r18 = 0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 >= 0) goto L_0x0040
            r16 = r1
            goto L_0x0070
        L_0x0040:
            r0 = r1
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            int r0 = r0.length
            if (r2 < r0) goto L_0x0060
            r0 = r1
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            r3 = r1
            java.lang.Object[] r3 = (java.lang.Object[]) r3
            int r3 = r3.length
            r16 = r1
            r1 = 2
            int r3 = r3 * r1
            int r1 = java.lang.Math.max(r1, r3)
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r1)
            java.lang.String r1 = "java.util.Arrays.copyOf(this, newSize)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            r1 = r0
            goto L_0x0062
        L_0x0060:
            r16 = r1
        L_0x0062:
            r0 = r1
            kotlin.coroutines.Continuation[] r0 = (kotlin.coroutines.Continuation[]) r0
            int r3 = r2 + 1
            r0[r2] = r15
            r0 = 0
            r13.cont = r0
            r2 = r3
            goto L_0x0072
        L_0x006e:
            r16 = r1
        L_0x0070:
            r1 = r16
        L_0x0072:
            int r9 = r9 + 1
            r0 = r20
            r3 = r21
            goto L_0x0020
        L_0x007a:
            r16 = r1
        L_0x007c:
        L_0x007d:
            r0 = r1
            kotlin.coroutines.Continuation[] r0 = (kotlin.coroutines.Continuation[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SharedFlowImpl.findSlotsToResumeLocked(kotlin.coroutines.Continuation[]):kotlin.coroutines.Continuation[]");
    }

    /* access modifiers changed from: protected */
    public SharedFlowSlot createSlot() {
        return new SharedFlowSlot();
    }

    /* access modifiers changed from: protected */
    public SharedFlowSlot[] createSlotArray(int size) {
        return new SharedFlowSlot[size];
    }

    public void resetReplayCache() {
        synchronized (this) {
            updateBufferLocked(getBufferEndIndex(), this.minCollectorIndex, getBufferEndIndex(), getQueueEndIndex());
            Unit unit = Unit.INSTANCE;
        }
    }

    public Flow<T> fuse(CoroutineContext context, int capacity, BufferOverflow onBufferOverflow2) {
        return SharedFlowKt.fuseSharedFlow(this, context, capacity, onBufferOverflow2);
    }

    @Metadata(mo11814d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B1\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\nH\u0016R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00038\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo11815d2 = {"Lkotlinx/coroutines/flow/SharedFlowImpl$Emitter;", "Lkotlinx/coroutines/DisposableHandle;", "flow", "Lkotlinx/coroutines/flow/SharedFlowImpl;", "index", "", "value", "", "cont", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/SharedFlowImpl;JLjava/lang/Object;Lkotlin/coroutines/Continuation;)V", "dispose", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
    /* compiled from: SharedFlow.kt */
    private static final class Emitter implements DisposableHandle {
        public final Continuation<Unit> cont;
        public final SharedFlowImpl<?> flow;
        public long index;
        public final Object value;

        public Emitter(SharedFlowImpl<?> flow2, long index2, Object value2, Continuation<? super Unit> cont2) {
            this.flow = flow2;
            this.index = index2;
            this.value = value2;
            this.cont = cont2;
        }

        public void dispose() {
            this.flow.cancelEmitter(this);
        }
    }
}
