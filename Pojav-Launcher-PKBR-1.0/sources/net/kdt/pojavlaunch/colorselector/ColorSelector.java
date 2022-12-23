package net.kdt.pojavlaunch.colorselector;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.internal.view.SupportMenu;
import com.p000br.pixelmonbrasil.debug.R;

public class ColorSelector implements HueSelectionListener, RectangleSelectionListener, AlphaSelectionListener, TextWatcher {
    private static final int ALPHA_MASK = 16777215;
    private boolean mAlphaEnabled = true;
    private int mAlphaSelected = 255;
    private final AlphaView mAlphaView;
    private ColorSelectionListener mColorSelectionListener;
    private final ColorSideBySideView mColorView;
    private final float[] mHsvSelected = {360.0f, 1.0f, 1.0f};
    private final float[] mHueTemplate = {0.0f, 1.0f, 1.0f};
    private final HueView mHueView;
    private final SVRectangleView mLuminosityIntensityView;
    private final View mRootView;
    private final ColorStateList mTextColors;
    private final EditText mTextView;
    private boolean mWatch = true;

    public ColorSelector(Context context, ViewGroup parent, ColorSelectionListener colorSelectionListener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_color_selector, parent, false);
        this.mRootView = inflate;
        HueView hueView = (HueView) inflate.findViewById(R.id.color_selector_hue_view);
        this.mHueView = hueView;
        SVRectangleView sVRectangleView = (SVRectangleView) inflate.findViewById(R.id.color_selector_rectangle_view);
        this.mLuminosityIntensityView = sVRectangleView;
        AlphaView alphaView = (AlphaView) inflate.findViewById(R.id.color_selector_alpha_view);
        this.mAlphaView = alphaView;
        this.mColorView = (ColorSideBySideView) inflate.findViewById(R.id.color_selector_color_view);
        EditText editText = (EditText) inflate.findViewById(R.id.color_selector_hex_edit);
        this.mTextView = editText;
        runColor(SupportMenu.CATEGORY_MASK);
        hueView.setHueSelectionListener(this);
        sVRectangleView.setRectSelectionListener(this);
        alphaView.setAlphaSelectionListener(this);
        editText.addTextChangedListener(this);
        this.mTextColors = editText.getTextColors();
        this.mColorSelectionListener = colorSelectionListener;
        parent.addView(inflate);
    }

    public View getRootView() {
        return this.mRootView;
    }

    public void show() {
        show(SupportMenu.CATEGORY_MASK);
    }

    public void show(int previousColor) {
        runColor(previousColor);
        dispatchColorChange();
    }

    public void onHueSelected(float hue) {
        float[] fArr = this.mHsvSelected;
        float[] fArr2 = this.mHueTemplate;
        fArr2[0] = hue;
        fArr[0] = hue;
        this.mLuminosityIntensityView.setColor(Color.HSVToColor(fArr2), true);
        dispatchColorChange();
    }

    public void onLuminosityIntensityChanged(float luminosity, float intensity) {
        float[] fArr = this.mHsvSelected;
        fArr[1] = intensity;
        fArr[2] = luminosity;
        dispatchColorChange();
    }

    public void onAlphaSelected(int alpha) {
        this.mAlphaSelected = alpha;
        dispatchColorChange();
    }

    public static int setAlpha(int color, int alpha) {
        return (16777215 & color) | ((alpha & 255) << 24);
    }

    /* access modifiers changed from: protected */
    public void dispatchColorChange() {
        int color = Color.HSVToColor(this.mAlphaSelected, this.mHsvSelected);
        this.mColorView.setColor(color);
        this.mWatch = false;
        this.mTextView.setText(String.format("%08X", new Object[]{Integer.valueOf(color)}));
        notifyColorSelector(color);
    }

    /* access modifiers changed from: protected */
    public void runColor(int color) {
        Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), this.mHsvSelected);
        float[] fArr = this.mHueTemplate;
        float[] fArr2 = this.mHsvSelected;
        fArr[0] = fArr2[0];
        this.mHueView.setHue(fArr2[0]);
        this.mLuminosityIntensityView.setColor(Color.HSVToColor(this.mHueTemplate), false);
        SVRectangleView sVRectangleView = this.mLuminosityIntensityView;
        float[] fArr3 = this.mHsvSelected;
        sVRectangleView.setLuminosityIntensity(fArr3[2], fArr3[1]);
        int alpha = Color.alpha(color);
        this.mAlphaSelected = alpha;
        AlphaView alphaView = this.mAlphaView;
        if (!this.mAlphaEnabled) {
            alpha = 255;
        }
        alphaView.setAlpha(alpha);
        this.mColorView.setColor(color);
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        if (this.mWatch) {
            try {
                int color = Integer.parseInt(s.toString(), 16);
                this.mTextView.setTextColor(this.mTextColors);
                runColor(color);
            } catch (NumberFormatException e) {
                this.mTextView.setTextColor(SupportMenu.CATEGORY_MASK);
            }
        } else {
            this.mWatch = true;
        }
    }

    public void setColorSelectionListener(ColorSelectionListener listener) {
        this.mColorSelectionListener = listener;
    }

    public void setAlphaEnabled(boolean alphaEnabled) {
        this.mAlphaEnabled = alphaEnabled;
        this.mAlphaView.setVisibility(alphaEnabled ? 0 : 8);
    }

    private void notifyColorSelector(int color) {
        ColorSelectionListener colorSelectionListener = this.mColorSelectionListener;
        if (colorSelectionListener != null) {
            colorSelectionListener.onColorSelected(color);
        }
    }
}
