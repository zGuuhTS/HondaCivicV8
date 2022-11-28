// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.command.battles;

import lombok.NonNull;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import java.util.Optional;
import com.focamacho.bvrtournaments.controller.BetController;
import net.minecraft.entity.player.EntityPlayerMP;
import com.focamacho.bvrtournaments.util.TextUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.CommandSource;
import com.focamacho.bvrtournaments.BVRTournaments;
import com.focamacho.bvrtournaments.command.Command;

public class BetCommand extends Command
{
    public BetCommand(final BVRTournaments plugin) {
        super(plugin);
    }
    
    @NonnullByDefault
    @NonNull
    public CommandResult execute(final CommandSource src, final CommandContext args) {
        if (!(src instanceof Player)) {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.notPlayer, new Object[0]));
            return CommandResult.success();
        }
        final Player player = args.getOne("player").get();
        final double amount = args.getOne("amount").get();
        final Optional<BetController.Bet> bet = this.plugin.getBetController().getBet((EntityPlayerMP)player);
        if (bet.isPresent()) {
            bet.get().bet((EntityPlayerMP)src, (EntityPlayerMP)player, amount);
        }
        else {
            src.sendMessage(TextUtils.formatText(this.plugin.getLang().commands.invalidPlayer, new Object[0]));
        }
        return CommandResult.success();
    }
}
