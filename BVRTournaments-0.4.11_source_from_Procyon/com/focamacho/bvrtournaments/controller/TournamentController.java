// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.controller;

import com.google.common.collect.Lists;
import java.util.Collections;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.focamacho.bvrtournaments.util.MoneyUtils;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.List;
import java.util.Optional;
import com.focamacho.bvrtournaments.config.Tier;
import com.focamacho.bvrtournaments.BVRTournaments;

public class TournamentController
{
    private final BVRTournaments plugin;
    private Tournament currentTournament;
    
    public Tournament createTournament(final Tier tier) {
        if (this.currentTournament != null && !this.currentTournament.isEnded()) {
            this.currentTournament.cancel();
        }
        return this.currentTournament = new Tournament(this.plugin, tier);
    }
    
    public Optional<Tournament> getCurrentTournament() {
        if (this.currentTournament == null || this.currentTournament.isEnded()) {
            return Optional.empty();
        }
        return Optional.of(this.currentTournament);
    }
    
    public TournamentController(final BVRTournaments plugin) {
        this.currentTournament = null;
        this.plugin = plugin;
    }
    
    public static class Tournament
    {
        private final BVRTournaments plugin;
        private final Tier tier;
        private boolean autoDetect;
        private final List<EntityPlayerMP> participants;
        private boolean started;
        private boolean ended;
        private String URL;
        
        public boolean addParticipant(final EntityPlayerMP player) {
            if (this.contains(player)) {
                return false;
            }
            if (this.tier != null) {
                final List<Pokemon> invalidPokemons = this.tier.checkPokemonsTier(player);
                if (invalidPokemons.size() > 0) {
                    final String pokemonNames = String.join(", ", (CharSequence[])invalidPokemons.stream().map(pokemon -> pokemon.getSpecies().getLocalizedName()).toArray(String[]::new));
                    TextUtils.sendFormattedChatMessage((CommandSource)player, this.plugin.getLang().commands.invalidTierForTournament, this.tier.tierName, pokemonNames);
                    return false;
                }
            }
            if (this.plugin.getConfig().general.priceToJoinTournament > 0.0) {
                if (MoneyUtils.getMoney(player.func_110124_au()) < this.plugin.getConfig().general.priceToJoinTournament) {
                    return false;
                }
                MoneyUtils.removeMoney(player.func_110124_au(), this.plugin.getConfig().general.priceToJoinTournament);
                TextUtils.sendFormattedChatMessage((CommandSource)player, this.plugin.getLang().commands.removedMoney, this.plugin.getConfig().general.priceToJoinTournament);
            }
            this.sendMessage(this.plugin.getLang().commands.playerJoin, player.func_70005_c_());
            this.participants.add(player);
            return true;
        }
        
        public boolean contains(final EntityPlayerMP player) {
            return this.participants.stream().anyMatch(entityPlayer -> entityPlayer.func_110124_au().equals(player.func_110124_au()));
        }
        
        public boolean contains(final String player) {
            return this.participants.stream().anyMatch(entityPlayer -> entityPlayer.func_70005_c_().equals(player));
        }
        
        public List<EntityPlayerMP> getParticipants() {
            return Collections.unmodifiableList((List<? extends EntityPlayerMP>)this.participants);
        }
        
        public void removeParticipant(final String player) {
            final Optional<EntityPlayerMP> participant = this.participants.stream().filter(entityPlayer -> entityPlayer.func_70005_c_().equals(player)).findFirst();
            if (participant.isPresent()) {
                final boolean remove = this.participants.remove(participant.get());
                if (remove) {
                    if (!this.isStarted() && this.plugin.getConfig().general.priceToJoinTournament > 0.0) {
                        MoneyUtils.addMoney(participant.get().func_110124_au(), this.plugin.getConfig().general.priceToJoinTournament);
                        TextUtils.sendFormattedChatMessage((CommandSource)participant.get(), this.plugin.getLang().commands.addMoney, this.plugin.getConfig().general.priceToJoinTournament);
                    }
                    this.sendMessage(this.plugin.getLang().commands.playerLeave, player);
                }
            }
        }
        
        public void removeParticipant(final EntityPlayerMP player) {
            this.removeParticipant(player.func_70005_c_());
        }
        
        public void start() {
            if (this.started || this.ended) {
                return;
            }
            this.started = true;
        }
        
        public void cancel() {
            this.ended = true;
            this.sendMessage(this.plugin.getLang().commands.tournamentCancel, new Object[0]);
        }
        
        public void finish() {
            this.ended = true;
            this.sendMessage(this.plugin.getLang().commands.tournamentFinish, new Object[0]);
        }
        
        public void sendMessage(final String message, final Object... args) {
            this.participants.forEach(player -> TextUtils.sendFormattedChatMessage((CommandSource)player, message, args));
        }
        
        private Tournament(final BVRTournaments plugin, final Tier tier) {
            this.autoDetect = true;
            this.participants = (List<EntityPlayerMP>)Lists.newArrayList();
            this.started = false;
            this.ended = false;
            this.URL = null;
            this.plugin = plugin;
            this.tier = tier;
        }
        
        public Tier getTier() {
            return this.tier;
        }
        
        public boolean isAutoDetect() {
            return this.autoDetect;
        }
        
        public void setAutoDetect(final boolean autoDetect) {
            this.autoDetect = autoDetect;
        }
        
        public boolean isStarted() {
            return this.started;
        }
        
        public boolean isEnded() {
            return this.ended;
        }
        
        public String getURL() {
            return this.URL;
        }
        
        public void setURL(final String URL) {
            this.URL = URL;
        }
    }
}
