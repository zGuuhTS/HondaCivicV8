package androidx.core.animation;

import android.animation.Animator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0007"}, mo11815d2 = {"androidx/core/animation/AnimatorKt$addPauseListener$listener$1", "Landroid/animation/Animator$AnimatorPauseListener;", "onAnimationPause", "", "animator", "Landroid/animation/Animator;", "onAnimationResume", "core-ktx_release"}, mo11816k = 1, mo11817mv = {1, 1, 15})
/* compiled from: Animator.kt */
public final class AnimatorKt$addPauseListener$listener$1 implements Animator.AnimatorPauseListener {
    final /* synthetic */ Function1 $onPause;
    final /* synthetic */ Function1 $onResume;

    public AnimatorKt$addPauseListener$listener$1(Function1 $captured_local_variable$0, Function1 $captured_local_variable$1) {
        this.$onPause = $captured_local_variable$0;
        this.$onResume = $captured_local_variable$1;
    }

    public void onAnimationPause(Animator animator) {
        Intrinsics.checkParameterIsNotNull(animator, "animator");
        this.$onPause.invoke(animator);
    }

    public void onAnimationResume(Animator animator) {
        Intrinsics.checkParameterIsNotNull(animator, "animator");
        this.$onResume.invoke(animator);
    }
}
