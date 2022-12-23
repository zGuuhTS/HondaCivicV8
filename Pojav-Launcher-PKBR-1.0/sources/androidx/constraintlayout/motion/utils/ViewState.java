package androidx.constraintlayout.motion.utils;

import android.view.View;

public class ViewState {
    public int bottom;
    public int left;
    public int right;
    public float rotation;

    /* renamed from: top  reason: collision with root package name */
    public int f213top;

    public void getState(View v) {
        this.left = v.getLeft();
        this.f213top = v.getTop();
        this.right = v.getRight();
        this.bottom = v.getBottom();
        this.rotation = v.getRotation();
    }

    public int width() {
        return this.right - this.left;
    }

    public int height() {
        return this.bottom - this.f213top;
    }
}
