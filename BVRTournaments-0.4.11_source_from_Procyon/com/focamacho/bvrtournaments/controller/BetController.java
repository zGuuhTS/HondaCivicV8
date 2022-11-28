// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.controller;

import java.util.Iterator;
import com.google.common.collect.Maps;
import java.util.function.ToDoubleFunction;
import java.text.MessageFormat;
import com.focamacho.bvrtournaments.util.MoneyUtils;
import com.focamacho.bvrtournaments.config.Config;
import java.util.Map;
import org.spongepowered.api.entity.living.player.Player;
import java.util.TimerTask;
import java.util.Timer;
import org.spongepowered.api.Sponge;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.text.Text;
import com.google.common.collect.HashBasedTable;
import org.spongepowered.api.boss.ServerBossBar;
import com.google.common.collect.Table;
import com.google.common.collect.Lists;
import java.util.Optional;
import net.minecraft.entity.player.EntityPlayerMP;
import java.util.List;
import com.focamacho.bvrtournaments.BVRTournaments;

public class BetController
{
    private final BVRTournaments plugin;
    private final List<Bet> bets;
    
    public void startBet(final List<EntityPlayerMP> team1, final List<EntityPlayerMP> team2) {
        this.bets.stream().filter(bet -> bet.team1.stream().anyMatch(player -> team1.contains(player) || team2.contains(player)) || bet.team2.stream().anyMatch(player -> team1.contains(player) || team2.contains(player))).findFirst().ifPresent(bet -> {
            bet.cancel();
            this.bets.remove(bet);
            return;
        });
        this.bets.add(new Bet(team1, team2, this.plugin));
    }
    
    public Optional<Bet> getBet(final EntityPlayerMP player) {
        return this.bets.stream().filter(bet -> bet.team1.contains(player) || bet.team2.contains(player)).findFirst();
    }
    
    public void cancelBet(final EntityPlayerMP player) {
        this.bets.stream().filter(bet -> bet.team1.contains(player) || bet.team2.contains(player)).findFirst().ifPresent(bet -> {
            bet.cancel();
            this.bets.remove(bet);
        });
    }
    
    public BetController(final BVRTournaments plugin) {
        this.bets = (List<Bet>)Lists.newArrayList();
        this.plugin = plugin;
    }
    
    public static class Bet
    {
        private final BVRTournaments plugin;
        private final List<EntityPlayerMP> team1;
        private final List<EntityPlayerMP> team2;
        private final float totalSecondsToBet;
        private int secondsRemainingToBet;
        private boolean isCanceled;
        private final Table<EntityPlayerMP, EntityPlayerMP, Double> bets;
        private final ServerBossBar bossBar;
        
