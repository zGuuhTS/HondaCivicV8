package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0001\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\u000eH\u0007J\u0011\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, mo11815d2 = {"Landroidx/lifecycle/LifecycleController;", "", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "minState", "Landroidx/lifecycle/Lifecycle$State;", "dispatchQueue", "Landroidx/lifecycle/DispatchQueue;", "parentJob", "Lkotlinx/coroutines/Job;", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;Landroidx/lifecycle/DispatchQueue;Lkotlinx/coroutines/Job;)V", "observer", "Landroidx/lifecycle/LifecycleEventObserver;", "finish", "", "handleDestroy", "lifecycle-runtime-ktx_release"}, mo11816k = 1, mo11817mv = {1, 4, 1})
/* compiled from: LifecycleController.kt */
public final class LifecycleController {
    /* access modifiers changed from: private */
    public final DispatchQueue dispatchQueue;
    private final Lifecycle lifecycle;
    /* access modifiers changed from: private */
    public final Lifecycle.State minState;
    private final LifecycleEventObserver observer;

    public LifecycleController(Lifecycle lifecycle2, Lifecycle.State minState2, DispatchQueue dispatchQueue2, Job parentJob) {
        Intrinsics.checkNotNullParameter(lifecycle2, "lifecycle");
        Intrinsics.checkNotNullParameter(minState2, "minState");
        Intrinsics.checkNotNullParameter(dispatchQueue2, "dispatchQueue");
        Intrinsics.checkNotNullParameter(parentJob, "parentJob");
        this.lifecycle = lifecycle2;
        this.minState = minState2;
        this.dispatchQueue = dispatchQueue2;
        LifecycleEventObserver lifecycleController$observer$1 = new LifecycleController$observer$1(this, parentJob);
        this.observer = lifecycleController$observer$1;
        if (lifecycle2.getCurrentState() == Lifecycle.State.DESTROYED) {
            Job.DefaultImpls.cancel$default(parentJob, (CancellationException) null, 1, (Object) null);
            finish();
            return;
        }
        lifecycle2.addObserver(lifecycleController$observer$1);
    }

    /* access modifiers changed from: private */
    public final void handleDestroy(Job parentJob) {
        Job.DefaultImpls.cancel$default(parentJob, (CancellationException) null, 1, (Object) null);
        finish();
    }

    public final void finish() {
        this.lifecycle.removeObserver(this.observer);
        this.dispatchQueue.finish();
    }
}
