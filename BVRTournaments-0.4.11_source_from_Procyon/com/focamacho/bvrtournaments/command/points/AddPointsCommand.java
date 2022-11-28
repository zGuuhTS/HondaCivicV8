// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.points;

import com.focamacho.bvrtournaments.controller.DatabaseController;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.Sponge;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class AddPointsCommand extends Command
{
    public AddPointsCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final String player = args.getOne("player").get();
        Integer points;
        Integer pointsToAdd;
        final String player2;
        Optional onlinePlayer;
        this.plugin.getDatabaseController().getUser(player).whenComplete((user, throwable) -> {
            if (user == null) {
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.playerNotFound, new Object[0]));
                return;
            }
            else {
                points = user.getPoints();
                pointsToAdd = args.getOne("points").orElse(-1);
                if (pointsToAdd <= 0) {
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.invalidValue, new Object[0]));
                    return;
                }
                else {
                    onlinePlayer = Sponge.getServer().getPlayer(player2);
                    if (onlinePlayer.isPresent()) {
                        this.plugin.getDatabaseController().setPoints(onlinePlayer.get(), points + pointsToAdd);
                        if (this.plugin.getConfig().general.pointsWarnMessage) {
                            TextUtils.sendFormattedMessage(onlinePlayer.get(), this.plugin.getLang().geral.receivedPointsMessage, pointsToAdd);
                        }
                    }
                    else {
                        this.plugin.getDatabaseController().setPoints(player2, points + pointsToAdd);
                    }
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsAdd, pointsToAdd));
                    return;
                }
            }
        });
        return CommandResult.success();
    }
}
