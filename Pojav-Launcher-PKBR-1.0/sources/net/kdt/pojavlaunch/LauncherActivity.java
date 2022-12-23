package net.kdt.pojavlaunch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kdt.mcgui.ProgressLayout;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import net.kdt.pojavlaunch.extra.ExtraListener;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.progresskeeper.ProgressKeeper;
import net.kdt.pojavlaunch.services.ProgressServiceKeeper;
import net.kdt.pojavlaunch.tasks.AsyncMinecraftDownloader;
import net.kdt.pojavlaunch.tasks.Downloader;
import net.kdt.pojavlaunch.value.FilesValues;
import org.apache.commons.p012io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import top.defaults.checkerboarddrawable.BuildConfig;

public class LauncherActivity extends BaseActivity {
    private final int REQUEST_STORAGE_REQUEST_CODE = 1;
    Runnable checkUpdate = new Runnable() {
        public final void run() {
            LauncherActivity.this.lambda$new$13$LauncherActivity();
        }
    };
    private final ExtraListener<String> mBackPreferenceListener = new ExtraListener() {
        public final boolean onValueSet(String str, Object obj) {
            return LauncherActivity.this.lambda$new$0$LauncherActivity(str, (String) obj);
        }
    };
    private Handler mHandler = new Handler();
    private final ExtraListener<Boolean> mLaunchGameListener = new ExtraListener() {
        public final boolean onValueSet(String str, Object obj) {
            return LauncherActivity.this.lambda$new$6$LauncherActivity(str, (Boolean) obj);
        }
    };
    private final Object mLockStoragePerm = new Object();
    private ProgressLayout mProgressLayout;
    private ProgressServiceKeeper mProgressServiceKeeper;
    private AlertDialog ramDialog;

    public /* synthetic */ boolean lambda$new$0$LauncherActivity(String key, String value) {
        if (!value.equals("true")) {
            return false;
        }
        onBackPressed();
        return false;
    }

    public /* synthetic */ boolean lambda$new$6$LauncherActivity(String key, Boolean value) {
        if (!getApplicationContext().getPackageName().equals("com.br.pixelmonbrasil") && !getApplicationContext().getPackageName().equals(BuildConfig.APPLICATION_ID)) {
            new AlertDialog.Builder(this).setMessage(HtmlCompat.fromHtml("Esse launcher é de fonte não conhecida.<br>Baixe oficialmente pelo nosso " + "<a href=\"https://discord.gg/pxbr\">discord</a>", 0)).show();
        } else if (!isInternetAvailable(this)) {
            new AlertDialog.Builder(this).setTitle("Ops.").setMessage("Você está sem conexão com a internet.").setPositiveButton("Ok", (DialogInterface.OnClickListener) null).show();
            return false;
        } else if (PXBRProfile.getCurrentProfileContent(getBaseContext(), (String) null) == null) {
            startActivity(new Intent(getBaseContext(), ActivityAccounts.class));
            return false;
        } else if (Tools.getTotalDeviceMemory(getBaseContext()) < 4000) {
            runOnUiThread(new Runnable() {
                public final void run() {
                    LauncherActivity.this.lambda$null$5$LauncherActivity();
                }
            });
        } else if (this.mProgressLayout.hasProcesses()) {
            Toast.makeText(this, R.string.tasks_ongoing, 1).show();
            return false;
        } else {
            Downloader dl = new Downloader(getBaseContext(), Tools.DIR_GAME_NEW + "/files.json", "https://tarikbr.github.io/test/files.json", true);
            dl.setDownloaderCallback(new Downloader.DownloaderCallback() {
                public final void onFinish() {
                    LauncherActivity.this.Download();
                }
            });
            dl.start();
        }
        return false;
    }

