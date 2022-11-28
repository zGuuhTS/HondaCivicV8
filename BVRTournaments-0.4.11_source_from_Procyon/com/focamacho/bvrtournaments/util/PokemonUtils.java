// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.util;

import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import org.spongepowered.api.item.inventory.ItemStack;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class PokemonUtils
{
    public static ItemStack getPokemonPhoto(final Pokemon pokemon) {
        return ItemStackUtil.fromNative(ItemPixelmonSprite.getPhoto(pokemon));
    }
    
    public enum Generation
    {
        GEN1, 
        GEN2, 
        GEN3, 
        GEN4, 
        GEN5, 
        GEN6, 
        GEN7, 
        GEN8;
        
        public static Generation get(final EnumSpecies pokemon) {
            final int dexNumber = pokemon.getNationalPokedexInteger();
            if (dexNumber <= 151) {
                return Generation.GEN1;
            }
            if (dexNumber <= 251) {
                return Generation.GEN2;
            }
            if (dexNumber <= 386) {
                return Generation.GEN3;
            }
            if (dexNumber <= 493) {
                return Generation.GEN4;
            }
            if (dexNumber <= 649) {
                return Generation.GEN5;
            }
            if (dexNumber <= 721) {
                return Generation.GEN6;
            }
            if (dexNumber <= 809) {
                return Generation.GEN7;
            }
            return Generation.GEN8;
        }
    }
}
