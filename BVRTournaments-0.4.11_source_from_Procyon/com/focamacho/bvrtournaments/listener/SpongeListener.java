// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.listener;

import org.spongepowered.api.event.Listener;
import com.focamacho.bvrtournaments.task.CheckEloTask;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import com.focamacho.bvrtournaments.BVRTournaments;

public class SpongeListener
{
    private final BVRTournaments plugin;
    
    @Listener
    public void onPlayerJoin(final ClientConnectionEvent.Join event) {
        new CheckEloTask(event.getTargetEntity(), this.plugin).check();
    }
    
    public SpongeListener(final BVRTournaments plugin) {
        this.plugin = plugin;
    }
}
