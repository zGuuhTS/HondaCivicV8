package org.apache.commons.p012io;

import java.io.File;
import java.io.IOException;

/* renamed from: org.apache.commons.io.FileExistsException */
public class FileExistsException extends IOException {
    private static final long serialVersionUID = 1;

    public FileExistsException() {
    }

    public FileExistsException(File file) {
        super("File " + file + " exists");
    }

    public FileExistsException(String str) {
        super(str);
    }
}
