package androidx.core.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u001a2\u0010\u0019\u001a\u00020\u001a*\u00020\u00032#\b\u0004\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u001a0\u001cH\b\u001a2\u0010 \u001a\u00020\u001a*\u00020\u00032#\b\u0004\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u001a0\u001cH\b\u001a2\u0010!\u001a\u00020\"*\u00020\u00032#\b\u0004\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\u0004\u0012\u00020\u001a0\u001cH\b\u001a\u0014\u0010#\u001a\u00020$*\u00020\u00032\b\b\u0002\u0010%\u001a\u00020&\u001a%\u0010'\u001a\u00020(*\u00020\u00032\u0006\u0010)\u001a\u00020*2\u000e\b\u0004\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0+H\b\u001a%\u0010,\u001a\u00020(*\u00020\u00032\u0006\u0010)\u001a\u00020*2\u000e\b\u0004\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001a0+H\b\u001a\u0017\u0010-\u001a\u00020\u001a*\u00020\u00032\b\b\u0001\u0010.\u001a\u00020\fH\b\u001a7\u0010/\u001a\u00020\u001a\"\n\b\u0000\u00100\u0018\u0001*\u000201*\u00020\u00032\u0017\u00102\u001a\u0013\u0012\u0004\u0012\u0002H0\u0012\u0004\u0012\u00020\u001a0\u001c¢\u0006\u0002\b3H\b¢\u0006\u0002\b4\u001a&\u0010/\u001a\u00020\u001a*\u00020\u00032\u0017\u00102\u001a\u0013\u0012\u0004\u0012\u000201\u0012\u0004\u0012\u00020\u001a0\u001c¢\u0006\u0002\b3H\b\u001a5\u00105\u001a\u00020\u001a*\u00020\u00032\b\b\u0003\u00106\u001a\u00020\f2\b\b\u0003\u00107\u001a\u00020\f2\b\b\u0003\u00108\u001a\u00020\f2\b\b\u0003\u00109\u001a\u00020\fH\b\u001a5\u0010:\u001a\u00020\u001a*\u00020\u00032\b\b\u0003\u0010;\u001a\u00020\f2\b\b\u0003\u00107\u001a\u00020\f2\b\b\u0003\u0010<\u001a\u00020\f2\b\b\u0003\u00109\u001a\u00020\fH\b\"*\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u000e¢\u0006\f\u001a\u0004\b\u0002\u0010\u0004\"\u0004\b\u0005\u0010\u0006\"*\u0010\u0007\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u000e¢\u0006\f\u001a\u0004\b\u0007\u0010\u0004\"\u0004\b\b\u0010\u0006\"*\u0010\t\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018Æ\u0002@Æ\u0002X\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0004\"\u0004\b\n\u0010\u0006\"\u0016\u0010\u000b\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0016\u0010\u000f\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000e\"\u0016\u0010\u0011\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000e\"\u0016\u0010\u0013\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000e\"\u0016\u0010\u0015\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u000e\"\u0016\u0010\u0017\u001a\u00020\f*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u000e¨\u0006="}, mo11815d2 = {"value", "", "isGone", "Landroid/view/View;", "(Landroid/view/View;)Z", "setGone", "(Landroid/view/View;Z)V", "isInvisible", "setInvisible", "isVisible", "setVisible", "marginBottom", "", "getMarginBottom", "(Landroid/view/View;)I", "marginEnd", "getMarginEnd", "marginLeft", "getMarginLeft", "marginRight", "getMarginRight", "marginStart", "getMarginStart", "marginTop", "getMarginTop", "doOnLayout", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "view", "doOnNextLayout", "doOnPreDraw", "Landroidx/core/view/OneShotPreDrawListener;", "drawToBitmap", "Landroid/graphics/Bitmap;", "config", "Landroid/graphics/Bitmap$Config;", "postDelayed", "Ljava/lang/Runnable;", "delayInMillis", "", "Lkotlin/Function0;", "postOnAnimationDelayed", "setPadding", "size", "updateLayoutParams", "T", "Landroid/view/ViewGroup$LayoutParams;", "block", "Lkotlin/ExtensionFunctionType;", "updateLayoutParamsTyped", "updatePadding", "left", "top", "right", "bottom", "updatePaddingRelative", "start", "end", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: View.kt */
public final class ViewKt {
    public static final void doOnNextLayout(View $this$doOnNextLayout, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnNextLayout, "$this$doOnNextLayout");
        Intrinsics.checkParameterIsNotNull(action, "action");
        $this$doOnNextLayout.addOnLayoutChangeListener(new ViewKt$doOnNextLayout$1(action));
    }

    public static final void doOnLayout(View $this$doOnLayout, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnLayout, "$this$doOnLayout");
        Intrinsics.checkParameterIsNotNull(action, "action");
        if (!ViewCompat.isLaidOut($this$doOnLayout) || $this$doOnLayout.isLayoutRequested()) {
            $this$doOnLayout.addOnLayoutChangeListener(new ViewKt$doOnLayout$$inlined$doOnNextLayout$1(action));
        } else {
            action.invoke($this$doOnLayout);
        }
    }

    public static final OneShotPreDrawListener doOnPreDraw(View $this$doOnPreDraw, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$doOnPreDraw, "$this$doOnPreDraw");
        Intrinsics.checkParameterIsNotNull(action, "action");
        OneShotPreDrawListener add = OneShotPreDrawListener.add($this$doOnPreDraw, new ViewKt$doOnPreDraw$1($this$doOnPreDraw, action));
        Intrinsics.checkExpressionValueIsNotNull(add, "OneShotPreDrawListener.add(this) { action(this) }");
        return add;
    }

    public static /* synthetic */ void updatePaddingRelative$default(View $this$updatePaddingRelative, int start, int top2, int end, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            start = $this$updatePaddingRelative.getPaddingStart();
        }
        if ((i & 2) != 0) {
            top2 = $this$updatePaddingRelative.getPaddingTop();
        }
        if ((i & 4) != 0) {
            end = $this$updatePaddingRelative.getPaddingEnd();
        }
        if ((i & 8) != 0) {
            bottom = $this$updatePaddingRelative.getPaddingBottom();
        }
        Intrinsics.checkParameterIsNotNull($this$updatePaddingRelative, "$this$updatePaddingRelative");
        $this$updatePaddingRelative.setPaddingRelative(start, top2, end, bottom);
    }

    public static final void updatePaddingRelative(View $this$updatePaddingRelative, int start, int top2, int end, int bottom) {
        Intrinsics.checkParameterIsNotNull($this$updatePaddingRelative, "$this$updatePaddingRelative");
        $this$updatePaddingRelative.setPaddingRelative(start, top2, end, bottom);
    }

    public static /* synthetic */ void updatePadding$default(View $this$updatePadding, int left, int top2, int right, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            left = $this$updatePadding.getPaddingLeft();
        }
        if ((i & 2) != 0) {
            top2 = $this$updatePadding.getPaddingTop();
        }
        if ((i & 4) != 0) {
            right = $this$updatePadding.getPaddingRight();
        }
        if ((i & 8) != 0) {
            bottom = $this$updatePadding.getPaddingBottom();
        }
        Intrinsics.checkParameterIsNotNull($this$updatePadding, "$this$updatePadding");
        $this$updatePadding.setPadding(left, top2, right, bottom);
    }

    public static final void updatePadding(View $this$updatePadding, int left, int top2, int right, int bottom) {
        Intrinsics.checkParameterIsNotNull($this$updatePadding, "$this$updatePadding");
        $this$updatePadding.setPadding(left, top2, right, bottom);
    }

    public static final void setPadding(View $this$setPadding, int size) {
        Intrinsics.checkParameterIsNotNull($this$setPadding, "$this$setPadding");
        $this$setPadding.setPadding(size, size, size, size);
    }

    public static final Runnable postDelayed(View $this$postDelayed, long delayInMillis, Function0<Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$postDelayed, "$this$postDelayed");
        Intrinsics.checkParameterIsNotNull(action, "action");
        Runnable runnable = new ViewKt$postDelayed$runnable$1(action);
        $this$postDelayed.postDelayed(runnable, delayInMillis);
        return runnable;
    }

    public static final Runnable postOnAnimationDelayed(View $this$postOnAnimationDelayed, long delayInMillis, Function0<Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$postOnAnimationDelayed, "$this$postOnAnimationDelayed");
        Intrinsics.checkParameterIsNotNull(action, "action");
        Runnable runnable = new ViewKt$postOnAnimationDelayed$runnable$1(action);
        $this$postOnAnimationDelayed.postOnAnimationDelayed(runnable, delayInMillis);
        return runnable;
    }

    public static /* synthetic */ Bitmap drawToBitmap$default(View view, Bitmap.Config config, int i, Object obj) {
        if ((i & 1) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        return drawToBitmap(view, config);
    }

    public static final Bitmap drawToBitmap(View $this$drawToBitmap, Bitmap.Config config) {
        Intrinsics.checkParameterIsNotNull($this$drawToBitmap, "$this$drawToBitmap");
        Intrinsics.checkParameterIsNotNull(config, "config");
        if (ViewCompat.isLaidOut($this$drawToBitmap)) {
            Bitmap $this$applyCanvas$iv = Bitmap.createBitmap($this$drawToBitmap.getWidth(), $this$drawToBitmap.getHeight(), config);
            Intrinsics.checkExpressionValueIsNotNull($this$applyCanvas$iv, "Bitmap.createBitmap(width, height, config)");
            Canvas $this$applyCanvas = new Canvas($this$applyCanvas$iv);
            $this$applyCanvas.translate(-((float) $this$drawToBitmap.getScrollX()), -((float) $this$drawToBitmap.getScrollY()));
            $this$drawToBitmap.draw($this$applyCanvas);
            return $this$applyCanvas$iv;
        }
        throw new IllegalStateException("View needs to be laid out before calling drawToBitmap()");
    }

    public static final boolean isVisible(View $this$isVisible) {
        Intrinsics.checkParameterIsNotNull($this$isVisible, "$this$isVisible");
        return $this$isVisible.getVisibility() == 0;
    }

    public static final void setVisible(View $this$isVisible, boolean value) {
        Intrinsics.checkParameterIsNotNull($this$isVisible, "$this$isVisible");
        $this$isVisible.setVisibility(value ? 0 : 8);
    }

    public static final boolean isInvisible(View $this$isInvisible) {
        Intrinsics.checkParameterIsNotNull($this$isInvisible, "$this$isInvisible");
        return $this$isInvisible.getVisibility() == 4;
    }

    public static final void setInvisible(View $this$isInvisible, boolean value) {
        Intrinsics.checkParameterIsNotNull($this$isInvisible, "$this$isInvisible");
        $this$isInvisible.setVisibility(value ? 4 : 0);
    }

    public static final boolean isGone(View $this$isGone) {
        Intrinsics.checkParameterIsNotNull($this$isGone, "$this$isGone");
        return $this$isGone.getVisibility() == 8;
    }

    public static final void setGone(View $this$isGone, boolean value) {
        Intrinsics.checkParameterIsNotNull($this$isGone, "$this$isGone");
        $this$isGone.setVisibility(value ? 8 : 0);
    }

    public static final void updateLayoutParams(View $this$updateLayoutParams, Function1<? super ViewGroup.LayoutParams, Unit> block) {
        Intrinsics.checkParameterIsNotNull($this$updateLayoutParams, "$this$updateLayoutParams");
        Intrinsics.checkParameterIsNotNull(block, "block");
        View $this$updateLayoutParams$iv = $this$updateLayoutParams;
        ViewGroup.LayoutParams params$iv = $this$updateLayoutParams$iv.getLayoutParams();
        if (params$iv != null) {
            block.invoke(params$iv);
            $this$updateLayoutParams$iv.setLayoutParams(params$iv);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
    }

    private static final <T extends ViewGroup.LayoutParams> void updateLayoutParamsTyped(View $this$updateLayoutParams, Function1<? super T, Unit> block) {
        ViewGroup.LayoutParams layoutParams = $this$updateLayoutParams.getLayoutParams();
        Intrinsics.reifiedOperationMarker(1, "T");
        ViewGroup.LayoutParams params = layoutParams;
        block.invoke(params);
        $this$updateLayoutParams.setLayoutParams(params);
    }

    public static final int getMarginLeft(View $this$marginLeft) {
        Intrinsics.checkParameterIsNotNull($this$marginLeft, "$this$marginLeft");
        ViewGroup.LayoutParams layoutParams = $this$marginLeft.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            layoutParams = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.leftMargin;
        }
        return 0;
    }

    public static final int getMarginTop(View $this$marginTop) {
        Intrinsics.checkParameterIsNotNull($this$marginTop, "$this$marginTop");
        ViewGroup.LayoutParams layoutParams = $this$marginTop.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            layoutParams = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.topMargin;
        }
        return 0;
    }

    public static final int getMarginRight(View $this$marginRight) {
        Intrinsics.checkParameterIsNotNull($this$marginRight, "$this$marginRight");
        ViewGroup.LayoutParams layoutParams = $this$marginRight.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            layoutParams = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.rightMargin;
        }
        return 0;
    }

    public static final int getMarginBottom(View $this$marginBottom) {
        Intrinsics.checkParameterIsNotNull($this$marginBottom, "$this$marginBottom");
        ViewGroup.LayoutParams layoutParams = $this$marginBottom.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            layoutParams = null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        if (marginLayoutParams != null) {
            return marginLayoutParams.bottomMargin;
        }
        return 0;
    }

    public static final int getMarginStart(View $this$marginStart) {
        Intrinsics.checkParameterIsNotNull($this$marginStart, "$this$marginStart");
        ViewGroup.LayoutParams lp = $this$marginStart.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) lp);
        }
        return 0;
    }

    public static final int getMarginEnd(View $this$marginEnd) {
        Intrinsics.checkParameterIsNotNull($this$marginEnd, "$this$marginEnd");
        ViewGroup.LayoutParams lp = $this$marginEnd.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams) lp);
        }
        return 0;
    }
}
