package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.CorruptedInputException */
public class CorruptedInputException extends XZIOException {
    private static final long serialVersionUID = 3;

    public CorruptedInputException() {
        super("Compressed data is corrupt");
    }

    public CorruptedInputException(String str) {
        super(str);
    }
}
