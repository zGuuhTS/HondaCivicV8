// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.battles;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import com.focamacho.bvrtournaments.config.Tier;
import java.util.Optional;
import org.spongepowered.api.Sponge;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.battle.Battle;
import com.focamacho.bvrtournaments.util.TextUtils;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class ILCommand extends Command
{
    public ILCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final Player player2 = args.getOne("player1").get();
        final Player player3 = args.getOne("player2").get();
        final Optional<String> optTier = (Optional<String>)args.getOne("tier");
        final EnumBattleType type = args.getOne("type").orElse(EnumBattleType.Single);
        final int maxPokes = args.getOne("maxPokes").orElse(6);
        if (player2 == player3) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.samePlayer, new Object[0]));
            return CommandResult.success();
        }
        final Integer arena = Battle.getAvailableArena(true, this.plugin);
        if (arena == null) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.missingArena, new Object[0]));
            return CommandResult.success();
        }
        Tier tier = null;
        if (optTier.isPresent()) {
            final String tierName = optTier.get();
            if (!tierName.equalsIgnoreCase("null")) {
                tier = this.plugin.getTier(tierName);
                if (tier == null) {
                    TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.invalidTier, tierName);
                    return CommandResult.success();
                }
                if (tier.checkPokemonsTier((EntityPlayerMP)player2).size() > 0) {
                    TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.invalidTierForBattle, player2.getName(), tier.tierName);
                    return CommandResult.success();
                }
                if (tier.checkPokemonsTier((EntityPlayerMP)player3).size() > 0) {
                    TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.invalidTierForBattle, player3.getName(), tier.tierName);
                    return CommandResult.success();
                }
            }
        }
        if (!Battle.getFirstAvailablePokemon((EntityPlayerMP)player2).isPresent()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.missingPoke, player2.getName());
            return CommandResult.success();
        }
        if (!Battle.getFirstAvailablePokemon((EntityPlayerMP)player3).isPresent()) {
            TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.missingPoke, player3.getName());
            return CommandResult.success();
        }
        final Battle battle = Battle.create((EntityPlayerMP)player2, (EntityPlayerMP)player3, arena, tier, type, maxPokes, this.plugin);
        battle.teleportParticipants();
        ReadyCommand.waitReady(battle, this.plugin);
        if (this.plugin.getConfig().battles.broadcastFight) {
            Sponge.getServer().getOnlinePlayers().forEach(player -> TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.broadcastILMessage, player2.getName(), player3.getName()));
        }
        TextUtils.sendFormattedMessage(player2, this.plugin.getLang().geral.battleMessage, player3.getName());
        TextUtils.sendFormattedMessage(player3, this.plugin.getLang().geral.battleMessage, player2.getName());
        return CommandResult.success();
    }
}
