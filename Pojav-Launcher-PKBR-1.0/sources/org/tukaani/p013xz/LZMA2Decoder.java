package org.tukaani.p013xz;

import java.io.InputStream;
import kotlin.UByte;

/* renamed from: org.tukaani.xz.LZMA2Decoder */
class LZMA2Decoder extends LZMA2Coder implements FilterDecoder {
    private int dictSize;

    LZMA2Decoder(byte[] bArr) throws UnsupportedOptionsException {
        if (bArr.length != 1 || (bArr[0] & UByte.MAX_VALUE) > 37) {
            throw new UnsupportedOptionsException("Unsupported LZMA2 properties");
        }
        byte b = (bArr[0] & 1) | 2;
        this.dictSize = b;
        this.dictSize = b << ((bArr[0] >>> 1) + 11);
    }

    public InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) {
        return new LZMA2InputStream(inputStream, this.dictSize, (byte[]) null, arrayCache);
    }

    public int getMemoryUsage() {
        return LZMA2InputStream.getMemoryUsage(this.dictSize);
    }
}
