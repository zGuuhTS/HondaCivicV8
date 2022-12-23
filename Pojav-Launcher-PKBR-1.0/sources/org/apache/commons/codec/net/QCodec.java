package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

public class QCodec extends RFC1522Codec implements StringEncoder, StringDecoder {
    private static final BitSet PRINTABLE_CHARS;
    private static final byte SPACE = 32;
    private static final byte UNDERSCORE = 95;
    private final Charset charset;
    private boolean encodeBlanks;

    static {
        BitSet bitSet = new BitSet(256);
        PRINTABLE_CHARS = bitSet;
        bitSet.set(32);
        bitSet.set(33);
        bitSet.set(34);
        bitSet.set(35);
        bitSet.set(36);
        bitSet.set(37);
        bitSet.set(38);
        bitSet.set(39);
        bitSet.set(40);
        bitSet.set(41);
        bitSet.set(42);
        bitSet.set(43);
        bitSet.set(44);
        bitSet.set(45);
        bitSet.set(46);
        bitSet.set(47);
        for (int i = 48; i <= 57; i++) {
            PRINTABLE_CHARS.set(i);
        }
        BitSet bitSet2 = PRINTABLE_CHARS;
        bitSet2.set(58);
        bitSet2.set(59);
        bitSet2.set(60);
        bitSet2.set(62);
        bitSet2.set(64);
        for (int i2 = 65; i2 <= 90; i2++) {
            PRINTABLE_CHARS.set(i2);
        }
        BitSet bitSet3 = PRINTABLE_CHARS;
        bitSet3.set(91);
        bitSet3.set(92);
        bitSet3.set(93);
        bitSet3.set(94);
        bitSet3.set(96);
        for (int i3 = 97; i3 <= 122; i3++) {
            PRINTABLE_CHARS.set(i3);
        }
        BitSet bitSet4 = PRINTABLE_CHARS;
        bitSet4.set(123);
        bitSet4.set(124);
        bitSet4.set(125);
        bitSet4.set(126);
    }

    public QCodec() {
        this(StandardCharsets.UTF_8);
    }

    public QCodec(Charset charset2) {
        this.encodeBlanks = false;
        this.charset = charset2;
    }

    public QCodec(String charsetName) {
        this(Charset.forName(charsetName));
    }

    /* access modifiers changed from: protected */
    public String getEncoding() {
        return "Q";
    }

    /* access modifiers changed from: protected */
    public byte[] doEncoding(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        byte[] data = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, bytes);
        if (this.encodeBlanks) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] == 32) {
                    data[i] = UNDERSCORE;
                }
            }
        }
        return data;
    }

    /* access modifiers changed from: protected */
    public byte[] doDecoding(byte[] bytes) throws DecoderException {
        if (bytes == null) {
            return null;
        }
        boolean hasUnderscores = false;
        int length = bytes.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (bytes[i] == 95) {
                hasUnderscores = true;
                break;
            } else {
                i++;
            }
        }
        if (!hasUnderscores) {
            return QuotedPrintableCodec.decodeQuotedPrintable(bytes);
        }
        byte[] tmp = new byte[bytes.length];
        for (int i2 = 0; i2 < bytes.length; i2++) {
            byte b = bytes[i2];
            if (b != 95) {
                tmp[i2] = b;
            } else {
                tmp[i2] = SPACE;
            }
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(tmp);
    }

    public String encode(String sourceStr, Charset sourceCharset) throws EncoderException {
        if (sourceStr == null) {
            return null;
        }
        return encodeText(sourceStr, sourceCharset);
    }

    public String encode(String sourceStr, String sourceCharset) throws EncoderException {
        if (sourceStr == null) {
            return null;
        }
        try {
            return encodeText(sourceStr, sourceCharset);
        } catch (UnsupportedEncodingException e) {
            throw new EncoderException(e.getMessage(), e);
        }
    }

    public String encode(String sourceStr) throws EncoderException {
        if (sourceStr == null) {
            return null;
        }
        return encode(sourceStr, getCharset());
    }

    public String decode(String str) throws DecoderException {
        if (str == null) {
            return null;
        }
        try {
            return decodeText(str);
        } catch (UnsupportedEncodingException e) {
            throw new DecoderException(e.getMessage(), e);
        }
    }

    public Object encode(Object obj) throws EncoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return encode((String) obj);
        }
        throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be encoded using Q codec");
    }

    public Object decode(Object obj) throws DecoderException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return decode((String) obj);
        }
        throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be decoded using Q codec");
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String getDefaultCharset() {
        return this.charset.name();
    }

    public boolean isEncodeBlanks() {
        return this.encodeBlanks;
    }

    public void setEncodeBlanks(boolean b) {
        this.encodeBlanks = b;
    }
}
