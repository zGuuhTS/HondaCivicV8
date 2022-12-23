package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import top.defaults.checkerboarddrawable.BuildConfig;

public class LocalLoginFragment extends Fragment {
    public static final String TAG = "LOCAL_LOGIN_FRAGMENT";
    private Button mLoginButton;
    private EditText mUsernameEditText;

    public LocalLoginFragment() {
        super(R.layout.fragment_local_login);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.mUsernameEditText = (EditText) view.findViewById(R.id.login_edit_email);
        Button button = (Button) view.findViewById(R.id.login_button);
        this.mLoginButton = button;
        button.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                LocalLoginFragment.this.lambda$onViewCreated$0$LocalLoginFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$onViewCreated$0$LocalLoginFragment(View v) {
        if (checkEditText()) {
            ExtraCore.setValue(ExtraConstants.MOJANG_LOGIN_TODO, new String[]{this.mUsernameEditText.getText().toString(), BuildConfig.FLAVOR});
            Tools.swapFragment(requireActivity(), SelectAuthFragment.class, MainMenuFragment.TAG, false, (Bundle) null);
        }
    }

    private boolean checkEditText() {
        new File(Tools.DIR_ACCOUNT_OLD).mkdir();
        String text = this.mUsernameEditText.getText().toString();
        return !text.isEmpty() && text.length() >= 3 && text.length() <= 16 && text.matches("\\w+") && !new File(new StringBuilder().append(Tools.DIR_ACCOUNT_NEW).append("/").append(text).append(".json").toString()).exists();
    }
}
