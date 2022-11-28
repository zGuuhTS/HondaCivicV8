// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.points;

import com.focamacho.bvrtournaments.controller.DatabaseController;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class PointsCommand extends Command
{
    public PointsCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        if (!(src instanceof Player) && !args.getOne("player").isPresent()) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notPlayer, new Object[0]));
            return CommandResult.success();
        }
        final String player = args.getOne("player").isPresent() ? args.getOne("player").get() : ((Player)src).getName();
        Integer points;
        String elo;
        final Object o;
        this.plugin.getDatabaseController().getUser(player).whenComplete((user, throwable) -> {
            if (user == null) {
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.playerNotFound, new Object[0]));
                return;
            }
            else {
                points = user.getPoints();
                elo = this.plugin.getConfigController().getEloNameForPoints(points);
                if (args.getOne("player").isPresent()) {
                    if (!elo.equalsIgnoreCase(this.plugin.getLang().others.missingElo)) {
                        src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsOtherMessage, o, points, elo));
                    }
                    else {
                        src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsOtherNoEloMessage, o, points));
                    }
                }
                else if (!elo.equalsIgnoreCase(this.plugin.getLang().others.missingElo)) {
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsMessage, points, elo));
                }
                else {
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsNoEloMessage, points));
                }
                return;
            }
        });
        return CommandResult.success();
    }
}
