// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tournament;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import com.focamacho.bvrtournaments.config.Tier;
import com.focamacho.bvrtournaments.controller.TournamentController;
import java.util.Optional;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class CreateTournamentCommand extends Command
{
    public CreateTournamentCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final Optional<TournamentController.Tournament> optTournament = this.plugin.getTournamentController().getCurrentTournament();
        if (optTournament.isPresent()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentAlreadyRunning, new Object[0]);
            return CommandResult.success();
        }
        final Optional<String> optTier = (Optional<String>)args.getOne("tier");
        Tier tier = null;
        if (optTier.isPresent()) {
            tier = this.plugin.getTier(optTier.get());
            if (tier == null) {
                TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.invalidTier, new Object[0]);
                return CommandResult.success();
            }
        }
        this.plugin.getTournamentController().createTournament(tier);
        TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentCreate, new Object[0]);
        if (this.plugin.getConfig().general.broadcastTournament) {
            if (tier != null) {
                TextUtils.broadcastFormattedMessage(this.plugin.getLang().geral.tournamentTierBroadcast, tier.tierName);
            }
            else {
                TextUtils.broadcastFormattedMessage(this.plugin.getLang().geral.tournamentBroadcast, new Object[0]);
            }
        }
        return CommandResult.success();
    }
}
