package org.example;

import java.util.Iterator;

public class GameLogic {
    private Game game;
    private final int WIDTH, HEIGHT;

    public GameLogic(Game game, int width, int height) {
        this.game = game;
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void update() {
        if (game.paused || game.gameOver) return;

        game.paddle.update(WIDTH);
        game.ball.move();
        game.wall.checkCollision(game.ball);
        checkCollisions();
    }

    private void checkCollisions() {
        Ball ball = game.ball;
        Paddle paddle = game.paddle;

        if (ball.getRect().intersects(paddle.getRect())) {
            ball.dy *= -1;
            double hitPos = (ball.x - paddle.x + ball.size / 2.0) / paddle.width - 0.5;
            ball.dx = 6 * hitPos;
        }

        Iterator<Brick> it = game.bricks.iterator();
        while (it.hasNext()) {
            Brick b = it.next();
            if (ball.getRect().intersects(b.getRect())) {
                ball.dy *= -1;
                game.score += 10;
                it.remove();
                break;
            }
        }

        if (ball.y > HEIGHT) {
            game.lives--;
            if (game.lives <= 0) {
                game.gameOver = true;
            } else {
                game.resetBall();
            }
        }

        if (game.bricks.isEmpty()) {
            game.gameOver = true;
        }
    }
}

