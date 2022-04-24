package org.bebr0.bebr0zavr;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Commands implements CommandExecutor {

    public Commands(Plugin plugin){
        plugin.getCommand("bebr0zavr").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            return true;
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("startGame")){
            Game game = new Game(player);
            
            player.sendMessage("Успех!");
            return true;
        }
        return false;
    }
}
