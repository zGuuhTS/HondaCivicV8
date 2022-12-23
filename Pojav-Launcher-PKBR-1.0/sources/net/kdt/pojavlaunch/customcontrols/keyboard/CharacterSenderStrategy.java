package net.kdt.pojavlaunch.customcontrols.keyboard;

public interface CharacterSenderStrategy {
    void sendBackspace();

    void sendChar(char c);

    void sendEnter();
}
