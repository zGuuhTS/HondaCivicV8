// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.task;

import net.luckperms.api.model.group.Group;
import com.focamacho.bvrtournaments.controller.DatabaseController;
import com.focamacho.bvrtournaments.config.Config;
import java.util.Collection;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import com.focamacho.bvrtournaments.BVRTournaments;
import org.spongepowered.api.entity.living.player.Player;

public class CheckEloTask
{
    private final Player player;
    private final BVRTournaments plugin;
    
    public void check() {
        final User user = this.plugin.getLuckPermsAPI().getUserManager().getUser(this.player.getUniqueId());
        if (user == null) {
            return;
        }
        final Integer points;
        String elo;
        final User user2;
        Collection groups;
        Config.General.Elo[] elos;
        int length;
        int i = 0;
        Config.General.Elo cElo;
        final String anotherString;
        final User user3;
        this.plugin.getDatabaseController().getUser(this.player.getName()).whenComplete((rankedUser, throwable) -> {
            points = rankedUser.getPoints();
            if (points == null) {
                this.plugin.getDatabaseController().setPoints(this.player.getName(), 0);
                return;
            }
            else {
                elo = this.plugin.getConfigController().getEloForPoints(points);
                groups = user2.getInheritedGroups(QueryOptions.defaultContextualOptions());
                groups.forEach(group -> {
                    elos = this.plugin.getConfig().general.elos;
                    for (length = elos.length; i < length; ++i) {
                        cElo = elos[i];
                        if (cElo.group.equalsIgnoreCase(group.getName()) && !group.getName().equalsIgnoreCase(anotherString)) {
                            user3.data().remove((Node)InheritanceNode.builder(group).build());
                        }
                    }
                    return;
                });
                if (elo != null && groups.stream().noneMatch(group -> group.getName().equalsIgnoreCase(elo))) {
                    user2.data().add((Node)InheritanceNode.builder(elo).build());
                }
                return;
            }
        });
        this.plugin.getLuckPermsAPI().getUserManager().saveUser(user);
    }
    
    public CheckEloTask(final Player player, final BVRTournaments plugin) {
        this.player = player;
        this.plugin = plugin;
    }
}
