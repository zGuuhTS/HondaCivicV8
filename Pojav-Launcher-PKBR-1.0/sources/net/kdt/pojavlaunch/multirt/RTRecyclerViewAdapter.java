package net.kdt.pojavlaunch.multirt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.internal.view.SupportMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.IOException;
import net.kdt.pojavlaunch.Architecture;
import net.kdt.pojavlaunch.PXBRApplication;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.multirt.RTRecyclerViewAdapter;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import top.defaults.checkerboarddrawable.BuildConfig;

public class RTRecyclerViewAdapter extends RecyclerView.Adapter<RTViewHolder> {
    MultiRTConfigDialog mConfigDialog;

    public RTRecyclerViewAdapter(MultiRTConfigDialog dialog) {
        this.mConfigDialog = dialog;
    }

    public RTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RTViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multirt_runtime, parent, false));
    }

    public void onBindViewHolder(RTViewHolder holder, int position) {
        holder.bindRuntime(MultiRTUtils.getRuntimes().get(position), position);
    }

    public int getItemCount() {
        return MultiRTUtils.getRuntimes().size();
    }

    public boolean isDefaultRuntime(Runtime rt) {
        return LauncherPreferences.PREF_DEFAULT_RUNTIME.equals(rt.name);
    }

    public void setDefault(Runtime rt) {
        LauncherPreferences.PREF_DEFAULT_RUNTIME = rt.name;
        LauncherPreferences.DEFAULT_PREF.edit().putString("defaultRuntime", LauncherPreferences.PREF_DEFAULT_RUNTIME).apply();
        notifyDataSetChanged();
    }

    public class RTViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final Context mContext;
        int mCurrentPosition;
        Runtime mCurrentRuntime;
        final ColorStateList mDefaultColors;
        final TextView mFullJavaVersionTextView;
        final TextView mJavaVersionTextView;
        final Button mSetDefaultButton;

        public RTViewHolder(View itemView) {
            super(itemView);
            this.mJavaVersionTextView = (TextView) itemView.findViewById(R.id.multirt_view_java_version);
            TextView textView = (TextView) itemView.findViewById(R.id.multirt_view_java_version_full);
            this.mFullJavaVersionTextView = textView;
            Button button = (Button) itemView.findViewById(R.id.multirt_view_setdefaultbtn);
            this.mSetDefaultButton = button;
            button.setOnClickListener(this);
            this.mDefaultColors = textView.getTextColors();
            this.mContext = itemView.getContext();
            itemView.findViewById(R.id.multirt_view_removebtn).setOnClickListener(this);
        }

        public void onClick(View view) {
            Runtime runtime;
            if (view.getId() == R.id.multirt_view_removebtn) {
                if (this.mCurrentRuntime != null) {
                    if (MultiRTUtils.getRuntimes().size() >= 2 || !this.mSetDefaultButton.isShown()) {
                        PXBRApplication.sExecutorService.execute(new Runnable() {
                            public final void run() {
                                RTRecyclerViewAdapter.RTViewHolder.this.lambda$onClick$1$RTRecyclerViewAdapter$RTViewHolder();
                            }
                        });
                        return;
                    }
                    AlertDialog.Builder bldr = new AlertDialog.Builder(this.mContext);
                    bldr.setTitle((int) R.string.global_error);
                    bldr.setMessage((int) R.string.multirt_config_removeerror_last);
                    bldr.setPositiveButton(17039370, (DialogInterface.OnClickListener) C0008x35977b14.INSTANCE);
                    bldr.show();
                }
            } else if (view.getId() == R.id.multirt_view_setdefaultbtn && (runtime = this.mCurrentRuntime) != null) {
                RTRecyclerViewAdapter.this.setDefault(runtime);
                RTRecyclerViewAdapter.this.notifyDataSetChanged();
            }
        }

        public /* synthetic */ void lambda$onClick$1$RTRecyclerViewAdapter$RTViewHolder() {
            try {
                MultiRTUtils.removeRuntimeNamed(this.mCurrentRuntime.name);
            } catch (IOException e) {
                Tools.showError(this.itemView.getContext(), e);
            }
        }

        public void bindRuntime(Runtime runtime, int pos) {
            this.mCurrentRuntime = runtime;
            this.mCurrentPosition = pos;
            if (runtime.versionString == null || Tools.DEVICE_ARCHITECTURE != Architecture.archAsInt(runtime.arch)) {
                if (runtime.versionString == null) {
                    this.mFullJavaVersionTextView.setText(R.string.multirt_runtime_corrupt);
                } else {
                    this.mFullJavaVersionTextView.setText(this.mContext.getString(R.string.multirt_runtime_incompatiblearch, new Object[]{runtime.arch}));
                }
                this.mJavaVersionTextView.setText(runtime.name);
                this.mFullJavaVersionTextView.setTextColor(SupportMenu.CATEGORY_MASK);
                this.mSetDefaultButton.setVisibility(8);
                return;
            }
            this.mJavaVersionTextView.setText(runtime.name.replace(".tar.xz", BuildConfig.FLAVOR).replace("-", " "));
            this.mFullJavaVersionTextView.setText(runtime.versionString);
            this.mFullJavaVersionTextView.setTextColor(this.mDefaultColors);
            this.mSetDefaultButton.setVisibility(0);
            boolean defaultRuntime = RTRecyclerViewAdapter.this.isDefaultRuntime(runtime);
            this.mSetDefaultButton.setEnabled(!defaultRuntime);
            this.mSetDefaultButton.setText(defaultRuntime ? R.string.multirt_config_setdefault_already : R.string.multirt_config_setdefault);
        }
    }
}
