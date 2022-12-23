package net.kdt.pojavlaunch;

public class AWTInputBridge {
    public static final int EVENT_TYPE_CHAR = 1000;
    public static final int EVENT_TYPE_CURSOR_POS = 1003;
    public static final int EVENT_TYPE_KEY = 1005;
    public static final int EVENT_TYPE_MOUSE_BUTTON = 1006;
    public static final int EVENT_TYPE_SCROLL = 1007;

    public static native void nativeMoveWindow(int i, int i2);

    public static native void nativePutClipboard(String str);

    public static native void nativeSendData(int i, int i2, int i3, int i4, int i5);

    public static void sendKey(char keychar, int keycode) {
        nativeSendData(EVENT_TYPE_KEY, keychar, keycode, 0, 0);
    }

    public static void sendChar(char keychar) {
        nativeSendData(1000, keychar, 0, 0, 0);
    }

    public static void sendMousePress(int awtButtons, boolean isDown) {
        nativeSendData(1006, awtButtons, isDown, 0, 0);
    }

    public static void sendMousePress(int awtButtons) {
        sendMousePress(awtButtons, true);
        sendMousePress(awtButtons, false);
    }

    public static void sendMousePos(int x, int y) {
        nativeSendData(1003, x, y, 0, 0);
    }

    static {
        System.loadLibrary("pojavexec_awt");
    }
}
