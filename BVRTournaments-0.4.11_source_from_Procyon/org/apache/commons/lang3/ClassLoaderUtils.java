// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3;

import java.util.Arrays;
import java.net.URLClassLoader;

public class ClassLoaderUtils
{
    public static String toString(final ClassLoader classLoader) {
        if (classLoader instanceof URLClassLoader) {
            return toString((URLClassLoader)classLoader);
        }
        return classLoader.toString();
    }
    
    public static String toString(final URLClassLoader classLoader) {
        return classLoader + Arrays.toString(classLoader.getURLs());
    }
}
