package net.kdt.pojavlaunch.scoped;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Point;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;
import android.webkit.MimeTypeMap;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import net.kdt.pojavlaunch.Tools;
import org.apache.commons.p012io.FileUtils;

public class FolderProvider extends DocumentsProvider {
    private static final String ALL_MIME_TYPES = "*/*";
    private static final File BASE_DIR = new File(Tools.DIR_GAME_HOME);
    private static final String[] DEFAULT_DOCUMENT_PROJECTION = {"document_id", "mime_type", "_display_name", "last_modified", "flags", "_size"};
    private static final String[] DEFAULT_ROOT_PROJECTION = {"root_id", "mime_types", "flags", "icon", "title", "summary", "document_id", "available_bytes"};

    public Cursor queryRoots(String[] projection) {
        MatrixCursor result = new MatrixCursor(projection != null ? projection : DEFAULT_ROOT_PROJECTION);
        MatrixCursor.RowBuilder row = result.newRow();
        File file = BASE_DIR;
        row.add("root_id", getDocIdForFile(file));
        row.add("document_id", getDocIdForFile(file));
        row.add("summary", (Object) null);
        row.add("flags", 25);
        row.add("title", "Pixelmon Brasil");
        row.add("mime_types", ALL_MIME_TYPES);
        row.add("available_bytes", Long.valueOf(file.getFreeSpace()));
        row.add("icon", Integer.valueOf(R.mipmap.ic_launcher));
        return result;
    }

    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        MatrixCursor result = new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);
        includeFile(result, documentId, (File) null);
        return result;
    }

    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) throws FileNotFoundException {
        MatrixCursor result = new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);
        for (File file : getFileForDocId(parentDocumentId).listFiles()) {
            includeFile(result, (String) null, file);
        }
        return result;
    }

    public ParcelFileDescriptor openDocument(String documentId, String mode, CancellationSignal signal) throws FileNotFoundException {
        return ParcelFileDescriptor.open(getFileForDocId(documentId), ParcelFileDescriptor.parseMode(mode));
    }

    public AssetFileDescriptor openDocumentThumbnail(String documentId, Point sizeHint, CancellationSignal signal) throws FileNotFoundException {
        File file = getFileForDocId(documentId);
        return new AssetFileDescriptor(ParcelFileDescriptor.open(file, 268435456), 0, file.length());
    }

    public boolean onCreate() {
        return true;
    }

    public String createDocument(String parentDocumentId, String mimeType, String displayName) throws FileNotFoundException {
        boolean succeeded;
        File newFile = new File(parentDocumentId, displayName);
        int noConflictId = 2;
        while (newFile.exists()) {
            newFile = new File(parentDocumentId, displayName + " (" + noConflictId + ")");
            noConflictId++;
        }
        try {
            if ("vnd.android.document/directory".equals(mimeType)) {
                succeeded = newFile.mkdir();
            } else {
                succeeded = newFile.createNewFile();
            }
            if (succeeded) {
                return newFile.getPath();
            }
            throw new FileNotFoundException("Failed to create document with id " + newFile.getPath());
        } catch (IOException e) {
            throw new FileNotFoundException("Failed to create document with id " + newFile.getPath());
        }
    }

    public String renameDocument(String documentId, String displayName) throws FileNotFoundException {
        File sourceFile = getFileForDocId(documentId);
        File targetFile = new File(getDocIdForFile(sourceFile.getParentFile()) + "/" + displayName);
        if (sourceFile.renameTo(targetFile)) {
            return getDocIdForFile(targetFile);
        }
        throw new FileNotFoundException("Couldn't rename the document with id" + documentId);
    }

    public String moveDocument(String sourceDocumentId, String sourceParentDocumentId, String targetParentDocumentId) throws FileNotFoundException {
        File sourceFile = getFileForDocId(sourceParentDocumentId + sourceDocumentId);
        File targetFile = new File(targetParentDocumentId + sourceDocumentId);
        if (sourceFile.renameTo(targetFile)) {
            return getDocIdForFile(targetFile);
        }
        throw new FileNotFoundException("Failed to move the document with id " + sourceFile.getPath());
    }

    public void removeDocument(String documentId, String parentDocumentId) throws FileNotFoundException {
        deleteDocument(parentDocumentId + "/" + documentId);
    }

    public void deleteDocument(String documentId) throws FileNotFoundException {
        File file = getFileForDocId(documentId);
        if (file.isDirectory()) {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                throw new FileNotFoundException("Failed to delete document with id " + documentId);
            }
        } else if (!file.delete()) {
            throw new FileNotFoundException("Failed to delete document with id " + documentId);
        }
    }

    public String getDocumentType(String documentId) throws FileNotFoundException {
        return getMimeType(getFileForDocId(documentId));
    }

    public Cursor querySearchDocuments(String rootId, String query, String[] projection) throws FileNotFoundException {
        boolean isInsideHome;
        MatrixCursor result = new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);
        File parent = getFileForDocId(rootId);
        LinkedList<File> pending = new LinkedList<>();
        pending.add(parent);
        while (!pending.isEmpty() && result.getCount() < 50) {
            File file = pending.removeFirst();
            try {
                isInsideHome = file.getCanonicalPath().startsWith(Tools.DIR_GAME_HOME);
            } catch (IOException e) {
                isInsideHome = true;
            }
            if (isInsideHome) {
                if (file.isDirectory()) {
                    Collections.addAll(pending, file.listFiles());
                } else if (file.getName().toLowerCase().contains(query)) {
                    includeFile(result, (String) null, file);
                }
            }
        }
        return result;
    }

    public boolean isChildDocument(String parentDocumentId, String documentId) {
        return documentId.startsWith(parentDocumentId);
    }

    private static String getDocIdForFile(File file) {
        return file.getAbsolutePath();
    }

    private static File getFileForDocId(String docId) throws FileNotFoundException {
        File f = new File(docId);
        if (f.exists()) {
            return f;
        }
        throw new FileNotFoundException(f.getAbsolutePath() + " not found");
    }

    private static String getMimeType(File file) {
        String mime;
        if (file.isDirectory()) {
            return "vnd.android.document/directory";
        }
        String name = file.getName();
        int lastDot = name.lastIndexOf(46);
        if (lastDot < 0 || (mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(lastDot + 1).toLowerCase())) == null) {
            return "application/octet-stream";
        }
        return mime;
    }

    private void includeFile(MatrixCursor result, String docId, File file) throws FileNotFoundException {
        if (docId == null) {
            docId = getDocIdForFile(file);
        } else {
            file = getFileForDocId(docId);
        }
        int flags = 0;
        if (file.isDirectory()) {
            if (file.canWrite()) {
                flags = 0 | 8;
            }
        } else if (file.canWrite()) {
            flags = 0 | 2;
        }
        if (file.getParentFile().canWrite()) {
            flags |= 4;
        }
        String displayName = file.getName();
        String mimeType = getMimeType(file);
        if (mimeType.startsWith("image/")) {
            flags |= 1;
        }
        MatrixCursor.RowBuilder row = result.newRow();
        row.add("document_id", docId);
        row.add("_display_name", displayName);
        row.add("_size", Long.valueOf(file.length()));
        row.add("mime_type", mimeType);
        row.add("last_modified", Long.valueOf(file.lastModified()));
        row.add("flags", Integer.valueOf(flags));
        row.add("icon", Integer.valueOf(R.mipmap.ic_launcher));
    }
}
