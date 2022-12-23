package org.apache.commons.compress.archivers.zip;

import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

public abstract class AbstractUnicodeExtraField implements ZipExtraField {
    private byte[] data;
    private long nameCRC32;
    private byte[] unicodeName;

    protected AbstractUnicodeExtraField() {
    }

    protected AbstractUnicodeExtraField(String str, byte[] bArr) {
        this(str, bArr, 0, bArr.length);
    }

    protected AbstractUnicodeExtraField(String str, byte[] bArr, int i, int i2) {
        CRC32 crc32 = new CRC32();
        crc32.update(bArr, i, i2);
        this.nameCRC32 = crc32.getValue();
        try {
            this.unicodeName = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("FATAL: UTF-8 encoding not supported.", e);
        }
    }

    private void assembleData() {
        byte[] bArr = this.unicodeName;
        if (bArr != null) {
            byte[] bArr2 = new byte[(bArr.length + 5)];
            this.data = bArr2;
            bArr2[0] = 1;
            System.arraycopy(ZipLong.getBytes(this.nameCRC32), 0, this.data, 1, 4);
            byte[] bArr3 = this.unicodeName;
            System.arraycopy(bArr3, 0, this.data, 5, bArr3.length);
        }
    }

    public byte[] getCentralDirectoryData() {
        if (this.data == null) {
            assembleData();
        }
        byte[] bArr = this.data;
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    public ZipShort getCentralDirectoryLength() {
        if (this.data == null) {
            assembleData();
        }
        byte[] bArr = this.data;
        return new ZipShort(bArr != null ? bArr.length : 0);
    }

    public byte[] getLocalFileDataData() {
        return getCentralDirectoryData();
    }

    public ZipShort getLocalFileDataLength() {
        return getCentralDirectoryLength();
    }

    public long getNameCRC32() {
        return this.nameCRC32;
    }

    public byte[] getUnicodeName() {
        byte[] bArr = this.unicodeName;
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    public void parseFromCentralDirectoryData(byte[] bArr, int i, int i2) throws ZipException {
        parseFromLocalFileData(bArr, i, i2);
    }

    public void parseFromLocalFileData(byte[] bArr, int i, int i2) throws ZipException {
        if (i2 >= 5) {
            byte b = bArr[i];
            if (b == 1) {
                this.nameCRC32 = ZipLong.getValue(bArr, i + 1);
                int i3 = i2 - 5;
                byte[] bArr2 = new byte[i3];
                this.unicodeName = bArr2;
                System.arraycopy(bArr, i + 5, bArr2, 0, i3);
                this.data = null;
                return;
            }
            throw new ZipException("Unsupported version [" + b + "] for UniCode path extra data.");
        }
        throw new ZipException("UniCode path extra data must have at least 5 bytes.");
    }

    public void setNameCRC32(long j) {
        this.nameCRC32 = j;
        this.data = null;
    }

    public void setUnicodeName(byte[] bArr) {
        if (bArr != null) {
            byte[] bArr2 = new byte[bArr.length];
            this.unicodeName = bArr2;
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        } else {
            this.unicodeName = null;
        }
        this.data = null;
    }
}
