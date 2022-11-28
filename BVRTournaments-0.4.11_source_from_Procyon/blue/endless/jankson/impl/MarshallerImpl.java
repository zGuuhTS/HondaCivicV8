// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.Method;
import java.util.Iterator;
import blue.endless.jankson.api.annotation.Comment;
import blue.endless.jankson.api.annotation.SerializedName;
import java.util.Collection;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.annotation.Annotation;
import blue.endless.jankson.api.annotation.Serializer;
import java.lang.reflect.Array;
import blue.endless.jankson.api.element.JsonArray;
import blue.endless.jankson.api.io.DeserializationException;
import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import blue.endless.jankson.api.element.JsonPrimitive;
import blue.endless.jankson.api.element.JsonNull;
import java.util.HashMap;
import blue.endless.jankson.impl.serializer.InternalDeserializerFunction;
import blue.endless.jankson.api.DeserializerFunction;
import java.util.function.Supplier;
import blue.endless.jankson.impl.serializer.DeserializerFunctionPool;
import blue.endless.jankson.api.element.JsonElement;
import java.util.function.BiFunction;
import blue.endless.jankson.api.element.JsonObject;
import java.util.function.Function;
import java.util.Map;
import blue.endless.jankson.api.Marshaller;

@Deprecated
public class MarshallerImpl implements Marshaller
{
    private static MarshallerImpl INSTANCE;
    private Map<Class<?>, Function<Object, ?>> primitiveMarshallers;
    Map<Class<?>, Function<JsonObject, ?>> typeAdapters;
    private Map<Class<?>, BiFunction<Object, Marshaller, JsonElement>> serializers;
    private Map<Class<?>, DeserializerFunctionPool<?>> deserializers;
    private Map<Class<?>, Supplier<?>> typeFactories;
    
    public static Marshaller getFallback() {
        return MarshallerImpl.INSTANCE;
    }
    
    public <T> void register(final Class<T> clazz, final Function<Object, T> marshaller) {
        this.primitiveMarshallers.put(clazz, marshaller);
    }
    
    public <T> void registerTypeAdapter(final Class<T> clazz, final Function<JsonObject, T> adapter) {
        this.typeAdapters.put(clazz, adapter);
    }
    
    public <T> void registerSerializer(final Class<T> clazz, final Function<T, JsonElement> serializer) {
        this.serializers.put((Class<?>)clazz, (it, marshaller) -> serializer.apply(it));
    }
    
    public <T> void registerSerializer(final Class<T> clazz, final BiFunction<T, Marshaller, JsonElement> serializer) {
        this.serializers.put(clazz, (BiFunction<Object, Marshaller, JsonElement>)serializer);
    }
    
    public <T> void registerTypeFactory(final Class<T> clazz, final Supplier<T> supplier) {
        this.typeFactories.put(clazz, supplier);
    }
    
    public <A, B> void registerDeserializer(final Class<A> sourceClass, final Class<B> targetClass, final DeserializerFunction<A, B> function) {
        DeserializerFunctionPool<B> pool = (DeserializerFunctionPool<B>)this.deserializers.get(targetClass);
        if (pool == null) {
            pool = new DeserializerFunctionPool<B>(targetClass);
            this.deserializers.put(targetClass, pool);
        }
        pool.registerUnsafe(sourceClass, function);
    }
    
