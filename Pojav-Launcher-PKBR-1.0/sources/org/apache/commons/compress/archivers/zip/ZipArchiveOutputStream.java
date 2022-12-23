package org.apache.commons.compress.archivers.zip;

import androidx.core.internal.view.SupportMenu;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import top.defaults.checkerboarddrawable.BuildConfig;

public class ZipArchiveOutputStream extends ArchiveOutputStream {
    static final int BUFFER_SIZE = 512;
    private static final int CFH_COMMENT_LENGTH_OFFSET = 32;
    private static final int CFH_COMPRESSED_SIZE_OFFSET = 20;
    private static final int CFH_CRC_OFFSET = 16;
    private static final int CFH_DISK_NUMBER_OFFSET = 34;
    private static final int CFH_EXTERNAL_ATTRIBUTES_OFFSET = 38;
    private static final int CFH_EXTRA_LENGTH_OFFSET = 30;
    private static final int CFH_FILENAME_LENGTH_OFFSET = 28;
    private static final int CFH_FILENAME_OFFSET = 46;
    private static final int CFH_GPB_OFFSET = 8;
    private static final int CFH_INTERNAL_ATTRIBUTES_OFFSET = 36;
    private static final int CFH_LFH_OFFSET = 42;
    private static final int CFH_METHOD_OFFSET = 10;
    private static final int CFH_ORIGINAL_SIZE_OFFSET = 24;
    static final byte[] CFH_SIG = ZipLong.CFH_SIG.getBytes();
    private static final int CFH_SIG_OFFSET = 0;
    private static final int CFH_TIME_OFFSET = 12;
    private static final int CFH_VERSION_MADE_BY_OFFSET = 4;
    private static final int CFH_VERSION_NEEDED_OFFSET = 6;
    static final byte[] DD_SIG = ZipLong.DD_SIG.getBytes();
    public static final int DEFAULT_COMPRESSION = -1;
    static final String DEFAULT_ENCODING = "UTF8";
    public static final int DEFLATED = 8;
    @Deprecated
    public static final int EFS_FLAG = 2048;
    private static final byte[] EMPTY = new byte[0];
    static final byte[] EOCD_SIG = ZipLong.getBytes(101010256);
    private static final int LFH_COMPRESSED_SIZE_OFFSET = 18;
    private static final int LFH_CRC_OFFSET = 14;
    private static final int LFH_EXTRA_LENGTH_OFFSET = 28;
    private static final int LFH_FILENAME_LENGTH_OFFSET = 26;
    private static final int LFH_FILENAME_OFFSET = 30;
    private static final int LFH_GPB_OFFSET = 6;
    private static final int LFH_METHOD_OFFSET = 8;
    private static final int LFH_ORIGINAL_SIZE_OFFSET = 22;
    static final byte[] LFH_SIG = ZipLong.LFH_SIG.getBytes();
    private static final int LFH_SIG_OFFSET = 0;
    private static final int LFH_TIME_OFFSET = 10;
    private static final int LFH_VERSION_NEEDED_OFFSET = 4;
    private static final byte[] LZERO = {0, 0, 0, 0};
    private static final byte[] ONE = ZipLong.getBytes(1);
    public static final int STORED = 0;
    private static final byte[] ZERO = {0, 0};
    static final byte[] ZIP64_EOCD_LOC_SIG = ZipLong.getBytes(117853008);
    static final byte[] ZIP64_EOCD_SIG = ZipLong.getBytes(101075792);
    private final Calendar calendarInstance = Calendar.getInstance();
    private long cdLength = 0;
    private long cdOffset = 0;
    private String comment = BuildConfig.FLAVOR;
    private final byte[] copyBuffer = new byte[32768];
    private UnicodeExtraFieldPolicy createUnicodeExtraFields = UnicodeExtraFieldPolicy.NEVER;
    protected final Deflater def;
    private String encoding = DEFAULT_ENCODING;
    private final List<ZipArchiveEntry> entries = new LinkedList();
    private CurrentEntry entry;
    private boolean fallbackToUTF8 = false;
    protected boolean finished = false;
    private boolean hasCompressionLevelChanged = false;
    private boolean hasUsedZip64 = false;
    private int level = -1;
    private int method = 8;
    private final Map<ZipArchiveEntry, Long> offsets = new HashMap();
    private final OutputStream out;
    private final RandomAccessFile raf;
    private final StreamCompressor streamCompressor;
    private boolean useUTF8Flag = true;
    private Zip64Mode zip64Mode = Zip64Mode.AsNeeded;
    private ZipEncoding zipEncoding = ZipEncodingHelper.getZipEncoding(DEFAULT_ENCODING);

