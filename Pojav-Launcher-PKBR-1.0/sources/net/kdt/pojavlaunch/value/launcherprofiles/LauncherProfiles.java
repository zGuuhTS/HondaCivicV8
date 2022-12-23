package net.kdt.pojavlaunch.value.launcherprofiles;

import java.util.UUID;

public class LauncherProfiles {
    public static MinecraftLauncherProfiles mainProfileJson;

    public static MinecraftLauncherProfiles update() {
        try {
            mainProfileJson = new MinecraftLauncherProfiles();
            MinecraftProfile mTempProfile = new MinecraftProfile();
            mTempProfile.lastVersionId = "1.12.2-forge-14.23.5.2860";
            mTempProfile.name = "1.12.2-forge-14.23.5.2860";
            mTempProfile.controlFile = null;
            mTempProfile.javaArgs = null;
            mTempProfile.gameDir = null;
            mainProfileJson.profiles.put(UUID.randomUUID().toString(), mTempProfile);
            return mainProfileJson;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
