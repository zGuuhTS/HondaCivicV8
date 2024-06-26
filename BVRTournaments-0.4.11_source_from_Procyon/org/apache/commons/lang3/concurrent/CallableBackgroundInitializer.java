// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.lang3.concurrent;

import org.apache.commons.lang3.Validate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;

public class CallableBackgroundInitializer<T> extends BackgroundInitializer<T>
{
    private final Callable<T> callable;
    
    public CallableBackgroundInitializer(final Callable<T> call) {
        this.checkCallable(call);
        this.callable = call;
    }
    
    public CallableBackgroundInitializer(final Callable<T> call, final ExecutorService exec) {
        super(exec);
        this.checkCallable(call);
        this.callable = call;
    }
    
    @Override
    protected T initialize() throws Exception {
        return this.callable.call();
    }
    
    private void checkCallable(final Callable<T> call) {
        Validate.notNull(call, "Callable must not be null!", new Object[0]);
    }
}
