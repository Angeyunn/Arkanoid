package arkanoid.model;

import java.awt.Graphics;
import java.awt.Color;
/**
 * Dai dien cho paddle nguoi choi dieu khien.
 */
public class Paddle extends MovableObject {
    //Luu lai kich thuoc ban dau de reset sau khi het power up
    private final int originalWidth;

    //Ham khoi tao
    public Paddle(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        this.originalWidth = width;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE); //Paddle mau xanh duong
        g.fillRect(x, y, width, height); //Ve hinh chu nhat
    }

    //Di chuyen sang hai ben
    public void moveLeft() {
        this.velocityX = -speed;
    }

    public void moveRight() {
        this.velocityX = speed;
    }

    public void stopMoving() {
        this.velocityX = 0;
    }

    //Getter cho kich thuoc ban dau
    public int getOriginalWidth() {
        return originalWidth;
    }

}