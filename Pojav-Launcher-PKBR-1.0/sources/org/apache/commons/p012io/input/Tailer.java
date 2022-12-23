package org.apache.commons.p012io.input;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/* renamed from: org.apache.commons.io.input.Tailer */
public class Tailer implements Runnable {
    private static final int DEFAULT_BUFSIZE = 4096;
    private static final int DEFAULT_DELAY_MILLIS = 1000;
    private static final String RAF_MODE = "r";
    private final long delayMillis;
    private final boolean end;
    private final File file;
    private final byte[] inbuf;
    private final TailerListener listener;
    private final boolean reOpen;
    private volatile boolean run;

    public Tailer(File file2, TailerListener tailerListener) {
        this(file2, tailerListener, 1000);
    }

    public Tailer(File file2, TailerListener tailerListener, long j) {
        this(file2, tailerListener, j, false);
    }

    public Tailer(File file2, TailerListener tailerListener, long j, boolean z) {
        this(file2, tailerListener, j, z, 4096);
    }

    public Tailer(File file2, TailerListener tailerListener, long j, boolean z, int i) {
        this(file2, tailerListener, j, z, false, i);
    }

    public Tailer(File file2, TailerListener tailerListener, long j, boolean z, boolean z2) {
        this(file2, tailerListener, j, z, z2, 4096);
    }

    public Tailer(File file2, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        this.run = true;
        this.file = file2;
        this.delayMillis = j;
        this.end = z;
        this.inbuf = new byte[i];
        this.listener = tailerListener;
        tailerListener.init(this);
        this.reOpen = z2;
    }

    public static Tailer create(File file2, TailerListener tailerListener) {
        return create(file2, tailerListener, 1000, false);
    }

    public static Tailer create(File file2, TailerListener tailerListener, long j) {
        return create(file2, tailerListener, j, false);
    }

    public static Tailer create(File file2, TailerListener tailerListener, long j, boolean z) {
        return create(file2, tailerListener, j, z, 4096);
    }

