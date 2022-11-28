// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tournament;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class TournamentCommand extends Command
{
    public TournamentCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentMessage, new Object[0]);
        return CommandResult.success();
    }
}
