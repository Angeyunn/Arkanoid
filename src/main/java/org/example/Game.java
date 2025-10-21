package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public Paddle paddle;
    public Ball ball;
    public Wall wall;
    public List<Brick> bricks;

    public int score = 0;
    public int lives = 3;
    public boolean paused = false;
    public boolean gameOver = false;

    private final int WIDTH, HEIGHT;

    public Game(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        initGame();
    }

    public void initGame() {
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 60, 100, 20, 10);
        ball = new Ball(WIDTH / 2, HEIGHT - 80, 20, 5, -5);
        wall = new Wall(WIDTH, HEIGHT);
        bricks = new ArrayList<>();

        int rows = 6, cols = 10, bw = 70, bh = 25;
        int offX = 50, offY = 50;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int type = (r % 4) + 1; // mỗi hàng một màu
                bricks.add(new Brick(offX + c * (bw + 5), offY + r * (bh + 5), bw, bh, type));
            }
        }
    }

    public void resetBall() {
        ball.x = WIDTH / 2;
        ball.y = HEIGHT - 80;
        ball.dx = 5;
        ball.dy = -5;
    }
}
