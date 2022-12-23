package net.kdt.pojavlaunch.progresskeeper;

public interface ProgressListener {
    void onProgressEnded();

    void onProgressStarted();

    void onProgressUpdated(int i, int i2, Object... objArr);
}
