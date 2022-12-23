package net.kdt.pojavlaunch;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.kdt.pojavlaunch.utils.FileUtils;
import org.apache.commons.p012io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import top.defaults.checkerboarddrawable.BuildConfig;

public class ImportControlActivity extends Activity {
    private EditText mEditText;
    private boolean mHasIntentChanged = true;
    private volatile boolean mIsFileVerified = false;
    private Uri mUriData;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.initContextConstants(getApplicationContext());
        setContentView(R.layout.activity_import_control);
        this.mEditText = (EditText) findViewById(R.id.editText_import_control_file_name);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        if (intent != null) {
            setIntent(intent);
        }
        this.mHasIntentChanged = true;
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        if (this.mHasIntentChanged) {
            this.mIsFileVerified = false;
            getUriData();
            this.mEditText.setText(getNameFromURI(this.mUriData));
            this.mHasIntentChanged = false;
            new Thread(new Runnable() {
                public final void run() {
                    ImportControlActivity.this.lambda$onPostResume$1$ImportControlActivity();
                }
            }).start();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public final void run() {
                    ImportControlActivity.this.lambda$onPostResume$2$ImportControlActivity();
                }
            }, 100);
        }
    }

    public /* synthetic */ void lambda$onPostResume$1$ImportControlActivity() {
        importControlFile("TMP_IMPORT_FILE");
        if (verify()) {
            this.mIsFileVerified = true;
        } else {
            runOnUiThread(new Runnable() {
                public final void run() {
                    ImportControlActivity.this.lambda$null$0$ImportControlActivity();
                }
            });
        }
    }

    public /* synthetic */ void lambda$null$0$ImportControlActivity() {
        Toast.makeText(this, getText(R.string.import_control_invalid_file), 0).show();
        finishAndRemoveTask();
    }

    public /* synthetic */ void lambda$onPostResume$2$ImportControlActivity() {
        ((InputMethodManager) getApplicationContext().getSystemService("input_method")).toggleSoftInput(1, 0);
        EditText editText = this.mEditText;
        editText.setSelection(editText.getText().length());
    }

    public void startImport(View view) {
        String fileName = trimFileName(this.mEditText.getText().toString());
        if (!isFileNameValid(fileName)) {
            Toast.makeText(this, getText(R.string.import_control_invalid_name), 0).show();
        } else if (!this.mIsFileVerified) {
            Toast.makeText(this, getText(R.string.import_control_verifying_file), 1).show();
        } else {
            new File(Tools.CTRLMAP_PATH + "/TMP_IMPORT_FILE.json").renameTo(new File(Tools.CTRLMAP_PATH + "/" + fileName + ".json"));
            Toast.makeText(getApplicationContext(), getText(R.string.import_control_done), 0).show();
            finishAndRemoveTask();
        }
    }

    private boolean importControlFile(String fileName) {
        try {
            InputStream is = getContentResolver().openInputStream(this.mUriData);
            OutputStream os = new FileOutputStream(Tools.CTRLMAP_PATH + "/" + fileName + ".json");
            IOUtils.copy(is, os);
            os.close();
            is.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isFileNameValid(String fileName) {
        String fileName2 = trimFileName(fileName);
        if (!fileName2.isEmpty() && !FileUtils.exists(Tools.CTRLMAP_PATH + "/" + fileName2 + ".json")) {
            return true;
        }
        return false;
    }

    private static String trimFileName(String fileName) {
        return fileName.replace(".json", BuildConfig.FLAVOR).replaceAll("%..", "/").replace("/", BuildConfig.FLAVOR).replace("\\", BuildConfig.FLAVOR).trim();
    }

    private void getUriData() {
        Uri data = getIntent().getData();
        this.mUriData = data;
        if (data == null) {
            try {
                this.mUriData = getIntent().getClipData().getItemAt(0).getUri();
            } catch (Exception e) {
            }
        }
    }

    private static boolean verify() {
        try {
            JSONObject layoutJobj = new JSONObject(Tools.read(Tools.CTRLMAP_PATH + "/TMP_IMPORT_FILE.json"));
            if (!layoutJobj.has("version") || !layoutJobj.has("mControlDataList")) {
                return false;
            }
            return true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNameFromURI(Uri uri) {
        Cursor c = getContentResolver().query(uri, (String[]) null, (String) null, (String[]) null, (String) null);
        c.moveToFirst();
        String fileName = c.getString(c.getColumnIndex("_display_name"));
        c.close();
        return trimFileName(fileName);
    }
}
