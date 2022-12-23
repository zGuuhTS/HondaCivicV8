package net.kdt.pojavlaunch.tasks;

import android.content.Context;
import java.io.IOException;
import org.json.JSONException;

public class Downloader extends Thread implements Runnable {
    private Context ctx;
    private DownloaderCallback listener = null;
    private Boolean overwrite;
    private String path;
    private String url;

    public interface DownloaderCallback {
        void onFinish() throws JSONException, IOException;
    }

    public Downloader(Context ctx2, String path2, String url2, Boolean overwrite2) {
        this.path = path2;
        this.url = url2;
        this.overwrite = overwrite2;
        this.ctx = ctx2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x00a4 A[Catch:{ Exception -> 0x00a8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r18 = this;
            r1 = r18
            r3 = 0
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r4 = r1.url     // Catch:{ Exception -> 0x00a8 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x00a8 }
            java.net.URLConnection r4 = r0.openConnection()     // Catch:{ Exception -> 0x00a8 }
            r4.connect()     // Catch:{ Exception -> 0x00a8 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r6 = r1.path     // Catch:{ Exception -> 0x00a8 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r6 = r5.getName()     // Catch:{ Exception -> 0x00a8 }
            java.io.File r7 = r5.getParentFile()     // Catch:{ Exception -> 0x00a8 }
            r7.mkdirs()     // Catch:{ Exception -> 0x00a8 }
            int r7 = r4.getContentLength()     // Catch:{ Exception -> 0x00a8 }
            r8 = 0
            java.io.File r9 = new java.io.File     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r10 = r1.path     // Catch:{ Exception -> 0x00a8 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00a8 }
            boolean r9 = r9.exists()     // Catch:{ Exception -> 0x00a8 }
            if (r9 == 0) goto L_0x0042
            java.lang.String r9 = r1.path     // Catch:{ Exception -> 0x00a8 }
            java.lang.String[] r10 = new java.lang.String[r3]     // Catch:{ Exception -> 0x00a8 }
            java.nio.file.Path r9 = java.nio.file.Paths.get(r9, r10)     // Catch:{ Exception -> 0x00a8 }
            long r9 = java.nio.file.Files.size(r9)     // Catch:{ Exception -> 0x00a8 }
            int r8 = (int) r9     // Catch:{ Exception -> 0x00a8 }
        L_0x0042:
            if (r7 != r8) goto L_0x0050
            java.lang.Boolean r9 = r1.overwrite     // Catch:{ Exception -> 0x00a8 }
            boolean r9 = r9.booleanValue()     // Catch:{ Exception -> 0x00a8 }
            if (r9 == 0) goto L_0x004d
            goto L_0x0050
        L_0x004d:
            r17 = r0
            goto L_0x00a0
        L_0x0050:
            java.io.BufferedInputStream r9 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00a8 }
            java.io.InputStream r10 = r0.openStream()     // Catch:{ Exception -> 0x00a8 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00a8 }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r11 = r1.path     // Catch:{ Exception -> 0x00a8 }
            r10.<init>(r11)     // Catch:{ Exception -> 0x00a8 }
            r11 = 4096(0x1000, float:5.74E-42)
            byte[] r11 = new byte[r11]     // Catch:{ Exception -> 0x00a8 }
            r12 = 0
        L_0x0066:
            int r14 = r9.read(r11)     // Catch:{ Exception -> 0x00a8 }
            r15 = r14
            r3 = -1
            if (r14 == r3) goto L_0x0094
            long r2 = (long) r15     // Catch:{ Exception -> 0x00a8 }
            long r12 = r12 + r2
            java.lang.String r2 = "download_minecraft"
            r16 = 100
            long r16 = r16 * r12
            r3 = r15
            long r14 = (long) r7     // Catch:{ Exception -> 0x00a8 }
            long r14 = r16 / r14
            int r15 = (int) r14     // Catch:{ Exception -> 0x00a8 }
            r17 = r0
            r14 = 1
            java.lang.Object[] r0 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x00a8 }
            r14 = 2131755109(0x7f100065, float:1.9141088E38)
            r14 = 0
            r0[r14] = r6     // Catch:{ Exception -> 0x00a8 }
            r14 = 2131755109(0x7f100065, float:1.9141088E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r2, r15, r14, r0)     // Catch:{ Exception -> 0x00a8 }
            r2 = 0
            r10.write(r11, r2, r3)     // Catch:{ Exception -> 0x00a8 }
            r0 = r17
            r3 = 0
            goto L_0x0066
        L_0x0094:
            r17 = r0
            r3 = r15
            r10.flush()     // Catch:{ Exception -> 0x00a8 }
            r10.close()     // Catch:{ Exception -> 0x00a8 }
            r9.close()     // Catch:{ Exception -> 0x00a8 }
        L_0x00a0:
            net.kdt.pojavlaunch.tasks.Downloader$DownloaderCallback r0 = r1.listener     // Catch:{ Exception -> 0x00a8 }
            if (r0 == 0) goto L_0x00a7
            r0.onFinish()     // Catch:{ Exception -> 0x00a8 }
        L_0x00a7:
            goto L_0x00b6
        L_0x00a8:
            r0 = move-exception
            android.content.Context r2 = r1.ctx
            r3 = 0
            net.kdt.pojavlaunch.Tools.showError(r2, r0, r3)
            r2 = 1
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            net.kdt.pojavlaunch.Tools.stopDownload = r2
        L_0x00b6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.tasks.Downloader.run():void");
    }

    public void setDownloaderCallback(DownloaderCallback listener2) {
        this.listener = listener2;
    }
}
