package androidx.core.animation;

import android.animation.Animator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000(\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u001a¡\u0001\u0010\u0000\u001a\u00020\u0001*\u00020\u00022#\b\u0006\u0010\u0003\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u00042#\b\u0006\u0010\t\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u00042#\b\u0006\u0010\n\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u00042#\b\u0006\u0010\u000b\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b\u001aW\u0010\f\u001a\u00020\r*\u00020\u00022#\b\u0006\u0010\u000e\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u00042#\b\u0006\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b\u001a2\u0010\u0010\u001a\u00020\u0001*\u00020\u00022#\b\u0004\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b\u001a2\u0010\u0012\u001a\u00020\u0001*\u00020\u00022#\b\u0004\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b\u001a2\u0010\u0013\u001a\u00020\r*\u00020\u00022#\b\u0004\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b\u001a2\u0010\u0014\u001a\u00020\u0001*\u00020\u00022#\b\u0004\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b\u001a2\u0010\u0015\u001a\u00020\r*\u00020\u00022#\b\u0004\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b\u001a2\u0010\u0016\u001a\u00020\u0001*\u00020\u00022#\b\u0004\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\b0\u0004H\b¨\u0006\u0017"}, mo11815d2 = {"addListener", "Landroid/animation/Animator$AnimatorListener;", "Landroid/animation/Animator;", "onEnd", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "animator", "", "onStart", "onCancel", "onRepeat", "addPauseListener", "Landroid/animation/Animator$AnimatorPauseListener;", "onResume", "onPause", "doOnCancel", "action", "doOnEnd", "doOnPause", "doOnRepeat", "doOnResume", "doOnStart", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: Animator.kt */
public final class AnimatorKt {
    public static final Animator.AnimatorListener doOnEnd(Animator $this$doOnEnd, Function1<? super Animator, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnEnd, "$this$doOnEnd");
        Intrinsics.checkParameterIsNotNull(action, "action");
        AnimatorKt$doOnEnd$$inlined$addListener$1 listener$iv = new AnimatorKt$doOnEnd$$inlined$addListener$1(action);
        $this$doOnEnd.addListener(listener$iv);
        return listener$iv;
    }

    public static final Animator.AnimatorListener doOnStart(Animator $this$doOnStart, Function1<? super Animator, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnStart, "$this$doOnStart");
        Intrinsics.checkParameterIsNotNull(action, "action");
        AnimatorKt$doOnStart$$inlined$addListener$1 listener$iv = new AnimatorKt$doOnStart$$inlined$addListener$1(action);
        $this$doOnStart.addListener(listener$iv);
        return listener$iv;
    }

    public static final Animator.AnimatorListener doOnCancel(Animator $this$doOnCancel, Function1<? super Animator, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnCancel, "$this$doOnCancel");
        Intrinsics.checkParameterIsNotNull(action, "action");
        AnimatorKt$doOnCancel$$inlined$addListener$1 listener$iv = new AnimatorKt$doOnCancel$$inlined$addListener$1(action);
        $this$doOnCancel.addListener(listener$iv);
        return listener$iv;
    }

    public static final Animator.AnimatorListener doOnRepeat(Animator $this$doOnRepeat, Function1<? super Animator, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnRepeat, "$this$doOnRepeat");
        Intrinsics.checkParameterIsNotNull(action, "action");
        AnimatorKt$doOnRepeat$$inlined$addListener$1 listener$iv = new AnimatorKt$doOnRepeat$$inlined$addListener$1(action);
        $this$doOnRepeat.addListener(listener$iv);
        return listener$iv;
    }

    public static final Animator.AnimatorPauseListener doOnResume(Animator $this$doOnResume, Function1<? super Animator, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnResume, "$this$doOnResume");
        Intrinsics.checkParameterIsNotNull(action, "action");
        AnimatorKt$doOnResume$$inlined$addPauseListener$1 listener$iv = new AnimatorKt$doOnResume$$inlined$addPauseListener$1(action);
        $this$doOnResume.addPauseListener(listener$iv);
        return listener$iv;
    }

    public static final Animator.AnimatorPauseListener doOnPause(Animator $this$doOnPause, Function1<? super Animator, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnPause, "$this$doOnPause");
        Intrinsics.checkParameterIsNotNull(action, "action");
        AnimatorKt$doOnPause$$inlined$addPauseListener$1 listener$iv = new AnimatorKt$doOnPause$$inlined$addPauseListener$1(action);
        $this$doOnPause.addPauseListener(listener$iv);
        return listener$iv;
    }

    public static /* synthetic */ Animator.AnimatorListener addListener$default(Animator $this$addListener, Function1 onEnd, Function1 onStart, Function1 onCancel, Function1 onRepeat, int i, Object obj) {
        if ((i & 1) != 0) {
            onEnd = AnimatorKt$addListener$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            onStart = AnimatorKt$addListener$2.INSTANCE;
        }
        if ((i & 4) != 0) {
            onCancel = AnimatorKt$addListener$3.INSTANCE;
        }
        if ((i & 8) != 0) {
            onRepeat = AnimatorKt$addListener$4.INSTANCE;
        }
        Intrinsics.checkParameterIsNotNull($this$addListener, "$this$addListener");
        Intrinsics.checkParameterIsNotNull(onEnd, "onEnd");
        Intrinsics.checkParameterIsNotNull(onStart, "onStart");
        Intrinsics.checkParameterIsNotNull(onCancel, "onCancel");
        Intrinsics.checkParameterIsNotNull(onRepeat, "onRepeat");
        AnimatorKt$addListener$listener$1 listener = new AnimatorKt$addListener$listener$1(onRepeat, onEnd, onCancel, onStart);
        $this$addListener.addListener(listener);
        return listener;
    }

    public static final Animator.AnimatorListener addListener(Animator $this$addListener, Function1<? super Animator, Unit> onEnd, Function1<? super Animator, Unit> onStart, Function1<? super Animator, Unit> onCancel, Function1<? super Animator, Unit> onRepeat) {
        Intrinsics.checkParameterIsNotNull($this$addListener, "$this$addListener");
        Intrinsics.checkParameterIsNotNull(onEnd, "onEnd");
        Intrinsics.checkParameterIsNotNull(onStart, "onStart");
        Intrinsics.checkParameterIsNotNull(onCancel, "onCancel");
        Intrinsics.checkParameterIsNotNull(onRepeat, "onRepeat");
        AnimatorKt$addListener$listener$1 listener = new AnimatorKt$addListener$listener$1(onRepeat, onEnd, onCancel, onStart);
        $this$addListener.addListener(listener);
        return listener;
    }

    public static /* synthetic */ Animator.AnimatorPauseListener addPauseListener$default(Animator $this$addPauseListener, Function1 onResume, Function1 onPause, int i, Object obj) {
        if ((i & 1) != 0) {
            onResume = AnimatorKt$addPauseListener$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            onPause = AnimatorKt$addPauseListener$2.INSTANCE;
        }
        Intrinsics.checkParameterIsNotNull($this$addPauseListener, "$this$addPauseListener");
        Intrinsics.checkParameterIsNotNull(onResume, "onResume");
        Intrinsics.checkParameterIsNotNull(onPause, "onPause");
        AnimatorKt$addPauseListener$listener$1 listener = new AnimatorKt$addPauseListener$listener$1(onPause, onResume);
        $this$addPauseListener.addPauseListener(listener);
        return listener;
    }

    public static final Animator.AnimatorPauseListener addPauseListener(Animator $this$addPauseListener, Function1<? super Animator, Unit> onResume, Function1<? super Animator, Unit> onPause) {
        Intrinsics.checkParameterIsNotNull($this$addPauseListener, "$this$addPauseListener");
        Intrinsics.checkParameterIsNotNull(onResume, "onResume");
        Intrinsics.checkParameterIsNotNull(onPause, "onPause");
        AnimatorKt$addPauseListener$listener$1 listener = new AnimatorKt$addPauseListener$listener$1(onPause, onResume);
        $this$addPauseListener.addPauseListener(listener);
        return listener;
    }
}