        public Bet(final List<EntityPlayerMP> team1, final List<EntityPlayerMP> team2, final BVRTournaments plugin) {
            this.isCanceled = false;
            this.bets = (Table<EntityPlayerMP, EntityPlayerMP, Double>)HashBasedTable.create();
            final Config.Bets config = plugin.getConfig().bets;
            this.plugin = plugin;
            this.team1 = team1;
            this.team2 = team2;
            this.totalSecondsToBet = (float)config.secondsToBet;
            this.secondsRemainingToBet = config.secondsToBet;
            this.bossBar = ServerBossBar.builder().name((Text)Text.of("placeholder")).color(TextUtils.getBossBarColor(config.bossBarColor)).overlay(TextUtils.getBossBarOverlay(config.bossBarOverlay)).createFog(false).darkenSky(false).playEndBossMusic(false).visible(true).build();
            if (config.broadcastBetAvailable) {
                Sponge.getServer().getOnlinePlayers().forEach(player -> TextUtils.sendFormattedMessage(player, this.formatString(plugin.getLang().geral.betStarted), new Object[0]));
            }
            this.updateBossbar();
            this.bossBar.addPlayers(Sponge.getServer().getOnlinePlayers());
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (Bet.this.isCanceled || Bet.this.secondsRemainingToBet <= 0) {
                        if (Bet.this.secondsRemainingToBet <= 0) {
                            final Map<EntityPlayerMP, Float> team1Percentages = Bet.this.getPercentagesSplit(team1);
                            final Map<EntityPlayerMP, Float> team2Percentages = Bet.this.getPercentagesSplit(team2);
                            final double totalAward = Bet.this.getTotalBet() / 100.0 * (100.0f - plugin.getConfig().bets.taxPercentage);
                            final List<EntityPlayerMP> val$team1;
                            final BVRTournaments val$plugin;
                            final double n;
                            team1Percentages.forEach((player, percentage) -> {
                                val$team1 = team1;
                                val$plugin = plugin;
                                if (val$team1.size() == 2) {
                                    TextUtils.sendFormattedMessage(player, val$plugin.getLang().geral.betTimeDuoEnded, val$team1.get(0).func_70005_c_(), val$team1.get(1).func_70005_c_(), n / 100.0 * percentage);
                                }
                                else {
                                    TextUtils.sendFormattedMessage(player, val$plugin.getLang().geral.betTimeEnded, val$team1.get(0).func_70005_c_(), n / 100.0 * percentage);
                                }
                                return;
                            });
                            final List<EntityPlayerMP> val$team2;
                            final BVRTournaments val$plugin2;
                            final double n2;
                            team2Percentages.forEach((player, percentage) -> {
                                val$team2 = team2;
                                val$plugin2 = plugin;
                                if (val$team2.size() == 2) {
                                    TextUtils.sendFormattedMessage(player, val$plugin2.getLang().geral.betTimeDuoEnded, val$team2.get(0).func_70005_c_(), val$team2.get(1).func_70005_c_(), n2 / 100.0 * percentage);
                                }
                                else {
                                    TextUtils.sendFormattedMessage(player, val$plugin2.getLang().geral.betTimeEnded, val$team2.get(0).func_70005_c_(), n2 / 100.0 * percentage);
                                }
                                return;
                            });
                        }
                        timer.cancel();
                        return;
                    }
                    Bet.this.secondsRemainingToBet--;
                    Bet.this.updateBossbar();
                }
            }, 1000L, 1000L);
        }
        
        public void bet(final EntityPlayerMP player, final EntityPlayerMP betPlayer, final double amount) {
            if (!this.team1.contains(betPlayer) && !this.team2.contains(betPlayer)) {
                throw new IllegalArgumentException("O jogador inserido n\u00e3o faz parte dessa aposta.");
            }
            if (this.isCanceled || this.secondsRemainingToBet <= 0) {
                ((Player)player).sendMessage(TextUtils.formatText(this.plugin.getLang().commands.cantBet, new Object[0]));
                return;
            }
            if (amount <= 0.0) {
                ((Player)player).sendMessage(TextUtils.formatText(this.plugin.getLang().commands.invalidValue, new Object[0]));
                return;
            }
            double totalBet = amount;
            if (this.bets.containsRow((Object)player)) {
                final Double playerBet = (Double)this.bets.get((Object)player, (Object)betPlayer);
                if (playerBet == null) {
                    ((Player)player).sendMessage(TextUtils.formatText(this.plugin.getLang().commands.alreadyBet, new Object[0]));
                    return;
                }
                totalBet += playerBet;
            }
            final double playerMoney = MoneyUtils.getMoney(player.func_110124_au());
            if (playerMoney < amount) {
                ((Player)player).sendMessage(TextUtils.formatText(this.plugin.getLang().commands.missingMoney, new Object[0]));
                return;
            }
            MoneyUtils.removeMoney(player.func_110124_au(), amount);
            this.bets.put((Object)player, (Object)betPlayer, (Object)totalBet);
            ((Player)player).sendMessage(TextUtils.formatText(MessageFormat.format(this.plugin.getLang().commands.betMade, amount, betPlayer.func_70005_c_(), this.plugin.getConfig().bets.taxPercentage), new Object[0]));
            if (totalBet > amount) {
                ((Player)player).sendMessage(TextUtils.formatText(MessageFormat.format(this.plugin.getLang().commands.totalBet, totalBet), new Object[0]));
            }
        }
        
        public void updateBossbar() {
            if (this.secondsRemainingToBet > 0) {
                if (this.team1.size() == 2) {
                    this.bossBar.setName(TextUtils.formatText(this.formatString(MessageFormat.format(this.plugin.getLang().geral.betDuoBossBar, this.team1.get(0).func_70005_c_(), this.team1.get(1).func_70005_c_(), this.team2.get(0).func_70005_c_(), this.team2.get(1).func_70005_c_())), new Object[0])).setPercent(this.secondsRemainingToBet / this.totalSecondsToBet);
                }
                else {
                    this.bossBar.setName(TextUtils.formatText(this.formatString(MessageFormat.format(this.plugin.getLang().geral.betBossBar, this.team1.get(0).func_70005_c_(), this.team2.get(0).func_70005_c_())), new Object[0])).setPercent(this.secondsRemainingToBet / this.totalSecondsToBet);
                }
            }
            else {
                this.bossBar.removePlayers(this.bossBar.getPlayers());
            }
        }
        
        public float getBetPercentage(final List<EntityPlayerMP> players) {
            final float percentage = (float)this.getBetAmount(players) / (float)this.getTotalBet() * 100.0f;
            return Float.isNaN(percentage) ? 0.0f : percentage;
        }
        
        public double getBetAmount(final List<EntityPlayerMP> players) {
            final Double value;
            return this.bets.cellSet().stream().filter(cell -> players.contains(cell.getColumnKey())).mapToDouble(cell -> {
                value = (Double)cell.getValue();
                return (value != null) ? value : 0.0;
            }).sum();
        }
        
        public double getTotalBet() {
            return this.bets.values().stream().mapToDouble(Double::doubleValue).sum();
        }
        
        public void cancel() {
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            this.bossBar.removePlayers(this.bossBar.getPlayers());
            final Player player;
            this.bets.cellSet().forEach(cell -> {
                player = (Player)cell.getRowKey();
                if (player != null) {
                    if (cell.getValue() != null) {
                        MoneyUtils.addMoney(player.getUniqueId(), (double)cell.getValue());
                    }
                    player.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.betCanceled, new Object[0]));
                }
            });
        }
        
        public void end(final List<EntityPlayerMP> winners, final List<EntityPlayerMP> losers) {
            if (this.isCanceled) {
                return;
            }
            this.isCanceled = true;
            this.bossBar.removePlayers(this.bossBar.getPlayers());
            final double totalBet = this.getTotalBet();
            final double totalBetWinner = this.getBetAmount(winners);
            final double totalBetLoser = this.getBetAmount(losers);
            final double taxValue = (totalBetLoser > 0.0) ? (totalBetLoser / 100.0 * this.plugin.getConfig().bets.taxPercentage) : 0.0;
            final double totalAward = totalBet - taxValue;
            final Map<EntityPlayerMP, Float> awardPercentages = (Map<EntityPlayerMP, Float>)Maps.newHashMap();
            for (final Table.Cell<EntityPlayerMP, EntityPlayerMP, Double> cell : this.bets.cellSet()) {
                if (winners.contains(cell.getColumnKey()) && cell.getValue() != null) {
                    awardPercentages.put((EntityPlayerMP)cell.getRowKey(), (float)((double)cell.getValue() / totalBetWinner) * 100.0f);
                }
            }
            final double n;
            double award;
            awardPercentages.forEach((player, percentage) -> {
                if (percentage > 0.0f) {
                    award = n / 100.0 * percentage;
                    MoneyUtils.addMoney(((EntityPlayerMP)player).func_110124_au(), award);
                    if (winners.size() == 2) {
                        TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.betDuoWon, winners.get(0).func_70005_c_(), winners.get(1).func_70005_c_(), award);
                    }
                    else {
                        TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.betWon, winners.get(0).func_70005_c_(), award);
                    }
                }
                return;
            });
            if (taxValue > 0.0 && this.plugin.getConfig().bets.winnerTaxPercentage > 0.0f) {
                final double award2 = taxValue / 100.0 * this.plugin.getConfig().bets.winnerTaxPercentage;
                for (final EntityPlayerMP winner : winners) {
                    MoneyUtils.addMoney(winner.func_110124_au(), award2 / winners.size());
                    TextUtils.sendFormattedMessage((Player)winner, this.plugin.getLang().geral.betWinner, winner.func_70005_c_(), award2 / winners.size());
                }
            }
        }
        
        private Map<EntityPlayerMP, Float> getPercentagesSplit(final List<EntityPlayerMP> winners) {
            final double totalBet = this.getTotalBet();
            final Map<EntityPlayerMP, Float> awardPercentages = (Map<EntityPlayerMP, Float>)Maps.newHashMap();
            for (final Table.Cell<EntityPlayerMP, EntityPlayerMP, Double> cell : this.bets.cellSet()) {
                if (winners.contains(cell.getColumnKey()) && cell.getValue() != null) {
                    awardPercentages.put((EntityPlayerMP)cell.getRowKey(), (float)((double)cell.getValue() / totalBet) * 100.0f);
                }
            }
            return awardPercentages;
        }
        
        private String formatString(final String toFormat) {
            return toFormat.replace("%percentage1%", String.format("%.2f", this.getBetPercentage(this.team1))).replace("%percentage2%", String.format("%.2f", this.getBetPercentage(this.team2))).replace("%bet1%", String.valueOf(this.getBetAmount(this.team1))).replace("%bet2%", String.valueOf(this.getBetAmount(this.team2))).replace("%totalbet%", String.valueOf(this.getTotalBet()));
        }
        
        private String[] formatString(final String... toFormat) {
            for (int i = 0; i < toFormat.length; ++i) {
                toFormat[i] = this.formatString(toFormat[i]);
            }
            return toFormat;
        }
        
        public List<EntityPlayerMP> getTeam1() {
            return this.team1;
        }
        
        public List<EntityPlayerMP> getTeam2() {
            return this.team2;
        }
    }
}
