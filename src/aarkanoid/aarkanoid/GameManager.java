package aarkanoid;

import aarkanoid.gameObjects.*;
import aarkanoid.states.GameState;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager instance;
    private GameState currentState;
    private List<GameObject> gameObjects;
    private Paddle paddle;
    private Ball ball;
    private int score;
    private int lives;
    private int level;

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public GameManager() {
        initialize();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private void initialize() {
        System.out.println("🔄 Initializing GameManager...");
        this.gameObjects = new ArrayList<>();
        this.currentState = GameState.MENU;
        this.score = 0;
        this.lives = 3;
        this.level = 1;

        createGameObjects();
        System.out.println("✅ GameManager initialized - Screen: " + SCREEN_WIDTH + "x" + SCREEN_HEIGHT);
    }

    private void createGameObjects() {
        gameObjects.clear();

        paddle = new Paddle(SCREEN_WIDTH/2 - 60, SCREEN_HEIGHT - 50, 120, 20);
        gameObjects.add(paddle);
        System.out.println("🎯 Paddle created at: " + paddle.getX() + ", " + paddle.getY());

        ball = new Ball(SCREEN_WIDTH/2 - 8, SCREEN_HEIGHT - 80, 16, 16, 5, -5);
        gameObjects.add(ball);
        System.out.println("⚽ Ball created at: " + ball.getX() + ", " + ball.getY());

        createBricks();
        System.out.println("🧱 Created " + (gameObjects.size() - 2) + " bricks");
    }

    private void createBricks() {
        int brickWidth = 70;  // Brick rộng hơn
        int brickHeight = 25; // Brick cao hơn
        int startX = 30;      // Lề trái
        int startY = 50;      // Lề trên

        // TĂNG SỐ LƯỢNG BRICK: 8 hàng x 10 cột = 80 bricks
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 10; col++) {
                Brick brick = createBrickByType(row, startX + col * (brickWidth + 5), startY + row * (brickHeight + 5));
                gameObjects.add(brick);
            }
        }
    }

    private Brick createBrickByType(int row, int x, int y) {
        switch (row) {
            case 0: return new ImmortalBrick(x, y);    // Hàng 1: Bất tử
            case 1: return new HardBrick(x, y);        // Hàng 2: Rất cứng
            case 2: return new StrongBrick(x, y);      // Hàng 3: Cứng
            case 3: return new StrongBrick(x, y);      // Hàng 4: Cứng
            case 4: return new MediumBrick(x, y);      // Hàng 5: Trung bình
            case 5: return new MediumBrick(x, y);      // Hàng 6: Trung bình
            case 6: return new NormalBrick(x, y);      // Hàng 7: Dễ vỡ
            case 7: return new NormalBrick(x, y);      // Hàng 8: Dễ vỡ
            default: return new NormalBrick(x, y);
        }
    }

    public void update() {
        if (currentState != GameState.PLAYING) return;

        for (GameObject obj : gameObjects) {
            obj.update();
        }

        checkCollisions();
        checkGameProgress();
    }

    private void checkCollisions() {
        if (!ball.isInPlay()) return;

        // Ball với bricks
        List<GameObject> objectsToRemove = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (obj instanceof Brick) {
                Brick brick = (Brick) obj;
                if (!brick.isDestroyed() && ball.getBounds().intersects(brick.getBounds())) {
                    brick.onHit();
                    ball.onCollision();

                    if (!brick.isImmortal()) {
                        score += brick.getScoreValue();
                        if (brick.isDestroyed()) {
                            objectsToRemove.add(obj);
                        }
                    }
                    break;
                }
            }
        }
        gameObjects.removeAll(objectsToRemove);

        if (ball.getBounds().intersects(paddle.getBounds())) {
            ball.onPaddleCollision(paddle);
        }
    }

    private void checkGameProgress() {
        // Ball rơi xuống đáy - DÙNG SCREEN_HEIGHT MỚI
        if (ball.getY() > SCREEN_HEIGHT) {
            lives--;
            if (lives <= 0) {
                currentState = GameState.GAME_OVER;
                System.out.println("💀 GAME OVER");
            } else {
                ball.reset();
                System.out.println("🎯 Lives left: " + lives);
            }
        }

        // Kiểm tra level hoàn thành
        boolean hasBricks = false;
        for (GameObject obj : gameObjects) {
            if (obj instanceof Brick && !((Brick) obj).isDestroyed() && !((Brick) obj).isImmortal()) {
                hasBricks = true;
                break;
            }
        }

        if (!hasBricks && currentState == GameState.PLAYING) {
            currentState = GameState.VICTORY;
            System.out.println("🎉 VICTORY! All bricks destroyed!");
        }
    }

    public void render(Graphics g) {
        // Clear screen với kích thước mới
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Render tất cả objects
        for (GameObject obj : gameObjects) {
            obj.render(g);
        }

        // Render UI
        renderUI(g);

        // Render state message
        renderStateMessage(g);
    }

    private void renderUI(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 20, SCREEN_HEIGHT - 30);
        g.drawString("Lives: " + lives, SCREEN_WIDTH - 120, SCREEN_HEIGHT - 30);
        g.drawString("Level: " + level, SCREEN_WIDTH/2 - 30, SCREEN_HEIGHT - 30);

        int bricksLeft = 0;
        for (GameObject obj : gameObjects) {
            if (obj instanceof Brick && !((Brick) obj).isDestroyed()) {
                bricksLeft++;
            }
        }
        g.drawString("Bricks: " + bricksLeft, SCREEN_WIDTH/2 - 30, 30);
    }

    private void renderStateMessage(Graphics g) {
        if (currentState != GameState.PLAYING) {
            // Nền mờ
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));

            String message = "";
            switch (currentState) {
                case MENU:
                    message = "PRESS SPACE TO START";
                    break;
                case GAME_OVER:
                    message = "GAME OVER";
                    break;
                case VICTORY:
                    message = "VICTORY!";
                    break;
            }

            // Căn giữa message
            FontMetrics fm = g.getFontMetrics();
            int x = (SCREEN_WIDTH - fm.stringWidth(message)) / 2;
            g.drawString(message, x, SCREEN_HEIGHT/2);

            // Hướng dẫn
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String instruction = "Press SPACE to " + (currentState == GameState.MENU ? "start" : "restart");
            int instX = (SCREEN_WIDTH - g.getFontMetrics().stringWidth(instruction)) / 2;
            g.drawString(instruction, instX, SCREEN_HEIGHT/2 + 50);
        }
    }

    public void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case 37: // Left
                if (currentState == GameState.PLAYING) paddle.moveLeft();
                break;
            case 39: // Right
                if (currentState == GameState.PLAYING) paddle.moveRight();
                break;
            case 32: // Space
                handleSpaceKey();
                break;
        }
    }

    public void handleKeyRelease(int keyCode) {
        if (currentState == GameState.PLAYING) {
            if (keyCode == 37 || keyCode == 39) {
                paddle.stop();
            }
        }
    }

    private void handleSpaceKey() {
        System.out.println("SPACE pressed in state: " + currentState);

        switch (currentState) {
            case MENU:
            case GAME_OVER:
            case VICTORY:
                restartGame();
                break;
        }
    }

    public void restartGame() {
        System.out.println("🔄 Restarting game...");
        score = 0;
        lives = 3;
        level = 1;
        createGameObjects();
        currentState = GameState.PLAYING;
    }

    public GameState getCurrentState() { return currentState; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getLevel() { return level; }

    public static int getScreenWidth() { return SCREEN_WIDTH; }
    public static int getScreenHeight() { return SCREEN_HEIGHT; }
}