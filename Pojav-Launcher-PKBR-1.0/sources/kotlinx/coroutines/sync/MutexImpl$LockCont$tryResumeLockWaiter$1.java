package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.sync.MutexImpl;

@Metadata(mo11814d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, mo11815d2 = {"<anonymous>", "", "it", ""}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Mutex.kt */
final class MutexImpl$LockCont$tryResumeLockWaiter$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ MutexImpl this$0;
    final /* synthetic */ MutexImpl.LockCont this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MutexImpl$LockCont$tryResumeLockWaiter$1(MutexImpl mutexImpl, MutexImpl.LockCont lockCont) {
        super(1);
        this.this$0 = mutexImpl;
        this.this$1 = lockCont;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable it) {
        this.this$0.unlock(this.this$1.owner);
    }
}
