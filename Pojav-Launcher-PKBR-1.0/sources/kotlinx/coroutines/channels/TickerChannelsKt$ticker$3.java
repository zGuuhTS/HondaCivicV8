package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(mo11814d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H@"}, mo11815d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.channels.TickerChannelsKt$ticker$3", mo12530f = "TickerChannels.kt", mo12531i = {}, mo12532l = {72, 73}, mo12533m = "invokeSuspend", mo12534n = {}, mo12535s = {})
/* compiled from: TickerChannels.kt */
final class TickerChannelsKt$ticker$3 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $delayMillis;
    final /* synthetic */ long $initialDelayMillis;
    final /* synthetic */ TickerMode $mode;
    private /* synthetic */ Object L$0;
    int label;

    @Metadata(mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
    /* compiled from: TickerChannels.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TickerMode.values().length];
            iArr[TickerMode.FIXED_PERIOD.ordinal()] = 1;
            iArr[TickerMode.FIXED_DELAY.ordinal()] = 2;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TickerChannelsKt$ticker$3(TickerMode tickerMode, long j, long j2, Continuation<? super TickerChannelsKt$ticker$3> continuation) {
        super(2, continuation);
        this.$mode = tickerMode;
        this.$delayMillis = j;
        this.$initialDelayMillis = j2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        TickerChannelsKt$ticker$3 tickerChannelsKt$ticker$3 = new TickerChannelsKt$ticker$3(this.$mode, this.$delayMillis, this.$initialDelayMillis, continuation);
        tickerChannelsKt$ticker$3.L$0 = obj;
        return tickerChannelsKt$ticker$3;
    }

    public final Object invoke(ProducerScope<? super Unit> producerScope, Continuation<? super Unit> continuation) {
        return ((TickerChannelsKt$ticker$3) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0048, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0061, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0064, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            switch(r1) {
                case 0: goto L_0x001c;
                case 1: goto L_0x0016;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0011:
            r0 = r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0048
        L_0x0016:
            r0 = r10
            r1 = 0
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0061
        L_0x001c:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
            kotlinx.coroutines.channels.TickerMode r3 = r1.$mode
            int[] r4 = kotlinx.coroutines.channels.TickerChannelsKt$ticker$3.WhenMappings.$EnumSwitchMapping$0
            int r3 = r3.ordinal()
            r3 = r4[r3]
            switch(r3) {
                case 1: goto L_0x004a;
                case 2: goto L_0x0032;
                default: goto L_0x0031;
            }
        L_0x0031:
            goto L_0x0062
        L_0x0032:
            long r4 = r1.$delayMillis
            long r6 = r1.$initialDelayMillis
            kotlinx.coroutines.channels.SendChannel r8 = r2.getChannel()
            r9 = r1
            kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
            r3 = 2
            r1.label = r3
            java.lang.Object r2 = kotlinx.coroutines.channels.TickerChannelsKt.fixedDelayTicker(r4, r6, r8, r9)
            if (r2 != r0) goto L_0x0047
            return r0
        L_0x0047:
            r0 = r1
        L_0x0048:
            r1 = r0
            goto L_0x0062
        L_0x004a:
            long r3 = r1.$delayMillis
            long r5 = r1.$initialDelayMillis
            kotlinx.coroutines.channels.SendChannel r7 = r2.getChannel()
            r8 = r1
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r9 = 1
            r1.label = r9
            java.lang.Object r3 = kotlinx.coroutines.channels.TickerChannelsKt.fixedPeriodTicker(r3, r5, r7, r8)
            if (r3 != r0) goto L_0x005f
            return r0
        L_0x005f:
            r0 = r1
            r1 = r2
        L_0x0061:
            r1 = r0
        L_0x0062:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt$ticker$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
