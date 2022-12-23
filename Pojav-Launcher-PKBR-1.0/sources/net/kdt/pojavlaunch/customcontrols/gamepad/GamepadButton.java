package net.kdt.pojavlaunch.customcontrols.gamepad;

import android.view.KeyEvent;

public class GamepadButton {
    public boolean isToggleable = false;
    public int[] keycodes;
    private boolean mIsDown = false;
    private boolean mIsToggled = false;

    public void update(KeyEvent event) {
        update(event.getAction() == 0);
    }

    public void update(boolean isKeyDown) {
        if (isKeyDown != this.mIsDown) {
            this.mIsDown = isKeyDown;
            if (!this.isToggleable) {
                Gamepad.sendInput(this.keycodes, isKeyDown);
            } else if (isKeyDown) {
                boolean z = !this.mIsToggled;
                this.mIsToggled = z;
                Gamepad.sendInput(this.keycodes, z);
            }
        }
    }

    public void resetButtonState() {
        if (this.mIsDown || this.mIsToggled) {
            Gamepad.sendInput(this.keycodes, false);
        }
        this.mIsDown = false;
        this.mIsToggled = false;
    }

    public boolean isDown() {
        return this.isToggleable ? this.mIsToggled : this.mIsDown;
    }
}
