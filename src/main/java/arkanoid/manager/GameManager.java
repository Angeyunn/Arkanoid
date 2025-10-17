package arkanoid.manager;

import arkanoid.model.Ball;
import arkanoid.model.GameObject;
import arkanoid.model.Paddle;
import arkanoid.model.brick.*;
import arkanoid.model.powerup.ExpandPaddlePowerUp;
import arkanoid.model.powerup.PowerUp;
import arkanoid.model.powerup.ShrinkPaddlePowerUp;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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

    private final Random rand = new Random();

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
        powerUps = new ArrayList<>(); //Khoi tao danh sach powerup
        //Tao man choi da dang hon de thu nghiem
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                int brickX = j * 5 + 2;
                int brickY = i * 2 + 2;
                if (i == 0) {
                    bricks.add(new UnbreakableBrick(brickX, brickY, 4, 1));
                } else if (i == 1) {
                    bricks.add(new ExplosiveBrick(brickX, brickY, 4, 1));
                } else if (i == 2) {
                    bricks.add(new StrongBrick(brickX, brickY, 4, 1));
                } else {
                    bricks.add(new NormalBrick(brickX, brickY, 4, 1));
                }
            }
        }
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
        //Cap nhat trang thai cac power-up
        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }

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
        if (ball.getX() <= 1) {
            ball.setX(1); //Chong dinh vao tuong
            ball.reverseXVelocity();
        }
        if (ball.getX() >= width - 2) {
            ball.setX(width - 2); //Chong dinh vao tuong
            ball.reverseXVelocity();
        }
        if (ball.getY() <= 1) {
            ball.reverseYVelocity();
        }
        //Bong cham day -> giam so mang
        if (ball.getY() >= height - 1) {
            lives--;
            if (lives > 0) { //Neu con mang thi choi tiep
                resetBallAndPaddle();
            } else {
                gameState = GameState.GAME_OVER;
            }
        }

        //Va cham voi paddle
        if (ballBounds.intersects(paddleBounds)) {
            ball.reverseYVelocity();
            //Dat lai vi tri bong ngay tren paddle
            ball.setY(paddle.getY() - ball.getHeight());
        }

        //Va cham voi gach
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (brick.isActive() && ballBounds.intersects(brick.getBounds())) {
                handleBallBrickCollision(ball, brick);
                    brick.takeHit();
                    if (brick.isDestroyed()) {
                        score += 10;
                        if (brick instanceof ExplosiveBrick) {
                            explode(brick);
                        }
                    spawnPowerUp(brick.getX(), brick.getY());
                }
                    break;
            }
        }
        // Ham va cham paddle va power up
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            //Neu power up va cham voi paddle
            if (powerUp.isActive() && powerUp.getBounds().intersects(paddleBounds)) {
                powerUp.applyEffect(paddle);
                powerUp.setActive(false); //Xoa power up sau khi thu thp xong
            }
            //Xoa power up neu no roi khoi man hinh
            if (powerUp.getY() >= height) {
                powerUp.setActive(false);
            }
        }
        bricks.removeIf(brick -> !brick.isActive());
        powerUps.removeIf(powerUp -> !powerUp.isActive());
    }
    private void resetBallAndPaddle() {
        ball.setX(width / 2);
        ball.setY(height / 2);
        ball.setVelocity(1, -1); //Reset huong bong
        paddle.setX(width / 2 - paddle.getOriginalWidth() / 2);
        paddle.setWidth(paddle.getOriginalWidth());
    }
    //Xu li va cham ball va brick
    private void handleBallBrickCollision(Ball ball, Brick brick) {
        Rectangle brickBounds = brick.getBounds();
        Rectangle ballBounds = ball.getBounds();
        Rectangle intersection = ballBounds.intersection(brickBounds);

        //Va cham tu canh tren hoac duoi cua gach
        if (intersection.width > intersection.height) {
            ball.reverseYVelocity();
        } else { //Va cham tu canh  trai hoac phai
            ball.reverseXVelocity();
        }
    }
    //Logic cua gach no
    private void explode(Brick explosiveBrick) {
        Rectangle explosionZone = new Rectangle(explosiveBrick.getX() - 2,
                explosiveBrick.getY() -2,
                explosiveBrick.getWidth() + 4,
                explosiveBrick.getHeight() +4);
        for (Brick brick : bricks) {
            if (brick.isActive() && !(brick instanceof UnbreakableBrick) && explosionZone.intersects(brick.getBounds())) {
                brick.setActive(false);
                score += 5; //Tang diem khi pha duoc gach
            }
        }
    }

    //logic tao powerup
        private void spawnPowerUp(int x, int y) {
            if (rand.nextInt(100) < 30) {
                if (rand.nextBoolean()) {
                    powerUps.add(new ExpandPaddlePowerUp(x, y, 3, 1, 0.5));
                } else {
                    powerUps.add(new ShrinkPaddlePowerUp(x, y, 3, 1, 0.5));
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