package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mo11814d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n"}, mo11815d2 = {"<anonymous>", "", "e"}, mo11816k = 3, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt$safeCtor$1 extends Lambda implements Function1<Throwable, Throwable> {
    final /* synthetic */ Function1<Throwable, Throwable> $block;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ExceptionsConstuctorKt$safeCtor$1(Function1<? super Throwable, ? extends Throwable> function1) {
        super(1);
        this.$block = function1;
    }

    public final Throwable invoke(Throwable e) {
        Object obj;
        Function1<Throwable, Throwable> function1 = this.$block;
        try {
            Result.Companion companion = Result.Companion;
            obj = Result.m64constructorimpl(function1.invoke(e));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m64constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m70isFailureimpl(obj)) {
            obj = null;
        }
        return (Throwable) obj;
    }
}
