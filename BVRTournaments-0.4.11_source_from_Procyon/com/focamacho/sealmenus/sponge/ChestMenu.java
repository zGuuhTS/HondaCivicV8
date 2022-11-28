// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealmenus.sponge;

import com.focamacho.sealmenus.sponge.item.ClickableItem;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.InventoryProperty;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import java.util.Optional;
import java.util.Iterator;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.ItemStack;
import java.util.Collections;
import java.util.Objects;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;
import java.util.HashMap;
import org.spongepowered.api.scheduler.Task;
import java.util.Set;
import org.spongepowered.api.entity.living.player.Player;
import java.util.List;
import org.spongepowered.api.item.inventory.Inventory;
import java.util.Map;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import java.util.function.Consumer;
import com.focamacho.sealmenus.sponge.item.MenuItem;

public class ChestMenu
{
    private static final MenuItem dummyItem;
    private final String title;
    private final int rows;
    protected final Object plugin;
    private Consumer<InteractInventoryEvent.Open> onOpen;
    private Consumer<InteractInventoryEvent.Close> onClose;
    private Consumer<ClickInventoryEvent> onClick;
    private Consumer<ClickInventoryEvent.Primary> onPrimary;
    private Consumer<ClickInventoryEvent.Middle> onMiddle;
    private Consumer<ClickInventoryEvent.Secondary> onSecondary;
    private Consumer<ClickInventoryEvent.Shift.Primary> onShiftPrimary;
    private Consumer<ClickInventoryEvent.Double> onDouble;
    private Consumer<ClickInventoryEvent.Drop.Single> onDrop;
    private Consumer<ClickInventoryEvent.Shift.Secondary> onShiftSecondary;
    private Consumer<ClickInventoryEvent.Drop.Full> onDropAll;
    private Consumer<ClickInventoryEvent.NumberPress> onNumber;
    protected Map<Integer, MenuItem> items;
    protected Inventory inventory;
    protected List<Player> playersViewing;
    protected final Set<Integer> slotsRequiringUpdate;
    private Task updateItemsTask;
    
    ChestMenu(final String title, final int rows, final Object plugin) {
        this.onOpen = (interact -> {});
        this.onClose = (interact -> {});
        this.onClick = (click -> {});
        this.onPrimary = (click -> {});
        this.onMiddle = (click -> {});
        this.onSecondary = (click -> {});
        this.onShiftPrimary = (click -> {});
        this.onDouble = (click -> {});
        this.onDrop = (click -> {});
        this.onShiftSecondary = (click -> {});
        this.onDropAll = (click -> {});
        this.onNumber = (click -> {});
        this.items = new HashMap<Integer, MenuItem>();
        this.playersViewing = (List<Player>)Lists.newArrayList();
        this.slotsRequiringUpdate = (Set<Integer>)Sets.newHashSet();
        this.updateItemsTask = null;
        if (rows <= 0 || rows > 6) {
            throw new IllegalArgumentException("The number of rows for a menu must be >= 1 && <= 6.");
        }
        this.title = Objects.requireNonNull(title);
        this.rows = rows;
        this.plugin = Objects.requireNonNull(plugin);
    }
    
    public ChestMenu addItem(final MenuItem item, final int slot) {
        if (slot < 0 || slot >= this.rows * 9) {
            throw new IllegalArgumentException("The slot can't be less than zero or greater than the inventory size.");
        }
        this.items.put(slot, item);
        this.requireUpdate(slot);
        return this;
    }
    
    public ChestMenu removeItem(final int slot) {
        this.items.remove(slot);
        this.requireUpdate(slot);
        return this;
    }
    
    public MenuItem getItem(final Integer slot) {
        return this.items.get(slot);
    }
    
    public Map<Integer, MenuItem> getItems() {
        return Collections.unmodifiableMap((Map<? extends Integer, ? extends MenuItem>)this.items);
    }
    
