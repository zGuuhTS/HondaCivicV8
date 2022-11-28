// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.points;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class TopPointsCommand extends Command
{
    public TopPointsCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notPlayer, new Object[0]));
            return CommandResult.success();
        }
        final Player player = (Player)src;
        this.plugin.getMenuController().getTopPointsMenu(player).open(player);
        return CommandResult.success();
    }
}
