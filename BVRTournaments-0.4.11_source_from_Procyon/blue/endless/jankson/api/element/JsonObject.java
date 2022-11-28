// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.element;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.io.IOException;
import blue.endless.jankson.impl.serializer.CommentSerializer;
import java.io.Writer;
import blue.endless.jankson.api.JsonGrammar;
import javax.annotation.Nullable;
import java.util.Iterator;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import blue.endless.jankson.impl.MarshallerImpl;
import java.util.List;
import blue.endless.jankson.api.Marshaller;
import java.util.function.Predicate;
import java.util.Map;

public class JsonObject extends JsonElement implements Map<String, JsonElement>
{
    private static final Predicate<String> CAN_BE_UNQUOTED;
    protected Marshaller marshaller;
    private List<Entry> entries;
    
    public JsonObject() {
        this.marshaller = MarshallerImpl.getFallback();
        this.entries = new ArrayList<Entry>();
    }
    
    @Nullable
    public JsonObject getObject(@Nonnull final String name) {
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase(name)) {
                if (entry.value instanceof JsonObject) {
                    return (JsonObject)entry.value;
                }
                return null;
            }
        }
        return null;
    }
    
    public JsonElement put(@Nonnull final String key, @Nonnull final JsonElement elem, @Nullable final String comment) {
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase(key)) {
                final JsonElement result = entry.value;
                entry.value = elem;
                entry.setComment(comment);
                return result;
            }
        }
        final Entry entry2 = new Entry();
        if (elem instanceof JsonObject) {
            ((JsonObject)elem).marshaller = this.marshaller;
        }
        if (elem instanceof JsonArray) {
            ((JsonArray)elem).marshaller = this.marshaller;
        }
        entry2.key = key;
        entry2.value = elem;
        entry2.setComment(comment);
        this.entries.add(entry2);
        return null;
    }
    
    @Nonnull
    public JsonElement putDefault(@Nonnull final String key, @Nonnull final JsonElement elem, @Nullable final String comment) {
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase(key)) {
                return entry.value;
            }
        }
        final Entry entry2 = new Entry();
        entry2.key = key;
        entry2.value = elem;
        entry2.setComment(comment);
        this.entries.add(entry2);
        return elem;
    }
    
    @Nullable
    public <T> T putDefault(@Nonnull final String key, @Nonnull final T elem, @Nullable final String comment) {
        return this.putDefault(key, elem, (Class<? extends T>)elem.getClass(), comment);
    }
    
    @Nullable
    public <T> T putDefault(@Nonnull final String key, @Nonnull final T elem, final Class<? extends T> clazz, @Nullable final String comment) {
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase(key)) {
                return this.marshaller.marshall((Class<T>)clazz, entry.value);
            }
        }
        final Entry entry2 = new Entry();
        entry2.key = key;
        entry2.value = this.marshaller.serialize(elem);
        if (entry2.value == null) {
            entry2.value = JsonNull.INSTANCE;
        }
        entry2.setComment(comment);
        this.entries.add(entry2);
        return elem;
    }
    
    @Nonnull
    public JsonObject getDelta(@Nonnull final JsonObject defaults) {
        final JsonObject result = new JsonObject();
        for (final Entry entry : this.entries) {
            final String key = entry.key;
            final JsonElement defaultValue = defaults.get(key);
            if (defaultValue == null) {
                result.put(entry.key, entry.value, entry.getComment());
            }
            else if (entry.value instanceof JsonObject && defaultValue instanceof JsonObject) {
                final JsonObject subDelta = ((JsonObject)entry.value).getDelta((JsonObject)defaultValue);
                if (subDelta.isEmpty()) {
                    continue;
                }
                result.put(entry.key, subDelta, entry.getComment());
            }
            else {
                if (entry.value.equals(defaultValue)) {
                    continue;
                }
                result.put(entry.key, entry.value, entry.getComment());
            }
        }
        return result;
    }
    
    @Nullable
    public String getComment(@Nonnull final String name) {
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase(name)) {
                return entry.getComment();
            }
        }
        return null;
    }
    
    public void setComment(@Nonnull final String name, @Nullable final String comment) {
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase(name)) {
                entry.setComment(comment);
            }
        }
    }
    
    @Override
    public String toJson(final boolean comments, final boolean newlines, final int depth) {
        final JsonGrammar grammar = JsonGrammar.builder().withComments(comments).printWhitespace(newlines).build();
        return this.toJson(grammar, depth);
    }
    
    @Override
    public void toJson(final Writer w, final JsonGrammar grammar, final int depth) throws IOException {
        final boolean skipBraces = depth == 0 && grammar.isBareRootObject();
        final int effectiveDepth = grammar.isBareRootObject() ? (depth - 1) : depth;
        final int nextDepth = grammar.isBareRootObject() ? depth : (depth + 1);
        if (!skipBraces) {
            w.append((CharSequence)"{");
            if (grammar.shouldOutputWhitespace() && this.entries.size() > 0) {
                w.append('\n');
            }
            else {
                w.append(' ');
            }
        }
        for (int i = 0; i < this.entries.size(); ++i) {
            final Entry entry = this.entries.get(i);
            if (grammar.shouldOutputWhitespace()) {
                for (int j = 0; j < nextDepth; ++j) {
                    w.append((CharSequence)"\t");
                }
            }
            CommentSerializer.print(w, entry.getComment(), effectiveDepth, grammar);
            boolean quoted = !grammar.shouldUnquoteKeys();
            if (!JsonObject.CAN_BE_UNQUOTED.test(entry.key)) {
                quoted = true;
            }
            if (quoted) {
                w.append((CharSequence)"\"");
            }
            w.append((CharSequence)entry.key);
            if (quoted) {
                w.append((CharSequence)"\"");
            }
            w.append((CharSequence)": ");
            w.append((CharSequence)entry.value.toJson(grammar, depth + 1));
            if (grammar.shouldPrintCommas()) {
                if (i < this.entries.size() - 1 || grammar.isTrailingCommas()) {
                    w.append((CharSequence)",");
                    if (i < this.entries.size() - 1 && !grammar.shouldOutputWhitespace()) {
                        w.append(' ');
                    }
                }
            }
            else if (!grammar.shouldOutputWhitespace()) {
                w.append((CharSequence)" ");
            }
            if (grammar.shouldOutputWhitespace()) {
                w.append('\n');
            }
        }
        if (!skipBraces) {
            if (this.entries.size() > 0) {
                if (grammar.shouldOutputWhitespace()) {
                    for (int k = 0; k < effectiveDepth; ++k) {
                        w.append((CharSequence)"\t");
                    }
                }
                else {
                    w.append(' ');
                }
            }
            w.append((CharSequence)"}");
        }
    }
    
    @Override
    public String toString() {
        return this.toJson(true, false, 0);
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof JsonObject)) {
            return false;
        }
        final JsonObject otherObject = (JsonObject)other;
        if (this.entries.size() != otherObject.entries.size()) {
            return false;
        }
        for (int i = 0; i < this.entries.size(); ++i) {
            final Entry a = this.entries.get(i);
            final Entry b = otherObject.entries.get(i);
            if (!a.equals(b)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        return this.entries.hashCode();
    }
    
    public void setMarshaller(final Marshaller marshaller) {
        this.marshaller = marshaller;
    }
    
    public Marshaller getMarshaller() {
        return this.marshaller;
    }
    
    @Nullable
    public <E> E get(@Nonnull final Class<E> clazz, @Nonnull final String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Cannot get from empty key");
        }
        final JsonElement elem = this.get(key);
        return this.marshaller.marshall(clazz, elem);
    }
    
    public boolean getBoolean(@Nonnull final String key, final boolean defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asBoolean(defaultValue);
        }
        return defaultValue;
    }
    
    public byte getByte(@Nonnull final String key, final byte defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asByte(defaultValue);
        }
        return defaultValue;
    }
    
    public char getChar(@Nonnull final String key, final char defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asChar(defaultValue);
        }
        return defaultValue;
    }
    
    public short getShort(@Nonnull final String key, final short defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asShort(defaultValue);
        }
        return defaultValue;
    }
    
    public int getInt(@Nonnull final String key, final int defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asInt(defaultValue);
        }
        return defaultValue;
    }
    
    public long getLong(@Nonnull final String key, final long defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asLong(defaultValue);
        }
        return defaultValue;
    }
    
    public float getFloat(@Nonnull final String key, final float defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asFloat(defaultValue);
        }
        return defaultValue;
    }
    
    public double getDouble(@Nonnull final String key, final double defaultValue) {
        final JsonElement elem = this.get(key);
        if (elem != null && elem instanceof JsonPrimitive) {
            return ((JsonPrimitive)elem).asDouble(defaultValue);
        }
        return defaultValue;
    }
    
    @Nullable
    public <E> E recursiveGet(@Nonnull final Class<E> clazz, @Nonnull final String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Cannot get from empty key");
        }
        final String[] parts = key.split("\\.");
        JsonObject cur = this;
        for (int i = 0; i < parts.length; ++i) {
            final String s = parts[i];
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Cannot get from broken key '" + key + "'");
            }
            final JsonElement elem = cur.get(s);
            if (i >= parts.length - 1) {
                return this.marshaller.marshall(clazz, elem);
            }
            if (!(elem instanceof JsonObject)) {
                return null;
            }
            cur = (JsonObject)elem;
        }
        throw new IllegalArgumentException("Cannot get from broken key '" + key + "'");
    }
    
    public <E extends JsonElement> E recursiveGetOrCreate(@Nonnull final Class<E> clazz, @Nonnull final String key, @Nonnull final E fallback, @Nullable final String comment) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Cannot get from empty key");
        }
        final String[] parts = key.split("\\.");
        JsonObject cur = this;
        int i = 0;
        while (i < parts.length) {
            final String s = parts[i];
            if (s.isEmpty()) {
                throw new IllegalArgumentException("Cannot get from broken key '" + key + "'");
            }
            final JsonElement elem = cur.get(s);
            if (i < parts.length - 1) {
                if (elem instanceof JsonObject) {
                    cur = (JsonObject)elem;
                }
                else {
                    final JsonObject replacement = new JsonObject();
                    cur.put(s, replacement);
                    cur = replacement;
                }
                ++i;
            }
            else {
                if (elem != null && clazz.isAssignableFrom(elem.getClass())) {
                    return (E)elem;
                }
                final E result = (E)fallback.clone();
                cur.put(s, result, comment);
                return result;
            }
        }
        throw new IllegalArgumentException("Cannot get from broken key '" + key + "'");
    }
    
    @Override
    public JsonObject clone() {
        final JsonObject result = new JsonObject();
        for (final Entry entry : this.entries) {
            result.put(entry.key, entry.value.clone(), entry.comment);
        }
        result.marshaller = this.marshaller;
        return result;
    }
    
    @Nullable
    @Override
    public JsonElement put(@Nonnull final String key, @Nonnull final JsonElement elem) {
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase(key)) {
                final JsonElement result = entry.value;
                entry.value = elem;
                return result;
            }
        }
        final Entry entry2 = new Entry();
        entry2.key = key;
        entry2.value = elem;
        this.entries.add(entry2);
        return null;
    }
    
    @Override
    public void clear() {
        this.entries.clear();
    }
    
    @Override
    public boolean containsKey(@Nullable final Object key) {
        if (key == null) {
            return false;
        }
        if (!(key instanceof String)) {
            return false;
        }
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase((String)key)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsValue(@Nullable final Object val) {
        if (val == null) {
            return false;
        }
        if (!(val instanceof JsonElement)) {
            return false;
        }
        for (final Entry entry : this.entries) {
            if (entry.value.equals(val)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Set<Map.Entry<String, JsonElement>> entrySet() {
        final Set<Map.Entry<String, JsonElement>> result = new LinkedHashSet<Map.Entry<String, JsonElement>>();
        for (final Entry entry : this.entries) {
            result.add(new Map.Entry<String, JsonElement>() {
                @Override
                public String getKey() {
                    return entry.key;
                }
                
                @Override
                public JsonElement getValue() {
                    return entry.value;
                }
                
                @Override
                public JsonElement setValue(final JsonElement value) {
                    final JsonElement oldValue = entry.value;
                    entry.value = value;
                    return oldValue;
                }
            });
        }
        return result;
    }
    
    @Nullable
    @Override
    public JsonElement get(@Nullable final Object key) {
        if (key == null || !(key instanceof String)) {
            return null;
        }
        for (final Entry entry : this.entries) {
            if (entry.key.equalsIgnoreCase((String)key)) {
                return entry.value;
            }
        }
        return null;
    }
    
    @Override
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }
    
    @Nonnull
    @Override
    public Set<String> keySet() {
        final Set<String> keys = new HashSet<String>();
        for (final Entry entry : this.entries) {
            keys.add(entry.key);
        }
        return keys;
    }
    
    @Override
    public void putAll(final Map<? extends String, ? extends JsonElement> map) {
        for (final Map.Entry<? extends String, ? extends JsonElement> entry : map.entrySet()) {
            this.put((String)entry.getKey(), (JsonElement)entry.getValue());
        }
    }
    
    @Nullable
    @Override
    public JsonElement remove(@Nullable final Object key) {
        if (key == null || !(key instanceof String)) {
            return null;
        }
        for (int i = 0; i < this.entries.size(); ++i) {
            final Entry entry = this.entries.get(i);
            if (entry.key.equalsIgnoreCase((String)key)) {
                return this.entries.remove(i).value;
            }
        }
        return null;
    }
    
    @Override
    public int size() {
        return this.entries.size();
    }
    
    @Override
    public Collection<JsonElement> values() {
        final List<JsonElement> values = new ArrayList<JsonElement>();
        for (final Entry entry : this.entries) {
            values.add(entry.value);
        }
        return values;
    }
    
    static {
        CAN_BE_UNQUOTED = Pattern.compile("^[a-zA-Z0-9]+$").asPredicate();
    }
    
    private static final class Entry
    {
        private String comment;
        protected String key;
        protected JsonElement value;
        
        @Override
        public boolean equals(final Object other) {
            if (other == null || !(other instanceof Entry)) {
                return false;
            }
            final Entry o = (Entry)other;
            return Objects.equals(this.comment, o.comment) && this.key.equals(o.key) && this.value.equals(o.value);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.comment, this.key, this.value);
        }
        
        public String getComment() {
            return this.comment;
        }
        
        public void setComment(final String comment) {
            if (comment != null && !comment.trim().isEmpty()) {
                this.comment = comment;
            }
            else {
                this.comment = null;
            }
        }
    }
}
