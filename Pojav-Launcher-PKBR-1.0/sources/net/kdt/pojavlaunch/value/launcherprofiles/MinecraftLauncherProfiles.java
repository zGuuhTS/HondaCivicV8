package net.kdt.pojavlaunch.value.launcherprofiles;

import java.util.HashMap;
import java.util.Map;
import net.kdt.pojavlaunch.Tools;

public class MinecraftLauncherProfiles {
    public int analyticsFailcount;
    public Map<String, MinecraftAuthenticationDatabase> authenticationDatabase;
    public String clientToken;
    public Map<String, MinecraftProfile> profiles = new HashMap();
    public boolean profilesWereMigrated;
    public MinecraftSelectedUser selectedUser;
    public MinecraftLauncherSettings settings;

    public String toJson() {
        return Tools.GLOBAL_GSON.toJson((Object) this);
    }
}
