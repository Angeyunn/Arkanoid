package arkanoid.view;

import arkanoid.manager.GameManager;
import arkanoid.model.GameObject;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;

/**
 * Lop GamePanel de ve game
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    //Kich thuoc cua so
    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 480;

    private Thread gameThread;
    private GameManager gameManager;
    private BufferedImage playingBackground;
    private BufferedImage pauseScreenImage;

    public GamePanel() {
        this.gameManager = new GameManager(PANEL_WIDTH, PANEL_HEIGHT);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.playingBackground = ImageLoader.loadImage("playing_background.png");
        this.pauseScreenImage = ImageLoader.loadImage("pausing_screen.png");
        this.setBackground(Color.BLACK);
        this.addKeyListener(this);
        this.setFocusable(true);
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(true) {
            gameManager.updateGame();
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Phuong thuc ve
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GameManager.GameState state = gameManager.getGameState();

        switch (state) {
            case MENU:
                gameManager.getMenuScreen().render(g, gameManager);
                break;
            case PLAYING:
                drawGameScreen(g);
                break;
            case GAME_OVER:
                drawGameScreen(g); // Ve man hinh game cuoi
                drawGameOverScreen(g, "GAME OVER");
                break;
            case GAME_WIN:
                drawGameScreen(g); // Ve man hinh game cuoi
                drawGameOverScreen(g, "YOU WIN!");
                break;
            case PAUSED:
                drawGameScreen(g);
                drawPausedScreen(g);
                break;
            case LEVEL_SELECT:
                gameManager.getLevelSelectScreen().render(g, gameManager);
        }
    }

    // Hàm vẽ màn hình game
    private void drawGameScreen(Graphics g) {
        if (playingBackground != null) {
            g.drawImage(playingBackground, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
        }
        List<GameObject> allObjects = gameManager.getAllGameObjects();
        for (GameObject object : allObjects) {
            if (object.isActive()) {
                object.render(g);
            }
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Score: " + gameManager.getScore(), 10, 20);
        g.drawString("Lives: " + gameManager.getLives(), 10, 40);
    }


    private void drawPausedScreen(Graphics g) {
        if (pauseScreenImage != null) {
            // 3. Ve anh pause moi
            g.drawImage(pauseScreenImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
        }
    }

    // Ham ve man hinh thang, thua
    private void drawGameOverScreen(Graphics g, String message) {
        g.setColor(new Color(0, 0, 0, 150)); // Lớp phủ mờ
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        g.setColor(Color.WHITE);
        drawCenteredString(g, message, new Font("Arial", Font.BOLD, 40), PANEL_HEIGHT / 2 - 20);
        drawCenteredString(g, "Press Enter to return to Menu", new Font("Arial", Font.PLAIN, 20), PANEL_HEIGHT / 2 + 20);
    }

    //Ham de ve can giua
    private void drawCenteredString(Graphics g, String text, Font font, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (PANEL_WIDTH - metrics.stringWidth(text)) / 2;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Them sau
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameManager.handleInput(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gameManager.handleInput(e.getKeyCode(), false);
    }
}