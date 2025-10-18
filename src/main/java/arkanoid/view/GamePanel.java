package arkanoid.view;

import arkanoid.manager.GameManager;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

/**
 * Lop GamePanel de ve game
 */
public class GamePanel extends JPanel {
    //Kich thuoc cua so
    public static final int PANEL_WIDTH = 640;
    public static final int PANEL_HEIGHT = 480;

    private GameManager gameManager;

    public GamePanel() {
        this.gameManager = new GameManager(PANEL_WIDTH, PANEL_HEIGHT); //Khoi tao gameManager voi kich thuoc pixel moi
        this.gameManager.startGame(); //Khoi dong logic game
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK); //Nen den

    }
}
