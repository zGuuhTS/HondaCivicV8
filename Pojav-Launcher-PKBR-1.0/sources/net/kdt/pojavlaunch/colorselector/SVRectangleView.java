package net.kdt.pojavlaunch.colorselector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import net.kdt.pojavlaunch.Tools;

public class SVRectangleView extends View {
    private final Paint mColorPaint;
    private float mFingerPosX;
    private float mFingerPosY;
    private float mHeightInverted;
    private final Paint mPointerPaint;
    private final float mPointerSize = Tools.dpToPx(6.0f);
    RectangleSelectionListener mRectSelectionListener;
    private Bitmap mSvRectangle;
    private RectF mViewSize;
    private float mWidthInverted;

    public SVRectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Paint paint = new Paint();
        this.mColorPaint = paint;
        Paint paint2 = new Paint();
        this.mPointerPaint = paint2;
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.FILL);
        paint2.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint2.setStrokeWidth(Tools.dpToPx(3.0f));
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float f = this.mWidthInverted * x;
        this.mFingerPosX = f;
        float f2 = this.mHeightInverted * y;
        this.mFingerPosY = f2;
        if (f < 0.0f) {
            this.mFingerPosX = 0.0f;
        } else if (f > 1.0f) {
            this.mFingerPosX = 1.0f;
        }
        if (f2 < 0.0f) {
            this.mFingerPosY = 0.0f;
        } else if (f2 > 1.0f) {
            this.mFingerPosY = 1.0f;
        }
        RectangleSelectionListener rectangleSelectionListener = this.mRectSelectionListener;
        if (rectangleSelectionListener != null) {
            rectangleSelectionListener.onLuminosityIntensityChanged(this.mFingerPosY, this.mFingerPosX);
        }
        invalidate();
        return true;
    }

    public void setLuminosityIntensity(float luminosity, float intensity) {
        this.mFingerPosX = intensity;
        this.mFingerPosY = luminosity;
        invalidate();
    }

    public void setColor(int color, boolean invalidate) {
        this.mColorPaint.setColor(color);
        if (invalidate) {
            invalidate();
        }
    }

    public void setRectSelectionListener(RectangleSelectionListener listener) {
        this.mRectSelectionListener = listener;
    }

    /* access modifiers changed from: protected */
    public void drawPointer(Canvas canvas, float x, float y) {
        float f = this.mPointerSize;
        Canvas canvas2 = canvas;
        float f2 = y;
        float f3 = y;
        canvas2.drawLine((f * 2.0f) + x, f2, f + x, f3, this.mPointerPaint);
        float f4 = this.mPointerSize;
        canvas2.drawLine(x - (f4 * 2.0f), f2, x - f4, f3, this.mPointerPaint);
        float f5 = this.mPointerSize;
        canvas2.drawLine(x, (f5 * 2.0f) + y, x, f5 + y, this.mPointerPaint);
        float f6 = this.mPointerSize;
        canvas.drawLine(x, y - (2.0f * f6), x, y - f6, this.mPointerPaint);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(this.mViewSize, this.mColorPaint);
        canvas.drawBitmap(this.mSvRectangle, 0.0f, 0.0f, (Paint) null);
        drawPointer(canvas, this.mViewSize.right * this.mFingerPosX, this.mViewSize.bottom * this.mFingerPosY);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int old_w, int old_h) {
        RectF rectF = new RectF(0.0f, 0.0f, (float) w, (float) h);
        this.mViewSize = rectF;
        this.mWidthInverted = 1.0f / rectF.right;
        this.mHeightInverted = 1.0f / this.mViewSize.bottom;
        if (w > 0 && h > 0) {
            regenerateRectangle();
        }
    }

    /* access modifiers changed from: protected */
    public void regenerateRectangle() {
        int w = getWidth();
        int h = getHeight();
        Bitmap bitmap = this.mSvRectangle;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.mSvRectangle = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Paint rectPaint = new Paint();
        Canvas canvas = new Canvas(this.mSvRectangle);
        float h2f = ((float) h) / 2.0f;
        float w2f = ((float) w) / 2.0f;
        rectPaint.setShader(new LinearGradient(0.0f, h2f, (float) w, h2f, -1, 0, Shader.TileMode.CLAMP));
        canvas.drawRect(this.mViewSize, rectPaint);
        rectPaint.setShader(new LinearGradient(w2f, 0.0f, w2f, (float) h, ViewCompat.MEASURED_STATE_MASK, 0, Shader.TileMode.CLAMP));
        canvas.drawRect(this.mViewSize, rectPaint);
    }
}
