package com.ipaulpro.afilechooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListAdapter extends BaseAdapter {
    private static final int ICON_FILE = 2131230825;
    private static final int ICON_FOLDER = 2131230826;
    private List<File> mData = new ArrayList();
    private final LayoutInflater mInflater;

    public FileListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void add(File file) {
        this.mData.add(file);
        notifyDataSetChanged();
    }

    public void remove(File file) {
        this.mData.remove(file);
        notifyDataSetChanged();
    }

    public void insert(File file, int index) {
        this.mData.add(index, file);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mData.clear();
        notifyDataSetChanged();
    }

    public File getItem(int position) {
        return this.mData.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getCount() {
        return this.mData.size();
    }

    public List<File> getListItems() {
        return this.mData;
    }

    public void setListItems(List<File> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            row = this.mInflater.inflate(17367043, parent, false);
        }
        TextView view = (TextView) row;
        File file = getItem(position);
        view.setText(file.getName());
        view.setCompoundDrawablesWithIntrinsicBounds(file.isDirectory() ? R.drawable.ic_folder : R.drawable.ic_file, 0, 0, 0);
        view.setCompoundDrawablePadding(20);
        return row;
    }
}
