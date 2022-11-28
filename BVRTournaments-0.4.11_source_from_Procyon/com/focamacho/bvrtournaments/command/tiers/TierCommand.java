// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.tiers;

import java.util.concurrent.Executors;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.api.command.CommandException;
import com.focamacho.bvrtournaments.menu.TierMenu;
import com.focamacho.bvrtournaments.config.Tier;
import org.spongepowered.api.entity.living.player.Player;
import com.focamacho.bvrtournaments.util.TextUtils;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import java.util.concurrent.ExecutorService;
import com.focamacho.bvrtournaments.command.Command;

public class TierCommand extends Command
{
    private static final ExecutorService executor;
    
    public TierCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
        final String pokemonOrTier;
        final EnumSpecies pokemon;
        StringBuilder tiers;
        String[] allowedPokemons;
        int length;
        int i = 0;
        String allowedPokemon;
        final EnumSpecies enumSpecies;
        final StringBuilder sb;
        Tier tier2;
        TierMenu menu;
        TierCommand.executor.execute(() -> {
            pokemonOrTier = args.getOne("pokemon/tier").get();
            pokemon = EnumSpecies.getFromNameAnyCase(pokemonOrTier);
            if (pokemon != null) {
                tiers = new StringBuilder();
                this.plugin.getTiers().forEach((id, tier) -> {
                    allowedPokemons = tier.allowedPokemons;
                    length = allowedPokemons.length;
                    while (i < length) {
                        allowedPokemon = allowedPokemons[i];
                        if (enumSpecies.name.equalsIgnoreCase(allowedPokemon)) {
                            if (sb.length() > 0) {
                                sb.append(", ");
                            }
                            sb.append(tier.tierName);
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                    return;
                });
                if (tiers.length() > 0) {
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pokemonTiers, pokemon.getLocalizedName(), tiers.toString()));
                }
                else {
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pokemonNoTier, new Object[0]));
                }
                return;
            }
            else {
                tier2 = this.plugin.getTier(pokemonOrTier);
                if (tier2 != null) {
                    if (this.plugin.getConfig().general.tierInventory && src instanceof Player) {
                        menu = this.plugin.getMenuController().getTierMenu(tier2);
                        if (menu != null) {
                            menu.open((Player)src);
                        }
                    }
                    else {
                        src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.tierPokemonList, tier2.tierName, String.join(", ", (CharSequence[])tier2.allowedPokemons)));
                    }
                    return;
                }
                else {
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notFoundTier, new Object[0]));
                    return;
                }
            }
        });
        return CommandResult.success();
    }
    
    static {
        executor = Executors.newSingleThreadExecutor();
    }
}
