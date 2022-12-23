package androidx.core.location;

import android.location.Location;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0002H\n¨\u0006\u0004"}, mo11815d2 = {"component1", "", "Landroid/location/Location;", "component2", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: Location.kt */
public final class LocationKt {
    public static final double component1(Location $this$component1) {
        Intrinsics.checkParameterIsNotNull($this$component1, "$this$component1");
        return $this$component1.getLatitude();
    }

    public static final double component2(Location $this$component2) {
        Intrinsics.checkParameterIsNotNull($this$component2, "$this$component2");
        return $this$component2.getLongitude();
    }
}
