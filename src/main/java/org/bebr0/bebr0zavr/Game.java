package org.bebr0.bebr0zavr;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private static final List<Game> gameList = new ArrayList<>();

    public static final Material jumpItem = Material.RED_CONCRETE;
    public static final Material sneakItem = Material.GREEN_CONCRETE;
    public static final Material dinoMaterialTop = Material.IRON_HELMET;
    public static final Material dinoMaterialBottom = Material.IRON_BOOTS;

    public static Game getGameOfAPlayer(Player player){
        for (Game game: gameList){
            if (game.player == player)
                return game;
        }

        return null;
    }

    private Inventory field;
    private Player player;

    private List<Cactus> cactusList = new ArrayList<>();

    private final int fieldSize = 54;
    private final Material ground = Material.GRASS_BLOCK;

    private final Dino dino;

    private int ticksPerSecond = 5;

    public Game(Player player){
        field = Bukkit.createInventory(null, fieldSize);
        this.player = player;
        prepareField();

        dino = new Dino(this);

        this.player.openInventory(field);
        gameList.add(this);

        startGameCycle();
    }

    private void startGameCycle(){
        new Thread(() -> {
            while (true) {
                generateCactus();

                for (int i = 0; i < cactusList.size(); i++) {
                    cactusList.get(i).move();
                }

                try {
                    Thread.sleep(1000 / ticksPerSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void generateCactus(){
        Random random = new Random();
        if (random.nextFloat() < (float) 2/9){
            cactusList.add(new Cactus(this));
        }
    }

    private void prepareField() {
        for (int i = 45; i < 54; i++){
            field.setItem(i, new ItemStack(ground));
        }

        field.setItem(37, new ItemStack(dinoMaterialBottom));
        field.setItem(28, new ItemStack(dinoMaterialTop));

        player.getInventory().setItem(9, new ItemStack(jumpItem));
        player.getInventory().setItem(10, new ItemStack(sneakItem));
    }


    public void moveOneFrame(boolean isUp){
        field.setItem(dino.getTopPos(), new ItemStack(Material.AIR));
        field.setItem(dino.getBottomPos(), new ItemStack(Material.AIR));
        if (isUp) {
            dino.setTopPos(dino.getTopPos() - 9);
            dino.setBottomPos(dino.getBottomPos() - 9);;
        }
        else {
            dino.setTopPos(dino.getTopPos() + 9);
            dino.setBottomPos(dino.getBottomPos() + 9);
        }
        field.setItem(dino.getTopPos(), new ItemStack(dinoMaterialTop));
        field.setItem(dino.getBottomPos(), new ItemStack(dinoMaterialBottom));
    }

    public void stopGame(){
        new BukkitRunnable(){
            @Override
            public void run(){
                player.closeInventory();
                player.sendTitle("Ты проиграл!", "Еще раз?", 40, 20, 10);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 1));
            }
        }.runTask(Plugin.getPlugin(Plugin.class));

        gameList.remove(this);
    }

    public Dino getDino() {
        return dino;
    }

    public Inventory getField() {
        return field;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Cactus> getCactusList() {
        return cactusList;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public Material getGround() {
        return ground;
    }

    public int getTicksPerSecond() {
        return ticksPerSecond;
    }
}
