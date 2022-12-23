package kotlinx.coroutines;

import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.scheduling.Task;

@Metadata(mo11814d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u000e\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0002j\u0002`\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0011J\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0004\b\u0016\u0010\u0017J\u001a\u0010\u0018\u001a\u00020\f2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00102\b\u0010\u001a\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u001b\u001a\u00020\fJ\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u000eH ¢\u0006\u0002\b\u001dR\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX \u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, mo11815d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", "", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelCompletedResult", "", "takenState", "", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "getExceptionalResult", "state", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: DispatchedTask.kt */
public abstract class DispatchedTask<T> extends Task {
    public int resumeMode;

    public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();

    public abstract Object takeState$kotlinx_coroutines_core();

    public DispatchedTask(int resumeMode2) {
        this.resumeMode = resumeMode2;
    }

    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
    }

    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state;
    }

    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        CompletedExceptionally completedExceptionally = state instanceof CompletedExceptionally ? (CompletedExceptionally) state : null;
        if (completedExceptionally == null) {
            return null;
        }
        return completedExceptionally.cause;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e9, code lost:
        if (r11.clearThreadContext() != false) goto L_0x00eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x010e, code lost:
        if (r11.clearThreadContext() != false) goto L_0x0110;
     */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e5 A[SYNTHETIC, Splitter:B:51:0x00e5] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x010a A[SYNTHETIC, Splitter:B:62:0x010a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r21 = this;
            r1 = r21
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x001a
            r0 = 0
            int r2 = r1.resumeMode
            r3 = -1
            if (r2 == r3) goto L_0x0010
            r2 = 1
            goto L_0x0011
        L_0x0010:
            r2 = 0
        L_0x0011:
            if (r2 == 0) goto L_0x0014
            goto L_0x001a
        L_0x0014:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001a:
            kotlinx.coroutines.scheduling.TaskContext r2 = r1.taskContext
            r3 = 0
            kotlin.coroutines.Continuation r0 = r21.getDelegate$kotlinx_coroutines_core()     // Catch:{ all -> 0x0115 }
            kotlinx.coroutines.internal.DispatchedContinuation r0 = (kotlinx.coroutines.internal.DispatchedContinuation) r0     // Catch:{ all -> 0x0115 }
            r4 = r0
            kotlin.coroutines.Continuation<T> r0 = r4.continuation     // Catch:{ all -> 0x0115 }
            r5 = r0
            java.lang.Object r0 = r4.countOrElement     // Catch:{ all -> 0x0115 }
            r6 = r0
            r7 = 0
            kotlin.coroutines.CoroutineContext r0 = r5.getContext()     // Catch:{ all -> 0x0115 }
            r8 = r0
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r8, r6)     // Catch:{ all -> 0x0115 }
            r9 = r0
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch:{ all -> 0x0115 }
            r10 = 0
            if (r9 == r0) goto L_0x0040
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r5, r8, r9)     // Catch:{ all -> 0x0115 }
            goto L_0x0043
        L_0x0040:
            r0 = r10
            kotlinx.coroutines.UndispatchedCoroutine r0 = (kotlinx.coroutines.UndispatchedCoroutine) r0     // Catch:{ all -> 0x0115 }
        L_0x0043:
            r11 = r0
            r0 = 0
            kotlin.coroutines.CoroutineContext r12 = r5.getContext()     // Catch:{ all -> 0x0103 }
            java.lang.Object r13 = r21.takeState$kotlinx_coroutines_core()     // Catch:{ all -> 0x0103 }
            java.lang.Throwable r14 = r1.getExceptionalResult$kotlinx_coroutines_core(r13)     // Catch:{ all -> 0x0103 }
            if (r14 != 0) goto L_0x006e
            int r15 = r1.resumeMode     // Catch:{ all -> 0x0067 }
            boolean r15 = kotlinx.coroutines.DispatchedTaskKt.isCancellableMode(r15)     // Catch:{ all -> 0x0067 }
            if (r15 == 0) goto L_0x006e
            kotlinx.coroutines.Job$Key r10 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x0067 }
            kotlin.coroutines.CoroutineContext$Key r10 = (kotlin.coroutines.CoroutineContext.Key) r10     // Catch:{ all -> 0x0067 }
            kotlin.coroutines.CoroutineContext$Element r10 = r12.get(r10)     // Catch:{ all -> 0x0067 }
            kotlinx.coroutines.Job r10 = (kotlinx.coroutines.Job) r10     // Catch:{ all -> 0x0067 }
            goto L_0x006e
        L_0x0067:
            r0 = move-exception
            r19 = r4
            r20 = r6
            goto L_0x0108
        L_0x006e:
            if (r10 == 0) goto L_0x00bd
            boolean r15 = r10.isActive()     // Catch:{ all -> 0x0103 }
            if (r15 != 0) goto L_0x00bd
            java.util.concurrent.CancellationException r15 = r10.getCancellationException()     // Catch:{ all -> 0x0103 }
            r16 = r0
            r0 = r15
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x0103 }
            r1.cancelCompletedResult$kotlinx_coroutines_core(r13, r0)     // Catch:{ all -> 0x0103 }
            r0 = r5
            r17 = 0
            kotlin.Result$Companion r18 = kotlin.Result.Companion     // Catch:{ all -> 0x0103 }
            r18 = 0
            boolean r19 = kotlinx.coroutines.DebugKt.getRECOVER_STACK_TRACES()     // Catch:{ all -> 0x0103 }
            if (r19 == 0) goto L_0x00a9
            r19 = r4
            boolean r4 = r0 instanceof kotlin.coroutines.jvm.internal.CoroutineStackFrame     // Catch:{ all -> 0x00a5 }
            if (r4 != 0) goto L_0x0098
            r20 = r6
            goto L_0x00ad
        L_0x0098:
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x00a5 }
            r20 = r6
            r6 = r0
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r6 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r6     // Catch:{ all -> 0x0101 }
            java.lang.Throwable r4 = kotlinx.coroutines.internal.StackTraceRecoveryKt.recoverFromStackFrame(r4, r6)     // Catch:{ all -> 0x0101 }
            goto L_0x00b0
        L_0x00a5:
            r0 = move-exception
            r20 = r6
            goto L_0x0108
        L_0x00a9:
            r19 = r4
            r20 = r6
        L_0x00ad:
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x0101 }
        L_0x00b0:
            java.lang.Object r4 = kotlin.ResultKt.createFailure(r4)     // Catch:{ all -> 0x0101 }
            java.lang.Object r4 = kotlin.Result.m64constructorimpl(r4)     // Catch:{ all -> 0x0101 }
            r0.resumeWith(r4)     // Catch:{ all -> 0x0101 }
            goto L_0x00e0
        L_0x00bd:
            r16 = r0
            r19 = r4
            r20 = r6
            if (r14 == 0) goto L_0x00d3
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0101 }
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r14)     // Catch:{ all -> 0x0101 }
            java.lang.Object r0 = kotlin.Result.m64constructorimpl(r0)     // Catch:{ all -> 0x0101 }
            r5.resumeWith(r0)     // Catch:{ all -> 0x0101 }
            goto L_0x00e0
        L_0x00d3:
            java.lang.Object r0 = r1.getSuccessfulResult$kotlinx_coroutines_core(r13)     // Catch:{ all -> 0x0101 }
            kotlin.Result$Companion r4 = kotlin.Result.Companion     // Catch:{ all -> 0x0101 }
            java.lang.Object r0 = kotlin.Result.m64constructorimpl(r0)     // Catch:{ all -> 0x0101 }
            r5.resumeWith(r0)     // Catch:{ all -> 0x0101 }
        L_0x00e0:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0101 }
            if (r11 == 0) goto L_0x00eb
            boolean r0 = r11.clearThreadContext()     // Catch:{ all -> 0x0115 }
            if (r0 == 0) goto L_0x00ee
        L_0x00eb:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r8, r9)     // Catch:{ all -> 0x0115 }
        L_0x00ee:
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x00ff }
            r0 = r1
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0     // Catch:{ all -> 0x00ff }
            r4 = 0
            r2.afterTask()     // Catch:{ all -> 0x00ff }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ff }
            java.lang.Object r0 = kotlin.Result.m64constructorimpl(r0)     // Catch:{ all -> 0x00ff }
            goto L_0x0132
        L_0x00ff:
            r0 = move-exception
            goto L_0x0128
        L_0x0101:
            r0 = move-exception
            goto L_0x0108
        L_0x0103:
            r0 = move-exception
            r19 = r4
            r20 = r6
        L_0x0108:
            if (r11 == 0) goto L_0x0110
            boolean r4 = r11.clearThreadContext()     // Catch:{ all -> 0x0115 }
            if (r4 == 0) goto L_0x0113
        L_0x0110:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r8, r9)     // Catch:{ all -> 0x0115 }
        L_0x0113:
            throw r0     // Catch:{ all -> 0x0115 }
        L_0x0115:
            r0 = move-exception
            r3 = r0
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0127 }
            r0 = r1
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0     // Catch:{ all -> 0x0127 }
            r4 = 0
            r2.afterTask()     // Catch:{ all -> 0x0127 }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0127 }
            java.lang.Object r0 = kotlin.Result.m64constructorimpl(r0)     // Catch:{ all -> 0x0127 }
            goto L_0x0132
        L_0x0127:
            r0 = move-exception
        L_0x0128:
            kotlin.Result$Companion r4 = kotlin.Result.Companion
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r0)
            java.lang.Object r0 = kotlin.Result.m64constructorimpl(r0)
        L_0x0132:
            java.lang.Throwable r4 = kotlin.Result.m67exceptionOrNullimpl(r0)
            r1.handleFatalException(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DispatchedTask.run():void");
    }

    public final void handleFatalException(Throwable exception, Throwable finallyException) {
        if (exception != null || finallyException != null) {
            if (!(exception == null || finallyException == null)) {
                ExceptionsKt.addSuppressed(exception, finallyException);
            }
            Throwable cause = exception == null ? finallyException : exception;
            Intrinsics.checkNotNull(cause);
            CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", cause));
        }
    }
}
