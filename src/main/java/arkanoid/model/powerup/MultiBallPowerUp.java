package arkanoid.model.powerup;

import arkanoid.model.Paddle;
import java.awt.Color;
import java.awt.Graphics;
import arkanoid.utils.ImageLoader;
import java.awt.image.BufferedImage;
/**
 * Power-up nhân đôi số lượng bóng hiện có.
 */
public class MultiBallPowerUp extends PowerUp {
    private BufferedImage image;
    public MultiBallPowerUp(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        this.image = ImageLoader.loadImage("multi_ball_powerup.png");
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    @Override
    public void applyEffect(Paddle paddle) {
    }

    @Override
    public void removeEffect(Paddle paddle) {
    }
}