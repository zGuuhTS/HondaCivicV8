package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.MotionWidget;

public class ViewState {
    public int bottom;
    public int left;
    public int right;
    public float rotation;

    /* renamed from: top  reason: collision with root package name */
    public int f210top;

    public void getState(MotionWidget v) {
        this.left = v.getLeft();
        this.f210top = v.getTop();
        this.right = v.getRight();
        this.bottom = v.getBottom();
        this.rotation = (float) ((int) v.getRotationZ());
    }

    public int width() {
        return this.right - this.left;
    }

    public int height() {
        return this.bottom - this.f210top;
    }
}
