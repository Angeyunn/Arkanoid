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
    public Ball cloneBall() {
        //Tao mot qua bong moi cung vi tri, toc do
        Ball newBall = new Ball(this.x, this.y, this.width, this.speed);

        //Sao chep van toc hien tai
        newBall.velocityX = this.velocityX;
        newBall.velocityY = this.velocityY;

        return newBall;
    }

    //Dao nguoc huong di chuyen khi va cham
    public void reverseXVelocity() {
        this.velocityX *= -1;
    }

    public void reverseYVelocity() {
        this.velocityY *= -1;
    }
}