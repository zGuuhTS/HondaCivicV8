package com.kdt.mcgui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import androidx.core.content.res.ResourcesCompat;
import com.p000br.pixelmonbrasil.debug.R;

public class TextProgressBar extends ProgressBar {
    private String mText = "Aguarde";
    private Paint mTextPaint;

    public TextProgressBar(Context context) {
        super(context, (AttributeSet) null, 16842872);
        init();
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 16842872);
        init();
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 16842872);
        init();
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, 16842872, defStyleRes);
        init();
    }

    private void init() {
        setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.view_text_progressbar, (Resources.Theme) null));
        setProgress(0);
        Paint paint = new Paint();
        this.mTextPaint = paint;
        paint.setColor(-1);
        this.mTextPaint.setFlags(32);
    }

    /* access modifiers changed from: protected */
    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mTextPaint.setTextSize((float) (((double) ((getHeight() - getPaddingBottom()) - getPaddingTop())) * 0.55d));
        canvas.drawText(this.mText, (float) ((int) Math.min((float) ((getProgress() * getWidth()) / getMax()), ((float) getWidth()) - this.mTextPaint.measureText(this.mText))), (float) ((int) (((float) (getHeight() / 2)) - ((this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f))), this.mTextPaint);
    }

    public final void setText(int resid) {
        setText(getContext().getResources().getText(resid).toString());
    }

    public final void setText(String text) {
        this.mText = text;
        invalidate();
    }
}
