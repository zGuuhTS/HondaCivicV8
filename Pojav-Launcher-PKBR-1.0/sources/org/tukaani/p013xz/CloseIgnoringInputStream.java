package org.tukaani.p013xz;

import java.io.FilterInputStream;
import java.io.InputStream;

/* renamed from: org.tukaani.xz.CloseIgnoringInputStream */
public class CloseIgnoringInputStream extends FilterInputStream {
    public CloseIgnoringInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public void close() {
    }
}
