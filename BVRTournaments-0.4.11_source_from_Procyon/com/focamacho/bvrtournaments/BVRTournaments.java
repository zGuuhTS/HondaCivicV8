// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments;

import org.spongepowered.api.plugin.PluginContainer;
import com.focamacho.bvrtournaments.config.Tier;
import com.focamacho.bvrtournaments.config.Challonge;
import com.focamacho.bvrtournaments.config.Lang;
import com.focamacho.bvrtournaments.config.Menu;
import com.focamacho.bvrtournaments.lib.EventListener;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.config.Config;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import com.focamacho.bvrtournaments.menu.TopPointsMenu;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.ProvisioningException;
import com.focamacho.bvrtournaments.compat.PlaceholderAPICompat;
import com.focamacho.bvrtournaments.listener.PixelmonEventListener;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.focamacho.bvrtournaments.listener.SpongeListener;
import com.focamacho.bvrtournaments.command.tournament.ParticipantsTournamentCommand;
import java.util.Collections;
import net.minecraft.entity.player.EntityPlayer;
import com.focamacho.bvrtournaments.command.tournament.KickTournamentCommand;
import com.focamacho.bvrtournaments.command.tournament.FinishTournamentCommand;
import com.focamacho.bvrtournaments.command.tournament.LeaveTournamentCommand;
import com.focamacho.bvrtournaments.command.tournament.JoinTournamentCommand;
import com.focamacho.bvrtournaments.command.tournament.CancelTournamentCommand;
import com.focamacho.bvrtournaments.command.tournament.StartTournamentCommand;
import org.spongepowered.api.command.CommandCallable;
import com.focamacho.bvrtournaments.command.tournament.CreateTournamentCommand;
import com.focamacho.bvrtournaments.command.tournament.TournamentCommand;
import com.focamacho.bvrtournaments.command.tiers.TierCommand;
import com.focamacho.bvrtournaments.command.battles.TeamPreviewCommand;
import com.focamacho.bvrtournaments.command.battles.CreateArenaCommand;
import com.focamacho.bvrtournaments.command.battles.ArenasCommand;
import com.focamacho.bvrtournaments.command.battles.BetCommand;
import com.focamacho.bvrtournaments.command.battles.ReadyCommand;
import com.focamacho.bvrtournaments.command.battles.ILDuoCommand;
import java.util.HashMap;
import java.util.Map;
import com.focamacho.bvrtournaments.command.battles.ILCommand;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import com.google.common.collect.Maps;
import com.focamacho.bvrtournaments.command.points.UpdatePointsCommand;
import com.focamacho.bvrtournaments.command.points.ResetPointsCommand;
import com.focamacho.bvrtournaments.command.points.TopPointsCommand;
import com.focamacho.bvrtournaments.command.points.SetPointsCommand;
import com.focamacho.bvrtournaments.command.points.RemovePointsCommand;
import com.focamacho.bvrtournaments.command.points.AddPointsCommand;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.args.CommandElement;
import com.focamacho.bvrtournaments.command.points.PointsCommand;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.List;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import com.google.inject.Inject;
import org.slf4j.Logger;
import net.luckperms.api.LuckPerms;
import com.focamacho.bvrtournaments.controller.TournamentController;
import com.focamacho.bvrtournaments.controller.DatabaseController;
import com.focamacho.bvrtournaments.controller.MenuController;
import com.focamacho.bvrtournaments.controller.BetController;
import com.focamacho.bvrtournaments.controller.ConfigController;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "bvrtournaments", name = "BVRTournaments", description = "Sponge plugin for creating Pixelmon Reforged Tournaments.", authors = { "Focamacho" }, dependencies = { @Dependency(id = "luckperms") })
public class BVRTournaments
{
    private ConfigController configController;
    private BetController betController;
    private MenuController menuController;
    private DatabaseController databaseController;
    private TournamentController tournamentController;
    private LuckPerms luckPermsAPI;
    @Inject
    private Logger logger;
    
