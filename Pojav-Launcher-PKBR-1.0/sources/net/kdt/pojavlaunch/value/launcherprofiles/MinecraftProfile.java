package net.kdt.pojavlaunch.value.launcherprofiles;

public class MinecraftProfile {
    public String controlFile;
    public String created;
    public String gameDir;
    public String icon;
    public String javaArgs;
    public String javaDir;
    public String lastUsed;
    public String lastVersionId;
    public String logConfig;
    public boolean logConfigIsXML;
    public String name;
    public String pojavRendererName;
    public MinecraftResolution[] resolution;
    public String type;

    public static MinecraftProfile createTemplate() {
        MinecraftProfile TEMPLATE = new MinecraftProfile();
        TEMPLATE.name = "New";
        TEMPLATE.lastVersionId = "latest-release";
        return TEMPLATE;
    }

    public MinecraftProfile() {
    }

    public MinecraftProfile(MinecraftProfile profile) {
        this.name = profile.name;
        this.type = profile.type;
        this.created = profile.created;
        this.lastUsed = profile.lastUsed;
        this.icon = profile.icon;
        this.lastVersionId = profile.lastVersionId;
        this.gameDir = profile.gameDir;
        this.javaDir = profile.javaDir;
        this.javaArgs = profile.javaArgs;
        this.logConfig = profile.logConfig;
        this.logConfigIsXML = profile.logConfigIsXML;
        this.pojavRendererName = profile.pojavRendererName;
        this.controlFile = profile.controlFile;
        this.resolution = profile.resolution;
    }
}
