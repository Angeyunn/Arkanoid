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
    private final int width; //Kich thuoc cua man hinh
    private final int height;

    private Paddle paddle; //Doi tuong thanh do
    private Ball ball; //Doi tuong qua bong
    private List<Brick> bricks; //Danh sach chua cac vien gach tren man hinh
    private List<PowerUp> powerUps; //Danh sach chua cac power-up xuat hien
    private int score; //Diem cua nguoi choi
    private int lives; //So mang con lai
    private GameState gameState; //Trang thai hien tai cua game (PLAYING, OVER, ...)

    private final Random rand = new Random(); // Tao ra cac su kien ngau nhien (roi powerup,..)

    //Phuong thuc khoi tao man hinh
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

    //Thiet lap man choi, dat moi thu ve trang thai ban dau
    public void startGame() {
        //Kich thuoc paddle va ball bang pixel
        int paddleWidth = 100;
        int paddleHeight = 20;
        int ballSize = 15;
        //Dat vi tri ban dau cua paddle va ball
        paddle = new Paddle(width / 2 - height / 2, height - 50, paddleWidth, paddleHeight, 8);
        ball = new Ball(width / 2, height / 2, ballSize, 6);
        ball.setVelocity(1, -1); //Cho bong di chuyen cheo len

        bricks = new ArrayList<>(); //Danh sach gach
        powerUps = new ArrayList<>(); //danh sach powerup

        int brickWidth = 60;
        int brickHeight = 20;
        int numCols = 10;
        int numRows = 5;
        int padding = 5;
        int offsetTop = 50; //Khoang cach tu dinh den man hinh
        int offsetLeft = (width - (numCols * (brickWidth + padding))) / 2; //Can giua

        //Tao man choi
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                //Tao cac khoi gach
                int brickX = offsetLeft + j * (brickWidth + padding);
                int brickY = offsetTop + i * (brickHeight + padding);
                if (i == 0) {
                    bricks.add(new UnbreakableBrick(brickX, brickY, brickWidth, brickHeight));
                } else if (i == 1) {
                    bricks.add(new ExplosiveBrick(brickX, brickY, brickWidth, brickHeight));
                } else if (i == 2) {
                    bricks.add(new StrongBrick(brickX, brickY, brickWidth, brickHeight));
                } else {
                    bricks.add(new NormalBrick(brickX, brickY, brickWidth, brickHeight));
                }
            }
        }
        score = 0;
        lives = 3;
        gameState = GameState.PLAYING;
    }

    //Cap nhat trang thai game
    public void updateGame() {
        //Gioi han di chuyen cua paddle ben trong khung hinh
        if (paddle.getX() <= 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() >= width) {
            paddle.setX(width - paddle.getWidth());
        }

        paddle.update();
        ball.update();
        //Cap nhat trang thai cac power-up
        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }

        checkCollision(); //Xu li cac va cham xay ra trong khung hinh do
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
        if (keyCode == java.awt.event.KeyEvent.VK_LEFT || keyCode == java.awt.event.KeyEvent.VK_A) {
            if (isPressed) {
                paddle.moveLeft();
            } else {
                paddle.stopMoving();
            }
        } else if (keyCode == java.awt.event.KeyEvent.VK_RIGHT || keyCode == java.awt.event.KeyEvent.VK_D) {
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
        if (ball.getX() <= 0) {
            ball.setX(0); //Chong dinh vao tuong
            ball.reverseXVelocity();
        }
        if (ball.getX() >= width - ball.getWidth()) {
            ball.setX(width - ball.getWidth()); //Chong dinh vao tuong
            ball.reverseXVelocity();
        }
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.reverseYVelocity();
        }
        //Bong cham day -> giam so mang
        if (ball.getY() >= height - ball.getHeight()) {
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
        Iterator<Brick> it = bricks.iterator(); //Bien 'it' dai dien cho vien gach
        while (it.hasNext()) {
            Brick brick = it.next(); //Lay ra vien gach hien tai de kiem tra
            // Neu vien gach chua bi pha huy và bound cua vien gach giao voi bound cua qua bong
            if (brick.isActive() && ballBounds.intersects(brick.getBounds())) {
                //Xu li huong nay cua bong
                handleBallBrickCollision(ball, brick);
                brick.takeHit();
                //Kiem tra vien gach bi pha hay chua
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
        paddle.setX(paddle.getOriginalWidth());
        paddle.setWidth(paddle.getOriginalWidth());
    }
    //Xu li va cham ball va brick
    private void handleBallBrickCollision(Ball ball, Brick brick) {
        Rectangle brickBounds = brick.getBounds();
        Rectangle ballBounds = ball.getBounds();
        //Tao ra hinh chu nhat dai dien cho phan giao nhau giua brickBounds va ballBounds
        Rectangle intersection = ballBounds.intersection(brickBounds);

        //Va cham tu canh tren hoac duoi cua gach
        //Xay ra khi intersection co chieu dai > rong
        if (intersection.width > intersection.height) {
            ball.reverseYVelocity(); //
        } else {
            //Va cham tu canh  trai hoac phai
            //Xay ra khi intersection co chieu dai < rong
            ball.reverseXVelocity();
        }
    }
    //Logic cua gach no
    //Tao mot vu no lan pha huy cac vien gach xung quanh
    private void explode(Brick explosiveBrick) {
        //Tao ra vung anh huong cua vu no la hinh chu nhat
        Rectangle explosionZone = new Rectangle(
                //Lay x, y, width, height cua vien gach no vua bi pha
                //Tao ra mot hinh chu nhat moi lon hon 2 don vi kich thuoc
                explosiveBrick.getX() - 2,
                explosiveBrick.getY() -2,
                explosiveBrick.getWidth() + 4,
                explosiveBrick.getHeight() +4);
        //Kiem tra tat ca cac vien gach trong man choi
        for (Brick brick : bricks) {
            //Neu vien gach van con && khong phai la gach khong the pha huy && nam trong vu no -> xoa vien gach
            if (brick.isActive() && !(brick instanceof UnbreakableBrick) && explosionZone.intersects(brick.getBounds())) {
                brick.setActive(false);
                score += 5; //Tang diem khi pha duoc gach
            }
        }
    }

    //logic tao powerup
    //Nhan x, y la toa do vien gach vua bi pha vo
    private void spawnPowerUp(int x, int y) {
        //Random 30%
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