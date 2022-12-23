package org.tukaani.p013xz.delta;

/* renamed from: org.tukaani.xz.delta.DeltaCoder */
abstract class DeltaCoder {
    static final int DISTANCE_MASK = 255;
    static final int DISTANCE_MAX = 256;
    static final int DISTANCE_MIN = 1;
    final int distance;
    final byte[] history = new byte[256];
    int pos = 0;

    DeltaCoder(int i) {
        if (i < 1 || i > 256) {
            throw new IllegalArgumentException();
        }
        this.distance = i;
    }
}
