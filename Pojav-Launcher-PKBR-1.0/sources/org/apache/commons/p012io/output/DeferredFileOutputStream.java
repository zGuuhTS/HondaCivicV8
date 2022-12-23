package org.apache.commons.p012io.output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.p012io.IOUtils;

/* renamed from: org.apache.commons.io.output.DeferredFileOutputStream */
public class DeferredFileOutputStream extends ThresholdingOutputStream {
    private boolean closed;
    private OutputStream currentOutputStream;
    private final File directory;
    private ByteArrayOutputStream memoryOutputStream;
    private File outputFile;
    private final String prefix;
    private final String suffix;

    public DeferredFileOutputStream(int i, File file) {
        this(i, file, (String) null, (String) null, (File) null);
    }

    private DeferredFileOutputStream(int i, File file, String str, String str2, File file2) {
        super(i);
        this.closed = false;
        this.outputFile = file;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.memoryOutputStream = byteArrayOutputStream;
        this.currentOutputStream = byteArrayOutputStream;
        this.prefix = str;
        this.suffix = str2;
        this.directory = file2;
    }

    public DeferredFileOutputStream(int i, String str, String str2, File file) {
        this(i, (File) null, str, str2, file);
        if (str == null) {
            throw new IllegalArgumentException("Temporary file prefix is missing");
        }
    }

    public void close() throws IOException {
        super.close();
        this.closed = true;
    }

    public byte[] getData() {
        ByteArrayOutputStream byteArrayOutputStream = this.memoryOutputStream;
        if (byteArrayOutputStream != null) {
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    public File getFile() {
        return this.outputFile;
    }

    /* access modifiers changed from: protected */
    public OutputStream getStream() throws IOException {
        return this.currentOutputStream;
    }

    public boolean isInMemory() {
        return !isThresholdExceeded();
    }

    /* access modifiers changed from: protected */
    public void thresholdReached() throws IOException {
        String str = this.prefix;
        if (str != null) {
            this.outputFile = File.createTempFile(str, this.suffix, this.directory);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(this.outputFile);
        this.memoryOutputStream.writeTo(fileOutputStream);
        this.currentOutputStream = fileOutputStream;
        this.memoryOutputStream = null;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        if (!this.closed) {
            throw new IOException("Stream not closed");
        } else if (isInMemory()) {
            this.memoryOutputStream.writeTo(outputStream);
        } else {
            FileInputStream fileInputStream = new FileInputStream(this.outputFile);
            try {
                IOUtils.copy((InputStream) fileInputStream, outputStream);
            } finally {
                IOUtils.closeQuietly((InputStream) fileInputStream);
            }
        }
    }
}
