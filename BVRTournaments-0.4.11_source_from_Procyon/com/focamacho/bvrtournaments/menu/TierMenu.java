// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.menu;

import java.util.Optional;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.focamacho.bvrtournaments.util.ItemUtils;
import com.focamacho.bvrtournaments.util.PokemonUtils;
import com.pixelmonmod.pixelmon.enums.forms.IEnumForm;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import com.focamacho.sealmenus.sponge.item.MenuItem;
import com.focamacho.sealmenus.sponge.item.ClickableItem;
import com.focamacho.sealmenus.sponge.PageableChestMenu;
import com.focamacho.sealmenus.sponge.ChestMenu;
import com.focamacho.sealmenus.sponge.SealMenus;
import com.focamacho.bvrtournaments.util.TextUtils;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.config.Tier;

public class TierMenu extends Menu
{
    public TierMenu(final Tier tier, final BVRTournaments plugin) {
        super(SealMenus.createPageableChestMenu(TextUtils.formatString(plugin.getMenusConfig().tier.inventoryName, tier.tierName), plugin.getMenusConfig().tier.inventorySize, plugin.getMenusConfig().tier.pokemonSlots, plugin), plugin);
        final com.focamacho.bvrtournaments.config.Menu.Tier config = plugin.getMenusConfig().tier;
        final PageableChestMenu menu = (PageableChestMenu)this.menu;
        menu.setNextPageItem(config.nextPageItem.createItem(), config.nextPageItem.itemSlot);
        menu.setPreviousPageItem(config.previousPageItem.createItem(), config.previousPageItem.itemSlot);
        for (final com.focamacho.bvrtournaments.config.Menu.ItemWithSlot decorativeItem : plugin.getMenusConfig().tier.decorativeItems) {
            this.menu.addItem(ClickableItem.create(decorativeItem.createItem()), decorativeItem.itemSlot);
        }
        for (final String allowedPokemon : tier.allowedPokemons) {
            final String[] split = allowedPokemon.split(":");
            final EnumSpecies specie = EnumSpecies.getFromNameAnyCase(split[0]);
            Label_0517: {
                if (specie == null) {
                    plugin.getLogger().error("Specie: " + split[0] + " doesn't exist.");
                }
                else {
                    final Pokemon pokemon = Pixelmon.pokemonFactory.create(specie);
                    IEnumForm form = null;
                    if (split.length == 2) {
                        final Optional<IEnumForm> optForm = (Optional<IEnumForm>)specie.getPossibleForms(true).stream().filter(enumForm -> enumForm.getFormSuffix().equalsIgnoreCase("-" + split[1])).findFirst();
                        if (!optForm.isPresent()) {
                            plugin.getLogger().error("Form: " + split[1] + " for Specie " + split[0] + " doesn't exist.");
                            plugin.getLogger().error("Available forms: " + String.join(", ", (CharSequence[])specie.getPossibleForms(true).stream().map(enumForm -> enumForm.getFormSuffix().substring(1)).toArray(String[]::new)));
                            break Label_0517;
                        }
                        form = optForm.get();
                        pokemon.setForm(form);
                    }
                    menu.addPageableItem(ClickableItem.create(ItemUtils.applyNameAndLore(PokemonUtils.getPokemonPhoto(pokemon), config.pokemonIconItem.itemName, config.pokemonIconItem.itemLore, specie.getLocalizedName(), (form == null) ? "" : form.getLocalizedName())));
                }
            }
        }
    }
}
