// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command;

import com.focamacho.bvrtournaments.BVRTournaments;
import org.spongepowered.api.command.spec.CommandExecutor;

public abstract class Command implements CommandExecutor
{
    protected final BVRTournaments plugin;
    
    public Command(final BVRTournaments plugin) {
        this.plugin = plugin;
    }
}
