package androidx.core.util;

import android.util.Pair;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\u0014\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a2\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004H\n¢\u0006\u0002\u0010\u0005\u001a2\u0010\u0006\u001a\n \u0002*\u0004\u0018\u0001H\u0003H\u0003\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004H\n¢\u0006\u0002\u0010\u0005\u001a1\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\bH\b\u001aA\u0010\t\u001a\u001e\u0012\f\u0012\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\u0012\f\u0012\n \u0002*\u0004\u0018\u0001H\u0003H\u00030\b\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004H\b¨\u0006\n"}, mo11815d2 = {"component1", "F", "kotlin.jvm.PlatformType", "S", "Landroid/util/Pair;", "(Landroid/util/Pair;)Ljava/lang/Object;", "component2", "toAndroidPair", "Lkotlin/Pair;", "toKotlinPair", "core-ktx_release"}, mo11816k = 2, mo11817mv = {1, 1, 15})
/* compiled from: Pair.kt */
public final class PairKt {
    public static final <F, S> F component1(Pair<F, S> $this$component1) {
        Intrinsics.checkParameterIsNotNull($this$component1, "$this$component1");
        return $this$component1.first;
    }

    public static final <F, S> S component2(Pair<F, S> $this$component2) {
        Intrinsics.checkParameterIsNotNull($this$component2, "$this$component2");
        return $this$component2.second;
    }

    public static final <F, S> kotlin.Pair<F, S> toKotlinPair(Pair<F, S> $this$toKotlinPair) {
        Intrinsics.checkParameterIsNotNull($this$toKotlinPair, "$this$toKotlinPair");
        return new kotlin.Pair<>($this$toKotlinPair.first, $this$toKotlinPair.second);
    }

    public static final <F, S> Pair<F, S> toAndroidPair(kotlin.Pair<? extends F, ? extends S> $this$toAndroidPair) {
        Intrinsics.checkParameterIsNotNull($this$toAndroidPair, "$this$toAndroidPair");
        return new Pair<>($this$toAndroidPair.getFirst(), $this$toAndroidPair.getSecond());
    }
}
