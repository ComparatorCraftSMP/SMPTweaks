package net.comparatorcraftsmp.smptweaks.phantom;

import java.util.UUID;

import net.comparatorcraftsmp.smptweaks.database.Access;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class PhantomToggle implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player)sender;
            UUID uuid = player.identity().uuid();
            if(args.length == 0){
                try {
                    boolean currentOverride = Access.getPhantomOverride(uuid);
                    Access.setPhantomOverride(uuid, !currentOverride);
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Something went wrong. Tell an admin to check the console"));
                    System.out.println(e);
                    System.out.println(e.getMessage());
                }
            }
            else if(args.length == 1){
                try{
                    switch(args[0].toLowerCase()){
                        case "true":
                            Access.setPhantomOverride(uuid, true);
                            break;
                        case "false":
                            Access.setPhantomOverride(uuid, false);
                            break;
                        default:
                            return false;
                    }
                }catch(Exception e){
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Something went wrong. Tell an admin to check the console"));
                    System.out.println(e);
                    System.out.println(e.getMessage());
                }
            }
            try{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Phantom Overide now set to " + Access.getPhantomOverride(uuid)));
            }catch(Exception e){
                System.out.println(e);
                System.out.println(e.getMessage());
            }
        }
        return true;
    }
}
