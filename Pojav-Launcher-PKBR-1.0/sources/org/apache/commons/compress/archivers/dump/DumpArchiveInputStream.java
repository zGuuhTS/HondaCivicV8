package org.apache.commons.compress.archivers.dump;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import org.apache.commons.p012io.FileUtils;
import org.apache.commons.p012io.IOUtils;

public class DumpArchiveInputStream extends ArchiveInputStream {
    private DumpArchiveEntry active;
    private byte[] blockBuffer;
    final String encoding;
    private long entryOffset;
    private long entrySize;
    private long filepos;
    private boolean hasHitEOF;
    private boolean isClosed;
    private final Map<Integer, Dirent> names;
    private final Map<Integer, DumpArchiveEntry> pending;
    private Queue<DumpArchiveEntry> queue;
    protected TapeInputStream raw;
    private final byte[] readBuf;
    private int readIdx;
    private int recordOffset;
    private DumpArchiveSummary summary;
    private final ZipEncoding zipEncoding;

    public DumpArchiveInputStream(InputStream inputStream) throws ArchiveException {
        this(inputStream, (String) null);
    }

    public DumpArchiveInputStream(InputStream inputStream, String str) throws ArchiveException {
        this.readBuf = new byte[1024];
        HashMap hashMap = new HashMap();
        this.names = hashMap;
        this.pending = new HashMap();
        this.raw = new TapeInputStream(inputStream);
        this.hasHitEOF = false;
        this.encoding = str;
        ZipEncoding zipEncoding2 = ZipEncodingHelper.getZipEncoding(str);
        this.zipEncoding = zipEncoding2;
        try {
            byte[] readRecord = this.raw.readRecord();
            if (DumpArchiveUtil.verify(readRecord)) {
                DumpArchiveSummary dumpArchiveSummary = new DumpArchiveSummary(readRecord, zipEncoding2);
                this.summary = dumpArchiveSummary;
                this.raw.resetBlockSize(dumpArchiveSummary.getNTRec(), this.summary.isCompressed());
                this.blockBuffer = new byte[4096];
                readCLRI();
                readBITS();
                hashMap.put(2, new Dirent(2, 2, 4, "."));
                this.queue = new PriorityQueue(10, new Comparator<DumpArchiveEntry>() {
                    public int compare(DumpArchiveEntry dumpArchiveEntry, DumpArchiveEntry dumpArchiveEntry2) {
                        if (dumpArchiveEntry.getOriginalName() == null || dumpArchiveEntry2.getOriginalName() == null) {
                            return Integer.MAX_VALUE;
                        }
                        return dumpArchiveEntry.getOriginalName().compareTo(dumpArchiveEntry2.getOriginalName());
                    }
                });
                return;
            }
            throw new UnrecognizedFormatException();
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage(), e);
        }
    }

    private String getPath(DumpArchiveEntry dumpArchiveEntry) {
        Stack stack = new Stack();
        int ino = dumpArchiveEntry.getIno();
        while (true) {
            if (!this.names.containsKey(Integer.valueOf(ino))) {
                stack.clear();
                break;
            }
            Dirent dirent = this.names.get(Integer.valueOf(ino));
            stack.push(dirent.getName());
            if (dirent.getIno() == dirent.getParentIno()) {
                break;
            }
            ino = dirent.getParentIno();
        }
        if (stack.isEmpty()) {
            this.pending.put(Integer.valueOf(dumpArchiveEntry.getIno()), dumpArchiveEntry);
            return null;
        }
        StringBuilder sb = new StringBuilder((String) stack.pop());
        while (!stack.isEmpty()) {
            sb.append(IOUtils.DIR_SEPARATOR_UNIX);
            sb.append((String) stack.pop());
        }
        return sb.toString();
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < 32) {
            return false;
        }
        return i >= 1024 ? DumpArchiveUtil.verify(bArr) : 60012 == DumpArchiveUtil.convert32(bArr, 24);
    }

    private void readBITS() throws IOException {
        byte[] readRecord = this.raw.readRecord();
        if (DumpArchiveUtil.verify(readRecord)) {
            this.active = DumpArchiveEntry.parse(readRecord);
            if (DumpArchiveConstants.SEGMENT_TYPE.BITS != this.active.getHeaderType()) {
                throw new InvalidFormatException();
            } else if (this.raw.skip((long) (this.active.getHeaderCount() * 1024)) != -1) {
                this.readIdx = this.active.getHeaderCount();
            } else {
                throw new EOFException();
            }
        } else {
            throw new InvalidFormatException();
        }
    }

    private void readCLRI() throws IOException {
        byte[] readRecord = this.raw.readRecord();
        if (DumpArchiveUtil.verify(readRecord)) {
            this.active = DumpArchiveEntry.parse(readRecord);
            if (DumpArchiveConstants.SEGMENT_TYPE.CLRI != this.active.getHeaderType()) {
                throw new InvalidFormatException();
            } else if (this.raw.skip((long) (this.active.getHeaderCount() * 1024)) != -1) {
                this.readIdx = this.active.getHeaderCount();
            } else {
                throw new EOFException();
            }
        } else {
            throw new InvalidFormatException();
        }
    }

    private void readDirectoryEntry(DumpArchiveEntry dumpArchiveEntry) throws IOException {
        long entrySize2 = dumpArchiveEntry.getEntrySize();
        boolean z = true;
        while (true) {
            if (z || DumpArchiveConstants.SEGMENT_TYPE.ADDR == dumpArchiveEntry.getHeaderType()) {
                if (!z) {
                    this.raw.readRecord();
                }
                if (!this.names.containsKey(Integer.valueOf(dumpArchiveEntry.getIno())) && DumpArchiveConstants.SEGMENT_TYPE.INODE == dumpArchiveEntry.getHeaderType()) {
                    this.pending.put(Integer.valueOf(dumpArchiveEntry.getIno()), dumpArchiveEntry);
                }
                int headerCount = dumpArchiveEntry.getHeaderCount() * 1024;
                if (this.blockBuffer.length < headerCount) {
                    this.blockBuffer = new byte[headerCount];
                }
                if (this.raw.read(this.blockBuffer, 0, headerCount) == headerCount) {
                    int i = 0;
                    while (i < headerCount - 8 && ((long) i) < entrySize2 - 8) {
                        int convert32 = DumpArchiveUtil.convert32(this.blockBuffer, i);
                        int convert16 = DumpArchiveUtil.convert16(this.blockBuffer, i + 4);
                        byte[] bArr = this.blockBuffer;
                        byte b = bArr[i + 6];
                        String decode = DumpArchiveUtil.decode(this.zipEncoding, bArr, i + 8, bArr[i + 7]);
                        if (!".".equals(decode) && !"..".equals(decode)) {
                            this.names.put(Integer.valueOf(convert32), new Dirent(convert32, dumpArchiveEntry.getIno(), b, decode));
                            for (Map.Entry next : this.pending.entrySet()) {
                                String path = getPath((DumpArchiveEntry) next.getValue());
                                if (path != null) {
                                    ((DumpArchiveEntry) next.getValue()).setName(path);
                                    ((DumpArchiveEntry) next.getValue()).setSimpleName(this.names.get(next.getKey()).getName());
                                    this.queue.add(next.getValue());
                                }
                            }
                            for (DumpArchiveEntry ino : this.queue) {
                                this.pending.remove(Integer.valueOf(ino.getIno()));
                            }
                        }
                        i += convert16;
                    }
                    byte[] peek = this.raw.peek();
                    if (DumpArchiveUtil.verify(peek)) {
                        dumpArchiveEntry = DumpArchiveEntry.parse(peek);
                        entrySize2 -= FileUtils.ONE_KB;
                        z = false;
                    } else {
                        throw new InvalidFormatException();
                    }
                } else {
                    throw new EOFException();
                }
            } else {
                return;
            }
        }
    }

    public void close() throws IOException {
        if (!this.isClosed) {
            this.isClosed = true;
            this.raw.close();
        }
    }

    public long getBytesRead() {
        return this.raw.getBytesRead();
    }

    @Deprecated
    public int getCount() {
        return (int) getBytesRead();
    }

    public DumpArchiveEntry getNextDumpEntry() throws IOException {
        return getNextEntry();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b6, code lost:
        if (org.apache.commons.compress.archivers.dump.DumpArchiveConstants.SEGMENT_TYPE.END != r8.active.getHeaderType()) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b8, code lost:
        r8.hasHitEOF = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00bb, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00bc, code lost:
        r2 = r8.active;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00c4, code lost:
        if (r2.isDirectory() == false) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c6, code lost:
        readDirectoryEntry(r8.active);
        r8.entryOffset = 0;
        r8.entrySize = 0;
        r1 = r8.active.getHeaderCount();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00d6, code lost:
        r8.entryOffset = 0;
        r8.entrySize = r8.active.getEntrySize();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e0, code lost:
        r8.readIdx = r1;
        r8.recordOffset = r8.readBuf.length;
        r1 = getPath(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00eb, code lost:
        if (r1 != null) goto L_0x00ee;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ed, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00ee, code lost:
        r7 = r2;
        r2 = r1;
        r1 = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.commons.compress.archivers.dump.DumpArchiveEntry getNextEntry() throws java.io.IOException {
        /*
            r8 = this;
            java.util.Queue<org.apache.commons.compress.archivers.dump.DumpArchiveEntry> r0 = r8.queue
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0011
            java.util.Queue<org.apache.commons.compress.archivers.dump.DumpArchiveEntry> r0 = r8.queue
            java.lang.Object r0 = r0.remove()
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r0 = (org.apache.commons.compress.archivers.dump.DumpArchiveEntry) r0
            return r0
        L_0x0011:
            r0 = 0
            r1 = r0
            r2 = r1
        L_0x0014:
            if (r1 != 0) goto L_0x00f9
            boolean r1 = r8.hasHitEOF
            if (r1 == 0) goto L_0x001b
            return r0
        L_0x001b:
            int r1 = r8.readIdx
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r2 = r8.active
            int r2 = r2.getHeaderCount()
            r3 = -1
            if (r1 >= r2) goto L_0x0048
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r1 = r8.active
            int r2 = r8.readIdx
            int r5 = r2 + 1
            r8.readIdx = r5
            boolean r1 = r1.isSparseRecord(r2)
            if (r1 != 0) goto L_0x001b
            org.apache.commons.compress.archivers.dump.TapeInputStream r1 = r8.raw
            r5 = 1024(0x400, double:5.06E-321)
            long r1 = r1.skip(r5)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 == 0) goto L_0x0042
            goto L_0x001b
        L_0x0042:
            java.io.EOFException r0 = new java.io.EOFException
            r0.<init>()
            throw r0
        L_0x0048:
            r1 = 0
            r8.readIdx = r1
            org.apache.commons.compress.archivers.dump.TapeInputStream r2 = r8.raw
            long r5 = r2.getBytesRead()
            r8.filepos = r5
            org.apache.commons.compress.archivers.dump.TapeInputStream r2 = r8.raw
            byte[] r2 = r2.readRecord()
            boolean r5 = org.apache.commons.compress.archivers.dump.DumpArchiveUtil.verify(r2)
            if (r5 == 0) goto L_0x00f3
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r2 = org.apache.commons.compress.archivers.dump.DumpArchiveEntry.parse(r2)
        L_0x0063:
            r8.active = r2
            org.apache.commons.compress.archivers.dump.DumpArchiveConstants$SEGMENT_TYPE r2 = org.apache.commons.compress.archivers.dump.DumpArchiveConstants.SEGMENT_TYPE.ADDR
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r5 = r8.active
            org.apache.commons.compress.archivers.dump.DumpArchiveConstants$SEGMENT_TYPE r5 = r5.getHeaderType()
            if (r2 != r5) goto L_0x00ae
            org.apache.commons.compress.archivers.dump.TapeInputStream r2 = r8.raw
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r5 = r8.active
            int r5 = r5.getHeaderCount()
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r6 = r8.active
            int r6 = r6.getHeaderHoles()
            int r5 = r5 - r6
            int r5 = r5 * 1024
            long r5 = (long) r5
            long r5 = r2.skip(r5)
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 == 0) goto L_0x00a8
            org.apache.commons.compress.archivers.dump.TapeInputStream r2 = r8.raw
            long r5 = r2.getBytesRead()
            r8.filepos = r5
            org.apache.commons.compress.archivers.dump.TapeInputStream r2 = r8.raw
            byte[] r2 = r2.readRecord()
            boolean r5 = org.apache.commons.compress.archivers.dump.DumpArchiveUtil.verify(r2)
            if (r5 == 0) goto L_0x00a2
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r2 = org.apache.commons.compress.archivers.dump.DumpArchiveEntry.parse(r2)
            goto L_0x0063
        L_0x00a2:
            org.apache.commons.compress.archivers.dump.InvalidFormatException r0 = new org.apache.commons.compress.archivers.dump.InvalidFormatException
            r0.<init>()
            throw r0
        L_0x00a8:
            java.io.EOFException r0 = new java.io.EOFException
            r0.<init>()
            throw r0
        L_0x00ae:
            org.apache.commons.compress.archivers.dump.DumpArchiveConstants$SEGMENT_TYPE r2 = org.apache.commons.compress.archivers.dump.DumpArchiveConstants.SEGMENT_TYPE.END
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r3 = r8.active
            org.apache.commons.compress.archivers.dump.DumpArchiveConstants$SEGMENT_TYPE r3 = r3.getHeaderType()
            if (r2 != r3) goto L_0x00bc
            r1 = 1
            r8.hasHitEOF = r1
            return r0
        L_0x00bc:
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r2 = r8.active
            boolean r3 = r2.isDirectory()
            r4 = 0
            if (r3 == 0) goto L_0x00d6
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r1 = r8.active
            r8.readDirectoryEntry(r1)
            r8.entryOffset = r4
            r8.entrySize = r4
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r1 = r8.active
            int r1 = r1.getHeaderCount()
            goto L_0x00e0
        L_0x00d6:
            r8.entryOffset = r4
            org.apache.commons.compress.archivers.dump.DumpArchiveEntry r3 = r8.active
            long r3 = r3.getEntrySize()
            r8.entrySize = r3
        L_0x00e0:
            r8.readIdx = r1
            byte[] r1 = r8.readBuf
            int r1 = r1.length
            r8.recordOffset = r1
            java.lang.String r1 = r8.getPath(r2)
            if (r1 != 0) goto L_0x00ee
            r2 = r0
        L_0x00ee:
            r7 = r2
            r2 = r1
            r1 = r7
            goto L_0x0014
        L_0x00f3:
            org.apache.commons.compress.archivers.dump.InvalidFormatException r0 = new org.apache.commons.compress.archivers.dump.InvalidFormatException
            r0.<init>()
            throw r0
        L_0x00f9:
            r1.setName(r2)
            java.util.Map<java.lang.Integer, org.apache.commons.compress.archivers.dump.Dirent> r0 = r8.names
            int r2 = r1.getIno()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            java.lang.Object r0 = r0.get(r2)
            org.apache.commons.compress.archivers.dump.Dirent r0 = (org.apache.commons.compress.archivers.dump.Dirent) r0
            java.lang.String r0 = r0.getName()
            r1.setSimpleName(r0)
            long r2 = r8.filepos
            r1.setOffset(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.dump.DumpArchiveInputStream.getNextEntry():org.apache.commons.compress.archivers.dump.DumpArchiveEntry");
    }

    public DumpArchiveSummary getSummary() {
        return this.summary;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.hasHitEOF || this.isClosed) {
            return -1;
        }
        long j = this.entryOffset;
        long j2 = this.entrySize;
        if (j >= j2) {
            return -1;
        }
        if (this.active != null) {
            if (((long) i2) + j > j2) {
                i2 = (int) (j2 - j);
            }
            int i3 = 0;
            while (i2 > 0) {
                byte[] bArr2 = this.readBuf;
                int length = bArr2.length;
                int i4 = this.recordOffset;
                int length2 = i2 > length - i4 ? bArr2.length - i4 : i2;
                if (i4 + length2 <= bArr2.length) {
                    System.arraycopy(bArr2, i4, bArr, i, length2);
                    i3 += length2;
                    this.recordOffset += length2;
                    i2 -= length2;
                    i += length2;
                }
                if (i2 > 0) {
                    if (this.readIdx >= 512) {
                        byte[] readRecord = this.raw.readRecord();
                        if (DumpArchiveUtil.verify(readRecord)) {
                            this.active = DumpArchiveEntry.parse(readRecord);
                            this.readIdx = 0;
                        } else {
                            throw new InvalidFormatException();
                        }
                    }
                    DumpArchiveEntry dumpArchiveEntry = this.active;
                    int i5 = this.readIdx;
                    this.readIdx = i5 + 1;
                    if (!dumpArchiveEntry.isSparseRecord(i5)) {
                        TapeInputStream tapeInputStream = this.raw;
                        byte[] bArr3 = this.readBuf;
                        if (tapeInputStream.read(bArr3, 0, bArr3.length) != this.readBuf.length) {
                            throw new EOFException();
                        }
                    } else {
                        Arrays.fill(this.readBuf, (byte) 0);
                    }
                    this.recordOffset = 0;
                }
            }
            this.entryOffset += (long) i3;
            return i3;
        }
        throw new IllegalStateException("No current dump entry");
    }
}
