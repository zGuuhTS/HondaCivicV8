package org.apache.commons.compress.archivers.zip;

import org.apache.commons.compress.archivers.zip.PKWareExtraHeader;

public class X0015_CertificateIdForFile extends PKWareExtraHeader {
    private PKWareExtraHeader.HashAlgorithm hashAlg;
    private int rcount;

    public X0015_CertificateIdForFile() {
        super(new ZipShort(21));
    }

    public PKWareExtraHeader.HashAlgorithm getHashAlgorithm() {
        return this.hashAlg;
    }

    public int getRecordCount() {
        return this.rcount;
    }

    public void parseFromCentralDirectoryData(byte[] bArr, int i, int i2) {
        super.parseFromCentralDirectoryData(bArr, i, i2);
        this.rcount = ZipShort.getValue(bArr, i);
        this.hashAlg = PKWareExtraHeader.HashAlgorithm.getAlgorithmByCode(ZipShort.getValue(bArr, i + 2));
    }
}
