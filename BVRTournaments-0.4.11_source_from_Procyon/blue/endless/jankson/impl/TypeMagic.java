// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl;

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.lang.reflect.Constructor;
import blue.endless.jankson.api.io.DeserializationException;
import javax.annotation.Nullable;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class TypeMagic
{
    private static Map<Class<?>, Class<?>> concreteClasses;
    
    @Nullable
    public static Class<?> classForType(final Type t) {
        if (t instanceof Class) {
            return (Class<?>)t;
        }
        if (t instanceof ParameterizedType) {
            final Type subtype = ((ParameterizedType)t).getRawType();
            if (subtype instanceof Class) {
                return (Class<?>)subtype;
            }
            String className = t.getTypeName();
            final int typeParamStart = className.indexOf(60);
            if (typeParamStart >= 0) {
                className = className.substring(0, typeParamStart);
            }
            try {
                return Class.forName(className);
            }
            catch (ClassNotFoundException ex3) {}
        }
        if (t instanceof WildcardType) {
            final Type[] upperBounds = ((WildcardType)t).getUpperBounds();
            if (upperBounds.length == 0) {
                return Object.class;
            }
            return classForType(upperBounds[0]);
        }
        else {
            if (t instanceof TypeVariable) {
                return Object.class;
            }
            if (t instanceof GenericArrayType) {
                final GenericArrayType arrayType = (GenericArrayType)t;
                final Class<?> componentClass = classForType(arrayType.getGenericComponentType());
                try {
                    final Class<?> arrayClass = Class.forName("[L" + componentClass.getCanonicalName() + ";");
                    return arrayClass;
                }
                catch (ClassNotFoundException ex2) {
                    return Object[].class;
                }
            }
            return null;
        }
    }
    
    @Nullable
    public static <U> U createAndCast(final Type t) {
        try {
            return createAndCast(classForType(t), false);
        }
        catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static <U> U createAndCastCarefully(final Type t) throws DeserializationException {
        return (U)createAndCast(classForType(t));
    }
    
    @Nullable
    public static <U> U createAndCast(final Class<U> t, final boolean failFast) throws DeserializationException {
        if (t.isInterface()) {
            final Class<?> substitute = TypeMagic.concreteClasses.get(t);
            if (substitute != null) {
                try {
                    return createAndCast(substitute);
                }
                catch (Throwable ex) {
                    return null;
                }
            }
        }
        Constructor<U> noArg = null;
        try {
            noArg = t.getConstructor((Class<?>[])new Class[0]);
        }
        catch (Throwable ex2) {
            try {
                noArg = t.getDeclaredConstructor((Class<?>[])new Class[0]);
            }
            catch (Throwable ex3) {
                if (failFast) {
                    throw new DeserializationException("Class " + t.getCanonicalName() + " doesn't have a no-arg constructor, so an instance can't be created.");
                }
                return null;
            }
        }
        try {
            final boolean available = noArg.isAccessible();
            if (!available) {
                noArg.setAccessible(true);
            }
            final U u = noArg.newInstance(new Object[0]);
            if (!available) {
                noArg.setAccessible(false);
            }
            return u;
        }
        catch (Throwable ex) {
            if (failFast) {
                throw new DeserializationException("An error occurred while creating an object.", ex);
            }
            return null;
        }
    }
    
    public static <T> T shoehorn(final Object o) {
        return (T)o;
    }
    
    static {
        (TypeMagic.concreteClasses = new HashMap<Class<?>, Class<?>>()).put(Map.class, HashMap.class);
        TypeMagic.concreteClasses.put(Set.class, HashSet.class);
        TypeMagic.concreteClasses.put(Collection.class, ArrayList.class);
        TypeMagic.concreteClasses.put(List.class, ArrayList.class);
        TypeMagic.concreteClasses.put(Queue.class, ArrayDeque.class);
        TypeMagic.concreteClasses.put(Deque.class, ArrayDeque.class);
    }
}
