package org.apache.commons.p012io.input;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import org.apache.commons.p012io.Charsets;
import top.defaults.checkerboarddrawable.BuildConfig;

/* renamed from: org.apache.commons.io.input.ReversedLinesFileReader */
public class ReversedLinesFileReader implements Closeable {
    /* access modifiers changed from: private */
    public final int avoidNewlineSplitBufferSize;
    /* access modifiers changed from: private */
    public final int blockSize;
    /* access modifiers changed from: private */
    public final int byteDecrement;
    private FilePart currentFilePart;
    /* access modifiers changed from: private */
    public final Charset encoding;
    /* access modifiers changed from: private */
    public final byte[][] newLineSequences;
    /* access modifiers changed from: private */
    public final RandomAccessFile randomAccessFile;
    private final long totalBlockCount;
    private final long totalByteLength;
    private boolean trailingNewlineOfFileSkipped;

    /* renamed from: org.apache.commons.io.input.ReversedLinesFileReader$FilePart */
    private class FilePart {
        private int currentLastBytePos;
        private final byte[] data;
        private byte[] leftOver;

        /* renamed from: no */
        private final long f175no;

        private FilePart(long j, int i, byte[] bArr) throws IOException {
            this.f175no = j;
            byte[] bArr2 = new byte[((bArr != null ? bArr.length : 0) + i)];
            this.data = bArr2;
            long access$300 = (long) ReversedLinesFileReader.this.blockSize;
            if (j > 0) {
                ReversedLinesFileReader.this.randomAccessFile.seek((j - 1) * access$300);
                if (ReversedLinesFileReader.this.randomAccessFile.read(bArr2, 0, i) != i) {
                    throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
                }
            }
            if (bArr != null) {
                System.arraycopy(bArr, 0, bArr2, i, bArr.length);
            }
            this.currentLastBytePos = bArr2.length - 1;
            this.leftOver = null;
        }

        private void createLeftOver() {
            int i = this.currentLastBytePos + 1;
            if (i > 0) {
                byte[] bArr = new byte[i];
                this.leftOver = bArr;
                System.arraycopy(this.data, 0, bArr, 0, i);
            } else {
                this.leftOver = null;
            }
            this.currentLastBytePos = -1;
        }

        private int getNewLineMatchByteCount(byte[] bArr, int i) {
            for (int[] iArr : ReversedLinesFileReader.this.newLineSequences) {
                boolean z = true;
                for (int length = iArr.length - 1; length >= 0; length--) {
                    int length2 = (i + length) - (iArr.length - 1);
                    z &= length2 >= 0 && bArr[length2] == iArr[length];
                }
                if (z) {
                    return iArr.length;
                }
            }
            return 0;
        }

        /* access modifiers changed from: private */
        public String readLine() throws IOException {
            String str;
            boolean z = this.f175no == 1;
            int i = this.currentLastBytePos;
            while (true) {
                if (i > -1) {
                    if (!z && i < ReversedLinesFileReader.this.avoidNewlineSplitBufferSize) {
                        break;
                    }
                    int newLineMatchByteCount = getNewLineMatchByteCount(this.data, i);
                    if (newLineMatchByteCount <= 0) {
                        i -= ReversedLinesFileReader.this.byteDecrement;
                        if (i < 0) {
                            break;
                        }
                    } else {
                        int i2 = i + 1;
                        int i3 = (this.currentLastBytePos - i2) + 1;
                        if (i3 >= 0) {
                            byte[] bArr = new byte[i3];
                            System.arraycopy(this.data, i2, bArr, 0, i3);
                            str = new String(bArr, ReversedLinesFileReader.this.encoding);
                            this.currentLastBytePos = i - newLineMatchByteCount;
                        } else {
                            throw new IllegalStateException("Unexpected negative line length=" + i3);
                        }
                    }
                } else {
                    break;
                }
            }
            createLeftOver();
            str = null;
            if (!z || this.leftOver == null) {
                return str;
            }
            String str2 = new String(this.leftOver, ReversedLinesFileReader.this.encoding);
            this.leftOver = null;
            return str2;
        }

