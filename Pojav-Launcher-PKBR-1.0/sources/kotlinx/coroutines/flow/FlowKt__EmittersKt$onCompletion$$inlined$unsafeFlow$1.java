package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function3;

@Metadata(mo11814d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\u0000"}, mo11815d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function3 $action$inlined;
    final /* synthetic */ Flow $this_onCompletion$inlined;

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006c, code lost:
        r2 = new kotlinx.coroutines.flow.internal.SafeCollector(r2, r11.getContext());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r5 = r4.$action$inlined;
        r11.L$0 = r2;
        r11.L$1 = null;
        r11.label = 3;
        kotlin.jvm.internal.InlineMarker.mark(6);
        r3 = r5.invoke(r2, null, r11);
        kotlin.jvm.internal.InlineMarker.mark(7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008e, code lost:
        if (r3 != r1) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0090, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0091, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0092, code lost:
        r1.releaseIntercepted();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0099, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009a, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009b, code lost:
        r8 = r2;
        r2 = r1;
        r1 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009e, code lost:
        r1.releaseIntercepted();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a1, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c0, code lost:
        throw r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.C07661
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.C07661) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1$1
            r0.<init>(r9, r11)
        L_0x0019:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r11.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0052;
                case 1: goto L_0x0045;
                case 2: goto L_0x0039;
                case 3: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002e:
            r10 = 0
            java.lang.Object r1 = r11.L$0
            kotlinx.coroutines.flow.internal.SafeCollector r1 = (kotlinx.coroutines.flow.internal.SafeCollector) r1
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x0037 }
            goto L_0x0092
        L_0x0037:
            r2 = move-exception
            goto L_0x009e
        L_0x0039:
            r10 = r9
            r1 = r3
            r2 = 0
            java.lang.Object r3 = r11.L$0
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            kotlin.ResultKt.throwOnFailure(r0)
            goto L_0x00c0
        L_0x0045:
            r10 = 0
            java.lang.Object r2 = r11.L$1
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            java.lang.Object r4 = r11.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1 r4 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1) r4
            kotlin.ResultKt.throwOnFailure(r0)     // Catch:{ all -> 0x00a2 }
            goto L_0x006c
        L_0x0052:
            kotlin.ResultKt.throwOnFailure(r0)
            r4 = r9
            r2 = r11
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r2 = r10
            r10 = 0
            kotlinx.coroutines.flow.Flow r5 = r4.$this_onCompletion$inlined     // Catch:{ all -> 0x00a2 }
            r11.L$0 = r4     // Catch:{ all -> 0x00a2 }
            r11.L$1 = r2     // Catch:{ all -> 0x00a2 }
            r6 = 1
            r11.label = r6     // Catch:{ all -> 0x00a2 }
            java.lang.Object r5 = r5.collect(r2, r11)     // Catch:{ all -> 0x00a2 }
            if (r5 != r1) goto L_0x006c
            return r1
        L_0x006c:
            kotlinx.coroutines.flow.internal.SafeCollector r5 = new kotlinx.coroutines.flow.internal.SafeCollector
            r6 = 0
            kotlin.coroutines.CoroutineContext r7 = r11.getContext()
            r5.<init>(r2, r7)
            r2 = r5
            kotlin.jvm.functions.Function3 r5 = r4.$action$inlined     // Catch:{ all -> 0x009a }
            r11.L$0 = r2     // Catch:{ all -> 0x009a }
            r11.L$1 = r3     // Catch:{ all -> 0x009a }
            r6 = 3
            r11.label = r6     // Catch:{ all -> 0x009a }
            r6 = 6
            kotlin.jvm.internal.InlineMarker.mark((int) r6)     // Catch:{ all -> 0x009a }
            java.lang.Object r3 = r5.invoke(r2, r3, r11)     // Catch:{ all -> 0x009a }
            r5 = 7
            kotlin.jvm.internal.InlineMarker.mark((int) r5)     // Catch:{ all -> 0x009a }
            if (r3 != r1) goto L_0x0091
            return r1
        L_0x0091:
            r1 = r2
        L_0x0092:
            r1.releaseIntercepted()
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L_0x009a:
            r1 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
        L_0x009e:
            r1.releaseIntercepted()
            throw r2
        L_0x00a2:
            r5 = move-exception
            r8 = r4
            r4 = r10
            r10 = r8
            kotlinx.coroutines.flow.ThrowingCollector r6 = new kotlinx.coroutines.flow.ThrowingCollector
            r6.<init>(r5)
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            kotlin.jvm.functions.Function3 r7 = r10.$action$inlined
            r11.L$0 = r5
            r11.L$1 = r3
            r3 = 2
            r11.label = r3
            java.lang.Object r3 = kotlinx.coroutines.flow.FlowKt__EmittersKt.invokeSafely$FlowKt__EmittersKt(r6, r7, r5, r11)
            if (r3 != r1) goto L_0x00bd
            return r1
        L_0x00bd:
            r1 = r2
            r2 = r4
            r3 = r5
        L_0x00c0:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public FlowKt__EmittersKt$onCompletion$$inlined$unsafeFlow$1(Flow flow, Function3 function3) {
        this.$this_onCompletion$inlined = flow;
        this.$action$inlined = function3;
    }
}
