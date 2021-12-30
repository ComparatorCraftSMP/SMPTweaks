package net.comparatorcraftsmp.smptweaks;
import java.io.File;

import net.comparatorcraftsmp.smptweaks.database.Access;        

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

public class TweaksPlugin extends JavaPlugin {
    public void onEnable() {
        //Load or create config file 
        saveDefaultConfig();
        //Regester Events and commands
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        //initalize the database
        String databaseName = getConfig().getString("database-location", "data.sqlite3");
        File databaseLocation = new File(getDataFolder(), databaseName);
        Access.init(this, databaseLocation);
    }
}
