// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.points;

import org.spongepowered.api.entity.living.player.Player;
import com.focamacho.bvrtournaments.controller.DatabaseController;
import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import net.objecthunter.exp4j.Expression;
import org.spongepowered.api.Sponge;
import net.objecthunter.exp4j.ExpressionBuilder;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class UpdatePointsCommand extends Command
{
    public UpdatePointsCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        final String playerWin = args.getOne("playerWin").get();
        final String playerDefeat = args.getOne("playerDefeat").get();
        final Integer minWinPoints = args.getOne("minWinPoints").orElse(null);
        final Integer minLosePoints = args.getOne("minLosePoints").orElse(null);
        final String player2;
        Integer pointsWin;
        Integer pointsDefeat;
        Expression expressionWin;
        int resultWin;
        Expression expressionDefeat;
        int resultDefeat;
        final String player3;
        Optional onlinePlayerWin;
        final String player4;
        Optional onlinePlayerDefeat;
        int finalResultWin;
        int finalResultDefeat;
        final Integer n;
        int finalResultWin2;
        final Integer n2;
        int finalResultDefeat2;
        int wonPoints;
        int lostPoints;
        final int i;
        final int j;
        this.plugin.getDatabaseController().getUser(playerWin).whenComplete((userWin, throwable) -> this.plugin.getDatabaseController().getUser(player2).whenComplete((userDefeat, throwable2) -> {
            if (userWin == null || userDefeat == null) {
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.playerNotFound, new Object[0]));
            }
            else {
                pointsWin = userWin.getPoints();
                pointsDefeat = userDefeat.getPoints();
                expressionWin = new ExpressionBuilder(this.plugin.getConfig().general.updateWinFormula).variables("x", "y").build().setVariable("x", pointsWin).setVariable("y", pointsDefeat);
                resultWin = (int)expressionWin.evaluate();
                expressionDefeat = new ExpressionBuilder(this.plugin.getConfig().general.updateDefeatFormula).variables("x", "y").build().setVariable("x", pointsDefeat).setVariable("y", pointsWin);
                resultDefeat = (int)expressionDefeat.evaluate();
                if (resultWin < 0) {
                    resultWin = 0;
                }
                if (resultDefeat < 0) {
                    resultDefeat = 0;
                }
                onlinePlayerWin = Sponge.getServer().getPlayer(player3);
                onlinePlayerDefeat = Sponge.getServer().getPlayer(player4);
                finalResultWin = resultWin;
                finalResultDefeat = resultDefeat;
                if (n != null && finalResultWin - pointsWin < n) {
                    finalResultWin2 = finalResultWin - (finalResultWin - pointsWin);
                    finalResultWin = finalResultWin2 + n;
                }
                if (n2 != null && pointsDefeat - finalResultDefeat < n2) {
                    finalResultDefeat2 = finalResultDefeat + (pointsDefeat - finalResultDefeat);
                    finalResultDefeat = finalResultDefeat2 - n2;
                }
                wonPoints = finalResultWin - pointsWin;
                lostPoints = pointsDefeat - finalResultDefeat;
                onlinePlayerWin.ifPresent(player -> {
                    if (this.plugin.getConfig().general.pointsWarnMessage) {
                        TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.receivedPointsMessage, i);
                    }
                    return;
                });
                onlinePlayerDefeat.ifPresent(player -> {
                    if (this.plugin.getConfig().general.pointsWarnMessage) {
                        TextUtils.sendFormattedMessage(player, this.plugin.getLang().geral.removedPointsMessage, j);
                    }
                    return;
                });
                this.plugin.getDatabaseController().setPoints(player3, finalResultWin);
                this.plugin.getDatabaseController().setPoints(player4, finalResultDefeat);
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsUpdateWin, player3, finalResultWin, wonPoints));
                src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.pointsUpdateDefeat, player4, finalResultDefeat, lostPoints));
            }
        }));
        return CommandResult.success();
    }
}
