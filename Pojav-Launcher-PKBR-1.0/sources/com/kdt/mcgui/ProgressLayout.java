package com.kdt.mcgui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.kdt.mcgui.ProgressLayout;
import com.p000br.pixelmonbrasil.debug.R;
import java.util.ArrayList;
import java.util.Iterator;
import net.kdt.pojavlaunch.progresskeeper.ProgressKeeper;
import net.kdt.pojavlaunch.progresskeeper.ProgressListener;
import net.kdt.pojavlaunch.progresskeeper.TaskCountListener;
import top.defaults.checkerboarddrawable.BuildConfig;

public class ProgressLayout extends ConstraintLayout implements View.OnClickListener, TaskCountListener {
    public static final String AUTHENTICATE_MICROSOFT = "authenticate_microsoft";
    public static final String DOWNLOAD_MINECRAFT = "download_minecraft";
    public static final String INSTALL_MOD = "install_mod";
    public static final String UNPACK_RUNTIME = "unpack_runtime";
    private ImageView mFlipArrow;
    /* access modifiers changed from: private */
    public LinearLayout mLinearLayout;
    private final ArrayList<LayoutProgressListener> mMap = new ArrayList<>();
    private TextView mTaskNumberDisplayer;

    public ProgressLayout(Context context) {
        super(context);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void observe(String progressKey) {
        this.mMap.add(new LayoutProgressListener(progressKey));
    }

    public void cleanUpObservers() {
        Iterator<LayoutProgressListener> it = this.mMap.iterator();
        while (it.hasNext()) {
            LayoutProgressListener progressListener = it.next();
            ProgressKeeper.removeListener(progressListener.progressKey, progressListener);
        }
    }

    public boolean hasProcesses() {
        return ProgressKeeper.getTaskCount() > 0;
    }

    private void init() {
        inflate(getContext(), R.layout.view_progress, this);
        this.mLinearLayout = (LinearLayout) findViewById(R.id.progress_linear_layout);
        this.mTaskNumberDisplayer = (TextView) findViewById(R.id.progress_textview);
        this.mFlipArrow = (ImageView) findViewById(R.id.progress_flip_arrow);
        setBackgroundColor(getResources().getColor(R.color.background_bottom_bar));
        setOnClickListener(this);
    }

    public static void setProgress(String progressKey, int progress) {
        ProgressKeeper.submitProgress(progressKey, progress, -1, null);
    }

    public static void setProgress(String progressKey, int progress, int resource, Object... message) {
        ProgressKeeper.submitProgress(progressKey, progress, resource, message);
    }

    public static void setProgress(String progressKey, int progress, String message) {
        setProgress(progressKey, progress, -1, message);
    }

    public static void clearProgress(String progressKey) {
        setProgress(progressKey, -1, -1, new Object[0]);
    }

    public void onClick(View v) {
        LinearLayout linearLayout = this.mLinearLayout;
        linearLayout.setVisibility(linearLayout.getVisibility() == 8 ? 0 : 8);
        this.mFlipArrow.setRotation(this.mLinearLayout.getVisibility() == 8 ? 0.0f : 180.0f);
    }

    public void onUpdateTaskCount(int tc) {
        post(new Runnable(tc) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                ProgressLayout.this.lambda$onUpdateTaskCount$0$ProgressLayout(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$onUpdateTaskCount$0$ProgressLayout(int tc) {
        if (tc > 0) {
            this.mTaskNumberDisplayer.setText(getContext().getString(R.string.progresslayout_tasks_in_progress, new Object[]{Integer.valueOf(tc)}));
            setVisibility(0);
            return;
        }
        setVisibility(8);
    }

    class LayoutProgressListener implements ProgressListener {
        final LinearLayout.LayoutParams params;
        final String progressKey;
        final TextProgressBar textView;

        public LayoutProgressListener(String progressKey2) {
            this.progressKey = progressKey2;
            this.textView = new TextProgressBar(ProgressLayout.this.getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, ProgressLayout.this.getResources().getDimensionPixelOffset(R.dimen._20sdp));
            this.params = layoutParams;
            layoutParams.bottomMargin = ProgressLayout.this.getResources().getDimensionPixelOffset(R.dimen._6sdp);
            ProgressKeeper.addListener(progressKey2, this);
        }

        public void onProgressStarted() {
            ProgressLayout.this.post(new Runnable() {
                public final void run() {
                    ProgressLayout.LayoutProgressListener.this.lambda$onProgressStarted$0$ProgressLayout$LayoutProgressListener();
                }
            });
        }

        public /* synthetic */ void lambda$onProgressStarted$0$ProgressLayout$LayoutProgressListener() {
            Log.i("ProgressLayout", "onProgressStarted");
            ProgressLayout.this.mLinearLayout.addView(this.textView, this.params);
        }

        public void onProgressUpdated(int progress, int resid, Object... va) {
            ProgressLayout.this.post(new Runnable(progress, resid, va) {
                public final /* synthetic */ int f$1;
                public final /* synthetic */ int f$2;
                public final /* synthetic */ Object[] f$3;

                {
                    this.f$1 = r2;
                    this.f$2 = r3;
                    this.f$3 = r4;
                }

                public final void run() {
                    ProgressLayout.LayoutProgressListener.this.lambda$onProgressUpdated$1$ProgressLayout$LayoutProgressListener(this.f$1, this.f$2, this.f$3);
                }
            });
        }

        public /* synthetic */ void lambda$onProgressUpdated$1$ProgressLayout$LayoutProgressListener(int progress, int resid, Object[] va) {
            this.textView.setProgress(progress);
            if (resid != -1) {
                this.textView.setText(ProgressLayout.this.getContext().getString(resid, va));
            } else if (va.length <= 0 || va[0] == null) {
                this.textView.setText(BuildConfig.FLAVOR);
            } else {
                this.textView.setText(va[0]);
            }
        }

        public /* synthetic */ void lambda$onProgressEnded$2$ProgressLayout$LayoutProgressListener() {
            ProgressLayout.this.mLinearLayout.removeView(this.textView);
        }

        public void onProgressEnded() {
            ProgressLayout.this.post(new Runnable() {
                public final void run() {
                    ProgressLayout.LayoutProgressListener.this.lambda$onProgressEnded$2$ProgressLayout$LayoutProgressListener();
                }
            });
        }
    }
}
