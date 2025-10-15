package aarkanoid.gameObjects;

import java.awt.*;

public abstract class MovableObject extends GameObject {
    protected double dx, dy;

    public MovableObject(int x, int y, int width, int height, double dx, double dy) {

        super(x, y, width, height);
        this.dx = dx;
        this.dy = dy;
    }

    public MovableObject(int x, int y, int width, int height, double dx, double dy, String texturePath) {

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

    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDx() { return dx; }
    public double getDy() { return dy; }

    public void setDx(double dx) { this.dx = dx; }
    public void setDy(double dy) { this.dy = dy; }
}