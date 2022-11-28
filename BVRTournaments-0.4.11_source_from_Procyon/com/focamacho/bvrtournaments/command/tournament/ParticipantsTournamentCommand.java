// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tournament;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.entity.player.EntityPlayer;
import com.focamacho.bvrtournaments.util.TextUtils;
import com.focamacho.bvrtournaments.controller.TournamentController;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class ParticipantsTournamentCommand extends Command
{
    public ParticipantsTournamentCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final Optional<TournamentController.Tournament> optTournament = this.plugin.getTournamentController().getCurrentTournament();
        if (!optTournament.isPresent() || optTournament.get().isEnded()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentParticipantsNotAvailable, new Object[0]);
            return CommandResult.success();
        }
        TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.tournamentParticipants, String.join(", ", (CharSequence[])optTournament.get().getParticipants().stream().map((Function<? super Object, ?>)EntityPlayer::func_70005_c_).toArray(String[]::new)), optTournament.get().getParticipants().size());
        return CommandResult.success();
    }
}
