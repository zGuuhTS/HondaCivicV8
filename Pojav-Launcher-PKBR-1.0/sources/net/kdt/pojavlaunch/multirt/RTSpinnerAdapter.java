package net.kdt.pojavlaunch.multirt;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.p000br.pixelmonbrasil.debug.R;
import java.util.List;
import top.defaults.checkerboarddrawable.BuildConfig;

public class RTSpinnerAdapter implements SpinnerAdapter {
    final Context mContext;
    List<Runtime> mRuntimes;

    public RTSpinnerAdapter(Context context, List<Runtime> runtimes) {
        this.mRuntimes = runtimes;
        Runtime runtime = new Runtime("<Default>");
        runtime.versionString = BuildConfig.FLAVOR;
        this.mRuntimes.add(runtime);
        this.mContext = context;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    public int getCount() {
        return this.mRuntimes.size();
    }

    public Object getItem(int position) {
        return this.mRuntimes.get(position);
    }

    public long getItemId(int position) {
        return (long) this.mRuntimes.get(position).name.hashCode();
    }

    public boolean hasStableIds() {
        return true;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(this.mContext).inflate(17367043, parent, false);
        }
        Runtime runtime = this.mRuntimes.get(position);
        if (position == this.mRuntimes.size() - 1) {
            ((TextView) view).setText(runtime.name);
        } else {
            TextView textView = (TextView) view;
            Object[] objArr = new Object[2];
            objArr[0] = runtime.name.replace(".tar.xz", BuildConfig.FLAVOR);
            objArr[1] = runtime.versionString == null ? view.getResources().getString(R.string.multirt_runtime_corrupt) : runtime.versionString;
            textView.setText(String.format("%s - %s", objArr));
        }
        return view;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return this.mRuntimes.isEmpty();
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