    public /* synthetic */ void lambda$null$5$LauncherActivity() {
        androidx.appcompat.app.AlertDialog create = new AlertDialog.Builder(this).setMessage((CharSequence) HtmlCompat.fromHtml("Seu dispositivo possui Memória RAM total abaixo do recomendado para conseguir rodar o Pixelmon Brasil<br>Caso tente rodar mesmo assim, pode ocorrer crashes e até mesmo não abrir o jogo.", 0)).setNegativeButton((CharSequence) "Cancelar", (DialogInterface.OnClickListener) $$Lambda$LauncherActivity$Ki4i2U0xxMzuXf341M2QyhmF7LY.INSTANCE).setOnCancelListener($$Lambda$LauncherActivity$T2d7mI1ZQ6GFnC5M3vIUakQTgOY.INSTANCE).setOnDismissListener(new DialogInterface.OnDismissListener() {
            public final void onDismiss(DialogInterface dialogInterface) {
                LauncherActivity.this.lambda$null$3$LauncherActivity(dialogInterface);
            }
        }).setPositiveButton((CharSequence) "Continuar", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                LauncherActivity.this.lambda$null$4$LauncherActivity(dialogInterface, i);
            }
        }).create();
        this.ramDialog = create;
        create.show();
    }

    static /* synthetic */ void lambda$null$1(DialogInterface dialogInterface, int i) {
    }

    static /* synthetic */ void lambda$null$2(DialogInterface i) {
    }

    public /* synthetic */ void lambda$null$3$LauncherActivity(DialogInterface dialogInterface) {
        this.ramDialog.dismiss();
    }

    public /* synthetic */ void lambda$null$4$LauncherActivity(DialogInterface dialogInterface, int i) {
        if (this.mProgressLayout.hasProcesses()) {
            Toast.makeText(getBaseContext(), R.string.tasks_ongoing, 1).show();
            return;
        }
        Downloader dl = new Downloader(getBaseContext(), Tools.DIR_GAME_NEW + "/files.json", "https://tarikbr.github.io/test/files.json", true);
        dl.setDownloaderCallback(new Downloader.DownloaderCallback() {
            public final void onFinish() {
                LauncherActivity.this.Download();
            }
        });
        dl.start();
    }

    private static boolean isInternetAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }

    /* access modifiers changed from: private */
    public void Download() {
        try {
            for (FilesValues file : (List) new Gson().fromJson((Reader) Files.newBufferedReader(Paths.get(Tools.DIR_GAME_NEW + "/files.json", new String[0])), new TypeToken<List<FilesValues>>() {
            }.getType())) {
                Tools.downloaded = false;
                Downloader dl = new Downloader(getBaseContext(), Tools.DIR_GAME_NEW + file.getOutput(), file.getLink(), file.getOverwrite());
                dl.setDownloaderCallback($$Lambda$LauncherActivity$RYcLVXEGtiNjDd0a9JyPSRgVk5g.INSTANCE);
                dl.start();
                do {
                } while (!Tools.downloaded.booleanValue());
                if (Tools.stopDownload.booleanValue()) {
                    return;
                }
            }
            String normalizedVersionId = AsyncMinecraftDownloader.normalizeVersionId("1.12.2-forge-14.23.5.2860");
            new AsyncMinecraftDownloader(this, AsyncMinecraftDownloader.getListedVersion(normalizedVersionId), normalizedVersionId, new AsyncMinecraftDownloader.DoneListener(normalizedVersionId) {
                public final /* synthetic */ String f$1;

                {
                    this.f$1 = r2;
                }

                public final void onDownloadDone() {
                    LauncherActivity.this.lambda$Download$9$LauncherActivity(this.f$1);
                }
            });
        } catch (IOException e) {
            Tools.showError(this, e);
        }
    }

    public /* synthetic */ void lambda$Download$9$LauncherActivity(String normalizedVersionId) {
        runOnUiThread(new Runnable(normalizedVersionId) {
            public final /* synthetic */ String f$1;

            {
                this.f$1 = r2;
            }

            public final void run() {
                LauncherActivity.this.lambda$null$8$LauncherActivity(this.f$1);
            }
        });
    }

    public /* synthetic */ void lambda$null$8$LauncherActivity(String normalizedVersionId) {
        Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
        mainIntent.putExtra(MainActivity.INTENT_MINECRAFT_VERSION, normalizedVersionId);
        mainIntent.addFlags(536870912);
        startActivity(mainIntent);
        finish();
        Process.killProcess(Process.myPid());
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getSharedPreferences("pojav_extract", 0).getBoolean("welcomeDialogShown", false)) {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
        setContentView((int) R.layout.activity_pojav_launcher);
        getWindow().setBackgroundDrawable((Drawable) null);
        this.mProgressLayout = (ProgressLayout) findViewById(R.id.progress_layout);
        ProgressServiceKeeper progressServiceKeeper = new ProgressServiceKeeper(this);
        this.mProgressServiceKeeper = progressServiceKeeper;
        ProgressKeeper.addTaskCountListener(progressServiceKeeper);
        askForStoragePermission();
        ProgressKeeper.addTaskCountListener(this.mProgressLayout);
        ExtraCore.addExtraListener(ExtraConstants.BACK_PREFERENCE, this.mBackPreferenceListener);
        ExtraCore.addExtraListener(ExtraConstants.LAUNCH_GAME, this.mLaunchGameListener);
        this.mProgressLayout.observe(ProgressLayout.DOWNLOAD_MINECRAFT);
        this.mProgressLayout.observe(ProgressLayout.UNPACK_RUNTIME);
        this.mProgressLayout.observe(ProgressLayout.INSTALL_MOD);
        this.mProgressLayout.observe(ProgressLayout.AUTHENTICATE_MICROSOFT);
        if (!Tools.checked.booleanValue()) {
            this.mHandler.postDelayed(this.checkUpdate, 2000);
        }
    }

    public /* synthetic */ void lambda$new$13$LauncherActivity() {
        new Thread(new Runnable() {
            public final void run() {
                LauncherActivity.this.lambda$null$12$LauncherActivity();
            }
        }).start();
    }

    public /* synthetic */ void lambda$null$12$LauncherActivity() {
        try {
            String vName = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            JSONObject json = new JSONObject(IOUtils.toString(new URL("https://api.github.com/repos/BVRPixelmon/Launcher-Releases/releases/latest"), StandardCharsets.UTF_8));
            if (Tools.checked.booleanValue()) {
                return;
            }
            if (!vName.equals(json.get("tag_name").toString()) || !vName.equals(json.get("tag_name").toString().replace("v", BuildConfig.FLAVOR))) {
                JSONArray jsonAssets = (JSONArray) json.get("assets");
                JSONObject asset = null;
                int i = 0;
                while (true) {
                    if (i >= jsonAssets.length()) {
                        break;
                    } else if (jsonAssets.getJSONObject(i).getString("name").equals("latest-mac.yml")) {
                        asset = jsonAssets.getJSONObject(i);
                        break;
                    } else {
                        i++;
                    }
                }
                if (asset != null) {
                    JSONObject jSONObject = asset;
                    runOnUiThread(new Runnable() {
                        public final void run() {
                            LauncherActivity.this.lambda$null$11$LauncherActivity();
                        }
                    });
                }
            }
        } catch (PackageManager.NameNotFoundException | IOException | JSONException e) {
            Tools.showError(this, e, false);
        }
    }

    public /* synthetic */ void lambda$null$11$LauncherActivity() {
        new AlertDialog.Builder(this).setTitle("Atualização!").setMessage("Uma nova versão do launcher está disponível!").setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                LauncherActivity.this.lambda$null$10$LauncherActivity(dialogInterface, i);
            }
        }).setNeutralButton("Lembrar Depois", (DialogInterface.OnClickListener) null).show();
        Tools.checked = true;
    }

    public /* synthetic */ void lambda$null$10$LauncherActivity(DialogInterface x, int y) {
        startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("https://www.youtube.com/watch?v=hPr-Yc92qaY")));
    }

    public boolean setFullscreen() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        ProgressLayout progressLayout = this.mProgressLayout;
        if (progressLayout != null) {
            progressLayout.cleanUpObservers();
            ProgressKeeper.removeTaskCountListener(this.mProgressLayout);
            ProgressKeeper.removeTaskCountListener(this.mProgressServiceKeeper);
            ExtraCore.removeExtraListenerFromValue(ExtraConstants.BACK_PREFERENCE, this.mBackPreferenceListener);
            ExtraCore.removeExtraListenerFromValue(ExtraConstants.LAUNCH_GAME, this.mLaunchGameListener);
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 2048 && data != null) {
            Tools.installRuntimeFromUri(this, data.getData());
        }
    }

    public void onAttachedToWindow() {
        LauncherPreferences.computeNotchSize(this);
    }

    private void askForStoragePermission() {
        int revokeCount = 0;
        while (Build.VERSION.SDK_INT >= 23 && Build.VERSION.SDK_INT < 29 && !isStorageAllowed()) {
            revokeCount++;
            if (revokeCount >= 3) {
                try {
                    Toast.makeText(this, R.string.toast_permission_denied, 1).show();
                    finish();
                } catch (InterruptedException e) {
                    Log.e("LauncherActivity", e.toString());
                }
            }
            requestStoragePermission();
            synchronized (this.mLockStoragePerm) {
                this.mLockStoragePerm.wait();
            }
        }
    }

    private boolean isStorageAllowed() {
        return ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            synchronized (this.mLockStoragePerm) {
                this.mLockStoragePerm.notifyAll();
            }
        }
    }
}
