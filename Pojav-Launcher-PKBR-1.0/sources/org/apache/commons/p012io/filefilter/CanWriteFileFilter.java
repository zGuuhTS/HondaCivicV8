package org.apache.commons.p012io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.CanWriteFileFilter */
public class CanWriteFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter CANNOT_WRITE;
    public static final IOFileFilter CAN_WRITE;

    static {
        CanWriteFileFilter canWriteFileFilter = new CanWriteFileFilter();
        CAN_WRITE = canWriteFileFilter;
        CANNOT_WRITE = new NotFileFilter(canWriteFileFilter);
    }

    protected CanWriteFileFilter() {
    }

    public boolean accept(File file) {
        return file.canWrite();
    }
}
