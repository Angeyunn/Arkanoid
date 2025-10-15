package aarkanoid.gameObjects;

import java.awt.*;
import java.util.Random;

public class Ball extends MovableObject {
    private Random random;
    private boolean inPlay;

    public Ball(int x, int y, int width, int height, double dx, double dy) {
        super(x, y, width, height, dx, dy);
        this.random = new Random();
        this.inPlay = true;
    }

    @Override
    public void update() {
        if (!inPlay) return;

        super.update();

        if (x < 0 || x > 800 - width) {
            dx = -dx;
            x = Math.max(0, Math.min(x, 800 - width));
        }
        if (y < 0) {
            dy = -dy;
            y = 0;
        }

        if (y > 600) {
            inPlay = false;
        }
    }

    public void onCollision() {
        dy = -dy;
    }

    public void onPaddleCollision(Paddle paddle) {
        dy = -Math.abs(dy);

        float hitPos = (x + width/2 - paddle.getX()) / (float)paddle.getWidth();
        dx = (hitPos - 0.5f)* 10f;
    }

    @Override
    public void render(Graphics g) {
        if (!inPlay) return;

        if (texture != null) {
            g.drawImage(texture, x, y, width, height, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillOval(x, y, width, height);

            // Hiệu ứng bóng cho dễ nhìn
            g.setColor(new Color(200, 200, 255));
            g.fillOval(x + 3, y + 3, width - 6, height - 6);
        }
    }

    @Override
    protected Color getFallbackColor() {
        return Color.WHITE;
    }

    public void reset() {
        setPosition(800/2 - 8, 600 - 80);
        setVelocity((random.nextBoolean() ? 5 : -5), -5);
        inPlay = true;
    }

    public boolean isInPlay() { return inPlay; }
    public void setInPlay(boolean inPlay) { this.inPlay = inPlay; }
}