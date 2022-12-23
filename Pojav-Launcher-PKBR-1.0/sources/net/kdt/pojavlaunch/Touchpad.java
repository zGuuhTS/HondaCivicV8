package net.kdt.pojavlaunch;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import org.lwjgl.glfw.CallbackBridge;

public class Touchpad extends FrameLayout implements GrabListener {
    private int mCurrentPointerID;
    private boolean mDisplayState;
    private final ImageView mMousePointerImageView;
    private float mPrevX;
    private float mPrevY;
    private final float mScaleFactor;
    private float mScrollLastInitialX;
    private float mScrollLastInitialY;
    private final GestureDetector mSingleTapDetector;

    public Touchpad(Context context) {
        this(context, (AttributeSet) null);
    }

    public Touchpad(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMousePointerImageView = new ImageView(getContext());
        this.mSingleTapDetector = new GestureDetector(getContext(), new SingleTapConfirm());
        this.mScaleFactor = ((float) LauncherPreferences.DEFAULT_PREF.getInt("resolutionRatio", 100)) / 100.0f;
        this.mCurrentPointerID = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        init();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (CallbackBridge.isGrabbing()) {
            disable();
            return false;
        }
        int action = event.getActionMasked();
        float x = event.getX();
        float y = event.getY();
        float mouseX = this.mMousePointerImageView.getX();
        float mouseY = this.mMousePointerImageView.getY();
        if (this.mSingleTapDetector.onTouchEvent(event)) {
            CallbackBridge.mouseX = this.mScaleFactor * mouseX;
            CallbackBridge.mouseY = this.mScaleFactor * mouseY;
            CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
            CallbackBridge.sendMouseKeycode(0);
            return true;
        }
        switch (action) {
            case 0:
                this.mPrevX = x;
                this.mPrevY = y;
                this.mCurrentPointerID = event.getPointerId(0);
                break;
            case 1:
                this.mPrevX = x;
                this.mPrevY = y;
                this.mCurrentPointerID = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                break;
            case 2:
                if (!LauncherPreferences.PREF_DISABLE_GESTURES && !CallbackBridge.isGrabbing() && event.getPointerCount() >= 2) {
                    int hScroll = ((int) (event.getX() - this.mScrollLastInitialX)) / MinecraftGLSurface.FINGER_SCROLL_THRESHOLD;
                    int vScroll = ((int) (event.getY() - this.mScrollLastInitialY)) / MinecraftGLSurface.FINGER_SCROLL_THRESHOLD;
                    if (!(vScroll == 0 && hScroll == 0)) {
                        CallbackBridge.sendScroll((double) hScroll, (double) vScroll);
                        this.mScrollLastInitialX = event.getX();
                        this.mScrollLastInitialY = event.getY();
                        break;
                    }
                } else {
                    if (this.mCurrentPointerID == event.getPointerId(0)) {
                        placeMouseAt(Math.max(0.0f, Math.min((float) Tools.currentDisplayMetrics.widthPixels, ((x - this.mPrevX) * LauncherPreferences.PREF_MOUSESPEED) + mouseX)), Math.max(0.0f, Math.min((float) Tools.currentDisplayMetrics.heightPixels, ((y - this.mPrevY) * LauncherPreferences.PREF_MOUSESPEED) + mouseY)));
                        CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
                    } else {
                        this.mCurrentPointerID = event.getPointerId(0);
                    }
                    this.mPrevX = x;
                    this.mPrevY = y;
                    break;
                }
                break;
            case 5:
                this.mScrollLastInitialX = event.getX();
                this.mScrollLastInitialY = event.getY();
                break;
        }
        CallbackBridge.DEBUG_STRING.setLength(0);
        return true;
    }

    public void enable() {
        setVisibility(0);
        placeMouseAt((float) (Tools.currentDisplayMetrics.widthPixels / 2), (float) (Tools.currentDisplayMetrics.heightPixels / 2));
    }

    public void disable() {
        setVisibility(8);
    }

    public boolean switchState() {
        this.mDisplayState = !this.mDisplayState;
        if (!CallbackBridge.isGrabbing()) {
            if (this.mDisplayState) {
                enable();
            } else {
                disable();
            }
        }
        return this.mDisplayState;
    }

    public void placeMouseAt(float x, float y) {
        this.mMousePointerImageView.setX(x);
        this.mMousePointerImageView.setY(y);
        CallbackBridge.mouseX = this.mScaleFactor * x;
        CallbackBridge.mouseY = this.mScaleFactor * y;
        CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
    }

    private void init() {
        this.mMousePointerImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_mouse_pointer, getContext().getTheme()));
        this.mMousePointerImageView.post(new Runnable() {
            public final void run() {
                Touchpad.this.lambda$init$0$Touchpad();
            }
        });
        addView(this.mMousePointerImageView);
        setFocusable(false);
        if (Build.VERSION.SDK_INT >= 26) {
            setDefaultFocusHighlightEnabled(false);
        }
        disable();
        this.mDisplayState = false;
    }

    public /* synthetic */ void lambda$init$0$Touchpad() {
        ViewGroup.LayoutParams params = this.mMousePointerImageView.getLayoutParams();
        params.width = (int) (LauncherPreferences.PREF_MOUSESCALE * 0.36f);
        params.height = (int) (LauncherPreferences.PREF_MOUSESCALE * 0.54f);
    }

    public void onGrabState(boolean isGrabbing) {
        post(new Runnable(isGrabbing) {
            public final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                Touchpad.this.lambda$onGrabState$1$Touchpad(this.f$1);
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: updateGrabState */
    public void lambda$onGrabState$1$Touchpad(boolean isGrabbing) {
        if (!isGrabbing) {
            if (this.mDisplayState && getVisibility() != 0) {
                enable();
            }
            if (!this.mDisplayState && getVisibility() == 0) {
                disable();
            }
        } else if (getVisibility() != 8) {
            disable();
        }
    }
}
