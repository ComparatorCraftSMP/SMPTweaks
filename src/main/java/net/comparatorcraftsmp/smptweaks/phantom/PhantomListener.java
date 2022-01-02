package net.comparatorcraftsmp.smptweaks.phantom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.entity.Phantom;

import net.comparatorcraftsmp.smptweaks.database.Access;

public class PhantomListener implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        if(event.getEntity() instanceof Phantom){
            Phantom phantom = (Phantom)event.getEntity();
            try{
                if(Access.getPhantomOverride(phantom.getSpawningEntity())){
                    event.setCancelled(true);
                }
            }catch(Exception e){
                System.out.println(e);
                System.out.println(e.getMessage());
            }
        }
    }
}
