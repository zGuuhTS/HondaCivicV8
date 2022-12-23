package net.kdt.pojavlaunch.customcontrols.handleview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.kdt.DefocusableScrollView;
import com.p000br.pixelmonbrasil.debug.R;
import java.util.Arrays;
import java.util.Collections;
import net.kdt.pojavlaunch.EfficientAndroidLWJGLKeycode;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.colorselector.ColorSelectionListener;
import net.kdt.pojavlaunch.colorselector.ColorSelector;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.ControlDrawerData;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlDrawer;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface;

public class EditControlPopup {
    public boolean internalChanges = false;
    protected ArrayAdapter<String> mAdapter;
    protected TextView mAlphaPercentTextView;
    protected SeekBar mAlphaSeekbar;
    private final ObjectAnimator mColorEditorAnimator;
    private final ColorSelector mColorSelector;
    protected TextView mCornerRadiusPercentTextView;
    protected SeekBar mCornerRadiusSeekbar;
    private TextView mCornerRadiusTextView;
    /* access modifiers changed from: private */
    public ControlInterface mCurrentlyEditedButton;
    private boolean mDisplaying = false;
    private boolean mDisplayingColor = false;
    private final ObjectAnimator mEditPopupAnimator;
    protected EditText mHeightEditText;
    protected Spinner[] mKeycodeSpinners = new Spinner[4];
    private final View.OnLayoutChangeListener mLayoutChangedListener = new View.OnLayoutChangeListener() {
        public void onLayoutChange(View v, int left, int top2, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            if (!EditControlPopup.this.internalChanges) {
                EditControlPopup.this.internalChanges = true;
                EditControlPopup editControlPopup = EditControlPopup.this;
                int width = (int) editControlPopup.safeParseFloat(editControlPopup.mWidthEditText.getText().toString());
                if (width >= 0 && Math.abs(right - width) > 1) {
                    EditControlPopup.this.mWidthEditText.setText(String.valueOf(right - left));
                }
                EditControlPopup editControlPopup2 = EditControlPopup.this;
                int height = (int) editControlPopup2.safeParseFloat(editControlPopup2.mHeightEditText.getText().toString());
                if (height >= 0 && Math.abs(bottom - height) > 1) {
                    EditControlPopup.this.mHeightEditText.setText(String.valueOf(bottom - top2));
                }
                EditControlPopup.this.internalChanges = false;
            }
        }
    };
    private TextView mMappingTextView;
    private final int mMargin;
    protected EditText mNameEditText;
    private TextView mNameTextView;
    protected Spinner mOrientationSpinner;
    private TextView mOrientationTextView;
    protected Switch mPassthroughSwitch;
    private ConstraintLayout mRootView;
    private final DefocusableScrollView mScrollView;
    protected TextView mSelectBackgroundColor;
    protected TextView mSelectStrokeColor;
    protected String[] mSpecialArray;
    protected TextView mStrokePercentTextView;
    protected SeekBar mStrokeWidthSeekbar;
    protected Switch mSwipeableSwitch;
    protected Switch mToggleSwitch;
    protected EditText mWidthEditText;

    public EditControlPopup(Context context, ViewGroup parent) {
        DefocusableScrollView defocusableScrollView = (DefocusableScrollView) LayoutInflater.from(context).inflate(R.layout.dialog_control_button_setting, parent, false);
        this.mScrollView = defocusableScrollView;
        parent.addView(defocusableScrollView);
        this.mMargin = context.getResources().getDimensionPixelOffset(R.dimen._20sdp);
        ColorSelector colorSelector = new ColorSelector(context, parent, (ColorSelectionListener) null);
        this.mColorSelector = colorSelector;
        colorSelector.getRootView().setElevation(11.0f);
        colorSelector.getRootView().setX((float) (-context.getResources().getDimensionPixelOffset(R.dimen._230sdp)));
        ObjectAnimator duration = ObjectAnimator.ofFloat(defocusableScrollView, "x", new float[]{0.0f}).setDuration(1000);
        this.mEditPopupAnimator = duration;
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(colorSelector.getRootView(), "x", new float[]{0.0f}).setDuration(1000);
        this.mColorEditorAnimator = duration2;
        Interpolator decelerate = new AccelerateDecelerateInterpolator();
        duration.setInterpolator(decelerate);
        duration2.setInterpolator(decelerate);
        defocusableScrollView.setElevation(10.0f);
        defocusableScrollView.setX((float) (-context.getResources().getDimensionPixelOffset(R.dimen._230sdp)));
        bindLayout();
        loadAdapter();
        setupRealTimeListeners();
    }

