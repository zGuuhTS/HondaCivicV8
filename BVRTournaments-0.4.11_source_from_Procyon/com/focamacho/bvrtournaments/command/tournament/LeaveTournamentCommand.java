// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tournament;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.controller.TournamentController;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class LeaveTournamentCommand extends Command
{
    public LeaveTournamentCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notPlayer, new Object[0]));
            return CommandResult.success();
        }
        final Optional<TournamentController.Tournament> optTournament = this.plugin.getTournamentController().getCurrentTournament();
        if (!optTournament.isPresent() || !optTournament.get().contains((EntityPlayerMP)src)) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentNotIn, new Object[0]);
            return CommandResult.success();
        }
        optTournament.get().removeParticipant((EntityPlayerMP)src);
        TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentLeave, new Object[0]);
        return CommandResult.success();
    }
}
