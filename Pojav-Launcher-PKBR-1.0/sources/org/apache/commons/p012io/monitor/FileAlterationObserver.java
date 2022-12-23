package org.apache.commons.p012io.monitor;

import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.p012io.FileUtils;
import org.apache.commons.p012io.IOCase;
import org.apache.commons.p012io.comparator.NameFileComparator;

/* renamed from: org.apache.commons.io.monitor.FileAlterationObserver */
public class FileAlterationObserver implements Serializable {
    private final Comparator<File> comparator;
    private final FileFilter fileFilter;
    private final List<FileAlterationListener> listeners;
    private final FileEntry rootEntry;

    public FileAlterationObserver(File file) {
        this(file, (FileFilter) null);
    }

    public FileAlterationObserver(File file, FileFilter fileFilter2) {
        this(file, fileFilter2, (IOCase) null);
    }

    public FileAlterationObserver(File file, FileFilter fileFilter2, IOCase iOCase) {
        this(new FileEntry(file), fileFilter2, iOCase);
    }

    public FileAlterationObserver(String str) {
        this(new File(str));
    }

    public FileAlterationObserver(String str, FileFilter fileFilter2) {
        this(new File(str), fileFilter2);
    }

    public FileAlterationObserver(String str, FileFilter fileFilter2, IOCase iOCase) {
        this(new File(str), fileFilter2, iOCase);
    }

    protected FileAlterationObserver(FileEntry fileEntry, FileFilter fileFilter2, IOCase iOCase) {
        this.listeners = new CopyOnWriteArrayList();
        if (fileEntry == null) {
            throw new IllegalArgumentException("Root entry is missing");
        } else if (fileEntry.getFile() != null) {
            this.rootEntry = fileEntry;
            this.fileFilter = fileFilter2;
            this.comparator = (iOCase == null || iOCase.equals(IOCase.SYSTEM)) ? NameFileComparator.NAME_SYSTEM_COMPARATOR : iOCase.equals(IOCase.INSENSITIVE) ? NameFileComparator.NAME_INSENSITIVE_COMPARATOR : NameFileComparator.NAME_COMPARATOR;
        } else {
            throw new IllegalArgumentException("Root directory is missing");
        }
    }

    private void checkAndNotify(FileEntry fileEntry, FileEntry[] fileEntryArr, File[] fileArr) {
        FileEntry[] fileEntryArr2 = fileArr.length > 0 ? new FileEntry[fileArr.length] : FileEntry.EMPTY_ENTRIES;
        int length = fileEntryArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            FileEntry fileEntry2 = fileEntryArr[i2];
            while (i < fileArr.length && this.comparator.compare(fileEntry2.getFile(), fileArr[i]) > 0) {
                fileEntryArr2[i] = createFileEntry(fileEntry, fileArr[i]);
                doCreate(fileEntryArr2[i]);
                i++;
            }
            if (i >= fileArr.length || this.comparator.compare(fileEntry2.getFile(), fileArr[i]) != 0) {
                checkAndNotify(fileEntry2, fileEntry2.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
                doDelete(fileEntry2);
            } else {
                doMatch(fileEntry2, fileArr[i]);
                checkAndNotify(fileEntry2, fileEntry2.getChildren(), listFiles(fileArr[i]));
                fileEntryArr2[i] = fileEntry2;
                i++;
            }
        }
        while (i < fileArr.length) {
            fileEntryArr2[i] = createFileEntry(fileEntry, fileArr[i]);
            doCreate(fileEntryArr2[i]);
            i++;
        }
        fileEntry.setChildren(fileEntryArr2);
    }

    private FileEntry createFileEntry(FileEntry fileEntry, File file) {
        FileEntry newChildInstance = fileEntry.newChildInstance(file);
        newChildInstance.refresh(file);
        File[] listFiles = listFiles(file);
        FileEntry[] fileEntryArr = listFiles.length > 0 ? new FileEntry[listFiles.length] : FileEntry.EMPTY_ENTRIES;
        for (int i = 0; i < listFiles.length; i++) {
            fileEntryArr[i] = createFileEntry(newChildInstance, listFiles[i]);
        }
        newChildInstance.setChildren(fileEntryArr);
        return newChildInstance;
    }

