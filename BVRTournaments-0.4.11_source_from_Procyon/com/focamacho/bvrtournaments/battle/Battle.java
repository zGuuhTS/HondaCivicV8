// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.battle;

import net.minecraft.entity.Entity;
import com.focamacho.bvrtournaments.controller.BetController;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.focamacho.bvrtournaments.command.battles.ReadyCommand;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleEndCause;
import org.spongepowered.api.command.CommandSource;
import java.text.MessageFormat;
import org.spongepowered.api.Sponge;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.rules.clauses.BattleClause;
import java.util.concurrent.TimeUnit;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.rules.clauses.BattleClauseRegistry;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import com.focamacho.bvrtournaments.controller.DatabaseController;
import org.spongepowered.api.scheduler.Task;
import com.google.common.collect.Lists;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import java.util.Collections;
import com.google.common.collect.Maps;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import com.focamacho.bvrtournaments.config.Tier;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.Location;
import java.util.Map;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.BVRTournaments;
import java.util.List;

public class Battle
{
    private static final List<Battle> battles;
    protected final BVRTournaments plugin;
    public final List<EntityPlayerMP> team1;
    public final List<EntityPlayerMP> team2;
    public final int arena;
    protected BattleControllerBase battle;
    private final Map<EntityPlayerMP, Location<World>> cacheLocations;
    private final Tier tier;
    private final EnumBattleType type;
    private final int maxPokes;
    private Map<EntityPlayerMP, Boolean> readyPlayers;
    
    private Battle(final List<EntityPlayerMP> team1, final List<EntityPlayerMP> team2, final int arena, final Tier tier, final EnumBattleType type, final int maxPokes, final BVRTournaments plugin) {
        this.battle = null;
        this.cacheLocations = (Map<EntityPlayerMP, Location<World>>)Maps.newHashMap();
        this.readyPlayers = (Map<EntityPlayerMP, Boolean>)Maps.newHashMap();
        this.plugin = plugin;
        this.team1 = Collections.unmodifiableList((List<? extends EntityPlayerMP>)team1);
        this.team2 = Collections.unmodifiableList((List<? extends EntityPlayerMP>)team2);
        this.arena = arena;
        this.tier = tier;
        this.type = type;
        this.maxPokes = maxPokes;
        final Location<World> location;
        team1.forEach(player -> location = this.cacheLocations.put(player, (Location<World>)((Player)player).getLocation()));
        final Location<World> location2;
        team2.forEach(player -> location2 = this.cacheLocations.put(player, (Location<World>)((Player)player).getLocation()));
        if (team1.size() > 1) {
            team1.forEach(player -> TextUtils.sendFormattedMessage(player, plugin.getLang().geral.noTeamSelect, new Object[0]));
            team2.forEach(player -> TextUtils.sendFormattedMessage(player, plugin.getLang().geral.noTeamSelect, new Object[0]));
        }
    }
    
    public static Battle create(final EntityPlayerMP player1, final EntityPlayerMP player2, final int arena, final Tier tier, final EnumBattleType type, final int maxPokes, final BVRTournaments plugin) {
        return new Battle(Collections.singletonList(player1), Collections.singletonList(player2), arena, tier, type, maxPokes, plugin);
    }
    
    public static Battle create(final EntityPlayerMP player1, final EntityPlayerMP player2, final EntityPlayerMP player3, final EntityPlayerMP player4, final int arena, final Tier tier, final EnumBattleType type, final int maxPokes, final BVRTournaments plugin) {
        return new Battle(Lists.newArrayList((Object[])new EntityPlayerMP[] { player1, player2 }), Lists.newArrayList((Object[])new EntityPlayerMP[] { player3, player4 }), arena, tier, type, maxPokes, plugin);
    }
    
    public boolean contains(final EntityPlayerMP player) {
        return this.team1.contains(player) || this.team2.contains(player);
    }
    
    public boolean isEveryoneReady() {
        return this.readyPlayers.size() == this.team1.size() + this.team2.size();
    }
    
    private void teleportPlayer(final EntityPlayerMP player, final Location<World> world) {
        Task.builder().execute(() -> ((Player)player).setLocation((Location)world)).submit((Object)this.plugin);
    }
    
