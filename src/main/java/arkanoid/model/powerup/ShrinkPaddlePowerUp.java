package arkanoid.model.powerup;

import arkanoid.model.Paddle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;


/**
 * Powerup giam kich thuoc paddle
 */
public class ShrinkPaddlePowerUp extends PowerUp {
    private static final int WIDTH_DECREASE = 20; //Hang so do dai giam di
    private BufferedImage image;

    //Ham khoi tao
    public ShrinkPaddlePowerUp(int x, int y, int width, int height, double speed) {

        super(x, y, width, height, speed);
        this.image = ImageLoader.loadImage("shrink_powerup.png");

    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    @Override
    public void applyEffect(Paddle paddle) {
        int newWidth = paddle.getWidth() - WIDTH_DECREASE;
        paddle.setWidth(Math.max(newWidth, 20)); //Chieu rong toi thieu la 20 pixel
    }

    @Override
    public void removeEffect(Paddle paddle) {
        paddle.setWidth(paddle.getOriginalWidth());
    }
}
