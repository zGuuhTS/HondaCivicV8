package androidx.collection;

import java.util.Iterator;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000@\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010(\n\u0000\u001a!\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u0001H\n\u001aQ\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000326\u0010\u000b\u001a2\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\b\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\n0\fH\b\u001a.\u0010\u0010\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010\u0012\u001a4\u0010\u0013\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0014H\b¢\u0006\u0002\u0010\u0015\u001a\u0019\u0010\u0016\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\b\u001a\u0016\u0010\u0017\u001a\u00020\u0018\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a-\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0002\u001a-\u0010\u001b\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u0002H\u0002H\u0007¢\u0006\u0002\u0010\u001c\u001a.\u0010\u001d\u001a\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u0002H\u0002H\n¢\u0006\u0002\u0010\u001e\u001a\u001c\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00020 \"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\"\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006!"}, mo11815d2 = {"size", "", "T", "Landroidx/collection/SparseArrayCompat;", "getSize", "(Landroidx/collection/SparseArrayCompat;)I", "contains", "", "key", "forEach", "", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "value", "getOrDefault", "defaultValue", "(Landroidx/collection/SparseArrayCompat;ILjava/lang/Object;)Ljava/lang/Object;", "getOrElse", "Lkotlin/Function0;", "(Landroidx/collection/SparseArrayCompat;ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "keyIterator", "Lkotlin/collections/IntIterator;", "plus", "other", "remove", "(Landroidx/collection/SparseArrayCompat;ILjava/lang/Object;)Z", "set", "(Landroidx/collection/SparseArrayCompat;ILjava/lang/Object;)V", "valueIterator", "", "collection-ktx"}, mo11816k = 2, mo11817mv = {1, 1, 13})
/* compiled from: SparseArray.kt */
public final class SparseArrayKt {
    public static final <T> int getSize(SparseArrayCompat<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        return $receiver.size();
    }

    public static final <T> boolean contains(SparseArrayCompat<T> $receiver, int key) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        return $receiver.containsKey(key);
    }

    public static final <T> void set(SparseArrayCompat<T> $receiver, int key, T value) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        $receiver.put(key, value);
    }

    public static final <T> SparseArrayCompat<T> plus(SparseArrayCompat<T> $receiver, SparseArrayCompat<T> other) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Intrinsics.checkParameterIsNotNull(other, "other");
        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat($receiver.size() + other.size());
        sparseArrayCompat.putAll($receiver);
        sparseArrayCompat.putAll(other);
        return sparseArrayCompat;
    }

    public static final <T> T getOrDefault(SparseArrayCompat<T> $receiver, int key, T defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        return $receiver.get(key, defaultValue);
    }

    public static final <T> T getOrElse(SparseArrayCompat<T> $receiver, int key, Function0<? extends T> defaultValue) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
        T t = $receiver.get(key);
        return t != null ? t : defaultValue.invoke();
    }

    public static final <T> boolean isNotEmpty(SparseArrayCompat<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        return !$receiver.isEmpty();
    }

    @Deprecated(message = "Replaced with member function. Remove extension import!")
    public static final <T> boolean remove(SparseArrayCompat<T> $receiver, int key, T value) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        return $receiver.remove(key, value);
    }

    public static final <T> void forEach(SparseArrayCompat<T> $receiver, Function2<? super Integer, ? super T, Unit> action) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int size = $receiver.size();
        for (int index = 0; index < size; index++) {
            action.invoke(Integer.valueOf($receiver.keyAt(index)), $receiver.valueAt(index));
        }
    }

    public static final <T> IntIterator keyIterator(SparseArrayCompat<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        return new SparseArrayKt$keyIterator$1($receiver);
    }

    public static final <T> Iterator<T> valueIterator(SparseArrayCompat<T> $receiver) {
        Intrinsics.checkParameterIsNotNull($receiver, "receiver$0");
        return new SparseArrayKt$valueIterator$1($receiver);
    }
}
