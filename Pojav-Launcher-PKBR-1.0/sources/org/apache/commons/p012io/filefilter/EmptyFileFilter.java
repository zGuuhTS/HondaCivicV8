package org.apache.commons.p012io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.EmptyFileFilter */
public class EmptyFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter EMPTY;
    public static final IOFileFilter NOT_EMPTY;

    static {
        EmptyFileFilter emptyFileFilter = new EmptyFileFilter();
        EMPTY = emptyFileFilter;
        NOT_EMPTY = new NotFileFilter(emptyFileFilter);
    }

    protected EmptyFileFilter() {
    }

    public boolean accept(File file) {
        if (!file.isDirectory()) {
            return file.length() == 0;
        }
        File[] listFiles = file.listFiles();
        return listFiles == null || listFiles.length == 0;
    }
}
