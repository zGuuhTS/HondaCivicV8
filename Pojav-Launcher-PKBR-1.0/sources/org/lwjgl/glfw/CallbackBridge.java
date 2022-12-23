package org.lwjgl.glfw;

import android.content.ClipData;
import android.view.Choreographer;
import java.util.ArrayList;
import java.util.Iterator;
import net.kdt.pojavlaunch.GrabListener;
import net.kdt.pojavlaunch.MainActivity;
import org.apache.commons.p012io.IOUtils;
import top.defaults.checkerboarddrawable.BuildConfig;

public class CallbackBridge {
    public static final int ANDROID_TYPE_GRAB_STATE = 0;
    public static final int CLIPBOARD_COPY = 2000;
    public static final int CLIPBOARD_OPEN = 2002;
    public static final int CLIPBOARD_PASTE = 2001;
    public static StringBuilder DEBUG_STRING = new StringBuilder();
    private static final ArrayList<GrabListener> grabListeners = new ArrayList<>();
    public static volatile boolean holdingAlt;
    public static volatile boolean holdingCapslock;
    public static volatile boolean holdingCtrl;
    public static volatile boolean holdingNumlock;
    public static volatile boolean holdingShift;
    private static boolean isGrabbing = false;
    public static float mouseX;
    public static float mouseY;
    public static volatile int physicalHeight;
    public static volatile int physicalWidth;
    public static Choreographer sChoreographer = Choreographer.getInstance();
    private static boolean threadAttached;
    public static volatile int windowHeight;
    public static volatile int windowWidth;

    public static native boolean nativeAttachThreadToOther(boolean z, boolean z2);

    public static native boolean nativeIsGrabbing();

    private static native boolean nativeSendChar(char c);

    private static native boolean nativeSendCharMods(char c, int i);

    private static native void nativeSendCursorPos(float f, float f2);

    private static native void nativeSendKey(int i, int i2, int i3, int i4);

    private static native void nativeSendMouseButton(int i, int i2, int i3);

    private static native void nativeSendScreenSize(int i, int i2);

    private static native void nativeSendScroll(double d, double d2);

    public static native void nativeSetUseInputStackQueue(boolean z);

    public static native void nativeSetWindowAttrib(int i, int i2);

    static {
        System.loadLibrary("pojavexec");
    }

    public static void putMouseEventWithCoords(int button, float x, float y) {
        putMouseEventWithCoords(button, true, x, y);
        sChoreographer.postFrameCallbackDelayed(new Choreographer.FrameCallback(button, x, y) {
            public final /* synthetic */ int f$0;
            public final /* synthetic */ float f$1;
            public final /* synthetic */ float f$2;

            {
                this.f$0 = r1;
                this.f$1 = r2;
                this.f$2 = r3;
            }

            public final void doFrame(long j) {
                CallbackBridge.putMouseEventWithCoords(this.f$0, false, this.f$1, this.f$2);
            }
        }, 33);
    }

    public static void putMouseEventWithCoords(int button, boolean isDown, float x, float y) {
        sendCursorPos(x, y);
        sendMouseKeycode(button, getCurrentMods(), isDown);
    }

    public static void sendCursorPos(float x, float y) {
        if (!threadAttached) {
            nativeSetUseInputStackQueue(MainActivity.isInputStackCall);
            threadAttached = nativeAttachThreadToOther(true, MainActivity.isInputStackCall);
        }
        DEBUG_STRING.append("CursorPos=").append(x).append(", ").append(y).append(IOUtils.LINE_SEPARATOR_UNIX);
        mouseX = x;
        mouseY = y;
        nativeSendCursorPos(x, y);
    }

    public static void sendPrepareGrabInitialPos() {
        DEBUG_STRING.append("Prepare set grab initial posititon: ignored");
    }

    public static void sendKeycode(int keycode, char keychar, int scancode, int modifiers, boolean isDown) {
        DEBUG_STRING.append("KeyCode=").append(keycode).append(", Char=").append(keychar);
        if (keycode != 0) {
            nativeSendKey(keycode, scancode, isDown, modifiers);
        }
        if (isDown && keychar != 0) {
            nativeSendCharMods(keychar, modifiers);
            nativeSendChar(keychar);
        }
    }

