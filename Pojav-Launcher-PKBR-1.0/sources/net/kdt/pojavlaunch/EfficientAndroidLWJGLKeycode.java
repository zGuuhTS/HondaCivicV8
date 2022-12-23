package net.kdt.pojavlaunch;

import android.view.KeyEvent;
import java.util.Arrays;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.lwjgl.glfw.CallbackBridge;
import top.defaults.checkerboarddrawable.BuildConfig;

public class EfficientAndroidLWJGLKeycode {
    private static final int KEYCODE_COUNT = 106;
    private static String[] androidKeyNameArray;
    private static int mTmpCount = 0;
    private static final int[] sAndroidKeycodes = new int[106];
    private static final short[] sLwjglKeycodes = new short[106];

    static {
        add(0, 0);
        add(3, LwjglGlfwKeycode.GLFW_KEY_HOME);
        add(4, LwjglGlfwKeycode.GLFW_KEY_ESCAPE);
        add(7, 48);
        add(8, 49);
        add(9, 50);
        add(10, 51);
        add(11, 52);
        add(12, 53);
        add(13, 54);
        add(14, 55);
        add(15, 56);
        add(16, 57);
        add(18, 51);
        add(19, LwjglGlfwKeycode.GLFW_KEY_UP);
        add(20, LwjglGlfwKeycode.GLFW_KEY_DOWN);
        add(21, LwjglGlfwKeycode.GLFW_KEY_LEFT);
        add(22, LwjglGlfwKeycode.GLFW_KEY_RIGHT);
        add(29, 65);
        add(30, 66);
        add(31, 67);
        add(32, 68);
        add(33, 69);
        add(34, 70);
        add(35, 71);
        add(36, 72);
        add(37, 73);
        add(38, 74);
        add(39, 75);
        add(40, 76);
        add(41, 77);
        add(42, 78);
        add(43, 79);
        add(44, 80);
        add(45, 81);
        add(46, 82);
        add(47, 83);
        add(48, 84);
        add(49, 85);
        add(50, 86);
        add(51, 87);
        add(52, 88);
        add(53, 89);
        add(54, 90);
        add(55, 44);
        add(56, 46);
        add(57, LwjglGlfwKeycode.GLFW_KEY_LEFT_ALT);
        add(58, LwjglGlfwKeycode.GLFW_KEY_RIGHT_ALT);
        add(59, LwjglGlfwKeycode.GLFW_KEY_LEFT_SHIFT);
        add(60, LwjglGlfwKeycode.GLFW_KEY_RIGHT_SHIFT);
        add(61, LwjglGlfwKeycode.GLFW_KEY_TAB);
        add(62, 32);
        add(66, LwjglGlfwKeycode.GLFW_KEY_ENTER);
        add(67, LwjglGlfwKeycode.GLFW_KEY_BACKSPACE);
        add(68, 96);
        add(69, 45);
        add(70, 61);
        add(71, 91);
        add(72, 93);
        add(73, 92);
        add(74, 59);
        add(75, 39);
        add(76, 47);
        add(77, 50);
        add(81, LwjglGlfwKeycode.GLFW_KEY_KP_ADD);
        add(92, LwjglGlfwKeycode.GLFW_KEY_PAGE_UP);
        add(93, LwjglGlfwKeycode.GLFW_KEY_PAGE_DOWN);
        add(111, LwjglGlfwKeycode.GLFW_KEY_ESCAPE);
        add(113, LwjglGlfwKeycode.GLFW_KEY_LEFT_CONTROL);
        add(114, LwjglGlfwKeycode.GLFW_KEY_RIGHT_CONTROL);
        add(115, LwjglGlfwKeycode.GLFW_KEY_CAPS_LOCK);
        add(121, LwjglGlfwKeycode.GLFW_KEY_PAUSE);
        add(123, LwjglGlfwKeycode.GLFW_KEY_END);
        add(124, LwjglGlfwKeycode.GLFW_KEY_INSERT);
        add(131, LwjglGlfwKeycode.GLFW_KEY_F1);
        add(AWTInputEvent.VK_DEAD_MACRON, LwjglGlfwKeycode.GLFW_KEY_F2);
        add(AWTInputEvent.VK_DEAD_BREVE, LwjglGlfwKeycode.GLFW_KEY_F3);
        add(AWTInputEvent.VK_DEAD_ABOVEDOT, LwjglGlfwKeycode.GLFW_KEY_F4);
        add(AWTInputEvent.VK_DEAD_DIAERESIS, LwjglGlfwKeycode.GLFW_KEY_F5);
        add(AWTInputEvent.VK_DEAD_ABOVERING, LwjglGlfwKeycode.GLFW_KEY_F6);
        add(AWTInputEvent.VK_DEAD_DOUBLEACUTE, LwjglGlfwKeycode.GLFW_KEY_F7);
        add(AWTInputEvent.VK_DEAD_CARON, LwjglGlfwKeycode.GLFW_KEY_F8);
        add(AWTInputEvent.VK_DEAD_CEDILLA, LwjglGlfwKeycode.GLFW_KEY_F9);
        add(AWTInputEvent.VK_DEAD_OGONEK, LwjglGlfwKeycode.GLFW_KEY_F10);
        add(AWTInputEvent.VK_DEAD_IOTA, LwjglGlfwKeycode.GLFW_KEY_F11);
        add(AWTInputEvent.VK_DEAD_VOICED_SOUND, LwjglGlfwKeycode.GLFW_KEY_F12);
        add(AWTInputEvent.VK_DEAD_SEMIVOICED_SOUND, LwjglGlfwKeycode.GLFW_KEY_NUM_LOCK);
        add(AWTInputEvent.VK_NUM_LOCK, LwjglGlfwKeycode.GLFW_KEY_KP_0);
        add(AWTInputEvent.VK_SCROLL_LOCK, LwjglGlfwKeycode.GLFW_KEY_KP_1);
        add(146, LwjglGlfwKeycode.GLFW_KEY_KP_2);
        add(147, LwjglGlfwKeycode.GLFW_KEY_KP_3);
        add(TarConstants.CHKSUM_OFFSET, LwjglGlfwKeycode.GLFW_KEY_KP_4);
        add(149, LwjglGlfwKeycode.GLFW_KEY_KP_5);
        add(AWTInputEvent.VK_AMPERSAND, LwjglGlfwKeycode.GLFW_KEY_KP_6);
        add(AWTInputEvent.VK_ASTERISK, LwjglGlfwKeycode.GLFW_KEY_KP_7);
        add(AWTInputEvent.VK_QUOTEDBL, LwjglGlfwKeycode.GLFW_KEY_KP_8);
        add(AWTInputEvent.VK_LESS, LwjglGlfwKeycode.GLFW_KEY_KP_9);
        add(AWTInputEvent.VK_PRINTSCREEN, LwjglGlfwKeycode.GLFW_KEY_KP_DIVIDE);
        add(155, LwjglGlfwKeycode.GLFW_KEY_KP_MULTIPLY);
        add(AWTInputEvent.VK_HELP, LwjglGlfwKeycode.GLFW_KEY_KP_SUBTRACT);
        add(AWTInputEvent.VK_META, LwjglGlfwKeycode.GLFW_KEY_KP_ADD);
        add(158, LwjglGlfwKeycode.GLFW_KEY_KP_DECIMAL);
        add(159, 44);
        add(AWTInputEvent.VK_GREATER, LwjglGlfwKeycode.GLFW_KEY_KP_ENTER);
        add(AWTInputEvent.VK_BRACELEFT, 61);
    }

