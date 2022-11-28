// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.util;

import java.util.function.Function;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.EventContext;
import com.focamacho.bvrtournaments.BVRTournaments;
import java.math.BigDecimal;
import org.spongepowered.api.service.economy.account.UniqueAccount;
import java.util.Optional;
import java.util.UUID;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.event.cause.Cause;

public class MoneyUtils
{
    private static final Cause emptyCause;
    private static final EconomyService economyService;
    
    public static double getMoney(final UUID player) {
        final Optional<UniqueAccount> account = (Optional<UniqueAccount>)MoneyUtils.economyService.getOrCreateAccount(player);
        return account.map(uniqueAccount -> uniqueAccount.getBalance(MoneyUtils.economyService.getDefaultCurrency()).doubleValue()).orElse(0.0);
    }
    
    public static void addMoney(final UUID player, final double value) {
        final Optional<UniqueAccount> account = (Optional<UniqueAccount>)MoneyUtils.economyService.getOrCreateAccount(player);
        account.ifPresent(uniqueAccount -> uniqueAccount.deposit(MoneyUtils.economyService.getDefaultCurrency(), BigDecimal.valueOf(value), MoneyUtils.emptyCause));
    }
    
    public static void removeMoney(final UUID player, final double value) {
        final Optional<UniqueAccount> account = (Optional<UniqueAccount>)MoneyUtils.economyService.getOrCreateAccount(player);
        account.ifPresent(uniqueAccount -> uniqueAccount.withdraw(MoneyUtils.economyService.getDefaultCurrency(), BigDecimal.valueOf(value), MoneyUtils.emptyCause));
    }
    
    public static EconomyService getEconomyService() {
        return MoneyUtils.economyService;
    }
    
    static {
        emptyCause = Cause.builder().append((Object)"").build(EventContext.empty());
        final Optional<EconomyService> service = (Optional<EconomyService>)Sponge.getServiceManager().provide((Class)EconomyService.class);
        if (service.isPresent()) {
            economyService = service.get();
        }
        else {
            Sponge.getPluginManager().getPlugin("bvrtournaments").flatMap(PluginContainer::getInstance).ifPresent(instance -> instance.getLogger().error("No compatible economy plugin loaded.\nPlease, install an economy plugin compatible with the Sponge API.\nSome functions will not work properly without an economy plugin."));
            economyService = null;
        }
    }
}
