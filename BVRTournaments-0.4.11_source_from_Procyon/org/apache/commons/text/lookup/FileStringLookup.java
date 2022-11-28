// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.text.lookup;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;

final class FileStringLookup extends AbstractStringLookup
{
    static final AbstractStringLookup INSTANCE;
    
    private FileStringLookup() {
    }
    
    @Override
    public String lookup(final String key) {
        if (key == null) {
            return null;
        }
        final String[] keys = key.split(String.valueOf(':'));
        final int keyLen = keys.length;
        if (keyLen < 2) {
            throw IllegalArgumentExceptions.format("Bad file key format [%s], expected format is CharsetName:DocumentPath.", key);
        }
        final String charsetName = keys[0];
        final String fileName = StringUtils.substringAfter(key, 58);
        try {
            return new String(Files.readAllBytes(Paths.get(fileName, new String[0])), charsetName);
        }
        catch (Exception e) {
            throw IllegalArgumentExceptions.format(e, "Error looking up file [%s] with charset [%s].", fileName, charsetName);
        }
    }
    
    static {
        INSTANCE = new FileStringLookup();
    }
}
