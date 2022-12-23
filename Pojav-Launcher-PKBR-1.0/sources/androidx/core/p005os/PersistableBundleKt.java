package androidx.core.p005os;

import android.os.Build;
import android.os.PersistableBundle;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a=\u0010\u0000\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004H\u0007¢\u0006\u0002\u0010\u0007¨\u0006\b"}, mo11815d2 = {"persistableBundleOf", "Landroid/os/PersistableBundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/PersistableBundle;", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* renamed from: androidx.core.os.PersistableBundleKt */
/* compiled from: PersistableBundle.kt */
public final class PersistableBundleKt {
    public static final PersistableBundle persistableBundleOf(Pair<String, ? extends Object>... pairs) {
        Intrinsics.checkParameterIsNotNull(pairs, "pairs");
        PersistableBundle persistableBundle = new PersistableBundle(pairs.length);
        PersistableBundle $this$apply = persistableBundle;
        for (Pair<String, ? extends Object> pair : pairs) {
            String key = pair.component1();
            Object value = pair.component2();
            if (value == null) {
                $this$apply.putString(key, (String) null);
            } else if (value instanceof Boolean) {
                if (Build.VERSION.SDK_INT >= 22) {
                    $this$apply.putBoolean(key, ((Boolean) value).booleanValue());
                } else {
                    throw new IllegalArgumentException("Illegal value type boolean for key \"" + key + Typography.quote);
                }
            } else if (value instanceof Double) {
                $this$apply.putDouble(key, ((Number) value).doubleValue());
            } else if (value instanceof Integer) {
                $this$apply.putInt(key, ((Number) value).intValue());
            } else if (value instanceof Long) {
                $this$apply.putLong(key, ((Number) value).longValue());
            } else if (value instanceof String) {
                $this$apply.putString(key, (String) value);
            } else if (value instanceof boolean[]) {
                if (Build.VERSION.SDK_INT >= 22) {
                    $this$apply.putBooleanArray(key, (boolean[]) value);
                } else {
                    throw new IllegalArgumentException("Illegal value type boolean[] for key \"" + key + Typography.quote);
                }
            } else if (value instanceof double[]) {
                $this$apply.putDoubleArray(key, (double[]) value);
            } else if (value instanceof int[]) {
                $this$apply.putIntArray(key, (int[]) value);
            } else if (value instanceof long[]) {
                $this$apply.putLongArray(key, (long[]) value);
            } else if (value instanceof Object[]) {
                Class componentType = value.getClass().getComponentType();
                if (componentType == null) {
                    Intrinsics.throwNpe();
                }
                if (!String.class.isAssignableFrom(componentType)) {
                    throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + key + Typography.quote);
                } else if (value != null) {
                    $this$apply.putStringArray(key, (String[]) value);
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
                }
            } else {
                throw new IllegalArgumentException("Illegal value type " + value.getClass().getCanonicalName() + " for key \"" + key + Typography.quote);
            }
        }
        return persistableBundle;
    }
}
