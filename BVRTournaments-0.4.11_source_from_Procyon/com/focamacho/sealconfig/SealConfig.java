// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import com.focamacho.sealconfig.parser.JanksonParser;
import java.util.logging.Logger;

public class SealConfig
{
    protected static final Logger logger;
    private final ConfigParser parser;
    
    public SealConfig() {
        this(JanksonParser.class);
    }
    
    public SealConfig(final Class<? extends ConfigParser> parser) {
        ConfigParser configParser = null;
        try {
            configParser = (ConfigParser)parser.getConstructors()[0].newInstance(SealConfig.logger);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException ex2) {
            final ReflectiveOperationException ex;
            final ReflectiveOperationException e = ex;
            SealConfig.logger.severe("Invalid Parser class.");
            e.printStackTrace();
        }
        this.parser = configParser;
    }
    
    public <T> T getConfig(final File configFile, final Class<T> classe) {
        return this.parser.getConfig(configFile, classe);
    }
    
    public void reload() {
        this.parser.reload();
    }
    
    public void save() {
        this.parser.save();
    }
    
    public void save(final Object configObject) {
        this.parser.save(configObject);
    }
    
    static {
        logger = Logger.getLogger("SealConfig");
    }
}
