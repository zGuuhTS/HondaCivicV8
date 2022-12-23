package androidx.window.layout;

import android.app.Activity;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo11814d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, mo11815d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Landroidx/window/layout/WindowLayoutInfo;"}, mo11816k = 3, mo11817mv = {1, 6, 0}, mo11819xi = 48)
@DebugMetadata(mo12529c = "androidx.window.layout.WindowInfoTrackerImpl$windowLayoutInfo$1", mo12530f = "WindowInfoTrackerImpl.kt", mo12531i = {0, 0, 1, 1}, mo12532l = {54, 55}, mo12533m = "invokeSuspend", mo12534n = {"$this$flow", "listener", "$this$flow", "listener"}, mo12535s = {"L$0", "L$1", "L$0", "L$1"})
/* compiled from: WindowInfoTrackerImpl.kt */
final class WindowInfoTrackerImpl$windowLayoutInfo$1 extends SuspendLambda implements Function2<FlowCollector<? super WindowLayoutInfo>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Activity $activity;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ WindowInfoTrackerImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    WindowInfoTrackerImpl$windowLayoutInfo$1(WindowInfoTrackerImpl windowInfoTrackerImpl, Activity activity, Continuation<? super WindowInfoTrackerImpl$windowLayoutInfo$1> continuation) {
        super(2, continuation);
        this.this$0 = windowInfoTrackerImpl;
        this.$activity = activity;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        WindowInfoTrackerImpl$windowLayoutInfo$1 windowInfoTrackerImpl$windowLayoutInfo$1 = new WindowInfoTrackerImpl$windowLayoutInfo$1(this.this$0, this.$activity, continuation);
        windowInfoTrackerImpl$windowLayoutInfo$1.L$0 = obj;
        return windowInfoTrackerImpl$windowLayoutInfo$1;
    }

    public final Object invoke(FlowCollector<? super WindowLayoutInfo> flowCollector, Continuation<? super Unit> continuation) {
        return ((WindowInfoTrackerImpl$windowLayoutInfo$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: kotlinx.coroutines.flow.FlowCollector} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0076  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            switch(r1) {
                case 0: goto L_0x0039;
                case 1: goto L_0x0024;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0011:
            r1 = r9
            java.lang.Object r2 = r1.L$2
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$1
            androidx.core.util.Consumer r3 = (androidx.core.util.Consumer) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x00b3 }
            r5 = r2
            goto L_0x009f
        L_0x0024:
            r1 = r9
            java.lang.Object r2 = r1.L$2
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r3 = r1.L$1
            androidx.core.util.Consumer r3 = (androidx.core.util.Consumer) r3
            java.lang.Object r4 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x00b3 }
            r5 = r2
            r2 = r1
            r1 = r0
            r0 = r10
            goto L_0x007b
        L_0x0039:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r2 = r1.L$0
            r4 = r2
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            r2 = 10
            kotlinx.coroutines.channels.BufferOverflow r3 = kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
            r5 = 4
            r6 = 0
            kotlinx.coroutines.channels.Channel r2 = kotlinx.coroutines.channels.ChannelKt.Channel$default(r2, r3, r6, r5, r6)
            androidx.window.layout.-$$Lambda$WindowInfoTrackerImpl$windowLayoutInfo$1$LgDWJbk4b494d79uZZm3iJ0WM6A r3 = new androidx.window.layout.-$$Lambda$WindowInfoTrackerImpl$windowLayoutInfo$1$LgDWJbk4b494d79uZZm3iJ0WM6A
            r3.<init>()
            androidx.window.layout.WindowInfoTrackerImpl r5 = r1.this$0
            androidx.window.layout.WindowBackend r5 = r5.windowBackend
            android.app.Activity r6 = r1.$activity
            androidx.window.layout.-$$Lambda$PNiE7SuEFxRjAZH7pJpZIFOFjWg r7 = androidx.window.layout.$$Lambda$PNiE7SuEFxRjAZH7pJpZIFOFjWg.INSTANCE
            r5.registerLayoutChangeCallback(r6, r7, r3)
            kotlinx.coroutines.channels.ChannelIterator r5 = r2.iterator()     // Catch:{ all -> 0x00b3 }
        L_0x0063:
            r2 = r1
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2     // Catch:{ all -> 0x00b3 }
            r1.L$0 = r4     // Catch:{ all -> 0x00b3 }
            r1.L$1 = r3     // Catch:{ all -> 0x00b3 }
            r1.L$2 = r5     // Catch:{ all -> 0x00b3 }
            r6 = 1
            r1.label = r6     // Catch:{ all -> 0x00b3 }
            java.lang.Object r2 = r5.hasNext(r2)     // Catch:{ all -> 0x00b3 }
            if (r2 != r0) goto L_0x0076
            return r0
        L_0x0076:
            r8 = r0
            r0 = r10
            r10 = r2
            r2 = r1
            r1 = r8
        L_0x007b:
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch:{ all -> 0x00ad }
            boolean r10 = r10.booleanValue()     // Catch:{ all -> 0x00ad }
            if (r10 == 0) goto L_0x00a0
            java.lang.Object r10 = r5.next()     // Catch:{ all -> 0x00ad }
            androidx.window.layout.WindowLayoutInfo r10 = (androidx.window.layout.WindowLayoutInfo) r10     // Catch:{ all -> 0x00ad }
            r6 = r2
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6     // Catch:{ all -> 0x00ad }
            r2.L$0 = r4     // Catch:{ all -> 0x00ad }
            r2.L$1 = r3     // Catch:{ all -> 0x00ad }
            r2.L$2 = r5     // Catch:{ all -> 0x00ad }
            r7 = 2
            r2.label = r7     // Catch:{ all -> 0x00ad }
            java.lang.Object r6 = r4.emit(r10, r6)     // Catch:{ all -> 0x00ad }
            if (r6 != r1) goto L_0x009c
            return r1
        L_0x009c:
            r10 = r0
            r0 = r1
            r1 = r2
        L_0x009f:
            goto L_0x0063
        L_0x00a0:
            androidx.window.layout.WindowInfoTrackerImpl r10 = r2.this$0
            androidx.window.layout.WindowBackend r10 = r10.windowBackend
            r10.unregisterLayoutChangeCallback(r3)
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L_0x00ad:
            r10 = move-exception
            r1 = r2
            r8 = r0
            r0 = r10
            r10 = r8
            goto L_0x00b4
        L_0x00b3:
            r0 = move-exception
        L_0x00b4:
            androidx.window.layout.WindowInfoTrackerImpl r2 = r1.this$0
            androidx.window.layout.WindowBackend r2 = r2.windowBackend
            r2.unregisterLayoutChangeCallback(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.window.layout.WindowInfoTrackerImpl$windowLayoutInfo$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* renamed from: invokeSuspend$lambda-0  reason: not valid java name */
    public static final void m59invokeSuspend$lambda0(Channel $channel, WindowLayoutInfo info) {
        Intrinsics.checkNotNullExpressionValue(info, "info");
        $channel.m1584trySendJP2dKIU(info);
    }
}
