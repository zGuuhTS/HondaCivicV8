package org.apache.commons.codec.binary;

import java.util.Arrays;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {
    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    static final int EOF = -1;
    protected static final int MASK_8BITS = 255;
    private static final int MAX_BUFFER_SIZE = 2147483639;
    public static final int MIME_CHUNK_SIZE = 76;
    protected static final byte PAD_DEFAULT = 61;
    public static final int PEM_CHUNK_SIZE = 64;
    @Deprecated
    protected final byte PAD;
    private final int chunkSeparatorLength;
    private final int encodedBlockSize;
    protected final int lineLength;
    protected final byte pad;
    private final int unencodedBlockSize;

    /* access modifiers changed from: package-private */
    public abstract void decode(byte[] bArr, int i, int i2, Context context);

    /* access modifiers changed from: package-private */
    public abstract void encode(byte[] bArr, int i, int i2, Context context);

    /* access modifiers changed from: protected */
    public abstract boolean isInAlphabet(byte b);

    static class Context {
        byte[] buffer;
        int currentLinePos;
        boolean eof;
        int ibitWorkArea;
        long lbitWorkArea;
        int modulus;
        int pos;
        int readPos;

        Context() {
        }

        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, readPos=%s]", new Object[]{getClass().getSimpleName(), Arrays.toString(this.buffer), Integer.valueOf(this.currentLinePos), Boolean.valueOf(this.eof), Integer.valueOf(this.ibitWorkArea), Long.valueOf(this.lbitWorkArea), Integer.valueOf(this.modulus), Integer.valueOf(this.pos), Integer.valueOf(this.readPos)});
        }
    }

    protected BaseNCodec(int unencodedBlockSize2, int encodedBlockSize2, int lineLength2, int chunkSeparatorLength2) {
        this(unencodedBlockSize2, encodedBlockSize2, lineLength2, chunkSeparatorLength2, PAD_DEFAULT);
    }

    protected BaseNCodec(int unencodedBlockSize2, int encodedBlockSize2, int lineLength2, int chunkSeparatorLength2, byte pad2) {
        this.PAD = PAD_DEFAULT;
        this.unencodedBlockSize = unencodedBlockSize2;
        this.encodedBlockSize = encodedBlockSize2;
        this.lineLength = lineLength2 > 0 && chunkSeparatorLength2 > 0 ? (lineLength2 / encodedBlockSize2) * encodedBlockSize2 : 0;
        this.chunkSeparatorLength = chunkSeparatorLength2;
        this.pad = pad2;
    }

    /* access modifiers changed from: package-private */
    public boolean hasData(Context context) {
        return context.buffer != null;
    }

    /* access modifiers changed from: package-private */
    public int available(Context context) {
        if (context.buffer != null) {
            return context.pos - context.readPos;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getDefaultBufferSize() {
        return 8192;
    }

    private static byte[] resizeBuffer(Context context, int minCapacity) {
        int newCapacity = context.buffer.length * 2;
        if (compareUnsigned(newCapacity, minCapacity) < 0) {
            newCapacity = minCapacity;
        }
        if (compareUnsigned(newCapacity, MAX_BUFFER_SIZE) > 0) {
            newCapacity = createPositiveCapacity(minCapacity);
        }
        byte[] b = new byte[newCapacity];
        System.arraycopy(context.buffer, 0, b, 0, context.buffer.length);
        context.buffer = b;
        return b;
    }

    private static int compareUnsigned(int x, int y) {
        return Integer.compare(x - 2147483648, Integer.MIN_VALUE + y);
    }

    private static int createPositiveCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError("Unable to allocate array size: " + (((long) minCapacity) & 4294967295L));
        } else if (minCapacity > MAX_BUFFER_SIZE) {
            return minCapacity;
        } else {
            return MAX_BUFFER_SIZE;
        }
    }

    /* access modifiers changed from: protected */
    public byte[] ensureBufferSize(int size, Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
        } else if ((context.pos + size) - context.buffer.length > 0) {
            return resizeBuffer(context, context.pos + size);
        }
        return context.buffer;
    }

    /* access modifiers changed from: package-private */
    public int readResults(byte[] b, int bPos, int bAvail, Context context) {
        if (context.buffer == null) {
            return context.eof != 0 ? -1 : 0;
        }
        int len = Math.min(available(context), bAvail);
        System.arraycopy(context.buffer, context.readPos, b, bPos, len);
        context.readPos += len;
        if (context.readPos >= context.pos) {
            context.buffer = null;
        }
        return len;
    }

    protected static boolean isWhiteSpace(byte byteToCheck) {
        switch (byteToCheck) {
            case 9:
            case 10:
            case 13:
            case 32:
                return true;
            default:
                return false;
        }
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj instanceof byte[]) {
            return encode((byte[]) obj);
        }
        throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
    }

    public String encodeToString(byte[] pArray) {
        return StringUtils.newStringUtf8(encode(pArray));
    }

    public String encodeAsString(byte[] pArray) {
        return StringUtils.newStringUtf8(encode(pArray));
    }

    public Object decode(Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
    }

    public byte[] decode(String pArray) {
        return decode(StringUtils.getBytesUtf8(pArray));
    }

    public byte[] decode(byte[] pArray) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        Context context = new Context();
        decode(pArray, 0, pArray.length, context);
        decode(pArray, 0, -1, context);
        byte[] result = new byte[context.pos];
        readResults(result, 0, result.length, context);
        return result;
    }

    public byte[] encode(byte[] pArray) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        return encode(pArray, 0, pArray.length);
    }

    public byte[] encode(byte[] pArray, int offset, int length) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        Context context = new Context();
        encode(pArray, offset, length, context);
        encode(pArray, offset, -1, context);
        byte[] buf = new byte[(context.pos - context.readPos)];
        readResults(buf, 0, buf.length, context);
        return buf;
    }

    public boolean isInAlphabet(byte[] arrayOctet, boolean allowWSPad) {
        for (byte octet : arrayOctet) {
            if (!isInAlphabet(octet) && (!allowWSPad || (octet != this.pad && !isWhiteSpace(octet)))) {
                return false;
            }
        }
        return true;
    }

    public boolean isInAlphabet(String basen) {
        return isInAlphabet(StringUtils.getBytesUtf8(basen), true);
    }

    /* access modifiers changed from: protected */
    public boolean containsAlphabetOrPad(byte[] arrayOctet) {
        if (arrayOctet == null) {
            return false;
        }
        for (byte element : arrayOctet) {
            if (this.pad == element || isInAlphabet(element)) {
                return true;
            }
        }
        return false;
    }

    public long getEncodedLength(byte[] pArray) {
        int length = pArray.length;
        int i = this.unencodedBlockSize;
        long len = ((long) (((length + i) - 1) / i)) * ((long) this.encodedBlockSize);
        int i2 = this.lineLength;
        if (i2 > 0) {
            return len + ((((((long) i2) + len) - 1) / ((long) i2)) * ((long) this.chunkSeparatorLength));
        }
        return len;
    }
}
