// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealmenus.sponge;

import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import java.util.function.Consumer;
import java.util.Objects;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.entity.living.player.Player;
import com.focamacho.sealmenus.sponge.item.ClickableItem;
import org.spongepowered.api.item.inventory.ItemStack;
import java.util.Collection;
import java.util.Collections;
import com.google.common.collect.Lists;
import java.util.AbstractMap;
import com.focamacho.sealmenus.sponge.item.MenuItem;
import java.util.List;

public class PageableChestMenu extends ChestMenu
{
    private final int[] itemSlots;
    private List<MenuItem> pageableItems;
    private int page;
    private AbstractMap.SimpleEntry<Integer, MenuItem> nextPageItem;
    private AbstractMap.SimpleEntry<Integer, MenuItem> previousPageItem;
    private List<PageableChestMenu> mirrorMenus;
    private PageableChestMenu fatherMenu;
    
    PageableChestMenu(final String title, final int rows, final int[] itemSlots, final Object plugin) {
        super(title, rows, plugin);
        this.nextPageItem = null;
        this.previousPageItem = null;
        this.mirrorMenus = (List<PageableChestMenu>)Lists.newArrayList();
        this.itemSlots = itemSlots;
        this.page = 0;
        this.pageableItems = (List<MenuItem>)Lists.newArrayList();
        this.fatherMenu = null;
    }
    
    private PageableChestMenu(final PageableChestMenu father) {
        this(father.getTitle(), father.getRows(), father.getItemSlots(), father.plugin);
        this.items = father.items;
        this.pageableItems = father.pageableItems;
        if (father.nextPageItem != null) {
            this.setNextPageItem(father.nextPageItem.getValue().getItem(), father.nextPageItem.getKey());
        }
        if (father.previousPageItem != null) {
            this.setPreviousPageItem(father.previousPageItem.getValue().getItem(), father.previousPageItem.getKey());
        }
        this.mirrorMenus = null;
        this.fatherMenu = father;
        father.mirrorMenus.add(this);
    }
    
    public PageableChestMenu addPageableItem(final MenuItem item, final int index) {
        this.pageableItems.add(Math.min(index, this.pageableItems.size()), item);
        this.requireUpdate(null);
        return this;
    }
    
    public PageableChestMenu addPageableItem(final MenuItem item) {
        return this.addPageableItem(item, this.pageableItems.size());
    }
    
    public PageableChestMenu removePageableItem(final MenuItem item) {
        this.pageableItems.remove(item);
        this.requireUpdate(null);
        return this;
    }
    
    public List<MenuItem> getPageableItems() {
        return Collections.unmodifiableList((List<? extends MenuItem>)this.pageableItems);
    }
    
    public PageableChestMenu setPageableItems(final List<MenuItem> items) {
        this.pageableItems.clear();
        this.pageableItems.addAll(items);
        this.requireUpdate(null);
        return this;
    }
    
    public PageableChestMenu clearPageableItems() {
        this.pageableItems.clear();
        this.requireUpdate(null);
        return this;
    }
    
    public int getPageCount() {
        return (int)Math.max(1.0, Math.ceil(this.pageableItems.size() / (double)this.itemSlots.length));
    }
    
    public PageableChestMenu setNextPageItem(final ItemStack item, final int slot) {
        if (slot < 0 || slot >= this.getRows() * 9) {
            throw new IllegalArgumentException("The slot can't be less than zero or greater than the inventory size.");
        }
        for (final int slotIndex : this.itemSlots) {
            if (slot == slotIndex) {
                throw new IllegalArgumentException("You can't add an item in a slot reserved for pageable items.");
            }
        }
        if (this.fatherMenu == null) {
            this.mirrorMenus.forEach(menu -> menu.setNextPageItem(item, slot));
        }
        final Integer oldSlot = (this.nextPageItem != null) ? this.nextPageItem.getKey() : null;
        this.nextPageItem = new AbstractMap.SimpleEntry<Integer, MenuItem>(slot, ClickableItem.create(item).setOnPrimary(click -> {
            if (click.getSource() instanceof Player) {
                Task.builder().execute(() -> {
                    if (this.page + 1 < this.getPageCount()) {
                        ++this.page;
                        this.update();
                    }
                }).submit(this.plugin);
            }
            return;
        }));
        this.requireUpdate(slot);
        if (!Objects.equals(oldSlot, slot)) {
            this.requireUpdate(oldSlot);
        }
        return this;
    }
    
