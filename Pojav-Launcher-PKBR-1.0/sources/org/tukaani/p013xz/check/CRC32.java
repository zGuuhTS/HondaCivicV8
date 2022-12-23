package org.tukaani.p013xz.check;

/* renamed from: org.tukaani.xz.check.CRC32 */
public class CRC32 extends Check {
    private final java.util.zip.CRC32 state = new java.util.zip.CRC32();

    public CRC32() {
        this.size = 4;
        this.name = "CRC32";
    }

    public byte[] finish() {
        long value = this.state.getValue();
        byte[] bArr = {(byte) ((int) value), (byte) ((int) (value >>> 8)), (byte) ((int) (value >>> 16)), (byte) ((int) (value >>> 24))};
        this.state.reset();
        return bArr;
    }

    public void update(byte[] bArr, int i, int i2) {
        this.state.update(bArr, i, i2);
    }
}
