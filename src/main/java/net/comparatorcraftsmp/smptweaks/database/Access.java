package net.comparatorcraftsmp.smptweaks.database;

import java.io.File;
import java.sql.*;

import net.comparatorcraftsmp.smptweaks.TweaksPlugin;

public class Access{
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
            plugin.disable();
        }
        System.out.println("Opened database successfully");
    }
}
