// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealmenus.sponge.item;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import lombok.NonNull;
import org.spongepowered.api.item.inventory.ItemStack;
import java.util.List;

public class LoopableItem extends MenuItem
{
    private final List<ItemStack> items;
    private final int totalTicks;
    private int tickCount;
    
    private LoopableItem(@NonNull final List<ItemStack> items, final int ticks) {
        super(items.get(0));
        if (items == null) {
            throw new NullPointerException("items is marked non-null but is null");
        }
        this.items = items;
        this.totalTicks = ticks;
        this.tickCount = ticks;
    }
    
    public static LoopableItem create(@NonNull final List<ItemStack> items, final int ticks) {
        if (items == null) {
            throw new NullPointerException("items is marked non-null but is null");
        }
        return new LoopableItem(items, ticks);
    }
    
    @Override
    public boolean update() {
        --this.tickCount;
        if (this.tickCount <= 0) {
            this.tickCount = this.totalTicks;
            final int itemIndex = this.items.indexOf(this.getItem()) + 1;
            if (itemIndex >= this.items.size()) {
                this.setItem(this.items.get(0));
            }
            else {
                this.setItem(this.items.get(itemIndex));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public MenuItem copy() {
        return create((List<ItemStack>)this.items.stream().map((Function<? super Object, ?>)ItemStack::copy).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()), this.totalTicks).setOnPrimary(this.getOnPrimary()).setOnMiddle(this.getOnMiddle()).setOnSecondary(this.getOnSecondary()).setOnShiftPrimary(this.getOnShiftPrimary()).setOnDouble(this.getOnDouble()).setOnDrop(this.getOnDrop()).setOnNumber(this.getOnNumber()).setOnShiftSecondary(this.getOnShiftSecondary()).setOnDropAll(this.getOnDropAll());
    }
    
    public List<ItemStack> getItems() {
        return this.items;
    }
}
