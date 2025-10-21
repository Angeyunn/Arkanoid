package org.example;

import javax.swing.*;
import java.awt.*;

public class Paddle {
    public int x, y, width, height, speed;
    public boolean left, right;
    public Image image;

    public Paddle(int x, int y, int width, int height, int speed) {
        this.x = x; this.y = y; this.width = width; this.height = height; this.speed = speed;
        this.image = new ImageIcon(getClass().getResource("/images/paddle.png")).getImage();
    }

    public void update(int panelWidth) {
        if (left) x -= speed;
        if (right) x += speed;
        if (x < 0) x = 0;
        if (x + width > panelWidth) x = panelWidth - width;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}
