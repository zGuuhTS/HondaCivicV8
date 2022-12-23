package org.apache.commons.compress.archivers.zip;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class ZipFile implements Closeable {
    static final int BYTE_SHIFT = 8;
    private static final int CFD_LOCATOR_OFFSET = 16;
    private static final int CFH_LEN = 42;
    private static final long CFH_SIG = ZipLong.getValue(ZipArchiveOutputStream.CFH_SIG);
    private static final int HASH_SIZE = 509;
    private static final long LFH_OFFSET_FOR_FILENAME_LENGTH = 26;
    private static final int MAX_EOCD_SIZE = 65557;
    static final int MIN_EOCD_SIZE = 22;
    static final int NIBLET_MASK = 15;
    private static final int POS_0 = 0;
    private static final int POS_1 = 1;
    private static final int POS_2 = 2;
    private static final int POS_3 = 3;
    private static final int ZIP64_EOCDL_LENGTH = 20;
    private static final int ZIP64_EOCDL_LOCATOR_OFFSET = 8;
    private static final int ZIP64_EOCD_CFD_LOCATOR_OFFSET = 48;
    private final byte[] CFH_BUF;
    private final byte[] DWORD_BUF;
    private final Comparator<ZipArchiveEntry> OFFSET_COMPARATOR;
    private final byte[] SHORT_BUF;
    private final byte[] WORD_BUF;
    /* access modifiers changed from: private */
    public final RandomAccessFile archive;
    private final String archiveName;
    private volatile boolean closed;
    private final String encoding;
    private final List<ZipArchiveEntry> entries;
    private final Map<String, LinkedList<ZipArchiveEntry>> nameMap;
    private final boolean useUnicodeExtraFields;
    private final ZipEncoding zipEncoding;

    /* renamed from: org.apache.commons.compress.archivers.zip.ZipFile$3 */
    static /* synthetic */ class C09193 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod;

        static {
            int[] iArr = new int[ZipMethod.values().length];
            $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod = iArr;
            try {
                iArr[ZipMethod.STORED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.UNSHRINKING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.IMPLODING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.DEFLATED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.BZIP2.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.AES_ENCRYPTED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.ENHANCED_DEFLATED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_1.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_2.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_3.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.EXPANDING_LEVEL_4.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.JPEG.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.LZMA.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.PKWARE_IMPLODING.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.PPMD.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.TOKENIZATION.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.UNKNOWN.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.WAVPACK.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
        }
    }

    private class BoundedInputStream extends InputStream {
        private boolean addDummyByte = false;
        private long loc;
        private long remaining;

        BoundedInputStream(long j, long j2) {
            this.remaining = j2;
            this.loc = j;
        }

        /* access modifiers changed from: package-private */
        public void addDummy() {
            this.addDummyByte = true;
        }

        public int read() throws IOException {
            int read;
            long j = this.remaining;
            this.remaining = j - 1;
            if (j > 0) {
                synchronized (ZipFile.this.archive) {
                    RandomAccessFile access$600 = ZipFile.this.archive;
                    long j2 = this.loc;
                    this.loc = 1 + j2;
                    access$600.seek(j2);
                    read = ZipFile.this.archive.read();
                }
                return read;
            } else if (!this.addDummyByte) {
                return -1;
            } else {
                this.addDummyByte = false;
                return 0;
            }
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read;
            long j = this.remaining;
            if (j <= 0) {
                if (!this.addDummyByte) {
                    return -1;
                }
                this.addDummyByte = false;
                bArr[i] = 0;
                return 1;
            } else if (i2 <= 0) {
                return 0;
            } else {
                if (((long) i2) > j) {
                    i2 = (int) j;
                }
                synchronized (ZipFile.this.archive) {
                    ZipFile.this.archive.seek(this.loc);
                    read = ZipFile.this.archive.read(bArr, i, i2);
                }
                if (read > 0) {
                    long j2 = (long) read;
                    this.loc += j2;
                    this.remaining -= j2;
                }
                return read;
            }
        }
    }

    private static class Entry extends ZipArchiveEntry {
        private final OffsetEntry offsetEntry;

        Entry(OffsetEntry offsetEntry2) {
            this.offsetEntry = offsetEntry2;
        }

        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.offsetEntry.headerOffset == entry.offsetEntry.headerOffset && this.offsetEntry.dataOffset == entry.offsetEntry.dataOffset;
        }

        /* access modifiers changed from: package-private */
        public OffsetEntry getOffsetEntry() {
            return this.offsetEntry;
        }

        public int hashCode() {
            return (super.hashCode() * 3) + ((int) (this.offsetEntry.headerOffset % 2147483647L));
        }
    }

    private static final class NameAndComment {
        /* access modifiers changed from: private */
        public final byte[] comment;
        /* access modifiers changed from: private */
        public final byte[] name;

        private NameAndComment(byte[] bArr, byte[] bArr2) {
            this.name = bArr;
            this.comment = bArr2;
        }
    }

    private static final class OffsetEntry {
        /* access modifiers changed from: private */
        public long dataOffset;
        /* access modifiers changed from: private */
        public long headerOffset;

        private OffsetEntry() {
            this.headerOffset = -1;
            this.dataOffset = -1;
        }
    }

    public ZipFile(File file) throws IOException {
        this(file, "UTF8");
    }

    public ZipFile(File file, String str) throws IOException {
        this(file, str, true);
    }

    public ZipFile(File file, String str, boolean z) throws IOException {
        this.entries = new LinkedList();
        this.nameMap = new HashMap(509);
        this.closed = true;
        this.DWORD_BUF = new byte[8];
        this.WORD_BUF = new byte[4];
        this.CFH_BUF = new byte[42];
        this.SHORT_BUF = new byte[2];
        this.OFFSET_COMPARATOR = new Comparator<ZipArchiveEntry>() {
            public int compare(ZipArchiveEntry zipArchiveEntry, ZipArchiveEntry zipArchiveEntry2) {
                if (zipArchiveEntry == zipArchiveEntry2) {
                    return 0;
                }
                Entry entry = null;
                Entry entry2 = zipArchiveEntry instanceof Entry ? (Entry) zipArchiveEntry : null;
                if (zipArchiveEntry2 instanceof Entry) {
                    entry = (Entry) zipArchiveEntry2;
                }
                if (entry2 == null) {
                    return 1;
                }
                if (entry == null) {
                    return -1;
                }
                int i = ((entry2.getOffsetEntry().headerOffset - entry.getOffsetEntry().headerOffset) > 0 ? 1 : ((entry2.getOffsetEntry().headerOffset - entry.getOffsetEntry().headerOffset) == 0 ? 0 : -1));
                if (i == 0) {
                    return 0;
                }
                return i < 0 ? -1 : 1;
            }
        };
        this.archiveName = file.getAbsolutePath();
        this.encoding = str;
        this.zipEncoding = ZipEncodingHelper.getZipEncoding(str);
        this.useUnicodeExtraFields = z;
        this.archive = new RandomAccessFile(file, "r");
        try {
            resolveLocalFileHeaderData(populateFromCentralDirectory());
            this.closed = false;
        } catch (Throwable th) {
            this.closed = true;
            IOUtils.closeQuietly(this.archive);
            throw th;
        }
    }

    public ZipFile(String str) throws IOException {
        this(new File(str), "UTF8");
    }

    public ZipFile(String str, String str2) throws IOException {
        this(new File(str), str2, true);
    }

    public static void closeQuietly(ZipFile zipFile) {
        IOUtils.closeQuietly(zipFile);
    }

    private Map<ZipArchiveEntry, NameAndComment> populateFromCentralDirectory() throws IOException {
        HashMap hashMap = new HashMap();
        positionAtCentralDirectory();
        this.archive.readFully(this.WORD_BUF);
        long value = ZipLong.getValue(this.WORD_BUF);
        if (value == CFH_SIG || !startsWithLocalFileHeader()) {
            while (value == CFH_SIG) {
                readCentralDirectoryEntry(hashMap);
                this.archive.readFully(this.WORD_BUF);
                value = ZipLong.getValue(this.WORD_BUF);
            }
            return hashMap;
        }
        throw new IOException("central directory is empty, can't expand corrupt archive.");
    }

    private void positionAtCentralDirectory() throws IOException {
        positionAtEndOfCentralDirectoryRecord();
        boolean z = false;
        boolean z2 = this.archive.getFilePointer() > 20;
        if (z2) {
            RandomAccessFile randomAccessFile = this.archive;
            randomAccessFile.seek(randomAccessFile.getFilePointer() - 20);
            this.archive.readFully(this.WORD_BUF);
            z = Arrays.equals(ZipArchiveOutputStream.ZIP64_EOCD_LOC_SIG, this.WORD_BUF);
        }
        if (!z) {
            if (z2) {
                skipBytes(16);
            }
            positionAtCentralDirectory32();
            return;
        }
        positionAtCentralDirectory64();
    }

    private void positionAtCentralDirectory32() throws IOException {
        skipBytes(16);
        this.archive.readFully(this.WORD_BUF);
        this.archive.seek(ZipLong.getValue(this.WORD_BUF));
    }

    private void positionAtCentralDirectory64() throws IOException {
        skipBytes(4);
        this.archive.readFully(this.DWORD_BUF);
        this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
        this.archive.readFully(this.WORD_BUF);
        if (Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.ZIP64_EOCD_SIG)) {
            skipBytes(44);
            this.archive.readFully(this.DWORD_BUF);
            this.archive.seek(ZipEightByteInteger.getLongValue(this.DWORD_BUF));
            return;
        }
        throw new ZipException("archive's ZIP64 end of central directory locator is corrupt.");
    }

    private void positionAtEndOfCentralDirectoryRecord() throws IOException {
        if (!tryToLocateSignature(22, 65557, ZipArchiveOutputStream.EOCD_SIG)) {
            throw new ZipException("archive is not a ZIP archive");
        }
    }

    private void readCentralDirectoryEntry(Map<ZipArchiveEntry, NameAndComment> map) throws IOException {
        this.archive.readFully(this.CFH_BUF);
        OffsetEntry offsetEntry = new OffsetEntry();
        Entry entry = new Entry(offsetEntry);
        int value = ZipShort.getValue(this.CFH_BUF, 0);
        entry.setVersionMadeBy(value);
        entry.setPlatform((value >> 8) & 15);
        entry.setVersionRequired(ZipShort.getValue(this.CFH_BUF, 2));
        GeneralPurposeBit parse = GeneralPurposeBit.parse(this.CFH_BUF, 4);
        boolean usesUTF8ForNames = parse.usesUTF8ForNames();
        ZipEncoding zipEncoding2 = usesUTF8ForNames ? ZipEncodingHelper.UTF8_ZIP_ENCODING : this.zipEncoding;
        entry.setGeneralPurposeBit(parse);
        entry.setRawFlag(ZipShort.getValue(this.CFH_BUF, 4));
        entry.setMethod(ZipShort.getValue(this.CFH_BUF, 6));
        entry.setTime(ZipUtil.dosToJavaTime(ZipLong.getValue(this.CFH_BUF, 8)));
        entry.setCrc(ZipLong.getValue(this.CFH_BUF, 12));
        entry.setCompressedSize(ZipLong.getValue(this.CFH_BUF, 16));
        entry.setSize(ZipLong.getValue(this.CFH_BUF, 20));
        int value2 = ZipShort.getValue(this.CFH_BUF, 24);
        int value3 = ZipShort.getValue(this.CFH_BUF, 26);
        int value4 = ZipShort.getValue(this.CFH_BUF, 28);
        int value5 = ZipShort.getValue(this.CFH_BUF, 30);
        entry.setInternalAttributes(ZipShort.getValue(this.CFH_BUF, 32));
        entry.setExternalAttributes(ZipLong.getValue(this.CFH_BUF, 34));
        byte[] bArr = new byte[value2];
        this.archive.readFully(bArr);
        entry.setName(zipEncoding2.decode(bArr), bArr);
        long unused = offsetEntry.headerOffset = ZipLong.getValue(this.CFH_BUF, 38);
        this.entries.add(entry);
        byte[] bArr2 = new byte[value3];
        this.archive.readFully(bArr2);
        entry.setCentralDirectoryExtra(bArr2);
        setSizesAndOffsetFromZip64Extra(entry, offsetEntry, value5);
        byte[] bArr3 = new byte[value4];
        this.archive.readFully(bArr3);
        entry.setComment(zipEncoding2.decode(bArr3));
        if (!usesUTF8ForNames && this.useUnicodeExtraFields) {
            map.put(entry, new NameAndComment(bArr, bArr3));
        }
    }

    private void resolveLocalFileHeaderData(Map<ZipArchiveEntry, NameAndComment> map) throws IOException {
        Iterator<ZipArchiveEntry> it = this.entries.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            OffsetEntry offsetEntry = entry.getOffsetEntry();
            long access$200 = offsetEntry.headerOffset;
            RandomAccessFile randomAccessFile = this.archive;
            long j = access$200 + LFH_OFFSET_FOR_FILENAME_LENGTH;
            randomAccessFile.seek(j);
            this.archive.readFully(this.SHORT_BUF);
            int value = ZipShort.getValue(this.SHORT_BUF);
            this.archive.readFully(this.SHORT_BUF);
            int value2 = ZipShort.getValue(this.SHORT_BUF);
            int i = value;
            while (i > 0) {
                int skipBytes = this.archive.skipBytes(i);
                if (skipBytes > 0) {
                    i -= skipBytes;
                } else {
                    throw new IOException("failed to skip file name in local file header");
                }
            }
            byte[] bArr = new byte[value2];
            this.archive.readFully(bArr);
            entry.setExtra(bArr);
            long unused = offsetEntry.dataOffset = j + 2 + 2 + ((long) value) + ((long) value2);
            if (map.containsKey(entry)) {
                NameAndComment nameAndComment = map.get(entry);
                ZipUtil.setNameAndCommentFromExtraFields(entry, nameAndComment.name, nameAndComment.comment);
            }
            String name = entry.getName();
            LinkedList linkedList = this.nameMap.get(name);
            if (linkedList == null) {
                linkedList = new LinkedList();
                this.nameMap.put(name, linkedList);
            }
            linkedList.addLast(entry);
        }
    }

    private void setSizesAndOffsetFromZip64Extra(ZipArchiveEntry zipArchiveEntry, OffsetEntry offsetEntry, int i) throws IOException {
        Zip64ExtendedInformationExtraField zip64ExtendedInformationExtraField = (Zip64ExtendedInformationExtraField) zipArchiveEntry.getExtraField(Zip64ExtendedInformationExtraField.HEADER_ID);
        if (zip64ExtendedInformationExtraField != null) {
            boolean z = true;
            boolean z2 = zipArchiveEntry.getSize() == 4294967295L;
            boolean z3 = zipArchiveEntry.getCompressedSize() == 4294967295L;
            boolean z4 = offsetEntry.headerOffset == 4294967295L;
            if (i != 65535) {
                z = false;
            }
            zip64ExtendedInformationExtraField.reparseCentralDirectoryData(z2, z3, z4, z);
            if (z2) {
                zipArchiveEntry.setSize(zip64ExtendedInformationExtraField.getSize().getLongValue());
            } else if (z3) {
                zip64ExtendedInformationExtraField.setSize(new ZipEightByteInteger(zipArchiveEntry.getSize()));
            }
            if (z3) {
                zipArchiveEntry.setCompressedSize(zip64ExtendedInformationExtraField.getCompressedSize().getLongValue());
            } else if (z2) {
                zip64ExtendedInformationExtraField.setCompressedSize(new ZipEightByteInteger(zipArchiveEntry.getCompressedSize()));
            }
            if (z4) {
                long unused = offsetEntry.headerOffset = zip64ExtendedInformationExtraField.getRelativeHeaderOffset().getLongValue();
            }
        }
    }

    private void skipBytes(int i) throws IOException {
        int i2 = 0;
        while (i2 < i) {
            int skipBytes = this.archive.skipBytes(i - i2);
            if (skipBytes > 0) {
                i2 += skipBytes;
            } else {
                throw new EOFException();
            }
        }
    }

    private boolean startsWithLocalFileHeader() throws IOException {
        this.archive.seek(0);
        this.archive.readFully(this.WORD_BUF);
        return Arrays.equals(this.WORD_BUF, ZipArchiveOutputStream.LFH_SIG);
    }

    private boolean tryToLocateSignature(long j, long j2, byte[] bArr) throws IOException {
        long length = this.archive.length() - j;
        long max = Math.max(0, this.archive.length() - j2);
        boolean z = true;
        if (length >= 0) {
            while (true) {
                if (length < max) {
                    break;
                }
                this.archive.seek(length);
                int read = this.archive.read();
                if (read != -1) {
                    if (read == bArr[0] && this.archive.read() == bArr[1] && this.archive.read() == bArr[2] && this.archive.read() == bArr[3]) {
                        break;
                    }
                    length--;
                } else {
                    break;
                }
            }
        }
        z = false;
        if (z) {
            this.archive.seek(length);
        }
        return z;
    }

    public boolean canReadEntryData(ZipArchiveEntry zipArchiveEntry) {
        return ZipUtil.canHandleEntryData(zipArchiveEntry);
    }

    public void close() throws IOException {
        this.closed = true;
        this.archive.close();
    }

    public void copyRawEntries(ZipArchiveOutputStream zipArchiveOutputStream, ZipArchiveEntryPredicate zipArchiveEntryPredicate) throws IOException {
        Enumeration<ZipArchiveEntry> entriesInPhysicalOrder = getEntriesInPhysicalOrder();
        while (entriesInPhysicalOrder.hasMoreElements()) {
            ZipArchiveEntry nextElement = entriesInPhysicalOrder.nextElement();
            if (zipArchiveEntryPredicate.test(nextElement)) {
                zipArchiveOutputStream.addRawArchiveEntry(nextElement, getRawInputStream(nextElement));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            if (!this.closed) {
                PrintStream printStream = System.err;
                printStream.println("Cleaning up unclosed ZipFile for archive " + this.archiveName);
                close();
            }
        } finally {
            super.finalize();
        }
    }

    public String getEncoding() {
        return this.encoding;
    }

    public Iterable<ZipArchiveEntry> getEntries(String str) {
        List list = this.nameMap.get(str);
        return list != null ? list : Collections.emptyList();
    }

    public Enumeration<ZipArchiveEntry> getEntries() {
        return Collections.enumeration(this.entries);
    }

    /* JADX WARNING: type inference failed for: r3v4, types: [java.lang.Object[]] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Iterable<org.apache.commons.compress.archivers.zip.ZipArchiveEntry> getEntriesInPhysicalOrder(java.lang.String r3) {
        /*
            r2 = this;
            r0 = 0
            org.apache.commons.compress.archivers.zip.ZipArchiveEntry[] r0 = new org.apache.commons.compress.archivers.zip.ZipArchiveEntry[r0]
            java.util.Map<java.lang.String, java.util.LinkedList<org.apache.commons.compress.archivers.zip.ZipArchiveEntry>> r1 = r2.nameMap
            boolean r1 = r1.containsKey(r3)
            if (r1 == 0) goto L_0x001f
            java.util.Map<java.lang.String, java.util.LinkedList<org.apache.commons.compress.archivers.zip.ZipArchiveEntry>> r1 = r2.nameMap
            java.lang.Object r3 = r1.get(r3)
            java.util.LinkedList r3 = (java.util.LinkedList) r3
            java.lang.Object[] r3 = r3.toArray(r0)
            r0 = r3
            org.apache.commons.compress.archivers.zip.ZipArchiveEntry[] r0 = (org.apache.commons.compress.archivers.zip.ZipArchiveEntry[]) r0
            java.util.Comparator<org.apache.commons.compress.archivers.zip.ZipArchiveEntry> r3 = r2.OFFSET_COMPARATOR
            java.util.Arrays.sort(r0, r3)
        L_0x001f:
            java.util.List r3 = java.util.Arrays.asList(r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.zip.ZipFile.getEntriesInPhysicalOrder(java.lang.String):java.lang.Iterable");
    }

    public Enumeration<ZipArchiveEntry> getEntriesInPhysicalOrder() {
        List<ZipArchiveEntry> list = this.entries;
        ZipArchiveEntry[] zipArchiveEntryArr = (ZipArchiveEntry[]) list.toArray(new ZipArchiveEntry[list.size()]);
        Arrays.sort(zipArchiveEntryArr, this.OFFSET_COMPARATOR);
        return Collections.enumeration(Arrays.asList(zipArchiveEntryArr));
    }

    public ZipArchiveEntry getEntry(String str) {
        LinkedList linkedList = this.nameMap.get(str);
        if (linkedList != null) {
            return (ZipArchiveEntry) linkedList.getFirst();
        }
        return null;
    }

    public InputStream getInputStream(ZipArchiveEntry zipArchiveEntry) throws IOException, ZipException {
        if (!(zipArchiveEntry instanceof Entry)) {
            return null;
        }
        OffsetEntry offsetEntry = ((Entry) zipArchiveEntry).getOffsetEntry();
        ZipUtil.checkRequestedFeatures(zipArchiveEntry);
        BoundedInputStream boundedInputStream = new BoundedInputStream(offsetEntry.dataOffset, zipArchiveEntry.getCompressedSize());
        switch (C09193.$SwitchMap$org$apache$commons$compress$archivers$zip$ZipMethod[ZipMethod.getMethodByCode(zipArchiveEntry.getMethod()).ordinal()]) {
            case 1:
                return boundedInputStream;
            case 2:
                return new UnshrinkingInputStream(boundedInputStream);
            case 3:
                return new ExplodingInputStream(zipArchiveEntry.getGeneralPurposeBit().getSlidingDictionarySize(), zipArchiveEntry.getGeneralPurposeBit().getNumberOfShannonFanoTrees(), new BufferedInputStream(boundedInputStream));
            case 4:
                boundedInputStream.addDummy();
                final Inflater inflater = new Inflater(true);
                return new InflaterInputStream(boundedInputStream, inflater) {
                    public void close() throws IOException {
                        try {
                            super.close();
                        } finally {
                            inflater.end();
                        }
                    }
                };
            case 5:
                return new BZip2CompressorInputStream(boundedInputStream);
            default:
                throw new ZipException("Found unsupported compression method " + zipArchiveEntry.getMethod());
        }
    }

    public InputStream getRawInputStream(ZipArchiveEntry zipArchiveEntry) {
        if (!(zipArchiveEntry instanceof Entry)) {
            return null;
        }
        return new BoundedInputStream(((Entry) zipArchiveEntry).getOffsetEntry().dataOffset, zipArchiveEntry.getCompressedSize());
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String, java.io.InputStream] */
    public String getUnixSymlink(ZipArchiveEntry zipArchiveEntry) throws IOException {
        InputStream inputStream = 0;
        if (zipArchiveEntry == null || !zipArchiveEntry.isUnixSymlink()) {
            return inputStream;
        }
        try {
            inputStream = getInputStream(zipArchiveEntry);
            return this.zipEncoding.decode(IOUtils.toByteArray(inputStream));
        } finally {
            if (inputStream != 0) {
                inputStream.close();
            }
        }
    }
}
