package org.apache.commons.p012io.filefilter;

import java.io.File;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.CanReadFileFilter */
public class CanReadFileFilter extends AbstractFileFilter implements Serializable {
    public static final IOFileFilter CANNOT_READ;
    public static final IOFileFilter CAN_READ;
    public static final IOFileFilter READ_ONLY;

    static {
        CanReadFileFilter canReadFileFilter = new CanReadFileFilter();
        CAN_READ = canReadFileFilter;
        CANNOT_READ = new NotFileFilter(canReadFileFilter);
        READ_ONLY = new AndFileFilter(canReadFileFilter, CanWriteFileFilter.CANNOT_WRITE);
    }

    protected CanReadFileFilter() {
    }

    public boolean accept(File file) {
        return file.canRead();
    }
}
