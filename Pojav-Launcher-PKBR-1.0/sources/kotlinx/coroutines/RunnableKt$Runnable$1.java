package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

@Metadata(mo11814d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n"}, mo11815d2 = {"<anonymous>", ""}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Runnable.kt */
public final class RunnableKt$Runnable$1 implements Runnable {
    final /* synthetic */ Function0<Unit> $block;

    public RunnableKt$Runnable$1(Function0<Unit> function0) {
        this.$block = function0;
    }

    public final void run() {
        this.$block.invoke();
    }
}