    @Listener
    public void onServerStart(final GameStartedServerEvent event) {
        this.configController = new ConfigController(this);
        this.betController = new BetController(this);
        this.databaseController = new DatabaseController(this);
        this.menuController = new MenuController(this);
        this.tournamentController = new TournamentController(this);
        final Optional<ProviderRegistration<LuckPerms>> provider = (Optional<ProviderRegistration<LuckPerms>>)Sponge.getServiceManager().getRegistration((Class)LuckPerms.class);
        if (provider.isPresent()) {
            this.luckPermsAPI = (LuckPerms)provider.get().getProvider();
            final Config.Commands commands = this.configController.getConfig().command;
            final Function<CommandSource, Iterable<String>> tierSuggestions = (Function<CommandSource, Iterable<String>>)(src -> this.getTiers().keySet().stream().map((Function<? super Object, ?>)String::toLowerCase).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
            this.registerCommand(commands.pointsCommand, (CommandExecutor)new PointsCommand(this), GenericArguments.optional(GenericArguments.string((Text)Text.of("player"))));
            this.registerCommand(commands.addPointsCommand, (CommandExecutor)new AddPointsCommand(this), GenericArguments.string((Text)Text.of("player")), GenericArguments.integer((Text)Text.of("points")));
            this.registerCommand(commands.removePointsCommand, (CommandExecutor)new RemovePointsCommand(this), GenericArguments.string((Text)Text.of("player")), GenericArguments.integer((Text)Text.of("points")));
            this.registerCommand(commands.setPointsCommand, (CommandExecutor)new SetPointsCommand(this), GenericArguments.string((Text)Text.of("player")), GenericArguments.integer((Text)Text.of("points")));
            this.registerCommand(commands.topPointsCommand, (CommandExecutor)new TopPointsCommand(this), new CommandElement[0]);
            this.registerCommand(commands.resetPointsCommand, (CommandExecutor)new ResetPointsCommand(this), new CommandElement[0]);
            this.registerCommand(commands.updatePointsCommand, (CommandExecutor)new UpdatePointsCommand(this), GenericArguments.string((Text)Text.of("playerWin")), GenericArguments.string((Text)Text.of("playerDefeat")), GenericArguments.optional(GenericArguments.integer((Text)Text.of("minWinPoints"))), GenericArguments.optional(GenericArguments.integer((Text)Text.of("minLosePoints"))));
            final Map<String, EnumBattleType> battleTypes = (Map<String, EnumBattleType>)Maps.newHashMap();
            for (final EnumBattleType value2 : EnumBattleType.values()) {
                battleTypes.put(value2.toString(), value2);
            }
            this.registerCommand(commands.ilCommand, (CommandExecutor)new ILCommand(this), GenericArguments.player((Text)Text.of("player1")), GenericArguments.player((Text)Text.of("player2")), GenericArguments.optional(GenericArguments.withSuggestions(GenericArguments.string((Text)Text.of("tier")), (Function)tierSuggestions)), GenericArguments.optional(GenericArguments.choicesInsensitive((Text)Text.of("type"), (Map)battleTypes)), GenericArguments.optional(GenericArguments.choices((Text)Text.of("maxPokes"), (Map)new HashMap<String, Integer>() {
                {
                    for (int i = 1; i <= 6; ++i) {
                        this.put(String.valueOf(i), i);
                    }
                }
            })));
            this.registerCommand(commands.ilDuoCommand, (CommandExecutor)new ILDuoCommand(this), GenericArguments.player((Text)Text.of("player1")), GenericArguments.player((Text)Text.of("player2")), GenericArguments.player((Text)Text.of("player3")), GenericArguments.player((Text)Text.of("player4")), GenericArguments.optional(GenericArguments.withSuggestions(GenericArguments.string((Text)Text.of("tier")), (Function)tierSuggestions)), GenericArguments.optional(GenericArguments.choices((Text)Text.of("maxPokes"), (Map)new HashMap<String, Integer>() {
                {
                    for (int i = 1; i <= 6; ++i) {
                        this.put(String.valueOf(i), i);
                    }
                }
            })));
            this.registerCommand(commands.readyCommand, (CommandExecutor)new ReadyCommand(this), new CommandElement[0]);
            this.registerCommand(commands.betCommand, (CommandExecutor)new BetCommand(this), GenericArguments.player((Text)Text.of("player")), GenericArguments.doubleNum((Text)Text.of("amount")));
            this.registerCommand(commands.arenasCommand, (CommandExecutor)new ArenasCommand(this), GenericArguments.optional(GenericArguments.integer((Text)Text.of("page"))));
            this.registerCommand(commands.createArenaCommand, (CommandExecutor)new CreateArenaCommand(this), GenericArguments.choicesInsensitive((Text)Text.of("type"), (Map)new HashMap<String, String>() {
                {
                    this.put("solo", "solo");
                    this.put("duo", "duo");
                }
            }));
            this.registerCommand(commands.teamPreviewCommand, (CommandExecutor)new TeamPreviewCommand(this), GenericArguments.player((Text)Text.of("player")));
            this.registerCommand(commands.tierCommand, (CommandExecutor)new TierCommand(this), GenericArguments.withSuggestions(GenericArguments.remainingJoinedStrings((Text)Text.of("pokemon/tier")), (Function)tierSuggestions));
            final Optional<TournamentController.Tournament> tournament;
            this.registerCommand(this.buildCommandSpec(commands.tournamentCommand, (CommandExecutor)new TournamentCommand(this), new CommandElement[0]).child((CommandCallable)this.buildCommandSpec(commands.tournamentCreateSubCommand, (CommandExecutor)new CreateTournamentCommand(this), GenericArguments.optional(GenericArguments.withSuggestions(GenericArguments.string((Text)Text.of("tier")), (Function)tierSuggestions))).build(), commands.tournamentCreateSubCommand.aliases).child((CommandCallable)this.buildCommandSpec(commands.tournamentStartSubCommand, (CommandExecutor)new StartTournamentCommand(this), new CommandElement[0]).build(), commands.tournamentStartSubCommand.aliases).child((CommandCallable)this.buildCommandSpec(commands.tournamentCancelSubCommand, (CommandExecutor)new CancelTournamentCommand(this), new CommandElement[0]).build(), commands.tournamentCancelSubCommand.aliases).child((CommandCallable)this.buildCommandSpec(commands.tournamentJoinSubCommand, (CommandExecutor)new JoinTournamentCommand(this), new CommandElement[0]).build(), commands.tournamentJoinSubCommand.aliases).child((CommandCallable)this.buildCommandSpec(commands.tournamentLeaveSubCommand, (CommandExecutor)new LeaveTournamentCommand(this), new CommandElement[0]).build(), commands.tournamentLeaveSubCommand.aliases).child((CommandCallable)this.buildCommandSpec(commands.tournamentFinishSubCommand, (CommandExecutor)new FinishTournamentCommand(this), new CommandElement[0]).build(), commands.tournamentFinishSubCommand.aliases).child((CommandCallable)this.buildCommandSpec(commands.tournamentKickSubCommand, (CommandExecutor)new KickTournamentCommand(this), GenericArguments.withSuggestions(GenericArguments.string((Text)Text.of("player")), src -> {
                tournament = this.getTournamentController().getCurrentTournament();
                return (List<Object>)tournament.map(value -> value.getParticipants().stream().map((Function<? super Object, ?>)EntityPlayer::func_70005_c_).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())).orElse(Collections.emptyList());
            })).build(), commands.tournamentKickSubCommand.aliases).child((CommandCallable)this.buildCommandSpec(commands.tournamentParticipantsSubCommand, (CommandExecutor)new ParticipantsTournamentCommand(this), new CommandElement[0]).build(), commands.tournamentParticipantsSubCommand.aliases).build(), commands.tournamentCommand.aliases);
            Sponge.getEventManager().registerListeners((Object)this, (Object)new SpongeListener(this));
            Pixelmon.EVENT_BUS.register((Object)new PixelmonEventListener(this));
            Sponge.getPluginManager().getPlugin("placeholderapi").ifPresent(placeholder -> {
                try {
                    new PlaceholderAPICompat(this);
                }
                catch (ProvisioningException e) {
                    this.logger.error("PlaceholderAPI compat failed to load.");
                    e.printStackTrace();
                }
                return;
            });
            Task.builder().execute(TopPointsMenu::updateTopHeads).interval((long)this.getConfig().general.topPointsUpdate, TimeUnit.MINUTES).submit((Object)this);
        }
        else {
            this.logger.error("LuckPerms is not loaded. This plugin will not work.");
        }
    }
    
