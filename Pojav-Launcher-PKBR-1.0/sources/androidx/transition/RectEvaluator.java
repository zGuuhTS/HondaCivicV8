package androidx.transition;

import android.animation.TypeEvaluator;
import android.graphics.Rect;

class RectEvaluator implements TypeEvaluator<Rect> {
    private Rect mRect;

    RectEvaluator() {
    }

    RectEvaluator(Rect reuseRect) {
        this.mRect = reuseRect;
    }

    public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
        int left = startValue.left + ((int) (((float) (endValue.left - startValue.left)) * fraction));
        int top2 = startValue.top + ((int) (((float) (endValue.top - startValue.top)) * fraction));
        int right = startValue.right + ((int) (((float) (endValue.right - startValue.right)) * fraction));
        int bottom = startValue.bottom + ((int) (((float) (endValue.bottom - startValue.bottom)) * fraction));
        Rect rect = this.mRect;
        if (rect == null) {
            return new Rect(left, top2, right, bottom);
        }
        rect.set(left, top2, right, bottom);
        return this.mRect;
    }
}
