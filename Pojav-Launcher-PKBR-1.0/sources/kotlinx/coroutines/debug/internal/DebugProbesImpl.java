package kotlinx.coroutines.debug.internal;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(mo11814d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\bÀ\u0002\u0018\u00002\u00020\u0013:\u0002\u0001B\t\b\u0002¢\u0006\u0004\b\u0001\u0010\u0002J3\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0004\b\b\u0010\tJ\u0015\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\r\u0010\u000eJ\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f¢\u0006\u0004\b\u0011\u0010\u0012J>\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u000f\"\b\b\u0000\u0010\u0014*\u00020\u00132\u001c\u0010\u0018\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00028\u00000\u0015H\b¢\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001b\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\u001b\u0010\u000eJ\u0013\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000f¢\u0006\u0004\b\u001d\u0010\u0012J)\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f2\u0006\u0010\u001e\u001a\u00020\u00102\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f¢\u0006\u0004\b!\u0010\"J5\u0010'\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f2\u0006\u0010$\u001a\u00020#2\b\u0010&\u001a\u0004\u0018\u00010%2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b'\u0010(J?\u0010.\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020)0-2\u0006\u0010*\u001a\u00020)2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001f0+2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b.\u0010/J3\u00101\u001a\u00020)2\u0006\u00100\u001a\u00020)2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u001f0+2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b1\u00102J\u001d\u00105\u001a\u0010\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020\f\u0018\u000103H\u0002¢\u0006\u0004\b5\u00106J\u0015\u00109\u001a\u00020#2\u0006\u00108\u001a\u000207¢\u0006\u0004\b9\u0010:J\r\u0010;\u001a\u00020\f¢\u0006\u0004\b;\u0010\u0002J%\u0010=\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\b=\u0010>J\u001b\u0010@\u001a\u00020\f2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0002¢\u0006\u0004\b@\u0010AJ)\u0010D\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\"\u0004\b\u0000\u0010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0000¢\u0006\u0004\bB\u0010CJ\u001b\u0010G\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bE\u0010FJ\u001b\u0010I\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000¢\u0006\u0004\bH\u0010FJ'\u0010L\u001a\b\u0012\u0004\u0012\u00020\u001f0\u000f\"\b\b\u0000\u0010\u0003*\u00020J2\u0006\u0010K\u001a\u00028\u0000H\u0002¢\u0006\u0004\bL\u0010MJ\u000f\u0010N\u001a\u00020\fH\u0002¢\u0006\u0004\bN\u0010\u0002J\u000f\u0010O\u001a\u00020\fH\u0002¢\u0006\u0004\bO\u0010\u0002J\r\u0010P\u001a\u00020\f¢\u0006\u0004\bP\u0010\u0002J\u001f\u0010R\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020Q2\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\bR\u0010SJ#\u0010T\u001a\u00020\f2\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\bT\u0010UJ/\u0010T\u001a\u00020\f2\n\u0010?\u001a\u0006\u0012\u0002\b\u00030\u00162\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010$\u001a\u00020#H\u0002¢\u0006\u0004\bT\u0010VJ;\u0010^\u001a\u00020\f*\u0002072\u0012\u0010Y\u001a\u000e\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u00020X0W2\n\u0010\\\u001a\u00060Zj\u0002`[2\u0006\u0010]\u001a\u00020#H\u0002¢\u0006\u0004\b^\u0010_J\u0017\u0010`\u001a\u000204*\u0006\u0012\u0002\b\u00030\u0016H\u0002¢\u0006\u0004\b`\u0010aJ\u001d\u0010?\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016*\u0006\u0012\u0002\b\u00030\u0004H\u0002¢\u0006\u0004\b?\u0010bJ\u001a\u0010?\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0016*\u00020QH\u0010¢\u0006\u0004\b?\u0010cJ\u0016\u0010d\u001a\u0004\u0018\u00010Q*\u00020QH\u0010¢\u0006\u0004\bd\u0010eJ\u001b\u0010f\u001a\u0004\u0018\u00010\u0006*\b\u0012\u0004\u0012\u00020\u001f0\u000fH\u0002¢\u0006\u0004\bf\u0010gR\u0014\u0010h\u001a\u00020#8\u0002XT¢\u0006\u0006\n\u0004\bh\u0010iR \u0010k\u001a\u000e\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020X0j8\u0002X\u0004¢\u0006\u0006\n\u0004\bk\u0010lR\u001e\u0010p\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00160m8BX\u0004¢\u0006\u0006\u001a\u0004\bn\u0010oR$\u0010q\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u0002040j8\u0002X\u0004¢\u0006\u0006\n\u0004\bq\u0010lR\u0014\u0010s\u001a\u00020r8\u0002X\u0004¢\u0006\u0006\n\u0004\bs\u0010tR\u0014\u0010v\u001a\u00020u8\u0002X\u0004¢\u0006\u0006\n\u0004\bv\u0010wR\"\u0010x\u001a\u0010\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020\f\u0018\u0001038\u0002X\u0004¢\u0006\u0006\n\u0004\bx\u0010yR\"\u0010z\u001a\u0002048\u0006@\u0006X\u000e¢\u0006\u0012\n\u0004\bz\u0010{\u001a\u0004\b|\u0010}\"\u0004\b~\u0010R\u0019\u0010\u0001\u001a\u00020)8\u0002@\u0002X\u000e¢\u0006\b\n\u0006\b\u0001\u0010\u0001R\u0016\u0010\u0001\u001a\u0002048@X\u0004¢\u0006\u0007\u001a\u0005\b\u0001\u0010}R&\u0010\u0001\u001a\u0002048\u0006@\u0006X\u000e¢\u0006\u0015\n\u0005\b\u0001\u0010{\u001a\u0005\b\u0001\u0010}\"\u0005\b\u0001\u0010R\u001b\u0010\u0001\u001a\u0004\u0018\u00010%8\u0002@\u0002X\u000e¢\u0006\b\n\u0006\b\u0001\u0010\u0001R\"\u0010\u0001\u001a\u00020#*\u0002078BX\u0004¢\u0006\u000f\u0012\u0006\b\u0001\u0010\u0001\u001a\u0005\b\u0001\u0010:R\u001b\u0010\u0001\u001a\u000204*\u00020\u001f8BX\u0004¢\u0006\b\u001a\u0006\b\u0001\u0010\u0001¨\u0006\u0001"}, mo11815d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl;", "<init>", "()V", "T", "Lkotlin/coroutines/Continuation;", "completion", "Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "frame", "createOwner", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/StackTraceFrame;)Lkotlin/coroutines/Continuation;", "Ljava/io/PrintStream;", "out", "", "dumpCoroutines", "(Ljava/io/PrintStream;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;", "dumpCoroutinesInfo", "()Ljava/util/List;", "", "R", "Lkotlin/Function2;", "Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "Lkotlin/coroutines/CoroutineContext;", "create", "dumpCoroutinesInfoImpl", "(Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "dumpCoroutinesSynchronized", "Lkotlinx/coroutines/debug/internal/DebuggerInfo;", "dumpDebuggerInfo", "info", "Ljava/lang/StackTraceElement;", "coroutineTrace", "enhanceStackTraceWithThreadDump", "(Lkotlinx/coroutines/debug/internal/DebugCoroutineInfo;Ljava/util/List;)Ljava/util/List;", "", "state", "Ljava/lang/Thread;", "thread", "enhanceStackTraceWithThreadDumpImpl", "(Ljava/lang/String;Ljava/lang/Thread;Ljava/util/List;)Ljava/util/List;", "", "indexOfResumeWith", "", "actualTrace", "Lkotlin/Pair;", "findContinuationStartIndex", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)Lkotlin/Pair;", "frameIndex", "findIndexOfFrame", "(I[Ljava/lang/StackTraceElement;Ljava/util/List;)I", "Lkotlin/Function1;", "", "getDynamicAttach", "()Lkotlin/jvm/functions/Function1;", "Lkotlinx/coroutines/Job;", "job", "hierarchyToString", "(Lkotlinx/coroutines/Job;)Ljava/lang/String;", "install", "frames", "printStackTrace", "(Ljava/io/PrintStream;Ljava/util/List;)V", "owner", "probeCoroutineCompleted", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)V", "probeCoroutineCreated$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "probeCoroutineCreated", "probeCoroutineResumed$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)V", "probeCoroutineResumed", "probeCoroutineSuspended$kotlinx_coroutines_core", "probeCoroutineSuspended", "", "throwable", "sanitizeStackTrace", "(Ljava/lang/Throwable;)Ljava/util/List;", "startWeakRefCleanerThread", "stopWeakRefCleanerThread", "uninstall", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "updateRunningState", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;Ljava/lang/String;)V", "updateState", "(Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;Lkotlin/coroutines/Continuation;Ljava/lang/String;)V", "", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "map", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "builder", "indent", "build", "(Lkotlinx/coroutines/Job;Ljava/util/Map;Ljava/lang/StringBuilder;Ljava/lang/String;)V", "isFinished", "(Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;)Z", "(Lkotlin/coroutines/Continuation;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "realCaller", "(Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "toStackTraceFrame", "(Ljava/util/List;)Lkotlinx/coroutines/debug/internal/StackTraceFrame;", "ARTIFICIAL_FRAME_MESSAGE", "Ljava/lang/String;", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "callerInfoCache", "Lkotlinx/coroutines/debug/internal/ConcurrentWeakMap;", "", "getCapturedCoroutines", "()Ljava/util/Set;", "capturedCoroutines", "capturedCoroutinesMap", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "coroutineStateLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "Ljava/text/SimpleDateFormat;", "dateFormat", "Ljava/text/SimpleDateFormat;", "dynamicAttach", "Lkotlin/jvm/functions/Function1;", "enableCreationStackTraces", "Z", "getEnableCreationStackTraces", "()Z", "setEnableCreationStackTraces", "(Z)V", "installations", "I", "isInstalled$kotlinx_coroutines_core", "isInstalled", "sanitizeStackTraces", "getSanitizeStackTraces", "setSanitizeStackTraces", "weakRefCleanerThread", "Ljava/lang/Thread;", "getDebugString", "getDebugString$annotations", "(Lkotlinx/coroutines/Job;)V", "debugString", "isInternalMethod", "(Ljava/lang/StackTraceElement;)Z", "CoroutineOwner", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: DebugProbesImpl.kt */
public final class DebugProbesImpl {
    private static final String ARTIFICIAL_FRAME_MESSAGE = "Coroutine creation stacktrace";
    public static final DebugProbesImpl INSTANCE;
    /* access modifiers changed from: private */
    public static final ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> callerInfoCache = new ConcurrentWeakMap<>(true);
    private static final ConcurrentWeakMap<CoroutineOwner<?>, Boolean> capturedCoroutinesMap = new ConcurrentWeakMap<>(false, 1, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final ReentrantReadWriteLock coroutineStateLock = new ReentrantReadWriteLock();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final /* synthetic */ SequenceNumberRefVolatile debugProbesImpl$SequenceNumberRefVolatile = new SequenceNumberRefVolatile(0);
    private static final Function1<Boolean, Unit> dynamicAttach;
    private static boolean enableCreationStackTraces = true;
    private static volatile int installations;
    private static boolean sanitizeStackTraces = true;
    private static final /* synthetic */ AtomicLongFieldUpdater sequenceNumber$FU = AtomicLongFieldUpdater.newUpdater(SequenceNumberRefVolatile.class, "sequenceNumber");
    private static Thread weakRefCleanerThread;

    /* synthetic */ class SequenceNumberRefVolatile {
        volatile long sequenceNumber;

        public SequenceNumberRefVolatile(long j) {
            this.sequenceNumber = j;
        }
    }

    private static /* synthetic */ void getDebugString$annotations(Job job) {
    }

    private DebugProbesImpl() {
    }

    static {
        DebugProbesImpl debugProbesImpl = new DebugProbesImpl();
        INSTANCE = debugProbesImpl;
        dynamicAttach = debugProbesImpl.getDynamicAttach();
    }

    /* access modifiers changed from: private */
    public final Set<CoroutineOwner<?>> getCapturedCoroutines() {
        return capturedCoroutinesMap.keySet();
    }

    public final boolean isInstalled$kotlinx_coroutines_core() {
        return installations > 0;
    }

    public final boolean getSanitizeStackTraces() {
        return sanitizeStackTraces;
    }

    public final void setSanitizeStackTraces(boolean z) {
        sanitizeStackTraces = z;
    }

    public final boolean getEnableCreationStackTraces() {
        return enableCreationStackTraces;
    }

    public final void setEnableCreationStackTraces(boolean z) {
        enableCreationStackTraces = z;
    }

    private final Function1<Boolean, Unit> getDynamicAttach() {
        Object obj;
        try {
            Result.Companion companion = Result.Companion;
            DebugProbesImpl debugProbesImpl = this;
            Object newInstance = Class.forName("kotlinx.coroutines.debug.internal.ByteBuddyDynamicAttach").getConstructors()[0].newInstance(new Object[0]);
            if (newInstance != null) {
                obj = Result.m64constructorimpl((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(newInstance, 1));
                if (Result.m70isFailureimpl(obj)) {
                    obj = null;
                }
                return (Function1) obj;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Function1<kotlin.Boolean, kotlin.Unit>");
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m64constructorimpl(ResultKt.createFailure(th));
        }
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public final void install() {
        /*
            r8 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L_0x0012
            int r2 = r0.getReadHoldCount()
            goto L_0x0013
        L_0x0012:
            r2 = r3
        L_0x0013:
            r4 = r3
        L_0x0014:
            if (r4 >= r2) goto L_0x001c
            r1.unlock()
            int r4 = r4 + 1
            goto L_0x0014
        L_0x001c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r4 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r5 = INSTANCE     // Catch:{ all -> 0x0070 }
            int r6 = installations     // Catch:{ all -> 0x0070 }
            r7 = 1
            int r6 = r6 + r7
            installations = r6     // Catch:{ all -> 0x0070 }
            int r6 = installations     // Catch:{ all -> 0x0070 }
            if (r6 <= r7) goto L_0x003c
        L_0x0030:
            if (r3 >= r2) goto L_0x0038
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0030
        L_0x0038:
            r0.unlock()
            return
        L_0x003c:
            r5.startWeakRefCleanerThread()     // Catch:{ all -> 0x0070 }
            kotlinx.coroutines.debug.AgentPremain r5 = kotlinx.coroutines.debug.AgentPremain.INSTANCE     // Catch:{ all -> 0x0070 }
            boolean r5 = r5.isInstalledStatically()     // Catch:{ all -> 0x0070 }
            if (r5 == 0) goto L_0x0053
        L_0x0047:
            if (r3 >= r2) goto L_0x004f
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0047
        L_0x004f:
            r0.unlock()
            return
        L_0x0053:
            kotlin.jvm.functions.Function1<java.lang.Boolean, kotlin.Unit> r5 = dynamicAttach     // Catch:{ all -> 0x0070 }
            if (r5 != 0) goto L_0x0058
        L_0x0057:
            goto L_0x0060
        L_0x0058:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r7)     // Catch:{ all -> 0x0070 }
            r5.invoke(r6)     // Catch:{ all -> 0x0070 }
            goto L_0x0057
        L_0x0060:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0070 }
        L_0x0064:
            if (r3 >= r2) goto L_0x006c
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0064
        L_0x006c:
            r0.unlock()
            return
        L_0x0070:
            r4 = move-exception
        L_0x0071:
            if (r3 >= r2) goto L_0x0079
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0071
        L_0x0079:
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.install():void");
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public final void uninstall() {
        /*
            r7 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            r3 = 0
            if (r2 != 0) goto L_0x0012
            int r2 = r0.getReadHoldCount()
            goto L_0x0013
        L_0x0012:
            r2 = r3
        L_0x0013:
            r4 = r3
        L_0x0014:
            if (r4 >= r2) goto L_0x001c
            r1.unlock()
            int r4 = r4 + 1
            goto L_0x0014
        L_0x001c:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r4 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r5 = INSTANCE     // Catch:{ all -> 0x008f }
            boolean r6 = r5.isInstalled$kotlinx_coroutines_core()     // Catch:{ all -> 0x008f }
            if (r6 == 0) goto L_0x0080
            int r6 = installations     // Catch:{ all -> 0x008f }
            int r6 = r6 + -1
            installations = r6     // Catch:{ all -> 0x008f }
            int r6 = installations     // Catch:{ all -> 0x008f }
            if (r6 == 0) goto L_0x0042
        L_0x0036:
            if (r3 >= r2) goto L_0x003e
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0036
        L_0x003e:
            r0.unlock()
            return
        L_0x0042:
            r5.stopWeakRefCleanerThread()     // Catch:{ all -> 0x008f }
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner<?>, java.lang.Boolean> r5 = capturedCoroutinesMap     // Catch:{ all -> 0x008f }
            r5.clear()     // Catch:{ all -> 0x008f }
            kotlinx.coroutines.debug.internal.ConcurrentWeakMap<kotlin.coroutines.jvm.internal.CoroutineStackFrame, kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl> r5 = callerInfoCache     // Catch:{ all -> 0x008f }
            r5.clear()     // Catch:{ all -> 0x008f }
            kotlinx.coroutines.debug.AgentPremain r5 = kotlinx.coroutines.debug.AgentPremain.INSTANCE     // Catch:{ all -> 0x008f }
            boolean r5 = r5.isInstalledStatically()     // Catch:{ all -> 0x008f }
            if (r5 == 0) goto L_0x0063
        L_0x0057:
            if (r3 >= r2) goto L_0x005f
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0057
        L_0x005f:
            r0.unlock()
            return
        L_0x0063:
            kotlin.jvm.functions.Function1<java.lang.Boolean, kotlin.Unit> r5 = dynamicAttach     // Catch:{ all -> 0x008f }
            if (r5 != 0) goto L_0x0068
        L_0x0067:
            goto L_0x0070
        L_0x0068:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x008f }
            r5.invoke(r6)     // Catch:{ all -> 0x008f }
            goto L_0x0067
        L_0x0070:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x008f }
        L_0x0074:
            if (r3 >= r2) goto L_0x007c
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0074
        L_0x007c:
            r0.unlock()
            return
        L_0x0080:
            r5 = 0
            java.lang.String r6 = "Agent was not installed"
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x008f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x008f }
            r5.<init>(r6)     // Catch:{ all -> 0x008f }
            java.lang.Throwable r5 = (java.lang.Throwable) r5     // Catch:{ all -> 0x008f }
            throw r5     // Catch:{ all -> 0x008f }
        L_0x008f:
            r4 = move-exception
        L_0x0090:
            if (r3 >= r2) goto L_0x0098
            r1.lock()
            int r3 = r3 + 1
            goto L_0x0090
        L_0x0098:
            r0.unlock()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.uninstall():void");
    }

    private final void startWeakRefCleanerThread() {
        weakRefCleanerThread = ThreadsKt.thread$default(false, true, (ClassLoader) null, "Coroutines Debugger Cleaner", 0, DebugProbesImpl$startWeakRefCleanerThread$1.INSTANCE, 21, (Object) null);
    }

    private final void stopWeakRefCleanerThread() {
        Thread thread = weakRefCleanerThread;
        if (thread != null) {
            thread.interrupt();
        }
        weakRefCleanerThread = null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f3 A[LOOP:4: B:41:0x00f1->B:42:0x00f3, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String hierarchyToString(kotlinx.coroutines.Job r17) {
        /*
            r16 = this;
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r1 = r0.readLock()
            int r2 = r0.getWriteHoldCount()
            if (r2 != 0) goto L_0x0011
            int r2 = r0.getReadHoldCount()
            goto L_0x0012
        L_0x0011:
            r2 = 0
        L_0x0012:
            r4 = 0
        L_0x0013:
            if (r4 >= r2) goto L_0x001b
            r1.unlock()
            int r4 = r4 + 1
            goto L_0x0013
        L_0x001b:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r4 = r0.writeLock()
            r4.lock()
            r0 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r5 = INSTANCE     // Catch:{ all -> 0x00ed }
            boolean r6 = r5.isInstalled$kotlinx_coroutines_core()     // Catch:{ all -> 0x00ed }
            if (r6 == 0) goto L_0x00da
            java.util.Set r5 = r5.getCapturedCoroutines()     // Catch:{ all -> 0x00ed }
            java.lang.Iterable r5 = (java.lang.Iterable) r5     // Catch:{ all -> 0x00ed }
            r6 = 0
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x00ed }
            r7.<init>()     // Catch:{ all -> 0x00ed }
            java.util.Collection r7 = (java.util.Collection) r7     // Catch:{ all -> 0x00ed }
            r8 = r5
            r9 = 0
            java.util.Iterator r10 = r8.iterator()     // Catch:{ all -> 0x00ed }
        L_0x0040:
            boolean r11 = r10.hasNext()     // Catch:{ all -> 0x00ed }
            if (r11 == 0) goto L_0x0067
            java.lang.Object r11 = r10.next()     // Catch:{ all -> 0x00ed }
            r12 = r11
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r12 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r12     // Catch:{ all -> 0x00ed }
            r13 = 0
            kotlin.coroutines.Continuation<T> r14 = r12.delegate     // Catch:{ all -> 0x00ed }
            kotlin.coroutines.CoroutineContext r14 = r14.getContext()     // Catch:{ all -> 0x00ed }
            kotlinx.coroutines.Job$Key r15 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x00ed }
            kotlin.coroutines.CoroutineContext$Key r15 = (kotlin.coroutines.CoroutineContext.Key) r15     // Catch:{ all -> 0x00ed }
            kotlin.coroutines.CoroutineContext$Element r14 = r14.get(r15)     // Catch:{ all -> 0x00ed }
            if (r14 == 0) goto L_0x0060
            r14 = 1
            goto L_0x0061
        L_0x0060:
            r14 = 0
        L_0x0061:
            if (r14 == 0) goto L_0x0040
            r7.add(r11)     // Catch:{ all -> 0x00ed }
            goto L_0x0040
        L_0x0067:
            java.util.List r7 = (java.util.List) r7     // Catch:{ all -> 0x00ed }
            java.lang.Iterable r7 = (java.lang.Iterable) r7     // Catch:{ all -> 0x00ed }
            r5 = r7
            r6 = 0
            r7 = 10
            int r7 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r5, r7)     // Catch:{ all -> 0x00ed }
            int r7 = kotlin.collections.MapsKt.mapCapacity(r7)     // Catch:{ all -> 0x00ed }
            r8 = 16
            int r7 = kotlin.ranges.RangesKt.coerceAtLeast((int) r7, (int) r8)     // Catch:{ all -> 0x00ed }
            java.util.LinkedHashMap r8 = new java.util.LinkedHashMap     // Catch:{ all -> 0x00ed }
            r8.<init>(r7)     // Catch:{ all -> 0x00ed }
            java.util.Map r8 = (java.util.Map) r8     // Catch:{ all -> 0x00ed }
            r9 = r5
            r10 = 0
            java.util.Iterator r11 = r9.iterator()     // Catch:{ all -> 0x00ed }
        L_0x008c:
            boolean r12 = r11.hasNext()     // Catch:{ all -> 0x00ed }
            if (r12 == 0) goto L_0x00af
            java.lang.Object r12 = r11.next()     // Catch:{ all -> 0x00ed }
            r13 = r12
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r13 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r13     // Catch:{ all -> 0x00ed }
            r14 = 0
            kotlin.coroutines.Continuation<T> r15 = r13.delegate     // Catch:{ all -> 0x00ed }
            kotlin.coroutines.CoroutineContext r15 = r15.getContext()     // Catch:{ all -> 0x00ed }
            kotlinx.coroutines.Job r15 = kotlinx.coroutines.JobKt.getJob(r15)     // Catch:{ all -> 0x00ed }
            r13 = r12
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r13 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r13     // Catch:{ all -> 0x00ed }
            r14 = 0
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r3 = r13.info     // Catch:{ all -> 0x00ed }
            r8.put(r15, r3)     // Catch:{ all -> 0x00ed }
            goto L_0x008c
        L_0x00af:
            r3 = r8
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ed }
            r5.<init>()     // Catch:{ all -> 0x00ed }
            r6 = r5
            r7 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r8 = INSTANCE     // Catch:{ all -> 0x00ed }
            java.lang.String r9 = ""
            r10 = r17
            r8.build(r10, r3, r6, r9)     // Catch:{ all -> 0x00eb }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00eb }
            java.lang.String r6 = "StringBuilder().apply(builderAction).toString()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)     // Catch:{ all -> 0x00eb }
            r3 = 0
        L_0x00ce:
            if (r3 >= r2) goto L_0x00d6
            r1.lock()
            int r3 = r3 + 1
            goto L_0x00ce
        L_0x00d6:
            r4.unlock()
            return r5
        L_0x00da:
            r10 = r17
            r3 = 0
            java.lang.String r5 = "Debug probes are not installed"
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00eb }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x00eb }
            r3.<init>(r5)     // Catch:{ all -> 0x00eb }
            java.lang.Throwable r3 = (java.lang.Throwable) r3     // Catch:{ all -> 0x00eb }
            throw r3     // Catch:{ all -> 0x00eb }
        L_0x00eb:
            r0 = move-exception
            goto L_0x00f0
        L_0x00ed:
            r0 = move-exception
            r10 = r17
        L_0x00f0:
            r3 = 0
        L_0x00f1:
            if (r3 >= r2) goto L_0x00f9
            r1.lock()
            int r3 = r3 + 1
            goto L_0x00f1
        L_0x00f9:
            r4.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.hierarchyToString(kotlinx.coroutines.Job):java.lang.String");
    }

    private final void build(Job $this$build, Map<Job, DebugCoroutineInfoImpl> map, StringBuilder builder, String indent) {
        String newIndent;
        DebugCoroutineInfoImpl info = map.get($this$build);
        if (info != null) {
            builder.append(indent + getDebugString($this$build) + ", continuation is " + info.getState() + " at line " + ((StackTraceElement) CollectionsKt.firstOrNull(info.lastObservedStackTrace())) + 10);
            newIndent = Intrinsics.stringPlus(indent, "\t");
        } else if (!($this$build instanceof ScopeCoroutine)) {
            builder.append(indent + getDebugString($this$build) + 10);
            newIndent = Intrinsics.stringPlus(indent, "\t");
        } else {
            newIndent = indent;
        }
        for (Job child : $this$build.getChildren()) {
            build(child, map, builder, newIndent);
        }
    }

    private final String getDebugString(Job $this$debugString) {
        return $this$debugString instanceof JobSupport ? ((JobSupport) $this$debugString).toDebugString() : $this$debugString.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00e1 A[LOOP:3: B:40:0x00df->B:41:0x00e1, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <R> java.util.List<R> dumpCoroutinesInfoImpl(kotlin.jvm.functions.Function2<? super kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner<?>, ? super kotlin.coroutines.CoroutineContext, ? extends R> r23) {
        /*
            r22 = this;
            r1 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r0.readLock()
            int r3 = r0.getWriteHoldCount()
            if (r3 != 0) goto L_0x0014
            int r3 = r0.getReadHoldCount()
            goto L_0x0015
        L_0x0014:
            r3 = 0
        L_0x0015:
            r5 = 0
        L_0x0016:
            if (r5 >= r3) goto L_0x001e
            r2.unlock()
            int r5 = r5 + 1
            goto L_0x0016
        L_0x001e:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r5 = r0.writeLock()
            r5.lock()
            r0 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r7 = INSTANCE     // Catch:{ all -> 0x00d5 }
            boolean r8 = r7.isInstalled$kotlinx_coroutines_core()     // Catch:{ all -> 0x00d5 }
            if (r8 == 0) goto L_0x00c0
            java.util.Set r7 = r7.getCapturedCoroutines()     // Catch:{ all -> 0x00d5 }
            java.lang.Iterable r7 = (java.lang.Iterable) r7     // Catch:{ all -> 0x00d5 }
            r8 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-14$$inlined$sortedBy$1 r9 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-14$$inlined$sortedBy$1     // Catch:{ all -> 0x00d5 }
            r9.<init>()     // Catch:{ all -> 0x00d5 }
            java.util.Comparator r9 = (java.util.Comparator) r9     // Catch:{ all -> 0x00d5 }
            java.util.List r9 = kotlin.collections.CollectionsKt.sortedWith(r7, r9)     // Catch:{ all -> 0x00d5 }
            java.lang.Iterable r9 = (java.lang.Iterable) r9     // Catch:{ all -> 0x00d5 }
            r7 = r9
            r8 = 0
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ all -> 0x00d5 }
            r9.<init>()     // Catch:{ all -> 0x00d5 }
            java.util.Collection r9 = (java.util.Collection) r9     // Catch:{ all -> 0x00d5 }
            r10 = r7
            r11 = 0
            r12 = r10
            r13 = 0
            java.util.Iterator r14 = r12.iterator()     // Catch:{ all -> 0x00d5 }
        L_0x0054:
            boolean r15 = r14.hasNext()     // Catch:{ all -> 0x00d5 }
            if (r15 == 0) goto L_0x00a2
            java.lang.Object r15 = r14.next()     // Catch:{ all -> 0x00d5 }
            r16 = r15
            r17 = 0
            r18 = r16
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r18 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r18     // Catch:{ all -> 0x00d5 }
            r19 = r18
            r18 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r4 = INSTANCE     // Catch:{ all -> 0x00d5 }
            r6 = r19
            boolean r4 = r4.isFinished(r6)     // Catch:{ all -> 0x00d5 }
            r19 = 0
            if (r4 == 0) goto L_0x007b
            r20 = r1
            r1 = r23
            goto L_0x0094
        L_0x007b:
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r4 = r6.info     // Catch:{ all -> 0x00d5 }
            kotlin.coroutines.CoroutineContext r4 = r4.getContext()     // Catch:{ all -> 0x00d5 }
            if (r4 != 0) goto L_0x0088
            r20 = r1
            r1 = r23
            goto L_0x0094
        L_0x0088:
            r19 = 0
            r20 = r1
            r1 = r23
            java.lang.Object r21 = r1.invoke(r6, r4)     // Catch:{ all -> 0x00d3 }
            r19 = r21
        L_0x0094:
            if (r19 == 0) goto L_0x009e
            r4 = r19
            r6 = 0
            r9.add(r4)     // Catch:{ all -> 0x00d3 }
            goto L_0x009f
        L_0x009e:
        L_0x009f:
            r1 = r20
            goto L_0x0054
        L_0x00a2:
            r20 = r1
            r1 = r23
            r4 = r9
            java.util.List r4 = (java.util.List) r4     // Catch:{ all -> 0x00d3 }
            r6 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r6)
            r0 = 0
        L_0x00b1:
            if (r0 >= r3) goto L_0x00b9
            r2.lock()
            int r0 = r0 + 1
            goto L_0x00b1
        L_0x00b9:
            r5.unlock()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r6)
            return r4
        L_0x00c0:
            r20 = r1
            r1 = r23
            r4 = 0
            java.lang.String r6 = "Debug probes are not installed"
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00d3 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00d3 }
            r4.<init>(r6)     // Catch:{ all -> 0x00d3 }
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x00d3 }
            throw r4     // Catch:{ all -> 0x00d3 }
        L_0x00d3:
            r0 = move-exception
            goto L_0x00da
        L_0x00d5:
            r0 = move-exception
            r20 = r1
            r1 = r23
        L_0x00da:
            r4 = 1
            kotlin.jvm.internal.InlineMarker.finallyStart(r4)
            r6 = 0
        L_0x00df:
            if (r6 >= r3) goto L_0x00e7
            r2.lock()
            int r6 = r6 + 1
            goto L_0x00df
        L_0x00e7:
            r5.unlock()
            kotlin.jvm.internal.InlineMarker.finallyEnd(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutinesInfoImpl(kotlin.jvm.functions.Function2):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f8 A[LOOP:3: B:41:0x00f6->B:42:0x00f8, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<kotlinx.coroutines.debug.internal.DebugCoroutineInfo> dumpCoroutinesInfo() {
        /*
            r27 = this;
            r1 = r27
            r2 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r0.readLock()
            int r4 = r0.getWriteHoldCount()
            if (r4 != 0) goto L_0x0016
            int r4 = r0.getReadHoldCount()
            goto L_0x0017
        L_0x0016:
            r4 = 0
        L_0x0017:
            r6 = 0
        L_0x0018:
            if (r6 >= r4) goto L_0x0020
            r3.unlock()
            int r6 = r6 + 1
            goto L_0x0018
        L_0x0020:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r6 = r0.writeLock()
            r6.lock()
            r0 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r7 = INSTANCE     // Catch:{ all -> 0x00f0 }
            boolean r8 = r7.isInstalled$kotlinx_coroutines_core()     // Catch:{ all -> 0x00f0 }
            if (r8 == 0) goto L_0x00d9
            java.util.Set r7 = r7.getCapturedCoroutines()     // Catch:{ all -> 0x00f0 }
            java.lang.Iterable r7 = (java.lang.Iterable) r7     // Catch:{ all -> 0x00f0 }
            r8 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-14$$inlined$sortedBy$1 r9 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-14$$inlined$sortedBy$1     // Catch:{ all -> 0x00f0 }
            r9.<init>()     // Catch:{ all -> 0x00f0 }
            java.util.Comparator r9 = (java.util.Comparator) r9     // Catch:{ all -> 0x00f0 }
            java.util.List r9 = kotlin.collections.CollectionsKt.sortedWith(r7, r9)     // Catch:{ all -> 0x00f0 }
            java.lang.Iterable r9 = (java.lang.Iterable) r9     // Catch:{ all -> 0x00f0 }
            r7 = r9
            r8 = 0
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ all -> 0x00f0 }
            r9.<init>()     // Catch:{ all -> 0x00f0 }
            java.util.Collection r9 = (java.util.Collection) r9     // Catch:{ all -> 0x00f0 }
            r10 = r7
            r11 = 0
            r12 = r10
            r13 = 0
            java.util.Iterator r14 = r12.iterator()     // Catch:{ all -> 0x00f0 }
        L_0x0056:
            boolean r15 = r14.hasNext()     // Catch:{ all -> 0x00f0 }
            if (r15 == 0) goto L_0x00bf
            java.lang.Object r15 = r14.next()     // Catch:{ all -> 0x00f0 }
            r16 = r15
            r17 = 0
            r18 = r16
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r18 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r18     // Catch:{ all -> 0x00f0 }
            r19 = r18
            r18 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r5 = INSTANCE     // Catch:{ all -> 0x00f0 }
            r20 = r0
            r0 = r19
            boolean r5 = r5.isFinished(r0)     // Catch:{ all -> 0x00f0 }
            r19 = 0
            if (r5 == 0) goto L_0x0081
            r24 = r0
            r25 = r1
            r21 = r2
            goto L_0x00ad
        L_0x0081:
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r5 = r0.info     // Catch:{ all -> 0x00f0 }
            kotlin.coroutines.CoroutineContext r5 = r5.getContext()     // Catch:{ all -> 0x00f0 }
            if (r5 != 0) goto L_0x0090
            r24 = r0
            r25 = r1
            r21 = r2
            goto L_0x00ad
        L_0x0090:
            r19 = 0
            r21 = r0
            r22 = r5
            r23 = 0
            r24 = r0
            kotlinx.coroutines.debug.internal.DebugCoroutineInfo r0 = new kotlinx.coroutines.debug.internal.DebugCoroutineInfo     // Catch:{ all -> 0x00f0 }
            r25 = r1
            r1 = r21
            r21 = r2
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r2 = r1.info     // Catch:{ all -> 0x00ee }
            r26 = r1
            r1 = r22
            r0.<init>(r2, r1)     // Catch:{ all -> 0x00ee }
            r19 = r0
        L_0x00ad:
            if (r19 == 0) goto L_0x00b7
            r0 = r19
            r1 = 0
            r9.add(r0)     // Catch:{ all -> 0x00ee }
            goto L_0x00b8
        L_0x00b7:
        L_0x00b8:
            r0 = r20
            r2 = r21
            r1 = r25
            goto L_0x0056
        L_0x00bf:
            r20 = r0
            r25 = r1
            r21 = r2
            r0 = r9
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x00ee }
            r5 = 0
        L_0x00cc:
            if (r5 >= r4) goto L_0x00d4
            r3.lock()
            int r5 = r5 + 1
            goto L_0x00cc
        L_0x00d4:
            r6.unlock()
            return r0
        L_0x00d9:
            r20 = r0
            r25 = r1
            r21 = r2
            r0 = 0
            java.lang.String r1 = "Debug probes are not installed"
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00ee }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ee }
            r0.<init>(r1)     // Catch:{ all -> 0x00ee }
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x00ee }
            throw r0     // Catch:{ all -> 0x00ee }
        L_0x00ee:
            r0 = move-exception
            goto L_0x00f5
        L_0x00f0:
            r0 = move-exception
            r25 = r1
            r21 = r2
        L_0x00f5:
            r5 = 0
        L_0x00f6:
            if (r5 >= r4) goto L_0x00fe
            r3.lock()
            int r5 = r5 + 1
            goto L_0x00f6
        L_0x00fe:
            r6.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpCoroutinesInfo():java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f8 A[LOOP:3: B:41:0x00f6->B:42:0x00f8, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.List<kotlinx.coroutines.debug.internal.DebuggerInfo> dumpDebuggerInfo() {
        /*
            r27 = this;
            r1 = r27
            r2 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = coroutineStateLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r0.readLock()
            int r4 = r0.getWriteHoldCount()
            if (r4 != 0) goto L_0x0016
            int r4 = r0.getReadHoldCount()
            goto L_0x0017
        L_0x0016:
            r4 = 0
        L_0x0017:
            r6 = 0
        L_0x0018:
            if (r6 >= r4) goto L_0x0020
            r3.unlock()
            int r6 = r6 + 1
            goto L_0x0018
        L_0x0020:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r6 = r0.writeLock()
            r6.lock()
            r0 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r7 = INSTANCE     // Catch:{ all -> 0x00f0 }
            boolean r8 = r7.isInstalled$kotlinx_coroutines_core()     // Catch:{ all -> 0x00f0 }
            if (r8 == 0) goto L_0x00d9
            java.util.Set r7 = r7.getCapturedCoroutines()     // Catch:{ all -> 0x00f0 }
            java.lang.Iterable r7 = (java.lang.Iterable) r7     // Catch:{ all -> 0x00f0 }
            r8 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-14$$inlined$sortedBy$1 r9 = new kotlinx.coroutines.debug.internal.DebugProbesImpl$dumpCoroutinesInfoImpl$lambda-14$$inlined$sortedBy$1     // Catch:{ all -> 0x00f0 }
            r9.<init>()     // Catch:{ all -> 0x00f0 }
            java.util.Comparator r9 = (java.util.Comparator) r9     // Catch:{ all -> 0x00f0 }
            java.util.List r9 = kotlin.collections.CollectionsKt.sortedWith(r7, r9)     // Catch:{ all -> 0x00f0 }
            java.lang.Iterable r9 = (java.lang.Iterable) r9     // Catch:{ all -> 0x00f0 }
            r7 = r9
            r8 = 0
            java.util.ArrayList r9 = new java.util.ArrayList     // Catch:{ all -> 0x00f0 }
            r9.<init>()     // Catch:{ all -> 0x00f0 }
            java.util.Collection r9 = (java.util.Collection) r9     // Catch:{ all -> 0x00f0 }
            r10 = r7
            r11 = 0
            r12 = r10
            r13 = 0
            java.util.Iterator r14 = r12.iterator()     // Catch:{ all -> 0x00f0 }
        L_0x0056:
            boolean r15 = r14.hasNext()     // Catch:{ all -> 0x00f0 }
            if (r15 == 0) goto L_0x00bf
            java.lang.Object r15 = r14.next()     // Catch:{ all -> 0x00f0 }
            r16 = r15
            r17 = 0
            r18 = r16
            kotlinx.coroutines.debug.internal.DebugProbesImpl$CoroutineOwner r18 = (kotlinx.coroutines.debug.internal.DebugProbesImpl.CoroutineOwner) r18     // Catch:{ all -> 0x00f0 }
            r19 = r18
            r18 = 0
            kotlinx.coroutines.debug.internal.DebugProbesImpl r5 = INSTANCE     // Catch:{ all -> 0x00f0 }
            r20 = r0
            r0 = r19
            boolean r5 = r5.isFinished(r0)     // Catch:{ all -> 0x00f0 }
            r19 = 0
            if (r5 == 0) goto L_0x0081
            r24 = r0
            r25 = r1
            r21 = r2
            goto L_0x00ad
        L_0x0081:
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r5 = r0.info     // Catch:{ all -> 0x00f0 }
            kotlin.coroutines.CoroutineContext r5 = r5.getContext()     // Catch:{ all -> 0x00f0 }
            if (r5 != 0) goto L_0x0090
            r24 = r0
            r25 = r1
            r21 = r2
            goto L_0x00ad
        L_0x0090:
            r19 = 0
            r21 = r0
            r22 = r5
            r23 = 0
            r24 = r0
            kotlinx.coroutines.debug.internal.DebuggerInfo r0 = new kotlinx.coroutines.debug.internal.DebuggerInfo     // Catch:{ all -> 0x00f0 }
            r25 = r1
            r1 = r21
            r21 = r2
            kotlinx.coroutines.debug.internal.DebugCoroutineInfoImpl r2 = r1.info     // Catch:{ all -> 0x00ee }
            r26 = r1
            r1 = r22
            r0.<init>(r2, r1)     // Catch:{ all -> 0x00ee }
            r19 = r0
        L_0x00ad:
            if (r19 == 0) goto L_0x00b7
            r0 = r19
            r1 = 0
            r9.add(r0)     // Catch:{ all -> 0x00ee }
            goto L_0x00b8
        L_0x00b7:
        L_0x00b8:
            r0 = r20
            r2 = r21
            r1 = r25
            goto L_0x0056
        L_0x00bf:
            r20 = r0
            r25 = r1
            r21 = r2
            r0 = r9
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x00ee }
            r5 = 0
        L_0x00cc:
            if (r5 >= r4) goto L_0x00d4
            r3.lock()
            int r5 = r5 + 1
            goto L_0x00cc
        L_0x00d4:
            r6.unlock()
            return r0
        L_0x00d9:
            r20 = r0
            r25 = r1
            r21 = r2
            r0 = 0
            java.lang.String r1 = "Debug probes are not installed"
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00ee }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ee }
            r0.<init>(r1)     // Catch:{ all -> 0x00ee }
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x00ee }
            throw r0     // Catch:{ all -> 0x00ee }
        L_0x00ee:
            r0 = move-exception
            goto L_0x00f5
        L_0x00f0:
            r0 = move-exception
            r25 = r1
            r21 = r2
        L_0x00f5:
            r5 = 0
        L_0x00f6:
            if (r5 >= r4) goto L_0x00fe
            r3.lock()
            int r5 = r5 + 1
            goto L_0x00f6
        L_0x00fe:
            r6.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.debug.internal.DebugProbesImpl.dumpDebuggerInfo():java.util.List");
    }

    public final void dumpCoroutines(PrintStream out) {
        synchronized (out) {
            INSTANCE.dumpCoroutinesSynchronized(out);
            Unit unit = Unit.INSTANCE;
        }
    }

    /* access modifiers changed from: private */
    public final boolean isFinished(CoroutineOwner<?> $this$isFinished) {
        CoroutineContext context = $this$isFinished.info.getContext();
        Job job = context == null ? null : (Job) context.get(Job.Key);
        if (job == null || !job.isCompleted()) {
            return false;
        }
        capturedCoroutinesMap.remove($this$isFinished);
        return true;
    }

    /* JADX INFO: finally extract failed */
    private final void dumpCoroutinesSynchronized(PrintStream out) {
        String state;
        PrintStream printStream = out;
        ReentrantReadWriteLock reentrantReadWriteLock = coroutineStateLock;
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i = 0; i < readHoldCount; i++) {
            readLock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        int i2 = 0;
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                printStream.print(Intrinsics.stringPlus("Coroutines dump ", dateFormat.format(Long.valueOf(System.currentTimeMillis()))));
                Sequence<CoroutineOwner> $this$forEach$iv = SequencesKt.sortedWith(SequencesKt.filter(CollectionsKt.asSequence(debugProbesImpl.getCapturedCoroutines()), DebugProbesImpl$dumpCoroutinesSynchronized$1$2.INSTANCE), new C0750x32573d2c());
                for (CoroutineOwner owner : $this$forEach$iv) {
                    DebugCoroutineInfoImpl info = owner.info;
                    List observedStackTrace = info.lastObservedStackTrace();
                    DebugProbesImpl debugProbesImpl2 = INSTANCE;
                    List enhancedStackTrace = debugProbesImpl2.enhanceStackTraceWithThreadDumpImpl(info.getState(), info.lastObservedThread, observedStackTrace);
                    int i3 = i2;
                    if (!Intrinsics.areEqual((Object) info.getState(), (Object) DebugCoroutineInfoImplKt.RUNNING) || enhancedStackTrace != observedStackTrace) {
                        state = info.getState();
                    } else {
                        state = Intrinsics.stringPlus(info.getState(), " (Last suspension stacktrace, not an actual stacktrace)");
                    }
                    Sequence $this$forEach$iv2 = $this$forEach$iv;
                    printStream.print("\n\nCoroutine " + owner.delegate + ", state: " + state);
                    if (observedStackTrace.isEmpty()) {
                        printStream.print(Intrinsics.stringPlus("\n\tat ", StackTraceRecoveryKt.artificialFrame(ARTIFICIAL_FRAME_MESSAGE)));
                        debugProbesImpl2.printStackTrace(printStream, info.getCreationStackTrace());
                    } else {
                        debugProbesImpl2.printStackTrace(printStream, enhancedStackTrace);
                    }
                    i2 = i3;
                    $this$forEach$iv = $this$forEach$iv2;
                }
                int i4 = i2;
                Sequence sequence = $this$forEach$iv;
                Unit unit = Unit.INSTANCE;
                for (int i5 = 0; i5 < readHoldCount; i5++) {
                    readLock.lock();
                }
                writeLock.unlock();
                return;
            }
            throw new IllegalStateException("Debug probes are not installed".toString());
        } catch (Throwable th) {
            for (int i6 = 0; i6 < readHoldCount; i6++) {
                readLock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }

    private final void printStackTrace(PrintStream out, List<StackTraceElement> frames) {
        for (StackTraceElement frame : frames) {
            out.print(Intrinsics.stringPlus("\n\tat ", frame));
        }
    }

    public final List<StackTraceElement> enhanceStackTraceWithThreadDump(DebugCoroutineInfo info, List<StackTraceElement> coroutineTrace) {
        return enhanceStackTraceWithThreadDumpImpl(info.getState(), info.getLastObservedThread(), coroutineTrace);
    }

    private final List<StackTraceElement> enhanceStackTraceWithThreadDumpImpl(String state, Thread thread, List<StackTraceElement> coroutineTrace) {
        Object obj;
        if (!Intrinsics.areEqual((Object) state, (Object) DebugCoroutineInfoImplKt.RUNNING) || thread == null) {
            return coroutineTrace;
        }
        try {
            Result.Companion companion = Result.Companion;
            DebugProbesImpl debugProbesImpl = this;
            obj = Result.m64constructorimpl(thread.getStackTrace());
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m64constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m70isFailureimpl(obj)) {
            obj = null;
        }
        StackTraceElement[] actualTrace = (StackTraceElement[]) obj;
        if (actualTrace == null) {
            return coroutineTrace;
        }
        StackTraceElement[] stackTraceElementArr = actualTrace;
        int length = stackTraceElementArr.length;
        int i = 0;
        int index$iv = 0;
        while (true) {
            if (index$iv >= length) {
                index$iv = -1;
                break;
            }
            StackTraceElement it = stackTraceElementArr[index$iv];
            if (Intrinsics.areEqual((Object) it.getClassName(), (Object) "kotlin.coroutines.jvm.internal.BaseContinuationImpl") && Intrinsics.areEqual((Object) it.getMethodName(), (Object) "resumeWith") && Intrinsics.areEqual((Object) it.getFileName(), (Object) "ContinuationImpl.kt")) {
                break;
            }
            index$iv++;
        }
        int indexOfResumeWith = index$iv;
        Pair<Integer, Integer> findContinuationStartIndex = findContinuationStartIndex(indexOfResumeWith, actualTrace, coroutineTrace);
        int continuationStartFrame = findContinuationStartIndex.component1().intValue();
        int delta = findContinuationStartIndex.component2().intValue();
        if (continuationStartFrame == -1) {
            return coroutineTrace;
        }
        ArrayList result = new ArrayList((((coroutineTrace.size() + indexOfResumeWith) - continuationStartFrame) - 1) - delta);
        int i2 = indexOfResumeWith - delta;
        if (i2 > 0) {
            do {
                int index = i;
                i++;
                result.add(actualTrace[index]);
            } while (i < i2);
        }
        int i3 = continuationStartFrame + 1;
        int size = coroutineTrace.size();
        if (i3 < size) {
            do {
                int index2 = i3;
                i3++;
                result.add(coroutineTrace.get(index2));
            } while (i3 < size);
        }
        return result;
    }

    private final Pair<Integer, Integer> findContinuationStartIndex(int indexOfResumeWith, StackTraceElement[] actualTrace, List<StackTraceElement> coroutineTrace) {
        for (int i = 0; i < 3; i++) {
            int it = i;
            int result = INSTANCE.findIndexOfFrame((indexOfResumeWith - 1) - it, actualTrace, coroutineTrace);
            if (result != -1) {
                return TuplesKt.m27to(Integer.valueOf(result), Integer.valueOf(it));
            }
        }
        return TuplesKt.m27to(-1, 0);
    }

    private final int findIndexOfFrame(int frameIndex, StackTraceElement[] actualTrace, List<StackTraceElement> coroutineTrace) {
        StackTraceElement continuationFrame = (StackTraceElement) ArraysKt.getOrNull((T[]) actualTrace, frameIndex);
        if (continuationFrame == null) {
            return -1;
        }
        int index$iv = 0;
        for (StackTraceElement it : coroutineTrace) {
            if (Intrinsics.areEqual((Object) it.getFileName(), (Object) continuationFrame.getFileName()) && Intrinsics.areEqual((Object) it.getClassName(), (Object) continuationFrame.getClassName()) && Intrinsics.areEqual((Object) it.getMethodName(), (Object) continuationFrame.getMethodName())) {
                return index$iv;
            }
            index$iv++;
        }
        return -1;
    }

    public final void probeCoroutineResumed$kotlinx_coroutines_core(Continuation<?> frame) {
        updateState(frame, DebugCoroutineInfoImplKt.RUNNING);
    }

    public final void probeCoroutineSuspended$kotlinx_coroutines_core(Continuation<?> frame) {
        updateState(frame, DebugCoroutineInfoImplKt.SUSPENDED);
    }

    private final void updateState(Continuation<?> frame, String state) {
        if (isInstalled$kotlinx_coroutines_core()) {
            if (!Intrinsics.areEqual((Object) state, (Object) DebugCoroutineInfoImplKt.RUNNING) || !KotlinVersion.CURRENT.isAtLeast(1, 3, 30)) {
                CoroutineOwner owner = owner(frame);
                if (owner != null) {
                    updateState(owner, frame, state);
                    return;
                }
                return;
            }
            CoroutineStackFrame stackFrame = frame instanceof CoroutineStackFrame ? (CoroutineStackFrame) frame : null;
            if (stackFrame != null) {
                updateRunningState(stackFrame, state);
            }
        }
    }

    private final void updateRunningState(CoroutineStackFrame frame, String state) {
        DebugCoroutineInfoImpl info;
        ReentrantReadWriteLock.ReadLock readLock = coroutineStateLock.readLock();
        readLock.lock();
        try {
            DebugProbesImpl debugProbesImpl = INSTANCE;
            if (debugProbesImpl.isInstalled$kotlinx_coroutines_core()) {
                ConcurrentWeakMap<CoroutineStackFrame, DebugCoroutineInfoImpl> concurrentWeakMap = callerInfoCache;
                DebugCoroutineInfoImpl cached = concurrentWeakMap.remove(frame);
                if (cached != null) {
                    info = cached;
                } else {
                    CoroutineOwner<?> owner = debugProbesImpl.owner(frame);
                    if (owner == null) {
                        readLock.unlock();
                        return;
                    }
                    info = owner.info;
                    CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = info.getLastObservedFrame$kotlinx_coroutines_core();
                    CoroutineStackFrame realCaller = lastObservedFrame$kotlinx_coroutines_core == null ? null : debugProbesImpl.realCaller(lastObservedFrame$kotlinx_coroutines_core);
                    if (realCaller != null) {
                        concurrentWeakMap.remove(realCaller);
                    }
                }
                info.updateState$kotlinx_coroutines_core(state, (Continuation) frame);
                CoroutineStackFrame caller = debugProbesImpl.realCaller(frame);
                if (caller == null) {
                    readLock.unlock();
                    return;
                }
                concurrentWeakMap.put(caller, info);
                Unit unit = Unit.INSTANCE;
                readLock.unlock();
            }
        } finally {
            readLock.unlock();
        }
    }

    private final CoroutineStackFrame realCaller(CoroutineStackFrame $this$realCaller) {
        CoroutineStackFrame caller = $this$realCaller;
        do {
            caller = caller.getCallerFrame();
            if (caller == null) {
                return null;
            }
        } while (caller.getStackTraceElement() == null);
        return caller;
    }

    private final void updateState(CoroutineOwner<?> owner, Continuation<?> frame, String state) {
        ReentrantReadWriteLock.ReadLock readLock = coroutineStateLock.readLock();
        readLock.lock();
        try {
            if (INSTANCE.isInstalled$kotlinx_coroutines_core()) {
                owner.info.updateState$kotlinx_coroutines_core(state, frame);
                Unit unit = Unit.INSTANCE;
                readLock.unlock();
            }
        } finally {
            readLock.unlock();
        }
    }

    private final CoroutineOwner<?> owner(Continuation<?> $this$owner) {
        CoroutineStackFrame coroutineStackFrame = $this$owner instanceof CoroutineStackFrame ? (CoroutineStackFrame) $this$owner : null;
        if (coroutineStackFrame == null) {
            return null;
        }
        return owner(coroutineStackFrame);
    }

    private final CoroutineOwner<?> owner(CoroutineStackFrame $this$owner) {
        CoroutineStackFrame coroutineStackFrame = $this$owner;
        while (!(coroutineStackFrame instanceof CoroutineOwner)) {
            coroutineStackFrame = coroutineStackFrame.getCallerFrame();
            if (coroutineStackFrame == null) {
                return null;
            }
        }
        return (CoroutineOwner) coroutineStackFrame;
    }

    public final <T> Continuation<T> probeCoroutineCreated$kotlinx_coroutines_core(Continuation<? super T> completion) {
        StackTraceFrame frame;
        if (!isInstalled$kotlinx_coroutines_core() || owner((Continuation<?>) completion) != null) {
            return completion;
        }
        if (enableCreationStackTraces) {
            frame = toStackTraceFrame(sanitizeStackTrace(new Exception()));
        } else {
            frame = null;
        }
        return createOwner(completion, frame);
    }

    private final StackTraceFrame toStackTraceFrame(List<StackTraceElement> $this$toStackTraceFrame) {
        List $this$foldRight$iv = $this$toStackTraceFrame;
        StackTraceFrame acc = null;
        if (!$this$foldRight$iv.isEmpty()) {
            ListIterator iterator$iv = $this$foldRight$iv.listIterator($this$foldRight$iv.size());
            while (iterator$iv.hasPrevious()) {
                acc = new StackTraceFrame(acc, iterator$iv.previous());
            }
        }
        return acc;
    }

    private final <T> Continuation<T> createOwner(Continuation<? super T> completion, StackTraceFrame frame) {
        if (!isInstalled$kotlinx_coroutines_core()) {
            return completion;
        }
        CoroutineOwner owner = new CoroutineOwner(completion, new DebugCoroutineInfoImpl(completion.getContext(), frame, sequenceNumber$FU.incrementAndGet(debugProbesImpl$SequenceNumberRefVolatile)), frame);
        ConcurrentWeakMap<CoroutineOwner<?>, Boolean> concurrentWeakMap = capturedCoroutinesMap;
        concurrentWeakMap.put(owner, true);
        if (!isInstalled$kotlinx_coroutines_core()) {
            concurrentWeakMap.clear();
        }
        return owner;
    }

    /* access modifiers changed from: private */
    public final void probeCoroutineCompleted(CoroutineOwner<?> owner) {
        capturedCoroutinesMap.remove(owner);
        CoroutineStackFrame lastObservedFrame$kotlinx_coroutines_core = owner.info.getLastObservedFrame$kotlinx_coroutines_core();
        CoroutineStackFrame caller = lastObservedFrame$kotlinx_coroutines_core == null ? null : realCaller(lastObservedFrame$kotlinx_coroutines_core);
        if (caller != null) {
            callerInfoCache.remove(caller);
        }
    }

    @Metadata(mo11814d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B%\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u001e\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX\u0005¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, mo11815d2 = {"Lkotlinx/coroutines/debug/internal/DebugProbesImpl$CoroutineOwner;", "T", "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "delegate", "info", "Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;", "frame", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/debug/internal/DebugCoroutineInfoImpl;Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;)V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "resumeWith", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
    /* compiled from: DebugProbesImpl.kt */
    private static final class CoroutineOwner<T> implements Continuation<T>, CoroutineStackFrame {
        public final Continuation<T> delegate;
        private final CoroutineStackFrame frame;
        public final DebugCoroutineInfoImpl info;

        public CoroutineContext getContext() {
            return this.delegate.getContext();
        }

        public CoroutineOwner(Continuation<? super T> delegate2, DebugCoroutineInfoImpl info2, CoroutineStackFrame frame2) {
            this.delegate = delegate2;
            this.info = info2;
            this.frame = frame2;
        }

        public CoroutineStackFrame getCallerFrame() {
            CoroutineStackFrame coroutineStackFrame = this.frame;
            if (coroutineStackFrame == null) {
                return null;
            }
            return coroutineStackFrame.getCallerFrame();
        }

        public StackTraceElement getStackTraceElement() {
            CoroutineStackFrame coroutineStackFrame = this.frame;
            if (coroutineStackFrame == null) {
                return null;
            }
            return coroutineStackFrame.getStackTraceElement();
        }

        public void resumeWith(Object result) {
            DebugProbesImpl.INSTANCE.probeCoroutineCompleted(this);
            this.delegate.resumeWith(result);
        }

        public String toString() {
            return this.delegate.toString();
        }
    }

    private final <T extends Throwable> List<StackTraceElement> sanitizeStackTrace(T throwable) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        StackTraceElement[] stackTraceElementArr = stackTrace;
        int i = -1;
        int index$iv = stackTraceElementArr.length - 1;
        while (true) {
            if (index$iv < 0) {
                break;
            } else if (Intrinsics.areEqual((Object) stackTraceElementArr[index$iv].getClassName(), (Object) "kotlin.coroutines.jvm.internal.DebugProbesKt")) {
                i = index$iv;
                break;
            } else {
                index$iv--;
            }
        }
        int probeIndex = i;
        if (!sanitizeStackTraces) {
            int i2 = size - probeIndex;
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                int it = i3;
                arrayList.add(it == 0 ? StackTraceRecoveryKt.artificialFrame(ARTIFICIAL_FRAME_MESSAGE) : stackTrace[it + probeIndex]);
            }
            return arrayList;
        }
        ArrayList result = new ArrayList((size - probeIndex) + 1);
        result.add(StackTraceRecoveryKt.artificialFrame(ARTIFICIAL_FRAME_MESSAGE));
        int i4 = probeIndex + 1;
        while (i4 < size) {
            if (isInternalMethod(stackTrace[i4])) {
                result.add(stackTrace[i4]);
                int j = i4 + 1;
                while (j < size && isInternalMethod(stackTrace[j])) {
                    j++;
                }
                int k = j - 1;
                while (k > i4 && stackTrace[k].getFileName() == null) {
                    k--;
                }
                if (k > i4 && k < j - 1) {
                    result.add(stackTrace[k]);
                }
                result.add(stackTrace[j - 1]);
                i4 = j;
            } else {
                result.add(stackTrace[i4]);
                i4++;
            }
        }
        return result;
    }

    private final boolean isInternalMethod(StackTraceElement $this$isInternalMethod) {
        return StringsKt.startsWith$default($this$isInternalMethod.getClassName(), "kotlinx.coroutines", false, 2, (Object) null);
    }
}
