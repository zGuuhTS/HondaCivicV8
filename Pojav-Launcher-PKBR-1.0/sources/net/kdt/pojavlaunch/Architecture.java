package net.kdt.pojavlaunch;

import android.os.Build;
import top.defaults.checkerboarddrawable.BuildConfig;

public class Architecture {
    public static int ARCH_ARM = 2;
    public static int ARCH_ARM64 = 1;
    public static int ARCH_X86 = 4;
    public static int ARCH_X86_64 = 8;
    public static int UNSUPPORTED_ARCH = -1;

    public static boolean is64BitsDevice() {
        return Build.SUPPORTED_64_BIT_ABIS.length != 0;
    }

    public static boolean is32BitsDevice() {
        return !is64BitsDevice();
    }

    public static int getDeviceArchitecture() {
        return isx86Device() ? is64BitsDevice() ? ARCH_X86_64 : ARCH_X86 : is64BitsDevice() ? ARCH_ARM64 : ARCH_ARM;
    }

    public static boolean isx86Device() {
        String[] ABI = is64BitsDevice() ? Build.SUPPORTED_64_BIT_ABIS : Build.SUPPORTED_32_BIT_ABIS;
        int comparedArch = is64BitsDevice() ? ARCH_X86_64 : ARCH_X86;
        for (String str : ABI) {
            if (archAsInt(str) == comparedArch) {
                return true;
            }
        }
        return false;
    }

    public static boolean isArmDevice() {
        return !isx86Device();
    }

    public static int archAsInt(String arch) {
        String arch2 = arch.toLowerCase().trim().replace(" ", BuildConfig.FLAVOR);
        if (arch2.contains("arm64") || arch2.equals("aarch64")) {
            return ARCH_ARM64;
        }
        if (arch2.contains("arm") || arch2.equals("aarch32")) {
            return ARCH_ARM;
        }
        if (arch2.contains("x86_64") || arch2.contains("amd64")) {
            return ARCH_X86_64;
        }
        if (arch2.contains("x86") || (arch2.startsWith("i") && arch2.endsWith("86"))) {
            return ARCH_X86;
        }
        return UNSUPPORTED_ARCH;
    }

    public static String archAsString(int arch) {
        if (arch == ARCH_ARM64) {
            return "arm64";
        }
        if (arch == ARCH_ARM) {
            return "arm";
        }
        if (arch == ARCH_X86_64) {
            return "x86_64";
        }
        if (arch == ARCH_X86) {
            return "x86";
        }
        return "UNSUPPORTED_ARCH";
    }
}
