package net.kdt.pojavlaunch.multirt;

import java.util.Objects;

public class Runtime {
    public String arch;
    public int javaVersion;
    public String name;
    public String versionString;

    public Runtime(String name2) {
        this.name = name2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.name.equals(((Runtime) o).name);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name});
    }
}
