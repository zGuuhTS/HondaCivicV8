// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.builder;

import java.lang.reflect.Modifier;
import java.lang.reflect.Field;
import org.apache.commons.lang3.reflect.FieldUtils;

public class ReflectionDiffBuilder<T> implements Builder<DiffResult<T>>
{
    private final Object left;
    private final Object right;
    private final DiffBuilder<T> diffBuilder;
    
    public ReflectionDiffBuilder(final T lhs, final T rhs, final ToStringStyle style) {
        this.left = lhs;
        this.right = rhs;
        this.diffBuilder = new DiffBuilder<T>(lhs, rhs, style);
    }
    
    @Override
    public DiffResult<T> build() {
        if (this.left.equals(this.right)) {
            return this.diffBuilder.build();
        }
        this.appendFields(this.left.getClass());
        return this.diffBuilder.build();
    }
    
    private void appendFields(final Class<?> clazz) {
        for (final Field field : FieldUtils.getAllFields(clazz)) {
            if (this.accept(field)) {
                try {
                    this.diffBuilder.append(field.getName(), FieldUtils.readField(field, this.left, true), FieldUtils.readField(field, this.right, true));
                }
                catch (IllegalAccessException ex) {
                    throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
                }
            }
        }
    }
    
    private boolean accept(final Field field) {
        return field.getName().indexOf(36) == -1 && !Modifier.isTransient(field.getModifiers()) && !Modifier.isStatic(field.getModifiers());
    }
}
