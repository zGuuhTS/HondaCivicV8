package net.kdt.pojavlaunch.customcontrols.handleview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.core.content.res.ResourcesCompat;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface;

public class ControlHandleView extends View {
    private final Drawable mDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_view_handle, getContext().getTheme());
    private final ViewTreeObserver.OnPreDrawListener mPositionListener = new ViewTreeObserver.OnPreDrawListener() {
        public boolean onPreDraw() {
            if (ControlHandleView.this.mView == null || !ControlHandleView.this.mView.getControlView().isShown()) {
                ControlHandleView.this.hide();
                return true;
            }
            ControlHandleView controlHandleView = ControlHandleView.this;
            controlHandleView.setX(controlHandleView.mView.getControlView().getX() + ((float) ControlHandleView.this.mView.getControlView().getWidth()));
            ControlHandleView controlHandleView2 = ControlHandleView.this;
            controlHandleView2.setY(controlHandleView2.mView.getControlView().getY() + ((float) ControlHandleView.this.mView.getControlView().getHeight()));
            return true;
        }
    };
    /* access modifiers changed from: private */
    public ControlInterface mView;
    private float mXOffset;
    private float mYOffset;

    public ControlHandleView(Context context) {
        super(context);
        init();
    }

    public ControlHandleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        int size = getResources().getDimensionPixelOffset(R.dimen._22sdp);
        this.mDrawable.setBounds(0, 0, size, size);
        setLayoutParams(new ViewGroup.LayoutParams(size, size));
        setBackground(this.mDrawable);
        setTranslationZ(10.5f);
    }

    public void setControlButton(ControlInterface controlInterface) {
        ControlInterface controlInterface2 = this.mView;
        if (controlInterface2 != null) {
            controlInterface2.getControlView().getViewTreeObserver().removeOnPreDrawListener(this.mPositionListener);
        }
        setVisibility(0);
        this.mView = controlInterface;
        controlInterface.getControlView().getViewTreeObserver().addOnPreDrawListener(this.mPositionListener);
        setX(controlInterface.getControlView().getX() + ((float) controlInterface.getControlView().getWidth()));
        setY(controlInterface.getControlView().getY() + ((float) controlInterface.getControlView().getHeight()));
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                this.mXOffset = event.getX();
                this.mYOffset = event.getY();
                return true;
            case 2:
                setX((getX() + event.getX()) - this.mXOffset);
                setY((getY() + event.getY()) - this.mYOffset);
                System.out.println(getX() - this.mView.getControlView().getX());
                System.out.println(getY() - this.mView.getControlView().getY());
                this.mView.getProperties().setWidth(getX() - this.mView.getControlView().getX());
                this.mView.getProperties().setHeight(getY() - this.mView.getControlView().getY());
                this.mView.regenerateDynamicCoordinates();
                return true;
            default:
                return true;
        }
    }

    public void hide() {
        ControlInterface controlInterface = this.mView;
        if (controlInterface != null) {
            controlInterface.getControlView().getViewTreeObserver().removeOnPreDrawListener(this.mPositionListener);
        }
        setVisibility(8);
    }
}
