package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Metadata;

@Metadata(mo11814d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0006\u001a\u00020\u0007H\b\u001a\t\u0010\b\u001a\u00020\u0007H\b\u001a\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0007H\b\u001a\t\u0010\u000e\u001a\u00020\nH\b\u001a\t\u0010\u000f\u001a\u00020\nH\b\u001a\t\u0010\u0010\u001a\u00020\nH\b\u001a\u0011\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\b\u001a\t\u0010\u0014\u001a\u00020\nH\b\u001a\u0019\u0010\u0015\u001a\u00060\u0016j\u0002`\u00172\n\u0010\u0018\u001a\u00060\u0016j\u0002`\u0017H\b\"\u001c\u0010\u0000\u001a\u0004\u0018\u00010\u0001X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0004\b\u0004\u0010\u0005¨\u0006\u0019"}, mo11815d2 = {"timeSource", "Lkotlinx/coroutines/AbstractTimeSource;", "getTimeSource", "()Lkotlinx/coroutines/AbstractTimeSource;", "setTimeSource", "(Lkotlinx/coroutines/AbstractTimeSource;)V", "currentTimeMillis", "", "nanoTime", "parkNanos", "", "blocker", "", "nanos", "registerTimeLoopThread", "trackTask", "unTrackTask", "unpark", "thread", "Ljava/lang/Thread;", "unregisterTimeLoopThread", "wrapTask", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "block", "kotlinx-coroutines-core"}, mo11816k = 2, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: AbstractTimeSource.kt */
public final class AbstractTimeSourceKt {
    private static AbstractTimeSource timeSource;

    public static final AbstractTimeSource getTimeSource() {
        return timeSource;
    }

    public static final void setTimeSource(AbstractTimeSource abstractTimeSource) {
        timeSource = abstractTimeSource;
    }

    private static final long currentTimeMillis() {
        AbstractTimeSource timeSource2 = getTimeSource();
        return timeSource2 == null ? System.currentTimeMillis() : timeSource2.currentTimeMillis();
    }

    private static final long nanoTime() {
        AbstractTimeSource timeSource2 = getTimeSource();
        return timeSource2 == null ? System.nanoTime() : timeSource2.nanoTime();
    }

    private static final Runnable wrapTask(Runnable block) {
        AbstractTimeSource timeSource2 = getTimeSource();
        return timeSource2 == null ? block : timeSource2.wrapTask(block);
    }

    private static final void trackTask() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.trackTask();
        }
    }

    private static final void unTrackTask() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.unTrackTask();
        }
    }

    private static final void registerTimeLoopThread() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.registerTimeLoopThread();
        }
    }

    private static final void unregisterTimeLoopThread() {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 != null) {
            timeSource2.unregisterTimeLoopThread();
        }
    }

    private static final void parkNanos(Object blocker, long nanos) {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 == null) {
            LockSupport.parkNanos(blocker, nanos);
        } else {
            timeSource2.parkNanos(blocker, nanos);
        }
    }

    private static final void unpark(Thread thread) {
        AbstractTimeSource timeSource2 = getTimeSource();
        if (timeSource2 == null) {
            LockSupport.unpark(thread);
        } else {
            timeSource2.unpark(thread);
        }
    }
}
