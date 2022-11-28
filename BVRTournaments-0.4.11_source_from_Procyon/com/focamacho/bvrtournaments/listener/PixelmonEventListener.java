// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.listener;

import java.util.Iterator;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleEndCause;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.Arrays;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import com.pixelmonmod.pixelmon.battles.rules.teamselection.TeamSelectionList;
import java.util.function.Function;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.focamacho.bvrtournaments.battle.Battle;
import java.util.List;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.focamacho.bvrtournaments.BVRTournaments;

public class PixelmonEventListener
{
    private final BVRTournaments plugin;
    
    @SubscribeEvent
    public void onBattleStart(final BattleStartedEvent event) {
        if (event.bc.isPvP()) {
            final List<Battle> queryBattles = Battle.getBattles().stream().filter(battle -> battle.getBattle() == null && event.bc.participants.stream().allMatch(participant -> participant instanceof PlayerParticipant && (battle.team1.contains(participant.player) || battle.team2.contains(participant.player)))).collect((Collector<? super Object, ?, List<Battle>>)Collectors.toList());
            if (queryBattles.size() == 1) {
                if (this.plugin.getConfig().battles.maxTimeIL > 0) {
                    event.bc.rules.turnTime = this.plugin.getConfig().battles.maxTimeIL;
                }
                if (event.participant1.length == 1 && !event.bc.rules.teamPreview) {
                    event.setCanceled(true);
                    event.bc.rules.teamPreview = true;
                    TeamSelectionList.addTeamSelection(event.bc.rules, false, (PartyStorage[])event.bc.participants.stream().map(BattleParticipant::getStorage).toArray(PartyStorage[]::new));
                    event.bc.getPlayers().forEach(participant -> TextUtils.sendFormattedMessage((Player)participant.getEntity(), this.plugin.getLang().geral.pokemonSelection, new Object[0]));
                }
                else {
                    queryBattles.get(0).setBattle(event.bc);
                    if (this.plugin.getConfig().bets.betsEnabled) {
                        this.plugin.getBetController().startBet((List<EntityPlayerMP>)Arrays.stream(event.participant1).map(participant -> (EntityPlayerMP)participant.getEntity()).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()), (List<EntityPlayerMP>)Arrays.stream(event.participant2).map(participant -> (EntityPlayerMP)participant.getEntity()).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onBattleEnd(final BattleEndEvent event) {
        if (event.bc.isPvP()) {
            final Iterator<Battle> iterator = Battle.getBattles().iterator();
            while (iterator.hasNext()) {
                final Battle battle = iterator.next();
                if (battle.getBattle() == event.bc) {
                    if (event.cause == EnumBattleEndCause.FORCE) {
                        battle.onBattleForceEnd(event);
                    }
                    else {
                        final List<EntityPlayerMP> winners = (List<EntityPlayerMP>)event.bc.participants.stream().filter(participant -> event.results.get((Object)participant) == BattleResults.VICTORY).map(participant -> participant.player).collect(Collectors.toList());
                        final List<EntityPlayerMP> losers = (List<EntityPlayerMP>)event.bc.participants.stream().filter(participant -> event.results.get((Object)participant) != BattleResults.VICTORY).map(participant -> participant.player).collect(Collectors.toList());
                        battle.onBattleEnd(event, winners, losers);
                    }
                    iterator.remove();
                }
            }
        }
    }
    
    public PixelmonEventListener(final BVRTournaments plugin) {
        this.plugin = plugin;
    }
}
