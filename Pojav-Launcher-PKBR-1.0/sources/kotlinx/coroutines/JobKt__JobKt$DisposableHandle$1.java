package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

@Metadata(mo11814d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, mo11815d2 = {"kotlinx/coroutines/JobKt__JobKt$DisposableHandle$1", "Lkotlinx/coroutines/DisposableHandle;", "dispose", "", "kotlinx-coroutines-core"}, mo11816k = 1, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: Job.kt */
public final class JobKt__JobKt$DisposableHandle$1 implements DisposableHandle {
    final /* synthetic */ Function0<Unit> $block;

    public JobKt__JobKt$DisposableHandle$1(Function0<Unit> $block2) {
        this.$block = $block2;
    }

    public void dispose() {
        this.$block.invoke();
    }
}
