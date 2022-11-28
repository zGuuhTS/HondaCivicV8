// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tournament;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import com.focamacho.bvrtournaments.util.TextUtils;
import com.focamacho.bvrtournaments.controller.TournamentController;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class FinishTournamentCommand extends Command
{
    public FinishTournamentCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final Optional<TournamentController.Tournament> optTournament = this.plugin.getTournamentController().getCurrentTournament();
        if (!optTournament.isPresent() || optTournament.get().isEnded() || !optTournament.get().isStarted()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentFinishNotAvailable, new Object[0]);
            return CommandResult.success();
        }
        optTournament.get().finish();
        return CommandResult.success();
    }
}
