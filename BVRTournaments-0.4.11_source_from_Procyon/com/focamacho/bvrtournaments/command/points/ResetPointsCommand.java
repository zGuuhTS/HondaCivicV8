// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.points;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import com.google.common.collect.Maps;
import com.focamacho.bvrtournaments.BVRTournaments;
import org.spongepowered.api.command.CommandSource;
import java.util.Map;
import com.focamacho.bvrtournaments.command.Command;

public class ResetPointsCommand extends Command
{
    private final Map<CommandSource, Long> waitingConfirmation;
    
    public ResetPointsCommand(final BVRTournaments plugin) {
        super(plugin);
        this.waitingConfirmation = (Map<CommandSource, Long>)Maps.newHashMap();
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        if (this.waitingConfirmation.containsKey(src) && System.currentTimeMillis() - this.waitingConfirmation.get(src) <= 0L) {
            this.waitingConfirmation.remove(src);
            this.plugin.getDatabaseController().reset();
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.resetConfirmed, new Object[0]));
        }
        else {
            this.waitingConfirmation.put(src, System.currentTimeMillis() + 10000L);
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.resetWarning, new Object[0]));
        }
        return CommandResult.success();
    }
}
