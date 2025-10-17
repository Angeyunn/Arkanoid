package arkanoid.model.powerup;

import arkanoid.model.Paddle;

/**
 * Powerup giam kich thuoc paddle
 */
public class ShrinkPaddlePowerUp extends PowerUp {
    private static final int WIDTH_DECREASE = 3; //Hang so do dai giam di

    //Ham khoi tao
    public ShrinkPaddlePowerUp(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void render() {
        // Logic ve powerup tang kich thuoc (lam sau)
    }

    @Override
    public void applyEffect(Paddle paddle) {
        int newWidth = paddle.getWidth() - WIDTH_DECREASE;
        paddle.setWidth(Math.max(newWidth, 2)); //Chieu rong toi thieu la 2
    }

    @Override
    public void removeEffect(Paddle paddle) {
        paddle.setWidth(paddle.getOriginalWidth());
    }
}
