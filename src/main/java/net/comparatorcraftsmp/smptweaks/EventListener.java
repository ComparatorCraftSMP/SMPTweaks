package net.comparatorcraftsmp.smptweaks;

import net.comparatorcraftsmp.smptweaks.database.Access;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.entity.Player;

public class EventListener implements Listener {
    /*
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Phantom){
            System.out.println("Phantom Spawn");
        }
    }
    */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Access.addPlayer(event.getPlayer().identity().uuid());
    }
}
