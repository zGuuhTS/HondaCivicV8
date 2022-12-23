package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.UByte;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.compress.PasswordRequiredException;

class AES256SHA256Decoder extends CoderBase {
    AES256SHA256Decoder() {
        super(new Class[0]);
    }

    /* access modifiers changed from: package-private */
    public InputStream decode(String str, InputStream inputStream, long j, Coder coder, byte[] bArr) throws IOException {
        final Coder coder2 = coder;
        final String str2 = str;
        final byte[] bArr2 = bArr;
        final InputStream inputStream2 = inputStream;
        return new InputStream() {
            private CipherInputStream cipherInputStream = null;
            private boolean isInitialized = false;

            private CipherInputStream init() throws IOException {
                byte[] bArr;
                if (this.isInitialized) {
                    return this.cipherInputStream;
                }
                byte b = coder2.properties[0] & UByte.MAX_VALUE;
                byte b2 = b & 63;
                byte b3 = coder2.properties[1] & UByte.MAX_VALUE;
                int i = ((b >> 6) & 1) + (b3 & 15);
                int i2 = ((b >> 7) & 1) + (b3 >> 4);
                int i3 = i2 + 2;
                if (i3 + i <= coder2.properties.length) {
                    byte[] bArr2 = new byte[i2];
                    System.arraycopy(coder2.properties, 2, bArr2, 0, i2);
                    byte[] bArr3 = new byte[16];
                    System.arraycopy(coder2.properties, i3, bArr3, 0, i);
                    if (bArr2 != null) {
                        if (b2 == 63) {
                            bArr = new byte[32];
                            System.arraycopy(bArr2, 0, bArr, 0, i2);
                            byte[] bArr4 = bArr2;
                            System.arraycopy(bArr4, 0, bArr, i2, Math.min(bArr4.length, 32 - i2));
                        } else {
                            try {
                                MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
                                byte[] bArr5 = new byte[8];
                                for (long j = 0; j < (1 << b2); j++) {
                                    instance.update(bArr2);
                                    instance.update(bArr2);
                                    instance.update(bArr5);
                                    for (int i4 = 0; i4 < 8; i4++) {
                                        bArr5[i4] = (byte) (bArr5[i4] + 1);
                                        if (bArr5[i4] != 0) {
                                            break;
                                        }
                                    }
                                }
                                bArr = instance.digest();
                            } catch (NoSuchAlgorithmException e) {
                                throw new IOException("SHA-256 is unsupported by your Java implementation", e);
                            }
                        }
                        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
                        try {
                            Cipher instance2 = Cipher.getInstance("AES/CBC/NoPadding");
                            instance2.init(2, secretKeySpec, new IvParameterSpec(bArr3));
                            CipherInputStream cipherInputStream2 = new CipherInputStream(inputStream2, instance2);
                            this.cipherInputStream = cipherInputStream2;
                            this.isInitialized = true;
                            return cipherInputStream2;
                        } catch (GeneralSecurityException e2) {
                            throw new IOException("Decryption error (do you have the JCE Unlimited Strength Jurisdiction Policy Files installed?)", e2);
                        }
                    } else {
                        throw new PasswordRequiredException(str2);
                    }
                } else {
                    throw new IOException("Salt size + IV size too long in " + str2);
                }
            }

            public void close() {
            }

            public int read() throws IOException {
                return init().read();
            }

            public int read(byte[] bArr, int i, int i2) throws IOException {
                return init().read(bArr, i, i2);
            }
        };
    }
}
