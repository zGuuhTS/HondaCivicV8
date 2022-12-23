package net.kdt.pojavlaunch.multirt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.webkit.MimeTypeMap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.p000br.pixelmonbrasil.debug.R;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

public class MultiRTConfigDialog {
    public static final int MULTIRT_PICK_RUNTIME = 2048;
    public static final int MULTIRT_PICK_RUNTIME_STARTUP = 2049;
    private AlertDialog mDialog;
    private RecyclerView mDialogView;

    public void show() {
        refresh();
        this.mDialog.show();
    }

    public void refresh() {
        if (this.mDialogView.getAdapter() != null) {
            this.mDialogView.getAdapter().notifyDataSetChanged();
        }
    }

    public void prepare(Activity activity) {
        RecyclerView recyclerView = new RecyclerView(activity);
        this.mDialogView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, 1, false));
        this.mDialogView.setAdapter(new RTRecyclerViewAdapter(this));
        this.mDialog = new AlertDialog.Builder(activity).setTitle(R.string.multirt_config_title).setView(this.mDialogView).setPositiveButton(R.string.multirt_config_add, new DialogInterface.OnClickListener(activity) {
            public final /* synthetic */ Activity f$0;

            {
                this.f$0 = r1;
            }

            public final void onClick(DialogInterface dialogInterface, int i) {
                MultiRTConfigDialog.openRuntimeSelector(this.f$0, 2048);
            }
        }).setNegativeButton(R.string.mcn_exit_call, $$Lambda$MultiRTConfigDialog$JcQquoswqc6kURk5Q70VGLuRSdE.INSTANCE).create();
    }

    public static void openRuntimeSelector(Activity activity, int code) {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(CompressorStreamFactory.f154XZ);
        if (mimeType == null) {
            mimeType = "*/*";
        }
        intent.setType(mimeType);
        activity.startActivityForResult(intent, code);
    }
}
