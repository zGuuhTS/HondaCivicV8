package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11814d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00060\u0001j\u0002`\u00022\b\u0012\u0004\u0012\u00020\u00000\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\n\u0010\u000b\u001a\u0004\u0018\u00010\u0000H\u0016J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0002J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0005H\u0016R\u0010\u0010\b\u001a\u00020\t8\u0000X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, mo11815d2 = {"Lkotlinx/coroutines/JobCancellationException;", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "Lkotlinx/coroutines/CopyableThrowable;", "message", "", "cause", "", "job", "Lkotlinx/coroutines/Job;", "(Ljava/lang/String;Ljava/lang/Throwable;Lkotlinx/coroutines/Job;)V", "createCopy", "equals", "", "other", "", "fillInStackTrace", "hashCode", "", "toString", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Exceptions.kt */
public final class JobCancellationException extends CancellationException implements CopyableThrowable<JobCancellationException> {
    public final Job job;

    public JobCancellationException(String message, Throwable cause, Job job2) {
        super(message);
        this.job = job2;
        if (cause != null) {
            initCause(cause);
        }
    }

    public Throwable fillInStackTrace() {
        if (DebugKt.getDEBUG()) {
            return super.fillInStackTrace();
        }
        setStackTrace((StackTraceElement[]) ((Object[]) new StackTraceElement[0]));
        return this;
    }

    public JobCancellationException createCopy() {
        if (!DebugKt.getDEBUG()) {
            return null;
        }
        String message = getMessage();
        Intrinsics.checkNotNull(message);
        return new JobCancellationException(message, this, this.job);
    }

    public String toString() {
        return super.toString() + "; job=" + this.job;
    }

    public boolean equals(Object other) {
        return other == this || ((other instanceof JobCancellationException) && Intrinsics.areEqual((Object) ((JobCancellationException) other).getMessage(), (Object) getMessage()) && Intrinsics.areEqual((Object) ((JobCancellationException) other).job, (Object) this.job) && Intrinsics.areEqual((Object) ((JobCancellationException) other).getCause(), (Object) getCause()));
    }

    public int hashCode() {
        String message = getMessage();
        Intrinsics.checkNotNull(message);
        int hashCode = ((message.hashCode() * 31) + this.job.hashCode()) * 31;
        Throwable cause = getCause();
        return hashCode + (cause == null ? 0 : cause.hashCode());
    }
}
