package org.bebr0.bebr0zavr;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Bird {

    public static final Material birdMaterial = Material.ELYTRA;
    public static final int initialPos1 = 26;
    public static final int initialPos2 = 35;

    private int pos;
    private Game game;

    private final int initialPos;

    public Bird(Game game) {
        this.game = game;
        Random random = new Random();
        if (random.nextFloat() > 0.5)
            pos = initialPos1; // 17
        else
            pos = initialPos2; // 26

        initialPos = pos;

        new BukkitRunnable(){
            @Override
            public void run(){
                game.getField().setItem(pos, new ItemStack(birdMaterial));
            }
        }.runTask(Plugin.getPlugin(Plugin.class));

    }

    public void move(){
        game.getField().setItem(pos, new ItemStack(Material.AIR));
        pos -= 1;

        if (game.getField().getItem(pos) != null)
            game.stopGame();
        if ((initialPos == initialPos1 && pos > initialPos1 - 9) || (initialPos == initialPos2 && pos > initialPos2 - 9))
            game.getField().setItem(pos, new ItemStack(birdMaterial));
        else
            game.getBirdList().remove(this);
    }

    public int getPos() {
        return pos;
    }

    public int getInitialPos() {
        return initialPos;
    }
}
