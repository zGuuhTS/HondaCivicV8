// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.battles;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import org.spongepowered.api.command.CommandException;
import java.util.Optional;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.focamacho.bvrtournaments.battle.Battle;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class TeamPreviewCommand extends Command
{
    public TeamPreviewCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
        if (!(src instanceof Player)) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.notPlayer, new Object[0]);
            return CommandResult.success();
        }
        final EntityPlayerMP player = (EntityPlayerMP)src;
        Optional<Battle> optBattle = ReadyCommand.isWaiting(player);
        if (!optBattle.isPresent()) {
            optBattle = Battle.getBattles().stream().filter(battle -> battle.contains(player)).findFirst();
            if (!optBattle.isPresent()) {
                TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.teamPreviewNotInBattle, new Object[0]);
                return CommandResult.success();
            }
        }
        final EntityPlayerMP previewPlayer = args.getOne("player").get();
        final Battle battle2 = optBattle.get();
        if (!battle2.team1.contains(previewPlayer) && !battle2.team2.contains(previewPlayer)) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.teamPreviewNotInBattleOther, new Object[0]);
            return CommandResult.success();
        }
        final String pokemons = String.join(", ", (CharSequence[])Pixelmon.storageManager.getParty(previewPlayer).getTeam().stream().map(pokemon -> {
            if (pokemon != null) {
                return pokemon.getLocalizedName();
            }
            else {
                return "";
            }
        }).toArray(String[]::new));
        TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.teamPreview, previewPlayer.func_70005_c_(), pokemons);
        return CommandResult.success();
    }
}
