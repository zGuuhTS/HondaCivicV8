package org.apache.commons.p012io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/* renamed from: org.apache.commons.io.output.ProxyWriter */
public class ProxyWriter extends FilterWriter {
    public ProxyWriter(Writer writer) {
        super(writer);
    }

    /* access modifiers changed from: protected */
    public void afterWrite(int i) throws IOException {
    }

    public Writer append(char c) throws IOException {
        try {
            beforeWrite(1);
            this.out.append(c);
            afterWrite(1);
        } catch (IOException e) {
            handleIOException(e);
        }
        return this;
    }

    public Writer append(CharSequence charSequence) throws IOException {
        int i;
        if (charSequence != null) {
            try {
                i = charSequence.length();
            } catch (IOException e) {
                handleIOException(e);
            }
        } else {
            i = 0;
        }
        beforeWrite(i);
        this.out.append(charSequence);
        afterWrite(i);
        return this;
    }

    public Writer append(CharSequence charSequence, int i, int i2) throws IOException {
        int i3 = i2 - i;
        try {
            beforeWrite(i3);
            this.out.append(charSequence, i, i2);
            afterWrite(i3);
        } catch (IOException e) {
            handleIOException(e);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public void beforeWrite(int i) throws IOException {
    }

    public void close() throws IOException {
        try {
            this.out.close();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public void flush() throws IOException {
        try {
            this.out.flush();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void handleIOException(IOException iOException) throws IOException {
        throw iOException;
    }

    public void write(int i) throws IOException {
        try {
            beforeWrite(1);
            this.out.write(i);
            afterWrite(1);
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public void write(String str) throws IOException {
        int i;
        if (str != null) {
            try {
                i = str.length();
            } catch (IOException e) {
                handleIOException(e);
                return;
            }
        } else {
            i = 0;
        }
        beforeWrite(i);
        this.out.write(str);
        afterWrite(i);
    }

    public void write(String str, int i, int i2) throws IOException {
        try {
            beforeWrite(i2);
            this.out.write(str, i, i2);
            afterWrite(i2);
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    public void write(char[] cArr) throws IOException {
        int i;
        if (cArr != null) {
            try {
                i = cArr.length;
            } catch (IOException e) {
                handleIOException(e);
                return;
            }
        } else {
            i = 0;
        }
        beforeWrite(i);
        this.out.write(cArr);
        afterWrite(i);
    }

    public void write(char[] cArr, int i, int i2) throws IOException {
        try {
            beforeWrite(i2);
            this.out.write(cArr, i, i2);
            afterWrite(i2);
        } catch (IOException e) {
            handleIOException(e);
        }
    }
}