    private void doCreate(FileEntry fileEntry) {
        for (FileAlterationListener next : this.listeners) {
            if (fileEntry.isDirectory()) {
                next.onDirectoryCreate(fileEntry.getFile());
            } else {
                next.onFileCreate(fileEntry.getFile());
            }
        }
        for (FileEntry doCreate : fileEntry.getChildren()) {
            doCreate(doCreate);
        }
    }

    private void doDelete(FileEntry fileEntry) {
        for (FileAlterationListener next : this.listeners) {
            if (fileEntry.isDirectory()) {
                next.onDirectoryDelete(fileEntry.getFile());
            } else {
                next.onFileDelete(fileEntry.getFile());
            }
        }
    }

    private void doMatch(FileEntry fileEntry, File file) {
        if (fileEntry.refresh(file)) {
            for (FileAlterationListener next : this.listeners) {
                if (fileEntry.isDirectory()) {
                    next.onDirectoryChange(file);
                } else {
                    next.onFileChange(file);
                }
            }
        }
    }

    private File[] listFiles(File file) {
        File[] fileArr;
        if (file.isDirectory()) {
            FileFilter fileFilter2 = this.fileFilter;
            fileArr = fileFilter2 == null ? file.listFiles() : file.listFiles(fileFilter2);
        } else {
            fileArr = null;
        }
        if (fileArr == null) {
            fileArr = FileUtils.EMPTY_FILE_ARRAY;
        }
        Comparator<File> comparator2 = this.comparator;
        if (comparator2 != null && fileArr.length > 1) {
            Arrays.sort(fileArr, comparator2);
        }
        return fileArr;
    }

    public void addListener(FileAlterationListener fileAlterationListener) {
        if (fileAlterationListener != null) {
            this.listeners.add(fileAlterationListener);
        }
    }

    public void checkAndNotify() {
        for (FileAlterationListener onStart : this.listeners) {
            onStart.onStart(this);
        }
        File file = this.rootEntry.getFile();
        if (file.exists()) {
            FileEntry fileEntry = this.rootEntry;
            checkAndNotify(fileEntry, fileEntry.getChildren(), listFiles(file));
        } else if (this.rootEntry.isExists()) {
            FileEntry fileEntry2 = this.rootEntry;
            checkAndNotify(fileEntry2, fileEntry2.getChildren(), FileUtils.EMPTY_FILE_ARRAY);
        }
        for (FileAlterationListener onStop : this.listeners) {
            onStop.onStop(this);
        }
    }

    public void destroy() throws Exception {
    }

    public File getDirectory() {
        return this.rootEntry.getFile();
    }

    public FileFilter getFileFilter() {
        return this.fileFilter;
    }

    public Iterable<FileAlterationListener> getListeners() {
        return this.listeners;
    }

    public void initialize() throws Exception {
        FileEntry fileEntry = this.rootEntry;
        fileEntry.refresh(fileEntry.getFile());
        File[] listFiles = listFiles(this.rootEntry.getFile());
        FileEntry[] fileEntryArr = listFiles.length > 0 ? new FileEntry[listFiles.length] : FileEntry.EMPTY_ENTRIES;
        for (int i = 0; i < listFiles.length; i++) {
            fileEntryArr[i] = createFileEntry(this.rootEntry, listFiles[i]);
        }
        this.rootEntry.setChildren(fileEntryArr);
    }

    public void removeListener(FileAlterationListener fileAlterationListener) {
        if (fileAlterationListener != null) {
            do {
            } while (this.listeners.remove(fileAlterationListener));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("[file='");
        sb.append(getDirectory().getPath());
        sb.append('\'');
        if (this.fileFilter != null) {
            sb.append(", ");
            sb.append(this.fileFilter.toString());
        }
        sb.append(", listeners=");
        sb.append(this.listeners.size());
        sb.append("]");
        return sb.toString();
    }
}
