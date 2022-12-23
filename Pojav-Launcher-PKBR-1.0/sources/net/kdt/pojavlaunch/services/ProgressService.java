package net.kdt.pojavlaunch.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.progresskeeper.ProgressKeeper;
import net.kdt.pojavlaunch.progresskeeper.TaskCountListener;

public class ProgressService extends Service implements TaskCountListener {
    private NotificationCompat.Builder mNotificationBuilder;
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private NotificationManagerCompat notificationManagerCompat;

    public static void startService(Context context) {
        ContextCompat.startForegroundService(context, new Intent(context, ProgressService.class));
    }

    public void onCreate() {
        Tools.buildNotificationChannel(getApplicationContext());
        this.notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        Intent killIntent = new Intent(getApplicationContext(), ProgressService.class);
        killIntent.putExtra("kill", true);
        this.mNotificationBuilder = new NotificationCompat.Builder((Context) this, "channel_id").setContentTitle(getString(R.string.lazy_service_default_title)).addAction(17301560, getString(R.string.notification_terminate), PendingIntent.getService(this, 0, killIntent, Build.VERSION.SDK_INT >= 23 ? 67108864 : 0)).setSmallIcon((int) R.drawable.notif_icon).setNotificationSilent();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || !intent.getBooleanExtra("kill", false)) {
            Log.d("ProgressService", "Started!");
            this.mNotificationBuilder.setContentText(getString(R.string.progresslayout_tasks_in_progress, new Object[]{Integer.valueOf(ProgressKeeper.getTaskCount())}));
            startForeground(1, this.mNotificationBuilder.build());
            if (ProgressKeeper.getTaskCount() < 1) {
                stopSelf();
            } else {
                ProgressKeeper.addTaskCountListener(this, false);
            }
            return 2;
        }
        stopSelf();
        Process.killProcess(Process.myPid());
        return 2;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        ProgressKeeper.removeTaskCountListener(this);
    }

    public void onUpdateTaskCount(int taskCount) {
        this.mainThreadHandler.post(new Runnable(taskCount) {
            public final /* synthetic */ int f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                ProgressService.this.lambda$onUpdateTaskCount$0$ProgressService(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$onUpdateTaskCount$0$ProgressService(int taskCount) {
        if (taskCount > 0) {
            this.mNotificationBuilder.setContentText(getString(R.string.progresslayout_tasks_in_progress, new Object[]{Integer.valueOf(taskCount)}));
            this.notificationManagerCompat.notify(1, this.mNotificationBuilder.build());
            return;
        }
        stopSelf();
    }
}
