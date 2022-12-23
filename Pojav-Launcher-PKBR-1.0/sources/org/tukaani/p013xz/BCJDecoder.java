package org.tukaani.p013xz;

import java.io.InputStream;
import kotlin.UByte;
import org.tukaani.p013xz.simple.ARM;
import org.tukaani.p013xz.simple.ARMThumb;
import org.tukaani.p013xz.simple.IA64;
import org.tukaani.p013xz.simple.PowerPC;
import org.tukaani.p013xz.simple.SPARC;
import org.tukaani.p013xz.simple.SimpleFilter;
import org.tukaani.p013xz.simple.X86;

/* renamed from: org.tukaani.xz.BCJDecoder */
class BCJDecoder extends BCJCoder implements FilterDecoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final long filterID;
    private final int startOffset;

    BCJDecoder(long j, byte[] bArr) throws UnsupportedOptionsException {
        if (isBCJFilterID(j)) {
            this.filterID = j;
            if (bArr.length == 0) {
                this.startOffset = 0;
            } else if (bArr.length == 4) {
                int i = 0;
                for (int i2 = 0; i2 < 4; i2++) {
                    i |= (bArr[i2] & UByte.MAX_VALUE) << (i2 * 8);
                }
                this.startOffset = i;
            } else {
                throw new UnsupportedOptionsException("Unsupported BCJ filter properties");
            }
        } else {
            throw new AssertionError();
        }
    }

    public InputStream getInputStream(InputStream inputStream, ArrayCache arrayCache) {
        SimpleFilter simpleFilter;
        long j = this.filterID;
        if (j == 4) {
            simpleFilter = new X86(false, this.startOffset);
        } else if (j == 5) {
            simpleFilter = new PowerPC(false, this.startOffset);
        } else if (j == 6) {
            simpleFilter = new IA64(false, this.startOffset);
        } else if (j == 7) {
            simpleFilter = new ARM(false, this.startOffset);
        } else if (j == 8) {
            simpleFilter = new ARMThumb(false, this.startOffset);
        } else if (j == 9) {
            simpleFilter = new SPARC(false, this.startOffset);
        } else {
            throw new AssertionError();
        }
        return new SimpleInputStream(inputStream, simpleFilter);
    }

    public int getMemoryUsage() {
        return SimpleInputStream.getMemoryUsage();
    }
}
