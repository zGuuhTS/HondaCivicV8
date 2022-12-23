package org.tukaani.p013xz.check;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* renamed from: org.tukaani.xz.check.SHA256 */
public class SHA256 extends Check {
    private final MessageDigest sha256 = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);

    public SHA256() throws NoSuchAlgorithmException {
        this.size = 32;
        this.name = MessageDigestAlgorithms.SHA_256;
    }

    public byte[] finish() {
        byte[] digest = this.sha256.digest();
        this.sha256.reset();
        return digest;
    }

    public void update(byte[] bArr, int i, int i2) {
        this.sha256.update(bArr, i, i2);
    }
}
