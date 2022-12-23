package net.kdt.pojavlaunch.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeekBarPreference;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.C0144R;
import top.defaults.checkerboarddrawable.BuildConfig;

public class CustomSeekBarPreference extends SeekBarPreference {
    /* access modifiers changed from: private */
    public int mMin;
    private String mSuffix;
    /* access modifiers changed from: private */
    public TextView mTextView;

    public CustomSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mSuffix = BuildConfig.FLAVOR;
        TypedArray a = context.obtainStyledAttributes(attrs, C0144R.styleable.SeekBarPreference, defStyleAttr, defStyleRes);
        this.mMin = a.getInt(3, 0);
        a.recycle();
    }

    public CustomSeekBarPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomSeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarPreferenceStyle);
    }

    public CustomSeekBarPreference(Context context) {
        this(context, (AttributeSet) null);
    }

    public void setMin(int min) {
        super.setMin(min);
        if (min != this.mMin) {
            this.mMin = min;
        }
    }

    public void onBindViewHolder(PreferenceViewHolder view) {
        super.onBindViewHolder(view);
        ((TextView) view.findViewById(16908310)).setTextColor(-1);
        TextView textView = (TextView) view.findViewById(R.id.seekbar_value);
        this.mTextView = textView;
        textView.setTextAlignment(2);
        ((SeekBar) view.findViewById(R.id.seekbar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CustomSeekBarPreference.this.mTextView.setText(String.valueOf(CustomSeekBarPreference.this.mMin + ((((progress + CustomSeekBarPreference.this.mMin) / CustomSeekBarPreference.this.getSeekBarIncrement()) * CustomSeekBarPreference.this.getSeekBarIncrement()) - CustomSeekBarPreference.this.mMin)));
                CustomSeekBarPreference.this.updateTextViewWithSuffix();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = (((seekBar.getProgress() + CustomSeekBarPreference.this.mMin) / CustomSeekBarPreference.this.getSeekBarIncrement()) * CustomSeekBarPreference.this.getSeekBarIncrement()) - CustomSeekBarPreference.this.mMin;
                CustomSeekBarPreference customSeekBarPreference = CustomSeekBarPreference.this;
                customSeekBarPreference.setValue(customSeekBarPreference.mMin + progress);
                CustomSeekBarPreference.this.updateTextViewWithSuffix();
            }
        });
        updateTextViewWithSuffix();
    }

    public void setSuffix(String suffix) {
        this.mSuffix = suffix;
    }

    public void setRange(int min, int max) {
        setMin(min);
        setMax(max);
    }

    /* access modifiers changed from: private */
    public void updateTextViewWithSuffix() {
        if (!this.mTextView.getText().toString().endsWith(this.mSuffix)) {
            TextView textView = this.mTextView;
            textView.setText(String.format("%s%s", new Object[]{textView.getText(), this.mSuffix}));
        }
    }
}
