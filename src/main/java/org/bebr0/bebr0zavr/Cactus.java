package org.bebr0.bebr0zavr;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Cactus {

    public static final Material cactusMaterial = Material.CACTUS;
    public static final int initialPos = 44;

    private int pos;
    private final Game game;

    public Cactus(Game game) {
        this.game = game;
        pos = initialPos;
        game.getField().setItem(pos, new ItemStack(cactusMaterial));
    }

    public void move(){
        game.getField().setItem(pos, new ItemStack(Material.AIR));
        pos -= 1;

        if (game.getField().getItem(pos) != null)
            game.stopGame();
        if (pos > initialPos - 9)
            game.getField().setItem(pos, new ItemStack(cactusMaterial));
        else
            game.getCactusList().remove(this);
    }

    public int getPos() {
        return pos;
    }
}
