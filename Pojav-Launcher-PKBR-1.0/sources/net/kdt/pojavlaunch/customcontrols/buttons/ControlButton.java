package net.kdt.pojavlaunch.customcontrols.buttons;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.MainActivity;
import net.kdt.pojavlaunch.MinecraftGLSurface;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.ControlLayout;
import net.kdt.pojavlaunch.customcontrols.handleview.EditControlPopup;
import org.lwjgl.glfw.CallbackBridge;

public class ControlButton extends TextView implements ControlInterface {
    protected boolean mIsPointerOutOfBounds = false;
    protected boolean mIsToggled = false;
    protected ControlData mProperties;
    private final Paint mRectPaint = new Paint();

    public ControlButton(ControlLayout layout, ControlData properties) {
        super(layout.getContext());
        setGravity(17);
        setAllCaps(true);
        setTextColor(-1);
        setPadding(4, 4, 4, 4);
        setProperties(preProcessProperties(properties, layout));
        injectBehaviors();
    }

    public View getControlView() {
        return this;
    }

    public ControlData getProperties() {
        return this.mProperties;
    }

    public void setProperties(ControlData properties, boolean changePos) {
        this.mProperties = properties;
        super.setProperties(properties, changePos);
        if (this.mProperties.isToggle) {
            TypedValue value = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.colorAccent, value, true);
            this.mRectPaint.setColor(value.data);
            this.mRectPaint.setAlpha(128);
        } else {
            this.mRectPaint.setColor(-1);
            this.mRectPaint.setAlpha(60);
        }
        setText(properties.name);
    }

    public void setVisible(boolean isVisible) {
        if (this.mProperties.isHideable) {
            setVisibility(isVisible ? 0 : 8);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mIsToggled || (!this.mProperties.isToggle && isActivated())) {
            canvas.drawRoundRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.mProperties.cornerRadius, this.mProperties.cornerRadius, this.mRectPaint);
        }
    }

    public void loadEditValues(EditControlPopup editControlPopup) {
        editControlPopup.loadValues(getProperties());
    }

    public void cloneButton() {
        ControlData cloneData = new ControlData(getProperties());
        cloneData.dynamicX = "0.5 * ${screen_width}";
        cloneData.dynamicY = "0.5 * ${screen_height}";
        ((ControlLayout) getParent()).addControlButton(cloneData);
    }

    public void removeButton() {
        getControlLayoutParent().getLayout().mControlDataList.remove(getProperties());
        getControlLayoutParent().removeView(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        MinecraftGLSurface v;
        MinecraftGLSurface v2;
        switch (event.getActionMasked()) {
            case 0:
            case 5:
                if (!getProperties().isToggle) {
                    sendKeyPresses(true);
                    break;
                }
                break;
            case 1:
            case 3:
            case 6:
                if (getProperties().passThruEnabled && (v = (MinecraftGLSurface) getControlLayoutParent().findViewById(R.id.main_game_render_view)) != null) {
                    v.dispatchTouchEvent(event);
                }
                if (this.mIsPointerOutOfBounds) {
                    getControlLayoutParent().onTouch(this, event);
                }
                this.mIsPointerOutOfBounds = false;
                if (!triggerToggle()) {
                    sendKeyPresses(false);
                    break;
                }
                break;
            case 2:
                if (getProperties().passThruEnabled && CallbackBridge.isGrabbing() && (v2 = (MinecraftGLSurface) getControlLayoutParent().findViewById(R.id.main_game_render_view)) != null) {
                    v2.dispatchTouchEvent(event);
                }
                if (event.getX() >= ((float) getControlView().getLeft()) && event.getX() <= ((float) getControlView().getRight()) && event.getY() >= ((float) getControlView().getTop()) && event.getY() <= ((float) getControlView().getBottom())) {
                    if (this.mIsPointerOutOfBounds) {
                        getControlLayoutParent().onTouch(this, event);
                        if (getProperties().isSwipeable && !getProperties().isToggle) {
                            sendKeyPresses(true);
                        }
                    }
                    this.mIsPointerOutOfBounds = false;
                    break;
                } else {
                    if (getProperties().isSwipeable && !this.mIsPointerOutOfBounds && !triggerToggle()) {
                        sendKeyPresses(false);
                    }
                    this.mIsPointerOutOfBounds = true;
                    getControlLayoutParent().onTouch(this, event);
                    break;
                }
            default:
                return false;
        }
        return super.onTouchEvent(event);
    }

    public boolean triggerToggle() {
        if (!this.mProperties.isToggle) {
            return false;
        }
        this.mIsToggled = !this.mIsToggled;
        invalidate();
        sendKeyPresses(this.mIsToggled);
        return true;
    }

    public void sendKeyPresses(boolean isDown) {
        setActivated(isDown);
        for (int keycode : this.mProperties.keycodes) {
            if (keycode >= 0) {
                CallbackBridge.sendKeyPress(keycode, CallbackBridge.getCurrentMods(), isDown);
                CallbackBridge.setModifiers(keycode, isDown);
            } else {
                sendSpecialKey(keycode, isDown);
            }
        }
    }

    private void sendSpecialKey(int keycode, boolean isDown) {
        switch (keycode) {
            case ControlData.SPECIALBTN_SCROLLDOWN:
                if (!isDown) {
                    CallbackBridge.sendScroll(0.0d, 1.0d);
                    return;
                }
                return;
            case ControlData.SPECIALBTN_SCROLLUP:
                if (!isDown) {
                    CallbackBridge.sendScroll(0.0d, -1.0d);
                    return;
                }
                return;
            case ControlData.SPECIALBTN_MOUSEMID:
                CallbackBridge.sendMouseButton(2, isDown);
                return;
            case ControlData.SPECIALBTN_VIRTUALMOUSE:
                if (isDown) {
                    MainActivity.toggleMouse(getContext());
                    return;
                }
                return;
            case -4:
                CallbackBridge.sendMouseButton(1, isDown);
                return;
            case -3:
                CallbackBridge.sendMouseButton(0, isDown);
                return;
            case -2:
                if (isDown) {
                    MainActivity.mControlLayout.toggleControlVisible();
                    return;
                }
                return;
            case -1:
                if (isDown) {
                    MainActivity.switchKeyboardState();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean hasOverlappingRendering() {
        return false;
    }
}
