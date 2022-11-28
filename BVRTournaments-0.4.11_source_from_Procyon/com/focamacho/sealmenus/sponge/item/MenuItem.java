// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealmenus.sponge.item;

import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import java.util.function.Consumer;
import lombok.NonNull;
import org.spongepowered.api.item.inventory.ItemStack;

public abstract class MenuItem
{
    @NonNull
    private ItemStack item;
    protected Consumer<ClickInventoryEvent.Primary> onPrimary;
    protected Consumer<ClickInventoryEvent.Middle> onMiddle;
    protected Consumer<ClickInventoryEvent.Secondary> onSecondary;
    protected Consumer<ClickInventoryEvent.Shift.Primary> onShiftPrimary;
    protected Consumer<ClickInventoryEvent.Double> onDouble;
    protected Consumer<ClickInventoryEvent.Drop.Single> onDrop;
    protected Consumer<ClickInventoryEvent.Shift.Secondary> onShiftSecondary;
    protected Consumer<ClickInventoryEvent.Drop.Full> onDropAll;
    protected Consumer<ClickInventoryEvent.NumberPress> onNumber;
    
    public boolean update() {
        return false;
    }
    
    public abstract MenuItem copy();
    
    MenuItem(@NonNull final ItemStack item) {
        this.onPrimary = (click -> {});
        this.onMiddle = (click -> {});
        this.onSecondary = (click -> {});
        this.onShiftPrimary = (click -> {});
        this.onDouble = (click -> {});
        this.onDrop = (click -> {});
        this.onShiftSecondary = (click -> {});
        this.onDropAll = (click -> {});
        this.onNumber = (click -> {});
        if (item == null) {
            throw new NullPointerException("item is marked non-null but is null");
        }
        this.item = item;
    }
    
    @NonNull
    public ItemStack getItem() {
        return this.item;
    }
    
    public MenuItem setItem(@NonNull final ItemStack item) {
        if (item == null) {
            throw new NullPointerException("item is marked non-null but is null");
        }
        this.item = item;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Primary> getOnPrimary() {
        return this.onPrimary;
    }
    
    public MenuItem setOnPrimary(final Consumer<ClickInventoryEvent.Primary> onPrimary) {
        this.onPrimary = onPrimary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Middle> getOnMiddle() {
        return this.onMiddle;
    }
    
    public MenuItem setOnMiddle(final Consumer<ClickInventoryEvent.Middle> onMiddle) {
        this.onMiddle = onMiddle;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Secondary> getOnSecondary() {
        return this.onSecondary;
    }
    
    public MenuItem setOnSecondary(final Consumer<ClickInventoryEvent.Secondary> onSecondary) {
        this.onSecondary = onSecondary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Shift.Primary> getOnShiftPrimary() {
        return this.onShiftPrimary;
    }
    
    public MenuItem setOnShiftPrimary(final Consumer<ClickInventoryEvent.Shift.Primary> onShiftPrimary) {
        this.onShiftPrimary = onShiftPrimary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Double> getOnDouble() {
        return this.onDouble;
    }
    
    public MenuItem setOnDouble(final Consumer<ClickInventoryEvent.Double> onDouble) {
        this.onDouble = onDouble;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Drop.Single> getOnDrop() {
        return this.onDrop;
    }
    
    public MenuItem setOnDrop(final Consumer<ClickInventoryEvent.Drop.Single> onDrop) {
        this.onDrop = onDrop;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Shift.Secondary> getOnShiftSecondary() {
        return this.onShiftSecondary;
    }
    
    public MenuItem setOnShiftSecondary(final Consumer<ClickInventoryEvent.Shift.Secondary> onShiftSecondary) {
        this.onShiftSecondary = onShiftSecondary;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.Drop.Full> getOnDropAll() {
        return this.onDropAll;
    }
    
    public MenuItem setOnDropAll(final Consumer<ClickInventoryEvent.Drop.Full> onDropAll) {
        this.onDropAll = onDropAll;
        return this;
    }
    
    public Consumer<ClickInventoryEvent.NumberPress> getOnNumber() {
        return this.onNumber;
    }
    
    public MenuItem setOnNumber(final Consumer<ClickInventoryEvent.NumberPress> onNumber) {
        this.onNumber = onNumber;
        return this;
    }
}
