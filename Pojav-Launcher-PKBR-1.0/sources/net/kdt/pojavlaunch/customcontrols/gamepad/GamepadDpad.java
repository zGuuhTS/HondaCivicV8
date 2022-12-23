package net.kdt.pojavlaunch.customcontrols.gamepad;

import android.view.KeyEvent;
import android.view.MotionEvent;
import androidx.core.view.InputDeviceCompat;

public class GamepadDpad {
    private int mLastKeycode = 23;

    public int[] convertEvent(MotionEvent event) {
        float xaxis = event.getAxisValue(15);
        float yaxis = event.getAxisValue(16);
        int action = 0;
        if (Float.compare(xaxis, -1.0f) == 0) {
            this.mLastKeycode = 21;
        } else if (Float.compare(xaxis, 1.0f) == 0) {
            this.mLastKeycode = 22;
        } else if (Float.compare(yaxis, -1.0f) == 0) {
            this.mLastKeycode = 19;
        } else if (Float.compare(yaxis, 1.0f) == 0) {
            this.mLastKeycode = 20;
        } else {
            action = 1;
        }
        return new int[]{this.mLastKeycode, action};
    }

    public static boolean isDpadEvent(MotionEvent event) {
        return (event.getSource() & InputDeviceCompat.SOURCE_JOYSTICK) == 16777232;
    }

    public static boolean isDpadEvent(KeyEvent event) {
        return event.isFromSource(InputDeviceCompat.SOURCE_GAMEPAD) && event.getDevice().getKeyboardType() != 2;
    }
}
