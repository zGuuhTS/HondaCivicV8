package net.kdt.pojavlaunch.tasks;

import android.app.Activity;
import android.util.Log;
import com.kdt.mcgui.ProgressLayout;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import net.kdt.pojavlaunch.JAssetInfo;
import net.kdt.pojavlaunch.JAssets;
import net.kdt.pojavlaunch.JMinecraftVersionList;
import net.kdt.pojavlaunch.PXBRApplication;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.tasks.AsyncMinecraftDownloader;
import net.kdt.pojavlaunch.utils.DownloadUtils;

public class AsyncMinecraftDownloader {
    public static final String MINECRAFT_RES = "https://resources.download.minecraft.net/";
    private final ConcurrentHashMap<Thread, byte[]> mThreadBuffers = new ConcurrentHashMap<>(5);

    public interface DoneListener {
        void onDownloadDone();
    }

    public AsyncMinecraftDownloader(Activity activity, JMinecraftVersionList.Version version, String realVersion, DoneListener listener) {
        PXBRApplication.sExecutorService.execute(new Runnable(activity, version, realVersion, listener) {
            public final /* synthetic */ Activity f$1;
            public final /* synthetic */ JMinecraftVersionList.Version f$2;
            public final /* synthetic */ String f$3;
            public final /* synthetic */ AsyncMinecraftDownloader.DoneListener f$4;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
            }

            public final void run() {
                AsyncMinecraftDownloader.this.lambda$new$0$AsyncMinecraftDownloader(this.f$1, this.f$2, this.f$3, this.f$4);
            }
        });
    }

    public /* synthetic */ void lambda$new$0$AsyncMinecraftDownloader(Activity activity, JMinecraftVersionList.Version version, String realVersion, DoneListener listener) {
        if (downloadGame(activity, version, realVersion)) {
            listener.onDownloadDone();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:159:0x03fe  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x0430 A[Catch:{ Exception -> 0x0443 }] */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x0438 A[Catch:{ Exception -> 0x0443 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0095 A[SYNTHETIC, Splitter:B:28:0x0095] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c5 A[Catch:{ all -> 0x00c8, all -> 0x03ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00de A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e0 A[SYNTHETIC, Splitter:B:43:0x00e0] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean downloadGame(android.app.Activity r24, net.kdt.pojavlaunch.JMinecraftVersionList.Version r25, java.lang.String r26) {
        /*
            r23 = this;
            r1 = r23
            r2 = r25
            r3 = r26
            java.lang.String r4 = "Downloader"
            java.lang.String r0 = ".json"
            java.lang.String r5 = "AsyncMcDownloader"
            java.lang.String r6 = "download_minecraft"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "/"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.StringBuilder r7 = r7.append(r3)
            java.lang.String r7 = r7.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = net.kdt.pojavlaunch.Tools.DIR_HOME_VERSION
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r7)
            java.lang.String r10 = ".jar"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r9 = r9.toString()
            r11 = 0
            r12 = 1
            java.io.File r14 = new java.io.File     // Catch:{ all -> 0x03ce }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x03ce }
            r15.<init>()     // Catch:{ all -> 0x03ce }
            java.lang.String r13 = net.kdt.pojavlaunch.Tools.DIR_HOME_VERSION     // Catch:{ all -> 0x03ce }
            java.lang.StringBuilder r13 = r15.append(r13)     // Catch:{ all -> 0x03ce }
            java.lang.StringBuilder r13 = r13.append(r7)     // Catch:{ all -> 0x03ce }
            java.lang.StringBuilder r13 = r13.append(r0)     // Catch:{ all -> 0x03ce }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x03ce }
            r14.<init>(r13)     // Catch:{ all -> 0x03ce }
            r13 = r14
            if (r2 == 0) goto L_0x00cf
            java.lang.String r15 = r2.url     // Catch:{ all -> 0x00c8 }
            if (r15 == 0) goto L_0x00cf
            boolean r15 = net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_CHECK_LIBRARY_SHA     // Catch:{ all -> 0x00c8 }
            if (r15 != 0) goto L_0x007a
            java.lang.String r15 = "Chk"
            java.lang.String r14 = "Checker is off"
            android.util.Log.w(r15, r14)     // Catch:{ all -> 0x0073 }
            goto L_0x007a
        L_0x0073:
            r0 = move-exception
            r22 = r5
            r17 = r7
            goto L_0x03d3
        L_0x007a:
            boolean r14 = r13.exists()     // Catch:{ all -> 0x00c8 }
            if (r14 == 0) goto L_0x0092
            boolean r14 = net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_CHECK_LIBRARY_SHA     // Catch:{ all -> 0x0073 }
            if (r14 == 0) goto L_0x0090
            java.lang.String r14 = r2.sha1     // Catch:{ all -> 0x0073 }
            if (r14 == 0) goto L_0x0090
            java.lang.String r14 = r2.sha1     // Catch:{ all -> 0x0073 }
            boolean r14 = net.kdt.pojavlaunch.Tools.compareSHA1(r13, r14)     // Catch:{ all -> 0x0073 }
            if (r14 == 0) goto L_0x0092
        L_0x0090:
            r14 = r12
            goto L_0x0093
        L_0x0092:
            r14 = 0
        L_0x0093:
            if (r14 != 0) goto L_0x00c5
            java.lang.Object[] r15 = new java.lang.Object[r12]     // Catch:{ all -> 0x00c8 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c8 }
            r12.<init>()     // Catch:{ all -> 0x00c8 }
            java.lang.StringBuilder r12 = r12.append(r3)     // Catch:{ all -> 0x00c8 }
            java.lang.StringBuilder r12 = r12.append(r0)     // Catch:{ all -> 0x00c8 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x00c8 }
            r17 = r7
            r7 = 0
            r15[r7] = r12     // Catch:{ all -> 0x03ca }
            r12 = 2131755109(0x7f100065, float:1.9141088E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r7, r12, r15)     // Catch:{ all -> 0x03ca }
            r13.delete()     // Catch:{ all -> 0x03ca }
            java.lang.String r7 = r2.url     // Catch:{ all -> 0x03ca }
            byte[] r12 = r23.getByteBuffer()     // Catch:{ all -> 0x03ca }
            net.kdt.pojavlaunch.tasks.-$$Lambda$AsyncMinecraftDownloader$O_TWTyWpjJ4DpUPluboDR-x92jY r15 = new net.kdt.pojavlaunch.tasks.-$$Lambda$AsyncMinecraftDownloader$O_TWTyWpjJ4DpUPluboDR-x92jY     // Catch:{ all -> 0x03ca }
            r15.<init>(r3)     // Catch:{ all -> 0x03ca }
            net.kdt.pojavlaunch.utils.DownloadUtils.downloadFileMonitored((java.lang.String) r7, (java.io.File) r13, (byte[]) r12, (net.kdt.pojavlaunch.Tools.DownloaderFeedback) r15)     // Catch:{ all -> 0x03ca }
            goto L_0x00d1
        L_0x00c5:
            r17 = r7
            goto L_0x00d1
        L_0x00c8:
            r0 = move-exception
            r17 = r7
            r22 = r5
            goto L_0x03d3
        L_0x00cf:
            r17 = r7
        L_0x00d1:
            net.kdt.pojavlaunch.JMinecraftVersionList$Version r7 = net.kdt.pojavlaunch.Tools.getVersionInfo(r26)     // Catch:{ all -> 0x03ca }
            r2 = r7
            r7 = r24
            boolean r12 = net.kdt.pojavlaunch.JRE17Util.installNewJreIfNeeded(r7, r2)     // Catch:{ all -> 0x03c6 }
            if (r12 != 0) goto L_0x00e0
            r4 = 0
            return r4
        L_0x00e0:
            java.io.File r12 = new java.io.File     // Catch:{ IOException -> 0x0106 }
            java.lang.String r14 = net.kdt.pojavlaunch.Tools.ASSETS_PATH     // Catch:{ IOException -> 0x0106 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0106 }
            r15.<init>()     // Catch:{ IOException -> 0x0106 }
            java.lang.String r7 = "indexes/"
            java.lang.StringBuilder r7 = r15.append(r7)     // Catch:{ IOException -> 0x0106 }
            java.lang.String r15 = r2.assets     // Catch:{ IOException -> 0x0106 }
            java.lang.StringBuilder r7 = r7.append(r15)     // Catch:{ IOException -> 0x0106 }
            java.lang.StringBuilder r0 = r7.append(r0)     // Catch:{ IOException -> 0x0106 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x0106 }
            r12.<init>(r14, r0)     // Catch:{ IOException -> 0x0106 }
            net.kdt.pojavlaunch.JAssets r0 = r1.downloadIndex(r2, r12)     // Catch:{ IOException -> 0x0106 }
            r11 = r0
            goto L_0x010e
        L_0x0106:
            r0 = move-exception
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x03c6 }
            android.util.Log.e(r5, r7, r0)     // Catch:{ all -> 0x03c6 }
        L_0x010e:
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r0 = r2.logging     // Catch:{ all -> 0x03c0 }
            java.lang.String r14 = "client"
            if (r0 == 0) goto L_0x01f9
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x01f3 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x01f3 }
            r15.<init>()     // Catch:{ all -> 0x01f3 }
            java.lang.String r12 = net.kdt.pojavlaunch.Tools.DIR_DATA     // Catch:{ all -> 0x01f3 }
            java.lang.StringBuilder r12 = r15.append(r12)     // Catch:{ all -> 0x01f3 }
            java.lang.String r15 = "/security"
            java.lang.StringBuilder r12 = r12.append(r15)     // Catch:{ all -> 0x01f3 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x01f3 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r15 = r2.logging     // Catch:{ all -> 0x01f3 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r15 = r15.client     // Catch:{ all -> 0x01f3 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r15 = r15.file     // Catch:{ all -> 0x01f3 }
            java.lang.String r15 = r15.f17id     // Catch:{ all -> 0x01f3 }
            java.lang.String r7 = "log4j-rce-patch"
            java.lang.String r7 = r15.replace(r14, r7)     // Catch:{ all -> 0x01f3 }
            r0.<init>(r12, r7)     // Catch:{ all -> 0x01f3 }
            boolean r7 = r0.exists()     // Catch:{ all -> 0x01f3 }
            if (r7 != 0) goto L_0x0155
            java.io.File r12 = new java.io.File     // Catch:{ all -> 0x03c6 }
            java.lang.String r15 = net.kdt.pojavlaunch.Tools.DIR_GAME_NEW     // Catch:{ all -> 0x03c6 }
            r19 = r0
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r0 = r2.logging     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r0 = r0.client     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r0 = r0.file     // Catch:{ all -> 0x03c6 }
            java.lang.String r0 = r0.f17id     // Catch:{ all -> 0x03c6 }
            r12.<init>(r15, r0)     // Catch:{ all -> 0x03c6 }
            r0 = r12
            goto L_0x0157
        L_0x0155:
            r19 = r0
        L_0x0157:
            boolean r12 = r0.exists()     // Catch:{ all -> 0x01f3 }
            if (r12 == 0) goto L_0x01bb
            if (r7 != 0) goto L_0x01bb
            boolean r12 = net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_CHECK_LIBRARY_SHA     // Catch:{ all -> 0x01f3 }
            if (r12 == 0) goto L_0x01a4
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r12 = r2.logging     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r12 = r12.client     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r12 = r12.file     // Catch:{ all -> 0x03c6 }
            java.lang.String r12 = r12.sha1     // Catch:{ all -> 0x03c6 }
            boolean r12 = net.kdt.pojavlaunch.Tools.compareSHA1(r0, r12)     // Catch:{ all -> 0x03c6 }
            if (r12 != 0) goto L_0x018c
            r0.delete()     // Catch:{ all -> 0x03c6 }
            r12 = 1
            java.lang.Object[] r15 = new java.lang.Object[r12]     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r12 = r2.logging     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r12 = r12.client     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r12 = r12.file     // Catch:{ all -> 0x03c6 }
            java.lang.String r12 = r12.f17id     // Catch:{ all -> 0x03c6 }
            r19 = r7
            r7 = 0
            r15[r7] = r12     // Catch:{ all -> 0x03c6 }
            r12 = 2131755067(0x7f10003b, float:1.9141003E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r7, r12, r15)     // Catch:{ all -> 0x03c6 }
            r15 = r11
            goto L_0x01be
        L_0x018c:
            r19 = r7
            r7 = 1
            java.lang.Object[] r12 = new java.lang.Object[r7]     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r7 = r2.logging     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r7 = r7.client     // Catch:{ all -> 0x03c6 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r7 = r7.file     // Catch:{ all -> 0x03c6 }
            java.lang.String r7 = r7.f17id     // Catch:{ all -> 0x03c6 }
            r15 = 0
            r12[r15] = r7     // Catch:{ all -> 0x03c6 }
            r7 = 2131755068(0x7f10003c, float:1.9141005E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r15, r7, r12)     // Catch:{ all -> 0x03c6 }
            r15 = r11
            goto L_0x01be
        L_0x01a4:
            r19 = r7
            long r20 = r0.length()     // Catch:{ all -> 0x01f3 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r7 = r2.logging     // Catch:{ all -> 0x01f3 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r7 = r7.client     // Catch:{ all -> 0x01f3 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r7 = r7.file     // Catch:{ all -> 0x01f3 }
            r15 = r11
            long r11 = r7.size     // Catch:{ all -> 0x0231 }
            int r7 = (r20 > r11 ? 1 : (r20 == r11 ? 0 : -1))
            if (r7 == 0) goto L_0x01be
            r0.delete()     // Catch:{ all -> 0x0231 }
            goto L_0x01be
        L_0x01bb:
            r19 = r7
            r15 = r11
        L_0x01be:
            boolean r7 = r0.exists()     // Catch:{ all -> 0x0231 }
            if (r7 != 0) goto L_0x01f0
            r7 = 1
            java.lang.Object[] r11 = new java.lang.Object[r7]     // Catch:{ all -> 0x0231 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r7 = r2.logging     // Catch:{ all -> 0x0231 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r7 = r7.client     // Catch:{ all -> 0x0231 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r7 = r7.file     // Catch:{ all -> 0x0231 }
            java.lang.String r7 = r7.f17id     // Catch:{ all -> 0x0231 }
            r12 = 0
            r11[r12] = r7     // Catch:{ all -> 0x0231 }
            r7 = 2131755109(0x7f100065, float:1.9141088E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r12, r7, r11)     // Catch:{ all -> 0x0231 }
            r7 = r2
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig r11 = r2.logging     // Catch:{ all -> 0x0231 }
            net.kdt.pojavlaunch.JMinecraftVersionList$LoggingConfig$LoggingClientConfig r11 = r11.client     // Catch:{ all -> 0x0231 }
            net.kdt.pojavlaunch.JMinecraftVersionList$FileProperties r11 = r11.file     // Catch:{ all -> 0x0231 }
            java.lang.String r11 = r11.url     // Catch:{ all -> 0x0231 }
            byte[] r12 = r23.getByteBuffer()     // Catch:{ all -> 0x0231 }
            r16 = r13
            net.kdt.pojavlaunch.tasks.-$$Lambda$AsyncMinecraftDownloader$KfHlGSbPtFbFeV81XSIIaYFhRr8 r13 = new net.kdt.pojavlaunch.tasks.-$$Lambda$AsyncMinecraftDownloader$KfHlGSbPtFbFeV81XSIIaYFhRr8     // Catch:{ all -> 0x0231 }
            r13.<init>()     // Catch:{ all -> 0x0231 }
            net.kdt.pojavlaunch.utils.DownloadUtils.downloadFileMonitored((java.lang.String) r11, (java.io.File) r0, (byte[]) r12, (net.kdt.pojavlaunch.Tools.DownloaderFeedback) r13)     // Catch:{ all -> 0x0231 }
            goto L_0x01fc
        L_0x01f0:
            r16 = r13
            goto L_0x01fc
        L_0x01f3:
            r0 = move-exception
            r15 = r11
            r22 = r5
            goto L_0x03d3
        L_0x01f9:
            r15 = r11
            r16 = r13
        L_0x01fc:
            net.kdt.pojavlaunch.value.DependentLibrary[] r0 = r2.libraries     // Catch:{ all -> 0x03b8 }
            int r7 = r0.length     // Catch:{ all -> 0x03b8 }
            r11 = 0
        L_0x0200:
            if (r11 >= r7) goto L_0x02ea
            r12 = r0[r11]     // Catch:{ all -> 0x03b8 }
            java.lang.String r13 = r12.name     // Catch:{ all -> 0x03b8 }
            r19 = r0
            java.lang.String r0 = "org.lwjgl"
            boolean r0 = r13.startsWith(r0)     // Catch:{ all -> 0x03b8 }
            if (r0 == 0) goto L_0x0237
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0231 }
            r0.<init>()     // Catch:{ all -> 0x0231 }
            java.lang.String r13 = "Ignored "
            java.lang.StringBuilder r0 = r0.append(r13)     // Catch:{ all -> 0x0231 }
            java.lang.String r13 = r12.name     // Catch:{ all -> 0x0231 }
            java.lang.StringBuilder r0 = r0.append(r13)     // Catch:{ all -> 0x0231 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0231 }
            r13 = 0
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r13, r0)     // Catch:{ all -> 0x0231 }
            r22 = r5
            r20 = r7
            r21 = r15
            goto L_0x02d9
        L_0x0231:
            r0 = move-exception
            r22 = r5
            r11 = r15
            goto L_0x03d3
        L_0x0237:
            java.lang.String r0 = r12.name     // Catch:{ all -> 0x03b8 }
            java.lang.String r0 = net.kdt.pojavlaunch.Tools.artifactToPath(r0)     // Catch:{ all -> 0x03b8 }
            java.io.File r13 = new java.io.File     // Catch:{ all -> 0x03b8 }
            r20 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x03b8 }
            r7.<init>()     // Catch:{ all -> 0x03b8 }
            r21 = r15
            java.lang.String r15 = net.kdt.pojavlaunch.Tools.DIR_HOME_LIBRARY     // Catch:{ all -> 0x02e5 }
            java.lang.StringBuilder r7 = r7.append(r15)     // Catch:{ all -> 0x02e5 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x02e5 }
            java.lang.StringBuilder r7 = r7.append(r0)     // Catch:{ all -> 0x02e5 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x02e5 }
            r13.<init>(r7)     // Catch:{ all -> 0x02e5 }
            r7 = r13
            java.io.File r13 = r7.getParentFile()     // Catch:{ all -> 0x02e5 }
            r13.mkdirs()     // Catch:{ all -> 0x02e5 }
            boolean r13 = r7.exists()     // Catch:{ all -> 0x02e5 }
            if (r13 != 0) goto L_0x0271
            r1.downloadLibrary(r12, r0, r7)     // Catch:{ all -> 0x02e5 }
            r22 = r5
            goto L_0x02d9
        L_0x0271:
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r13 = r12.downloads     // Catch:{ all -> 0x02e5 }
            if (r13 == 0) goto L_0x02c6
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r13 = r12.downloads     // Catch:{ all -> 0x02e5 }
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r13 = r13.artifact     // Catch:{ all -> 0x02e5 }
            if (r13 == 0) goto L_0x02c6
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r13 = r12.downloads     // Catch:{ all -> 0x02e5 }
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r13 = r13.artifact     // Catch:{ all -> 0x02e5 }
            java.lang.String r13 = r13.sha1     // Catch:{ all -> 0x02e5 }
            if (r13 == 0) goto L_0x02c6
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r13 = r12.downloads     // Catch:{ all -> 0x02e5 }
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r13 = r13.artifact     // Catch:{ all -> 0x02e5 }
            java.lang.String r13 = r13.sha1     // Catch:{ all -> 0x02e5 }
            boolean r13 = r13.isEmpty()     // Catch:{ all -> 0x02e5 }
            if (r13 != 0) goto L_0x02c6
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r13 = r12.downloads     // Catch:{ all -> 0x02e5 }
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r13 = r13.artifact     // Catch:{ all -> 0x02e5 }
            java.lang.String r13 = r13.sha1     // Catch:{ all -> 0x02e5 }
            boolean r13 = net.kdt.pojavlaunch.Tools.compareSHA1(r7, r13)     // Catch:{ all -> 0x02e5 }
            if (r13 != 0) goto L_0x02b2
            r7.delete()     // Catch:{ all -> 0x02e5 }
            r13 = 1
            java.lang.Object[] r15 = new java.lang.Object[r13]     // Catch:{ all -> 0x02e5 }
            java.lang.String r13 = r12.name     // Catch:{ all -> 0x02e5 }
            r22 = r5
            r5 = 0
            r15[r5] = r13     // Catch:{ all -> 0x03b4 }
            r13 = 2131755067(0x7f10003b, float:1.9141003E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r5, r13, r15)     // Catch:{ all -> 0x03b4 }
            r1.downloadLibrary(r12, r0, r7)     // Catch:{ all -> 0x03b4 }
            goto L_0x02d9
        L_0x02b2:
            r22 = r5
            r13 = 2131755067(0x7f10003b, float:1.9141003E38)
            r5 = 1
            java.lang.Object[] r15 = new java.lang.Object[r5]     // Catch:{ all -> 0x03b4 }
            java.lang.String r5 = r12.name     // Catch:{ all -> 0x03b4 }
            r13 = 0
            r15[r13] = r5     // Catch:{ all -> 0x03b4 }
            r5 = 2131755068(0x7f10003c, float:1.9141005E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r13, r5, r15)     // Catch:{ all -> 0x03b4 }
            goto L_0x02d9
        L_0x02c6:
            r22 = r5
            r5 = 2131755068(0x7f10003c, float:1.9141005E38)
            r15 = 1
            java.lang.Object[] r5 = new java.lang.Object[r15]     // Catch:{ all -> 0x03b4 }
            java.lang.String r15 = r12.name     // Catch:{ all -> 0x03b4 }
            r13 = 0
            r5[r13] = r15     // Catch:{ all -> 0x03b4 }
            r15 = 2131755069(0x7f10003d, float:1.9141007E38)
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r13, r15, r5)     // Catch:{ all -> 0x03b4 }
        L_0x02d9:
            int r11 = r11 + 1
            r0 = r19
            r7 = r20
            r15 = r21
            r5 = r22
            goto L_0x0200
        L_0x02e5:
            r0 = move-exception
            r22 = r5
            goto L_0x03b5
        L_0x02ea:
            r22 = r5
            r21 = r15
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x03b4 }
            r0.<init>(r9)     // Catch:{ all -> 0x03b4 }
            r5 = 1
            net.kdt.pojavlaunch.JMinecraftVersionList$Version r7 = net.kdt.pojavlaunch.Tools.getVersionInfo(r3, r5)     // Catch:{ all -> 0x03b4 }
            r5 = r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x03b4 }
            r7.<init>()     // Catch:{ all -> 0x03b4 }
            java.lang.String r11 = "originalVersion.inheritsFrom="
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x03b4 }
            java.lang.String r11 = r5.inheritsFrom     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x03b4 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x03b4 }
            android.util.Log.i(r4, r7)     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x03b4 }
            r7.<init>()     // Catch:{ all -> 0x03b4 }
            java.lang.String r11 = "originalVersion.downloads="
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x03b4 }
            java.util.Map<java.lang.String, net.kdt.pojavlaunch.value.MinecraftClientInfo> r11 = r5.downloads     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x03b4 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x03b4 }
            android.util.Log.i(r4, r7)     // Catch:{ all -> 0x03b4 }
            java.lang.String r4 = r5.inheritsFrom     // Catch:{ all -> 0x03b4 }
            if (r4 != 0) goto L_0x0344
            java.util.Map<java.lang.String, net.kdt.pojavlaunch.value.MinecraftClientInfo> r4 = r5.downloads     // Catch:{ all -> 0x03b4 }
            if (r4 == 0) goto L_0x03af
            java.util.Map<java.lang.String, net.kdt.pojavlaunch.value.MinecraftClientInfo> r4 = r5.downloads     // Catch:{ all -> 0x03b4 }
            java.lang.Object r4 = r4.get(r14)     // Catch:{ all -> 0x03b4 }
            net.kdt.pojavlaunch.value.MinecraftClientInfo r4 = (net.kdt.pojavlaunch.value.MinecraftClientInfo) r4     // Catch:{ all -> 0x03b4 }
            r7 = r4
            if (r4 == 0) goto L_0x03af
            java.lang.String r4 = r7.url     // Catch:{ all -> 0x03b4 }
            java.lang.String r8 = r7.sha1     // Catch:{ all -> 0x03b4 }
            r1.verifyAndDownloadMainJar(r4, r8, r0)     // Catch:{ all -> 0x03b4 }
            goto L_0x03af
        L_0x0344:
            boolean r4 = r0.exists()     // Catch:{ all -> 0x03b4 }
            if (r4 == 0) goto L_0x0354
            long r11 = r0.length()     // Catch:{ all -> 0x03b4 }
            r18 = 0
            int r4 = (r11 > r18 ? 1 : (r11 == r18 ? 0 : -1))
            if (r4 != 0) goto L_0x03af
        L_0x0354:
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x03b4 }
            r7.<init>()     // Catch:{ all -> 0x03b4 }
            java.lang.String r11 = net.kdt.pojavlaunch.Tools.DIR_HOME_VERSION     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x03b4 }
            java.lang.String r11 = r2.f17id     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r11)     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x03b4 }
            java.lang.String r8 = r2.f17id     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x03b4 }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ all -> 0x03b4 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x03b4 }
            r4.<init>(r7)     // Catch:{ all -> 0x03b4 }
            java.util.Map<java.lang.String, net.kdt.pojavlaunch.value.MinecraftClientInfo> r7 = r2.downloads     // Catch:{ all -> 0x03b4 }
            if (r7 == 0) goto L_0x0396
            java.util.Map<java.lang.String, net.kdt.pojavlaunch.value.MinecraftClientInfo> r7 = r2.downloads     // Catch:{ all -> 0x03b4 }
            java.lang.Object r7 = r7.get(r14)     // Catch:{ all -> 0x03b4 }
            net.kdt.pojavlaunch.value.MinecraftClientInfo r7 = (net.kdt.pojavlaunch.value.MinecraftClientInfo) r7     // Catch:{ all -> 0x03b4 }
            r8 = r7
            if (r7 == 0) goto L_0x0396
            java.lang.String r7 = r8.url     // Catch:{ all -> 0x03b4 }
            java.lang.String r10 = r8.sha1     // Catch:{ all -> 0x03b4 }
            r1.verifyAndDownloadMainJar(r7, r10, r4)     // Catch:{ all -> 0x03b4 }
        L_0x0396:
            boolean r7 = r4.exists()     // Catch:{ all -> 0x03b4 }
            if (r7 == 0) goto L_0x03af
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ all -> 0x03b4 }
            r7.<init>(r4)     // Catch:{ all -> 0x03b4 }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x03b4 }
            r8.<init>(r0)     // Catch:{ all -> 0x03b4 }
            org.apache.commons.p012io.IOUtils.copy((java.io.InputStream) r7, (java.io.OutputStream) r8)     // Catch:{ all -> 0x03b4 }
            r7.close()     // Catch:{ all -> 0x03b4 }
            r8.close()     // Catch:{ all -> 0x03b4 }
        L_0x03af:
            r11 = r21
            r5 = r22
            goto L_0x03dc
        L_0x03b4:
            r0 = move-exception
        L_0x03b5:
            r11 = r21
            goto L_0x03d3
        L_0x03b8:
            r0 = move-exception
            r22 = r5
            r21 = r15
            r11 = r21
            goto L_0x03d3
        L_0x03c0:
            r0 = move-exception
            r22 = r5
            r21 = r11
            goto L_0x03d3
        L_0x03c6:
            r0 = move-exception
            r22 = r5
            goto L_0x03d3
        L_0x03ca:
            r0 = move-exception
            r22 = r5
            goto L_0x03d3
        L_0x03ce:
            r0 = move-exception
            r22 = r5
            r17 = r7
        L_0x03d3:
            java.lang.String r4 = r0.toString()
            r5 = r22
            android.util.Log.e(r5, r4, r0)
        L_0x03dc:
            r0 = 2131755107(0x7f100063, float:1.9141084E38)
            r4 = 0
            java.lang.Object[] r7 = new java.lang.Object[r4]
            com.kdt.mcgui.ProgressLayout.setProgress(r6, r4, r0, r7)
            java.io.File r0 = new java.io.File
            java.lang.String r6 = net.kdt.pojavlaunch.Tools.DIR_HOME_VERSION
            r0.<init>(r6)
            r0.mkdir()
            java.io.File r0 = new java.io.File
            java.lang.String r6 = net.kdt.pojavlaunch.Tools.DIR_HOME_VERSION
            r0.<init>(r6)
            java.io.File[] r0 = r0.listFiles()
            int r6 = r0.length
            r13 = r4
        L_0x03fc:
            if (r13 >= r6) goto L_0x042a
            r4 = r0[r13]
            java.lang.String r7 = r4.getName()
            java.lang.String r8 = ".part"
            boolean r7 = r7.endsWith(r8)
            if (r7 == 0) goto L_0x0427
            java.lang.String r7 = net.kdt.pojavlaunch.Tools.APP_NAME
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r10 = "Cleaning cache: "
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.StringBuilder r8 = r8.append(r4)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r7, r8)
            r4.delete()
        L_0x0427:
            int r13 = r13 + 1
            goto L_0x03fc
        L_0x042a:
            java.lang.String r0 = r2.assets     // Catch:{ Exception -> 0x0443 }
            boolean r4 = r11.mapToResources     // Catch:{ Exception -> 0x0443 }
            if (r4 == 0) goto L_0x0438
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0443 }
            java.lang.String r6 = net.kdt.pojavlaunch.Tools.OBSOLETE_RESOURCES_PATH     // Catch:{ Exception -> 0x0443 }
            r4.<init>(r6)     // Catch:{ Exception -> 0x0443 }
            goto L_0x043f
        L_0x0438:
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0443 }
            java.lang.String r6 = net.kdt.pojavlaunch.Tools.ASSETS_PATH     // Catch:{ Exception -> 0x0443 }
            r4.<init>(r6)     // Catch:{ Exception -> 0x0443 }
        L_0x043f:
            r1.downloadAssets(r11, r0, r4)     // Catch:{ Exception -> 0x0443 }
            goto L_0x044b
        L_0x0443:
            r0 = move-exception
            java.lang.String r4 = r0.toString()
            android.util.Log.e(r5, r4, r0)
        L_0x044b:
            r4 = 1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.tasks.AsyncMinecraftDownloader.downloadGame(android.app.Activity, net.kdt.pojavlaunch.JMinecraftVersionList$Version, java.lang.String):boolean");
    }

    public void verifyAndDownloadMainJar(String url, String sha1, File destination) throws Exception {
        while (true) {
            if (!destination.exists() || (destination.exists() && !Tools.compareSHA1(destination, sha1))) {
                DownloadUtils.downloadFileMonitored(url, destination, getByteBuffer(), (Tools.DownloaderFeedback) new Tools.DownloaderFeedback(destination) {
                    public final /* synthetic */ File f$0;

                    {
                        this.f$0 = r1;
                    }

                    public final void updateProgress(int i, int i2) {
                        ProgressLayout.setProgress(ProgressLayout.DOWNLOAD_MINECRAFT, (int) Math.max((((float) i) / ((float) i2)) * 100.0f, 0.0f), R.string.mcl_launch_downloading, this.f$0.getName());
                    }
                });
            } else {
                return;
            }
        }
    }

    public void downloadAssets(JAssets assets, String assetsVersion, File outputDir) throws IOException {
        boolean skip;
        AtomicInteger downloadedSize;
        File objectsDir;
        int i;
        JAssets jAssets = assets;
        File file = outputDir;
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 500, TimeUnit.MILLISECONDS, workQueue);
        Log.i("AsyncMcDownloader", "Assets begin time: " + System.currentTimeMillis());
        int i2 = 0;
        ProgressLayout.setProgress(ProgressLayout.DOWNLOAD_MINECRAFT, 0, R.string.mcl_launch_download_assets, new Object[0]);
        Map<String, JAssetInfo> assetsObjects = jAssets.objects;
        int assetsSizeBytes = 0;
        AtomicInteger downloadedSize2 = new AtomicInteger(0);
        AtomicBoolean localInterrupt = new AtomicBoolean(false);
        File objectsDir2 = new File(file, "objects");
        int i3 = 1;
        ProgressLayout.setProgress(ProgressLayout.DOWNLOAD_MINECRAFT, 0, R.string.mcl_launch_downloading, "assets");
        for (String assetKey : assetsObjects.keySet()) {
            JAssetInfo asset = assetsObjects.get(assetKey);
            int assetsSizeBytes2 = assetsSizeBytes + asset.size;
            String assetPath = asset.hash.substring(i2, 2) + "/" + asset.hash;
            File outFile = jAssets.mapToResources ? new File(file, "/" + assetKey) : new File(objectsDir2, assetPath);
            boolean skip2 = outFile.exists();
            if (!LauncherPreferences.PREF_CHECK_LIBRARY_SHA || !skip2) {
                skip = skip2;
            } else {
                skip = Tools.compareSHA1(outFile, asset.hash);
            }
            if (skip) {
                downloadedSize2.addAndGet(asset.size);
                i = i3;
                objectsDir = objectsDir2;
                downloadedSize = downloadedSize2;
            } else {
                if (outFile.exists()) {
                    Object[] objArr = new Object[i3];
                    objArr[0] = assetKey;
                    ProgressLayout.setProgress(ProgressLayout.DOWNLOAD_MINECRAFT, 0, R.string.dl_library_sha_fail, objArr);
                }
                File file2 = outFile;
                String str = assetPath;
                $$Lambda$AsyncMinecraftDownloader$RX17s28XwKVHXLTHb6uAWno1bm4 r0 = r1;
                i = 1;
                objectsDir = objectsDir2;
                downloadedSize = downloadedSize2;
                $$Lambda$AsyncMinecraftDownloader$RX17s28XwKVHXLTHb6uAWno1bm4 r1 = new Runnable(assets, asset, objectsDir2, downloadedSize2, assetKey, outputDir, localInterrupt) {
                    public final /* synthetic */ JAssets f$1;
                    public final /* synthetic */ JAssetInfo f$2;
                    public final /* synthetic */ File f$3;
                    public final /* synthetic */ AtomicInteger f$4;
                    public final /* synthetic */ String f$5;
                    public final /* synthetic */ File f$6;
                    public final /* synthetic */ AtomicBoolean f$7;

                    {
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                        this.f$5 = r6;
                        this.f$6 = r7;
                        this.f$7 = r8;
                    }

                    public final void run() {
                        AsyncMinecraftDownloader.this.lambda$downloadAssets$4$AsyncMinecraftDownloader(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7);
                    }
                };
                executor.execute(r0);
            }
            i3 = i;
            assetsSizeBytes = assetsSizeBytes2;
            objectsDir2 = objectsDir;
            downloadedSize2 = downloadedSize;
            i2 = 0;
            jAssets = assets;
        }
        int i4 = i3;
        File file3 = objectsDir2;
        AtomicInteger downloadedSize3 = downloadedSize2;
        executor.shutdown();
        try {
            Log.i("AsyncMcDownloader", "Queue size: " + workQueue.size());
            while (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS) && !localInterrupt.get()) {
                Object[] objArr2 = new Object[i4];
                objArr2[0] = "assets";
                ProgressLayout.setProgress(ProgressLayout.DOWNLOAD_MINECRAFT, (int) Math.max((((float) downloadedSize3.get()) / ((float) assetsSizeBytes)) * 100.0f, 0.0f), R.string.mcl_launch_downloading, objArr2);
            }
            executor.shutdownNow();
            while (!executor.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            }
            Log.i("AsyncMcDownloader", "Fully shut down!");
        } catch (InterruptedException e) {
            Log.e("AsyncMcDownloader", e.toString());
        }
        Log.i("AsyncMcDownloader", "Assets end time: " + System.currentTimeMillis());
    }

    public /* synthetic */ void lambda$downloadAssets$4$AsyncMinecraftDownloader(JAssets assets, JAssetInfo asset, File objectsDir, AtomicInteger downloadedSize, String assetKey, File outputDir, AtomicBoolean localInterrupt) {
        try {
            if (!assets.mapToResources) {
                downloadAsset(asset, objectsDir, downloadedSize);
            } else {
                downloadAssetMapped(asset, assetKey, outputDir, downloadedSize);
            }
        } catch (IOException e) {
            Log.e("AsyncMcManager", e.toString());
            localInterrupt.set(true);
        }
    }

    public void downloadAsset(JAssetInfo asset, File objectsDir, final AtomicInteger downloadCounter) throws IOException {
        String assetPath = asset.hash.substring(0, 2) + "/" + asset.hash;
        DownloadUtils.downloadFileMonitored(MINECRAFT_RES + assetPath, new File(objectsDir, assetPath), getByteBuffer(), (Tools.DownloaderFeedback) new Tools.DownloaderFeedback() {
            int prevCurr;

            public void updateProgress(int curr, int max) {
                downloadCounter.addAndGet(curr - this.prevCurr);
                this.prevCurr = curr;
            }
        });
    }

    public void downloadAssetMapped(JAssetInfo asset, String assetName, File resDir, final AtomicInteger downloadCounter) throws IOException {
        DownloadUtils.downloadFileMonitored(MINECRAFT_RES + (asset.hash.substring(0, 2) + "/" + asset.hash), new File(resDir, "/" + assetName), getByteBuffer(), (Tools.DownloaderFeedback) new Tools.DownloaderFeedback() {
            int prevCurr;

            public void updateProgress(int curr, int max) {
                downloadCounter.addAndGet(curr - this.prevCurr);
                this.prevCurr = curr;
            }
        });
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007b A[Catch:{ all -> 0x0097 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007f A[Catch:{ all -> 0x0097 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void downloadLibrary(net.kdt.pojavlaunch.value.DependentLibrary r10, java.lang.String r11, java.io.File r12) throws java.lang.Throwable {
        /*
            r9 = this;
            r0 = 0
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r1 = r10.downloads
            if (r1 == 0) goto L_0x000b
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r1 = r10.downloads
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r1 = r1.artifact
            if (r1 != 0) goto L_0x003c
        L_0x000b:
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r1 = new net.kdt.pojavlaunch.value.MinecraftLibraryArtifact
            r1.<init>()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r10.url
            if (r3 != 0) goto L_0x001c
            java.lang.String r3 = "https://libraries.minecraft.net/"
            goto L_0x0026
        L_0x001c:
            java.lang.String r3 = r10.url
            java.lang.String r4 = "http://"
            java.lang.String r5 = "https://"
            java.lang.String r3 = r3.replace(r4, r5)
        L_0x0026:
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r11)
            java.lang.String r2 = r2.toString()
            r1.url = r2
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r2 = new net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads
            r2.<init>(r1)
            r10.downloads = r2
            r0 = 1
        L_0x003c:
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r1 = r10.downloads     // Catch:{ all -> 0x0097 }
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r1 = r1.artifact     // Catch:{ all -> 0x0097 }
            java.lang.String r1 = r1.url     // Catch:{ all -> 0x0097 }
            r2 = 0
            r3 = 0
        L_0x0044:
            if (r2 != 0) goto L_0x0096
            int r4 = r3 + 1
            byte r3 = (byte) r4     // Catch:{ all -> 0x0097 }
            r4 = 5
            if (r3 > r4) goto L_0x008e
            byte[] r4 = r9.getByteBuffer()     // Catch:{ all -> 0x0097 }
            net.kdt.pojavlaunch.tasks.-$$Lambda$AsyncMinecraftDownloader$c9FFEOszBJ1qO39lOky6hXXhViY r5 = new net.kdt.pojavlaunch.tasks.-$$Lambda$AsyncMinecraftDownloader$c9FFEOszBJ1qO39lOky6hXXhViY     // Catch:{ all -> 0x0097 }
            r5.<init>(r12)     // Catch:{ all -> 0x0097 }
            net.kdt.pojavlaunch.utils.DownloadUtils.downloadFileMonitored((java.lang.String) r1, (java.io.File) r12, (byte[]) r4, (net.kdt.pojavlaunch.Tools.DownloaderFeedback) r5)     // Catch:{ all -> 0x0097 }
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r4 = r10.downloads     // Catch:{ all -> 0x0097 }
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r4 = r4.artifact     // Catch:{ all -> 0x0097 }
            java.lang.String r4 = r4.sha1     // Catch:{ all -> 0x0097 }
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x0075
            boolean r4 = net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_CHECK_LIBRARY_SHA     // Catch:{ all -> 0x0097 }
            if (r4 != 0) goto L_0x0075
            net.kdt.pojavlaunch.value.DependentLibrary$LibraryDownloads r4 = r10.downloads     // Catch:{ all -> 0x0097 }
            net.kdt.pojavlaunch.value.MinecraftLibraryArtifact r4 = r4.artifact     // Catch:{ all -> 0x0097 }
            java.lang.String r4 = r4.sha1     // Catch:{ all -> 0x0097 }
            boolean r4 = net.kdt.pojavlaunch.Tools.compareSHA1(r12, r4)     // Catch:{ all -> 0x0097 }
            if (r4 == 0) goto L_0x0073
            goto L_0x0075
        L_0x0073:
            r4 = r5
            goto L_0x0076
        L_0x0075:
            r4 = r6
        L_0x0076:
            r2 = r4
            java.lang.String r4 = "download_minecraft"
            if (r2 == 0) goto L_0x007f
            r7 = 2131755068(0x7f10003c, float:1.9141005E38)
            goto L_0x0082
        L_0x007f:
            r7 = 2131755069(0x7f10003d, float:1.9141007E38)
        L_0x0082:
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0097 }
            java.lang.String r8 = r12.getName()     // Catch:{ all -> 0x0097 }
            r6[r5] = r8     // Catch:{ all -> 0x0097 }
            com.kdt.mcgui.ProgressLayout.setProgress(r4, r5, r7, r6)     // Catch:{ all -> 0x0097 }
            goto L_0x0044
        L_0x008e:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x0097 }
            java.lang.String r5 = "Library download failed after 5 retries"
            r4.<init>(r5)     // Catch:{ all -> 0x0097 }
            throw r4     // Catch:{ all -> 0x0097 }
        L_0x0096:
            goto L_0x00a6
        L_0x0097:
            r1 = move-exception
            java.lang.String r2 = r1.toString()
            java.lang.String r3 = "AsyncMcDownloader"
            android.util.Log.e(r3, r2, r1)
            if (r0 == 0) goto L_0x00a7
            r1.printStackTrace()
        L_0x00a6:
            return
        L_0x00a7:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.tasks.AsyncMinecraftDownloader.downloadLibrary(net.kdt.pojavlaunch.value.DependentLibrary, java.lang.String, java.io.File):void");
    }

    public JAssets downloadIndex(JMinecraftVersionList.Version version, File output) throws IOException {
        if (!output.exists()) {
            output.getParentFile().mkdirs();
            DownloadUtils.downloadFile(version.assetIndex != null ? version.assetIndex.url : "https://s3.amazonaws.com/Minecraft.Download/indexes/" + version.assets + ".json", output);
        }
        return (JAssets) Tools.GLOBAL_GSON.fromJson(Tools.read(output.getAbsolutePath()), JAssets.class);
    }

    public static String normalizeVersionId(String versionString) {
        JMinecraftVersionList versionList = (JMinecraftVersionList) ExtraCore.getValue(ExtraConstants.RELEASE_TABLE);
        if (versionList == null || versionList.versions == null) {
            return versionString;
        }
        if ("latest-release".equals(versionString)) {
            versionString = versionList.latest.get("release");
        }
        if ("latest-snapshot".equals(versionString)) {
            return versionList.latest.get(JMinecraftVersionList.TYPE_SNAPSHOT);
        }
        return versionString;
    }

    public static JMinecraftVersionList.Version getListedVersion(String normalizedVersionString) {
        JMinecraftVersionList versionList = (JMinecraftVersionList) ExtraCore.getValue(ExtraConstants.RELEASE_TABLE);
        if (versionList == null || versionList.versions == null) {
            return null;
        }
        for (JMinecraftVersionList.Version version : versionList.versions) {
            if (version.f17id.equals(normalizedVersionString)) {
                return version;
            }
        }
        return null;
    }

    private byte[] getByteBuffer() {
        byte[] buffer = this.mThreadBuffers.get(Thread.currentThread());
        if (buffer != null) {
            return buffer;
        }
        byte[] buffer2 = new byte[8192];
        this.mThreadBuffers.put(Thread.currentThread(), buffer2);
        return buffer2;
    }
}
