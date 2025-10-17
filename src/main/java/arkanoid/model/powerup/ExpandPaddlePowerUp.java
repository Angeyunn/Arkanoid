package arkanoid.model.powerup;

import arkanoid.model.Paddle;

/**
 * Powerup tang kich thuoc paddle
 */
public class ExpandPaddlePowerUp extends PowerUp {
    private static final int WIDTH_INCREASE = 5; // Do dai tang them

    //Ham khoi tao
    public ExpandPaddlePowerUp(int x,  int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void render() {
        // Logic ve powerup tang kich thuoc (lam sau)
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
