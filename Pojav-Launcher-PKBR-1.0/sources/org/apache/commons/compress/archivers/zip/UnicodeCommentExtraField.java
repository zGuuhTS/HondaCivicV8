package org.apache.commons.compress.archivers.zip;

public class UnicodeCommentExtraField extends AbstractUnicodeExtraField {
    public static final ZipShort UCOM_ID = new ZipShort(25461);

    public UnicodeCommentExtraField() {
    }

    public UnicodeCommentExtraField(String str, byte[] bArr) {
        super(str, bArr);
    }

    public UnicodeCommentExtraField(String str, byte[] bArr, int i, int i2) {
        super(str, bArr, i, i2);
    }

    public ZipShort getHeaderId() {
        return UCOM_ID;
    }
}