    public void appear(boolean fromRight) {
        disappearColor();
        if (fromRight) {
            if (!this.mDisplaying || !isAtRight()) {
                this.mEditPopupAnimator.setFloatValues(new float[]{(float) Tools.currentDisplayMetrics.widthPixels, (float) ((Tools.currentDisplayMetrics.widthPixels - this.mScrollView.getWidth()) - this.mMargin)});
                this.mEditPopupAnimator.start();
            }
        } else if (!this.mDisplaying || isAtRight()) {
            this.mEditPopupAnimator.setFloatValues(new float[]{(float) (-this.mScrollView.getWidth()), (float) this.mMargin});
            this.mEditPopupAnimator.start();
        }
        this.mDisplaying = true;
    }

    public void disappear() {
        if (this.mDisplaying) {
            this.mDisplaying = false;
            if (isAtRight()) {
                this.mEditPopupAnimator.setFloatValues(new float[]{(float) ((Tools.currentDisplayMetrics.widthPixels - this.mScrollView.getWidth()) - this.mMargin), (float) Tools.currentDisplayMetrics.widthPixels});
            } else {
                this.mEditPopupAnimator.setFloatValues(new float[]{(float) this.mMargin, (float) (-this.mScrollView.getWidth())});
            }
            this.mEditPopupAnimator.start();
        }
    }

    public void appearColor(boolean fromRight, int color) {
        if (fromRight) {
            if (!this.mDisplayingColor || !isAtRight()) {
                this.mColorEditorAnimator.setFloatValues(new float[]{(float) Tools.currentDisplayMetrics.widthPixels, (float) ((Tools.currentDisplayMetrics.widthPixels - this.mScrollView.getWidth()) - this.mMargin)});
                this.mColorEditorAnimator.start();
            }
        } else if (!this.mDisplayingColor || isAtRight()) {
            this.mColorEditorAnimator.setFloatValues(new float[]{(float) (-this.mScrollView.getWidth()), (float) this.mMargin});
            this.mColorEditorAnimator.start();
        }
        this.mDisplayingColor = true;
        if (color != -1) {
            this.mColorSelector.show(color);
        }
    }

    public void disappearColor() {
        if (this.mDisplayingColor) {
            this.mDisplayingColor = false;
            if (isAtRight()) {
                this.mColorEditorAnimator.setFloatValues(new float[]{(float) ((Tools.currentDisplayMetrics.widthPixels - this.mScrollView.getWidth()) - this.mMargin), (float) Tools.currentDisplayMetrics.widthPixels});
            } else {
                this.mColorEditorAnimator.setFloatValues(new float[]{(float) this.mMargin, (float) (-this.mScrollView.getWidth())});
            }
            this.mColorEditorAnimator.start();
        }
    }

    public boolean disappearLayer() {
        if (this.mDisplayingColor) {
            disappearColor();
            return false;
        }
        disappear();
        return true;
    }

    public void adaptPanelPosition() {
        if (this.mDisplaying) {
            appear(this.mCurrentlyEditedButton.getControlView().getX() + (((float) this.mCurrentlyEditedButton.getControlView().getWidth()) / 2.0f) < ((float) Tools.currentDisplayMetrics.widthPixels) / 2.0f);
        }
    }

