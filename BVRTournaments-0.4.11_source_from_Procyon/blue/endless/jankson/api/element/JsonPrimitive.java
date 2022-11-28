// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.api.element;

import java.io.IOException;
import blue.endless.jankson.api.Escaper;
import java.io.Writer;
import blue.endless.jankson.api.JsonGrammar;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Nonnull;

public class JsonPrimitive extends JsonElement
{
    public static JsonPrimitive TRUE;
    public static JsonPrimitive FALSE;
    @Nonnull
    private Object value;
    
    private JsonPrimitive() {
    }
    
    public JsonPrimitive(@Nonnull final Object value) {
        if (value instanceof Character) {
            this.value = "" + value;
        }
        else if (value instanceof Long) {
            this.value = value;
        }
        else if (value instanceof Double) {
            this.value = value;
        }
        else if (value instanceof BigInteger) {
            this.value = ((BigInteger)value).toString(16);
        }
        else if (value instanceof Float) {
            this.value = value;
        }
        else if (value instanceof Number) {
            this.value = ((Number)value).longValue();
        }
        else if (value instanceof CharSequence) {
            this.value = value.toString();
        }
        else {
            if (!(value instanceof Boolean)) {
                throw new IllegalArgumentException("Object of type '" + value.getClass().getCanonicalName() + "' not allowed as a JsonPrimitive");
            }
            this.value = value;
        }
    }
    
    @Nonnull
    public String asString() {
        if (this.value == null) {
            return "null";
        }
        return this.value.toString();
    }
    
    public boolean asBoolean(final boolean defaultValue) {
        if (this.value instanceof Boolean) {
            return (boolean)this.value;
        }
        return defaultValue;
    }
    
    public byte asByte(final byte defaultValue) {
        if (this.value instanceof Number) {
            return ((Number)this.value).byteValue();
        }
        return defaultValue;
    }
    
    public char asChar(final char defaultValue) {
        if (this.value instanceof Number) {
            return (char)((Number)this.value).intValue();
        }
        if (this.value instanceof Character) {
            return (char)this.value;
        }
        if (!(this.value instanceof String)) {
            return defaultValue;
        }
        if (((String)this.value).length() == 1) {
            return ((String)this.value).charAt(0);
        }
        return defaultValue;
    }
    
    public short asShort(final short defaultValue) {
        if (this.value instanceof Number) {
            return ((Number)this.value).shortValue();
        }
        return defaultValue;
    }
    
    public int asInt(final int defaultValue) {
        if (this.value instanceof Number) {
            return ((Number)this.value).intValue();
        }
        return defaultValue;
    }
    
    public long asLong(final long defaultValue) {
        if (this.value instanceof Number) {
            return ((Number)this.value).longValue();
        }
        return defaultValue;
    }
    
    public float asFloat(final float defaultValue) {
        if (this.value instanceof Number) {
            return ((Number)this.value).floatValue();
        }
        return defaultValue;
    }
    
    public double asDouble(final double defaultValue) {
        if (this.value instanceof Number) {
            return ((Number)this.value).doubleValue();
        }
        return defaultValue;
    }
    
    public BigInteger asBigInteger(final BigInteger defaultValue) {
        if (this.value instanceof Number) {
            return BigInteger.valueOf(((Number)this.value).longValue());
        }
        if (this.value instanceof String) {
            return new BigInteger((String)this.value, 16);
        }
        return defaultValue;
    }
    
    public BigDecimal asBigDecimal(final BigDecimal defaultValue) {
        if (this.value instanceof Number) {
            return BigDecimal.valueOf(((Number)this.value).doubleValue());
        }
        if (this.value instanceof String) {
            return new BigDecimal((String)this.value);
        }
        return defaultValue;
    }
    
    @Nonnull
    @Override
    public String toString() {
        return this.toJson();
    }
    
    @Nonnull
    public Object getValue() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object other) {
        return other != null && other instanceof JsonPrimitive && Objects.equals(this.value, ((JsonPrimitive)other).value);
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public String toJson(final boolean comments, final boolean newlines, final int depth) {
        return this.toJson(JsonGrammar.builder().withComments(comments).printWhitespace(newlines).build(), depth);
    }
    
    @Override
    public void toJson(final Writer writer, final JsonGrammar grammar, final int depth) throws IOException {
        if (this.value == null) {
            writer.write("null");
            return;
        }
        if (this.value instanceof Double && grammar.isBareSpecialNumerics()) {
            final double d = (double)this.value;
            if (Double.isNaN(d)) {
                writer.write("NaN");
                return;
            }
            if (!Double.isInfinite(d)) {
                writer.write(this.value.toString());
                return;
            }
            if (d < 0.0) {
                writer.write("-Infinity");
                return;
            }
            writer.write("Infinity");
        }
        else {
            if (this.value instanceof Number) {
                writer.write(this.value.toString());
                return;
            }
            if (this.value instanceof Boolean) {
                writer.write(this.value.toString());
                return;
            }
            writer.write(34);
            writer.write(Escaper.escapeString(this.value.toString()));
            writer.write(34);
        }
    }
    
    @Override
    public JsonPrimitive clone() {
        final JsonPrimitive result = new JsonPrimitive();
        result.value = this.value;
        return result;
    }
    
    public static JsonPrimitive of(@Nonnull final String s) {
        final JsonPrimitive result = new JsonPrimitive();
        result.value = s;
        return result;
    }
    
    public static JsonPrimitive of(@Nonnull final BigInteger n) {
        final JsonPrimitive result = new JsonPrimitive();
        result.value = n.toString(16);
        return result;
    }
    
    public static JsonPrimitive of(@Nonnull final BigDecimal n) {
        final JsonPrimitive result = new JsonPrimitive();
        result.value = n.toString();
        return result;
    }
    
    public static JsonPrimitive of(@Nonnull final Double d) {
        final JsonPrimitive result = new JsonPrimitive();
        result.value = d;
        return result;
    }
    
    public static JsonPrimitive of(@Nonnull final Long l) {
        final JsonPrimitive result = new JsonPrimitive();
        result.value = l;
        return result;
    }
    
    public static JsonPrimitive of(@Nonnull final Boolean b) {
        final JsonPrimitive result = new JsonPrimitive();
        result.value = b;
        return result;
    }
    
    static {
        JsonPrimitive.TRUE = new JsonPrimitive(Boolean.TRUE);
        JsonPrimitive.FALSE = new JsonPrimitive(Boolean.FALSE);
    }
}
