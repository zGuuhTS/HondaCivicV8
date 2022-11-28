// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealmenus.sponge.item;

import lombok.NonNull;
import org.spongepowered.api.item.inventory.ItemStack;

public class ClickableItem extends MenuItem
{
    private ClickableItem(@NonNull final ItemStack item) {
        super(item);
        if (item == null) {
            throw new NullPointerException("item is marked non-null but is null");
        }
    }
    
    public static ClickableItem create(@NonNull final ItemStack item) {
        if (item == null) {
            throw new NullPointerException("item is marked non-null but is null");
        }
        return new ClickableItem(item);
    }
    
    @Override
    public MenuItem copy() {
        return create(this.getItem().copy()).setOnPrimary(this.getOnPrimary()).setOnMiddle(this.getOnMiddle()).setOnSecondary(this.getOnSecondary()).setOnShiftPrimary(this.getOnShiftPrimary()).setOnDouble(this.getOnDouble()).setOnDrop(this.getOnDrop()).setOnNumber(this.getOnNumber()).setOnShiftSecondary(this.getOnShiftSecondary()).setOnDropAll(this.getOnDropAll());
    }
}
