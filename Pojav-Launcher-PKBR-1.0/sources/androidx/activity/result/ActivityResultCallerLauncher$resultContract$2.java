package androidx.activity.result;

import android.content.Context;
import android.content.Intent;
import androidx.activity.result.contract.ActivityResultContract;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo11813bv = {1, 0, 3}, mo11814d1 = {"\u0000\t\n\u0000\n\u0002\b\u0005*\u0001\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, mo11815d2 = {"<anonymous>", "androidx/activity/result/ActivityResultCallerLauncher$resultContract$2$1", "I", "O", "invoke", "()Landroidx/activity/result/ActivityResultCallerLauncher$resultContract$2$1;"}, mo11816k = 3, mo11817mv = {1, 4, 1})
/* compiled from: ActivityResultCaller.kt */
final class ActivityResultCallerLauncher$resultContract$2 extends Lambda implements Function0<C01851> {
    final /* synthetic */ ActivityResultCallerLauncher this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ActivityResultCallerLauncher$resultContract$2(ActivityResultCallerLauncher activityResultCallerLauncher) {
        super(0);
        this.this$0 = activityResultCallerLauncher;
    }

    public final C01851 invoke() {
        return new ActivityResultContract<Unit, O>(this) {
            final /* synthetic */ ActivityResultCallerLauncher$resultContract$2 this$0;

            {
                this.this$0 = this$0;
            }

            public Intent createIntent(Context context, Unit unit) {
                Intrinsics.checkNotNullParameter(context, "context");
                Intent createIntent = this.this$0.this$0.getCallerContract().createIntent(context, this.this$0.this$0.getInput());
                Intrinsics.checkNotNullExpressionValue(createIntent, "callerContract.createIntent(context, input)");
                return createIntent;
            }

            public O parseResult(int resultCode, Intent intent) {
                return this.this$0.this$0.getCallerContract().parseResult(resultCode, intent);
            }
        };
    }
}
