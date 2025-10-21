package org.example;

import javax.swing.*;
import java.awt.*;

public class Brick {
    public int x, y, width, height;
    public boolean destroyed = false;
    public int type;
    public Image image;

    public Brick(int x, int y, int width, int height, int type) {
        this.x = x; this.y = y;
        this.width = width; this.height = height;
        this.type = type;
        loadImage();
    }

    private void loadImage() {
        switch (type) {
            case 1 -> image = new ImageIcon(getClass().getResource("/images/brick1.png")).getImage();
            case 2 -> image = new ImageIcon(getClass().getResource("/images/brick2.png")).getImage();
            case 3 -> image = new ImageIcon(getClass().getResource("/images/brick3.png")).getImage();
            case 4 -> image = new ImageIcon(getClass().getResource("/images/brick4.png")).getImage();
            default -> image = new ImageIcon(getClass().getResource("/images/brick5.png")).getImage();
        }
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
}
