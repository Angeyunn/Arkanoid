package arkanoid.view;

import arkanoid.manager.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import arkanoid.utils.ImageLoader;

/**
 * Lop quan li viec ve va xu li input o menu chon level
 */
public class LevelSelectScreen {

    private BufferedImage[] levelBackgrounds;//Luu 6 anh nen
    private int currentSelectedLevel = 1; //Level dang duoc chon
    private final int MAX_LEVELS = 6; //Tong so level

    //Phuong thuc tai anh nen
    public LevelSelectScreen() {
        levelBackgrounds = new BufferedImage[MAX_LEVELS];
        for (int i = 0; i < MAX_LEVELS; i++) {
            levelBackgrounds[i] = ImageLoader.loadImage("level_" + (i + 1) + "_bg.png");
        }
    }

    public void render(Graphics g, GameManager gm) {
        BufferedImage bgToDraw = levelBackgrounds[currentSelectedLevel - 1];
        if (bgToDraw != null) {
            g.drawImage(bgToDraw, 0, 0, GamePanel.PANEL_WIDTH, GamePanel.PANEL_HEIGHT, null);
        }
    }

    public void handleInput(int keyCode, GameManager gm, boolean isPressed) {
        if (!isPressed) {
            return;
        }
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                currentSelectedLevel--;
                if (currentSelectedLevel < 1) {
                    currentSelectedLevel = MAX_LEVELS; //Quay ve level 6
                }
                break;
            case KeyEvent.VK_RIGHT:
                currentSelectedLevel++;
                if (currentSelectedLevel > MAX_LEVELS) {
                    currentSelectedLevel = 1; // Quay vòng về level 1
                }
                break;
            case KeyEvent.VK_ENTER:
                gm.loadLevelAndStart(currentSelectedLevel);
                break;
            case KeyEvent.VK_ESCAPE: // Cho phép quay lại Menu
                gm.goToMenu();
                break;
        }
    }

    //Ham de can giua
    private void drawCenteredString(Graphics g, String string, Font font, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (GamePanel.PANEL_WIDTH - metrics.stringWidth(string)) / 2;
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(string, x, y);
    }
}