package net.kdt.pojavlaunch.prefs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.preference.Preference;
import com.p000br.pixelmonbrasil.debug.R;

public class ControlOffsetPreference extends Preference {
    private AlertDialog mPreferenceDialog;

    public ControlOffsetPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ControlOffsetPreference(Context context) {
        super(context);
        init();
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        this.mPreferenceDialog.show();
        SeekBar topOffsetSeekbar = (SeekBar) this.mPreferenceDialog.findViewById(R.id.control_offset_top_seekbar);
        SeekBar rightOffsetSeekbar = (SeekBar) this.mPreferenceDialog.findViewById(R.id.control_offset_right_seekbar);
        SeekBar bottomOffsetSeekbar = (SeekBar) this.mPreferenceDialog.findViewById(R.id.control_offset_bottom_seekbar);
        SeekBar leftOffsetSeekbar = (SeekBar) this.mPreferenceDialog.findViewById(R.id.control_offset_left_seekbar);
        final SeekBar seekBar = topOffsetSeekbar;
        final TextView textView = (TextView) this.mPreferenceDialog.findViewById(R.id.control_offset_top_textview);
        final SeekBar seekBar2 = rightOffsetSeekbar;
        final TextView textView2 = (TextView) this.mPreferenceDialog.findViewById(R.id.control_offset_right_textview);
        final SeekBar seekBar3 = bottomOffsetSeekbar;
        final TextView textView3 = (TextView) this.mPreferenceDialog.findViewById(R.id.control_offset_bottom_textview);
        final SeekBar seekBar4 = leftOffsetSeekbar;
        final TextView textView4 = (TextView) this.mPreferenceDialog.findViewById(R.id.control_offset_left_textview);
        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (seekBar == seekBar) {
                    textView.setText(String.format("%s %d%s", new Object[]{ControlOffsetPreference.this.getContext().getString(R.string.control_top_offset), Integer.valueOf(i), " px"}));
                } else if (seekBar == seekBar2) {
                    textView2.setText(String.format("%s %d%s", new Object[]{ControlOffsetPreference.this.getContext().getString(R.string.control_right_offset), Integer.valueOf(i), " px"}));
                } else if (seekBar == seekBar3) {
                    textView3.setText(String.format("%s %d%s", new Object[]{ControlOffsetPreference.this.getContext().getString(R.string.control_bottom_offset), Integer.valueOf(i), " px"}));
                } else if (seekBar == seekBar4) {
                    textView4.setText(String.format("%s %d%s", new Object[]{ControlOffsetPreference.this.getContext().getString(R.string.control_left_offset), Integer.valueOf(i), " px"}));
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
        topOffsetSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        rightOffsetSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        bottomOffsetSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        leftOffsetSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        topOffsetSeekbar.setProgress(LauncherPreferences.PREF_CONTROL_TOP_OFFSET);
        rightOffsetSeekbar.setProgress(LauncherPreferences.PREF_CONTROL_RIGHT_OFFSET);
        bottomOffsetSeekbar.setProgress(LauncherPreferences.PREF_CONTROL_BOTTOM_OFFSET);
        leftOffsetSeekbar.setProgress(LauncherPreferences.PREF_CONTROL_LEFT_OFFSET);
        seekBarChangeListener.onProgressChanged(topOffsetSeekbar, LauncherPreferences.PREF_CONTROL_TOP_OFFSET, false);
        seekBarChangeListener.onProgressChanged(rightOffsetSeekbar, LauncherPreferences.PREF_CONTROL_RIGHT_OFFSET, false);
        seekBarChangeListener.onProgressChanged(bottomOffsetSeekbar, LauncherPreferences.PREF_CONTROL_BOTTOM_OFFSET, false);
        seekBarChangeListener.onProgressChanged(leftOffsetSeekbar, LauncherPreferences.PREF_CONTROL_LEFT_OFFSET, false);
        this.mPreferenceDialog.getButton(-1).setOnClickListener(new View.OnClickListener(seekBar, rightOffsetSeekbar, bottomOffsetSeekbar, leftOffsetSeekbar) {
            public final /* synthetic */ SeekBar f$1;
            public final /* synthetic */ SeekBar f$2;
            public final /* synthetic */ SeekBar f$3;
            public final /* synthetic */ SeekBar f$4;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
            }

            public final void onClick(View view) {
                ControlOffsetPreference.this.lambda$onClick$0$ControlOffsetPreference(this.f$1, this.f$2, this.f$3, this.f$4, view);
            }
        });
    }

    public /* synthetic */ void lambda$onClick$0$ControlOffsetPreference(SeekBar topOffsetSeekbar, SeekBar rightOffsetSeekbar, SeekBar bottomOffsetSeekbar, SeekBar leftOffsetSeekbar, View view) {
        LauncherPreferences.DEFAULT_PREF.edit().putInt("controlTopOffset", topOffsetSeekbar.getProgress()).apply();
        LauncherPreferences.DEFAULT_PREF.edit().putInt("controlRightOffset", rightOffsetSeekbar.getProgress()).apply();
        LauncherPreferences.DEFAULT_PREF.edit().putInt("controlBottomOffset", bottomOffsetSeekbar.getProgress()).apply();
        LauncherPreferences.DEFAULT_PREF.edit().putInt("controlLeftOffset", leftOffsetSeekbar.getProgress()).apply();
        this.mPreferenceDialog.dismiss();
    }

    private void init() {
        if (getTitle() == null) {
            setTitle((int) R.string.preference_control_offset_title);
            setSummary((int) R.string.preference_control_offset_description);
        }
        if (getIcon() == null) {
            setIcon(17301614);
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(R.layout.dialog_control_offset_preference);
        dialogBuilder.setTitle(getContext().getString(R.string.control_offset_title));
        dialogBuilder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        dialogBuilder.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
        this.mPreferenceDialog = dialogBuilder.create();
    }
}
