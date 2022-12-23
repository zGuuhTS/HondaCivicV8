package net.kdt.pojavlaunch.colorselector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import net.kdt.pojavlaunch.Tools;
import top.defaults.checkerboarddrawable.CheckerboardDrawable;

public class AlphaView extends View {
    private float mAlphaDiv;
    private AlphaSelectionListener mAlphaSelectionListener;
    private final Paint mBlackPaint;
    private final Drawable mCheckerboardDrawable = CheckerboardDrawable.create();
    private float mScreenDiv;
    private int mSelectedAlpha;
    private final Paint mShaderPaint = new Paint();
    private final RectF mViewSize = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
    private float mWidthThird;

    public AlphaView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        Paint paint = new Paint();
        this.mBlackPaint = paint;
        paint.setStrokeWidth(Tools.dpToPx(3.0f));
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
    }

    public void setAlphaSelectionListener(AlphaSelectionListener alphaSelectionListener) {
        this.mAlphaSelectionListener = alphaSelectionListener;
    }

    public void setAlpha(int alpha) {
        this.mSelectedAlpha = alpha;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int clamp = (int) MathUtils.clamp(this.mAlphaDiv * event.getY(), 0.0f, 255.0f);
        this.mSelectedAlpha = clamp;
        AlphaSelectionListener alphaSelectionListener = this.mAlphaSelectionListener;
        if (alphaSelectionListener != null) {
            alphaSelectionListener.onAlphaSelected(clamp);
        }
        invalidate();
        return true;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int old_w, int old_h) {
        this.mViewSize.right = (float) w;
        this.mViewSize.bottom = (float) h;
        this.mShaderPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) h, 0, -1, Shader.TileMode.REPEAT));
        this.mAlphaDiv = 255.0f / this.mViewSize.bottom;
        this.mScreenDiv = this.mViewSize.bottom / 255.0f;
        this.mWidthThird = this.mViewSize.right / 3.0f;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mCheckerboardDrawable.draw(canvas);
        canvas.drawRect(this.mViewSize, this.mShaderPaint);
        float linePos = ((float) this.mSelectedAlpha) * this.mScreenDiv;
        float f = linePos;
        float f2 = linePos;
        canvas.drawLine(0.0f, f, this.mWidthThird, f2, this.mBlackPaint);
        canvas.drawLine(this.mWidthThird * 2.0f, f, (float) getRight(), f2, this.mBlackPaint);
    }
}
