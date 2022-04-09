package org.bebr0.bebr0zavr.events;

import org.bebr0.bebr0zavr.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class JumpEvent implements Listener {

    @EventHandler
    public void onJump(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().equals(new ItemStack(Game.jumpItem))) {
                Game game = Game.getGameOfAPlayer((Player) event.getWhoClicked());
                if (game != null) {
                    game.getDino().jump();
                }

                event.setCancelled(true);
            }
        }
    }
}
