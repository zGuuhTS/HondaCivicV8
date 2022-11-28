// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.controller;

import java.util.Collections;
import java.util.Collection;
import java.util.Optional;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.Sponge;
import java.util.concurrent.CompletableFuture;
import com.focamacho.bvrtournaments.task.CheckEloTask;
import org.spongepowered.api.entity.living.player.Player;
import java.util.concurrent.Executors;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.concurrent.ExecutorService;
import java.util.List;
import java.util.Map;
import com.focamacho.bvrtournaments.database.DatabaseConnector;
import com.focamacho.bvrtournaments.BVRTournaments;

public class DatabaseController
{
    private final BVRTournaments plugin;
    private DatabaseConnector connector;
    private final Map<Integer, Arena> soloArenas;
    private final Map<Integer, Arena> duoArenas;
    private final List<RankedUser> cachedUsers;
    private final ExecutorService databaseThread;
    
    public DatabaseController(final BVRTournaments plugin) {
        this.soloArenas = (Map<Integer, Arena>)Maps.newHashMap();
        this.duoArenas = (Map<Integer, Arena>)Maps.newHashMap();
        this.cachedUsers = (List<RankedUser>)Lists.newArrayList();
        this.databaseThread = Executors.newSingleThreadExecutor();
        this.plugin = plugin;
        this.soloArenas.clear();
        this.duoArenas.clear();
        this.connector = new DatabaseConnector(plugin);
        final Arena arena2;
        this.connector.getSoloArenas().forEach(arena -> arena2 = this.soloArenas.put(arena.id, arena));
        final Arena arena3;
        this.connector.getDuoArenas().forEach(arena -> arena3 = this.duoArenas.put(arena.id, arena));
    }
    
    public void setPoints(final Player player, final int points) {
        this.setPoints(player.getName(), points);
        new CheckEloTask(player, this.plugin).check();
    }
    
    public void setPoints(final String player, final int points) {
        this.databaseThread.execute(() -> this.connector.updateUser(player, points));
    }
    
    public CompletableFuture<RankedUser> getUser(final String player) {
        final CompletableFuture<RankedUser> future = new CompletableFuture<RankedUser>();
        final RankedUser user;
        final CompletableFuture<RankedUser> completableFuture;
        this.databaseThread.execute(() -> {
            user = this.connector.getUser(player);
            if (user != null) {
                this.cachedUsers.removeIf(cachedUser -> cachedUser.getName().equalsIgnoreCase(player));
                this.cachedUsers.add(user);
            }
            completableFuture.complete(user);
            return;
        });
        return future;
    }
    
    public RankedUser getCachedUser(final String player) {
        return this.cachedUsers.stream().filter(user -> user.name.equalsIgnoreCase(player)).findFirst().orElse(new RankedUser(player, 0, 0));
    }
    
    public CompletableFuture<List<RankedUser>> getTopUsers(final int limit) {
        final CompletableFuture<List<RankedUser>> future = new CompletableFuture<List<RankedUser>>();
        this.databaseThread.execute(() -> future.complete(this.connector.getTopUsers(limit)));
        return future;
    }
    
    public void reset() {
        this.databaseThread.execute(() -> this.connector.resetPoints());
        Sponge.getServer().getOnlinePlayers().forEach(player -> new CheckEloTask(player, this.plugin).check());
    }
    
    public void addArena(final Location<World> pos1, final Location<World> pos2) {
        final int arena = this.connector.insertArena(pos1, pos2);
        this.soloArenas.put(arena, new Arena(arena, pos1, pos2));
    }
    
    public void addArena(final Location<World> pos1, final Location<World> pos2, final Location<World> pos3, final Location<World> pos4) {
        final int arena = this.connector.insertArena(pos1, pos2, pos3, pos4);
        this.duoArenas.put(arena, new Arena(arena, pos1, pos2, pos3, pos4));
    }
    
    public void addArena(final List<Location<World>> locations) {
        if (locations.size() == 4) {
            this.addArena(locations.get(0), locations.get(1), locations.get(2), locations.get(3));
        }
        else if (locations.size() == 2) {
            this.addArena(locations.get(0), locations.get(1));
        }
    }
    
    public void removeSoloArena(final int id) {
        this.soloArenas.remove(id);
        this.databaseThread.execute(() -> this.connector.removeSoloArena(id));
    }
    
    public void removeDuoArena(final int id) {
        this.duoArenas.remove(id);
        this.databaseThread.execute(() -> this.connector.removeDuoArena(id));
    }
    
    public Optional<Arena> getSoloArenaById(final int id) {
        return this.soloArenas.containsKey(id) ? Optional.of(this.soloArenas.get(id)) : Optional.empty();
    }
    
    public Optional<Arena> getDuoArenaById(final int id) {
        return this.duoArenas.containsKey(id) ? Optional.of(this.duoArenas.get(id)) : Optional.empty();
    }
    
    public Collection<Arena> getSoloArenas() {
        return this.soloArenas.values();
    }
    
    public Collection<Arena> getDuoArenas() {
        return this.duoArenas.values();
    }
    
    public static class RankedUser
    {
        private final String name;
        private final Integer points;
        private final Integer rank;
        
        public RankedUser(final String name, final Integer points, final Integer rank) {
            this.name = name;
            this.points = points;
            this.rank = rank;
        }
        
        public String getName() {
            return this.name;
        }
        
        public Integer getPoints() {
            return this.points;
        }
        
        public Integer getRank() {
            return this.rank;
        }
    }
    
    public static class Arena
    {
        private final int id;
        private final List<Location<World>> locations;
        
        public Arena(final int id, final Location<World> location1, final Location<World> location2) {
            this.locations = (List<Location<World>>)Lists.newArrayList();
            this.id = id;
            Collections.addAll((Collection<? super Location>)this.locations, new Location[] { location1, location2 });
        }
        
        public Arena(final int id, final Location<World> location1, final Location<World> location2, final Location<World> location3, final Location<World> location4) {
            this.locations = (List<Location<World>>)Lists.newArrayList();
            this.id = id;
            Collections.addAll((Collection<? super Location>)this.locations, new Location[] { location1, location2, location3, location4 });
        }
        
        public int getId() {
            return this.id;
        }
        
        public List<Location<World>> getLocations() {
            return this.locations;
        }
    }
}
