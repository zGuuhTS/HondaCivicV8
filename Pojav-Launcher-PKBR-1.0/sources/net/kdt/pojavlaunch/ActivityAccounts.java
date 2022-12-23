package net.kdt.pojavlaunch;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.kdt.mcgui.mcAccountSpinner;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.fragments.MicrosoftLoginFragment;

public class ActivityAccounts extends AppCompatActivity {
    private final View.OnClickListener mAccountDeleteButtonListener = new View.OnClickListener() {
        public final void onClick(View view) {
            ActivityAccounts.this.lambda$new$1$ActivityAccounts(view);
        }
    };
    private mcAccountSpinner mAccountSpinner;
    private Button mBackButton;
    private final View.OnClickListener mBackButtonListener = new View.OnClickListener() {
        public final void onClick(View view) {
            ActivityAccounts.this.lambda$new$2$ActivityAccounts(view);
        }
    };
    private ImageButton mDeleteAccountButton;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_accounts);
        this.mDeleteAccountButton = (ImageButton) findViewById(R.id.delete_account_button);
        this.mAccountSpinner = (mcAccountSpinner) findViewById(R.id.account_spinner);
        this.mBackButton = (Button) findViewById(R.id.back_button);
        this.mDeleteAccountButton.setOnClickListener(this.mAccountDeleteButtonListener);
        this.mBackButton.setOnClickListener(this.mBackButtonListener);
    }

    public /* synthetic */ void lambda$new$1$ActivityAccounts(View v) {
        new AlertDialog.Builder(this).setMessage((int) R.string.warning_remove_account).setNeutralButton(17039360, (DialogInterface.OnClickListener) null).setPositiveButton((int) R.string.global_delete, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                ActivityAccounts.this.lambda$null$0$ActivityAccounts(dialogInterface, i);
            }
        }).show();
    }

    public /* synthetic */ void lambda$null$0$ActivityAccounts(DialogInterface dialog, int which) {
        this.mAccountSpinner.removeCurrentAccount();
    }

    public /* synthetic */ void lambda$new$2$ActivityAccounts(View v) {
        finish();
    }

    public void onBackPressed() {
        if (isFragmentVisible(MicrosoftLoginFragment.TAG)) {
            MicrosoftLoginFragment fragment = (MicrosoftLoginFragment) getSupportFragmentManager().findFragmentByTag(MicrosoftLoginFragment.TAG);
            if (fragment.canGoBack()) {
                fragment.goBack();
                return;
            }
        }
        super.onBackPressed();
    }

    private boolean isFragmentVisible(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment != null && fragment.isVisible();
    }
}
