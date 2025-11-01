package arkanoid.model.powerup;

import arkanoid.model.Paddle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;

/**
 * Powerup tang kich thuoc paddle
 */
public class ExpandPaddlePowerUp extends PowerUp {
    private static final int WIDTH_INCREASE = 30; // Do dai tang them
    private BufferedImage image;

    //Ham khoi tao
    public ExpandPaddlePowerUp(int x,  int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        this.image = ImageLoader.loadImage("expand_powerup.png");
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    @Override
    public void applyEffect(Paddle paddle) {
        //Logic lam cho paddle dai ra
        paddle.setWidth(paddle.getWidth() + WIDTH_INCREASE);
    }

    public void removeEffect(Paddle paddle) {
        paddle.setWidth(paddle.getOriginalWidth());
    }
}
