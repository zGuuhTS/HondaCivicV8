package androidx.window.layout;

import android.app.Activity;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo11814d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, mo11815d2 = {"<anonymous>", "", "invoke", "()Ljava/lang/Boolean;"}, mo11816k = 3, mo11817mv = {1, 6, 0}, mo11819xi = 48)
/* compiled from: SafeWindowLayoutComponentProvider.kt */
final class SafeWindowLayoutComponentProvider$isWindowLayoutComponentValid$1 extends Lambda implements Function0<Boolean> {
    final /* synthetic */ ClassLoader $classLoader;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SafeWindowLayoutComponentProvider$isWindowLayoutComponentValid$1(ClassLoader classLoader) {
        super(0);
        this.$classLoader = classLoader;
    }

    public final Boolean invoke() {
        Class windowLayoutComponent = SafeWindowLayoutComponentProvider.INSTANCE.windowLayoutComponentClass(this.$classLoader);
        boolean z = false;
        Method addListenerMethod = windowLayoutComponent.getMethod("addWindowLayoutInfoListener", new Class[]{Activity.class, Consumer.class});
        Method removeListenerMethod = windowLayoutComponent.getMethod("removeWindowLayoutInfoListener", new Class[]{Consumer.class});
        SafeWindowLayoutComponentProvider safeWindowLayoutComponentProvider = SafeWindowLayoutComponentProvider.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(addListenerMethod, "addListenerMethod");
        if (safeWindowLayoutComponentProvider.isPublic(addListenerMethod)) {
            SafeWindowLayoutComponentProvider safeWindowLayoutComponentProvider2 = SafeWindowLayoutComponentProvider.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(removeListenerMethod, "removeListenerMethod");
            if (safeWindowLayoutComponentProvider2.isPublic(removeListenerMethod)) {
                z = true;
            }
        }
        return Boolean.valueOf(z);
    }
}
