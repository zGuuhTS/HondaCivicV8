package androidx.window.layout;

import android.app.Activity;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import androidx.window.layout.ExtensionInterfaceCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11814d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0003H\u0016¨\u0006\u0007"}, mo11815d2 = {"androidx/window/layout/SidecarCompat$registerConfigurationChangeListener$configChangeObserver$1", "Landroid/content/ComponentCallbacks;", "onConfigurationChanged", "", "newConfig", "Landroid/content/res/Configuration;", "onLowMemory", "window_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
/* renamed from: androidx.window.layout.SidecarCompat$registerConfigurationChangeListener$configChangeObserver$1 */
/* compiled from: SidecarCompat.kt */
public final class C0650xe5f1d4c7 implements ComponentCallbacks {
    final /* synthetic */ Activity $activity;
    final /* synthetic */ SidecarCompat this$0;

    C0650xe5f1d4c7(SidecarCompat $receiver, Activity $activity2) {
        this.this$0 = $receiver;
        this.$activity = $activity2;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        ExtensionInterfaceCompat.ExtensionCallbackInterface access$getExtensionCallback$p = this.this$0.extensionCallback;
        if (access$getExtensionCallback$p != null) {
            Activity activity = this.$activity;
            access$getExtensionCallback$p.onWindowLayoutChanged(activity, this.this$0.getWindowLayoutInfo(activity));
        }
    }

    public void onLowMemory() {
    }
}
