package net.kdt.pojavlaunch.value;

import net.kdt.pojavlaunch.JMinecraftVersionList;

public class ForgeInstallProfile {
    public ForgeInstallProperties install;
    public String json;
    public String minecraft;
    public String path;
    public String version;
    public JMinecraftVersionList.Version versionInfo;

    public static class ForgeInstallProperties {
        public String filePath;
        public String minecraft;
        public String path;
        public String profileName;
        public String target;
        public String version;
    }
}
