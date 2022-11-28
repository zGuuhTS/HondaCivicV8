// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.relocated.blue.endless.jankson.impl;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nonnull;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.serializer.InternalDeserializerFunction;
import java.lang.reflect.Parameter;
import java.lang.reflect.Method;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.annotation.Deserializer;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.impl.serializer.DeserializerFunctionPool;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonArray;
import java.util.Iterator;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonPrimitive;
import java.util.Collection;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import javax.annotation.Nullable;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Marshaller;
import java.lang.reflect.Type;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonNull;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.annotation.SerializedName;
import java.lang.reflect.Field;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.io.DeserializationException;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.JsonGrammar;
import java.lang.reflect.Modifier;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonObject;

public class POJODeserializer
{
    public static void unpackObject(final Object target, final JsonObject source) {
        try {
            unpackObject(target, source, false);
        }
        catch (Throwable t) {}
    }
    
    public static void unpackObject(final Object target, final JsonObject source, final boolean failFast) throws DeserializationException {
        final JsonObject work = source.clone();
        for (final Field f : target.getClass().getDeclaredFields()) {
            final int modifiers = f.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                if (!Modifier.isTransient(modifiers)) {
                    unpackField(target, f, work, failFast);
                }
            }
        }
        for (final Field f : target.getClass().getFields()) {
            final int modifiers = f.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                if (!Modifier.isTransient(modifiers)) {
                    unpackField(target, f, work, failFast);
                }
            }
        }
        if (!work.isEmpty() && failFast) {
            throw new DeserializationException("There was data that couldn't be applied to the destination object: " + work.toJson(JsonGrammar.STRICT));
        }
    }
    
    public static void unpackField(final Object parent, final Field f, final JsonObject source, final boolean failFast) throws DeserializationException {
        String fieldName = f.getName();
        final SerializedName nameAnnotation = f.getAnnotation(SerializedName.class);
        if (nameAnnotation != null) {
            fieldName = nameAnnotation.value();
        }
        if (source.containsKey(fieldName)) {
            final JsonElement elem = source.get(fieldName);
            source.remove(fieldName);
            if (elem == null || elem == JsonNull.INSTANCE) {
                final boolean accessible = f.isAccessible();
                if (!accessible) {
                    f.setAccessible(true);
                }
                try {
                    f.set(parent, null);
                    if (!accessible) {
                        f.setAccessible(false);
                    }
                }
                catch (IllegalArgumentException | IllegalAccessException ex3) {
                    final Exception ex2;
                    final Exception ex = ex2;
                    if (failFast) {
                        throw new DeserializationException("Couldn't set field \"" + f.getName() + "\" of class \"" + parent.getClass().getCanonicalName() + "\"", ex);
                    }
                }
            }
            else {
                try {
                    unpackFieldData(parent, f, elem, source.getMarshaller());
                }
                catch (Throwable t) {
                    if (failFast) {
                        throw new DeserializationException("There was a problem unpacking field " + f.getName() + " of class " + parent.getClass().getCanonicalName(), t);
                    }
                }
            }
        }
    }
    
    @Nullable
    public static Object unpack(final Type t, final JsonElement elem, final Marshaller marshaller) {
        final Class<?> rawClass = TypeMagic.classForType(t);
        if (rawClass.isPrimitive()) {
            return null;
        }
        if (elem == null) {
            return null;
        }
        return null;
    }
    
    public static boolean unpackFieldData(final Object parent, final Field field, final JsonElement elem, final Marshaller marshaller) throws Throwable {
        if (elem == null) {
            return true;
        }
        try {
            field.setAccessible(true);
        }
        catch (Throwable t) {
            return false;
        }
        if (elem == JsonNull.INSTANCE) {
            field.set(parent, null);
            return true;
        }
        final Class<?> fieldClass = field.getType();
        if (!isCollections(fieldClass)) {
            final Object result = marshaller.marshallCarefully(fieldClass, elem);
            field.set(parent, result);
            return true;
        }
        if (field.get(parent) == null) {
            final Object fieldValue = TypeMagic.createAndCast(field.getGenericType());
            if (fieldValue == null) {
                return false;
            }
            field.set(parent, fieldValue);
        }
        if (Map.class.isAssignableFrom(fieldClass)) {
            final Type[] parameters = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
            unpackMap((Map<Object, Object>)field.get(parent), parameters[0], parameters[1], elem, marshaller);
            return true;
        }
        if (Collection.class.isAssignableFrom(fieldClass)) {
            final Type elementType = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
            unpackCollection((Collection<Object>)field.get(parent), elementType, elem, marshaller);
            return true;
        }
        return false;
    }
    
    private static boolean isCollections(final Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz) || Collection.class.isAssignableFrom(clazz);
    }
    
    public static void unpackMap(final Map<Object, Object> map, final Type keyType, final Type valueType, final JsonElement elem, final Marshaller marshaller) throws DeserializationException {
        if (!(elem instanceof JsonObject)) {
            throw new DeserializationException("Cannot deserialize a " + elem.getClass().getSimpleName() + " into a Map - expected a JsonObject!");
        }
        final JsonObject object = (JsonObject)elem;
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            try {
                final Object k = marshaller.marshall(keyType, new JsonPrimitive(entry.getKey()));
                final Object v = marshaller.marshall(valueType, entry.getValue());
                if (k == null || v == null) {
                    continue;
                }
                map.put(k, v);
            }
            catch (Throwable t) {}
        }
    }
    
    public static void unpackCollection(final Collection<Object> collection, final Type elementType, final JsonElement elem, final Marshaller marshaller) throws DeserializationException {
        if (!(elem instanceof JsonArray)) {
            throw new DeserializationException("Cannot deserialize a " + elem.getClass().getSimpleName() + " into a Set - expected a JsonArray!");
        }
        final JsonArray array = (JsonArray)elem;
        for (final JsonElement arrayElem : array) {
            final Object o = marshaller.marshall(elementType, arrayElem);
            if (o != null) {
                collection.add(o);
            }
        }
    }
    
    protected static <B> DeserializerFunctionPool<B> deserializersFor(final Class<B> targetClass) {
        final DeserializerFunctionPool<B> pool = new DeserializerFunctionPool<B>(targetClass);
        for (final Method m : targetClass.getDeclaredMethods()) {
            if (m.getAnnotation(Deserializer.class) != null) {
                if (Modifier.isStatic(m.getModifiers())) {
                    if (m.getReturnType().equals(targetClass)) {
                        final Parameter[] params = m.getParameters();
                        if (params.length >= 1) {
                            final Class<?> sourceClass = params[0].getType();
                            final InternalDeserializerFunction<B> deserializer = makeDeserializer(m, sourceClass, targetClass);
                            if (deserializer != null) {
                                pool.registerUnsafe(sourceClass, deserializer);
                            }
                        }
                    }
                }
            }
        }
        return pool;
    }
    
    @Nullable
    private static <A, B> InternalDeserializerFunction<B> makeDeserializer(@Nonnull final Method m, @Nonnull final Class<A> sourceClass, @Nonnull final Class<B> targetClass) {
        if (!m.getReturnType().equals(targetClass)) {
            return null;
        }
        final Parameter[] params = m.getParameters();
        if (params.length == 1) {
            final Exception ex3;
            Exception ex;
            return (InternalDeserializerFunction<B>)((o, marshaller) -> {
                try {
                    return m.invoke(null, o);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex5) {
                    ex = ex3;
                    throw new DeserializationException(ex);
                }
            });
        }
        if (params.length != 2) {
            return null;
        }
        if (params[1].getType().equals(Marshaller.class)) {
            final Exception ex4;
            Exception ex2;
            return (InternalDeserializerFunction<B>)((o, marshaller) -> {
                try {
                    return m.invoke(null, o, marshaller);
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex6) {
                    ex2 = ex4;
                    throw new DeserializationException(ex2);
                }
            });
        }
        return null;
    }
}
