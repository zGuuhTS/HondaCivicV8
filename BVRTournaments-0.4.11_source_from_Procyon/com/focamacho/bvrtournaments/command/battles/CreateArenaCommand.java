// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.battles;

import com.google.common.collect.Lists;
import java.util.Optional;
import org.spongepowered.api.effect.sound.SoundType;
import java.util.List;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.google.common.collect.Maps;
import com.focamacho.bvrtournaments.BVRTournaments;
import org.spongepowered.api.entity.living.player.Player;
import java.util.Map;
import com.focamacho.bvrtournaments.lib.EventListener;
import com.focamacho.bvrtournaments.command.Command;

public class CreateArenaCommand extends Command implements EventListener
{
    private final Map<Player, Arena> arenasBeingCreated;
    
    public CreateArenaCommand(final BVRTournaments plugin) {
        super(plugin);
        this.arenasBeingCreated = (Map<Player, Arena>)Maps.newHashMap();
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notPlayer, new Object[0]));
            return CommandResult.success();
        }
        if (this.arenasBeingCreated.containsKey(src)) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.alreadyCreating, new Object[0]));
            return CommandResult.success();
        }
        final boolean solo = args.getOne("type").orElse("duo").equalsIgnoreCase("solo");
        this.arenasBeingCreated.put((Player)src, new Arena(solo));
        src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.clickToSet, 1, 1));
        src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.typeToCancel, new Object[0]));
        return CommandResult.success();
    }
    
    @Listener
    public void onPlayerCommand(final SendCommandEvent event) {
        if (event.getSource() instanceof Player) {
            final Player player = (Player)event.getSource();
            if (!this.arenasBeingCreated.containsKey(player)) {
                return;
            }
            if (event.getCommand().equalsIgnoreCase("cancelar") || event.getCommand().equalsIgnoreCase("cancel")) {
                this.arenasBeingCreated.remove(player);
                player.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.canceled, new Object[0]));
                event.setCancelled(true);
            }
        }
    }
    
    @Listener
    public void onPlayerLeave(final ClientConnectionEvent.Disconnect event) {
        this.arenasBeingCreated.remove(event.getTargetEntity());
    }
    
    @Listener
    public void onRightClickBlock(final InteractBlockEvent.Secondary.MainHand event) {
        if (event.getSource() instanceof Player) {
            final Player player = (Player)event.getSource();
            final Arena arena = this.arenasBeingCreated.get(player);
            if (arena == null) {
                return;
            }
            final Optional<Location<World>> location = (Optional<Location<World>>)event.getTargetBlock().getLocation();
            if (!location.isPresent()) {
                return;
            }
            if ((arena.locations.size() == 1 && arena.solo) || (arena.locations.size() == 3 && !arena.solo)) {
                arena.locations.add(location.get().add(0.0, 1.0, 0.0));
                this.plugin.getDatabaseController().addArena(arena.locations);
                player.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.createdArena, new Object[0]));
                this.arenasBeingCreated.remove(player);
            }
            else {
                arena.locations.add(location.get().add(0.0, 1.0, 0.0));
                final int currentPos = arena.solo ? 1 : ((arena.locations.size() >= 2) ? (arena.locations.size() - 1) : (arena.locations.size() + 1));
                final int currentTeam = arena.solo ? 2 : ((arena.locations.size() >= 2) ? 2 : 1);
                player.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.posSet, new Object[0]));
                player.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.clickToSet, currentPos, currentTeam));
            }
            event.setCancelled(true);
            player.playSound(SoundType.of("minecraft:block.note.chime"), player.getPosition(), 1.0);
        }
    }
    
    private static class Arena
    {
        private final boolean solo;
        private List<Location<World>> locations;
        
        public Arena(final boolean solo) {
            this.locations = (List<Location<World>>)Lists.newArrayList();
            this.solo = solo;
        }
    }
}
