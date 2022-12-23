package androidx.window.layout;

import android.graphics.Rect;
import androidx.window.core.Bounds;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11814d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, mo11815d2 = {"Landroidx/window/layout/WindowMetrics;", "", "bounds", "Landroid/graphics/Rect;", "(Landroid/graphics/Rect;)V", "_bounds", "Landroidx/window/core/Bounds;", "(Landroidx/window/core/Bounds;)V", "getBounds", "()Landroid/graphics/Rect;", "equals", "", "other", "hashCode", "", "toString", "", "window_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
/* compiled from: WindowMetrics.kt */
public final class WindowMetrics {
    private final Bounds _bounds;

    public WindowMetrics(Bounds _bounds2) {
        Intrinsics.checkNotNullParameter(_bounds2, "_bounds");
        this._bounds = _bounds2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public WindowMetrics(Rect bounds) {
        this(new Bounds(bounds));
        Intrinsics.checkNotNullParameter(bounds, "bounds");
    }

    public final Rect getBounds() {
        return this._bounds.toRect();
    }

    public String toString() {
        return "WindowMetrics { bounds: " + getBounds() + " }";
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || !Intrinsics.areEqual((Object) getClass(), (Object) other.getClass())) {
            return false;
        }
        return Intrinsics.areEqual((Object) this._bounds, (Object) ((WindowMetrics) other)._bounds);
    }

    public int hashCode() {
        return this._bounds.hashCode();
    }
}
