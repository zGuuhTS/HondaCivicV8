// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealheads;

import com.google.gson.GsonBuilder;
import java.util.Iterator;
import java.io.IOException;
import com.google.gson.JsonElement;
import java.io.Reader;
import java.io.InputStreamReader;
import com.google.gson.JsonObject;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Optional;
import java.util.UUID;
import com.google.gson.Gson;

public class TextureFetcher
{
    private static final Gson gson;
    
    public static Optional<String> getTexture(final UUID uuid) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s", uuid.toString().replace("-", ""))).openConnection();
            connection.setReadTimeout(5000);
            final JsonObject user = TextureFetcher.gson.fromJson(new InputStreamReader(connection.getInputStream()), JsonObject.class);
            for (final JsonElement propertyElement : user.get("properties").getAsJsonArray()) {
                final JsonObject property = propertyElement.getAsJsonObject();
                if (property.get("name").getAsString().equals("textures")) {
                    return Optional.of(property.get("value").getAsString());
                }
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        catch (NullPointerException ex) {}
        return Optional.empty();
    }
    
    public static Optional<String> getTexture(final String name) {
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", name)).openConnection();
            connection.setReadTimeout(5000);
            final JsonObject player = TextureFetcher.gson.fromJson(new InputStreamReader(connection.getInputStream()), JsonObject.class);
            final String userId = player.get("id").getAsString();
            return getTexture(UUID.fromString(userId.replaceAll("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5")));
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        catch (NullPointerException ex) {}
        return Optional.empty();
    }
    
    static {
        gson = new GsonBuilder().create();
    }
}
