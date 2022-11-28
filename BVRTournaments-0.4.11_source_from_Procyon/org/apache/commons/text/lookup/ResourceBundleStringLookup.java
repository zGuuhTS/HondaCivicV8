// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

final class ResourceBundleStringLookup extends AbstractStringLookup
{
    static final ResourceBundleStringLookup INSTANCE;
    private final String bundleName;
    
    ResourceBundleStringLookup() {
        this(null);
    }
    
    ResourceBundleStringLookup(final String bundleName) {
        this.bundleName = bundleName;
    }
    
    ResourceBundle getBundle(final String keyBundleName) {
        return ResourceBundle.getBundle(keyBundleName);
    }
    
    String getString(final String keyBundleName, final String bundleKey) {
        return this.getBundle(keyBundleName).getString(bundleKey);
    }
    
    @Override
    public String lookup(final String key) {
        if (key == null) {
            return null;
        }
        final String[] keys = key.split(ResourceBundleStringLookup.SPLIT_STR);
        final int keyLen = keys.length;
        final boolean anyBundle = this.bundleName == null;
        if (anyBundle && keyLen != 2) {
            throw IllegalArgumentExceptions.format("Bad resource bundle key format [%s]; expected format is BundleName:KeyName.", key);
        }
        if (this.bundleName != null && keyLen != 1) {
            throw IllegalArgumentExceptions.format("Bad resource bundle key format [%s]; expected format is KeyName.", key);
        }
        final String keyBundleName = anyBundle ? keys[0] : this.bundleName;
        final String bundleKey = anyBundle ? keys[1] : keys[0];
        try {
            return this.getString(keyBundleName, bundleKey);
        }
        catch (MissingResourceException e2) {
            return null;
        }
        catch (Exception e) {
            throw IllegalArgumentExceptions.format(e, "Error looking up resource bundle [%s] and key [%s].", keyBundleName, bundleKey);
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + " [bundleName=" + this.bundleName + "]";
    }
    
    static {
        INSTANCE = new ResourceBundleStringLookup();
    }
}
