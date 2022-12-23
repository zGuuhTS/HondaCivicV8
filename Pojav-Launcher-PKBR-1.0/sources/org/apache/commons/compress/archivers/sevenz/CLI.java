package org.apache.commons.compress.archivers.sevenz;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import top.defaults.checkerboarddrawable.BuildConfig;

public class CLI {
    /* access modifiers changed from: private */
    public static final byte[] BUF = new byte[8192];

    private enum Mode {
        LIST("Analysing") {
            private String getContentMethods(SevenZArchiveEntry sevenZArchiveEntry) {
                StringBuilder sb = new StringBuilder();
                boolean z = true;
                for (SevenZMethodConfiguration sevenZMethodConfiguration : sevenZArchiveEntry.getContentMethods()) {
                    if (!z) {
                        sb.append(", ");
                    }
                    z = false;
                    sb.append(sevenZMethodConfiguration.getMethod());
                    if (sevenZMethodConfiguration.getOptions() != null) {
                        sb.append("(");
                        sb.append(sevenZMethodConfiguration.getOptions());
                        sb.append(")");
                    }
                }
                return sb.toString();
            }

            public void takeAction(SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) {
                PrintStream printStream;
                String str;
                PrintStream printStream2;
                String str2;
                String str3;
                PrintStream printStream3;
                System.out.print(sevenZArchiveEntry.getName());
                if (sevenZArchiveEntry.isDirectory()) {
                    printStream = System.out;
                    str = " dir";
                } else {
                    printStream = System.out;
                    str = " " + sevenZArchiveEntry.getCompressedSize() + "/" + sevenZArchiveEntry.getSize();
                }
                printStream.print(str);
                if (sevenZArchiveEntry.getHasLastModifiedDate()) {
                    printStream2 = System.out;
                    str2 = " " + sevenZArchiveEntry.getLastModifiedDate();
                } else {
                    printStream2 = System.out;
                    str2 = " no last modified date";
                }
                printStream2.print(str2);
                if (!sevenZArchiveEntry.isDirectory()) {
                    printStream3 = System.out;
                    str3 = " " + getContentMethods(sevenZArchiveEntry);
                } else {
                    printStream3 = System.out;
                    str3 = BuildConfig.FLAVOR;
                }
                printStream3.println(str3);
            }
        },
        EXTRACT("Extracting") {
            public void takeAction(SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) throws IOException {
                File file = new File(sevenZArchiveEntry.getName());
                if (!sevenZArchiveEntry.isDirectory()) {
                    PrintStream printStream = System.out;
                    printStream.println("extracting to " + file);
                    File parentFile = file.getParentFile();
                    if (parentFile == null || parentFile.exists() || parentFile.mkdirs()) {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            long size = sevenZArchiveEntry.getSize();
                            long j = 0;
                            while (j < size) {
                                int read = sevenZFile.read(CLI.BUF, 0, (int) Math.min(size - j, (long) CLI.BUF.length));
                                if (read >= 1) {
                                    j += (long) read;
                                    fileOutputStream.write(CLI.BUF, 0, read);
                                } else {
                                    throw new IOException("reached end of entry " + sevenZArchiveEntry.getName() + " after " + j + " bytes, expected " + size);
                                }
                            }
                        } finally {
                            fileOutputStream.close();
                        }
                    } else {
                        throw new IOException("Cannot create " + parentFile);
                    }
                } else if (file.isDirectory() || file.mkdirs()) {
                    PrintStream printStream2 = System.out;
                    printStream2.println("created directory " + file);
                } else {
                    throw new IOException("Cannot create directory " + file);
                }
            }
        };
        
        private final String message;

        private Mode(String str) {
            this.message = str;
        }

        public String getMessage() {
            return this.message;
        }

        public abstract void takeAction(SevenZFile sevenZFile, SevenZArchiveEntry sevenZArchiveEntry) throws IOException;
    }

    private static Mode grabMode(String[] strArr) {
        return strArr.length < 2 ? Mode.LIST : (Mode) Enum.valueOf(Mode.class, strArr[1].toUpperCase());
    }

    public static void main(String[] strArr) throws Exception {
        if (strArr.length == 0) {
            usage();
            return;
        }
        Mode grabMode = grabMode(strArr);
        PrintStream printStream = System.out;
        printStream.println(grabMode.getMessage() + " " + strArr[0]);
        File file = new File(strArr[0]);
        if (!file.isFile()) {
            PrintStream printStream2 = System.err;
            printStream2.println(file + " doesn't exist or is a directory");
        }
        SevenZFile sevenZFile = new SevenZFile(file);
        while (true) {
            try {
                SevenZArchiveEntry nextEntry = sevenZFile.getNextEntry();
                if (nextEntry != null) {
                    grabMode.takeAction(sevenZFile, nextEntry);
                } else {
                    return;
                }
            } finally {
                sevenZFile.close();
            }
        }
    }

    private static void usage() {
        System.out.println("Parameters: archive-name [list|extract]");
    }
}
