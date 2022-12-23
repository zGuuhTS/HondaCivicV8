package org.apache.commons.p012io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.DirectoryFileFilter */
public class DirectoryFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter DIRECTORY;
    public static final IOFileFilter INSTANCE;

    static {
        DirectoryFileFilter directoryFileFilter = new DirectoryFileFilter();
        DIRECTORY = directoryFileFilter;
        INSTANCE = directoryFileFilter;
    }

    protected DirectoryFileFilter() {
    }

    public boolean accept(File file) {
        return file.isDirectory();
    }
}
