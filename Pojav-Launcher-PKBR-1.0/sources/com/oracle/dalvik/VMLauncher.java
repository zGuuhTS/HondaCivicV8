package com.oracle.dalvik;

public final class VMLauncher {
    public static native int launchJVM(String[] strArr);

    private VMLauncher() {
    }
}
