package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;

@Metadata(mo11814d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\b"}, mo11815d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$filter$$inlined$unsafeTransform$1 implements Flow<T> {
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$filter$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$predicate$inlined = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d1 = collector;
        Continuation continuation = $completion;
        final Function2 function2 = this.$predicate$inlined;
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<T>() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0033  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0042  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x006a  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                /*
                    r9 = this;
                    boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C07932.C07941
                    if (r0 == 0) goto L_0x0014
                    r0 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C07932.C07941) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r11 = r0.label
                    int r11 = r11 - r2
                    r0.label = r11
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r9, r11)
                L_0x0019:
                    r11 = r0
                    java.lang.Object r0 = r11.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r11.label
                    switch(r2) {
                        case 0: goto L_0x0042;
                        case 1: goto L_0x0033;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r11)
                    throw r10
                L_0x002d:
                    r10 = 0
                    r1 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x007a
                L_0x0033:
                    r10 = 0
                    r2 = 0
                    java.lang.Object r3 = r11.L$1
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    java.lang.Object r4 = r11.L$0
                    kotlin.ResultKt.throwOnFailure(r0)
                    r5 = r4
                    r4 = r3
                    r3 = r0
                    goto L_0x0062
                L_0x0042:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r9
                    r3 = 0
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r11
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r7
                    r11.L$0 = r10
                    r11.L$1 = r4
                    r7 = 1
                    r11.label = r7
                    java.lang.Object r2 = r6.invoke(r10, r11)
                    if (r2 != r1) goto L_0x005d
                    return r1
                L_0x005d:
                    r8 = r5
                    r5 = r10
                    r10 = r3
                    r3 = r2
                    r2 = r8
                L_0x0062:
                    java.lang.Boolean r3 = (java.lang.Boolean) r3
                    boolean r3 = r3.booleanValue()
                    if (r3 == 0) goto L_0x007b
                    r3 = 0
                    r11.L$0 = r3
                    r11.L$1 = r3
                    r3 = 2
                    r11.label = r3
                    java.lang.Object r3 = r4.emit(r5, r11)
                    if (r3 != r1) goto L_0x0079
                    return r1
                L_0x0079:
                    r1 = r2
                L_0x007a:
                    goto L_0x007c
                L_0x007b:
                L_0x007c:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C07932.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C07932 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit((Object) null, this);
                    }
                };
                InlineMarker.mark(5);
                Continuation continuation = $completion;
                FlowCollector $this$filter_u24lambda_u2d0 = $this$unsafeTransform_u24lambda_u2d1;
                Continuation continuation2 = $completion;
                Object value2 = value;
                if (((Boolean) r7.invoke(value2, $completion)).booleanValue()) {
                    InlineMarker.mark(0);
                    $this$filter_u24lambda_u2d0.emit(value2, $completion);
                    InlineMarker.mark(1);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        if (collect == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return collect;
        }
        return Unit.INSTANCE;
    }

    public Object collect$$forInline(FlowCollector collector, Continuation $completion) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, $completion) {
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__TransformKt$filter$$inlined$unsafeTransform$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.collect((FlowCollector) null, this);
            }
        };
        InlineMarker.mark(5);
        final FlowCollector $this$unsafeTransform_u24lambda_u2d1 = collector;
        Continuation continuation = $completion;
        final Function2 function2 = this.$predicate$inlined;
        FlowCollector r6 = new FlowCollector<T>() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                /*
                    r9 = this;
                    boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C07932.C07941
                    if (r0 == 0) goto L_0x0014
                    r0 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C07932.C07941) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r11 = r0.label
                    int r11 = r11 - r2
                    r0.label = r11
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r9, r11)
                L_0x0019:
                    r11 = r0
                    java.lang.Object r0 = r11.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r11.label
                    switch(r2) {
                        case 0: goto L_0x0042;
                        case 1: goto L_0x0033;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r11)
                    throw r10
                L_0x002d:
                    r10 = 0
                    r1 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x007a
                L_0x0033:
                    r10 = 0
                    r2 = 0
                    java.lang.Object r3 = r11.L$1
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    java.lang.Object r4 = r11.L$0
                    kotlin.ResultKt.throwOnFailure(r0)
                    r5 = r4
                    r4 = r3
                    r3 = r0
                    goto L_0x0062
                L_0x0042:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r9
                    r3 = 0
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r11
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r7
                    r11.L$0 = r10
                    r11.L$1 = r4
                    r7 = 1
                    r11.label = r7
                    java.lang.Object r2 = r6.invoke(r10, r11)
                    if (r2 != r1) goto L_0x005d
                    return r1
                L_0x005d:
                    r8 = r5
                    r5 = r10
                    r10 = r3
                    r3 = r2
                    r2 = r8
                L_0x0062:
                    java.lang.Boolean r3 = (java.lang.Boolean) r3
                    boolean r3 = r3.booleanValue()
                    if (r3 == 0) goto L_0x007b
                    r3 = 0
                    r11.L$0 = r3
                    r11.L$1 = r3
                    r3 = 2
                    r11.label = r3
                    java.lang.Object r3 = r4.emit(r5, r11)
                    if (r3 != r1) goto L_0x0079
                    return r1
                L_0x0079:
                    r1 = r2
                L_0x007a:
                    goto L_0x007c
                L_0x007b:
                L_0x007c:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filter$$inlined$unsafeTransform$1.C07932.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C07932 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit((Object) null, this);
                    }
                };
                InlineMarker.mark(5);
                Continuation continuation = $completion;
                FlowCollector $this$filter_u24lambda_u2d0 = $this$unsafeTransform_u24lambda_u2d1;
                Continuation continuation2 = $completion;
                Object value2 = value;
                if (((Boolean) function2.invoke(value2, $completion)).booleanValue()) {
                    InlineMarker.mark(0);
                    $this$filter_u24lambda_u2d0.emit(value2, $completion);
                    InlineMarker.mark(1);
                }
                return Unit.INSTANCE;
            }
        };
        InlineMarker.mark(0);
        this.$this_unsafeTransform$inlined.collect(r6, $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
