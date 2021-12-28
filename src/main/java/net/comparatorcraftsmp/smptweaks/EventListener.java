package net.comparatorcraftsmp.smptweaks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntitySpawnEvent;

import org.bukkit.entity.Phantom;

public class EventListener implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Phantom){
            System.out.println("Phantom Spawn");
        }
    }
}
