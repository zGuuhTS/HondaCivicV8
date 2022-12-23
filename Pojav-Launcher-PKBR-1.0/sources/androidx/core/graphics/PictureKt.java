package androidx.core.graphics;

import android.graphics.Canvas;
import android.graphics.Picture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a6\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\tH\b¨\u0006\n"}, mo11815d2 = {"record", "Landroid/graphics/Picture;", "width", "", "height", "block", "Lkotlin/Function1;", "Landroid/graphics/Canvas;", "", "Lkotlin/ExtensionFunctionType;", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: Picture.kt */
public final class PictureKt {
    public static final Picture record(Picture $this$record, int width, int height, Function1<? super Canvas, Unit> block) {
        Intrinsics.checkParameterIsNotNull($this$record, "$this$record");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Canvas c = $this$record.beginRecording(width, height);
        try {
            Intrinsics.checkExpressionValueIsNotNull(c, "c");
            block.invoke(c);
            return $this$record;
        } finally {
            InlineMarker.finallyStart(1);
            $this$record.endRecording();
            InlineMarker.finallyEnd(1);
        }
    }
}
