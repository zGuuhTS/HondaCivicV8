package androidx.window.embedding;

import android.graphics.Rect;
import android.os.Build;
import android.view.WindowMetrics;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11814d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\b\u0017\u0018\u00002\u00020\u0001:\u0002\u0017\u0018B/\b\u0000\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0013\u0010\u0013\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0003H\u0016R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, mo11815d2 = {"Landroidx/window/embedding/SplitRule;", "Landroidx/window/embedding/EmbeddingRule;", "minWidth", "", "minSmallestWidth", "splitRatio", "", "layoutDirection", "(IIFI)V", "getLayoutDirection", "()I", "getMinSmallestWidth", "getMinWidth", "getSplitRatio", "()F", "checkParentMetrics", "", "parentMetrics", "Landroid/view/WindowMetrics;", "equals", "other", "", "hashCode", "Api30Impl", "LayoutDir", "window_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
/* compiled from: SplitRule.kt */
public class SplitRule extends EmbeddingRule {
    private final int layoutDirection;
    private final int minSmallestWidth;
    private final int minWidth;
    private final float splitRatio;

    @Metadata(mo11814d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, mo11815d2 = {"Landroidx/window/embedding/SplitRule$LayoutDir;", "", "window_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
    @Retention(AnnotationRetention.SOURCE)
    @java.lang.annotation.Retention(RetentionPolicy.SOURCE)
    /* compiled from: SplitRule.kt */
    public @interface LayoutDir {
    }

    public SplitRule() {
        this(0, 0, 0.0f, 0, 15, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ SplitRule(int i, int i2, float f, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0.5f : f, (i4 & 8) != 0 ? 3 : i3);
    }

    public final int getMinWidth() {
        return this.minWidth;
    }

    public final int getMinSmallestWidth() {
        return this.minSmallestWidth;
    }

    public final float getSplitRatio() {
        return this.splitRatio;
    }

    public final int getLayoutDirection() {
        return this.layoutDirection;
    }

    public SplitRule(int minWidth2, int minSmallestWidth2, float splitRatio2, int layoutDirection2) {
        this.minWidth = minWidth2;
        this.minSmallestWidth = minSmallestWidth2;
        this.splitRatio = splitRatio2;
        this.layoutDirection = layoutDirection2;
    }

    public final boolean checkParentMetrics(WindowMetrics parentMetrics) {
        Intrinsics.checkNotNullParameter(parentMetrics, "parentMetrics");
        if (Build.VERSION.SDK_INT <= 30) {
            return false;
        }
        Rect bounds = Api30Impl.INSTANCE.getBounds(parentMetrics);
        boolean validMinWidth = this.minWidth == 0 || bounds.width() >= this.minWidth;
        boolean validSmallestMinWidth = this.minSmallestWidth == 0 || Math.min(bounds.width(), bounds.height()) >= this.minSmallestWidth;
        if (!validMinWidth || !validSmallestMinWidth) {
            return false;
        }
        return true;
    }

    @Metadata(mo11814d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÁ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, mo11815d2 = {"Landroidx/window/embedding/SplitRule$Api30Impl;", "", "()V", "getBounds", "Landroid/graphics/Rect;", "windowMetrics", "Landroid/view/WindowMetrics;", "window_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
    /* compiled from: SplitRule.kt */
    public static final class Api30Impl {
        public static final Api30Impl INSTANCE = new Api30Impl();

        private Api30Impl() {
        }

        public final Rect getBounds(WindowMetrics windowMetrics) {
            Intrinsics.checkNotNullParameter(windowMetrics, "windowMetrics");
            Rect bounds = windowMetrics.getBounds();
            Intrinsics.checkNotNullExpressionValue(bounds, "windowMetrics.bounds");
            return bounds;
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SplitRule) || this.minWidth != ((SplitRule) other).minWidth || this.minSmallestWidth != ((SplitRule) other).minSmallestWidth) {
            return false;
        }
        if ((this.splitRatio == ((SplitRule) other).splitRatio) && this.layoutDirection == ((SplitRule) other).layoutDirection) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((this.minWidth * 31) + this.minSmallestWidth) * 31) + Float.hashCode(this.splitRatio)) * 31) + this.layoutDirection;
    }
}
