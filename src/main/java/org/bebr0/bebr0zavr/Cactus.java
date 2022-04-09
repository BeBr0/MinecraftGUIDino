package org.bebr0.bebr0zavr;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Cactus {

    public static final Material cactusMaterial = Material.CACTUS;

    private int pos;
    private Game game;

    public Cactus(Game game) {
        this.game = game;
        pos = 44;
        game.getField().setItem(pos, new ItemStack(cactusMaterial));
    }

    public void remove(){
        game.getField().setItem(pos, new ItemStack(Material.AIR));
        game.getCactusList().remove(this);
    }

    public void move(){
        game.getField().setItem(pos, new ItemStack(Material.AIR));
        pos -= 1;

        if (game.getField().getItem(pos) != null)
            game.stopGame();
        if (pos > 35)
            game.getField().setItem(pos, new ItemStack(cactusMaterial));
        else
            game.getCactusList().remove(this);
    }

    public int getPos() {
        return pos;
    }
}
