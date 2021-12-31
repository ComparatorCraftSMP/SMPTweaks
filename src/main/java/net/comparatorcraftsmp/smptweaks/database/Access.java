package net.comparatorcraftsmp.smptweaks.database;

import java.io.File;
import java.sql.*;

import net.comparatorcraftsmp.smptweaks.TweaksPlugin;

public class Access{
    static final int currentVersion = 1;
    
    static TweaksPlugin plugin;
    static File databasePath;

    static Connection c = null;
    public static void init(TweaksPlugin _plugin, File _databasePath){
        plugin = _plugin;
        databasePath = _databasePath;
        
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(1);
        }
        try (Statement stmt = c.createStatement()) {
            //create database and get current version from it
            stmt.execute("CREATE TABLE IF NOT EXISTS migration(version INTEGER);");
            ResultSet result = stmt.executeQuery("SELECT MAX(version) FROM migration");
            int version = result.getInt("MAX(version)");
            migrate(version);
            System.out.println(version);
        }catch(Exception e){
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Opened database successfully");
    }

    static void migrate(int version){
        if(version == 0){
            executeTransaction(new String[]{"CREATE TABLE players(" +
              "uuid TEXT PRIMARY KEY, "+
              "phantom_override INTEGER);",
              "INSERT INTO migration VALUES (1)"});
            version = 1;
        } 
    }

    static void executeTransaction(String[] statements){
        try{
            c.setAutoCommit(false);
            for(String i : statements){
                try(Statement stmt = c.createStatement()){
                    stmt.execute(i);
                }catch(Exception e){
                    System.out.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(1);
                }
            }
            c.commit();
        }catch(Exception e){
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
    }
}
