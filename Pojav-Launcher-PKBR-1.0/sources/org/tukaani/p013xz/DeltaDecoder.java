package org.tukaani.p013xz;

import java.io.InputStream;
import kotlin.UByte;

/* renamed from: org.tukaani.xz.DeltaDecoder */
class DeltaDecoder extends DeltaCoder implements FilterDecoder {
    private final int distance;

    DeltaDecoder(byte[] bArr) throws UnsupportedOptionsException {
        if (bArr.length == 1) {
            this.distance = (bArr[0] & UByte.MAX_VALUE) + 1;
            return;
        }
        throw new UnsupportedOptionsException("Unsupported Delta filter properties");
    }

    public InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) {
        return new DeltaInputStream(inputStream, this.distance);
    }

    public int getMemoryUsage() {
        return 1;
    }
}
