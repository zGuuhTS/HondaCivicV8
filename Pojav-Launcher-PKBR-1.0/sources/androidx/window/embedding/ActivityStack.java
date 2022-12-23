package androidx.window.embedding;

import android.app.Activity;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11814d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0011\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J\u0013\u0010\f\u001a\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0006\u0010\u0005\u001a\u00020\u0006J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, mo11815d2 = {"Landroidx/window/embedding/ActivityStack;", "", "activities", "", "Landroid/app/Activity;", "isEmpty", "", "(Ljava/util/List;Z)V", "getActivities$window_release", "()Ljava/util/List;", "contains", "activity", "equals", "other", "hashCode", "", "toString", "", "window_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
/* compiled from: ActivityStack.kt */
public final class ActivityStack {
    private final List<Activity> activities;
    private final boolean isEmpty;

    public ActivityStack(List<? extends Activity> activities2, boolean isEmpty2) {
        Intrinsics.checkNotNullParameter(activities2, "activities");
        this.activities = activities2;
        this.isEmpty = isEmpty2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ActivityStack(List list, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, (i & 2) != 0 ? false : z);
    }

    public final List<Activity> getActivities$window_release() {
        return this.activities;
    }

    public final boolean contains(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        return this.activities.contains(activity);
    }

    public final boolean isEmpty() {
        return this.isEmpty;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ActivityStack)) {
            return false;
        }
        if (Intrinsics.areEqual((Object) this.activities, (Object) ((ActivityStack) other).activities) || this.isEmpty == ((ActivityStack) other).isEmpty) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        if (this.isEmpty) {
            result = 1;
        } else {
            result = 0;
        }
        return (result * 31) + this.activities.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$toString_u24lambda_u2d0 = sb;
        $this$toString_u24lambda_u2d0.append("ActivityStack{");
        $this$toString_u24lambda_u2d0.append(Intrinsics.stringPlus("activities=", getActivities$window_release()));
        $this$toString_u24lambda_u2d0.append("isEmpty=" + this.isEmpty + '}');
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
