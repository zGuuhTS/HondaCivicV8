// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

public abstract class ConfigParser
{
    protected final Logger logger;
    protected final Map<Class<?>, Map<File, Object>> configs;
    
    protected ConfigParser(final Logger logger) {
        this.configs = new HashMap<Class<?>, Map<File, Object>>();
        this.logger = logger;
    }
    
     <T> T getConfig(final File configFile, final Class<T> classe) {
        final Map<File, Object> configs = this.configs.get(classe);
        if (configs == null) {
            return (T)this.createConfig(configFile, (Class<Object>)classe);
        }
        final Object config = configs.get(configFile);
        if (config == null) {
            return (T)this.createConfig(configFile, (Class<Object>)classe);
        }
        return (T)config;
    }
    
    void reload() {
        this.configs.forEach((classe, map) -> map.forEach((file, object) -> this.createConfig(file, classe)));
    }
    
    void save() {
        this.configs.forEach((classe, map) -> map.forEach((file, object) -> this.save(object)));
    }
    
    protected abstract void save(final Object p0);
    
    protected abstract <T> T createConfig(final File p0, final Class<T> p1);
    
    protected void setValues(final Object configObject, final Object newObject) {
        try {
            for (final Field field : configObject.getClass().getFields()) {
                for (final Field newField : newObject.getClass().getFields()) {
                    if (field.getName().equalsIgnoreCase(newField.getName())) {
                        final Class<?> fieldType = field.getType();
                        if (fieldType.isAssignableFrom(Object.class) && !fieldType.isAssignableFrom(String.class) && !fieldType.isAssignableFrom(Map.class)) {
                            this.setValues(field.get(configObject), newField.get(newObject));
                        }
                        else {
                            field.set(configObject, newField.get(newObject));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            this.logger.severe("Error reloading config object:");
            e.printStackTrace();
        }
    }
}
