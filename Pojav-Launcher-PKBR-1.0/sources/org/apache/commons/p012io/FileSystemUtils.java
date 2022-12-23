package org.apache.commons.p012io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/* renamed from: org.apache.commons.io.FileSystemUtils */
public class FileSystemUtils {

    /* renamed from: DF */
    private static final String f172DF;
    private static final int INIT_PROBLEM = -1;
    private static final FileSystemUtils INSTANCE = new FileSystemUtils();

    /* renamed from: OS */
    private static final int f173OS;
    private static final int OTHER = 0;
    private static final int POSIX_UNIX = 3;
    private static final int UNIX = 2;
    private static final int WINDOWS = 1;

    static {
        String str = "df";
        int i = -1;
        try {
            String property = System.getProperty("os.name");
            if (property != null) {
                String lowerCase = property.toLowerCase(Locale.ENGLISH);
                if (lowerCase.indexOf("windows") != -1) {
                    i = 1;
                } else {
                    if (lowerCase.indexOf("linux") == -1 && lowerCase.indexOf("mpe/ix") == -1 && lowerCase.indexOf("freebsd") == -1 && lowerCase.indexOf("irix") == -1 && lowerCase.indexOf("digital unix") == -1 && lowerCase.indexOf("unix") == -1) {
                        if (lowerCase.indexOf("mac os x") == -1) {
                            if (lowerCase.indexOf("sun os") == -1 && lowerCase.indexOf("sunos") == -1) {
                                if (lowerCase.indexOf("solaris") == -1) {
                                    if (lowerCase.indexOf("hp-ux") == -1 && lowerCase.indexOf("aix") == -1) {
                                        i = 0;
                                    }
                                    i = 3;
                                }
                            }
                            str = "/usr/xpg4/bin/df";
                            i = 3;
                        }
                    }
                    i = 2;
                }
                f173OS = i;
                f172DF = str;
                return;
            }
            throw new IOException("os.name not found");
        } catch (Exception e) {
        }
    }

    @Deprecated
    public static long freeSpace(String str) throws IOException {
        return INSTANCE.freeSpaceOS(str, f173OS, false, -1);
    }

    public static long freeSpaceKb() throws IOException {
        return freeSpaceKb(-1);
    }

    public static long freeSpaceKb(long j) throws IOException {
        return freeSpaceKb(new File(".").getAbsolutePath(), j);
    }

    public static long freeSpaceKb(String str) throws IOException {
        return freeSpaceKb(str, -1);
    }

    public static long freeSpaceKb(String str, long j) throws IOException {
        return INSTANCE.freeSpaceOS(str, f173OS, true, j);
    }

    /* access modifiers changed from: package-private */
    public long freeSpaceOS(String str, int i, boolean z, long j) throws IOException {
        boolean z2;
        if (str != null) {
            switch (i) {
                case 0:
                    throw new IllegalStateException("Unsupported operating system");
                case 1:
                    long freeSpaceWindows = freeSpaceWindows(str, j);
                    return z ? freeSpaceWindows / FileUtils.ONE_KB : freeSpaceWindows;
                case 2:
                    z2 = false;
                    break;
                case 3:
                    z2 = true;
                    break;
                default:
                    throw new IllegalStateException("Exception caught when determining operating system");
            }
            return freeSpaceUnix(str, z, z2, j);
        }
        throw new IllegalArgumentException("Path must not be empty");
    }

    /* access modifiers changed from: package-private */
    public long freeSpaceUnix(String str, boolean z, boolean z2, long j) throws IOException {
        if (str.length() != 0) {
            String str2 = "-";
            if (z) {
                str2 = str2 + "k";
            }
            if (z2) {
                str2 = str2 + "P";
            }
            List<String> performCommand = performCommand(str2.length() > 1 ? new String[]{f172DF, str2, str} : new String[]{f172DF, str}, 3, j);
            if (performCommand.size() >= 2) {
                StringTokenizer stringTokenizer = new StringTokenizer(performCommand.get(1), " ");
                if (stringTokenizer.countTokens() >= 4) {
                    stringTokenizer.nextToken();
                } else if (stringTokenizer.countTokens() != 1 || performCommand.size() < 3) {
                    throw new IOException("Command line '" + f172DF + "' did not return data as expected " + "for path '" + str + "'- check path is valid");
                } else {
                    stringTokenizer = new StringTokenizer(performCommand.get(2), " ");
                }
                stringTokenizer.nextToken();
                stringTokenizer.nextToken();
                return parseBytes(stringTokenizer.nextToken(), str);
            }
            throw new IOException("Command line '" + f172DF + "' did not return info as expected " + "for path '" + str + "'- response was " + performCommand);
        }
        throw new IllegalArgumentException("Path must not be empty");
    }

