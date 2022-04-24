package org.bebr0.bebr0zavr;

public class Dino {

    private int topPos = 28;
    private int bottomPos = 37;
    private final Game game;

    private boolean isJumping = false;
    private boolean isSneaking = false;

    public Dino(Game game) {
        this.game = game;
    }

    public void jump(){
        new Thread(() -> {
            if (isJumping || isSneaking)
                return;
            isJumping = true;
            while (getTopPos() > 9){
                game.moveOneFrame(true);
                try {
                    Thread.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (getBottomPos() < 36){
                game.moveOneFrame(false);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            isJumping = false;
        }).start();
    }

    public void sneak(){
        if (isJumping)
            return;

        if (!isSneaking){
            topPos = bottomPos + 1;
            isSneaking = true;
        }
        else{
            topPos = bottomPos - 9;
            isSneaking = false;
        }

        game.sneakDino();
    }

    public int getTopPos() {
        return topPos;
    }

    public void setTopPos(int topPos) {
        this.topPos = topPos;
    }

    public int getBottomPos() {
        return bottomPos;
    }

    public void setBottomPos(int bottomPos) {
        this.bottomPos = bottomPos;
    }

    public boolean isSneaking() {
        return isSneaking;
    }
}
