package org.tukaani.p013xz.index;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import org.tukaani.p013xz.XZIOException;
import org.tukaani.p013xz.common.EncoderUtil;

/* renamed from: org.tukaani.xz.index.IndexEncoder */
public class IndexEncoder extends IndexBase {
    private final ArrayList<IndexRecord> records = new ArrayList<>();

    public IndexEncoder() {
        super(new XZIOException("XZ Stream or its Index has grown too big"));
    }

    public void add(long j, long j2) throws XZIOException {
        super.add(j, j2);
        this.records.add(new IndexRecord(j, j2));
    }

    public void encode(OutputStream outputStream) throws IOException {
        CRC32 crc32 = new CRC32();
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, crc32);
        checkedOutputStream.write(0);
        EncoderUtil.encodeVLI(checkedOutputStream, this.recordCount);
        Iterator<IndexRecord> it = this.records.iterator();
        while (it.hasNext()) {
            IndexRecord next = it.next();
            EncoderUtil.encodeVLI(checkedOutputStream, next.unpadded);
            EncoderUtil.encodeVLI(checkedOutputStream, next.uncompressed);
        }
        for (int indexPaddingSize = getIndexPaddingSize(); indexPaddingSize > 0; indexPaddingSize--) {
            checkedOutputStream.write(0);
        }
        long value = crc32.getValue();
        for (int i = 0; i < 4; i++) {
            outputStream.write((byte) ((int) (value >>> (i * 8))));
        }
    }

    public /* bridge */ /* synthetic */ long getIndexSize() {
        return super.getIndexSize();
    }

    public /* bridge */ /* synthetic */ long getStreamSize() {
        return super.getStreamSize();
    }
}
