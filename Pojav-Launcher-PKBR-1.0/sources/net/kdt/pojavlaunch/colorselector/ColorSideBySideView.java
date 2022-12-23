package net.kdt.pojavlaunch.colorselector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import top.defaults.checkerboarddrawable.CheckerboardDrawable;

public class ColorSideBySideView extends View {
    private int mAlphaColor;
    private final CheckerboardDrawable mCheckerboardDrawable = CheckerboardDrawable.create();
    private int mColor;
    private float mHalfHeight;
    private float mHeight;
    private final Paint mPaint = new Paint();
    private float mWidth;

    public ColorSideBySideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setColor(int color) {
        this.mColor = ColorSelector.setAlpha(color, 255);
        this.mAlphaColor = color;
        invalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mCheckerboardDrawable.draw(canvas);
        this.mPaint.setColor(this.mColor);
        Canvas canvas2 = canvas;
        canvas2.drawRect(0.0f, 0.0f, this.mWidth, this.mHalfHeight, this.mPaint);
        this.mPaint.setColor(this.mAlphaColor);
        canvas2.drawRect(0.0f, this.mHalfHeight, this.mWidth, this.mHeight, this.mPaint);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int old_w, int old_h) {
        this.mHalfHeight = ((float) h) / 2.0f;
        this.mWidth = (float) w;
        this.mHeight = (float) h;
    }
}
