package org.tukaani.p013xz.rangecoder;

import androidx.core.view.ViewCompat;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.tukaani.p013xz.CorruptedInputException;

/* renamed from: org.tukaani.xz.rangecoder.RangeDecoderFromStream */
public final class RangeDecoderFromStream extends RangeDecoder {
    private final DataInputStream inData;

    public RangeDecoderFromStream(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        this.inData = dataInputStream;
        if (dataInputStream.readUnsignedByte() == 0) {
            this.code = dataInputStream.readInt();
            this.range = -1;
            return;
        }
        throw new CorruptedInputException();
    }

    public boolean isFinished() {
        return this.code == 0;
    }

    public void normalize() throws IOException {
        if ((this.range & ViewCompat.MEASURED_STATE_MASK) == 0) {
            this.code = (this.code << 8) | this.inData.readUnsignedByte();
            this.range <<= 8;
        }
    }
}
