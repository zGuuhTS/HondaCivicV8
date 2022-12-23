package p001fr.spse.extended_view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/* renamed from: fr.spse.extended_view.ExtendedButton */
public class ExtendedButton extends Button implements ExtendedView {
    private ExtendedViewData mExtendedViewData = new ExtendedViewData(this);

    public ExtendedButton(Context context) {
        super(context);
        initExtendedView(context, (AttributeSet) null);
    }

    public ExtendedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initExtendedView(context, attrs);
    }

    public ExtendedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initExtendedView(context, attrs);
    }

    public void setCompoundDrawables(Drawable left, Drawable top2, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesRelative(left, top2, right, bottom);
        postProcessDrawables();
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top2, int right, int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top2, right, bottom);
        postProcessDrawables();
    }

    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top2, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top2, right, bottom);
        postProcessDrawables();
    }

    public void setCompoundDrawablesRelative(Drawable start, Drawable top2, Drawable end, Drawable bottom) {
        super.setCompoundDrawablesRelative(start, top2, end, bottom);
        postProcessDrawables();
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top2, int end, int bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top2, end, bottom);
        postProcessDrawables();
    }

    public void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable start, Drawable top2, Drawable end, Drawable bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top2, end, bottom);
        postProcessDrawables();
    }

    public ExtendedViewData getExtendedViewData() {
        return this.mExtendedViewData;
    }

    public Drawable[] getCompoundsDrawables() {
        return getCompoundDrawablesRelative();
    }

    public void setCompoundsDrawables(Drawable[] drawables) {
        setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3]);
    }
}