    public static Tailer create(File file2, TailerListener tailerListener, long j, boolean z, int i) {
        Tailer tailer = new Tailer(file2, tailerListener, j, z, i);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    public static Tailer create(File file2, TailerListener tailerListener, long j, boolean z, boolean z2) {
        return create(file2, tailerListener, j, z, z2, 4096);
    }

    public static Tailer create(File file2, TailerListener tailerListener, long j, boolean z, boolean z2, int i) {
        Tailer tailer = new Tailer(file2, tailerListener, j, z, z2, i);
        Thread thread = new Thread(tailer);
        thread.setDaemon(true);
        thread.start();
        return tailer;
    }

    private long readLines(RandomAccessFile randomAccessFile) throws IOException {
        int read;
        StringBuilder sb = new StringBuilder();
        long filePointer = randomAccessFile.getFilePointer();
        long j = filePointer;
        boolean z = false;
        while (this.run && (read = randomAccessFile.read(this.inbuf)) != -1) {
            for (int i = 0; i < read; i++) {
                byte b = this.inbuf[i];
                if (b == 10) {
                    this.listener.handle(sb.toString());
                    sb.setLength(0);
                    filePointer = ((long) i) + j + 1;
                    z = false;
                } else if (b != 13) {
                    if (z) {
                        this.listener.handle(sb.toString());
                        sb.setLength(0);
                        filePointer = ((long) i) + j + 1;
                        z = false;
                    }
                    sb.append((char) b);
                } else {
                    if (z) {
                        sb.append(13);
                    }
                    z = true;
                }
            }
            j = randomAccessFile.getFilePointer();
        }
        randomAccessFile.seek(filePointer);
        return filePointer;
    }

    public long getDelay() {
        return this.delayMillis;
    }

    public File getFile() {
        return this.file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x008c A[Catch:{ Exception -> 0x00ad }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r13 = this;
            r0 = 0
            r2 = 0
            r3 = r0
            r5 = r3
        L_0x0005:
            boolean r7 = r13.run     // Catch:{ Exception -> 0x00ad }
            java.lang.String r8 = "r"
            if (r7 == 0) goto L_0x003b
            if (r2 != 0) goto L_0x003b
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x0016 }
            java.io.File r9 = r13.file     // Catch:{ FileNotFoundException -> 0x0016 }
            r7.<init>(r9, r8)     // Catch:{ FileNotFoundException -> 0x0016 }
            r2 = r7
            goto L_0x001c
        L_0x0016:
            r7 = move-exception
            org.apache.commons.io.input.TailerListener r7 = r13.listener     // Catch:{ Exception -> 0x00ad }
            r7.fileNotFound()     // Catch:{ Exception -> 0x00ad }
        L_0x001c:
            if (r2 != 0) goto L_0x0026
            long r7 = r13.delayMillis     // Catch:{ InterruptedException -> 0x0024 }
            java.lang.Thread.sleep(r7)     // Catch:{ InterruptedException -> 0x0024 }
            goto L_0x0005
        L_0x0024:
            r7 = move-exception
            goto L_0x0005
        L_0x0026:
            boolean r3 = r13.end     // Catch:{ Exception -> 0x00ad }
            if (r3 == 0) goto L_0x0032
            java.io.File r3 = r13.file     // Catch:{ Exception -> 0x00ad }
            long r3 = r3.length()     // Catch:{ Exception -> 0x00ad }
            r5 = r3
            goto L_0x0033
        L_0x0032:
            r5 = r0
        L_0x0033:
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00ad }
            r2.seek(r5)     // Catch:{ Exception -> 0x00ad }
            goto L_0x0005
        L_0x003b:
            boolean r7 = r13.run     // Catch:{ Exception -> 0x00ad }
            if (r7 == 0) goto L_0x00b3
            java.io.File r7 = r13.file     // Catch:{ Exception -> 0x00ad }
            boolean r7 = org.apache.commons.p012io.FileUtils.isFileNewer((java.io.File) r7, (long) r3)     // Catch:{ Exception -> 0x00ad }
            java.io.File r9 = r13.file     // Catch:{ Exception -> 0x00ad }
            long r9 = r9.length()     // Catch:{ Exception -> 0x00ad }
            int r9 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r9 >= 0) goto L_0x0070
            org.apache.commons.io.input.TailerListener r7 = r13.listener     // Catch:{ Exception -> 0x00ad }
            r7.fileRotated()     // Catch:{ Exception -> 0x00ad }
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch:{ FileNotFoundException -> 0x0069 }
            java.io.File r9 = r13.file     // Catch:{ FileNotFoundException -> 0x0069 }
            r7.<init>(r9, r8)     // Catch:{ FileNotFoundException -> 0x0069 }
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.Closeable) r2)     // Catch:{ FileNotFoundException -> 0x0065 }
            r5 = r0
        L_0x005f:
            r2 = r7
            goto L_0x003b
        L_0x0061:
            r0 = move-exception
            goto L_0x00b8
        L_0x0063:
            r0 = move-exception
            goto L_0x00a9
        L_0x0065:
            r2 = move-exception
            r5 = r0
            r2 = r7
            goto L_0x006a
        L_0x0069:
            r7 = move-exception
        L_0x006a:
            org.apache.commons.io.input.TailerListener r7 = r13.listener     // Catch:{ Exception -> 0x00ad }
            r7.fileNotFound()     // Catch:{ Exception -> 0x00ad }
            goto L_0x003b
        L_0x0070:
            if (r9 <= 0) goto L_0x007e
            long r3 = r13.readLines(r2)     // Catch:{ Exception -> 0x00ad }
        L_0x0076:
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00ad }
            r11 = r3
            r3 = r5
            r5 = r11
            goto L_0x0088
        L_0x007e:
            if (r7 == 0) goto L_0x0088
            r2.seek(r0)     // Catch:{ Exception -> 0x00ad }
            long r3 = r13.readLines(r2)     // Catch:{ Exception -> 0x00ad }
            goto L_0x0076
        L_0x0088:
            boolean r7 = r13.reOpen     // Catch:{ Exception -> 0x00ad }
            if (r7 == 0) goto L_0x008f
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.Closeable) r2)     // Catch:{ Exception -> 0x00ad }
        L_0x008f:
            long r9 = r13.delayMillis     // Catch:{ InterruptedException -> 0x0095 }
            java.lang.Thread.sleep(r9)     // Catch:{ InterruptedException -> 0x0095 }
            goto L_0x0096
        L_0x0095:
            r7 = move-exception
        L_0x0096:
            boolean r7 = r13.run     // Catch:{ Exception -> 0x00ad }
            if (r7 == 0) goto L_0x003b
            boolean r7 = r13.reOpen     // Catch:{ Exception -> 0x00ad }
            if (r7 == 0) goto L_0x003b
            java.io.RandomAccessFile r7 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x00ad }
            java.io.File r9 = r13.file     // Catch:{ Exception -> 0x00ad }
            r7.<init>(r9, r8)     // Catch:{ Exception -> 0x00ad }
            r7.seek(r5)     // Catch:{ Exception -> 0x0063, all -> 0x0061 }
            goto L_0x005f
        L_0x00a9:
            r2 = r7
            goto L_0x00ae
        L_0x00ab:
            r0 = move-exception
            goto L_0x00b7
        L_0x00ad:
            r0 = move-exception
        L_0x00ae:
            org.apache.commons.io.input.TailerListener r1 = r13.listener     // Catch:{ all -> 0x00ab }
            r1.handle((java.lang.Exception) r0)     // Catch:{ all -> 0x00ab }
        L_0x00b3:
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.Closeable) r2)
            return
        L_0x00b7:
            r7 = r2
        L_0x00b8:
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.Closeable) r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p012io.input.Tailer.run():void");
    }

    public void stop() {
        this.run = false;
    }
}