    public MarshallerImpl() {
        this.primitiveMarshallers = new HashMap<Class<?>, Function<Object, ?>>();
        this.typeAdapters = new HashMap<Class<?>, Function<JsonObject, ?>>();
        this.serializers = new HashMap<Class<?>, BiFunction<Object, Marshaller, JsonElement>>();
        this.deserializers = new HashMap<Class<?>, DeserializerFunctionPool<?>>();
        this.typeFactories = new HashMap<Class<?>, Supplier<?>>();
        this.register(Void.class, it -> null);
        this.register(String.class, it -> it.toString());
        this.register(Byte.class, it -> (it instanceof Number) ? Byte.valueOf(it.byteValue()) : null);
        this.register(Character.class, it -> Character.valueOf((it instanceof Number) ? ((char)it.shortValue()) : it.toString().charAt(0)));
        this.register(Short.class, it -> (it instanceof Number) ? Short.valueOf(it.shortValue()) : null);
        this.register(Integer.class, it -> (it instanceof Number) ? Integer.valueOf(it.intValue()) : null);
        this.register(Long.class, it -> (it instanceof Number) ? Long.valueOf(it.longValue()) : null);
        this.register(Float.class, it -> (it instanceof Number) ? Float.valueOf(it.floatValue()) : null);
        this.register(Double.class, it -> (it instanceof Number) ? Double.valueOf(it.doubleValue()) : null);
        this.register(Boolean.class, it -> (it instanceof Boolean) ? it : null);
        this.register(Void.TYPE, it -> null);
        this.register(Byte.TYPE, it -> (it instanceof Number) ? Byte.valueOf(it.byteValue()) : null);
        this.register(Character.TYPE, it -> Character.valueOf((it instanceof Number) ? ((char)it.shortValue()) : it.toString().charAt(0)));
        this.register(Short.TYPE, it -> (it instanceof Number) ? Short.valueOf(it.shortValue()) : null);
        this.register(Integer.TYPE, it -> (it instanceof Number) ? Integer.valueOf(it.intValue()) : null);
        this.register(Long.TYPE, it -> (it instanceof Number) ? Long.valueOf(it.longValue()) : null);
        this.register(Float.TYPE, it -> (it instanceof Number) ? Float.valueOf(it.floatValue()) : null);
        this.register(Double.TYPE, it -> (it instanceof Number) ? Double.valueOf(it.doubleValue()) : null);
        this.register(Boolean.TYPE, it -> (it instanceof Boolean) ? it : null);
        this.registerSerializer(Void.class, it -> JsonNull.INSTANCE);
        final JsonPrimitive jsonPrimitive;
        this.registerSerializer(Character.class, it -> {
            new JsonPrimitive("" + it);
            return jsonPrimitive;
        });
        this.registerSerializer(String.class, (Function<String, JsonElement>)JsonPrimitive::new);
        this.registerSerializer(Byte.class, it -> new JsonPrimitive((long)it));
        this.registerSerializer(Short.class, it -> new JsonPrimitive((long)it));
        this.registerSerializer(Integer.class, it -> new JsonPrimitive((long)it));
        this.registerSerializer(Long.class, (Function<Long, JsonElement>)JsonPrimitive::new);
        this.registerSerializer(Float.class, it -> new JsonPrimitive((double)it));
        this.registerSerializer(Double.class, (Function<Double, JsonElement>)JsonPrimitive::new);
        this.registerSerializer(Boolean.class, (Function<Boolean, JsonElement>)JsonPrimitive::new);
        this.registerSerializer(Void.TYPE, it -> JsonNull.INSTANCE);
        final JsonPrimitive jsonPrimitive2;
        this.registerSerializer(Character.TYPE, it -> {
            new JsonPrimitive("" + it);
            return jsonPrimitive2;
        });
        this.registerSerializer(Byte.TYPE, it -> new JsonPrimitive((long)it));
        this.registerSerializer(Short.TYPE, it -> new JsonPrimitive((long)it));
        this.registerSerializer(Integer.TYPE, it -> new JsonPrimitive((long)it));
        this.registerSerializer(Long.TYPE, (Function<Long, JsonElement>)JsonPrimitive::new);
        this.registerSerializer(Float.TYPE, it -> new JsonPrimitive((double)it));
        this.registerSerializer(Double.TYPE, (Function<Double, JsonElement>)JsonPrimitive::new);
        this.registerSerializer(Boolean.TYPE, (Function<Boolean, JsonElement>)JsonPrimitive::new);
    }
    
    @Nullable
    @Override
    public <T> T marshall(final Type type, final JsonElement elem) {
        if (elem == null) {
            return null;
        }
        if (elem == JsonNull.INSTANCE) {
            return null;
        }
        if (type instanceof Class) {
            try {
                return this.marshall((Class<T>)type, elem);
            }
            catch (ClassCastException t) {
                return null;
            }
        }
        if (type instanceof ParameterizedType) {
            try {
                final Class<T> clazz = (Class<T>)TypeMagic.classForType(type);
                return this.marshall(clazz, elem);
            }
            catch (ClassCastException t) {
                return null;
            }
        }
        return null;
    }
    
    @Override
    public <T> T marshall(final Class<T> clazz, final JsonElement elem) {
        try {
            return this.marshall(clazz, elem, false);
        }
        catch (Throwable t) {
            return null;
        }
    }
    
    @Override
    public <T> T marshallCarefully(final Class<T> clazz, final JsonElement elem) throws DeserializationException {
        return this.marshall(clazz, elem, true);
    }
    
