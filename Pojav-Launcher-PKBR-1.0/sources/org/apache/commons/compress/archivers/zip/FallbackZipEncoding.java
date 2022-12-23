package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;

class FallbackZipEncoding implements ZipEncoding {
    private final String charsetName;

    public FallbackZipEncoding() {
        this.charsetName = null;
    }

    public FallbackZipEncoding(String str) {
        this.charsetName = str;
    }

    public boolean canEncode(String str) {
        return true;
    }

    public String decode(byte[] bArr) throws IOException {
        return this.charsetName == null ? new String(bArr) : new String(bArr, this.charsetName);
    }

    public ByteBuffer encode(String str) throws IOException {
        String str2 = this.charsetName;
        return str2 == null ? ByteBuffer.wrap(str.getBytes()) : ByteBuffer.wrap(str.getBytes(str2));
    }
}
