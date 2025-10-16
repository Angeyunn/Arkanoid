package arkanoid.model.powerup;

import arkanoid.model.Paddle;

/**
 * Powerup tang kich thuoc paddle
 */
public class ExpandPaddlePowerUp extends PowerUp {
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
    }

    public void removeEffect(Paddle paddle) {
        //logic thu paddle ve kich thuoc ban dau
    }
}
