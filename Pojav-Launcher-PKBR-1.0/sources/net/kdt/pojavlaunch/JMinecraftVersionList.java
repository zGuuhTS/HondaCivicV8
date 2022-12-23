package net.kdt.pojavlaunch;

import java.util.Map;
import net.kdt.pojavlaunch.value.DependentLibrary;
import net.kdt.pojavlaunch.value.MinecraftClientInfo;

public class JMinecraftVersionList {
    public static final String TYPE_OLD_ALPHA = "old_alpha";
    public static final String TYPE_OLD_BETA = "old_beta";
    public static final String TYPE_RELEASE = "release";
    public static final String TYPE_SNAPSHOT = "snapshot";
    public Map<String, String> latest;
    public Version[] versions;

    public static class Arguments {
        public Object[] game;
        public Object[] jvm;

        public static class ArgValue {
            public ArgRules[] rules;
            public String value;
            public String[] values;

            public static class ArgRules {
                public String action;
                public String features;

                /* renamed from: os */
                public ArgOS f16os;

                public static class ArgOS {
                    public String name;
                    public String version;
                }
            }
        }
    }

    public static class AssetIndex extends FileProperties {
        public long totalSize;
    }

    public static class FileProperties {

        /* renamed from: id */
        public String f17id;
        public String sha1;
        public long size;
        public String url;
    }

    public static class JavaVersionInfo {
        public String component;
        public int majorVersion;
    }

    public static class LoggingConfig {
        public LoggingClientConfig client;

        public static class LoggingClientConfig {
            public String argument;
            public FileProperties file;
            public String type;
        }
    }

    public static class Version extends FileProperties {
        public Arguments arguments;
        public AssetIndex assetIndex;
        public String assets;
        public Map<String, MinecraftClientInfo> downloads;
        public String inheritsFrom;
        public JavaVersionInfo javaVersion;
        public DependentLibrary[] libraries;
        public LoggingConfig logging;
        public String mainClass;
        public String minecraftArguments;
        public int minimumLauncherVersion;
        public DependentLibrary optifineLib;
        public String releaseTime;
        public String time;
        public String type;
    }
}
