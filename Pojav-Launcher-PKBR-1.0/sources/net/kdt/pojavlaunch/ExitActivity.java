package net.kdt.pojavlaunch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.p000br.pixelmonbrasil.debug.R;

public class ExitActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int code = -1;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            code = extras.getInt("code", -1);
        }
        new AlertDialog.Builder(this).setMessage((CharSequence) getString(R.string.mcn_exit_title, new Object[]{Integer.valueOf(code)})).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() {
            public final void onDismiss(DialogInterface dialogInterface) {
                ExitActivity.this.lambda$onCreate$0$ExitActivity(dialogInterface);
            }
        }).show();
    }

    public /* synthetic */ void lambda$onCreate$0$ExitActivity(DialogInterface dialog) {
        finish();
    }

    public static void showExitMessage(Context ctx, int code) {
        Intent i = new Intent(ctx, ExitActivity.class);
        i.putExtra("code", code);
        i.addFlags(32768);
        i.addFlags(268435456);
        ctx.startActivity(i);
    }
}
