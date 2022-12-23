package net.kdt.pojavlaunch;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}
