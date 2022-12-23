package net.kdt.pojavlaunch.value;

import net.kdt.pojavlaunch.JMinecraftVersionList;

public class DependentLibrary {
    public LibraryDownloads downloads;
    public String name;
    public JMinecraftVersionList.Arguments.ArgValue.ArgRules[] rules;
    public String url;

    public static class LibraryDownloads {
        public MinecraftLibraryArtifact artifact;

        public LibraryDownloads(MinecraftLibraryArtifact artifact2) {
            this.artifact = artifact2;
        }
    }
}
