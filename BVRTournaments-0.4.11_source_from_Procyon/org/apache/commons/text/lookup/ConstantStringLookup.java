// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.util.Objects;
import org.apache.commons.lang3.ClassUtils;
import java.util.concurrent.ConcurrentHashMap;

class ConstantStringLookup extends AbstractStringLookup
{
    private static final ConcurrentHashMap<String, String> CONSTANT_CACHE;
    private static final char FIELD_SEPARATOR = '.';
    static final ConstantStringLookup INSTANCE;
    
    static void clear() {
        ConstantStringLookup.CONSTANT_CACHE.clear();
    }
    
    protected Class<?> fetchClass(final String className) throws ClassNotFoundException {
        return ClassUtils.getClass(className);
    }
    
    @Override
    public synchronized String lookup(final String key) {
        if (key == null) {
            return null;
        }
        String result = ConstantStringLookup.CONSTANT_CACHE.get(key);
        if (result != null) {
            return result;
        }
        final int fieldPos = key.lastIndexOf(46);
        if (fieldPos < 0) {
            return null;
        }
        try {
            final Object value = this.resolveField(key.substring(0, fieldPos), key.substring(fieldPos + 1));
            if (value != null) {
                final String string = Objects.toString(value, null);
                ConstantStringLookup.CONSTANT_CACHE.put(key, string);
                result = string;
            }
        }
        catch (Exception ex) {
            return null;
        }
        return result;
    }
    
    protected Object resolveField(final String className, final String fieldName) throws Exception {
        final Class<?> clazz = this.fetchClass(className);
        if (clazz == null) {
            return null;
        }
        return clazz.getField(fieldName).get(null);
    }
    
    static {
        CONSTANT_CACHE = new ConcurrentHashMap<String, String>();
        INSTANCE = new ConstantStringLookup();
    }
}
