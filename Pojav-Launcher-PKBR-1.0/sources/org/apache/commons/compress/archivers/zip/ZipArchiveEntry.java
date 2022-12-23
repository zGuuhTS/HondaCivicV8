package org.apache.commons.compress.archivers.zip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ExtraFieldUtils;
import org.apache.commons.p012io.IOUtils;
import top.defaults.checkerboarddrawable.BuildConfig;

public class ZipArchiveEntry extends ZipEntry implements ArchiveEntry {
    public static final int CRC_UNKNOWN = -1;
    private static final byte[] EMPTY = new byte[0];
    public static final int PLATFORM_FAT = 0;
    public static final int PLATFORM_UNIX = 3;
    private static final int SHORT_MASK = 65535;
    private static final int SHORT_SHIFT = 16;
    private static final ZipExtraField[] noExtraFields = new ZipExtraField[0];
    private long externalAttributes;
    private ZipExtraField[] extraFields;
    private GeneralPurposeBit gpb;
    private int internalAttributes;
    private int method;
    private String name;
    private int platform;
    private int rawFlag;
    private byte[] rawName;
    private long size;
    private UnparseableExtraFieldData unparseableExtra;
    private int versionMadeBy;
    private int versionRequired;

    protected ZipArchiveEntry() {
        this(BuildConfig.FLAVOR);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ZipArchiveEntry(java.io.File r3, java.lang.String r4) {
        /*
            r2 = this;
            boolean r0 = r3.isDirectory()
            if (r0 == 0) goto L_0x001d
            java.lang.String r0 = "/"
            boolean r1 = r4.endsWith(r0)
            if (r1 != 0) goto L_0x001d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r4)
            r1.append(r0)
            java.lang.String r4 = r1.toString()
        L_0x001d:
            r2.<init>((java.lang.String) r4)
            boolean r4 = r3.isFile()
            if (r4 == 0) goto L_0x002d
            long r0 = r3.length()
            r2.setSize(r0)
        L_0x002d:
            long r3 = r3.lastModified()
            r2.setTime(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.archivers.zip.ZipArchiveEntry.<init>(java.io.File, java.lang.String):void");
    }

    public ZipArchiveEntry(String str) {
        super(str);
        this.method = -1;
        this.size = -1;
        this.internalAttributes = 0;
        this.platform = 0;
        this.externalAttributes = 0;
        this.unparseableExtra = null;
        this.name = null;
        this.rawName = null;
        this.gpb = new GeneralPurposeBit();
        setName(str);
    }

    public ZipArchiveEntry(ZipEntry zipEntry) throws ZipException {
        super(zipEntry);
        this.method = -1;
        this.size = -1;
        this.internalAttributes = 0;
        this.platform = 0;
        this.externalAttributes = 0;
        this.unparseableExtra = null;
        this.name = null;
        this.rawName = null;
        this.gpb = new GeneralPurposeBit();
        setName(zipEntry.getName());
        byte[] extra = zipEntry.getExtra();
        if (extra != null) {
            setExtraFields(ExtraFieldUtils.parse(extra, true, ExtraFieldUtils.UnparseableExtraField.READ));
        } else {
            setExtra();
        }
        setMethod(zipEntry.getMethod());
        this.size = zipEntry.getSize();
    }

    public ZipArchiveEntry(ZipArchiveEntry zipArchiveEntry) throws ZipException {
        this((ZipEntry) zipArchiveEntry);
        setInternalAttributes(zipArchiveEntry.getInternalAttributes());
        setExternalAttributes(zipArchiveEntry.getExternalAttributes());
        setExtraFields(getAllExtraFieldsNoCopy());
        setPlatform(zipArchiveEntry.getPlatform());
        GeneralPurposeBit generalPurposeBit = zipArchiveEntry.getGeneralPurposeBit();
        setGeneralPurposeBit(generalPurposeBit == null ? null : (GeneralPurposeBit) generalPurposeBit.clone());
    }

    private ZipExtraField[] copyOf(ZipExtraField[] zipExtraFieldArr) {
        return copyOf(zipExtraFieldArr, zipExtraFieldArr.length);
    }

    private ZipExtraField[] copyOf(ZipExtraField[] zipExtraFieldArr, int i) {
        ZipExtraField[] zipExtraFieldArr2 = new ZipExtraField[i];
        System.arraycopy(zipExtraFieldArr, 0, zipExtraFieldArr2, 0, Math.min(zipExtraFieldArr.length, i));
        return zipExtraFieldArr2;
    }

    private ZipExtraField[] getAllExtraFields() {
        ZipExtraField[] allExtraFieldsNoCopy = getAllExtraFieldsNoCopy();
        return allExtraFieldsNoCopy == this.extraFields ? copyOf(allExtraFieldsNoCopy) : allExtraFieldsNoCopy;
    }

    private ZipExtraField[] getAllExtraFieldsNoCopy() {
        ZipExtraField[] zipExtraFieldArr = this.extraFields;
        return zipExtraFieldArr == null ? getUnparseableOnly() : this.unparseableExtra != null ? getMergedFields() : zipExtraFieldArr;
    }

    private ZipExtraField[] getMergedFields() {
        ZipExtraField[] zipExtraFieldArr = this.extraFields;
        ZipExtraField[] copyOf = copyOf(zipExtraFieldArr, zipExtraFieldArr.length + 1);
        copyOf[this.extraFields.length] = this.unparseableExtra;
        return copyOf;
    }

    private ZipExtraField[] getParseableExtraFields() {
        ZipExtraField[] parseableExtraFieldsNoCopy = getParseableExtraFieldsNoCopy();
        return parseableExtraFieldsNoCopy == this.extraFields ? copyOf(parseableExtraFieldsNoCopy) : parseableExtraFieldsNoCopy;
    }

    private ZipExtraField[] getParseableExtraFieldsNoCopy() {
        ZipExtraField[] zipExtraFieldArr = this.extraFields;
        return zipExtraFieldArr == null ? noExtraFields : zipExtraFieldArr;
    }

    private ZipExtraField[] getUnparseableOnly() {
        UnparseableExtraFieldData unparseableExtraFieldData = this.unparseableExtra;
        if (unparseableExtraFieldData == null) {
            return noExtraFields;
        }
        return new ZipExtraField[]{unparseableExtraFieldData};
    }

    private void mergeExtraFields(ZipExtraField[] zipExtraFieldArr, boolean z) throws ZipException {
        if (this.extraFields == null) {
            setExtraFields(zipExtraFieldArr);
            return;
        }
        for (ZipExtraField zipExtraField : zipExtraFieldArr) {
            ZipExtraField extraField = zipExtraField instanceof UnparseableExtraFieldData ? this.unparseableExtra : getExtraField(zipExtraField.getHeaderId());
            if (extraField == null) {
                addExtraField(zipExtraField);
            } else if (z) {
                byte[] localFileDataData = zipExtraField.getLocalFileDataData();
                extraField.parseFromLocalFileData(localFileDataData, 0, localFileDataData.length);
            } else {
                byte[] centralDirectoryData = zipExtraField.getCentralDirectoryData();
                extraField.parseFromCentralDirectoryData(centralDirectoryData, 0, centralDirectoryData.length);
            }
        }
        setExtra();
    }

    public void addAsFirstExtraField(ZipExtraField zipExtraField) {
        if (zipExtraField instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData) zipExtraField;
        } else {
            if (getExtraField(zipExtraField.getHeaderId()) != null) {
                removeExtraField(zipExtraField.getHeaderId());
            }
            ZipExtraField[] zipExtraFieldArr = this.extraFields;
            ZipExtraField[] zipExtraFieldArr2 = new ZipExtraField[(zipExtraFieldArr != null ? zipExtraFieldArr.length + 1 : 1)];
            this.extraFields = zipExtraFieldArr2;
            zipExtraFieldArr2[0] = zipExtraField;
            if (zipExtraFieldArr != null) {
                System.arraycopy(zipExtraFieldArr, 0, zipExtraFieldArr2, 1, zipExtraFieldArr2.length - 1);
            }
        }
        setExtra();
    }

    public void addExtraField(ZipExtraField zipExtraField) {
        if (zipExtraField instanceof UnparseableExtraFieldData) {
            this.unparseableExtra = (UnparseableExtraFieldData) zipExtraField;
        } else if (this.extraFields == null) {
            this.extraFields = new ZipExtraField[]{zipExtraField};
        } else {
            if (getExtraField(zipExtraField.getHeaderId()) != null) {
                removeExtraField(zipExtraField.getHeaderId());
            }
            ZipExtraField[] zipExtraFieldArr = this.extraFields;
            ZipExtraField[] copyOf = copyOf(zipExtraFieldArr, zipExtraFieldArr.length + 1);
            copyOf[copyOf.length - 1] = zipExtraField;
            this.extraFields = copyOf;
        }
        setExtra();
    }

    public Object clone() {
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) super.clone();
        zipArchiveEntry.setInternalAttributes(getInternalAttributes());
        zipArchiveEntry.setExternalAttributes(getExternalAttributes());
        zipArchiveEntry.setExtraFields(getAllExtraFieldsNoCopy());
        return zipArchiveEntry;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) obj;
        String name2 = getName();
        String name3 = zipArchiveEntry.getName();
        if (name2 == null) {
            if (name3 != null) {
                return false;
            }
        } else if (!name2.equals(name3)) {
            return false;
        }
        String comment = getComment();
        String comment2 = zipArchiveEntry.getComment();
        if (comment == null) {
            comment = BuildConfig.FLAVOR;
        }
        if (comment2 == null) {
            comment2 = BuildConfig.FLAVOR;
        }
        return getTime() == zipArchiveEntry.getTime() && comment.equals(comment2) && getInternalAttributes() == zipArchiveEntry.getInternalAttributes() && getPlatform() == zipArchiveEntry.getPlatform() && getExternalAttributes() == zipArchiveEntry.getExternalAttributes() && getMethod() == zipArchiveEntry.getMethod() && getSize() == zipArchiveEntry.getSize() && getCrc() == zipArchiveEntry.getCrc() && getCompressedSize() == zipArchiveEntry.getCompressedSize() && Arrays.equals(getCentralDirectoryExtra(), zipArchiveEntry.getCentralDirectoryExtra()) && Arrays.equals(getLocalFileDataExtra(), zipArchiveEntry.getLocalFileDataExtra()) && this.gpb.equals(zipArchiveEntry.gpb);
    }

    public byte[] getCentralDirectoryExtra() {
        return ExtraFieldUtils.mergeCentralDirectoryData(getAllExtraFieldsNoCopy());
    }

    public long getExternalAttributes() {
        return this.externalAttributes;
    }

    public ZipExtraField getExtraField(ZipShort zipShort) {
        ZipExtraField[] zipExtraFieldArr = this.extraFields;
        if (zipExtraFieldArr == null) {
            return null;
        }
        for (ZipExtraField zipExtraField : zipExtraFieldArr) {
            if (zipShort.equals(zipExtraField.getHeaderId())) {
                return zipExtraField;
            }
        }
        return null;
    }

    public ZipExtraField[] getExtraFields() {
        return getParseableExtraFields();
    }

    public ZipExtraField[] getExtraFields(boolean z) {
        return z ? getAllExtraFields() : getParseableExtraFields();
    }

    public GeneralPurposeBit getGeneralPurposeBit() {
        return this.gpb;
    }

    public int getInternalAttributes() {
        return this.internalAttributes;
    }

    public Date getLastModifiedDate() {
        return new Date(getTime());
    }

    public byte[] getLocalFileDataExtra() {
        byte[] extra = getExtra();
        return extra != null ? extra : EMPTY;
    }

    public int getMethod() {
        return this.method;
    }

    public String getName() {
        String str = this.name;
        return str == null ? super.getName() : str;
    }

    public int getPlatform() {
        return this.platform;
    }

    public int getRawFlag() {
        return this.rawFlag;
    }

    public byte[] getRawName() {
        byte[] bArr = this.rawName;
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }

    public long getSize() {
        return this.size;
    }

    public int getUnixMode() {
        if (this.platform != 3) {
            return 0;
        }
        return (int) ((getExternalAttributes() >> 16) & 65535);
    }

    public UnparseableExtraFieldData getUnparseableExtraFieldData() {
        return this.unparseableExtra;
    }

    public int getVersionMadeBy() {
        return this.versionMadeBy;
    }

    public int getVersionRequired() {
        return this.versionRequired;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public boolean isDirectory() {
        return getName().endsWith("/");
    }

    public boolean isUnixSymlink() {
        return (getUnixMode() & 40960) == 40960;
    }

    public void removeExtraField(ZipShort zipShort) {
        if (this.extraFields != null) {
            ArrayList arrayList = new ArrayList();
            for (ZipExtraField zipExtraField : this.extraFields) {
                if (!zipShort.equals(zipExtraField.getHeaderId())) {
                    arrayList.add(zipExtraField);
                }
            }
            if (this.extraFields.length != arrayList.size()) {
                this.extraFields = (ZipExtraField[]) arrayList.toArray(new ZipExtraField[arrayList.size()]);
                setExtra();
                return;
            }
            throw new NoSuchElementException();
        }
        throw new NoSuchElementException();
    }

    public void removeUnparseableExtraFieldData() {
        if (this.unparseableExtra != null) {
            this.unparseableExtra = null;
            setExtra();
            return;
        }
        throw new NoSuchElementException();
    }

    public void setCentralDirectoryExtra(byte[] bArr) {
        try {
            mergeExtraFields(ExtraFieldUtils.parse(bArr, false, ExtraFieldUtils.UnparseableExtraField.READ), false);
        } catch (ZipException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void setExternalAttributes(long j) {
        this.externalAttributes = j;
    }

    /* access modifiers changed from: protected */
    public void setExtra() {
        super.setExtra(ExtraFieldUtils.mergeLocalFileDataData(getAllExtraFieldsNoCopy()));
    }

    public void setExtra(byte[] bArr) throws RuntimeException {
        try {
            mergeExtraFields(ExtraFieldUtils.parse(bArr, true, ExtraFieldUtils.UnparseableExtraField.READ), true);
        } catch (ZipException e) {
            throw new RuntimeException("Error parsing extra fields for entry: " + getName() + " - " + e.getMessage(), e);
        }
    }

    public void setExtraFields(ZipExtraField[] zipExtraFieldArr) {
        ArrayList arrayList = new ArrayList();
        for (UnparseableExtraFieldData unparseableExtraFieldData : zipExtraFieldArr) {
            if (unparseableExtraFieldData instanceof UnparseableExtraFieldData) {
                this.unparseableExtra = unparseableExtraFieldData;
            } else {
                arrayList.add(unparseableExtraFieldData);
            }
        }
        this.extraFields = (ZipExtraField[]) arrayList.toArray(new ZipExtraField[arrayList.size()]);
        setExtra();
    }

    public void setGeneralPurposeBit(GeneralPurposeBit generalPurposeBit) {
        this.gpb = generalPurposeBit;
    }

    public void setInternalAttributes(int i) {
        this.internalAttributes = i;
    }

    public void setMethod(int i) {
        if (i >= 0) {
            this.method = i;
            return;
        }
        throw new IllegalArgumentException("ZIP compression method can not be negative: " + i);
    }

    /* access modifiers changed from: protected */
    public void setName(String str) {
        if (str != null && getPlatform() == 0 && !str.contains("/")) {
            str = str.replace(IOUtils.DIR_SEPARATOR_WINDOWS, IOUtils.DIR_SEPARATOR_UNIX);
        }
        this.name = str;
    }

    /* access modifiers changed from: protected */
    public void setName(String str, byte[] bArr) {
        setName(str);
        this.rawName = bArr;
    }

    /* access modifiers changed from: protected */
    public void setPlatform(int i) {
        this.platform = i;
    }

    public void setRawFlag(int i) {
        this.rawFlag = i;
    }

    public void setSize(long j) {
        if (j >= 0) {
            this.size = j;
            return;
        }
        throw new IllegalArgumentException("invalid entry size");
    }

    public void setUnixMode(int i) {
        int i2 = 0;
        int i3 = (i & 128) == 0 ? 1 : 0;
        if (isDirectory()) {
            i2 = 16;
        }
        setExternalAttributes((long) ((i << 16) | i3 | i2));
        this.platform = 3;
    }

    public void setVersionMadeBy(int i) {
        this.versionMadeBy = i;
    }

    public void setVersionRequired(int i) {
        this.versionRequired = i;
    }
}
