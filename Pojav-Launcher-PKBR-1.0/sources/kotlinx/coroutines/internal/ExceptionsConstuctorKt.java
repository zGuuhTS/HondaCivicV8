package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

@Metadata(mo11814d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0006j\u0004\u0018\u0001`\u00072\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0002\u001a1\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00072\u0014\b\u0004\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0006H\b\u001a!\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\u00052\u0006\u0010\u0011\u001a\u0002H\u0010H\u0000¢\u0006\u0002\u0010\u0012\u001a\u001b\u0010\u0013\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\b\b\u0002\u0010\u0014\u001a\u00020\tH\u0010\u001a\u0018\u0010\u0015\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0016\u001a\u00020\tH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"4\u0010\u0002\u001a(\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00070\u0003X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0017\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00062\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006¨\u0006\u0018"}, mo11815d2 = {"cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "throwableFields", "", "createConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "E", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, mo11816k = 2, mo11817mv = {1, 5, 1}, mo11819xi = 48)
/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt {
    private static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private static final WeakHashMap<Class<? extends Throwable>, Function1<Throwable, Throwable>> exceptionCtors = new WeakHashMap<>();
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public static final <E extends java.lang.Throwable> E tryCopyException(E r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.CopyableThrowable
            r1 = 0
            if (r0 == 0) goto L_0x002a
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0014 }
            r0 = 0
            r2 = r11
            kotlinx.coroutines.CopyableThrowable r2 = (kotlinx.coroutines.CopyableThrowable) r2     // Catch:{ all -> 0x0014 }
            java.lang.Throwable r2 = r2.createCopy()     // Catch:{ all -> 0x0014 }
            java.lang.Object r0 = kotlin.Result.m64constructorimpl(r2)     // Catch:{ all -> 0x0014 }
            goto L_0x001f
        L_0x0014:
            r0 = move-exception
            kotlin.Result$Companion r2 = kotlin.Result.Companion
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r0)
            java.lang.Object r0 = kotlin.Result.m64constructorimpl(r0)
        L_0x001f:
            boolean r2 = kotlin.Result.m70isFailureimpl(r0)
            if (r2 == 0) goto L_0x0026
            goto L_0x0027
        L_0x0026:
            r1 = r0
        L_0x0027:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            return r1
        L_0x002a:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = cacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r0.readLock()
            r2.lock()
            r3 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r4 = exceptionCtors     // Catch:{ all -> 0x012a }
            java.lang.Class r5 = r11.getClass()     // Catch:{ all -> 0x012a }
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x012a }
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4     // Catch:{ all -> 0x012a }
            r2.unlock()
            if (r4 != 0) goto L_0x0121
            int r2 = throwableFields
            java.lang.Class r3 = r11.getClass()
            r4 = 0
            int r3 = fieldsCountOrDefault(r3, r4)
            if (r2 == r3) goto L_0x009b
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r0.readLock()
            int r3 = r0.getWriteHoldCount()
            if (r3 != 0) goto L_0x0061
            int r3 = r0.getReadHoldCount()
            goto L_0x0062
        L_0x0061:
            r3 = r4
        L_0x0062:
            r5 = r4
        L_0x0063:
            if (r5 >= r3) goto L_0x006b
            r2.unlock()
            int r5 = r5 + 1
            goto L_0x0063
        L_0x006b:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r5 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r6 = exceptionCtors     // Catch:{ all -> 0x008e }
            java.util.Map r6 = (java.util.Map) r6     // Catch:{ all -> 0x008e }
            java.lang.Class r7 = r11.getClass()     // Catch:{ all -> 0x008e }
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1 r8 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1.INSTANCE     // Catch:{ all -> 0x008e }
            r6.put(r7, r8)     // Catch:{ all -> 0x008e }
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x008e }
        L_0x0082:
            if (r4 >= r3) goto L_0x008a
            r2.lock()
            int r4 = r4 + 1
            goto L_0x0082
        L_0x008a:
            r0.unlock()
            return r1
        L_0x008e:
            r1 = move-exception
        L_0x008f:
            if (r4 >= r3) goto L_0x0097
            r2.lock()
            int r4 = r4 + 1
            goto L_0x008f
        L_0x0097:
            r0.unlock()
            throw r1
        L_0x009b:
            r0 = 0
            java.lang.Class r2 = r11.getClass()
            java.lang.reflect.Constructor[] r2 = r2.getConstructors()
            r3 = 0
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1 r5 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1
            r5.<init>()
            java.util.Comparator r5 = (java.util.Comparator) r5
            java.util.List r2 = kotlin.collections.ArraysKt.sortedWith((T[]) r2, r5)
            java.util.Iterator r3 = r2.iterator()
        L_0x00b5:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x00c7
            java.lang.Object r5 = r3.next()
            java.lang.reflect.Constructor r5 = (java.lang.reflect.Constructor) r5
            kotlin.jvm.functions.Function1 r0 = createConstructor(r5)
            if (r0 == 0) goto L_0x00b5
        L_0x00c7:
            java.util.concurrent.locks.ReentrantReadWriteLock r3 = cacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r5 = r3.readLock()
            int r6 = r3.getWriteHoldCount()
            if (r6 != 0) goto L_0x00d8
            int r6 = r3.getReadHoldCount()
            goto L_0x00d9
        L_0x00d8:
            r6 = r4
        L_0x00d9:
            r7 = r4
        L_0x00da:
            if (r7 >= r6) goto L_0x00e2
            r5.unlock()
            int r7 = r7 + 1
            goto L_0x00da
        L_0x00e2:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r3 = r3.writeLock()
            r3.lock()
            r7 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r8 = exceptionCtors     // Catch:{ all -> 0x0114 }
            java.util.Map r8 = (java.util.Map) r8     // Catch:{ all -> 0x0114 }
            java.lang.Class r9 = r11.getClass()     // Catch:{ all -> 0x0114 }
            if (r0 != 0) goto L_0x00f9
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1 r10 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1.INSTANCE     // Catch:{ all -> 0x0114 }
            kotlin.jvm.functions.Function1 r10 = (kotlin.jvm.functions.Function1) r10     // Catch:{ all -> 0x0114 }
            goto L_0x00fa
        L_0x00f9:
            r10 = r0
        L_0x00fa:
            r8.put(r9, r10)     // Catch:{ all -> 0x0114 }
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0114 }
        L_0x00ff:
            if (r4 >= r6) goto L_0x0107
            r5.lock()
            int r4 = r4 + 1
            goto L_0x00ff
        L_0x0107:
            r3.unlock()
            if (r0 != 0) goto L_0x010d
            goto L_0x0113
        L_0x010d:
            java.lang.Object r1 = r0.invoke(r11)
            java.lang.Throwable r1 = (java.lang.Throwable) r1
        L_0x0113:
            return r1
        L_0x0114:
            r1 = move-exception
        L_0x0115:
            if (r4 >= r6) goto L_0x011d
            r5.lock()
            int r4 = r4 + 1
            goto L_0x0115
        L_0x011d:
            r3.unlock()
            throw r1
        L_0x0121:
            r0 = r4
            r1 = 0
            java.lang.Object r2 = r0.invoke(r11)
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            return r2
        L_0x012a:
            r0 = move-exception
            r2.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ExceptionsConstuctorKt.tryCopyException(java.lang.Throwable):java.lang.Throwable");
    }

    private static final Function1<Throwable, Throwable> createConstructor(Constructor<?> constructor) {
        Class[] p = constructor.getParameterTypes();
        switch (p.length) {
            case 0:
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4(constructor);
            case 1:
                Class cls = p[0];
                if (Intrinsics.areEqual((Object) cls, (Object) Throwable.class)) {
                    return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$2(constructor);
                }
                if (Intrinsics.areEqual((Object) cls, (Object) String.class)) {
                    return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3(constructor);
                }
                return null;
            case 2:
                if (!Intrinsics.areEqual((Object) p[0], (Object) String.class) || !Intrinsics.areEqual((Object) p[1], (Object) Throwable.class)) {
                    return null;
                }
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1(constructor);
            default:
                return null;
        }
    }

    private static final Function1<Throwable, Throwable> safeCtor(Function1<? super Throwable, ? extends Throwable> block) {
        return new ExceptionsConstuctorKt$safeCtor$1(block);
    }

    private static final int fieldsCountOrDefault(Class<?> $this$fieldsCountOrDefault, int defaultValue) {
        Integer num;
        KClass<?> kotlinClass = JvmClassMappingKt.getKotlinClass($this$fieldsCountOrDefault);
        try {
            Result.Companion companion = Result.Companion;
            num = Result.m64constructorimpl(Integer.valueOf(fieldsCount$default($this$fieldsCountOrDefault, 0, 1, (Object) null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            num = Result.m64constructorimpl(ResultKt.createFailure(th));
        }
        Integer valueOf = Integer.valueOf(defaultValue);
        if (Result.m70isFailureimpl(num)) {
            num = valueOf;
        }
        return ((Number) num).intValue();
    }

    private static final int fieldsCount(Class<?> $this$fieldsCount, int accumulator) {
        Class<?> cls = $this$fieldsCount;
        int totalFields = accumulator;
        do {
            Field[] declaredFields = cls.getDeclaredFields();
            int count$iv = 0;
            int length = declaredFields.length;
            for (int i = 0; i < length; i++) {
                if (!Modifier.isStatic(declaredFields[i].getModifiers())) {
                    count$iv++;
                }
            }
            totalFields += count$iv;
            cls = cls.getSuperclass();
        } while (cls != null);
        return totalFields;
    }

    static /* synthetic */ int fieldsCount$default(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return fieldsCount(cls, i);
    }
}
