package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.ExceptionsKt;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(mo11814d1 = {"\u0000>\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\u001a\u001a\u0010\u0002\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0001\u001aC\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\t0\n2\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0004\u0012\u0004\u0012\u0002H\b0\f¢\u0006\u0002\b\rH\b¢\u0006\u0002\u0010\u000e\u001aP\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\b*\b\u0012\u0004\u0012\u0002H\t0\u00042\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0004\u0012\u0004\u0012\u0002H\b0\f¢\u0006\u0002\b\rH\b\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000f\u001a5\u0010\u0010\u001a\u00020\u0003\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\n2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00030\fHHø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a5\u0010\u0010\u001a\u00020\u0003\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u00042\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00030\fHHø\u0001\u0000¢\u0006\u0002\u0010\u0013\u001a$\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\t0\u0015\"\b\b\u0000\u0010\t*\u00020\u0016*\b\u0012\u0004\u0012\u0002H\t0\u0004H\u0007\u001a'\u0010\u0017\u001a\u0004\u0018\u0001H\t\"\b\b\u0000\u0010\t*\u00020\u0016*\b\u0012\u0004\u0012\u0002H\t0\u0004H@ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u001a'\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\t0\u001a\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\u0004H@ø\u0001\u0000¢\u0006\u0002\u0010\u0018\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, mo11815d2 = {"DEFAULT_CLOSE_MESSAGE", "", "cancelConsumed", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "cause", "", "consume", "R", "E", "Lkotlinx/coroutines/channels/BroadcastChannel;", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "action", "(Lkotlinx/coroutines/channels/BroadcastChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onReceiveOrNull", "Lkotlinx/coroutines/selects/SelectClause1;", "", "receiveOrNull", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toList", "", "kotlinx-coroutines-core"}, mo11816k = 5, mo11817mv = {1, 5, 1}, mo11819xi = 48, mo11820xs = "kotlinx/coroutines/channels/ChannelsKt")
/* compiled from: Channels.common.kt */
final /* synthetic */ class ChannelsKt__Channels_commonKt {
    public static final <E, R> R consume(BroadcastChannel<E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        ReceiveChannel channel = $this$consume.openSubscription();
        try {
            return block.invoke(channel);
        } finally {
            InlineMarker.finallyStart(1);
            ReceiveChannel.DefaultImpls.cancel$default(channel, (CancellationException) null, 1, (Object) null);
            InlineMarker.finallyEnd(1);
        }
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Deprecated in the favour of 'receiveCatching'", replaceWith = @ReplaceWith(expression = "receiveCatching().getOrNull()", imports = {}))
    public static final <E> Object receiveOrNull(ReceiveChannel<? extends E> $this$receiveOrNull, Continuation<? super E> $completion) {
        return $this$receiveOrNull.receiveOrNull($completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Deprecated in the favour of 'onReceiveCatching'")
    public static final <E> SelectClause1<E> onReceiveOrNull(ReceiveChannel<? extends E> $this$onReceiveOrNull) {
        return $this$onReceiveOrNull.getOnReceiveOrNull();
    }

    public static final <E, R> R consume(ReceiveChannel<? extends E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        Throwable cause;
        try {
            R invoke = block.invoke($this$consume);
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume, (Throwable) null);
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable e) {
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consume, cause);
            InlineMarker.finallyEnd(1);
            throw e;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r12.L$0 = r7;
        r12.L$1 = r6;
        r12.L$2 = r4;
        r12.label = 1;
        r8 = r4.hasNext(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006d, code lost:
        if (r8 != r1) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006f, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0070, code lost:
        r9 = r1;
        r1 = r0;
        r0 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r2;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007f, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x0090;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0081, code lost:
        r8.invoke(r5.next());
        r0 = r1;
        r1 = r2;
        r2 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0090, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0093, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009f, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a0, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a1, code lost:
        r5 = r6;
        r6 = r7;
        r9 = r1;
        r1 = r0;
        r0 = r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r10, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            r0.<init>(r12)
        L_0x0019:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r12.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x004c;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002e:
            r10 = 0
            r11 = 0
            r2 = 0
            java.lang.Object r4 = r12.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r12.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r12.L$0
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0049 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r2
            r2 = r1
            r1 = r0
            goto L_0x0079
        L_0x0049:
            r1 = move-exception
            goto L_0x00aa
        L_0x004c:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = 0
            r6 = r10
            r10 = 0
            r5 = 0
            r4 = r6
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch:{ all -> 0x00a7 }
            r4 = r8
            r9 = r11
            r11 = r10
            r10 = r2
            r2 = r7
            r7 = r9
        L_0x0061:
            r12.L$0 = r7     // Catch:{ all -> 0x0049 }
            r12.L$1 = r6     // Catch:{ all -> 0x0049 }
            r12.L$2 = r4     // Catch:{ all -> 0x0049 }
            r12.label = r3     // Catch:{ all -> 0x0049 }
            java.lang.Object r8 = r4.hasNext(r12)     // Catch:{ all -> 0x0049 }
            if (r8 != r1) goto L_0x0070
            return r1
        L_0x0070:
            r9 = r1
            r1 = r0
            r0 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r2
            r2 = r9
        L_0x0079:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00a0 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00a0 }
            if (r0 == 0) goto L_0x0090
            java.lang.Object r0 = r5.next()     // Catch:{ all -> 0x00a0 }
            r8.invoke(r0)     // Catch:{ all -> 0x00a0 }
            r0 = r1
            r1 = r2
            r2 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x0061
        L_0x0090:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00a0 }
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L_0x00a0:
            r0 = move-exception
            r5 = r6
            r6 = r7
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x00aa
        L_0x00a7:
            r1 = move-exception
            r11 = r10
            r10 = r2
        L_0x00aa:
            r2 = r1
            throw r1     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            r1 = move-exception
            kotlin.jvm.internal.InlineMarker.finallyStart(r3)
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r2)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <E> Object consumeEach$$forInline(ReceiveChannel<? extends E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        Throwable cause$iv;
        ReceiveChannel $this$consumeEach_u24lambda_u2d1 = $this$consumeEach;
        try {
            ChannelIterator<? extends E> it = $this$consumeEach_u24lambda_u2d1.iterator();
            while (true) {
                InlineMarker.mark(3);
                InlineMarker.mark(0);
                Object hasNext = it.hasNext((Continuation<? super Boolean>) null);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    action.invoke(it.next());
                } else {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ChannelsKt.cancelConsumed($this$consumeEach_u24lambda_u2d1, (Throwable) null);
                    InlineMarker.finallyEnd(1);
                    return Unit.INSTANCE;
                }
            }
        } catch (Throwable e$iv) {
            InlineMarker.finallyStart(1);
            ChannelsKt.cancelConsumed($this$consumeEach_u24lambda_u2d1, cause$iv);
            InlineMarker.finallyEnd(1);
            throw e$iv;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r14.L$0 = r9;
        r14.L$1 = r8;
        r14.L$2 = r7;
        r14.L$3 = r5;
        r14.label = 1;
        r10 = r5.hasNext(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007f, code lost:
        if (r10 != r1) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0081, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0082, code lost:
        r12 = r1;
        r1 = r0;
        r0 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0094, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0096, code lost:
        r9.add(r6.next());
        r0 = r1;
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00aa, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ad, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b7, code lost:
        return kotlin.collections.CollectionsKt.build(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b9, code lost:
        r2 = r3;
        r3 = r4;
        r6 = r7;
        r7 = r8;
        r12 = r1;
        r1 = r0;
        r0 = r12;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object toList(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r13, kotlin.coroutines.Continuation<? super java.util.List<? extends E>> r14) {
        /*
            boolean r0 = r14 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r14 = r0.label
            int r14 = r14 - r2
            r0.label = r14
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1
            r0.<init>(r14)
        L_0x0019:
            r14 = r0
            java.lang.Object r0 = r14.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r14.label
            switch(r2) {
                case 0: goto L_0x0053;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x002d:
            r13 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.Object r5 = r14.L$3
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            r6 = 0
            java.lang.Object r7 = r14.L$2
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r8 = r14.L$1
            java.util.List r8 = (java.util.List) r8
            java.lang.Object r9 = r14.L$0
            java.util.List r9 = (java.util.List) r9
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0050 }
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r0
            goto L_0x008e
        L_0x0050:
            r1 = move-exception
            goto L_0x00c5
        L_0x0053:
            kotlin.ResultKt.throwOnFailure(r0)
            java.util.List r2 = kotlin.collections.CollectionsKt.createListBuilder()
            r3 = r2
            r4 = 0
            r5 = 0
            r7 = r13
            r13 = 0
            r6 = 0
            r8 = r7
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r10 = r8.iterator()     // Catch:{ all -> 0x00c1 }
            r8 = r3
            r3 = r13
            r13 = r4
            r4 = r9
            r9 = r2
            r2 = r5
            r5 = r10
        L_0x0070:
            r14.L$0 = r9     // Catch:{ all -> 0x0050 }
            r14.L$1 = r8     // Catch:{ all -> 0x0050 }
            r14.L$2 = r7     // Catch:{ all -> 0x0050 }
            r14.L$3 = r5     // Catch:{ all -> 0x0050 }
            r10 = 1
            r14.label = r10     // Catch:{ all -> 0x0050 }
            java.lang.Object r10 = r5.hasNext(r14)     // Catch:{ all -> 0x0050 }
            if (r10 != r1) goto L_0x0082
            return r1
        L_0x0082:
            r12 = r1
            r1 = r0
            r0 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r12
        L_0x008e:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x00b8 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x00b8 }
            if (r0 == 0) goto L_0x00aa
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x00b8 }
            r11 = 0
            r9.add(r0)     // Catch:{ all -> 0x00b8 }
            r0 = r1
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            goto L_0x0070
        L_0x00aa:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00b8 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r7)
            java.util.List r13 = kotlin.collections.CollectionsKt.build(r10)
            return r13
        L_0x00b8:
            r0 = move-exception
            r2 = r3
            r3 = r4
            r6 = r7
            r7 = r8
            r12 = r1
            r1 = r0
            r0 = r12
            goto L_0x00c5
        L_0x00c1:
            r1 = move-exception
            r3 = r13
            r13 = r4
            r2 = r5
        L_0x00c5:
            r4 = r1
            throw r1     // Catch:{ all -> 0x00c8 }
        L_0x00c8:
            r1 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toList(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r12.L$0 = r7;
        r12.L$1 = r6;
        r12.L$2 = r5;
        r12.label = 1;
        r8 = r5.hasNext(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006d, code lost:
        if (r8 != r1) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006f, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0070, code lost:
        r9 = r1;
        r1 = r0;
        r0 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r2;
        r2 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007e, code lost:
        if (((java.lang.Boolean) r0).booleanValue() == false) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0080, code lost:
        r8.invoke(r6.next());
        r0 = r1;
        r1 = r2;
        r2 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008e, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0091, code lost:
        kotlin.jvm.internal.InlineMarker.finallyStart(1);
        kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default(r7, (java.util.concurrent.CancellationException) null, 1, (java.lang.Object) null);
        kotlin.jvm.internal.InlineMarker.finallyEnd(1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009d, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009f, code lost:
        r6 = r7;
        r9 = r1;
        r1 = r0;
        r0 = r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.BroadcastChannel<E> r10, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$3
            r0.<init>(r12)
        L_0x0019:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r12.label
            r3 = 0
            r4 = 1
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002f:
            r10 = 0
            r11 = 0
            r2 = 0
            java.lang.Object r5 = r12.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r12.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r12.L$0
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0048 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r2
            r2 = r1
            r1 = r0
            goto L_0x0078
        L_0x0048:
            r1 = move-exception
            goto L_0x00a7
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = 0
            r5 = 0
            kotlinx.coroutines.channels.ReceiveChannel r6 = r10.openSubscription()
            r10 = r6
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r10.iterator()     // Catch:{ all -> 0x00a4 }
            r10 = r2
            r2 = r7
            r7 = r11
            r11 = r5
            r5 = r8
        L_0x0061:
            r12.L$0 = r7     // Catch:{ all -> 0x0048 }
            r12.L$1 = r6     // Catch:{ all -> 0x0048 }
            r12.L$2 = r5     // Catch:{ all -> 0x0048 }
            r12.label = r4     // Catch:{ all -> 0x0048 }
            java.lang.Object r8 = r5.hasNext(r12)     // Catch:{ all -> 0x0048 }
            if (r8 != r1) goto L_0x0070
            return r1
        L_0x0070:
            r9 = r1
            r1 = r0
            r0 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r2
            r2 = r9
        L_0x0078:
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x009e }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x009e }
            if (r0 == 0) goto L_0x008e
            java.lang.Object r0 = r6.next()     // Catch:{ all -> 0x009e }
            r8.invoke(r0)     // Catch:{ all -> 0x009e }
            r0 = r1
            r1 = r2
            r2 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x0061
        L_0x008e:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x009e }
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r7, (java.util.concurrent.CancellationException) r3, (int) r4, (java.lang.Object) r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L_0x009e:
            r0 = move-exception
            r6 = r7
            r9 = r1
            r1 = r0
            r0 = r9
            goto L_0x00a7
        L_0x00a4:
            r1 = move-exception
            r10 = r2
            r11 = r5
        L_0x00a7:
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            kotlinx.coroutines.channels.ReceiveChannel.DefaultImpls.cancel$default((kotlinx.coroutines.channels.ReceiveChannel) r6, (java.util.concurrent.CancellationException) r3, (int) r4, (java.lang.Object) r3)
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.BroadcastChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: finally extract failed */
    private static final <E> Object consumeEach$$forInline(BroadcastChannel<E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        ReceiveChannel $this$consumeEach_u24lambda_u2d4 = $this$consumeEach.openSubscription();
        try {
            ChannelIterator<E> it = $this$consumeEach_u24lambda_u2d4.iterator();
            while (true) {
                InlineMarker.mark(3);
                InlineMarker.mark(0);
                Object hasNext = it.hasNext((Continuation<? super Boolean>) null);
                InlineMarker.mark(1);
                if (((Boolean) hasNext).booleanValue()) {
                    action.invoke(it.next());
                } else {
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    ReceiveChannel.DefaultImpls.cancel$default($this$consumeEach_u24lambda_u2d4, (CancellationException) null, 1, (Object) null);
                    InlineMarker.finallyEnd(1);
                    return Unit.INSTANCE;
                }
            }
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            ReceiveChannel.DefaultImpls.cancel$default($this$consumeEach_u24lambda_u2d4, (CancellationException) null, 1, (Object) null);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public static final void cancelConsumed(ReceiveChannel<?> $this$cancelConsumed, Throwable cause) {
        CancellationException cancellationException = null;
        if (cause != null) {
            Throwable it = cause;
            if (it instanceof CancellationException) {
                cancellationException = (CancellationException) it;
            }
            if (cancellationException == null) {
                cancellationException = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", it);
            }
        }
        $this$cancelConsumed.cancel(cancellationException);
    }
}
