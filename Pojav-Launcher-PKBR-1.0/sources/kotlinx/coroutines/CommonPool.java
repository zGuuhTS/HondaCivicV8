package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

@Metadata(mo11814d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0011\u001a\u0004\u0018\u0001H\u0012\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0014H\b¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\u001c\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001d2\n\u0010\u0013\u001a\u00060\u001ej\u0002`\u001fH\u0016J\b\u0010 \u001a\u00020\u0006H\u0002J!\u0010!\u001a\u00020\u00102\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010\u0005\u001a\u00020\u0019H\u0000¢\u0006\u0002\b$J\r\u0010%\u001a\u00020\u0017H\u0000¢\u0006\u0002\b&J\u0015\u0010'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020)H\u0000¢\u0006\u0002\b*J\b\u0010+\u001a\u00020\u0004H\u0016J\r\u0010\u000f\u001a\u00020\u0017H\u0000¢\u0006\u0002\b,R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u000e¢\u0006\u0002\n\u0000¨\u0006-"}, mo11815d2 = {"Lkotlinx/coroutines/CommonPool;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "()V", "DEFAULT_PARALLELISM_PROPERTY_NAME", "", "executor", "Ljava/util/concurrent/Executor;", "getExecutor", "()Ljava/util/concurrent/Executor;", "parallelism", "", "getParallelism", "()I", "pool", "requestedParallelism", "usePrivatePool", "", "Try", "T", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "close", "", "createPlainPool", "Ljava/util/concurrent/ExecutorService;", "createPool", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "getOrCreatePoolSync", "isGoodCommonPool", "fjpClass", "Ljava/lang/Class;", "isGoodCommonPool$kotlinx_coroutines_core", "restore", "restore$kotlinx_coroutines_core", "shutdown", "timeout", "", "shutdown$kotlinx_coroutines_core", "toString", "usePrivatePool$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: CommonPool.kt */
public final class CommonPool extends ExecutorCoroutineDispatcher {
    private static final String DEFAULT_PARALLELISM_PROPERTY_NAME = "kotlinx.coroutines.default.parallelism";
    public static final CommonPool INSTANCE = new CommonPool();
    private static volatile Executor pool;
    private static final int requestedParallelism;
    private static boolean usePrivatePool;

    private CommonPool() {
    }

    public Executor getExecutor() {
        Executor executor = pool;
        return executor == null ? getOrCreatePoolSync() : executor;
    }

    static {
        String str;
        int i;
        CommonPool commonPool = INSTANCE;
        try {
            str = System.getProperty(DEFAULT_PARALLELISM_PROPERTY_NAME);
        } catch (Throwable th) {
            str = null;
        }
        if (str == null) {
            i = -1;
        } else {
            String property = str;
            Integer parallelism = StringsKt.toIntOrNull(property);
            if (parallelism == null || parallelism.intValue() < 1) {
                throw new IllegalStateException(Intrinsics.stringPlus("Expected positive number in kotlinx.coroutines.default.parallelism, but has ", property).toString());
            }
            i = parallelism.intValue();
        }
        requestedParallelism = i;
    }

    private final int getParallelism() {
        Integer valueOf = Integer.valueOf(requestedParallelism);
        if (!(valueOf.intValue() > 0)) {
            valueOf = null;
        }
        if (valueOf == null) {
            return RangesKt.coerceAtLeast(Runtime.getRuntime().availableProcessors() - 1, 1);
        }
        return valueOf.intValue();
    }

    private final <T> T Try(Function0<? extends T> block) {
        try {
            return block.invoke();
        } catch (Throwable th) {
            return null;
        }
    }

    private final ExecutorService createPool() {
        Class cls;
        ExecutorService it;
        if (System.getSecurityManager() != null) {
            return createPlainPool();
        }
        ExecutorService it2 = null;
        try {
            cls = Class.forName("java.util.concurrent.ForkJoinPool");
        } catch (Throwable th) {
            cls = null;
        }
        if (cls == null) {
            return createPlainPool();
        }
        Class fjpClass = cls;
        if (!usePrivatePool && requestedParallelism < 0) {
            try {
                Object invoke = fjpClass.getMethod("commonPool", new Class[0]).invoke((Object) null, new Object[0]);
                it = invoke instanceof ExecutorService ? (ExecutorService) invoke : null;
            } catch (Throwable th2) {
                it = null;
            }
            if (it != null) {
                if (!INSTANCE.isGoodCommonPool$kotlinx_coroutines_core(fjpClass, it)) {
                    it = null;
                }
                if (it != null) {
                    return it;
                }
            }
        }
        try {
            Object newInstance = fjpClass.getConstructor(new Class[]{Integer.TYPE}).newInstance(new Object[]{Integer.valueOf(INSTANCE.getParallelism())});
            if (newInstance instanceof ExecutorService) {
                it2 = (ExecutorService) newInstance;
            }
        } catch (Throwable th3) {
        }
        if (it2 == null) {
            return createPlainPool();
        }
        return it2;
    }

    /* access modifiers changed from: private */
    /* renamed from: isGoodCommonPool$lambda-9  reason: not valid java name */
    public static final void m1539isGoodCommonPool$lambda9() {
    }

    public final boolean isGoodCommonPool$kotlinx_coroutines_core(Class<?> fjpClass, ExecutorService executor) {
        executor.submit($$Lambda$CommonPool$36bgNy4lLwRHCWOZfm6LcwyUbo.INSTANCE);
        Integer num = null;
        try {
            Object invoke = fjpClass.getMethod("getPoolSize", new Class[0]).invoke(executor, new Object[0]);
            if (invoke instanceof Integer) {
                num = (Integer) invoke;
            }
        } catch (Throwable th) {
        }
        if (num != null && num.intValue() >= 1) {
            return true;
        }
        return false;
    }

    private final ExecutorService createPlainPool() {
        return Executors.newFixedThreadPool(getParallelism(), new ThreadFactory(new AtomicInteger()) {
            public final /* synthetic */ AtomicInteger f$0;

            {
                this.f$0 = r1;
            }

            public final Thread newThread(Runnable runnable) {
                return CommonPool.m1538createPlainPool$lambda12(this.f$0, runnable);
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: createPlainPool$lambda-12  reason: not valid java name */
    public static final Thread m1538createPlainPool$lambda12(AtomicInteger $threadId, Runnable it) {
        Thread $this$createPlainPool_u24lambda_u2d12_u24lambda_u2d11 = new Thread(it, Intrinsics.stringPlus("CommonPool-worker-", Integer.valueOf($threadId.incrementAndGet())));
        $this$createPlainPool_u24lambda_u2d12_u24lambda_u2d11.setDaemon(true);
        return $this$createPlainPool_u24lambda_u2d12_u24lambda_u2d11;
    }

    private final synchronized Executor getOrCreatePoolSync() {
        Executor executor;
        executor = pool;
        if (executor == null) {
            ExecutorService it = createPool();
            pool = it;
            executor = it;
        }
        return executor;
    }

    public void dispatch(CoroutineContext context, Runnable block) {
        try {
            Executor executor = pool;
            if (executor == null) {
                executor = getOrCreatePoolSync();
            }
            AbstractTimeSource timeSource = AbstractTimeSourceKt.getTimeSource();
            executor.execute(timeSource == null ? block : timeSource.wrapTask(block));
        } catch (RejectedExecutionException e) {
            AbstractTimeSource timeSource2 = AbstractTimeSourceKt.getTimeSource();
            if (timeSource2 != null) {
                timeSource2.unTrackTask();
            }
            DefaultExecutor.INSTANCE.enqueue(block);
        }
    }

    public final synchronized void usePrivatePool$kotlinx_coroutines_core() {
        shutdown$kotlinx_coroutines_core(0);
        usePrivatePool = true;
        pool = null;
    }

    public final synchronized void shutdown$kotlinx_coroutines_core(long timeout) {
        Executor executor = pool;
        ExecutorService $this$shutdown_u24lambda_u2d15 = executor instanceof ExecutorService ? (ExecutorService) executor : null;
        if ($this$shutdown_u24lambda_u2d15 != null) {
            $this$shutdown_u24lambda_u2d15.shutdown();
            if (timeout > 0) {
                $this$shutdown_u24lambda_u2d15.awaitTermination(timeout, TimeUnit.MILLISECONDS);
            }
            for (Runnable it : $this$shutdown_u24lambda_u2d15.shutdownNow()) {
                DefaultExecutor.INSTANCE.enqueue(it);
            }
        }
        pool = $$Lambda$CommonPool$B8tWIgKlJHpaqvQwjtIxhEc709w.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* renamed from: shutdown$lambda-16  reason: not valid java name */
    public static final void m1541shutdown$lambda16(Runnable it) {
        throw new RejectedExecutionException("CommonPool was shutdown");
    }

    public final synchronized void restore$kotlinx_coroutines_core() {
        shutdown$kotlinx_coroutines_core(0);
        usePrivatePool = false;
        pool = null;
    }

    public String toString() {
        return "CommonPool";
    }

    public void close() {
        throw new IllegalStateException("Close cannot be invoked on CommonPool".toString());
    }
}
