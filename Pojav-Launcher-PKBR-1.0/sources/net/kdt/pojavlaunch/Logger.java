package net.kdt.pojavlaunch;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.ref.WeakReference;

public class Logger {
    private static Logger sLoggerSingleton = null;
    private final File mLogFile;
    private WeakReference<eventLogListener> mLogListenerWeakReference;
    private PrintStream mLogStream;

    public interface eventLogListener {
        void onEventLogged(String str);
    }

    private Logger() {
        this("latestlog.txt");
    }

    private Logger(String fileName) {
        this.mLogListenerWeakReference = null;
        File file = new File(Tools.DIR_GAME_HOME, fileName);
        this.mLogFile = file;
        file.delete();
        try {
            file.createNewFile();
            this.mLogStream = new PrintStream(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        if (sLoggerSingleton == null) {
            synchronized (Logger.class) {
                if (sLoggerSingleton == null) {
                    sLoggerSingleton = new Logger();
                }
            }
        }
        return sLoggerSingleton;
    }

    public void appendToLog(String text) {
        if (!shouldCensorLog(text)) {
            appendToLogUnchecked(text);
        }
    }

    public void appendToLogUnchecked(String text) {
        this.mLogStream.println(text);
        notifyLogListener(text);
    }

    public void reset() {
        try {
            this.mLogFile.delete();
            this.mLogFile.createNewFile();
            this.mLogStream = new PrintStream(this.mLogFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.mLogStream.close();
    }

    private static boolean shouldCensorLog(String text) {
        if (text.contains("Session ID is")) {
            return true;
        }
        return false;
    }

    public void setLogListener(eventLogListener logListener) {
        this.mLogListenerWeakReference = new WeakReference<>(logListener);
    }

    private void notifyLogListener(String text) {
        WeakReference<eventLogListener> weakReference = this.mLogListenerWeakReference;
        if (weakReference != null) {
            eventLogListener logListener = (eventLogListener) weakReference.get();
            if (logListener == null) {
                this.mLogListenerWeakReference = null;
            } else {
                logListener.onEventLogged(text);
            }
        }
    }
}
