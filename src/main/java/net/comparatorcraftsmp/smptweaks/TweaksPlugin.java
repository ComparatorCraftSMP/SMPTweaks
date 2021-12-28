package net.comparatorcraftsmp.smptweaks;

import java.io.File;

import net.comparatorcraftsmp.smptweaks.database.Access;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

public class TweaksPlugin extends JavaPlugin {
    private final String defaultPlayerDatabase = "data.db";

    private String databaseName;
    private File databaseLocation;
    public void onEnable() {
        //Load or create config file 
        saveDefaultConfig();
        //Regester Events and commands
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        //initalize the database
        if(getConfig().getString("custom-database-location", "false").equals("true")){
            databaseName = getConfig().getString("database-location", "data.db");
        }else{
            databaseName = defaultPlayerDatabase;
        }
        databaseLocation = new File(getDataFolder(), databaseName);
        Access.init(this, databaseLocation);
    }
    public void disable(){
        setEnabled(false);
    }
}
