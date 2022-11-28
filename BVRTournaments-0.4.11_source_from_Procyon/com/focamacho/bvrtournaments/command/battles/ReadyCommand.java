// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.battles;

import com.google.common.collect.Maps;
import java.util.Optional;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.battle.Battle;
import java.util.Map;
import com.focamacho.bvrtournaments.command.Command;

public class ReadyCommand extends Command
{
    private static final Map<Battle, Long> waitingReady;
    
    public ReadyCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notPlayer, new Object[0]));
            return CommandResult.success();
        }
        final EntityPlayerMP source = (EntityPlayerMP)src;
        Battle battle = null;
        ReadyCommand.waitingReady.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue() >= 0L);
        for (final Map.Entry<Battle, Long> entry2 : ReadyCommand.waitingReady.entrySet()) {
            final Battle bt = entry2.getKey();
            if (bt.team1.contains(source) || bt.team2.contains(source)) {
                battle = bt;
                break;
            }
        }
        if (battle == null) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notInBattle, new Object[0]));
        }
        else {
            if (battle.contains(source) && Boolean.TRUE.equals(battle.getReadyPlayers().put(source, true))) {
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.alreadyReady, new Object[0]));
                return CommandResult.success();
            }
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.ready, new Object[0]));
            if (battle.isEveryoneReady()) {
                ReadyCommand.waitingReady.remove(battle);
                battle.startBattle();
            }
        }
        return CommandResult.success();
    }
    
    public static void waitReady(final Battle battle, final BVRTournaments plugin) {
        ReadyCommand.waitingReady.put(battle, System.currentTimeMillis() + plugin.getConfig().battles.readyMaxTime * 1000L);
    }
    
    public static boolean checkArena(final int id) {
        ReadyCommand.waitingReady.entrySet().removeIf(entry -> System.currentTimeMillis() - entry.getValue() >= 0L);
        return ReadyCommand.waitingReady.keySet().stream().anyMatch(battle -> battle.arena == id);
    }
    
    public static Optional<Battle> isWaiting(final EntityPlayerMP player) {
        for (final Map.Entry<Battle, Long> entry : ReadyCommand.waitingReady.entrySet()) {
            if (System.currentTimeMillis() - entry.getValue() >= 0L) {
                continue;
            }
            if (entry.getKey().team1.contains(player) || entry.getKey().team2.contains(player)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }
    
    static {
        waitingReady = Maps.newHashMap();
    }
}
