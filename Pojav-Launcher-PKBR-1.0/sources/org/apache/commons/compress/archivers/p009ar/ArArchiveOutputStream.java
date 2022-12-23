package org.apache.commons.compress.archivers.p009ar;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.ArchiveUtils;
import top.defaults.checkerboarddrawable.BuildConfig;

/* renamed from: org.apache.commons.compress.archivers.ar.ArArchiveOutputStream */
public class ArArchiveOutputStream extends ArchiveOutputStream {
    public static final int LONGFILE_BSD = 1;
    public static final int LONGFILE_ERROR = 0;
    private long entryOffset = 0;
    private boolean finished = false;
    private boolean haveUnclosedEntry = false;
    private int longFileMode = 0;
    private final OutputStream out;
    private ArArchiveEntry prevEntry;

    public ArArchiveOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    private long fill(long j, long j2, char c) throws IOException {
        long j3 = j2 - j;
        if (j3 > 0) {
            for (int i = 0; ((long) i) < j3; i++) {
                write(c);
            }
        }
        return j2;
    }

    private long write(String str) throws IOException {
        byte[] bytes = str.getBytes("ascii");
        write(bytes);
        return (long) bytes.length;
    }

    private long writeArchiveHeader() throws IOException {
        byte[] asciiBytes = ArchiveUtils.toAsciiBytes(ArArchiveEntry.HEADER);
        this.out.write(asciiBytes);
        return (long) asciiBytes.length;
    }

    private long writeEntryHeader(ArArchiveEntry arArchiveEntry) throws IOException {
        boolean z;
        long j;
        String name = arArchiveEntry.getName();
        if (this.longFileMode != 0 || name.length() <= 16) {
            int i = 0;
            if (1 != this.longFileMode || (name.length() <= 16 && !name.contains(" "))) {
                j = write(name) + 0;
                z = false;
            } else {
                j = write("#1/" + String.valueOf(name.length())) + 0;
                z = true;
            }
            long fill = fill(j, 16, ' ');
            String str = BuildConfig.FLAVOR + arArchiveEntry.getLastModified();
            if (str.length() <= 12) {
                long fill2 = fill(fill + write(str), 28, ' ');
                String str2 = BuildConfig.FLAVOR + arArchiveEntry.getUserId();
                if (str2.length() <= 6) {
                    long fill3 = fill(fill2 + write(str2), 34, ' ');
                    String str3 = BuildConfig.FLAVOR + arArchiveEntry.getGroupId();
                    if (str3.length() <= 6) {
                        long fill4 = fill(fill3 + write(str3), 40, ' ');
                        String str4 = BuildConfig.FLAVOR + Integer.toString(arArchiveEntry.getMode(), 8);
                        if (str4.length() <= 8) {
                            long fill5 = fill(fill4 + write(str4), 48, ' ');
                            long length = arArchiveEntry.getLength();
                            if (z) {
                                i = name.length();
                            }
                            String valueOf = String.valueOf(length + ((long) i));
                            if (valueOf.length() <= 10) {
                                long fill6 = fill(fill5 + write(valueOf), 58, ' ') + write(ArArchiveEntry.TRAILER);
                                return z ? fill6 + write(name) : fill6;
                            }
                            throw new IOException("size too long");
                        }
                        throw new IOException("filemode too long");
                    }
                    throw new IOException("groupid too long");
                }
                throw new IOException("userid too long");
            }
            throw new IOException("modified too long");
        }
        throw new IOException("filename too long, > 16 chars: " + name);
    }

    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        this.out.close();
        this.prevEntry = null;
    }

    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        } else if (this.prevEntry == null || !this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        } else {
            if (this.entryOffset % 2 != 0) {
                this.out.write(10);
            }
            this.haveUnclosedEntry = false;
        }
    }

    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (!this.finished) {
            return new ArArchiveEntry(file, str);
        }
        throw new IOException("Stream has already been finished");
    }

    public void finish() throws IOException {
        if (this.haveUnclosedEntry) {
            throw new IOException("This archive contains unclosed entries.");
        } else if (!this.finished) {
            this.finished = true;
        } else {
            throw new IOException("This archive has already been finished");
        }
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (!this.finished) {
            ArArchiveEntry arArchiveEntry = (ArArchiveEntry) archiveEntry;
            ArArchiveEntry arArchiveEntry2 = this.prevEntry;
            if (arArchiveEntry2 == null) {
                writeArchiveHeader();
            } else if (arArchiveEntry2.getLength() != this.entryOffset) {
                throw new IOException("length does not match entry (" + this.prevEntry.getLength() + " != " + this.entryOffset);
            } else if (this.haveUnclosedEntry) {
                closeArchiveEntry();
            }
            this.prevEntry = arArchiveEntry;
            writeEntryHeader(arArchiveEntry);
            this.entryOffset = 0;
            this.haveUnclosedEntry = true;
            return;
        }
        throw new IOException("Stream has already been finished");
    }

    public void setLongFileMode(int i) {
        this.longFileMode = i;
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        count(i2);
        this.entryOffset += (long) i2;
    }
}
