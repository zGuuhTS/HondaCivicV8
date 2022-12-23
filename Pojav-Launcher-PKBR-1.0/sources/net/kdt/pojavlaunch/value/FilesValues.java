package net.kdt.pojavlaunch.value;

import java.io.Serializable;

public class FilesValues implements Serializable {
    private String link;
    private String output;
    private Boolean overwrite;

    public FilesValues(String output2, String link2, Boolean overwrite2) {
        this.output = output2;
        this.link = link2;
        this.overwrite = overwrite2;
    }

    public String getOutput() {
        return this.output;
    }

    public String getLink() {
        return this.link;
    }

    public Boolean getOverwrite() {
        return this.overwrite;
    }
}
