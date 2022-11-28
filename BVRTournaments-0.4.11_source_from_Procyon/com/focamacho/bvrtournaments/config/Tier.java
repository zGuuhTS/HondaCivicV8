// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.config;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;
import com.pixelmonmod.pixelmon.Pixelmon;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.focamacho.sealconfig.relocated.blue.endless.jankson.api.annotation.Comment;

public class Tier
{
    @Comment("Name used in messages. The Tier ID is the file name.")
    public String tierName;
    @Comment("Aliases that can be used for checking the tier using the /tier command.")
    public String[] tierAliases;
    @Comment("Level cap. Set to 0 to disable.")
    public int levelCap;
    @Comment("Raise pokemons level to Level cap.")
    public boolean raiseToCap;
    @Comment("Clauses for the battle.")
    public String[] clauses;
    @Comment("Allowed pokemons in this tier.")
    public String[] allowedPokemons;
    
    public Tier() {
        this.tierName = "";
        this.tierAliases = new String[0];
        this.levelCap = 0;
        this.raiseToCap = true;
        this.clauses = new String[0];
        this.allowedPokemons = new String[0];
    }
    
    public boolean isAllowed(final Pokemon pokemon) {
        if (pokemon == null) {
            return true;
        }
        for (final String allowedPokemon : this.allowedPokemons) {
            final String[] split = allowedPokemon.split(":");
            if (pokemon.getSpecies().name().equalsIgnoreCase(split[0]) && (split.length == 1 || pokemon.getFormEnum().getFormSuffix().equalsIgnoreCase("-" + split[1]))) {
                return true;
            }
        }
        return false;
    }
    
    public List<Pokemon> checkPokemonsTier(final EntityPlayerMP player) {
        return Arrays.stream(Pixelmon.storageManager.getParty(player).getAll()).filter(pokemon -> !this.isAllowed(pokemon)).collect((Collector<? super Pokemon, ?, List<Pokemon>>)Collectors.toList());
    }
}