    public void destroy() {
        ((ViewGroup) this.mScrollView.getParent()).removeView(this.mColorSelector.getRootView());
        ((ViewGroup) this.mScrollView.getParent()).removeView(this.mScrollView);
    }

    private void loadAdapter() {
        String[] strArr;
        this.mAdapter = new ArrayAdapter<>(this.mRootView.getContext(), R.layout.item_centered_textview);
        this.mSpecialArray = ControlData.buildSpecialButtonArray();
        int i = 0;
        while (true) {
            strArr = this.mSpecialArray;
            if (i >= strArr.length) {
                break;
            }
            strArr[i] = "SPECIAL_" + this.mSpecialArray[i];
            i++;
        }
        Collections.reverse(Arrays.asList(strArr));
        this.mAdapter.addAll(this.mSpecialArray);
        this.mAdapter.addAll(EfficientAndroidLWJGLKeycode.generateKeyName());
        this.mAdapter.setDropDownViewResource(17367055);
        for (Spinner spinner : this.mKeycodeSpinners) {
            spinner.setAdapter(this.mAdapter);
        }
        ArrayAdapter<ControlDrawerData.Orientation> adapter = new ArrayAdapter<>(this.mScrollView.getContext(), 17367048);
        adapter.addAll(ControlDrawerData.getOrientations());
        adapter.setDropDownViewResource(17367055);
        this.mOrientationSpinner.setAdapter(adapter);
    }

    private void setDefaultVisibilitySetting() {
        for (int i = 0; i < this.mRootView.getChildCount(); i++) {
            this.mRootView.getChildAt(i).setVisibility(0);
        }
    }

    private boolean isAtRight() {
        return this.mScrollView.getX() > ((float) Tools.currentDisplayMetrics.widthPixels) / 2.0f;
    }

    public static void setPercentageText(TextView textView, int progress) {
        textView.setText(progress + " %");
    }

    public void loadValues(ControlData data) {
        setDefaultVisibilitySetting();
        this.mOrientationTextView.setVisibility(8);
        this.mOrientationSpinner.setVisibility(8);
        this.mNameEditText.setText(data.name);
        this.mWidthEditText.setText(String.valueOf(data.getWidth()));
        this.mHeightEditText.setText(String.valueOf(data.getHeight()));
        this.mAlphaSeekbar.setProgress((int) (data.opacity * 100.0f));
        this.mStrokeWidthSeekbar.setProgress(data.strokeWidth);
        this.mCornerRadiusSeekbar.setProgress((int) data.cornerRadius);
        setPercentageText(this.mAlphaPercentTextView, (int) (data.opacity * 100.0f));
        setPercentageText(this.mStrokePercentTextView, data.strokeWidth);
        setPercentageText(this.mCornerRadiusPercentTextView, (int) data.cornerRadius);
        this.mToggleSwitch.setChecked(data.isToggle);
        this.mPassthroughSwitch.setChecked(data.passThruEnabled);
        this.mSwipeableSwitch.setChecked(data.isSwipeable);
        for (int i = 0; i < data.keycodes.length; i++) {
            if (data.keycodes[i] < 0) {
                this.mKeycodeSpinners[i].setSelection(data.keycodes[i] + this.mSpecialArray.length);
            } else {
                this.mKeycodeSpinners[i].setSelection(EfficientAndroidLWJGLKeycode.getIndexByValue(data.keycodes[i]) + this.mSpecialArray.length);
            }
        }
    }

    public void loadValues(ControlDrawerData data) {
        loadValues(data.properties);
        this.mOrientationSpinner.setSelection(ControlDrawerData.orientationToInt(data.orientation));
        this.mMappingTextView.setVisibility(8);
        this.mKeycodeSpinners[0].setVisibility(8);
        this.mKeycodeSpinners[1].setVisibility(8);
        this.mKeycodeSpinners[2].setVisibility(8);
        this.mKeycodeSpinners[3].setVisibility(8);
        this.mOrientationTextView.setVisibility(0);
        this.mOrientationSpinner.setVisibility(0);
        this.mSwipeableSwitch.setVisibility(8);
        this.mPassthroughSwitch.setVisibility(8);
        this.mToggleSwitch.setVisibility(8);
    }

