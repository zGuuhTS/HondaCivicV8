// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.battles;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Iterator;
import com.focamacho.bvrtournaments.config.Tier;
import java.util.Optional;
import java.util.Set;
import org.spongepowered.api.Sponge;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import java.util.function.Predicate;
import java.util.Objects;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.battle.Battle;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import com.google.common.collect.Sets;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class ILDuoCommand extends Command
{
    public ILDuoCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final Set<Player> players = (Set<Player>)Sets.newLinkedHashSet();
        for (int i = 1; i <= 4; ++i) {
            players.add(args.getOne("player" + i).get());
        }
        final Optional<String> optTier = (Optional<String>)args.getOne("tier");
        final int maxPokes = args.getOne("maxPokes").orElse(6);
        if (players.size() != 4) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.samePlayer, new Object[0]));
            return CommandResult.success();
        }
        final Integer arena = Battle.getAvailableArena(false, this.plugin);
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
                for (final Player player2 : players) {
                    if (tier.checkPokemonsTier((EntityPlayerMP)player2).size() > 0) {
                        TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.invalidTierForBattle, player2.getName(), tier.tierName);
                        return CommandResult.success();
                    }
                }
            }
        }
        for (final Player player3 : players) {
            if (!Battle.getFirstAvailablePokemon((EntityPlayerMP)player3).isPresent()) {
                TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.missingPoke, player3.getName());
                return CommandResult.success();
            }
            if (Pixelmon.storageManager.getParty((EntityPlayerMP)player3).getTeam().stream().filter(Objects::nonNull).count() != maxPokes) {
                TextUtils.sendFormattedChatMessage(src, this.plugin.getLang().commands.maxPokes, player3.getName());
                return CommandResult.success();
            }
        }
        final EntityPlayerMP[] participants = players.stream().map(player -> player).toArray(EntityPlayerMP[]::new);
        final Battle battle = Battle.create(participants[0], participants[1], participants[2], participants[3], arena, tier, EnumBattleType.Double, maxPokes, this.plugin);
        battle.teleportParticipants();
        ReadyCommand.waitReady(battle, this.plugin);
        if (this.plugin.getConfig().battles.broadcastFight) {
            final Object o;
            Sponge.getServer().getOnlinePlayers().forEach(player -> TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.broadcastDuoILMessage, o[0].func_70005_c_(), o[1].func_70005_c_(), o[2].func_70005_c_(), o[3].func_70005_c_()));
        }
        TextUtils.sendFormattedMessage((Player)participants[0], this.plugin.getLang().geral.battleDuoMessage, participants[2].func_70005_c_(), participants[3].func_70005_c_());
        TextUtils.sendFormattedMessage((Player)participants[1], this.plugin.getLang().geral.battleDuoMessage, participants[2].func_70005_c_(), participants[3].func_70005_c_());
        TextUtils.sendFormattedMessage((Player)participants[2], this.plugin.getLang().geral.battleDuoMessage, participants[0].func_70005_c_(), participants[1].func_70005_c_());
        TextUtils.sendFormattedMessage((Player)participants[3], this.plugin.getLang().geral.battleDuoMessage, participants[0].func_70005_c_(), participants[1].func_70005_c_());
        return CommandResult.success();
    }
}
