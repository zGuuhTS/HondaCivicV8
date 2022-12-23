package com.kdt;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class DefocusableScrollView extends ScrollView {
    private boolean mKeepFocusing = false;

    public DefocusableScrollView(Context context) {
        super(context);
    }

    public DefocusableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefocusableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DefocusableScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setKeepFocusing(boolean shouldKeepFocusing) {
        this.mKeepFocusing = shouldKeepFocusing;
    }

    public boolean isKeepFocusing() {
        return this.mKeepFocusing;
    }

    /* access modifiers changed from: protected */
    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (!this.mKeepFocusing) {
            return 0;
        }
        return super.computeScrollDeltaToGetChildRectOnScreen(rect);
    }
}
