package net.kdt.pojavlaunch.customcontrols.gamepad;

import android.view.Choreographer;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.math.MathUtils;
import androidx.core.view.InputDeviceCompat;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.utils.MCOptionUtils;
import org.lwjgl.glfw.CallbackBridge;

public class Gamepad {
    private final double MOUSE_MAX_ACCELERATION = 2.0d;
    private int mCurrentJoystickDirection = -1;
    private GamepadMap mCurrentMap;
    private final GamepadMap mGameMap;
    private final GamepadDpad mGamepadDpad = new GamepadDpad();
    private MCOptionUtils.MCOptionListener mGuiScaleListener;
    private long mLastFrameTime;
    private boolean mLastGrabbingState;
    private float mLastHorizontalValue = 0.0f;
    private float mLastVerticalValue = 0.0f;
    private final GamepadJoystick mLeftJoystick;
    private final GamepadMap mMenuMap;
    private final boolean mModifierAnalogTriggers;
    private boolean mModifierSwappedAxis;
    private double mMouseAngle;
    private double mMouseMagnitude;
    private double mMouseSensitivity = 19.0d;
    private float mMouse_x;
    private float mMouse_y;
    private final ImageView mPointerImageView;
    private final GamepadJoystick mRightJoystick;
    private final float mScaleFactor = (((float) LauncherPreferences.DEFAULT_PREF.getInt("resolutionRatio", 100)) / 100.0f);
    /* access modifiers changed from: private */
    public final Choreographer mScreenChoreographer;
    private final double mSensitivityFactor = (((double) (1080.0f / ((float) Tools.currentDisplayMetrics.heightPixels))) * 1.4d);

