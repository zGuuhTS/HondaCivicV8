package net.kdt.pojavlaunch;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.gson.JsonSyntaxException;
import com.kdt.pickafile.FileListView;
import com.kdt.pickafile.FileSelectedListener;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.io.IOException;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.ControlDrawerData;
import net.kdt.pojavlaunch.customcontrols.ControlLayout;
import net.kdt.pojavlaunch.customcontrols.CustomControls;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class CustomControlsActivity extends BaseActivity {
    private static String sSelectedName = "new_control";
    public boolean isModified = false;
    private ControlLayout mControlLayout;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerNavigationView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_custom_controls);
        this.mControlLayout = (ControlLayout) findViewById(R.id.customctrl_controllayout);
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.customctrl_drawerlayout);
        this.mDrawerNavigationView = (ListView) findViewById(R.id.customctrl_navigation_view);
        findViewById(R.id.drawer_button).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                CustomControlsActivity.this.lambda$onCreate$0$CustomControlsActivity(view);
            }
        });
        this.mDrawerLayout.setDrawerLockMode(1);
        this.mDrawerNavigationView.setAdapter(new ArrayAdapter(this, 17367043, getResources().getStringArray(R.array.menu_customcontrol_customactivity)));
        this.mDrawerNavigationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                CustomControlsActivity.this.lambda$onCreate$1$CustomControlsActivity(adapterView, view, i, j);
            }
        });
        this.mControlLayout.setActivity(this);
        this.mControlLayout.setModifiable(true);
        loadControl(LauncherPreferences.PREF_DEFAULTCTRL_PATH, this.mControlLayout);
    }

    public /* synthetic */ void lambda$onCreate$0$CustomControlsActivity(View v) {
        this.mDrawerLayout.openDrawer((View) this.mDrawerNavigationView);
    }

    public /* synthetic */ void lambda$onCreate$1$CustomControlsActivity(AdapterView parent, View view, int position, long id) {
        switch (position) {
            case 0:
                this.mControlLayout.addControlButton(new ControlData("New"));
                break;
            case 1:
                this.mControlLayout.addDrawer(new ControlDrawerData());
                break;
            case 2:
                load(this.mControlLayout);
                break;
            case 3:
                save(false, this.mControlLayout);
                break;
            case 4:
                dialogSelectDefaultCtrl(this.mControlLayout);
                break;
            case 5:
                try {
                    Uri contentUri = DocumentsContract.buildDocumentUri(getString(R.string.storageProviderAuthorities), doSaveCtrl(sSelectedName, this.mControlLayout));
                    Intent shareIntent = new Intent();
                    shareIntent.setAction("android.intent.action.SEND");
                    shareIntent.putExtra("android.intent.extra.STREAM", contentUri);
                    shareIntent.addFlags(1);
                    shareIntent.setType("application/json");
                    startActivity(shareIntent);
                    startActivity(Intent.createChooser(shareIntent, sSelectedName));
                    break;
                } catch (Exception e) {
                    Tools.showError(this, e);
                    break;
                }
        }
        this.mDrawerLayout.closeDrawers();
    }

    public void onBackPressed() {
        if (!this.isModified) {
            setResult(-1, new Intent());
            super.onBackPressed();
            return;
        }
        save(true, this.mControlLayout);
    }

    public static void dialogSelectDefaultCtrl(final ControlLayout layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(layout.getContext());
        builder.setTitle((int) R.string.customctrl_selectdefault);
        builder.setPositiveButton(17039360, (DialogInterface.OnClickListener) null);
        final AlertDialog dialog = builder.create();
        FileListView flv = new FileListView(dialog, "json");
        flv.lockPathAt(new File(Tools.CTRLMAP_PATH));
        flv.setFileSelectedListener(new FileSelectedListener() {
            public void onFileSelected(File file, String path) {
                CustomControlsActivity.setDefaultControlJson(path, layout);
                dialog.dismiss();
            }
        });
        dialog.setView(flv);
        dialog.show();
    }

    public static void save(boolean exit, final ControlLayout layout) {
        final Context ctx = layout.getContext();
        EditText edit = new EditText(ctx);
        edit.setSingleLine();
        edit.setText(sSelectedName);
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle((int) R.string.global_save);
        builder.setView((View) edit);
        builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(17039360, (DialogInterface.OnClickListener) null);
        if (exit) {
            builder.setNeutralButton((int) R.string.mcn_exit_call, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface p1, int p2) {
                    layout.setModifiable(false);
                    Context context = ctx;
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).leaveCustomControls();
                        return;
                    }
                    ((CustomControlsActivity) context).isModified = false;
                    ((Activity) ctx).onBackPressed();
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener(edit, ctx, layout, exit) {
            public final /* synthetic */ EditText f$1;
            public final /* synthetic */ Context f$2;
            public final /* synthetic */ ControlLayout f$3;
            public final /* synthetic */ boolean f$4;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
                this.f$4 = r5;
            }

            public final void onShow(DialogInterface dialogInterface) {
                AlertDialog.this.getButton(-1).setOnClickListener(new View.OnClickListener(this.f$1, this.f$2, this.f$3, AlertDialog.this, this.f$4) {
                    public final /* synthetic */ EditText f$0;
                    public final /* synthetic */ Context f$1;
                    public final /* synthetic */ ControlLayout f$2;
                    public final /* synthetic */ AlertDialog f$3;
                    public final /* synthetic */ boolean f$4;

                    {
                        this.f$0 = r1;
                        this.f$1 = r2;
                        this.f$2 = r3;
                        this.f$3 = r4;
                        this.f$4 = r5;
                    }

                    public final void onClick(View view) {
                        CustomControlsActivity.lambda$null$2(this.f$0, this.f$1, this.f$2, this.f$3, this.f$4, view);
                    }
                });
            }
        });
        dialog.show();
    }

    static /* synthetic */ void lambda$null$2(EditText edit, Context ctx, ControlLayout layout, AlertDialog dialog, boolean exit, View view) {
        if (edit.getText().toString().isEmpty()) {
            edit.setError(ctx.getResources().getString(R.string.global_error_field_empty));
            return;
        }
        try {
            Toast.makeText(ctx, ctx.getString(R.string.global_save) + ": " + doSaveCtrl(edit.getText().toString(), layout), 0).show();
            dialog.dismiss();
            if (exit) {
                if (ctx instanceof MainActivity) {
                    ((MainActivity) ctx).leaveCustomControls();
                } else {
                    ((Activity) ctx).onBackPressed();
                }
            }
        } catch (Throwable th) {
            Tools.showError(ctx, th, exit);
        }
    }

    public static void load(final ControlLayout layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(layout.getContext());
        builder.setTitle((int) R.string.global_load);
        builder.setPositiveButton(17039360, (DialogInterface.OnClickListener) null);
        final AlertDialog dialog = builder.create();
        FileListView flv = new FileListView(dialog, "json");
        if (Build.VERSION.SDK_INT < 29) {
            flv.listFileAt(new File(Tools.CTRLMAP_PATH));
        } else {
            flv.lockPathAt(new File(Tools.CTRLMAP_PATH));
        }
        flv.setFileSelectedListener(new FileSelectedListener() {
            public void onFileSelected(File file, String path) {
                CustomControlsActivity.loadControl(path, layout);
                dialog.dismiss();
            }
        });
        dialog.setView(flv);
        dialog.show();
    }

    /* access modifiers changed from: private */
    public static void setDefaultControlJson(String path, ControlLayout ctrlLayout) {
        try {
            ctrlLayout.loadLayout((CustomControls) Tools.GLOBAL_GSON.fromJson(Tools.read(path), CustomControls.class));
            LauncherPreferences.DEFAULT_PREF.edit().putString("defaultCtrl", path).apply();
            LauncherPreferences.PREF_DEFAULTCTRL_PATH = path;
        } catch (JsonSyntaxException | IOException exception) {
            Tools.showError(ctrlLayout.getContext(), exception);
        }
    }

    private static String doSaveCtrl(String name, ControlLayout layout) throws Exception {
        String jsonPath = Tools.CTRLMAP_PATH + "/" + name + ".json";
        layout.saveLayout(jsonPath);
        return jsonPath;
    }

    /* access modifiers changed from: private */
    public static void loadControl(String path, ControlLayout layout) {
        try {
            layout.loadLayout(path);
            String replace = path.replace(Tools.CTRLMAP_PATH, ".");
            sSelectedName = replace;
            sSelectedName = replace.substring(0, replace.length() - 5);
        } catch (Exception e) {
            Tools.showError(layout.getContext(), e);
        }
    }
}
