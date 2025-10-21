package org.example;

import javax.swing.*;
import java.awt.*;

public class Wall {
    private int width, height;
    private Image wallTop;

    public Wall(int width, int height) {
        this.width = width;
        this.height = height;
        this.wallTop = new ImageIcon(getClass().getResource("/images/paddle.png")).getImage();
    }

    public void draw(Graphics2D g) {
        g.drawImage(wallTop, 0, 0, width, 20, null);
    }

    public void checkCollision(Ball ball) {
        if (ball.x <= 0 || ball.x + ball.size >= width) ball.dx *= -1;
        if (ball.y <= 20) ball.dy *= -1;
    }
}
