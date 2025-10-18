package arkanoid.model;

import java.awt.Color;
import java.awt.Graphics;
/**
 * Dai dien cho qua bong.
 */
public class Ball extends MovableObject {
    //Ham khoi tao
    public Ball(int x, int y, int size, double speed) {
        //Bong la hinh vuong nen width = height
        super(x, y, size, size, speed);
    }

    @Override
    public void update() {

        move();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE); //Bong mau trang
        g.fillRect(x, y, width, height); //Ve hinh tron
    }

    //Dao nguoc huong di chuyen khi va cham
    public void reverseXVelocity() {
        this.velocityX *= -1;
    }

    public void reverseYVelocity() {
        this.velocityY *= -1;
    }
}