    /* access modifiers changed from: package-private */
    public long freeSpaceWindows(String str, long j) throws IOException {
        String normalize = FilenameUtils.normalize(str, false);
        if (normalize.length() > 0 && normalize.charAt(0) != '\"') {
            normalize = "\"" + normalize + "\"";
        }
        List<String> performCommand = performCommand(new String[]{"cmd.exe", "/C", "dir /a /-c " + normalize}, Integer.MAX_VALUE, j);
        for (int size = performCommand.size() - 1; size >= 0; size--) {
            String str2 = performCommand.get(size);
            if (str2.length() > 0) {
                return parseDir(str2, normalize);
            }
        }
        throw new IOException("Command line 'dir /-c' did not return any info for path '" + normalize + "'");
    }

    /* access modifiers changed from: package-private */
    public Process openProcess(String[] strArr) throws IOException {
        return Runtime.getRuntime().exec(strArr);
    }

    /* access modifiers changed from: package-private */
    public long parseBytes(String str, String str2) throws IOException {
        try {
            long parseLong = Long.parseLong(str);
            if (parseLong >= 0) {
                return parseLong;
            }
            throw new IOException("Command line '" + f172DF + "' did not find free space in response " + "for path '" + str2 + "'- check path is valid");
        } catch (NumberFormatException e) {
            throw new IOExceptionWithCause("Command line '" + f172DF + "' did not return numeric data as expected " + "for path '" + str2 + "'- check path is valid", e);
        }
    }

