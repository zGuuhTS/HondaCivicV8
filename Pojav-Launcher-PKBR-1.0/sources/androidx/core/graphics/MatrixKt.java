package androidx.core.graphics;

import android.graphics.Matrix;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0014\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u001a\u001a\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u0003\u001a\u001a\u0010\t\u001a\u00020\u00012\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u001a\u0015\u0010\f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\r\u001a\u00020\u0001H\n\u001a\r\u0010\u000e\u001a\u00020\u000f*\u00020\u0001H\b¨\u0006\u0010"}, mo11815d2 = {"rotationMatrix", "Landroid/graphics/Matrix;", "degrees", "", "px", "py", "scaleMatrix", "sx", "sy", "translationMatrix", "tx", "ty", "times", "m", "values", "", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: Matrix.kt */
public final class MatrixKt {
    public static final Matrix times(Matrix $this$times, Matrix m) {
        Intrinsics.checkParameterIsNotNull($this$times, "$this$times");
        Intrinsics.checkParameterIsNotNull(m, "m");
        Matrix $this$apply = new Matrix($this$times);
        $this$apply.preConcat(m);
        return $this$apply;
    }

    public static final float[] values(Matrix $this$values) {
        Intrinsics.checkParameterIsNotNull($this$values, "$this$values");
        float[] $this$apply = new float[9];
        $this$values.getValues($this$apply);
        return $this$apply;
    }

    public static final Matrix translationMatrix(float tx, float ty) {
        Matrix $this$apply = new Matrix();
        $this$apply.setTranslate(tx, ty);
        return $this$apply;
    }

    public static /* synthetic */ Matrix translationMatrix$default(float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 0.0f;
        }
        if ((i & 2) != 0) {
            f2 = 0.0f;
        }
        return translationMatrix(f, f2);
    }

    public static final Matrix scaleMatrix(float sx, float sy) {
        Matrix $this$apply = new Matrix();
        $this$apply.setScale(sx, sy);
        return $this$apply;
    }

    public static /* synthetic */ Matrix scaleMatrix$default(float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 1.0f;
        }
        if ((i & 2) != 0) {
            f2 = 1.0f;
        }
        return scaleMatrix(f, f2);
    }

    public static /* synthetic */ Matrix rotationMatrix$default(float f, float f2, float f3, int i, Object obj) {
        if ((i & 2) != 0) {
            f2 = 0.0f;
        }
        if ((i & 4) != 0) {
            f3 = 0.0f;
        }
        return rotationMatrix(f, f2, f3);
    }

    public static final Matrix rotationMatrix(float degrees, float px, float py) {
        Matrix $this$apply = new Matrix();
        $this$apply.setRotate(degrees, px, py);
        return $this$apply;
    }
}