    public void teleportParticipants() {
        final Optional<DatabaseController.Arena> optArena = (this.team1.size() > 1) ? this.plugin.getDatabaseController().getDuoArenaById(this.arena) : this.plugin.getDatabaseController().getSoloArenaById(this.arena);
        if (optArena.isPresent()) {
            final DatabaseController.Arena arena = optArena.get();
            int currentIndex = 0;
            for (final EntityPlayerMP player : this.team1) {
                this.teleportPlayer(player, (Location<World>)arena.getLocations().get(currentIndex).add(0.5, 0.0, 0.5));
                ++currentIndex;
            }
            for (final EntityPlayerMP player : this.team2) {
                this.teleportPlayer(player, (Location<World>)arena.getLocations().get(currentIndex).add(0.5, 0.0, 0.5));
                ++currentIndex;
            }
        }
    }
    
    public void teleportParticipantsBack() {
        this.cacheLocations.forEach((player, location) -> player.setLocation(location));
    }
    
    public void startBattle() {
        if (this.battle != null) {
            throw new IllegalStateException("This battle was already started.");
        }
        Battle.battles.add(this);
        this.teleportParticipants();
        final List<EntityPlayerMP> allPlayers = new ArrayList<EntityPlayerMP>(this.team1);
        allPlayers.addAll(this.team2);
        for (final EntityPlayerMP player2 : allPlayers) {
            if (!getFirstAvailablePokemon(player2).isPresent()) {
                this.cancelBattle();
                return;
            }
        }
        final BattleRules rules = new BattleRules();
        rules.battleType = this.type;
        rules.numPokemon = this.maxPokes;
        if (this.tier != null) {
            if (this.tier.levelCap > 0) {
                rules.levelCap = this.tier.levelCap;
                rules.raiseToCap = this.tier.raiseToCap;
            }
            if (this.tier.clauses.length > 0) {
                final List<BattleClause> clauses = (List<BattleClause>)Lists.newArrayList();
                for (final String clause : this.tier.clauses) {
                    clauses.add(BattleClauseRegistry.getClauseRegistry().getClause(clause));
                }
                rules.setNewClauses((List)clauses);
            }
        }
        if (this.team1.size() > 1) {
            final PlayerParticipant playerParticipant;
            final PlayerParticipant playerParticipant2;
            BattleRegistry.startBattle((BattleParticipant[])this.team1.stream().map(player -> {
                new PlayerParticipant((EntityPlayerMP)player, new EntityPixelmon[] { getFirstAvailablePokemon((EntityPlayerMP)player).get().getOrSpawnPixelmon(player) });
                return playerParticipant;
            }).toArray(PlayerParticipant[]::new), (BattleParticipant[])this.team2.stream().map(player -> {
                new PlayerParticipant((EntityPlayerMP)player, new EntityPixelmon[] { getFirstAvailablePokemon((EntityPlayerMP)player).get().getOrSpawnPixelmon(player) });
                return playerParticipant2;
            }).toArray(PlayerParticipant[]::new), rules);
        }
        else {
            BattleRegistry.startBattle((BattleParticipant[])new PlayerParticipant[] { new PlayerParticipant((EntityPlayerMP)this.team1.get(0), new EntityPixelmon[0]) }, (BattleParticipant[])new PlayerParticipant[] { new PlayerParticipant((EntityPlayerMP)this.team2.get(0), new EntityPixelmon[0]) }, rules);
        }
        Task.builder().execute(() -> {
            if (this.battle == null) {
                synchronized (Battle.battles) {
                    Battle.battles.remove(this);
                }
            }
        }).delay(60L, TimeUnit.SECONDS).submit((Object)this.plugin);
    }
    
