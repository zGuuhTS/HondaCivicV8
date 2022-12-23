package net.kdt.pojavlaunch.customcontrols.handleview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.p000br.pixelmonbrasil.debug.R;

public class DrawerPullButton extends View {
    private VectorDrawableCompat mDrawable;
    private final Paint mPaint = new Paint();

    public DrawerPullButton(Context context) {
        super(context);
        init();
    }

    public DrawerPullButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mDrawable = VectorDrawableCompat.create(getContext().getResources(), R.drawable.ic_sharp_settings_24, (Resources.Theme) null);
        setAlpha(0.33f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawArc(0.0f, (float) (-getHeight()), (float) getWidth(), (float) getHeight(), 0.0f, 180.0f, true, this.mPaint);
        this.mPaint.setColor(-1);
        this.mDrawable.setBounds(0, 0, canvas.getHeight(), canvas.getHeight());
        canvas.save();
        canvas.translate((float) ((canvas.getWidth() - canvas.getHeight()) / 2), 0.0f);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }
}