    public static boolean containsKey(int keycode) {
        return getIndexByKey(keycode) >= 0;
    }

    public static String[] generateKeyName() {
        if (androidKeyNameArray == null) {
            androidKeyNameArray = new String[sAndroidKeycodes.length];
            int i = 0;
            while (true) {
                String[] strArr = androidKeyNameArray;
                if (i >= strArr.length) {
                    break;
                }
                strArr[i] = KeyEvent.keyCodeToString(sAndroidKeycodes[i]).replace("KEYCODE_", BuildConfig.FLAVOR);
                i++;
            }
        }
        return androidKeyNameArray;
    }

    public static void execKey(KeyEvent keyEvent) {
        execKey(keyEvent, getIndexByKey(keyEvent.getKeyCode()));
    }

    public static void execKey(KeyEvent keyEvent, int valueIndex) {
        CallbackBridge.holdingAlt = keyEvent.isAltPressed();
        CallbackBridge.holdingCapslock = keyEvent.isCapsLockOn();
        CallbackBridge.holdingCtrl = keyEvent.isCtrlPressed();
        CallbackBridge.holdingNumlock = keyEvent.isNumLockOn();
        CallbackBridge.holdingShift = keyEvent.isShiftPressed();
        System.out.println(keyEvent.getKeyCode() + " " + keyEvent.getDisplayLabel());
        CallbackBridge.sendKeyPress(getValueByIndex(valueIndex), (char) (keyEvent.getUnicodeChar() != 0 ? keyEvent.getUnicodeChar() : 0), 0, CallbackBridge.getCurrentMods(), keyEvent.getAction() == 0);
    }

    public static void execKeyIndex(int index) {
        CallbackBridge.sendKeyPress(getValueByIndex(index));
    }

    public static int getValueByIndex(int index) {
        return sLwjglKeycodes[index];
    }

    public static int getIndexByKey(int key) {
        return Arrays.binarySearch(sAndroidKeycodes, key);
    }

    public static short getValue(int key) {
        return sLwjglKeycodes[Arrays.binarySearch(sAndroidKeycodes, key)];
    }

    public static int getIndexByValue(int lwjglKey) {
        int i = 0;
        while (true) {
            short[] sArr = sLwjglKeycodes;
            if (i >= sArr.length) {
                return 0;
            }
            if (sArr[i] == lwjglKey) {
                return i;
            }
            i++;
        }
    }

    private static void add(int androidKeycode, short LWJGLKeycode) {
        int[] iArr = sAndroidKeycodes;
        int i = mTmpCount;
        iArr[i] = androidKeycode;
        sLwjglKeycodes[i] = LWJGLKeycode;
        mTmpCount = i + 1;
    }
}