        /* access modifiers changed from: private */
        public FilePart rollOver() throws IOException {
            if (this.currentLastBytePos > -1) {
                throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
            } else if (this.f175no > 1) {
                ReversedLinesFileReader reversedLinesFileReader = ReversedLinesFileReader.this;
                return new FilePart(this.f175no - 1, reversedLinesFileReader.blockSize, this.leftOver);
            } else if (this.leftOver == null) {
                return null;
            } else {
                throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.this.encoding));
            }
        }
    }

    public ReversedLinesFileReader(File file) throws IOException {
        this(file, 4096, Charset.defaultCharset().toString());
    }

    public ReversedLinesFileReader(File file, int i, String str) throws IOException {
        this(file, i, Charsets.toCharset(str));
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0054  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ReversedLinesFileReader(java.io.File r10, int r11, java.nio.charset.Charset r12) throws java.io.IOException {
        /*
            r9 = this;
            r9.<init>()
            r0 = 0
            r9.trailingNewlineOfFileSkipped = r0
            r9.blockSize = r11
            r9.encoding = r12
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile
            java.lang.String r2 = "r"
            r1.<init>(r10, r2)
            r9.randomAccessFile = r1
            long r1 = r1.length()
            r9.totalByteLength = r1
            long r3 = (long) r11
            long r5 = r1 % r3
            int r10 = (int) r5
            if (r10 <= 0) goto L_0x0026
            long r1 = r1 / r3
            r3 = 1
            long r1 = r1 + r3
            r9.totalBlockCount = r1
            goto L_0x0032
        L_0x0026:
            long r3 = r1 / r3
            r9.totalBlockCount = r3
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 <= 0) goto L_0x0032
            r6 = r11
            goto L_0x0033
        L_0x0032:
            r6 = r10
        L_0x0033:
            org.apache.commons.io.input.ReversedLinesFileReader$FilePart r10 = new org.apache.commons.io.input.ReversedLinesFileReader$FilePart
            long r4 = r9.totalBlockCount
            r7 = 0
            r8 = 0
            r2 = r10
            r3 = r9
            r2.<init>(r4, r6, r7)
            r9.currentFilePart = r10
            java.nio.charset.Charset r10 = org.apache.commons.p012io.Charsets.toCharset((java.nio.charset.Charset) r12)
            java.nio.charset.CharsetEncoder r11 = r10.newEncoder()
            float r11 = r11.maxBytesPerChar()
            r1 = 1065353216(0x3f800000, float:1.0)
            int r11 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            r1 = 2
            r2 = 1
            if (r11 != 0) goto L_0x0057
        L_0x0054:
            r9.byteDecrement = r2
            goto L_0x00a8
        L_0x0057:
            java.lang.String r11 = "UTF-8"
            java.nio.charset.Charset r11 = java.nio.charset.Charset.forName(r11)
            if (r10 != r11) goto L_0x0060
            goto L_0x0054
        L_0x0060:
            java.lang.String r11 = "Shift_JIS"
            java.nio.charset.Charset r11 = java.nio.charset.Charset.forName(r11)
            if (r10 != r11) goto L_0x0069
            goto L_0x0054
        L_0x0069:
            java.lang.String r11 = "UTF-16BE"
            java.nio.charset.Charset r11 = java.nio.charset.Charset.forName(r11)
            if (r10 == r11) goto L_0x00a6
            java.lang.String r11 = "UTF-16LE"
            java.nio.charset.Charset r11 = java.nio.charset.Charset.forName(r11)
            if (r10 != r11) goto L_0x007a
            goto L_0x00a6
        L_0x007a:
            java.lang.String r11 = "UTF-16"
            java.nio.charset.Charset r11 = java.nio.charset.Charset.forName(r11)
            if (r10 != r11) goto L_0x008a
            java.io.UnsupportedEncodingException r10 = new java.io.UnsupportedEncodingException
            java.lang.String r11 = "For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)"
            r10.<init>(r11)
            throw r10
        L_0x008a:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Encoding "
            r10.append(r11)
            r10.append(r12)
            java.lang.String r11 = " is not supported yet (feel free to submit a patch)"
            r10.append(r11)
            java.io.UnsupportedEncodingException r11 = new java.io.UnsupportedEncodingException
            java.lang.String r10 = r10.toString()
            r11.<init>(r10)
            throw r11
        L_0x00a6:
            r9.byteDecrement = r1
        L_0x00a8:
            r10 = 3
            byte[][] r10 = new byte[r10][]
            java.lang.String r11 = "\r\n"
            byte[] r11 = r11.getBytes(r12)
            r10[r0] = r11
            java.lang.String r11 = "\n"
            byte[] r11 = r11.getBytes(r12)
            r10[r2] = r11
            java.lang.String r11 = "\r"
            byte[] r11 = r11.getBytes(r12)
            r10[r1] = r11
            r9.newLineSequences = r10
            r10 = r10[r0]
            int r10 = r10.length
            r9.avoidNewlineSplitBufferSize = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p012io.input.ReversedLinesFileReader.<init>(java.io.File, int, java.nio.charset.Charset):void");
    }

    public void close() throws IOException {
        this.randomAccessFile.close();
    }

    public String readLine() throws IOException {
        String access$100 = this.currentFilePart.readLine();
        while (access$100 == null) {
            FilePart access$200 = this.currentFilePart.rollOver();
            this.currentFilePart = access$200;
            if (access$200 == null) {
                break;
            }
            access$100 = access$200.readLine();
        }
        if (!BuildConfig.FLAVOR.equals(access$100) || this.trailingNewlineOfFileSkipped) {
            return access$100;
        }
        this.trailingNewlineOfFileSkipped = true;
        return readLine();
    }
}
