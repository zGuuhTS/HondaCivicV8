package androidx.window.layout;

import android.app.Activity;
import android.graphics.Rect;
import androidx.window.core.Bounds;
import androidx.window.extensions.layout.FoldingFeature;
import androidx.window.extensions.layout.WindowLayoutInfo;
import androidx.window.layout.FoldingFeature;
import androidx.window.layout.HardwareFoldingFeature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11814d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\tJ\u001d\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0000¢\u0006\u0002\b\tJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¨\u0006\u0011"}, mo11815d2 = {"Landroidx/window/layout/ExtensionsWindowLayoutInfoAdapter;", "", "()V", "translate", "Landroidx/window/layout/FoldingFeature;", "activity", "Landroid/app/Activity;", "oemFeature", "Landroidx/window/extensions/layout/FoldingFeature;", "translate$window_release", "Landroidx/window/layout/WindowLayoutInfo;", "info", "Landroidx/window/extensions/layout/WindowLayoutInfo;", "validBounds", "", "bounds", "Landroidx/window/core/Bounds;", "window_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
/* compiled from: ExtensionsWindowLayoutInfoAdapter.kt */
public final class ExtensionsWindowLayoutInfoAdapter {
    public static final ExtensionsWindowLayoutInfoAdapter INSTANCE = new ExtensionsWindowLayoutInfoAdapter();

    private ExtensionsWindowLayoutInfoAdapter() {
    }

    public final FoldingFeature translate$window_release(Activity activity, FoldingFeature oemFeature) {
        HardwareFoldingFeature.Type type;
        FoldingFeature.State state;
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(oemFeature, "oemFeature");
        switch (oemFeature.getType()) {
            case 1:
                type = HardwareFoldingFeature.Type.Companion.getFOLD();
                break;
            case 2:
                type = HardwareFoldingFeature.Type.Companion.getHINGE();
                break;
            default:
                return null;
        }
        switch (oemFeature.getState()) {
            case 1:
                state = FoldingFeature.State.FLAT;
                break;
            case 2:
                state = FoldingFeature.State.HALF_OPENED;
                break;
            default:
                return null;
        }
        Rect bounds = oemFeature.getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds, "oemFeature.bounds");
        if (!validBounds(activity, new Bounds(bounds))) {
            return null;
        }
        Rect bounds2 = oemFeature.getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds2, "oemFeature.bounds");
        return new HardwareFoldingFeature(new Bounds(bounds2), type, state);
    }

    public final WindowLayoutInfo translate$window_release(Activity activity, WindowLayoutInfo info) {
        Iterable $this$mapNotNull$iv;
        Object it$iv$iv;
        Activity activity2 = activity;
        Intrinsics.checkNotNullParameter(activity2, "activity");
        Intrinsics.checkNotNullParameter(info, "info");
        Iterable displayFeatures = info.getDisplayFeatures();
        Intrinsics.checkNotNullExpressionValue(displayFeatures, "info.displayFeatures");
        Iterable<androidx.window.extensions.layout.FoldingFeature> it$iv$iv2 = displayFeatures;
        Collection destination$iv$iv = new ArrayList();
        for (androidx.window.extensions.layout.FoldingFeature foldingFeature : it$iv$iv2) {
            if (foldingFeature instanceof androidx.window.extensions.layout.FoldingFeature) {
                ExtensionsWindowLayoutInfoAdapter extensionsWindowLayoutInfoAdapter = INSTANCE;
                $this$mapNotNull$iv = it$iv$iv2;
                Intrinsics.checkNotNullExpressionValue(foldingFeature, "feature");
                it$iv$iv = extensionsWindowLayoutInfoAdapter.translate$window_release(activity2, foldingFeature);
            } else {
                $this$mapNotNull$iv = it$iv$iv2;
                it$iv$iv = null;
            }
            if (it$iv$iv != null) {
                destination$iv$iv.add(it$iv$iv);
            }
            it$iv$iv2 = $this$mapNotNull$iv;
        }
        Iterable $this$mapNotNull$iv2 = it$iv$iv2;
        return new WindowLayoutInfo((List) destination$iv$iv);
    }

    private final boolean validBounds(Activity activity, Bounds bounds) {
        Rect windowBounds = WindowMetricsCalculatorCompat.INSTANCE.computeCurrentWindowMetrics(activity).getBounds();
        if (bounds.isZero()) {
            return false;
        }
        if (bounds.getWidth() != windowBounds.width() && bounds.getHeight() != windowBounds.height()) {
            return false;
        }
        if (bounds.getWidth() < windowBounds.width() && bounds.getHeight() < windowBounds.height()) {
            return false;
        }
        if (bounds.getWidth() == windowBounds.width() && bounds.getHeight() == windowBounds.height()) {
            return false;
        }
        return true;
    }
}
