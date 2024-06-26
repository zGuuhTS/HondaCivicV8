package androidx.core.util;

import android.util.SparseLongArray;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.collections.LongIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\n\u001a\u0015\u0010\b\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0001H\b\u001a\u0015\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\b\u001aE\u0010\f\u001a\u00020\r*\u00020\u000226\u0010\u000e\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\r0\u000fH\b\u001a\u001d\u0010\u0012\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u000bH\b\u001a#\u0010\u0014\u001a\u00020\u000b*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015H\b\u001a\r\u0010\u0016\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010\u0017\u001a\u00020\u0006*\u00020\u0002H\b\u001a\f\u0010\u0018\u001a\u00020\u0019*\u00020\u0002H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002H\u0002\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002H\u0007\u001a\u001c\u0010\u001d\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\u0007\u001a\u001d\u0010\u001e\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000bH\n\u001a\f\u0010\u001f\u001a\u00020 *\u00020\u0002H\u0007\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Ç\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006!"}, mo11815d2 = {"size", "", "Landroid/util/SparseLongArray;", "getSize", "(Landroid/util/SparseLongArray;)I", "contains", "", "key", "containsKey", "containsValue", "value", "", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "getOrDefault", "defaultValue", "getOrElse", "Lkotlin/Function0;", "isEmpty", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "putAll", "remove", "set", "valueIterator", "Lkotlin/collections/LongIterator;", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: SparseLongArray.kt */
public final class SparseLongArrayKt {
    public static final int getSize(SparseLongArray $this$size) {
        Intrinsics.checkParameterIsNotNull($this$size, "$this$size");
        return $this$size.size();
    }

    public static final boolean contains(SparseLongArray $this$contains, int key) {
        Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
        return $this$contains.indexOfKey(key) >= 0;
    }

    public static final void set(SparseLongArray $this$set, int key, long value) {
        Intrinsics.checkParameterIsNotNull($this$set, "$this$set");
        $this$set.put(key, value);
    }

    public static final SparseLongArray plus(SparseLongArray $this$plus, SparseLongArray other) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseLongArray sparseLongArray = new SparseLongArray($this$plus.size() + other.size());
        putAll(sparseLongArray, $this$plus);
        putAll(sparseLongArray, other);
        return sparseLongArray;
    }

    public static final boolean containsKey(SparseLongArray $this$containsKey, int key) {
        Intrinsics.checkParameterIsNotNull($this$containsKey, "$this$containsKey");
        return $this$containsKey.indexOfKey(key) >= 0;
    }

    public static final boolean containsValue(SparseLongArray $this$containsValue, long value) {
        Intrinsics.checkParameterIsNotNull($this$containsValue, "$this$containsValue");
        return $this$containsValue.indexOfValue(value) != -1;
    }

    public static final long getOrDefault(SparseLongArray $this$getOrDefault, int key, long defaultValue) {
        Intrinsics.checkParameterIsNotNull($this$getOrDefault, "$this$getOrDefault");
        return $this$getOrDefault.get(key, defaultValue);
    }

    public static final long getOrElse(SparseLongArray $this$getOrElse, int key, Function0<Long> defaultValue) {
        Intrinsics.checkParameterIsNotNull($this$getOrElse, "$this$getOrElse");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        int it = $this$getOrElse.indexOfKey(key);
        return it != -1 ? $this$getOrElse.valueAt(it) : defaultValue.invoke().longValue();
    }

    public static final boolean isEmpty(SparseLongArray $this$isEmpty) {
        Intrinsics.checkParameterIsNotNull($this$isEmpty, "$this$isEmpty");
        return $this$isEmpty.size() == 0;
    }

    public static final boolean isNotEmpty(SparseLongArray $this$isNotEmpty) {
        Intrinsics.checkParameterIsNotNull($this$isNotEmpty, "$this$isNotEmpty");
        return $this$isNotEmpty.size() != 0;
    }

    public static final boolean remove(SparseLongArray $this$remove, int key, long value) {
        Intrinsics.checkParameterIsNotNull($this$remove, "$this$remove");
        int index = $this$remove.indexOfKey(key);
        if (index == -1 || value != $this$remove.valueAt(index)) {
            return false;
        }
        $this$remove.removeAt(index);
        return true;
    }

    public static final void putAll(SparseLongArray $this$putAll, SparseLongArray other) {
        Intrinsics.checkParameterIsNotNull($this$putAll, "$this$putAll");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseLongArray $this$forEach$iv = other;
        int size = $this$forEach$iv.size();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            $this$putAll.put($this$forEach$iv.keyAt(index$iv), $this$forEach$iv.valueAt(index$iv));
        }
    }

    public static final void forEach(SparseLongArray $this$forEach, Function2<? super Integer, ? super Long, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$forEach, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int size = $this$forEach.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($this$forEach.keyAt(index)), Long.valueOf($this$forEach.valueAt(index)));
        }
    }

    public static final IntIterator keyIterator(SparseLongArray $this$keyIterator) {
        Intrinsics.checkParameterIsNotNull($this$keyIterator, "$this$keyIterator");
        return new SparseLongArrayKt$keyIterator$1($this$keyIterator);
    }

    public static final LongIterator valueIterator(SparseLongArray $this$valueIterator) {
        Intrinsics.checkParameterIsNotNull($this$valueIterator, "$this$valueIterator");
        return new SparseLongArrayKt$valueIterator$1($this$valueIterator);
    }
}
