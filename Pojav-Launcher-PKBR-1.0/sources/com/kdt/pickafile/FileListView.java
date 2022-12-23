package com.kdt.pickafile;

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.ipaulpro.afilechooser.FileListAdapter;
import java.io.File;
import java.util.Arrays;
import net.kdt.pojavlaunch.Tools;

public class FileListView extends LinearLayout {
    private Context context;
    private DialogTitleListener dialogTitleListener;
    private FileSelectedListener fileSelectedListener;
    private final String[] fileSuffixes;
    private File fullPath;
    private File lockPath;
    private ListView mainLv;
    private boolean showFiles;
    private boolean showFolders;

    public FileListView(AlertDialog build) {
        this(build.getContext(), (AttributeSet) null, new String[0]);
        dialogToTitleListener(build);
    }

    public FileListView(AlertDialog build, String fileSuffix) {
        this(build.getContext(), (AttributeSet) null, new String[]{fileSuffix});
        dialogToTitleListener(build);
    }

    public FileListView(AlertDialog build, String[] fileSuffixes2) {
        this(build.getContext(), (AttributeSet) null, fileSuffixes2);
        dialogToTitleListener(build);
    }

    public FileListView(Context context2) {
        this(context2, (AttributeSet) null);
    }

    public FileListView(Context context2, AttributeSet attrs) {
        this(context2, attrs, new String[0]);
    }

    public FileListView(Context context2, AttributeSet attrs, String[] fileSuffixes2) {
        this(context2, attrs, 0, fileSuffixes2);
    }

    public FileListView(Context context2, AttributeSet attrs, int defStyle, String[] fileSuffixes2) {
        super(context2, attrs, defStyle);
        this.lockPath = new File("/");
        this.showFiles = true;
        this.showFolders = true;
        this.fileSuffixes = fileSuffixes2;
        init(context2);
    }

    private void dialogToTitleListener(AlertDialog dialog) {
        if (dialog != null) {
            dialog.getClass();
            this.dialogTitleListener = new DialogTitleListener() {
                public final void onChangeDialogTitle(String str) {
                    AlertDialog.this.setTitle(str);
                }
            };
        }
    }

    public void init(Context context2) {
        this.context = context2;
        LinearLayout.LayoutParams layParam = new LinearLayout.LayoutParams(-1, -2);
        setOrientation(1);
        ListView listView = new ListView(context2);
        this.mainLv = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                FileListView.this.lambda$init$0$FileListView(adapterView, view, i, j);
            }
        });
        this.mainLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                return FileListView.this.lambda$init$1$FileListView(adapterView, view, i, j);
            }
        });
        addView(this.mainLv, layParam);
        try {
            listFileAt(Environment.getExternalStorageDirectory());
        } catch (NullPointerException e) {
        }
    }

    public /* synthetic */ void lambda$init$0$FileListView(AdapterView p1, View p2, int p3, long p4) {
        File mainFile = new File(p1.getItemAtPosition(p3).toString());
        if (p3 != 0 || this.lockPath.equals(this.fullPath)) {
            listFileAt(mainFile);
        } else {
            parentDir();
        }
    }

    public /* synthetic */ boolean lambda$init$1$FileListView(AdapterView p1, View p2, int p3, long p4) {
        File mainFile = new File(p1.getItemAtPosition(p3).toString());
        if (!mainFile.isFile()) {
            return false;
        }
        this.fileSelectedListener.onFileLongClick(mainFile, mainFile.getAbsolutePath());
        return true;
    }

    public void setFileSelectedListener(FileSelectedListener listener) {
        this.fileSelectedListener = listener;
    }

    public void setDialogTitleListener(DialogTitleListener listener) {
        this.dialogTitleListener = listener;
    }

    public void listFileAt(File path) {
        try {
            if (!path.exists()) {
                Toast.makeText(this.context, "This folder (or file) doesn't exist", 0).show();
                refreshPath();
            } else if (path.isDirectory()) {
                this.fullPath = path;
                File[] listFile = path.listFiles();
                FileListAdapter fileAdapter = new FileListAdapter(this.context);
                if (!path.equals(this.lockPath)) {
                    fileAdapter.add(new File(path, ".."));
                }
                if (!(listFile == null || listFile.length == 0)) {
                    Arrays.sort(listFile, new SortFileName());
                    for (File file : listFile) {
                        if (file.isDirectory()) {
                            if (this.showFolders && (!file.getName().startsWith(".") || file.getName().equals(".minecraft"))) {
                                fileAdapter.add(file);
                            }
                        } else if (this.showFiles) {
                            String[] strArr = this.fileSuffixes;
                            if (strArr.length > 0) {
                                int length = strArr.length;
                                int i = 0;
                                while (true) {
                                    if (i >= length) {
                                        break;
                                    }
                                    if (file.getName().endsWith("." + strArr[i])) {
                                        fileAdapter.add(file);
                                        break;
                                    }
                                    i++;
                                }
                            } else {
                                fileAdapter.add(file);
                            }
                        }
                    }
                }
                this.mainLv.setAdapter(fileAdapter);
                DialogTitleListener dialogTitleListener2 = this.dialogTitleListener;
                if (dialogTitleListener2 != null) {
                    dialogTitleListener2.onChangeDialogTitle(path.getAbsolutePath());
                }
            } else {
                this.fileSelectedListener.onFileSelected(path, path.getAbsolutePath());
            }
        } catch (Exception e) {
            Tools.showError(this.context, e);
        }
    }

    public File getFullPath() {
        return this.fullPath;
    }

    public void refreshPath() {
        listFileAt(getFullPath());
    }

    public void parentDir() {
        if (!this.fullPath.getAbsolutePath().equals("/")) {
            listFileAt(this.fullPath.getParentFile());
        }
    }

    public void lockPathAt(File path) {
        this.lockPath = path;
        listFileAt(path);
    }

    public void setShowFiles(boolean showFiles2) {
        this.showFiles = showFiles2;
    }

    public void setShowFolders(boolean showFolders2) {
        this.showFolders = showFolders2;
    }
}
