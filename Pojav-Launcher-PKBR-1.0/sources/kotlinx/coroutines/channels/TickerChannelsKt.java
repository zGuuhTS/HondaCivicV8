package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(mo11814d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a/\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a/\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a4\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, mo11815d2 = {"fixedDelayTicker", "", "delayMillis", "", "initialDelayMillis", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(JJLkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fixedPeriodTicker", "ticker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "context", "Lkotlin/coroutines/CoroutineContext;", "mode", "Lkotlinx/coroutines/channels/TickerMode;", "kotlinx-coroutines-core"}, mo11816k = 2, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: TickerChannels.kt */
public final class TickerChannelsKt {
    public static /* synthetic */ ReceiveChannel ticker$default(long j, long j2, CoroutineContext coroutineContext, TickerMode tickerMode, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        if ((i & 4) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 8) != 0) {
            tickerMode = TickerMode.FIXED_PERIOD;
        }
        return ticker(j, j2, coroutineContext, tickerMode);
    }

    public static final ReceiveChannel<Unit> ticker(long delayMillis, long initialDelayMillis, CoroutineContext context, TickerMode mode) {
        long j = delayMillis;
        long j2 = initialDelayMillis;
        boolean z = true;
        if (j >= 0) {
            if (j2 < 0) {
                z = false;
            }
            if (z) {
                return ProduceKt.produce(GlobalScope.INSTANCE, Dispatchers.getUnconfined().plus(context), 0, new TickerChannelsKt$ticker$3(mode, delayMillis, initialDelayMillis, (Continuation<? super TickerChannelsKt$ticker$3>) null));
            }
            CoroutineContext coroutineContext = context;
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + j2 + " ms").toString());
        }
        CoroutineContext coroutineContext2 = context;
        throw new IllegalArgumentException(("Expected non-negative delay, but has " + j + " ms").toString());
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0094, code lost:
        r10 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0098, code lost:
        r6 = r6 + r10;
        r8 = kotlin.Unit.INSTANCE;
        r0.L$0 = r3;
        r0.J$0 = r6;
        r0.J$1 = r10;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a9, code lost:
        if (r3.send(r8, r0) != r2) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00ab, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ac, code lost:
        r8 = r6;
        r6 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ae, code lost:
        r10 = kotlinx.coroutines.AbstractTimeSourceKt.getTimeSource();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b2, code lost:
        if (r10 != null) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b4, code lost:
        r10 = java.lang.System.nanoTime();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b9, code lost:
        r10 = r10.nanoTime();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00bd, code lost:
        r12 = kotlin.ranges.RangesKt.coerceAtLeast(r8 - r10, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c5, code lost:
        if (r12 != r4) goto L_0x00ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c9, code lost:
        if (r6 == r4) goto L_0x00ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00cb, code lost:
        r8 = r6 - ((r10 - r8) % r6);
        r10 = r10 + r8;
        r14 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r8);
        r0.L$0 = r3;
        r0.J$0 = r10;
        r0.J$1 = r6;
        r0.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00e2, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r14, r0) != r2) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00e4, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00e5, code lost:
        r8 = r6;
        r6 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e7, code lost:
        r6 = r10;
        r4 = 0;
        r10 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ec, code lost:
        r4 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r12);
        r0.L$0 = r3;
        r0.J$0 = r8;
        r0.J$1 = r6;
        r0.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00fd, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r4, r0) != r2) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ff, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0100, code lost:
        r10 = r6;
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0102, code lost:
        r4 = 0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object fixedPeriodTicker(long r16, long r18, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r20, kotlin.coroutines.Continuation<? super kotlin.Unit> r21) {
        /*
            r0 = r21
            boolean r1 = r0 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            if (r1 == 0) goto L_0x0016
            r1 = r0
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r1 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r1 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            r1.<init>(r0)
        L_0x001b:
            r0 = r1
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x0066;
                case 1: goto L_0x005a;
                case 2: goto L_0x004e;
                case 3: goto L_0x0040;
                case 4: goto L_0x0031;
                default: goto L_0x0029;
            }
        L_0x0029:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0031:
            long r6 = r0.J$1
            long r8 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            r10 = r6
            r6 = r8
            goto L_0x0102
        L_0x0040:
            r6 = r4
            long r8 = r0.J$1
            long r10 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x00e7
        L_0x004e:
            long r6 = r0.J$1
            long r8 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x00ae
        L_0x005a:
            long r6 = r0.J$1
            long r8 = r0.J$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r3 = (kotlinx.coroutines.channels.SendChannel) r3
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0094
        L_0x0066:
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r16
            r6 = r18
            r3 = r20
            kotlinx.coroutines.AbstractTimeSource r10 = kotlinx.coroutines.AbstractTimeSourceKt.getTimeSource()
            if (r10 != 0) goto L_0x007a
            long r10 = java.lang.System.nanoTime()
            goto L_0x007e
        L_0x007a:
            long r10 = r10.nanoTime()
        L_0x007e:
            long r12 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r6)
            long r10 = r10 + r12
            r0.L$0 = r3
            r0.J$0 = r8
            r0.J$1 = r10
            r12 = 1
            r0.label = r12
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r6, r0)
            if (r6 != r2) goto L_0x0093
            return r2
        L_0x0093:
            r6 = r10
        L_0x0094:
            long r10 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r8)
        L_0x0098:
            long r6 = r6 + r10
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            r0.L$0 = r3
            r0.J$0 = r6
            r0.J$1 = r10
            r9 = 2
            r0.label = r9
            java.lang.Object r8 = r3.send(r8, r0)
            if (r8 != r2) goto L_0x00ac
            return r2
        L_0x00ac:
            r8 = r6
            r6 = r10
        L_0x00ae:
            kotlinx.coroutines.AbstractTimeSource r10 = kotlinx.coroutines.AbstractTimeSourceKt.getTimeSource()
            if (r10 != 0) goto L_0x00b9
            long r10 = java.lang.System.nanoTime()
            goto L_0x00bd
        L_0x00b9:
            long r10 = r10.nanoTime()
        L_0x00bd:
            long r12 = r8 - r10
            long r12 = kotlin.ranges.RangesKt.coerceAtLeast((long) r12, (long) r4)
            int r14 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r14 != 0) goto L_0x00ec
            int r14 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r14 == 0) goto L_0x00ec
            long r14 = r10 - r8
            long r14 = r14 % r6
            long r8 = r6 - r14
            long r10 = r10 + r8
            long r14 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r8)
            r0.L$0 = r3
            r0.J$0 = r10
            r0.J$1 = r6
            r4 = 3
            r0.label = r4
            java.lang.Object r4 = kotlinx.coroutines.DelayKt.delay(r14, r0)
            if (r4 != r2) goto L_0x00e5
            return r2
        L_0x00e5:
            r8 = r6
            r6 = r12
        L_0x00e7:
            r6 = r10
            r4 = 0
            r10 = r8
            goto L_0x0098
        L_0x00ec:
            long r4 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r12)
            r0.L$0 = r3
            r0.J$0 = r8
            r0.J$1 = r6
            r10 = 4
            r0.label = r10
            java.lang.Object r4 = kotlinx.coroutines.DelayKt.delay(r4, r0)
            if (r4 != r2) goto L_0x0100
            return r2
        L_0x0100:
            r10 = r6
            r6 = r8
        L_0x0102:
            r4 = 0
            goto L_0x0098
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedPeriodTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x005b, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006b, code lost:
        if (r5.send(r6, r8) != r1) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006e, code lost:
        r8.L$0 = r5;
        r8.J$0 = r3;
        r8.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0079, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r3, r8) != r1) goto L_0x005e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object fixedDelayTicker(long r3, long r5, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            r0.<init>(r8)
        L_0x0019:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x0041;
                case 2: goto L_0x0037;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r3.<init>(r4)
            throw r3
        L_0x002d:
            long r3 = r8.J$0
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x007c
        L_0x0037:
            long r3 = r8.J$0
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x006e
        L_0x0041:
            long r3 = r8.J$0
            java.lang.Object r5 = r8.L$0
            kotlinx.coroutines.channels.SendChannel r5 = (kotlinx.coroutines.channels.SendChannel) r5
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x005d
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r0)
            r8.L$0 = r7
            r8.J$0 = r3
            r2 = 1
            r8.label = r2
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r5, r8)
            if (r5 != r1) goto L_0x005c
        L_0x005b:
            return r1
        L_0x005c:
            r5 = r7
        L_0x005d:
        L_0x005e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            r8.L$0 = r5
            r8.J$0 = r3
            r7 = 2
            r8.label = r7
            java.lang.Object r6 = r5.send(r6, r8)
            if (r6 != r1) goto L_0x006e
            goto L_0x005b
        L_0x006e:
            r8.L$0 = r5
            r8.J$0 = r3
            r6 = 3
            r8.label = r6
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r3, r8)
            if (r6 != r1) goto L_0x007c
            goto L_0x005b
        L_0x007c:
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedDelayTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