    public ItemStack getItemStack(final Integer slot) {
        for (final Inventory inventorySlot : this.inventory.slots()) {
            final Optional<SlotIndex> slotIndex = (Optional<SlotIndex>)inventorySlot.getInventoryProperty((Class)SlotIndex.class);
            if (slotIndex.isPresent() && Objects.equals(slotIndex.get().getValue(), slot)) {
                return inventorySlot.peek().orElse(ItemStack.empty());
            }
        }
        return ItemStack.empty();
    }
    
    public Map<Integer, ItemStack> getItemStacks() {
        final Map<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
        Optional<SlotIndex> slotIndex;
        final Map<Object, ItemStack> map;
        ItemStack itemStack;
        this.inventory.slots().forEach(slot -> {
            if (slot.peek().isPresent() && !((ItemStack)slot.peek().get()).isEmpty()) {
                slotIndex = (Optional<SlotIndex>)slot.getInventoryProperty((Class)SlotIndex.class);
                slotIndex.ifPresent(index -> itemStack = map.put(index.getValue(), slot.peek().get()));
            }
            return;
        });
        return Collections.unmodifiableMap((Map<? extends Integer, ? extends ItemStack>)items);
    }
    
    public boolean containsItem(final Integer slot) {
        return slot != null && this.items.containsKey(slot);
    }
    
    public ChestMenu setItems(final Map<Integer, MenuItem> items) {
        this.items.clear();
        items.forEach((slot, item) -> this.addItem(item, slot));
        this.requireUpdate(null);
        return this;
    }
    
    public ChestMenu clearItems() {
        this.items.clear();
        this.requireUpdate(null);
        return this;
    }
    
