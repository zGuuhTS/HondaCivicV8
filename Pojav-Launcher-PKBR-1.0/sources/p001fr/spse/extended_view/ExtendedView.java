package p001fr.spse.extended_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/* renamed from: fr.spse.extended_view.ExtendedView */
public interface ExtendedView {
    Drawable[] getCompoundsDrawables();

    ExtendedViewData getExtendedViewData();

    void setCompoundsDrawables(Drawable[] drawableArr);

    void initExtendedView(Context context, AttributeSet set) {
        getAttributes(context, set);
        postProcessDrawables();
    }

    void getAttributes(Context context, AttributeSet set) {
        if (set != null) {
            TypedArray values = context.getTheme().obtainStyledAttributes(set, C0143R.styleable.ExtendedView, 0, 0);
            try {
                getExtendedViewData().setIntegerCompounds(new boolean[]{values.getBoolean(C0143R.styleable.ExtendedView_drawableStartIntegerScaling, false), values.getBoolean(C0143R.styleable.ExtendedView_drawableTopIntegerScaling, false), values.getBoolean(C0143R.styleable.ExtendedView_drawableEndIntegerScaling, false), values.getBoolean(C0143R.styleable.ExtendedView_drawableBottomIntegerScaling, false)});
                getExtendedViewData().setSizeCompounds(new int[]{values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableStartSize, -1), values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableTopSize, -1), values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableEndSize, -1), values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableBottomSize, -1)});
                getExtendedViewData().setPaddingCompounds(new int[]{values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableStartPadding, -1), values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableTopPadding, -1), values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableEndPadding, -1), values.getDimensionPixelSize(C0143R.styleable.ExtendedView_drawableBottomPadding, -1)});
            } finally {
                values.recycle();
            }
        }
    }

    void postProcessDrawables() {
        if (getExtendedViewData() != null) {
            makeDrawablesIntegerScaled();
            scaleDrawablesToDesiredSize();
        }
    }

    void scaleDrawablesToDesiredSize() {
        Drawable[] drawables = getCompoundsDrawables();
        int[] sizeCompounds = getExtendedViewData().getSizeCompounds();
        int index = -1;
        boolean shouldUpdate = false;
        Rect bounds = new Rect();
        for (Drawable drawable : drawables) {
            index++;
            if (!(sizeCompounds[index] == -1 || drawable == null)) {
                drawable.copyBounds(bounds);
                if (!(bounds.right == sizeCompounds[index] || bounds.bottom == sizeCompounds[index])) {
                    bounds.right = Math.max(bounds.right, 1);
                    bounds.bottom = Math.max(bounds.bottom, 1);
                    if (bounds.right == bounds.bottom) {
                        bounds.bottom = sizeCompounds[index];
                        bounds.right = sizeCompounds[index];
                    } else if (bounds.right > bounds.bottom) {
                        bounds.bottom = (bounds.bottom * sizeCompounds[index]) / bounds.right;
                        bounds.right = sizeCompounds[index];
                    } else {
                        bounds.right = (bounds.right * sizeCompounds[index]) / bounds.bottom;
                        bounds.bottom = sizeCompounds[index];
                    }
                    drawable.setBounds(0, 0, bounds.right, bounds.bottom);
                    shouldUpdate = true;
                }
            }
        }
        if (shouldUpdate) {
            setCompoundsDrawables(drawables);
        }
    }

    void makeDrawablesIntegerScaled() {
        int index = 0;
        boolean[] integerCompounds = getExtendedViewData().getIntegerCompounds();
        for (Drawable compoundDrawable : getCompoundsDrawables()) {
            if (integerCompounds[index]) {
                makeDrawableIntegerScaled(compoundDrawable);
            }
            index++;
        }
    }

    void makeDrawableIntegerScaled(Drawable drawable) {
        if (drawable != null) {
            drawable.setDither(false);
            drawable.setFilterBitmap(false);
        }
    }
}
