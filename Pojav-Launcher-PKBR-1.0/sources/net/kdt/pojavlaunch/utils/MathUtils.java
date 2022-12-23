package net.kdt.pojavlaunch.utils;

public class MathUtils {
    public static float map(float x, float in_min, float in_max, float out_min, float out_max) {
        return (((x - in_min) * (out_max - out_min)) / (in_max - in_min)) + out_min;
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.hypot((double) (x2 - x1), (double) (y2 - y1));
    }
}
