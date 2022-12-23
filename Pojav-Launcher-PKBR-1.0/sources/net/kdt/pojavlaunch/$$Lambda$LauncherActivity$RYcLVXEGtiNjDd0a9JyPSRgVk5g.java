package net.kdt.pojavlaunch;

import net.kdt.pojavlaunch.tasks.Downloader;

/* renamed from: net.kdt.pojavlaunch.-$$Lambda$LauncherActivity$RYcLVXEGtiNjDd0a9JyPSRgVk5g  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$LauncherActivity$RYcLVXEGtiNjDd0a9JyPSRgVk5g implements Downloader.DownloaderCallback {
    public static final /* synthetic */ $$Lambda$LauncherActivity$RYcLVXEGtiNjDd0a9JyPSRgVk5g INSTANCE = new $$Lambda$LauncherActivity$RYcLVXEGtiNjDd0a9JyPSRgVk5g();

    private /* synthetic */ $$Lambda$LauncherActivity$RYcLVXEGtiNjDd0a9JyPSRgVk5g() {
    }

    public final void onFinish() {
        Tools.downloaded = true;
    }
}
