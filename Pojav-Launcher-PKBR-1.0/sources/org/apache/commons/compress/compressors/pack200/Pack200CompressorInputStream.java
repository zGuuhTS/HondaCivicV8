package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import org.apache.commons.compress.compressors.CompressorInputStream;

public class Pack200CompressorInputStream extends CompressorInputStream {
    private static final byte[] CAFE_DOOD;
    private static final int SIG_LENGTH;
    private final InputStream originalInput;
    private final StreamBridge streamBridge;

    static {
        byte[] bArr = {-54, -2, -48, 13};
        CAFE_DOOD = bArr;
        SIG_LENGTH = bArr.length;
    }

    public Pack200CompressorInputStream(File file) throws IOException {
        this(file, Pack200Strategy.IN_MEMORY);
    }

    public Pack200CompressorInputStream(File file, Map<String, String> map) throws IOException {
        this(file, Pack200Strategy.IN_MEMORY, map);
    }

    public Pack200CompressorInputStream(File file, Pack200Strategy pack200Strategy) throws IOException {
        this((InputStream) null, file, pack200Strategy, (Map<String, String>) null);
    }

    public Pack200CompressorInputStream(File file, Pack200Strategy pack200Strategy, Map<String, String> map) throws IOException {
        this((InputStream) null, file, pack200Strategy, map);
    }

    public Pack200CompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, Pack200Strategy.IN_MEMORY);
    }

    private Pack200CompressorInputStream(InputStream inputStream, File file, Pack200Strategy pack200Strategy, Map<String, String> map) throws IOException {
        this.originalInput = inputStream;
        StreamBridge newStreamBridge = pack200Strategy.newStreamBridge();
        this.streamBridge = newStreamBridge;
        JarOutputStream jarOutputStream = new JarOutputStream(newStreamBridge);
        Pack200.Unpacker newUnpacker = Pack200.newUnpacker();
        if (map != null) {
            newUnpacker.properties().putAll(map);
        }
        if (file == null) {
            newUnpacker.unpack(new FilterInputStream(inputStream) {
                public void close() {
                }
            }, jarOutputStream);
        } else {
            newUnpacker.unpack(file, jarOutputStream);
        }
        jarOutputStream.close();
    }

    public Pack200CompressorInputStream(InputStream inputStream, Map<String, String> map) throws IOException {
        this(inputStream, Pack200Strategy.IN_MEMORY, map);
    }

    public Pack200CompressorInputStream(InputStream inputStream, Pack200Strategy pack200Strategy) throws IOException {
        this(inputStream, (File) null, pack200Strategy, (Map<String, String>) null);
    }

    public Pack200CompressorInputStream(InputStream inputStream, Pack200Strategy pack200Strategy, Map<String, String> map) throws IOException {
        this(inputStream, (File) null, pack200Strategy, map);
    }

    public static boolean matches(byte[] bArr, int i) {
        if (i < SIG_LENGTH) {
            return false;
        }
        for (int i2 = 0; i2 < SIG_LENGTH; i2++) {
            if (bArr[i2] != CAFE_DOOD[i2]) {
                return false;
            }
        }
        return true;
    }

    public int available() throws IOException {
        return this.streamBridge.getInput().available();
    }

    public void close() throws IOException {
        try {
            this.streamBridge.stop();
        } finally {
            InputStream inputStream = this.originalInput;
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void mark(int i) {
        try {
            this.streamBridge.getInput().mark(i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean markSupported() {
        try {
            return this.streamBridge.getInput().markSupported();
        } catch (IOException e) {
            return false;
        }
    }

    public int read() throws IOException {
        return this.streamBridge.getInput().read();
    }

    public int read(byte[] bArr) throws IOException {
        return this.streamBridge.getInput().read(bArr);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.streamBridge.getInput().read(bArr, i, i2);
    }

    public void reset() throws IOException {
        this.streamBridge.getInput().reset();
    }

    public long skip(long j) throws IOException {
        return this.streamBridge.getInput().skip(j);
    }
}
