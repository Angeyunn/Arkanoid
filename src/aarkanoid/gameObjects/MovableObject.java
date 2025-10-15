package aarkanoid.gameObjects;

import java.awt.*;

public abstract class MovableObject extends GameObject {
    protected float dx, dy;

    public MovableObject(int x, int y, int width, int height, float dx, float dy) {
        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(int x, int y, int width, int height, float dx, float dy, String texturePath) {
        super(x, y, width, height, texturePath);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
        bounds.setLocation(x, y);
    }

    public void setVelocity(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public float getDx() { return dx; }
    public float getDy() { return dy; }

    public void setDx(float dx) { this.dx = dx; }
    public void setDy(float dy) { this.dy = dy; }
}