    private static final class CurrentEntry {
        /* access modifiers changed from: private */
        public long bytesRead;
        /* access modifiers changed from: private */
        public boolean causedUseOfZip64;
        /* access modifiers changed from: private */
        public long dataStart;
        /* access modifiers changed from: private */
        public final ZipArchiveEntry entry;
        /* access modifiers changed from: private */
        public boolean hasWritten;
        /* access modifiers changed from: private */
        public long localDataStart;

        private CurrentEntry(ZipArchiveEntry zipArchiveEntry) {
            this.localDataStart = 0;
            this.dataStart = 0;
            this.bytesRead = 0;
            this.causedUseOfZip64 = false;
            this.entry = zipArchiveEntry;
        }
    }

    public static final class UnicodeExtraFieldPolicy {
        public static final UnicodeExtraFieldPolicy ALWAYS = new UnicodeExtraFieldPolicy("always");
        public static final UnicodeExtraFieldPolicy NEVER = new UnicodeExtraFieldPolicy("never");
        public static final UnicodeExtraFieldPolicy NOT_ENCODEABLE = new UnicodeExtraFieldPolicy("not encodeable");
        private final String name;

        private UnicodeExtraFieldPolicy(String str) {
            this.name = str;
        }

        public String toString() {
            return this.name;
        }
    }

