package com.kdt;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.AWTInputEvent;
import net.kdt.pojavlaunch.Logger;
import top.defaults.checkerboarddrawable.BuildConfig;

public class LoggerView extends ConstraintLayout {
    private Logger.eventLogListener mLogListener;
    private TextView mLogTextView;
    private ScrollView mScrollView;
    private ToggleButton mToggleButton;

    public LoggerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LoggerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        this.mToggleButton.setChecked(visibility == 0);
    }

    private void init() {
        inflate(getContext(), R.layout.view_logger, this);
        TextView textView = (TextView) findViewById(R.id.content_log_view);
        this.mLogTextView = textView;
        textView.setTypeface(Typeface.MONOSPACE);
        this.mLogTextView.setMaxLines(Integer.MAX_VALUE);
        this.mLogTextView.setEllipsize((TextUtils.TruncateAt) null);
        this.mLogTextView.setVisibility(8);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.content_log_toggle_log);
        this.mToggleButton = toggleButton;
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                LoggerView.this.lambda$init$0$LoggerView(compoundButton, z);
            }
        });
        this.mToggleButton.setChecked(false);
        ((ImageButton) findViewById(R.id.log_view_cancel)).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                LoggerView.this.lambda$init$1$LoggerView(view);
            }
        });
        this.mScrollView = (ScrollView) findViewById(R.id.content_log_scroll);
        this.mLogListener = new Logger.eventLogListener() {
            public final void onEventLogged(String str) {
                LoggerView.this.lambda$init$3$LoggerView(str);
            }
        };
        Logger.getInstance().setLogListener(this.mLogListener);
    }

    public /* synthetic */ void lambda$init$0$LoggerView(CompoundButton compoundButton, boolean isChecked) {
        this.mLogTextView.setVisibility(isChecked ? 0 : 8);
        if (!isChecked) {
            this.mLogTextView.setText(BuildConfig.FLAVOR);
        }
    }

    public /* synthetic */ void lambda$init$1$LoggerView(View view) {
        setVisibility(8);
    }

    public /* synthetic */ void lambda$init$3$LoggerView(String text) {
        if (this.mLogTextView.getVisibility() == 0) {
            post(new Runnable(text) {
                public final /* synthetic */ String f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    LoggerView.this.lambda$null$2$LoggerView(this.f$1);
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$2$LoggerView(String text) {
        this.mLogTextView.append(text + 10);
        this.mScrollView.fullScroll(AWTInputEvent.VK_DEAD_CIRCUMFLEX);
    }
}