    public void loadJoystickValues(ControlData data) {
        loadValues(data);
        this.mMappingTextView.setVisibility(8);
        this.mKeycodeSpinners[0].setVisibility(8);
        this.mKeycodeSpinners[1].setVisibility(8);
        this.mKeycodeSpinners[2].setVisibility(8);
        this.mKeycodeSpinners[3].setVisibility(8);
        this.mNameTextView.setVisibility(8);
        this.mNameEditText.setVisibility(8);
        this.mCornerRadiusTextView.setVisibility(8);
        this.mCornerRadiusSeekbar.setVisibility(8);
        this.mCornerRadiusPercentTextView.setVisibility(8);
        this.mSwipeableSwitch.setVisibility(8);
        this.mPassthroughSwitch.setVisibility(8);
        this.mToggleSwitch.setVisibility(8);
    }

    private void bindLayout() {
        this.mRootView = (ConstraintLayout) this.mScrollView.findViewById(R.id.edit_layout);
        this.mNameEditText = (EditText) this.mScrollView.findViewById(R.id.editName_editText);
        this.mWidthEditText = (EditText) this.mScrollView.findViewById(R.id.editSize_editTextX);
        this.mHeightEditText = (EditText) this.mScrollView.findViewById(R.id.editSize_editTextY);
        this.mToggleSwitch = (Switch) this.mScrollView.findViewById(R.id.checkboxToggle);
        this.mPassthroughSwitch = (Switch) this.mScrollView.findViewById(R.id.checkboxPassThrough);
        this.mSwipeableSwitch = (Switch) this.mScrollView.findViewById(R.id.checkboxSwipeable);
        this.mKeycodeSpinners[0] = (Spinner) this.mScrollView.findViewById(R.id.editMapping_spinner_1);
        this.mKeycodeSpinners[1] = (Spinner) this.mScrollView.findViewById(R.id.editMapping_spinner_2);
        this.mKeycodeSpinners[2] = (Spinner) this.mScrollView.findViewById(R.id.editMapping_spinner_3);
        this.mKeycodeSpinners[3] = (Spinner) this.mScrollView.findViewById(R.id.editMapping_spinner_4);
        this.mOrientationSpinner = (Spinner) this.mScrollView.findViewById(R.id.editOrientation_spinner);
        this.mStrokeWidthSeekbar = (SeekBar) this.mScrollView.findViewById(R.id.editStrokeWidth_seekbar);
        this.mCornerRadiusSeekbar = (SeekBar) this.mScrollView.findViewById(R.id.editCornerRadius_seekbar);
        this.mAlphaSeekbar = (SeekBar) this.mScrollView.findViewById(R.id.editButtonOpacity_seekbar);
        this.mSelectBackgroundColor = (TextView) this.mScrollView.findViewById(R.id.editBackgroundColor_textView);
        this.mSelectStrokeColor = (TextView) this.mScrollView.findViewById(R.id.editStrokeColor_textView);
        this.mStrokePercentTextView = (TextView) this.mScrollView.findViewById(R.id.editStrokeWidth_textView_percent);
        this.mAlphaPercentTextView = (TextView) this.mScrollView.findViewById(R.id.editButtonOpacity_textView_percent);
        this.mCornerRadiusPercentTextView = (TextView) this.mScrollView.findViewById(R.id.editCornerRadius_textView_percent);
        this.mMappingTextView = (TextView) this.mScrollView.findViewById(R.id.editMapping_textView);
        this.mOrientationTextView = (TextView) this.mScrollView.findViewById(R.id.editOrientation_textView);
        this.mNameTextView = (TextView) this.mScrollView.findViewById(R.id.editName_textView);
        this.mCornerRadiusTextView = (TextView) this.mScrollView.findViewById(R.id.editCornerRadius_textView);
    }

