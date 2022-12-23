package net.kdt.pojavlaunch.utils;

import androidx.core.internal.view.SupportMenu;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import net.kdt.pojavlaunch.Tools;
import net.objecthunter.exp4j.operator.Operator;
import org.apache.commons.p012io.IOUtils;

public class DownloadUtils {
    public static final String USER_AGENT = Tools.APP_NAME;
    public static final Charset utf8 = Charset.forName("UTF-8");

    public static void download(String url, OutputStream os) throws IOException {
        download(new URL(url), os);
    }

    public static void download(URL url, OutputStream os) throws IOException {
        InputStream is = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setConnectTimeout(Operator.PRECEDENCE_POWER);
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() == 200) {
                InputStream is2 = conn.getInputStream();
                IOUtils.copy(is2, os);
                if (is2 != null) {
                    try {
                        is2.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                throw new IOException("Server returned HTTP " + conn.getResponseCode() + ": " + conn.getResponseMessage());
            }
        } catch (IOException e2) {
            throw new IOException("Unable to download from " + url.toString(), e2);
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String downloadString(String url) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        download(url, (OutputStream) bos);
        bos.close();
        return new String(bos.toByteArray(), utf8);
    }

    public static void downloadFile(String url, File out) throws IOException {
        out.getParentFile().mkdirs();
        File tempOut = File.createTempFile(out.getName(), ".part", out.getParentFile());
        BufferedOutputStream bos = null;
        try {
            OutputStream bos2 = new BufferedOutputStream(new FileOutputStream(tempOut));
            download(url, bos2);
            tempOut.renameTo(out);
            bos2.close();
            if (tempOut.exists()) {
                tempOut.delete();
            }
        } catch (IOException th2) {
            if (bos != null) {
                bos.close();
            }
            if (tempOut.exists()) {
                tempOut.delete();
            }
            throw th2;
        } catch (IOException th3) {
            if (bos != null) {
                bos.close();
            }
            if (tempOut.exists()) {
                tempOut.delete();
            }
            throw th3;
        }
    }

    public static void downloadFileMonitored(String urlInput, String nameOutput, byte[] buffer, Tools.DownloaderFeedback monitor) throws IOException {
        downloadFileMonitored(urlInput, new File(nameOutput), buffer, monitor);
    }

    public static void downloadFileMonitored(String urlInput, File outputFile, byte[] buffer, Tools.DownloaderFeedback monitor) throws IOException {
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
        }
        HttpURLConnection conn = (HttpURLConnection) new URL(urlInput).openConnection();
        InputStream readStr = conn.getInputStream();
        FileOutputStream fos = new FileOutputStream(outputFile);
        int oval = 0;
        int len = conn.getContentLength();
        if (buffer == null) {
            buffer = new byte[SupportMenu.USER_MASK];
        }
        while (true) {
            int read = readStr.read(buffer);
            int cur = read;
            if (read != -1) {
                oval += cur;
                fos.write(buffer, 0, cur);
                monitor.updateProgress(oval, len);
            } else {
                fos.close();
                conn.disconnect();
                return;
            }
        }
    }

    public static void downloadFileMonitoredWithHeaders(String urlInput, File outputFile, byte[] buffer, Tools.DownloaderFeedback monitor, String userAgent, String cookies) throws IOException {
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
        }
        HttpURLConnection conn = (HttpURLConnection) new URL(urlInput).openConnection();
        conn.setRequestProperty("User-Agent", userAgent);
        conn.setRequestProperty("Cookies", cookies);
        InputStream readStr = conn.getInputStream();
        FileOutputStream fos = new FileOutputStream(outputFile);
        int oval = 0;
        int len = conn.getContentLength();
        if (buffer == null) {
            buffer = new byte[SupportMenu.USER_MASK];
        }
        while (true) {
            int read = readStr.read(buffer);
            int cur = read;
            if (read != -1) {
                oval += cur;
                fos.write(buffer, 0, cur);
                monitor.updateProgress(oval, len);
            } else {
                fos.close();
                conn.disconnect();
                return;
            }
        }
    }
}
