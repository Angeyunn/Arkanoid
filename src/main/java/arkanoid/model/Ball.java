package arkanoid.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;
/**
 * Dai dien cho qua bong.
 */
public class Ball extends MovableObject {

    private BufferedImage image; //Bien luu anh
    //Ham khoi tao
    public Ball(int x, int y, int size, double speed) {
        //Bong la hinh vuong nen width = height
        super(x, y, size, size, speed);
        this.image = ImageLoader.loadImage("ball.png");
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, (int) x, (int) y, width, height, null);
        }
    }

    //Dao nguoc huong di chuyen khi va cham
    public void reverseXVelocity() {
        this.velocityX *= -1;
    }

    public void reverseYVelocity() {
        this.velocityY *= -1;
    }
}