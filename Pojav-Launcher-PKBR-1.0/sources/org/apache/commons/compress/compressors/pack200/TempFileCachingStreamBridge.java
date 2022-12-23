package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class TempFileCachingStreamBridge extends StreamBridge {
    /* access modifiers changed from: private */

    /* renamed from: f */
    public final File f162f;

    TempFileCachingStreamBridge() throws IOException {
        File createTempFile = File.createTempFile("commons-compress", "packtemp");
        this.f162f = createTempFile;
        createTempFile.deleteOnExit();
        this.out = new FileOutputStream(createTempFile);
    }

    /* access modifiers changed from: package-private */
    public InputStream getInputView() throws IOException {
        this.out.close();
        return new FileInputStream(this.f162f) {
            public void close() throws IOException {
                super.close();
                TempFileCachingStreamBridge.this.f162f.delete();
            }
        };
    }
}
