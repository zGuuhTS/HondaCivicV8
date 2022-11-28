// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealheads.sponge;

import org.spongepowered.api.data.manipulator.mutable.RepresentedPlayerData;
import org.spongepowered.api.data.value.ValueContainer;
import org.spongepowered.api.data.type.SkullTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.manipulator.mutable.SkullData;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.profile.property.ProfileProperty;
import org.spongepowered.api.profile.GameProfile;
import com.focamacho.sealheads.TextureFetcher;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import org.spongepowered.api.entity.living.player.Player;
import java.util.UUID;
import java.util.function.Function;
import org.spongepowered.api.item.inventory.ItemStack;
import java.util.Optional;

public class SealHeads
{
    public static Optional<ItemStack> getHead(final String player) {
        final Optional<String> texture = getTexture(player);
        return texture.map((Function<? super String, ? extends ItemStack>)SealHeads::createHeadFromTexture);
    }
    
    public static Optional<ItemStack> getHead(final UUID player) {
        final Optional<String> texture = getTexture(player);
        return texture.map((Function<? super String, ? extends ItemStack>)SealHeads::createHeadFromTexture);
    }
    
    public static Optional<ItemStack> getHead(final Player player) {
        return getHead(player.getName());
    }
    
    public static CompletableFuture<Optional<ItemStack>> getHead(final String player, final ExecutorService service) {
        final CompletableFuture<Optional<ItemStack>> future = new CompletableFuture<Optional<ItemStack>>();
        final Optional<ItemStack> head;
        final CompletableFuture<Optional<ItemStack>> completableFuture;
        service.execute(() -> {
            head = getHead(player);
            completableFuture.complete(head);
            return;
        });
        return future;
    }
    
    public static CompletableFuture<Optional<ItemStack>> getHead(final UUID player, final ExecutorService service) {
        final CompletableFuture<Optional<ItemStack>> future = new CompletableFuture<Optional<ItemStack>>();
        final Optional<ItemStack> head;
        final CompletableFuture<Optional<ItemStack>> completableFuture;
        service.execute(() -> {
            head = getHead(player);
            completableFuture.complete(head);
            return;
        });
        return future;
    }
    
    public static CompletableFuture<Optional<ItemStack>> getHead(final Player player, final ExecutorService service) {
        final CompletableFuture<Optional<ItemStack>> future = new CompletableFuture<Optional<ItemStack>>();
        final Optional<ItemStack> head;
        final CompletableFuture<Optional<ItemStack>> completableFuture;
        service.execute(() -> {
            head = getHead(player);
            completableFuture.complete(head);
            return;
        });
        return future;
    }
    
    public static Optional<String> getTexture(final String player) {
        return TextureFetcher.getTexture(player);
    }
    
    public static Optional<String> getTexture(final UUID player) {
        return TextureFetcher.getTexture(player);
    }
    
    public static Optional<String> getTexture(final Player player) {
        return getTexture(player.getName());
    }
    
    public static CompletableFuture<Optional<String>> getTexture(final String player, final ExecutorService service) {
        final CompletableFuture<Optional<String>> future = new CompletableFuture<Optional<String>>();
        final Optional<String> texture;
        final CompletableFuture<Optional<String>> completableFuture;
        service.execute(() -> {
            texture = getTexture(player);
            completableFuture.complete(texture);
            return;
        });
        return future;
    }
    
    public static CompletableFuture<Optional<String>> getTexture(final Player player, final ExecutorService service) {
        final CompletableFuture<Optional<String>> future = new CompletableFuture<Optional<String>>();
        final Optional<String> texture;
        final CompletableFuture<Optional<String>> completableFuture;
        service.execute(() -> {
            texture = getTexture(player);
            completableFuture.complete(texture);
            return;
        });
        return future;
    }
    
    public static CompletableFuture<Optional<String>> getTexture(final UUID player, final ExecutorService service) {
        final CompletableFuture<Optional<String>> future = new CompletableFuture<Optional<String>>();
        final Optional<String> texture;
        final CompletableFuture<Optional<String>> completableFuture;
        service.execute(() -> {
            texture = getTexture(player);
            completableFuture.complete(texture);
            return;
        });
        return future;
    }
    
    public static ItemStack createHeadFromTexture(final String texture) {
        final GameProfile profile = GameProfile.of(UUID.randomUUID());
        profile.getPropertyMap().put((Object)"textures", (Object)ProfileProperty.of("value", texture));
        final ItemStack stack = ItemStack.builder().itemType(ItemTypes.SKULL).build();
        final SkullData skullData = (SkullData)((SkullData)Sponge.getDataManager().getManipulatorBuilder((Class)SkullData.class).get().create()).set(Keys.SKULL_TYPE, (Object)SkullTypes.PLAYER);
        stack.offer((ValueContainer)skullData);
        final RepresentedPlayerData playerData = (RepresentedPlayerData)((RepresentedPlayerData)Sponge.getDataManager().getManipulatorBuilder((Class)RepresentedPlayerData.class).get().create()).set(Keys.REPRESENTED_PLAYER, (Object)profile);
        stack.offer((ValueContainer)playerData);
        return stack;
    }
}
