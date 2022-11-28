// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tournament;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import com.focamacho.bvrtournaments.util.MoneyUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.controller.TournamentController;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class JoinTournamentCommand extends Command
{
    public JoinTournamentCommand(final BVRTournaments plugin) {
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
        if (!optTournament.isPresent() || optTournament.get().isEnded()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentNotAvailable, new Object[0]);
            return CommandResult.success();
        }
        if (optTournament.get().contains((EntityPlayerMP)src)) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentAlreadyIn, new Object[0]);
            return CommandResult.success();
        }
        if (optTournament.get().isStarted()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentStarted, new Object[0]);
            return CommandResult.success();
        }
        if (optTournament.get().getParticipants().size() >= this.plugin.getConfig().general.maxPlayersTournament) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentMaxPlayer, new Object[0]);
            return CommandResult.success();
        }
        if (this.plugin.getConfig().general.priceToJoinTournament > 0.0 && MoneyUtils.getMoney(((Player)src).getUniqueId()) < this.plugin.getConfig().general.priceToJoinTournament) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.noMoney, this.plugin.getConfig().general.priceToJoinTournament);
            return CommandResult.success();
        }
        if (optTournament.get().addParticipant((EntityPlayerMP)src)) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentJoin, new Object[0]);
        }
        return CommandResult.success();
    }
}
