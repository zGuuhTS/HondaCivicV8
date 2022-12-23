package org.apache.commons.p012io.monitor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;

/* renamed from: org.apache.commons.io.monitor.FileAlterationMonitor */
public final class FileAlterationMonitor implements Runnable {
    private final long interval;
    private final List<FileAlterationObserver> observers;
    private volatile boolean running;
    private Thread thread;
    private ThreadFactory threadFactory;

    public FileAlterationMonitor() {
        this(10000);
    }

    public FileAlterationMonitor(long j) {
        this.observers = new CopyOnWriteArrayList();
        this.thread = null;
        this.running = false;
        this.interval = j;
    }

    public FileAlterationMonitor(long j, FileAlterationObserver... fileAlterationObserverArr) {
        this(j);
        if (fileAlterationObserverArr != null) {
            for (FileAlterationObserver addObserver : fileAlterationObserverArr) {
                addObserver(addObserver);
            }
        }
    }

    public void addObserver(FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            this.observers.add(fileAlterationObserver);
        }
    }

    public long getInterval() {
        return this.interval;
    }

    public Iterable<FileAlterationObserver> getObservers() {
        return this.observers;
    }

    public void removeObserver(FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            do {
            } while (this.observers.remove(fileAlterationObserver));
        }
    }

    public void run() {
        while (this.running) {
            for (FileAlterationObserver checkAndNotify : this.observers) {
                checkAndNotify.checkAndNotify();
            }
            if (this.running) {
                try {
                    Thread.sleep(this.interval);
                } catch (InterruptedException e) {
                }
            } else {
                return;
            }
        }
    }

    public void setThreadFactory(ThreadFactory threadFactory2) {
        synchronized (this) {
            this.threadFactory = threadFactory2;
        }
    }

    public void start() throws Exception {
        synchronized (this) {
            if (!this.running) {
                for (FileAlterationObserver initialize : this.observers) {
                    initialize.initialize();
                }
                this.running = true;
                ThreadFactory threadFactory2 = this.threadFactory;
                this.thread = threadFactory2 != null ? threadFactory2.newThread(this) : new Thread(this);
                this.thread.start();
            } else {
                throw new IllegalStateException("Monitor is already running");
            }
        }
    }

    public void stop() throws Exception {
        synchronized (this) {
            stop(this.interval);
        }
    }

    public void stop(long j) throws Exception {
        synchronized (this) {
            if (this.running) {
                this.running = false;
                try {
                    this.thread.join(j);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                for (FileAlterationObserver destroy : this.observers) {
                    destroy.destroy();
                }
            } else {
                throw new IllegalStateException("Monitor is not running");
            }
        }
    }
}
