// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.util.function.Function;
import java.util.Comparator;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import org.apache.commons.lang3.Validate;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Collection;

public class ReflectionToStringBuilder extends ToStringBuilder
{
    private boolean appendStatics;
    private boolean appendTransients;
    private boolean excludeNullValues;
    protected String[] excludeFieldNames;
    private Class<?> upToClass;
    
    public static String toString(final Object object) {
        return toString(object, null, false, false, null);
    }
    
    public static String toString(final Object object, final ToStringStyle style) {
        return toString(object, style, false, false, null);
    }
    
    public static String toString(final Object object, final ToStringStyle style, final boolean outputTransients) {
        return toString(object, style, outputTransients, false, null);
    }
    
    public static String toString(final Object object, final ToStringStyle style, final boolean outputTransients, final boolean outputStatics) {
        return toString(object, style, outputTransients, outputStatics, null);
    }
    
    public static <T> String toString(final T object, final ToStringStyle style, final boolean outputTransients, final boolean outputStatics, final Class<? super T> reflectUpToClass) {
        return new ReflectionToStringBuilder((T)object, style, null, (Class<? super T>)reflectUpToClass, outputTransients, outputStatics).toString();
    }
    
    public static <T> String toString(final T object, final ToStringStyle style, final boolean outputTransients, final boolean outputStatics, final boolean excludeNullValues, final Class<? super T> reflectUpToClass) {
        return new ReflectionToStringBuilder((T)object, style, null, (Class<? super T>)reflectUpToClass, outputTransients, outputStatics, excludeNullValues).toString();
    }
    
    public static String toStringExclude(final Object object, final Collection<String> excludeFieldNames) {
        return toStringExclude(object, toNoNullStringArray(excludeFieldNames));
    }
    
    static String[] toNoNullStringArray(final Collection<String> collection) {
        if (collection == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return toNoNullStringArray(collection.toArray());
    }
    
    static String[] toNoNullStringArray(final Object[] array) {
        final List<String> list = new ArrayList<String>(array.length);
        for (final Object e : array) {
            if (e != null) {
                list.add(e.toString());
            }
        }
        return list.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
    }
    
    public static String toStringExclude(final Object object, final String... excludeFieldNames) {
        return new ReflectionToStringBuilder(object).setExcludeFieldNames(excludeFieldNames).toString();
    }
    
    private static Object checkNotNull(final Object obj) {
        return Validate.notNull(obj, "The Object passed in should not be null.", new Object[0]);
    }
    
    public ReflectionToStringBuilder(final Object object) {
        super(checkNotNull(object));
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }
    
    public ReflectionToStringBuilder(final Object object, final ToStringStyle style) {
        super(checkNotNull(object), style);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }
    
    public ReflectionToStringBuilder(final Object object, final ToStringStyle style, final StringBuffer buffer) {
        super(checkNotNull(object), style, buffer);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
    }
    
    public <T> ReflectionToStringBuilder(final T object, final ToStringStyle style, final StringBuffer buffer, final Class<? super T> reflectUpToClass, final boolean outputTransients, final boolean outputStatics) {
        super(checkNotNull(object), style, buffer);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
        this.setUpToClass(reflectUpToClass);
        this.setAppendTransients(outputTransients);
        this.setAppendStatics(outputStatics);
    }
    
    public <T> ReflectionToStringBuilder(final T object, final ToStringStyle style, final StringBuffer buffer, final Class<? super T> reflectUpToClass, final boolean outputTransients, final boolean outputStatics, final boolean excludeNullValues) {
        super(checkNotNull(object), style, buffer);
        this.appendStatics = false;
        this.appendTransients = false;
        this.upToClass = null;
        this.setUpToClass(reflectUpToClass);
        this.setAppendTransients(outputTransients);
        this.setAppendStatics(outputStatics);
        this.setExcludeNullValues(excludeNullValues);
    }
    
    protected boolean accept(final Field field) {
        return field.getName().indexOf(36) == -1 && (!Modifier.isTransient(field.getModifiers()) || this.isAppendTransients()) && (!Modifier.isStatic(field.getModifiers()) || this.isAppendStatics()) && (this.excludeFieldNames == null || Arrays.binarySearch(this.excludeFieldNames, field.getName()) < 0) && !field.isAnnotationPresent(ToStringExclude.class);
    }
    
    protected void appendFieldsIn(final Class<?> clazz) {
        if (clazz.isArray()) {
            this.reflectionAppendArray(this.getObject());
            return;
        }
        final Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing((Function<? super Field, ? extends Comparable>)Field::getName));
        AccessibleObject.setAccessible(fields, true);
        for (final Field field : fields) {
            final String fieldName = field.getName();
            if (this.accept(field)) {
                try {
                    final Object fieldValue = this.getValue(field);
                    if (!this.excludeNullValues || fieldValue != null) {
                        this.append(fieldName, fieldValue, !field.isAnnotationPresent(ToStringSummary.class));
                    }
                }
                catch (IllegalAccessException ex) {
                    throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
                }
            }
        }
    }
    
    public String[] getExcludeFieldNames() {
        return this.excludeFieldNames.clone();
    }
    
    public Class<?> getUpToClass() {
        return this.upToClass;
    }
    
    protected Object getValue(final Field field) throws IllegalAccessException {
        return field.get(this.getObject());
    }
    
    public boolean isAppendStatics() {
        return this.appendStatics;
    }
    
    public boolean isAppendTransients() {
        return this.appendTransients;
    }
    
    public boolean isExcludeNullValues() {
        return this.excludeNullValues;
    }
    
    public ReflectionToStringBuilder reflectionAppendArray(final Object array) {
        this.getStyle().reflectionAppendArrayDetail(this.getStringBuffer(), null, array);
        return this;
    }
    
    public void setAppendStatics(final boolean appendStatics) {
        this.appendStatics = appendStatics;
    }
    
    public void setAppendTransients(final boolean appendTransients) {
        this.appendTransients = appendTransients;
    }
    
    public void setExcludeNullValues(final boolean excludeNullValues) {
        this.excludeNullValues = excludeNullValues;
    }
    
    public ReflectionToStringBuilder setExcludeFieldNames(final String... excludeFieldNamesParam) {
        if (excludeFieldNamesParam == null) {
            this.excludeFieldNames = null;
        }
        else {
            Arrays.sort(this.excludeFieldNames = toNoNullStringArray(excludeFieldNamesParam));
        }
        return this;
    }
    
    public void setUpToClass(final Class<?> clazz) {
        if (clazz != null) {
            final Object object = this.getObject();
            if (object != null && !clazz.isInstance(object)) {
                throw new IllegalArgumentException("Specified class is not a superclass of the object");
            }
        }
        this.upToClass = clazz;
    }
    
    @Override
    public String toString() {
        if (this.getObject() == null) {
            return this.getStyle().getNullText();
        }
        Class<?> clazz = this.getObject().getClass();
        this.appendFieldsIn(clazz);
        while (clazz.getSuperclass() != null && clazz != this.getUpToClass()) {
            clazz = clazz.getSuperclass();
            this.appendFieldsIn(clazz);
        }
        return super.toString();
    }
}
