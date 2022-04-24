package org.bebr0.bebr0zavr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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

    private static String jumpItemName = ChatColor.RED + "Прыгнуть";
    private static String sneakItemName = ChatColor.GREEN + "Присесть";

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
    private List<Bird> birdList = new ArrayList<>();

    private final int fieldSize = 54;
    private final Material ground = Material.GRASS_BLOCK;

    private final Dino dino;
    private boolean isLive = false;

    private int ticksPerSecond = 5;
    private int score = 0;

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

        isLive = true;
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (isLive) {
                if (canSpawn())
                    generateCactus();

                if (canSpawn())
                    generateBird();

                score++;

                for (int i = 0; i < cactusList.size(); i++) {
                   cactusList.get(i).move();
                }

                for (int i = 0; i < birdList.size(); i++) {
                    birdList.get(i).move();
                }

                if (score / 100 > ticksPerSecond){
                    ticksPerSecond++;
                }

                try {
                    Thread.sleep(1000 / ticksPerSecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private boolean canSpawn(){
        if (cactusList.size() != 0) {
            if (cactusList.get(cactusList.size() - 1).getPos() >= Cactus.initialPos - 4)
                return false;
        }

        if (birdList.size() != 0) {
            Bird bird = birdList.get(birdList.size() - 1);
            if ((bird.getInitialPos() == Bird.initialPos1 && bird.getPos() >= Bird.initialPos1 - 4) ||
                    (bird.getInitialPos() == Bird.initialPos2 && bird.getPos() >= Bird.initialPos2 - 4))
                return false;
        }

        return true;
    }

    private void generateBird() {
        Random random = new Random();
        if (random.nextFloat() < (float) 2/9){
            birdList.add(new Bird(this));
        }
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

        ItemStack jump = new ItemStack(jumpItem);
        ItemMeta jumpMeta = jump.getItemMeta();
        jumpMeta.setDisplayName(jumpItemName);

        jump.setItemMeta(jumpMeta);

        ItemStack sneak = new ItemStack(sneakItem);
        ItemMeta sneakMeta = sneak.getItemMeta();
        sneakMeta.setDisplayName(sneakItemName);

        sneak.setItemMeta(sneakMeta);

        player.getInventory().setItem(9, jump);
        player.getInventory().setItem(10, sneak);
    }


    public void moveOneFrame(boolean isJumping){
        field.setItem(dino.getTopPos(), new ItemStack(Material.AIR));
        field.setItem(dino.getBottomPos(), new ItemStack(Material.AIR));
        if (isJumping) {
            dino.setTopPos(dino.getTopPos() - 9);
            dino.setBottomPos(dino.getBottomPos() - 9);
        }
        else {
            dino.setTopPos(dino.getTopPos() + 9);
            dino.setBottomPos(dino.getBottomPos() + 9);
        }
        field.setItem(dino.getTopPos(), new ItemStack(dinoMaterialTop));
        field.setItem(dino.getBottomPos(), new ItemStack(dinoMaterialBottom));
    }

    public void sneakDino(){
        if (dino.isSneaking()) {
            field.setItem(dino.getBottomPos() - 9, new ItemStack(Material.AIR));
            field.setItem(dino.getTopPos(), new ItemStack(dinoMaterialTop));
        }
        else{
            field.setItem(dino.getBottomPos() + 1, new ItemStack(Material.AIR));
            field.setItem(dino.getTopPos(), new ItemStack(dinoMaterialTop));
        }
    }

    public void stopGame(){
        new BukkitRunnable(){
            @Override
            public void run(){
                isLive = false;
                player.closeInventory();
                player.sendTitle("Ты проиграл!", "Твой счет - " + score, 40, 20, 10);
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

    public List<Bird> getBirdList() {
        return birdList;
    }
}
