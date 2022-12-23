package net.kdt.pojavlaunch;

import android.view.MotionEvent;

public class TapDetector {
    public static final int DETECTION_METHOD_BOTH = 3;
    public static final int DETECTION_METHOD_DOWN = 1;
    public static final int DETECTION_METHOD_UP = 2;
    private static final int TAP_MAX_DELTA_MS = 300;
    private static final int TAP_MIN_DELTA_MS = 10;
    private static final int TAP_SLOP_SQUARE_PX = ((int) Math.pow((double) Tools.dpToPx(100.0f), 2.0d));
    private int mCurrentTapNumber = 0;
    private final int mDetectionMethod;
    private long mLastEventTime = 0;
    private float mLastX = 9999.0f;
    private float mLastY = 9999.0f;
    private final int mTapNumberToDetect;

    public TapDetector(int tapNumberToDetect, int detectionMethod) {
        this.mDetectionMethod = detectionMethod;
        this.mTapNumberToDetect = detectBothTouch() ? tapNumberToDetect * 2 : tapNumberToDetect;
    }

    public boolean onTouchEvent(MotionEvent e) {
        int eventAction = e.getActionMasked();
        int pointerIndex = -1;
        if (detectDownTouch()) {
            if (eventAction == 0) {
                pointerIndex = 0;
            } else if (eventAction == 5) {
                pointerIndex = e.getActionIndex();
            }
        }
        if (detectUpTouch()) {
            if (eventAction == 1) {
                pointerIndex = 0;
            } else if (eventAction == 6) {
                pointerIndex = e.getActionIndex();
            }
        }
        if (pointerIndex == -1) {
            return false;
        }
        float eventX = e.getX(pointerIndex);
        float eventY = e.getY(pointerIndex);
        long eventTime = e.getEventTime();
        long deltaTime = eventTime - this.mLastEventTime;
        int deltaX = ((int) this.mLastX) - ((int) eventX);
        int deltaY = ((int) this.mLastY) - ((int) eventY);
        this.mLastEventTime = eventTime;
        this.mLastX = eventX;
        this.mLastY = eventY;
        if (this.mCurrentTapNumber > 0 && (deltaTime < 10 || deltaTime > 300 || (deltaX * deltaX) + (deltaY * deltaY) > TAP_SLOP_SQUARE_PX)) {
            this.mCurrentTapNumber = 0;
        }
        int i = this.mCurrentTapNumber + 1;
        this.mCurrentTapNumber = i;
        if (i < this.mTapNumberToDetect) {
            return false;
        }
        resetTapDetectionState();
        return true;
    }

    private void resetTapDetectionState() {
        this.mCurrentTapNumber = 0;
        this.mLastEventTime = 0;
        this.mLastX = 9999.0f;
        this.mLastY = 9999.0f;
    }

    private boolean detectDownTouch() {
        return (this.mDetectionMethod & 1) == 1;
    }

    private boolean detectUpTouch() {
        return (this.mDetectionMethod & 2) == 2;
    }

    private boolean detectBothTouch() {
        return this.mDetectionMethod == 3;
    }
}
