package org.tukaani.p013xz;

/* renamed from: org.tukaani.xz.XZFormatException */
public class XZFormatException extends XZIOException {
    private static final long serialVersionUID = 3;

    public XZFormatException() {
        super("Input is not in the XZ format");
    }
}
