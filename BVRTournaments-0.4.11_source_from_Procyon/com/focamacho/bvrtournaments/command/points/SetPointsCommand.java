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

public class SetPointsCommand extends Command
{
    public SetPointsCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final String player = args.getOne("player").get();
        Integer pointsToSet;
        final String player2;
        Optional onlinePlayer;
        this.plugin.getDatabaseController().getUser(player).whenComplete((user, throwable) -> {
            if (user == null) {
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.playerNotFound, new Object[0]));
                return;
            }
            else {
                pointsToSet = args.getOne("points").orElse(-1);
                if (pointsToSet < 0) {
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.invalidValue, new Object[0]));
                    return;
                }
                else {
                    onlinePlayer = Sponge.getServer().getPlayer(player2);
                    if (onlinePlayer.isPresent()) {
                        this.plugin.getDatabaseController().setPoints(onlinePlayer.get(), pointsToSet);
                        if (this.plugin.getConfig().general.pointsWarnMessage) {
                            TextUtils.sendFormattedMessage(onlinePlayer.get(), this.plugin.getLang().geral.setPointsMessage, pointsToSet);
                        }
                    }
                    else {
                        this.plugin.getDatabaseController().setPoints(player2, pointsToSet);
                    }
                    src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsSet, pointsToSet));
                    return;
                }
            }
        });
        return CommandResult.success();
    }
}
