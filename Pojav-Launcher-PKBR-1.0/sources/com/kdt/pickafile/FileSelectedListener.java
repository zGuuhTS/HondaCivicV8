package com.kdt.pickafile;

import java.io.File;

public abstract class FileSelectedListener {
    public abstract void onFileSelected(File file, String str);

    public void onFileLongClick(File file, String path) {
    }
}
