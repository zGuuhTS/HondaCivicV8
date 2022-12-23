package net.kdt.pojavlaunch;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.InputDeviceCompat;
import kotlinx.coroutines.DebugKt;
import net.kdt.pojavlaunch.customcontrols.ControlLayout;
import net.kdt.pojavlaunch.customcontrols.gamepad.Gamepad;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.utils.JREUtils;
import net.kdt.pojavlaunch.utils.MCOptionUtils;
import net.kdt.pojavlaunch.utils.MathUtils;
import org.apache.commons.p012io.IOUtils;
import org.lwjgl.glfw.CallbackBridge;

public class MinecraftGLSurface extends View implements GrabListener {
    public static final int FINGER_SCROLL_THRESHOLD = ((int) Tools.dpToPx(6.0f));
    public static final int FINGER_STILL_THRESHOLD = ((int) Tools.dpToPx(9.0f));
    private static final int[] HOTBAR_KEYS = {49, 50, 51, 52, 53, 54, 55, 56, 57};
    public static final int MSG_DROP_ITEM_BUTTON_CHECK = 1029;
    public static final int MSG_LEFT_MOUSE_BUTTON_CHECK = 1028;
    /* access modifiers changed from: private */
    public static boolean triggeredLeftMouseButton = false;
    private boolean debugErrored;
    private int mCurrentPointerID;
    private final TapDetector mDoubleTapDetector;
    private Gamepad mGamepad;
    private int mGuiScale;
    private final MCOptionUtils.MCOptionListener mGuiScaleListener;
    /* access modifiers changed from: private */
    public final Handler mHandler;
    /* access modifiers changed from: private */
    public float mInitialX;
    /* access modifiers changed from: private */
    public float mInitialY;
    private int mLastHotbarKey;
    private int mLastPointerCount;
    private TextView mPointerDebugTextView;
    private float mPrevX;
    private float mPrevY;
    private final float mScaleFactor;
    private float mScrollLastInitialX;
    private float mScrollLastInitialY;
    private final double mSensitivityFactor;
    private boolean mShouldBeDown;
    private final TapDetector mSingleTapDetector;
    View mSurface;
    SurfaceReadyListener mSurfaceReadyListener;

    public interface SurfaceReadyListener {
        void isReady();
    }

    public /* synthetic */ void lambda$new$0$MinecraftGLSurface() {
        this.mGuiScale = MCOptionUtils.getMcScale();
    }

    public MinecraftGLSurface(Context context) {
        this(context, (AttributeSet) null);
    }

