package org.tukaani.p013xz.check;

/* renamed from: org.tukaani.xz.check.None */
public class None extends Check {
    public None() {
        this.size = 0;
        this.name = "None";
    }

    public byte[] finish() {
        return new byte[0];
    }

    public void update(byte[] bArr, int i, int i2) {
    }
}
