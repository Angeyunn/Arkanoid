package aarkanoid.gameObjects;

import java.awt.*;

public abstract class Brick extends GameObject {
    protected int hitPoints;
    protected boolean destroyed;

    public Brick(int x, int y, int width, int height, int hitPoints) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.destroyed = false;
    }

    public Brick(int x, int y, int width, int height, String texturePath, int hitPoints) {
        super(x, y, width, height, texturePath);
        this.hitPoints = hitPoints;
        this.destroyed = false;
    }

    public void onHit() {
        hitPoints--;
        if (hitPoints <= 0) destroyed = true;
    }

    public abstract int getScoreValue();
    public boolean isImmortal() { return false; }
    public boolean isDestroyed() { return destroyed; }
    public int getHitPoints() { return hitPoints; }

    @Override
    public void render(Graphics g) {
        if (texture != null) {
            g.drawImage(texture, x, y, width, height, null);
        } else {
            g.setColor(getFallbackColor());
            g.fillRect(x, y, width, height);
        }
    }
}