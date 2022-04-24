package org.bebr0.bebr0zavr.events;

import org.bebr0.bebr0zavr.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SneakEvent implements Listener {

    @EventHandler
    public void onSneak(InventoryClickEvent event){
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getType() == Game.sneakItem) {
                Game game = Game.getGameOfAPlayer((Player) event.getWhoClicked());
                if (game != null) {
                    game.getDino().sneak();
                }

                event.setCancelled(true);
            }
        }
    }
}