    /* access modifiers changed from: package-private */
    public long parseDir(String str, String str2) throws IOException {
        int i;
        int i2;
        int i3;
        int length = str.length();
        while (true) {
            length--;
            i = 0;
            if (length < 0) {
                i2 = 0;
                break;
            } else if (Character.isDigit(str.charAt(length))) {
                i2 = length + 1;
                break;
            }
        }
        while (true) {
            if (length < 0) {
                i3 = 0;
                break;
            }
            char charAt = str.charAt(length);
            if (!Character.isDigit(charAt) && charAt != ',' && charAt != '.') {
                i3 = length + 1;
                break;
            }
            length--;
        }
        if (length >= 0) {
            StringBuilder sb = new StringBuilder(str.substring(i3, i2));
            while (i < sb.length()) {
                if (sb.charAt(i) == ',' || sb.charAt(i) == '.') {
                    sb.deleteCharAt(i);
                    i--;
                }
                i++;
            }
            return parseBytes(sb.toString(), str2);
        }
        throw new IOException("Command line 'dir /-c' did not return valid info for path '" + str2 + "'");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0101  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.lang.String> performCommand(java.lang.String[] r10, int r11, long r12) throws java.io.IOException {
        /*
            r9 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 20
            r0.<init>(r1)
            r1 = 0
            java.lang.Thread r2 = org.apache.commons.p012io.ThreadMonitor.start(r12)     // Catch:{ InterruptedException -> 0x00c8, all -> 0x00c2 }
            java.lang.Process r3 = r9.openProcess(r10)     // Catch:{ InterruptedException -> 0x00c8, all -> 0x00c2 }
            java.io.InputStream r4 = r3.getInputStream()     // Catch:{ InterruptedException -> 0x00bb, all -> 0x00b9 }
            java.io.OutputStream r5 = r3.getOutputStream()     // Catch:{ InterruptedException -> 0x00b7, all -> 0x00b5 }
            java.io.InputStream r6 = r3.getErrorStream()     // Catch:{ InterruptedException -> 0x00b3, all -> 0x00b1 }
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch:{ InterruptedException -> 0x00ae, all -> 0x00ab }
            java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch:{ InterruptedException -> 0x00ae, all -> 0x00ab }
            r8.<init>(r4)     // Catch:{ InterruptedException -> 0x00ae, all -> 0x00ab }
            r7.<init>(r8)     // Catch:{ InterruptedException -> 0x00ae, all -> 0x00ab }
        L_0x0026:
            java.lang.String r1 = r7.readLine()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            if (r1 == 0) goto L_0x0040
            int r8 = r0.size()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            if (r8 >= r11) goto L_0x0040
            java.util.Locale r8 = java.util.Locale.ENGLISH     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.String r1 = r1.toLowerCase(r8)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.String r1 = r1.trim()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r0.add(r1)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            goto L_0x0026
        L_0x0040:
            r3.waitFor()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            org.apache.commons.p012io.ThreadMonitor.stop(r2)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            int r11 = r3.exitValue()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            if (r11 != 0) goto L_0x007f
            boolean r11 = r0.isEmpty()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            if (r11 != 0) goto L_0x0064
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.InputStream) r4)
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.OutputStream) r5)
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.InputStream) r6)
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.Reader) r7)
            if (r3 == 0) goto L_0x0063
            r3.destroy()
        L_0x0063:
            return r0
        L_0x0064:
            java.io.IOException r11 = new java.io.IOException     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r0.<init>()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.String r1 = "Command line did not return any info for command "
            r0.append(r1)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.util.List r1 = java.util.Arrays.asList(r10)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r0.append(r1)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.String r0 = r0.toString()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r11.<init>(r0)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            throw r11     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
        L_0x007f:
            java.io.IOException r11 = new java.io.IOException     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r0.<init>()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.String r1 = "Command line returned OS error code '"
            r0.append(r1)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            int r1 = r3.exitValue()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r0.append(r1)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.String r1 = "' for command "
            r0.append(r1)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.util.List r1 = java.util.Arrays.asList(r10)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r0.append(r1)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            java.lang.String r0 = r0.toString()     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            r11.<init>(r0)     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
            throw r11     // Catch:{ InterruptedException -> 0x00a9, all -> 0x00a6 }
        L_0x00a6:
            r10 = move-exception
            goto L_0x00f2
        L_0x00a9:
            r11 = move-exception
            goto L_0x00c0
        L_0x00ab:
            r10 = move-exception
            r7 = r1
            goto L_0x00f2
        L_0x00ae:
            r11 = move-exception
            r7 = r1
            goto L_0x00c0
        L_0x00b1:
            r10 = move-exception
            goto L_0x00c6
        L_0x00b3:
            r11 = move-exception
            goto L_0x00be
        L_0x00b5:
            r10 = move-exception
            goto L_0x00c5
        L_0x00b7:
            r11 = move-exception
            goto L_0x00bd
        L_0x00b9:
            r10 = move-exception
            goto L_0x00c4
        L_0x00bb:
            r11 = move-exception
            r4 = r1
        L_0x00bd:
            r5 = r1
        L_0x00be:
            r6 = r1
            r7 = r6
        L_0x00c0:
            r1 = r4
            goto L_0x00cd
        L_0x00c2:
            r10 = move-exception
            r3 = r1
        L_0x00c4:
            r4 = r1
        L_0x00c5:
            r5 = r1
        L_0x00c6:
            r7 = r1
            goto L_0x00f3
        L_0x00c8:
            r11 = move-exception
            r3 = r1
            r5 = r3
            r6 = r5
            r7 = r6
        L_0x00cd:
            org.apache.commons.io.IOExceptionWithCause r0 = new org.apache.commons.io.IOExceptionWithCause     // Catch:{ all -> 0x00f0 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00f0 }
            r2.<init>()     // Catch:{ all -> 0x00f0 }
            java.lang.String r4 = "Command line threw an InterruptedException for command "
            r2.append(r4)     // Catch:{ all -> 0x00f0 }
            java.util.List r10 = java.util.Arrays.asList(r10)     // Catch:{ all -> 0x00f0 }
            r2.append(r10)     // Catch:{ all -> 0x00f0 }
            java.lang.String r10 = " timeout="
            r2.append(r10)     // Catch:{ all -> 0x00f0 }
            r2.append(r12)     // Catch:{ all -> 0x00f0 }
            java.lang.String r10 = r2.toString()     // Catch:{ all -> 0x00f0 }
            r0.<init>(r10, r11)     // Catch:{ all -> 0x00f0 }
            throw r0     // Catch:{ all -> 0x00f0 }
        L_0x00f0:
            r10 = move-exception
            r4 = r1
        L_0x00f2:
            r1 = r6
        L_0x00f3:
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.InputStream) r4)
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.OutputStream) r5)
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.InputStream) r1)
            org.apache.commons.p012io.IOUtils.closeQuietly((java.io.Reader) r7)
            if (r3 == 0) goto L_0x0104
            r3.destroy()
        L_0x0104:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.p012io.FileSystemUtils.performCommand(java.lang.String[], int, long):java.util.List");
    }
}