    public void setupRealTimeListeners() {
        this.mNameEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (!EditControlPopup.this.internalChanges) {
                    EditControlPopup.this.mCurrentlyEditedButton.getProperties().name = s.toString();
                    EditControlPopup.this.mCurrentlyEditedButton.setProperties(EditControlPopup.this.mCurrentlyEditedButton.getProperties(), false);
                }
            }
        });
        this.mWidthEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (!EditControlPopup.this.internalChanges) {
                    float width = EditControlPopup.this.safeParseFloat(s.toString());
                    if (width >= 0.0f) {
                        EditControlPopup.this.mCurrentlyEditedButton.getProperties().setWidth(width);
                        EditControlPopup.this.mCurrentlyEditedButton.updateProperties();
                    }
                }
            }
        });
        this.mHeightEditText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                if (!EditControlPopup.this.internalChanges) {
                    float height = EditControlPopup.this.safeParseFloat(s.toString());
                    if (height >= 0.0f) {
                        EditControlPopup.this.mCurrentlyEditedButton.getProperties().setHeight(height);
                        EditControlPopup.this.mCurrentlyEditedButton.updateProperties();
                    }
                }
            }
        });
        this.mSwipeableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditControlPopup.this.lambda$setupRealTimeListeners$0$EditControlPopup(compoundButton, z);
            }
        });
        this.mToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditControlPopup.this.lambda$setupRealTimeListeners$1$EditControlPopup(compoundButton, z);
            }
        });
        this.mPassthroughSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                EditControlPopup.this.lambda$setupRealTimeListeners$2$EditControlPopup(compoundButton, z);
            }
        });
        this.mAlphaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!EditControlPopup.this.internalChanges) {
                    EditControlPopup.this.mCurrentlyEditedButton.getProperties().opacity = ((float) EditControlPopup.this.mAlphaSeekbar.getProgress()) / 100.0f;
                    EditControlPopup.this.mCurrentlyEditedButton.getControlView().setAlpha(((float) EditControlPopup.this.mAlphaSeekbar.getProgress()) / 100.0f);
                    EditControlPopup.setPercentageText(EditControlPopup.this.mAlphaPercentTextView, progress);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mStrokeWidthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!EditControlPopup.this.internalChanges) {
                    EditControlPopup.this.mCurrentlyEditedButton.getProperties().strokeWidth = EditControlPopup.this.mStrokeWidthSeekbar.getProgress();
                    EditControlPopup.this.mCurrentlyEditedButton.setBackground();
                    EditControlPopup.setPercentageText(EditControlPopup.this.mStrokePercentTextView, progress);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mCornerRadiusSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!EditControlPopup.this.internalChanges) {
                    EditControlPopup.this.mCurrentlyEditedButton.getProperties().cornerRadius = (float) EditControlPopup.this.mCornerRadiusSeekbar.getProgress();
                    EditControlPopup.this.mCurrentlyEditedButton.setBackground();
                    EditControlPopup.setPercentageText(EditControlPopup.this.mCornerRadiusPercentTextView, progress);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        int i = 0;
        while (true) {
            Spinner[] spinnerArr = this.mKeycodeSpinners;
            if (i < spinnerArr.length) {
                final int finalI = i;
                spinnerArr[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        if (position < EditControlPopup.this.mSpecialArray.length) {
                            EditControlPopup.this.mCurrentlyEditedButton.getProperties().keycodes[finalI] = EditControlPopup.this.mKeycodeSpinners[finalI].getSelectedItemPosition() - EditControlPopup.this.mSpecialArray.length;
                        } else {
                            EditControlPopup.this.mCurrentlyEditedButton.getProperties().keycodes[finalI] = EfficientAndroidLWJGLKeycode.getValueByIndex(EditControlPopup.this.mKeycodeSpinners[finalI].getSelectedItemPosition() - EditControlPopup.this.mSpecialArray.length);
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                i++;
            } else {
                this.mOrientationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        if (EditControlPopup.this.mCurrentlyEditedButton instanceof ControlDrawer) {
                            ((ControlDrawer) EditControlPopup.this.mCurrentlyEditedButton).drawerData.orientation = ControlDrawerData.intToOrientation(EditControlPopup.this.mOrientationSpinner.getSelectedItemPosition());
                            ((ControlDrawer) EditControlPopup.this.mCurrentlyEditedButton).syncButtons();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                this.mSelectStrokeColor.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        EditControlPopup.this.lambda$setupRealTimeListeners$4$EditControlPopup(view);
                    }
                });
                this.mSelectBackgroundColor.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View view) {
                        EditControlPopup.this.lambda$setupRealTimeListeners$6$EditControlPopup(view);
                    }
                });
                return;
            }
        }
    }

    public /* synthetic */ void lambda$setupRealTimeListeners$0$EditControlPopup(CompoundButton buttonView, boolean isChecked) {
        if (!this.internalChanges) {
            this.mCurrentlyEditedButton.getProperties().isSwipeable = isChecked;
        }
    }

    public /* synthetic */ void lambda$setupRealTimeListeners$1$EditControlPopup(CompoundButton buttonView, boolean isChecked) {
        if (!this.internalChanges) {
            this.mCurrentlyEditedButton.getProperties().isToggle = isChecked;
        }
    }

    public /* synthetic */ void lambda$setupRealTimeListeners$2$EditControlPopup(CompoundButton buttonView, boolean isChecked) {
        if (!this.internalChanges) {
            this.mCurrentlyEditedButton.getProperties().passThruEnabled = isChecked;
        }
    }

    public /* synthetic */ void lambda$setupRealTimeListeners$4$EditControlPopup(View v) {
        this.mColorSelector.setAlphaEnabled(false);
        this.mColorSelector.setColorSelectionListener(new ColorSelectionListener() {
            public final void onColorSelected(int i) {
                EditControlPopup.this.lambda$null$3$EditControlPopup(i);
            }
        });
        appearColor(isAtRight(), this.mCurrentlyEditedButton.getProperties().strokeColor);
    }

    public /* synthetic */ void lambda$null$3$EditControlPopup(int color) {
        this.mCurrentlyEditedButton.getProperties().strokeColor = color;
        this.mCurrentlyEditedButton.setBackground();
    }

    public /* synthetic */ void lambda$setupRealTimeListeners$6$EditControlPopup(View v) {
        this.mColorSelector.setAlphaEnabled(true);
        this.mColorSelector.setColorSelectionListener(new ColorSelectionListener() {
            public final void onColorSelected(int i) {
                EditControlPopup.this.lambda$null$5$EditControlPopup(i);
            }
        });
        appearColor(isAtRight(), this.mCurrentlyEditedButton.getProperties().bgColor);
    }

    public /* synthetic */ void lambda$null$5$EditControlPopup(int color) {
        this.mCurrentlyEditedButton.getProperties().bgColor = color;
        this.mCurrentlyEditedButton.setBackground();
    }

    /* access modifiers changed from: private */
    public float safeParseFloat(String string) {
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            Log.e("EditControlPopup", e.toString());
            return -1.0f;
        }
    }

    public void setCurrentlyEditedButton(ControlInterface button) {
        ControlInterface controlInterface = this.mCurrentlyEditedButton;
        if (controlInterface != null) {
            controlInterface.getControlView().removeOnLayoutChangeListener(this.mLayoutChangedListener);
        }
        this.mCurrentlyEditedButton = button;
        button.getControlView().addOnLayoutChangeListener(this.mLayoutChangedListener);
    }
}
