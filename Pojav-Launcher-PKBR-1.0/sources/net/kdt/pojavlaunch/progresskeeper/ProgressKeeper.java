package net.kdt.pojavlaunch.progresskeeper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProgressKeeper {
    private static final ConcurrentHashMap<String, ConcurrentLinkedQueue<ProgressListener>> sProgressListeners = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, ProgressState> sProgressStates = new ConcurrentHashMap<>();
    private static final ArrayList<TaskCountListener> sTaskCountListeners = new ArrayList<>();

    public static void submitProgress(String progressRecord, int progress, int resid, Object... va) {
        ConcurrentHashMap<String, ProgressState> concurrentHashMap = sProgressStates;
        ProgressState progressState = concurrentHashMap.get(progressRecord);
        boolean shouldCallEnded = true;
        boolean shouldCallStarted = progressState == null;
        if (!(resid == -1 && progress == -1)) {
            shouldCallEnded = false;
        }
        if (shouldCallEnded) {
            shouldCallStarted = false;
            concurrentHashMap.remove(progressRecord);
            updateTaskCount();
        } else if (shouldCallStarted) {
            ProgressState progressState2 = new ProgressState();
            progressState = progressState2;
            concurrentHashMap.put(progressRecord, progressState2);
            updateTaskCount();
        }
        if (progressState != null) {
            progressState.progress = progress;
            progressState.resid = resid;
            progressState.varArg = va;
        }
        ConcurrentLinkedQueue<ProgressListener> progressListeners = sProgressListeners.get(progressRecord);
        if (progressListeners != null) {
            Iterator<ProgressListener> it = progressListeners.iterator();
            while (it.hasNext()) {
                ProgressListener listener = it.next();
                if (shouldCallStarted) {
                    listener.onProgressStarted();
                } else if (shouldCallEnded) {
                    listener.onProgressEnded();
                } else {
                    listener.onProgressUpdated(progress, resid, va);
                }
            }
        }
    }

    private static void updateTaskCount() {
        int count = sProgressStates.size();
        Iterator<TaskCountListener> it = sTaskCountListeners.iterator();
        while (it.hasNext()) {
            it.next().onUpdateTaskCount(count);
        }
    }

    public static void addListener(String progressRecord, ProgressListener listener) {
        ProgressState state = sProgressStates.get(progressRecord);
        if (state == null || (state.resid == -1 && state.progress == -1)) {
            listener.onProgressEnded();
        } else {
            listener.onProgressStarted();
            listener.onProgressUpdated(state.progress, state.resid, state.varArg);
        }
        ConcurrentHashMap<String, ConcurrentLinkedQueue<ProgressListener>> concurrentHashMap = sProgressListeners;
        ConcurrentLinkedQueue<ProgressListener> listenerWeakReferenceList = concurrentHashMap.get(progressRecord);
        if (listenerWeakReferenceList == null) {
            ConcurrentLinkedQueue<ProgressListener> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
            listenerWeakReferenceList = concurrentLinkedQueue;
            concurrentHashMap.put(progressRecord, concurrentLinkedQueue);
        }
        listenerWeakReferenceList.add(listener);
    }

    public static void removeListener(String progressRecord, ProgressListener listener) {
        ConcurrentLinkedQueue<ProgressListener> listenerWeakReferenceList = sProgressListeners.get(progressRecord);
        if (listenerWeakReferenceList != null) {
            listenerWeakReferenceList.remove(listener);
        }
    }

    public static void addTaskCountListener(TaskCountListener listener) {
        listener.onUpdateTaskCount(sProgressStates.size());
        ArrayList<TaskCountListener> arrayList = sTaskCountListeners;
        if (!arrayList.contains(listener)) {
            arrayList.add(listener);
        }
    }

    public static void addTaskCountListener(TaskCountListener listener, boolean runUpdate) {
        if (runUpdate) {
            listener.onUpdateTaskCount(sProgressStates.size());
        }
        ArrayList<TaskCountListener> arrayList = sTaskCountListeners;
        if (!arrayList.contains(listener)) {
            arrayList.add(listener);
        }
    }

    public static void removeTaskCountListener(TaskCountListener listener) {
        sTaskCountListeners.remove(listener);
    }

    public static int getTaskCount() {
        return sProgressStates.size();
    }
}
