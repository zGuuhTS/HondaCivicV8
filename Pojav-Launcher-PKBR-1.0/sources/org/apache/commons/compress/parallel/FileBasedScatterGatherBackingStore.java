package org.apache.commons.compress.parallel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileBasedScatterGatherBackingStore implements ScatterGatherBackingStore {
    private boolean closed;

    /* renamed from: os */
    private final FileOutputStream f167os;
    private final File target;

    public FileBasedScatterGatherBackingStore(File file) throws FileNotFoundException {
        this.target = file;
        this.f167os = new FileOutputStream(file);
    }

    public void close() throws IOException {
        closeForWriting();
        this.target.delete();
    }

    public void closeForWriting() throws IOException {
        if (!this.closed) {
            this.f167os.close();
            this.closed = true;
        }
    }

    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.target);
    }

    public void writeOut(byte[] bArr, int i, int i2) throws IOException {
        this.f167os.write(bArr, i, i2);
    }
}
