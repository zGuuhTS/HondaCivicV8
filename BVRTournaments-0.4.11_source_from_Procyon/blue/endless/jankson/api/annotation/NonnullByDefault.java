// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import javax.annotation.meta.TypeQualifierDefault;
import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

@Nonnull
@TypeQualifierDefault({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.PACKAGE })
public @interface NonnullByDefault {
}