    public void update() {
        if (this.inventory == null) {
            Integer slot;
            MenuItem item;
            this.inventory = Inventory.builder().of(InventoryArchetypes.CHEST).property((InventoryProperty)InventoryTitle.of((Text)Text.of(this.title))).property((InventoryProperty)InventoryDimension.of(this.rows, 9)).listener((Class)ClickInventoryEvent.class, ce -> {
                if (ce instanceof ClickInventoryEvent.Double) {
                    ((ClickInventoryEvent)ce).setCancelled(true);
                }
                if (ce instanceof ClickInventoryEvent.Drag) {
                    ((ClickInventoryEvent)ce).setCancelled(true);
                }
                if (ce instanceof ClickInventoryEvent.Shift) {
                    ((ClickInventoryEvent)ce).setCancelled(true);
                }
                if (!((ClickInventoryEvent)ce).getSlot().isPresent()) {
                    ((ClickInventoryEvent)ce).setCancelled(true);
                    return;
                }
                else {
                    slot = (Integer)((SlotIndex)((Slot)((ClickInventoryEvent)ce).getSlot().get()).getInventoryProperty((Class)SlotIndex.class).get()).getValue();
                    if (slot == null) {
                        slot = -1;
                    }
                    if (slot < 9 * this.rows) {
                        ((ClickInventoryEvent)ce).setCancelled(true);
                        this.getOnClick().accept((ClickInventoryEvent)ce);
                        item = this.getItem(slot);
                        if (item == null) {
                            item = ChestMenu.dummyItem;
                        }
                        if (ce instanceof ClickInventoryEvent.Double) {
                            this.getOnDouble().accept((ClickInventoryEvent.Double)ce);
                            item.getOnDouble().accept((ClickInventoryEvent.Double)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.Shift.Primary) {
                            this.getOnShiftPrimary().accept((ClickInventoryEvent.Shift.Primary)ce);
                            item.getOnShiftPrimary().accept((ClickInventoryEvent.Shift.Primary)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.Shift.Secondary) {
                            this.getOnShiftSecondary().accept((ClickInventoryEvent.Shift.Secondary)ce);
                            item.getOnShiftSecondary().accept((ClickInventoryEvent.Shift.Secondary)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.Primary) {
                            this.getOnPrimary().accept((ClickInventoryEvent.Primary)ce);
                            item.getOnPrimary().accept((ClickInventoryEvent.Primary)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.Middle) {
                            this.getOnMiddle().accept((ClickInventoryEvent.Middle)ce);
                            item.getOnMiddle().accept((ClickInventoryEvent.Middle)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.Secondary) {
                            this.getOnSecondary().accept((ClickInventoryEvent.Secondary)ce);
                            item.getOnSecondary().accept((ClickInventoryEvent.Secondary)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.Drop.Full) {
                            this.getOnDropAll().accept((ClickInventoryEvent.Drop.Full)ce);
                            item.getOnDropAll().accept((ClickInventoryEvent.Drop.Full)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.Drop) {
                            this.getOnDrop().accept((ClickInventoryEvent.Drop.Single)ce);
                            item.getOnDrop().accept((ClickInventoryEvent.Drop.Single)ce);
                        }
                        else if (ce instanceof ClickInventoryEvent.NumberPress) {
                            this.getOnNumber().accept(ce);
                            item.getOnNumber().accept(ce);
                        }
                    }
                    return;
                }
            }).listener((Class)InteractInventoryEvent.class, ie -> {
                if (ie instanceof InteractInventoryEvent.Open) {
                    this.getOnOpen().accept((InteractInventoryEvent.Open)ie);
                    Task.builder().execute(this::handlesUpdateItemsTask).submit(this.plugin);
                }
                else if (ie instanceof InteractInventoryEvent.Close) {
                    this.getOnClose().accept(ie);
                    if (((InteractInventoryEvent)ie).getSource() instanceof Player) {
                        this.playersViewing.remove(((InteractInventoryEvent)ie).getSource());
                    }
                }
                return;
            }).build(this.plugin);
        }
        for (final Inventory slot2 : this.inventory.slots()) {
            final Integer slotIndex = (Integer)slot2.getInventoryProperty((Class)SlotIndex.class).get().getValue();
            final ItemStack slotStack = slot2.peek().orElse(ItemStack.empty());
            if (this.containsItem(slotIndex)) {
                final ItemStack stack = this.getItem(slotIndex).getItem();
                if (slotStack == stack) {
                    continue;
                }
                Task.builder().execute(() -> slot2.set(stack)).submit(this.plugin);
            }
            else {
                if (slotStack.isEmpty()) {
                    continue;
                }
                Task.builder().execute(slot2::clear).submit(this.plugin);
            }
        }
        this.slotsRequiringUpdate.clear();
    }
    
    public void update(final int slot) {
        if (this.inventory == null) {
            this.update();
        }
        for (final Inventory inventorySlot : this.inventory.slots()) {
            final Integer slotIndex = (Integer)inventorySlot.getInventoryProperty((Class)SlotIndex.class).get().getValue();
            if (Objects.equals(slot, slotIndex)) {
                if (this.containsItem(slot)) {
                    final ItemStack stack = this.getItem(slot).getItem();
                    if (inventorySlot.peek().orElse(ItemStack.empty()) != stack) {
                        Task.builder().execute(() -> inventorySlot.set(stack)).submit(this.plugin);
                    }
                    break;
                }
                Task.builder().execute(inventorySlot::clear).submit(this.plugin);
                break;
            }
        }
        this.slotsRequiringUpdate.remove(slot);
    }
    
    public void requireUpdate(final Integer slot) {
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
    
    public void open(final Player player) {
        if (this.inventory == null) {
            this.update();
        }
        Task.builder().execute(() -> {
            if (this.slotsRequiringUpdate.size() > 0) {
                if (this.slotsRequiringUpdate.contains(null)) {
                    this.update();
                }
                else {
                    this.slotsRequiringUpdate.forEach(this::update);
                }
            }
            player.closeInventory();
            player.openInventory(this.inventory).ifPresent(container -> this.playersViewing.add(player));
        }).submit(this.plugin);
    }
    
    public boolean hasViewers() {
        return this.playersViewing.size() > 0;
    }
    
    public ChestMenu copy() {
        final ChestMenu copy = new ChestMenu(this.title, this.rows, this.plugin);
        copy.setOnOpen(this.getOnOpen());
        copy.setOnClose(this.getOnClose());
        copy.setOnClick(this.getOnClick());
        copy.setOnPrimary(this.getOnPrimary());
        copy.setOnSecondary(this.getOnSecondary());
        copy.setOnDrop(this.getOnDrop());
        copy.setOnDropAll(this.getOnDropAll());
        copy.setOnMiddle(this.getOnMiddle());
        copy.setOnNumber(this.getOnNumber());
        copy.setOnShiftPrimary(this.getOnShiftPrimary());
        copy.setOnShiftSecondary(this.getOnShiftSecondary());
        copy.setOnDouble(this.getOnDouble());
        this.getItems().forEach((slot, item) -> copy.addItem(item.copy(), slot));
        return copy;
    }
    
    protected void handlesUpdateItemsTask() {
        if (this.updateItemsTask == null && this.hasViewers()) {
            this.updateItemsTask = Task.builder().intervalTicks(1L).execute(task -> {
                this.items.forEach((slot, item) -> {
                    if (item.update()) {
                        this.requireUpdate(slot);
                    }
                    return;
                });
                if (!this.hasViewers()) {
                    task.cancel();
                    this.updateItemsTask = null;
                }
            }).submit(this.plugin);
        }
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public int getRows() {
        return this.rows;
    }
    
    public Consumer<InteractInventoryEvent.Open> getOnOpen() {
        return this.onOpen;
    }
    
    public ChestMenu setOnOpen(final Consumer<InteractInventoryEvent.Open> onOpen) {
        this.onOpen = onOpen;
        return this;
    }
    
    public Consumer<InteractInventoryEvent.Close> getOnClose() {
        return this.onClose;
    }
    
    public ChestMenu setOnClose(final Consumer<InteractInventoryEvent.Close> onClose) {
        this.onClose = onClose;
        return this;
    }
    
    public Consumer<ClickInventoryEvent> getOnClick() {
        return this.onClick;
    }
    
    public ChestMenu setOnClick(final Consumer<ClickInventoryEvent> onClick) {
        this.onClick = onClick;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Primary> getOnPrimary() {
        return this.onPrimary;
    }
    
    public ChestMenu setOnPrimary(final Consumer<ClickInventoryEvent.Primary> onPrimary) {
        this.onPrimary = onPrimary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Middle> getOnMiddle() {
        return this.onMiddle;
    }
    
    public ChestMenu setOnMiddle(final Consumer<ClickInventoryEvent.Middle> onMiddle) {
        this.onMiddle = onMiddle;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Secondary> getOnSecondary() {
        return this.onSecondary;
    }
    
    public ChestMenu setOnSecondary(final Consumer<ClickInventoryEvent.Secondary> onSecondary) {
        this.onSecondary = onSecondary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Shift.Primary> getOnShiftPrimary() {
        return this.onShiftPrimary;
    }
    
    public ChestMenu setOnShiftPrimary(final Consumer<ClickInventoryEvent.Shift.Primary> onShiftPrimary) {
        this.onShiftPrimary = onShiftPrimary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Double> getOnDouble() {
        return this.onDouble;
    }
    
    public ChestMenu setOnDouble(final Consumer<ClickInventoryEvent.Double> onDouble) {
        this.onDouble = onDouble;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Drop.Single> getOnDrop() {
        return this.onDrop;
    }
    
    public ChestMenu setOnDrop(final Consumer<ClickInventoryEvent.Drop.Single> onDrop) {
        this.onDrop = onDrop;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Shift.Secondary> getOnShiftSecondary() {
        return this.onShiftSecondary;
    }
    
    public ChestMenu setOnShiftSecondary(final Consumer<ClickInventoryEvent.Shift.Secondary> onShiftSecondary) {
        this.onShiftSecondary = onShiftSecondary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Drop.Full> getOnDropAll() {
        return this.onDropAll;
    }
    
    public ChestMenu setOnDropAll(final Consumer<ClickInventoryEvent.Drop.Full> onDropAll) {
        this.onDropAll = onDropAll;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.NumberPress> getOnNumber() {
        return this.onNumber;
    }
    
    public ChestMenu setOnNumber(final Consumer<ClickInventoryEvent.NumberPress> onNumber) {
        this.onNumber = onNumber;
        return this;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    static {
        dummyItem = ClickableItem.create(ItemStack.empty());
    }
}
