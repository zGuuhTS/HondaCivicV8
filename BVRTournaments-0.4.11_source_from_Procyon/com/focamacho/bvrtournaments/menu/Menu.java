// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.menu;

import org.spongepowered.api.entity.living.player.Player;
import com.focamacho.sealmenus.sponge.ChestMenu;
import com.focamacho.bvrtournaments.BVRTournaments;

public class Menu
{
    protected final BVRTournaments plugin;
    protected final ChestMenu menu;
    
    protected Menu(final ChestMenu menu, final BVRTournaments plugin) {
        this.menu = menu;
        this.plugin = plugin;
    }
    
    public void open(final Player player) {
        this.menu.open(player);
    }
}
