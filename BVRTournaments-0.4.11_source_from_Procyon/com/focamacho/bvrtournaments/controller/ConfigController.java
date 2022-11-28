// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.controller;

import net.luckperms.api.model.group.Group;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.io.File;
import com.focamacho.bvrtournaments.util.PokemonUtils;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import java.util.Iterator;
import java.util.Arrays;
import com.focamacho.bvrtournaments.util.Reference;
import com.google.common.collect.Maps;
import java.util.Collections;
import org.h2.value.CaseInsensitiveMap;
import com.focamacho.bvrtournaments.config.Tier;
import java.util.Map;
import com.focamacho.bvrtournaments.config.Challonge;
import com.focamacho.bvrtournaments.config.Lang;
import com.focamacho.bvrtournaments.config.Menu;
import com.focamacho.bvrtournaments.config.Config;
import com.focamacho.sealconfig.SealConfig;
import com.focamacho.bvrtournaments.BVRTournaments;

public class ConfigController
{
    private final BVRTournaments plugin;
    private final SealConfig sealConfig;
    private final Config config;
    private final Menu menuConfig;
    private final Lang lang;
    private final Challonge challongeConfig;
    private final Map<String, Tier> tiers;
    private final Map<String, Tier> unmodifiableTiers;
    private final Map<String, String> groupNames;
    
    public ConfigController(final BVRTournaments plugin) {
        this.sealConfig = new SealConfig();
        this.tiers = (Map<String, Tier>)new CaseInsensitiveMap();
        this.unmodifiableTiers = Collections.unmodifiableMap((Map<? extends String, ? extends Tier>)this.tiers);
        this.groupNames = (Map<String, String>)Maps.newHashMap();
        this.plugin = plugin;
        this.config = this.sealConfig.getConfig(Reference.CONFIG_FILE, Config.class);
        this.menuConfig = this.sealConfig.getConfig(Reference.MENU_FILE, Menu.class);
        this.lang = this.sealConfig.getConfig(Reference.LANG_FILE, Lang.class);
        this.challongeConfig = this.sealConfig.getConfig(Reference.CHALLONGE_FILE, Challonge.class);
        this.loadTiers();
    }
    
