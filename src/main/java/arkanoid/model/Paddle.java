package arkanoid.model;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;
import arkanoid.model.powerup.PowerUp;
/**
 * Dai dien cho paddle nguoi choi dieu khien.
 */
public class Paddle extends MovableObject {
    //Luu lai kich thuoc ban dau de reset sau khi het power up
    private final int originalWidth;

    private BufferedImage image; //Bien luu anh

    private PowerUp currentPowerUp = null; //Bien luu powerup
    private long powerUpStartTime = 0;
    private static final long POWER_UP_DURATION_MS = 8000; // 8 giay
    //Ham khoi tao
    public Paddle(int x, int y, int width, int height, double speed) {
        super(x, y, width, height, speed);
        this.originalWidth = width;
        this.image = ImageLoader.loadImage("paddle.png");
    }

    @Override
    public void update() {

        move();
        if (currentPowerUp != null && System.currentTimeMillis() - powerUpStartTime > POWER_UP_DURATION_MS) {
            currentPowerUp.removeEffect(this);
            currentPowerUp = null;
        }
    }

    @Override
    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, (int) x, (int) y, width, height, null);
        }
    }

    //Di chuyen sang hai ben
    public void moveLeft() {
        this.velocityX = -speed;
    }

    public void moveRight() {
        this.velocityX = speed;
    }

    public void stopMoving() {
        this.velocityX = 0;
    }

    //Getter cho kich thuoc ban dau
    public int getOriginalWidth() {
        return originalWidth;
    }

    //Phuong thuc ap dung powerup
    public void applyPowerUp(PowerUp powerUp) {
        //Neu dang co power-up, xoa hieu ung cu
        if (currentPowerUp != null) {
            currentPowerUp.removeEffect(this);
        }

        powerUp.applyEffect(this);
        this.currentPowerUp = powerUp;
        this.powerUpStartTime = System.currentTimeMillis();
    }

    //Phuong thuc reset power up
    public void resetPowerUp() {
        if (currentPowerUp != null) {
            currentPowerUp.removeEffect(this); // Xoa hieu ung
            currentPowerUp = null;
        }
        setWidth(originalWidth);
    }
}