// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.util;

import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.ItemTypes;
import com.google.common.collect.Maps;
import java.util.Optional;
import java.util.function.Function;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.Sponge;
import com.focamacho.sealheads.sponge.SealHeads;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import java.util.List;
import com.google.common.collect.Lists;
import org.spongepowered.api.data.key.Keys;
import java.util.Map;
import org.spongepowered.api.item.inventory.ItemStack;
import com.focamacho.bvrtournaments.BVRTournaments;

public class ItemUtils
{
    private static final BVRTournaments plugin;
    private static final ItemStack defaultSkull;
    private static final Map<String, ItemStack> cachedHeads;
    
    public static ItemStack applyNameAndLore(final ItemStack stack, final String name, final String[] lore, final Object... args) {
        final ItemStack itemStack = ItemStack.builder().from(stack).build();
        if (!name.isEmpty()) {
            itemStack.offer(Keys.DISPLAY_NAME, (Object)TextUtils.formatText(name, args));
        }
        final List<Text> listLore = (List<Text>)Lists.newArrayList();
        for (final String s : lore) {
            listLore.add(TextUtils.formatText(s, args));
        }
        itemStack.offer(Keys.ITEM_LORE, (Object)listLore);
        return itemStack;
    }
    
    public static ItemStack getHead(final String name) {
        if (ItemUtils.cachedHeads.containsKey(name)) {
            return ItemUtils.cachedHeads.get(name);
        }
        final ItemStack itemStack;
        Task.builder().execute(() -> SealHeads.getHead(name).ifPresent(head -> itemStack = ItemUtils.cachedHeads.put(name, head))).async().submit((Object)ItemUtils.plugin);
        return ItemUtils.defaultSkull;
    }
    
    public static ItemStack getDefaultSkull() {
        return ItemUtils.defaultSkull;
    }
    
    static {
        plugin = Sponge.getPluginManager().getPlugin("bvrtournaments").flatMap(PluginContainer::getInstance).get();
        cachedHeads = Maps.newHashMap();
        defaultSkull = ItemStack.builder().fromContainer((DataView)ItemStack.builder().itemType(ItemTypes.SKULL).build().toContainer().set(DataQuery.of(new String[] { "UnsafeDamage" }), (Object)3)).build();
    }
}
