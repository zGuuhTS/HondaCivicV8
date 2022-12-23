package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.DeltaEncoder */
class DeltaEncoder extends DeltaCoder implements FilterEncoder {
    private final DeltaOptions options;
    private final byte[] props;

    DeltaEncoder(DeltaOptions deltaOptions) {
        byte[] bArr = new byte[1];
        this.props = bArr;
        bArr[0] = (byte) (deltaOptions.getDistance() - 1);
        this.options = (DeltaOptions) deltaOptions.clone();
    }

    public long getFilterID() {
        return 3;
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
