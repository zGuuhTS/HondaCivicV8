package net.kdt.pojavlaunch.customcontrols.gamepad;

import android.view.InputDevice;
import android.view.MotionEvent;
import androidx.core.view.InputDeviceCompat;
import net.kdt.pojavlaunch.utils.MathUtils;

public class GamepadJoystick {
    public static final int DIRECTION_EAST = 0;
    public static final int DIRECTION_NONE = -1;
    public static final int DIRECTION_NORTH = 2;
    public static final int DIRECTION_NORTH_EAST = 1;
    public static final int DIRECTION_NORTH_WEST = 3;
    public static final int DIRECTION_SOUTH = 6;
    public static final int DIRECTION_SOUTH_EAST = 7;
    public static final int DIRECTION_SOUTH_WEST = 5;
    public static final int DIRECTION_WEST = 4;
    private final int mHorizontalAxis;
    private final InputDevice mInputDevice;
    private final int mVerticalAxis;

    public GamepadJoystick(int horizontalAxis, int verticalAxis, InputDevice device) {
        this.mVerticalAxis = verticalAxis;
        this.mHorizontalAxis = horizontalAxis;
        this.mInputDevice = device;
    }

    public double getAngleRadian(MotionEvent event) {
        return -Math.atan2((double) getVerticalAxis(event), (double) getHorizontalAxis(event));
    }

    public double getAngleDegree(MotionEvent event) {
        double result = Math.toDegrees(getAngleRadian(event));
        if (result < 0.0d) {
            return result + 360.0d;
        }
        return result;
    }

    public double getMagnitude(MotionEvent event) {
        return (double) MathUtils.dist(0.0f, 0.0f, Math.abs(event.getAxisValue(this.mHorizontalAxis)), Math.abs(event.getAxisValue(this.mVerticalAxis)));
    }

    public float getVerticalAxis(MotionEvent event) {
        return applyDeadzone(event, this.mVerticalAxis);
    }

    public float getHorizontalAxis(MotionEvent event) {
        return applyDeadzone(event, this.mHorizontalAxis);
    }

    public static boolean isJoystickEvent(MotionEvent event) {
        return (event.getSource() & InputDeviceCompat.SOURCE_JOYSTICK) == 16777232 && event.getAction() == 2;
    }

    public int getHeightDirection(MotionEvent event) {
        if (getMagnitude(event) <= ((double) getDeadzone())) {
            return -1;
        }
        return ((int) ((getAngleDegree(event) + 22.5d) / 45.0d)) % 8;
    }

    public float getDeadzone() {
        try {
            return Math.max(this.mInputDevice.getMotionRange(this.mHorizontalAxis).getFlat() * 1.9f, 0.2f);
        } catch (Exception e) {
            return 0.2f;
        }
    }

    private float applyDeadzone(MotionEvent event, int axis) {
        double magnitude = getMagnitude(event);
        float deadzone = getDeadzone();
        if (magnitude < ((double) deadzone)) {
            return 0.0f;
        }
        return (float) ((((double) event.getAxisValue(axis)) / magnitude) * ((magnitude - ((double) deadzone)) / ((double) (1.0f - deadzone))));
    }
}
