package org.apache.commons.compress.archivers.p009ar;

import java.io.File;
import java.util.Date;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* renamed from: org.apache.commons.compress.archivers.ar.ArArchiveEntry */
public class ArArchiveEntry implements ArchiveEntry {
    private static final int DEFAULT_MODE = 33188;
    public static final String HEADER = "!<arch>\n";
    public static final String TRAILER = "`\n";
    private final int groupId;
    private final long lastModified;
    private final long length;
    private final int mode;
    private final String name;
    private final int userId;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ArArchiveEntry(File file, String str) {
        this(str, file.isFile() ? file.length() : 0, 0, 0, 33188, file.lastModified() / 1000);
    }

    public ArArchiveEntry(String str, long j) {
        this(str, j, 0, 0, 33188, System.currentTimeMillis() / 1000);
    }

    public ArArchiveEntry(String str, long j, int i, int i2, int i3, long j2) {
        this.name = str;
        this.length = j;
        this.userId = i;
        this.groupId = i2;
        this.mode = i3;
        this.lastModified = j2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        String str = this.name;
        String str2 = ((ArArchiveEntry) obj).name;
        if (str == null) {
            if (str2 != null) {
                return false;
            }
        } else if (!str.equals(str2)) {
            return false;
        }
        return true;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    public Date getLastModifiedDate() {
        return new Date(getLastModified() * 1000);
    }

    public long getLength() {
        return this.length;
    }

    public int getMode() {
        return this.mode;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return getLength();
    }

    public int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        String str = this.name;
        return (str == null ? 0 : str.hashCode()) + 31;
    }

    public boolean isDirectory() {
        return false;
    }
}
