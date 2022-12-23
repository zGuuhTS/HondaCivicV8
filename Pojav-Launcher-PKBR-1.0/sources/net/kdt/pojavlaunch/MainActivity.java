package net.kdt.pojavlaunch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.drawerlayout.widget.DrawerLayout;
import com.kdt.LoggerView;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import net.kdt.pojavlaunch.JMinecraftVersionList;
import net.kdt.pojavlaunch.MinecraftGLSurface;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.ControlDrawerData;
import net.kdt.pojavlaunch.customcontrols.ControlLayout;
import net.kdt.pojavlaunch.customcontrols.CustomControls;
import net.kdt.pojavlaunch.customcontrols.keyboard.LwjglCharSender;
import net.kdt.pojavlaunch.customcontrols.keyboard.TouchCharInput;
import net.kdt.pojavlaunch.multirt.MultiRTUtils;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.services.GameService;
import net.kdt.pojavlaunch.utils.JREUtils;
import net.kdt.pojavlaunch.utils.MCOptionUtils;
import net.kdt.pojavlaunch.value.MinecraftAccount;
import net.kdt.pojavlaunch.value.launcherprofiles.LauncherProfiles;
import net.kdt.pojavlaunch.value.launcherprofiles.MinecraftProfile;
import org.lwjgl.glfw.CallbackBridge;
import top.defaults.checkerboarddrawable.BuildConfig;

