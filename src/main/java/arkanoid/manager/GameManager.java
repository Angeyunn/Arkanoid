package arkanoid.manager;

import arkanoid.model.Ball;
import arkanoid.model.GameObject;
import arkanoid.model.Paddle;
import arkanoid.model.brick.Brick;
import arkanoid.model.brick.NormalBrick;
import arkanoid.model.powerup.PowerUp;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.KeyEvent;
/**
 * Lop quan li chinh, dieu khien logic trong game
 */
public class GameManager {
    private final int width;
    private final int height;

    private Paddle paddle;
    private Ball ball;
    private List<Brick> bricks;
    private List<PowerUp> powerUps;

    private int score;
    private int lives;
    private GameState gameState;

    public GameManager(int width, int height) {
        this.width = width;
        this.height = height;
    }

    //Getter va setter
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //Thiet lap man choi
    public void startGame() {
        paddle = new Paddle(width / 2 - 5, height - 3, 10, 1, 1);
        ball = new Ball(width / 2, height / 2, 1, 1);
        ball.setVelocity(1, -1); //Cho bong di chuyen cheo len

        bricks = new ArrayList<>();
        //Tao luoi gach de test
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                bricks.add(new NormalBrick(j * 5 + 2, i * 2 + 2, 4, 1));
            }
        }
        powerUps = new ArrayList<>();
        score = 0;
        lives = 3;
        gameState = GameState.PLAYING;
    }

    //Cap nhat trang thai game moi frame
    public void updateGame() {
        //Gioi han di chuyen cua paddle
        if (paddle.getX() <= 1) {
            paddle.setX(1);
        }
        if (paddle.getX() + paddle.getWidth() >= width - 1) {
            paddle.setX(width - 1 - paddle.getWidth());
        }

        paddle.update();
        ball.update();
        checkCollision();
    }

    //Xu li input tu terminal
    public void handleInput(char key) {
        if (key == 'a') {
            paddle.moveLeft();
        } else if (key == 'd') {
            paddle.moveRight();
        }
    }

    // Phuong thuc di chuyen ranh rieng cho phien ban GUI
    public void handleInput(int keyCode, boolean isPressed) {
        if (keyCode == java.awt.event.KeyEvent.VK_LEFT) {
            if (isPressed) {
                paddle.moveLeft();
            } else {
                paddle.stopMoving();
            }
        } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT) {
            if (isPressed) {
                paddle.moveRight();
            } else {
                paddle.stopMoving();
            }
        }
    }

    //Xu li va cham
    private void checkCollision() {
        Rectangle ballBounds = ball.getBounds();
        Rectangle paddleBounds = paddle.getBounds();

        //Va cham voi tuong
        if (ball.getX() <= 1 || ball.getX() >= width - 2) {
            ball.reverseXVelocity();
        }
        if (ball.getY() <= 1) {
            ball.reverseYVelocity();
        }
        if (ball.getY() >= height - 2) {
            lives--;
            ball.setX(width / 2);
            ball.setY(height / 2);
            paddle.setX(width / 2 - 5);
        }

        //Va cham voi paddle
        if (ballBounds.intersects(paddleBounds)) {
            ball.reverseYVelocity();
            ball.setY(paddle.getY() - ball.getHeight());
        }

        //Va cham voi gach
        for (Iterator<Brick> it = bricks.iterator(); it.hasNext();) {
            Brick brick = it.next();
            if (brick.isActive()) {
                if (ballBounds.intersects(brick.getBounds())) {
                    ball.reverseYVelocity();
                    brick.takeHit();
                    if (brick.isDestroyed()) {
                        score += 10;
                    }
                    break;
                }
            }
        }
    }

    //Cac ham getter
    public int getScore() {
        return score;
    }
    public int getLives() {
        return lives;
    }
    public List<GameObject> getAllGameObjects() {
        List<GameObject> allObjects = new ArrayList<>();
        if (paddle != null) allObjects.add(paddle);
        if (ball != null) allObjects.add(ball);
        if (bricks != null) allObjects.addAll(bricks);
        if (powerUps != null) allObjects.addAll(powerUps);
        return allObjects;
    }

    public enum GameState {
        MENU, PLAYING, PAUSED, GAME_OVER
    }
}