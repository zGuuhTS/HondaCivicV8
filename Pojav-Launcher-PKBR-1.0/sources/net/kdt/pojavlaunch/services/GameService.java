package net.kdt.pojavlaunch.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.p000br.pixelmonbrasil.debug.R;
import java.lang.ref.WeakReference;
import net.kdt.pojavlaunch.Tools;

public class GameService extends Service {
    private static final WeakReference<Service> sGameService = new WeakReference<>((Object) null);

    public static void startService(Context context) {
        ContextCompat.startForegroundService(context, new Intent(context, GameService.class));
    }

    public static void stopService() {
        Service gameService = (Service) sGameService.get();
        if (gameService != null) {
            gameService.stopSelf();
        }
    }

    public void onCreate() {
        Tools.buildNotificationChannel(getApplicationContext());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null || !intent.getBooleanExtra("kill", false)) {
            Intent killIntent = new Intent(getApplicationContext(), GameService.class);
            killIntent.putExtra("kill", true);
            startForeground(2, new NotificationCompat.Builder((Context) this, "channel_id").setContentTitle(getString(R.string.lazy_service_default_title)).setContentText(getString(R.string.notification_game_runs)).addAction(17301560, getString(R.string.notification_terminate), PendingIntent.getService(this, 0, killIntent, Build.VERSION.SDK_INT >= 23 ? 67108864 : 0)).setSmallIcon((int) R.drawable.notif_icon).setNotificationSilent().build());
            return 2;
        }
        stopSelf();
        Process.killProcess(Process.myPid());
        return 2;
    }

    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        Process.killProcess(Process.myPid());
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
