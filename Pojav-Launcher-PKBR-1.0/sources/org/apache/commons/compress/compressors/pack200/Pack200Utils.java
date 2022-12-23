package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Pack200Utils {
    private Pack200Utils() {
    }

    public static void normalize(File file) throws IOException {
        normalize(file, file, (Map<String, String>) null);
    }

    public static void normalize(File file, File file2) throws IOException {
        normalize(file, file2, (Map<String, String>) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x005c A[SYNTHETIC, Splitter:B:29:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0061 A[Catch:{ all -> 0x0065 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void normalize(java.io.File r5, java.io.File r6, java.util.Map<java.lang.String, java.lang.String> r7) throws java.io.IOException {
        /*
            if (r7 != 0) goto L_0x0007
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
        L_0x0007:
            java.lang.String r0 = "pack.segment.limit"
            java.lang.String r1 = "-1"
            r7.put(r0, r1)
            java.lang.String r0 = "commons-compress"
            java.lang.String r1 = "pack200normalize"
            java.io.File r0 = java.io.File.createTempFile(r0, r1)
            r0.deleteOnExit()
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0065 }
            r1.<init>(r0)     // Catch:{ all -> 0x0065 }
            r2 = 0
            java.util.jar.Pack200$Packer r3 = java.util.jar.Pack200.newPacker()     // Catch:{ all -> 0x0059 }
            java.util.SortedMap r4 = r3.properties()     // Catch:{ all -> 0x0059 }
            r4.putAll(r7)     // Catch:{ all -> 0x0059 }
            java.util.jar.JarFile r7 = new java.util.jar.JarFile     // Catch:{ all -> 0x0059 }
            r7.<init>(r5)     // Catch:{ all -> 0x0059 }
            r3.pack(r7, r1)     // Catch:{ all -> 0x0056 }
            r1.close()     // Catch:{ all -> 0x0059 }
            java.util.jar.Pack200$Unpacker r5 = java.util.jar.Pack200.newUnpacker()     // Catch:{ all -> 0x0053 }
            java.util.jar.JarOutputStream r7 = new java.util.jar.JarOutputStream     // Catch:{ all -> 0x0053 }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0053 }
            r1.<init>(r6)     // Catch:{ all -> 0x0053 }
            r7.<init>(r1)     // Catch:{ all -> 0x0053 }
            r6 = r7
            java.util.jar.JarOutputStream r6 = (java.util.jar.JarOutputStream) r6     // Catch:{ all -> 0x0050 }
            r5.unpack(r0, r6)     // Catch:{ all -> 0x0050 }
            r7.close()     // Catch:{ all -> 0x0065 }
            r0.delete()
            return
        L_0x0050:
            r5 = move-exception
            r1 = r7
            goto L_0x005a
        L_0x0053:
            r5 = move-exception
            r1 = r2
            goto L_0x005a
        L_0x0056:
            r5 = move-exception
            r2 = r7
            goto L_0x005a
        L_0x0059:
            r5 = move-exception
        L_0x005a:
            if (r2 == 0) goto L_0x005f
            r2.close()     // Catch:{ all -> 0x0065 }
        L_0x005f:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ all -> 0x0065 }
        L_0x0064:
            throw r5     // Catch:{ all -> 0x0065 }
        L_0x0065:
            r5 = move-exception
            r0.delete()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.compress.compressors.pack200.Pack200Utils.normalize(java.io.File, java.io.File, java.util.Map):void");
    }

    public static void normalize(File file, Map<String, String> map) throws IOException {
        normalize(file, file, map);
    }
}
