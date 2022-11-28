// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealconfig.parser;

import java.lang.reflect.Field;
import java.util.Iterator;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonElement;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.element.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import org.apache.commons.io.FileUtils;
import java.nio.charset.StandardCharsets;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.Jankson;
import java.util.logging.Logger;
import com.focamacho.sealconfig.relocated.org.apache.commons.text.translate.UnicodeUnescaper;
import com.focamacho.sealconfig.ConfigParser;

public class JanksonParser extends ConfigParser
{
    private static final UnicodeUnescaper unicodeUnescaper;
    
    public JanksonParser(final Logger logger) {
        super(logger);
    }
    
    @Override
    protected void save(final Object configObject) {
        String toSave;
        boolean mk;
        this.configs.forEach((classe, map) -> map.forEach((file, object) -> {
            if (configObject == object) {
                try {
                    toSave = JanksonParser.unicodeUnescaper.translate(Jankson.builder().build().load(Jankson.builder().build().toJson(configObject).toJson((boolean)(1 != 0), (boolean)(1 != 0))).toJson((boolean)(1 != 0), (boolean)(1 != 0), 0, 2));
                    if (!file.exists()) {
                        mk = file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    FileUtils.write(file, toSave, StandardCharsets.UTF_8);
                }
                catch (Exception e) {
                    this.logger.severe("Error saving a config file:");
                    e.printStackTrace();
                }
            }
        }));
    }
    
    @Override
    protected <T> T createConfig(final File configFile, final Class<T> configClass) {
        try {
            final JsonObject defaults = Jankson.builder().build().load(Jankson.builder().build().toJson(configClass.getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0])).toJson(true, true));
            JsonObject configObject;
            if (!configFile.exists()) {
                final boolean mk = configFile.getParentFile().mkdirs();
                final boolean nf = configFile.createNewFile();
                configObject = defaults;
            }
            else {
                configObject = Jankson.builder().build().load(configFile);
                configObject = this.checkValues(defaults, configObject, configClass);
            }
            FileUtils.write(configFile, JanksonParser.unicodeUnescaper.translate(configObject.toJson(true, true, 0, 2)), StandardCharsets.UTF_8);
            final T config = Jankson.builder().build().fromJson(configObject.toJson(), configClass);
            Map<File, Object> configs = this.configs.get(configClass);
            if (configs == null) {
                this.configs.put(configClass, new HashMap<File, Object>());
                configs = this.configs.get(configClass);
            }
            if (configs.get(configFile) == null) {
                configs.put(configFile, config);
            }
            else {
                this.setValues(configs.get(configFile), config);
            }
            this.removeClassDefaults(config, configObject);
            return config;
        }
        catch (Exception e) {
            this.logger.severe("Error loading a config file:");
            e.printStackTrace();
            return null;
        }
    }
    
    private JsonObject checkValues(final JsonObject defaultObject, JsonObject actualObject, final Class<?> configClass) {
        for (final Map.Entry<String, JsonElement> entry : defaultObject.entrySet()) {
            if (!actualObject.containsKey(entry.getKey())) {
                actualObject = this.applyDefaults(defaultObject, actualObject);
            }
            else {
                if (!(actualObject.get(entry.getKey()) instanceof JsonObject)) {
                    continue;
                }
                try {
                    final Field field = configClass.getDeclaredField(entry.getKey());
                    final Class<?> fieldType = field.getType();
                    if (!fieldType.isAssignableFrom(Object.class) || fieldType.isAssignableFrom(String.class) || fieldType.isAssignableFrom(Map.class)) {
                        continue;
                    }
                    actualObject.put(entry.getKey(), this.checkValues(entry.getValue(), actualObject.getObject(entry.getKey()), field.getType()));
                }
                catch (Exception ex) {}
            }
        }
        for (final Map.Entry<String, JsonElement> entry : defaultObject.entrySet()) {
            actualObject.setComment(entry.getKey(), defaultObject.getComment(entry.getKey()));
        }
        return actualObject;
    }
    
    private JsonObject applyDefaults(final JsonObject defaultObject, final JsonObject actualObject) {
        final JsonObject newObject = defaultObject.clone();
        final JsonObject jsonObject;
        actualObject.forEach((key, value) -> {
            if (jsonObject.containsKey(key)) {
                jsonObject.put(key, value);
            }
            return;
        });
        return newObject;
    }
    
    private void removeClassDefaults(final Object config, final JsonObject configObject) {
        try {
            final Field[] fields2;
            final Field[] fields = fields2 = config.getClass().getFields();
            for (final Field field : fields2) {
                final JsonObject jsonObject = configObject.getObject(field.getName());
                if (jsonObject != null) {
                    final Object obj = field.get(config);
                    if (!field.getType().isAssignableFrom(Map.class)) {
                        if (configObject.containsKey(field.getName())) {
                            this.removeClassDefaults(obj, configObject.getObject(field.getName()));
                        }
                    }
                    else if (obj instanceof Map) {
                        final Map<String, ?> map = (Map<String, ?>)obj;
                        map.entrySet().removeIf(entry -> !jsonObject.containsKey(entry.getKey()));
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    static {
        unicodeUnescaper = new UnicodeUnescaper();
    }
}
