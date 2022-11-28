// 
// Decompiled by Procyon v0.5.36
// 

package com.focamacho.bvrtournaments.database;

import java.util.UUID;
import org.spongepowered.api.world.extent.Extent;
import java.text.MessageFormat;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.Location;
import java.util.Collections;
import com.google.common.collect.Lists;
import java.util.List;
import java.sql.ResultSet;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.Sponge;
import com.focamacho.bvrtournaments.controller.DatabaseController;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import com.focamacho.bvrtournaments.config.Config;
import java.sql.SQLException;
import java.sql.Connection;
import com.focamacho.bvrtournaments.BVRTournaments;

public class DatabaseConnector
{
    private final BVRTournaments plugin;
    private final String tablePrefix;
    private final String tablePrefixArenas;
    private Connection connection;
    
    public DatabaseConnector(final BVRTournaments plugin) {
        this.plugin = plugin;
        this.tablePrefix = plugin.getConfig().mysql.mysqlTablePrefix;
        this.tablePrefixArenas = plugin.getConfig().mysql.mysqlTablePrefixArenas;
        final Config.MySQL config = plugin.getConfig().mysql;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception ex) {}
        if (this.connect()) {
            try {
                final String userTableSql = "CREATE TABLE IF NOT EXISTS " + this.tablePrefix + "accounts (username VARCHAR(30) NOT NULL PRIMARY KEY,points INT NOT NULL)";
                final String locationsTableSql = "CREATE TABLE IF NOT EXISTS " + this.tablePrefixArenas + "locations (id INTEGER PRIMARY KEY AUTO_INCREMENT,world VARCHAR(36),x DOUBLE,y DOUBLE,Z DOUBLE)";
                final String arenasTableSql = "CREATE TABLE IF NOT EXISTS " + this.tablePrefixArenas + "arenas (id INTEGER PRIMARY KEY AUTO_INCREMENT,first_location INT,second_location INT)";
                final String duoArenasTableSql = "CREATE TABLE IF NOT EXISTS " + this.tablePrefixArenas + "duo_arenas (id INTEGER PRIMARY KEY AUTO_INCREMENT,first_location INT,second_location INT,third_location INT,fourth_location INT)";
                this.connection.createStatement().execute(userTableSql);
                this.connection.createStatement().execute(locationsTableSql);
                this.connection.createStatement().execute(arenasTableSql);
                this.connection.createStatement().execute(duoArenasTableSql);
            }
            catch (SQLException e) {
                plugin.getLogger().error("Error creating database tables.");
                e.printStackTrace();
            }
            finally {
                this.disconnect();
            }
        }
    }
    
    public boolean connect() {
        try {
            final Config.MySQL config = this.plugin.getConfig().mysql;
            if (this.connection != null && !this.connection.isClosed()) {
                return true;
            }
            String databaseUrl = "jdbc:mysql://" + config.mysqlAddress + "/" + config.mysqlDatabase + "?characterEncoding=utf8";
            if (!config.advanced.mysqlConnectionUrl.isEmpty()) {
                databaseUrl = config.advanced.mysqlConnectionUrl;
            }
            this.connection = DriverManager.getConnection(databaseUrl, config.mysqlUser, config.mysqlPassword);
            return true;
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to connect to the database.");
            e.printStackTrace();
            return false;
        }
    }
    
    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to disconnect from the database.");
            e.printStackTrace();
        }
    }
    
    public void updateUser(final String username, final int points) {
        final String sql = "INSERT INTO " + this.tablePrefix + "accounts (username,points) VALUES(?,?) ON DUPLICATE KEY UPDATE points=?";
        try {
            if (this.connect()) {
                final PreparedStatement statement = this.connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setInt(2, points);
                statement.setInt(3, points);
                statement.executeUpdate();
            }
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to update a user in the database.");
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
    }
    
    public DatabaseController.RankedUser getUser(final String username) {
        final String sql = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER(ORDER BY points DESC) AS rank FROM " + this.tablePrefix + "accounts) AS t WHERE t.username = ?;";
        try {
            if (!this.connect()) {
                return null;
            }
            final PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setString(1, username);
            final ResultSet resultSet = statement.executeQuery();
            resultSet.beforeFirst();
            if (resultSet.next()) {
                return new DatabaseController.RankedUser(resultSet.getString("username"), resultSet.getInt("points"), resultSet.getInt("rank"));
            }
            if (!Sponge.getServer().getPlayer(username).isPresent() || !Sponge.getServer().getPlayer(username).get().isOnline()) {
                return null;
            }
            this.updateUser(username, this.plugin.getConfig().general.defaultPoints);
            return this.getUser(username);
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to get user's points from the database.");
            e.printStackTrace();
            return null;
        }
        finally {
            this.disconnect();
        }
    }
    
    public List<DatabaseController.RankedUser> getTopUsers(final int limit) {
        final String sql = "SELECT *, ROW_NUMBER() OVER(ORDER BY points DESC) AS rank FROM " + this.tablePrefix + "accounts LIMIT ?";
        try {
            if (this.connect()) {
                final List<DatabaseController.RankedUser> users = (List<DatabaseController.RankedUser>)Lists.newArrayList();
                final PreparedStatement statement = this.connection.prepareStatement(sql);
                statement.setInt(1, limit);
                final ResultSet resultSet = statement.executeQuery();
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    users.add(new DatabaseController.RankedUser(resultSet.getString("username"), resultSet.getInt("points"), resultSet.getInt("rank")));
                }
                return users;
            }
            return Collections.emptyList();
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to get top users from the database.");
            e.printStackTrace();
            return Collections.emptyList();
        }
        finally {
            this.disconnect();
        }
    }
    
    private Integer insertArena(final List<Location<World>> locations) {
        final String locationSql = "INSERT INTO " + this.tablePrefixArenas + "locations (world,x,y,z) VALUES(?, ?, ?, ?);";
        final String arenaSql = "INSERT INTO " + this.tablePrefixArenas + "arenas (first_location,second_location) VALUES(?, ?);";
        final String duoArenaSql = "INSERT INTO " + this.tablePrefixArenas + "duo_arenas (first_location,second_location,third_location,fourth_location) VALUES(?, ?, ?, ?);";
        try {
            if (this.connect()) {
                final PreparedStatement statementArena = this.connection.prepareStatement((locations.size() == 4) ? duoArenaSql : arenaSql, 1);
                for (int i = 1; i <= locations.size(); ++i) {
                    final Location<World> location = locations.get(i - 1);
                    if (location == null) {
                        statementArena.setNull(i, 4);
                    }
                    else {
                        final PreparedStatement locationStatement = this.connection.prepareStatement(locationSql, 1);
                        locationStatement.setString(1, ((World)location.getExtent()).getUniqueId().toString());
                        locationStatement.setDouble(2, location.getX());
                        locationStatement.setDouble(3, location.getY());
                        locationStatement.setDouble(4, location.getZ());
                        locationStatement.executeUpdate();
                        final ResultSet keys = locationStatement.getGeneratedKeys();
                        keys.next();
                        statementArena.setLong(i, keys.getLong(1));
                        keys.close();
                    }
                }
                statementArena.executeUpdate();
                final ResultSet keys2 = statementArena.getGeneratedKeys();
                keys2.next();
                final int arenaID = (int)keys2.getLong(1);
                keys2.close();
                return arenaID;
            }
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to insert a arena in the database.");
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
        return null;
    }
    
    public Integer insertArena(final Location<World> location1, final Location<World> location2) {
        return this.insertArena(Lists.newArrayList((Object[])new Location[] { location1, location2 }));
    }
    
    public Integer insertArena(final Location<World> location1, final Location<World> location2, final Location<World> location3, final Location<World> location4) {
        return this.insertArena(Lists.newArrayList((Object[])new Location[] { location1, location2, location3, location4 }));
    }
    
    private void removeArena(final String table, final int id) {
        final String sql = "SELECT * FROM " + table + " WHERE id = ?;";
        try {
            if (this.connect()) {
                final PreparedStatement statement = this.connection.prepareStatement(sql);
                statement.setInt(1, id);
                final ResultSet resultSet = statement.executeQuery();
                resultSet.beforeFirst();
                if (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getFetchSize(); ++i) {
                        final int location = resultSet.getInt(i);
                        final String removeLocation = "DELETE FROM " + this.tablePrefixArenas + "locations WHERE id = ?;";
                        final PreparedStatement locationQuery = this.connection.prepareStatement(removeLocation);
                        locationQuery.setInt(1, location);
                        locationQuery.executeUpdate();
                    }
                }
                final PreparedStatement delete = this.connection.prepareStatement("DELETE FROM " + table + " WHERE id = ?;");
                delete.setInt(1, id);
                delete.executeUpdate();
            }
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to remove a arena from the database.");
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
    }
    
    public void removeSoloArena(final int id) {
        this.removeArena(this.tablePrefixArenas + "arenas", id);
    }
    
    public void removeDuoArena(final int id) {
        this.removeArena(this.tablePrefixArenas + "duo_arenas", id);
    }
    
    public List<DatabaseController.Arena> getSoloArenas() {
        try {
            if (this.connect()) {
                final List<DatabaseController.Arena> arenas = (List<DatabaseController.Arena>)Lists.newArrayList();
                final ResultSet soloResultSet = this.connection.createStatement().executeQuery(MessageFormat.format("SELECT {0}arenas.id AS id, L1.world AS world1, L1.x AS x1, L1.y AS y1, L1.z AS z1, L2.world AS world2, L2.x AS x2, L2.y AS y2, L2.z AS z2 FROM {0}arenas INNER JOIN {0}locations AS L1 ON {0}arenas.first_location=L1.id INNER JOIN {0}locations AS L2 ON {0}arenas.second_location=L2.id;", this.tablePrefixArenas));
                soloResultSet.beforeFirst();
                while (soloResultSet.next()) {
                    try {
                        arenas.add(new DatabaseController.Arena(soloResultSet.getInt("id"), (Location<World>)new Location((Extent)Sponge.getServer().getWorld(UUID.fromString(soloResultSet.getString("world1"))).get(), soloResultSet.getDouble("x1"), soloResultSet.getDouble("y1"), soloResultSet.getDouble("z1")), (Location<World>)new Location((Extent)Sponge.getServer().getWorld(UUID.fromString(soloResultSet.getString("world2"))).get(), soloResultSet.getDouble("x2"), soloResultSet.getDouble("y2"), soloResultSet.getDouble("z2"))));
                    }
                    catch (Exception e) {
                        this.plugin.getLogger().error("Error trying to get an arena from the database.");
                        e.printStackTrace();
                    }
                }
                soloResultSet.close();
                return arenas;
            }
            return null;
        }
        catch (SQLException e2) {
            this.plugin.getLogger().error("Error trying to get arenas from the database.");
            e2.printStackTrace();
            return null;
        }
        finally {
            this.disconnect();
        }
    }
    
    public List<DatabaseController.Arena> getDuoArenas() {
        try {
            if (this.connect()) {
                final List<DatabaseController.Arena> arenas = (List<DatabaseController.Arena>)Lists.newArrayList();
                final ResultSet duoResultSet = this.connection.createStatement().executeQuery(MessageFormat.format("SELECT {0}duo_arenas.id AS id, L1.world AS world1, L1.x AS x1, L1.y AS y1, L1.z AS z1, L2.world AS world2, L2.x AS x2, L2.y AS y2, L2.z AS z2, L3.world AS world3, L3.x AS x3, L3.y AS y3, L3.z AS z3, L4.world AS world4, L4.x AS x4, L4.y AS y4, L4.z AS z4 FROM {0}duo_arenas INNER JOIN {0}locations AS L1 ON {0}duo_arenas.first_location=L1.id INNER JOIN {0}locations AS L2 ON {0}duo_arenas.second_location=L2.id INNER JOIN {0}locations AS L3 ON {0}duo_arenas.third_location=L3.id INNER JOIN {0}locations AS L4 ON {0}duo_arenas.fourth_location=L4.id;", this.tablePrefixArenas));
                duoResultSet.beforeFirst();
                while (duoResultSet.next()) {
                    try {
                        arenas.add(new DatabaseController.Arena(duoResultSet.getInt("id"), (Location<World>)new Location((Extent)Sponge.getServer().getWorld(UUID.fromString(duoResultSet.getString("world1"))).get(), duoResultSet.getDouble("x1"), duoResultSet.getDouble("y1"), duoResultSet.getDouble("z1")), (Location<World>)new Location((Extent)Sponge.getServer().getWorld(UUID.fromString(duoResultSet.getString("world2"))).get(), duoResultSet.getDouble("x2"), duoResultSet.getDouble("y2"), duoResultSet.getDouble("z2")), (Location<World>)new Location((Extent)Sponge.getServer().getWorld(UUID.fromString(duoResultSet.getString("world3"))).get(), duoResultSet.getDouble("x3"), duoResultSet.getDouble("y3"), duoResultSet.getDouble("z3")), (Location<World>)new Location((Extent)Sponge.getServer().getWorld(UUID.fromString(duoResultSet.getString("world4"))).get(), duoResultSet.getDouble("x4"), duoResultSet.getDouble("y4"), duoResultSet.getDouble("z4"))));
                    }
                    catch (Exception e) {
                        this.plugin.getLogger().error("Error trying to get an arena from the database.");
                        e.printStackTrace();
                    }
                }
                duoResultSet.close();
                return arenas;
            }
            return null;
        }
        catch (SQLException e2) {
            this.plugin.getLogger().error("Error trying to get arenas from the database.");
            e2.printStackTrace();
            return null;
        }
        finally {
            this.disconnect();
        }
    }
    
    public void resetPoints() {
        try {
            if (this.connect()) {
                final String dropTableSql = "DROP TABLE IF EXISTS " + this.tablePrefix + "accounts";
                final String createTableSql = "CREATE TABLE IF NOT EXISTS " + this.tablePrefix + "accounts (username VARCHAR(30) NOT NULL PRIMARY KEY,points INT NOT NULL)";
                this.connection.createStatement().execute(dropTableSql);
                this.connection.createStatement().execute(createTableSql);
            }
        }
        catch (SQLException e) {
            this.plugin.getLogger().error("Error trying to reset all user data on the database.");
            e.printStackTrace();
        }
        finally {
            this.disconnect();
        }
    }
}
