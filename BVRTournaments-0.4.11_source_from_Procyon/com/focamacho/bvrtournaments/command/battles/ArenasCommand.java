// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.battles;

import java.text.MessageFormat;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import java.util.List;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.action.ClickAction;
import org.spongepowered.api.text.action.HoverAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.Location;
import java.util.concurrent.atomic.AtomicReference;
import com.focamacho.bvrtournaments.util.TextUtils;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Comparator;
import com.focamacho.bvrtournaments.controller.DatabaseController;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.lib.EventListener;
import com.focamacho.bvrtournaments.command.Command;

public class ArenasCommand extends Command implements EventListener
{
    public ArenasCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) throws CommandException {
        this.sendArenaMessages(src, args.getOne((Text)Text.of("page")).orElse(1));
        return CommandResult.success();
    }
    
    private void sendArenaMessages(final CommandSource src, int page) {
        src.sendMessage((Text)Text.of("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n "));
        final List<DatabaseController.Arena> arenas = (List<DatabaseController.Arena>)Lists.newArrayList((Iterable)this.plugin.getDatabaseController().getSoloArenas());
        arenas.sort(Comparator.comparingInt(DatabaseController.Arena::getId));
        arenas.addAll(this.plugin.getDatabaseController().getDuoArenas().stream().sorted(Comparator.comparingInt(DatabaseController.Arena::getId)).collect((Collector<? super DatabaseController.Arena, ?, Collection<? extends DatabaseController.Arena>>)Collectors.toList()));
        while (arenas.size() > 0 && page > Math.ceil(arenas.size() / 18.0)) {
            if (page < 1) {
                page = 1;
                break;
            }
            --page;
        }
        final AtomicReference<Text> toSend = new AtomicReference<Text>(TextUtils.formatText(this.plugin.getLang().commands.arenasList, new Object[0]).replace("{0}", Text.of(new Object[] { page })).replace("{1}", Text.of(new Object[] { Math.max(1, (int)Math.ceil(arenas.size() / 18.0)) })));
        toSend.set(toSend.get().concat((Text)Text.NEW_LINE));
        final int finalPage = page;
        if (arenas.size() == 0) {
            toSend.set(toSend.get().concat(TextUtils.formatText(this.plugin.getLang().commands.noArenas, new Object[0])));
            toSend.set(toSend.get().concat((Text)Text.NEW_LINE));
        }
        else {
            for (int i = 18 * page - 18; i < Math.min(page * 18, arenas.size()); ++i) {
                final DatabaseController.Arena arena = arenas.get(i);
                final List<Location<World>> locations = arena.getLocations();
                final Location<World> baseLocation = locations.get(0);
                Text text = TextUtils.formatText((locations.size() == 4) ? this.plugin.getLang().commands.duoArenaItem : this.plugin.getLang().commands.arenaItem, i + 1);
                text = text.replace("{1}", Text.builder().append(new Text[] { TextUtils.formatText(this.plugin.getLang().commands.coordinates, baseLocation.getX(), baseLocation.getZ(), baseLocation.getY()) }).onHover((HoverAction)TextActions.showText(TextUtils.formatText(this.formatCoordiantes(locations), new Object[0]))).onClick((ClickAction)TextActions.runCommand("/minecraft:tp " + baseLocation.getX() + " " + baseLocation.getY() + " " + baseLocation.getZ())).build());
                toSend.set(toSend.get().concat(text.replace("{2}", Text.builder().append(new Text[] { TextUtils.formatText(this.plugin.getLang().commands.remove, new Object[0]) }).onHover((HoverAction)TextActions.showText(TextUtils.formatText(this.plugin.getLang().commands.clickToRemove, new Object[0]))).onClick((ClickAction)TextActions.runCommand("/removecreatedarena " + ((locations.size() == 4) ? "duo " : "solo ") + arena.getId() + " " + finalPage)).build())));
                toSend.set(toSend.get().concat((Text)Text.NEW_LINE));
            }
        }
        final Text.Builder previousPage = Text.builder().format(TextFormat.NONE).append(new Text[] { (Text)Text.of(((page > 1) ? "§a" : "§7") + "<") });
        final Text.Builder nextPage = Text.builder().format(TextFormat.NONE).append(new Text[] { (Text)Text.of(((arenas.size() / 18.0 > page) ? "§a" : "§7") + ">") });
        if (page > 1) {
            previousPage.onClick((ClickAction)TextActions.runCommand("/" + this.plugin.getConfig().command.arenasCommand.aliases[0] + " " + (page - 1))).onHover((HoverAction)TextActions.showText(TextUtils.formatText(this.plugin.getLang().commands.clickPreviousPage, new Object[0])));
        }
        if (arenas.size() / 18.0 > page) {
            nextPage.onClick((ClickAction)TextActions.runCommand("/" + this.plugin.getConfig().command.arenasCommand.aliases[0] + " " + (page + 1))).onHover((HoverAction)TextActions.showText(TextUtils.formatText(this.plugin.getLang().commands.clickNextPage, new Object[0])));
        }
        toSend.set(toSend.get().concat(TextUtils.formatText(this.plugin.getLang().commands.arenasList2, new Object[0]).replace("{0}", previousPage.build()).replace("{1}", nextPage.build())));
        src.sendMessage((Text)toSend.get());
    }
    
    @Listener
    public void onPlayerCommand(final SendCommandEvent event) {
        if (event.getSource() instanceof CommandSource) {
            final CommandSource src = (CommandSource)event.getSource();
            if (event.getCommand().equalsIgnoreCase("removecreatedarena") && src.hasPermission(this.plugin.getConfig().command.createArenaCommand.permission)) {
                event.setCancelled(true);
                final String[] args = event.getArguments().split(" ");
                if (args.length == 3) {
                    Integer number = null;
                    Integer page = null;
                    try {
                        number = Integer.parseInt(args[1]);
                        page = Integer.parseInt(args[2]);
                    }
                    catch (NumberFormatException ex) {}
                    final boolean solo = args[0].equalsIgnoreCase("solo");
                    if (number != null && page != null) {
                        if (solo) {
                            this.plugin.getDatabaseController().removeSoloArena(number);
                        }
                        else {
                            this.plugin.getDatabaseController().removeDuoArena(number);
                        }
                        this.sendArenaMessages(src, page);
                    }
                }
            }
        }
    }
    
    private String formatCoordiantes(final List<Location<World>> locations) {
        String text = (locations.size() == 4) ? this.plugin.getLang().commands.duoHoverCoordinates : this.plugin.getLang().commands.hoverCoordinates;
        for (int i = 0; i < locations.size(); ++i) {
            final Location<World> location = locations.get(i);
            text = text.replace("{" + i + "}", MessageFormat.format(this.plugin.getLang().commands.coordinates, location.getX(), location.getZ(), location.getY()));
        }
        return text;
    }
}