    @Nullable
    public <T> T marshall(final Class<T> clazz, final JsonElement elem, final boolean failFast) throws DeserializationException {
        if (elem == null) {
            return null;
        }
        if (elem == JsonNull.INSTANCE) {
            return null;
        }
        if (clazz.isAssignableFrom(elem.getClass())) {
            return (T)elem;
        }
        DeserializerFunctionPool<T> pool = (DeserializerFunctionPool<T>)this.deserializers.get(clazz);
        if (pool != null) {
            try {
                return pool.apply(elem, this);
            }
            catch (DeserializerFunctionPool.FunctionMatchFailedException ex) {}
        }
        pool = POJODeserializer.deserializersFor(clazz);
        try {
            final T poolResult = pool.apply(elem, this);
            return poolResult;
        }
        catch (DeserializerFunctionPool.FunctionMatchFailedException ex2) {
            if (Enum.class.isAssignableFrom(clazz)) {
                if (!(elem instanceof JsonPrimitive)) {
                    return null;
                }
                final String name = ((JsonPrimitive)elem).getValue().toString();
                final T[] constants = clazz.getEnumConstants();
                if (constants == null) {
                    return null;
                }
                for (final T t : constants) {
                    if (((Enum)t).name().equals(name)) {
                        return t;
                    }
                }
            }
            if (clazz.equals(String.class)) {
                if (elem instanceof JsonObject) {
                    return (T)((JsonObject)elem).toJson(false, false);
                }
                if (elem instanceof JsonArray) {
                    return (T)((JsonArray)elem).toJson(false, false);
                }
                if (elem instanceof JsonPrimitive) {
                    ((JsonPrimitive)elem).getValue();
                    return (T)((JsonPrimitive)elem).asString();
                }
                if (elem instanceof JsonNull) {
                    return (T)"null";
                }
                if (failFast) {
                    throw new DeserializationException("Encountered unexpected JsonElement type while deserializing to string: " + elem.getClass().getCanonicalName());
                }
                return null;
            }
            else {
                if (!(elem instanceof JsonPrimitive)) {
                    if (elem instanceof JsonObject) {
                        if (clazz.isPrimitive()) {
                            throw new DeserializationException("Can't marshall json object into primitive type " + clazz.getCanonicalName());
                        }
                        if (JsonPrimitive.class.isAssignableFrom(clazz)) {
                            if (failFast) {
                                throw new DeserializationException("Can't marshall json object into a json primitive");
                            }
                            return null;
                        }
                        else {
                            final JsonObject obj = (JsonObject)elem;
                            obj.setMarshaller(this);
                            if (this.typeAdapters.containsKey(clazz)) {
                                return (T)this.typeAdapters.get(clazz).apply((JsonObject)elem);
                            }
                            if (this.typeFactories.containsKey(clazz)) {
                                final T result = (T)this.typeFactories.get(clazz).get();
                                try {
                                    POJODeserializer.unpackObject(result, obj, failFast);
                                    return result;
                                }
                                catch (Throwable t2) {
                                    if (failFast) {
                                        throw t2;
                                    }
                                    return null;
                                }
                            }
                            try {
                                final T result = TypeMagic.createAndCast(clazz, failFast);
                                POJODeserializer.unpackObject(result, obj, failFast);
                                return result;
                            }
                            catch (Throwable t3) {
                                if (failFast) {
                                    throw t3;
                                }
                                return null;
                            }
                        }
                    }
                    if (elem instanceof JsonArray) {
                        if (clazz.isPrimitive()) {
                            return null;
                        }
                        if (clazz.isArray()) {
                            final Class<?> componentType = clazz.getComponentType();
                            final JsonArray array = (JsonArray)elem;
                            final T result2 = (T)Array.newInstance(componentType, array.size());
                            for (int i = 0; i < array.size(); ++i) {
                                Array.set(result2, i, this.marshall(componentType, array.get(i)));
                            }
                            return result2;
                        }
                    }
                    return null;
                }
                final Function<Object, ?> func = this.primitiveMarshallers.get(clazz);
                if (func != null) {
                    return (T)func.apply(((JsonPrimitive)elem).getValue());
                }
                if (failFast) {
                    throw new DeserializationException("Don't know how to unpack value '" + elem.toString() + "' into target type '" + clazz.getCanonicalName() + "'");
                }
                return null;
            }
        }
    }
    
