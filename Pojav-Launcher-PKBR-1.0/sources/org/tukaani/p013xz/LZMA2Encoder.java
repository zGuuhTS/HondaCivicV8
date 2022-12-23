package org.tukaani.p013xz;

import org.tukaani.p013xz.lzma.LZMAEncoder;

/* renamed from: org.tukaani.xz.LZMA2Encoder */
class LZMA2Encoder extends LZMA2Coder implements FilterEncoder {
    private final LZMA2Options options;
    private final byte[] props;

    LZMA2Encoder(LZMA2Options lZMA2Options) {
        byte[] bArr = new byte[1];
        this.props = bArr;
        if (lZMA2Options.getPresetDict() == null) {
            if (lZMA2Options.getMode() == 0) {
                bArr[0] = 0;
            } else {
                bArr[0] = (byte) (LZMAEncoder.getDistSlot(Math.max(lZMA2Options.getDictSize(), 4096) - 1) - 23);
            }
            this.options = (LZMA2Options) lZMA2Options.clone();
            return;
        }
        throw new IllegalArgumentException("XZ doesn't support a preset dictionary for now");
    }

    public long getFilterID() {
        return 33;
    }

    public byte[] getFilterProps() {
        return this.props;
    }

    public FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream, ArrayCache arrayCache) {
        return this.options.getOutputStream(finishableOutputStream, arrayCache);
    }

    public boolean supportsFlushing() {
        return true;
    }
}
