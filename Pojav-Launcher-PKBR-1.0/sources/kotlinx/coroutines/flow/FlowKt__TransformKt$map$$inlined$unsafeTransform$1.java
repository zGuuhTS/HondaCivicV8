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
public final class FlowKt__TransformKt$map$$inlined$unsafeTransform$1 implements Flow<R> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;
    final /* synthetic */ Function2 $transform$inlined$1;

    public FlowKt__TransformKt$map$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$transform$inlined$1 = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u2d1 = collector;
        Continuation continuation = $completion;
        final Function2 function2 = this.$transform$inlined$1;
        Object collect = this.$this_unsafeTransform$inlined.collect(new FlowCollector<T>() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0033  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x0040  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0066 A[RETURN] */
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0067  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1.C08042.C08051
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1.C08042.C08051) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r10 = r0.label
                    int r10 = r10 - r2
                    r0.label = r10
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0040;
                        case 1: goto L_0x0033;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r10)
                    throw r9
                L_0x002d:
                    r9 = 0
                    r1 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0069
                L_0x0033:
                    r9 = 0
                    r2 = 0
                    java.lang.Object r3 = r10.L$0
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r3
                    r3 = r9
                    r9 = r0
                    goto L_0x005a
                L_0x0040:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r8
                    r3 = 0
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r10
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r7
                    r10.L$0 = r4
                    r7 = 1
                    r10.label = r7
                    java.lang.Object r9 = r6.invoke(r9, r10)
                    if (r9 != r1) goto L_0x0059
                    return r1
                L_0x0059:
                    r2 = r5
                L_0x005a:
                    r5 = 0
                    r10.L$0 = r5
                    r5 = 2
                    r10.label = r5
                    java.lang.Object r9 = r4.emit(r9, r10)
                    if (r9 != r1) goto L_0x0067
                    return r1
                L_0x0067:
                    r1 = r2
                    r9 = r3
                L_0x0069:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1.C08042.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C08042 this$0;

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
                FlowCollector $this$map_u24lambda_u2d4 = $this$unsafeTransform_u24lambda_u2d1;
                Continuation continuation2 = $completion;
                Object invoke = r7.invoke(value, $completion);
                InlineMarker.mark(0);
                $this$map_u24lambda_u2d4.emit(invoke, $completion);
                InlineMarker.mark(1);
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
            final /* synthetic */ FlowKt__TransformKt$map$$inlined$unsafeTransform$1 this$0;

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
        final Function2 function2 = this.$transform$inlined$1;
        FlowCollector r6 = new FlowCollector<T>() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1.C08042.C08051
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1.C08042.C08051) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r10 = r0.label
                    int r10 = r10 - r2
                    r0.label = r10
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0040;
                        case 1: goto L_0x0033;
                        case 2: goto L_0x002d;
                        default: goto L_0x0025;
                    }
                L_0x0025:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r10)
                    throw r9
                L_0x002d:
                    r9 = 0
                    r1 = 0
                    kotlin.ResultKt.throwOnFailure(r0)
                    goto L_0x0069
                L_0x0033:
                    r9 = 0
                    r2 = 0
                    java.lang.Object r3 = r10.L$0
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    kotlin.ResultKt.throwOnFailure(r0)
                    r4 = r3
                    r3 = r9
                    r9 = r0
                    goto L_0x005a
                L_0x0040:
                    kotlin.ResultKt.throwOnFailure(r0)
                    r2 = r8
                    r3 = 0
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r10
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r7
                    r10.L$0 = r4
                    r7 = 1
                    r10.label = r7
                    java.lang.Object r9 = r6.invoke(r9, r10)
                    if (r9 != r1) goto L_0x0059
                    return r1
                L_0x0059:
                    r2 = r5
                L_0x005a:
                    r5 = 0
                    r10.L$0 = r5
                    r5 = 2
                    r10.label = r5
                    java.lang.Object r9 = r4.emit(r9, r10)
                    if (r9 != r1) goto L_0x0067
                    return r1
                L_0x0067:
                    r1 = r2
                    r9 = r3
                L_0x0069:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$map$$inlined$unsafeTransform$1.C08042.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public Object emit$$forInline(Object value, Continuation $completion) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C08042 this$0;

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
                FlowCollector $this$map_u24lambda_u2d4 = $this$unsafeTransform_u24lambda_u2d1;
                Continuation continuation2 = $completion;
                Object invoke = function2.invoke(value, $completion);
                InlineMarker.mark(0);
                $this$map_u24lambda_u2d4.emit(invoke, $completion);
                InlineMarker.mark(1);
                return Unit.INSTANCE;
            }
        };
        InlineMarker.mark(0);
        this.$this_unsafeTransform$inlined.collect(r6, $completion);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
