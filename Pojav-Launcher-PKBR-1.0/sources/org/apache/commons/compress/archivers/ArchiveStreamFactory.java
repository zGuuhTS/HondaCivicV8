package org.apache.commons.compress.archivers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.arj.ArjArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveInputStream;
import org.apache.commons.compress.archivers.cpio.CpioArchiveOutputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveInputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.p009ar.ArArchiveInputStream;
import org.apache.commons.compress.archivers.p009ar.ArArchiveOutputStream;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ArchiveStreamFactory {

    /* renamed from: AR */
    public static final String f137AR = "ar";
    public static final String ARJ = "arj";
    public static final String CPIO = "cpio";
    public static final String DUMP = "dump";
    public static final String JAR = "jar";
    public static final String SEVEN_Z = "7z";
    public static final String TAR = "tar";
    public static final String ZIP = "zip";
    private final String encoding;
    private volatile String entryEncoding;

    public ArchiveStreamFactory() {
        this((String) null);
    }

    public ArchiveStreamFactory(String str) {
        this.entryEncoding = null;
        this.encoding = str;
        this.entryEncoding = str;
    }

    public ArchiveInputStream createArchiveInputStream(InputStream inputStream) throws ArchiveException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Stream must not be null.");
        } else if (inputStream.markSupported()) {
            byte[] bArr = new byte[12];
            inputStream.mark(12);
            try {
                int readFully = IOUtils.readFully(inputStream, bArr);
                inputStream.reset();
                if (ZipArchiveInputStream.matches(bArr, readFully)) {
                    return createArchiveInputStream(ZIP, inputStream);
                }
                if (JarArchiveInputStream.matches(bArr, readFully)) {
                    return createArchiveInputStream(JAR, inputStream);
                }
                if (ArArchiveInputStream.matches(bArr, readFully)) {
                    return createArchiveInputStream(f137AR, inputStream);
                }
                if (CpioArchiveInputStream.matches(bArr, readFully)) {
                    return createArchiveInputStream(CPIO, inputStream);
                }
                if (ArjArchiveInputStream.matches(bArr, readFully)) {
                    return createArchiveInputStream(ARJ, inputStream);
                }
                if (!SevenZFile.matches(bArr, readFully)) {
                    byte[] bArr2 = new byte[32];
                    inputStream.mark(32);
                    int readFully2 = IOUtils.readFully(inputStream, bArr2);
                    inputStream.reset();
                    if (DumpArchiveInputStream.matches(bArr2, readFully2)) {
                        return createArchiveInputStream(DUMP, inputStream);
                    }
                    byte[] bArr3 = new byte[512];
                    inputStream.mark(512);
                    int readFully3 = IOUtils.readFully(inputStream, bArr3);
                    inputStream.reset();
                    if (TarArchiveInputStream.matches(bArr3, readFully3)) {
                        return createArchiveInputStream(TAR, inputStream);
                    }
                    if (readFully3 >= 512) {
                        TarArchiveInputStream tarArchiveInputStream = null;
                        try {
                            TarArchiveInputStream tarArchiveInputStream2 = new TarArchiveInputStream(new ByteArrayInputStream(bArr3));
                            try {
                                if (tarArchiveInputStream2.getNextTarEntry().isCheckSumOK()) {
                                    ArchiveInputStream createArchiveInputStream = createArchiveInputStream(TAR, inputStream);
                                    IOUtils.closeQuietly(tarArchiveInputStream2);
                                    return createArchiveInputStream;
                                }
                                IOUtils.closeQuietly(tarArchiveInputStream2);
                            } catch (Exception e) {
                                tarArchiveInputStream = tarArchiveInputStream2;
                                IOUtils.closeQuietly(tarArchiveInputStream);
                                throw new ArchiveException("No Archiver found for the stream signature");
                            } catch (Throwable th) {
                                th = th;
                                tarArchiveInputStream = tarArchiveInputStream2;
                                IOUtils.closeQuietly(tarArchiveInputStream);
                                throw th;
                            }
                        } catch (Exception e2) {
                            IOUtils.closeQuietly(tarArchiveInputStream);
                            throw new ArchiveException("No Archiver found for the stream signature");
                        } catch (Throwable th2) {
                            th = th2;
                            IOUtils.closeQuietly(tarArchiveInputStream);
                            throw th;
                        }
                    }
                    throw new ArchiveException("No Archiver found for the stream signature");
                }
                throw new StreamingNotSupportedException(SEVEN_Z);
            } catch (IOException e3) {
                throw new ArchiveException("Could not use reset and mark operations.", e3);
            }
        } else {
            throw new IllegalArgumentException("Mark is not supported.");
        }
    }

    public ArchiveInputStream createArchiveInputStream(String str, InputStream inputStream) throws ArchiveException {
        if (str == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        } else if (inputStream == null) {
            throw new IllegalArgumentException("InputStream must not be null.");
        } else if (f137AR.equalsIgnoreCase(str)) {
            return new ArArchiveInputStream(inputStream);
        } else {
            if (ARJ.equalsIgnoreCase(str)) {
                return this.entryEncoding != null ? new ArjArchiveInputStream(inputStream, this.entryEncoding) : new ArjArchiveInputStream(inputStream);
            }
            if (ZIP.equalsIgnoreCase(str)) {
                return this.entryEncoding != null ? new ZipArchiveInputStream(inputStream, this.entryEncoding) : new ZipArchiveInputStream(inputStream);
            }
            if (TAR.equalsIgnoreCase(str)) {
                return this.entryEncoding != null ? new TarArchiveInputStream(inputStream, this.entryEncoding) : new TarArchiveInputStream(inputStream);
            }
            if (JAR.equalsIgnoreCase(str)) {
                return this.entryEncoding != null ? new JarArchiveInputStream(inputStream, this.entryEncoding) : new JarArchiveInputStream(inputStream);
            }
            if (CPIO.equalsIgnoreCase(str)) {
                return this.entryEncoding != null ? new CpioArchiveInputStream(inputStream, this.entryEncoding) : new CpioArchiveInputStream(inputStream);
            }
            if (DUMP.equalsIgnoreCase(str)) {
                return this.entryEncoding != null ? new DumpArchiveInputStream(inputStream, this.entryEncoding) : new DumpArchiveInputStream(inputStream);
            }
            if (SEVEN_Z.equalsIgnoreCase(str)) {
                throw new StreamingNotSupportedException(SEVEN_Z);
            }
            throw new ArchiveException("Archiver: " + str + " not found.");
        }
    }

    public ArchiveOutputStream createArchiveOutputStream(String str, OutputStream outputStream) throws ArchiveException {
        if (str == null) {
            throw new IllegalArgumentException("Archivername must not be null.");
        } else if (outputStream == null) {
            throw new IllegalArgumentException("OutputStream must not be null.");
        } else if (f137AR.equalsIgnoreCase(str)) {
            return new ArArchiveOutputStream(outputStream);
        } else {
            if (ZIP.equalsIgnoreCase(str)) {
                ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputStream);
                if (this.entryEncoding != null) {
                    zipArchiveOutputStream.setEncoding(this.entryEncoding);
                }
                return zipArchiveOutputStream;
            } else if (TAR.equalsIgnoreCase(str)) {
                return this.entryEncoding != null ? new TarArchiveOutputStream(outputStream, this.entryEncoding) : new TarArchiveOutputStream(outputStream);
            } else {
                if (JAR.equalsIgnoreCase(str)) {
                    return this.entryEncoding != null ? new JarArchiveOutputStream(outputStream, this.entryEncoding) : new JarArchiveOutputStream(outputStream);
                }
                if (CPIO.equalsIgnoreCase(str)) {
                    return this.entryEncoding != null ? new CpioArchiveOutputStream(outputStream, this.entryEncoding) : new CpioArchiveOutputStream(outputStream);
                }
                if (SEVEN_Z.equalsIgnoreCase(str)) {
                    throw new StreamingNotSupportedException(SEVEN_Z);
                }
                throw new ArchiveException("Archiver: " + str + " not found.");
            }
        }
    }

    public String getEntryEncoding() {
        return this.entryEncoding;
    }

    @Deprecated
    public void setEntryEncoding(String str) {
        if (this.encoding == null) {
            this.entryEncoding = str;
            return;
        }
        throw new IllegalStateException("Cannot overide encoding set by the constructor");
    }
}
