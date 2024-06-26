package androidx.core.util;

import android.util.AtomicFile;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\u0016\u0010\u0003\u001a\u00020\u0004*\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a0\u0010\u0007\u001a\u00020\b*\u00020\u00022!\u0010\t\u001a\u001d\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\b0\nH\b\u001a\u0014\u0010\u000f\u001a\u00020\b*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0001H\u0007\u001a\u001e\u0010\u0011\u001a\u00020\b*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0013"}, mo11815d2 = {"readBytes", "", "Landroid/util/AtomicFile;", "readText", "", "charset", "Ljava/nio/charset/Charset;", "tryWrite", "", "block", "Lkotlin/Function1;", "Ljava/io/FileOutputStream;", "Lkotlin/ParameterName;", "name", "out", "writeBytes", "array", "writeText", "text", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: AtomicFile.kt */
public final class AtomicFileKt {
    public static final void tryWrite(AtomicFile $this$tryWrite, Function1<? super FileOutputStream, Unit> block) {
        Intrinsics.checkParameterIsNotNull($this$tryWrite, "$this$tryWrite");
        Intrinsics.checkParameterIsNotNull(block, "block");
        FileOutputStream stream = $this$tryWrite.startWrite();
        try {
            Intrinsics.checkExpressionValueIsNotNull(stream, "stream");
            block.invoke(stream);
            InlineMarker.finallyStart(1);
            $this$tryWrite.finishWrite(stream);
            InlineMarker.finallyEnd(1);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            $this$tryWrite.failWrite(stream);
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public static final void writeBytes(AtomicFile $this$writeBytes, byte[] array) {
        Intrinsics.checkParameterIsNotNull($this$writeBytes, "$this$writeBytes");
        Intrinsics.checkParameterIsNotNull(array, "array");
        AtomicFile $this$tryWrite$iv = $this$writeBytes;
        FileOutputStream stream$iv = $this$tryWrite$iv.startWrite();
        try {
            Intrinsics.checkExpressionValueIsNotNull(stream$iv, "stream");
            stream$iv.write(array);
            $this$tryWrite$iv.finishWrite(stream$iv);
        } catch (Throwable th) {
            $this$tryWrite$iv.failWrite(stream$iv);
            throw th;
        }
    }

    public static /* synthetic */ void writeText$default(AtomicFile atomicFile, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        writeText(atomicFile, str, charset);
    }

    public static final void writeText(AtomicFile $this$writeText, String text, Charset charset) {
        Intrinsics.checkParameterIsNotNull($this$writeText, "$this$writeText");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        writeBytes($this$writeText, bytes);
    }

    public static final byte[] readBytes(AtomicFile $this$readBytes) {
        Intrinsics.checkParameterIsNotNull($this$readBytes, "$this$readBytes");
        byte[] readFully = $this$readBytes.readFully();
        Intrinsics.checkExpressionValueIsNotNull(readFully, "readFully()");
        return readFully;
    }

    public static /* synthetic */ String readText$default(AtomicFile atomicFile, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return readText(atomicFile, charset);
    }

    public static final String readText(AtomicFile $this$readText, Charset charset) {
        Intrinsics.checkParameterIsNotNull($this$readText, "$this$readText");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        byte[] readFully = $this$readText.readFully();
        Intrinsics.checkExpressionValueIsNotNull(readFully, "readFully()");
        return new String(readFully, charset);
    }
}
