// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.controller;

import com.focamacho.bvrtournaments.menu.TopPointsMenu;
import org.spongepowered.api.entity.living.player.Player;
import com.google.common.collect.Maps;
import com.focamacho.bvrtournaments.menu.TierMenu;
import com.focamacho.bvrtournaments.config.Tier;
import java.util.Map;
import com.focamacho.bvrtournaments.BVRTournaments;

public class MenuController
{
    private final BVRTournaments plugin;
    private final Map<Tier, TierMenu> tiers;
    
    public MenuController(final BVRTournaments plugin) {
        this.tiers = (Map<Tier, TierMenu>)Maps.newHashMap();
        this.plugin = plugin;
        final TierMenu tierMenu;
        plugin.getTiers().values().forEach(tier -> tierMenu = this.tiers.put(tier, new TierMenu(tier, plugin)));
    }
    
    public TierMenu getTierMenu(final Tier tier) {
        return this.tiers.get(tier);
    }
    
    public TopPointsMenu getTopPointsMenu(final Player player) {
        return new TopPointsMenu(player, this.plugin);
    }
}
