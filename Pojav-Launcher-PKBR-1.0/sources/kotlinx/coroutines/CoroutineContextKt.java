package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.DefaultScheduler;

@Metadata(mo11814d1 = {"\u0000H\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\b\u0010\u000b\u001a\u00020\fH\u0000\u001a8\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0014H\b¢\u0006\u0002\u0010\u0015\u001a4\u0010\u0016\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u0017\u001a\u00020\b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0014H\b¢\u0006\u0002\u0010\u0018\u001a\u0014\u0010\u0019\u001a\u00020\b*\u00020\u001a2\u0006\u0010\u0017\u001a\u00020\bH\u0007\u001a\u0013\u0010\u001b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001c*\u00020\u001dH\u0010\u001a(\u0010\u001e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001c*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0017\u001a\u00020\b2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0012H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u0001*\u00020\b8@X\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006 "}, mo11815d2 = {"COROUTINES_SCHEDULER_PROPERTY_NAME", "", "DEBUG_THREAD_NAME_SEPARATOR", "useCoroutinesScheduler", "", "getUseCoroutinesScheduler", "()Z", "coroutineName", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineName", "(Lkotlin/coroutines/CoroutineContext;)Ljava/lang/String;", "createDefaultDispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "withContinuationContext", "T", "continuation", "Lkotlin/coroutines/Continuation;", "countOrElement", "", "block", "Lkotlin/Function0;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withCoroutineContext", "context", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "newCoroutineContext", "Lkotlinx/coroutines/CoroutineScope;", "undispatchedCompletion", "Lkotlinx/coroutines/UndispatchedCoroutine;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateUndispatchedCompletion", "oldValue", "kotlinx-coroutines-core"}, mo11816k = 2, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: CoroutineContext.kt */
public final class CoroutineContextKt {
    public static final String COROUTINES_SCHEDULER_PROPERTY_NAME = "kotlinx.coroutines.scheduler";
    private static final String DEBUG_THREAD_NAME_SEPARATOR = " @";
    private static final boolean useCoroutinesScheduler;

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        if (r0.equals(top.defaults.checkerboarddrawable.BuildConfig.FLAVOR) != false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0051, code lost:
        throw new java.lang.IllegalStateException(("System property 'kotlinx.coroutines.scheduler' has unrecognized value '" + r0 + '\'').toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        if (r0.equals(kotlinx.coroutines.DebugKt.DEBUG_PROPERTY_VALUE_ON) != false) goto L_0x0052;
     */
    static {
        /*
            java.lang.String r0 = "kotlinx.coroutines.scheduler"
            java.lang.String r0 = kotlinx.coroutines.internal.SystemPropsKt.systemProp(r0)
            r1 = 0
            if (r0 == 0) goto L_0x0052
            int r2 = r0.hashCode()
            switch(r2) {
                case 0: goto L_0x0024;
                case 3551: goto L_0x001b;
                case 109935: goto L_0x0011;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x002d
        L_0x0011:
            java.lang.String r2 = "off"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0010
            r2 = 0
            goto L_0x0053
        L_0x001b:
            java.lang.String r2 = "on"
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0010
            goto L_0x0052
        L_0x0024:
            java.lang.String r2 = ""
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0010
            goto L_0x0052
        L_0x002d:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "System property 'kotlinx.coroutines.scheduler' has unrecognized value '"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            r3 = 39
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r2 = r2.toString()
            r3.<init>(r2)
            java.lang.Throwable r3 = (java.lang.Throwable) r3
            throw r3
        L_0x0052:
            r2 = 1
        L_0x0053:
            useCoroutinesScheduler = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CoroutineContextKt.<clinit>():void");
    }

    public static final boolean getUseCoroutinesScheduler() {
        return useCoroutinesScheduler;
    }

    public static final CoroutineDispatcher createDefaultDispatcher() {
        return useCoroutinesScheduler ? DefaultScheduler.INSTANCE : CommonPool.INSTANCE;
    }

    public static final CoroutineContext newCoroutineContext(CoroutineScope $this$newCoroutineContext, CoroutineContext context) {
        CoroutineContext combined = $this$newCoroutineContext.getCoroutineContext().plus(context);
        CoroutineContext debug = DebugKt.getDEBUG() ? combined.plus(new CoroutineId(DebugKt.getCOROUTINE_ID().incrementAndGet())) : combined;
        return (combined == Dispatchers.getDefault() || combined.get(ContinuationInterceptor.Key) != null) ? debug : debug.plus(Dispatchers.getDefault());
    }

    public static final <T> T withCoroutineContext(CoroutineContext context, Object countOrElement, Function0<? extends T> block) {
        Object oldValue = ThreadContextKt.updateThreadContext(context, countOrElement);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            ThreadContextKt.restoreThreadContext(context, oldValue);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final <T> T withContinuationContext(Continuation<?> continuation, Object countOrElement, Function0<? extends T> block) {
        UndispatchedCoroutine undispatchedCompletion;
        CoroutineContext context = continuation.getContext();
        Object oldValue = ThreadContextKt.updateThreadContext(context, countOrElement);
        if (oldValue != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCompletion = updateUndispatchedCompletion(continuation, context, oldValue);
        } else {
            undispatchedCompletion = null;
        }
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            if (undispatchedCompletion == null || undispatchedCompletion.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context, oldValue);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public static final UndispatchedCoroutine<?> updateUndispatchedCompletion(Continuation<?> $this$updateUndispatchedCompletion, CoroutineContext context, Object oldValue) {
        if (!($this$updateUndispatchedCompletion instanceof CoroutineStackFrame)) {
            return null;
        }
        if (!(context.get(UndispatchedMarker.INSTANCE) != null)) {
            return null;
        }
        UndispatchedCoroutine completion = undispatchedCompletion((CoroutineStackFrame) $this$updateUndispatchedCompletion);
        if (completion != null) {
            completion.saveThreadContext(context, oldValue);
        }
        return completion;
    }

    public static final UndispatchedCoroutine<?> undispatchedCompletion(CoroutineStackFrame $this$undispatchedCompletion) {
        CoroutineStackFrame completion = $this$undispatchedCompletion;
        while (!(completion instanceof DispatchedCoroutine) && (completion = completion.getCallerFrame()) != null) {
            if (completion instanceof UndispatchedCoroutine) {
                return (UndispatchedCoroutine) completion;
            }
        }
        return null;
    }

    public static final String getCoroutineName(CoroutineContext $this$coroutineName) {
        CoroutineId coroutineId;
        String name;
        if (!DebugKt.getDEBUG() || (coroutineId = (CoroutineId) $this$coroutineName.get(CoroutineId.Key)) == null) {
            return null;
        }
        CoroutineName coroutineName = (CoroutineName) $this$coroutineName.get(CoroutineName.Key);
        String coroutineName2 = "coroutine";
        if (!(coroutineName == null || (name = coroutineName.getName()) == null)) {
            coroutineName2 = name;
        }
        return coroutineName2 + '#' + coroutineId.getId();
    }
}