    public static void sendChar(char keychar, int modifiers) {
        nativeSendCharMods(keychar, modifiers);
        nativeSendChar(keychar);
    }

    public static void sendKeyPress(int keyCode, int modifiers, boolean status) {
        sendKeyPress(keyCode, 0, modifiers, status);
    }

    public static void sendKeyPress(int keyCode, int scancode, int modifiers, boolean status) {
        sendKeyPress(keyCode, 0, scancode, modifiers, status);
    }

    public static void sendKeyPress(int keyCode, char keyChar, int scancode, int modifiers, boolean status) {
        sendKeycode(keyCode, keyChar, scancode, modifiers, status);
    }

    public static void sendKeyPress(int keyCode) {
        sendKeyPress(keyCode, getCurrentMods(), true);
        sendKeyPress(keyCode, getCurrentMods(), false);
    }

    public static void sendMouseButton(int button, boolean status) {
        sendMouseKeycode(button, getCurrentMods(), status);
    }

    public static void sendMouseKeycode(int button, int modifiers, boolean isDown) {
        DEBUG_STRING.append("MouseKey=").append(button).append(", down=").append(isDown).append(IOUtils.LINE_SEPARATOR_UNIX);
        nativeSendMouseButton(button, isDown, modifiers);
    }

    public static void sendMouseKeycode(int keycode) {
        sendMouseKeycode(keycode, getCurrentMods(), true);
        sendMouseKeycode(keycode, getCurrentMods(), false);
    }

    public static void sendScroll(double xoffset, double yoffset) {
        DEBUG_STRING.append("ScrollX=").append(xoffset).append(",ScrollY=").append(yoffset);
        nativeSendScroll(xoffset, yoffset);
    }

    public static void sendUpdateWindowSize(int w, int h) {
        nativeSendScreenSize(w, h);
    }

    public static boolean isGrabbing() {
        return isGrabbing;
    }

    public static String accessAndroidClipboard(int type, String copy) {
        switch (type) {
            case CLIPBOARD_COPY /*2000*/:
                MainActivity.GLOBAL_CLIPBOARD.setPrimaryClip(ClipData.newPlainText("Copy", copy));
                return null;
            case CLIPBOARD_PASTE /*2001*/:
                if (!MainActivity.GLOBAL_CLIPBOARD.hasPrimaryClip() || !MainActivity.GLOBAL_CLIPBOARD.getPrimaryClipDescription().hasMimeType("text/plain")) {
                    return BuildConfig.FLAVOR;
                }
                return MainActivity.GLOBAL_CLIPBOARD.getPrimaryClip().getItemAt(0).getText().toString();
            case CLIPBOARD_OPEN /*2002*/:
                MainActivity.openLink(copy);
                return null;
            default:
                return null;
        }
    }

    public static int getCurrentMods() {
        int currMods = 0;
        if (holdingAlt) {
            currMods = 0 | 4;
        }
        if (holdingCapslock) {
            currMods |= 16;
        }
        if (holdingCtrl) {
            currMods |= 2;
        }
        if (holdingNumlock) {
            currMods |= 32;
        }
        if (holdingShift) {
            return currMods | 1;
        }
        return currMods;
    }

    public static void setModifiers(int keyCode, boolean isDown) {
        switch (keyCode) {
            case 280:
                holdingCapslock = isDown;
                return;
            case 282:
                holdingNumlock = isDown;
                return;
            case 340:
                holdingShift = isDown;
                return;
            case 341:
                holdingCtrl = isDown;
                return;
            case 342:
                holdingAlt = isDown;
                return;
            default:
                return;
        }
    }

    private static void onGrabStateChanged(boolean grabbing) {
        isGrabbing = grabbing;
        ArrayList<GrabListener> arrayList = grabListeners;
        synchronized (arrayList) {
            Iterator<GrabListener> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().onGrabState(grabbing);
            }
        }
    }

    public static void addGrabListener(GrabListener listener) {
        ArrayList<GrabListener> arrayList = grabListeners;
        synchronized (arrayList) {
            listener.onGrabState(isGrabbing);
            arrayList.add(listener);
        }
    }

    public static void removeGrabListener(GrabListener listener) {
        ArrayList<GrabListener> arrayList = grabListeners;
        synchronized (arrayList) {
            arrayList.remove(listener);
        }
    }
}
