// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.sealmenus.sponge;

public class SealMenus
{
    public static ChestMenu createChestMenu(final String title, final int rows, final Object plugin) {
        return new ChestMenu(title, rows, plugin);
    }
    
    public static PageableChestMenu createPageableChestMenu(final String title, final int rows, final int[] itemSlots, final Object plugin) {
        return new PageableChestMenu(title, rows, itemSlots, plugin);
    }
}
