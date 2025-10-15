package aarkanoid.gameObjects;

import java.awt.*;

public class Paddle extends MovableObject {
    private static final int SPEED = 8; // Tăng tốc độ cho màn hình lớn

    public Paddle(int x, int y, int width, int height) {
        super(x, y, width, height, 0, 0);
    }

    public void moveLeft() {
        setDx(-SPEED);
    }

    public void moveRight() {
        setDx(SPEED);
    }

    public void stop() {
        setDx(0);
    }

    @Override
    public void update() {
        super.update();
        // Giới hạn trong màn hình - DÙNG KÍCH THƯỚC MỚI
        if (x < 0) x = 0;
        if (x > 800 - width) x = 800 - width;
    }

    @Override
    public void render(Graphics g) {
        // PADDLE LỚN HƠN VÀ ĐẸP HƠN
        if (texture != null) {
            g.drawImage(texture, x, y, width, height, null);
        } else {
            // Gradient effect cho paddle
            for (int i = 0; i < height; i++) {
                float ratio = (float) i / height;
                int green = (int) (255 * (1 - ratio * 0.5f));
                g.setColor(new Color(0, green, 0));
                g.fillRect(x, y + i, width, 1);
            }

            // Viền nổi bật
            g.setColor(Color.WHITE);
            g.drawRect(x, y, width - 1, height - 1);
            g.drawRect(x + 1, y + 1, width - 3, height - 3);
        }
    }

    @Override
    protected Color getFallbackColor() {
        return Color.GREEN;
    }

    public float getHitPosition(float ballX) {
        return (ballX - x) / (float) width;
    }
}