    public PageableChestMenu setPreviousPageItem(final ItemStack item, final int slot) {
        if (slot < 0 || slot >= this.getRows() * 9) {
            throw new IllegalArgumentException("The slot can't be less than zero or greater than the inventory size.");
        }
        for (final int slotIndex : this.itemSlots) {
            if (slot == slotIndex) {
                throw new IllegalArgumentException("You can't add an item in a slot reserved for pageable items.");
            }
        }
        if (this.fatherMenu == null) {
            this.mirrorMenus.forEach(menu -> menu.setPreviousPageItem(item, slot));
        }
        final Integer oldSlot = (this.previousPageItem != null) ? this.previousPageItem.getKey() : null;
        this.previousPageItem = new AbstractMap.SimpleEntry<Integer, MenuItem>(slot, ClickableItem.create(item).setOnPrimary(click -> {
            if (click.getSource() instanceof Player) {
                Task.builder().execute(() -> {
                    if (this.page > 0) {
                        --this.page;
                        this.update();
                    }
                }).submit(this.plugin);
            }
            return;
        }));
        this.requireUpdate(slot);
        if (!Objects.equals(oldSlot, slot)) {
            this.requireUpdate(oldSlot);
        }
        return this;
    }
    
    @Override
    public ChestMenu addItem(final MenuItem item, final int slot) {
        for (final int slotIndex : this.itemSlots) {
            if (slot == slotIndex) {
                throw new IllegalArgumentException("You can't add an item in a slot reserved for pageable items.");
            }
        }
        return super.addItem(item, slot);
    }
    
