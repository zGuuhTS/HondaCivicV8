// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.Map;

public final class StringLookupFactory
{
    public static final StringLookupFactory INSTANCE;
    static final FunctionStringLookup<String> INSTANCE_BASE64_DECODER;
    static final FunctionStringLookup<String> INSTANCE_BASE64_ENCODER;
    static final FunctionStringLookup<String> INSTANCE_ENVIRONMENT_VARIABLES;
    static final FunctionStringLookup<String> INSTANCE_NULL;
    static final FunctionStringLookup<String> INSTANCE_SYSTEM_PROPERTIES;
    public static final String KEY_BASE64_DECODER = "base64Decoder";
    public static final String KEY_BASE64_ENCODER = "base64Encoder";
    public static final String KEY_CONST = "const";
    public static final String KEY_DATE = "date";
    public static final String KEY_DNS = "dns";
    public static final String KEY_ENV = "env";
    public static final String KEY_FILE = "file";
    public static final String KEY_JAVA = "java";
    public static final String KEY_LOCALHOST = "localhost";
    public static final String KEY_PROPERTIES = "properties";
    public static final String KEY_RESOURCE_BUNDLE = "resourceBundle";
    public static final String KEY_SCRIPT = "script";
    public static final String KEY_SYS = "sys";
    public static final String KEY_URL = "url";
    public static final String KEY_URL_DECODER = "urlDecoder";
    public static final String KEY_URL_ENCODER = "urlEncoder";
    public static final String KEY_XML = "xml";
    
    public static void clear() {
        ConstantStringLookup.clear();
    }
    
    private StringLookupFactory() {
    }
    
    public void addDefaultStringLookups(final Map<String, StringLookup> stringLookupMap) {
        if (stringLookupMap != null) {
            stringLookupMap.put("base64", StringLookupFactory.INSTANCE_BASE64_DECODER);
            for (final DefaultStringLookup stringLookup : DefaultStringLookup.values()) {
                stringLookupMap.put(InterpolatorStringLookup.toKey(stringLookup.getKey()), stringLookup.getStringLookup());
            }
        }
    }
    
    public StringLookup base64DecoderStringLookup() {
        return StringLookupFactory.INSTANCE_BASE64_DECODER;
    }
    
    public StringLookup base64EncoderStringLookup() {
        return StringLookupFactory.INSTANCE_BASE64_ENCODER;
    }
    
    @Deprecated
    public StringLookup base64StringLookup() {
        return StringLookupFactory.INSTANCE_BASE64_DECODER;
    }
    
    public <R, U> BiStringLookup<U> biFunctionStringLookup(final BiFunction<String, U, R> biFunction) {
        return BiFunctionStringLookup.on(biFunction);
    }
    
    public StringLookup constantStringLookup() {
        return ConstantStringLookup.INSTANCE;
    }
    
    public StringLookup dateStringLookup() {
        return DateStringLookup.INSTANCE;
    }
    
    public StringLookup dnsStringLookup() {
        return DnsStringLookup.INSTANCE;
    }
    
    public StringLookup environmentVariableStringLookup() {
        return StringLookupFactory.INSTANCE_ENVIRONMENT_VARIABLES;
    }
    
    public StringLookup fileStringLookup() {
        return FileStringLookup.INSTANCE;
    }
    
    public <R> StringLookup functionStringLookup(final Function<String, R> function) {
        return FunctionStringLookup.on(function);
    }
    
    public StringLookup interpolatorStringLookup() {
        return InterpolatorStringLookup.INSTANCE;
    }
    
    public StringLookup interpolatorStringLookup(final Map<String, StringLookup> stringLookupMap, final StringLookup defaultStringLookup, final boolean addDefaultLookups) {
        return new InterpolatorStringLookup(stringLookupMap, defaultStringLookup, addDefaultLookups);
    }
    
    public <V> StringLookup interpolatorStringLookup(final Map<String, V> map) {
        return new InterpolatorStringLookup((Map<String, V>)map);
    }
    
    public StringLookup interpolatorStringLookup(final StringLookup defaultStringLookup) {
        return new InterpolatorStringLookup(defaultStringLookup);
    }
    
    public StringLookup javaPlatformStringLookup() {
        return JavaPlatformStringLookup.INSTANCE;
    }
    
    public StringLookup localHostStringLookup() {
        return LocalHostStringLookup.INSTANCE;
    }
    
    public <V> StringLookup mapStringLookup(final Map<String, V> map) {
        return FunctionStringLookup.on(map);
    }
    
    public StringLookup nullStringLookup() {
        return StringLookupFactory.INSTANCE_NULL;
    }
    
    public StringLookup propertiesStringLookup() {
        return PropertiesStringLookup.INSTANCE;
    }
    
    public StringLookup resourceBundleStringLookup() {
        return ResourceBundleStringLookup.INSTANCE;
    }
    
    public StringLookup resourceBundleStringLookup(final String bundleName) {
        return new ResourceBundleStringLookup(bundleName);
    }
    
    public StringLookup scriptStringLookup() {
        return ScriptStringLookup.INSTANCE;
    }
    
    public StringLookup systemPropertyStringLookup() {
        return StringLookupFactory.INSTANCE_SYSTEM_PROPERTIES;
    }
    
    public StringLookup urlDecoderStringLookup() {
        return UrlDecoderStringLookup.INSTANCE;
    }
    
    public StringLookup urlEncoderStringLookup() {
        return UrlEncoderStringLookup.INSTANCE;
    }
    
    public StringLookup urlStringLookup() {
        return UrlStringLookup.INSTANCE;
    }
    
    public StringLookup xmlStringLookup() {
        return XmlStringLookup.INSTANCE;
    }
    
    static {
        INSTANCE = new StringLookupFactory();
        INSTANCE_BASE64_DECODER = FunctionStringLookup.on(key -> new String(Base64.getDecoder().decode(key), StandardCharsets.ISO_8859_1));
        INSTANCE_BASE64_ENCODER = FunctionStringLookup.on(key -> Base64.getEncoder().encodeToString(key.getBytes(StandardCharsets.ISO_8859_1)));
        INSTANCE_ENVIRONMENT_VARIABLES = FunctionStringLookup.on(System::getenv);
        INSTANCE_NULL = FunctionStringLookup.on(key -> null);
        INSTANCE_SYSTEM_PROPERTIES = FunctionStringLookup.on(System::getProperty);
    }
}
