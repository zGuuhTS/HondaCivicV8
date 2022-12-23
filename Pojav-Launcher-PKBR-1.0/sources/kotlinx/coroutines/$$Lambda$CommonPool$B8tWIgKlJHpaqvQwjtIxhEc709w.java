package kotlinx.coroutines;

import java.util.concurrent.Executor;

/* renamed from: kotlinx.coroutines.-$$Lambda$CommonPool$B8tWIgKlJHpaqvQwjtIxhEc709w  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$CommonPool$B8tWIgKlJHpaqvQwjtIxhEc709w implements Executor {
    public static final /* synthetic */ $$Lambda$CommonPool$B8tWIgKlJHpaqvQwjtIxhEc709w INSTANCE = new $$Lambda$CommonPool$B8tWIgKlJHpaqvQwjtIxhEc709w();

    private /* synthetic */ $$Lambda$CommonPool$B8tWIgKlJHpaqvQwjtIxhEc709w() {
    }

    public final void execute(Runnable runnable) {
        CommonPool.m1541shutdown$lambda16(runnable);
    }
}
