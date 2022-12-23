package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;

@Metadata(mo11814d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@"}, mo11815d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Lkotlinx/coroutines/ChildJob;"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
@DebugMetadata(mo12529c = "kotlinx.coroutines.JobSupport$children$1", mo12530f = "JobSupport.kt", mo12531i = {1, 1, 1}, mo12532l = {952, 954}, mo12533m = "invokeSuspend", mo12534n = {"$this$sequence", "this_$iv", "cur$iv"}, mo12535s = {"L$0", "L$1", "L$2"})
/* compiled from: JobSupport.kt */
final class JobSupport$children$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super ChildJob>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ JobSupport this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    JobSupport$children$1(JobSupport jobSupport, Continuation<? super JobSupport$children$1> continuation) {
        super(2, continuation);
        this.this$0 = jobSupport;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        JobSupport$children$1 jobSupport$children$1 = new JobSupport$children$1(this.this$0, continuation);
        jobSupport$children$1.L$0 = obj;
        return jobSupport$children$1;
    }

    public final Object invoke(SequenceScope<? super ChildJob> sequenceScope, Continuation<? super Unit> continuation) {
        return ((JobSupport$children$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0053, code lost:
        r1 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0078, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r5, (java.lang.Object) r6) != 0) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007c, code lost:
        if ((r5 instanceof kotlinx.coroutines.ChildHandleNode) == false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007e, code lost:
        r9 = ((kotlinx.coroutines.ChildHandleNode) r5).childJob;
        r1.L$0 = r7;
        r1.L$1 = r6;
        r1.L$2 = r5;
        r1.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0091, code lost:
        if (r7.yield(r9, r1) != r0) goto L_0x0094;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0093, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0096, code lost:
        r5 = r5.getNextNode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a0, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            r12 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r12.label
            switch(r1) {
                case 0: goto L_0x002d;
                case 1: goto L_0x0026;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r0)
            throw r13
        L_0x0011:
            r1 = r12
            r2 = 0
            r3 = 0
            r4 = 0
            java.lang.Object r5 = r1.L$2
            kotlinx.coroutines.internal.LockFreeLinkedListNode r5 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r5
            java.lang.Object r6 = r1.L$1
            kotlinx.coroutines.internal.LockFreeLinkedListHead r6 = (kotlinx.coroutines.internal.LockFreeLinkedListHead) r6
            java.lang.Object r7 = r1.L$0
            kotlin.sequences.SequenceScope r7 = (kotlin.sequences.SequenceScope) r7
            kotlin.ResultKt.throwOnFailure(r13)
            goto L_0x0095
        L_0x0026:
            r0 = r12
            r1 = 0
            r2 = r1
            kotlin.ResultKt.throwOnFailure(r13)
            goto L_0x0053
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r13)
            r1 = r12
            java.lang.Object r2 = r1.L$0
            kotlin.sequences.SequenceScope r2 = (kotlin.sequences.SequenceScope) r2
            kotlinx.coroutines.JobSupport r3 = r1.this$0
            java.lang.Object r3 = r3.getState$kotlinx_coroutines_core()
            boolean r4 = r3 instanceof kotlinx.coroutines.ChildHandleNode
            if (r4 == 0) goto L_0x0055
            r4 = r3
            kotlinx.coroutines.ChildHandleNode r4 = (kotlinx.coroutines.ChildHandleNode) r4
            kotlinx.coroutines.ChildJob r4 = r4.childJob
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r6 = 1
            r1.label = r6
            java.lang.Object r4 = r2.yield(r4, r5)
            if (r4 != r0) goto L_0x0051
            return r0
        L_0x0051:
            r0 = r1
            r1 = r3
        L_0x0053:
            r1 = r0
            goto L_0x009e
        L_0x0055:
            boolean r4 = r3 instanceof kotlinx.coroutines.Incomplete
            if (r4 == 0) goto L_0x009e
            r4 = r3
            kotlinx.coroutines.Incomplete r4 = (kotlinx.coroutines.Incomplete) r4
            kotlinx.coroutines.NodeList r4 = r4.getList()
            if (r4 != 0) goto L_0x0063
            goto L_0x009e
        L_0x0063:
            r3 = r4
            r4 = 0
            kotlinx.coroutines.internal.LockFreeLinkedListHead r3 = (kotlinx.coroutines.internal.LockFreeLinkedListHead) r3
            r5 = 0
            java.lang.Object r6 = r3.getNext()
            kotlinx.coroutines.internal.LockFreeLinkedListNode r6 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r6
            r7 = r2
            r2 = r4
            r11 = r6
            r6 = r3
            r3 = r5
            r5 = r11
        L_0x0074:
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r5, (java.lang.Object) r6)
            if (r4 != 0) goto L_0x009b
            boolean r4 = r5 instanceof kotlinx.coroutines.ChildHandleNode
            if (r4 == 0) goto L_0x0096
            r4 = r5
            kotlinx.coroutines.ChildHandleNode r4 = (kotlinx.coroutines.ChildHandleNode) r4
            r8 = 0
            kotlinx.coroutines.ChildJob r9 = r4.childJob
            r1.L$0 = r7
            r1.L$1 = r6
            r1.L$2 = r5
            r10 = 2
            r1.label = r10
            java.lang.Object r4 = r7.yield(r9, r1)
            if (r4 != r0) goto L_0x0094
            return r0
        L_0x0094:
            r4 = r8
        L_0x0095:
        L_0x0096:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r5 = r5.getNextNode()
            goto L_0x0074
        L_0x009b:
        L_0x009e:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport$children$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
