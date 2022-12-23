package net.kdt.pojavlaunch.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.fragments.MainMenuFragment;
import net.kdt.pojavlaunch.prefs.screens.LauncherPreferenceFragment;

public class MainMenuFragment extends Fragment {
    public static final String SETTING_FRAGMENT_TAG = "SETTINGS_FRAGMENT";
    public static final String TAG = "MainMenuFragment";
    /* access modifiers changed from: private */
    public static String[] mDescriptions = {"Nós somos a maior comunidade da América Latina. Jogue agora e junte-se a outros 1000 jogadores!", "Este ano passou voando mesmo e derrepente já estamos no Natal de novo! Novidades, eventos e promoções aguardam os jogadores no Pixelmon Brasil ;)"};
    /* access modifiers changed from: private */
    public static Integer[] mThumbeId = {Integer.valueOf(R.drawable.mobile_render), Integer.valueOf(R.drawable.mobile_render_xmas)};
    /* access modifiers changed from: private */
    public static Integer[] mThumbeIdVert = {Integer.valueOf(R.drawable.mobile_render_vert), Integer.valueOf(R.drawable.mobile_render_xmas_vert)};
    /* access modifiers changed from: private */
    public static String[] mTitles = {"Bem-Vindo", "HO HO HO!"};
    /* access modifiers changed from: private */
    public TextView description;
    private ViewGroup frameLayout;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public int f0i = 0;
    private Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public View mImageView;
    Runnable mThumbRun = new Runnable() {
        public void run() {
            while (true) {
                if (Tools.canChange.booleanValue() && MainMenuFragment.this.isVisible()) {
                    Tools.canChange = false;
                    MainMenuFragment.this.requireActivity().runOnUiThread(new Runnable() {
                        public final void run() {
                            MainMenuFragment.C00001.this.lambda$run$0$MainMenuFragment$1();
                        }
                    });
                    MainMenuFragment.access$008(MainMenuFragment.this);
                    if (MainMenuFragment.this.f0i >= MainMenuFragment.mThumbeId.length) {
                        int unused = MainMenuFragment.this.f0i = 0;
                    }
                }
            }
        }

        public /* synthetic */ void lambda$run$0$MainMenuFragment$1() {
            if (MainMenuFragment.this.requireActivity().getResources().getConfiguration().orientation == 1) {
                MainMenuFragment.this.mImageView.setBackgroundResource(MainMenuFragment.mThumbeIdVert[MainMenuFragment.this.f0i].intValue());
            } else {
                MainMenuFragment.this.mImageView.setBackgroundResource(MainMenuFragment.mThumbeId[MainMenuFragment.this.f0i].intValue());
            }
            MainMenuFragment.this.title.setText(MainMenuFragment.mTitles[MainMenuFragment.this.f0i]);
            MainMenuFragment.this.description.setText(MainMenuFragment.mDescriptions[MainMenuFragment.this.f0i]);
        }
    };
    /* access modifiers changed from: private */
    public TextView title;
    private View view;

    static /* synthetic */ int access$008(MainMenuFragment x0) {
        int i = x0.f0i;
        x0.f0i = i + 1;
        return i;
    }

    public MainMenuFragment() {
        super(R.layout.fragment_launcher);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.frameLayout = new FrameLayout(getActivity());
        View inflate = inflater.inflate(R.layout.fragment_launcher, (ViewGroup) null);
        this.view = inflate;
        this.frameLayout.addView(inflate);
        return this.frameLayout;
    }

    public void onViewCreated(View view2, Bundle savedInstanceState) {
        Button mPlayButton = (Button) view2.findViewById(R.id.play_button);
        Button mSettingsButton = (Button) view2.findViewById(R.id.settings_button);
        Button mDiscordButton = (Button) view2.findViewById(R.id.discord_button);
        Button mSocialButton = (Button) view2.findViewById(R.id.social_button);
        this.title = (TextView) view2.findViewById(R.id.fragment_title);
        this.description = (TextView) view2.findViewById(R.id.fragment_description);
        this.mImageView = view2.findViewById(R.id.image_background);
        if (!Tools.startedCounter.booleanValue()) {
            Tools.changeBackground();
        }
        new Thread(new Runnable() {
            public final void run() {
                MainMenuFragment.this.lambda$onViewCreated$0$MainMenuFragment();
            }
        }).start();
        mPlayButton.setOnClickListener($$Lambda$MainMenuFragment$rxwIeHpwQcYwqOpA1JtaXVzdPho.INSTANCE);
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MainMenuFragment.this.lambda$onViewCreated$2$MainMenuFragment(view);
            }
        });
        mDiscordButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MainMenuFragment.this.lambda$onViewCreated$3$MainMenuFragment(view);
            }
        });
        mSocialButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MainMenuFragment.this.lambda$onViewCreated$4$MainMenuFragment(view);
            }
        });
    }

    public /* synthetic */ void lambda$onViewCreated$0$MainMenuFragment() {
        this.mThumbRun.run();
    }

    public /* synthetic */ void lambda$onViewCreated$2$MainMenuFragment(View v) {
        Tools.swapFragment(getActivity(), LauncherPreferenceFragment.class, SETTING_FRAGMENT_TAG, true, (Bundle) null);
    }

    public /* synthetic */ void lambda$onViewCreated$3$MainMenuFragment(View v) {
        Tools.openURL(requireActivity(), "https://discord.gg/pxbr");
    }

    public /* synthetic */ void lambda$onViewCreated$4$MainMenuFragment(View v) {
        Tools.openURL(requireActivity(), "https://www.instagram.com/pixelmonbrasil/");
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.frameLayout.removeAllViews();
        View inflate = ((LayoutInflater) requireActivity().getSystemService("layout_inflater")).inflate(R.layout.fragment_launcher, (ViewGroup) null);
        this.view = inflate;
        this.frameLayout.addView(inflate);
    }
}
