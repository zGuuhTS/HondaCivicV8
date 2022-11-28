// 
// Decompiled by Procyon v0.5.36
// 

package blue.endless.jankson.impl.serializer;

import blue.endless.jankson.api.io.DeserializationException;
import blue.endless.jankson.api.JsonGrammar;
import blue.endless.jankson.api.element.JsonArray;
import blue.endless.jankson.api.element.JsonObject;
import blue.endless.jankson.api.element.JsonPrimitive;
import blue.endless.jankson.api.Marshaller;
import blue.endless.jankson.api.element.JsonElement;
import java.util.HashMap;

public class DeserializerFunctionPool<B>
{
    private Class<B> targetClass;
    private HashMap<Class<?>, InternalDeserializerFunction<B>> values;
    
    public DeserializerFunctionPool(final Class<B> targetClass) {
        this.values = new HashMap<Class<?>, InternalDeserializerFunction<B>>();
        this.targetClass = targetClass;
    }
    
    public void registerUnsafe(final Class<?> sourceClass, final InternalDeserializerFunction<B> function) {
        this.values.put(sourceClass, function);
    }
    
    public InternalDeserializerFunction<B> getFunction(final Class<?> sourceClass) {
        return this.values.get(sourceClass);
    }
    
    public B apply(final JsonElement elem, final Marshaller marshaller) throws DeserializationException, FunctionMatchFailedException {
        InternalDeserializerFunction<B> selected = null;
        if (elem instanceof JsonPrimitive) {
            final Object obj = ((JsonPrimitive)elem).getValue();
            selected = this.values.get(obj.getClass());
            if (selected != null) {
                return selected.deserialize(obj, marshaller);
            }
            selected = this.values.get(JsonPrimitive.class);
            if (selected != null) {
                return selected.deserialize(elem, marshaller);
            }
        }
        else if (elem instanceof JsonObject) {
            selected = this.values.get(JsonObject.class);
            if (selected != null) {
                return selected.deserialize(elem, marshaller);
            }
        }
        else if (elem instanceof JsonArray) {
            selected = this.values.get(JsonArray.class);
            if (selected != null) {
                return selected.deserialize(elem, marshaller);
            }
        }
        selected = this.values.get(JsonElement.class);
        if (selected != null) {
            return selected.deserialize(elem, marshaller);
        }
        throw new FunctionMatchFailedException("Couldn't find a deserializer in class '" + this.targetClass.getCanonicalName() + "' to unpack element '" + elem.toJson(JsonGrammar.JSON5) + "'.");
    }
    
    public static class FunctionMatchFailedException extends Exception
    {
        private static final long serialVersionUID = -7909332778483440658L;
        
        public FunctionMatchFailedException(final String message) {
            super(message);
        }
    }
}
