package arkanoid.manager;

import arkanoid.model.Ball;
import arkanoid.model.GameObject;
import arkanoid.model.Paddle;
import arkanoid.model.brick.*;
import arkanoid.model.powerup.ExpandPaddlePowerUp;
import arkanoid.model.powerup.MultiBallPowerUp;
import arkanoid.model.powerup.PowerUp;
import arkanoid.model.powerup.ShrinkPaddlePowerUp;
import arkanoid.view.MenuScreen;
import arkanoid.view.LevelSelectScreen;

import javax.swing.*;
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
    private List<Ball> balls; //Doi tuong qua bong
    private List<Brick> bricks; //Danh sach chua cac vien gach tren man hinh
    private List<PowerUp> powerUps; //Danh sach chua cac power-up xuat hien
    private int score; //Diem cua nguoi choi
    private int lives; //So mang con lai
    private GameState gameState; //Trang thai hien tai cua game (PLAYING, OVER, ...)

    private final Random rand = new Random(); // Tao ra cac su kien ngau nhien (roi powerup,..)
    private MenuScreen menuScreen;

    private LevelManager levelManager;
    private LevelSelectScreen levelSelectScreen;

    private SoundManager soundManager; //soundManager
    private boolean isBallLaunched = false;
    private List<Ball> ballsToAdd = new ArrayList<>(); //Danh sach tam cua balls de tranh loi

    //Phuong thuc khoi tao man hinh
    public GameManager(int width, int height) {
        this.width = width;
        this.height = height;
        this.soundManager = new SoundManager();
        this.soundManager.loadSound("bounce.wav");
        this.soundManager.loadSound("break.wav");
        this.menuScreen = new MenuScreen();
        this.levelManager = new LevelManager();
        this.levelSelectScreen = new LevelSelectScreen();
        this.balls = new ArrayList<>();
        this.gameState = GameState.MENU;
    }

    //Getter va setter
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameState getGameState() {
        return gameState;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }
    public LevelSelectScreen getLevelSelectScreen() {
        return levelSelectScreen;
    }

    //Thiet lap man choi, dat moi thu ve trang thai ban dau
    public void resetGame() {
        //Kich thuoc paddle va ball bang pixel
        int paddleWidth = 100;
        int paddleHeight = 20;
        int ballSize = 20;
        //Dat vi tri ban dau cua paddle va ball
        paddle = new Paddle(width / 2 - paddleWidth / 2, height - 50, paddleWidth, paddleHeight, 8);
        balls.clear();
        Ball newBall = new Ball(width / 2, height / 2, ballSize, 5);

        newBall.setDirection(0, 0);
        balls.add(newBall);

        powerUps = new ArrayList<>(); //danh sach powerup

        score = 0;
        lives = 3;
        isBallLaunched = false;
    }
    //Ham duoc goi boi LevelSelectScreen
    public void loadLevelAndStart(int levelNumber) {
        resetGame();
        this.bricks = levelManager.getLevel(levelNumber, width, height);
        this.gameState = GameState.PLAYING;
    }

    //Ham duoc goi boi MenuScreen
    public void goToLevelSelect() {
        gameState = GameState.LEVEL_SELECT;
    }

    //Ham quay ve MENU
    public void goToMenu() {
        gameState = GameState.MENU;
    }

    //Cap nhat trang thai game
    public void updateGame() {
        //Chi update logic khi game dang chay
        if (gameState != GameState.PLAYING) {
            return;
        }

        //Gioi han di chuyen cua paddle ben trong khung hinh
        if (paddle.getX() <= 0) {
            paddle.setX(0);
        }
        if (paddle.getX() + paddle.getWidth() >= width) {
            paddle.setX(width - paddle.getWidth());
        }

        paddle.update();
        for (Ball ball : balls) {
            ball.update();
        }
        //Cap nhat trang thai cac power-up
        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }
        ballsToAdd.clear();
        //Kiem tra bong da duoc phong hay chua, neu chua thi cho dinh vao paddle
        if (!isBallLaunched) {
            if (!balls.isEmpty()) {
                Ball mainBall = balls.get(0);
                mainBall.setX(paddle.getX() + paddle.getWidth() / 2 - (mainBall.getWidth() / 2));
                mainBall.setY(paddle.getY() - mainBall.getHeight() - 1);
            }
        } else {
            //Xu li cac va cham xay ra trong khung hinh do
            Iterator<Ball> ballIterator = balls.iterator();
            while (ballIterator.hasNext()) {
                Ball currentBall = ballIterator.next();
                checkCollision(currentBall, ballIterator); //Gui Iterator de co the xoa bang

            }
        }
        balls.addAll(ballsToAdd);
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
        if (isPressed) {
            if (keyCode == KeyEvent.VK_ESCAPE) {
                if (gameState == GameState.PLAYING) {
                    togglePause(); // Phím ESC luôn dùng để Pause/Resume
                } else if (gameState == GameState.PAUSED) {
                    togglePause();
                } else if (gameState == GameState.LEVEL_SELECT) {
                    goToMenu();
                }
            }
            switch (gameState) {
                case MENU:
                    menuScreen.handleInput(keyCode, this, isPressed);
                    break;
                case PAUSED:
                    if (keyCode == KeyEvent.VK_ENTER) {
                        goToMenu();
                    }
                    break;
                case LEVEL_SELECT:
                    levelSelectScreen.handleInput(keyCode, this, isPressed);
                    break;
                case GAME_OVER:
                case GAME_WIN:
                    if (keyCode == KeyEvent.VK_ENTER) {
                    goToMenu();
                    }
                    break;
                    case PLAYING:
                        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                            paddle.moveLeft();
                        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                            paddle.moveRight();
                        }
                        if (!isBallLaunched && (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W)) {
                            isBallLaunched = true;
                            if (!balls.isEmpty()) {
                                balls.get(0).setDirection(0, -1); //Phong thang len
                            }
                        }
                        break;
            }
        }
        else {
            if (gameState == GameState.PLAYING) {
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A
                || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                    paddle.stopMoving();
                }
            }
        }
    }

    //Phuong thuc pause game
    public void togglePause() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.PAUSED;
        } else if (gameState == GameState.PAUSED) {
            gameState = GameState.PLAYING;
        }
    }


    //Xu li va cham
    private void checkCollision(Ball ball, Iterator<Ball> ballIterator) {
        Rectangle ballBounds = ball.getBounds();
        Rectangle paddleBounds = paddle.getBounds();

        //Va cham voi tuong
        if (ball.getX() <= 0) {
            ball.setX(0); //Chong dinh vao tuong
            ball.reverseXVelocity();
            soundManager.play("bounce.wav");
        }
        if (ball.getX() >= width - ball.getWidth()) {
            ball.setX(width - ball.getWidth()); //Chong dinh vao tuong
            ball.reverseXVelocity();
            soundManager.play("bounce.wav");
        }
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.reverseYVelocity();
            soundManager.play("bounce.wav");
        }
        //Bong cham day -> giam so mang
        if (ball.getY() >= height - ball.getHeight()) {
            ballIterator.remove();
            //Neu la qua bong cuoi cung -> mat mang
            if (balls.isEmpty()) {
                lives--;
                if (lives > 0) { //Neu con mang thi choi tiep
                    resetBallAndPaddle();
                } else {
                    gameState = GameState.GAME_OVER;
                }
            }
        }

        //Va cham voi paddle
        if (ballBounds.intersects(paddleBounds)) {
            double px = paddle.getX();
            double pw = paddle.getWidth();
            // bx là tâm của quả bóng
            double bx = ball.getX() + (ball.getWidth() / 2.0);

            // 2. Tính toán vị trí va chạm (từ 0.0 đến 1.0)
            // (bx - px) là vị trí va chạm tương đối trên paddle
            double hit_pos = (bx - px) / pw;

            // Giới hạn hit_pos trong khoảng [0.0, 1.0] để tránh lỗi
            hit_pos = Math.max(0.0, Math.min(1.0, hit_pos));

            // Tin toan goc nay
            double relative_pos = hit_pos - 0.5;

            // Goc nay toi da la 60 do
            double max_angle_rad = Math.PI / 3.0 * 2.0;
            double angle = relative_pos * max_angle_rad;

            // Tinh toan direction moi
            double dirX = Math.sin(angle);
            double dirY = -Math.cos(angle);

            ball.setDirection(dirX, dirY);
            //Hieu chinh de tranh dinh paddle
            ball.setY(paddle.getY() - ball.getHeight() - 1);
            soundManager.play("bounce.wav");
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
                    soundManager.play("break.wav");
                    if (brick instanceof ExplosiveBrick) {
                        explode(brick);
                    }
                    spawnPowerUp(brick.getX(), brick.getY());
                } else {
                    soundManager.play("bounce.wav");
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
                if (powerUp instanceof MultiBallPowerUp) {
                    spawnExtraBalls(); //Neu la powerup Multiball, sinh them mot ball moi
                } else {
                    paddle.applyPowerUp(powerUp);
                }

                powerUp.setActive(false); //Xoa power up sau khi thu thp xong
            }
            //Xoa power up neu no roi khoi man hinh
            if (powerUp.getY() >= height) {
                powerUp.setActive(false);
            }
        }
        bricks.removeIf(brick -> !brick.isActive());
        powerUps.removeIf(powerUp -> !powerUp.isActive());

        //Dieu kien thang: tat ca cac gach deu la gach khong the pha
        if (bricks.stream().allMatch(b -> b instanceof UnbreakableBrick)) {
            gameState = GameState.GAME_WIN;
        }
    }

    private void resetBallAndPaddle() {
        paddle.setX(width / 2 - paddle.getOriginalWidth() / 2);
        paddle.resetPowerUp();
        balls.clear(); //Xoa het bong
        Ball newBall =  new Ball(width / 2, height / 2, 20, 5);
        newBall.setDirection(0, 0);
        ballsToAdd.add(newBall);
        isBallLaunched = false;
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
            //Day bong ra khoi gach
            if (ball.getY() < brick.getY()) {//Bong dap tu tren xuong
                ball.setY(brick.getY() - ball.getHeight());
            } else {
                //Bong dap tu duoi len
                ball.setY(brick.getY() + ball.getHeight());
            }
            ball.reverseYVelocity(); //Dao nguoc huong Y
        } else {
            //Day bong ra khoi gach
            if (ball.getX() < brick.getX()) { //Bong dap tu trai sang
                ball.setX(brick.getX() - ball.getWidth());
            } else { //Bong dap tu phai sang
                ball.setX(brick.getX() + brick.getWidth());
            }
            ball.reverseXVelocity(); //Dao huong X
        }
    }
    //Logic cua gach no
    //Tao mot vu no lan pha huy cac vien gach xung quanh
    private void explode(Brick explosiveBrick) {
        int expansion = 6;
        //Tao ra vung anh huong cua vu no la hinh chu nhat
        Rectangle explosionZone = new Rectangle(
                //Lay x, y, width, height cua vien gach no vua bi pha
                //Tao ra mot hinh chu nhat moi lon hon expansion don vi kich thuoc
                explosiveBrick.getX() - expansion,
                explosiveBrick.getY() - expansion,
                explosiveBrick.getWidth() + (2 * expansion),
                explosiveBrick.getHeight() + (2 * expansion));
        //Kiem tra tat ca cac vien gach trong man choi
        for (Brick brick : bricks) {
            //Neu vien gach van con && khong phai la gach khong the pha huy && nam trong vu no -> xoa vien gach
            if (brick.isActive() && !(brick instanceof UnbreakableBrick) && explosionZone.intersects(brick.getBounds())) {
                brick.takeHit();
                if (brick.isDestroyed()) {
                    score += 5; //Tang diem khi pha duoc gach
                }
            }
        }
    }

    //logic tao powerup
    //Nhan x, y la toa do vien gach vua bi pha vo
    private void spawnPowerUp(int x, int y) {
        int randVal = rand.nextInt(100);
        if (randVal < 10) { // 10% ra Multiball
            powerUps.add(new MultiBallPowerUp(x, y, 15, 15, 2));
        } else if (randVal < 30) { // 20% ra 2 loai kia
            if (rand.nextBoolean()) {
                powerUps.add(new ExpandPaddlePowerUp(x, y, 15, 15, 2));
            } else {
                powerUps.add(new ShrinkPaddlePowerUp(x, y, 15, 15, 2));
            }
        }
        //70% khong ra gi
    }

    //Ham spawn bong clone
    private void spawnExtraBalls() {
        List<Ball> newBalls = new ArrayList<>();
        for (Ball ball : balls) {
            //Clone qua bong hien tai
            Ball ball2 = ball.cloneBall();

            //Thay doi huong qua bong clone bang cach dao nguoc X
            ball2.reverseXVelocity();

            newBalls.add(ball2);
        }
        ballsToAdd.addAll(newBalls);
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
        if (gameState != GameState.MENU) {
            if (paddle != null) allObjects.add(paddle);
            if (balls != null) {
                allObjects.addAll(balls);
            }
            if (bricks != null) allObjects.addAll(bricks);
            if (powerUps != null) allObjects.addAll(powerUps);
        }
        return allObjects;
    }

    public enum GameState {
        MENU, LEVEL_SELECT, PLAYING, PAUSED, GAME_OVER, GAME_WIN;
    }
}