package org.apache.commons.compress.archivers.zip;

import androidx.core.internal.view.SupportMenu;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class PKWareExtraHeader implements ZipExtraField {
    private byte[] centralData;
    private final ZipShort headerId;
    private byte[] localData;

    public enum EncryptionAlgorithm {
        DES(26113),
        RC2pre52(26114),
        TripleDES168(26115),
        TripleDES192(26121),
        AES128(26126),
        AES192(26127),
        AES256(26128),
        RC2(26370),
        RC4(26625),
        UNKNOWN(SupportMenu.USER_MASK);
        
        private static final Map<Integer, EncryptionAlgorithm> codeToEnum = null;
        private final int code;

        static {
            int i;
            HashMap hashMap = new HashMap();
            for (EncryptionAlgorithm encryptionAlgorithm : values()) {
                hashMap.put(Integer.valueOf(encryptionAlgorithm.getCode()), encryptionAlgorithm);
            }
            codeToEnum = Collections.unmodifiableMap(hashMap);
        }

        private EncryptionAlgorithm(int i) {
            this.code = i;
        }

        public static EncryptionAlgorithm getAlgorithmByCode(int i) {
            return codeToEnum.get(Integer.valueOf(i));
        }

        public int getCode() {
            return this.code;
        }
    }

    public enum HashAlgorithm {
        NONE(0),
        CRC32(1),
        MD5(32771),
        SHA1(32772),
        RIPEND160(32775),
        SHA256(32780),
        SHA384(32781),
        SHA512(32782);
        
        private static final Map<Integer, HashAlgorithm> codeToEnum = null;
        private final int code;

        static {
            int i;
            HashMap hashMap = new HashMap();
            for (HashAlgorithm hashAlgorithm : values()) {
                hashMap.put(Integer.valueOf(hashAlgorithm.getCode()), hashAlgorithm);
            }
            codeToEnum = Collections.unmodifiableMap(hashMap);
        }

        private HashAlgorithm(int i) {
            this.code = i;
        }

        public static HashAlgorithm getAlgorithmByCode(int i) {
            return codeToEnum.get(Integer.valueOf(i));
        }

        public int getCode() {
            return this.code;
        }
    }

    protected PKWareExtraHeader(ZipShort zipShort) {
        this.headerId = zipShort;
    }

    public byte[] getCentralDirectoryData() {
        byte[] bArr = this.centralData;
        return bArr != null ? ZipUtil.copy(bArr) : getLocalFileDataData();
    }

    public ZipShort getCentralDirectoryLength() {
        return this.centralData != null ? new ZipShort(this.centralData.length) : getLocalFileDataLength();
    }

    public ZipShort getHeaderId() {
        return this.headerId;
    }

    public byte[] getLocalFileDataData() {
        return ZipUtil.copy(this.localData);
    }

    public ZipShort getLocalFileDataLength() {
        byte[] bArr = this.localData;
        return new ZipShort(bArr != null ? bArr.length : 0);
    }

    public void parseFromCentralDirectoryData(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        setCentralDirectoryData(bArr2);
        if (this.localData == null) {
            setLocalFileDataData(bArr2);
        }
    }

    public void parseFromLocalFileData(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        setLocalFileDataData(bArr2);
    }

    public void setCentralDirectoryData(byte[] bArr) {
        this.centralData = ZipUtil.copy(bArr);
    }

    public void setLocalFileDataData(byte[] bArr) {
        this.localData = ZipUtil.copy(bArr);
    }
}
