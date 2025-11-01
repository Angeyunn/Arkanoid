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

/**
 * Lop GamePanel de ve game
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {
    //Kich thuoc cua so
    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 480;

    // Luong cho game loop
    private Thread gameThread;

    private GameManager gameManager;

    public GamePanel() {
        this.gameManager = new GameManager(PANEL_WIDTH, PANEL_HEIGHT); //Khoi tao gameManager voi kich thuoc pixel moi
        this.gameManager.startGame(); //Khoi dong logic game
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK); //Nen den
        this.addKeyListener(this); //Doc tu ban phim
        this.setFocusable(true); //Thiet lap panel de co the nhan input phim
        startGameLoop(); //Bat dau game loop
    }

    //Bat dau luong game
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Game loop chinh
    @Override
    public void run() {
        while(true) {
            gameManager.updateGame(); //Cap nhat lai logic game
            repaint(); //Ve lai panel
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
        //Ve game
        List<GameObject> allObjects = gameManager.getAllGameObjects(); //Lay tat ca doi tuong tu gameManage
        //Neu doi tuong con hoat dong thi render
        for (GameObject object : allObjects) {
            if (object.isActive()) {
                object.render(g);
            }
        }
        //Ve UI (Diem, mang)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16)); //Font arial size 16
        g.drawString("Score: " + gameManager.getScore(), 10, 20);
        g.drawString("Lives: " + gameManager.getLives(), 10, 40);

        //Ve man hinh gameOver
        GameManager.GameState state = gameManager.getGameState();
        if (state == GameManager.GameState.GAME_OVER) {
            drawCenteredString(g, "GAME OVER", new Font("Arial", Font.BOLD, 40), PANEL_HEIGHT / 2 - 20);
            drawCenteredString(g, "Press enter to restart.", new Font("Arial", Font.PLAIN, 20), PANEL_HEIGHT / 2 + 20);
        } else if (state == GameManager.GameState.GAME_WIN) {
            drawCenteredString(g, "GAME WIN", new Font("Arial", Font.BOLD, 40), PANEL_HEIGHT / 2 - 20);
            drawCenteredString(g, "Press enter to restart.", new Font("Arial", Font.PLAIN, 20), PANEL_HEIGHT / 2 + 20);
        }
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
        //Khi phim duoc nhan, bao cho gameManager
        gameManager.handleInput(e.getKeyCode(), true);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameManager.togglePause();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //Khi phim duoc nha, bao cho gameManager
        gameManager.handleInput(e.getKeyCode(), false);
    }
}