    public void onBattleEnd(final BattleEndEvent event, final List<EntityPlayerMP> winners, final List<EntityPlayerMP> losers) {
        if (this.teleportBackOnEnd()) {
            this.teleportParticipantsBack();
        }
        if (winners.size() == 0) {
            return;
        }
        if (winners.size() == 2) {
            for (final String cmd : this.plugin.getConfig().battles.endDuoCommands) {
                Sponge.getCommandManager().process((CommandSource)Sponge.getServer().getConsole(), MessageFormat.format(cmd, winners.get(0).func_70005_c_(), winners.get(1).func_70005_c_(), losers.get(0).func_70005_c_(), losers.get(1).func_70005_c_()));
            }
        }
        else {
            for (final String cmd : this.plugin.getConfig().battles.endCommands) {
                Sponge.getCommandManager().process((CommandSource)Sponge.getServer().getConsole(), MessageFormat.format(cmd, winners.get(0).func_70005_c_(), losers.get(0).func_70005_c_()));
            }
        }
        if (this.plugin.getConfig().battles.broadcastWin) {
            if (winners.size() == 2) {
                TextUtils.broadcastFormattedMessage(this.plugin.getLang().geral.broadcastDuoWinMessage, winners.get(0).func_70005_c_(), winners.get(1).func_70005_c_());
            }
            else {
                TextUtils.broadcastFormattedMessage(this.plugin.getLang().geral.broadcastWinMessage, winners.get(0).func_70005_c_());
            }
        }
        this.plugin.getBetController().getBet(winners.get(0)).ifPresent(bet -> bet.end(winners, losers));
        winners.forEach(winner -> TextUtils.sendFormattedMessage(winner, this.plugin.getLang().geral.winMessage, new Object[0]));
        losers.forEach(loser -> TextUtils.sendFormattedMessage(loser, this.plugin.getLang().geral.loseMessage, new Object[0]));
    }
    
    public void onBattleForceEnd(final BattleEndEvent event) {
        if (this.teleportBackOnEnd()) {
            this.teleportParticipantsBack();
        }
        this.team1.forEach(player -> TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.abnormalEndMessage, new Object[0]));
        this.team2.forEach(player -> TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.abnormalEndMessage, new Object[0]));
        this.plugin.getBetController().cancelBet(this.team1.get(0));
    }
    
    public void cancelBattle() {
        if (this.battle != null && !this.battle.battleEnded) {
            this.battle.endBattle(EnumBattleEndCause.FORCE);
        }
        if (this.teleportBackOnEnd()) {
            this.teleportParticipantsBack();
        }
        Battle.battles.remove(this);
    }
    
    protected boolean teleportBackOnEnd() {
        return this.plugin.getConfig().battles.teleportBackIl;
    }
    
    public boolean isFinished() {
        return this.battle.battleEnded;
    }
    
    public static Integer getAvailableArena(final boolean solo, final BVRTournaments plugin) {
        final Collection<DatabaseController.Arena> toSort = solo ? plugin.getDatabaseController().getSoloArenas() : plugin.getDatabaseController().getDuoArenas();
        final List<DatabaseController.Arena> arenas = toSort.stream().sorted(Comparator.comparingInt(DatabaseController.Arena::getId)).collect((Collector<? super DatabaseController.Arena, ?, List<DatabaseController.Arena>>)Collectors.toList());
        for (final DatabaseController.Arena arena : arenas) {
            if (!ReadyCommand.checkArena(arena.getId()) && getBattles().stream().noneMatch(battle -> battle.arena == arena.getId())) {
                return arena.getId();
            }
        }
        return null;
    }
    
    public static Optional<Pokemon> getFirstAvailablePokemon(final EntityPlayerMP player) {
        return (Optional<Pokemon>)Pixelmon.storageManager.getParty(player).getTeam().stream().filter(pokemon -> pokemon != null && pokemon.getHealth() > 0).findFirst();
    }
    
    public static List<Battle> getBattles() {
        return Battle.battles;
    }
    
    public void setBattle(final BattleControllerBase battle) {
        this.battle = battle;
    }
    
    public BattleControllerBase getBattle() {
        return this.battle;
    }
    
    public Tier getTier() {
        return this.tier;
    }
    
    public Map<EntityPlayerMP, Boolean> getReadyPlayers() {
        return this.readyPlayers;
    }
    
    public void setReadyPlayers(final Map<EntityPlayerMP, Boolean> readyPlayers) {
        this.readyPlayers = readyPlayers;
    }
    
    static {
        battles = Collections.synchronizedList((List<Battle>)Lists.newArrayList());
    }
}
