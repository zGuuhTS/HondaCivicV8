package org.apache.commons.p012io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.p012io.FileUtils;

/* renamed from: org.apache.commons.io.filefilter.AgeFileFilter */
public class AgeFileFilter extends AbstractFileFilter implements Serializable {
    private final boolean acceptOlder;
    private final long cutoff;

    public AgeFileFilter(long j) {
        this(j, true);
    }

    public AgeFileFilter(long j, boolean z) {
        this.acceptOlder = z;
        this.cutoff = j;
    }

    public AgeFileFilter(File file) {
        this(file, true);
    }

    public AgeFileFilter(File file, boolean z) {
        this(file.lastModified(), z);
    }

    public AgeFileFilter(Date date) {
        this(date, true);
    }

    public AgeFileFilter(Date date, boolean z) {
        this(date.getTime(), z);
    }

    public boolean accept(File file) {
        boolean isFileNewer = FileUtils.isFileNewer(file, this.cutoff);
        return this.acceptOlder ? !isFileNewer : isFileNewer;
    }

    public String toString() {
        String str = this.acceptOlder ? "<=" : ">";
        return super.toString() + "(" + str + this.cutoff + ")";
    }
}
