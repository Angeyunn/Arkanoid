package arkanoid.model.powerup;

import arkanoid.model.Paddle;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Powerup giam kich thuoc paddle
 */
public class ShrinkPaddlePowerUp extends PowerUp {
    private static final int WIDTH_DECREASE = 20; //Hang so do dai giam di

    //Ham khoi tao
    public ShrinkPaddlePowerUp(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
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
