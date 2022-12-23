package net.kdt.pojavlaunch.extra;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class ExtraCore {
    private static ExtraCore sExtraCoreSingleton = null;
    private final Map<String, ConcurrentLinkedQueue<WeakReference<ExtraListener>>> mListenerMap = new ConcurrentHashMap();
    private final Map<String, Object> mValueMap = new ConcurrentHashMap();

    private ExtraCore() {
    }

    private static ExtraCore getInstance() {
        if (sExtraCoreSingleton == null) {
            synchronized (ExtraCore.class) {
                if (sExtraCoreSingleton == null) {
                    sExtraCoreSingleton = new ExtraCore();
                }
            }
        }
        return sExtraCoreSingleton;
    }

    public static void setValue(String key, Object value) {
        if (value != null && key != null) {
            getInstance().mValueMap.put(key, value);
            ConcurrentLinkedQueue<WeakReference<ExtraListener>> extraListenerList = getInstance().mListenerMap.get(key);
            if (extraListenerList != null) {
                Iterator<WeakReference<ExtraListener>> it = extraListenerList.iterator();
                while (it.hasNext()) {
                    WeakReference<ExtraListener> listener = it.next();
                    if (listener.get() == null) {
                        extraListenerList.remove(listener);
                    } else if (((ExtraListener) listener.get()).onValueSet(key, value)) {
                        removeExtraListenerFromValue(key, (ExtraListener) listener.get());
                    }
                }
            }
        }
    }

    public static Object getValue(String key) {
        return getInstance().mValueMap.get(key);
    }

    public static Object getValue(String key, Object defaultValue) {
        Object value = getInstance().mValueMap.get(key);
        return value != null ? value : defaultValue;
    }

    public static void removeValue(String key) {
        getInstance().mValueMap.remove(key);
    }

    public static Object consumeValue(String key) {
        Object value = getInstance().mValueMap.get(key);
        getInstance().mValueMap.remove(key);
        return value;
    }

    public static void removeAllValues() {
        getInstance().mValueMap.clear();
    }

    public static void addExtraListener(String key, ExtraListener listener) {
        ConcurrentLinkedQueue<WeakReference<ExtraListener>> listenerList = getInstance().mListenerMap.get(key);
        if (listenerList == null) {
            listenerList = new ConcurrentLinkedQueue<>();
            getInstance().mListenerMap.put(key, listenerList);
        }
        listenerList.add(new WeakReference(listener));
    }

    public static void removeExtraListenerFromValue(String key, ExtraListener listener) {
        ConcurrentLinkedQueue<WeakReference<ExtraListener>> listenerList = getInstance().mListenerMap.get(key);
        if (listenerList == null) {
            listenerList = new ConcurrentLinkedQueue<>();
            getInstance().mListenerMap.put(key, listenerList);
        }
        Iterator<WeakReference<ExtraListener>> it = listenerList.iterator();
        while (it.hasNext()) {
            WeakReference<ExtraListener> listenerWeakReference = it.next();
            ExtraListener actualListener = (ExtraListener) listenerWeakReference.get();
            if (actualListener == null || actualListener == listener) {
                listenerList.remove(listenerWeakReference);
            }
        }
    }

    public static void removeAllExtraListenersFromValue(String key) {
        ConcurrentLinkedQueue<WeakReference<ExtraListener>> listenerList = getInstance().mListenerMap.get(key);
        if (listenerList == null) {
            listenerList = new ConcurrentLinkedQueue<>();
            getInstance().mListenerMap.put(key, listenerList);
        }
        listenerList.clear();
    }

    public static void removeAllExtraListeners() {
        getInstance().mListenerMap.clear();
    }
}
