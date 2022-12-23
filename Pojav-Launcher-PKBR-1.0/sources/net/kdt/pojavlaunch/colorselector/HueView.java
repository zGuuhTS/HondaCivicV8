package net.kdt.pojavlaunch.colorselector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import net.kdt.pojavlaunch.Tools;

public class HueView extends View {
    private final Paint blackPaint;
    private Bitmap mGamma;
    private float mHeight;
    private float mHeightHueRatio;
    private float mHueHeightRatio;
    private HueSelectionListener mHueSelectionListener;
    private float mSelectionHue;
    private float mWidth;
    private float mWidthThird;

    public HueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Paint paint = new Paint();
        this.blackPaint = paint;
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStrokeWidth(Tools.dpToPx(3.0f));
    }

    public void setHueSelectionListener(HueSelectionListener listener) {
        this.mHueSelectionListener = listener;
    }

    public void setHue(float hue) {
        this.mSelectionHue = hue;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mSelectionHue = event.getY() * this.mHeightHueRatio;
        invalidate();
        HueSelectionListener hueSelectionListener = this.mHueSelectionListener;
        if (hueSelectionListener == null) {
            return true;
        }
        hueSelectionListener.onHueSelected(this.mSelectionHue);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.mGamma, 0.0f, 0.0f, (Paint) null);
        float linePos = this.mSelectionHue * this.mHueHeightRatio;
        float f = linePos;
        float f2 = linePos;
        canvas.drawLine(0.0f, f, this.mWidthThird, f2, this.blackPaint);
        canvas.drawLine(this.mWidthThird * 2.0f, f, this.mWidth, f2, this.blackPaint);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int old_w, int old_h) {
        float f = (float) w;
        this.mWidth = f;
        this.mHeight = (float) h;
        this.mWidthThird = f / 3.0f;
        regenerateGammaBitmap();
    }

    /* access modifiers changed from: protected */
    public void regenerateGammaBitmap() {
        Bitmap bitmap = this.mGamma;
        if (bitmap != null) {
            bitmap.recycle();
        }
        this.mGamma = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(this.mGamma);
        float f = this.mHeight;
        this.mHeightHueRatio = 360.0f / f;
        this.mHueHeightRatio = f / 360.0f;
        float[] hsvFiller = {0.0f, 1.0f, 1.0f};
        for (float i = 0.0f; i < this.mHeight; i += 1.0f) {
            hsvFiller[0] = this.mHeightHueRatio * i;
            paint.setColor(Color.HSVToColor(hsvFiller));
            canvas.drawLine(0.0f, i, this.mWidth, i, paint);
        }
    }
}
