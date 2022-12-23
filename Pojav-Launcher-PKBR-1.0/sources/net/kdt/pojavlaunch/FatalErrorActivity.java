package net.kdt.pojavlaunch;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.p000br.pixelmonbrasil.debug.R;

public class FatalErrorActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        boolean storageAllow = extras.getBoolean("storageAllow");
        String stackTrace = Log.getStackTraceString((Throwable) extras.getSerializable("throwable"));
        new AlertDialog.Builder(this).setTitle((int) R.string.error_fatal).setMessage((CharSequence) (storageAllow ? "Crash stack trace saved to " + extras.getString("savePath") + "." : "Storage permission is required to save crash stack trace!") + "\n\n" + stackTrace).setPositiveButton(17039370, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                FatalErrorActivity.this.lambda$onCreate$0$FatalErrorActivity(dialogInterface, i);
            }
        }).setNegativeButton((int) R.string.global_restart, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                FatalErrorActivity.this.lambda$onCreate$1$FatalErrorActivity(dialogInterface, i);
            }
        }).setNeutralButton(17039361, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener(stackTrace) {
            public final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void onClick(DialogInterface dialogInterface, int i) {
                FatalErrorActivity.this.lambda$onCreate$2$FatalErrorActivity(this.f$1, dialogInterface, i);
            }
        }).setCancelable(false).show();
    }

    public /* synthetic */ void lambda$onCreate$0$FatalErrorActivity(DialogInterface p1, int p2) {
        finish();
    }

    public /* synthetic */ void lambda$onCreate$1$FatalErrorActivity(DialogInterface p1, int p2) {
        startActivity(new Intent(this, LauncherActivity.class));
    }

    public /* synthetic */ void lambda$onCreate$2$FatalErrorActivity(String stackTrace, DialogInterface p1, int p2) {
        ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("error", stackTrace));
        finish();
    }

    public static void showError(Context ctx, String savePath, boolean storageAllow, Throwable th) {
        Intent ferrorIntent = new Intent(ctx, FatalErrorActivity.class);
        ferrorIntent.addFlags(32768);
        ferrorIntent.addFlags(268435456);
        ferrorIntent.putExtra("throwable", th);
        ferrorIntent.putExtra("savePath", savePath);
        ferrorIntent.putExtra("storageAllow", storageAllow);
        ctx.startActivity(ferrorIntent);
    }
}
