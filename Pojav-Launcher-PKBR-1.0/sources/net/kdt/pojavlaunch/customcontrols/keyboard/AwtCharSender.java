package net.kdt.pojavlaunch.customcontrols.keyboard;

import net.kdt.pojavlaunch.AWTInputBridge;

public class AwtCharSender implements CharacterSenderStrategy {
    public void sendBackspace() {
        AWTInputBridge.sendKey(' ', 8);
    }

    public void sendEnter() {
        AWTInputBridge.sendKey(' ', 10);
    }

    public void sendChar(char character) {
        AWTInputBridge.sendChar(character);
    }
}
