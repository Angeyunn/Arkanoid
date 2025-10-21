package org.example;

import javax.swing.*;
import java.awt.*;

public class Ball {
    public double x, y, dx, dy;
    public int size;
    public Image image;

    public Ball(double x, double y, int size, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.dx = dx;
        this.dy = dy;
        this.image = new ImageIcon(getClass().getResource("/images/ball.png")).getImage();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public Rectangle getRect() {
        return new Rectangle((int)x, (int)y, size, size);
    }
}
