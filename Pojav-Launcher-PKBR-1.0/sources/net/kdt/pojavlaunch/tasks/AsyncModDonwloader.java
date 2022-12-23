package net.kdt.pojavlaunch.tasks;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class AsyncModDonwloader extends Thread implements Runnable {
    private DownloaderCallback listener = null;
    private String path;
    private String url;

    public interface DownloaderCallback {
        void onError(String str);

        void onFinish();

        void onProgress(int i);
    }

    public AsyncModDonwloader(String path2, String url2) {
        this.path = path2;
        this.url = url2;
    }

    public void run() {
        try {
            URL url2 = new URL(this.url);
            URLConnection urlConnection = url2.openConnection();
            urlConnection.connect();
            String filename = urlConnection.getHeaderField("Content-Disposition");
            String filename2 = filename.substring(filename.indexOf("filename") + 10, filename.length() - 2);
            int total = urlConnection.getContentLength();
            InputStream input = new BufferedInputStream(url2.openStream());
            OutputStream output = new FileOutputStream(this.path + "/" + filename2);
            byte[] data = new byte[4096];
            long current = 0;
            while (true) {
                int read = input.read(data);
                int count = read;
                if (read == -1) {
                    break;
                }
                current += (long) count;
                DownloaderCallback downloaderCallback = this.listener;
                if (downloaderCallback != null) {
                    downloaderCallback.onProgress((int) ((100 * current) / ((long) total)));
                }
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            DownloaderCallback downloaderCallback2 = this.listener;
            if (downloaderCallback2 != null) {
                downloaderCallback2.onFinish();
            }
        } catch (Exception e) {
            DownloaderCallback downloaderCallback3 = this.listener;
            if (downloaderCallback3 != null) {
                downloaderCallback3.onError(e.getMessage());
            }
        }
    }

    public void setDownloaderCallback(DownloaderCallback listener2) {
        this.listener = listener2;
    }
}
