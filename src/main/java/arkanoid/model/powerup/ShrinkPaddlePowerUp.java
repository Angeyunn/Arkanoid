package arkanoid.model.powerup;

import arkanoid.model.Paddle;

/**
 * Powerup giam kich thuoc paddle
 */
public class ShrinkPaddlePowerUp extends PowerUp {
    public ShrinkPaddlePowerUp(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
    }

    @Override
    public void render() {
        // Logic ve powerup tang kich thuoc (lam sau)
    }

    @Override
    public void applyEffect(Paddle paddle) {
        //Logic lam cho paddle ngan di
    }

    @Override
    public void removeEffect(Paddle paddle) {
        //logic thu paddle ve kich thuoc ban dau
    }
}