    public ZipArchiveOutputStream(File file) throws IOException {
        FileOutputStream fileOutputStream;
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            try {
                randomAccessFile.setLength(0);
                fileOutputStream = null;
                randomAccessFile2 = randomAccessFile;
            } catch (IOException e) {
                IOUtils.closeQuietly(randomAccessFile);
                fileOutputStream = new FileOutputStream(file);
                Deflater deflater = new Deflater(this.level, true);
                this.def = deflater;
                this.streamCompressor = StreamCompressor.create((DataOutput) randomAccessFile2, deflater);
                this.out = fileOutputStream;
                this.raf = randomAccessFile2;
            }
        } catch (IOException e2) {
            randomAccessFile = null;
            IOUtils.closeQuietly(randomAccessFile);
            fileOutputStream = new FileOutputStream(file);
            Deflater deflater2 = new Deflater(this.level, true);
            this.def = deflater2;
            this.streamCompressor = StreamCompressor.create((DataOutput) randomAccessFile2, deflater2);
            this.out = fileOutputStream;
            this.raf = randomAccessFile2;
        }
        Deflater deflater22 = new Deflater(this.level, true);
        this.def = deflater22;
        this.streamCompressor = StreamCompressor.create((DataOutput) randomAccessFile2, deflater22);
        this.out = fileOutputStream;
        this.raf = randomAccessFile2;
    }

    public ZipArchiveOutputStream(OutputStream outputStream) {
        this.out = outputStream;
        this.raf = null;
        Deflater deflater = new Deflater(this.level, true);
        this.def = deflater;
        this.streamCompressor = StreamCompressor.create(outputStream, deflater);
    }

    private void addUnicodeExtraFields(ZipArchiveEntry zipArchiveEntry, boolean z, ByteBuffer byteBuffer) throws IOException {
        if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !z) {
            zipArchiveEntry.addExtraField(new UnicodePathExtraField(zipArchiveEntry.getName(), byteBuffer.array(), byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position()));
        }
        String comment2 = zipArchiveEntry.getComment();
        if (comment2 != null && !BuildConfig.FLAVOR.equals(comment2)) {
            boolean canEncode = this.zipEncoding.canEncode(comment2);
            if (this.createUnicodeExtraFields == UnicodeExtraFieldPolicy.ALWAYS || !canEncode) {
                ByteBuffer encode = getEntryEncoding(zipArchiveEntry).encode(comment2);
                zipArchiveEntry.addExtraField(new UnicodeCommentExtraField(comment2, encode.array(), encode.arrayOffset(), encode.limit() - encode.position()));
            }
        }
    }

    private boolean checkIfNeedsZip64(Zip64Mode zip64Mode2) throws ZipException {
        boolean isZip64Required = isZip64Required(this.entry.entry, zip64Mode2);
        if (!isZip64Required || zip64Mode2 != Zip64Mode.Never) {
            return isZip64Required;
        }
        throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(this.entry.entry));
    }

    private void closeCopiedEntry(boolean z) throws IOException {
        preClose();
        CurrentEntry currentEntry = this.entry;
        long unused = currentEntry.bytesRead = currentEntry.entry.getSize();
        closeEntry(checkIfNeedsZip64(getEffectiveZip64Mode(this.entry.entry)), z);
    }

    private void closeEntry(boolean z, boolean z2) throws IOException {
        if (!z2 && this.raf != null) {
            rewriteSizesAndCrc(z);
        }
        writeDataDescriptor(this.entry.entry);
        this.entry = null;
    }

    private void copyFromZipInputStream(InputStream inputStream) throws IOException {
        CurrentEntry currentEntry = this.entry;
        if (currentEntry != null) {
            ZipUtil.checkRequestedFeatures(currentEntry.entry);
            boolean unused = this.entry.hasWritten = true;
            while (true) {
                int read = inputStream.read(this.copyBuffer);
                if (read >= 0) {
                    this.streamCompressor.writeCounted(this.copyBuffer, 0, read);
                    count(read);
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalStateException("No current entry");
        }
    }

    private byte[] createCentralFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        long longValue = this.offsets.get(zipArchiveEntry).longValue();
        boolean z = hasZip64Extra(zipArchiveEntry) || zipArchiveEntry.getCompressedSize() >= 4294967295L || zipArchiveEntry.getSize() >= 4294967295L || longValue >= 4294967295L || this.zip64Mode == Zip64Mode.Always;
        if (!z || this.zip64Mode != Zip64Mode.Never) {
            handleZip64Extra(zipArchiveEntry, longValue, z);
            return createCentralFileHeader(zipArchiveEntry, getName(zipArchiveEntry), longValue, z);
        }
        throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
    }

    private byte[] createCentralFileHeader(ZipArchiveEntry zipArchiveEntry, ByteBuffer byteBuffer, long j, boolean z) throws IOException {
        long j2 = j;
        byte[] centralDirectoryExtra = zipArchiveEntry.getCentralDirectoryExtra();
        String comment2 = zipArchiveEntry.getComment();
        if (comment2 == null) {
            comment2 = BuildConfig.FLAVOR;
        }
        ByteBuffer encode = getEntryEncoding(zipArchiveEntry).encode(comment2);
        int limit = byteBuffer.limit() - byteBuffer.position();
        int limit2 = encode.limit() - encode.position();
        int i = limit + 46;
        byte[] bArr = new byte[(centralDirectoryExtra.length + i + limit2)];
        System.arraycopy(CFH_SIG, 0, bArr, 0, 4);
        ZipShort.putShort((zipArchiveEntry.getPlatform() << 8) | (!this.hasUsedZip64 ? 20 : 45), bArr, 4);
        int method2 = zipArchiveEntry.getMethod();
        boolean canEncode = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        ZipShort.putShort(versionNeededToExtract(method2, z), bArr, 6);
        getGeneralPurposeBits(method2, !canEncode && this.fallbackToUTF8).encode(bArr, 8);
        ZipShort.putShort(method2, bArr, 10);
        ZipUtil.toDosTime(this.calendarInstance, zipArchiveEntry.getTime(), bArr, 12);
        ZipLong.putLong(zipArchiveEntry.getCrc(), bArr, 16);
        if (zipArchiveEntry.getCompressedSize() >= 4294967295L || zipArchiveEntry.getSize() >= 4294967295L || this.zip64Mode == Zip64Mode.Always) {
            ZipLong.ZIP64_MAGIC.putLong(bArr, 20);
            ZipLong.ZIP64_MAGIC.putLong(bArr, 24);
        } else {
            ZipLong.putLong(zipArchiveEntry.getCompressedSize(), bArr, 20);
            ZipLong.putLong(zipArchiveEntry.getSize(), bArr, 24);
        }
        ZipShort.putShort(limit, bArr, 28);
        ZipShort.putShort(centralDirectoryExtra.length, bArr, 30);
        ZipShort.putShort(limit2, bArr, 32);
        System.arraycopy(ZERO, 0, bArr, 34, 2);
        ZipShort.putShort(zipArchiveEntry.getInternalAttributes(), bArr, 36);
        ZipLong.putLong(zipArchiveEntry.getExternalAttributes(), bArr, 38);
        if (j2 >= 4294967295L || this.zip64Mode == Zip64Mode.Always) {
            ZipLong.putLong(4294967295L, bArr, 42);
        } else {
            ZipLong.putLong(Math.min(j2, 4294967295L), bArr, 42);
        }
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset(), bArr, 46, limit);
        System.arraycopy(centralDirectoryExtra, 0, bArr, i, centralDirectoryExtra.length);
        System.arraycopy(encode.array(), encode.arrayOffset(), bArr, i + centralDirectoryExtra.length, limit2);
        return bArr;
    }

    private byte[] createLocalFileHeader(ZipArchiveEntry zipArchiveEntry, ByteBuffer byteBuffer, boolean z, boolean z2) {
        long size;
        byte[] localFileDataExtra = zipArchiveEntry.getLocalFileDataExtra();
        int limit = byteBuffer.limit() - byteBuffer.position();
        int i = limit + 30;
        byte[] bArr = new byte[(localFileDataExtra.length + i)];
        System.arraycopy(LFH_SIG, 0, bArr, 0, 4);
        int method2 = zipArchiveEntry.getMethod();
        if (!z2 || isZip64Required(this.entry.entry, this.zip64Mode)) {
            ZipShort.putShort(versionNeededToExtract(method2, hasZip64Extra(zipArchiveEntry)), bArr, 4);
        } else {
            ZipShort.putShort(10, bArr, 4);
        }
        getGeneralPurposeBits(method2, !z && this.fallbackToUTF8).encode(bArr, 6);
        ZipShort.putShort(method2, bArr, 8);
        ZipUtil.toDosTime(this.calendarInstance, zipArchiveEntry.getTime(), bArr, 10);
        if (!z2 && (method2 == 8 || this.raf != null)) {
            System.arraycopy(LZERO, 0, bArr, 14, 4);
        } else {
            ZipLong.putLong(zipArchiveEntry.getCrc(), bArr, 14);
        }
        if (hasZip64Extra(this.entry.entry)) {
            ZipLong.ZIP64_MAGIC.putLong(bArr, 18);
            ZipLong.ZIP64_MAGIC.putLong(bArr, 22);
        } else {
            if (z2) {
                size = zipArchiveEntry.getCompressedSize();
            } else if (method2 == 8 || this.raf != null) {
                byte[] bArr2 = LZERO;
                System.arraycopy(bArr2, 0, bArr, 18, 4);
                System.arraycopy(bArr2, 0, bArr, 22, 4);
            } else {
                size = zipArchiveEntry.getSize();
            }
            ZipLong.putLong(size, bArr, 18);
            ZipLong.putLong(zipArchiveEntry.getSize(), bArr, 22);
        }
        ZipShort.putShort(limit, bArr, 26);
        ZipShort.putShort(localFileDataExtra.length, bArr, 28);
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset(), bArr, 30, limit);
        System.arraycopy(localFileDataExtra, 0, bArr, i, localFileDataExtra.length);
        return bArr;
    }

    private void flushDeflater() throws IOException {
        if (this.entry.entry.getMethod() == 8) {
            this.streamCompressor.flushDeflater();
        }
    }

    private Zip64Mode getEffectiveZip64Mode(ZipArchiveEntry zipArchiveEntry) {
        return (this.zip64Mode == Zip64Mode.AsNeeded && this.raf == null && zipArchiveEntry.getMethod() == 8 && zipArchiveEntry.getSize() == -1) ? Zip64Mode.Never : this.zip64Mode;
    }

    private ZipEncoding getEntryEncoding(ZipArchiveEntry zipArchiveEntry) {
        return (this.zipEncoding.canEncode(zipArchiveEntry.getName()) || !this.fallbackToUTF8) ? this.zipEncoding : ZipEncodingHelper.UTF8_ZIP_ENCODING;
    }

    private GeneralPurposeBit getGeneralPurposeBits(int i, boolean z) {
        GeneralPurposeBit generalPurposeBit = new GeneralPurposeBit();
        generalPurposeBit.useUTF8ForNames(this.useUTF8Flag || z);
        if (isDeflatedToOutputStream(i)) {
            generalPurposeBit.useDataDescriptor(true);
        }
        return generalPurposeBit;
    }

    private ByteBuffer getName(ZipArchiveEntry zipArchiveEntry) throws IOException {
        return getEntryEncoding(zipArchiveEntry).encode(zipArchiveEntry.getName());
    }

    private Zip64ExtendedInformationExtraField getZip64Extra(ZipArchiveEntry zipArchiveEntry) {
        CurrentEntry currentEntry = this.entry;
        if (currentEntry != null) {
            boolean unused = currentEntry.causedUseOfZip64 = !this.hasUsedZip64;
        }
        this.hasUsedZip64 = true;
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField) zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField == null) {
            zip64ExtendedInformationExtraField = new Zip64ExtendedInformationExtraField();
        }
        zipArchiveEntry.addAsFirstExtraField(zip64ExtendedInformationExtraField);
        return zip64ExtendedInformationExtraField;
    }

    private boolean handleSizesAndCrc(long j, long j2, Zip64Mode zip64Mode2) throws ZipException {
        if (this.entry.entry.getMethod() == 8) {
            this.entry.entry.setSize(this.entry.bytesRead);
        } else if (this.raf != null) {
            this.entry.entry.setSize(j);
        } else if (this.entry.entry.getCrc() == j2) {
            if (this.entry.entry.getSize() != j) {
                throw new ZipException("bad size for entry " + this.entry.entry.getName() + ": " + this.entry.entry.getSize() + " instead of " + j);
            }
            return checkIfNeedsZip64(zip64Mode2);
        } else {
            throw new ZipException("bad CRC checksum for entry " + this.entry.entry.getName() + ": " + Long.toHexString(this.entry.entry.getCrc()) + " instead of " + Long.toHexString(j2));
        }
        this.entry.entry.setCompressedSize(j);
        this.entry.entry.setCrc(j2);
        return checkIfNeedsZip64(zip64Mode2);
    }

    private void handleZip64Extra(ZipArchiveEntry zipArchiveEntry, long j, boolean z) {
        ZipEightByteInteger zipEightByteInteger;
        if (z) {
            Zip64ExtendedInformationExtraField zip64Extra = getZip64Extra(zipArchiveEntry);
            if (zipArchiveEntry.getCompressedSize() >= 4294967295L || zipArchiveEntry.getSize() >= 4294967295L || this.zip64Mode == Zip64Mode.Always) {
                zip64Extra.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
                zipEightByteInteger = new ZipEightByteInteger(zipArchiveEntry.getSize());
            } else {
                zipEightByteInteger = null;
                zip64Extra.setCompressedSize((ZipEightByteInteger) null);
            }
            zip64Extra.setSize(zipEightByteInteger);
            if (j >= 4294967295L || this.zip64Mode == Zip64Mode.Always) {
                zip64Extra.setRelativeHeaderOffset(new ZipEightByteInteger(j));
            }
            zipArchiveEntry.setExtra();
        }
    }

    private boolean hasZip64Extra(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID) != null;
    }

    private boolean isDeflatedToOutputStream(int i) {
        return i == 8 && this.raf == null;
    }

    private boolean isTooLageForZip32(ZipArchiveEntry zipArchiveEntry) {
        return zipArchiveEntry.getSize() >= 4294967295L || zipArchiveEntry.getCompressedSize() >= 4294967295L;
    }

    private boolean isZip64Required(ZipArchiveEntry zipArchiveEntry, Zip64Mode zip64Mode2) {
        return zip64Mode2 == Zip64Mode.Always || isTooLageForZip32(zipArchiveEntry);
    }

    private void preClose() throws IOException {
        if (!this.finished) {
            CurrentEntry currentEntry = this.entry;
            if (currentEntry == null) {
                throw new IOException("No current entry to close");
            } else if (!currentEntry.hasWritten) {
                write(EMPTY, 0, 0);
            }
        } else {
            throw new IOException("Stream has already been finished");
        }
    }

    private void putArchiveEntry(ArchiveEntry archiveEntry, boolean z) throws IOException {
        if (!this.finished) {
            if (this.entry != null) {
                closeArchiveEntry();
            }
            ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) archiveEntry;
            CurrentEntry currentEntry = new CurrentEntry(zipArchiveEntry);
            this.entry = currentEntry;
            this.entries.add(currentEntry.entry);
            setDefaults(this.entry.entry);
            Zip64Mode effectiveZip64Mode = getEffectiveZip64Mode(this.entry.entry);
            validateSizeInformation(effectiveZip64Mode);
            if (shouldAddZip64Extra(this.entry.entry, effectiveZip64Mode)) {
                Zip64ExtendedInformationExtraField zip64Extra = getZip64Extra(this.entry.entry);
                ZipEightByteInteger zipEightByteInteger = ZipEightByteInteger.ZERO;
                ZipEightByteInteger zipEightByteInteger2 = ZipEightByteInteger.ZERO;
                if (z) {
                    zipEightByteInteger = new ZipEightByteInteger(this.entry.entry.getSize());
                    zipEightByteInteger2 = new ZipEightByteInteger(this.entry.entry.getCompressedSize());
                } else if (this.entry.entry.getMethod() == 0 && this.entry.entry.getSize() != -1) {
                    zipEightByteInteger = new ZipEightByteInteger(this.entry.entry.getSize());
                    zipEightByteInteger2 = zipEightByteInteger;
                }
                zip64Extra.setSize(zipEightByteInteger);
                zip64Extra.setCompressedSize(zipEightByteInteger2);
                this.entry.entry.setExtra();
            }
            if (this.entry.entry.getMethod() == 8 && this.hasCompressionLevelChanged) {
                this.def.setLevel(this.level);
                this.hasCompressionLevelChanged = false;
            }
            writeLocalFileHeader(zipArchiveEntry, z);
            return;
        }
        throw new IOException("Stream has already been finished");
    }

    private void rewriteSizesAndCrc(boolean z) throws IOException {
        long filePointer = this.raf.getFilePointer();
        this.raf.seek(this.entry.localDataStart);
        writeOut(ZipLong.getBytes(this.entry.entry.getCrc()));
        if (!hasZip64Extra(this.entry.entry) || !z) {
            writeOut(ZipLong.getBytes(this.entry.entry.getCompressedSize()));
            writeOut(ZipLong.getBytes(this.entry.entry.getSize()));
        } else {
            writeOut(ZipLong.ZIP64_MAGIC.getBytes());
            writeOut(ZipLong.ZIP64_MAGIC.getBytes());
        }
        if (hasZip64Extra(this.entry.entry)) {
            ByteBuffer name = getName(this.entry.entry);
            this.raf.seek(this.entry.localDataStart + 12 + 4 + ((long) (name.limit() - name.position())) + 4);
            writeOut(ZipEightByteInteger.getBytes(this.entry.entry.getSize()));
            writeOut(ZipEightByteInteger.getBytes(this.entry.entry.getCompressedSize()));
            if (!z) {
                this.raf.seek(this.entry.localDataStart - 10);
                writeOut(ZipShort.getBytes(10));
                this.entry.entry.removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
                this.entry.entry.setExtra();
                if (this.entry.causedUseOfZip64) {
                    this.hasUsedZip64 = false;
                }
            }
        }
        this.raf.seek(filePointer);
    }

    private void setDefaults(ZipArchiveEntry zipArchiveEntry) {
        if (zipArchiveEntry.getMethod() == -1) {
            zipArchiveEntry.setMethod(this.method);
        }
        if (zipArchiveEntry.getTime() == -1) {
            zipArchiveEntry.setTime(System.currentTimeMillis());
        }
    }

    private boolean shouldAddZip64Extra(ZipArchiveEntry zipArchiveEntry, Zip64Mode zip64Mode2) {
        return zip64Mode2 == Zip64Mode.Always || zipArchiveEntry.getSize() >= 4294967295L || zipArchiveEntry.getCompressedSize() >= 4294967295L || !(zipArchiveEntry.getSize() != -1 || this.raf == null || zip64Mode2 == Zip64Mode.Never);
    }

    private void validateSizeInformation(Zip64Mode zip64Mode2) throws ZipException {
        if (this.entry.entry.getMethod() == 0 && this.raf == null) {
            if (this.entry.entry.getSize() == -1) {
                throw new ZipException("uncompressed size is required for STORED method when not writing to a file");
            } else if (this.entry.entry.getCrc() != -1) {
                this.entry.entry.setCompressedSize(this.entry.entry.getSize());
            } else {
                throw new ZipException("crc checksum is required for STORED method when not writing to a file");
            }
        }
        if ((this.entry.entry.getSize() >= 4294967295L || this.entry.entry.getCompressedSize() >= 4294967295L) && zip64Mode2 == Zip64Mode.Never) {
            throw new Zip64RequiredException(Zip64RequiredException.getEntryTooBigMessage(this.entry.entry));
        }
    }

    private int versionNeededToExtract(int i, boolean z) {
        if (z) {
            return 45;
        }
        return isDeflatedToOutputStream(i) ? 20 : 10;
    }

    private void writeCentralDirectoryInChunks() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(70000);
        while (true) {
            int i = 0;
            for (ZipArchiveEntry createCentralFileHeader : this.entries) {
                byteArrayOutputStream.write(createCentralFileHeader(createCentralFileHeader));
                i++;
                if (i > 1000) {
                    writeCounted(byteArrayOutputStream.toByteArray());
                    byteArrayOutputStream.reset();
                }
            }
            writeCounted(byteArrayOutputStream.toByteArray());
            return;
        }
    }

    private void writeCounted(byte[] bArr) throws IOException {
        this.streamCompressor.writeCounted(bArr);
    }

    private void writeLocalFileHeader(ZipArchiveEntry zipArchiveEntry, boolean z) throws IOException {
        boolean canEncode = this.zipEncoding.canEncode(zipArchiveEntry.getName());
        ByteBuffer name = getName(zipArchiveEntry);
        if (this.createUnicodeExtraFields != UnicodeExtraFieldPolicy.NEVER) {
            addUnicodeExtraFields(zipArchiveEntry, canEncode, name);
        }
        byte[] createLocalFileHeader = createLocalFileHeader(zipArchiveEntry, name, canEncode, z);
        long totalBytesWritten = this.streamCompressor.getTotalBytesWritten();
        this.offsets.put(zipArchiveEntry, Long.valueOf(totalBytesWritten));
        long unused = this.entry.localDataStart = totalBytesWritten + 14;
        writeCounted(createLocalFileHeader);
        long unused2 = this.entry.dataStart = this.streamCompressor.getTotalBytesWritten();
    }

    public void addRawArchiveEntry(ZipArchiveEntry zipArchiveEntry, InputStream inputStream) throws IOException {
        ZipArchiveEntry zipArchiveEntry2 = new ZipArchiveEntry(zipArchiveEntry);
        if (hasZip64Extra(zipArchiveEntry2)) {
            zipArchiveEntry2.removeExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        }
        boolean z = (zipArchiveEntry2.getCrc() == -1 || zipArchiveEntry2.getSize() == -1 || zipArchiveEntry2.getCompressedSize() == -1) ? false : true;
        putArchiveEntry(zipArchiveEntry2, z);
        copyFromZipInputStream(inputStream);
        closeCopiedEntry(z);
    }

    public boolean canWriteEntryData(ArchiveEntry archiveEntry) {
        if (!(archiveEntry instanceof ZipArchiveEntry)) {
            return false;
        }
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) archiveEntry;
        return (zipArchiveEntry.getMethod() == ZipMethod.IMPLODING.getCode() || zipArchiveEntry.getMethod() == ZipMethod.UNSHRINKING.getCode() || !ZipUtil.canHandleEntryData(zipArchiveEntry)) ? false : true;
    }

    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        destroy();
    }

    public void closeArchiveEntry() throws IOException {
        preClose();
        flushDeflater();
        long totalBytesWritten = this.streamCompressor.getTotalBytesWritten();
        long access$000 = this.entry.dataStart;
        long crc32 = this.streamCompressor.getCrc32();
        long unused = this.entry.bytesRead = this.streamCompressor.getBytesRead();
        closeEntry(handleSizesAndCrc(totalBytesWritten - access$000, crc32, getEffectiveZip64Mode(this.entry.entry)), false);
        this.streamCompressor.reset();
    }

    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (!this.finished) {
            return new ZipArchiveEntry(file, str);
        }
        throw new IOException("Stream has already been finished");
    }

    /* access modifiers changed from: protected */
    public final void deflate() throws IOException {
        this.streamCompressor.deflate();
    }

    /* access modifiers changed from: package-private */
    public void destroy() throws IOException {
        RandomAccessFile randomAccessFile = this.raf;
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.close();
        }
    }

    public void finish() throws IOException {
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        } else if (this.entry == null) {
            this.cdOffset = this.streamCompressor.getTotalBytesWritten();
            writeCentralDirectoryInChunks();
            this.cdLength = this.streamCompressor.getTotalBytesWritten() - this.cdOffset;
            writeZip64CentralDirectory();
            writeCentralDirectoryEnd();
            this.offsets.clear();
            this.entries.clear();
            this.streamCompressor.close();
            this.finished = true;
        } else {
            throw new IOException("This archive contains unclosed entries.");
        }
    }

    public void flush() throws IOException {
        OutputStream outputStream = this.out;
        if (outputStream != null) {
            outputStream.flush();
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public boolean isSeekable() {
        return this.raf != null;
    }

    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        putArchiveEntry(archiveEntry, false);
    }

    public void setComment(String str) {
        this.comment = str;
    }

    public void setCreateUnicodeExtraFields(UnicodeExtraFieldPolicy unicodeExtraFieldPolicy) {
        this.createUnicodeExtraFields = unicodeExtraFieldPolicy;
    }

    public void setEncoding(String str) {
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        if (this.useUTF8Flag && !ZipEncodingHelper.isUTF8(str)) {
            this.useUTF8Flag = false;
        }
    }

    public void setFallbackToUTF8(boolean z) {
        this.fallbackToUTF8 = z;
    }

    public void setLevel(int i) {
        if (i < -1 || i > 9) {
            throw new IllegalArgumentException("Invalid compression level: " + i);
        }
        this.hasCompressionLevelChanged = this.level != i;
        this.level = i;
    }

    public void setMethod(int i) {
        this.method = i;
    }

    public void setUseLanguageEncodingFlag(boolean z) {
        this.useUTF8Flag = z && ZipEncodingHelper.isUTF8(this.encoding);
    }

    public void setUseZip64(Zip64Mode zip64Mode2) {
        this.zip64Mode = zip64Mode2;
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        CurrentEntry currentEntry = this.entry;
        if (currentEntry != null) {
            ZipUtil.checkRequestedFeatures(currentEntry.entry);
            count(this.streamCompressor.write(bArr, i, i2, this.entry.entry.getMethod()));
            return;
        }
        throw new IllegalStateException("No current entry");
    }

    /* access modifiers changed from: protected */
    public void writeCentralDirectoryEnd() throws IOException {
        writeCounted(EOCD_SIG);
        byte[] bArr = ZERO;
        writeCounted(bArr);
        writeCounted(bArr);
        int size = this.entries.size();
        if (size > 65535 && this.zip64Mode == Zip64Mode.Never) {
            throw new Zip64RequiredException("archive contains more than 65535 entries.");
        } else if (this.cdOffset <= 4294967295L || this.zip64Mode != Zip64Mode.Never) {
            byte[] bytes = ZipShort.getBytes(Math.min(size, SupportMenu.USER_MASK));
            writeCounted(bytes);
            writeCounted(bytes);
            writeCounted(ZipLong.getBytes(Math.min(this.cdLength, 4294967295L)));
            writeCounted(ZipLong.getBytes(Math.min(this.cdOffset, 4294967295L)));
            ByteBuffer encode = this.zipEncoding.encode(this.comment);
            int limit = encode.limit() - encode.position();
            writeCounted(ZipShort.getBytes(limit));
            this.streamCompressor.writeCounted(encode.array(), encode.arrayOffset(), limit);
        } else {
            throw new Zip64RequiredException("archive's size exceeds the limit of 4GByte.");
        }
    }

    /* access modifiers changed from: protected */
    public void writeCentralFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        writeCounted(createCentralFileHeader(zipArchiveEntry));
    }

    /* access modifiers changed from: protected */
    public void writeDataDescriptor(ZipArchiveEntry zipArchiveEntry) throws IOException {
        if (zipArchiveEntry.getMethod() == 8 && this.raf == null) {
            writeCounted(DD_SIG);
            writeCounted(ZipLong.getBytes(zipArchiveEntry.getCrc()));
            if (!hasZip64Extra(zipArchiveEntry)) {
                writeCounted(ZipLong.getBytes(zipArchiveEntry.getCompressedSize()));
                writeCounted(ZipLong.getBytes(zipArchiveEntry.getSize()));
                return;
            }
            writeCounted(ZipEightByteInteger.getBytes(zipArchiveEntry.getCompressedSize()));
            writeCounted(ZipEightByteInteger.getBytes(zipArchiveEntry.getSize()));
        }
    }

    /* access modifiers changed from: protected */
    public void writeLocalFileHeader(ZipArchiveEntry zipArchiveEntry) throws IOException {
        writeLocalFileHeader(zipArchiveEntry, false);
    }

    /* access modifiers changed from: protected */
    public final void writeOut(byte[] bArr) throws IOException {
        this.streamCompressor.writeOut(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: protected */
    public final void writeOut(byte[] bArr, int i, int i2) throws IOException {
        this.streamCompressor.writeOut(bArr, i, i2);
    }

    /* access modifiers changed from: protected */
    public void writeZip64CentralDirectory() throws IOException {
        if (this.zip64Mode != Zip64Mode.Never) {
            if (!this.hasUsedZip64 && (this.cdOffset >= 4294967295L || this.cdLength >= 4294967295L || this.entries.size() >= 65535)) {
                this.hasUsedZip64 = true;
            }
            if (this.hasUsedZip64) {
                long totalBytesWritten = this.streamCompressor.getTotalBytesWritten();
                writeOut(ZIP64_EOCD_SIG);
                writeOut(ZipEightByteInteger.getBytes(44));
                writeOut(ZipShort.getBytes(45));
                writeOut(ZipShort.getBytes(45));
                byte[] bArr = LZERO;
                writeOut(bArr);
                writeOut(bArr);
                byte[] bytes = ZipEightByteInteger.getBytes((long) this.entries.size());
                writeOut(bytes);
                writeOut(bytes);
                writeOut(ZipEightByteInteger.getBytes(this.cdLength));
                writeOut(ZipEightByteInteger.getBytes(this.cdOffset));
                writeOut(ZIP64_EOCD_LOC_SIG);
                writeOut(bArr);
                writeOut(ZipEightByteInteger.getBytes(totalBytesWritten));
                writeOut(ONE);
            }
        }
    }
}
