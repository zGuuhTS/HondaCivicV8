package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceFragmentCompat;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo11814d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u0002:\u0001&B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0017J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010\u0012\u001a\u00020\u0013H&J$\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0017J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001eH\u0017J\u001a\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0017J\u0012\u0010!\u001a\u00020\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\u0012\u0010\"\u001a\u00020\u000e2\b\u0010#\u001a\u0004\u0018\u00010$H\u0002J\u0010\u0010\"\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u001eH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006'"}, mo11815d2 = {"Landroidx/preference/PreferenceHeaderFragmentCompat;", "Landroidx/fragment/app/Fragment;", "Landroidx/preference/PreferenceFragmentCompat$OnPreferenceStartFragmentCallback;", "()V", "onBackPressedCallback", "Landroidx/activity/OnBackPressedCallback;", "slidingPaneLayout", "Landroidx/slidingpanelayout/widget/SlidingPaneLayout;", "getSlidingPaneLayout", "()Landroidx/slidingpanelayout/widget/SlidingPaneLayout;", "buildContentView", "inflater", "Landroid/view/LayoutInflater;", "onAttach", "", "context", "Landroid/content/Context;", "onCreateInitialDetailFragment", "onCreatePreferenceHeader", "Landroidx/preference/PreferenceFragmentCompat;", "onCreateView", "Landroid/view/View;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onPreferenceStartFragment", "", "caller", "pref", "Landroidx/preference/Preference;", "onViewCreated", "view", "onViewStateRestored", "openPreferenceHeader", "intent", "Landroid/content/Intent;", "header", "InnerOnBackPressedCallback", "preference_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
/* compiled from: PreferenceHeaderFragmentCompat.kt */
public abstract class PreferenceHeaderFragmentCompat extends Fragment implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
    /* access modifiers changed from: private */
    public OnBackPressedCallback onBackPressedCallback;

    public abstract PreferenceFragmentCompat onCreatePreferenceHeader();

    public final SlidingPaneLayout getSlidingPaneLayout() {
        return (SlidingPaneLayout) requireView();
    }

    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        Intrinsics.checkNotNullParameter(caller, "caller");
        Intrinsics.checkNotNullParameter(pref, "pref");
        if (caller.getId() == C0100R.C0102id.preferences_header) {
            openPreferenceHeader(pref);
            return true;
        } else if (caller.getId() != C0100R.C0102id.preferences_detail) {
            return false;
        } else {
            FragmentFactory fragmentFactory = getChildFragmentManager().getFragmentFactory();
            ClassLoader classLoader = requireContext().getClassLoader();
            String fragment = pref.getFragment();
            Intrinsics.checkNotNull(fragment);
            Fragment frag = fragmentFactory.instantiate(classLoader, fragment);
            Intrinsics.checkNotNullExpressionValue(frag, "childFragmentManager.fra….fragment!!\n            )");
            frag.setArguments(pref.getExtras());
            FragmentManager $this$commit$iv = getChildFragmentManager();
            Intrinsics.checkNotNullExpressionValue($this$commit$iv, "childFragmentManager");
            FragmentTransaction transaction$iv = $this$commit$iv.beginTransaction();
            Intrinsics.checkNotNullExpressionValue(transaction$iv, "beginTransaction()");
            FragmentTransaction $this$onPreferenceStartFragment_u24lambda_u2d0 = transaction$iv;
            $this$onPreferenceStartFragment_u24lambda_u2d0.setReorderingAllowed(true);
            $this$onPreferenceStartFragment_u24lambda_u2d0.replace(C0100R.C0102id.preferences_detail, frag);
            $this$onPreferenceStartFragment_u24lambda_u2d0.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            $this$onPreferenceStartFragment_u24lambda_u2d0.addToBackStack((String) null);
            transaction$iv.commit();
            return true;
        }
    }

    @Metadata(mo11814d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, mo11815d2 = {"Landroidx/preference/PreferenceHeaderFragmentCompat$InnerOnBackPressedCallback;", "Landroidx/activity/OnBackPressedCallback;", "Landroidx/slidingpanelayout/widget/SlidingPaneLayout$PanelSlideListener;", "caller", "Landroidx/preference/PreferenceHeaderFragmentCompat;", "(Landroidx/preference/PreferenceHeaderFragmentCompat;)V", "handleOnBackPressed", "", "onPanelClosed", "panel", "Landroid/view/View;", "onPanelOpened", "onPanelSlide", "slideOffset", "", "preference_release"}, mo11816k = 1, mo11817mv = {1, 6, 0}, mo11819xi = 48)
    /* compiled from: PreferenceHeaderFragmentCompat.kt */
    private static final class InnerOnBackPressedCallback extends OnBackPressedCallback implements SlidingPaneLayout.PanelSlideListener {
        private final PreferenceHeaderFragmentCompat caller;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public InnerOnBackPressedCallback(PreferenceHeaderFragmentCompat caller2) {
            super(true);
            Intrinsics.checkNotNullParameter(caller2, "caller");
            this.caller = caller2;
            caller2.getSlidingPaneLayout().addPanelSlideListener(this);
        }

        public void handleOnBackPressed() {
            this.caller.getSlidingPaneLayout().closePane();
        }

        public void onPanelSlide(View panel, float slideOffset) {
            Intrinsics.checkNotNullParameter(panel, "panel");
        }

        public void onPanelOpened(View panel) {
            Intrinsics.checkNotNullParameter(panel, "panel");
            setEnabled(true);
        }

        public void onPanelClosed(View panel) {
            Intrinsics.checkNotNullParameter(panel, "panel");
            setEnabled(false);
        }
    }

    public void onAttach(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.onAttach(context);
        FragmentManager $this$commit$iv = getParentFragmentManager();
        Intrinsics.checkNotNullExpressionValue($this$commit$iv, "parentFragmentManager");
        FragmentTransaction transaction$iv = $this$commit$iv.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(transaction$iv, "beginTransaction()");
        transaction$iv.setPrimaryNavigationFragment(this);
        transaction$iv.commit();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        SlidingPaneLayout slidingPaneLayout = buildContentView(inflater);
        if (getChildFragmentManager().findFragmentById(C0100R.C0102id.preferences_header) == null) {
            PreferenceFragmentCompat newHeaderFragment = onCreatePreferenceHeader();
            FragmentManager $this$commit$iv = getChildFragmentManager();
            Intrinsics.checkNotNullExpressionValue($this$commit$iv, "childFragmentManager");
            FragmentTransaction transaction$iv = $this$commit$iv.beginTransaction();
            Intrinsics.checkNotNullExpressionValue(transaction$iv, "beginTransaction()");
            FragmentTransaction $this$onCreateView_u24lambda_u2d3_u24lambda_u2d2 = transaction$iv;
            $this$onCreateView_u24lambda_u2d3_u24lambda_u2d2.setReorderingAllowed(true);
            $this$onCreateView_u24lambda_u2d3_u24lambda_u2d2.add(C0100R.C0102id.preferences_header, (Fragment) newHeaderFragment);
            transaction$iv.commit();
        }
        slidingPaneLayout.setLockMode(3);
        return slidingPaneLayout;
    }

    private final SlidingPaneLayout buildContentView(LayoutInflater inflater) {
        SlidingPaneLayout slidingPaneLayout = new SlidingPaneLayout(inflater.getContext());
        slidingPaneLayout.setId(C0100R.C0102id.preferences_sliding_pane_layout);
        FragmentContainerView headerContainer = new FragmentContainerView(inflater.getContext());
        headerContainer.setId(C0100R.C0102id.preferences_header);
        SlidingPaneLayout.LayoutParams headerLayoutParams = new SlidingPaneLayout.LayoutParams(getResources().getDimensionPixelSize(C0100R.dimen.preferences_header_width), -1);
        headerLayoutParams.weight = (float) getResources().getInteger(C0100R.integer.preferences_header_pane_weight);
        slidingPaneLayout.addView(headerContainer, headerLayoutParams);
        FragmentContainerView detailContainer = new FragmentContainerView(inflater.getContext());
        detailContainer.setId(C0100R.C0102id.preferences_detail);
        SlidingPaneLayout.LayoutParams $this$buildContentView_u24lambda_u2d8 = new SlidingPaneLayout.LayoutParams(getResources().getDimensionPixelSize(C0100R.dimen.preferences_detail_width), -1);
        $this$buildContentView_u24lambda_u2d8.weight = (float) getResources().getInteger(C0100R.integer.preferences_detail_pane_weight);
        slidingPaneLayout.addView(detailContainer, $this$buildContentView_u24lambda_u2d8);
        return slidingPaneLayout;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        this.onBackPressedCallback = new InnerOnBackPressedCallback(this);
        View $this$doOnLayout$iv = getSlidingPaneLayout();
        if (!ViewCompat.isLaidOut($this$doOnLayout$iv) || $this$doOnLayout$iv.isLayoutRequested()) {
            $this$doOnLayout$iv.addOnLayoutChangeListener(new C0514x85395cb4(this));
        } else {
            View view2 = $this$doOnLayout$iv;
            OnBackPressedCallback access$getOnBackPressedCallback$p = this.onBackPressedCallback;
            Intrinsics.checkNotNull(access$getOnBackPressedCallback$p);
            access$getOnBackPressedCallback$p.setEnabled(getSlidingPaneLayout().isSlideable() && getSlidingPaneLayout().isOpen());
        }
        getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public final void onBackStackChanged() {
                PreferenceHeaderFragmentCompat.m48onViewCreated$lambda10(PreferenceHeaderFragmentCompat.this);
            }
        });
        Context requireContext = requireContext();
        OnBackPressedDispatcherOwner onBackPressedDispatcherOwner = requireContext instanceof OnBackPressedDispatcherOwner ? (OnBackPressedDispatcherOwner) requireContext : null;
        if (onBackPressedDispatcherOwner != null) {
            OnBackPressedDispatcher onBackPressedDispatcher = onBackPressedDispatcherOwner.getOnBackPressedDispatcher();
            LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
            OnBackPressedCallback onBackPressedCallback2 = this.onBackPressedCallback;
            Intrinsics.checkNotNull(onBackPressedCallback2);
            onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback2);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onViewCreated$lambda-10  reason: not valid java name */
    public static final void m48onViewCreated$lambda10(PreferenceHeaderFragmentCompat this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnBackPressedCallback onBackPressedCallback2 = this$0.onBackPressedCallback;
        Intrinsics.checkNotNull(onBackPressedCallback2);
        onBackPressedCallback2.setEnabled(this$0.getChildFragmentManager().getBackStackEntryCount() == 0);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        Fragment it;
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState == null && (it = onCreateInitialDetailFragment()) != null) {
            FragmentManager $this$commit$iv = getChildFragmentManager();
            Intrinsics.checkNotNullExpressionValue($this$commit$iv, "childFragmentManager");
            FragmentTransaction transaction$iv = $this$commit$iv.beginTransaction();
            Intrinsics.checkNotNullExpressionValue(transaction$iv, "beginTransaction()");
            FragmentTransaction $this$onViewStateRestored_u24lambda_u2d13_u24lambda_u2d12 = transaction$iv;
            $this$onViewStateRestored_u24lambda_u2d13_u24lambda_u2d12.setReorderingAllowed(true);
            $this$onViewStateRestored_u24lambda_u2d13_u24lambda_u2d12.replace(C0100R.C0102id.preferences_detail, it);
            transaction$iv.commit();
        }
    }

    public Fragment onCreateInitialDetailFragment() {
        Fragment findFragmentById = getChildFragmentManager().findFragmentById(C0100R.C0102id.preferences_header);
        if (findFragmentById != null) {
            PreferenceFragmentCompat headerFragment = (PreferenceFragmentCompat) findFragmentById;
            Fragment fragment = null;
            if (headerFragment.getPreferenceScreen().getPreferenceCount() <= 0) {
                return null;
            }
            int i = 0;
            int preferenceCount = headerFragment.getPreferenceScreen().getPreferenceCount();
            while (i < preferenceCount) {
                int index = i;
                i++;
                Preference header = headerFragment.getPreferenceScreen().getPreference(index);
                Intrinsics.checkNotNullExpressionValue(header, "headerFragment.preferenc…reen.getPreference(index)");
                if (header.getFragment() != null) {
                    String it = header.getFragment();
                    if (it != null) {
                        fragment = getChildFragmentManager().getFragmentFactory().instantiate(requireContext().getClassLoader(), it);
                    }
                    return fragment;
                }
            }
            return null;
        }
        throw new NullPointerException("null cannot be cast to non-null type androidx.preference.PreferenceFragmentCompat");
    }

    private final void openPreferenceHeader(Preference header) {
        Fragment fragment;
        if (header.getFragment() == null) {
            openPreferenceHeader(header.getIntent());
            return;
        }
        String it = header.getFragment();
        if (it == null) {
            fragment = null;
        } else {
            fragment = getChildFragmentManager().getFragmentFactory().instantiate(requireContext().getClassLoader(), it);
        }
        if (fragment != null) {
            fragment.setArguments(header.getExtras());
        }
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = getChildFragmentManager().getBackStackEntryAt(0);
            Intrinsics.checkNotNullExpressionValue(entry, "childFragmentManager.getBackStackEntryAt(0)");
            getChildFragmentManager().popBackStack(entry.getId(), 1);
        }
        FragmentManager $this$commit$iv = getChildFragmentManager();
        Intrinsics.checkNotNullExpressionValue($this$commit$iv, "childFragmentManager");
        FragmentTransaction transaction$iv = $this$commit$iv.beginTransaction();
        Intrinsics.checkNotNullExpressionValue(transaction$iv, "beginTransaction()");
        FragmentTransaction $this$openPreferenceHeader_u24lambda_u2d17 = transaction$iv;
        $this$openPreferenceHeader_u24lambda_u2d17.setReorderingAllowed(true);
        int i = C0100R.C0102id.preferences_detail;
        Intrinsics.checkNotNull(fragment);
        $this$openPreferenceHeader_u24lambda_u2d17.replace(i, fragment);
        if (getSlidingPaneLayout().isOpen()) {
            $this$openPreferenceHeader_u24lambda_u2d17.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
        getSlidingPaneLayout().openPane();
        transaction$iv.commit();
    }

    private final void openPreferenceHeader(Intent intent) {
        if (intent != null) {
            startActivity(intent);
        }
    }
}