public class MainActivity extends BaseActivity {
    public static volatile ClipboardManager GLOBAL_CLIPBOARD = null;
    public static final String INTENT_MINECRAFT_VERSION = "intent_version";
    public static volatile boolean isInputStackCall;
    public static ControlLayout mControlLayout;
    public static TouchCharInput touchCharInput;
    private static Touchpad touchpad;
    private DrawerLayout drawerLayout;
    private ArrayAdapter<String> gameActionArrayAdapter;
    private AdapterView.OnItemClickListener gameActionClickListener;
    public ArrayAdapter<String> ingameControlsEditorArrayAdapter;
    public AdapterView.OnItemClickListener ingameControlsEditorListener;
    boolean isInEditor;
    private LoggerView loggerView;
    private View mDrawerPullButton;
    private boolean mIsResuming = false;
    MinecraftAccount mProfile;
    protected volatile JMinecraftVersionList.Version mVersionInfo;
    private MinecraftGLSurface minecraftGLView;
    MinecraftProfile minecraftProfile;
    private ListView navDrawer;
    public float scaleFactor = 1.0f;
    int tmpMouseSpeed;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mProfile = PXBRProfile.getCurrentProfileContent(this, (String) null);
        MinecraftProfile minecraftProfile2 = new MinecraftProfile();
        this.minecraftProfile = minecraftProfile2;
        minecraftProfile2.lastVersionId = "1.12.2-forge-14.23.5.2860";
        this.minecraftProfile.name = "1.12.2-forge-14.23.5.2860";
        this.minecraftProfile.controlFile = null;
        this.minecraftProfile.javaArgs = null;
        this.minecraftProfile.gameDir = null;
        MCOptionUtils.load(Tools.getGameDirPath(this.minecraftProfile));
        GameService.startService(this);
        initLayout(R.layout.activity_basemain);
        getWindow().setBackgroundDrawable((Drawable) null);
        if (LauncherPreferences.PREF_USE_ALTERNATE_SURFACE) {
            getWindow().setBackgroundDrawable((Drawable) null);
        }
        if (Build.VERSION.SDK_INT >= 24) {
            getWindow().setSustainedPerformanceMode(LauncherPreferences.PREF_SUSTAINED_PERFORMANCE);
        }
        this.ingameControlsEditorArrayAdapter = new ArrayAdapter<>(this, 17367043, getResources().getStringArray(R.array.menu_customcontrol));
        this.ingameControlsEditorListener = $$Lambda$MainActivity$YZAFHuEDZLB4UOhf7MX9S0LNDik.INSTANCE;
        MCOptionUtils.addMCOptionListener($$Lambda$dkzNOewH4D09c5DbiH16HPXA8Xo.INSTANCE);
        mControlLayout.setModifiable(false);
    }

    static /* synthetic */ void lambda$onCreate$0(AdapterView parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mControlLayout.addControlButton(new ControlData("New"));
                return;
            case 1:
                mControlLayout.addDrawer(new ControlDrawerData());
                return;
            case 2:
                CustomControlsActivity.load(mControlLayout);
                return;
            case 3:
                CustomControlsActivity.save(true, mControlLayout);
                return;
            case 4:
                CustomControlsActivity.dialogSelectDefaultCtrl(mControlLayout);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void initLayout(int resId) {
        setContentView(resId);
        bindValues();
        this.mDrawerPullButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MainActivity.this.lambda$initLayout$1$MainActivity(view);
            }
        });
        this.drawerLayout.setDrawerLockMode(1);
        try {
            Logger.getInstance().reset();
            GLOBAL_CLIPBOARD = (ClipboardManager) getSystemService("clipboard");
            touchCharInput.setCharacterSender(new LwjglCharSender());
            String runtime = LauncherPreferences.PREF_DEFAULT_RUNTIME;
            if (this.minecraftProfile.javaDir != null && this.minecraftProfile.javaDir.startsWith(Tools.LAUNCHERPROFILES_RTPREFIX)) {
                String runtimeName = this.minecraftProfile.javaDir.substring(Tools.LAUNCHERPROFILES_RTPREFIX.length());
                if (MultiRTUtils.forceReread(runtimeName).versionString != null) {
                    runtime = runtimeName;
                }
            }
            if (this.minecraftProfile.pojavRendererName != null) {
                Log.i("RdrDebug", "__P_renderer=" + this.minecraftProfile.pojavRendererName);
                Tools.LOCAL_RENDERER = this.minecraftProfile.pojavRendererName;
            }
            setTitle("Minecraft " + this.minecraftProfile.lastVersionId);
            MultiRTUtils.setRuntimeNamed(runtime);
            String version = getIntent().getStringExtra(INTENT_MINECRAFT_VERSION);
            this.mVersionInfo = Tools.getVersionInfo(version == null ? this.minecraftProfile.lastVersionId : version);
            isInputStackCall = this.mVersionInfo.arguments != null;
            Tools.getDisplayMetrics(this);
            CallbackBridge.windowWidth = Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.widthPixels, this.scaleFactor);
            CallbackBridge.windowHeight = Tools.getDisplayFriendlyRes(Tools.currentDisplayMetrics.heightPixels, this.scaleFactor);
            this.gameActionArrayAdapter = new ArrayAdapter<>(this, 17367043, getResources().getStringArray(R.array.menu_ingame));
            this.gameActionClickListener = new AdapterView.OnItemClickListener() {
                public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                    MainActivity.this.lambda$initLayout$2$MainActivity(adapterView, view, i, j);
                }
            };
            this.navDrawer.setAdapter(this.gameActionArrayAdapter);
            this.navDrawer.setOnItemClickListener(this.gameActionClickListener);
            this.drawerLayout.closeDrawers();
            this.minecraftGLView.setSurfaceReadyListener(new MinecraftGLSurface.SurfaceReadyListener() {
                public final void isReady() {
                    MainActivity.this.lambda$initLayout$3$MainActivity();
                }
            });
            this.minecraftGLView.start();
        } catch (Throwable e) {
            Tools.showError(this, e, true);
        }
    }

    public /* synthetic */ void lambda$initLayout$1$MainActivity(View v) {
        this.drawerLayout.openDrawer((View) this.navDrawer);
    }

    public /* synthetic */ void lambda$initLayout$2$MainActivity(AdapterView parent, View view, int position, long id) {
        switch (position) {
            case 0:
                dialogForceClose(this);
                break;
            case 1:
                openLogOutput();
                break;
            case 2:
                this.minecraftGLView.togglepointerDebugging();
                break;
            case 3:
                dialogSendCustomKey();
                break;
            case 4:
                adjustMouseSpeedLive();
                break;
            case 5:
                openCustomControls();
                break;
        }
        this.drawerLayout.closeDrawers();
    }

    public /* synthetic */ void lambda$initLayout$3$MainActivity() {
        try {
            if (LauncherPreferences.PREF_VIRTUAL_MOUSE_START) {
                touchpad.switchState();
            }
            runCraft();
        } catch (Throwable e) {
            Tools.showError(getApplicationContext(), e, true);
        }
    }

    private void loadControls() {
        try {
            mControlLayout.loadLayout(this.minecraftProfile.controlFile == null ? LauncherPreferences.PREF_DEFAULTCTRL_PATH : Tools.CTRLMAP_PATH + "/" + this.minecraftProfile.controlFile);
        } catch (IOException e) {
            try {
                Log.w("MainActivity", "Unable to load the control file, loading the default now", e);
                mControlLayout.loadLayout(Tools.CTRLDEF_FILE);
            } catch (IOException ioException) {
                Tools.showError(this, ioException);
            }
        } catch (Throwable th) {
            Tools.showError(this, th);
        }
        mControlLayout.toggleControlVisible();
    }

    public void onAttachedToWindow() {
        LauncherPreferences.computeNotchSize(this);
        loadControls();
    }

    private void bindValues() {
        mControlLayout = (ControlLayout) findViewById(R.id.main_control_layout);
        this.minecraftGLView = (MinecraftGLSurface) findViewById(R.id.main_game_render_view);
        touchpad = (Touchpad) findViewById(R.id.main_touchpad);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_options);
        this.navDrawer = (ListView) findViewById(R.id.main_navigation_view);
        this.loggerView = (LoggerView) findViewById(R.id.mainLoggerView);
        mControlLayout = (ControlLayout) findViewById(R.id.main_control_layout);
        touchCharInput = (TouchCharInput) findViewById(R.id.mainTouchCharInput);
        this.mDrawerPullButton = findViewById(R.id.drawer_button);
    }

    public void onResume() {
        super.onResume();
        this.mIsResuming = true;
        getWindow().getDecorView().setSystemUiVisibility(2);
        CallbackBridge.nativeSetWindowAttrib(LwjglGlfwKeycode.GLFW_HOVERED, 1);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        if (CallbackBridge.isGrabbing()) {
            CallbackBridge.sendKeyPress(256);
        }
        CallbackBridge.nativeSetWindowAttrib(LwjglGlfwKeycode.GLFW_HOVERED, 0);
        this.mIsResuming = false;
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        CallbackBridge.nativeSetWindowAttrib(LwjglGlfwKeycode.GLFW_VISIBLE, 1);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        CallbackBridge.nativeSetWindowAttrib(LwjglGlfwKeycode.GLFW_VISIBLE, 0);
        super.onStop();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Tools.updateWindowSize(this);
        this.minecraftGLView.refreshSize();
        runOnUiThread($$Lambda$MainActivity$U74faPxdUfV6EEHC9Kgh4tWzfQ.INSTANCE);
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        if (this.minecraftGLView != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    MainActivity.this.lambda$onPostResume$5$MainActivity();
                }
            }, 500);
        }
    }

    public /* synthetic */ void lambda$onPostResume$5$MainActivity() {
        this.minecraftGLView.refreshSize();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            LauncherPreferences.loadPreferences(getApplicationContext());
            try {
                mControlLayout.loadLayout(LauncherPreferences.PREF_DEFAULTCTRL_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fullyExit() {
        Process.killProcess(Process.myPid());
    }

    public static boolean isAndroid8OrHigher() {
        return Build.VERSION.SDK_INT >= 26;
    }

    private void runCraft() throws Throwable {
        if (Tools.LOCAL_RENDERER == null) {
            Tools.LOCAL_RENDERER = LauncherPreferences.PREF_RENDERER;
        }
        Logger.getInstance().appendToLog("--------- beginning with launcher debug");
        Logger.getInstance().appendToLog("Info: Launcher version: 0.1");
        if (Tools.LOCAL_RENDERER.equals("vulkan_zink")) {
            checkVulkanZinkIsSupported();
        }
        checkLWJGL3Installed();
        JREUtils.jreReleaseList = JREUtils.readJREReleaseProperties();
        Logger.getInstance().appendToLog("Architecture: " + Architecture.archAsString(Tools.DEVICE_ARCHITECTURE));
        checkJavaArgsIsLaunchable(JREUtils.jreReleaseList.get("JAVA_VERSION"));
        Logger.getInstance().appendToLog("Info: Selected Minecraft version: " + this.mVersionInfo.f17id + ((this.mVersionInfo.inheritsFrom == null || this.mVersionInfo.inheritsFrom.equals(this.mVersionInfo.f17id)) ? BuildConfig.FLAVOR : " (" + this.mVersionInfo.inheritsFrom + ")"));
        JREUtils.redirectAndPrintJRELog();
        LauncherProfiles.update();
        Tools.launchMinecraft(this, this.mProfile, this.minecraftProfile);
    }

    private void checkJavaArgsIsLaunchable(String jreVersion) throws Throwable {
        Logger.getInstance().appendToLog("Info: Custom Java arguments: \"" + LauncherPreferences.PREF_CUSTOM_JAVA_ARGS + "\"");
    }

    private void checkLWJGL3Installed() {
        File lwjgl3dir = new File(Tools.DIR_GAME_HOME, "lwjgl3");
        if (!lwjgl3dir.exists() || lwjgl3dir.isFile() || lwjgl3dir.list().length == 0) {
            Logger.getInstance().appendToLog("Error: LWJGL3 was not installed!");
            throw new RuntimeException(getString(R.string.mcn_check_fail_lwjgl));
        } else {
            Logger.getInstance().appendToLog("Info: LWJGL3 directory: " + Arrays.toString(lwjgl3dir.list()));
        }
    }

    private void checkVulkanZinkIsSupported() {
        if (Tools.DEVICE_ARCHITECTURE == Architecture.ARCH_X86 || Build.VERSION.SDK_INT < 25 || !getPackageManager().hasSystemFeature("android.hardware.vulkan.level") || !getPackageManager().hasSystemFeature("android.hardware.vulkan.version")) {
            Logger.getInstance().appendToLog("Error: Vulkan Zink renderer is not supported!");
            throw new RuntimeException(getString(R.string.mcn_check_fail_vulkan_support));
        }
    }

    public void printStream(InputStream stream) {
        try {
            BufferedReader buffStream = new BufferedReader(new InputStreamReader(stream));
            while (true) {
                String readLine = buffStream.readLine();
                String line = readLine;
                if (readLine != null) {
                    Logger.getInstance().appendToLog(line);
                } else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String fromArray(List<String> arr) {
        StringBuilder s = new StringBuilder();
        for (String exec : arr) {
            s.append(" ").append(exec);
        }
        return s.toString();
    }

    private void dialogSendCustomKey() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.control_customkey);
        dialog.setItems(EfficientAndroidLWJGLKeycode.generateKeyName(), $$Lambda$MainActivity$ycdLnzDkbhPWAgtJ17xXDdweE.INSTANCE);
        dialog.show();
    }

    private void openCustomControls() {
        if (this.ingameControlsEditorListener != null && this.ingameControlsEditorArrayAdapter != null) {
            mControlLayout.setModifiable(true);
            this.navDrawer.setAdapter(this.ingameControlsEditorArrayAdapter);
            this.navDrawer.setOnItemClickListener(this.ingameControlsEditorListener);
            this.isInEditor = true;
        }
    }

    public void leaveCustomControls() {
        try {
            mControlLayout.loadLayout((CustomControls) null);
            mControlLayout.setModifiable(false);
            System.gc();
            mControlLayout.loadLayout(this.minecraftProfile.controlFile == null ? LauncherPreferences.PREF_DEFAULTCTRL_PATH : Tools.CTRLMAP_PATH + "/" + this.minecraftProfile.controlFile);
        } catch (IOException e) {
            Tools.showError(this, e);
        }
        this.navDrawer.setAdapter(this.gameActionArrayAdapter);
        this.navDrawer.setOnItemClickListener(this.gameActionClickListener);
        this.isInEditor = false;
    }

    private void openLogOutput() {
        this.loggerView.setVisibility(0);
        this.mIsResuming = false;
    }

    public void closeLogOutput(View view) {
        this.loggerView.setVisibility(8);
        this.mIsResuming = true;
    }

    public void toggleMenu(View v) {
        this.drawerLayout.openDrawer(5);
    }

    public static void toggleMouse(Context ctx) {
        if (!CallbackBridge.isGrabbing()) {
            Toast.makeText(ctx, touchpad.switchState() ? R.string.control_mouseon : R.string.control_mouseoff, 0).show();
        }
    }

    public static void dialogForceClose(Context ctx) {
        new AlertDialog.Builder(ctx).setMessage(R.string.mcn_exit_confirm).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).setPositiveButton(17039370, $$Lambda$MainActivity$acoednS2iUHsl6_858aab49oNc4.INSTANCE).show();
    }

    static /* synthetic */ void lambda$dialogForceClose$7(DialogInterface p1, int p2) {
        try {
            fullyExit();
        } catch (Throwable th) {
            Log.w(Tools.APP_NAME, "Could not enable System.exit() method!", th);
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (this.isInEditor) {
            return super.dispatchKeyEvent(event);
        }
        if (event.getKeyCode() != 4 || touchCharInput.isEnabled()) {
            return this.minecraftGLView.processKeyEvent(event);
        }
        if (event.getAction() != 1) {
            return true;
        }
        CallbackBridge.sendKeyPress(256);
        return true;
    }

    public static void switchKeyboardState() {
        TouchCharInput touchCharInput2 = touchCharInput;
        if (touchCharInput2 != null) {
            touchCharInput2.switchKeyboardState();
        }
    }

    public void adjustMouseSpeedLive() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.mcl_setting_title_mousespeed);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_live_mouse_speed_editor, (ViewGroup) null);
        SeekBar sb = (SeekBar) v.findViewById(R.id.mouseSpeed);
        final TextView tv = (TextView) v.findViewById(R.id.mouseSpeedTV);
        sb.setMax(275);
        int i = (int) (LauncherPreferences.PREF_MOUSESPEED * 100.0f);
        this.tmpMouseSpeed = i;
        sb.setProgress(i - 25);
        tv.setText(this.tmpMouseSpeed + " %");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MainActivity.this.tmpMouseSpeed = i + 25;
                tv.setText(MainActivity.this.tmpMouseSpeed + " %");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        b.setView(v);
        b.setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.lambda$adjustMouseSpeedLive$8$MainActivity(dialogInterface, i);
            }
        });
        b.setNegativeButton(17039360, $$Lambda$MainActivity$Xh4lo6BJr8zDESxRsh127O_0uc.INSTANCE);
        b.show();
    }

    public /* synthetic */ void lambda$adjustMouseSpeedLive$8$MainActivity(DialogInterface dialogInterface, int i) {
        LauncherPreferences.PREF_MOUSESPEED = ((float) this.tmpMouseSpeed) / 100.0f;
        LauncherPreferences.DEFAULT_PREF.edit().putInt("mousespeed", this.tmpMouseSpeed).commit();
        dialogInterface.dismiss();
        System.gc();
    }

    static /* synthetic */ void lambda$adjustMouseSpeedLive$9(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        System.gc();
    }

    public static void openLink(String link) {
        Context ctx = touchpad.getContext();
        ((Activity) ctx).runOnUiThread(new Runnable(link, ctx) {
            public final /* synthetic */ String f$0;
            public final /* synthetic */ Context f$1;

            {
                this.f$0 = r1;
                this.f$1 = r2;
            }

            public final void run() {
                MainActivity.lambda$openLink$10(this.f$0, this.f$1);
            }
        });
    }

    static /* synthetic */ void lambda$openLink$10(String link, Context ctx) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(link.replace("file://", "content://")), "*/*");
            ctx.startActivity(intent);
        } catch (Throwable th) {
            Tools.showError(ctx, th);
        }
    }
}
