package org.apache.commons.p012io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.Serializable;

/* renamed from: org.apache.commons.io.filefilter.DelegateFileFilter */
public class DelegateFileFilter extends AbstractFileFilter implements Serializable {
    private final FileFilter fileFilter;
    private final FilenameFilter filenameFilter;

    public DelegateFileFilter(FileFilter fileFilter2) {
        if (fileFilter2 != null) {
            this.fileFilter = fileFilter2;
            this.filenameFilter = null;
            return;
        }
        throw new IllegalArgumentException("The FileFilter must not be null");
    }

    public DelegateFileFilter(FilenameFilter filenameFilter2) {
        if (filenameFilter2 != null) {
            this.filenameFilter = filenameFilter2;
            this.fileFilter = null;
            return;
        }
        throw new IllegalArgumentException("The FilenameFilter must not be null");
    }

    public boolean accept(File file) {
        FileFilter fileFilter2 = this.fileFilter;
        return fileFilter2 != null ? fileFilter2.accept(file) : super.accept(file);
    }

    public boolean accept(File file, String str) {
        FilenameFilter filenameFilter2 = this.filenameFilter;
        return filenameFilter2 != null ? filenameFilter2.accept(file, str) : super.accept(file, str);
    }

    public String toString() {
        Object obj = this.fileFilter;
        if (obj == null) {
            obj = this.filenameFilter;
        }
        String obj2 = obj.toString();
        return super.toString() + "(" + obj2 + ")";
    }
}