    @Override
    public JsonElement serialize(final Object obj) {
        if (obj == null) {
            return JsonNull.INSTANCE;
        }
        final BiFunction<Object, Marshaller, JsonElement> serializer = this.serializers.get(obj.getClass());
        if (serializer != null) {
            final JsonElement result = serializer.apply(obj, this);
            if (result instanceof JsonObject) {
                ((JsonObject)result).setMarshaller(this);
            }
            if (result instanceof JsonArray) {
                ((JsonArray)result).setMarshaller(this);
            }
            return result;
        }
        for (final Map.Entry<Class<?>, BiFunction<Object, Marshaller, JsonElement>> entry : this.serializers.entrySet()) {
            if (entry.getKey().isAssignableFrom(obj.getClass())) {
                final JsonElement result2 = entry.getValue().apply(obj, this);
                if (result2 instanceof JsonObject) {
                    ((JsonObject)result2).setMarshaller(this);
                }
                if (result2 instanceof JsonArray) {
                    ((JsonArray)result2).setMarshaller(this);
                }
                return result2;
            }
        }
        for (final Method m : obj.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(Serializer.class) && !Modifier.isStatic(m.getModifiers())) {
                final Class<?> clazz = m.getReturnType();
                if (JsonElement.class.isAssignableFrom(clazz)) {
                    final Parameter[] params = m.getParameters();
                    if (params.length == 0) {
                        try {
                            final boolean access = m.isAccessible();
                            if (!access) {
                                m.setAccessible(true);
                            }
                            final JsonElement result3 = (JsonElement)m.invoke(obj, new Object[0]);
                            if (!access) {
                                m.setAccessible(false);
                            }
                            if (result3 instanceof JsonObject) {
                                ((JsonObject)result3).setMarshaller(this);
                            }
                            if (result3 instanceof JsonArray) {
                                ((JsonArray)result3).setMarshaller(this);
                            }
                            return result3;
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex3) {
                            final Exception ex;
                            final Exception e = ex;
                            return JsonNull.INSTANCE;
                        }
                    }
                    if (params.length == 1 && Marshaller.class.isAssignableFrom(params[0].getType())) {
                        try {
                            final boolean access = m.isAccessible();
                            if (!access) {
                                m.setAccessible(true);
                            }
                            final JsonElement result3 = (JsonElement)m.invoke(obj, this);
                            if (!access) {
                                m.setAccessible(false);
                            }
                            if (result3 instanceof JsonObject) {
                                ((JsonObject)result3).setMarshaller(this);
                            }
                            if (result3 instanceof JsonArray) {
                                ((JsonArray)result3).setMarshaller(this);
                            }
                            return result3;
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex4) {
                            final Exception ex2;
                            final Exception e = ex2;
                            return JsonNull.INSTANCE;
                        }
                    }
                }
            }
        }
        if (obj instanceof Enum) {
            return new JsonPrimitive(((Enum)obj).name());
        }
        if (obj.getClass().isArray()) {
            final JsonArray array = new JsonArray();
            array.setMarshaller(this);
            for (int i = 0; i < Array.getLength(obj); ++i) {
                final Object elem = Array.get(obj, i);
                final JsonElement parsed = this.serialize(elem);
                array.add(parsed);
            }
            return array;
        }
        if (obj instanceof Collection) {
            final JsonArray array = new JsonArray();
            array.setMarshaller(this);
            for (final Object elem : (Collection)obj) {
                final JsonElement parsed = this.serialize(elem);
                array.add(parsed);
            }
            return array;
        }
        if (obj instanceof Map) {
            final JsonObject result4 = new JsonObject();
            for (final Map.Entry<?, ?> entry2 : ((Map)obj).entrySet()) {
                final String k = entry2.getKey().toString();
                final Object v = entry2.getValue();
                result4.put(k, this.serialize(v));
            }
            return result4;
        }
        final JsonObject result4 = new JsonObject();
        for (final Field f : obj.getClass().getFields()) {
            if (!Modifier.isStatic(f.getModifiers())) {
                if (!Modifier.isTransient(f.getModifiers())) {
                    f.setAccessible(true);
                    try {
                        final Object child = f.get(obj);
                        String name = f.getName();
                        final SerializedName nameAnnotation = f.getAnnotation(SerializedName.class);
                        if (nameAnnotation != null) {
                            name = nameAnnotation.value();
                        }
                        final Comment comment = f.getAnnotation(Comment.class);
                        if (comment == null) {
                            result4.put(name, this.serialize(child));
                        }
                        else {
                            result4.put(name, this.serialize(child), comment.value());
                        }
                    }
                    catch (IllegalArgumentException ex5) {}
                    catch (IllegalAccessException ex6) {}
                }
            }
        }
        for (final Field f : obj.getClass().getDeclaredFields()) {
            if (!Modifier.isPublic(f.getModifiers()) && !Modifier.isStatic(f.getModifiers())) {
                if (!Modifier.isTransient(f.getModifiers())) {
                    f.setAccessible(true);
                    try {
                        final Object child = f.get(obj);
                        String name = f.getName();
                        final SerializedName nameAnnotation = f.getAnnotation(SerializedName.class);
                        if (nameAnnotation != null) {
                            name = nameAnnotation.value();
                        }
                        final Comment comment = f.getAnnotation(Comment.class);
                        if (comment == null) {
                            result4.put(name, this.serialize(child));
                        }
                        else {
                            result4.put(name, this.serialize(child), comment.value());
                        }
                    }
                    catch (IllegalArgumentException ex7) {}
                    catch (IllegalAccessException ex8) {}
                }
            }
        }
        return result4;
    }
    
    static {
        MarshallerImpl.INSTANCE = new MarshallerImpl();
    }
}
