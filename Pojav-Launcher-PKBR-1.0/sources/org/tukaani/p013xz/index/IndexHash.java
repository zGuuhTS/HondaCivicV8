package org.tukaani.p013xz.index;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.zip.CheckedInputStream;
import org.tukaani.p013xz.CorruptedInputException;
import org.tukaani.p013xz.XZIOException;
import org.tukaani.p013xz.check.CRC32;
import org.tukaani.p013xz.check.Check;
import org.tukaani.p013xz.check.SHA256;
import org.tukaani.p013xz.common.DecoderUtil;

/* renamed from: org.tukaani.xz.index.IndexHash */
public class IndexHash extends IndexBase {
    private Check hash;

    public IndexHash() {
        super(new CorruptedInputException());
        try {
            this.hash = new SHA256();
        } catch (NoSuchAlgorithmException e) {
            this.hash = new CRC32();
        }
    }

    public void add(long j, long j2) throws XZIOException {
        super.add(j, j2);
        ByteBuffer allocate = ByteBuffer.allocate(16);
        allocate.putLong(j);
        allocate.putLong(j2);
        this.hash.update(allocate.array());
    }

    public /* bridge */ /* synthetic */ long getIndexSize() {
        return super.getIndexSize();
    }

    public /* bridge */ /* synthetic */ long getStreamSize() {
        return super.getStreamSize();
    }

    public void validate(InputStream inputStream) throws IOException {
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        int i = 0;
        crc32.update(0);
        CheckedInputStream checkedInputStream = new CheckedInputStream(inputStream, crc32);
        if (DecoderUtil.decodeVLI(checkedInputStream) == this.recordCount) {
            IndexHash indexHash = new IndexHash();
            long j = 0;
            while (j < this.recordCount) {
                try {
                    indexHash.add(DecoderUtil.decodeVLI(checkedInputStream), DecoderUtil.decodeVLI(checkedInputStream));
                    if (indexHash.blocksSum > this.blocksSum || indexHash.uncompressedSum > this.uncompressedSum || indexHash.indexListSize > this.indexListSize) {
                        throw new CorruptedInputException("XZ Index is corrupt");
                    }
                    j++;
                } catch (XZIOException e) {
                    throw new CorruptedInputException("XZ Index is corrupt");
                }
            }
            if (indexHash.blocksSum == this.blocksSum && indexHash.uncompressedSum == this.uncompressedSum && indexHash.indexListSize == this.indexListSize && Arrays.equals(indexHash.hash.finish(), this.hash.finish())) {
                DataInputStream dataInputStream = new DataInputStream(checkedInputStream);
                int indexPaddingSize = getIndexPaddingSize();
                while (indexPaddingSize > 0) {
                    if (dataInputStream.readUnsignedByte() == 0) {
                        indexPaddingSize--;
                    } else {
                        throw new CorruptedInputException("XZ Index is corrupt");
                    }
                }
                long value = crc32.getValue();
                while (i < 4) {
                    if (((value >>> (i * 8)) & 255) == ((long) dataInputStream.readUnsignedByte())) {
                        i++;
                    } else {
                        throw new CorruptedInputException("XZ Index is corrupt");
                    }
                }
                return;
            }
            throw new CorruptedInputException("XZ Index is corrupt");
        }
        throw new CorruptedInputException("XZ Block Header or the start of XZ Index is corrupt");
    }
}
