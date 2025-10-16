package arkanoid.model.powerup;

import arkanoid.model.MovableObject;
import arkanoid.model.Paddle;

/**
 * Lop truu tuong vat pham power up
 * Roi xuong khi mot vien gach bi pha
 */
public abstract class PowerUp extends MovableObject {
    //Thoi gian hieu luc cua power-up (don vi frame hoac sec)
    private int duration;

    public PowerUp(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        this.velocityY = speed; //Powerup chi roi xuong
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public abstract void render();

    //Ap dung hieu ung len paddle
    public abstract void applyEffect(Paddle paddle);

    //Xoa hieu ung khoi paddle
    public abstract void removeEffect(Paddle paddle);
}