    public /* synthetic */ void lambda$new$0$Gamepad() {
        notifyGUISizeChange(MCOptionUtils.getMcScale());
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00b5 A[EDGE_INSN: B:21:0x00b5->B:12:0x00b5 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x008c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Gamepad(android.view.View r9, android.view.InputDevice r10) {
        /*
            r8 = this;
            r8.<init>()
            android.content.SharedPreferences r0 = net.kdt.pojavlaunch.prefs.LauncherPreferences.DEFAULT_PREF
            java.lang.String r1 = "resolutionRatio"
            r2 = 100
            int r0 = r0.getInt(r1, r2)
            float r0 = (float) r0
            r1 = 1120403456(0x42c80000, float:100.0)
            float r0 = r0 / r1
            r8.mScaleFactor = r0
            android.util.DisplayMetrics r0 = net.kdt.pojavlaunch.Tools.currentDisplayMetrics
            int r0 = r0.heightPixels
            float r0 = (float) r0
            r1 = 1149698048(0x44870000, float:1080.0)
            float r1 = r1 / r0
            double r0 = (double) r1
            r2 = 4608983858650965606(0x3ff6666666666666, double:1.4)
            double r0 = r0 * r2
            r8.mSensitivityFactor = r0
            net.kdt.pojavlaunch.customcontrols.gamepad.GamepadDpad r0 = new net.kdt.pojavlaunch.customcontrols.gamepad.GamepadDpad
            r0.<init>()
            r8.mGamepadDpad = r0
            r0 = -1
            r8.mCurrentJoystickDirection = r0
            r0 = 0
            r8.mLastHorizontalValue = r0
            r8.mLastVerticalValue = r0
            r0 = 4611686018427387904(0x4000000000000000, double:2.0)
            r8.MOUSE_MAX_ACCELERATION = r0
            r0 = 4626041242239631360(0x4033000000000000, double:19.0)
            r8.mMouseSensitivity = r0
            net.kdt.pojavlaunch.customcontrols.gamepad.GamepadMap r0 = net.kdt.pojavlaunch.customcontrols.gamepad.GamepadMap.getDefaultGameMap()
            r8.mGameMap = r0
            net.kdt.pojavlaunch.customcontrols.gamepad.GamepadMap r1 = net.kdt.pojavlaunch.customcontrols.gamepad.GamepadMap.getDefaultMenuMap()
            r8.mMenuMap = r1
            r8.mCurrentMap = r0
            r0 = 1
            r8.mLastGrabbingState = r0
            r8.mModifierSwappedAxis = r0
            net.kdt.pojavlaunch.customcontrols.gamepad.-$$Lambda$Gamepad$mu22xOJDdBJvoGZy-rE6TzS6coI r1 = new net.kdt.pojavlaunch.customcontrols.gamepad.-$$Lambda$Gamepad$mu22xOJDdBJvoGZy-rE6TzS6coI
            r1.<init>()
            r8.mGuiScaleListener = r1
            android.view.Choreographer r1 = android.view.Choreographer.getInstance()
            r8.mScreenChoreographer = r1
            net.kdt.pojavlaunch.customcontrols.gamepad.Gamepad$1 r2 = new net.kdt.pojavlaunch.customcontrols.gamepad.Gamepad$1
            r2.<init>()
            r1.postFrameCallback(r2)
            long r3 = java.lang.System.nanoTime()
            r8.mLastFrameTime = r3
            net.kdt.pojavlaunch.utils.MCOptionUtils$MCOptionListener r1 = r8.mGuiScaleListener
            net.kdt.pojavlaunch.utils.MCOptionUtils.addMCOptionListener(r1)
            android.content.Context r1 = r9.getContext()
            java.lang.String r3 = "GAMEPAD CREATED"
            android.widget.Toast r1 = android.widget.Toast.makeText(r1, r3, r0)
            r1.show()
            java.util.List r1 = r10.getMotionRanges()
            java.util.Iterator r1 = r1.iterator()
        L_0x0083:
            boolean r3 = r1.hasNext()
            r4 = 22
            r5 = 0
            if (r3 == 0) goto L_0x00b5
            java.lang.Object r3 = r1.next()
            android.view.InputDevice$MotionRange r3 = (android.view.InputDevice.MotionRange) r3
            int r6 = r3.getAxis()
            r7 = 18
            if (r6 == r7) goto L_0x00b2
            int r6 = r3.getAxis()
            r7 = 17
            if (r6 == r7) goto L_0x00b2
            int r6 = r3.getAxis()
            if (r6 == r4) goto L_0x00b2
            int r6 = r3.getAxis()
            r7 = 23
            if (r6 != r7) goto L_0x00b1
            goto L_0x00b2
        L_0x00b1:
            goto L_0x0083
        L_0x00b2:
            r8.mModifierSwappedAxis = r5
        L_0x00b5:
            net.kdt.pojavlaunch.customcontrols.gamepad.GamepadJoystick r1 = new net.kdt.pojavlaunch.customcontrols.gamepad.GamepadJoystick
            r1.<init>(r5, r0, r10)
            r8.mLeftJoystick = r1
            boolean r0 = r8.mModifierSwappedAxis
            if (r0 != 0) goto L_0x00cc
            net.kdt.pojavlaunch.customcontrols.gamepad.GamepadJoystick r0 = new net.kdt.pojavlaunch.customcontrols.gamepad.GamepadJoystick
            r1 = 11
            r3 = 14
            r0.<init>(r1, r3, r10)
            r8.mRightJoystick = r0
            goto L_0x00d7
        L_0x00cc:
            net.kdt.pojavlaunch.customcontrols.gamepad.GamepadJoystick r0 = new net.kdt.pojavlaunch.customcontrols.gamepad.GamepadJoystick
            r1 = 12
            r3 = 13
            r0.<init>(r1, r3, r10)
            r8.mRightJoystick = r0
        L_0x00d7:
            boolean r0 = r8.supportAnalogTriggers(r10)
            r8.mModifierAnalogTriggers = r0
            android.content.Context r0 = r9.getContext()
            android.widget.ImageView r1 = new android.widget.ImageView
            android.content.Context r3 = r9.getContext()
            r1.<init>(r3)
            r8.mPointerImageView = r1
            android.content.res.Resources r3 = r0.getResources()
            r6 = 2131230827(0x7f08006b, float:1.8077718E38)
            android.content.res.Resources$Theme r7 = r0.getTheme()
            android.graphics.drawable.Drawable r3 = androidx.core.content.res.ResourcesCompat.getDrawable(r3, r6, r7)
            r1.setImageDrawable(r3)
            android.graphics.drawable.Drawable r3 = r1.getDrawable()
            r3.setFilterBitmap(r5)
            int r3 = net.kdt.pojavlaunch.utils.MCOptionUtils.getMcScale()
            int r3 = r3 * r4
            float r3 = (float) r3
            float r4 = r8.mScaleFactor
            float r3 = r3 / r4
            int r3 = (int) r3
            android.widget.FrameLayout$LayoutParams r4 = new android.widget.FrameLayout$LayoutParams
            r4.<init>(r3, r3)
            r1.setLayoutParams(r4)
            int r4 = org.lwjgl.glfw.CallbackBridge.windowWidth
            int r4 = r4 / 2
            float r4 = (float) r4
            r8.mMouse_x = r4
            int r4 = org.lwjgl.glfw.CallbackBridge.windowHeight
            int r4 = r4 / 2
            float r4 = (float) r4
            r8.mMouse_y = r4
            float r5 = r8.mMouse_x
            org.lwjgl.glfw.CallbackBridge.sendCursorPos(r5, r4)
            int r4 = org.lwjgl.glfw.CallbackBridge.physicalWidth
            int r4 = r4 / 2
            int r5 = org.lwjgl.glfw.CallbackBridge.physicalHeight
            int r5 = r5 / 2
            r8.placePointerView(r4, r5)
            android.view.ViewParent r4 = r9.getParent()
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            r4.addView(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.customcontrols.gamepad.Gamepad.<init>(android.view.View, android.view.InputDevice):void");
    }

    public void update(KeyEvent event) {
        sendButton(event);
    }

    public void update(MotionEvent event) {
        updateDirectionalJoystick(event);
        updateMouseJoystick(event);
        updateAnalogTriggers(event);
        int[] dpadEvent = this.mGamepadDpad.convertEvent(event);
        sendButton(dpadEvent[0], dpadEvent[1]);
    }

    public void notifyGUISizeChange(int newSize) {
        this.mPointerImageView.post(new Runnable((int) (((float) (newSize * 22)) / this.mScaleFactor)) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                Gamepad.this.lambda$notifyGUISizeChange$1$Gamepad(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$notifyGUISizeChange$1$Gamepad(int size) {
        this.mPointerImageView.setLayoutParams(new FrameLayout.LayoutParams(size, size));
    }

    public void sendButton(KeyEvent event) {
        sendButton(event.getKeyCode(), event.getAction());
    }

    public void sendButton(int keycode, int action) {
        boolean isDown = action == 0;
        switch (keycode) {
            case 19:
                getCurrentMap().DPAD_UP.update(isDown);
                return;
            case 20:
                getCurrentMap().DPAD_DOWN.update(isDown);
                return;
            case 21:
                getCurrentMap().DPAD_LEFT.update(isDown);
                return;
            case 22:
                getCurrentMap().DPAD_RIGHT.update(isDown);
                return;
            case 23:
                getCurrentMap().DPAD_RIGHT.update(false);
                getCurrentMap().DPAD_LEFT.update(false);
                getCurrentMap().DPAD_UP.update(false);
                getCurrentMap().DPAD_DOWN.update(false);
                return;
            case 96:
                getCurrentMap().BUTTON_A.update(isDown);
                return;
            case 97:
                getCurrentMap().BUTTON_B.update(isDown);
                return;
            case 99:
                getCurrentMap().BUTTON_X.update(isDown);
                return;
            case 100:
                getCurrentMap().BUTTON_Y.update(isDown);
                return;
            case 102:
                getCurrentMap().SHOULDER_LEFT.update(isDown);
                return;
            case 103:
                getCurrentMap().SHOULDER_RIGHT.update(isDown);
                return;
            case 104:
                if (!this.mModifierAnalogTriggers) {
                    getCurrentMap().TRIGGER_LEFT.update(isDown);
                    return;
                }
                return;
            case 105:
                if (!this.mModifierAnalogTriggers) {
                    getCurrentMap().TRIGGER_RIGHT.update(isDown);
                    return;
                }
                return;
            case 106:
                getCurrentMap().THUMBSTICK_LEFT.update(isDown);
                return;
            case 107:
                getCurrentMap().THUMBSTICK_RIGHT.update(isDown);
                return;
            case 108:
                getCurrentMap().BUTTON_START.update(isDown);
                return;
            case 109:
                getCurrentMap().BUTTON_SELECT.update(isDown);
                return;
            default:
                CallbackBridge.sendKeyPress(32, CallbackBridge.getCurrentMods(), isDown);
                return;
        }
    }

    public static void sendInput(int[] keycodes, boolean isDown) {
        for (int keycode : keycodes) {
            switch (keycode) {
                case -2:
                    if (!isDown) {
                        break;
                    } else {
                        CallbackBridge.sendScroll(0.0d, 1.0d);
                        break;
                    }
                case -1:
                    if (!isDown) {
                        break;
                    } else {
                        CallbackBridge.sendScroll(0.0d, -1.0d);
                        break;
                    }
                case 0:
                    CallbackBridge.sendMouseButton(0, isDown);
                    break;
                case 1:
                    CallbackBridge.sendMouseButton(1, isDown);
                    break;
                default:
                    CallbackBridge.sendKeyPress(keycode, CallbackBridge.getCurrentMods(), isDown);
                    break;
            }
            CallbackBridge.setModifiers(keycode, isDown);
        }
    }

    public static boolean isGamepadEvent(MotionEvent event) {
        return GamepadJoystick.isJoystickEvent(event);
    }

    public static boolean isGamepadEvent(KeyEvent event) {
        if (!((event.getSource() & InputDeviceCompat.SOURCE_GAMEPAD) == 1025 || (event.getDevice() != null && (event.getDevice().getSources() & InputDeviceCompat.SOURCE_GAMEPAD) == 1025)) || !GamepadDpad.isDpadEvent(event)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void tick(long frameTimeNanos) {
        if (!(this.mLastHorizontalValue == 0.0f && this.mLastVerticalValue == 0.0f)) {
            GamepadJoystick currentJoystick = this.mLastGrabbingState ? this.mLeftJoystick : this.mRightJoystick;
            double acceleration = Math.pow((this.mMouseMagnitude - ((double) currentJoystick.getDeadzone())) / ((double) (1.0f - currentJoystick.getDeadzone())), 2.0d);
            if (acceleration > 1.0d) {
                acceleration = 1.0d;
            }
            float deltaTimeScale = ((float) (frameTimeNanos - this.mLastFrameTime)) / 1.6666666E7f;
            CallbackBridge.mouseX += ((float) (Math.cos(this.mMouseAngle) * acceleration * this.mMouseSensitivity)) * deltaTimeScale;
            CallbackBridge.mouseY -= ((float) ((Math.sin(this.mMouseAngle) * acceleration) * this.mMouseSensitivity)) * deltaTimeScale;
            if (!this.mLastGrabbingState) {
                CallbackBridge.mouseX = MathUtils.clamp(CallbackBridge.mouseX, 0.0f, (float) CallbackBridge.windowWidth);
                CallbackBridge.mouseY = MathUtils.clamp(CallbackBridge.mouseY, 0.0f, (float) CallbackBridge.windowHeight);
                placePointerView((int) (CallbackBridge.mouseX / this.mScaleFactor), (int) (CallbackBridge.mouseY / this.mScaleFactor));
            }
            this.mMouse_x = CallbackBridge.mouseX;
            this.mMouse_y = CallbackBridge.mouseY;
            CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
        }
        this.mLastFrameTime = frameTimeNanos;
    }

    /* access modifiers changed from: private */
    public void updateGrabbingState() {
        boolean lastGrabbingValue = this.mLastGrabbingState;
        boolean isGrabbing = CallbackBridge.isGrabbing();
        this.mLastGrabbingState = isGrabbing;
        if (lastGrabbingValue != isGrabbing) {
            this.mCurrentMap.resetPressedState();
            if (this.mLastGrabbingState) {
                this.mCurrentMap = this.mGameMap;
                this.mPointerImageView.setVisibility(4);
                this.mMouseSensitivity = 18.0d;
                return;
            }
            this.mCurrentMap = this.mMenuMap;
            sendDirectionalKeycode(this.mCurrentJoystickDirection, false, this.mGameMap);
            this.mMouse_x = (float) (CallbackBridge.windowWidth / 2);
            float f = (float) (CallbackBridge.windowHeight / 2);
            this.mMouse_y = f;
            CallbackBridge.sendCursorPos(this.mMouse_x, f);
            placePointerView(CallbackBridge.physicalWidth / 2, CallbackBridge.physicalHeight / 2);
            this.mPointerImageView.setVisibility(0);
            this.mMouseSensitivity = ((double) (this.mScaleFactor * 19.0f)) / this.mSensitivityFactor;
        }
    }

    private void updateMouseJoystick(MotionEvent event) {
        GamepadJoystick currentJoystick = this.mLastGrabbingState ? this.mRightJoystick : this.mLeftJoystick;
        float horizontalValue = currentJoystick.getHorizontalAxis(event);
        float verticalValue = currentJoystick.getVerticalAxis(event);
        if (horizontalValue == this.mLastHorizontalValue && verticalValue == this.mLastVerticalValue) {
            this.mLastHorizontalValue = horizontalValue;
            this.mLastVerticalValue = verticalValue;
            this.mMouseMagnitude = currentJoystick.getMagnitude(event);
            this.mMouseAngle = currentJoystick.getAngleRadian(event);
            return;
        }
        this.mLastHorizontalValue = horizontalValue;
        this.mLastVerticalValue = verticalValue;
        this.mMouseMagnitude = currentJoystick.getMagnitude(event);
        this.mMouseAngle = currentJoystick.getAngleRadian(event);
        tick(System.nanoTime());
    }

    private void updateDirectionalJoystick(MotionEvent event) {
        GamepadJoystick currentJoystick = this.mLastGrabbingState ? this.mLeftJoystick : this.mRightJoystick;
        int lastJoystickDirection = this.mCurrentJoystickDirection;
        int heightDirection = currentJoystick.getHeightDirection(event);
        this.mCurrentJoystickDirection = heightDirection;
        if (heightDirection != lastJoystickDirection) {
            sendDirectionalKeycode(lastJoystickDirection, false, getCurrentMap());
            sendDirectionalKeycode(this.mCurrentJoystickDirection, true, getCurrentMap());
        }
    }

    private void updateAnalogTriggers(MotionEvent event) {
        if (this.mModifierAnalogTriggers) {
            boolean z = false;
            getCurrentMap().TRIGGER_LEFT.update(((double) event.getAxisValue(17)) > 0.5d || ((double) event.getAxisValue(23)) > 0.5d || (this.mModifierSwappedAxis && ((double) event.getAxisValue(11)) > 0.5d));
            GamepadButton gamepadButton = getCurrentMap().TRIGGER_RIGHT;
            if (((double) event.getAxisValue(18)) > 0.5d || ((double) event.getAxisValue(22)) > 0.5d || (this.mModifierSwappedAxis && ((double) event.getAxisValue(14)) > 0.5d)) {
                z = true;
            }
            gamepadButton.update(z);
        }
    }

    private boolean supportAnalogTriggers(InputDevice inputDevice) {
        for (InputDevice.MotionRange motionRange : inputDevice.getMotionRanges()) {
            int axis = motionRange.getAxis();
            if (axis == 23 || axis == 22 || axis == 17 || axis == 18) {
                return true;
            }
            boolean z = this.mModifierSwappedAxis;
            if (z && axis == 11) {
                return true;
            }
            if (z && axis == 14) {
                return true;
            }
        }
        return false;
    }

    private GamepadMap getCurrentMap() {
        return this.mCurrentMap;
    }

    private static void sendDirectionalKeycode(int direction, boolean isDown, GamepadMap map) {
        switch (direction) {
            case 0:
                sendInput(map.DIRECTION_RIGHT, isDown);
                return;
            case 1:
                sendInput(map.DIRECTION_FORWARD, isDown);
                sendInput(map.DIRECTION_RIGHT, isDown);
                return;
            case 2:
                sendInput(map.DIRECTION_FORWARD, isDown);
                return;
            case 3:
                sendInput(map.DIRECTION_FORWARD, isDown);
                sendInput(map.DIRECTION_LEFT, isDown);
                return;
            case 4:
                sendInput(map.DIRECTION_LEFT, isDown);
                return;
            case 5:
                sendInput(map.DIRECTION_BACKWARD, isDown);
                sendInput(map.DIRECTION_LEFT, isDown);
                return;
            case 6:
                sendInput(map.DIRECTION_BACKWARD, isDown);
                return;
            case 7:
                sendInput(map.DIRECTION_RIGHT, isDown);
                sendInput(map.DIRECTION_BACKWARD, isDown);
                return;
            default:
                return;
        }
    }

    private void placePointerView(int x, int y) {
        ImageView imageView = this.mPointerImageView;
        imageView.setX((float) (x - (imageView.getWidth() / 2)));
        ImageView imageView2 = this.mPointerImageView;
        imageView2.setY((float) (y - (imageView2.getHeight() / 2)));
    }
}