    @Listener
    public void onServerReload(final GameReloadEvent event) {
        this.configController.reload();
        this.databaseController = new DatabaseController(this);
        this.menuController = new MenuController(this);
    }
    
    private CommandSpec.Builder buildCommandSpec(final Config.Commands.CommandObject object, final CommandExecutor executor, final CommandElement... args) {
        final CommandSpec.Builder builder = CommandSpec.builder().description(TextUtils.formatText(object.description, new Object[0])).executor(executor);
        if (!object.permission.isEmpty()) {
            builder.permission(object.permission);
        }
        if (args.length > 0) {
            builder.arguments(args);
        }
        return builder;
    }
    
    private void registerCommand(final CommandSpec spec, final String... aliases) {
        if (aliases.length == 0) {
            this.logger.error("Not registering command because it has no valid aliases.");
            return;
        }
        Sponge.getCommandManager().register((Object)this, (CommandCallable)spec, aliases);
        if (spec.getExecutor() instanceof EventListener) {
            Sponge.getEventManager().registerListeners((Object)this, (Object)spec.getExecutor());
        }
    }
    
    private void registerCommand(final Config.Commands.CommandObject object, final CommandExecutor executor, final CommandElement... args) {
        this.registerCommand(this.buildCommandSpec(object, executor, args).build(), object.aliases);
    }
    
    public Config getConfig() {
        return this.configController.getConfig();
    }
    
    public Menu getMenusConfig() {
        return this.configController.getMenuConfig();
    }
    
    public Lang getLang() {
        return this.configController.getLang();
    }
    
    public Challonge getChallongeConfig() {
        return this.configController.getChallongeConfig();
    }
    
    public Map<String, Tier> getTiers() {
        return this.configController.getTiers();
    }
    
    public Tier getTier(final String tier) {
        return this.configController.getTier(tier);
    }
    
    public ConfigController getConfigController() {
        return this.configController;
    }
    
    public BetController getBetController() {
        return this.betController;
    }
    
    public MenuController getMenuController() {
        return this.menuController;
    }
    
    public DatabaseController getDatabaseController() {
        return this.databaseController;
    }
    
    public TournamentController getTournamentController() {
        return this.tournamentController;
    }
    
    public LuckPerms getLuckPermsAPI() {
        return this.luckPermsAPI;
    }
    
    public Logger getLogger() {
        return this.logger;
    }
}
