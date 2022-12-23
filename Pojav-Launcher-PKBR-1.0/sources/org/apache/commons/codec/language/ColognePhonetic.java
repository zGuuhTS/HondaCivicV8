package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.p012io.IOUtils;

public class ColognePhonetic implements StringEncoder {
    private static final char[] AEIJOUY = {'A', 'E', 'I', 'J', 'O', 'U', 'Y'};
    private static final char[] AHKLOQRUX = {'A', 'H', 'K', 'L', 'O', 'Q', 'R', 'U', 'X'};
    private static final char[] AHKOQUX = {'A', 'H', 'K', 'O', 'Q', 'U', 'X'};
    private static final char CHAR_IGNORE = '-';
    private static final char[] CKQ = {'C', 'K', 'Q'};
    private static final char[] CSZ = {'C', 'S', 'Z'};
    private static final char[] DTX = {'D', 'T', 'X'};
    private static final char[] FPVW = {'F', 'P', 'V', 'W'};
    private static final char[] GKQ = {'G', 'K', 'Q'};

    /* renamed from: SZ */
    private static final char[] f133SZ = {'S', 'Z'};

    private abstract class CologneBuffer {
        protected final char[] data;
        protected int length = 0;

        /* access modifiers changed from: protected */
        public abstract char[] copyData(int i, int i2);

        public CologneBuffer(char[] data2) {
            this.data = data2;
            this.length = data2.length;
        }

        public CologneBuffer(int buffSize) {
            this.data = new char[buffSize];
            this.length = 0;
        }

        public int length() {
            return this.length;
        }

        public String toString() {
            return new String(copyData(0, this.length));
        }
    }

    private class CologneOutputBuffer extends CologneBuffer {
        private char lastCode = IOUtils.DIR_SEPARATOR_UNIX;

        public CologneOutputBuffer(int buffSize) {
            super(buffSize);
        }

        public void put(char code) {
            if (!(code == '-' || this.lastCode == code || (code == '0' && this.length != 0))) {
                this.data[this.length] = code;
                this.length++;
            }
            this.lastCode = code;
        }

        /* access modifiers changed from: protected */
        public char[] copyData(int start, int length) {
            char[] newData = new char[length];
            System.arraycopy(this.data, start, newData, 0, length);
            return newData;
        }
    }

    private class CologneInputBuffer extends CologneBuffer {
        public CologneInputBuffer(char[] data) {
            super(data);
        }

        /* access modifiers changed from: protected */
        public char[] copyData(int start, int length) {
            char[] newData = new char[length];
            System.arraycopy(this.data, (this.data.length - this.length) + start, newData, 0, length);
            return newData;
        }

        public char getNextChar() {
            return this.data[getNextPos()];
        }

        /* access modifiers changed from: protected */
        public int getNextPos() {
            return this.data.length - this.length;
        }

        public char removeNext() {
            this.length--;
            return getNextChar();
        }
    }

    private static boolean arrayContains(char[] arr, char key) {
        for (char element : arr) {
            if (element == key) {
                return true;
            }
        }
        return false;
    }

    public String colognePhonetic(String text) {
        char nextChar;
        if (text == null) {
            return null;
        }
        CologneInputBuffer input = new CologneInputBuffer(preprocess(text));
        CologneOutputBuffer output = new CologneOutputBuffer(input.length() * 2);
        char lastChar = '-';
        while (input.length() > 0) {
            char chr = input.removeNext();
            if (input.length() > 0) {
                nextChar = input.getNextChar();
            } else {
                nextChar = '-';
            }
            if (chr >= 'A' && chr <= 'Z') {
                if (arrayContains(AEIJOUY, chr)) {
                    output.put('0');
                } else if (chr == 'B' || (chr == 'P' && nextChar != 'H')) {
                    output.put('1');
                } else if ((chr == 'D' || chr == 'T') && !arrayContains(CSZ, nextChar)) {
                    output.put('2');
                } else if (arrayContains(FPVW, chr)) {
                    output.put('3');
                } else if (arrayContains(GKQ, chr)) {
                    output.put('4');
                } else if (chr == 'X' && !arrayContains(CKQ, lastChar)) {
                    output.put('4');
                    output.put('8');
                } else if (chr == 'S' || chr == 'Z') {
                    output.put('8');
                } else if (chr == 'C') {
                    if (output.length() == 0) {
                        if (arrayContains(AHKLOQRUX, nextChar)) {
                            output.put('4');
                        } else {
                            output.put('8');
                        }
                    } else if (arrayContains(f133SZ, lastChar) || !arrayContains(AHKOQUX, nextChar)) {
                        output.put('8');
                    } else {
                        output.put('4');
                    }
                } else if (arrayContains(DTX, chr)) {
                    output.put('8');
                } else if (chr == 'R') {
                    output.put('7');
                } else if (chr == 'L') {
                    output.put('5');
                } else if (chr == 'M' || chr == 'N') {
                    output.put('6');
                } else if (chr == 'H') {
                    output.put('-');
                }
                lastChar = chr;
            }
        }
        return output.toString();
    }

    public Object encode(Object object) throws EncoderException {
        if (object instanceof String) {
            return encode((String) object);
        }
        throw new EncoderException("This method's parameter was expected to be of the type " + String.class.getName() + ". But actually it was of the type " + object.getClass().getName() + ".");
    }

    public String encode(String text) {
        return colognePhonetic(text);
    }

    public boolean isEncodeEqual(String text1, String text2) {
        return colognePhonetic(text1).equals(colognePhonetic(text2));
    }

    private char[] preprocess(String text) {
        char[] chrs = text.toUpperCase(Locale.GERMAN).toCharArray();
        for (int index = 0; index < chrs.length; index++) {
            switch (chrs[index]) {
                case 196:
                    chrs[index] = 'A';
                    break;
                case 214:
                    chrs[index] = 'O';
                    break;
                case 220:
                    chrs[index] = 'U';
                    break;
            }
        }
        return chrs;
    }
}
