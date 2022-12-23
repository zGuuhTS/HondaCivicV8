package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

class Simple8BitZipEncoding implements ZipEncoding {
    private final char[] highChars;
    private final List<Simple8BitChar> reverseMapping;

    private static final class Simple8BitChar implements Comparable<Simple8BitChar> {
        public final byte code;
        public final char unicode;

        Simple8BitChar(byte b, char c) {
            this.code = b;
            this.unicode = c;
        }

        public int compareTo(Simple8BitChar simple8BitChar) {
            return this.unicode - simple8BitChar.unicode;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Simple8BitChar)) {
                return false;
            }
            Simple8BitChar simple8BitChar = (Simple8BitChar) obj;
            return this.unicode == simple8BitChar.unicode && this.code == simple8BitChar.code;
        }

        public int hashCode() {
            return this.unicode;
        }

        public String toString() {
            return "0x" + Integer.toHexString(this.unicode & 65535) + "->0x" + Integer.toHexString(this.code & UByte.MAX_VALUE);
        }
    }

    public Simple8BitZipEncoding(char[] cArr) {
        char[] cArr2 = (char[]) cArr.clone();
        this.highChars = cArr2;
        ArrayList arrayList = new ArrayList(cArr2.length);
        byte b = ByteCompanionObject.MAX_VALUE;
        for (char simple8BitChar : cArr2) {
            b = (byte) (b + 1);
            arrayList.add(new Simple8BitChar(b, simple8BitChar));
        }
        Collections.sort(arrayList);
        this.reverseMapping = Collections.unmodifiableList(arrayList);
    }

    private Simple8BitChar encodeHighChar(char c) {
        int size = this.reverseMapping.size();
        int i = 0;
        while (size > i) {
            int i2 = ((size - i) / 2) + i;
            Simple8BitChar simple8BitChar = this.reverseMapping.get(i2);
            if (simple8BitChar.unicode == c) {
                return simple8BitChar;
            }
            if (simple8BitChar.unicode < c) {
                i = i2 + 1;
            } else {
                size = i2;
            }
        }
        if (i >= this.reverseMapping.size()) {
            return null;
        }
        Simple8BitChar simple8BitChar2 = this.reverseMapping.get(i);
        if (simple8BitChar2.unicode != c) {
            return null;
        }
        return simple8BitChar2;
    }

    public boolean canEncode(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!canEncodeChar(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean canEncodeChar(char c) {
        return (c >= 0 && c < 128) || encodeHighChar(c) != null;
    }

    public String decode(byte[] bArr) throws IOException {
        char[] cArr = new char[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            cArr[i] = decodeByte(bArr[i]);
        }
        return new String(cArr);
    }

    public char decodeByte(byte b) {
        return b >= 0 ? (char) b : this.highChars[b + ByteCompanionObject.MIN_VALUE];
    }

    public ByteBuffer encode(String str) {
        ByteBuffer allocate = ByteBuffer.allocate(str.length() + 6 + ((str.length() + 1) / 2));
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (allocate.remaining() < 6) {
                allocate = ZipEncodingHelper.growBuffer(allocate, allocate.position() + 6);
            }
            if (!pushEncodedChar(allocate, charAt)) {
                ZipEncodingHelper.appendSurrogate(allocate, charAt);
            }
        }
        allocate.limit(allocate.position());
        allocate.rewind();
        return allocate;
    }

    public boolean pushEncodedChar(ByteBuffer byteBuffer, char c) {
        byte b;
        if (c < 0 || c >= 128) {
            Simple8BitChar encodeHighChar = encodeHighChar(c);
            if (encodeHighChar == null) {
                return false;
            }
            b = encodeHighChar.code;
        } else {
            b = (byte) c;
        }
        byteBuffer.put(b);
        return true;
    }
}
