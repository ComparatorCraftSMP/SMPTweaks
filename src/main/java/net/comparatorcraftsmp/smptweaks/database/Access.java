package net.comparatorcraftsmp.smptweaks.database;

import java.io.File;
import java.sql.*;
import java.util.UUID;

import net.comparatorcraftsmp.smptweaks.TweaksPlugin;
import org.bukkit.Bukkit;

public class Access{
    static final int currentVersion = 1;
    
    static TweaksPlugin plugin;
    static File databasePath;

    static boolean panic = false;

    static Connection c = null;
    public static void init(TweaksPlugin _plugin, File _databasePath) throws ClassNotFoundException, SQLException{
        plugin = _plugin;
        databasePath = _databasePath;
        
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        Statement stmt = c.createStatement();
        //create database and get current version from it
        stmt.execute("CREATE TABLE IF NOT EXISTS migration(version INTEGER);");
        ResultSet result = stmt.executeQuery("SELECT MAX(version) FROM migration;");
        int version = result.getInt("MAX(version)");
        migrate(version);
    }

    public static void addPlayer(UUID uuid){
        String insertString = "INSERT OR IGNORE INTO players(uuid) VALUES(?);";
        try(PreparedStatement stmt = c.prepareStatement(insertString)){
            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    static void migrate(int version) throws SQLException{
        if(version == 0){
            executeTransaction(new String[]{"CREATE TABLE players(" +
              "uuid TEXT PRIMARY KEY, "+
              "phantom_override INTEGER DEFAULT 0," +
              "UNIQUE(uuid)" +
              ");",
              "INSERT INTO migration VALUES (1);"});
           version = 1; 
        } 
    }

    static void executeTransaction(String[] statements) throws SQLException{
        c.setAutoCommit(false);
        for(String i : statements){
            Statement stmt = c.createStatement();
            stmt.execute(i);
        }
        c.commit();
        c.setAutoCommit(true);
    }

    public static void setPhantomOverride(UUID uuid, boolean newValue) throws SQLException{
        String updateString = "UPDATE players SET phantom_override = ? WHERE uuid = ?;";
        PreparedStatement stmt = c.prepareStatement(updateString);
        stmt.setInt(1, newValue ? 1 : 0);
        stmt.setString(2, uuid.toString());
        stmt.executeUpdate();
    }

    public static boolean getPhantomOverride(UUID uuid) throws SQLException{
        String queryString = "SELECT phantom_override FROM players WHERE uuid = ?;";
        PreparedStatement stmt = c.prepareStatement(queryString);
        stmt.setString(1, uuid.toString());
        ResultSet result = stmt.executeQuery();
        return result.getInt("phantom_override") == 1;
    }
}