    public MinecraftGLSurface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mGamepad = null;
        this.mScaleFactor = ((float) LauncherPreferences.PREF_SCALE_FACTOR) / 100.0f;
        this.mSensitivityFactor = ((double) (1080.0f / ((float) Tools.getDisplayMetrics((Activity) getContext()).heightPixels))) * 1.4d;
        this.mSingleTapDetector = new TapDetector(1, 3);
        this.mDoubleTapDetector = new TapDetector(2, 1);
        $$Lambda$MinecraftGLSurface$e6q6fv5KFWsVW5IOqoTb9uoj6Ms r1 = new MCOptionUtils.MCOptionListener() {
            public final void onOptionChanged() {
                MinecraftGLSurface.this.lambda$new$0$MinecraftGLSurface();
            }
        };
        this.mGuiScaleListener = r1;
        this.mSurfaceReadyListener = null;
        this.mLastHotbarKey = -1;
        this.mShouldBeDown = false;
        this.mLastPointerCount = 0;
        this.mCurrentPointerID = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        this.mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg) {
                if (msg.what == 1028) {
                    if (!LauncherPreferences.PREF_DISABLE_GESTURES) {
                        float x = CallbackBridge.mouseX;
                        float y = CallbackBridge.mouseY;
                        if (CallbackBridge.isGrabbing() && MathUtils.dist(x, y, MinecraftGLSurface.this.mInitialX, MinecraftGLSurface.this.mInitialY) < ((float) MinecraftGLSurface.FINGER_STILL_THRESHOLD)) {
                            boolean unused = MinecraftGLSurface.triggeredLeftMouseButton = true;
                            CallbackBridge.sendMouseButton(0, true);
                        }
                    }
                } else if (msg.what == 1029 && CallbackBridge.isGrabbing()) {
                    CallbackBridge.sendKeyPress(81);
                    MinecraftGLSurface.this.mHandler.sendEmptyMessageDelayed(MinecraftGLSurface.MSG_DROP_ITEM_BUTTON_CHECK, 600);
                }
            }
        };
        this.debugErrored = false;
        setFocusable(true);
        MCOptionUtils.addMCOptionListener(r1);
    }

    public void start() {
        TextView textView = new TextView(getContext());
        this.mPointerDebugTextView = textView;
        textView.setX(0.0f);
        this.mPointerDebugTextView.setY(0.0f);
        this.mPointerDebugTextView.setVisibility(8);
        ((ViewGroup) getParent()).addView(this.mPointerDebugTextView);
        if (LauncherPreferences.PREF_USE_ALTERNATE_SURFACE) {
            final SurfaceView surfaceView = new SurfaceView(getContext());
            this.mSurface = surfaceView;
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                private boolean isCalled = false;

                public void surfaceCreated(SurfaceHolder holder) {
                    if (this.isCalled) {
                        JREUtils.setupBridgeWindow(surfaceView.getHolder().getSurface());
                        return;
                    }
                    this.isCalled = true;
                    MinecraftGLSurface.this.realStart(surfaceView.getHolder().getSurface());
                }

                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    MinecraftGLSurface.this.refreshSize();
                }

                public void surfaceDestroyed(SurfaceHolder holder) {
                }
            });
            ((ViewGroup) getParent()).addView(surfaceView);
            return;
        }
        TextureView textureView = new TextureView(getContext());
        textureView.setOpaque(true);
        this.mSurface = textureView;
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            private boolean isCalled = false;

            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                Surface tSurface = new Surface(surface);
                if (this.isCalled) {
                    JREUtils.setupBridgeWindow(tSurface);
                    return;
                }
                this.isCalled = true;
                MinecraftGLSurface.this.realStart(tSurface);
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                MinecraftGLSurface.this.refreshSize();
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return true;
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
        ((ViewGroup) getParent()).addView(textureView);
    }

    public boolean onTouchEvent(MotionEvent e) {
        if (((ControlLayout) getParent()).getModifiable()) {
            return false;
        }
        int i = 0;
        while (i < e.getPointerCount()) {
            if (e.getToolType(i) != 3 && e.getToolType(i) != 2) {
                i++;
            } else if (CallbackBridge.isGrabbing()) {
                return false;
            } else {
                CallbackBridge.sendCursorPos(e.getX(i) * this.mScaleFactor, e.getY(i) * this.mScaleFactor);
                return true;
            }
        }
        if (CallbackBridge.isGrabbing() == 0) {
            CallbackBridge.mouseX = e.getX() * this.mScaleFactor;
            CallbackBridge.mouseY = e.getY() * this.mScaleFactor;
            if (this.mSingleTapDetector.onTouchEvent(e)) {
                CallbackBridge.putMouseEventWithCoords(0, CallbackBridge.mouseX, CallbackBridge.mouseY);
                return true;
            }
        }
        boolean hasDoubleTapped = this.mDoubleTapDetector.onTouchEvent(e);
        switch (e.getActionMasked()) {
            case 0:
                CallbackBridge.sendPrepareGrabInitialPos();
                int hudKeyHandled = handleGuiBar((int) e.getX(), (int) e.getY());
                if (!(hudKeyHandled != -1)) {
                    CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
                    this.mPrevX = e.getX();
                    this.mPrevY = e.getY();
                    if (CallbackBridge.isGrabbing()) {
                        this.mCurrentPointerID = e.getPointerId(0);
                        this.mInitialX = CallbackBridge.mouseX;
                        this.mInitialY = CallbackBridge.mouseY;
                        this.mHandler.sendEmptyMessageDelayed(MSG_LEFT_MOUSE_BUTTON_CHECK, (long) LauncherPreferences.PREF_LONGPRESS_TRIGGER);
                    }
                    this.mLastHotbarKey = hudKeyHandled;
                    break;
                } else {
                    CallbackBridge.sendKeyPress(hudKeyHandled);
                    if (hasDoubleTapped && hudKeyHandled == this.mLastHotbarKey) {
                        CallbackBridge.sendKeyPress(70);
                    }
                    this.mHandler.sendEmptyMessageDelayed(MSG_DROP_ITEM_BUTTON_CHECK, 350);
                    CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
                    this.mLastHotbarKey = hudKeyHandled;
                    break;
                }
            case 1:
            case 3:
                this.mShouldBeDown = false;
                this.mCurrentPointerID = -1;
                if (handleGuiBar((int) e.getX(), (int) e.getY()) != -1) {
                }
                if (CallbackBridge.isGrabbing()) {
                    CallbackBridge.sendKeyPress(81, 0, false);
                    this.mHandler.removeMessages(MSG_DROP_ITEM_BUTTON_CHECK);
                    if (!triggeredLeftMouseButton) {
                        this.mHandler.removeMessages(MSG_LEFT_MOUSE_BUTTON_CHECK);
                        if (!LauncherPreferences.PREF_DISABLE_GESTURES && MathUtils.dist(this.mInitialX, this.mInitialY, CallbackBridge.mouseX, CallbackBridge.mouseY) < ((float) FINGER_STILL_THRESHOLD)) {
                            CallbackBridge.sendMouseButton(1, true);
                            CallbackBridge.sendMouseButton(1, false);
                            break;
                        }
                    } else {
                        CallbackBridge.sendMouseButton(0, false);
                        triggeredLeftMouseButton = false;
                        break;
                    }
                }
                break;
            case 2:
                int hudKeyHandled2 = e.getPointerCount();
                if (!CallbackBridge.isGrabbing()) {
                    if (hudKeyHandled2 != 1) {
                        if (!LauncherPreferences.PREF_DISABLE_GESTURES) {
                            int i2 = FINGER_SCROLL_THRESHOLD;
                            int hScroll = ((int) (e.getX() - this.mScrollLastInitialX)) / i2;
                            int vScroll = ((int) (e.getY() - this.mScrollLastInitialY)) / i2;
                            if (!(vScroll == 0 && hScroll == 0)) {
                                CallbackBridge.sendScroll((double) hScroll, (double) vScroll);
                                this.mScrollLastInitialX = e.getX();
                                this.mScrollLastInitialY = e.getY();
                                break;
                            }
                        }
                    } else {
                        CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
                        this.mPrevX = e.getX();
                        this.mPrevY = e.getY();
                        break;
                    }
                } else {
                    int pointerIndex = e.findPointerIndex(this.mCurrentPointerID);
                    int hudKeyHandled3 = handleGuiBar((int) e.getX(), (int) e.getY());
                    if (pointerIndex == -1 || this.mLastPointerCount != hudKeyHandled2 || !this.mShouldBeDown) {
                        if (hudKeyHandled3 == -1) {
                            this.mShouldBeDown = true;
                            this.mCurrentPointerID = e.getPointerId(0);
                            this.mPrevX = e.getX();
                            this.mPrevY = e.getY();
                            break;
                        }
                    } else {
                        if (hudKeyHandled3 == -1) {
                            CallbackBridge.mouseX = (float) (((double) CallbackBridge.mouseX) + (((double) (e.getX(pointerIndex) - this.mPrevX)) * this.mSensitivityFactor));
                            CallbackBridge.mouseY = (float) (((double) CallbackBridge.mouseY) + (((double) (e.getY(pointerIndex) - this.mPrevY)) * this.mSensitivityFactor));
                        }
                        this.mPrevX = e.getX(pointerIndex);
                        this.mPrevY = e.getY(pointerIndex);
                        CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
                        break;
                    }
                }
                break;
            case 5:
                this.mScrollLastInitialX = e.getX();
                this.mScrollLastInitialY = e.getY();
                int hudKeyHandled4 = handleGuiBar((int) e.getX(e.getPointerCount() - 1), (int) e.getY(e.getPointerCount() - 1));
                if (hudKeyHandled4 != -1) {
                    CallbackBridge.sendKeyPress(hudKeyHandled4);
                    if (hasDoubleTapped && hudKeyHandled4 == this.mLastHotbarKey) {
                        CallbackBridge.sendKeyPress(70);
                    }
                }
                this.mLastHotbarKey = hudKeyHandled4;
                break;
        }
        this.mLastPointerCount = e.getPointerCount();
        CallbackBridge.DEBUG_STRING.setLength(0);
        return true;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        int mouseCursorIndex = -1;
        if (Gamepad.isGamepadEvent(event)) {
            if (this.mGamepad == null) {
                this.mGamepad = new Gamepad(this, event.getDevice());
            }
            this.mGamepad.update(event);
            return true;
        }
        int i = 0;
        while (true) {
            if (i >= event.getPointerCount()) {
                break;
            } else if (event.getToolType(i) == 3 || event.getToolType(i) == 2) {
                mouseCursorIndex = i;
            } else {
                i++;
            }
        }
        mouseCursorIndex = i;
        if (mouseCursorIndex == -1) {
            return false;
        }
        switch (event.getActionMasked()) {
            case 7:
                CallbackBridge.mouseX = event.getX(mouseCursorIndex) * this.mScaleFactor;
                CallbackBridge.mouseY = event.getY(mouseCursorIndex) * this.mScaleFactor;
                CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
                CallbackBridge.DEBUG_STRING.setLength(0);
                return true;
            case 8:
                CallbackBridge.sendScroll((double) event.getAxisValue(9), (double) event.getAxisValue(10));
                return true;
            case 11:
                return sendMouseButtonUnconverted(event.getActionButton(), true);
            case 12:
                return sendMouseButtonUnconverted(event.getActionButton(), false);
            default:
                return false;
        }
    }

    public boolean dispatchCapturedPointerEvent(MotionEvent e) {
        CallbackBridge.mouseX += e.getX() * this.mScaleFactor;
        CallbackBridge.mouseY += e.getY() * this.mScaleFactor;
        if (this.mPointerDebugTextView.getVisibility() == 0 && !this.debugErrored) {
            StringBuilder builder = new StringBuilder();
            try {
                builder.append("PointerCapture debug\n");
                builder.append("MotionEvent=").append(e.getActionMasked()).append(IOUtils.LINE_SEPARATOR_UNIX);
                builder.append("PressingBtn=").append(MotionEvent.class.getDeclaredMethod("buttonStateToString", new Class[0]).invoke((Object) null, new Object[]{Integer.valueOf(e.getButtonState())})).append("\n\n");
                builder.append("PointerX=").append(e.getX()).append(IOUtils.LINE_SEPARATOR_UNIX);
                builder.append("PointerY=").append(e.getY()).append(IOUtils.LINE_SEPARATOR_UNIX);
                builder.append("RawX=").append(e.getRawX()).append(IOUtils.LINE_SEPARATOR_UNIX);
                builder.append("RawY=").append(e.getRawY()).append("\n\n");
                builder.append("XPos=").append(CallbackBridge.mouseX).append(IOUtils.LINE_SEPARATOR_UNIX);
                builder.append("YPos=").append(CallbackBridge.mouseY).append("\n\n");
                builder.append("MovingX=").append(getMoving(e.getX(), true)).append(IOUtils.LINE_SEPARATOR_UNIX);
                builder.append("MovingY=").append(getMoving(e.getY(), false)).append(IOUtils.LINE_SEPARATOR_UNIX);
            } catch (Throwable th) {
                this.mPointerDebugTextView.setText(builder.toString());
                builder.setLength(0);
                throw th;
            }
            this.mPointerDebugTextView.setText(builder.toString());
            builder.setLength(0);
        }
        this.mPointerDebugTextView.setText(CallbackBridge.DEBUG_STRING.toString());
        CallbackBridge.DEBUG_STRING.setLength(0);
        switch (e.getActionMasked()) {
            case 2:
                CallbackBridge.sendCursorPos(CallbackBridge.mouseX, CallbackBridge.mouseY);
                return true;
            case 8:
                CallbackBridge.sendScroll((double) e.getAxisValue(10), (double) e.getAxisValue(9));
                return true;
            case 11:
                return sendMouseButtonUnconverted(e.getActionButton(), true);
            case 12:
                return sendMouseButtonUnconverted(e.getActionButton(), false);
            default:
                return false;
        }
    }

    public boolean processKeyEvent(KeyEvent event) {
        int eventKeycode = event.getKeyCode();
        if (eventKeycode == 0) {
            return true;
        }
        boolean z = false;
        if (eventKeycode == 25 || eventKeycode == 24) {
            return false;
        }
        if (event.getRepeatCount() != 0 || event.getAction() == 2) {
            return true;
        }
        if ((event.getFlags() & 2) == 2) {
            if (eventKeycode == 66) {
                return true;
            }
            MainActivity.touchCharInput.dispatchKeyEvent(event);
            return true;
        } else if (event.getDevice() != null && (((event.getSource() & LwjglGlfwKeycode.GLFW_VISIBLE) == 131076 || (event.getSource() & 8194) == 8194) && eventKeycode == 4)) {
            if (event.getAction() == 0) {
                z = true;
            }
            CallbackBridge.sendMouseButton(1, z);
            return true;
        } else if (Gamepad.isGamepadEvent(event)) {
            if (this.mGamepad == null) {
                this.mGamepad = new Gamepad(this, event.getDevice());
            }
            this.mGamepad.update(event);
            return true;
        } else {
            int index = EfficientAndroidLWJGLKeycode.getIndexByKey(eventKeycode);
            if (index >= 0) {
                EfficientAndroidLWJGLKeycode.execKey(event, index);
                return true;
            } else if ((event.getFlags() & 1024) == 1024) {
                return true;
            } else {
                return false;
            }
        }
    }

    private String getMoving(float pos, boolean xOrY) {
        if (pos == 0.0f) {
            return "STOPPED";
        }
        return pos > 0.0f ? xOrY ? "RIGHT" : "DOWN" : xOrY ? "LEFT" : "UP";
    }

    public static boolean sendMouseButtonUnconverted(int button, boolean status) {
        int glfwButton = InputDeviceCompat.SOURCE_ANY;
        switch (button) {
            case 1:
                glfwButton = 0;
                break;
            case 2:
                glfwButton = 1;
                break;
            case 4:
                glfwButton = 2;
                break;
        }
        if (glfwButton == -256) {
            return false;
        }
        CallbackBridge.sendMouseButton(glfwButton, status);
        return true;
    }

    public int handleGuiBar(int x, int y) {
        if (!CallbackBridge.isGrabbing()) {
            return -1;
        }
        if (y < CallbackBridge.physicalHeight - mcscale(20)) {
            return -1;
        }
        int barWidth = mcscale(180);
        int barX = (CallbackBridge.physicalWidth / 2) - (barWidth / 2);
        if (x < barX || x >= barX + barWidth) {
            return -1;
        }
        return HOTBAR_KEYS[(int) MathUtils.map((float) x, (float) barX, (float) (barX + barWidth), 0.0f, 9.0f)];
    }

    private int mcscale(int input) {
        return (int) (((float) (this.mGuiScale * input)) / this.mScaleFactor);
    }

    public void togglepointerDebugging() {
        TextView textView = this.mPointerDebugTextView;
        int i = 8;
        if (textView.getVisibility() == 8) {
            i = 0;
        }
        textView.setVisibility(i);
    }

    public void refreshSize() {
        CallbackBridge.windowWidth = Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.widthPixels, this.mScaleFactor);
        CallbackBridge.windowHeight = Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.heightPixels, this.mScaleFactor);
        if (this.mSurface == null) {
            Log.w("MGLSurface", "Attempt to refresh size on null surface");
            return;
        }
        if (LauncherPreferences.PREF_USE_ALTERNATE_SURFACE) {
            SurfaceView view = (SurfaceView) this.mSurface;
            if (view.getHolder() != null) {
                view.getHolder().setFixedSize(CallbackBridge.windowWidth, CallbackBridge.windowHeight);
            }
        } else {
            TextureView view2 = (TextureView) this.mSurface;
            if (view2.getSurfaceTexture() != null) {
                view2.getSurfaceTexture().setDefaultBufferSize(CallbackBridge.windowWidth, CallbackBridge.windowHeight);
            }
        }
        CallbackBridge.sendUpdateWindowSize(CallbackBridge.windowWidth, CallbackBridge.windowHeight);
    }

    /* access modifiers changed from: private */
    public void realStart(Surface surface) {
        refreshSize();
        MCOptionUtils.set("fullscreen", DebugKt.DEBUG_PROPERTY_VALUE_OFF);
        MCOptionUtils.set("overrideWidth", String.valueOf(CallbackBridge.windowWidth));
        MCOptionUtils.set("overrideHeight", String.valueOf(CallbackBridge.windowHeight));
        MCOptionUtils.save();
        MCOptionUtils.getMcScale();
        JREUtils.setupBridgeWindow(surface);
        new Thread(new Runnable() {
            public final void run() {
                MinecraftGLSurface.this.lambda$realStart$1$MinecraftGLSurface();
            }
        }, "JVM Main thread").start();
    }

    public /* synthetic */ void lambda$realStart$1$MinecraftGLSurface() {
        while (true) {
            try {
                SurfaceReadyListener surfaceReadyListener = this.mSurfaceReadyListener;
                if (surfaceReadyListener == null) {
                    Thread.sleep(100);
                } else {
                    surfaceReadyListener.isReady();
                    return;
                }
            } catch (Throwable e) {
                Tools.showError(getContext(), e, true);
                return;
            }
        }
    }

    public void onGrabState(boolean isGrabbing) {
        post(new Runnable(isGrabbing) {
            public final /* synthetic */ boolean f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                MinecraftGLSurface.this.lambda$onGrabState$2$MinecraftGLSurface(this.f$1);
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: updateGrabState */
    public void lambda$onGrabState$2$MinecraftGLSurface(boolean isGrabbing) {
        if (MainActivity.isAndroid8OrHigher()) {
            if (isGrabbing && !hasPointerCapture()) {
                requestFocus();
                requestPointerCapture();
            }
            if (!isGrabbing && hasPointerCapture()) {
                releasePointerCapture();
                clearFocus();
            }
        }
    }

    public void setSurfaceReadyListener(SurfaceReadyListener listener) {
        this.mSurfaceReadyListener = listener;
    }
}
