// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.menu;

import java.util.TreeMap;
import java.util.Optional;
import java.util.function.Function;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.Sponge;
import java.util.List;
import org.spongepowered.api.scheduler.Task;
import java.util.Iterator;
import com.focamacho.bvrtournaments.util.ItemUtils;
import java.util.Map;
import com.focamacho.sealmenus.sponge.item.MenuItem;
import com.focamacho.sealmenus.sponge.item.ClickableItem;
import com.focamacho.sealmenus.sponge.PageableChestMenu;
import com.focamacho.sealmenus.sponge.ChestMenu;
import com.focamacho.sealmenus.sponge.SealMenus;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import com.focamacho.bvrtournaments.controller.DatabaseController;
import java.util.SortedMap;
import com.focamacho.bvrtournaments.BVRTournaments;

public class TopPointsMenu extends Menu
{
    private static final BVRTournaments plugin;
    private static final SortedMap<DatabaseController.RankedUser, ItemStack> cachedHeads;
    
    public TopPointsMenu(final Player player, final BVRTournaments plugin) {
        super(SealMenus.createPageableChestMenu(plugin.getMenusConfig().topPoints.inventoryName, plugin.getMenusConfig().topPoints.inventorySize, plugin.getMenusConfig().topPoints.playersSlots, plugin), plugin);
        final com.focamacho.bvrtournaments.config.Menu.TopPoints config = plugin.getMenusConfig().topPoints;
        final PageableChestMenu menu = (PageableChestMenu)this.menu;
        menu.setNextPageItem(config.nextPageItem.createItem(), config.nextPageItem.itemSlot);
        menu.setPreviousPageItem(config.previousPageItem.createItem(), config.previousPageItem.itemSlot);
        for (final com.focamacho.bvrtournaments.config.Menu.ItemWithSlot decorativeItem : plugin.getMenusConfig().topPoints.decorativeItems) {
            this.menu.addItem(ClickableItem.create(decorativeItem.createItem()), decorativeItem.itemSlot);
        }
        if (TopPointsMenu.cachedHeads.isEmpty()) {
            updateTopHeads();
        }
        int headsToPut = config.pages * config.playersSlots.length;
        for (final Map.Entry<DatabaseController.RankedUser, ItemStack> entry : TopPointsMenu.cachedHeads.entrySet()) {
            if (headsToPut == 0) {
                break;
            }
            if (entry.getKey().getPoints() <= 0) {
                continue;
            }
            menu.addPageableItem(ClickableItem.create(entry.getValue()));
            --headsToPut;
        }
        menu.addItem(ClickableItem.create(ItemUtils.applyNameAndLore(ItemUtils.getHead(player.getName()), config.playerLoadingItem.itemName, config.playerLoadingItem.itemLore, new Object[0])), config.playerItem.itemSlot);
        final com.focamacho.bvrtournaments.config.Menu.TopPoints topPoints;
        final ClickableItem userHead;
        final PageableChestMenu pageableChestMenu;
        plugin.getDatabaseController().getUser(player.getName()).whenComplete((user, throwable) -> {
            userHead = ClickableItem.create(ItemUtils.applyNameAndLore(ItemUtils.getHead(user.getName()), topPoints.playerItem.item.itemName, topPoints.playerItem.item.itemLore, user.getName(), (user.getRank() == -1) ? plugin.getLang().others.missingRank : user.getRank(), user.getPoints(), plugin.getConfigController().getEloNameForPoints(user.getPoints())));
            pageableChestMenu.addItem(userHead, topPoints.playerItem.itemSlot);
        });
    }
    
    public static void updateHead(final DatabaseController.RankedUser user) {
        final ItemStack head;
        Task.builder().execute(() -> {
            head = ItemUtils.applyNameAndLore(ItemUtils.getHead(user.getName()), TopPointsMenu.plugin.getMenusConfig().topPoints.playersItem.itemName, TopPointsMenu.plugin.getMenusConfig().topPoints.playersItem.itemLore, user.getName(), (user.getRank() == -1) ? TopPointsMenu.plugin.getLang().others.missingRank : user.getRank(), user.getPoints(), TopPointsMenu.plugin.getConfigController().getEloNameForPoints(user.getPoints()));
            synchronized (TopPointsMenu.cachedHeads) {
                TopPointsMenu.cachedHeads.put(user, head);
            }
        }).async().submit((Object)TopPointsMenu.plugin);
    }
    
    public static void updateTopHeads() {
        TopPointsMenu.cachedHeads.clear();
        final int maxHeads = TopPointsMenu.plugin.getMenusConfig().topPoints.playersSlots.length * TopPointsMenu.plugin.getMenusConfig().topPoints.pages;
        final Iterator<DatabaseController.RankedUser> iterator;
        DatabaseController.RankedUser user;
        TopPointsMenu.plugin.getDatabaseController().getTopUsers(maxHeads).whenComplete((users, throwable) -> {
            users.iterator();
            while (iterator.hasNext()) {
                user = iterator.next();
                updateHead(user);
            }
        });
    }
    
    static {
        plugin = Sponge.getPluginManager().getPlugin("bvrtournaments").flatMap(PluginContainer::getInstance).get();
        cachedHeads = new TreeMap<DatabaseController.RankedUser, ItemStack>((user1, user2) -> user1.getName().equalsIgnoreCase(user2.getName()) ? 0 : ((user1.getRank() > user2.getRank()) ? 1 : -1));
    }
}
