package net.kdt.pojavlaunch.customcontrols.gamepad;

public class GamepadMap {
    public static final int MOUSE_SCROLL_DOWN = -1;
    public static final int MOUSE_SCROLL_UP = -2;
    public GamepadButton BUTTON_A = new GamepadButton();
    public GamepadButton BUTTON_B = new GamepadButton();
    public GamepadButton BUTTON_SELECT = new GamepadButton();
    public GamepadButton BUTTON_START = new GamepadButton();
    public GamepadButton BUTTON_X = new GamepadButton();
    public GamepadButton BUTTON_Y = new GamepadButton();
    public int[] DIRECTION_BACKWARD;
    public int[] DIRECTION_FORWARD;
    public int[] DIRECTION_LEFT;
    public int[] DIRECTION_RIGHT;
    public GamepadButton DPAD_DOWN = new GamepadButton();
    public GamepadButton DPAD_LEFT = new GamepadButton();
    public GamepadButton DPAD_RIGHT = new GamepadButton();
    public GamepadButton DPAD_UP = new GamepadButton();
    public GamepadButton SHOULDER_LEFT = new GamepadButton();
    public GamepadButton SHOULDER_RIGHT = new GamepadButton();
    public GamepadButton THUMBSTICK_LEFT = new GamepadButton();
    public GamepadButton THUMBSTICK_RIGHT = new GamepadButton();
    public GamepadButton TRIGGER_LEFT = new GamepadButton();
    public GamepadButton TRIGGER_RIGHT = new GamepadButton();

    public void resetPressedState() {
        this.BUTTON_A.resetButtonState();
        this.BUTTON_B.resetButtonState();
        this.BUTTON_X.resetButtonState();
        this.BUTTON_Y.resetButtonState();
        this.BUTTON_START.resetButtonState();
        this.BUTTON_SELECT.resetButtonState();
        this.TRIGGER_LEFT.resetButtonState();
        this.TRIGGER_RIGHT.resetButtonState();
        this.SHOULDER_LEFT.resetButtonState();
        this.SHOULDER_RIGHT.resetButtonState();
        this.THUMBSTICK_LEFT.resetButtonState();
        this.THUMBSTICK_RIGHT.resetButtonState();
        this.DPAD_UP.resetButtonState();
        this.DPAD_RIGHT.resetButtonState();
        this.DPAD_DOWN.resetButtonState();
        this.DPAD_LEFT.resetButtonState();
    }

    public static GamepadMap getDefaultGameMap() {
        GamepadMap gameMap = new GamepadMap();
        int[] iArr = {32};
        gameMap.BUTTON_A.keycodes = iArr;
        int[] iArr2 = {81};
        gameMap.BUTTON_B.keycodes = iArr2;
        int[] iArr3 = {69};
        gameMap.BUTTON_X.keycodes = iArr3;
        int[] iArr4 = {70};
        gameMap.BUTTON_Y.keycodes = iArr4;
        gameMap.DIRECTION_FORWARD = new int[]{87};
        gameMap.DIRECTION_BACKWARD = new int[]{83};
        gameMap.DIRECTION_RIGHT = new int[]{68};
        gameMap.DIRECTION_LEFT = new int[]{65};
        int[] iArr5 = {340};
        gameMap.DPAD_UP.keycodes = iArr5;
        int[] iArr6 = {79};
        gameMap.DPAD_DOWN.keycodes = iArr6;
        int[] iArr7 = {75};
        gameMap.DPAD_RIGHT.keycodes = iArr7;
        int[] iArr8 = {74};
        gameMap.DPAD_LEFT.keycodes = iArr8;
        int[] iArr9 = {-2};
        gameMap.SHOULDER_LEFT.keycodes = iArr9;
        int[] iArr10 = {-1};
        gameMap.SHOULDER_RIGHT.keycodes = iArr10;
        int[] iArr11 = {1};
        gameMap.TRIGGER_LEFT.keycodes = iArr11;
        int[] iArr12 = {0};
        gameMap.TRIGGER_RIGHT.keycodes = iArr12;
        int[] iArr13 = {341};
        gameMap.THUMBSTICK_LEFT.keycodes = iArr13;
        int[] iArr14 = {340};
        gameMap.THUMBSTICK_RIGHT.keycodes = iArr14;
        gameMap.THUMBSTICK_RIGHT.isToggleable = true;
        int[] iArr15 = {256};
        gameMap.BUTTON_START.keycodes = iArr15;
        int[] iArr16 = {258};
        gameMap.BUTTON_SELECT.keycodes = iArr16;
        return gameMap;
    }

    public static GamepadMap getDefaultMenuMap() {
        GamepadMap menuMap = new GamepadMap();
        int[] iArr = {0};
        menuMap.BUTTON_A.keycodes = iArr;
        int[] iArr2 = {256};
        menuMap.BUTTON_B.keycodes = iArr2;
        int[] iArr3 = {1};
        menuMap.BUTTON_X.keycodes = iArr3;
        menuMap.BUTTON_Y.keycodes = new int[]{340, 1};
        menuMap.DIRECTION_FORWARD = new int[]{-2, -2, -2, -2, -2};
        menuMap.DIRECTION_BACKWARD = new int[]{-1, -1, -1, -1, -1};
        menuMap.DIRECTION_RIGHT = new int[0];
        menuMap.DIRECTION_LEFT = new int[0];
        menuMap.DPAD_UP.keycodes = new int[0];
        int[] iArr4 = {79};
        menuMap.DPAD_DOWN.keycodes = iArr4;
        int[] iArr5 = {75};
        menuMap.DPAD_RIGHT.keycodes = iArr5;
        int[] iArr6 = {74};
        menuMap.DPAD_LEFT.keycodes = iArr6;
        int[] iArr7 = {-2};
        menuMap.SHOULDER_LEFT.keycodes = iArr7;
        int[] iArr8 = {-1};
        menuMap.SHOULDER_RIGHT.keycodes = iArr8;
        menuMap.TRIGGER_LEFT.keycodes = new int[0];
        menuMap.TRIGGER_RIGHT.keycodes = new int[0];
        menuMap.THUMBSTICK_LEFT.keycodes = new int[0];
        menuMap.THUMBSTICK_RIGHT.keycodes = new int[0];
        int[] iArr9 = {256};
        menuMap.BUTTON_START.keycodes = iArr9;
        menuMap.BUTTON_SELECT.keycodes = new int[0];
        return menuMap;
    }

    public GamepadButton[] getButtons() {
        return new GamepadButton[]{this.BUTTON_A, this.BUTTON_B, this.BUTTON_X, this.BUTTON_Y, this.BUTTON_SELECT, this.BUTTON_START, this.TRIGGER_LEFT, this.TRIGGER_RIGHT, this.SHOULDER_LEFT, this.SHOULDER_RIGHT, this.THUMBSTICK_LEFT, this.THUMBSTICK_RIGHT, this.DPAD_UP, this.DPAD_RIGHT, this.DPAD_DOWN, this.DPAD_LEFT};
    }

    public static GamepadMap getEmptyMap() {
        GamepadMap emptyMap = new GamepadMap();
        for (GamepadButton button : emptyMap.getButtons()) {
            button.keycodes = new int[0];
        }
        emptyMap.DIRECTION_LEFT = new int[0];
        emptyMap.DIRECTION_FORWARD = new int[0];
        emptyMap.DIRECTION_RIGHT = new int[0];
        emptyMap.DIRECTION_BACKWARD = new int[0];
        return emptyMap;
    }
}
