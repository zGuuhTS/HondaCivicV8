package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.Tools;

public class SelectAuthFragment extends Fragment {
    public static final String TAG = "AUTH_SELECT_FRAGMENT";

    public SelectAuthFragment() {
        super(R.layout.fragment_select_auth_method);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        ((Button) view.findViewById(R.id.button_microsoft_authentication)).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SelectAuthFragment.this.lambda$onViewCreated$0$SelectAuthFragment(view);
            }
        });
        ((Button) view.findViewById(R.id.button_local_authentication)).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                SelectAuthFragment.this.lambda$onViewCreated$1$SelectAuthFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$onViewCreated$0$SelectAuthFragment(View v) {
        Tools.swapFragment(requireActivity(), MicrosoftLoginFragment.class, MicrosoftLoginFragment.TAG, false, (Bundle) null);
    }

    public /* synthetic */ void lambda$onViewCreated$1$SelectAuthFragment(View v) {
        Tools.swapFragment(requireActivity(), LocalLoginFragment.class, LocalLoginFragment.TAG, false, (Bundle) null);
    }
}
