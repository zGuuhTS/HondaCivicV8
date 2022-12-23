package org.apache.commons.p012io.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Pattern;
import org.apache.commons.p012io.input.XmlStreamReader;

/* renamed from: org.apache.commons.io.output.XmlStreamWriter */
public class XmlStreamWriter extends Writer {
    private static final int BUFFER_SIZE = 4096;
    static final Pattern ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
    private final String defaultEncoding;
    private String encoding;
    private final OutputStream out;
    private Writer writer;
    private StringWriter xmlPrologWriter;

    public XmlStreamWriter(File file) throws FileNotFoundException {
        this(file, (String) null);
    }

    public XmlStreamWriter(File file, String str) throws FileNotFoundException {
        this((OutputStream) new FileOutputStream(file), str);
    }

    public XmlStreamWriter(OutputStream outputStream) {
        this(outputStream, (String) null);
    }

    public XmlStreamWriter(OutputStream outputStream, String str) {
        this.xmlPrologWriter = new StringWriter(4096);
        this.out = outputStream;
        this.defaultEncoding = str == null ? "UTF-8" : str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0061, code lost:
        if (r0.length() >= 4096) goto L_0x0063;
     */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void detectEncoding(char[] r7, int r8, int r9) throws java.io.IOException {
        /*
            r6 = this;
            java.io.StringWriter r0 = r6.xmlPrologWriter
            java.lang.StringBuffer r0 = r0.getBuffer()
            int r1 = r0.length()
            int r1 = r1 + r9
            r2 = 4096(0x1000, float:5.74E-42)
            if (r1 <= r2) goto L_0x0016
            int r1 = r0.length()
            int r1 = 4096 - r1
            goto L_0x0017
        L_0x0016:
            r1 = r9
        L_0x0017:
            java.io.StringWriter r3 = r6.xmlPrologWriter
            r3.write(r7, r8, r1)
            int r3 = r0.length()
            r4 = 5
            if (r3 < r4) goto L_0x0089
            r3 = 0
            java.lang.String r4 = r0.substring(r3, r4)
            java.lang.String r5 = "<?xml"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0063
            java.lang.String r4 = "?>"
            int r4 = r0.indexOf(r4)
            if (r4 <= 0) goto L_0x005d
            java.util.regex.Pattern r2 = ENCODING_PATTERN
            java.lang.String r3 = r0.substring(r3, r4)
            java.util.regex.Matcher r2 = r2.matcher(r3)
            boolean r3 = r2.find()
            if (r3 == 0) goto L_0x0063
            r3 = 1
            java.lang.String r2 = r2.group(r3)
            java.lang.String r2 = r2.toUpperCase()
            r6.encoding = r2
            int r4 = r2.length()
            int r4 = r4 - r3
            java.lang.String r2 = r2.substring(r3, r4)
            goto L_0x0065
        L_0x005d:
            int r3 = r0.length()
            if (r3 < r2) goto L_0x0067
        L_0x0063:
            java.lang.String r2 = r6.defaultEncoding
        L_0x0065:
            r6.encoding = r2
        L_0x0067:
            java.lang.String r2 = r6.encoding
            if (r2 == 0) goto L_0x0089
            r2 = 0
            r6.xmlPrologWriter = r2
            java.io.OutputStreamWriter r2 = new java.io.OutputStreamWriter
            java.io.OutputStream r3 = r6.out
            java.lang.String r4 = r6.encoding
            r2.<init>(r3, r4)
            r6.writer = r2
            java.lang.String r0 = r0.toString()
            r2.write(r0)
            if (r9 <= r1) goto L_0x0089
            java.io.Writer r0 = r6.writer
            int r8 = r8 + r1
            int r9 = r9 - r1
            r0.write(r7, r8, r9)
        L_0x0089:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p012io.output.XmlStreamWriter.detectEncoding(char[], int, int):void");
    }

    public void close() throws IOException {
        if (this.writer == null) {
            this.encoding = this.defaultEncoding;
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.out, this.encoding);
            this.writer = outputStreamWriter;
            outputStreamWriter.write(this.xmlPrologWriter.toString());
        }
        this.writer.close();
    }

    public void flush() throws IOException {
        Writer writer2 = this.writer;
        if (writer2 != null) {
            writer2.flush();
        }
    }

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void write(char[] cArr, int i, int i2) throws IOException {
        if (this.xmlPrologWriter != null) {
            detectEncoding(cArr, i, i2);
        } else {
            this.writer.write(cArr, i, i2);
        }
    }
}
