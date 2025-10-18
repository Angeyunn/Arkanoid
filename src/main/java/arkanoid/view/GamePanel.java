package arkanoid.view;

import arkanoid.manager.GameManager;
import arkanoid.model.GameObject;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.util.List;

/**
 * Lop GamePanel de ve game
 */
public class GamePanel extends JPanel implements Runnable {
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
        List<GameObject> allObjects = gameManager.getAllGameObjects(); //Lay tat ca doi tuong tu gameManage
        //Neu doi tuong con hoat dong thi render
        for (GameObject object : allObjects) {
            if (object.isActive()) {
                object.render(g);
            }
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16)); //Font arial size 16
        g.drawString("Score: " + gameManager.getScore(), 10, 20);
        g.drawString("Lives: " + gameManager.getLives(), 10, 40);
    }
}
