// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.config;

import org.spongepowered.api.data.DataContainer;
import java.util.Optional;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.inventory.ItemStack;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.annotation.Comment;

public class Menu
{
    @Comment("Top Points menu.")
    public TopPoints topPoints;
    @Comment("Tier menu.")
    public Tier tier;
    
    public Menu() {
        this.topPoints = new TopPoints();
        this.tier = new Tier();
    }
    
    public static class TopPoints
    {
        @Comment("Inventory name.")
        public String inventoryName;
        @Comment("The amount of lines the inventory will have. (Max of 6)")
        public int inventorySize;
        @Comment("Slot numbers for the players heads.")
        public int[] playersSlots;
        @Comment("Amount of pages available on the menu.")
        public int pages;
        @Comment("Item that will be used to represent the player that opened the menu. Uses the player skull as icon.")
        public ItemWithSlot playerItem;
        @Comment("Item that will be used while the player stats haven't been loaded yet. Uses the player skull as icon.")
        public Item playerLoadingItem;
        @Comment("Item that will be used to represent a player. Uses the player skull as icon.")
        public Item playersItem;
        @Comment("Item that will be used to go to the next page of the inventory.")
        public ItemWithSlot nextPageItem;
        @Comment("Item that will be used to go to the previous page of the inventory.")
        public ItemWithSlot previousPageItem;
        @Comment("Extra items in the menu, that don't have any actions.")
        public ItemWithSlot[] decorativeItems;
        
        public TopPoints() {
            this.inventoryName = "Top Jogadores";
            this.inventorySize = 6;
            this.playersSlots = new int[] { 11, 12, 13, 14, 15, 20, 21, 22, 23, 24 };
            this.pages = 1;
            this.playerItem = new ItemWithSlot(40, "", "&bEste aqui \u00e9 voc\u00ea!", new String[] { "&cRank: &7{1}", "&ePontos: &7{2}", "&dElo: &7{3}" });
            this.playerLoadingItem = new Item("", "&eCarregando seus dados...", new String[] { "", "&7Aguarde um pouquinho!" });
            this.playersItem = new Item("", "&b{0}", new String[] { "&cRank: &7{1}", "&ePontos: &7{2}", "&dElo: &7{3}" });
            this.nextPageItem = new ItemWithSlot(53, "minecraft:arrow", "&bPr\u00f3xima P\u00e1gina", new String[] { "&7&l\u21db &eClique para ir at\u00e9", "&ea pr\u00f3xima p\u00e1gina." });
            this.previousPageItem = new ItemWithSlot(45, "minecraft:arrow", "&bP\u00e1gina Anterior", new String[] { "&7&l\u21db &eClique para ir at\u00e9", "&ea p\u00e1gina anterior." });
            this.decorativeItems = new ItemWithSlot[0];
        }
    }
    
    public static class Tier
    {
        @Comment("Inventory name. Use {0} to represent the tier name.")
        public String inventoryName;
        @Comment("The amount of lines the inventory will have. (Max of 6)")
        public int inventorySize;
        @Comment("Slot numbers for the pokemons.")
        public int[] pokemonSlots;
        @Comment("Item that represents a pokemon in the menu. Uses the pokemon sprite as icon.")
        public Item pokemonIconItem;
        @Comment("Item that will be used to go to the next page of the inventory.")
        public ItemWithSlot nextPageItem;
        @Comment("Item that will be used to go to the previous page of the inventory.")
        public ItemWithSlot previousPageItem;
        @Comment("Extra items in the menu, that don't have any actions.")
        public ItemWithSlot[] decorativeItems;
        
        public Tier() {
            this.inventoryName = "{0}";
            this.inventorySize = 6;
            this.pokemonSlots = new int[] { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43 };
            this.pokemonIconItem = new Item("", "&e{0} {1}", new String[0]);
            this.nextPageItem = new ItemWithSlot(53, "minecraft:arrow", "&bPr\u00f3xima P\u00e1gina", new String[] { "&7&l\u21db &eClique para ir at\u00e9", "&ea pr\u00f3xima p\u00e1gina." });
            this.previousPageItem = new ItemWithSlot(45, "minecraft:arrow", "&bP\u00e1gina Anterior", new String[] { "&7&l\u21db &eClique para ir at\u00e9", "&ea p\u00e1gina anterior." });
            this.decorativeItems = new ItemWithSlot[0];
        }
    }
    
    public static class Item
    {
        public String itemId;
        public String itemName;
        public String[] itemLore;
        
        public Item(final String itemId, final String itemName, final String... itemLore) {
            this.itemId = "";
            this.itemName = "";
            this.itemLore = new String[0];
            this.itemId = itemId;
            this.itemName = itemName;
            this.itemLore = itemLore;
        }
        
        public ItemStack createItem(final Object... args) {
            if (!this.itemId.isEmpty()) {
                final String[] splitId = this.itemId.split(":");
                if (splitId.length > 1) {
                    final Optional<ItemType> type = (Optional<ItemType>)Sponge.getRegistry().getType((Class)ItemType.class, splitId[0] + ":" + splitId[1]);
                    if (type.isPresent()) {
                        final DataContainer container = ItemStack.of((ItemType)type.get()).toContainer();
                        if (splitId.length > 2) {
                            try {
                                container.set(DataQuery.of(new String[] { "UnsafeDamage" }), (Object)Integer.parseInt(splitId[2]));
                            }
                            catch (NumberFormatException ex) {}
                        }
                        final ItemStack.Builder builder = ItemStack.builder().fromContainer((DataView)container);
                        if (!this.itemName.isEmpty()) {
                            builder.add(Keys.DISPLAY_NAME, (Object)TextUtils.formatText(this.itemName, args));
                        }
                        if (this.itemLore.length > 0) {
                            builder.add(Keys.ITEM_LORE, (Object)TextUtils.formatTextLore(this.itemLore, args));
                        }
                        builder.add(Keys.HIDE_ATTRIBUTES, (Object)true);
                        builder.add(Keys.HIDE_ENCHANTMENTS, (Object)true);
                        builder.add(Keys.HIDE_MISCELLANEOUS, (Object)true);
                        return builder.build();
                    }
                }
            }
            return ItemStack.empty();
        }
        
        public Item() {
            this.itemId = "";
            this.itemName = "";
            this.itemLore = new String[0];
        }
    }
    
    public static class ItemWithSlot
    {
        public int itemSlot;
        public Item item;
        
        public ItemWithSlot(final int itemSlot, final String itemId, final String itemName, final String... itemLore) {
            this.itemSlot = 0;
            this.itemSlot = itemSlot;
            this.item = new Item(itemId, itemName, itemLore);
        }
        
        public ItemStack createItem() {
            return this.item.createItem(new Object[0]);
        }
        
        public ItemWithSlot() {
            this.itemSlot = 0;
        }
    }
}