    public Tier getTier(final String tier) {
        for (final Map.Entry<String, Tier> entry : this.tiers.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(tier) || Arrays.stream(entry.getValue().tierAliases).anyMatch(alias -> alias.equalsIgnoreCase(tier))) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public Map<String, Tier> getTiers() {
        return this.unmodifiableTiers;
    }
    
    public String getEloName(final String player) {
        return this.getEloNameForPoints(this.plugin.getDatabaseController().getCachedUser(player).getPoints());
    }
    
    public String getEloForPoints(final Integer points) {
        if (points == null) {
            return null;
        }
        String elo = null;
        Map<String, Integer> elos = (Map<String, Integer>)Maps.newLinkedHashMap();
        for (final Config.General.Elo cElo : this.plugin.getConfig().general.elos) {
            elos.put(cElo.group, cElo.points);
        }
        elos = this.sortByValue(elos);
        for (final Map.Entry<String, Integer> entry : elos.entrySet()) {
            if (entry.getValue() > points) {
                break;
            }
            elo = entry.getKey();
        }
        if (elo != null && !this.groupNames.containsKey(elo)) {
            this.updateGroupName(elo);
        }
        return elo;
    }
    
    public String getEloNameForPoints(final Integer points) {
        final String elo = this.getEloForPoints(points);
        if (elo == null) {
            return this.plugin.getLang().others.missingElo;
        }
        return this.groupNames.getOrDefault(elo, elo);
    }
    
    public void reload() {
        this.sealConfig.reload();
        this.loadTiers();
    }
    
    private void loadTiers() {
        this.tiers.clear();
        if (!Reference.TIERS_FOLDER.exists() || Reference.TIERS_FOLDER.listFiles().length == 0) {
            this.tiers.put("NOLEGENDARIES", this.getDefaultTier("NOLEGENDARIES", "No Legendaries", p -> !p.isLegendary()));
            this.tiers.put("LEGENDARIES", this.getDefaultTier("LEGENDARIES", "Legendaries", EnumSpecies::isLegendary));
            this.tiers.put("GEN1", this.getDefaultTier("GEN1", "Generation 1", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN1));
            this.tiers.put("GEN2", this.getDefaultTier("GEN2", "Generation 2", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN2));
            this.tiers.put("GEN3", this.getDefaultTier("GEN3", "Generation 3", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN3));
            this.tiers.put("GEN4", this.getDefaultTier("GEN4", "Generation 4", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN4));
            this.tiers.put("GEN5", this.getDefaultTier("GEN5", "Generation 5", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN5));
            this.tiers.put("GEN6", this.getDefaultTier("GEN6", "Generation 6", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN6));
            this.tiers.put("GEN7", this.getDefaultTier("GEN7", "Generation 7", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN7));
            this.tiers.put("GEN8", this.getDefaultTier("GEN8", "Generation 8", p -> PokemonUtils.Generation.get(p) == PokemonUtils.Generation.GEN8));
            this.tiers.put("FE", this.getDefaultTier("FE", "Fully-Evolved", p -> p.getBaseStats().getEvolutions().isEmpty()));
            this.tiers.put("NFE", this.getDefaultTier("NFE", "Not-Fully-Evolved", p -> !p.getBaseStats().getEvolutions().isEmpty()));
            this.tiers.put("LC", this.getDefaultTier("LC", "Little Cup", p -> !p.getBaseStats().getEvolutions().isEmpty() && p.getBaseStats().preEvolutions.length == 0, 5, "sleep", "evasion", "ohko", "endlessbattle"));
            this.tiers.put("MONOTYPE", this.getDefaultTier("MONOTYPE", "Monotype", p -> p.getBaseStats().getTypeList().size() == 1));
            this.tiers.put("DUALTYPE", this.getDefaultTier("DUALTYPE", "Dualtype", p -> p.getBaseStats().getTypeList().size() == 2));
        }
        for (final File file : Reference.TIERS_FOLDER.listFiles()) {
            final String id = file.getName().split("\\.")[0];
            if (!this.tiers.containsKey(file.getName().split("\\.")[0])) {
                this.tiers.put(id, this.sealConfig.getConfig(file, Tier.class));
            }
        }
    }
    
    private Tier getDefaultTier(final String id, final String name, final Predicate<EnumSpecies> predicate, final int levelCap, final String... clauses) {
        final File tierFile = new File(Reference.TIERS_FOLDER, id + ".json");
        if (tierFile.exists()) {
            return this.sealConfig.getConfig(tierFile, Tier.class);
        }
        final String[] pokemons = Arrays.stream(EnumSpecies.values()).filter(p -> predicate.test(p) && !p.getPokemonName().startsWith("MissingNo")).map(specie -> specie.name).toArray(String[]::new);
        final Tier tier = this.sealConfig.getConfig(tierFile, Tier.class);
        tier.tierName = name;
        tier.tierAliases = new String[] { name };
        tier.allowedPokemons = pokemons;
        tier.levelCap = levelCap;
        tier.clauses = clauses;
        this.sealConfig.save(tier);
        return tier;
    }
    
    private Tier getDefaultTier(final String id, final String name, final Predicate<EnumSpecies> predicate) {
        return this.getDefaultTier(id, name, predicate, 0, new String[0]);
    }
    
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map) {
        final List<Map.Entry<K, V>> list = new ArrayList<Map.Entry<K, V>>(map.entrySet());
        list.sort((Comparator<? super Map.Entry<K, V>>)Map.Entry.comparingByValue());
        final Map<K, V> result = new LinkedHashMap<K, V>();
        for (final Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    public void updateGroupName(final String groupName) {
        final Group group = this.plugin.getLuckPermsAPI().getGroupManager().getGroup(groupName);
        if (group == null || group.getDisplayName() == null) {
            return;
        }
        this.groupNames.put(groupName, group.getDisplayName());
    }
    
    public Config getConfig() {
        return this.config;
    }
    
    public Menu getMenuConfig() {
        return this.menuConfig;
    }
    
    public Lang getLang() {
        return this.lang;
    }
    
    public Challonge getChallongeConfig() {
        return this.challongeConfig;
    }
}
