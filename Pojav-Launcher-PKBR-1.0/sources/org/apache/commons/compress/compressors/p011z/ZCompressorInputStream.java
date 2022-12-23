package org.apache.commons.compress.compressors.p011z;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import org.apache.commons.compress.compressors.lzw.LZWInputStream;

/* renamed from: org.apache.commons.compress.compressors.z.ZCompressorInputStream */
public class ZCompressorInputStream extends LZWInputStream {
    private static final int BLOCK_MODE_MASK = 128;
    private static final int MAGIC_1 = 31;
    private static final int MAGIC_2 = 157;
    private static final int MAX_CODE_SIZE_MASK = 31;
    private final boolean blockMode;
    private final int maxCodeSize;
    private long totalCodesRead = 0;

    public ZCompressorInputStream(InputStream inputStream) throws IOException {
        super(inputStream, ByteOrder.LITTLE_ENDIAN);
        int readBits = (int) this.f161in.readBits(8);
        int readBits2 = (int) this.f161in.readBits(8);
        int readBits3 = (int) this.f161in.readBits(8);
        if (readBits == 31 && readBits2 == 157 && readBits3 >= 0) {
            boolean z = (readBits3 & 128) != 0;
            this.blockMode = z;
            int i = readBits3 & 31;
            this.maxCodeSize = i;
            if (z) {
                setClearCode(9);
            }
            initializeTables(i);
            clearEntries();
            return;
        }
        throw new IOException("Input is not in .Z format");
    }

    private void clearEntries() {
        throw new RuntimeException("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:783)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:716)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e2expr(TypeTransformer.java:635)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:701)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:722)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:706)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:813)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:161)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:433)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:129)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:528)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:425)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:441)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:123)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:271)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:107)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
    }

    public static boolean matches(byte[] bArr, int i) {
        return i > 3 && bArr[0] == 31 && bArr[1] == -99;
    }

    private void reAlignReading() throws IOException {
        long j = 8 - (this.totalCodesRead % 8);
        if (j == 8) {
            j = 0;
        }
        for (long j2 = 0; j2 < j; j2++) {
            readNextCode();
        }
        this.f161in.clearBitCache();
    }

    /* access modifiers changed from: protected */
    public int addEntry(int i, byte b) throws IOException {
        int codeSize = 1 << getCodeSize();
        int addEntry = addEntry(i, b, codeSize);
        if (getTableSize() == codeSize && getCodeSize() < this.maxCodeSize) {
            reAlignReading();
            incrementCodeSize();
        }
        return addEntry;
    }

    /* access modifiers changed from: protected */
    public int decompressNextSymbol() throws IOException {
        int readNextCode = readNextCode();
        if (readNextCode < 0) {
            return -1;
        }
        boolean z = false;
        if (!this.blockMode || readNextCode != getClearCode()) {
            if (readNextCode == getTableSize()) {
                addRepeatOfPreviousCode();
                z = true;
            } else if (readNextCode > getTableSize()) {
                throw new IOException(String.format("Invalid %d bit code 0x%x", new Object[]{Integer.valueOf(getCodeSize()), Integer.valueOf(readNextCode)}));
            }
            return expandCodeToOutputStack(readNextCode, z);
        }
        clearEntries();
        reAlignReading();
        resetCodeSize();
        resetPreviousCode();
        return 0;
    }

    /* access modifiers changed from: protected */
    public int readNextCode() throws IOException {
        int readNextCode = super.readNextCode();
        if (readNextCode >= 0) {
            this.totalCodesRead++;
        }
        return readNextCode;
    }
}
