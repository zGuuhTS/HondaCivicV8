package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H@¢\u0006\u0004\b\u0003\u0010\u0004"}, mo11815d2 = {"<anonymous>", "T", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo11816k = 3, mo11817mv = {1, 4, 1})
@DebugMetadata(mo12529c = "androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2", mo12530f = "PausingDispatcher.kt", mo12531i = {0}, mo12532l = {162}, mo12533m = "invokeSuspend", mo12534n = {"controller"}, mo12535s = {"L$0"})
/* compiled from: PausingDispatcher.kt */
final class PausingDispatcherKt$whenStateAtLeast$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, Object> {
    final /* synthetic */ Function2 $block;
    final /* synthetic */ Lifecycle.State $minState;
    final /* synthetic */ Lifecycle $this_whenStateAtLeast;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    PausingDispatcherKt$whenStateAtLeast$2(Lifecycle lifecycle, Lifecycle.State state, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_whenStateAtLeast = lifecycle;
        this.$minState = state;
        this.$block = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkNotNullParameter(continuation, "completion");
        PausingDispatcherKt$whenStateAtLeast$2 pausingDispatcherKt$whenStateAtLeast$2 = new PausingDispatcherKt$whenStateAtLeast$2(this.$this_whenStateAtLeast, this.$minState, this.$block, continuation);
        pausingDispatcherKt$whenStateAtLeast$2.L$0 = obj;
        return pausingDispatcherKt$whenStateAtLeast$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((PausingDispatcherKt$whenStateAtLeast$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: androidx.lifecycle.LifecycleController} */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0060, code lost:
        r2.finish();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0064, code lost:
        return r10;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            switch(r1) {
                case 0: goto L_0x0021;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0011:
            r0 = r9
            r1 = 0
            java.lang.Object r2 = r0.L$0
            r1 = r2
            androidx.lifecycle.LifecycleController r1 = (androidx.lifecycle.LifecycleController) r1
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x001f }
            r2 = r1
            r1 = r0
            r0 = r10
            goto L_0x0060
        L_0x001f:
            r2 = move-exception
            goto L_0x006a
        L_0x0021:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.CoroutineScope r2 = (kotlinx.coroutines.CoroutineScope) r2
            kotlin.coroutines.CoroutineContext r3 = r2.getCoroutineContext()
            kotlinx.coroutines.Job$Key r4 = kotlinx.coroutines.Job.Key
            kotlin.coroutines.CoroutineContext$Key r4 = (kotlin.coroutines.CoroutineContext.Key) r4
            kotlin.coroutines.CoroutineContext$Element r3 = r3.get(r4)
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3
            if (r3 == 0) goto L_0x006e
            r2 = r3
            androidx.lifecycle.PausingDispatcher r3 = new androidx.lifecycle.PausingDispatcher
            r3.<init>()
            androidx.lifecycle.LifecycleController r4 = new androidx.lifecycle.LifecycleController
            androidx.lifecycle.Lifecycle r5 = r1.$this_whenStateAtLeast
            androidx.lifecycle.Lifecycle$State r6 = r1.$minState
            androidx.lifecycle.DispatchQueue r7 = r3.dispatchQueue
            r4.<init>(r5, r6, r7, r2)
            r2 = r4
            r4 = r3
            kotlin.coroutines.CoroutineContext r4 = (kotlin.coroutines.CoroutineContext) r4     // Catch:{ all -> 0x0065 }
            kotlin.jvm.functions.Function2 r5 = r1.$block     // Catch:{ all -> 0x0065 }
            r1.L$0 = r2     // Catch:{ all -> 0x0065 }
            r6 = 1
            r1.label = r6     // Catch:{ all -> 0x0065 }
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r1)     // Catch:{ all -> 0x0065 }
            if (r4 != r0) goto L_0x005e
            return r0
        L_0x005e:
            r0 = r10
            r10 = r4
        L_0x0060:
            r2.finish()
            return r10
        L_0x0065:
            r0 = move-exception
            r8 = r2
            r2 = r0
            r0 = r1
            r1 = r8
        L_0x006a:
            r1.finish()
            throw r2
        L_0x006e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "when[State] methods should have a parent job"
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.PausingDispatcherKt$whenStateAtLeast$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
