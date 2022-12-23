package net.kdt.pojavlaunch.customcontrols.keyboard;

import net.kdt.pojavlaunch.AWTInputEvent;
import org.lwjgl.glfw.CallbackBridge;

public class LwjglCharSender implements CharacterSenderStrategy {
    public void sendBackspace() {
        CallbackBridge.sendKeycode(AWTInputEvent.VK_JAPANESE_KATAKANA, 8, 0, 0, true);
        CallbackBridge.sendKeycode(AWTInputEvent.VK_JAPANESE_KATAKANA, 8, 0, 0, false);
    }

    public void sendEnter() {
        CallbackBridge.sendKeyPress(257);
    }

    public void sendChar(char character) {
        CallbackBridge.sendChar(character, 0);
    }
}