    @Override
    public boolean containsItem(final Integer slot) {
        if (this.nextPageItem != null && Objects.equals(slot, this.nextPageItem.getKey()) && this.getPageCount() > this.page + 1) {
            return true;
        }
        if (this.previousPageItem != null && Objects.equals(slot, this.previousPageItem.getKey()) && this.page > 0) {
            return true;
        }
        int i = 0;
        while (i < this.itemSlots.length) {
            final int itemSlot = this.itemSlots[i];
            if (slot == itemSlot) {
                if (this.pageableItems.size() - 1 >= this.itemSlots.length * this.page + i) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return super.containsItem(slot);
    }
    
    @Override
    public void requireUpdate(final Integer slot) {
        if (this.fatherMenu == null) {
            this.mirrorMenus.forEach(menu -> menu.requireUpdate(slot));
        }
        if (this.inventory != null) {
            if (this.hasViewers()) {
                if (slot == null) {
                    this.update();
                }
                else {
                    this.update(slot);
                }
            }
            else {
                this.slotsRequiringUpdate.add(slot);
            }
        }
    }
    
    @Override
    public MenuItem getItem(final Integer slot) {
        if (this.nextPageItem != null && Objects.equals(slot, this.nextPageItem.getKey()) && this.getPageCount() > this.page + 1) {
            return this.nextPageItem.getValue();
        }
        if (this.previousPageItem != null && Objects.equals(slot, this.previousPageItem.getKey()) && this.page > 0) {
            return this.previousPageItem.getValue();
        }
        int i = 0;
        while (i < this.itemSlots.length) {
            final int itemSlot = this.itemSlots[i];
            if (slot == itemSlot) {
                if (this.pageableItems.size() - 1 >= this.itemSlots.length * this.page + i) {
                    return this.pageableItems.get(this.itemSlots.length * this.page + i);
                }
                break;
            }
            else {
                ++i;
            }
        }
        return super.getItem(slot);
    }
    
    @Override
    public void open(final Player player) {
        if (this.inventory == null || !super.hasViewers()) {
            this.page = 0;
            super.open(player);
        }
        else {
            new PageableChestMenu(this).open(player);
        }
    }
    
    @Override
    public boolean hasViewers() {
        return super.hasViewers() || (this.fatherMenu == null && this.mirrorMenus.stream().anyMatch(PageableChestMenu::hasViewers));
    }
    
    @Override
    public ChestMenu copy() {
        final PageableChestMenu copy = new PageableChestMenu(this.getTitle(), this.getRows(), this.getItemSlots(), this.plugin);
        copy.setOnOpen(this.getOnOpen());
        copy.setOnClose(this.getOnClose());
        copy.setOnPrimary(this.getOnPrimary());
        copy.setOnSecondary(this.getOnSecondary());
        copy.setOnDrop(this.getOnDrop());
        copy.setOnDropAll(this.getOnDropAll());
        copy.setOnMiddle(this.getOnMiddle());
        copy.setOnNumber(this.getOnNumber());
        copy.setOnShiftPrimary(this.getOnShiftPrimary());
        copy.setOnShiftSecondary(this.getOnShiftSecondary());
        copy.setOnDouble(this.getOnDouble());
        if (this.nextPageItem != null) {
            copy.setNextPageItem(this.nextPageItem.getValue().getItem(), this.nextPageItem.getKey());
        }
        if (this.previousPageItem != null) {
            copy.setPreviousPageItem(this.previousPageItem.getValue().getItem(), this.previousPageItem.getKey());
        }
        this.getItems().forEach((slot, item) -> copy.addItem(item.copy(), slot));
        copy.setPageableItems(this.getPageableItems());
        return copy;
    }
    
    @Override
    protected void handlesUpdateItemsTask() {
        if (this.fatherMenu == null) {
            super.handlesUpdateItemsTask();
        }
        else {
            this.fatherMenu.handlesUpdateItemsTask();
        }
    }
    
    @Override
    public Consumer<InteractInventoryEvent.Open> getOnOpen() {
        return (this.fatherMenu == null) ? super.getOnOpen() : this.fatherMenu.getOnOpen();
    }
    
    @Override
    public Consumer<InteractInventoryEvent.Close> getOnClose() {
        return (this.fatherMenu == null) ? super.getOnClose() : this.fatherMenu.getOnClose();
    }
    
    @Override
    public Consumer<ClickInventoryEvent> getOnClick() {
        return (this.fatherMenu == null) ? super.getOnClick() : this.fatherMenu.getOnClick();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Primary> getOnPrimary() {
        return (this.fatherMenu == null) ? super.getOnPrimary() : this.fatherMenu.getOnPrimary();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Middle> getOnMiddle() {
        return (this.fatherMenu == null) ? super.getOnMiddle() : this.fatherMenu.getOnMiddle();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Secondary> getOnSecondary() {
        return (this.fatherMenu == null) ? super.getOnSecondary() : this.fatherMenu.getOnSecondary();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Shift.Primary> getOnShiftPrimary() {
        return (this.fatherMenu == null) ? super.getOnShiftPrimary() : this.fatherMenu.getOnShiftPrimary();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Double> getOnDouble() {
        return (this.fatherMenu == null) ? super.getOnDouble() : this.fatherMenu.getOnDouble();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Drop.Single> getOnDrop() {
        return (this.fatherMenu == null) ? super.getOnDrop() : this.fatherMenu.getOnDrop();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Shift.Secondary> getOnShiftSecondary() {
        return (this.fatherMenu == null) ? super.getOnShiftSecondary() : this.fatherMenu.getOnShiftSecondary();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.Drop.Full> getOnDropAll() {
        return (this.fatherMenu == null) ? super.getOnDropAll() : this.fatherMenu.getOnDropAll();
    }
    
    @Override
    public Consumer<ClickInventoryEvent.NumberPress> getOnNumber() {
        return (this.fatherMenu == null) ? super.getOnNumber() : this.fatherMenu.getOnNumber();
    }
    
    public int[] getItemSlots() {
        return this.itemSlots;
    }
}
